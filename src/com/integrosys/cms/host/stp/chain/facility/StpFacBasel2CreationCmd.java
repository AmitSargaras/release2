package com.integrosys.cms.host.stp.chain.facility;

import com.integrosys.cms.app.limit.bus.IFacilityBnmCodes;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Facility Basel II create and update Stp message
 *
 * @author Andy Wong
 * @since Sep 28, 2009
 */
public class StpFacBasel2CreationCmd extends AbstractStpCommand {
//    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        HashMap fieldValMap = new HashMap();
        OBFacilityTrxValue obFacilityTrxValue = (OBFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
        OBFacilityMaster obFacilityMaster = (OBFacilityMaster) obFacilityTrxValue.getFacilityMaster();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        IFacilityBnmCodes bnmCodes = obFacilityMaster.getFacilityBnmCodes();

        // Andy Wong, Sep 30, 2009: only stp when IRB or SA Finalized field not empty
        if (StringUtils.isNotEmpty(bnmCodes.getBaselIRBEntryCode())
                || StringUtils.isNotEmpty(bnmCodes.getBaselSAFinalisedEntryCode())) {
            ctx.put(FIELD_VAL_MAP, fieldValMap);
            ctx.put(REF_NUM, obFacilityTrxValue.getReferenceID());
            ctx.put(STP_TRANS_MAP, StpCommandUtil.getTrxMap(obStpMasterTrans));
            OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

            ILimit actualLimit = getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getLimitID());
            // put the OB into an array of object
            ArrayList aObject = new ArrayList();
            aObject.add(actualLimit);
            aObject.add(bnmCodes);
            aObject.add(fieldValMap);

            // map to field OB
            List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.FACILITY_PATH);
            // construct msg, send msg and decipher msg
            stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());

            // put required OB in context to be used in subsequent command
            ctx.put(STP_TRX_VALUE, obStpMasterTrans);
            ctx.put(FAC_TRX_VALUE, obFacilityTrxValue);

            // map object to biz OB and update STP transaction
            boolean successUpd = processResponse(ctx, obStpTrans, stpList);
            if (!successUpd) {
                return true;
            } else {
                updateFacWithoutTrx(ctx, obFacilityTrxValue);
                return false;
            }
        }
        return false;
    }

    public boolean processResponse(Map ctx, OBStpTrans obStpTrans, List obStpFieldL) throws Exception {
        boolean successStp = super.processResponse(ctx, obStpTrans, obStpFieldL);

        if (successStp) {
            //persist SA concept code to CMS DB
            IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
            IFacilityBnmCodes actualBnmCodes = facilityTrxValue.getFacilityMaster().getFacilityBnmCodes();
            IFacilityBnmCodes stageBnmCodes = facilityTrxValue.getStagingFacilityMaster().getFacilityBnmCodes();
            getStpMapper().mapToBizOB(actualBnmCodes, obStpFieldL);
            getStpMapper().mapToBizOB(stageBnmCodes, obStpFieldL);

            ctx.put(FAC_TRX_VALUE, facilityTrxValue);
        }
        return successStp;
    }
}