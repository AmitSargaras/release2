package com.integrosys.cms.ui.systemBank;

import java.util.*;

import org.apache.struts.action.ActionMessage;

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
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.component.common.transaction.ICompTrxResult;

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
				 {"BranchCode", "java.lang.String", REQUEST_SCOPE},
		            {"BranchName", "java.lang.String", REQUEST_SCOPE},
		            {"State", "java.lang.String", REQUEST_SCOPE},
		            {"City", "java.lang.String", REQUEST_SCOPE},
		            {"startIndex", "java.lang.String", REQUEST_SCOPE},
		            {"startIndx", "java.lang.String", REQUEST_SCOPE},
				 {"searchBy", "java.lang.String", REQUEST_SCOPE},
	                {"searchText", "java.lang.String", REQUEST_SCOPE},
	                { "systemBankObj", "com.integrosys.cms.app.systemBank.bus.OBSystemBank", SERVICE_SCOPE },
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"systemBankBranchList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
	                { "systemBankObj", "com.integrosys.cms.app.systemBank.bus.OBSystemBank", FORM_SCOPE },
	                { "systemBankObj", "com.integrosys.cms.app.systemBank.bus.OBSystemBank", SERVICE_SCOPE },
	                {"startIndex", "java.lang.String", REQUEST_SCOPE},
		            {"startIndx", "java.lang.String", REQUEST_SCOPE},
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
	        HashMap exceptionMap = new HashMap();
	        	ISystemBank systemBank=(ISystemBank) map.get("systemBankObj");
	        	String searchBy=(String) map.get("searchBy");
	        	String searchText=(String) map.get("searchText");
	        	boolean isErrorBranchCode=false;
	        	boolean isErrorBranchName=false;
	        	boolean isErrorStateCode=false;
	        	boolean isErrorCityName=false;
	        	
	        	String branchCode = (String) map.get("BranchCode");
				String branchName = (String) map.get("BranchName");
				String state = (String) map.get("State");
				String city = (String) map.get("City");
				String startIndx = (String) map.get("startIndx");
				String startInd = (String) map.get("startIndex");
				if(startIndx == null)
	        		startIndx = "0";

				if(branchCode!=null){
					if(!(branchCode.trim().equals("")))
					isErrorBranchCode = ASSTValidator.isValidAlphaNumStringWithoutSpace(branchCode);
				}
				if(branchName!=null){
					if(!(branchName.trim().equals("")))
					isErrorBranchName = ASSTValidator.isValidAlphaNumStringWithoutSpace(branchName);
				}	
				if(state!=null){
					if(!(state.trim().equals("")))
						isErrorStateCode = ASSTValidator.isValidAlphaNumStringWithoutSpace(state);
				}	
				if(city!=null){
					if(!(city.trim().equals("")))
						isErrorCityName = ASSTValidator.isValidAlphaNumStringWithoutSpace(city);
				}	
				if(isErrorBranchCode){
					exceptionMap.put("branchInvalidCodeError", new ActionMessage("error.string.branchCodeInvalid"));
					SearchResult systemBankBranchList = new SearchResult();
					try{
					systemBankBranchList= (SearchResult)  getSystemBankBranchProxy().getAllActualBranch();
					}catch(Exception e){
						DefaultLogger.debug(this,"Error in retriving Branch list ");
					}
					resultMap.put("systemBankBranchList", systemBankBranchList);
					resultMap.put("systemBankObj", systemBank);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
					
				}else if(isErrorBranchName){
					exceptionMap.put("branchInvalidNameError", new ActionMessage("error.string.branchNameInvalid"));
					SearchResult systemBankBranchList = new SearchResult();
					try{
					systemBankBranchList= (SearchResult)  getSystemBankBranchProxy().getAllActualBranch();
					}catch(Exception e){
						DefaultLogger.debug(this,"Error in retriving Branch list ");
					}
					
					resultMap.put("systemBankBranchList", systemBankBranchList);
					resultMap.put("systemBankObj", systemBank);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
					
				}else if(isErrorStateCode){
					exceptionMap.put("stateInvalidNameError", new ActionMessage("error.string.stateInvalid"));
					SearchResult systemBankBranchList = new SearchResult();
					try{
					systemBankBranchList= (SearchResult)  getSystemBankBranchProxy().getAllActualBranch();
					}catch(Exception e){
						DefaultLogger.debug(this,"Error in retriving Branch list ");
					}
					
					resultMap.put("systemBankBranchList", systemBankBranchList);
					resultMap.put("systemBankObj", systemBank);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
					
				}else if(isErrorCityName){
					exceptionMap.put("cityInvalidNameError", new ActionMessage("error.string.cityInvalid"));
					SearchResult systemBankBranchList = new SearchResult();
					try{
					systemBankBranchList= (SearchResult)  getSystemBankBranchProxy().getAllActualBranch();
					}catch(Exception e){
						DefaultLogger.debug(this,"Error in retriving Branch list ");
					}
					
					resultMap.put("systemBankBranchList", systemBankBranchList);
					resultMap.put("systemBankObj", systemBank);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
					
				}else{
					try {
	        	//ISystemBank systemBank;
	        	OBSystemBankBranch obSystemBankBranch = new OBSystemBankBranch();
				ISystemBankTrxValue trxValue=null;
				SearchResult systemBankBranchList = new SearchResult();
	        	// function to get system Bank according to search text and type
	            systemBankBranchList= (SearchResult)  getSystemBankBranchProxy().getSystemBranchList(branchCode, branchName, state, city);
	            if (systemBankBranchList != null) {
					for (Iterator it = systemBankBranchList.getResultList().iterator(); it.hasNext();) {

						obSystemBankBranch = (OBSystemBankBranch) it.next();
	            trxValue = (OBSystemBankTrxValue) getSystemBankProxy().getSystemBankTrxValue(obSystemBankBranch.getSystemBankCode().getId());
					}
	            }
	           // systemBank = (OBSystemBank) trxValue.getSystemBank();
	                  resultMap.put("systemBankBranchList", systemBankBranchList);
	                  resultMap.put("systemBankObj", systemBank);
	              	resultMap.put("startIndex", startInd);
					resultMap.put("startIndx", startIndx);
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

}



