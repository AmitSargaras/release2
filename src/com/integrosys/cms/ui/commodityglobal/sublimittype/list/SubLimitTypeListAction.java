/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/list/SubLimitTypeListAction.java,v 1.2 2005/10/07 02:40:23 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.list;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants;
import com.integrosys.cms.ui.commodityglobal.sublimittype.SubLimitTypeAction;

/**
 * Describe this class. Purpose:list all Sub-Limit Type Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15
 * @Tag 
 *      com.integrosys.cms.ui.commodityglobal.sublimit.list.SubLimitTypeListAction
 *      .java
 */
public class SubLimitTypeListAction extends SubLimitTypeAction {

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang
	 * .String, java.util.HashMap, java.util.HashMap)
	 */
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		String jspPage = "";
		if (SLTUIConstants.SN_WORK_IN_PROGRESS.equals((String) resultMap.get(SLTUIConstants.SN_WORK_IN_PROGRESS))) {
			jspPage = SLTUIConstants.PN_WORK_IN_PROGRESS;
		}
		else {
			jspPage = getPageReference(event);
		}
		DefaultLogger.debug(this, "Page : " + jspPage);
		aPage.setPageReference(jspPage);
		return aPage;
	}

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getCommandChain(java.
	 * lang.String)
	 */
	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, " - getCommandChain() - Begin.");
		DefaultLogger.debug(this, " - event : " + event);
		ICommand commArray[] = null;
		if (EVENT_PREPARE.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new ReadSLTListCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new UpdateSLTListCommand();
		}
		else if (EVENT_SUBMIT.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new SubmitSLTCommand();
		}
		else if (EVENT_DELETE.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new DeleteSLTListCommand();
		}
		else if (SLTUIConstants.EN_TO_TRACK.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new ReadSLTListByTrxIdCommand();
		}
		else if (SLTUIConstants.EN_CK_PROCESS.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new ReadSLTListByTrxIdCommand();
		}
		else if (EVENT_APPROVE.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new ApproveSLTListCommand();
		}
		else if (EVENT_REJECT.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new RejectSLTListCommand();
		}
		else if (SLTUIConstants.EN_MK_PROCESS.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new ReadSLTListByTrxIdCommand();
		}
		else if (SLTUIConstants.EN_PRE_CLOSE.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new ReadSLTListByTrxIdCommand();
		}
		else if (SLTUIConstants.EN_CLOSE.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new CloseSLTListCommand();
		}
		else if (EVENT_VIEW.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new ViewSLTListCommand();
		}
		// For the following event,no command needed.
		// - SLTUIConstants.EN_UPDATE_RETURN : return from SLTItemAction.
		DefaultLogger.debug(this, " - getCommandChain() - End.");
		return commArray;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return SLTListValidator.validateInput((SubLimitTypeListForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_PREPARE) || event.equals(EVENT_APPROVE) || event.equals(EVENT_SUBMIT)
				|| event.equals(EVENT_REJECT) || event.equals(EVENT_UPDATE)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		if (EVENT_UPDATE.equals(event)) {
			return EVENT_PREPARE;
		}
		if (EVENT_SUBMIT.equals(event)) {
			return EVENT_PREPARE;
		}
		if (EVENT_DELETE.equals(event)) {
			return EVENT_PREPARE;
		}
		return event;
	}

	private String getPageReference(String event) {
		if (EVENT_PREPARE.equals(event)) {
			return SLTUIConstants.PN_SLT_LIST_UPDATE;
		}
		if (SLTUIConstants.EN_UPDATE_RETURN.equals(event)) {
			return SLTUIConstants.PN_SLT_LIST_UPDATE;
		}
		if (EVENT_UPDATE.equals(event)) {
			return SLTUIConstants.PN_SLT_ACK_SAVE;
		}
		if (EVENT_SUBMIT.equals(event)) {
			return SLTUIConstants.PN_SLT_ACK_SUBMIT;
		}
		if (EVENT_DELETE.equals(event)) {
			return SLTUIConstants.PN_SLT_LIST_UPDATE;
		}
		if (SLTUIConstants.EN_TO_TRACK.equals(event)) {
			return SLTUIConstants.PN_SLT_LIST_VIEW;
		}
		if (SLTUIConstants.EN_CK_PROCESS.equals(event)) {
			return SLTUIConstants.PN_SLT_LIST_CK_PROCESS;
		}
		if (EVENT_APPROVE.equals(event)) {
			return SLTUIConstants.PN_SLT_ACK_APPROVE;
		}
		if (EVENT_REJECT.equals(event)) {
			return SLTUIConstants.PN_SLT_ACK_REJECT;
		}
		if (SLTUIConstants.EN_MK_PROCESS.equals(event)) {
			return SLTUIConstants.PN_SLT_LIST_UPDATE;
		}
		if (SLTUIConstants.EN_PRE_CLOSE.equals(event)) {
			return SLTUIConstants.PN_PRE_CLOSE;
		}
		if (SLTUIConstants.EN_CLOSE.equals(event)) {
			return SLTUIConstants.PN_SLT_ACK_CLOSE;
		}
		if (EVENT_VIEW.equals(event)) {
			return SLTUIConstants.PN_SLT_LIST_VIEW;
		}
		return event;
	}
}