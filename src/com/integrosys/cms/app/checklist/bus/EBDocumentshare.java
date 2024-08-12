package com.integrosys.cms.app.checklist.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Nov 15, 2005 Time: 5:44:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EBDocumentshare extends EJBObject {

	public Long getCMPDocShareId() throws RemoteException;

	public IShareDoc getValue() throws RemoteException;

	public void setValue(IShareDoc iShareDoc) throws RemoteException;

	// public void setIsDeletedInd(boolean anIsDeletedInd) throws
	// RemoteException;

	// public boolean getIsDeletedInd() throws RemoteException;

}
