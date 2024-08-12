//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetrecspecnonagent;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.recspecnoa.IReceivableSpecificNoAgent;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.CollateralMapper;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class AssetRecSpecNonAgentMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {

		AssetRecSpecNonAgentForm aForm = (AssetRecSpecNonAgentForm) cForm;
		IReceivableSpecificNoAgent iObj = (IReceivableSpecificNoAgent) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("AssetRecSpecNonAgentMapperHelper - mapFormToOB", "Locale is: " + locale);
		Date stageDate;
		try {
			iObj.setApprovedBuyer(aForm.getApprovedBuyer());
			iObj.setApprovedBuyerLocation(aForm.getLocApprBuyer().trim());
			iObj.setOwnAccNo(aForm.getProceedRecToBank());
			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getProceedRecControlledBank())) {
				iObj.setIsOwnProceedsOfReceivables(Boolean.valueOf(aForm.getProceedRecControlledBank()).booleanValue());
			}
			iObj.setBankAccNoLocation((aForm.getBankACLoc().trim()));
			if (aForm.getNominalValue().equals("")) {
				iObj.setNominalValue(null);
			}
			else {
				iObj.setNominalValue(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
						.getNominalValue()));
			}

			iObj.setInvoiceType(aForm.getTypeOfInvoice());

			// new addition for gcms
			iObj.setProjectName(((aForm.getProjectName())));
			iObj.setBlanketAssignment(((aForm.getBlanketAssignment())));

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getDateAwarded())) {
				stageDate = CollateralMapper.compareDate(locale, iObj.getDateAwarded(), aForm.getDateAwarded());
				iObj.setDateAwarded(stageDate);
			}
			else {
				iObj.setDateAwarded(null);
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getLetterInstructFlag())) {
				iObj.setLetterInstructFlag((new Boolean(aForm.getLetterInstructFlag())).booleanValue());
			}

			if (!AbstractCommonMapper.isEmptyOrNull(aForm.getLetterUndertakeFlag())) {
				iObj.setLetterUndertakeFlag((new Boolean(aForm.getLetterUndertakeFlag())).booleanValue());
			}

			iObj.setRemarks(aForm.getRemarks());
			DefaultLogger.debug("Helper", "error is :" + aForm.getBankACLoc());

		}
		catch (Exception e) {
			DefaultLogger.debug("----com.integrosys.cms.ui.collateral.assetbased.AssetRecSpecNonAgentMapperHelper",
					"error is :" + e.toString());
			throw new MapperException(e.getMessage());
		}

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		AssetRecSpecNonAgentForm aForm = (AssetRecSpecNonAgentForm) cForm;
		IReceivableSpecificNoAgent iObj = (IReceivableSpecificNoAgent) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("AssetRecSpecNonAgentMapperHelper - mapFormToOB", "Locale is: " + locale);
		Amount amt;
		try {
			DefaultLogger.debug("Helper", "error is :" + iObj.getBankAccNoLocation());
			aForm.setApprovedBuyer(iObj.getApprovedBuyer());
			if (iObj.getApprovedBuyerLocation() != null) {
				aForm.setLocApprBuyer(iObj.getApprovedBuyerLocation().trim());
			}

			aForm.setTypeOfInvoice(iObj.getInvoiceType());
			amt = iObj.getNominalValue();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				aForm.setNominalValue(CurrencyManager.convertToString(locale, amt));
			}
			if (iObj.getIsOwnProceedsOfReceivables()) {
				aForm.setProceedRecControlledBank("true");
			}
			else {
				aForm.setProceedRecControlledBank("false");
			}
			aForm.setProceedRecToBank(iObj.getOwnAccNo());
			if (iObj.getBankAccNoLocation() != null) {
				aForm.setBankACLoc(iObj.getBankAccNoLocation().trim());
			}

			// new addition for gcms
			aForm.setProjectName(((iObj.getProjectName())));
			aForm.setBlanketAssignment(((iObj.getBlanketAssignment())));
			aForm.setDateAwarded(DateUtil.formatDate(locale, iObj.getDateAwarded()));

			if (iObj.getLetterInstructFlag()) {
				aForm.setLetterInstructFlag("true");
			}
			else {
				aForm.setProceedRecControlledBank("false");
			}

			if (iObj.getLetterUndertakeFlag()) {
				aForm.setLetterUndertakeFlag("true");
			}
			else {
				aForm.setLetterUndertakeFlag("false");
			}

			aForm.setRemarks(iObj.getRemarks());
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.assetbased.AssetRecSpecNonAgentMapperHelper",
					"error is :" + e.toString());
		}
		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IReceivableSpecificNoAgent) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());

	}

}
