package com.integrosys.cms.batch.reports;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: SB for report request
 * 
 * @author $Author: ritika $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/27 03:40:41 $ Tag: $Name: $
 */

public interface SBReportRequestManagerHome extends javax.ejb.EJBHome {
	/**
	 * Default Create method
	 * 
	 * @throws javax.ejb.CreateException on error
	 * @throws java.rmi.RemoteException
	 */
	public SBReportRequestManager create() throws javax.ejb.CreateException, java.rmi.RemoteException;

}
