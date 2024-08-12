package com.integrosys.cms.ui.feed.exchangerate;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;

public abstract class ExchangeRateCommand extends AbstractCommand {
	private IForexFeedProxy forexFeedProxy;

	/**
	 * @return the forexFeedProxy
	 */
	public IForexFeedProxy getForexFeedProxy() {
		return forexFeedProxy;
	}

	/**
	 * @param forexFeedProxy the forexFeedProxy to set
	 */
	public void setForexFeedProxy(IForexFeedProxy forexFeedProxy) {
		this.forexFeedProxy = forexFeedProxy;
	}
	
}
