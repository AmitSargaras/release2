package com.integrosys.cms.ui.relationshipmgr;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This command lists the Relationship Manager
 * $Author:Dattatray Thorat 
 * @version $Revision: 1.0 $
 * @since $Date: 2011/02/18 11:32:23 
 */
public class ListRelationShipManagerCmd extends AbstractCommand implements ICommonEventConstant {

	
	private IRelationshipMgrProxyManager relationshipMgrProxyManager;

	/**
	 * @return the relationshipMgrProxyManager
	 */
	public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
		return relationshipMgrProxyManager;
	}

	/**
	 * @param relationshipMgrProxyManager the relationshipMgrProxyManager to set
	 */
	public void setRelationshipMgrProxyManager(
			IRelationshipMgrProxyManager relationshipMgrProxyManager) {
		this.relationshipMgrProxyManager = relationshipMgrProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public ListRelationShipManagerCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{"RMCode", "java.lang.String", REQUEST_SCOPE},
	            {"RMName", "java.lang.String", REQUEST_SCOPE},
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }
			});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
				{"RMCode", "java.lang.String", REQUEST_SCOPE},
	            {"RMName", "java.lang.String", REQUEST_SCOPE},	            
	            {"relationshipMgrList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
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
	        	String rmCode = (String) map.get("RMCode");
				String rmName = (String) map.get("RMName");
				String startInd = (String) map.get("startIndex");
				ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
				String loginUser = user.getLoginID();
				
				if( rmCode!= null && ! rmCode.trim().equals("") ){
					boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(rmCode);
					if( searchTextFlag == true){
						exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter","RelationshipMgr "));
						rmCode= "";
					}
				}
				if( rmName!= null && ! rmName.trim().equals("") ){
					boolean searchTextFlag = ASSTValidator.isValidAlphaNumStringWithSpace(rmName);
					if( searchTextFlag == true){
						exceptionMap.put("searchTextError", new ActionMessage("error.string.invalidCharacter","RelationshipMgr"));
						rmName= "";
					}
				}
				
	            SearchResult relationshipMgrList= getRelationshipMgrProxyManager().getRelationshipMgrList(rmCode,rmName);
	            
	            resultMap.put("loginUser",loginUser);
	            resultMap.put("startIndex", startInd);
	            resultMap.put("RMCode",rmCode);
	            resultMap.put("RMName", rmName);	            
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



