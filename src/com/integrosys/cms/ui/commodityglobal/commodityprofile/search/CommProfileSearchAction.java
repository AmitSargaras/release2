package com.integrosys.cms.ui.commodityglobal.commodityprofile.search;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfConstants;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CommProfileAction;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-2-9
 * @Tag com.integrosys.cms.ui.commodityglobal.commodityprofile.search.
 *      CommProfileSearchAction.java
 */
public class CommProfileSearchAction extends CommProfileAction implements CMDTProfConstants {

	public ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, " - Event : " + event);
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareSearchCMDTProfCommand();
		}
		else if (EN_SEARCH.equals(event) || EN_READ.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ExtractSearchCriteriaCommand();
		}
		return objArray;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return CommProfileSearchValidator.validateInput(aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		if (EN_SEARCH.equals(event) || EN_READ.equals(event)) {
			return true;
		}
		return false;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		aPage.setPageReference(getReference(event));
		return aPage;
	}

	protected String getErrorEvent(String event) {
		if (EN_SEARCH.equals(event) || EN_READ.equals(event)) {
			return EVENT_PREPARE;
		}
		return super.getErrorEvent(event);
	}

	private String getReference(String event) {
		return event;
	}
}
