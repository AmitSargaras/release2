/*
 * Created on Apr 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.customer.CustomerSearchForm;
import com.integrosys.cms.ui.customer.CustomerSearchFormValidator;
import com.integrosys.cms.ui.customer.PrepareCustomerSearchCommand;
import com.integrosys.cms.ui.manualinput.limit.ListLimitCmd;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class AAListAction extends CommonAction {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getNextPage(java.lang
	 * .String, java.util.HashMap, java.util.HashMap)
	 */
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		// TODO Auto-generated method stub
		Page aPage = new Page();
		if (EventConstant.EVENT_REJECTEDFLOW_LIST_LIMIT.equals(event)) {
			aPage.setPageReference("rejectedFlow_list_limit");
		}
		else
			aPage.setPageReference(getReference(event));
		return aPage;
	}

	private String getReference(String event) {
		if (EventConstant.EVENT_PREP_CUSTOMER.equals(event)
				||EventConstant.EVENT_CUSTOMER_REJECTEDFLOW_SEARCH.equals(event) ) {
			return "customer_search";
		}
		else if (EventConstant.EVENT_LIST_CUSTOMER.equals(event) || EventConstant.EVENT_LIST.equals(event)) {
			return "customer_list";
		}
		else {
			return event;
		}
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return CustomerSearchFormValidator.validateInput((CustomerSearchForm) aForm, locale);
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals(EventConstant.EVENT_LIST_CUSTOMER)||
				EventConstant.EVENT_REJECTEDFLOW_LIST_LIMIT.equals(event)) {
			result = true;
		}
		return result;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (event.equals(EventConstant.EVENT_LIST_CUSTOMER)||
				EventConstant.EVENT_REJECTEDFLOW_LIST_LIMIT.equals(event)) {
			errorEvent = EventConstant.EVENT_PREP_CUSTOMER;
		}
		return errorEvent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.base.uiinfra.common.CommonAction#getCommandChain(java.
	 * lang.String)
	 */
	protected ICommand[] getCommandChain(String event) {
		// TODO Auto-generated method stub
		ICommand[] objArray = null;
		if (EventConstant.EVENT_PREP_CUSTOMER.equals(event)
				||EventConstant.EVENT_CUSTOMER_REJECTEDFLOW_SEARCH.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCustomerSearchCommand();
		}
		else if (EventConstant.EVENT_LIST_CUSTOMER.equals(event) || 
				EventConstant.EVENT_LIST.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListAACmd();
		}
		else if (EventConstant.EVENT_REJECTEDFLOW_LIST_LIMIT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ListLimitCmd();
		}
		return objArray;
	}

}
