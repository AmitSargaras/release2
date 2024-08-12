/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/stock/list/ListStockListCommand.java,v 1.2 2003/08/11 12:06:50 btchng Exp $
 */
package com.integrosys.cms.ui.feed.stock.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.stock.StockCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/11 12:06:50 $ Tag: $Name: $
 */
public class ListStockListCommand extends StockCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {// Produce all the feed entries.
		{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue", SERVICE_SCOPE }, 
				{ StockListForm.MAPPER, "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue", FORM_SCOPE } });
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		DefaultLogger.debug(this, "entering doExecute(...)");

		// Pass through to the mapper to prepare for display.

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IStockFeedGroupTrxValue trxValue = (IStockFeedGroupTrxValue) hashMap.get("stockFeedGroupTrxValue");

		resultMap.put("stockFeedGroupTrxValue", trxValue);
		resultMap.put(StockListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
