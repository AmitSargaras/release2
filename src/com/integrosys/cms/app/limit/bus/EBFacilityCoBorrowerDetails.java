package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

public interface EBFacilityCoBorrowerDetails extends EJBObject {

	public IFacilityCoBorrowerDetails getValue() throws RemoteException;
	
	public void setValue(IFacilityCoBorrowerDetails stock) throws ConcurrentUpdateException, RemoteException;

}
