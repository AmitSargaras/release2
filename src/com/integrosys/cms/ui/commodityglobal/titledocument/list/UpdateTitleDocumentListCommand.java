/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/titledocument/list/UpdateTitleDocumentListCommand.java,v 1.1 2004/08/13 03:34:42 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.titledocument.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.commodity.main.bus.titledocument.OBTitleDocument;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.commodity.main.trx.titledocument.ITitleDocumentTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/08/13 03:34:42 $ Tag: $Name: $
 */
public class UpdateTitleDocumentListCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "titleDocumentTrxValue",
						"com.integrosys.cms.app.commodity.main.trx.titledocument.ITitleDocumentTrxValue", SERVICE_SCOPE },
				{ "titleDocumentObj", "java.util.HashMap", SERVICE_SCOPE },
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
		HashMap titledocObj = (HashMap) map.get("titleDocumentObj");

		ArrayList titleDocList = new ArrayList();
		titleDocList.addAll((Collection) titledocObj.get("stageTitleDocNonNeg"));
		titleDocList.addAll((Collection) titledocObj.get("stageTitleDocNeg"));

		ITitleDocument[] titleDocListArr = (ITitleDocument[]) titleDocList.toArray(new OBTitleDocument[0]);
		trxValue.setStagingTitleDocument(titleDocListArr);

		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ctx.setCustomer(null);
		ctx.setLimitProfile(null);

		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		DefaultLogger.debug(this, "trxValue: " + trxValue);
		try {
			trxValue = proxy.makerSaveTitleDocument(ctx, trxValue, trxValue.getStagingTitleDocument());
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
