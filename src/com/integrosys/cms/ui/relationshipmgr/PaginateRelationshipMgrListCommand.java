package com.integrosys.cms.ui.relationshipmgr;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class PaginateRelationshipMgrListCommand extends AbstractCommand {

	private IRelationshipMgrProxyManager relationshipMgrProxyManager;

	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	public void setRelationshipMgrProxyManager(
			IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public PaginateRelationshipMgrListCommand() {
		
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"RMCode", "java.lang.String", REQUEST_SCOPE},
	            {"RMName", "java.lang.String", REQUEST_SCOPE},	            
	            {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }
			});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"relationshipMgrList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"RMCode", "java.lang.String", REQUEST_SCOPE},
	            {"RMName", "java.lang.String", REQUEST_SCOPE},	            
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"loginUser", "java.lang.String", SERVICE_SCOPE}
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
	        	String startInd = (String) map.get("startIndex");
				ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
				String loginUser = user.getLoginID();
				String rmCode = (String) map.get("RMCode");
				String rmName = (String) map.get("RMName");
				
				if( rmCode == null || rmCode.equals("null") )
					rmCode = "";
				
				if( rmName == null || rmName.equals("null") )
					rmName = "";
				
	            SearchResult relationshipMgrList= getRelationshipMgrProxyManager().getRelationshipMgrList(rmCode,rmName);
	            resultMap.put("RMCode",rmCode);
	            resultMap.put("RMName", rmName);
	            resultMap.put("loginUser",loginUser);
	            resultMap.put("startIndex", startInd);
	            resultMap.put("relationshipMgrList", relationshipMgrList);
	        } catch (RelationshipMgrException rme) {
	            DefaultLogger.error(this, "got exception in doExecute" ,rme);
	        	CommandProcessingException cpe = new CommandProcessingException(rme.getMessage());
				cpe.initCause(rme);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	        return returnMap;
	    }
}
