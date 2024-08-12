/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/FAOMapper.java,v 1.9 2005/08/12 08:19:49 lyng Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.OBGeneralCharge;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Description
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/08/12 08:19:49 $ Tag: $Name: $
 */

public class FAOMapper extends AbstractCommonMapper {
	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		FAOForm aForm = (FAOForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		ICollateralTrxValue trxValue = (ICollateralTrxValue) inputs.get("serviceColObj");
		IGeneralCharge iCol = (IGeneralCharge) trxValue.getStagingCollateral();

		if (isEmptyOrNull(aForm.getInsCoverNum())) {
			iCol.setFaoInsrGracePeriod(ICMSConstant.INT_INVALID_VALUE);
		}
		else {
			iCol.setFaoInsrGracePeriod(Integer.parseInt(aForm.getInsCoverNum()));
		}
		iCol.setFaoInsrGracePeriodFreq(aForm.getInsCoverUnit());

		if (aForm.getEvent().endsWith(FAOAction.EVENT_DELETE_ITEM)) {
			HashMap returnMap = new HashMap();
			returnMap.put("col", iCol);
			returnMap.put("deleteFaos", aForm.getFaoItemDelete());
			returnMap.put("deleteInsurances", aForm.getInsuranceItemDelete());
			return returnMap;
		}

		return iCol;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		FAOForm aForm = (FAOForm) cForm;

		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String hasException = "false";
		HashMap objMap = (HashMap) obj;
		IGeneralCharge iCol = (IGeneralCharge) objMap.get("col");
		Collection faoSummaryList = (Collection) objMap.get("faoSummaryList");

		if (aForm.getEvent().endsWith(FAOAction.EVENT_DELETE_ITEM)) {
			hasException = (String) objMap.get("hasException");
		}

		if ((hasException == null) || hasException.equals("false")) {
			DefaultLogger.debug(this, "<<<<<<< Has Exception: false");
			aForm.setFaoItemDelete(new String[0]);
			aForm.setInsuranceItemDelete(new String[0]);
			aForm.setCmsSecCurreny(iCol.getCurrencyCode());

			if ((faoSummaryList != null) && !faoSummaryList.isEmpty()) {
				HashMap faoTotal = AssetGenChargeUtil.getFAOSummaryTotal(faoSummaryList);
				try {
					BigDecimal bd = (BigDecimal) faoTotal.get("totalGross");
					if (bd != null) {
						aForm.setTotalGrossValCMS(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalGrossValCMS(AssetGenChargeUtil.FOREX_ERROR);
					}
					bd = (BigDecimal) faoTotal.get("totalNetValue");
					if (bd != null) {
						aForm.setTotalNetValue(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalNetValue(AssetGenChargeUtil.FOREX_ERROR);
					}
					bd = (BigDecimal) faoTotal.get("totalInsuredAmt");
					if (bd != null) {
						aForm.setTotalFAOInsuredAmt(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalFAOInsuredAmt(AssetGenChargeUtil.FOREX_ERROR);
					}
					bd = (BigDecimal) faoTotal.get("totalEffectiveAmt");
					if (bd != null) {
						aForm.setTotalFAOEffectiveAmt(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalFAOEffectiveAmt(AssetGenChargeUtil.FOREX_ERROR);
					}
					bd = (BigDecimal) faoTotal.get("totalRecoverableAmt");
					if (bd != null) {
						aForm.setTotalInsuranceCover(UIUtil.formatNumber(bd, 0, locale));
					}
					else {
						aForm.setTotalInsuranceCover(AssetGenChargeUtil.FOREX_ERROR);
					}
				}
				catch (Exception e) {
					throw new MapperException(e.getMessage());
				}
			}
			else {
				aForm.setTotalGrossValCMS("");
				aForm.setTotalNetValue("");
				aForm.setTotalFAOInsuredAmt("");
				aForm.setTotalFAOEffectiveAmt("");
				aForm.setTotalInsuranceCover("");
			}

			HashMap insuranceTotal = AssetGenChargeUtil.getInsuranceSummaryTotal(iCol, IInsurancePolicy.FAO, locale);
			BigDecimal totalInsuredAmt = (BigDecimal) insuranceTotal.get("totalInsuredAmt");
			BigDecimal totalInsuredCoverAmt = (BigDecimal) insuranceTotal.get("totalInsuredCoverAmt");

			try {
				if (totalInsuredAmt != null) {
					aForm.setTotalInsuredAmt(UIUtil.formatNumber(totalInsuredAmt, 0, locale));
				}
				else {
					aForm.setTotalInsuredAmt(AssetGenChargeUtil.FOREX_ERROR);
				}
				if (totalInsuredCoverAmt != null) {
					aForm.setTotalValidCoverInsAmt(UIUtil.formatNumber(totalInsuredCoverAmt, 0, locale));
				}
				else {
					aForm.setTotalValidCoverInsAmt(AssetGenChargeUtil.FOREX_ERROR);
				}
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}

			if (iCol.getFaoInsrGracePeriod() != ICMSConstant.INT_INVALID_VALUE) {
				aForm.setInsCoverNum(String.valueOf(iCol.getFaoInsrGracePeriod()));
			}
			else {
				aForm.setInsCoverNum("");
			}

			aForm.setInsCoverUnit(iCol.getFaoInsrGracePeriodFreq());

			Amount cmvAmt = ((OBGeneralCharge) iCol).getCalculatedFAOCMV();
			Amount fsvAmt = ((OBGeneralCharge) iCol).getCalculatedFAOFSV();
			aForm.setMargin(AssetGenChargeUtil.getSummaryAverageMargin(cmvAmt, fsvAmt, locale));

			try {
				aForm.setValuationCMVFAO(UIUtil.formatAmount(cmvAmt, 0, locale, false));
				aForm.setValuationFSVFAO(UIUtil.formatAmount(fsvAmt, 0, locale, false));
			}
			catch (Exception e) {
				throw new MapperException(e.getMessage());
			}
		}

		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }, });
	}

}
