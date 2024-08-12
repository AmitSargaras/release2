/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ListCustomerCommand.java,v 1.17 2005/05/12 12:58:51 jtan Exp $
 */

package com.integrosys.cms.ui.diaryitem;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

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
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.OBDiaryItem;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/05/12 12:58:51 $ Tag: $Name: $
 */
public class DiaryListCustomerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public DiaryListCustomerCommand() {

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
				{ "customerName", "java.lang.String", REQUEST_SCOPE },
				{ "fromPage", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem",FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "dropLineOD", "java.lang.String", REQUEST_SCOPE},
				{ "odScheduleUploadFile", "org.apache.struts.upload.FormFile", REQUEST_SCOPE},
				});
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
				//{ "customerList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
				{ "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE }, 
				{ "dropLineOD", "java.lang.String", REQUEST_SCOPE},
				{ "dropLineOD", "java.lang.String", SERVICE_SCOPE },
				{ "odScheduleUploadFile", "org.apache.struts.upload.FormFile", REQUEST_SCOPE},
				{ "odScheduleUploadFile", "org.apache.struts.upload.FormFile", SERVICE_SCOPE},
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		String indicator = (String) map.get("indicator");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBDiaryItem filter = (OBDiaryItem) map.get("diaryItemObj");
		result.put("diaryItemObj", filter);
		String fromPage = (String) map.get("fromPage");
		
		String startIdx = (String) map.get("startIndex");
		if(startIdx==null){
			startIdx="0";
		}
		CustomerSearchCriteria globalCriteria = (CustomerSearchCriteria)map.get(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ);
		
		String searchText=(String) map.get("customerName");
		if(searchText==null && "pagination".equals(fromPage)){
			searchText=globalCriteria.getCustomerName();
		}else{
			String errorCode="";
			if(ASSTValidator.isValidGenericASST(searchText)){
				exceptionMap.put("customerNameError", new ActionMessage("error.string.invalidCharacter"));
			}else if (!(errorCode=Validator.checkString(searchText, true, 3, 50)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("customerNameError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),3, 50 + ""));
			}
		}
		
		
		CustomerSearchCriteria searchCriteria= new CustomerSearchCriteria();
		try{
				searchCriteria.setCustomerName(searchText);
				searchCriteria.setStartIndex(Integer.parseInt(startIdx));
				searchCriteria.setNItems(10000);
				searchCriteria.setCustomerStatus(ICMSConstant.CUSTOMER_STATUS_ALL);
				String dropLineOD = (String)map.get("dropLineOD");
				FormFile odScheduleUploadFile = (FormFile)map.get("odScheduleUploadFile");
				ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
				SearchResult sr = custproxy.searchCustomer(searchCriteria);
				result.put("customerList", sr);
				result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ, searchCriteria);
				result.put("customerSearchCriteria1", searchCriteria);
				result.put("odScheduleUploadFile", odScheduleUploadFile);
				result.put("dropLineOD", dropLineOD);
				
			}
			catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("failed to search customer using search criteria '" + searchCriteria + "'");
				cpe.initCause(e);
				throw cpe;
			}


		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		
		return temp;
	}

}
