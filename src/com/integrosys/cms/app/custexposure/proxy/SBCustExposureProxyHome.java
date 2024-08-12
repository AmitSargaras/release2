package com.integrosys.cms.app.custexposure.proxy;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: JITENDRA
 * Date: May 30, 2008
 * Time: 11:14:30 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SBCustExposureProxyHome extends EJBHome {

    public SBCustExposureProxy create() throws CreateException, RemoteException;
}
