package com.integrosys.cms.ui.creditriskparam.countrylimit;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.cms.app.creditriskparam.proxy.countrylimit.ICountryLimitProxy;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Jul 26, 2010
 * Time: 5:10:12 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CountryLimitCommand extends AbstractCommand {

    private ICountryLimitProxy countryLimitProxy;

    public ICountryLimitProxy getCountryLimitProxy() {
        return countryLimitProxy;
    }

    public void setCountryLimitProxy(ICountryLimitProxy countryLimitProxy) {
        this.countryLimitProxy = countryLimitProxy;
    }
}
