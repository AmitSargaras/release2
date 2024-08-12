package com.integrosys.cms.ui.feed.stock.item;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.StockExchangeList;
import com.integrosys.cms.ui.feed.stock.StockCommand;

/**
 * This class implements command
 */
public class PrepareStockItemCommand extends StockCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
						SERVICE_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { StockItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry",
				FORM_SCOPE }, };
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
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			// default country currency to Stock Exchange country
			IStockFeedGroupTrxValue value = (IStockFeedGroupTrxValue) map.get("stockFeedGroupTrxValue");
			String stockExchangeCode = value.getStagingStockFeedGroup().getSubType();
			String countryCode = StockExchangeList.getInstance().getCountryCode(stockExchangeCode);
			String currencyCode = CurrencyList.getInstance().getCurrencyCodeByCountry(countryCode);
			resultMap.put(StockItemForm.MAPPER, currencyCode);

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
