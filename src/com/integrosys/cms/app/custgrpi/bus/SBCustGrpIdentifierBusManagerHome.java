package com.integrosys.cms.app.custgrpi.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;


public interface SBCustGrpIdentifierBusManagerHome
        extends EJBHome {

    public SBCustGrpIdentifierBusManager create() throws CreateException, RemoteException;

}
