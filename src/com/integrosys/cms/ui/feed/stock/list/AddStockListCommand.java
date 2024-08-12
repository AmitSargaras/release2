/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/stock/list/AddStockListCommand.java,v 1.3 2003/08/29 12:41:00 btchng Exp $
 */
package com.integrosys.cms.ui.feed.stock.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.stock.StockCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/29 12:41:00 $ Tag: $Name%
 */
public class AddStockListCommand extends StockCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {

				{ StockListForm.MAPPER, "com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup", FORM_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			IStockFeedGroup inputFeedGroup = (IStockFeedGroup) map.get(StockListForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();

			IStockFeedEntry[] inputEntriesArr = inputFeedGroup.getFeedEntries();

			IStockFeedGroupTrxValue value = (IStockFeedGroupTrxValue) map.get("stockFeedGroupTrxValue");
			IStockFeedGroup group = value.getStagingStockFeedGroup();
			IStockFeedEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr != null) {
				DefaultLogger.debug(this, "number of existing entries = " + entriesArr.length);

				for (int i = 0; i < inputEntriesArr.length; i++) {
					for (int j = 0; j < entriesArr.length; j++) {
						if (inputEntriesArr[i].getTicker().equals(entriesArr[j].getTicker())) {
							entriesArr[j].setUnitPrice(inputEntriesArr[i].getUnitPrice());
							entriesArr[j].setBlackListed(inputEntriesArr[i].getBlackListed());
							entriesArr[j].setSuspended(inputEntriesArr[i].getSuspended());
						}
					}

				}
			}

			group.setFeedEntries(entriesArr);
			value.setStagingStockFeedGroup(group);

			// resultMap.put("stockFeedGroupTrxValue", value);
			// resultMap.put(StockListForm.MAPPER, value);

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
