package com.integrosys.cms.ui.limit.facility.multitierfin;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.IFacilityMultiTierFinancing;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBFacilityMultiTierFinancing;
import com.integrosys.cms.ui.common.UIUtil;

public class MultiTierFinMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
				{ "limit", "com.integrosys.cms.app.limit.bus.ILimit", SERVICE_SCOPE }, });
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		IFacilityMaster facilityMasterObj = (IFacilityMaster) inputs.get("facilityMasterObj");
		ILimit limit = (ILimit) inputs.get("limit");
		if (limit == null) {
			limit = facilityMasterObj.getLimit();
		}
		 String currencyCode = limit.getApprovedLimitAmount().getCurrencyCode();
		MultiTierFinForm form = (MultiTierFinForm) cForm;
		IFacilityMultiTierFinancing multiTierFin = new OBFacilityMultiTierFinancing();

		multiTierFin.setCurrencyCode(currencyCode);
        if (StringUtils.isNotBlank(form.getTierSeqNo())) {
			multiTierFin.setTierSeqNo(Short.valueOf(form.getTierSeqNo()));
		}

        if (StringUtils.isNotBlank(form.getTierTerm())) {
			multiTierFin.setTierTerm(Short.valueOf(form.getTierTerm()));
		} 

		if (StringUtils.isNotBlank(form.getTierTermCode())) {
			multiTierFin.setTierTermCode(new Character(form.getTierTermCode().charAt(0)));
		}
		
		if (StringUtils.isNotBlank(form.getRate())) {
			multiTierFin.setRate(Double.valueOf(form.getRate()));
		}
		
		if(StringUtils.isNotBlank(form.getRateNumber())){
			multiTierFin.setRateNumber(Long.valueOf(form.getRateNumber()));
		}else{
			multiTierFin.setRateNumber(null);
		}
		
		if(StringUtils.isNotBlank(form.getRateVariance())){
			multiTierFin.setRateVariance(Double.valueOf(form.getRateVariance()));
		}else{
			multiTierFin.setRateVariance(null);
		}
		
		if(StringUtils.isNotBlank(form.getPaymentAmount())){
			multiTierFin.setPaymentAmount(new Amount(UIUtil.mapStringToBigDecimal(form.getPaymentAmount()),
					new CurrencyCode(currencyCode)));
		}else{
			multiTierFin.setPaymentAmount(null);
		}
		

		multiTierFin.setVarianceCode(form.getVarianceCode());
		multiTierFin.setGracePeriod(form.getGracePeriod());
		
		return multiTierFin;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		MultiTierFinForm form = (MultiTierFinForm) cForm;
		IFacilityMultiTierFinancing multiTierFin = (IFacilityMultiTierFinancing) obj;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
	
		if (multiTierFin != null) {
			try {
				
				if (multiTierFin.getTierSeqNo() != null)
					form.setTierSeqNo(String.valueOf(multiTierFin.getTierSeqNo()));
				
				if (multiTierFin.getTierTerm() != null) 
					form.setTierTerm(String.valueOf(multiTierFin.getTierTerm()));
				
				if (multiTierFin.getTierTermCode() != null) 
					form.setTierTermCode(String.valueOf(multiTierFin.getTierTermCode()));
				
				if (multiTierFin.getRate() != null)
					form.setRate(String.valueOf(multiTierFin.getRate()));
				
				if(multiTierFin.getRateNumber()!=null){
					form.setRateNumber(String.valueOf(multiTierFin.getRateNumber()));
				}
				
				if(multiTierFin.getRateVariance()!=null){
					form.setRateVariance(String.valueOf(multiTierFin.getRateVariance()));
				}
				
				if(multiTierFin.getPaymentAmount()!=null){
					form.setPaymentAmount(UIUtil.formatAmount(multiTierFin.getPaymentAmount(), 2, locale, false));
				}
				
				form.setGracePeriod(multiTierFin.getGracePeriod());
				form.setVarianceCode(multiTierFin.getVarianceCode());
				
			}
			catch (Exception e) {
				DefaultLogger.error(this, "Exception Caught", e);
				e.printStackTrace();
			}
		}
		return form;
	}
}
