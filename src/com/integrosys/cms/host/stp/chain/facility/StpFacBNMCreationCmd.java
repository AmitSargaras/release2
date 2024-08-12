package com.integrosys.cms.host.stp.chain.facility;

import com.integrosys.cms.app.limit.bus.*;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.component.user.app.bus.ICommonUser;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 4, 2008
 * Time: 11:10:52 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Andy Wong
 * @since 23 Oct 2008
 */
public class StpFacBNMCreationCmd extends AbstractStpCommand {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public boolean execute(Map ctx) throws Exception {
        HashMap fieldValMap = new HashMap();
        OBFacilityTrxValue obFacilityTrxValue = (OBFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
        OBFacilityMaster obFacilityMaster = (OBFacilityMaster) obFacilityTrxValue.getFacilityMaster();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);

        ctx.put(FIELD_VAL_MAP, fieldValMap);
        ctx.put(REF_NUM, obFacilityTrxValue.getReferenceID());
        ctx.put(STP_TRANS_MAP, StpCommandUtil.getTrxMap(obStpMasterTrans));
        OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

        OBFacilityBnmCodes obFacilityBnmCodes = (OBFacilityBnmCodes) obFacilityMaster.getFacilityBnmCodes();
        OBLimit obLimit = (OBLimit) getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getLimitID());

        //put the OB into an array of object
        ArrayList aObject = new ArrayList();
        aObject.add(obLimit);
        aObject.add(obFacilityBnmCodes);
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
        } else {
            updateFacWithoutTrx(ctx, obFacilityTrxValue);
            return false;
        }
    }

    public boolean processResponse(Map ctx, OBStpTrans obStpTrans, List obStpFieldL) throws Exception {
        //Modified by KLYong: Map to biz object only if stp message was valid AA
        boolean successStp = super.processResponse(ctx, obStpTrans, obStpFieldL);

        if (successStp) { //Check valid stp response
            IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
            IFacilityMaster actualFacilityMaster = (IFacilityMaster) facilityTrxValue.getFacilityMaster();
            IFacilityMaster stageFacilityMaster = (IFacilityMaster) facilityTrxValue.getStagingFacilityMaster();

            IFacilityBnmCodes actualFacilityBnmCodes = actualFacilityMaster.getFacilityBnmCodes();
            IFacilityBnmCodes stageFacilityBnmCodes = stageFacilityMaster.getFacilityBnmCodes();
            getStpMapper().mapToBizOB(stageFacilityBnmCodes, obStpFieldL);
            getStpMapper().mapToBizOB(actualFacilityBnmCodes, obStpFieldL);
            ctx.put(FAC_TRX_VALUE, facilityTrxValue);
        }
        return successStp;
    }

}
