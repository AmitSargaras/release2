package com.integrosys.cms.host.stp.chain.facility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.cms.app.limit.bus.IFacilityFeeCharge;
import com.integrosys.cms.app.limit.bus.IFacilityGeneral;
import com.integrosys.cms.app.limit.bus.IFacilityInterest;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityPayment;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBFacilityFeeCharge;
import com.integrosys.cms.app.limit.bus.OBFacilityGeneral;
import com.integrosys.cms.app.limit.bus.OBFacilityInterest;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityPayment;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyImpl;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.host.stp.bus.OBStpMasterTrans;
import com.integrosys.cms.host.stp.bus.OBStpTrans;
import com.integrosys.cms.host.stp.chain.AbstractStpCommand;
import com.integrosys.cms.host.stp.common.StpCommandUtil;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.mapper.ISTPMapper;

/**
 * Created by IntelliJ IDEA.
 * User: Jerlin Ong
 * Date: Sep 3, 2008
 * Time: 2:22:26 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * @author Andy Wong
 * @since 22 Oct 2008
 */
public class StpFacMasterCreationCmd extends AbstractStpCommand {
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

		ILimit actualLimit = getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getLimitID());
		fieldValMap.put("AFCIFN", actualLimit.getLEReference()); // map to CIF                    
        //Andy Wong, 18 Jan 2009: default collateral type if facility code in 110, 120, 140, 160, 170, otherwise O
        if(StringUtils.equals(actualLimit.getFacilityCode(), "110")
            || StringUtils.equals(actualLimit.getFacilityCode(), "160")
            || StringUtils.equals(actualLimit.getFacilityCode(), "170")){
            fieldValMap.put("AFTPSQ", "1");
        } else if (StringUtils.equals(actualLimit.getFacilityCode(), "120")){
            fieldValMap.put("AFTPSQ", "2");
        } else if (StringUtils.equals(actualLimit.getFacilityCode(), "140")){
            fieldValMap.put("AFTPSQ", "4");
        }

        // Andy Wong, Dec 4, 2009: set main facility seq for inner limit if it's omnibus
        if(obFacilityMaster.getLimit().getOuterLimitID() > 0
                && obFacilityMaster.getLimit().getLimitID() != obFacilityMaster.getLimit().getOuterLimitID()
                && (obFacilityMaster.getMainFacilitySequenceNumber() == null || obFacilityMaster.getMainFacilitySequenceNumber().equals(new Long(0)))){
            ILimit omnibus = getActualLimitBusManager().getLimit(obFacilityMaster.getLimit().getOuterLimitID());
            if(omnibus.getFacilitySequence() > 0)
                obFacilityMaster.setMainFacilitySequenceNumber(new Long(omnibus.getFacilitySequence()));
            else
                throw new StpCommonException(obStpTrans.getTrxId(), ERR_CODE_BIZ_GENERIC, "Omnibus facility sequence is zero, please stp to host successfully before inner limit.");
        }

        //do conversion from 100% to 0-1 number
        convertPercentToDecimalPoint(obFacilityMaster, fieldValMap);

        // set facility currency code from obLimit
        if (actualLimit.getApprovedLimitAmount() != null) {
            fieldValMap.put(IStpConstants.MSG_FAC_CURRENCY_FIELD, actualLimit.getApprovedLimitAmount().getCurrencyCode());
        }

        // put the OB into an array of object
		ArrayList aObject = new ArrayList();
		aObject.add(obFacilityMaster);
		aObject.add(actualLimit);
		aObject.add(obFacilityMaster.getFacilityGeneral());
		aObject.add(obFacilityMaster.getFacilityInterest());
		aObject.add(obFacilityMaster.getFacilityFeeCharge());
		aObject.add(obFacilityMaster.getFacilityPayment());
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
		}
		else {
			updateFacWithoutTrx(ctx, obFacilityTrxValue);
			ILimitTrxValue obLimitTrxValue = (ILimitTrxValue) ctx.get(LIMIT_TRX_VALUE);
			updateLimitWithoutTrx(obLimitTrxValue.getLimit(), obLimitTrxValue.getStagingLimit());
			return false;
		}
	}

	public boolean processResponse(Map ctx, OBStpTrans obStpTrans, List obStpFieldL) throws Exception {
		// Modified by KLYong: Map to biz object only if stp message was valid
		// AA
		boolean successStp = super.processResponse(ctx, obStpTrans, obStpFieldL);

		if (successStp) { // Check valid stp response
			IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) ctx.get(FAC_TRX_VALUE);
			IFacilityMaster actualFacilityMaster = facilityTrxValue.getFacilityMaster();
			IFacilityMaster stageFacilityMaster = facilityTrxValue.getStagingFacilityMaster();
			getStpMapper().mapToBizOB(actualFacilityMaster, obStpFieldL);
			getStpMapper().mapToBizOB(stageFacilityMaster, obStpFieldL);

			IFacilityGeneral actualFacilityGeneral = actualFacilityMaster.getFacilityGeneral();
			IFacilityGeneral stageFacilityGeneral = stageFacilityMaster.getFacilityGeneral();
			getStpMapper().mapToBizOB(actualFacilityGeneral, obStpFieldL);
			getStpMapper().mapToBizOB(stageFacilityGeneral, obStpFieldL);

			IFacilityInterest actualFacilityInterest = actualFacilityMaster.getFacilityInterest();
			IFacilityInterest stageFacilityInterest = stageFacilityMaster.getFacilityInterest();
			getStpMapper().mapToBizOB(actualFacilityInterest, obStpFieldL);
			getStpMapper().mapToBizOB(stageFacilityInterest, obStpFieldL);

			IFacilityFeeCharge actualFacilityFeeCharge = actualFacilityMaster.getFacilityFeeCharge();
			IFacilityFeeCharge stageFacilityFeeCharge = stageFacilityMaster.getFacilityFeeCharge();
			getStpMapper().mapToBizOB(actualFacilityFeeCharge, obStpFieldL);
			getStpMapper().mapToBizOB(stageFacilityFeeCharge, obStpFieldL);

			IFacilityPayment actualFacilityPayment = actualFacilityMaster.getFacilityPayment();
			IFacilityPayment stageFacilityPayment = stageFacilityMaster.getFacilityPayment();
			getStpMapper().mapToBizOB(actualFacilityPayment, obStpFieldL);
			getStpMapper().mapToBizOB(stageFacilityPayment, obStpFieldL);

            // retrieve existing Limit from Limit Trx value
            ILimitProxy limitProxy = new LimitProxyImpl();
            ILimitTrxValue limitTrxValue = limitProxy.getTrxLimit(actualFacilityMaster.getLimit().getLimitID());

			ILimit actualLimit = limitTrxValue.getLimit();
			ILimit stageLimit = limitTrxValue.getStagingLimit();

            getStpMapper().mapToBizOB(actualLimit, obStpFieldL);
			getStpMapper().mapToBizOB(stageLimit, obStpFieldL);

			// combine AA Num + Fac Code + Fac Seq as Limit ID
			actualLimit.setLimitRef(actualLimit.getLimitProfileReferenceNumber() + actualLimit.getFacilityCode()
					+ actualLimit.getFacilitySequence());
			stageLimit.setLimitRef(actualLimit.getLimitRef());

			actualFacilityMaster.setLimit(actualLimit);
            //Andy Wong: set actual limit OB into stg facility master, facility master using all actual
			stageFacilityMaster.setLimit(actualLimit);
			ctx.put(FAC_TRX_VALUE, facilityTrxValue);

			limitTrxValue.setLimit(actualLimit);
			limitTrxValue.setStagingLimit(stageLimit);
			ctx.put(LIMIT_TRX_VALUE, limitTrxValue);
		}
		return successStp;
	}

    private void convertPercentToDecimalPoint(OBFacilityMaster obFacilityMaster, Map fieldValMap){
//        OBFacilityGeneral obFacilityGeneral = (OBFacilityGeneral) obFacilityMaster.getFacilityGeneral();
		OBFacilityInterest obFacilityInterest = (OBFacilityInterest) obFacilityMaster.getFacilityInterest();
		OBFacilityFeeCharge obFacilityFeeCharge = (OBFacilityFeeCharge) obFacilityMaster.getFacilityFeeCharge();
//		OBFacilityPayment obFacilityPayment = (OBFacilityPayment) obFacilityMaster.getFacilityPayment();

        if ((obFacilityInterest.getInterestRate() != null) && (obFacilityInterest.getInterestRate().doubleValue() > 0)) {
            BigDecimal bigDecimal = new BigDecimal(obFacilityInterest.getInterestRate().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AFRATE", bigDecimal.toString());
		}
		if ((obFacilityInterest.getSpread() != null) && (obFacilityInterest.getSpread().doubleValue() > 0)) {
            BigDecimal bigDecimal = new BigDecimal(obFacilityInterest.getSpread().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AFVAR", bigDecimal.toString());
		}
		if ((obFacilityInterest.getPrimeRateFloor() != null)
				&& (obFacilityInterest.getPrimeRateFloor().doubleValue() > 0)) {
            BigDecimal bigDecimal = new BigDecimal(obFacilityInterest.getPrimeRateFloor().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AFPFLR", bigDecimal.toString());
		}
		if ((obFacilityInterest.getPrimeRateCeiling() != null)
				&& (obFacilityInterest.getPrimeRateCeiling().doubleValue() > 0)) {
            BigDecimal bigDecimal = new BigDecimal(obFacilityInterest.getPrimeRateCeiling().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AFPCEL", bigDecimal.toString());
		}
		if ((obFacilityMaster.getOdExcessRateVar() != null)
				&& (obFacilityMaster.getOdExcessRateVar().doubleValue() > 0)) {
            BigDecimal bigDecimal = new BigDecimal(obFacilityMaster.getOdExcessRateVar().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AFEXRT", bigDecimal.toString());
		}
		if ((obFacilityFeeCharge.getCommissionRate() != null)
				&& (obFacilityFeeCharge.getCommissionRate().doubleValue() > 0)) {
            BigDecimal bigDecimal = new BigDecimal(obFacilityFeeCharge.getCommissionRate().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AFCOMR", bigDecimal.toString());
		}
		if ((obFacilityFeeCharge.getFacilityCommitmentRate() != null)
				&& (obFacilityFeeCharge.getFacilityCommitmentRate().doubleValue() > 0)) {
            BigDecimal bigDecimal = new BigDecimal(obFacilityFeeCharge.getFacilityCommitmentRate().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AFCFER", bigDecimal.toString());
		}
		if ((obFacilityMaster.getEcofRate() != null) && (obFacilityMaster.getEcofRate().doubleValue() > 0)) {
            BigDecimal bigDecimal = new BigDecimal(obFacilityMaster.getEcofRate().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AFECOR", bigDecimal.toString());
		}
		if ((obFacilityMaster.getEcofVariance() != null) && (obFacilityMaster.getEcofVariance().doubleValue() > 0)) {
            BigDecimal bigDecimal = new BigDecimal(obFacilityMaster.getEcofVariance().doubleValue()).
                    setScale(9, BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(100), BigDecimal.ROUND_HALF_UP);
            fieldValMap.put("AFECV", bigDecimal.toString());
		}
    }
}
