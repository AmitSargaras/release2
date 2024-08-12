//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetrecopen;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.recopen.IReceivableOpen;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class AssetRecOpenMapperHelper {

	public static Object mapFormToOB(CommonForm cForm, HashMap inputs, Object obj) throws MapperException {

		AssetRecOpenForm aForm = (AssetRecOpenForm) cForm;
		IReceivableOpen iObj = (IReceivableOpen) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("AssetopenMapperHelper - mapFormToOB", "Locale is: " + locale);
		Date stageDate;
		try {

			iObj.setApprovedBuyer(aForm.getApprovedBuyer());
			iObj.setApprovedBuyerLocation(aForm.getLocApprBuyer().trim());
			if (aForm.getNominalValue().equals("")) {
				iObj.setNominalValue(null);
			}
			else {
				iObj.setNominalValue(CurrencyManager.convertToAmount(locale, iObj.getCurrencyCode(), aForm
						.getNominalValue()));
			}

			iObj.setInvoiceType(aForm.getTypeOfInvoice());
			iObj.setRemarks(aForm.getRemarks());
		}
		catch (Exception e) {
			DefaultLogger.debug("----com.integrosys.cms.ui.collateral.assetbased.AssetRecOpenMapperHelper",
					"error is :" + e.toString());
		}

		return iObj;
	}

	public static CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
		AssetRecOpenForm aForm = (AssetRecOpenForm) cForm;
		IReceivableOpen iObj = (IReceivableOpen) obj;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug("AssetopenMapperHelper - mapOBToForm", "Locale is: " + locale);
		Amount amt;
		try {
			aForm.setApprovedBuyer(iObj.getApprovedBuyer());
			if (iObj.getApprovedBuyerLocation() != null) {
				aForm.setLocApprBuyer(iObj.getApprovedBuyerLocation().trim());
			}
			aForm.setTypeOfInvoice(iObj.getInvoiceType());
			amt = iObj.getNominalValue();
			if ((amt != null) && (amt.getCurrencyCode() != null)) {
				aForm.setNominalValue(CurrencyManager.convertToString(locale, amt));
			}
			aForm.setRemarks(iObj.getRemarks());
		}
		catch (Exception e) {
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.assetbased.AssetRecOpenMapperHelper", "error is :"
					+ e.toString());
		}
		return aForm;
	}

	public static Object getObject(HashMap inputs) {
		return ((IReceivableOpen) ((ICollateralTrxValue) inputs.get("serviceColObj")).getStagingCollateral());

	}

}
