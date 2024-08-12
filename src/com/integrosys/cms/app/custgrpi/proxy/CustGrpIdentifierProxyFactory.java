package com.integrosys.cms.app.custgrpi.proxy;

public class CustGrpIdentifierProxyFactory {
    /**
    * Create a default customer proxy implementation
    *
    * @return ICustomerProxy
    */
    public static ICustGrpIdentifierProxy getProxy() {
       return new CustGrpIdentifierProxyImpl();
    }
}
