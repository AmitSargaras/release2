/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/item/SubLimitTypeItemAction.java,v 1.1 2005/10/06 06:03:37 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype.item;

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
 *      com.integrosys.cms.ui.commodityglobal.sublimit.list.SubLimitTypeItemAction
 *      .java
 */
public class SubLimitTypeItemAction extends SubLimitTypeAction {

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang
	 * .String, java.util.HashMap, java.util.HashMap)
	 */
	protected IPage getNextPage(String event, HashMap hashmap, HashMap exceptionMap) {
		Page aPage = new Page();
		String jspPage = getPageReference(event);
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
		if (SLTUIConstants.EN_PREPARE_ADD.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new PrepareSLTItemCommand();
		}
		else if (EVENT_CREATE.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new CreateSLTItemCommand();
		}
		else if (SLTUIConstants.EN_PREPARE_UPDATE.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new PrepareSLTItemCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new UpdateSLTItemCommand();
		}
		DefaultLogger.debug(this, " - getCommandChain() - End.");
		return commArray;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return SLTItemValidator.validateInput((SubLimitTypeItemForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EVENT_CREATE) || event.equals(EVENT_UPDATE)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		if (EVENT_CREATE.equals(event)) {
			return SLTUIConstants.EN_PREPARE_ADD;
		}
		else if (EVENT_UPDATE.equals(event)) {
			return SLTUIConstants.EN_PREPARE_UPDATE;
		}
		return event;
	}

	private String getPageReference(String event) {
		if (SLTUIConstants.EN_PREPARE_ADD.equals(event)) {
			return SLTUIConstants.PN_SLT_ITEM_UPDATE;
		}
		if (EVENT_CREATE.equals(event)) {
			return SLTUIConstants.PN_SLT_ITEM_UPDATE_RETURN;
		}
		if (SLTUIConstants.EN_PREPARE_UPDATE.equals(event)) {
			return SLTUIConstants.PN_SLT_ITEM_UPDATE;
		}
		if (EVENT_UPDATE.equals(event)) {
			return SLTUIConstants.PN_SLT_ITEM_UPDATE_RETURN;
		}
		if (EVENT_CANCEL.equals(event)) {
			return SLTUIConstants.PN_SLT_ITEM_UPDATE_RETURN;
		}
		DefaultLogger.debug(this, "Unknown Event.");
		return null;
	}
}