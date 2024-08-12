/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/list/SubLimitListAction.java,v 1.3 2005/10/25 09:36:03 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.list;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainAction;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-14 Tag :
 *        com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitAction
 *        .java
 */
public class SubLimitListAction extends CommodityMainAction {

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang
	 * .String, java.util.HashMap, java.util.HashMap)
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		String refPage = null;
		if (SLUIConstants.EN_VIEW_RETURN.equals(event)) {
			refPage = getPageRefByFromEvent((String) resultMap.get(SLUIConstants.FN_FROM_EVENT));
		}
		else {
			refPage = getPageRefByEvent(event);
		}
		DefaultLogger.debug(this, "PageReference : " + refPage);
		aPage.setPageReference(refPage);
		return aPage;
	}

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getCommandChain(java.
	 * lang.String)
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand commArray[] = null;
		DefaultLogger.debug(this, "- Event : " + event);
		if (EVENT_PREPARE.equals(event)) {
			commArray = new ICommand[2];
			commArray[0] = new ReadSubLimitListCommand();
			commArray[1] = new PrepareSubLimitListCommand();
		}
		if (EVENT_DELETE.equals(event)) {
			commArray = new ICommand[3];
			commArray[0] = new SaveSubLimitCommand();
			commArray[1] = new DeleteSubLimitCommand();
			commArray[2] = new PrepareSubLimitListCommand();
		}
		if (EVENT_SUBMIT.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new UpdateSubLimitCommand();
		}
		if (EVENT_VIEW.equals(event)) {
			commArray = new ICommand[2];
			commArray[0] = new ReadSubLimitListCommand();
			commArray[1] = new PrepareSubLimitListCommand();
		}
		if (SLUIConstants.EN_CK_PROCESS_VIEW.equals(event)) {
			commArray = new ICommand[2];
			commArray[0] = new ReadSubLimitListCommand();
			commArray[1] = new PrepareSubLimitListCommand();
		}
		if (SLUIConstants.EN_VIEW_RETURN.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new ViewReturnCommand();
		}
		if (SLUIConstants.EN_PREPARE_UPDATE_ITEM.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new SaveSubLimitCommand();
		}
		if (SLUIConstants.EN_RETURN_UPDATE_ITEM.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new PrepareSubLimitListCommand();
		}
		if (SLUIConstants.EN_RESTORE.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new RestoreSubLimitListCommand();
		}
		// EVENT_CANCEL,EN_CANCEL_UPDATE
		return commArray;
	}

	protected boolean isValidationRequired(String event) {
		if (EVENT_SUBMIT.equals(event)) {
			return true;
		}
		return false;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return SubLimitListValidator.validateInput((SubLimitListForm) aForm, locale);
	}

	protected String getErrorEvent(String event) {
		if (EVENT_SUBMIT.equals(event)) {
			return SLUIConstants.EN_RESTORE;
		}
		if (EVENT_CANCEL.equals(event)) {
			return SLUIConstants.EN_RESTORE;
		}
		if (EVENT_DELETE.equals(event)) {
			return SLUIConstants.EN_RESTORE;
		}
		return event;
	}

	private String getPageRefByEvent(String event) {
		if (EVENT_PREPARE.equals(event)) {
			return SLUIConstants.PN_UPDATE_SL_LIST;
		}
		if (SLUIConstants.EN_PREPARE_UPDATE_ITEM.equals(event)) {
			return SLUIConstants.PN_PREPARE_UPDATE_ITEM;
		}
		if (SLUIConstants.EN_RETURN_UPDATE_ITEM.equals(event)) {
			return SLUIConstants.PN_UPDATE_SL_LIST;
		}
		if (EVENT_DELETE.equals(event)) {
			return SLUIConstants.PN_UPDATE_SL_LIST;
		}
		if (EVENT_SUBMIT.equals(event)) {
			return SLUIConstants.PN_UPDATE_RETURN;
		}
		if (EVENT_CANCEL.equals(event)) {
			return SLUIConstants.PN_UPDATE_RETURN;
		}
		if (EVENT_VIEW.equals(event)) {
			return SLUIConstants.PN_VIEW_SL;
		}
		if (SLUIConstants.EN_CK_PROCESS_VIEW.equals(event)) {
			return SLUIConstants.PN_CK_PROCESS_VIEW_SL;
		}
		if (SLUIConstants.EN_RESTORE.equals(event)) {
			return SLUIConstants.PN_UPDATE_SL_LIST;
		}
		return event;
	}

	private String getPageRefByFromEvent(String fromEvent) {
		DefaultLogger.debug(this, "FromEvent : " + fromEvent);
		if (EVENT_READ.equals(fromEvent)) {
			return SLUIConstants.PN_MK_VIEW_CMDT;
		}
		if (CommodityMainAction.EVENT_PROCESS.equals(fromEvent)) {
			return SLUIConstants.PN_CK_PROCESS_CMDT;
		}
		if (CommodityMainAction.EVENT_PREPARE_CLOSE.equals(fromEvent)) {
			return SLUIConstants.PN_MK_CLOSE_CMDT;
		}
		if (CommodityMainAction.EVENT_TRACK.equals(fromEvent)) {
			return SLUIConstants.PN_TRACK_CMDT;
		}
		if (CommodityMainAction.EVENT_PREPARE_UPDATE.equals(fromEvent)) {
			return SLUIConstants.PN_UPDATE_RETURN;
		}
		return fromEvent;
	}
}