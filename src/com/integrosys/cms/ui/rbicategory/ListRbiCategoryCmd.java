package com.integrosys.cms.ui.rbicategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.rbicategory.proxy.IRbiCategoryProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

/**
 * @author $Govind.Sahu$<br>
 *Command for list Rbi Category
*/
public class ListRbiCategoryCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	
	private IRbiCategoryProxyManager rbiCategoryProxy;

	

	/**
	 * @return the rbiCategoryProxy
	 */
	public IRbiCategoryProxyManager getRbiCategoryProxy() {
		return rbiCategoryProxy;
	}

	/**
	 * @param rbiCategoryProxy the rbiCategoryProxy to set
	 */
	public void setRbiCategoryProxy(IRbiCategoryProxyManager rbiCategoryProxy) {
		this.rbiCategoryProxy = rbiCategoryProxy;
	}

	public ListRbiCategoryCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "totalCount", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
				{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
				
				
			};
	}
	   public String[][] getResultDescriptor() {
	        return (new String[][]{
	                {"rbiCategoryList", "java.util.ArrayList", REQUEST_SCOPE},
					{ "event", "java.lang.String", REQUEST_SCOPE },
					 {"searchResultRbiCategory", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
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
	        int stindex = 0;
	        
	        try {
	        	String globalStartIndex = (String) map.get(IGlobalConstant.GLOBAL_CMSTRXSEARCH_START_INDEX);
	        	List rbiCategoryList = new ArrayList();
				startIndex = String.valueOf(stindex);
	        	
	        	  rbiCategoryList= (List)  getRbiCategoryProxy().getAllRbiCategoryList();
	              SearchResult searchResultRbiCategory = new SearchResult(Integer.parseInt(startIndex), 10, rbiCategoryList.size(), rbiCategoryList);
	            	
	                  resultMap.put("rbiCategoryList", rbiCategoryList);
	                  resultMap.put("searchResultRbiCategory", searchResultRbiCategory);
	                  resultMap.put("startIndex", startIndex);
	                  
	        }catch (RbiCategoryException ex) {
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



