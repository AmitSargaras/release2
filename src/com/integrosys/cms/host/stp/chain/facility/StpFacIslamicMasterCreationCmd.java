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
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Sai Heng
 * Date: Jan 13, 2009
 * Time: 11:10:52 AM
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Sai Heng
 * @since 13 Jan 2009
 */
public class StpFacIslamicMasterCreationCmd extends AbstractStpCommand {
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

        OBFacilityIslamicMaster obFacilityIslamicMaster = (OBFacilityIslamicMaster) obFacilityMaster.getFacilityIslamicMaster();
        OBLimit obLimit = (OBLimit) getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getLimitID());

//        logger.debug("obFacilityMaster.getLimit().getLimitID() ---------------- " + obFacilityMaster.getLimit().getLimitID());

        //do conversion from 100% to 0-1 number
        convertPercentToDecimalPoint(obFacilityIslamicMaster, fieldValMap);

        //put the OB into an array of object
        ArrayList aObject = new ArrayList();
        aObject.add(obLimit);
        aObject.add(obFacilityIslamicMaster);
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
        boolean successStp = super.processResponse(ctx, obStpTrans, obStpFieldL);

        if (successStp) { //Check valid stp response
            IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
            IFacilityMaster actualFacilityMaster = (IFacilityMaster) facilityTrxValue.getFacilityMaster();
            IFacilityMaster stageFacilityMaster = (IFacilityMaster) facilityTrxValue.getStagingFacilityMaster();

            IFacilityIslamicMaster actualFacilityIslamicMaster = actualFacilityMaster.getFacilityIslamicMaster();
            IFacilityIslamicMaster stageFacilityIslamicMaster = stageFacilityMaster.getFacilityIslamicMaster();
            getStpMapper().mapToBizOB(stageFacilityIslamicMaster, obStpFieldL);
            getStpMapper().mapToBizOB(actualFacilityIslamicMaster, obStpFieldL);
            ctx.put(FAC_TRX_VALUE, facilityTrxValue);
        }
        return successStp;
    }

    private void convertPercentToDecimalPoint(OBFacilityIslamicMaster obFacilityIslamicMaster, Map fieldValMap){
        if ((obFacilityIslamicMaster.getSecurityDepPercentage() != null) && (obFacilityIslamicMaster.getSecurityDepPercentage().doubleValue() > 0)) {
//			double securityDepPercentage = obFacilityIslamicMaster.getSecurityDepPercentage().doubleValue() / 100;
            BigDecimal bigDecimal = new BigDecimal(obFacilityIslamicMaster.getSecurityDepPercentage().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AISCDP", bigDecimal.toString());
		}
        if ((obFacilityIslamicMaster.getCustomerInterestRate() != null) && (obFacilityIslamicMaster.getCustomerInterestRate().doubleValue() > 0)) {
//			double customerInterestRate = obFacilityIslamicMaster.getCustomerInterestRate().doubleValue() / 100;
            BigDecimal bigDecimal = new BigDecimal(obFacilityIslamicMaster.getCustomerInterestRate().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AICRAT", bigDecimal.toString());
		}
        if ((obFacilityIslamicMaster.getCommissionRate() != null) && (obFacilityIslamicMaster.getCommissionRate().doubleValue() > 0)) {
//			double commissionRate = obFacilityIslamicMaster.getCommissionRate().doubleValue() / 100;
            BigDecimal bigDecimal = new BigDecimal(obFacilityIslamicMaster.getCommissionRate().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AICOMR", bigDecimal.toString());
		}
    }
}