package com.integrosys.cms.ui.limit.facility.rentalrenewal;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityIslamicRentalRenewal;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;

public class FacilityIslamicRentalRenewalMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
			{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
		});
	}
	
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		FacilityIslamicRentalRenewalForm form = (FacilityIslamicRentalRenewalForm)cForm;
		IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
		OBFacilityIslamicRentalRenewal ob = facilityMasterObj.getFacilityIslamicRentalRenewal();
		if (ob == null)
			ob = new OBFacilityIslamicRentalRenewal();
		
		try {
			if (StringUtils.isBlank(form.getRenewalTerm())) {
				ob.setRenewalTerm(null);
			} else {
				ob.setRenewalTerm(UIUtil.mapStringToLong(form.getRenewalTerm(), locale));				
			}
			
			ob.setRenewalTermCodeCategoryCode(CategoryCodeConstant.PRIME_REVIEW_TERM_CODE);
			ob.setRenewalTermCodeEntryCode(form.getRenewalTermCode());
			
			if (StringUtils.isBlank(form.getRenewalRate())) {
				ob.setRenewalRate(null);
			} else {
				ob.setRenewalRate(UIUtil.mapStringToBigDecimal(form.getRenewalRate(), locale));
			}
			
			ob.setPrimeRateNumberCategoryCode(CategoryCodeConstant.FAC_RATE);
			ob.setPrimeRateNumberEntryCode(form.getPrimeRateNumber());
			
			if (StringUtils.isBlank(form.getPrimeVariance())) {
				ob.setPrimeVariance(null);
			} else {
				ob.setPrimeVariance(UIUtil.mapStringToBigDecimal(form.getPrimeVariance(), locale));
			}
			
			ob.setPrimeVarianceCode(form.getPrimeVarianceCode());
			
			facilityMasterObj.setFacilityIslamicRentalRenewal(ob);
		} catch (RuntimeException e) {
			MapperException me = new MapperException("failed to map form values to ob");
			me.initCause(e);
			throw me;
		}
		
		return facilityMasterObj;
	}
	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		FacilityIslamicRentalRenewalForm form = (FacilityIslamicRentalRenewalForm)cForm;
		OBFacilityIslamicRentalRenewal ob = (OBFacilityIslamicRentalRenewal)obj;

		try {
			if (ob != null) {
				if (ob.getRenewalTerm() != null) {
					form.setRenewalTerm(UIUtil.mapNumberObjectToString(ob.getRenewalTerm(), locale, 0));				
				}
				
				form.setRenewalTermCode(ob.getRenewalTermCodeEntryCode());
				
				if (ob.getRenewalRate() != null) {
					form.setRenewalRate(UIUtil.mapNumberObjectToString(ob.getRenewalRate(), locale, 7));
				}
				
				form.setPrimeRateNumber(ob.getPrimeRateNumberEntryCode());
				
				if (ob.getPrimeVariance() != null) {
					form.setPrimeVariance(UIUtil.mapNumberObjectToString(ob.getPrimeVariance(), locale, 7));
				}
				
				form.setPrimeVarianceCode(ob.getPrimeVarianceCode());			
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
