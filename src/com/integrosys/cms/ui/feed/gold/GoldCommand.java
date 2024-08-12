package com.integrosys.cms.ui.feed.gold;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.feed.proxy.gold.IGoldFeedProxy;

public class GoldCommand extends AbstractCommand{

	private IGoldFeedProxy goldFeedProxy;

	/**
	 * @return the goldFeedProxy
	 */
	public IGoldFeedProxy getGoldFeedProxy() {
		return goldFeedProxy;
	}

	/**
	 * @param goldFeedProxy the goldFeedProxy to set
	 */
	public void setGoldFeedProxy(IGoldFeedProxy goldFeedProxy) {
		this.goldFeedProxy = goldFeedProxy;
	}
	
}
