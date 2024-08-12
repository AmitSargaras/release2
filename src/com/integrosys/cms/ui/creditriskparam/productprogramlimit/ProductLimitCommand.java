package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.creditriskparam.proxy.productlimit.IProductLimitParameterProxy;
import com.integrosys.cms.app.creditriskparam.proxy.sectorlimit.ISectorLimitParameterProxy;

/**
 * Author: KC Chin
 * Date: Aug 10, 2010
 */
public abstract class ProductLimitCommand extends AbstractCommand{

	private IProductLimitParameterProxy productLimitProxy;

    public IProductLimitParameterProxy getProductLimitProxy() {
        return productLimitProxy;
    }

    public void setProductLimitProxy(IProductLimitParameterProxy productLimitProxy) {
        this.productLimitProxy = productLimitProxy;
    }
}