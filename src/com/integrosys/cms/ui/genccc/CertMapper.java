/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genccc;

import java.util.HashMap;
import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.cccertificate.bus.ICCCertificateItem;
import com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue;
import com.integrosys.cms.app.common.bus.OBBookingLocation;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/07/26 05:36:13 $ Tag: $Name: $
 */

public class CertMapper extends AbstractCommonMapper {
	/**
	 * Default Construtor
	 */
	public CertMapper() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "cert", "com.integrosys.cms.app.cccertificate.bus.ICCCertificate", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "certTrxVal", "com.integrosys.cms.app.cccertificate.trx.ICCCertificateTrxValue", SERVICE_SCOPE }, });
	}

	/**
	 * This method is used to map the Form values into Corresponding OB Values
	 * and returns the same.
	 * 
	 * @param cForm is of type CommonForm
	 * @return Object
	 */
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		ICCCertificate temp = (ICCCertificate) map.get("cert");
		if (temp == null) {
			throw new MapperException("ICCCertificate Session value is null");
		}
		GenerateCCCForm aForm = (GenerateCCCForm) cForm;

		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		try {
			DefaultLogger.debug(this, "Locale " + locale);
			if ((aForm.getActLimit() != null) && (aForm.getActLimit().length > 0)) {
				ICCCertificateItem cleanItems[] = temp.getCleanCCCertificateItemList();
				String actLimit[] = aForm.getActLimit();
				String actAmtCurrCode[] = aForm.getActAmtCurrCode();
				String maturityDate[] = aForm.getMaturityDate();
				for (int i = 0; i < cleanItems.length; i++) {
					/****
					 * Amount tempAmt = cleanItems[i].getActivatedAmount(); if
					 * (tempAmt == null || tempAmt.getCurrencyCode() == null) {
					 * tempAmt = new Amount(0.0,
					 * cleanItems[i].getApprovedLimitAmount
					 * ().getCurrencyCode()); }
					 * DefaultLogger.debug(this,"Activated Amount"+ tempAmt);
					 ****/
					Amount newAmt = CurrencyManager.convertToAmount(locale, actAmtCurrCode[i], actLimit[i]);
					temp.getCleanCCCertificateItemList()[i].setActivatedAmount(newAmt);
					temp.getCleanCCCertificateItemList()[i].setMaturityDate(DateUtil.convertDate(locale,
							maturityDate[i]));
				}
				/*
				 * ICCCertificateItem notCleanItems[] =
				 * temp.getNotCleanCCCertificateItemList(); for(int
				 * i=0;i<notCleanItems.length;i++) { Amount tempAmt =
				 * notCleanItems[i].getActivatedAmount(); if (tempAmt == null) {
				 * tempAmt = new Amount(0.0,
				 * notCleanItems[i].getApprovedLimitAmount().getCurrencyCode());
				 * } DefaultLogger.debug(this,"Activated Amount"+ tempAmt);
				 * Amount newAmt =
				 * CurrencyManager.convertToAmount(locale,tempAmt
				 * .getCurrencyCode(),actLimit[index++]);
				 * temp.getNotCleanCCCertificateItemList
				 * ()[i].setActivatedAmount(newAmt); }
				 */
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new MapperException(e.getMessage());
		}

		temp.setCreditOfficerName(aForm.getCreditOfficerName());
		temp.setCreditOfficerSignNo(aForm.getCreditOfficerSgnNo());
		temp.setSeniorOfficerName(aForm.getSeniorCreditOfficerName());
		temp.setSeniorOfficerSignNo(aForm.getSeniorCreditOfficerSgnNo());
		// temp.setRemarks(aForm.getRemarksCCC());
		temp.setRemarks(aForm.getRemarks());
		temp.setCreditOfficerLocation(new OBBookingLocation(aForm.getCreditOfficerLocationCountry(), aForm
				.getCreditOfficerLocationOrgCode()));
		temp.setSeniorOfficerLocation(new OBBookingLocation(aForm.getSeniorCreditOfficerLocationCountry(), aForm
				.getSeniorCreditOfficerLocationOrgCode()));
		return temp;
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
		DefaultLogger.debug(this, "inside mapOb to form ");
		GenerateCCCForm aForm = (GenerateCCCForm) cForm;
		ICCCertificate cert = (ICCCertificate) obj;
		ICCCertificateTrxValue certTrxVal = (ICCCertificateTrxValue) map.get("certTrxVal");

		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		try {
			if (cert != null) {
				aForm.setCreditOfficerName(cert.getCreditOfficerName());
				aForm.setCreditOfficerSgnNo(cert.getCreditOfficerSignNo());
				aForm.setSeniorCreditOfficerName(cert.getSeniorOfficerName());
				aForm.setSeniorCreditOfficerSgnNo(cert.getSeniorOfficerSignNo());
				// aForm.setRemarksCCC(cert.getRemarks());
				if (cert.getCreditOfficerLocation() != null) {
					aForm.setCreditOfficerLocationCountry(cert.getCreditOfficerLocation().getCountryCode());
					aForm.setCreditOfficerLocationOrgCode(cert.getCreditOfficerLocation().getOrganisationCode());
				}
				if (cert.getSeniorOfficerLocation() != null) {
					aForm.setSeniorCreditOfficerLocationCountry(cert.getSeniorOfficerLocation().getCountryCode());
					aForm.setSeniorCreditOfficerLocationOrgCode(cert.getSeniorOfficerLocation().getOrganisationCode());
				}
			}
			aForm.setRemarks(certTrxVal.getRemarks());
			ICCCertificateItem items[] = cert.getCCCertificateItemList();
			String actAmt[] = null;
			String actAmtCurrCode[] = null;
			String maturityDate[] = null;
			int index = 0;
			if ((items != null) && (items.length > 0)) {
				actAmt = new String[items.length];
				actAmtCurrCode = new String[items.length];
				maturityDate = new String[items.length];
				ICCCertificateItem cleanItems[] = cert.getCleanCCCertificateItemList();
				if ((cleanItems != null) && (cleanItems.length > 0)) {
					for (int i = 0; i < cleanItems.length; i++) {
						maturityDate[i] = DateUtil.formatDate(locale, cleanItems[i].getMaturityDate());
						if ((cleanItems[i].getActivatedAmount() != null)
								&& (cleanItems[i].getActivatedAmount().getCurrencyCode() != null)) {
							actAmt[index] = CurrencyManager.convertToDisplayString(locale, cleanItems[i]
									.getActivatedAmount());
							actAmtCurrCode[index] = cleanItems[i].getApprovedLimitAmount().getCurrencyCode();
							index++;
						}
					}
				}
				/*
				 * ICCCertificateItem notCleanItems [] =
				 * cert.getNotCleanCCCertificateItemList(); if ((notCleanItems
				 * != null) && (notCleanItems.length > 0)) { for(int
				 * i=0;i<notCleanItems.length;i++) { if
				 * ((notCleanItems[i].getActivatedAmount() != null) &&
				 * (notCleanItems[i].getActivatedAmount().getCurrencyCode() !=
				 * null)) { actAmt[index] =
				 * CurrencyManager.convertToDisplayString
				 * (locale,notCleanItems[i].getActivatedAmount());
				 * actAmtCurrCode[index] =
				 * cleanItems[i].getApprovedLimitAmount().getCurrencyCode();
				 * index ++; } } }
				 */
				if ((actAmt != null) && (actAmt.length > 0)) {
					for (int j = 0; j < actAmt.length; j++) {
						if (actAmt[j] == null) {
							actAmt[j] = "";
						}
					}
				}
				aForm.setActLimit(actAmt);
				aForm.setActAmtCurrCode(actAmtCurrCode);
				aForm.setMaturityDate(maturityDate);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, e);
			throw new MapperException(e.getMessage());
		}
		DefaultLogger.debug(this, "Going out of mapOb to form ");
		return aForm;
	}
}
