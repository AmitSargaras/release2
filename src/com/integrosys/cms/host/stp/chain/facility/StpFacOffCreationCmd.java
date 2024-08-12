package com.integrosys.cms.host.stp.chain.facility;

import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityOfficer;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;
import com.integrosys.cms.ui.limit.facility.FacilityUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 4, 2008
 * Time: 11:12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class StpFacOffCreationCmd extends AbstractStpCommand {

    public boolean execute(Map ctx) throws Exception {
        HashMap fieldValMap = new HashMap();
        OBFacilityTrxValue obFacilityTrxValue = (OBFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
        OBFacilityMaster obFacilityMaster = (OBFacilityMaster) obFacilityTrxValue.getFacilityMaster();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        ctx.put(FIELD_VAL_MAP, fieldValMap);

        OBLimit obLimit = (OBLimit) getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getLimitID());

        //Filter out deleted facility officer
        obFacilityMaster.setFacilityOfficerSet(FacilityUtil.filterDeletedOfficer(obFacilityMaster.getFacilityOfficerSet()));

        //create and update transaction
        for (Iterator iterator = obFacilityMaster.getFacilityOfficerSet().iterator(); iterator.hasNext();) {
            OBFacilityOfficer obFacilityOfficer = (OBFacilityOfficer) iterator.next();
            ctx.put(REF_NUM, obFacilityOfficer.getCmsRefId());
            ctx.put(STP_TRANS_MAP, stpTransMap);
            OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

            //put the OB into an array of object
            ArrayList aObject = new ArrayList();
            aObject.add(obLimit);
            aObject.add(obFacilityOfficer);
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
            }
        }
        return false;
    }

    public boolean processResponse(Map ctx, OBStpTrans obStpTrans, List obStpFieldL) throws Exception {
        //Modified by KLYong: Map to biz object only if stp message was valid AA
        boolean successStp = super.processResponse(ctx, obStpTrans, obStpFieldL);

        if (successStp) { //Check valid stp response
            IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
            IFacilityMaster actualFacilityMaster = (IFacilityMaster) facilityTrxValue.getFacilityMaster();
            IFacilityMaster stageFacilityMaster = (IFacilityMaster) facilityTrxValue.getStagingFacilityMaster();

            //Andy Wong: map to actual and stage OB based on cmsRefId
            for (Iterator iterator = actualFacilityMaster.getFacilityOfficerSet().iterator(); iterator.hasNext();) {
                OBFacilityOfficer obFacilityOfficer = (OBFacilityOfficer) iterator.next();
                if (obFacilityOfficer.getCmsRefId()!=null
                        && obFacilityOfficer.getCmsRefId().equals(obStpTrans.getReferenceId())) {
                    getStpMapper().mapToBizOB(obFacilityOfficer, obStpFieldL);
                    break;
                }
            }
            for (Iterator iterator = stageFacilityMaster.getFacilityOfficerSet().iterator(); iterator.hasNext();) {
                OBFacilityOfficer obFacilityOfficer = (OBFacilityOfficer) iterator.next();
                if (obFacilityOfficer.getCmsRefId()!=null
                        && obFacilityOfficer.getCmsRefId().equals(obStpTrans.getReferenceId())) {
                    getStpMapper().mapToBizOB(obFacilityOfficer, obStpFieldL);
                    break;
                }
            }

            ctx.put(FAC_TRX_VALUE, facilityTrxValue);
        }
        return successStp;
    }
}
