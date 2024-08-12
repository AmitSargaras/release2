/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/titledocument/list/ApproveTitleDocumentListCommand.java,v 1.3 2004/06/17 06:32:06 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.titledocument.list;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.titledocument.ITitleDocumentTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/17 06:32:06 $ Tag: $Name: $
 */

public class ApproveTitleDocumentListCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "titleDocumentTrxValue",
						"com.integrosys.cms.app.commodity.main.trx.titledocument.ITitleDocumentTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.commodity.main.trx.titledocument.ITitleDocumentTrxValue", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ITitleDocumentTrxValue trxValue = (ITitleDocumentTrxValue) map.get("titleDocumentTrxValue");
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ctx.setCustomer(null);
		ctx.setLimitProfile(null);

		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();

		try {
			trxValue = proxy.checkerApproveUpdateTitleDocument(ctx, trxValue);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommandProcessingException(e.getMessage());
		}

		result.put("request.ITrxValue", trxValue);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
