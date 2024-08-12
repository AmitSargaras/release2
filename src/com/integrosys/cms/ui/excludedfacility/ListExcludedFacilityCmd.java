package com.integrosys.cms.ui.excludedfacility;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.excludedfacility.proxy.IExcludedFacilityProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

public class ListExcludedFacilityCmd extends AbstractCommand implements ICommonEventConstant{

	private IExcludedFacilityProxyManager excludedFacilityProxy;
	
	public IExcludedFacilityProxyManager getExcludedFacilityProxy() {
		return excludedFacilityProxy;
	}

	public void setExcludedFacilityProxy(IExcludedFacilityProxyManager excludedFacilityProxy) {
		this.excludedFacilityProxy = excludedFacilityProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public ListExcludedFacilityCmd() {
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ "type", "java.lang.String", REQUEST_SCOPE },
				{ "text", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				
				
			};
	}
	public String[][] getResultDescriptor() {
	        return (new String[][]{
	        		{"excludedFacilityList", "java.util.ArrayList", REQUEST_SCOPE},
					{ "event", "java.lang.String", REQUEST_SCOPE },
					{ "startIndex", "java.lang.String", REQUEST_SCOPE },
	                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
	        });
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        String startIndex = (String) map.get("startIndex");
        int stindex = 0;
       
        try {
        	SearchResult excludedFacilityList = new SearchResult();
        	String event = (String) (map.get("event"));
 			String type = (String) map.get("type");
 			String searchText = (String) map.get("text");
 			if( searchText!= null && ! searchText.trim().equals("") && type!= null && ! type.trim().equals("")  ){
 				if("excludedFacilityDescription".equals(type)){
 					if ( !(Validator.checkString(searchText.trim(), true, 1, 255).equals(Validator.ERROR_NONE))) {
 						exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
 	 					searchText= "";
 					}
 				}/*else if ( !(Validator.checkStringWithNoSpecialCharsAndSpace(searchText.trim(), true, 1, 40)
						.equals(Validator.ERROR_NONE))) {
 					exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
 					searchText= "";
				}*/
 					DefaultLogger.debug(this, "Type::: "+type+":serachText::"+searchText);
 					excludedFacilityList= (SearchResult)  getExcludedFacilityProxy().getSearchedExcludedFacility(type,searchText);
 					resultMap.put("excludedFacilityList", excludedFacilityList);
 					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
 			        return returnMap;
 					
 			}
 			
 				excludedFacilityList= (SearchResult)  getExcludedFacilityProxy().getAllActualExcludedFacility();
 			
             
            
            resultMap.put("excludedFacilityList", excludedFacilityList);
        }catch (ComponentException ex) {
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
