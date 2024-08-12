package com.integrosys.cms.app.custexposure.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Session Bean Home Interface
 * @author skchai
 *
 */
public interface SBCustExposureBusManager extends EJBObject {

	/**
	 * Get Exposure based on customer profile id
	 * @param subProfileId
	 * @return
	 * @throws CustExposureException
	 * @throws RemoteException
	 */
    public ICustExposure getCustExposure(long subProfileId) throws CustExposureException, RemoteException;
    
    /**
     * Populate custExposure with customer's limit profiles / limits and collateral(s)
     * @param custExposure skeleton object which must at least contains cMSCustomer (with only customerId) and iCCICounterpartyDetails objects
     * @return
     * @throws CustExposureException
     * @throws RemoteException
     */
    public void getCustExposure(ICustExposure custExposure) throws CustExposureException, RemoteException;
}
