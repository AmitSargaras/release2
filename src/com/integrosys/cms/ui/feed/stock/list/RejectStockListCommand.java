/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/stock/list/RejectStockListCommand.java,v 1.7 2003/09/22 04:51:48 btchng Exp $
 */
package com.integrosys.cms.ui.feed.stock.list;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.otherbank.trx.IOtherBankTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.stock.StockCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/09/22 04:51:48 $ Tag: $Name: $
 */
public class RejectStockListCommand extends StockCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{"remarks","java.lang.String",REQUEST_SCOPE},
				{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IStockFeedGroupTrxValue value = (IStockFeedGroupTrxValue) map.get("stockFeedGroupTrxValue");

			String remarks = (String)map.get("remarks");
			value.setRemarks(remarks);

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			if(remarks == null || remarks.equals("")){
				exceptionMap.put("remarksError", new ActionMessage("error.remark.mandatory"));
				IStockFeedGroupTrxValue trxValue = null;
            	resultMap.put("IStockFeedGroupTrxValue", trxValue);
            	resultMap.put("request.ITrxValue", trxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
			}
			
			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			value = getStockFeedProxy().checkerRejectStockFeedGroup(trxContext, value);

			resultMap.put("request.ITrxValue", value);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
