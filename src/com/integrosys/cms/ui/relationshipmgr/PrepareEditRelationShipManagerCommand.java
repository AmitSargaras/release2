package com.integrosys.cms.ui.relationshipmgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.app.limit.bus.LimitDAO;

/**
 * This command Prepares the Relationship Manager before editing
 * $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 */
public class PrepareEditRelationShipManagerCommand extends AbstractCommand implements ICommonEventConstant {
	
	private IRelationshipMgrProxyManager relationshipMgrProxyManager;
	
	private IRelationshipMgrDAO relationshipMgrDAO;

	private IOtherBankProxyManager otherBankProxyManager;
	
	/**
	 * @return the otherBankProxyManager
	 */
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	/**
	 * @param otherBankProxyManager the otherBankProxyManager to set
	 */
	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}
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

	public PrepareEditRelationShipManagerCommand() {
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
				{ "RelationshipMgrObj", "com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr", FORM_SCOPE },
				{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            { "regionList","java.util.List",SERVICE_SCOPE},
				{ "localCADs","java.util.List",SERVICE_SCOPE},
	            {"RMId", "java.lang.String", REQUEST_SCOPE},
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"RMCode", "java.lang.String", REQUEST_SCOPE},
	            {"RMName", "java.lang.String", REQUEST_SCOPE},
	            { "RelationshipMgrObjNew", "com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr", SERVICE_SCOPE },
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
	        	String id = (String) map.get("RMId");
	        	String event = (String) map.get("event");
	        	
	        	IRelationshipMgrTrxValue relationshipMgrTrxValue = getRelationshipMgrProxyManager().getRelationshipMgrTrxValue(Long.parseLong(id));
	        	IRelationshipMgr relationshipMgr = relationshipMgrTrxValue.getRelationshipMgr();
	        	
	        	if(relationshipMgrTrxValue.getStatus().equals("PENDING_UPDATE") || relationshipMgrTrxValue.getStatus().equals("PENDING_DELETE") 
	        			|| relationshipMgrTrxValue.getStatus().equals("DRAFT")
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
	        	resultMap.put("regionList", getRegionList());
	            resultMap.put("event", event);
	            resultMap.put("RelationshipMgrObj", relationshipMgr);
	            resultMap.put("RelationshipMgrObjNew", relationshipMgr);
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
	    
	    private List getRegionList() {
			List lbValList = new ArrayList();
			try {
				List idList = (List) getRelationshipMgrProxyManager().getRegionList(PropertyManager.getValue("clims.application.country"));				
			
				for (int i = 0; i < idList.size(); i++) {
					IRegion region = (IRegion)idList.get(i);
						String id = Long.toString(region.getIdRegion());
						String val = region.getRegionName();
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(lvBean);
				}
			} catch (Exception ex) {
			}
			return CommonUtil.sortDropdown(lbValList);
		}

	}



