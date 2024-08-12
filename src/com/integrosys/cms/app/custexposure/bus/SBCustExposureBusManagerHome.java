package com.integrosys.cms.app.custexposure.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

public interface SBCustExposureBusManagerHome extends EJBHome {

    public SBCustExposureBusManager create() throws CreateException, RemoteException;

}
