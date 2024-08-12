package com.integrosys.cms.ui.caseBranch;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.caseBranch.proxy.ICaseBranchProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

/**
 $Author: Abhijit R $
 Command for list CaseBranch
 */
public class ListCaseBranchCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	
	private ICaseBranchProxyManager caseBranchProxy;

	public ICaseBranchProxyManager getCaseBranchProxy() {
		return caseBranchProxy;
	}

	public void setCaseBranchProxy(ICaseBranchProxyManager caseBranchProxy) {
		this.caseBranchProxy = caseBranchProxy;
	}

	public ListCaseBranchCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ "go", "java.lang.String", REQUEST_SCOPE },
				{ "branchCodeSearch", "java.lang.String", REQUEST_SCOPE },
				{ "branchNameSearch", "java.lang.String", REQUEST_SCOPE },
				{ "branchCodeSearchSession", "java.lang.String", SERVICE_SCOPE },
				{ "branchNameSearchSession", "java.lang.String", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"caseBranchList", "java.util.ArrayList", REQUEST_SCOPE},
	                { "branchCodeSearch", "java.lang.String", REQUEST_SCOPE },
					{ "branchNameSearch", "java.lang.String", REQUEST_SCOPE },
					{ "branchCodeSearchSession", "java.lang.String", SERVICE_SCOPE },
					{ "branchNameSearchSession", "java.lang.String", SERVICE_SCOPE },
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{ "startIndex", "java.lang.String", REQUEST_SCOPE },
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
	        String startIndex = (String) map.get("startIndex");
	        String branchCodeSearch = (String) map.get("branchCodeSearch");
	        String branchNameSearch = (String) map.get("branchNameSearch");
	        String branchCodeSearchSession = (String) map.get("branchCodeSearchSession");
	        String branchNameSearchSession = (String) map.get("branchNameSearchSession");
	        String event = (String) map.get("event");
	        String go = (String) map.get("go");
	        if(null==go)
	        	go="";
	        if(null==branchNameSearch)
        		branchNameSearch="";
        	if(null==branchCodeSearch)
        		branchCodeSearch="";
        	if(null==branchNameSearchSession)
         		branchNameSearchSession="";
         	if(null==branchCodeSearchSession)
         		branchCodeSearchSession="";
	        
         	int stindex = 0;
	        
	        // remove all values from session if collateral master is freshly entered
	    	if((event.equals("maker_list_caseBranch")||event.equals("checker_list_caseBranch"))&&go.equals(""))
	    		branchCodeSearch=branchCodeSearchSession=branchNameSearch=branchNameSearchSession="";
	    			//--> END removing values from session.
	    	// if go button is clicked then put values in session
	    	if(go!=null){
	    	if(go.equalsIgnoreCase("y")){
	    		branchCodeSearchSession=branchCodeSearch;
	    		branchNameSearchSession=branchNameSearch;
	    	}
	    	}
	    	
	    	// get values from session.
	    	branchCodeSearch=branchCodeSearchSession;
    		branchNameSearch=branchNameSearchSession;
    		
    		if(!Validator.validateNumber(branchCodeSearch, true,1, 9999999999999d) ){
    			branchCodeSearch=branchCodeSearchSession="";
			}
    		if(ASSTValidator.isValidDirectorName(branchNameSearch)){
    			branchNameSearch=branchNameSearchSession="";
			}
	        try {
	        	String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
	        	SearchResult caseBranchList = new SearchResult();
	        	if("".equals(branchCodeSearch)&&"".equals(branchNameSearch))
	            caseBranchList= (SearchResult)  getCaseBranchProxy().getAllActualCaseBranch();
	        	else
	        	caseBranchList= (SearchResult)  getCaseBranchProxy().getAllFilteredActualCaseBranch(branchCodeSearch,branchNameSearch);	
	            if (StringUtils.isBlank(startIndex)) {
					if (StringUtils.isBlank(globalStartIndex) || "null".equals(globalStartIndex.trim())) {
						stindex = 0;
						startIndex = String.valueOf(stindex);
						resultMap.put("startIndex", startIndex);

					}
					else {
						stindex = Integer.parseInt(globalStartIndex);
						startIndex = globalStartIndex;
						resultMap.put("startIndex", startIndex);
					}
				}
				else {
					stindex = Integer.parseInt(startIndex);
					resultMap.put("startIndex", startIndex);
				}

	                  resultMap.put("caseBranchList", caseBranchList);
	                  
	                  resultMap.put("branchCodeSearch", branchCodeSearch);
	                  resultMap.put("branchCodeSearchSession", branchCodeSearchSession);
	                  resultMap.put("branchNameSearch", branchNameSearch);
	                  resultMap.put("branchNameSearchSession", branchNameSearchSession);
	        }catch (CaseBranchException ex) {
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



