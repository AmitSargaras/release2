package com.integrosys.cms.ui.limit.facility.securitydeposit;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityIslamicSecurityDeposit;
import com.integrosys.cms.ui.common.UIUtil;

public class FacilityIslamicSecDepositMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
			{ "facilityMasterObj", "com.integrosys.cms.app.limit.bus.IFacilityMaster", SERVICE_SCOPE },
		});
	}
	
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		IFacilityMaster facilityMasterObj = (IFacilityMaster) map.get("facilityMasterObj");
		FacilityIslamicSecDepositForm form = (FacilityIslamicSecDepositForm)cForm;
		
		OBFacilityIslamicSecurityDeposit obj = facilityMasterObj.getFacilityIslamicSecurityDeposit();
		if (obj == null)
			obj = new OBFacilityIslamicSecurityDeposit();
		
		try {		
			if (StringUtils.isBlank(form.getNumberOfMonth())) {
				obj.setNumberOfMonth(null);
			} else {
				obj.setNumberOfMonth(UIUtil.mapStringToInteger(form.getNumberOfMonth(), locale));
			}
			
			if (StringUtils.isBlank(form.getSecurityDeposit())) {
				obj.setSecurityDeposit(null);
			} else {
				obj.setSecurityDeposit(UIUtil.mapStringToBigDecimal(form.getSecurityDeposit(), locale));
			}
			
			if (StringUtils.isBlank(form.getFixedSecDepositAmt())) {
				obj.setFixSecurityDepositAmount(null);
			} else {
				obj.setFixSecurityDepositAmount(UIUtil.mapStringToBigDecimal(form.getFixedSecDepositAmt(), locale));
			}
			
			if (StringUtils.isBlank(form.getOriginalSecDepositAmt())) {
				obj.setOriginalSecurityDepositAmount(null);
			} else {
				obj.setOriginalSecurityDepositAmount(UIUtil.mapStringToBigDecimal(form.getOriginalSecDepositAmt(), locale));
			}
			
			if (StringUtils.isBlank(form.getRecallSDUponRenewalInd())) {
				obj.setIsRecalculateUponReview(null);
			} else {
				obj.setIsRecalculateUponReview(UIUtil.mapStringToBoolean(form.getRecallSDUponRenewalInd()));
			}
			
			if (StringUtils.isBlank(form.getMthB4PrintRenewalRpt())) {
				obj.setMonthBeforePrintRenewalReport(null);
			} else {
				obj.setMonthBeforePrintRenewalReport(UIUtil.mapStringToInteger(form.getMthB4PrintRenewalRpt(), locale));
			}
			
			obj.setRemarks(form.getRemark());
			
			facilityMasterObj.setFacilityIslamicSecurityDeposit(obj);
		} catch (RuntimeException e) {
			MapperException me = new MapperException("failed to map form values to ob");
			me.initCause(e);
			throw me;
		}
		return facilityMasterObj;
	}
	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		FacilityIslamicSecDepositForm form = (FacilityIslamicSecDepositForm)cForm;
		OBFacilityIslamicSecurityDeposit ob = (OBFacilityIslamicSecurityDeposit)obj;
		
		try {
			if (ob != null) {
				if (ob.getNumberOfMonth() != null) {
					form.setNumberOfMonth(UIUtil.mapNumberObjectToString(ob.getNumberOfMonth(), locale, 0));
				}
				
				if (ob.getSecurityDeposit() != null) {
					form.setSecurityDeposit(UIUtil.mapNumberObjectToString(ob.getSecurityDeposit(), locale, 7));
				}
				
				if (ob.getFixSecurityDepositAmount() != null) {
					form.setFixedSecDepositAmt(UIUtil.mapNumberObjectToString(ob.getFixSecurityDepositAmount(), locale, 2));
				}
				
				if (ob.getOriginalSecurityDepositAmount() != null) {
					form.setOriginalSecDepositAmt(UIUtil.mapNumberObjectToString(ob.getOriginalSecurityDepositAmount(), locale, 7));
				}
				
				form.setRecallSDUponRenewalInd(UIUtil.mapBooleanToString(ob.getIsRecalculateUponReview()));
				
				if (ob.getMonthBeforePrintRenewalReport() != null) {
					form.setMthB4PrintRenewalRpt(UIUtil.mapNumberObjectToString(ob.getMonthBeforePrintRenewalReport(), locale, 0));
				}
				
				form.setRemark(ob.getRemarks());
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
