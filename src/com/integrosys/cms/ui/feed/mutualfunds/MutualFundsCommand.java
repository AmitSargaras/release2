package com.integrosys.cms.ui.feed.mutualfunds;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.feed.proxy.mutualfunds.IMutualFundsFeedProxy;

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
public abstract class MutualFundsCommand extends AbstractCommand {
	
	private IMutualFundsFeedProxy mutualFundsFeedProxy;

	public IMutualFundsFeedProxy getMutualFundsFeedProxy() {
		return mutualFundsFeedProxy;
	}

	public void setMutualFundsFeedProxy(IMutualFundsFeedProxy mutualFundsFeedProxy) {
		this.mutualFundsFeedProxy = mutualFundsFeedProxy;
	}
}
