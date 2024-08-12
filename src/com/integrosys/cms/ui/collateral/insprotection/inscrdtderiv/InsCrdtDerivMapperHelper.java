/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/insprotection/inscrdtderiv/InsCrdtDerivMapperHelper.java,v 1.10 2004/01/16 08:28:35 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.insprotection.inscrdtderiv;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditderivative.ICreditDerivative;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class InsCrdtDerivMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {
		InsCrdtDerivForm aForm = (InsCrdtDerivForm) cForm;
		ICreditDerivative iObj = (ICreditDerivative) obj;
		ILimitCharge[] limit = iObj.getLimitCharges();
		ILimitCharge objLimit = null;
		if (limit!=null && limit.length>0) objLimit=limit[0];
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("InsCrdtDerivMapperHelper - mapFormToOB", "Locale is: " + locale);
		Date stageDate;
		try {

			iObj.setDescription(aForm.getDescription());

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateISDAMasterAgmt())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getISDADate(), aForm.getDateISDAMasterAgmt());
				iObj.setISDADate(stageDate);
			}
			else {
				iObj.setISDADate(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateTreasury())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getTreasuryDocDate(), aForm.getDateTreasury());
				iObj.setTreasuryDocDate(stageDate);
			}
			else {
				iObj.setTreasuryDocDate(null);
			}
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getBankRiskAppConfirm())) {
				iObj.setIsBankRiskConfirmation(aForm.getBankRiskAppConfirm());
			}
			iObj.setArrInsurer(aForm.getArrInsurer());
			objLimit.setChargeType(aForm.getChargeType());
			limit[0] = objLimit;
			iObj.setLimitCharges(limit);
		}
		catch (Exception e) {
			DefaultLogger.debug("InsCrdtDerivMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		InsCrdtDerivForm aForm = (InsCrdtDerivForm) cForm;
		ICreditDerivative iObj = (ICreditDerivative) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        ILimitCharge[] limit = iObj.getLimitCharges();
        ILimitCharge objLimit = null;        
        if (limit != null && limit.length > 0) {
            objLimit = limit[0];
        }
		DefaultLogger.debug("InsCrdtDerivMapperHelper - mapOBToForm", "Locale is: " + locale);
		try {
			aForm.setDescription(iObj.getDescription());
			aForm.setDateISDAMasterAgmt(DateUtil.formatDate(locale, iObj.getISDADate()));
			aForm.setDateTreasury(DateUtil.formatDate(locale, iObj.getTreasuryDocDate()));
			aForm.setBankRiskAppConfirm(iObj.getIsBankRiskConfirmation());
			aForm.setArrInsurer(iObj.getArrInsurer());
			if (objLimit!=null) aForm.setChargeType(objLimit.getChargeType());
		}
		catch (Exception e) {
			DefaultLogger.debug("InsCrdtDerivMapperHelper", "error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((ICreditDerivative) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());
	}

}
