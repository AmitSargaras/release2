/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/
package com.integrosys.cms.app.limitbooking.proxy;

public class LimitBookingProxyFactory {
    /**
    * Create a default limit booking proxy implementation
    *
    * @return ILimitBookingProxy
    */
    public static ILimitBookingProxy getProxy() {
       return new LimitBookingProxyImpl();
    }
}