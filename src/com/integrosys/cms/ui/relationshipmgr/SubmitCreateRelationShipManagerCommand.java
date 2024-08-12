package com.integrosys.cms.ui.relationshipmgr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseCreation.bus.ICaseCreation;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBLocalCAD;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This command Edits the Relationship Manager selected for edition 
 * 
 * $Author: Dattatray Thorat
 * 
 * @version $Revision: 1.2 $
 * @since $Date: 2011/03/25 11:32:23 $ Tag: $Name: $
 */
public class SubmitCreateRelationShipManagerCommand extends AbstractCommand implements ICommonEventConstant {
	
	private IRelationshipMgrProxyManager relationshipMgrProxyManager;
	
	private IRelationshipMgrDAO relationshipMgrDAO;

	private  static boolean isRMCodeUnique = true;
	
	/**
	 * @return the isRMCodeUnique
	 */
	public static boolean isRMCodeUnique() {
		return isRMCodeUnique;
	}

	/**
	 * @param isRMCodeUnique the isRMCodeUnique to set
	 */
	public static void setRMCodeUnique(boolean isRMCodeUnique) {
		SubmitCreateRelationShipManagerCommand.isRMCodeUnique = isRMCodeUnique;
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

	public SubmitCreateRelationShipManagerCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{ "RMCode", "java.lang.String", REQUEST_SCOPE },
				{ "RelationshipMgrObj", "com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr", FORM_SCOPE },
				{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
	            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
    			{ "localCADList","java.util.List",REQUEST_SCOPE},
    			{ "localCADs","java.util.List",SERVICE_SCOPE},
		});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
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
	        	
	        	boolean isRMCodeUnique = true;
	        	boolean isRelationshipMgrNameUnique = true;
	        	boolean isEmpIdUnique = false;
	        	
				String rmCode = (String)map.get("RMCode");
				List localCADList =  (List) map.get("localCADList");
				List localCADs = (List) map.get("localCADs");
				
				if(rmCode!=null)
					isRMCodeUnique = getRelationshipMgrProxyManager().isRMCodeUnique(rmCode.trim());

				if(isRMCodeUnique != false){
					exceptionMap.put("relationshipMgrCodeError", new ActionMessage("error.string.rmcode.exist"));
					IRelationshipMgrTrxValue relationshipMgrTrxValue = null;
					resultMap.put("request.ITrxValue", relationshipMgrTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
	        	
	        	OBRelationshipMgr relationshipMgr = (OBRelationshipMgr) map.get("RelationshipMgrObj");
	        	
	        	String newRelationshipMgrName = relationshipMgr.getRelationshipMgrName();
	        	String empId  = relationshipMgr.getEmployeeId();
	        	
	        	relationshipMgr.setCpsId(relationshipMgr.getEmployeeId());
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IRelationshipMgrTrxValue trxValueIn = (OBRelationshipMgrTrxValue) map.get("IRelationshipMgrTrxValue");

				IRelationshipMgrTrxValue trxValueOut = new OBRelationshipMgrTrxValue();

					if (event.equals("maker_submit_create_relationship_mgr")) {
						// Start
						isRelationshipMgrNameUnique = getRelationshipMgrProxyManager().isRelationshipMgrNameUnique(newRelationshipMgrName.trim());
//						isEmpIdUnique = getRelationshipMgrProxyManager().isEmployeeIdUnique(empId.trim());
						
						//Validation removal
//						if(isRelationshipMgrNameUnique != false){
//							exceptionMap.put("relationshipMgrNameError", new ActionMessage("error.string.exist","RelationshipMgr Name"));
//							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//							return returnMap;
//						}
						
						
//						if(isEmpIdUnique){
//							exceptionMap.put("employeeIdDuplicateError", new ActionMessage("error.string.exist","Employee Id"));
//							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//							return returnMap;
//						}
						// End
						trxValueOut = getRelationshipMgrProxyManager().makerCreateRelationshipMgr(ctx, trxValueOut, relationshipMgr);
						if(null != localCADs) {
							Iterator itrNew = localCADs.iterator();
							while (itrNew.hasNext()) {
								ILocalCAD item= (ILocalCAD) itrNew.next();	
								
								ILocalCAD localCAD = new OBLocalCAD();
								List<ILocalCAD> localCADsList = new ArrayList();				
								
								localCADsList =  getRelationshipMgrDAO().getLocalCADs(rmCode);
								int count =0;
								for(int i=0;i<localCADsList.size();i++) {
									if(localCADsList.get(i).getLocalCADEmployeeCode().equals(item.getLocalCADEmployeeCode())) {
										count++;
									}
								}
								if(count == 0) {
								localCAD.setLocalCADEmployeeCode(item.getLocalCADEmployeeCode());
								localCAD.setLocalCADName(item.getLocalCADName());
								localCAD.setLocalCADEmailID(item.getLocalCADEmailID());
								localCAD.setLocalCADmobileNo(item.getLocalCADmobileNo());
								localCAD.setLocalCADSupervisorName(item.getLocalCADSupervisorName());
								localCAD.setLocalCADSupervisorEmail(item.getLocalCADSupervisorEmail());
								localCAD.setLocalCADSupervisorMobileNo(item.getLocalCADSupervisorMobileNo());
								localCAD.setRelationshipMgrID(rmCode);
								localCAD.setCreationDate(new Date());
								localCAD.setCreatedBy(ctx.getUser().getLoginID());
								localCAD.setLastUpdateDate(new Date());
								localCAD.setLastUpdateBy(ctx.getUser().getLoginID());
								localCAD.setStatus("PENDING_CREATE");
								localCAD.setDeprecated("N");
								
								getRelationshipMgrDAO().createLocalCAD("stagingLocalCAD", localCAD);
								}
						}
						}
					} else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getRelationshipMgrProxyManager().makerEditRejectedRelationshipMgr(ctx, trxValueIn, relationshipMgr);
					} 

					resultMap.put("request.ITrxValue", trxValueOut);
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