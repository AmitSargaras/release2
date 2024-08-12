package com.integrosys.cms.app.cci.proxy;


public class CCICustomerProxyFactory {
    /**
    * Create a default customer proxy implementation
    *
    * @return ICustomerProxy
    */
    public static ICCICustomerProxy getProxy() {
       return new CCICustomerProxyImpl();
    }
}
