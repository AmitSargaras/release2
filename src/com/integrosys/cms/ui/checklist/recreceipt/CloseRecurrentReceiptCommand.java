/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recreceipt;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/07 10:59:08 $ Tag: $Name: $
 */
public class CloseRecurrentReceiptCommand extends AbstractCommand {

	private IRecurrentProxyManager recurrentProxyManager;

	public void setRecurrentProxyManager(IRecurrentProxyManager recurrentProxyManager) {
		this.recurrentProxyManager = recurrentProxyManager;
	}

	public IRecurrentProxyManager getRecurrentProxyManager() {
		return recurrentProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CloseRecurrentReceiptCommand() {
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
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue", SERVICE_SCOPE },
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
		IRecurrentCheckListTrxValue checkListTrxVal = (IRecurrentCheckListTrxValue) map.get("checkListTrxVal");

		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ctx.setTrxCountryOrigin(limit.getOriginatingLocation().getCountryCode());
		ctx.setTrxOrganisationOrigin(limit.getOriginatingLocation().getOrganisationCode());

		try {
			checkListTrxVal = getRecurrentProxyManager().makerCloseCheckListReceiptTrx(ctx, checkListTrxVal);
		}
		catch (RecurrentException ex) {
			throw new CommandProcessingException("failed to close checklist receipt workflow", ex);
		}
		resultMap.put("request.ITrxValue", checkListTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
