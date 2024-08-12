/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/security/SecurityMapper.java,v 1.20 2006/10/10 08:00:06 jzhan Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.security;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBLimitCharge;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;
import com.integrosys.cms.ui.collateral.CollateralMapper;
import com.integrosys.cms.ui.collateral.commodity.secapportion.DeleteApportionmentMapper;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.20 $
 * @since $Date: 2006/10/10 08:00:06 $ Tag: $Name: $
 */

public class SecurityMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		SecurityForm aForm = (SecurityForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		int indexID = Integer.parseInt((String) inputs.get("indexID"));
		HashMap trxValueMap = (HashMap) inputs.get("commodityMainTrxValue");
		ICommodityCollateral[] col = (ICommodityCollateral[]) trxValueMap.get("staging");
		OBCommodityCollateral obToChange = null;

		try {
			if ((col == null) || (indexID >= col.length)) {
				return null;
			}
			obToChange = (OBCommodityCollateral) AccessorUtil.deepClone(col[indexID]);
			// obToChange = (OBCommodityCollateral)(col[indexID]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		obToChange.setCurrencyCode(aForm.getCmsSecurityCurrency());
		ILimitCharge[] limitChargeList = obToChange.getLimitCharges();
		OBLimitCharge limitCharge;
		if ((limitChargeList != null) && (limitChargeList.length > 0)) {
			limitCharge = (OBLimitCharge) limitChargeList[0];
			if (limitCharge == null) {
				limitCharge = new OBLimitCharge();
			}
		}
		else {
			limitCharge = new OBLimitCharge();
		}
		obToChange.setIsLE(aForm.getLe());
		obToChange.setLEDate(CollateralMapper.compareDate(locale, obToChange.getLEDate(), aForm.getLeDate()));
		/*
		 * limitCharge.setIsLE(aForm.getLe());
		 * limitCharge.setLEDate(CollateralMapper.compareDate(locale,
		 * limitCharge.getLEDate(), aForm.getLeDate()));
		 * limitCharge.setIsLEByChargeRanking(aForm.getLeCharge());
		 * limitCharge.setLEDateByChargeRanking
		 * (CollateralMapper.compareDate(locale,
		 * limitCharge.getLEDateByChargeRanking(), aForm.getLeDateCharge()));
		 * limitCharge.setIsLEByJurisdiction(aForm.getLeJurisdiction());
		 * limitCharge
		 * .setLEDateByJurisdiction(CollateralMapper.compareDate(locale,
		 * limitCharge.getLEDateByJurisdiction(),
		 * aForm.getLeDateJurisdiction()));
		 * limitCharge.setIsLEByGovernLaws(aForm.getLeGoverningLaw());
		 * limitCharge
		 * .setLEDateByGovernLaws(CollateralMapper.compareDate(locale,
		 * limitCharge.getLEDateByGovernLaws(), aForm.getLeDateGovernginLaw()));
		 */
		limitCharge.setChargeType(aForm.getChargeType());
		limitCharge.setLimitMaps(col[indexID].getCurrentCollateralLimits());
		limitCharge.setLegalChargeDate(CollateralMapper.compareDate(locale, limitCharge.getLegalChargeDate(), aForm
				.getLegalChargeDate()));
		limitCharge.setChargeCcyCode(obToChange.getCurrencyCode());
		if (isEmptyOrNull(aForm.getChargeAmount())) {
			limitCharge.setChargeAmount(null);
		}
		else {
			try {
				limitCharge.setChargeAmount(CurrencyManager.convertToAmount(locale, obToChange.getCurrencyCode(), aForm
						.getChargeAmount()));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}
		if ((limitChargeList == null) || (limitChargeList.length == 0)) {
			limitChargeList = new OBLimitCharge[1];
		}

		limitChargeList[0] = limitCharge;
		obToChange.setLimitCharges(limitChargeList);

		obToChange.setIsExchangeCtrlObtained(aForm.getExchangeControlObtained());
		obToChange.setDegreeOfEnvironmentalRisky(aForm.getSecurityEnvRisk());
		obToChange.setDateOfDegreeOfEnvironmentalRiskyConfirmed(CollateralMapper.compareDate(locale, obToChange
				.getDateOfDegreeOfEnvironmentalRiskyConfirmed(), aForm.getDateSecurityEnvRisk()));
		obToChange.setRemarks(aForm.getRemarkSecurityEnvRisk());
		obToChange.setSecApportionment(col[indexID].getSecApportionment());
		if (isEmptyOrNull(aForm.getSecurityLocation())) {
			obToChange.setCollateralLocation(null);
		}
		else {
			obToChange.setCollateralLocation(aForm.getSecurityLocation());
		}

		obToChange.setSecurityOrganization(aForm.getSecurityOrganization());

		obToChange.setApprovedCustomerDifferentialSign(aForm.getPlusmnSign());
		obToChange.setApprovedCustomerDifferential(UIUtil.mapStringToBigDecimal(aForm.getCustomerDiff()));

		IValuation val = obToChange.getValuation();
		if (val == null) {
			val = new OBValuation();
		}
		val.setCurrencyCode(aForm.getValuationCurrency());
		obToChange.setValuation(val);

		obToChange = (OBCommodityCollateral) (new DeleteApportionmentMapper().mapFormToOB(cForm, inputs, obToChange));
		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		SecurityForm aForm = (SecurityForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(this, "entering security mapper : mapOBToForm");
		ICommodityCollateral col = (ICommodityCollateral) obj;

		aForm.setSecurityID(String.valueOf(col.getSCISecurityID()));
		aForm.setSecuritySubType(col.getCollateralSubType().getSubTypeName());
		aForm.setSciSecurityCurrency(col.getSCICurrencyCode());
		aForm.setLe(col.getIsLE());
		aForm.setLeDate(DateUtil.formatDate(locale, col.getLEDate()));
		if (isEmptyOrNull(col.getCurrencyCode())) {
			aForm.setCmsSecurityCurrency(col.getSCICurrencyCode());
		}
		else {
			aForm.setCmsSecurityCurrency(col.getCurrencyCode());
		}
		ILimitCharge charge = null;
		if ((col.getLimitCharges() != null) && (col.getLimitCharges().length > 0)) {
			charge = col.getLimitCharges()[0];
		}
		if (charge != null) {
			/*
			 * aForm.setLe(charge.getIsLE());
			 * aForm.setLe(DateUtil.formatDate(locale, charge.getLEDate()));
			 * aForm.setLeCharge(charge.getIsLEByChargeRanking());
			 * aForm.setLeDateCharge(DateUtil.formatDate(locale,
			 * charge.getLEDateByChargeRanking()));
			 * aForm.setLeJurisdiction(charge.getIsLEByJurisdiction());
			 * aForm.setLeDateJurisdiction(DateUtil.formatDate(locale,
			 * charge.getLEDateByJurisdiction()));
			 * aForm.setLeGoverningLaw(charge.getIsLEByGovernLaws());
			 * aForm.setLeDateGovernginLaw(DateUtil.formatDate(locale,
			 * charge.getLEDateByGovernLaws()));
			 */
			aForm.setLegalChargeDate(DateUtil.formatDate(locale, charge.getLegalChargeDate()));
			aForm.setChargeType(charge.getChargeType());
			if ((charge.getChargeAmount() != null) && (charge.getChargeAmount().getCurrencyCode() != null)
					&& (charge.getChargeAmount().getAmount() >= 0d)) {
				try {
					aForm.setChargeAmount(CurrencyManager.convertToString(locale, charge.getChargeAmount()));
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
		}

		aForm.setExchangeControlObtained(col.getIsExchangeCtrlObtained());
		if (col.getDegreeOfEnvironmentalRisky() != null) {
			aForm.setSecurityEnvRisk(col.getDegreeOfEnvironmentalRisky().trim());
		}
		aForm.setDateSecurityEnvRisk(DateUtil.formatDate(locale, col.getDateOfDegreeOfEnvironmentalRiskyConfirmed()));
		aForm.setRemarkSecurityEnvRisk(col.getRemarks());

		aForm.setSecurityLocation(col.getCollateralLocation());
		aForm.setSecurityOrganization(col.getSecurityOrganization());

		aForm.setPlusmnSign(col.getApprovedCustomerDifferentialSign());
		if (col.getApprovedCustomerDifferential() != null) {
			try {
				aForm.setCustomerDiff(UIUtil.formatNumber(col.getApprovedCustomerDifferential(), 6, locale));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		IValuation val = col.getValuation();
		if (val != null) {
			aForm.setValuationCurrency(val.getCurrencyCode());
			aForm.setValDate(DateUtil.formatDate(locale, val.getValuationDate()));
			try {
				aForm.setValCMV(UIUtil.mapAmountToString(val.getCMV(), locale, false));
				aForm.setValFSV(UIUtil.mapAmountToString(val.getFSV(), locale, false));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		aForm = (SecurityForm) (new DeleteApportionmentMapper().mapOBToForm(cForm, obj, inputs));
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "indexID", "java.lang.String", REQUEST_SCOPE },
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}
}
