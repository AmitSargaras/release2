package com.integrosys.cms.app.cci.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;


public interface SBCCICounterpartyDetailsBusManagerHome
        extends EJBHome {

    public SBCCICounterpartyDetailsBusManager create() throws CreateException, RemoteException;

}
