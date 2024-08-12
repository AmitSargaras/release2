package com.integrosys.cms.ui.tatduration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.tatdoc.bus.ITatDocConstant;
import com.integrosys.cms.app.tatdoc.bus.ITatDocDraft;
import com.integrosys.cms.app.tatdoc.bus.OBTatDoc;
import com.integrosys.cms.app.tatdoc.bus.OBTatDocDraft;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;

public class TatDurationMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() 
	{
		return new String[][] 
		         {
				{ "tatParamTrxValue", "com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, 
				};
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
		/*ITatDoc tatDoc = null;
		ITatDocTrxValue trxValue = (ITatDocTrxValue) inputs.get("tatDocTrxValue");
		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		if (trxValue != null) {
			if (trxValue.getTatDoc() != null) {
				tatDoc = new OBTatDoc();
				AccessorUtil.copyValue(trxValue.getTatDoc(), tatDoc, new String[] { "TatDocID" });
			}
		}

		TatDocForm form = (TatDocForm) cForm;
		List listTatDocDraft = new ArrayList();

		// set the value from form to array of object
		int indexOfTatDocDraft = 0;
		for (int i = 0; i < form.getDocumentReceivedDate().length; i++, indexOfTatDocDraft++) {
			if (form.getDocumentReceivedDate()[i] != null && !"".equals(form.getDocumentReceivedDate()[i])) {
				ITatDocDraft tatDocDraft = new OBTatDocDraft();
				tatDocDraft.setDocDraftStage(ITatDocConstant.DOCUMENT_RECEIVED);
				tatDocDraft.setDraftNumber((short) (i + 1));
				tatDocDraft.setDraftDate((StringUtils.isNotBlank(form.getDocumentReceivedDate()[i])) ? DateUtil
						.convertDate(locale, form.getDocumentReceivedDate()[i]) : null);
				listTatDocDraft.add(tatDocDraft);
			}
		}

		for (int i = 0; i < form.getDocSentToLegalDeptDate().length; i++, indexOfTatDocDraft++) {
			if (form.getDocSentToLegalDeptDate()[i] != null && !"".equals(form.getDocSentToLegalDeptDate()[i])) {
				ITatDocDraft tatDocDraft = new OBTatDocDraft();
				tatDocDraft.setDocDraftStage(ITatDocConstant.DOC_SENT_TO_LEGAL_DEPT);
				tatDocDraft.setDraftNumber((short) (i + 1));
				tatDocDraft.setDraftDate((StringUtils.isNotBlank(form.getDocSentToLegalDeptDate()[i])) ? DateUtil
						.convertDate(locale, form.getDocSentToLegalDeptDate()[i]) : null);
				listTatDocDraft.add(tatDocDraft);
			}
		}

		for (int i = 0; i < form.getDocReceivedFromLegalDeptDate().length; i++, indexOfTatDocDraft++) {
			if (form.getDocReceivedFromLegalDeptDate()[i] != null
					&& !"".equals(form.getDocReceivedFromLegalDeptDate()[i])) {
				ITatDocDraft tatDocDraft = new OBTatDocDraft();
				tatDocDraft.setDocDraftStage(ITatDocConstant.DOC_RECEIVED_FROM_LEGAL_DEPT);
				tatDocDraft.setDraftNumber((short) (i + 1));
				tatDocDraft.setDraftDate((StringUtils.isNotBlank(form.getDocReceivedFromLegalDeptDate()[i])) ? DateUtil
						.convertDate(locale, form.getDocReceivedFromLegalDeptDate()[i]) : null);
				listTatDocDraft.add(tatDocDraft);
			}
		}

		for (int i = 0; i < form.getDocumentReturnedDate().length; i++, indexOfTatDocDraft++) {
			if (form.getDocumentReturnedDate()[i] != null && !"".equals(form.getDocumentReturnedDate()[i])) {
				ITatDocDraft tatDocDraft = new OBTatDocDraft();
				tatDocDraft.setDocDraftStage(ITatDocConstant.DOCUMENT_RETURNED);
				tatDocDraft.setDraftNumber((short) (i + 1));
				tatDocDraft.setDraftDate((StringUtils.isNotBlank(form.getDocumentReturnedDate()[i])) ? DateUtil
						.convertDate(locale, form.getDocumentReturnedDate()[i]) : null);
				listTatDocDraft.add(tatDocDraft);
			}
		}

		*//**
		 * <p>
		 * comparing the data inside listTatDocDraft with the actual data
		 * 
		 * <p>
		 * if tatdoc == null then create new object directly from the
		 * listTatDocDraft
		 * 
		 * <p>
		 * if tatdoc != null then copy the value from the actual object to the
		 * listTatDocDraft then create new object based from the copied value
		 * from the listTatDoc
		 *//*
		ITatDocDraft[] newTatDocDraft = null;
		ITatDocDraft[] existingTatDocDraft = null;

		if (tatDoc != null) {
			existingTatDocDraft = tatDoc.getDraftList();

			for (int i = 0; i < listTatDocDraft.size(); i++) {
				ITatDocDraft tatDocDraft = (ITatDocDraft) listTatDocDraft.get(i);
				for (int j = 0; j < existingTatDocDraft.length; j++) {
					if (tatDocDraft.getDocDraftStage().equals(existingTatDocDraft[j].getDocDraftStage())
							&& tatDocDraft.getDraftNumber() == existingTatDocDraft[j].getDraftNumber()) {
						AccessorUtil.copyValue(existingTatDocDraft[j], ((ITatDocDraft) listTatDocDraft.get(i)),
								new String[] { "DraftID", "DraftDate" });
						break;
					}
				}
			}
		}

		newTatDocDraft = new OBTatDocDraft[listTatDocDraft.size()];
		for (int i = 0; i < listTatDocDraft.size(); i++) {
			newTatDocDraft[i] = new OBTatDocDraft();
			newTatDocDraft[i] = (ITatDocDraft) listTatDocDraft.get(i);
		}

		// set the value from form to object
		// tatDoc
		if (tatDoc == null) {
			tatDoc = new OBTatDoc();
		}
		tatDoc.setLimitProfileID(Long.parseLong(form.getLimitProfileID()));
		// pre disb
		tatDoc.setSolicitorInstructionDate((StringUtils.isNotBlank(form.getSolicitorInstructionDate())) ? DateUtil
				.convertDate(locale, form.getSolicitorInstructionDate()) : null);
		tatDoc.setFileReceivedFromBizCenter((StringUtils.isNotBlank(form.getFileFromBizCenterDate())) ? DateUtil
				.convertDate(locale, form.getFileFromBizCenterDate()) : null);

		tatDoc.setDraftList(newTatDocDraft);
		
		tatDoc.setIsPAOrSolInvolvementReq(form.getIsPAOrSolInvolvementReq());
		tatDoc.setDocReceviceForPADate((StringUtils.isNotBlank(form.getDocReadyForPADate())) ? DateUtil.convertDate(
				locale, form.getDocReadyForPADate()) : null);
		tatDoc.setDocPAExcuteDate((StringUtils.isNotBlank(form.getPAExecutedDate())) ? DateUtil.convertDate(locale,
				form.getPAExecutedDate()) : null);
		tatDoc.setPreDisbursementRemarks(form.getPreDisburseRemarks());

		// disb
		tatDoc.setSolicitorAdviseReleaseDate((StringUtils.isNotBlank(form.getSolicitorAdviseReleaseDate())) ? DateUtil
				.convertDate(locale, form.getSolicitorAdviseReleaseDate()) : null);
		tatDoc
				.setDisbursementDocCompletionDate((StringUtils.isNotBlank(form.getDisbursementDocCompletedDate())) ? DateUtil
						.convertDate(locale, form.getDisbursementDocCompletedDate())
						: null);
		tatDoc.setDisbursementDate((StringUtils.isNotBlank(form.getDisbursementDate())) ? DateUtil.convertDate(locale,
				form.getDisbursementDate()) : null);
		tatDoc.setDisbursementRemarks(form.getDisbursementRemarks());

		// post disb
		tatDoc.setDocCompletionDate((StringUtils.isNotBlank(form.getDocCompletionDate())) ? DateUtil.convertDate(
				locale, form.getDocCompletionDate()) : null);
		tatDoc.setPostDisbursementRemarks(form.getPostDisbursementRemarks());

		return tatDoc;*/
		
		return null;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		/*TatDocForm form = (TatDocForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		// create the array with correct size
		String[] docReceived = new String[5];
		String[] docSentToLegalDept = new String[5];
		String[] docReceivedFromLegalDept = new String[5];
		String[] docReturned = new String[5];

		form.setDocumentReceivedDate(docReceived);
		form.setDocSentToLegalDeptDate(docSentToLegalDept);
		form.setDocReceivedFromLegalDeptDate(docReceivedFromLegalDept);
		form.setDocumentReturnedDate(docReturned);

		ITatDoc tatDoc = (ITatDoc)obj;
		if (tatDoc != null) {

			String status = trxValue.getStatus();
			if (((ICMSConstant.STATE_REJECTED.equals(status) && !(TatDocAction.EVENT_VIEW.equals(event)
					|| TatDocAction.EVENT_READ_CLOSE.equals(event) || TatDocAction.EVENT_READ_EDIT_TODO.equals(event)))
					|| ICMSConstant.STATE_CLOSED.equals(status)
					|| (ICMSConstant.STATE_DRAFT.equals(status) && TatDocAction.EVENT_READ_EDIT.equals(event))
					|| (ICMSConstant.STATE_PENDING_UPDATE.equals(status) && !(TatDocAction.EVENT_VIEW.equals(event) || TatDocAction.EVENT_CHECKER_READ
							.equals(event))) || (ICMSConstant.STATE_PENDING_CREATE.equals(status) && !(TatDocAction.EVENT_VIEW
					.equals(event) || TatDocAction.EVENT_CHECKER_READ.equals(event))))
					&& !TatDocAction.EVENT_VIEW_CHECKER.equals(event)) {
				//return form;
			}

			// DefaultLogger.debug(this, ">>>>>>>>>> formating date");
			// DefaultLogger.debug(this, ">>>>>>>>>> tatDoc.getDraftList()" +
			// (tatDoc.getDraftList())[0].getDraftDate());
			// DefaultLogger.debug(this, ">>>>>>>>> format date = " +
			// DateUtil.formatDate(locale,
			// (tatDoc.getDraftList())[0].getDraftDate()));

			// input array with correct value
			for (int i = 0; i < tatDoc.getDraftList().length; i++) {
				if (ITatDocConstant.DOCUMENT_RECEIVED.equals((tatDoc.getDraftList())[i].getDocDraftStage())) {
					DefaultLogger.debug(this, ">>>>>>>>> doc receive index: "
							+ ((tatDoc.getDraftList())[i].getDraftNumber() - 1));
					DefaultLogger.debug(this, ">>>>>>>>> draft date = " + (tatDoc.getDraftList())[i].getDraftDate());
					docReceived[(tatDoc.getDraftList())[i].getDraftNumber() - 1] = ((tatDoc.getDraftList())[i]
							.getDraftDate() != null && !"".equals((tatDoc.getDraftList())[i].getDraftDate())) ? DateUtil
							.formatDate(locale, (tatDoc.getDraftList())[i].getDraftDate())
							: null;
				}
				else if (ITatDocConstant.DOC_SENT_TO_LEGAL_DEPT.equals((tatDoc.getDraftList())[i].getDocDraftStage())) {
					docSentToLegalDept[(tatDoc.getDraftList())[i].getDraftNumber() - 1] = ((tatDoc.getDraftList())[i]
							.getDraftDate() != null && !"".equals((tatDoc.getDraftList())[i].getDraftDate())) ? DateUtil
							.formatDate(locale, (tatDoc.getDraftList())[i].getDraftDate())
							: null;
				}
				else if (ITatDocConstant.DOC_RECEIVED_FROM_LEGAL_DEPT.equals((tatDoc.getDraftList())[i]
						.getDocDraftStage())) {
					docReceivedFromLegalDept[(tatDoc.getDraftList())[i].getDraftNumber() - 1] = ((tatDoc.getDraftList())[i]
							.getDraftDate() != null && !"".equals((tatDoc.getDraftList())[i].getDraftDate())) ? DateUtil
							.formatDate(locale, (tatDoc.getDraftList())[i].getDraftDate())
							: null;
				}
				else if (ITatDocConstant.DOCUMENT_RETURNED.equals((tatDoc.getDraftList())[i].getDocDraftStage())) {
					docReturned[(tatDoc.getDraftList())[i].getDraftNumber() - 1] = ((tatDoc.getDraftList())[i]
							.getDraftDate() != null && !"".equals((tatDoc.getDraftList())[i].getDraftDate())) ? DateUtil
							.formatDate(locale, (tatDoc.getDraftList())[i].getDraftDate())
							: null;
				}
			}

			// set values to form
			// pre disb
			
			form.setIsPAOrSolInvolvementReq(tatDoc.getIsPAOrSolInvolvementReq());
			form.setSolicitorInstructionDate((tatDoc.getSolicitorInstructionDate() != null && !"".equals(tatDoc
					.getSolicitorInstructionDate())) ? DateUtil
					.formatDate(locale, tatDoc.getSolicitorInstructionDate()) : null);
			form.setFileFromBizCenterDate((tatDoc.getFileReceivedFromBizCenter() != null && !"".equals(tatDoc
					.getFileReceivedFromBizCenter())) ? DateUtil.formatDate(locale, tatDoc
					.getFileReceivedFromBizCenter()) : null);

			form
					.setDocReadyForPADate((tatDoc.getDocReceviceForPADate() != null && !"".equals(tatDoc
							.getDocReceviceForPADate())) ? DateUtil
							.formatDate(locale, tatDoc.getDocReceviceForPADate()) : null);
			form
					.setPAExecutedDate((tatDoc.getDocPAExcuteDate() != null && !"".equals(tatDoc.getDocPAExcuteDate())) ? DateUtil
							.formatDate(locale, tatDoc.getDocPAExcuteDate())
							: null);
			form.setPreDisburseRemarks(tatDoc.getPreDisbursementRemarks());

			// disb
			form.setSolicitorAdviseReleaseDate((tatDoc.getSolicitorAdviseReleaseDate() != null && !"".equals(tatDoc
					.getSolicitorAdviseReleaseDate())) ? DateUtil.formatDate(locale, tatDoc
					.getSolicitorAdviseReleaseDate()) : null);
			form.setDisbursementDocCompletedDate((tatDoc.getDisbursementDocCompletionDate() != null && !""
					.equals(tatDoc.getDisbursementDocCompletionDate())) ? DateUtil.formatDate(locale, tatDoc
					.getDisbursementDocCompletionDate()) : null);
			form
					.setDisbursementDate((tatDoc.getDisbursementDate() != null && !"".equals(tatDoc
							.getDisbursementDate())) ? DateUtil.formatDate(locale, tatDoc.getDisbursementDate()) : null);
			form.setDisbursementRemarks(tatDoc.getDisbursementRemarks());

			// post disb
			form.setDocCompletionDate((tatDoc.getDocCompletionDate() != null && !"".equals(tatDoc
					.getDocCompletionDate())) ? DateUtil.formatDate(locale, tatDoc.getDocCompletionDate()) : null);
			form.setPostDisbursementRemarks(tatDoc.getPostDisbursementRemarks());
		}
		return form;*/
		
		return null;
	}
}
