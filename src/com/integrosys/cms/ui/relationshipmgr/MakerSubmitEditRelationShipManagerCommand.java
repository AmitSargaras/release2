package com.integrosys.cms.ui.relationshipmgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
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
 */
public class MakerSubmitEditRelationShipManagerCommand extends AbstractCommand implements ICommonEventConstant {


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

	public MakerSubmitEditRelationShipManagerCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{ "RelationshipMgrObj", "com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr", FORM_SCOPE },
				{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
	            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
    			{ "localCADList","java.util.List",SERVICE_SCOPE},
    			{ "localCADs","java.util.List",SERVICE_SCOPE},
    			{ "removedLocalCADs","java.util.List",SERVICE_SCOPE}
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
	        	boolean isRelationshipMgrNameUnique = false;
	        	boolean isEmpIdUnique = false;
	        	String empId = "";
	        	OBRelationshipMgr relationshipMgr = (OBRelationshipMgr) map.get("RelationshipMgrObj");
	        	ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
	        	
//	        	String rmCode = relationshipMgr.getRelationshipMgrCode();
	        	String newRelationshipMgrName = relationshipMgr.getRelationshipMgrName();
	        	String newEmployeeId = relationshipMgr.getEmployeeId();
				String oldRelationshipMgrName = "";
				List localCADList =  (List) map.get("localCADList");
				List localCADs = (List) map.get("localCADs");
				List removedLocalCADs = (List) map.get("removedLocalCADs");
				relationshipMgr.setCpsId(relationshipMgr.getEmployeeId());
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IRelationshipMgrTrxValue trxValueIn = (OBRelationshipMgrTrxValue) map.get("IRelationshipMgrTrxValue");
				IRelationshipMgrTrxValue trxValueOut = new OBRelationshipMgrTrxValue();
				
				if(trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION)){//add - save - maker process - submit 
					/*	Commented by Sandeep Shinde	*/
					
					/*if(rmCode!=null)
						isRMCodeUnique = getRelationshipMgrProxyManager().isRMCodeUnique(rmCode.trim());

					if(isRMCodeUnique != false){
						exceptionMap.put("relationshipMgrCodeError", new ActionMessage("error.string.rmcode.exist"));
						IRelationshipMgrTrxValue relationshipMgrTrxValue = null;
						resultMap.put("request.ITrxValue", relationshipMgrTrxValue);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}*/
					
					// Start
					oldRelationshipMgrName = trxValueIn.getStagingRelationshipMgr().getRelationshipMgrName();
					if( ! oldRelationshipMgrName.equals(newRelationshipMgrName) )
						isRelationshipMgrNameUnique = getRelationshipMgrProxyManager().isRelationshipMgrNameUnique(newRelationshipMgrName.trim());
					
//					empId = trxValueIn.getStagingRelationshipMgr().getEmployeeId();
//					if( ! empId.equals(newEmployeeId) )
//	        		isEmpIdUnique = getRelationshipMgrProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
	        		
//					if(isRelationshipMgrNameUnique != false){
//						exceptionMap.put("relationshipMgrNameError", new ActionMessage("error.string.exist","RelationshipMgr Name"));
//						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//						return returnMap;
//					}
					
//					if(isEmpIdUnique){
//						exceptionMap.put("employeeIdDuplicateError", new ActionMessage("error.string.exist","Employee Id"));
//						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//						return returnMap;
//					}
					// End
					
					
						trxValueOut = getRelationshipMgrProxyManager().makerUpdateCreateRelationshipMgr(ctx, trxValueIn, relationshipMgr);
						
						
						if(null != localCADs) {
							Iterator itrNew = localCADs.iterator();
							while (itrNew.hasNext()) {
								ILocalCAD item= (ILocalCAD) itrNew.next();	
								List<ILocalCAD> localCADsList = new ArrayList();				
								
								localCADsList =  getRelationshipMgrDAO().getLocalCADs(relationshipMgr.getRelationshipMgrCode());
								int count =0;
								for(int i=0;i<localCADsList.size();i++) {
									if(localCADsList.get(i).getLocalCADEmployeeCode().equals(item.getLocalCADEmployeeCode())) {
										count++;
									}
								}
								
								if(count==0 && (!"PENDING_CREATE".equals(item.getStatus()))) {
								ILocalCAD localCAD = new OBLocalCAD();
								
								
								localCAD.setLocalCADEmployeeCode(item.getLocalCADEmployeeCode());
								localCAD.setLocalCADName(item.getLocalCADName());
								localCAD.setLocalCADEmailID(item.getLocalCADEmailID());
								localCAD.setLocalCADmobileNo(item.getLocalCADmobileNo());
								localCAD.setLocalCADSupervisorName(item.getLocalCADSupervisorName());
								localCAD.setLocalCADSupervisorEmail(item.getLocalCADSupervisorEmail());
								localCAD.setLocalCADSupervisorMobileNo(item.getLocalCADSupervisorMobileNo());
								localCAD.setRelationshipMgrID(relationshipMgr.getRelationshipMgrCode());
								localCAD.setCreationDate(new Date());
								localCAD.setCreatedBy(ctx.getUser().getLoginID());
								localCAD.setLastUpdateDate(new Date());
								localCAD.setLastUpdateBy(ctx.getUser().getLoginID());
//								localCAD.setStatus("ACTIVE");
								localCAD.setStatus("PENDING_CREATE");
								localCAD.setDeprecated("N");
								
								getRelationshipMgrDAO().createLocalCAD("stagingLocalCAD", localCAD);
								}
							}
						}
						
						
						if(null != removedLocalCADs) {
							Iterator itrNew = removedLocalCADs.iterator();
							while (itrNew.hasNext()) {
								ILocalCAD item= (ILocalCAD) itrNew.next();	
								List<ILocalCAD> localCADsList = new ArrayList();				
								
								localCADsList =  getRelationshipMgrDAO().getLocalCADs(relationshipMgr.getRelationshipMgrCode());
								int count =0;
								for(int i=0;i<localCADsList.size();i++) {
									if(localCADsList.get(i).getLocalCADEmployeeCode().equals(item.getLocalCADEmployeeCode())) {
										count++;
									}
								}
								
								if(count > 0 && (!"PENDING_DELETE".equals(item.getStatus()))) {
								ILocalCAD localCAD = new OBLocalCAD();
								
								localCAD.setLocalCADEmployeeCode(item.getLocalCADEmployeeCode());
								localCAD.setLocalCADName(item.getLocalCADName());
								localCAD.setLocalCADEmailID(item.getLocalCADEmailID());
								localCAD.setLocalCADmobileNo(item.getLocalCADmobileNo());
								localCAD.setLocalCADSupervisorName(item.getLocalCADSupervisorName());
								localCAD.setLocalCADSupervisorEmail(item.getLocalCADSupervisorEmail());
								localCAD.setLocalCADSupervisorMobileNo(item.getLocalCADSupervisorMobileNo());
								localCAD.setRelationshipMgrID(relationshipMgr.getRelationshipMgrCode());
								localCAD.setCreationDate(new Date());
								localCAD.setCreatedBy(ctx.getUser().getLoginID());
								localCAD.setLastUpdateDate(new Date());
								localCAD.setLastUpdateBy(ctx.getUser().getLoginID());
								localCAD.setStatus("PENDING_DELETE");
								localCAD.setDeprecated("N");
								
								getRelationshipMgrDAO().createLocalCAD("stagingLocalCAD", localCAD);
								}
							}
						}
						
						
						
						
						
						
				}else{
					if (event.equals("maker_submit_edit") || event.equals("maker_confirm_resubmit_update")) {

						// Start
						oldRelationshipMgrName = trxValueIn.getRelationshipMgr().getRelationshipMgrName();
						if( ! oldRelationshipMgrName.equals(newRelationshipMgrName) )
							isRelationshipMgrNameUnique = getRelationshipMgrProxyManager().isRelationshipMgrNameUnique(newRelationshipMgrName.trim());
						
//						empId = trxValueIn.getStagingRelationshipMgr().getEmployeeId();
						//System.out.println("empId:"+empId+" newEmployeeId:"+newEmployeeId);
//						if( null!=empId &&  (! empId.equals(newEmployeeId) ))
//			        		isEmpIdUnique = getRelationshipMgrProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
		        		
		        		
		        		
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
						
						trxValueOut = getRelationshipMgrProxyManager().makerUpdateRelationshipMgr(ctx, trxValueIn, relationshipMgr);
					}else if (event.equals("maker_save_update_relationship_mgr")){
						
						// Start						
						oldRelationshipMgrName = trxValueIn.getRelationshipMgr().getRelationshipMgrName();
						if( ! oldRelationshipMgrName.equals(newRelationshipMgrName) )
							isRelationshipMgrNameUnique = getRelationshipMgrProxyManager().isRelationshipMgrNameUnique(newRelationshipMgrName.trim());
						
//						empId = trxValueIn.getStagingRelationshipMgr().getEmployeeId();
//						if( ! empId.equals(newEmployeeId) )
//		        		isEmpIdUnique = getRelationshipMgrProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
		        		
		        		
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
						
						trxValueOut = getRelationshipMgrProxyManager().makerUpdateSaveRelationshipMgr(ctx, trxValueIn, relationshipMgr);
					}else {
						// event is  maker_confirm_resubmit_edit
						
						// Start
						oldRelationshipMgrName = trxValueIn.getStagingRelationshipMgr().getRelationshipMgrName();
						if( ! oldRelationshipMgrName.equals(newRelationshipMgrName) )
							isRelationshipMgrNameUnique = getRelationshipMgrProxyManager().isRelationshipMgrNameUnique(newRelationshipMgrName.trim());
						
//						empId = trxValueIn.getStagingRelationshipMgr().getEmployeeId();
//						if( ! empId.equals(newEmployeeId) )
//			        		isEmpIdUnique = getRelationshipMgrProxyManager().isEmployeeIdUnique(newEmployeeId.trim());
			        		
		        		
		        		
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
						
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getRelationshipMgrProxyManager().makerEditRejectedRelationshipMgr(ctx, trxValueIn, relationshipMgr);
					} 
					
					String localCADStatus = "ACTIVE";
				      
					if( "PENDING_DELETE".equalsIgnoreCase(trxValueIn.getStatus()) ) {
						localCADStatus = "INACTIVE";
					}
					
					if(null != localCADs) {
						Iterator itrNew = localCADs.iterator();
						while (itrNew.hasNext()) {
							ILocalCAD item= (ILocalCAD) itrNew.next();	
							List<ILocalCAD> localCADsList = new ArrayList();				
							
							localCADsList =  getRelationshipMgrDAO().getLocalCADs(relationshipMgr.getRelationshipMgrCode());
							int count =0;
							for(int i=0;i<localCADsList.size();i++) {
								if(localCADsList.get(i).getLocalCADEmployeeCode().equals(item.getLocalCADEmployeeCode())) {
									count++;
								}
							}
							
							if(count==0 && (!"PENDING_CREATE".equals(item.getStatus()))) {
							ILocalCAD localCAD = new OBLocalCAD();
							
							
							localCAD.setLocalCADEmployeeCode(item.getLocalCADEmployeeCode());
							localCAD.setLocalCADName(item.getLocalCADName());
							localCAD.setLocalCADEmailID(item.getLocalCADEmailID());
							localCAD.setLocalCADmobileNo(item.getLocalCADmobileNo());
							localCAD.setLocalCADSupervisorName(item.getLocalCADSupervisorName());
							localCAD.setLocalCADSupervisorEmail(item.getLocalCADSupervisorEmail());
							localCAD.setLocalCADSupervisorMobileNo(item.getLocalCADSupervisorMobileNo());
							localCAD.setRelationshipMgrID(relationshipMgr.getRelationshipMgrCode());
							localCAD.setCreationDate(new Date());
							localCAD.setCreatedBy(ctx.getUser().getLoginID());
							localCAD.setLastUpdateDate(new Date());
							localCAD.setLastUpdateBy(ctx.getUser().getLoginID());
//							localCAD.setStatus("ACTIVE");
							localCAD.setStatus("PENDING_CREATE");
							localCAD.setDeprecated("N");
							
							getRelationshipMgrDAO().createLocalCAD("stagingLocalCAD", localCAD);
							}
						}
					}
					
					
					if(null != removedLocalCADs) {
						Iterator itrNew = removedLocalCADs.iterator();
						while (itrNew.hasNext()) {
							ILocalCAD item= (ILocalCAD) itrNew.next();	
							List<ILocalCAD> localCADsList = new ArrayList();				
							
							localCADsList =  getRelationshipMgrDAO().getLocalCADs(relationshipMgr.getRelationshipMgrCode());
							int count =0;
							for(int i=0;i<localCADsList.size();i++) {
								if(localCADsList.get(i).getLocalCADEmployeeCode().equals(item.getLocalCADEmployeeCode())) {
									count++;
								}
							}
							
							if(count > 0 && (!"PENDING_DELETE".equals(item.getStatus()))) {
							ILocalCAD localCAD = new OBLocalCAD();
							
							localCAD.setLocalCADEmployeeCode(item.getLocalCADEmployeeCode());
							localCAD.setLocalCADName(item.getLocalCADName());
							localCAD.setLocalCADEmailID(item.getLocalCADEmailID());
							localCAD.setLocalCADmobileNo(item.getLocalCADmobileNo());
							localCAD.setLocalCADSupervisorName(item.getLocalCADSupervisorName());
							localCAD.setLocalCADSupervisorEmail(item.getLocalCADSupervisorEmail());
							localCAD.setLocalCADSupervisorMobileNo(item.getLocalCADSupervisorMobileNo());
							localCAD.setRelationshipMgrID(relationshipMgr.getRelationshipMgrCode());
							localCAD.setCreationDate(new Date());
							localCAD.setCreatedBy(ctx.getUser().getLoginID());
							localCAD.setLastUpdateDate(new Date());
							localCAD.setLastUpdateBy(ctx.getUser().getLoginID());
							localCAD.setStatus("PENDING_DELETE");
							localCAD.setDeprecated("N");
							
							getRelationshipMgrDAO().createLocalCAD("stagingLocalCAD", localCAD);
							}
							
							else if(count == 0 && ("PENDING_CREATE".equals(item.getStatus()))) {
								
								
					             collateralDAO.updateStageNewCreatedLocalCadsDelete(trxValueIn.getStagingRelationshipMgr().getRelationshipMgrCode(),item.getLocalCADEmployeeCode(),localCADStatus);
					             
					             ILocalCAD localCAD = new OBLocalCAD();
									
									localCAD.setLocalCADEmployeeCode(item.getLocalCADEmployeeCode());
									localCAD.setLocalCADName(item.getLocalCADName());
									localCAD.setLocalCADEmailID(item.getLocalCADEmailID());
									localCAD.setLocalCADmobileNo(item.getLocalCADmobileNo());
									localCAD.setLocalCADSupervisorName(item.getLocalCADSupervisorName());
									localCAD.setLocalCADSupervisorEmail(item.getLocalCADSupervisorEmail());
									localCAD.setLocalCADSupervisorMobileNo(item.getLocalCADSupervisorMobileNo());
									localCAD.setRelationshipMgrID(trxValueIn.getStagingRelationshipMgr().getRelationshipMgrCode());
									localCAD.setCreationDate(new Date());
									localCAD.setCreatedBy(ctx.getUser().getLoginID());
									localCAD.setLastUpdateDate(new Date());
									localCAD.setLastUpdateBy(ctx.getUser().getLoginID());
									localCAD.setStatus("PENDING_DELETE");
									localCAD.setDeprecated("N");
									
									getRelationshipMgrDAO().createLocalCAD("stagingLocalCAD", localCAD);
								
							}
						}
					}
					
					
					
					
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