package com.integrosys.cms.app.custexposure.bus.group;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.custexposure.bus.CustExposureException;

public interface SBGroupExposureBusManager extends EJBObject {

	/**
	 * Get the entire group exposure, only the first 5 customer entities will be retrieved.
	 * @param groupId
	 * @return
	 * @throws CustExposureException
	 * @throws RemoteException
	 */
    public IGroupExposure getGroupExposure(long groupId) throws CustExposureException, RemoteException;

}
