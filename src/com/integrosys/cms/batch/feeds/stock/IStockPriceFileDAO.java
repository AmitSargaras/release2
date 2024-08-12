package com.integrosys.cms.batch.feeds.stock;

import java.util.List;

/**
 * <p>
 * Data Access Object interface used by Stock Price Feed module
 * 
 * <p>
 * Use {@link #createStockPriceItems(List)} for bulk insert of stock price feed
 * to gain performance
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 * 
 */
public interface IStockPriceFileDAO {

	/**
	 * Create stock price feed into persistent storage, and return persisted
	 * data.
	 * 
	 * @param stockPriceItem a stock price feed
	 * @return persisted stock price feed
	 */
	public IStockPrice createStockPriceItem(IStockPrice stockPriceItem);

	/**
	 * Create all the stock price feed in the list supplied
	 * 
	 * @param stockPriceFeedList list of stock price feed to be persisted
	 */
	public void createStockPriceItems(List stockPriceFeedList);

}
