package com.integrosys.cms.ui.systemBankBranch;

import java.util.*;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBank.proxy.ISystemBankProxyManager;
import com.integrosys.cms.app.systemBank.trx.ISystemBankTrxValue;
import com.integrosys.cms.app.systemBank.trx.OBSystemBankTrxValue;
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;

/**
 *@author $Author: Abhijit R$
 *Command for searching System Bank Branch
 */
public class SearchListSystemBankBranchCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	private ISystemBankProxyManager systemBankProxy;

	public ISystemBankProxyManager getSystemBankProxy() {
		return systemBankProxy;
	}

	public void setSystemBankProxy(ISystemBankProxyManager systemBankProxy) {
		this.systemBankProxy = systemBankProxy;
	}
	
	private ISystemBankBranchProxyManager systemBankBranchProxy;

	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}

	public SearchListSystemBankBranchCommand() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				
				 {"searchBy", "java.lang.String", REQUEST_SCOPE},
	                {"searchText", "java.lang.String", REQUEST_SCOPE}
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"systemBankBranchList", "java.util.ArrayList", REQUEST_SCOPE},
	                { "systemBankObj", "com.integrosys.cms.app.systemBank.bus.OBSystemBank", FORM_SCOPE },
	                { "systemBankObj", "com.integrosys.cms.app.systemBank.bus.OBSystemBank", SERVICE_SCOPE },
	                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
	        });
	    }

	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	        HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        try {
	        	//ArrayList systemBankBranchList = new ArrayList();
	        	String searchBy=(String) map.get("searchBy");
	        	String searchText=(String) map.get("searchText");
	        	ISystemBank systemBank;
	        	OBSystemBankBranch obSystemBankBranch = new OBSystemBankBranch();
				ISystemBankTrxValue trxValue=null;
				SearchResult systemBankBranchList = new SearchResult();
	        	// function to get system Bank according to search text and type
	            systemBankBranchList= (SearchResult)  getSystemBankBranchProxy().getAllActual(searchBy, searchText);
	            if (systemBankBranchList != null) {
					for (Iterator it = systemBankBranchList.getResultList().iterator(); it.hasNext();) {

						obSystemBankBranch = (OBSystemBankBranch) it.next();
	            trxValue = (OBSystemBankTrxValue) getSystemBankProxy().getSystemBankTrxValue(Long.parseLong(obSystemBankBranch.getSystemBankCode().getSystemBankCode()));
					}
	            }
	            systemBank = (OBSystemBank) trxValue.getSystemBank();
	                  resultMap.put("systemBankBranchList", systemBankBranchList);
	                  resultMap.put("systemBankObj", systemBank);
	                  resultMap.put("ISystemBankTrxValue", trxValue);
	        }catch (SystemBankException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			} catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
}



