/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/stockindex/list/ListViewStockIndexListCommand.java,v 1.1 2003/09/05 09:45:28 btchng Exp $
 */
package com.integrosys.cms.ui.feed.stockindex.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedEntry;
import com.integrosys.cms.app.feed.bus.stockindex.IStockIndexFeedGroup;
import com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.stockindex.StockIndexCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/05 09:45:28 $ Tag: $Name: $
 */
public class ListViewStockIndexListCommand extends StockIndexCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {// Consume the input target offset.
				{ StockIndexListForm.MAPPER, "java.lang.Integer", FORM_SCOPE },
				{ "stockIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.stockindex.IStockIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = ((Integer) map.get(StockIndexListForm.MAPPER)).intValue();

			// Session-scoped trx value.
			IStockIndexFeedGroupTrxValue value = (IStockIndexFeedGroupTrxValue) map.get("stockIndexFeedGroupTrxValue");
			IStockIndexFeedGroup group = value.getStagingStockIndexFeedGroup();
			IStockIndexFeedEntry[] entriesArr = group.getFeedEntries();

			targetOffset = StockIndexListMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("offset", new Integer(targetOffset));

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
