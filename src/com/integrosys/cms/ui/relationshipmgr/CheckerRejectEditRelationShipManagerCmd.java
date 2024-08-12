/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.relationshipmgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBLocalCAD;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * 
 * @author $Author: Dattatray Thorat $<br>
 * Command for checker to reject update by maker.
 * 
 */
public class CheckerRejectEditRelationShipManagerCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	public CheckerRejectEditRelationShipManagerCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"remarks", "java.lang.String", REQUEST_SCOPE},
				 { "TrxId", "java.lang.String", REQUEST_SCOPE },
					{ "localCADs","java.util.List",SERVICE_SCOPE},
					{ "removedLocalCADs","java.util.List",SERVICE_SCOPE},
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
				{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE}, 
				{ "request.ITrxValue", "com.integrosys.component.common.transaction.ICompTrxResult",REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE }
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		 	HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        HashMap exceptionMap = new HashMap();
	        try {
	            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
	            String trxId=(String) (map.get("TrxId"));
	            IRelationshipMgrTrxValue trxValueIn = (OBRelationshipMgrTrxValue) map.get("IRelationshipMgrTrxValue");
	            
				List localCADs = (List) map.get("localCADs");
				List removedLocalCADs = (List) map.get("removedLocalCADs");
	            String remarks = (String) map.get("remarks");
	            
	            if(remarks == null || remarks.equals("")){
	            	exceptionMap.put("remarksError", new ActionMessage("error.reject.remark"));
	            	IRelationshipMgrTrxValue trxValue = null;
	            	resultMap.put("TrxId", trxId); 
	            	resultMap.put("IRelationshipMgrTrxValue", trxValueIn);
	            	resultMap.put("request.ITrxValue", trxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
	            }else{
	            	ctx.setRemarks(remarks);
	            	IRelationshipMgrTrxValue trxValueOut = getRelationshipMgrProxyManager().checkerRejectRelationshipMgr(ctx, trxValueIn);
	            	
	            	if(null != localCADs) {
						Iterator itrNew = localCADs.iterator();
						while (itrNew.hasNext()) {
							ILocalCAD item= (ILocalCAD) itrNew.next();	
							List<ILocalCAD> localCADsList = new ArrayList();				
							
							localCADsList =  getRelationshipMgrDAO().getLocalCADs(trxValueIn.getStagingRelationshipMgr().getRelationshipMgrCode());
							int count =0;
							for(int i=0;i<localCADsList.size();i++) {
								if(localCADsList.get(i).getLocalCADEmployeeCode().equals(item.getLocalCADEmployeeCode())) {
									count++;
								}
							}
							if(count == 0) {
							ILocalCAD localCAD = new OBLocalCAD();
							
							/*localCAD.setLocalCADEmployeeCode(item.getLocalCADEmployeeCode());
							localCAD.setLocalCADName(item.getLocalCADName());
							localCAD.setLocalCADEmailID(item.getLocalCADEmailID());
							localCAD.setLocalCADmobileNo(item.getLocalCADmobileNo());
							localCAD.setLocalCADSupervisorName(item.getLocalCADSupervisorName());
							localCAD.setLocalCADSupervisorEmail(item.getLocalCADSupervisorEmail());
							localCAD.setLocalCADSupervisorMobileNo(item.getLocalCADSupervisorMobileNo());
							localCAD.setRelationshipMgrID(trxValueOut.getRelationshipMgr().getRelationshipMgrCode());
							localCAD.setCreationDate(item.getCreationDate());
							localCAD.setCreatedBy(item.getCreatedBy());
							localCAD.setLastUpdateDate(new Date());
							localCAD.setLastUpdateBy(ctx.getUser().getLoginID());
							localCAD.setStatus("INACTIVE");
							localCAD.setDeprecated("N");
							
							getRelationshipMgrDAO().createLocalCAD("actualLocalCAD", localCAD);*/
							}
	            	}
	            	resultMap.put("request.ITrxValue", trxValueOut);
	            }	
	            }
	        }catch (RelationshipMgrException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			}
	        catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	}

}
