package com.integrosys.cms.ui.limit.facility.insurance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.IFacilityInsurance;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBFacilityInsurance;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;

public class InsuranceMapper extends AbstractCommonMapper {
	private Amount defaultValueAmount = new Amount(-1,"MYR");
	
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE }, });
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		InsuranceForm form = (InsuranceForm) cForm;
		IFacilityMaster facilityMasterObj = (IFacilityMaster) inputs.get("facilityMasterObj");
		Set setFacilityInsurance = facilityMasterObj.getFacilityInsuranceSet();
		ILimit limit = facilityMasterObj.getLimit();
		String currencyCode = limit.getApprovedLimitAmount().getCurrencyCode();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
		IFacilityInsurance[] facilityInsurance = new OBFacilityInsurance[2];

		if (setFacilityInsurance != null) {
			Iterator iter = setFacilityInsurance.iterator();
			while (iter.hasNext()) {
				IFacilityInsurance tempFacilityInsurance = (IFacilityInsurance) iter.next();
//				System.out.println("tempFacilityInsurance.getOrder() = " + tempFacilityInsurance.getOrder());
				facilityInsurance[tempFacilityInsurance.getOrder()] = tempFacilityInsurance;
			}
			setFacilityInsurance.clear();
		}
		else {
			setFacilityInsurance = new HashSet();
		}
		for (int i = 0; i < 2; i++) {
			if (facilityInsurance[i] == null) {
				facilityInsurance[i] = new OBFacilityInsurance();
			}
		}

		try {
			for (int i = 0; i < 2; i++) {
				facilityInsurance[i].setOrder((short) i);
				facilityInsurance[i].setCurrencyCode(currencyCode);
				facilityInsurance[i].setCoverageTypeCategoryCode(CategoryCodeConstant.INSURANCE_TYPE);
				facilityInsurance[i].setCoverageTypeEntryCode(form.getCoverageTypeEntryCode()[i]);
				facilityInsurance[i].setInsuranceCompanyCategoryCode(CategoryCodeConstant.INSURER_NAME);
				facilityInsurance[i].setInsuranceCompanyEntryCode(form.getInsuranceCompanyEntryCode()[i]);
				facilityInsurance[i].setCurrencyCode(currencyCode);
				if (StringUtils.isNotBlank(form.getPolicyNumber()[i])) {
					facilityInsurance[i].setPolicyNumber(form.getPolicyNumber()[i]);
				}else{
					facilityInsurance[i].setPolicyNumber(null);
				}
				if (StringUtils.isNotBlank(form.getPropNumberOrCvNote()[i])) {
					facilityInsurance[i].setPropNumberOrCvNote(form.getPropNumberOrCvNote()[i]);
				}else{
					facilityInsurance[i].setPropNumberOrCvNote(null);
				}
				if (StringUtils.isNotBlank(form.getInsuredAmount()[i])) {
					facilityInsurance[i].setInsuredAmount(new Amount(UIUtil.mapStringToBigDecimal((form
							.getInsuredAmount()[i])), new CurrencyCode(currencyCode)));
				}else{
					facilityInsurance[i].setInsuredAmount(defaultValueAmount);
				}
				if (StringUtils.isNotBlank(form.getInsurancePremiumAmount()[i])) {
					facilityInsurance[i].setInsurancePremiumAmount(new Amount(UIUtil.mapStringToBigDecimal(form
							.getInsurancePremiumAmount()[i]), new CurrencyCode(currencyCode)));
				}else{
					facilityInsurance[i].setInsurancePremiumAmount(defaultValueAmount);
				}
				if (StringUtils.isNotBlank(form.getIssuedDate()[i])) {
					facilityInsurance[i].setIssuedDate(sdf.parse(form.getIssuedDate()[i]));
				}else{
					facilityInsurance[i].setIssuedDate(null);
				}
				if (StringUtils.isNotBlank(form.getExpiryDate()[i])) {
					facilityInsurance[i].setExpiryDate(sdf.parse(form.getExpiryDate()[i]));
				}else{
					facilityInsurance[i].setExpiryDate(null);
				}
				if (StringUtils.isNotBlank(form.getEffectiveDate()[i])) {
					facilityInsurance[i].setEffectiveDate(sdf.parse(form.getEffectiveDate()[i]));
				}else{
					facilityInsurance[i].setEffectiveDate(null);
				}
				if (StringUtils.isNotBlank(form.getArrangementIndicator()[i])) {
					facilityInsurance[i].setArrangementIndicator(new Character(form.getArrangementIndicator()[i]
							.charAt(0)));
				}
				facilityInsurance[i].setRemarks(form.getRemarksInsurance()[i]);
				setFacilityInsurance.add(facilityInsurance[i]);
			}
			facilityMasterObj.setFacilityInsuranceSet(setFacilityInsurance);
		}
		catch (ParseException e) {
			DefaultLogger.error(this, "Parse Exception Caught", e);
		}
		return facilityMasterObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		List listInsurance = (List) obj;
		InsuranceForm form = (InsuranceForm) cForm;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		String[] coverageTypeEntryCode = new String[2];
		String[] insuranceCompanyEntryCode = new String[2];
		String[] policyNumber = new String[2];
		String[] propNumberOrCvNote = new String[2];
		String[] insuredAmount = new String[2];
		String[] insuredAmountCurrency = new String[2];
		String[] insurancePremiumAmount = new String[2];
		String[] insurancePremiumAmountCurrency = new String[2];
		String[] issuedDate = new String[2];
		String[] expiryDate = new String[2];
		String[] effectiveDate = new String[2];
		String[] arrangementIndicator = new String[2];
		String[] remarks = new String[2];

		try {
			for (int i = 0; i < listInsurance.size(); i++) {
				IFacilityInsurance facilityInsurance = (IFacilityInsurance) listInsurance.get(i);

				coverageTypeEntryCode[facilityInsurance.getOrder()] = facilityInsurance.getCoverageTypeEntryCode();
				insuranceCompanyEntryCode[facilityInsurance.getOrder()] = facilityInsurance
						.getInsuranceCompanyEntryCode();
				if (facilityInsurance.getPolicyNumber() != null) {
					policyNumber[facilityInsurance.getOrder()] = facilityInsurance.getPolicyNumber();
				}
				if (facilityInsurance.getPropNumberOrCvNote() != null) {
					propNumberOrCvNote[facilityInsurance.getOrder()] = facilityInsurance.getPropNumberOrCvNote();
				}
				if (facilityInsurance.getInsuredAmount() != null&&facilityInsurance.getInsuredAmount().getAmount()>=0) {
					insuredAmount[facilityInsurance.getOrder()] = UIUtil.formatAmount(facilityInsurance
							.getInsuredAmount(), 2, locale, false);
					insuredAmountCurrency[facilityInsurance.getOrder()] = facilityInsurance.getInsuredAmount()
							.getCurrencyCode();
				}else{
					insuredAmount[facilityInsurance.getOrder()] = "0.00";
				}
				if (facilityInsurance.getInsurancePremiumAmount() != null&&facilityInsurance.getInsurancePremiumAmount().getAmount()>=0) {
					insurancePremiumAmount[facilityInsurance.getOrder()] = UIUtil.formatAmount(facilityInsurance
							.getInsurancePremiumAmount(), 2, locale, false);
					insurancePremiumAmountCurrency[facilityInsurance.getOrder()] = facilityInsurance
							.getInsurancePremiumAmount().getCurrencyCode();
				}else{
					insurancePremiumAmount[facilityInsurance.getOrder()] = "0.00";
				}
				if (facilityInsurance.getIssuedDate() != null) {
					issuedDate[facilityInsurance.getOrder()] = sdf.format(facilityInsurance.getIssuedDate());
				}
				if (facilityInsurance.getExpiryDate() != null) {
					expiryDate[facilityInsurance.getOrder()] = sdf.format(facilityInsurance.getExpiryDate());
				}
				if (facilityInsurance.getEffectiveDate() != null) {
					effectiveDate[facilityInsurance.getOrder()] = sdf.format(facilityInsurance.getEffectiveDate());
				}
				arrangementIndicator[facilityInsurance.getOrder()] = String.valueOf(facilityInsurance
						.getArrangementIndicator());
				remarks[facilityInsurance.getOrder()] = facilityInsurance.getRemarks();
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception Caught", e);
		}
		for (int i = 0; i < listInsurance.size(); i++) {
			IFacilityInsurance facilityInsurance = (IFacilityInsurance) listInsurance.get(i);

			form.setCoverageTypeEntryCode(coverageTypeEntryCode);
			form.setInsuranceCompanyEntryCode(insuranceCompanyEntryCode);
			form.setPolicyNumber(policyNumber);
			form.setPropNumberOrCvNote(propNumberOrCvNote);
			form.setInsuredAmount(insuredAmount);
			if (facilityInsurance.getInsurancePremiumAmount() != null) {
				form.setInsurancePremiumAmount(insurancePremiumAmount);
			}
			if (facilityInsurance.getIssuedDate() != null) {
				form.setIssuedDate(issuedDate);
			}
			if (facilityInsurance.getExpiryDate() != null) {
				form.setExpiryDate(expiryDate);
			}
			if (facilityInsurance.getEffectiveDate() != null) {
				form.setEffectiveDate(effectiveDate);
			}
			form.setArrangementIndicator(arrangementIndicator);
			form.setRemarksInsurance(remarks);

		}
		// TODO Auto-generated method stub
		return form;
	}
}
