package com.integrosys.cms.ui.genlad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ListCustomerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ListCustomerCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "relationshipMgr", "java.lang.String", REQUEST_SCOPE },
				{ "searchCustomerName", "java.lang.String", REQUEST_SCOPE },
				{ "segment", "java.lang.String", REQUEST_SCOPE },
				{ "dueMonth", "java.lang.String", REQUEST_SCOPE },
				{ "dueYear", "java.lang.String", REQUEST_SCOPE },
				{ "fromPage", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "generateLadFormObj",
						"com.integrosys.cms.ui.genlad.OBFilter", FORM_SCOPE },
				{ "segment", "java.lang.String", REQUEST_SCOPE },
				{
						IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						GLOBAL_SCOPE } });
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				// { "customerList",
				// "com.integrosys.base.businfra.search.SearchResult",
				// FORM_SCOPE },
				{ "customerList",
						"com.integrosys.base.businfra.search.SearchResult",
						SERVICE_SCOPE },
				{ "generateLadFormObj",
						"com.integrosys.cms.ui.genlad.OBFilter", SERVICE_SCOPE },
						{ "isRecordAvailable", "java.lang.String", SERVICE_SCOPE },
				{
						IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						GLOBAL_SCOPE },
				{
						"customerSearchCriteria1",
						"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
						SERVICE_SCOPE },
				{ "relationshipMgr", "java.lang.String", SERVICE_SCOPE },
				{ "segment", "java.lang.String", SERVICE_SCOPE },
				{ "dueMonth", "java.lang.String", SERVICE_SCOPE },
				{ "dueYear", "java.lang.String", SERVICE_SCOPE },
				{ "isRecordAvailable", "java.lang.String", SERVICE_SCOPE },
				{ "recordAlreadyPresent", "java.lang.String", SERVICE_SCOPE }

		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *             on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		String indicator = (String) map.get("indicator");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String startIdx = "0";

		CustomerSearchCriteria globalCriteria = (CustomerSearchCriteria) map
				.get(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ);

		String searchText = (String) map.get("searchCustomerName");

		String errorCode = "";
		if (ASSTValidator.isValidGenericASST(searchText)) {
			exceptionMap.put("customerNameError", new ActionMessage(
					"error.string.invalidCharacter"));
		} else if (!(errorCode = Validator.checkString(searchText, true, 3, 50))
				.equals(Validator.ERROR_NONE)) {
			exceptionMap.put("customerNameError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), 3,
					50 + ""));
		}

		CustomerSearchCriteria searchCriteria = new CustomerSearchCriteria();
		try {
			
			
		
		
			searchCriteria.setCustomerName(searchText);
			searchCriteria.setStartIndex(Integer.parseInt(startIdx));
			searchCriteria.setNItems(10000);
			searchCriteria.setCustomerStatus(ICMSConstant.CUSTOMER_STATUS_ALL);
			
			
			
	
			/*
			 * if("RPT0032".equals(filter.getReportId())){
			 * searchCriteria.setCustomerStatus
			 * (ICMSConstant.CUSTOMER_STATUS_INACTIVE); }else
			 * if("RPT0002".equals(filter.getReportId())){
			 * searchCriteria.setCustomerStatus
			 * (ICMSConstant.CUSTOMER_STATUS_ALL); }
			 */
			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			SearchResult sr = custproxy.searchCustomer(searchCriteria);
			result.put("customerList", sr);
			result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,
					searchCriteria);
			result.put("customerSearchCriteria1", searchCriteria);
			result.put("searchCustomerName",
					(String) map.get("searchCustomerName"));
			result.put("relationshipMgr", (String) map.get("relationshipMgr"));
			result.put("segment", (String) map.get("segment"));
			result.put("dueMonth", (String) map.get("dueMonth"));
			result.put("dueYear", (String) map.get("dueYear"));
			result.put("isRecordAvailable", "Y");
			result.put("recordAlreadyPresent", "N");
		
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"failed to search customer using search criteria '"
							+ searchCriteria + "'");
			cpe.initCause(e);
			throw cpe;
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}