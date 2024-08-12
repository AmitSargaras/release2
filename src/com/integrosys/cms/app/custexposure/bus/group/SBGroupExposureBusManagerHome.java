package com.integrosys.cms.app.custexposure.bus.group;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

public interface SBGroupExposureBusManagerHome extends EJBHome {

    public SBGroupExposureBusManager create() throws CreateException, RemoteException;

}
