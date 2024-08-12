package com.integrosys.cms.host.stp.chain.facility;

import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityRelationship;
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
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 4, 2008
 * Time: 11:12:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class StpFacRelCreationCmd extends AbstractStpCommand {

    public boolean execute(Map ctx) throws Exception {
        HashMap fieldValMap = new HashMap();
        OBFacilityTrxValue obFacilityTrxValue = (OBFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
        OBFacilityMaster obFacilityMaster = (OBFacilityMaster) obFacilityTrxValue.getFacilityMaster();
        OBStpMasterTrans obStpMasterTrans = (OBStpMasterTrans) ctx.get(STP_TRX_VALUE);
        Map stpTransMap = StpCommandUtil.getTrxMap(obStpMasterTrans);

        ctx.put(FIELD_VAL_MAP, fieldValMap);
        OBLimit obLimit = (OBLimit) getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getLimitID());

        //Filter out deleted facility relationship
        obFacilityMaster.setFacilityRelationshipSet(FacilityUtil.filterDeletedRelationship(obFacilityMaster.getFacilityRelationshipSet()));

        //create and update transaction
        for (Iterator iterator = obFacilityMaster.getFacilityRelationshipSet().iterator(); iterator.hasNext();) {
            OBFacilityRelationship obFacilityRelationship = (OBFacilityRelationship) iterator.next();
            ctx.put(REF_NUM, obFacilityRelationship.getCmsRefId());
            ctx.put(STP_TRANS_MAP, stpTransMap);
            OBStpTrans obStpTrans = (OBStpTrans) initTransaction(ctx);

            //put the OB into an array of object
            ArrayList aObject = new ArrayList();
            aObject.add(obLimit);
            aObject.add(obFacilityRelationship);
            if (obFacilityRelationship.getGuaranteePercentage() != null && obFacilityRelationship.getGuaranteePercentage().doubleValue() > 0) {
                BigDecimal bigDecimal = new BigDecimal(obFacilityRelationship.getGuaranteePercentage().doubleValue()).
                        setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
                fieldValMap.put("ACGTEP", bigDecimal.toString());
            }
            if (obFacilityRelationship.getShareHolderPercentage() != null && obFacilityRelationship.getShareHolderPercentage().doubleValue() > 0) {
                BigDecimal bigDecimal = new BigDecimal(obFacilityRelationship.getShareHolderPercentage().doubleValue()).
                        setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
                fieldValMap.put("ACSHRP", bigDecimal.toString());
            }
            if (obFacilityRelationship.getProfitRatio() != null && obFacilityRelationship.getProfitRatio().doubleValue() > 0) {
                BigDecimal bigDecimal = new BigDecimal(obFacilityRelationship.getProfitRatio().doubleValue()).
                        setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
                fieldValMap.put("ACAPAO", bigDecimal.toString());
            }
            if (obFacilityRelationship.getDividendRatio() != null && obFacilityRelationship.getDividendRatio().doubleValue() > 0) {
                BigDecimal bigDecimal = new BigDecimal(obFacilityRelationship.getDividendRatio().doubleValue()).
                        setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
                fieldValMap.put("ACAPIO", bigDecimal.toString());
            }

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
            for (Iterator iterator = actualFacilityMaster.getFacilityRelationshipSet().iterator(); iterator.hasNext();) {
                OBFacilityRelationship obFacilityRelationship = (OBFacilityRelationship) iterator.next();
                if (obFacilityRelationship.getCmsRefId()!=null
                        && obFacilityRelationship.getCmsRefId().equals(obStpTrans.getReferenceId())) {
                    getStpMapper().mapToBizOB(obFacilityRelationship, obStpFieldL);
                    break;
                }
            }
            for (Iterator iterator = stageFacilityMaster.getFacilityRelationshipSet().iterator(); iterator.hasNext();) {
                OBFacilityRelationship obFacilityRelationship = (OBFacilityRelationship) iterator.next();
                if (obFacilityRelationship.getCmsRefId()!=null
                        && obFacilityRelationship.getCmsRefId().equals(obStpTrans.getReferenceId())) {
                    getStpMapper().mapToBizOB(obFacilityRelationship, obStpFieldL);
                    break;
                }
            }
            ctx.put(FAC_TRX_VALUE, facilityTrxValue);
        }
        return successStp;
    }
}
