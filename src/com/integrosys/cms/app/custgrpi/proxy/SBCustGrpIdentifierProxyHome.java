package com.integrosys.cms.app.custgrpi.proxy;


import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

public interface SBCustGrpIdentifierProxyHome extends EJBHome {

    public SBCustGrpIdentifierProxy create() throws CreateException, RemoteException;
}
