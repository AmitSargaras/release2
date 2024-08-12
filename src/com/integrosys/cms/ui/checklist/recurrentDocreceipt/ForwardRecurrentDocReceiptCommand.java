/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBCMSTrxRouteInfo;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/07/12 06:03:27 $ Tag: $Name: $
 */
public class ForwardRecurrentDocReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ForwardRecurrentDocReceiptCommand() {
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
				{ "forwardUser", "java.lang.String", REQUEST_SCOPE }, });
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
				REQUEST_SCOPE } });
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			String forwardUser = (String) map.get("forwardUser");

			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			// DefaultLogger.debug(this, "checkListTrxVal before approve " +
			// checkListTrxVal);
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ctx.setTrxCountryOrigin(CheckListUtil.getColTrxCountry(checkListTrxVal.getStagingCheckList()));

			OBCMSTrxRouteInfo forwardRouteInfo = new OBCMSTrxRouteInfo(forwardUser);
			long prevToAuthGroupTypeId = checkListTrxVal.getToAuthGroupTypeId();
			checkListTrxVal.setToAuthGroupTypeId((Long.valueOf(forwardRouteInfo.getMemberShipTypeID())).longValue());
			checkListTrxVal.setToAuthGId((Long.valueOf(forwardRouteInfo.getTeamID())).longValue());
			checkListTrxVal.setToUserId((Long.valueOf(forwardRouteInfo.getUserID())).longValue());
			if ((checkListTrxVal.getToAuthGroupTypeId() == ICMSConstant.TEAM_TYPE_FAM_USER)
					&& (prevToAuthGroupTypeId > checkListTrxVal.getToAuthGroupTypeId()))
			// Backward to FAM?
			{
				checkListTrxVal.setOpDesc(ICMSConstant.ACTION_BACKWARD);
			}
			else {
				checkListTrxVal.setOpDesc(ICMSConstant.ACTION_FORWARD);
			}

			checkListTrxVal = proxy.officeOperation(ctx, checkListTrxVal);

			// DefaultLogger.debug(this, "checkListTrxVal after approve " +
			// checkListTrxVal);
			resultMap.put("request.ITrxValue", checkListTrxVal);
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
