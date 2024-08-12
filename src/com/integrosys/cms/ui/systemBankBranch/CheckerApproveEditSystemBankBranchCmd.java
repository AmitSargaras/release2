package com.integrosys.cms.ui.systemBankBranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.OBSystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.proxy.ISystemBankBranchProxyManager;
import com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue;
import com.integrosys.cms.app.systemBankBranch.trx.OBSystemBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Abhijit R $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditSystemBankBranchCmd extends AbstractCommand implements ICommonEventConstant {


	private ISystemBankBranchProxyManager systemBankBranchProxy;


	public ISystemBankBranchProxyManager getSystemBankBranchProxy() {
		return systemBankBranchProxy;
	}

	public void setSystemBankBranchProxy(
			ISystemBankBranchProxyManager systemBankBranchProxy) {
		this.systemBankBranchProxy = systemBankBranchProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditSystemBankBranchCmd() {
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
				{"ISystemBankBranchTrxValue", "com.integrosys.cms.app.systemBankBranch.trx.ISystemBankBranchTrxValue", SERVICE_SCOPE},
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
			// System Bank Trx value
			ISystemBankBranchTrxValue trxValueIn = (OBSystemBankBranchTrxValue) map.get("ISystemBankBranchTrxValue");
			boolean validBranchCode=false;
			boolean validRBICode=false;
			boolean validBranchName=false;
			ISystemBankBranch systemBankBranch = trxValueIn.getStagingSystemBankBranch();
			if(trxValueIn.getStatus().equalsIgnoreCase("PENDING_CREATE")){
			if(systemBankBranch.getSystemBankBranchCode()!=null && systemBankBranch.getSystemBankBranchCode().trim()!=""){
				 try {
					validBranchCode=getSystemBankBranchProxy().isUniqueCode("system_bank_branch_code",systemBankBranch.getSystemBankBranchCode());
				} catch (SystemBankBranchException e) {
					throw new SystemBankBranchException("Branch code null !");
				}
				catch(Exception e){
					throw new SystemBankBranchException("Branch code null !");
				}
			}
			if(systemBankBranch.getRbiCode()!=null && systemBankBranch.getRbiCode().trim()!=""){
				 try {
					validRBICode=getSystemBankBranchProxy().isUniqueCode("rbi_code",systemBankBranch.getRbiCode());
				} catch (SystemBankBranchException e) {
					throw new SystemBankBranchException("RBI  code null !");
				}
				catch(Exception e){
					throw new SystemBankBranchException("RBI  code null !");
				}
			}
			if(systemBankBranch.getSystemBankBranchName()!=null && systemBankBranch.getSystemBankBranchName().trim()!=""){
				 try {
					 validBranchName=getSystemBankBranchProxy().isUniqueCode("system_bank_branch_name",systemBankBranch.getSystemBankBranchName());
				} catch (SystemBankBranchException e) {
					throw new SystemBankBranchException("Branch Name null !");
				}
				catch(Exception e){
					throw new SystemBankBranchException("Branch Name null !");
				}
			}
			}
			if(validBranchCode){
				exceptionMap.put("systemBankBranchDuplicateCodeError", new ActionMessage("error.string.duplicate"));
				ISystemBankBranchTrxValue trxValueInError = null;
				resultMap.put("request.ITrxValue", trxValueIn);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}else if(validBranchName){
				exceptionMap.put("systemBankBranchDuplicateBranchNameError", new ActionMessage("error.string.duplicateBranchName"));
				ISystemBankBranchTrxValue trxValueInError = null;
				resultMap.put("request.ITrxValue", trxValueInError);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}else if(validRBICode){
				exceptionMap.put("systemBankBranchDuplicateRBICodeError", new ActionMessage("error.string.duplicateRBICode"));
				ISystemBankBranchTrxValue trxValueInError = null;
				resultMap.put("request.ITrxValue", trxValueIn);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				return returnMap;
			}else{
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated system bank Trx
			ISystemBankBranchTrxValue trxValueOut = getSystemBankBranchProxy().checkerApproveSystemBankBranch(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
			}
		}catch (SystemBankException ex) {
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



