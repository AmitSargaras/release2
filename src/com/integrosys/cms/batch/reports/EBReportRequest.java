package com.integrosys.cms.batch.reports;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: Contains the meta information of report types
 * 
 * @author $Author: ritika $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/27 03:40:41 $ Tag: $Name: $
 */

public interface EBReportRequest extends EJBObject {
	/**
	 * Get an object representation from persistance
	 * @return IReportRequest
	 * @throws RemoteException
	 */
	public IReportRequest getValue() throws RemoteException;

	/**
	 * 
	 * @param value is of type IReportRequest
	 * @throws RemoteException
	 */
	public void setValue(IReportRequest value) throws RemoteException;

}
