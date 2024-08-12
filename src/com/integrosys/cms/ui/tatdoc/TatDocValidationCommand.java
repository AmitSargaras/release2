package com.integrosys.cms.ui.tatdoc;

import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListSummary;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * <p>
 * Web command to validate the TAT document on business rules basis.
 * <p>
 * Currently is doing validation on document completion date (at both
 * disbursement and post disbursement section)
 * @author Chong Jun Yong
 * 
 */
public class TatDocValidationCommand extends TatDocCommand {

	private final Logger logger = LoggerFactory.getLogger(TatDocValidationCommand.class);

	private ICheckListProxyManager checkListProxyManager;

	private String[] validDocumentStatusForDisbursementCompletionDate;

	private String[] validDocumentStatusForPostDisbursementCompletionDate;

	private boolean isFilterPreApprovalDocuments;

	public void setCheckListProxyManager(ICheckListProxyManager checkListProxyManager) {
		this.checkListProxyManager = checkListProxyManager;
	}

	/**
	 * Valid document status for disbursement completion date can be filled in.
	 * @param validDocumentStatusForDisbursementCompletionDate document status
	 *        array
	 */
	public void setValidDocumentStatusForDisbursementCompletionDate(
			String[] validDocumentStatusForDisbursementCompletionDate) {
		this.validDocumentStatusForDisbursementCompletionDate = validDocumentStatusForDisbursementCompletionDate;
	}

	/**
	 * Valid document status for <b>post</b> disbursement completion date can be
	 * filled in.
	 * @param validDocumentStatusForPostDisbursementCompletionDate document
	 *        status array
	 */
	public void setValidDocumentStatusForPostDisbursementCompletionDate(
			String[] validDocumentStatusForPostDisbursementCompletionDate) {
		this.validDocumentStatusForPostDisbursementCompletionDate = validDocumentStatusForPostDisbursementCompletionDate;
	}

	/**
	 * Whether not to validate pre approval documents.
	 * @param isFilterPreApprovalDocuments to indicate whether <b>not</b> to
	 *        validate pre approval documents
	 */
	public void setIsFilterPreApprovalDocuments(boolean isFilterPreApprovalDocuments) {
		this.isFilterPreApprovalDocuments = isFilterPreApprovalDocuments;
	}

	public String[][] getParameterDescriptor() {
		return new String[][] { { TatDocForm.MAPPER, "com.integrosys.cms.app.tatdoc.bus.OBTatDoc", FORM_SCOPE },
				{ "previousDisbursementCompletionDate", "java.lang.String", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		ITatDoc tatDoc = (ITatDoc) map.get(TatDocForm.MAPPER);
		ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");
		String previousDisbursementCompletionDate = (String) map.get("previousDisbursementCompletionDate");

		if (trxContext.getLimitProfile() == null) {
			throw new AccessDeniedException("There is no AA to be processed for TAT Document validation.");
		}

		ICheckListSummary[] ccCheckListSummaries = null;
		ICheckListSummary[] colCheckListSummaries = null;
		try {
			HashMap ccMap = this.checkListProxyManager.getAllCCCheckListSummaryList(trxContext, trxContext
					.getLimitProfile().getLimitProfileID());
			ccCheckListSummaries = (ccMap == null) ? null : (ICheckListSummary[]) ccMap.get(ICMSConstant.NORMAL_LIST);
			HashMap colCheckListMap = this.checkListProxyManager.getAllCollateralCheckListSummaryList(trxContext,
					trxContext.getLimitProfile().getLimitProfileID());
			colCheckListSummaries = (colCheckListMap == null) ? null : (ICheckListSummary[]) colCheckListMap
					.get(ICMSConstant.NORMAL_LIST);
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("error access checklist template, for AA ["
					+ trxContext.getLimitProfile().getLimitProfileID() + "]", ex);
		}
		catch (CheckListException ex) {
			throw new CommandProcessingException("error access checklist, for AA ["
					+ trxContext.getLimitProfile().getLimitProfileID() + "]", ex);
		}

		// checking document completion date at disbursement section
		if (tatDoc.getDisbursementDocCompletionDate() != null
				&& StringUtils.isBlank(previousDisbursementCompletionDate)) {
			if (ccCheckListSummaries != null) {
				for (int i = 0; i < ccCheckListSummaries.length; i++) {
					boolean isDocumentsPerfected = checkDocumentPerfected(ccCheckListSummaries[i],
							this.validDocumentStatusForDisbursementCompletionDate);
					if (!isDocumentsPerfected) {
						exceptionMap.put("disbursementDocCompletedDate", new ActionMessage(
								"error.string.document_not_completed"));
					}
				}
			}

			if (exceptionMap.get("disbursementDocCompletedDate") == null) {
				if (colCheckListSummaries != null) {
					for (int i = 0; i < colCheckListSummaries.length; i++) {
						boolean isDocumentsPerfected = checkDocumentPerfected(colCheckListSummaries[i],
								this.validDocumentStatusForDisbursementCompletionDate);
						if (!isDocumentsPerfected) {
							exceptionMap.put("disbursementDocCompletedDate", new ActionMessage(
									"error.string.document_not_completed"));
						}
					}
				}
			}

		}
		// checking document completion date at POST disbursement section
		if (tatDoc.getDocCompletionDate() != null) {
			if (ccCheckListSummaries != null) {
				for (int i = 0; i < ccCheckListSummaries.length; i++) {
					boolean isDocumentsPerfected = checkDocumentPerfected(ccCheckListSummaries[i],
							this.validDocumentStatusForPostDisbursementCompletionDate);
					if (!isDocumentsPerfected) {
						exceptionMap.put("docCompletionDate", new ActionMessage("error.string.document_not_completed"));
					}
				}
			}

			if (exceptionMap.get("docCompletionDate") == null) {
				if (colCheckListSummaries != null) {
					for (int i = 0; i < colCheckListSummaries.length; i++) {
						boolean isDocumentsPerfected = checkDocumentPerfected(colCheckListSummaries[i],
								this.validDocumentStatusForPostDisbursementCompletionDate);
						if (!isDocumentsPerfected) {
							exceptionMap.put("docCompletionDate", new ActionMessage(
									"error.string.document_not_completed"));
						}
					}
				}
			}
		}

		if (exceptionMap.get("docCompletionDate") != null || exceptionMap.get("disbursementDocCompletedDate") != null) {
			// to stop the command chain
			exceptionMap.put(STOP_COMMAND_CHAIN, new ActionMessage("stop"));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	private boolean checkDocumentPerfected(ICheckListSummary checkListSummary, String[] validDocumentStatus) {
		if (checkListSummary.getCheckListID() != ICMSConstant.LONG_INVALID_VALUE) {
			try {
				ICheckListTrxValue checkListTrxVal = this.checkListProxyManager.getCheckList(checkListSummary
						.getCheckListID());
				ICheckList actualCheckList = checkListTrxVal.getCheckList();

				if (actualCheckList.getCheckListItemList() != null) {
					for (int i = 0; i < actualCheckList.getCheckListItemList().length; i++) {
						ICheckListItem item = actualCheckList.getCheckListItemList()[i];
						if (item.getIsMandatoryInd()
								&& !(this.isFilterPreApprovalDocuments && item.getIsPreApprove() && !item
										.getIsLockedInd())) {
							if (!ArrayUtils.contains(validDocumentStatus, item.getItemStatus())) {
								return false;
							}
						}
					}
				}
			}
			catch (CheckListException e) {
				logger.warn("failed to retrieve checklist transaction for checklist id ["
						+ checkListSummary.getCheckListID() + "], return 'false'", e);
				return false;
			}
		}

		return true;
	}
}
