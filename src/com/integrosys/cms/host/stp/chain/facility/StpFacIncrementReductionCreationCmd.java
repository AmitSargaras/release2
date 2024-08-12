package com.integrosys.cms.host.stp.chain.facility;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.*;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;

import java.util.*;

/**
 * Facility incremental reduction create and update Stp message
 *
 * @author Andy Wong
 * @since Sep 29, 2009
 */
public class StpFacIncrementReductionCreationCmd extends AbstractStpCommand {

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
            // facility incremental create and update transaction
            for (Iterator iterator = obFacilityMaster.getFacilityIncrementals().iterator(); iterator.hasNext();) {
                OBFacilityIncremental facilityIncremental = (OBFacilityIncremental) iterator.next();
                ctx.put(REF_NUM, facilityIncremental.getCmsReferenceId());
                ctx.put(STP_TRANS_MAP, stpTransMap);
                fieldValMap.put("INIDFL", "I");
                OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

                //put the OB into an array of object
                ArrayList aObject = new ArrayList();
                aObject.add(limit);
                aObject.add(facilityIncremental);
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

            // facility reduction create and update transaction
            for (Iterator iterator = obFacilityMaster.getFacilityReductions().iterator(); iterator.hasNext();) {
                OBFacilityReduction facilityReduction = (OBFacilityReduction) iterator.next();
                ctx.put(REF_NUM, facilityReduction.getCmsReferenceId());
                ctx.put(STP_TRANS_MAP, stpTransMap);
                fieldValMap.put("INIDFL", "R");
                OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

                //put the OB into an array of object
                ArrayList aObject = new ArrayList();
                aObject.add(limit);
                aObject.add(facilityReduction);
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
        }

        return false;
    }

    public boolean processResponse(Map ctx, OBStpTrans obStpTrans, List obStpFieldL) throws Exception {
        boolean successStp = super.processResponse(ctx, obStpTrans, obStpFieldL);

        if (successStp) {
            OBFacilityTrxValue facilityTrxValue = (OBFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
            IFacilityMaster actualFacilityMaster = facilityTrxValue.getFacilityMaster();
            IFacilityMaster stageFacilityMaster = facilityTrxValue.getStagingFacilityMaster();

            //map to actual and stage OB based on cmsRefId
            for (Iterator iterator = actualFacilityMaster.getFacilityIncrementals().iterator(); iterator.hasNext();) {
                OBFacilityIncremental facilityIncremental = (OBFacilityIncremental) iterator.next();
                if (facilityIncremental.getCmsReferenceId() != null
                        && facilityIncremental.getCmsReferenceId().equals(obStpTrans.getReferenceId())) {
                    getStpMapper().mapToBizOB(facilityIncremental, obStpFieldL);
                    break;
                }
            }
            for (Iterator iterator = stageFacilityMaster.getFacilityIncrementals().iterator(); iterator.hasNext();) {
                OBFacilityIncremental facilityIncremental = (OBFacilityIncremental) iterator.next();
                if (facilityIncremental.getCmsReferenceId() != null
                        && facilityIncremental.getCmsReferenceId().equals(obStpTrans.getReferenceId())) {
                    getStpMapper().mapToBizOB(facilityIncremental, obStpFieldL);
                    break;
                }
            }


            for (Iterator iterator = actualFacilityMaster.getFacilityReductions().iterator(); iterator.hasNext();) {
                OBFacilityReduction facilityReduction = (OBFacilityReduction) iterator.next();
                if (facilityReduction.getCmsReferenceId() != null
                        && facilityReduction.getCmsReferenceId().equals(obStpTrans.getReferenceId())) {
                    getStpMapper().mapToBizOB(facilityReduction, obStpFieldL);
                    break;
                }
            }
            for (Iterator iterator = stageFacilityMaster.getFacilityReductions().iterator(); iterator.hasNext();) {
                OBFacilityReduction facilityReduction = (OBFacilityReduction) iterator.next();
                if (facilityReduction.getCmsReferenceId() != null
                        && facilityReduction.getCmsReferenceId().equals(obStpTrans.getReferenceId())) {
                    getStpMapper().mapToBizOB(facilityReduction, obStpFieldL);
                    break;
                }
            }
            ctx.put(FAC_TRX_VALUE, facilityTrxValue);
        }
        return successStp;
    }
}