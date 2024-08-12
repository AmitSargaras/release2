package com.integrosys.cms.ui.tatdoc;

import java.util.*;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrack;
import com.integrosys.cms.app.maintaintat.bus.OBTatLimitTrackStage;
import com.integrosys.cms.app.tatdoc.bus.*;
import com.integrosys.cms.app.tatduration.bus.OBTatParamItem;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue;

public class TatDocMapper extends AbstractCommonMapper {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "tatDocTrxValue", "com.integrosys.cms.app.tatdoc.trx.ITatDocTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE }, };
	}

	public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {
        TatDocForm tatForm = (TatDocForm) cForm;

        OBTatLimitTrack trackOB = new OBTatLimitTrack();
        trackOB.setTatTrackingId(tatForm.getTatTrackID() != null && tatForm.getTatTrackID().length() > 0 ? Long.parseLong(tatForm.getTatTrackID()) : ICMSConstant.LONG_INVALID_VALUE);
        trackOB.setLimitProfileId(tatForm.getLimitProfileID() != null && tatForm.getLimitProfileID().length() > 0 ? Long.parseLong(tatForm.getLimitProfileID()) : ICMSConstant.LONG_INVALID_VALUE);

        Set stageListSet = new LinkedHashSet();

        //Pre
        String preDisburseRemarks = tatForm.getPreDisburseRemarks();
        trackOB.setPreDisbursementRemarks(preDisburseRemarks != null && preDisburseRemarks.length() > 0 ? preDisburseRemarks : "");
        
        String[] preTrackingStageID = tatForm.getPreTrackingStageID();
        String[] preTatParamItemID = tatForm.getPreTatParamItemID();
        String[] preStartDateString = tatForm.getPreStartDateString();
        String[] preEndDateString = tatForm.getPreEndDateString();
        String[] preActualDateString = tatForm.getPreActualDateString();
        String[] preTatApplicable = tatForm.getPreTatApplicable();
        String[] preReason = tatForm.getPreReason();
        String[] preIsStageActive = tatForm.getPreIsStageActive();

        int length = preTrackingStageID != null ? preTrackingStageID.length : 0;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                OBTatLimitTrackStage trackStageOB = new OBTatLimitTrackStage();
                trackStageOB.setTatTrackingStageId(Long.parseLong(preTrackingStageID[i]));
                trackStageOB.setTatParamItemId(Long.parseLong(preTatParamItemID[i]));
                trackStageOB.setStartDate(preStartDateString[i] != null && preStartDateString[i].length() > 0 ? MaintainTatDurationUtil.getStringToDate(preStartDateString[i]) : null);
                trackStageOB.setEndDate(preEndDateString[i] != null && preEndDateString[i].length() > 0 ? MaintainTatDurationUtil.getStringToDate(preEndDateString[i]) : null);
                trackStageOB.setActualDate(preActualDateString[i] != null && preActualDateString[i].length() > 0 ? MaintainTatDurationUtil.getStringToDate(preActualDateString[i]) : null);
                trackStageOB.setTatApplicable(preTatApplicable[i]);
                trackStageOB.setReasonExceeding(preReason[i]);
                trackStageOB.setStageActive(preIsStageActive[i]);
                stageListSet.add(trackStageOB);
            }
        }

        //In
        String inDisburseRemarks = tatForm.getDisbursementRemarks();
        trackOB.setDisbursementRemarks(inDisburseRemarks != null && inDisburseRemarks.length() > 0 ? inDisburseRemarks : "");

        String[] inTrackingStageID = tatForm.getInTrackingStageID();
        String[] inTatParamItemID = tatForm.getInTatParamItemID();
        String[] inStartDateString = tatForm.getInStartDateString();
        String[] inEndDateString = tatForm.getInEndDateString();
        String[] inActualDateString = tatForm.getInActualDateString();
        String[] inTatApplicable = tatForm.getInTatApplicable();
        String[] inReason = tatForm.getInReason();
        String[] inIsStageActive = tatForm.getInIsStageActive();

        length = inTrackingStageID != null ? inTrackingStageID.length : 0;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                OBTatLimitTrackStage trackStageOB = new OBTatLimitTrackStage();
                trackStageOB.setTatTrackingStageId(Long.parseLong(inTrackingStageID[i]));
                trackStageOB.setTatParamItemId(Long.parseLong(inTatParamItemID[i]));
                trackStageOB.setStartDate(inStartDateString[i] != null && inStartDateString[i].length() > 0 ? MaintainTatDurationUtil.getStringToDate(inStartDateString[i]) : null);
                trackStageOB.setEndDate(inEndDateString[i] != null && inEndDateString[i].length() > 0 ? MaintainTatDurationUtil.getStringToDate(inEndDateString[i]) : null);
                trackStageOB.setActualDate(inActualDateString[i] != null && inActualDateString[i].length() > 0 ? MaintainTatDurationUtil.getStringToDate(inActualDateString[i]) : null);
                trackStageOB.setTatApplicable(inTatApplicable[i]);
                trackStageOB.setReasonExceeding(inReason[i]);
                trackStageOB.setStageActive(inIsStageActive[i]);
                stageListSet.add(trackStageOB);
            }
        }
        
        //Post
        String postDisburseRemarks = tatForm.getPostDisburseRemarks();
        trackOB.setPostDisbursementRemarks(postDisburseRemarks != null && postDisburseRemarks.length() > 0 ? postDisburseRemarks : "");

        String[] postTrackingStageID = tatForm.getPostTrackingStageID();
        String[] postTatParamItemID = tatForm.getPostTatParamItemID();
        String[] postStartDateString = tatForm.getPostStartDateString();
        String[] postEndDateString = tatForm.getPostEndDateString();
        String[] postActualDateString = tatForm.getPostActualDateString();
        String[] postTatApplicable = tatForm.getPostTatApplicable();
        String[] postReason = tatForm.getPostReason();
        String[] postIsStageActive = tatForm.getPostIsStageActive();

        length = postTrackingStageID != null ? postTrackingStageID.length : 0;
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                OBTatLimitTrackStage trackStageOB = new OBTatLimitTrackStage();
                trackStageOB.setTatTrackingStageId(Long.parseLong(postTrackingStageID[i]));
                trackStageOB.setTatParamItemId(Long.parseLong(postTatParamItemID[i]));
                trackStageOB.setStartDate(postStartDateString[i] != null && postStartDateString[i].length() > 0 ? MaintainTatDurationUtil.getStringToDate(postStartDateString[i]) : null);
                trackStageOB.setEndDate(postEndDateString[i] != null && postEndDateString[i].length() > 0 ? MaintainTatDurationUtil.getStringToDate(postEndDateString[i]) : null);
                trackStageOB.setActualDate(postActualDateString[i] != null && postActualDateString[i].length() > 0 ? MaintainTatDurationUtil.getStringToDate(postActualDateString[i]) : null);
                trackStageOB.setTatApplicable(postTatApplicable[i]);
                trackStageOB.setReasonExceeding(postReason[i]);
                trackStageOB.setStageActive(postIsStageActive[i]);
               stageListSet.add(trackStageOB);
            }
        }

        trackOB.setStageListSet(stageListSet);

        return trackOB;
//		ITatDoc tatDoc = null;
//		ITatDocTrxValue trxValue = (ITatDocTrxValue) inputs.get("tatDocTrxValue");
//		Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
//
//		if (trxValue != null) {
//			if (trxValue.getTatDoc() != null) {
//				tatDoc = new OBTatDoc();
//				AccessorUtil.copyValue(trxValue.getTatDoc(), tatDoc, new String[] { "TatDocID" });
//			}
//		}
//
//		TatDocForm form = (TatDocForm) cForm;
//		List listTatDocDraft = new ArrayList();
//
//		// set the value from form to array of object
//		int indexOfTatDocDraft = 0;
//		for (int i = 0; i < form.getDocumentReceivedDate().length; i++, indexOfTatDocDraft++) {
//			if (form.getDocumentReceivedDate()[i] != null && !"".equals(form.getDocumentReceivedDate()[i])) {
//				ITatDocDraft tatDocDraft = new OBTatDocDraft();
//				tatDocDraft.setDocDraftStage(ITatDocConstant.DOCUMENT_RECEIVED);
//				tatDocDraft.setDraftNumber((short) (i + 1));
//				tatDocDraft.setDraftDate((StringUtils.isNotBlank(form.getDocumentReceivedDate()[i])) ? DateUtil
//						.convertDate(locale, form.getDocumentReceivedDate()[i]) : null);
//				listTatDocDraft.add(tatDocDraft);
//			}
//		}
//
//		for (int i = 0; i < form.getDocSentToLegalDeptDate().length; i++, indexOfTatDocDraft++) {
//			if (form.getDocSentToLegalDeptDate()[i] != null && !"".equals(form.getDocSentToLegalDeptDate()[i])) {
//				ITatDocDraft tatDocDraft = new OBTatDocDraft();
//				tatDocDraft.setDocDraftStage(ITatDocConstant.DOC_SENT_TO_LEGAL_DEPT);
//				tatDocDraft.setDraftNumber((short) (i + 1));
//				tatDocDraft.setDraftDate((StringUtils.isNotBlank(form.getDocSentToLegalDeptDate()[i])) ? DateUtil
//						.convertDate(locale, form.getDocSentToLegalDeptDate()[i]) : null);
//				listTatDocDraft.add(tatDocDraft);
//			}
//		}
//
//		for (int i = 0; i < form.getDocReceivedFromLegalDeptDate().length; i++, indexOfTatDocDraft++) {
//			if (form.getDocReceivedFromLegalDeptDate()[i] != null
//					&& !"".equals(form.getDocReceivedFromLegalDeptDate()[i])) {
//				ITatDocDraft tatDocDraft = new OBTatDocDraft();
//				tatDocDraft.setDocDraftStage(ITatDocConstant.DOC_RECEIVED_FROM_LEGAL_DEPT);
//				tatDocDraft.setDraftNumber((short) (i + 1));
//				tatDocDraft.setDraftDate((StringUtils.isNotBlank(form.getDocReceivedFromLegalDeptDate()[i])) ? DateUtil
//						.convertDate(locale, form.getDocReceivedFromLegalDeptDate()[i]) : null);
//				listTatDocDraft.add(tatDocDraft);
//			}
//		}
//
//		for (int i = 0; i < form.getDocumentReturnedDate().length; i++, indexOfTatDocDraft++) {
//			if (form.getDocumentReturnedDate()[i] != null && !"".equals(form.getDocumentReturnedDate()[i])) {
//				ITatDocDraft tatDocDraft = new OBTatDocDraft();
//				tatDocDraft.setDocDraftStage(ITatDocConstant.DOCUMENT_RETURNED);
//				tatDocDraft.setDraftNumber((short) (i + 1));
//				tatDocDraft.setDraftDate((StringUtils.isNotBlank(form.getDocumentReturnedDate()[i])) ? DateUtil
//						.convertDate(locale, form.getDocumentReturnedDate()[i]) : null);
//				listTatDocDraft.add(tatDocDraft);
//			}
//		}
//
//		/**
//		 * <p>
//		 * comparing the data inside listTatDocDraft with the actual data
//		 *
//		 * <p>
//		 * if tatdoc == null then create new object directly from the
//		 * listTatDocDraft
//		 *
//		 * <p>
//		 * if tatdoc != null then copy the value from the actual object to the
//		 * listTatDocDraft then create new object based from the copied value
//		 * from the listTatDoc
//		 */
//		ITatDocDraft[] newTatDocDraft = null;
//		ITatDocDraft[] existingTatDocDraft = null;
//
//		if (tatDoc != null) {
//			existingTatDocDraft = tatDoc.getDraftList();
//
//			for (int i = 0; i < listTatDocDraft.size(); i++) {
//				ITatDocDraft tatDocDraft = (ITatDocDraft) listTatDocDraft.get(i);
//				for (int j = 0; j < existingTatDocDraft.length; j++) {
//					if (tatDocDraft.getDocDraftStage().equals(existingTatDocDraft[j].getDocDraftStage())
//							&& tatDocDraft.getDraftNumber() == existingTatDocDraft[j].getDraftNumber()) {
//						AccessorUtil.copyValue(existingTatDocDraft[j], ((ITatDocDraft) listTatDocDraft.get(i)),
//								new String[] { "DraftID", "DraftDate" });
//						break;
//					}
//				}
//			}
//		}
//
//		newTatDocDraft = new OBTatDocDraft[listTatDocDraft.size()];
//		for (int i = 0; i < listTatDocDraft.size(); i++) {
//			newTatDocDraft[i] = new OBTatDocDraft();
//			newTatDocDraft[i] = (ITatDocDraft) listTatDocDraft.get(i);
//		}
//
//		// set the value from form to object
//		// tatDoc
//		if (tatDoc == null) {
//			tatDoc = new OBTatDoc();
//		}
//		tatDoc.setLimitProfileID(Long.parseLong(form.getLimitProfileID()));
//		// pre disb
//		tatDoc.setSolicitorInstructionDate((StringUtils.isNotBlank(form.getSolicitorInstructionDate())) ? DateUtil
//				.convertDate(locale, form.getSolicitorInstructionDate()) : null);
//		tatDoc.setFileReceivedFromBizCenter((StringUtils.isNotBlank(form.getFileFromBizCenterDate())) ? DateUtil
//				.convertDate(locale, form.getFileFromBizCenterDate()) : null);
//
//		tatDoc.setDraftList(newTatDocDraft);
//
//		tatDoc.setIsPAOrSolInvolvementReq(form.getIsPAOrSolInvolvementReq());
//		tatDoc.setDocReceviceForPADate((StringUtils.isNotBlank(form.getDocReadyForPADate())) ? DateUtil.convertDate(
//				locale, form.getDocReadyForPADate()) : null);
//		tatDoc.setDocPAExcuteDate((StringUtils.isNotBlank(form.getPAExecutedDate())) ? DateUtil.convertDate(locale,
//				form.getPAExecutedDate()) : null);
//		tatDoc.setPreDisbursementRemarks(form.getPreDisburseRemarks());
//
//		// disb
//		tatDoc.setSolicitorAdviseReleaseDate((StringUtils.isNotBlank(form.getSolicitorAdviseReleaseDate())) ? DateUtil
//				.convertDate(locale, form.getSolicitorAdviseReleaseDate()) : null);
//		tatDoc
//				.setDisbursementDocCompletionDate((StringUtils.isNotBlank(form.getDisbursementDocCompletedDate())) ? DateUtil
//						.convertDate(locale, form.getDisbursementDocCompletedDate())
//						: null);
//		tatDoc.setDisbursementDate((StringUtils.isNotBlank(form.getDisbursementDate())) ? DateUtil.convertDate(locale,
//				form.getDisbursementDate()) : null);
//		tatDoc.setDisbursementRemarks(form.getDisbursementRemarks());
//
//		// post disb
//		tatDoc.setDocCompletionDate((StringUtils.isNotBlank(form.getDocCompletionDate())) ? DateUtil.convertDate(
//				locale, form.getDocCompletionDate()) : null);
//		tatDoc.setPostDisbursementRemarks(form.getPostDisbursementRemarks());
//
//		return tatDoc;
	}

	public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
		TatDocForm form = (TatDocForm) cForm;
		Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        OBTatLimitTrack trackOB = (OBTatLimitTrack) obj;

        form.setTatTrackID(trackOB.getTatTrackingId() + "");
        form.setLimitProfileID(trackOB.getLimitProfileId() + "");
        form.setPreDisburseRemarks(trackOB.getPreDisbursementRemarks() != null && trackOB.getPreDisbursementRemarks().length() > 0 ? trackOB.getPreDisbursementRemarks() : "");
        form.setDisbursementRemarks(trackOB.getDisbursementRemarks() != null && trackOB.getDisbursementRemarks().length() > 0 ? trackOB.getDisbursementRemarks() : "");
        form.setPostDisburseRemarks(trackOB.getPostDisbursementRemarks() != null && trackOB.getPostDisbursementRemarks().length() > 0 ? trackOB.getPostDisbursementRemarks() : "");

        int numberStage = trackOB.getStageListSet() != null && trackOB.getStageListSet().size() > 0 ? trackOB.getStageListSet().size() : 0;

        String[] preTrackingStageID = new String[numberStage];
        String[] preTatParamItemID = new String[numberStage];
        String[] preStartDateString = new String[numberStage];
        String[] preEndDateString = new String[numberStage];
        String[] preActualDateString = new String[numberStage];
        String[] preTatApplicable = new String[numberStage];
        String[] preReason = new String[numberStage];
        String[] preIsStageActive = new String[numberStage];

        String[] inTrackingStageID = new String[numberStage];
        String[] inTatParamItemID = new String[numberStage];
        String[] inStartDateString = new String[numberStage];
        String[] inEndDateString = new String[numberStage];
        String[] inActualDateString = new String[numberStage];
        String[] inTatApplicable = new String[numberStage];
        String[] inReason = new String[numberStage];
        String[] inIsStageActive = new String[numberStage];

        String[] postTrackingStageID = new String[numberStage];
        String[] postTatParamItemID = new String[numberStage];
        String[] postStartDateString = new String[numberStage];
        String[] postEndDateString = new String[numberStage];
        String[] postActualDateString = new String[numberStage];
        String[] postTatApplicable = new String[numberStage];
        String[] postReason = new String[numberStage];
        String[] postIsStageActive = new String[numberStage];

        if (numberStage > 0) {
            int i = 0;
            for (Iterator itr = trackOB.getStageListSet().iterator(); itr.hasNext();) {
                OBTatLimitTrackStage trackStageOB = (OBTatLimitTrackStage) itr.next();
                OBTatParamItem item = trackStageOB.getTatParamItem();

                if (item.getStageType().equals("1")) {
                    //PRE

                    preTrackingStageID[i] = trackStageOB.getTatTrackingStageId() + "";
                    preTatParamItemID[i] = trackStageOB.getTatParamItemId() + "";
                    preStartDateString[i] = trackStageOB.getStartDate() != null ? MaintainTatDurationUtil.getFormattedDate(trackStageOB.getStartDate()) : "";
                    preEndDateString[i] = trackStageOB.getEndDate() != null ? MaintainTatDurationUtil.getFormattedDate(trackStageOB.getEndDate()) : "";
                    preActualDateString[i] = trackStageOB.getActualDate() != null ? MaintainTatDurationUtil.getFormattedDate(trackStageOB.getActualDate()) : "";
                    preTatApplicable[i] = trackStageOB.getTatApplicable();
                    preReason[i] = trackStageOB.getReasonExceeding();
                    preIsStageActive[i] = String.valueOf(trackStageOB.isStageActive());
                } else if (item.getStageType().equals("2")) {
                    //IN

                    inTrackingStageID[i] = trackStageOB.getTatTrackingStageId() + "";
                    inTatParamItemID[i] = trackStageOB.getTatParamItemId() + "";
                    inStartDateString[i] = trackStageOB.getStartDate() != null ? MaintainTatDurationUtil.getFormattedDate(trackStageOB.getStartDate()) : "";
                    inEndDateString[i] = trackStageOB.getEndDate() != null ? MaintainTatDurationUtil.getFormattedDate(trackStageOB.getEndDate()) : "";
                    inActualDateString[i] = trackStageOB.getActualDate() != null ? MaintainTatDurationUtil.getFormattedDate(trackStageOB.getActualDate()) : "";
                    inTatApplicable[i] = trackStageOB.getTatApplicable();
                    inReason[i] = trackStageOB.getReasonExceeding();
                    inIsStageActive[i] = String.valueOf(trackStageOB.isStageActive());
                } else {
                    //POST

                    postTrackingStageID[i] = trackStageOB.getTatTrackingStageId() + "";
                    postTatParamItemID[i] = trackStageOB.getTatParamItemId() + "";
                    postStartDateString[i] = trackStageOB.getStartDate() != null ? MaintainTatDurationUtil.getFormattedDate(trackStageOB.getStartDate()) : "";
                    postEndDateString[i] = trackStageOB.getEndDate() != null ? MaintainTatDurationUtil.getFormattedDate(trackStageOB.getEndDate()) : "";
                    postActualDateString[i] = trackStageOB.getActualDate() != null ? MaintainTatDurationUtil.getFormattedDate(trackStageOB.getActualDate()) : "";
                    postTatApplicable[i] = trackStageOB.getTatApplicable();
                    postReason[i] = trackStageOB.getReasonExceeding();
                    postIsStageActive[i] = String.valueOf(trackStageOB.isStageActive());
                }

                i++;
            }
        }

//		// create the array with correct size
//		String[] docReceived = new String[5];
//		String[] docSentToLegalDept = new String[5];
//		String[] docReceivedFromLegalDept = new String[5];
//		String[] docReturned = new String[5];
//
//		form.setDocumentReceivedDate(docReceived);
//		form.setDocSentToLegalDeptDate(docSentToLegalDept);
//		form.setDocReceivedFromLegalDeptDate(docReceivedFromLegalDept);
//		form.setDocumentReturnedDate(docReturned);
//
//		ITatDoc tatDoc = (ITatDoc)obj;
//		if (tatDoc != null) {
//
//			/*String status = trxValue.getStatus();
//			if (((ICMSConstant.STATE_REJECTED.equals(status) && !(TatDocAction.EVENT_VIEW.equals(event)
//					|| TatDocAction.EVENT_READ_CLOSE.equals(event) || TatDocAction.EVENT_READ_EDIT_TODO.equals(event)))
//					|| ICMSConstant.STATE_CLOSED.equals(status)
//					|| (ICMSConstant.STATE_DRAFT.equals(status) && TatDocAction.EVENT_READ_EDIT.equals(event))
//					|| (ICMSConstant.STATE_PENDING_UPDATE.equals(status) && !(TatDocAction.EVENT_VIEW.equals(event) || TatDocAction.EVENT_CHECKER_READ
//							.equals(event))) || (ICMSConstant.STATE_PENDING_CREATE.equals(status) && !(TatDocAction.EVENT_VIEW
//					.equals(event) || TatDocAction.EVENT_CHECKER_READ.equals(event))))
//					&& !TatDocAction.EVENT_VIEW_CHECKER.equals(event)) {
//				//return form;
//			}*/
//
//			// DefaultLogger.debug(this, ">>>>>>>>>> formating date");
//			// DefaultLogger.debug(this, ">>>>>>>>>> tatDoc.getDraftList()" +
//			// (tatDoc.getDraftList())[0].getDraftDate());
//			// DefaultLogger.debug(this, ">>>>>>>>> format date = " +
//			// DateUtil.formatDate(locale,
//			// (tatDoc.getDraftList())[0].getDraftDate()));
//
//			// input array with correct value
//			for (int i = 0; i < tatDoc.getDraftList().length; i++) {
//				if (ITatDocConstant.DOCUMENT_RECEIVED.equals((tatDoc.getDraftList())[i].getDocDraftStage())) {
//					DefaultLogger.debug(this, ">>>>>>>>> doc receive index: "
//							+ ((tatDoc.getDraftList())[i].getDraftNumber() - 1));
//					DefaultLogger.debug(this, ">>>>>>>>> draft date = " + (tatDoc.getDraftList())[i].getDraftDate());
//					docReceived[(tatDoc.getDraftList())[i].getDraftNumber() - 1] = ((tatDoc.getDraftList())[i]
//							.getDraftDate() != null && !"".equals((tatDoc.getDraftList())[i].getDraftDate())) ? DateUtil
//							.formatDate(locale, (tatDoc.getDraftList())[i].getDraftDate())
//							: null;
//				}
//				else if (ITatDocConstant.DOC_SENT_TO_LEGAL_DEPT.equals((tatDoc.getDraftList())[i].getDocDraftStage())) {
//					docSentToLegalDept[(tatDoc.getDraftList())[i].getDraftNumber() - 1] = ((tatDoc.getDraftList())[i]
//							.getDraftDate() != null && !"".equals((tatDoc.getDraftList())[i].getDraftDate())) ? DateUtil
//							.formatDate(locale, (tatDoc.getDraftList())[i].getDraftDate())
//							: null;
//				}
//				else if (ITatDocConstant.DOC_RECEIVED_FROM_LEGAL_DEPT.equals((tatDoc.getDraftList())[i]
//						.getDocDraftStage())) {
//					docReceivedFromLegalDept[(tatDoc.getDraftList())[i].getDraftNumber() - 1] = ((tatDoc.getDraftList())[i]
//							.getDraftDate() != null && !"".equals((tatDoc.getDraftList())[i].getDraftDate())) ? DateUtil
//							.formatDate(locale, (tatDoc.getDraftList())[i].getDraftDate())
//							: null;
//				}
//				else if (ITatDocConstant.DOCUMENT_RETURNED.equals((tatDoc.getDraftList())[i].getDocDraftStage())) {
//					docReturned[(tatDoc.getDraftList())[i].getDraftNumber() - 1] = ((tatDoc.getDraftList())[i]
//							.getDraftDate() != null && !"".equals((tatDoc.getDraftList())[i].getDraftDate())) ? DateUtil
//							.formatDate(locale, (tatDoc.getDraftList())[i].getDraftDate())
//							: null;
//				}
//			}
//
//			// set values to form
//			// pre disb
//
//			form.setIsPAOrSolInvolvementReq(tatDoc.getIsPAOrSolInvolvementReq());
//			form.setSolicitorInstructionDate((tatDoc.getSolicitorInstructionDate() != null && !"".equals(tatDoc
//					.getSolicitorInstructionDate())) ? DateUtil
//					.formatDate(locale, tatDoc.getSolicitorInstructionDate()) : null);
//			form.setFileFromBizCenterDate((tatDoc.getFileReceivedFromBizCenter() != null && !"".equals(tatDoc
//					.getFileReceivedFromBizCenter())) ? DateUtil.formatDate(locale, tatDoc
//					.getFileReceivedFromBizCenter()) : null);
//
//			form
//					.setDocReadyForPADate((tatDoc.getDocReceviceForPADate() != null && !"".equals(tatDoc
//							.getDocReceviceForPADate())) ? DateUtil
//							.formatDate(locale, tatDoc.getDocReceviceForPADate()) : null);
//			form
//					.setPAExecutedDate((tatDoc.getDocPAExcuteDate() != null && !"".equals(tatDoc.getDocPAExcuteDate())) ? DateUtil
//							.formatDate(locale, tatDoc.getDocPAExcuteDate())
//							: null);
//			form.setPreDisburseRemarks(tatDoc.getPreDisbursementRemarks());
//
//			// disb
//			form.setSolicitorAdviseReleaseDate((tatDoc.getSolicitorAdviseReleaseDate() != null && !"".equals(tatDoc
//					.getSolicitorAdviseReleaseDate())) ? DateUtil.formatDate(locale, tatDoc
//					.getSolicitorAdviseReleaseDate()) : null);
//			form.setDisbursementDocCompletedDate((tatDoc.getDisbursementDocCompletionDate() != null && !""
//					.equals(tatDoc.getDisbursementDocCompletionDate())) ? DateUtil.formatDate(locale, tatDoc
//					.getDisbursementDocCompletionDate()) : null);
//			form
//					.setDisbursementDate((tatDoc.getDisbursementDate() != null && !"".equals(tatDoc
//							.getDisbursementDate())) ? DateUtil.formatDate(locale, tatDoc.getDisbursementDate()) : null);
//			form.setDisbursementRemarks(tatDoc.getDisbursementRemarks());
//
//			// post disb
//			form.setDocCompletionDate((tatDoc.getDocCompletionDate() != null && !"".equals(tatDoc
//					.getDocCompletionDate())) ? DateUtil.formatDate(locale, tatDoc.getDocCompletionDate()) : null);
//			form.setPostDisbursementRemarks(tatDoc.getPostDisbursementRemarks());
//		}
		return form;
	}
}
