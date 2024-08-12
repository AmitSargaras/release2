package com.integrosys.cms.ui.limit.facility.bbavaripkg;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.IFacilityIslamicBbaVariPackage;
import com.integrosys.cms.app.limit.bus.OBFacilityIslamicBbaVariPackage;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

public class FacilityIslamicBbaVariPackageMapper extends AbstractCommonMapper {
	private int defaultValue = -1;
	private Amount defaultValueAmount = new Amount(-1,"MYR");

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				});
	}

	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		FacilityIslamicBbaVariPackageForm form = (FacilityIslamicBbaVariPackageForm) cForm;
		IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");

		ILimit limit = (ILimit) map.get("limit");
		if (limit == null) {
			limit = facilityMasterObj.getLimit();
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");

		String currencyCode = limit.getApprovedLimitAmount().getCurrencyCode();
		if (facilityMasterObj == null) {
			facilityMasterObj = new OBFacilityMaster();
		}
		if (facilityMasterObj.getFacilityIslamicBbaVariPackage() == null) {
			facilityMasterObj.setFacilityIslamicBbaVariPackage(new OBFacilityIslamicBbaVariPackage());
		}
		try {
			// Cust. Prof. Rate.
			facilityMasterObj.getFacilityIslamicBbaVariPackage().setCurrencyCode(currencyCode);
			
			if (StringUtils.isNotBlank(form.getCustProfRate())) {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setCustProfRate(new Double(form.getCustProfRate()));
			} 
			else {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setCustProfRate(null);
			}
			
			if (StringUtils.isNotBlank(form.getRebateMethod()))
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setRebateMethod(new Character(form.getRebateMethod().charAt(0)));
			else
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setRebateMethod(null);
			
			facilityMasterObj.getFacilityIslamicBbaVariPackage().setGppPaymentMode(form.getGppPaymentMode());
			
			// GPP Calculation Method
			if (form.getGppCalculationMethod() != null)
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setGppCalculationMethod(form.getGppCalculationMethod());
			else
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setGppCalculationMethod(null);

			// GPP Term
			if (StringUtils.isNotBlank(form.getGppTerm())) {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setGppTerm(new Short(form.getGppTerm()));
			} 
			else {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setGppTerm(null);
			}
			
			// GPP Term Code
			if (StringUtils.isNotBlank(form.getGppTermCode())) {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setGppTermCode(new Character(form.getGppTermCode().charAt(0)));
			} 
			else {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setGppTermCode(null);
			}
			
			// Full Release Profit *
			if (StringUtils.isNotBlank(form.getFullReleaseProfit())) {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setFullReleaseProfit(Boolean.valueOf(form.getFullReleaseProfit().equals("Y")));
			}
			
			// Refund Fulrel profit method
			if (StringUtils.isNotBlank(form.getRefundFullReleaseProfit())) {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setRefundFullReleaseProfit(new Character(form.getRefundFullReleaseProfit().charAt(0)));
			}else {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setRefundFullReleaseProfit(null);
			}
			
			// Fulrel profit calculation method
			if (StringUtils.isNotBlank(form.getFulrelProfitCalMethod())) {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setFulrelProfitCalMethod(new Character(form.getFulrelProfitCalMethod().charAt(0)));
			}else{
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setFulrelProfitCalMethod(null);
			}

			// Installment Amount
			if (StringUtils.isNotBlank(form.getInstallment())) {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setInstallment(
						new Amount(UIUtil.mapStringToBigDecimal(form.getInstallment()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setInstallment(null);
			}
			
			// Final Payment Amount
			if (StringUtils.isNotBlank(form.getFinalPaymentAmount())) {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setFinalPaymentAmount(
						new Amount(UIUtil.mapStringToBigDecimal(form.getFinalPaymentAmount()), new CurrencyCode(
								currencyCode)));
			}
			else {
				facilityMasterObj.getFacilityIslamicBbaVariPackage().setInstallment(null);
			}
		}
		catch (RuntimeException e) {
			MapperException me = new MapperException("failed to map form values to ob");
			me.initCause(e);
			throw me;
		}

		return facilityMasterObj;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		FacilityIslamicBbaVariPackageForm form = (FacilityIslamicBbaVariPackageForm) cForm;
		IFacilityIslamicBbaVariPackage facilityBba = (IFacilityIslamicBbaVariPackage) obj;
		IFacilityTrxValue facilityTrxValue = (IFacilityTrxValue) map.get("facilityTrxValue");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yy");
		int decPlaces = 2;
		boolean withCCY = false;
		try {
			
			if (facilityBba.getCustProfRate() != null) {
				form.setCustProfRate(String.valueOf(facilityBba.getCustProfRate()));
			}
			
			if (facilityBba.getRebateMethod() != null) {
				form.setRebateMethod(String.valueOf(facilityBba.getRebateMethod()));
			}
			
			form.setGppPaymentMode(facilityBba.getGppPaymentMode());
			form.setGppCalculationMethod(facilityBba.getGppCalculationMethod());
			
			if ( facilityBba.getGppTerm() != null) {
				form.setGppTerm(String.valueOf(facilityBba.getGppTerm()));
			}
			
			if (facilityBba.getGppTermCode() != null) {
				form.setGppTermCode(String.valueOf(facilityBba.getGppTermCode()));
			}
			
			// Full Release Profit *
			if (facilityBba.getFullReleaseProfit()!= null) {
				form.setFullReleaseProfit(facilityBba.getFullReleaseProfit().booleanValue() ? "Y" : "N");
			}
			else {
				form.setFullReleaseProfit("N");
			}
			
			// Refund Full Release Profit
			if (facilityBba.getRefundFullReleaseProfit() != null) {
				form.setRefundFullReleaseProfit(String.valueOf(facilityBba.getRefundFullReleaseProfit()));
			}			
			
			// Fulrel profit calculation method
			form.setFulrelProfitCalMethod(String.valueOf(facilityBba.getFulrelProfitCalMethod()));
			
			// Installment Amount
			if (facilityBba.getInstallment() != null) {
				form.setInstallment(UIUtil.formatAmount(facilityBba
						.getInstallment(), decPlaces, locale, withCCY));
			}
			
			// Final Payment Amount
			if (facilityBba.getFinalPaymentAmount() != null) {
				form.setFinalPaymentAmount(UIUtil.formatAmount(facilityBba
						.getFinalPaymentAmount(), decPlaces, locale, withCCY));
			}
			
			// Selling Price 
			if (facilityBba.getSellingPrice() != null) {
				form.setSellingPrice(UIUtil.formatAmount(facilityBba
						.getSellingPrice(), decPlaces, locale, withCCY));
			}
			
			// Total GPP Amount 
			if (facilityBba.getTotalGppAmount() != null) {
				form.setTotalGppAmount(UIUtil.formatAmount(facilityBba
						.getTotalGppAmount(), decPlaces, locale, withCCY));
			}			
			
			// Total Profit 
			if (facilityBba.getTotalProfit() != null) {
				form.setTotalProfit(UIUtil.formatAmount(facilityBba
						.getTotalProfit(), decPlaces, locale, withCCY));
			}
			
		}
		catch (RuntimeException e) {
			MapperException me = new MapperException("failed to map ob values into form");
			me.initCause(e);
			throw me;
		}
		catch (Exception e) {
			MapperException me = new MapperException("failed to format amount from object to display values");
			me.initCause(e);
			throw me;
		}
		return form;
	}
}
