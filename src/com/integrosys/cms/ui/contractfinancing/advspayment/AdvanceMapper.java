/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.contractfinancing.advspayment;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.contractfinancing.bus.OBAdvance;

/**
 * Mapper class used to map form values to objects and vice versa
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 2007/Feb/07 $ Tag: $Name: $
 */
public class AdvanceMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public AdvanceMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "obAdvance", "com.integrosys.cms.app.contractfinancing.bus.OBAdvance", SERVICE_SCOPE },
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "facilityType", "java.lang.String", REQUEST_SCOPE },
				{ "moa", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "contractFinancingTrxValue",
						"com.integrosys.cms.app.contractfinancing.trx.IContractFinancingTrxValue", SERVICE_SCOPE }, });

	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		DefaultLogger.debug(this, "******************** Inside Map Form to OB ");

		String event = (String) map.get(ICommonEventConstant.EVENT);
		String facilityType = (String) map.get("facilityType");
		String moa = (String) map.get("moa");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		AdvsPaymentForm aForm = (AdvsPaymentForm) cForm;

		try {
			if (AdvsPaymentAction.EVENT_CREATE.equals(event) || AdvsPaymentAction.EVENT_UPDATE.equals(event)
					|| AdvsPaymentAction.EVENT_REFRESH.equals(event)
					|| AdvsPaymentAction.EVENT_MAKER_PREPARE_CREATE_ITEM.equals(event)
					|| AdvsPaymentAction.EVENT_MAKER_PREPARE_UPDATE_ITEM.equals(event)
					|| AdvsPaymentAction.EVENT_DELETE_ITEM.equals(event)
					|| AdvsPaymentAction.EVENT_VIEW_ITEM.equals(event)) {

				OBAdvance obAdvance = (OBAdvance) map.get("obAdvance");
				if (obAdvance == null) {
					obAdvance = new OBAdvance();
				}
				try {
					if (facilityType != null) {
						obAdvance.setFacilityType(facilityType);
					}
					if (moa != null) {
						obAdvance.setFacilityTypeMOA(Float.parseFloat(moa));
					}
					obAdvance.setReferenceNo(aForm.getReferenceNo());
					obAdvance.setDrawdownDate(DateUtil.convertDate(locale, aForm.getDrawdownDate()));
					obAdvance.setTenorUOM(aForm.getTenorUom());
					obAdvance.setTenor(Integer.parseInt(aForm.getTenorPeriod()));
					if (!aForm.getClaimCurrency().equals("") && !aForm.getClaimAmount().equals("")) {
						obAdvance.setAmount(CurrencyManager.convertToAmount(locale, aForm.getClaimCurrency(), aForm
								.getClaimAmount()));
					}
					obAdvance.setExpiryDate(DateUtil.convertDate(locale, aForm.getExpiryDate()));
				}
				catch (Exception e) {
					// temporary not throw exception
					DefaultLogger.debug(this, e.toString());
				}

				return obAdvance;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		return null;
	}

	/**
	 * This method is used to map data from OB to the form and to return the
	 * form.
	 * 
	 * @param cForm is of type CommonForm
	 * @param obj is of type Object
	 * @return Object
	 */
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		try {
			DefaultLogger.debug(this, "inside mapOb to form");
			AdvsPaymentForm aForm = (AdvsPaymentForm) cForm;
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			DefaultLogger.debug(this, "mapOBToForm obj=" + obj);

			if (obj != null) {
				OBAdvance obAdvance = (OBAdvance) obj;

				aForm.setFacilityType(obAdvance.getFacilityType());
				aForm.setReferenceNo(obAdvance.getReferenceNo());
				aForm.setDrawdownDate(DateUtil.formatDate(locale, obAdvance.getDrawdownDate()));
				aForm.setTenorUom(obAdvance.getTenorUOM());
				aForm.setTenorPeriod(Integer.toString(obAdvance.getTenor()));
				if (obAdvance.getAmount() != null) {
					aForm.setClaimCurrency(obAdvance.getAmount().getCurrencyCode());
					aForm.setClaimAmount(new DecimalFormat("#").format(obAdvance.getAmount().getAmount()));
				}
				aForm.setExpiryDate(DateUtil.formatDate(locale, obAdvance.getExpiryDate()));

				try {
					if (obAdvance.getOperativeLimitZeroWhenFullPayment() != null) {
						aForm.setOperativeLimitCurrency(obAdvance.getOperativeLimitZeroWhenFullPayment()
								.getCurrencyCode());
						aForm.setOperativeLimitAmount(new DecimalFormat("#").format(obAdvance
								.getOperativeLimitZeroWhenFullPayment().getAmount()));
					}
				}
				catch (AmountConversionException e) {
					if (e.toString().indexOf("AMT_CONV_EX") > -1) {
						aForm.setClaimCurrency("no conversion for this currency");
						aForm.setClaimAmount("");
					}
				}

			}
			DefaultLogger.debug(this, "Going out of mapOb to form ");
			return aForm;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "error in AdvanceMapper is" + e);
		}
		return null;
	}
}