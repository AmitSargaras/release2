package com.integrosys.cms.ui.otherbankbranch;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.otherbranch.bus.OtherBranchException;
import com.integrosys.cms.app.otherbranch.proxy.IOtherBranchProxyManager;
import com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue;
import com.integrosys.cms.app.otherbranch.trx.OBOtherBankBranchTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Dattatray Thorat $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditOtherBranchCmd extends AbstractCommand implements ICommonEventConstant {


	private IOtherBranchProxyManager otherBranchProxyManager;

	/**
	 * @return the otherBranchProxyManager
	 */
	public IOtherBranchProxyManager getOtherBranchProxyManager() {
		return otherBranchProxyManager;
	}

	/**
	 * @param otherBranchProxyManager the otherBranchProxyManager to set
	 */
	public void setOtherBranchProxyManager(
			IOtherBranchProxyManager otherBranchProxyManager) {
		this.otherBranchProxyManager = otherBranchProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditOtherBranchCmd() {
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
				{"IOtherBankBranchTrxValue", "com.integrosys.cms.app.otherbranch.trx.IOtherBankBranchTrxValue", SERVICE_SCOPE},
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
			IOtherBankBranchTrxValue trxValueIn = (OBOtherBankBranchTrxValue) map.get("IOtherBankBranchTrxValue");
			String bankCode = trxValueIn.getStagingOtherBranch().getOtherBankCode().getOtherBankCode();
			String branchName = trxValueIn.getStagingOtherBranch().getOtherBranchName();
			boolean chkRbiCode = false;
			boolean isOtherBankBrachNameUnique = false;
			String rbiCode= Long.toString(trxValueIn.getStagingOtherBranch().getRbiCode());			
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			
			if ( trxValueIn.getStatus().equalsIgnoreCase("PENDING_CREATE") ) {
			
				isOtherBankBrachNameUnique = getOtherBranchProxyManager().isUniqueBranchName(branchName,bankCode );
				
				if(  rbiCode != null && ! rbiCode.equals("0") )
					chkRbiCode = getOtherBranchProxyManager().isUniqueRbiCode(rbiCode);
				
				if( isOtherBankBrachNameUnique ){
					exceptionMap.put("otherBrachNameError", new ActionMessage("error.string.exist","Other Bank Brach Name"));
				}
				
				if( chkRbiCode ){	
					exceptionMap.put("branchRbiCodeError", new ActionMessage("error.string.rbicode.exist"));
				}
				
				if( isOtherBankBrachNameUnique || chkRbiCode ){
					IOtherBankBranchTrxValue otherBankBranchTrxValue = null;
					resultMap.put("request.ITrxValue", otherBankBranchTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}				
			// Function  to approve updated other bank Trx
			IOtherBankBranchTrxValue trxValueOut = getOtherBranchProxyManager().checkerApproveOtherBranch(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (OtherBranchException ex) {
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