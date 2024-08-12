package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface EBCoBorrowerDetails extends EJBObject {

	public ICoBorrowerDetails getValue() throws RemoteException;
	
	public void setValue(ICoBorrowerDetails stock) throws ConcurrentUpdateException, RemoteException;

}
