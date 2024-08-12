package com.integrosys.cms.ui.tatdoc;

import java.util.Date;
import java.util.Locale;

import com.integrosys.cms.app.tatdoc.bus.MaintainTatDurationUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;

public class TatDocFormValidator {
	public static ActionErrors validateInput(TatDocForm form, Locale locale) {

		ActionErrors errors = new ActionErrors();
		int i = 0;
		int j = 0;
		String draft = null;
		boolean isMandatory = false;

		if (form.getEvent().equals("submit") || form.getEvent().equals("submit_todo")) {
			isMandatory = true;
		}

		/**
		 * validate documentReceivedDate so that the previous draft should be
		 * fill in before the next draft can be fill in
		 */
//		String isPAOrSolInvolvementReq = (form.getIsPAOrSolInvolvementReq() == null ? "" : form
//				.getIsPAOrSolInvolvementReq());
//		for (i = 0; i < form.getDocumentReceivedDate().length; i++) {
//			if (StringUtils.isBlank(form.getDocumentReceivedDate()[0]) && isPAOrSolInvolvementReq.equals("Y")
//					&& isMandatory) {
//				errors.add("documentReceivedDate", new ActionMessage("error.date.mandatory"));
//				break;
//			}
//			if (StringUtils.isBlank(form.getDocumentReceivedDate()[i])) {
//				draft = (i + 1) + "";
//				for (j = i + 1; j < form.getDocumentReceivedDate().length; j++) {
//					if (StringUtils.isNotBlank(form.getDocumentReceivedDate()[j])) {
//						errors.add("documentReceivedDate", new ActionMessage("error.date.empty", "Draft " + draft));
//						break;
//					}
//					else {
//						draft = draft + ", " + (j + 1);
//					}
//				}
//				break;
//			}
//			else {
//				if ((DateUtil.convertDate(locale, form.getDocumentReceivedDate()[i]).compareTo(new Date())) > 0) {
//					errors.add("documentReceivedDate", new ActionMessage("error.date.future"));
//					break;
//				}
//				else {
//					// boolean isBreak = false;
//				}
//			}
//		}
//
//		if (!errors.get("documentReceivedDate").hasNext()) {
//			for (int k = 1; k < form.getDocumentReceivedDate().length; k++) {
//				if (StringUtils.isNotBlank(form.getDocumentReceivedDate()[k])) {
//					if ((DateUtil.convertDate(locale, form.getDocumentReceivedDate()[k - 1]).compareTo(DateUtil
//							.convertDate(locale, form.getDocumentReceivedDate()[k]))) > 0) {
//						errors.add("documentReceivedDate", new ActionMessage("error.date.compareDate",
//								"Document Received Draft " + (k + 1), "Document Received Draft " + k));
//						break;
//					}
//				}
//			}
//		}

		/**
		 * validate docSentToLegalDeptDate so that the previous draft should be
		 * fill in before the next draft can be fill in
		 */
//		draft = null;
//		for (i = 0; i < form.getDocSentToLegalDeptDate().length; i++) {
//			if (StringUtils.isBlank(form.getDocSentToLegalDeptDate()[i])) {
//				draft = (i + 1) + "";
//				for (j = i + 1; j < form.getDocSentToLegalDeptDate().length; j++) {
//					if (StringUtils.isNotBlank(form.getDocSentToLegalDeptDate()[j])) {
//						errors.add("docSentToLegalDeptDate", new ActionMessage("error.date.empty", "Draft " + draft));
//						break;
//					}
//					else {
//						draft = draft + ", " + (j + 1);
//					}
//				}
//				break;
//			}
//			else {
//				if ((DateUtil.convertDate(locale, form.getDocSentToLegalDeptDate()[i]).compareTo(new Date())) > 0) {
//					errors.add("docSentToLegalDeptDate", new ActionMessage("error.date.future"));
//					break;
//				}
//			}
//		}
//
//		if (!errors.get("docSentToLegalDeptDate").hasNext()) {
//			for (int k = 1; k < form.getDocSentToLegalDeptDate().length; k++) {
//				if (StringUtils.isNotBlank(form.getDocSentToLegalDeptDate()[k])) {
//					if ((DateUtil.convertDate(locale, form.getDocSentToLegalDeptDate()[k - 1]).compareTo(DateUtil
//							.convertDate(locale, form.getDocSentToLegalDeptDate()[k]))) > 0) {
//						errors.add("docSentToLegalDeptDate", new ActionMessage("error.date.compareDate",
//								"Document To Legal Draft " + (k + 1), "Document To Legal Draft " + k));
//						break;
//					}
//				}
//			}
//		}

		/**
		 * validate docReceivedFromLegalDeptDate so that the previous draft
		 * should be fill in before the next draft can be fill in
		 */
//		draft = null;
//		for (i = 0; i < form.getDocReceivedFromLegalDeptDate().length; i++) {
//			if (StringUtils.isBlank(form.getDocReceivedFromLegalDeptDate()[i])) {
//				draft = (i + 1) + "";
//				for (j = i + 1; j < form.getDocReceivedFromLegalDeptDate().length; j++) {
//					if (StringUtils.isNotBlank(form.getDocReceivedFromLegalDeptDate()[j])) {
//						errors.add("docReceivedFromLegalDeptDate", new ActionMessage("error.date.empty", "Draft "
//								+ draft));
//						break;
//					}
//					else {
//						draft = draft + ", " + (j + 1);
//					}
//				}
//				break;
//			}
//			else {
//				if ((DateUtil.convertDate(locale, form.getDocReceivedFromLegalDeptDate()[i]).compareTo(new Date())) > 0) {
//					errors.add("docReceivedFromLegalDeptDate", new ActionMessage("error.date.future"));
//					break;
//				}
//			}
//		}
//
//		if (!errors.get("docReceivedFromLegalDeptDate").hasNext()) {
//			for (int k = 1; k < form.getDocReceivedFromLegalDeptDate().length; k++) {
//				if (StringUtils.isNotBlank(form.getDocReceivedFromLegalDeptDate()[k])) {
//					if ((DateUtil.convertDate(locale, form.getDocReceivedFromLegalDeptDate()[k - 1]).compareTo(DateUtil
//							.convertDate(locale, form.getDocReceivedFromLegalDeptDate()[k]))) > 0) {
//						errors.add("docReceivedFromLegalDeptDate", new ActionMessage("error.date.compareDate",
//								"Document From Legal Draft " + (k + 1), "Document From Legal Draft " + k));
//						break;
//					}
//				}
//			}
//		}

		/**
		 * validate documentReturnedDate so that the previous draft should be
		 * fill in before the next draft can be fill in
		 */
//		draft = null;
//		for (i = 0; i < form.getDocumentReturnedDate().length; i++) {
//			if (StringUtils.isBlank(form.getDocumentReturnedDate()[i])) {
//				draft = (i + 1) + "";
//				for (j = i + 1; j < form.getDocumentReturnedDate().length; j++) {
//					if (StringUtils.isNotBlank(form.getDocumentReturnedDate()[j])) {
//						errors.add("documentReturnedDate", new ActionMessage("error.date.empty", "Draft " + draft));
//						break;
//					}
//					else {
//						draft = draft + ", " + (j + 1);
//					}
//				}
//				break;
//			}
//			else {
//				if ((DateUtil.convertDate(locale, form.getDocumentReturnedDate()[i]).compareTo(new Date())) > 0) {
//					errors.add("documentReturnedDate", new ActionMessage("error.date.future"));
//					break;
//				}
//			}
//		}
//
//		if (!errors.get("documentReturnedDate").hasNext()) {
//			for (int k = 1; k < form.getDocumentReturnedDate().length; k++) {
//				if (StringUtils.isNotBlank(form.getDocumentReturnedDate()[k])) {
//					if ((DateUtil.convertDate(locale, form.getDocumentReturnedDate()[k - 1]).compareTo(DateUtil
//							.convertDate(locale, form.getDocumentReturnedDate()[k]))) > 0) {
//						errors.add("documentReturnedDate", new ActionMessage("error.date.compareDate",
//								"Document Returned Draft " + (k + 1), "Document Returned Draft " + k));
//						break;
//					}
//				}
//			}
//		}

		// fileFromBizCenterDate
//		if (StringUtils.isBlank(form.getFileFromBizCenterDate())) {
//			if (isMandatory) {
//				errors.add("fileFromBizCenterDate", new ActionMessage("error.date.mandatory"));
//			}
//		}
//		else {
//			if ((DateUtil.convertDate(locale, form.getFileFromBizCenterDate()).compareTo(new Date())) > 0) {
//				errors.add("fileFromBizCenterDate", new ActionMessage("error.date.future"));
//			}
//			else if (StringUtils.isNotBlank(form.getSolicitorInstructionDate())
//					&& (DateUtil.convertDate(locale, form.getFileFromBizCenterDate()).compareTo(DateUtil.convertDate(
//							locale, form.getSolicitorInstructionDate()))) > 0) {
//				errors.add("fileFromBizCenterDate", new ActionMessage("error.date.compareDate.cannotBelater",
//						"This date", "Date of Instruction to Solicitor"));
//			}
//		}

		// solicitorInstructionDate
//		if (StringUtils.isNotBlank(form.getSolicitorInstructionDate())) {
//			if ((DateUtil.convertDate(locale, form.getSolicitorInstructionDate()).compareTo(new Date())) > 0) {
//				errors.add("solicitorInstructionDate", new ActionMessage("error.date.future"));
//			}
//			else if (StringUtils.isNotBlank(form.getApprovalDate())
//					&& (DateUtil.convertDate(locale, form.getSolicitorInstructionDate()).compareTo(DateUtil
//							.convertDate(locale, form.getApprovalDate()))) < 0) {
//				errors.add("solicitorInstructionDate", new ActionMessage("error.date.compareDate",
//						"Date of Instruction to Solicitor", "Approval Date"));
//			}
//		}

		// docReceivedFromLegalDeptDate
//		if (!errors.get("docReceivedFromLegalDeptDate").hasNext()) {
//			for (i = 0; i < form.getDocReceivedFromLegalDeptDate().length; i++) {
//				if (StringUtils.isNotBlank(form.getDocReceivedFromLegalDeptDate()[i])) {
//					if (StringUtils.isBlank(form.getDocSentToLegalDeptDate()[i])) {
//						errors.add("docReceivedFromLegalDeptDate", new ActionMessage("error.date.required",
//								"Document Sent To Legal Department Draft " + (i + 1)));
//					}
//					/*
//					 * else if (((DateUtil.convertDate(locale,
//					 * form.getDocReceivedFromLegalDeptDate
//					 * ()[i])).compareTo(DateUtil .convertDate(locale,
//					 * form.getDocSentToLegalDeptDate()[i]))) < 0) {
//					 * errors.add("docReceivedFromLegalDeptDate", new
//					 * ActionMessage( "error.date.compareDate.cannotBeEarlier",
//					 * "This date", "Document Sent To Legal Department Draft " +
//					 * (i + 1))); }
//					 */
//				}
//			}
//		}

		// documentReturnedDate
//		if (!errors.get("documentReturnedDate").hasNext()) {
//			for (i = 0; i < form.getDocumentReturnedDate().length; i++) {
//				if (StringUtils.isNotBlank(form.getDocumentReturnedDate()[i])) {
//					if (StringUtils.isBlank(form.getDocumentReceivedDate()[i])) {
//						errors.add("documentReturnedDate", new ActionMessage("error.date.required",
//								"Document Received Draft " + (i + 1)));
//					}
//					/*
//					 * else if (((DateUtil.convertDate(locale,
//					 * form.getDocumentReturnedDate()[i])).compareTo(DateUtil
//					 * .convertDate(locale, form.getDocumentReceivedDate()[i])))
//					 * < 0) { errors.add("documentReturnedDate", new
//					 * ActionMessage("error.date.compareDate.cannotBeEarlier",
//					 * "This date", "Document Received Draft " + (i + 1))); }
//					 */
//				}
//			}
//		}

		// docReadyForPADate and PAExecutedDate

		/*
		 * if (StringUtils.isBlank(form.getDocReadyForPADate())) {
		 * errors.add("docReadyForPADate", new
		 * ActionMessage("error.date.mandatory")); } else { if
		 * ((DateUtil.convertDate(locale,
		 * form.getDocReadyForPADate()).compareTo(new Date())) > 0) {
		 * errors.add("docReadyForPADate", new
		 * ActionMessage("error.date.future")); } }
		 */
//		if (!StringUtils.isBlank(form.getDocReadyForPADate())) {
//			if ((DateUtil.convertDate(locale, form.getDocReadyForPADate()).compareTo(new Date())) > 0) {
//				errors.add("docReadyForPADate", new ActionMessage("error.date.future"));
//			}
//			else if (form.getPAExecutedDate() != null && !"".equals(form.getPAExecutedDate())) {
//				if (StringUtils.isBlank(form.getDocReadyForPADate())) {
//					errors.add("PAExecutedDate", new ActionMessage("error.date.required",
//							"Date Document Received For PA Execution"));
//				}
//				else if (((DateUtil.convertDate(locale, form.getPAExecutedDate())).compareTo(DateUtil.convertDate(
//						locale, form.getDocReadyForPADate()))) < 0) {
//					errors.add("PAExecutedDate", new ActionMessage("error.date.compareDate.cannotBeEarlier",
//							"This date", "Date Document Received For PA Execution"));
//				}
//				else {
//					if ((DateUtil.convertDate(locale, form.getPAExecutedDate()).compareTo(new Date())) > 0) {
//						errors.add("PAExecutedDate", new ActionMessage("error.date.future"));
//					}
//				}
//			}
//			else if (isPAOrSolInvolvementReq.equals("Y")) {
//				errors.add("PAExecutedDate", new ActionMessage("error.date.mandatory"));
//			}
//		}
		// solicitorAdviseReleaseDate
//		if (StringUtils.isNotBlank(form.getSolicitorAdviseReleaseDate())) {
//			if (StringUtils.isBlank(form.getPAExecutedDate())) {
//				if (isPAOrSolInvolvementReq.equals("Y")) {
//					errors.add("solicitorAdviseReleaseDate", new ActionMessage("error.date.required",
//							"Date Document Received For PA Execution"));
//				}
//
//			}
//			else if (((DateUtil.convertDate(locale, form.getSolicitorAdviseReleaseDate())).compareTo(DateUtil
//					.convertDate(locale, form.getPAExecutedDate()))) < 0) {
//				errors.add("solicitorAdviseReleaseDate", new ActionMessage("error.date.compareDate.cannotBeEarlier",
//						"This date", "Date Document Executed By PA"));
//			}
//			else {
//				if ((DateUtil.convertDate(locale, form.getSolicitorAdviseReleaseDate()).compareTo(new Date())) > 0) {
//					errors.add("solicitorAdviseReleaseDate", new ActionMessage("error.date.future"));
//				}
//			}
//		}

		// disbursementDocCompletedDate
//		if (StringUtils.isNotBlank(form.getDisbursementDocCompletedDate())) {
//			if (StringUtils.isBlank(form.getSolicitorAdviseReleaseDate())) {
//				if (isPAOrSolInvolvementReq.equals("Y")) {
//					errors.add("disbursementDocCompletedDate", new ActionMessage("error.date.required",
//							"Solicitor Advice For Release"));
//				}
//
//			}
//			else if (((DateUtil.convertDate(locale, form.getDisbursementDocCompletedDate())).compareTo(DateUtil
//					.convertDate(locale, form.getSolicitorAdviseReleaseDate()))) < 0) {
//				errors.add("disbursementDocCompletedDate", new ActionMessage("error.date.compareDate.cannotBeEarlier",
//						"This date", "Solicitor Advice For Release"));
//			}
//			else {
//				if ((DateUtil.convertDate(locale, form.getDisbursementDocCompletedDate()).compareTo(new Date())) > 0) {
//					errors.add("disbursementDocCompletedDate", new ActionMessage("error.date.future"));
//				}
//			}
//		}

		// disbursementDate
//		if (StringUtils.isNotBlank(form.getDisbursementDate())
//				&& ((DateUtil.convertDate(locale, form.getDisbursementDate()).compareTo(new Date())) > 0)) {
//			errors.add("disbursementDate", new ActionMessage("error.date.future"));
//		}

		/**
		 * docCompletionDate <li>
		 * <ol>
		 * validate docCompletionDate so that it check for completion document
		 * first before it can be fill in
		 * <ol>
		 * validate if disbursementDate have value or not
		 * <ol>
		 * if disbursementDate validate whether the date value on
		 * docCompletionDate is later than disbursementDate
		 * </li>
		 */
//		if (StringUtils.isNotBlank(form.getDocCompletionDate())) {
//			if (StringUtils.isBlank(form.getDisbursementDate())) {
//				errors.add("docCompletionDate", new ActionMessage("error.date.required", "Date of Disbursement"));
//			}
//			else if (((DateUtil.convertDate(locale, form.getDocCompletionDate())).compareTo(DateUtil.convertDate(
//					locale, form.getDisbursementDate()))) < 0) {
//				errors.add("docCompletionDate", new ActionMessage("error.date.compareDate.cannotBeEarlier",
//						"This date", "Date of Disbursement"));
//			}
//			else {
//				if ((DateUtil.convertDate(locale, form.getDocCompletionDate()).compareTo(new Date())) > 0) {
//					errors.add("docCompletionDate", new ActionMessage("error.date.future"));
//				}
//			}
//		}

        boolean forceReason = false;
        //String stage = "";

        //checking for pre reason
        if (form.getPreActualDateString() != null) {
            String[] preActualDateString = form.getPreActualDateString();
            for (int x = 0; x < preActualDateString.length; x++) {
                if (preActualDateString[x].equals("null")) continue;

                if (form.getPreIsStageActive()[x].equals("false")) continue;

                String strForeCastDt = form.getPreEndDateString()[x];
                if (!strForeCastDt.equals("null")) {
                    Date forecastEndDt = MaintainTatDurationUtil.getStringToDate(strForeCastDt);
                    Date actualEndDt = MaintainTatDurationUtil.getStringToDate(preActualDateString[x]);
                    //Date forecastEndDt = DateUtil.convertDate(locale, strForeCastDt);
                    //Date actualEndDt = DateUtil.convertDate(locale, preActualDateString[x]);

                    if (actualEndDt != null && forecastEndDt != null) {
                        if (actualEndDt.after(forecastEndDt) && form.getPreReason()[x].equals("0")) {
                            forceReason = true;
                            //stage = form.getPreTatParamItemID()[x];
                            errors.add("Stage" + x, new ActionMessage("error.tatConfirm.NoReason"));
                            break;
                        }
                    }
                }
            }
        }
        //checking for in reason
        if (!forceReason) {
            if (form.getInActualDateString() != null) {
                String[] inActualDateString = form.getInActualDateString();
                for (int x = 0; x < inActualDateString.length; x++) {
                    if (inActualDateString[x].equals("null")) continue;

                    if (form.getInIsStageActive()[x].equals("false")) continue;

                    String strForeCastDt = form.getInEndDateString()[x];
                    if (!strForeCastDt.equals("null")) {
                        Date forecastEndDt = MaintainTatDurationUtil.getStringToDate(strForeCastDt);
                        Date actualEndDt = MaintainTatDurationUtil.getStringToDate(inActualDateString[x]);
                        //Date forecastEndDt = DateUtil.convertDate(locale, strForeCastDt);
                        //Date actualEndDt = DateUtil.convertDate(locale, inActualDateString[x]);

                        if (actualEndDt != null && forecastEndDt != null) {
                            if (actualEndDt.after(forecastEndDt) && form.getPreReason()[x].equals("0")) {
                                forceReason = true;
                                //stage = form.getInTatParamItemID()[x];
                                errors.add("Stage" + x, new ActionMessage("error.tatConfirm.NoReason"));
                                break;
                            }
                        }
                    }
                }
            }
        }
        //checking for post reason
        if (!forceReason) {
            if (form.getPostActualDateString() != null) {
                String[] postActualDateString = form.getPostActualDateString();
                for (int x = 0; x < postActualDateString.length; x++) {
                    if (postActualDateString[x].equals("null")) continue;

                    if (form.getPostIsStageActive()[x].equals("false")) continue;

                    String strForeCastDt = form.getPostEndDateString()[x];
                    if (!strForeCastDt.equals("null")) {
                        Date forecastEndDt = MaintainTatDurationUtil.getStringToDate(strForeCastDt);
                        Date actualEndDt = MaintainTatDurationUtil.getStringToDate(postActualDateString[x]);
                        //Date forecastEndDt = DateUtil.convertDate(locale, strForeCastDt);
                        //Date actualEndDt = DateUtil.convertDate(locale, postActualDateString[x]);

                        if (actualEndDt != null && forecastEndDt != null) {
                            if (actualEndDt.after(forecastEndDt) && form.getPreReason()[x].equals("0")) {
                                forceReason = true;
                                //stage = form.getInTatParamItemID()[x];
                                errors.add("Stage" + x, new ActionMessage("error.tatConfirm.NoReason"));
                                break;
                            }
                        }
                    }
                }
            }
        }

		if (StringUtils.isNotBlank(form.getPreDisburseRemarks())) {
			if (form.getPreDisburseRemarks().length() > 250) {
				errors.add("preDisburseRemarks", new ActionMessage("error.string.greaterthan", "0", "250"));
			}
		}

		if (StringUtils.isNotBlank(form.getDisbursementRemarks())) {
			if (form.getDisbursementRemarks().length() > 250) {
				errors.add("disbursementRemarks", new ActionMessage("error.string.greaterthan", "0", "250"));
			}
		}

		if (StringUtils.isNotBlank(form.getPostDisburseRemarks())) {
			if (form.getPostDisburseRemarks().length() > 250) {
				errors.add("postDisbursementRemarks", new ActionMessage("error.string.greaterthan", "0", "250"));
			}
		}

		return errors;
	}
}