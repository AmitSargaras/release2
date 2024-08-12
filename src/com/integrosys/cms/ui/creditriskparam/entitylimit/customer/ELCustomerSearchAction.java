/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.customer;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitAction;

/**
 * Entity Limit Search Action Class
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class ELCustomerSearchAction extends EntityLimitAction {

	public static final String EVENT_CUST_ADD = "customer_add";
	public static final String EVENT_CUST_PREPARE_SEARCH = "customer_search";
	
	public static final String EVENT_CUST_ADD_SEARCH = "add_customer_search";
	public static final String EVENT_CUST_ADD_RESULT = "add_customer_search_result";
	
	public static final String EVENT_CUST_ADD_SELECT = "add_customer_search_select";
	
	protected ICommand[] getCommandChain(String event) {
		
		DefaultLogger.debug(this, "CRCustomerSearchAction is " + event);
		
		ICommand objArray[] = null;
		if (EVENT_PREPARE.equals(event) 
				|| EVENT_CUST_ADD.equals(event)) {
			
			objArray = new ICommand[1];
//			objArray[0] = new PrepareELCustSearchCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("PrepareELCustSearchCommand");
			
		} else if (EVENT_CUST_ADD_SEARCH.equals(event)) {
			
			objArray = new ICommand[1];
//			objArray[0] = new ELCustSearchCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("ELCustSearchCommand");
			
		} else if (EVENT_CUST_ADD_SELECT.equals(event)) {
			
			objArray = new ICommand[1];
//			objArray[0] = new AddNewEntityLimitCommand();
            objArray[0] = (ICommand) getNameCommandMap().get("AddNewEntityLimitCommand");
			
		} 
		return objArray;
	}

	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {

		Page aPage = new Page();
		aPage.setPageReference(getReference(event));
        return aPage;
	}
	
	private String getReference(String event) {
		
		if (EVENT_PREPARE.equals(event) || EVENT_CUST_ADD.equals(event))
		{
			return EVENT_CUST_PREPARE_SEARCH;
		}
		else if (EVENT_CUST_ADD_SEARCH.equals(event)) {
					
			return EVENT_CUST_ADD_RESULT;
		}
		else
		{
			return event;
		}
	}
	
	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm
	 *            is of type ActionForm
	 * @param locale
	 *            of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		DefaultLogger.debug(this, "Inside validate Input child class");
		
		String event = ((ELCustSearchForm) aForm).getEvent();
		
		if (EVENT_CUST_ADD_SEARCH.equals(event)) {
			return ELCustSearchFormValidator.validateInput(
					(ELCustSearchForm) aForm, locale);
		}
		
		if (EVENT_CUST_ADD_SELECT.equals(event))
			return ELCustSearchFormValidator.validateInput(
					(ELCustSearchForm) aForm, locale);
		
		return new ActionErrors();
	}
	
	/**
	 * This method is to check if validation to form is needed
	 */
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_CUST_ADD_SELECT.equals(event) || EVENT_CUST_ADD_SEARCH.equals(event)) {
			result = true;
		}
		return result;
	}

	/**
	 * Getting error event.
	 */
	protected String getErrorEvent(String event) {
		String errorEvent = getDefaultEvent();
		if (EVENT_CUST_ADD_SELECT.equals(event)) {
			errorEvent = EVENT_CUST_ADD_RESULT;
		}
		return errorEvent;
	}

}
