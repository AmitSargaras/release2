package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.uiinfra.common.AbstractCommand;

import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.propertyindex.proxy.IPropertyIdxProxyManager;

/**
 * Title: CMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Sept 16, 2008
 */
public abstract class PropertyIdxCmd extends AbstractCommand {

	private IPropertyIdxProxyManager propertyIdxProxy;

    public IPropertyIdxProxyManager getPropertyIdxProxy() {
        return propertyIdxProxy;
    }

    public void setPropertyIdxProxy(IPropertyIdxProxyManager propertyIdxProxy) {
        this.propertyIdxProxy = propertyIdxProxy;
    }
}