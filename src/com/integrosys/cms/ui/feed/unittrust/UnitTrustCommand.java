package com.integrosys.cms.ui.feed.unittrust;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.feed.proxy.unittrust.IUnitTrustFeedProxy;

public class UnitTrustCommand extends AbstractCommand{
	
	public IUnitTrustFeedProxy unitTrustFeedProxy;

	public IUnitTrustFeedProxy getUnitTrustFeedProxy() {
		return unitTrustFeedProxy;
	}

	public void setUnitTrustFeedProxy(IUnitTrustFeedProxy unitTrustFeedProxy) {
		this.unitTrustFeedProxy = unitTrustFeedProxy;
	}

}
