package com.integrosys.cms.ui.limit.facility.reduction;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.OBFacilityReduction;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;


public class FacilityReductionMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
			{ "sessionFacReductionObj", "com.integrosys.cms.app.limit.bus.OBFacilityReduction", SERVICE_SCOPE},
		});
	}	
	
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		FacilityReductionForm form = (FacilityReductionForm)cForm;
		OBFacilityReduction reduction = (OBFacilityReduction)map.get("sessionFacReductionObj");
		if (reduction == null)
			reduction = new OBFacilityReduction();
		
		try {
			if (StringUtils.isBlank(form.getAmountApplied())) {
				reduction.setAmountApplied(null);
			} else {
				reduction.setAmountApplied(UIUtil.mapStringToBigDecimal(form.getAmountApplied(), locale));				
			}
			
			if (StringUtils.isBlank(form.getIncrementalReductionLimit())) {
				reduction.setIncrementalLimit(null);
			} else {
				reduction.setIncrementalLimit(UIUtil.mapStringToBigDecimal(form.getIncrementalReductionLimit(), locale));
			}
			
			if (StringUtils.isBlank(form.getApplicationDate())) {
				reduction.setApplicationDate(null);
			} else {
				reduction.setApplicationDate(UIUtil.mapStringToDate(
						locale, reduction.getApplicationDate(), form.getApplicationDate()));
			}
			
			if (StringUtils.isBlank(form.getOfferAcceptanceDate())) {
				reduction.setDateOfferAccepted(null);
			} else {
				reduction.setDateOfferAccepted(UIUtil.mapStringToDate(
						locale, reduction.getDateOfferAccepted(), form.getOfferAcceptanceDate()));
			}
			
			if (StringUtils.isBlank(form.getApprovedDate())) {
				reduction.setDateApproved(null);
			} else {
				reduction.setDateApproved(UIUtil.mapStringToDate(
						locale, reduction.getDateApproved(), form.getApprovedDate()));
			}

			reduction.setApprovedByCategoryCode(CategoryCodeConstant.APPROVED_BY);
			reduction.setApprovedByEntryCode(form.getApprovedBy());
			
			reduction.setCancelRejectCodeCategoryCode(CategoryCodeConstant.CANC_REJ_CODE);
			reduction.setCancelRejectCodeEntryCode(form.getCancelRejectCode());
			
			if (StringUtils.isBlank(form.getCancelRejectDate())) {
				reduction.setCancelRejectDate(null);
			} else {
				reduction.setCancelRejectDate(UIUtil.mapStringToDate(
						locale, reduction.getCancelRejectDate(), form.getCancelRejectDate()));
			}
			
			reduction.setFacilityStatusCategoryCode(CategoryCodeConstant.FACILITY_STATUS);
			reduction.setFacilityStatus(form.getFacilityStatus());			
			
			if (StringUtils.isBlank(form.getInstructedDate())) {
				reduction.setDateInstructed(null);
			} else {
				reduction.setDateInstructed(UIUtil.mapStringToDate(
						locale, reduction.getDateInstructed(), form.getInstructedDate()));
			}
			
			reduction.setSolicitorReference(form.getSolicitorReference());
			reduction.setSolicitorName(form.getSolicitorName());
			
			reduction.setRequestReasonCategoryCode(CategoryCodeConstant.REQUEST_REASON_CODE);
			reduction.setRequestReasonEntryCode(form.getRequestReasonCode());
			
			reduction.setDocumentationStatusCategoryCode(CategoryCodeConstant.LMT_STATUS);
			reduction.setDocumentationStatusEntryCode(form.getDocStatus());
			
			if (StringUtils.isBlank(form.getDateUserEnterDocStatus())) {
				reduction.setDocumentationDate(null);
			} else {
				reduction.setDocumentationDate(UIUtil.mapStringToDate(
						locale, reduction.getDocumentationDate(), form.getDateUserEnterDocStatus()));
			}
			
			reduction.setLawyerCodeCategoryCode(CategoryCodeConstant.SOLICITOR);
			reduction.setLawyerCodeEntryCode(form.getLawyerCode());
			
		} catch (RuntimeException e) {
			MapperException me = new MapperException("failed to map form values to ob");
			me.initCause(e);
			throw me;
		}
		
		return reduction;		
	}
	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		FacilityReductionForm form = (FacilityReductionForm)cForm;
		
		OBFacilityReduction reduction = null;
		boolean hasError = false;
		if (obj instanceof OBFacilityReduction) {
			reduction = (OBFacilityReduction)obj;
		} else {
			hasError = true;
			reduction = (OBFacilityReduction)((HashMap) obj).get("obj");
		}
		
		try {
			if (reduction != null) {
				// Display field 
				form.setSequenceNumber(UIUtil.mapNumberObjectToString(reduction.getIncrementalNumber(), locale, 0));
				form.setOriginalLimit(UIUtil.mapNumberObjectToString(reduction.getOriginalLimit(), locale, 2));
				form.setDateEntered(UIUtil.mapOBDate_FormString(locale, reduction.getDateEntered()));
				form.setOfferEnterDate(UIUtil.mapOBDate_FormString(locale, reduction.getDateEnteredForOffer()));
				form.setAppliedEnterDate(UIUtil.mapOBDate_FormString(locale, reduction.getDateEnteredForApplied()));
				form.setApproveEnterDate(UIUtil.mapOBDate_FormString(locale, reduction.getDateEnteredForApproved()));
				form.setOfferAcceptedEnterDate(UIUtil.mapOBDate_FormString(locale, reduction.getDateEnteredForOfferAcceptance()));
				form.setCancelRejectEnterDate(UIUtil.mapOBDate_FormString(locale, reduction.getDateEnteredForCancelRejected()));
				
				// Editable fields
				if (!hasError) {
					form.setAmountApplied(
							UIUtil.mapNumberObjectToString(reduction.getAmountApplied(), locale, 2));
					
					form.setIncrementalReductionLimit(
							UIUtil.mapNumberObjectToString(reduction.getIncrementalLimit(), locale, 2));
					
					form.setApplicationDate(UIUtil.mapOBDate_FormString(locale, reduction.getApplicationDate()));
					form.setOfferAcceptanceDate(UIUtil.mapOBDate_FormString(locale, reduction.getDateOfferAccepted()));
					form.setApprovedDate(UIUtil.mapOBDate_FormString(locale, reduction.getDateApproved()));
					form.setApprovedBy(reduction.getApprovedByEntryCode());
					form.setCancelRejectCode(reduction.getCancelRejectCodeEntryCode());
					form.setCancelRejectDate(UIUtil.mapOBDate_FormString(locale, reduction.getCancelRejectDate()));
					form.setFacilityStatus(reduction.getFacilityStatus());
					form.setInstructedDate(UIUtil.mapOBDate_FormString(locale, reduction.getDateInstructed()));
					form.setSolicitorReference(reduction.getSolicitorReference());
					form.setSolicitorName(reduction.getSolicitorName());
					form.setRequestReasonCode(reduction.getRequestReasonEntryCode());
					form.setDocStatus(reduction.getDocumentationStatusEntryCode());
					form.setDateUserEnterDocStatus(UIUtil.mapOBDate_FormString(locale, reduction.getDocumentationDate()));
					form.setLawyerCode(reduction.getLawyerCodeEntryCode());
				}
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
