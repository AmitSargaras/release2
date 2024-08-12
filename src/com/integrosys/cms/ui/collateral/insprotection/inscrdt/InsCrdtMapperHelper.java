/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/inscrdt/InsCrdtMapperHelper.java,v 1.12 2003/10/14 11:17:38 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.insprotection.inscrdt;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance.ICreditInsurance;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2003/10/14 11:17:38 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class InsCrdtMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		InsCrdtForm aForm = (InsCrdtForm) cForm;
		ICreditInsurance iObj = (ICreditInsurance) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		ILimitCharge[] limit = iObj.getLimitCharges();
		ILimitCharge objLimit = null;
		if (limit!=null && limit.length>0) objLimit=limit[0];
		DefaultLogger.debug("InsCrdtMapperHelper - mapFormToOB", "Locale is: " + locale);
		Date stageDate;
		try {

			iObj.setDescription(aForm.getDescription());
			iObj.setInsurerName(aForm.getInsureName());
			iObj.setInsuranceType(aForm.getInsurType());

			iObj.setInsuredCcyCode(iObj.getCurrencyCode());
			
			if (aForm.getInsuredAmt().equals("") && (iObj.getInsuredAmount() != null)
					&& (iObj.getInsuredAmount().getAmount() > 0)) {
				iObj.setInsuredAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), "0"));
			}
			else if (!AbstractCommonMapper.isEmptyOrNull(aForm.getInsuredAmt())) {
				iObj.setInsuredAmount(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
						.getInsuredAmt()));
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getEffDateInsurance())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getInsEffectiveDate(), aForm
						.getEffDateInsurance());
				iObj.setInsEffectiveDate(stageDate);
			}
			else {
				iObj.setInsEffectiveDate(null);
			}

			iObj.setPolicyNo(aForm.getPolicyNo());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getExpiryDateInsure())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getInsExpiryDate(), aForm.getExpiryDateInsure());
				iObj.setInsExpiryDate(stageDate);
			}
			else {
				iObj.setInsExpiryDate(null);
			}

			iObj.setExternalLegalCounsel(aForm.getExtLegalCounsel());
			iObj.setIsAccelerationClause(Boolean.valueOf(aForm.getAccelClause()).booleanValue());
			iObj.setLocalCCyInCoreMarket(aForm.getLocalCurrCM());
			iObj.setCoreMarket(aForm.getCoreMarke());
			iObj.setISDADate(DateUtil.convertDate(locale, aForm.getISDADate()));
			iObj.setTreasuryDocDate(DateUtil.convertDate(locale, aForm.getTreasuryDocDate()));
			if (aForm.getBankRiskConfirmation()!=null && aForm.getBankRiskConfirmation().equals(ICMSConstant.TRUE_VALUE)) iObj.setBankRiskConfirmation(true);
			else iObj.setBankRiskConfirmation(false);
			iObj.setArrInsurer(aForm.getArrInsurer());
			objLimit.setChargeType(aForm.getChargeType());
			limit[0] = objLimit;
			iObj.setLimitCharges(limit);
		}
		catch (Exception e) {
			DefaultLogger.debug("InsCrdtMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}
		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		InsCrdtForm aForm = (InsCrdtForm) cForm;
		ICreditInsurance iObj = (ICreditInsurance) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        ILimitCharge[] limit = iObj.getLimitCharges();
        ILimitCharge objLimit = null;        
        if (limit != null && limit.length > 0) {
            objLimit = limit[0];
        }
		DefaultLogger.debug("InsCrdtMapperHelper - mapOBToForm", "Locale is: " + locale);
		try {
			aForm.setDescription(iObj.getDescription());
			aForm.setInsureName(iObj.getInsurerName());
			aForm.setInsurType(iObj.getInsuranceType());
			if ((iObj.getInsuredAmount() != null) && (iObj.getInsuredAmount().getCurrencyCode() != null)) {
				if (iObj.getInsuredAmount().getAmount() > 0) {
					aForm.setInsuredAmt(CurrencyManager.convertToString(locale, iObj.getInsuredAmount()));
				}
			}
			aForm.setEffDateInsurance(DateUtil.formatDate(locale, iObj.getInsEffectiveDate()));
			aForm.setPolicyNo(iObj.getPolicyNo());
			aForm.setExpiryDateInsure(DateUtil.formatDate(locale, iObj.getInsExpiryDate()));
			aForm.setExtLegalCounsel(iObj.getExternalLegalCounsel());
			aForm.setAccelClause(String.valueOf(iObj.getIsAccelerationClause()));
			aForm.setLocalCurrCM(iObj.getLocalCCyInCoreMarket());
			aForm.setCoreMarke(iObj.getCoreMarket());
			aForm.setISDADate(DateUtil.formatDate(locale, iObj.getISDADate()));
			aForm.setTreasuryDocDate(DateUtil.formatDate(locale, iObj.getTreasuryDocDate()));
			if (iObj.getBankRiskConfirmation()) aForm.setBankRiskConfirmation(ICMSConstant.TRUE_VALUE);
			else aForm.setBankRiskConfirmation(ICMSConstant.FALSE_VALUE);
			aForm.setArrInsurer(iObj.getArrInsurer());
			if (objLimit!=null) aForm.setChargeType(objLimit.getChargeType());
		}
		catch (Exception e) {
			DefaultLogger.debug("InsCrdtMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((ICreditInsurance) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
