package com.integrosys.cms.ui.fccBranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranchDao;
import com.integrosys.cms.app.fccBranch.proxy.IFCCBranchProxyManager;
import com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue;
import com.integrosys.cms.app.fccBranch.trx.OBFCCBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Abhijit R $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditFCCBranchCmd extends AbstractCommand implements ICommonEventConstant {


	private IFCCBranchProxyManager fccBranchProxy;


	

	/**
	 * @return the fccBranchProxy
	 */
	public IFCCBranchProxyManager getFccBranchProxy() {
		return fccBranchProxy;
	}

	/**
	 * @param fccBranchProxy the fccBranchProxy to set
	 */
	public void setFccBranchProxy(IFCCBranchProxyManager fccBranchProxy) {
		this.fccBranchProxy = fccBranchProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditFCCBranchCmd() {
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
				{"IFCCBranchTrxValue", "com.integrosys.cms.app.fccBranch.trx.IFCCBranchTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
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
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// FCCBranch Trx value
			IFCCBranchTrxValue trxValueIn = (OBFCCBranchTrxValue) map.get("IFCCBranchTrxValue");
			 String isCombinationUnique = "true";
			
			IFCCBranchDao fccBranchDao = (IFCCBranchDao)BeanHouse.get("fccBranchDao");
			
			
			if(trxValueIn.getStatus().equalsIgnoreCase("PENDING_CREATE") ||(trxValueIn.getStatus().equalsIgnoreCase("PENDING_UPDATE"))){
				IFCCBranch fccBranch= trxValueIn.getStagingFCCBranch();
				if(fccBranch.getBranchCode()!=null && fccBranch.getBranchCode().trim()!=""){
					 try {
						
							String branchCode = fccBranch.getBranchCode();
				        	String aliasBranchCode  = fccBranch.getAliasBranchCode();
				        	long id = 0L;
				        	if(null != trxValueIn.getFCCBranch())
				        	id = trxValueIn.getFCCBranch().getId();
				        	isCombinationUnique = getFccBranchProxy().fccBranchUniqueCombination(branchCode.trim(),aliasBranchCode.trim(),id);
				        	
				        	
					} 
					catch(Exception e){
						throw new Exception("Branch code null !");
					}
				}
				
				}
			
			
			if(isCombinationUnique.equalsIgnoreCase("fccBranch")){
				exceptionMap.put("fccBranchDuplicateError", new ActionMessage("error.string.exist","Branch Code"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
			else if(isCombinationUnique.equalsIgnoreCase("aliasBranch")){
				exceptionMap.put("fccBranchCombinationDuplicateError", new ActionMessage("error.string.exist","Alias Branch Code"));
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}else{
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated FCCBranch Trx
			IFCCBranchTrxValue trxValueOut = getFccBranchProxy().checkerApproveFCCBranch(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
			}
		}catch (FCCBranchException ex) {
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



