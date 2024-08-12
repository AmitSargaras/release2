/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/StockExchangeList.java,v 1.4 2005/08/08 09:31:59 hshii Exp $
 */
package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.feed.bus.stock.IStockExchange;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedBusManager;
import com.integrosys.cms.app.feed.bus.stock.StockExchangeComparator;

/**
 * Represents the list of stock exchanges' information.
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/08 09:31:59 $ Tag: $Name: $
 */
public class StockExchangeList {

	private StockExchangeList() {

	}

	public static StockExchangeList getInstance() {
		if (stockExchangeList == null) {

			stockExchangeList = new StockExchangeList();

			try {
				IStockFeedBusManager mgr = (IStockFeedBusManager) BeanHouse.get("stockFeedBusManager");

				IStockExchange[] stockExchangesArr = mgr.getAllStockExchanges();

				for (int i = 0; i < stockExchangesArr.length; i++) {
					stockExchangeList.addStockExchange(stockExchangesArr[i]);
				}

				stockExchangeList.stockExchangesArr = stockExchangesArr;

			}
			catch (Exception e) {
				DefaultLogger.error(StockExchangeList.class.getName(), "Exception caught.", e);

				// stockExchangeList is empty, not null.
			}

		}
		/*
		 * DefaultLogger.debug(StockExchangeList.class.getName(),
		 * "stockExchangeCodeNameMap = " +
		 * stockExchangeList.stockExchangeCodeNameMap);
		 */
		return stockExchangeList;
	}

	public static void fillStockExchange2Map(HashMap map) {
		StockExchangeList stockExchangeList = StockExchangeList.getInstance();
		IStockExchange[] stockExchangesArr = stockExchangeList.getStockExchanges();
		Arrays.sort(stockExchangesArr, new StockExchangeComparator());

		ArrayList labels = new ArrayList();
		ArrayList values = new ArrayList();
		for (int i = 0; i < stockExchangesArr.length; i++) {
			labels.add(stockExchangesArr[i].getStockExchangeName());
			values.add(stockExchangesArr[i].getStockExchangeCode());
		}

		map.put("stockExchangeValues", values);
		map.put("stockExchangeLabels", labels);

	}

	/**
	 * Gets all the stock exchanges.
	 * @return The array of stock exchanges' info.
	 */
	public IStockExchange[] getStockExchanges() {
		return stockExchangesArr;
	}

	/**
	 * Gets the labels in the form of stock exchange names.
	 * @return The list of stock exchange names. If there are no names, this
	 *         list is empty.
	 */
	public Collection getStockExchangeLabels() {
		return stockExchangeNames;
	}

	/**
	 * Gets the values in the form of stock exchange codes.
	 * @return The list of stock exchange codes. If there are no codes, this
	 *         list is empty.
	 */
	public Collection getStockExchangeValues() {
		return stockExchangeCodes;
	}

	/**
	 * Gets the stock exchange name based on the input stock exchange code.
	 * @param stockExchangeCode The stock exchange code.
	 * @return The stock exchange name or <code>null</code> if there is no such
	 *         code or empty string if code is present but name is not.
	 */
	public String getStockExchangeName(String stockExchangeCode) {
		if (stockExchangeCodeNameMap.containsKey(stockExchangeCode)) {
			Object obj = stockExchangeCodeNameMap.get(stockExchangeCode);
			if (obj == null) {
				return "";
			}
			else {
				return (String) obj;
			}
		}
		else {
			return null;
		}

	}

	/**
	 * Gets the country code based on the input stock exchange code.
	 * @param stockExchangeCode The stock exchange code.
	 * @return The country code or <code>null</code> if there is no such code or
	 *         empty string if code is present but country code is not.
	 */
	public String getCountryCode(String stockExchangeCode) {
		if (stockExchangeCodeCountryCodeMap.containsKey(stockExchangeCode)) {
			Object obj = stockExchangeCodeCountryCodeMap.get(stockExchangeCode);
			if (obj == null) {
				return "";
			}
			else {
				return (String) obj;
			}
		}
		else {
			return null;
		}
	}

	private void addStockExchange(IStockExchange stockExchange) {

		String stockExchangeCode = stockExchange.getStockExchangeCode();
		String stockExchangeName = stockExchange.getStockExchangeName();
		String countryCode = stockExchange.getCountryCode();

		stockExchangeCodes.add(stockExchangeCode);
		stockExchangeNames.add(stockExchangeName);
		countryCodes.add(countryCode);
		stockExchangeCodeNameMap.put(stockExchangeCode, stockExchangeName);
		stockExchangeCodeCountryCodeMap.put(stockExchangeCode, countryCode);
	}

	private static StockExchangeList stockExchangeList;

	private Collection stockExchangeCodes = new ArrayList();

	private Collection stockExchangeNames = new ArrayList();

	private Collection countryCodes = new ArrayList();

	private HashMap stockExchangeCodeNameMap = new HashMap();

	private HashMap stockExchangeCodeCountryCodeMap = new HashMap();

	private IStockExchange[] stockExchangesArr = new IStockExchange[0];
}
