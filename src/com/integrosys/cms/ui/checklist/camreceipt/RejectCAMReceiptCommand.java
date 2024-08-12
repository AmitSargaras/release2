/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.camreceipt;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/10/10 08:11:59 $ Tag: $Name: $
 */
public class RejectCAMReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public RejectCAMReceiptCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"remarks", "java.lang.String", REQUEST_SCOPE},
				 
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
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE },
				{ "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },		
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
		DefaultLogger.debug(this, "Inside doExecute()");
	
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");

			// DefaultLogger.debug(this, "checkListTrxVal before approve " +
			// checkListTrxVal);
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

			  String remarks = (String) map.get("remarks");
	          if(remarks == null||remarks.trim().equals("")){
					exceptionMap.put("remarksError", new ActionMessage("error.reject.remark"));
					resultMap.put("custTypeTrxID", checkListTrxVal.getTransactionID());
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
				}else{
			
					try {
			// Begin OFFICE
			String preStatus = checkListTrxVal.getStatus();
			DefaultLogger.debug(this, "Current status-----" + preStatus);
			if (ICMSConstant.STATE_PENDING_OFFICE.equals(preStatus))
			// Reject by OFFICE
			{
				checkListTrxVal.setToAuthGroupTypeId(ICMSConstant.TEAM_TYPE_FAM_USER);
				checkListTrxVal.setToAuthGId(ICMSConstant.LONG_INVALID_VALUE);
				checkListTrxVal.setToUserId(ICMSConstant.LONG_INVALID_VALUE);
				checkListTrxVal.setOpDesc(ICMSConstant.ACTION_REJECT);
				checkListTrxVal = proxy.officeOperation(ctx, checkListTrxVal);
				DefaultLogger.debug(this, "-----After Reject/Verify by GAM/RCO/SCO ");
			}
			else if (ICMSConstant.STATE_PENDING_AUTH.equals(preStatus)
					|| ICMSConstant.STATE_PENDING_VERIFY.equals(preStatus))
			// Reject/Verify by FAM
			{
				checkListTrxVal.setToAuthGroupTypeId(ICMSConstant.TEAM_TYPE_CPC_MAKER);
				checkListTrxVal.setToAuthGId(ICMSConstant.LONG_INVALID_VALUE);
				checkListTrxVal.setToUserId(ICMSConstant.LONG_INVALID_VALUE);
				checkListTrxVal.setOpDesc(ICMSConstant.ACTION_REJECT);
				checkListTrxVal = proxy.officeOperation(ctx, checkListTrxVal);
				DefaultLogger.debug(this, "-----After Reject/Verify by FAM ");
			}
			// End OFFICE
			else if (ICMSConstant.STATE_PENDING_MGR_VERIFY.equals(preStatus)) {
				checkListTrxVal = proxy.managerRejectCheckListReceipt(ctx, checkListTrxVal);
			}
			else {
				checkListTrxVal = proxy.checkerRejectCheckListReceipt(ctx, checkListTrxVal);
			}
			resultMap.put("request.ITrxValue", checkListTrxVal);
			// DefaultLogger.debug(this, "checkListTrxVal after reject " +
			// checkListTrxVal);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	}
}
