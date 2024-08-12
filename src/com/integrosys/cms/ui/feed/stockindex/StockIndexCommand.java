package com.integrosys.cms.ui.feed.stockindex;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.feed.proxy.stockindex.IStockIndexFeedProxy;

/**
 * <p>
 * Command class to be overriden by concrete command class.
 * 
 * <p>
 * Provide convenient method to inject Proxy Interface. Sub class to care about
 * getProxy method provided.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class StockIndexCommand extends AbstractCommand {

	private IStockIndexFeedProxy stockIndexFeedProxy;

	/**
	 * @return the stock index feed proxy injected
	 */
	public IStockIndexFeedProxy getStockIndexFeedProxy() {
		return this.stockIndexFeedProxy;
	}

	/**
	 * @param stockIndexFeedProxy the stock index feed proxy to be injected
	 */
	public void setStockIndexFeedProxy(IStockIndexFeedProxy stockIndexFeedProxy) {
		this.stockIndexFeedProxy = stockIndexFeedProxy;
	}
}