package com.integrosys.cms.app.propertyparameters.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 30, 2007 Time: 12:37:23 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SBPropertyParametersHome extends EJBHome {

	public SBPropertyParameters create() throws CreateException, RemoteException;

}
