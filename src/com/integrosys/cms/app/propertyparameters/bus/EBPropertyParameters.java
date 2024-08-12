package com.integrosys.cms.app.propertyparameters.bus;

import java.rmi.RemoteException;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Jan 30, 2007 Time: 12:32:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EBPropertyParameters extends javax.ejb.EJBObject {

	public IPropertyParameters getValue() throws RemoteException;

	public void setValue(IPropertyParameters aIPropertyParameters) throws RemoteException, ConcurrentUpdateException,
			PropertyParametersException;

}
