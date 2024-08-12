package com.integrosys.cms.ui.feed.stock;

import com.integrosys.base.uiinfra.common.AbstractCommand;

import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;

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
public abstract class StockCommand extends AbstractCommand {
	
	private IStockFeedProxy stockFeedProxy;
	
	public IStockFeedProxy getStockFeedProxy() {
		return this.stockFeedProxy;
	}
	
	public void setStockFeedProxy(IStockFeedProxy stockFeedProxy) {
		this.stockFeedProxy = stockFeedProxy;
	}
}
