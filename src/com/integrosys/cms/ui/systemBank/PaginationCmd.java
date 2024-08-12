/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.systemBank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.ui.feed.stock.list.StockListForm;
import com.integrosys.cms.ui.feed.stock.list.StockListMapper;

/**
*@author $Author: Abhijit R$
*Command for pagination of system bank branch
 */
public class PaginationCmd extends AbstractCommand implements ICommonEventConstant {
	private ISystemBankBranchProxyManager systemBankBranchProxy;

	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "name", "java.lang.String", REQUEST_SCOPE },
				{ "loginId", "java.lang.String", REQUEST_SCOPE},
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				 { "systemBankObj", "com.integrosys.cms.app.systemBank.bus.OBSystemBank", SERVICE_SCOPE },
				
				 });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				 {"systemBankBranchList", "java.util.ArrayList", REQUEST_SCOPE},
				 { "offset", "java.lang.Integer", SERVICE_SCOPE },
					{ "length", "java.lang.Integer", SERVICE_SCOPE },
					{ "startIndex", "java.lang.String", REQUEST_SCOPE },
					  { "systemBankObj", "com.integrosys.cms.app.systemBank.bus.OBSystemBank", FORM_SCOPE },
		                { "systemBankObj", "com.integrosys.cms.app.systemBank.bus.OBSystemBank", SERVICE_SCOPE },
				 });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			//List inputList = (List) map.get(MaintainSystemBankBranchForm.SYSTEM_BANK_BRANCH_MAPPER);
			ISystemBank systemBank=(ISystemBank) map.get("systemBankObj");
			String name = (String) map.get("name");
			String login = (String)map.get("loginId");
			String startIdx = (String) map.get("startIndex");
			//ArrayList systemBankBranchList = new ArrayList();
			SearchResult systemBankBranchList = new SearchResult();
		
		
		//	int targetOffset = Integer.parseInt((String) inputList.get(0));
			String targetOffset=startIdx;
			//	systemBankBranchList= (SearchResult)  getSystemBankBranchProxy().searchBranch(login);
				
				//targetOffset = StockListMapper.adjustOffset(targetOffset, length, systemBankBranchList.getNItems());
				DefaultLogger.debug(this, "Target Offset: " + targetOffset);
			resultMap.put("systemBankBranchList", systemBankBranchList);
		    resultMap.put("systemBankObj", systemBank);
		    resultMap.put("startIndex", startIdx);
			resultMap.put("offset", new Integer(targetOffset));
			
		}catch (SystemBankException ex) {
       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
         ex.printStackTrace();
         throw (new CommandProcessingException(ex.getMessage()));
	}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage(), e));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
