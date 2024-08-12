package com.integrosys.cms.ui.fccBranch;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fccBranch.proxy.IFCCBranchProxyManager;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBank.trx.ISystemBankTrxValue;

/**
 *@author $Author: Abhijit R$
 *Command for searching System Bank Branch
 */
public class SearchListFCCBranchCommand extends AbstractCommand implements ICommonEventConstant {

	
	
	private IFCCBranchProxyManager fccBranchProxy;

	

	/**
	 * @return the fccBranchProxy
	 */
	public IFCCBranchProxyManager getFccBranchProxy() {
		return fccBranchProxy;
	}

	/**
	 * @param fccBranchProxy the fccBranchProxy to set
	 */
	public void setFccBranchProxy(IFCCBranchProxyManager fccBranchProxy) {
		this.fccBranchProxy = fccBranchProxy;
	}

	public SearchListFCCBranchCommand() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				
				 {"searchBy", "java.lang.String", REQUEST_SCOPE},
	                {"searchText", "java.lang.String", REQUEST_SCOPE}
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"fccBranchList", "java.util.ArrayList", REQUEST_SCOPE},
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
	        	String searchBy=(String) map.get("searchBy");
	        	String searchText=(String) map.get("searchText");
				ISystemBankTrxValue trxValue=null;
				SearchResult fccBranchList = new SearchResult();
	            fccBranchList= (SearchResult)  getFccBranchProxy().getAllActual(searchBy, searchText);
	                  resultMap.put("fccBranchList", fccBranchList);
	               
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



