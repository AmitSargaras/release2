package com.integrosys.cms.ui.caseBranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranchDao;
import com.integrosys.cms.app.caseBranch.proxy.ICaseBranchProxyManager;
import com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue;
import com.integrosys.cms.app.caseBranch.trx.OBCaseBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Abhijit R $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditCaseBranchCmd extends AbstractCommand implements ICommonEventConstant {


	private ICaseBranchProxyManager caseBranchProxy;


	public ICaseBranchProxyManager getCaseBranchProxy() {
		return caseBranchProxy;
	}

	public void setCaseBranchProxy(
			ICaseBranchProxyManager caseBranchProxy) {
		this.caseBranchProxy = caseBranchProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditCaseBranchCmd() {
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
				{"ICaseBranchTrxValue", "com.integrosys.cms.app.caseBranch.trx.ICaseBranchTrxValue", SERVICE_SCOPE},
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
			// CaseBranch Trx value
			ICaseBranchTrxValue trxValueIn = (OBCaseBranchTrxValue) map.get("ICaseBranchTrxValue");
			boolean isValidCaseBranch=false;
			
			ICaseBranchDao caseBranchDao = (ICaseBranchDao)BeanHouse.get("caseBranchDao");
			
			
			if(trxValueIn.getStatus().equalsIgnoreCase("PENDING_CREATE")){
				ICaseBranch caseBranch= trxValueIn.getStagingCaseBranch();
				if(caseBranch.getBranchCode()!=null && caseBranch.getBranchCode().trim()!=""){
					 try {
						 isValidCaseBranch=caseBranchDao.isUniqueCode("BRANCHCODE",caseBranch.getBranchCode());
					} 
					catch(Exception e){
						throw new Exception("Branch code null !");
					}
				}
				
				}
			
			
			if(isValidCaseBranch){
				exceptionMap.put("caseBranchDuplicateCodeError", new ActionMessage("error.string.duplicate"));
				ICaseBranchTrxValue trxValueInError = null;
				resultMap.put("request.ITrxValue", trxValueIn);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}else{
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated CaseBranch Trx
			ICaseBranchTrxValue trxValueOut = getCaseBranchProxy().checkerApproveCaseBranch(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
			}
		}catch (CaseBranchException ex) {
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



