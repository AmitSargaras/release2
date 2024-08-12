package com.integrosys.cms.host.stp.chain.facility;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Facility islamic security deposit delete Stp message
 *
 * @author Andy Wong
 * @since Sep 29, 2009
 */
public class StpFacIslamicSecDepositDeletionCmd extends AbstractStpCommand {
//    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        HashMap fieldValMap = new HashMap();
        OBFacilityTrxValue obFacilityTrxValue = (OBFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
        OBFacilityMaster obFacilityMaster = (OBFacilityMaster) obFacilityTrxValue.getFacilityMaster();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        ctx.put(FIELD_VAL_MAP, fieldValMap);
        ILimit limit = getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getLimitID());
        ILimitProfile limitProfile = getActualLimitBusManager().getLimitProfile(limit.getLimitProfileID());

        //only stp when application type is corporate
        if (limitProfile.getApplicationType().equals(ICMSConstant.APPLICATION_TYPE_CO)
                || limitProfile.getApplicationType().equals(ICMSConstant.APPLICATION_TYPE_CO_HP)) {
            ctx.put(REF_NUM, Long.toString(obFacilityMaster.getId()));
            ctx.put(STP_TRANS_MAP, stpTransMap);
            ctx.put(IS_DELETE, new Boolean(true));
            OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

            // StpTrans OB will be null if delete trx had been succeed
            if (obStpTrans != null) {
                //put the OB into an array of object
                ArrayList aObject = new ArrayList();
                aObject.add(limit);
                aObject.add(fieldValMap);

                //map to field OB
                List stpList = prepareRequest(ctx, aObject.toArray(), obStpTrans.getTrxType(), ISTPMapper.FACILITY_PATH);
                // construct msg, send msg and decipher msg
                stpList = sendMessage(obStpTrans.getReferenceId(), ctx, stpList, obStpTrans.getTrxType());
                //put required OB in context to be used in subsequent command
                ctx.put(STP_TRX_VALUE, obStpMasterTrans);
                ctx.put(FAC_TRX_VALUE, obFacilityTrxValue);

                //map object to biz OB and update STP transaction
                boolean successUpd = processResponse(ctx, obStpTrans, stpList);
                if (!successUpd) {
                    return true;
                }
            }
        }
        return false;
    }

}