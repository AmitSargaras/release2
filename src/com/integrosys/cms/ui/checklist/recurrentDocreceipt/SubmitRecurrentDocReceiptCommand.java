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
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/10/10 08:12:40 $ Tag: $Name: $
 */
public class SubmitRecurrentDocReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SubmitRecurrentDocReceiptCommand() {
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
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE},
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
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
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			ICheckList checkList = (ICheckList) map.get("checkListForm");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			//ctx.setTrxCountryOrigin(CheckListUtil.getColTrxCountry(checkList))
			// ;

			/*if (CheckListUtil.isInCountry(ctx.getTeam(), ctx.getLimitProfile())) {
				ctx.setTrxCountryOrigin(ctx.getLimitProfile().getOriginatingLocation().getCountryCode());
				ctx.setTrxOrganisationOrigin(ctx.getLimitProfile().getOriginatingLocation().getOrganisationCode());
			}
			else {
				ctx.setTrxCountryOrigin(CheckListUtil.getColTrxCountry(checkList));
				ctx.setTrxOrganisationOrigin(checkList.getCheckListLocation().getOrganisationCode());
			}*/

			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			// DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>Updating" +
			// checkList);
			if (checkListTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED)
					|| checkListTrxVal.getStatus().equals(ICMSConstant.STATE_OFFICER_REJECTED)) {
				checkListTrxVal = proxy.makerEditRejectedCheckListReceiptTrx(ctx, checkListTrxVal, checkList);
			}
			else {
				checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkList);
			}
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
