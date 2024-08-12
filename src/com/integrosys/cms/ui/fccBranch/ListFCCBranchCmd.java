package com.integrosys.cms.ui.fccBranch;

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
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.proxy.IFCCBranchProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

/**
 $Author: Abhijit R $
 Command for list FCCBranch
 */
public class ListFCCBranchCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	
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

	public ListFCCBranchCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				{ "type", "java.lang.String", REQUEST_SCOPE },
				{ "text", "java.lang.String", REQUEST_SCOPE } 
				
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"fccBranchList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE },
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
	       
	        String event = (String) map.get("event");
	       
	        
         	int stindex = 0;
	        
	        // remove all values from session if collateral master is freshly entered
	    	
	        try {
	        	String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
	        	SearchResult fccBranchList = new SearchResult();
	        	HashMap exceptionMap = new HashMap();
	        	String searchText = (String) map.get("text");
	        	String type = (String) map.get("type");
				
				if( searchText!= null && ! searchText.trim().equals("") ){
					boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(searchText);
					if( searchTextFlag == true){
						exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
						searchText= "";
					}				
				}			
				
	        	if(null == searchText ||"".equals(searchText))
	            fccBranchList= (SearchResult)  getFccBranchProxy().getAllActualFCCBranch();
	        	else
	        	fccBranchList= (SearchResult)  getFccBranchProxy().getAllFilteredActualFCCBranch(type,searchText);	
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

	                  resultMap.put("fccBranchList", fccBranchList);
	                  
	                 
	        }catch (FCCBranchException ex) {
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



