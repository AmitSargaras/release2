package com.integrosys.cms.app.propertyparameters.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 30, 2007 Time: 12:32:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EBPropertyParametersHome extends javax.ejb.EJBHome {

	public EBPropertyParameters create(IPropertyParameters anICollateralTask) throws CreateException, RemoteException;

	public EBPropertyParameters findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

}
