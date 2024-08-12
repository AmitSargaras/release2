/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/item/SubLimitItemAction.java,v 1.2 2006/09/27 02:19:26 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.item;

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
 *        com.integrosys.cms.ui.collateral.commodity.sublimit.item
 *        .SubLimitItemAction.java
 */
public class SubLimitItemAction extends CommodityMainAction {

	/*
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang
	 * .String, java.util.HashMap, java.util.HashMap)
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		String refPage = getPageRefByEvent(event);
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
		if (SLUIConstants.EN_PREPARE_UPDATE_ITEM.equals(event) || SLUIConstants.EN_RE_PREPARE_UPDATE_ITEM.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new PrepareUpdateSLICommand();
		}
		if (SLUIConstants.EN_OK_UPDATE_ITEM.equals(event)) {
			commArray = new ICommand[1];
			commArray[0] = new UpdateSubLimitItemCommand();
		}
		// EN_CANCEL_UPDATE_ITEM
		return commArray;
	}

	protected boolean isValidationRequired(String event) {
		if (SLUIConstants.EN_OK_UPDATE_ITEM.equals(event)) {
			return true;
		}
		return false;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return SubLimitItemValidator.validateInput((SubLimitItemForm) aForm, locale);
	}

	protected String getErrorEvent(String event) {
		if (SLUIConstants.EN_OK_UPDATE_ITEM.equals(event)) {
			return SLUIConstants.EN_RE_PREPARE_UPDATE_ITEM;
		}
		if (SLUIConstants.EN_CANCEL_UPDATE_ITEM.equals(event)) {
			return SLUIConstants.EN_RE_PREPARE_UPDATE_ITEM;
		}
		return event;
	}

	private String getPageRefByEvent(String event) {
		if (SLUIConstants.EN_PREPARE_UPDATE_ITEM.equals(event)) {
			return SLUIConstants.PN_PREPARE_UPDATE_ITEM;
		}
		if (SLUIConstants.EN_OK_UPDATE_ITEM.equals(event)) {
			return SLUIConstants.PN_RETURN_UPDATE_ITEM;
		}
		if (SLUIConstants.EN_CANCEL_UPDATE_ITEM.equals(event)) {
			return SLUIConstants.PN_RETURN_UPDATE_ITEM;
		}
		if (SLUIConstants.EN_RE_PREPARE_UPDATE_ITEM.equals(event)) {
			return SLUIConstants.PN_PREPARE_UPDATE_ITEM;
		}
		return event;
	}
}