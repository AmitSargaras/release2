package com.integrosys.cms.ui.creditApproval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;
import com.integrosys.cms.app.creditApproval.proxy.ICreditApprovalProxy;
import com.integrosys.cms.asst.validator.ASSTValidator;

/**
 *@author $Govind.Sahu$
 *Command for searching Credit Approval
 */
public class SearchListCreditApprovalCommand extends AbstractCommand implements ICommonEventConstant {


	private ICreditApprovalProxy creditApprovalProxy;

	/**
	 * Default Constructor
	 */
	public SearchListCreditApprovalCommand() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {				
				    { "approvalCode", "java.lang.String", REQUEST_SCOPE},
	                { "approvalName", "java.lang.String", REQUEST_SCOPE},
					{ "name", "java.lang.String", REQUEST_SCOPE },
					{ "loginId", "java.lang.String", REQUEST_SCOPE},
					{ "startIndex", "java.lang.String", REQUEST_SCOPE }				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                { "oBCreditApproval", "java.util.ArrayList", REQUEST_SCOPE},
	        		{ "startIndex", "java.lang.String", REQUEST_SCOPE },
	        		{ "approvalCode", "java.lang.String", REQUEST_SCOPE},
	                { "approvalName", "java.lang.String", REQUEST_SCOPE},					
	        		{ "searchResultCreditApproval", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},               
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
	        
	        try {
				String name = (String) map.get("name");
				String login = (String)map.get("loginId");
				String startIndex = (String) map.get("startIndex");
				String searchTxtApprovalCode="";
				String searchTxtApprovalName="";
				SearchResult searchResultCreditApproval = null;
				
				if(startIndex==null || startIndex.equals("null")){
				startIndex = "0";
				}
				
				DefaultLogger.debug(this, "after getting CreditApproval feed group from proxy.");
				
	        	
	        	 searchTxtApprovalCode=(String) map.get("approvalCode");
	        	 searchTxtApprovalName=(String) map.get("approvalName");
	        	
	        	 if(searchTxtApprovalCode!=null){
	        	 searchTxtApprovalCode=searchTxtApprovalCode.trim();
	        	 }
	        	 if(searchTxtApprovalName!=null){
	        	 searchTxtApprovalName=searchTxtApprovalName.trim();
	        	 }
	        	 
	    		if(searchTxtApprovalCode!=null && ASSTValidator.isValidAlphaNumStringWithoutSpace(searchTxtApprovalCode))
	    		{
			        exceptionMap.put("searchTxtApprovalCode", new ActionMessage("error.string.invalidCharacter"));
			        searchTxtApprovalCode = "";
	    		}
	    		if(searchTxtApprovalName!=null && ASSTValidator.isValidAlphaNumStringWithSpace(searchTxtApprovalName))
	    		{
	    			exceptionMap.put("searchTxtApprovalName", new ActionMessage("error.string.invalidCharacter"));
	    			searchTxtApprovalName = "";
	    		}
					
	    		if(exceptionMap.size()!=0)
	    		{
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	    		}
	        	if(searchTxtApprovalCode==null || "null".equals(searchTxtApprovalCode))
	        	{
	        		searchTxtApprovalCode ="";
	        	}
	        	if(searchTxtApprovalName==null || "null".equals(searchTxtApprovalName))
	        	{
	        		searchTxtApprovalName ="";
	        	}
	    		
	            List creditApprovalList = (ArrayList)getCreditApprovalProxy().getAllActual(searchTxtApprovalCode,searchTxtApprovalName);
	            searchResultCreditApproval = new SearchResult(Integer.parseInt(startIndex), 10, creditApprovalList.size(), creditApprovalList);
	            resultMap.put("oBCreditApproval", creditApprovalList);	            
	            resultMap.put("searchResultCreditApproval", searchResultCreditApproval);
				resultMap.put("startIndex", startIndex);
				resultMap.put("approvalCode", searchTxtApprovalCode);            
	            resultMap.put("approvalName", searchTxtApprovalName);	
	            
	        }catch (CreditApprovalException ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			catch (TransactionException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "got exception in doExecute" + e);
				throw (new CommandProcessingException(e.getMessage()));
			}catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        resultMap.put("offset", new Integer(0));
			resultMap.put("length", new Integer(10));
	        return returnMap;
	    }

		/**
		 * @return the creditApprovalProxy
		 */
		public ICreditApprovalProxy getCreditApprovalProxy() {
			return creditApprovalProxy;
		}

		/**
		 * @param creditApprovalProxy the creditApprovalProxy to set
		 */
		public void setCreditApprovalProxy(ICreditApprovalProxy creditApprovalProxy) {
			this.creditApprovalProxy = creditApprovalProxy;
		}
}



