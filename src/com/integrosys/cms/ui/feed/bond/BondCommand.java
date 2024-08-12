package com.integrosys.cms.ui.feed.bond;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;

public abstract class BondCommand extends AbstractCommand {
	private IBondFeedProxy bondFeedProxy;

	public IBondFeedProxy getBondFeedProxy() {
		return bondFeedProxy;
	}

	public void setBondFeedProxy(IBondFeedProxy bondFeedProxy) {
		this.bondFeedProxy = bondFeedProxy;
	}

}
