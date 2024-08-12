package com.integrosys.cms.ui.collateral.guarantees.feedetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IFeeDetails;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.IGteGovtLink;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink.OBFeeDetails;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 7, 2007 Time: 6:10:53 PM
 * To change this template use File | Settings | File Templates.
 */

public class FeeDetailsMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		DefaultLogger.debug(this, "Inside mapFormToOB");

		FeeDetailsForm aForm = (FeeDetailsForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		DefaultLogger.debug(this + " - mapFormToOB", "Locale is: " + locale);

		IGteGovtLink iGteGovtLink = (IGteGovtLink) (((ICollateralTrxValue) inputs.get("serviceColObj"))
				.getStagingCollateral());
		String currencyCode = iGteGovtLink.getCurrencyCode();
		IFeeDetails[] obFeeDetails = iGteGovtLink.getFeeDetails();

		DefaultLogger.debug(this, "indexID  is:" + inputs.get("indexID") + ":");

		int index = Integer.parseInt((String) inputs.get("indexID"));

		IFeeDetails obToChange = null;
		if (index == -1) {
			obToChange = new OBFeeDetails();
		}
		else {
			try {
				obToChange = (IFeeDetails) AccessorUtil.deepClone(obFeeDetails[index]);
			}
			catch (Exception e) {
				DefaultLogger.debug(this, e.getMessage());
			}
		}

		Date stageDate;

		try {

			if (!isEmptyOrNull(aForm.getEffectiveDate())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getEffectiveDate(), aForm.getEffectiveDate());
				obToChange.setEffectiveDate(stageDate);
			}
			else {
				obToChange.setEffectiveDate(null);
			}

			if (!isEmptyOrNull(aForm.getExpirationDate())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getExpirationDate(), aForm.getExpirationDate());
				obToChange.setExpirationDate(stageDate);
			}
			else {
				obToChange.setExpirationDate(null);
			}

			/*
			if (isEmptyOrNull(aForm.getAmountCGC())) {
				//obToChange.setAmountCGC(CurrencyManager.convertToAmount(locale, currencyCode, "0"));
				obToChange.setAmountCGC(null);
			}
			else {
				obToChange.setAmountCGC(CurrencyManager.convertToAmount(locale, currencyCode, aForm.getAmountCGC()));
			}
			*/			

			if (isEmptyOrNull(aForm.getAmountFee())) {
				//obToChange.setAmountFee(CurrencyManager.convertToAmount(locale, currencyCode, "0"));
				obToChange.setAmountFee(null);
			}
			else {
				obToChange.setAmountFee(CurrencyManager.convertToAmount(locale, currencyCode, aForm.getAmountFee()));
			}

			if (aForm.getTenor().equals("")) {
				obToChange.setTenor(0);
			}
			else {
				obToChange.setTenor(Integer.parseInt(aForm.getTenor().trim()));
				obToChange.setTenorFreq(aForm.getTenorFreq());
			}

			if (!isEmptyOrNull(aForm.getFeePaymentDateCGC())) {
				stageDate = UIUtil.compareDate(locale, obToChange.getFeePaymentDateCGC(), aForm.getFeePaymentDateCGC());
				obToChange.setFeePaymentDateCGC(stageDate);
			} else {
				obToChange.setFeePaymentDateCGC(null);
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "mapFormToOB error is :" + e.toString());
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}
		DefaultLogger.debug(this, "existing mapFormToOB  :");
		return obToChange;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {

		DefaultLogger.debug(this, "(Inside mapOBToForm called :cForm " + cForm);
		DefaultLogger.debug(this, "(Inside mapOBToForm called :Object " + obj);
		DefaultLogger.debug(this, "(Inside mapOBToForm called :inputs " + inputs);

		FeeDetailsForm aForm = (FeeDetailsForm) cForm;
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		IFeeDetails obFeeDetails = (IFeeDetails) obj;

		Amount amt = null;

		if (obFeeDetails != null) { // Edit feeDetails

			aForm.setEffectiveDate(DateUtil.formatDate(locale, obFeeDetails.getEffectiveDate()));
			aForm.setExpirationDate(DateUtil.formatDate(locale, obFeeDetails.getExpirationDate()));

			amt = obFeeDetails.getAmountFee();
			if (amt != null) {
				try {
					aForm.setAmountFee(CurrencyManager.convertToString(locale, amt));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown...obFeeDetails.getAmountFee()");
				}
			}

			amt = obFeeDetails.getAmountCGC();
			if (amt != null) {
				try {
					aForm.setAmountCGC(CurrencyManager.convertToString(locale, amt));
				}
				catch (Exception e) {
					DefaultLogger.error(this, "exception thrown...obFeeDetails.getAmountCGC()");
				}
			}

			if (obFeeDetails.getTenor() > 0) {
				aForm.setTenor(String.valueOf(obFeeDetails.getTenor()));
				aForm.setTenorFreq(obFeeDetails.getTenorFreq());
			}

			aForm.setFeeDetailsID(obFeeDetails.getFeeDetailsID() + "");
			aForm.setRefID(obFeeDetails.getRefID() + "");

			aForm.setFeePaymentDateCGC(DateUtil.formatDate(locale, obFeeDetails.getFeePaymentDateCGC()));
		}
		DefaultLogger.debug(this, "existing mapOBToForm  :");
		return aForm;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "java.lang.Object", SERVICE_SCOPE },
				{ "indexID", "java.lang.String", REQUEST_SCOPE }, { "from_event", "java.lang.String", REQUEST_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, });
	}
}