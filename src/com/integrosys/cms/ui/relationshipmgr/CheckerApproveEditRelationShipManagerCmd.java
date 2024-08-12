package com.integrosys.cms.ui.relationshipmgr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.fileUpload.proxy.IFileUploadProxyManager;
import com.integrosys.cms.app.relationshipmgr.bus.IRelationshipMgrDAO;
import com.integrosys.cms.app.relationshipmgr.bus.OBLocalCAD;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Dattatray Thorat $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditRelationShipManagerCmd extends AbstractCommand implements ICommonEventConstant {


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
	public CheckerApproveEditRelationShipManagerCmd() {
	}

//	private static IFileUploadJdbc fileUploadJdbc = null;
//	
//	public static IFileUploadJdbc getFileUploadJdbc() {
//		if (fileUploadJdbc == null) {
//			fileUploadJdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
//        }
//		return fileUploadJdbc;
//	}
	
	public IFileUploadJdbc fileUploadJdbc;
	
	
	public IFileUploadJdbc getFileUploadJdbc() {
		return fileUploadJdbc;
	}

	public void setFileUploadJdbc(IFileUploadJdbc fileUploadJdbc) {
		this.fileUploadJdbc = fileUploadJdbc;
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE},
    			{ "localCADList","java.util.List",SERVICE_SCOPE},
    			{ "localCADs","java.util.List",SERVICE_SCOPE},
    			{ "removedLocalCADs","java.util.List",SERVICE_SCOPE},
		}
		);
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		}
		);
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
		HashMap exceptionMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// System Bank Trx value
			IRelationshipMgrTrxValue trxValueIn = (OBRelationshipMgrTrxValue) map.get("IRelationshipMgrTrxValue");
			
			List localCADList =  (List) map.get("localCADList");
			List localCADs = (List) map.get("localCADs");
			List removedLocalCADs = (List) map.get("removedLocalCADs");
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao"); 
			
        	boolean isRelationshipMgrNameUnique = false;
        	boolean isEmpIdUnique = false;
        	String oldRelationshipMgrName = "";
        	String newRelationshipMgrName = "";
        	String empId = "";
        	String newEmpId = "";
        	  	
        	if( trxValueIn.getStatus().equalsIgnoreCase("PENDING_CREATE") ){
//        		oldRelationshipMgrName = trxValueIn.getStagingRelationshipMgr().getRelationshipMgrName();
//        		isRelationshipMgrNameUnique = getRelationshipMgrProxyManager().isRelationshipMgrNameUnique(oldRelationshipMgrName.trim());
//        		empId = trxValueIn.getStagingRelationshipMgr().getEmployeeId();
//        		isEmpIdUnique = getRelationshipMgrProxyManager().isEmployeeIdUnique(empId.trim());
        	}
			else if( trxValueIn.getStatus().equalsIgnoreCase("PENDING_UPDATE") ){
			// Start
//				newRelationshipMgrName = trxValueIn.getStagingRelationshipMgr().getRelationshipMgrName();
//				oldRelationshipMgrName = trxValueIn.getRelationshipMgr().getRelationshipMgrName();
//				if( ! oldRelationshipMgrName.equals(newRelationshipMgrName) )
//					isRelationshipMgrNameUnique = getRelationshipMgrProxyManager().isRelationshipMgrNameUnique(newRelationshipMgrName.trim());
//				
//				newEmpId = trxValueIn.getStagingRelationshipMgr().getEmployeeId();
//				empId = trxValueIn.getRelationshipMgr().getEmployeeId();
//				trxValueIn.getRelationshipMgr().setCpsId(empId);
//				if( null !=empId  &&  (! empId.equals(newEmpId)) )
//        		isEmpIdUnique = getRelationshipMgrProxyManager().isEmployeeIdUnique(newEmpId.trim());
			}
			
//			if(isRelationshipMgrNameUnique != false){
//				exceptionMap.put("relationshipMgrNameError", new ActionMessage("error.string.exist","RelationshipMgr Name"));
//				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//				return returnMap;
//			}
			
//			if(isEmpIdUnique){
//				exceptionMap.put("employeeIdDuplicateError", new ActionMessage("error.string.exist","Employee Id"));
//				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
//				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
//				
//				return returnMap;
//			}
			// End

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated system bank Trx
			IRelationshipMgrTrxValue trxValueOut = getRelationshipMgrProxyManager().checkerApproveRelationshipMgr(ctx, trxValueIn);
			String localCADStatus = "ACTIVE";
		      
			if( trxValueIn.getStatus().equalsIgnoreCase("PENDING_DELETE") ) {
				localCADStatus = "INACTIVE";
			}
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
					if(count == 0 || "INACTIVE".equals(localCADStatus)) {
					ILocalCAD localCAD = new OBLocalCAD();
					
					localCAD.setLocalCADEmployeeCode(item.getLocalCADEmployeeCode());
					localCAD.setLocalCADName(item.getLocalCADName());
					localCAD.setLocalCADEmailID(item.getLocalCADEmailID());
					localCAD.setLocalCADmobileNo(item.getLocalCADmobileNo());
					localCAD.setLocalCADSupervisorName(item.getLocalCADSupervisorName());
					localCAD.setLocalCADSupervisorEmail(item.getLocalCADSupervisorEmail());
					localCAD.setLocalCADSupervisorMobileNo(item.getLocalCADSupervisorMobileNo());
					localCAD.setRelationshipMgrID(trxValueIn.getStagingRelationshipMgr().getRelationshipMgrCode());
					localCAD.setCreationDate(item.getCreationDate());
					localCAD.setCreatedBy(item.getCreatedBy());
					localCAD.setLastUpdateDate(new Date());
					localCAD.setLastUpdateBy(ctx.getUser().getLoginID());
					localCAD.setStatus(localCADStatus);
					localCAD.setDeprecated("N");
					
					getRelationshipMgrDAO().createLocalCAD("actualLocalCAD", localCAD);
					
//					getRelationshipMgrDAO().updateStagingLocalCAD("stagingLocalCAD", localCAD);
					 collateralDAO.updateStagingLoccalCadsCreate(trxValueIn.getStagingRelationshipMgr().getRelationshipMgrCode(),item.getLocalCADEmployeeCode(),localCADStatus);
					
					}
				}
			}
			
			
			if(null != removedLocalCADs) {
				Iterator itrNew = removedLocalCADs.iterator();
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
					if(count > 0) {
					ILocalCAD localCAD = new OBLocalCAD();
					
					localCAD.setLocalCADEmployeeCode(item.getLocalCADEmployeeCode());
					localCAD.setLocalCADName(item.getLocalCADName());
					localCAD.setLocalCADEmailID(item.getLocalCADEmailID());
					localCAD.setLocalCADmobileNo(item.getLocalCADmobileNo());
					localCAD.setLocalCADSupervisorName(item.getLocalCADSupervisorName());
					localCAD.setLocalCADSupervisorEmail(item.getLocalCADSupervisorEmail());
					localCAD.setLocalCADSupervisorMobileNo(item.getLocalCADSupervisorMobileNo());
					localCAD.setRelationshipMgrID(trxValueIn.getStagingRelationshipMgr().getRelationshipMgrCode());
					localCAD.setCreationDate(item.getCreationDate());
					localCAD.setCreatedBy(item.getCreatedBy());
					localCAD.setLastUpdateDate(new Date());
					localCAD.setLastUpdateBy(ctx.getUser().getLoginID());
					localCAD.setStatus(localCADStatus);
					localCAD.setDeprecated("Y");
					
					
		             collateralDAO.updateActualLoccalCadsDelete(trxValueIn.getStagingRelationshipMgr().getRelationshipMgrCode(),item.getLocalCADEmployeeCode(),localCADStatus );
		             
		             collateralDAO.updateStagingLoccalCadsDelete(trxValueIn.getStagingRelationshipMgr().getRelationshipMgrCode(),item.getLocalCADEmployeeCode(),localCADStatus);
					
					
					
//					getRelationshipMgrDAO().updateLocalCAD("actualLocalCAD", localCAD);
//					
//					getRelationshipMgrDAO().updateStagingLocalCAD("stagingLocalCAD", localCAD);
					}
				}
			}
			
			
			
			
			getFileUploadJdbc().updatePartyRMDetails(trxValueIn.getStagingRelationshipMgr().getRelationshipMgrCode(), trxValueIn.getStagingRelationshipMgr().getRegion());
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (RelationshipMgrException ex) {
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



