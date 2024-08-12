package com.integrosys.cms.ui.goodsMaster;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.goodsMaster.proxy.IGoodsMasterProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

public class ListGoodsMasterCmd extends AbstractCommand implements ICommonEventConstant{

private IGoodsMasterProxyManager goodsMasterProxy;
	
	public IGoodsMasterProxyManager getGoodsMasterProxy() {
		return goodsMasterProxy;
	}

	public void setGoodsMasterProxy(IGoodsMasterProxyManager goodsMasterProxy) {
		this.goodsMasterProxy = goodsMasterProxy;
	}
	
	/**
	 * Default Constructor
	 */
	public ListGoodsMasterCmd() {
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
				{ "event", "java.lang.String", REQUEST_SCOPE },
			};
	}
	public String[][] getResultDescriptor() {
	        return (new String[][]{
	        		{"goodsMasterList", "java.util.ArrayList", REQUEST_SCOPE},
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
        	SearchResult goodsMasterList = new SearchResult();
        	String event = (String) (map.get("event"));
 			String type = (String) map.get("type");
 			String searchText = (String) map.get("text");
 			String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
        	
 			if( searchText!= null && ! searchText.trim().equals("") ){
				boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(searchText);
				if( searchTextFlag == true){
					exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter"));
					searchText= "";
				}				
			}
 		
 			if(null == searchText ||"".equals(searchText))
 				goodsMasterList= (SearchResult)   getGoodsMasterProxy().getAllActualGoodsMaster();
	        else
	        	goodsMasterList= (SearchResult)  getGoodsMasterProxy().getAllFilteredActualGoodsMaster(type,searchText);	
 			
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
 			
 			resultMap.put("goodsMasterList", goodsMasterList);
 			
        }catch (GoodsMasterException ex) {
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
