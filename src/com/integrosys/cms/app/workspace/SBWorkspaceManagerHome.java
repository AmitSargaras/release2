package com.integrosys.cms.app.workspace;

import java.rmi.RemoteException;

import javax.ejb.CreateException;

public interface SBWorkspaceManagerHome extends javax.ejb.EJBHome {
	/**
	 * To create a SBWorkspaceManagerHome
	 */
	public SBWorkspaceManager create() throws RemoteException, CreateException;
}