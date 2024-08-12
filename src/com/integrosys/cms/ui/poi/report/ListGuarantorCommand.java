/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ListCustomerCommand.java,v 1.17 2005/05/12 12:58:51 jtan Exp $
 */

package com.integrosys.cms.ui.poi.report;

import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
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
import com.integrosys.cms.app.partygroup.bus.PartyGroupException;
import com.integrosys.cms.app.partygroup.proxy.IPartyGroupProxyManager;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/05/12 12:58:51 $ Tag: $Name: $
 */
public class ListGuarantorCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ListGuarantorCommand() {

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
				{ "searchGuarantorName", "java.lang.String", REQUEST_SCOPE },
				{ "fromPage", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter",FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
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
				{ "guarantorList", "java.util.List", SERVICE_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				{ "customerSearchCriteria1", "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE }, 
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
		OBFilter filter = (OBFilter) map.get("reportFormObj");
		
		
		IPartyGroupProxyManager partyGroupProxy=(IPartyGroupProxyManager) BeanHouse.get("partyGroupProxy");
		
		if(filter.getReportId()!=null)
			result.put("reportFormObj", filter);
		
		String fromPage = (String) map.get("fromPage");
		
		String startIdx = (String) map.get("startIndex");
		if(startIdx==null){
			startIdx="0";
		}
		CustomerSearchCriteria globalCriteria = (CustomerSearchCriteria)map.get(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ);
		
		String searchText=(String) map.get("searchGuarantorName");
		if(searchText==null && "pagination".equals(fromPage)){
			searchText=globalCriteria.getCustomerName();
		}else{
			String errorCode="";
			if(ASSTValidator.isValidGenericASST(searchText)){
				exceptionMap.put("guarantorNameError", new ActionMessage("error.string.invalidCharacter"));
			}else if (!(errorCode=Validator.checkString(searchText, true, 3, 50)).equals(Validator.ERROR_NONE)) {
				exceptionMap.put("guarantorNameError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),3, 50 + ""));
			}
		}
		
		List guarantorList = null;
		try {
			guarantorList = partyGroupProxy.getPartyByFacilityList(searchText);
		} catch (PartyGroupException e) {
			e.printStackTrace();
		} catch (TrxParameterException e) {
			e.printStackTrace();
		} catch (TransactionException e) {
			e.printStackTrace();
		}
		
		
		
		/*CustomerSearchCriteria searchCriteria= new CustomerSearchCriteria();
		try{
				searchCriteria.setCustomerName(searchText);
				searchCriteria.setStartIndex(Integer.parseInt(startIdx));
				searchCriteria.setNItems(10000);
				if("RPT0032".equals(filter.getReportId())){
					searchCriteria.setActiveCustomer(false);
				}
				ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
				SearchResult sr = custproxy.searchCustomer(searchCriteria);
				result.put("customerList", sr);
				result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ, searchCriteria);
				result.put("customerSearchCriteria1", searchCriteria);
			}
			catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("failed to search customer using search criteria '" + searchCriteria + "'");
				cpe.initCause(e);
				throw cpe;
			}
*/
		result.put("guarantorList", guarantorList);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
