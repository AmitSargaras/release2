package com.integrosys.cms.app.propertyparameters.proxy;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:36:15 AM
 * To change this template use File | Settings | File Templates.
 */
public interface SBPrPaProxyManagerHome extends EJBHome {
	public SBPrPaProxyManager create() throws CreateException, RemoteException;
}
