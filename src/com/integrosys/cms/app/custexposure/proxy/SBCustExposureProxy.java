package com.integrosys.cms.app.custexposure.proxy;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.custexposure.bus.CustExposureException;
import com.integrosys.cms.app.custexposure.bus.ICustExposure;

/**
 * Session Remote Interface for customer exposure proxy
 * @author skchai
 * 
 */
public interface SBCustExposureProxy extends EJBObject {


    public ICustExposure getCustExposure(long subProfileId)  throws CustExposureException, RemoteException;
}
