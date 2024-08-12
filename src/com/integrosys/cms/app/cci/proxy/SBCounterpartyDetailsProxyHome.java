package com.integrosys.cms.app.cci.proxy;


import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

public interface SBCounterpartyDetailsProxyHome
        extends EJBHome {

    public SBCounterpartyDetailsProxy create()
            throws CreateException, RemoteException;
}
