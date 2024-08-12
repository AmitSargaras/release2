package com.integrosys.cms.ui.limit.facility.incremental;

import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.limit.bus.OBFacilityIncremental;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;

public class FacilityIncrementalMapper extends AbstractCommonMapper {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
			{ "sessionFacIncrementalObj", "com.integrosys.cms.app.limit.bus.OBFacilityIncremental", SERVICE_SCOPE},
		});
	}	
	
	public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		
		FacilityIncrementalForm form = (FacilityIncrementalForm)cForm;
		
		OBFacilityIncremental incremental = (OBFacilityIncremental) map.get("sessionFacIncrementalObj");
		if (incremental == null)
			incremental = new OBFacilityIncremental();
		
		try {
			if (StringUtils.isBlank(form.getAmountApplied())) {
				incremental.setAmountApplied(null);
			} else {
				incremental.setAmountApplied(UIUtil.mapStringToBigDecimal(form.getAmountApplied(), locale));				
			}
			
			if (StringUtils.isBlank(form.getIncrementalReductionLimit())) {
				incremental.setIncrementalLimit(null);
			} else {
				incremental.setIncrementalLimit(UIUtil.mapStringToBigDecimal(form.getIncrementalReductionLimit(), locale));
			}
			
			if (StringUtils.isBlank(form.getApplicationDate())) {
				incremental.setApplicationDate(null);
			} else {
				incremental.setApplicationDate(UIUtil.mapStringToDate(
						locale, incremental.getApplicationDate(), form.getApplicationDate()));
			}
			
			incremental.setLoanPurposeCodeCategoryCode(CategoryCodeConstant.LOAN_PURPOSE);
			incremental.setLoanPurposeCodeEntryCode(form.getPurpose());
			
			if (StringUtils.isBlank(form.getOfferDate())) {
				incremental.setDateOfOffer(null);
			} else {
				incremental.setDateOfOffer(UIUtil.mapStringToDate(
						locale, incremental.getDateOfOffer(), form.getOfferDate()));
			}

			if (StringUtils.isBlank(form.getOfferAcceptanceDate())) {
				incremental.setDateOfferAccepted(null);
			} else {
				incremental.setDateOfferAccepted(UIUtil.mapStringToDate(
						locale, incremental.getDateOfferAccepted(), form.getOfferAcceptanceDate()));
			}
			
			if (StringUtils.isBlank(form.getApprovedDate())) {
				incremental.setDateApproved(null);
			} else {
				incremental.setDateApproved(UIUtil.mapStringToDate(
						locale, incremental.getDateApproved(), form.getApprovedDate()));
			}

			incremental.setApprovedByCategoryCode(CategoryCodeConstant.APPROVED_BY);
			incremental.setApprovedByEntryCode(form.getApprovedBy());
			
			incremental.setCancelRejectCodeCategoryCode(CategoryCodeConstant.CANC_REJ_CODE);
			incremental.setCancelRejectCodeEntryCode(form.getCancelRejectCode());
			
			if (StringUtils.isBlank(form.getCancelRejectDate())) {
				incremental.setCancelRejectDate(null);
			} else {
				incremental.setCancelRejectDate(UIUtil.mapStringToDate(
						locale, incremental.getCancelRejectDate(), form.getCancelRejectDate()));
			}
			
			incremental.setFacilityStatusCategoryCode(CategoryCodeConstant.FACILITY_STATUS);
			incremental.setFacilityStatus(form.getFacilityStatus());
			
			if (StringUtils.isBlank(form.getPrimeReviewDate())) {
				incremental.setPrimeReviewDate(null);
			} else {
				incremental.setPrimeReviewDate(UIUtil.mapStringToDate(
						locale, incremental.getPrimeReviewDate(), form.getPrimeReviewDate()));				
			}
			
			if (StringUtils.isBlank(form.getPrimeReviewTerm())) {
				incremental.setPrimeReviewTerm(null);
			} else {
				incremental.setPrimeReviewTerm(UIUtil.mapStringToInteger(form.getPrimeReviewTerm(), locale));
			}
			
			incremental.setPrimeReviewTermCategoryCode(CategoryCodeConstant.PRIME_REVIEW_TERM_CODE);
			incremental.setPrimeReviewTermEntryCode(form.getPrimeReviewTermCode());
			
			incremental.setDealerLLPCodeCategoryCode(CategoryCodeConstant.DEALER);
			incremental.setDealerLLPCodeEntryCode(form.getDealerNumber());
			
			if (StringUtils.isBlank(form.getDateSendToDecisionCenter())) {
				incremental.setDateSendToDecisionCenter(null);
			} else {
				incremental.setDateSendToDecisionCenter(UIUtil.mapStringToDate(
						locale, incremental.getDateSendToDecisionCenter(), form.getDateSendToDecisionCenter()));
			}
			
			if (StringUtils.isBlank(form.getDateReceiveFromDecisionCenter())) {
				incremental.setDateReceiveFromDecisionCenter(null);
			} else {
				incremental.setDateReceiveFromDecisionCenter(UIUtil.mapStringToDate(
						locale, incremental.getDateReceiveFromDecisionCenter(), form.getDateReceiveFromDecisionCenter()));
			}
			
			if (StringUtils.isBlank(form.getDateApproveByCGCBNM())) {
				incremental.setDateApprovedByCGCBNM(null);
			} else {
				incremental.setDateApprovedByCGCBNM(UIUtil.mapStringToDate(
						locale, incremental.getDateApprovedByCGCBNM(), form.getDateApproveByCGCBNM()));
			}
			
			if (StringUtils.isBlank(form.getInstructedDate())) {
				incremental.setDateInstructed(null);
			} else {
				incremental.setDateInstructed(UIUtil.mapStringToDate(
						locale, incremental.getDateInstructed(), form.getInstructedDate()));
			}
			
			incremental.setSolicitorReference(form.getSolicitorReference());
			incremental.setSolicitorName(form.getSolicitorName());
			
			incremental.setRequestReasonCategoryCode(CategoryCodeConstant.REQUEST_REASON_CODE);
			incremental.setRequestReasonEntryCode(form.getRequestReasonCode());
			
			incremental.setDocumentationStatusCategoryCode(CategoryCodeConstant.LMT_STATUS);
			incremental.setDocumentationStatusEntryCode(form.getDocStatus());
			
			if (StringUtils.isBlank(form.getDateUserEnterDocStatus())) {
				incremental.setDocumentationDate(null);
			} else {
				incremental.setDocumentationDate(UIUtil.mapStringToDate(
						locale, incremental.getDocumentationDate(), form.getDateUserEnterDocStatus()));
			}
			
			incremental.setLawyerCodeCategoryCode(CategoryCodeConstant.SOLICITOR);
			incremental.setLawyerCodeEntryCode(form.getLawyerCode());
			
		} catch (RuntimeException e) {
			MapperException me = new MapperException("failed to map form values to ob");
			me.initCause(e);
			throw me;
		}
		
		return incremental;
	}	
	
	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
		FacilityIncrementalForm form = (FacilityIncrementalForm)cForm;
		
		OBFacilityIncremental incremental = null;
		boolean hasError = false;
		if (obj instanceof OBFacilityIncremental) {
			incremental = (OBFacilityIncremental)obj;
		} else {
			hasError = true;
			incremental = (OBFacilityIncremental)((HashMap) obj).get("obj");
		}
		
		try {
			if (incremental != null) {
				// Display field 
				form.setSequenceNumber(UIUtil.mapNumberObjectToString(incremental.getIncrementalNumber(), locale, 0));
				form.setOriginalLimit(UIUtil.mapNumberObjectToString(incremental.getOriginalLimit(), locale, 2));
				form.setDateEntered(UIUtil.mapOBDate_FormString(locale, incremental.getDateEntered()));
				form.setOfferEnterDate(UIUtil.mapOBDate_FormString(locale, incremental.getDateEnteredForOffer()));
				form.setAppliedEnterDate(UIUtil.mapOBDate_FormString(locale, incremental.getDateEnteredForApplied()));
				form.setApproveEnterDate(UIUtil.mapOBDate_FormString(locale, incremental.getDateEnteredForApproved()));
				form.setOfferAcceptedEnterDate(UIUtil.mapOBDate_FormString(locale, incremental.getDateEnteredForOfferAcceptance()));
				form.setCancelRejectEnterDate(UIUtil.mapOBDate_FormString(locale, incremental.getDateEnteredForCancelRejected()));
				
				// Editable fields
				if (!hasError) {
					form.setAmountApplied(
							UIUtil.mapNumberObjectToString(incremental.getAmountApplied(), locale, 2));
					
					form.setIncrementalReductionLimit(
							UIUtil.mapNumberObjectToString(incremental.getIncrementalLimit(), locale, 2));
										
					form.setApplicationDate(UIUtil.mapOBDate_FormString(locale, incremental.getApplicationDate()));
					form.setPurpose(incremental.getLoanPurposeCodeEntryCode());
					form.setOfferDate(UIUtil.mapOBDate_FormString(locale, incremental.getDateOfOffer()));
					form.setOfferAcceptanceDate(UIUtil.mapOBDate_FormString(locale, incremental.getDateOfferAccepted()));
					form.setApprovedDate(UIUtil.mapOBDate_FormString(locale, incremental.getDateApproved()));
					form.setApprovedBy(incremental.getApprovedByEntryCode());
					form.setCancelRejectCode(incremental.getCancelRejectCodeEntryCode());
					form.setCancelRejectDate(UIUtil.mapOBDate_FormString(locale, incremental.getCancelRejectDate()));
					form.setFacilityStatus(incremental.getFacilityStatus());
					form.setPrimeReviewDate(UIUtil.mapOBDate_FormString(locale, incremental.getPrimeReviewDate()));
					form.setPrimeReviewTerm(UIUtil.mapNumberObjectToString(incremental.getPrimeReviewTerm(), locale, 0));
					form.setPrimeReviewTermCode(incremental.getPrimeReviewTermEntryCode());
					form.setDealerNumber(incremental.getDealerLLPCodeEntryCode());
					form.setDateSendToDecisionCenter(UIUtil.mapOBDate_FormString(locale, incremental.getDateSendToDecisionCenter()));
					form.setDateReceiveFromDecisionCenter(UIUtil.mapOBDate_FormString(locale, incremental.getDateReceiveFromDecisionCenter()));
					form.setDateApproveByCGCBNM(UIUtil.mapOBDate_FormString(locale, incremental.getDateApprovedByCGCBNM()));
					form.setInstructedDate(UIUtil.mapOBDate_FormString(locale, incremental.getDateInstructed()));
					form.setSolicitorReference(incremental.getSolicitorReference());
					form.setSolicitorName(incremental.getSolicitorName());
					form.setRequestReasonCode(incremental.getRequestReasonEntryCode());
					form.setDocStatus(incremental.getDocumentationStatusEntryCode());
					form.setDateUserEnterDocStatus(UIUtil.mapOBDate_FormString(locale, incremental.getDocumentationDate()));
					form.setLawyerCode(incremental.getLawyerCodeEntryCode());
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
