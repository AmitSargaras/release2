package com.integrosys.cms.ui.relationshipmgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.limit.bus.LimitDAO;

/**
 * This command Prepares the Relationship Manager before deleting
 * $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 */
public class PrepareDeleteRelationShipManagerCommand extends AbstractCommand implements ICommonEventConstant {
	
	

	private IRelationshipMgrProxyManager relationshipMgrProxyManager;
	private IRelationshipMgrDAO relationshipMgrDAO;

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
		
		
	public IRelationshipMgrDAO getRelationshipMgrDAO() {
			return relationshipMgrDAO;
		}

		public void setRelationshipMgrDAO(IRelationshipMgrDAO relationshipMgrDAO) {
			this.relationshipMgrDAO = relationshipMgrDAO;
		}

	/**
	 * Default Constructor
	 */

	public PrepareDeleteRelationShipManagerCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"RMId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"RMCode", "java.lang.String", REQUEST_SCOPE},
	            {"RMName", "java.lang.String", REQUEST_SCOPE},
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"RelationshipMgrObj", "com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
				{ "localCADs","java.util.List",SERVICE_SCOPE},
	            {"RMId", "java.lang.String", REQUEST_SCOPE},
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"RMCode", "java.lang.String", REQUEST_SCOPE},
	            {"RMName", "java.lang.String", REQUEST_SCOPE},
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
	        try {
	        	boolean flag = false;
	        	String id = (String) map.get("RMId");
	        	String event = (String) map.get("event");
	        	IRelationshipMgrTrxValue relationshipMgrTrxValue = null;
	        	IRelationshipMgr relationshipMgr = new OBRelationshipMgr();
	        	
	        	relationshipMgrTrxValue = getRelationshipMgrProxyManager().getRelationshipMgrTrxValue(Long.parseLong(id));
	        	relationshipMgr = relationshipMgrTrxValue.getRelationshipMgr();
	        	
	        	if(relationshipMgrTrxValue.getStatus().equals("PENDING_UPDATE") || relationshipMgrTrxValue.getStatus().equals("PENDING_DELETE") 
	        			|| relationshipMgrTrxValue.getStatus().equals("PENDING_CREATE")
	        			|| relationshipMgrTrxValue.getStatus().equals("REJECTED")){
					resultMap.put("wip", "wip");
				}
	        	LimitDAO limitDao = new LimitDAO();
	    		try {
	    		String migratedFlag = "N";	
	    		boolean status = false;	
	    		 status = limitDao.getCAMMigreted("CMS_RELATIONSHIP_MGR",Long.parseLong(id),"ID");
	    		
	    		if(status)
	    		{
	    			migratedFlag= "Y";
	    		}
	    		resultMap.put("migratedFlag", migratedFlag);
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		List localCADs = new ArrayList();
								
				localCADs =  getRelationshipMgrDAO().getLocalCADs(relationshipMgr.getRelationshipMgrCode());
				
				resultMap.put("localCADs", localCADs);
	            resultMap.put("event", event);
	            resultMap.put("RelationshipMgrObj", relationshipMgr);
	            resultMap.put("IRelationshipMgrTrxValue", relationshipMgrTrxValue);
				resultMap.put("RMId", id);
	        	resultMap.put("startIndex", map.get("startIndex"));	 
	        	resultMap.put("RMCode", map.get("RMCode"));	 
	        	resultMap.put("RMName", map.get("RMName"));	 
	        } catch (RelationshipMgrException obe) {
	        	CommandProcessingException cpe = new CommandProcessingException(obe.getMessage());
				cpe.initCause(obe);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }

	}



