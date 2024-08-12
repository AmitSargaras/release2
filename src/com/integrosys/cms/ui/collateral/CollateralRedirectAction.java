/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/CollateralRedirectAction.java,v 1.7 2006/07/19 11:11:27 wltan Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2006/07/19 11:11:27 $ Tag: $Name: $
 */
public class CollateralRedirectAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if (EVENT_SEARCH.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new CollateralSearchCommand();
			objArray[1] = new RefreshCollateralCommand();
			objArray[2] = new PrepareCollateralSearchCommand();
		}
		else if (EVENT_LIST.equals(event) || "list1".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CollateralSearchCommand();
		}
		else if ("refresh".equals(event) || "refresh_create".equals(event) || 
				"prepare_form".equals(event) || "prepare".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RefreshCollateralCommand();
			objArray[1] = new PrepareCollateralSearchCommand();
		}
		else if (event.equals("prepare_create")){
			objArray = new ICommand[1];
			objArray[0] = new RedirectCollateralCreateCommand();
		}
		else {
			objArray = new ICommand[1];
			objArray[0] = new RedirectCollateralCommand();
		}


		return (objArray);
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap. It returns the page
	 * object.
	 * 
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();

		String subTypeCode = (String) resultMap.get("subtypeCode");
		DefaultLogger.debug(this, "subTypeCode is " + subTypeCode);

		// to cater for security sub-types that were auto-spawned from existing
		// types
		// checking against the common code tables
		String actualSubTypeCode = CommonDataSingleton.getCodeCategoryLabelByValue(CategoryCodeConstant.SEC_SUBTYP,
				subTypeCode);
		DefaultLogger.debug(this, "actualSubTypeCode is " + actualSubTypeCode);

		String forwardName = "submit_fail";

		// use the existing subtype if security type was auto-spawned
		subTypeCode = (actualSubTypeCode != null) ? actualSubTypeCode : subTypeCode;
		DefaultLogger.debug(this, "using this subTypeCode : " + subTypeCode + " for forwarding");

		if ((EVENT_SEARCH.equals(event)) || ("prepare_form".equals(event))) {
			forwardName = "after_search";
		}
		else if (EVENT_LIST.equals(event)) {
			forwardName = "after_list";
		}
		else if ("list1".equals(event)) {
			forwardName = "after_list";
		}
		else if ("refresh".equals(event)) {
			forwardName = "after_search";
		}		
		else if ("prepare_create".equals(event)) {
			forwardName = subTypeCode;
		}		
		else if ("refresh_create".equals(event)) {
			forwardName = "refresh_create";
		}
		else if ("prepare".equals(event)) {
			forwardName = "prepare";
		}
		else {
			if ((resultMap.get("trxSubType") != null)
					&& resultMap.get("trxSubType").equals(ICMSConstant.MANUAL_INPUT_TRX_TYPE)) {
				forwardName=ICMSConstant.MANUAL_INPUT_TRX_TYPE;
			}
			else {
				forwardName=subTypeCode;
			}
		}

		aPage.setPageReference(forwardName);
		return aPage;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		return CollateralPrepareCreateFormValidator.validateInput((CollateralSearchForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		if (event.equals("list") || event.equals("prepare_create")) 
			return true;
		return false;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if ("list".equals(event)) {
			errorEvent = "prepare_form";
		}
		else if ("refresh".equals(event)) {
			errorEvent = "search";
		}
		else if ("prepare_create".equals(event)) {
			errorEvent = "prepare";
		}
		return errorEvent;
	}

}
