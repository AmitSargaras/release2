package com.integrosys.cms.app.custexposure.proxy.group;

/**
 * Created by IntelliJ IDEA.
 * User: JITENDRA
 * Date: May 30, 2008
 * Time: 11:15:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class GroupExposureProxyFactory {

     /**
    * Create a default customer proxy implementation
    *
    * @return ICustomerProxy
    */
    public static IGroupExposureProxy getProxy() {
       return new GroupExposureProxyImpl();
    }
}
