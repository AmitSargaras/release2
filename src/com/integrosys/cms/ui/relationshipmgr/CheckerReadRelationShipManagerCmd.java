/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.relationshipmgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBLocalCAD;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 *$Author: Dattatray Thorat $
 *Command for checker to read Relationship Manager Trx value
 */
public class CheckerReadRelationShipManagerCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IRelationshipMgrProxyManager relationshipMgrProxyManager;

	private IOtherBankProxyManager otherBankProxyManager;
	
	private IRelationshipMgrDAO relationshipMgrDAO;
	
	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

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
	public CheckerReadRelationShipManagerCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "removedLocalCADs","java.util.List",SERVICE_SCOPE},
				{ "localCADs","java.util.List",SERVICE_SCOPE},
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "RelationshipMgrObj", "com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr", FORM_SCOPE },
				{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "regionList","java.util.List",SERVICE_SCOPE},
				{ "removedLocalCADs","java.util.List",SERVICE_SCOPE},
				{ "localCADs","java.util.List",SERVICE_SCOPE},
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
	
			String rmCode=(String) (map.get("TrxId"));
			String event=(String) (map.get("event"));
			List localCADsOld = (List) map.get("localCADs");
			List removedLocalCADsOld = (List) map.get("removedLocalCADs");
			List localCADs = new ArrayList();
			List localCADsActual = new ArrayList();
			List stagingLocalCADs = new ArrayList();
			List removedLocalCADs = new ArrayList();
			List localCADsEmployeeCodeList = new ArrayList();
			boolean flag = false;
			boolean flagNew = false;
			
			IRelationshipMgrTrxValue trxValue = (OBRelationshipMgrTrxValue) getRelationshipMgrProxyManager().getRelationshipMgrByTrxID(rmCode);

			IRelationshipMgr relationshipMgr = (OBRelationshipMgr) trxValue.getStagingRelationshipMgr();
			
			if(null != relationshipMgr) {
				
				removedLocalCADs =  getRelationshipMgrDAO().getStagingDeletedLocalCADs(relationshipMgr.getRelationshipMgrCode());
				
				if("checker_edit_read".equals(event) || "checker_process_create".equals(event) ) {
				localCADs =  getRelationshipMgrDAO().getStagingCreatedAndDeletedLocalCADs(relationshipMgr.getRelationshipMgrCode());
				}
				else if("maker_prepare_close".equals(event) || "maker_prepare_resubmit".equals(event)) {
					localCADs =  getRelationshipMgrDAO().getStagingLocalCADs(relationshipMgr.getRelationshipMgrCode());
					
					localCADsActual =  getRelationshipMgrDAO().getLocalCADs(relationshipMgr.getRelationshipMgrCode());
					
					
					if (localCADsActual != null) {
						if(localCADs == null) {
							localCADs = new ArrayList();
						}
						for (int j = 0; j < localCADsActual.size(); j++) {
							OBLocalCAD localCADsActual1 = (OBLocalCAD) localCADsActual.get(j);
								localCADs.add(localCADsActual1);
						}
					}
					
					if (removedLocalCADs != null) {
//						localCADsEmployeeCodeList = localCADs;
						for (int i = 0; i < localCADs.size(); i++) {
							OBLocalCAD localCAD = (OBLocalCAD) localCADs.get(i);
							for (int j = 0; j < removedLocalCADs.size(); j++) {
								OBLocalCAD removedLocalCADs1 = (OBLocalCAD) removedLocalCADs.get(j);
								if (localCAD.getLocalCADEmployeeCode()
										.equals(removedLocalCADs1.getLocalCADEmployeeCode())) {
//									localCADs.remove(i);
									flag = true;
								}
							}
							
							if(flag == false) {
								localCADsEmployeeCodeList.add(localCAD);
							}
							flag = false;
						}
					}

					localCADs = localCADsEmployeeCodeList;
					
				}
				else {
					localCADs =  getRelationshipMgrDAO().getStagingLocalCADs(relationshipMgr.getRelationshipMgrCode());
				}
				
				
				
				if("checker_edit_read".equals(event) || "checker_process_create".equals(event)) {
					localCADsActual =  getRelationshipMgrDAO().getLocalCADs(relationshipMgr.getRelationshipMgrCode());
					
					if (localCADsActual != null) {
						if(localCADs == null) {
							localCADs = new ArrayList();
						}
						for (int j = 0; j < localCADsActual.size(); j++) {
							OBLocalCAD localCADsActual1 = (OBLocalCAD) localCADsActual.get(j);
							for (int i = 0; i < localCADs.size(); i++) {
								OBLocalCAD localCAD = (OBLocalCAD) localCADs.get(i);
								if(localCADsActual1.getLocalCADEmployeeCode().equals(localCAD.getLocalCADEmployeeCode())) {
									flagNew = true;
								}
							}
							if(flagNew == false) {
								localCADs.add(localCADsActual1);
							}
							flagNew = false;
						}
					}
				}
				
				
				if ("maker_update_save_process".equals(event) || "maker_draft_close_process".equals(event)) {
					localCADs = getRelationshipMgrDAO().getLocalCADs(relationshipMgr.getRelationshipMgrCode());
					stagingLocalCADs = getRelationshipMgrDAO()
							.getStagingLocalCADs(relationshipMgr.getRelationshipMgrCode());
					
					if (removedLocalCADs != null) {
//						localCADsEmployeeCodeList = localCADs;
						for (int i = 0; i < localCADs.size(); i++) {
							OBLocalCAD localCAD = (OBLocalCAD) localCADs.get(i);
							for (int j = 0; j < removedLocalCADs.size(); j++) {
								OBLocalCAD removedLocalCADs1 = (OBLocalCAD) removedLocalCADs.get(j);
								if (localCAD.getLocalCADEmployeeCode()
										.equals(removedLocalCADs1.getLocalCADEmployeeCode())) {
//									localCADs.remove(i);
									flag = true;
								}
							}
							
							if(flag == false) {
								localCADsEmployeeCodeList.add(localCAD);
							}
							flag = false;
							
						}
					}

					localCADs = localCADsEmployeeCodeList;
					
					if (stagingLocalCADs != null) {
						if(localCADs == null) {
							localCADs = new ArrayList();
						}
						for (int j = 0; j < stagingLocalCADs.size(); j++) {
							OBLocalCAD stagingLocalCADs1 = (OBLocalCAD) stagingLocalCADs.get(j);
							localCADs.add(stagingLocalCADs1);
						}
					}

				}}
						
			resultMap.put("regionList", getRegionList());
			resultMap.put("TrxId", rmCode);
			resultMap.put("event", event);
			resultMap.put("IRelationshipMgrTrxValue", trxValue);
			resultMap.put("RelationshipMgrObj", relationshipMgr);
			resultMap.put("localCADs", localCADs);
			resultMap.put("removedLocalCADs", removedLocalCADs);
		} catch (RelationshipMgrException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
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
