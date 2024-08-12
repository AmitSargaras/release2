package com.integrosys.cms.ui.creditApproval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalException;

public class PaginateCreditApprovalListCommand extends CreditApprovalCommand {
	
	public String[][] getParameterDescriptor() {
		return new String[][] {				
			{ "name", "java.lang.String", REQUEST_SCOPE },
			{ "loginId", "java.lang.String", REQUEST_SCOPE},
			 { "approvalCode", "java.lang.String", REQUEST_SCOPE},
             { "approvalName", "java.lang.String", REQUEST_SCOPE},
			{ "startIndex", "java.lang.String", REQUEST_SCOPE }		
		};
	}
	
	public String[][] getResultDescriptor() {
       return (new String[][]{
	        { "oBCreditApproval", "java.util.ArrayList", REQUEST_SCOPE},
			{ "startIndex", "java.lang.String", REQUEST_SCOPE },
			{ "searchResultCreditApproval", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
			 { "approvalCode", "java.lang.String", REQUEST_SCOPE},
             { "approvalName", "java.lang.String", REQUEST_SCOPE},
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
			String startIndex = (String) map.get("startIndex");
			SearchResult searchResultCreditApproval = null;
			
			String searchTxtApprovalCode=(String) map.get("approvalCode");
        	String searchTxtApprovalName=(String) map.get("approvalName");
        	
			if(startIndex==null)
				startIndex = "0";
			        	
    		if(exceptionMap.size()!=0)
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
    		
    		if( searchTxtApprovalCode == null || searchTxtApprovalCode.equals("null") )
    			searchTxtApprovalCode = "";
			
			if( searchTxtApprovalName == null || searchTxtApprovalName.equals("null") )
				searchTxtApprovalName = "";
					
            List creditApprovalList = (ArrayList)getCreditApprovalProxy().getAllActual(searchTxtApprovalCode,searchTxtApprovalName);
            searchResultCreditApproval = new SearchResult(Integer.parseInt(startIndex), 10, creditApprovalList.size(), creditApprovalList);
            
            resultMap.put("approvalCode", searchTxtApprovalCode);            
            resultMap.put("approvalName", searchTxtApprovalName);			
            resultMap.put("oBCreditApproval", creditApprovalList);            
            resultMap.put("searchResultCreditApproval", searchResultCreditApproval);
			resultMap.put("startIndex", startIndex);				
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
}
