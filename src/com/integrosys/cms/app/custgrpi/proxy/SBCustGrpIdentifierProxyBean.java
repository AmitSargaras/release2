package com.integrosys.cms.app.custgrpi.proxy;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;


public class SBCustGrpIdentifierProxyBean
        extends AbstractCustGrpIdentifierProxy implements SessionBean {

    public void setSessionContext(SessionContext context) throws EJBException, RemoteException {
        _context = context;
    }


    public void ejbRemove() throws EJBException, RemoteException {
        //To change body of implemented methods use Options | File Templates.
    }


    public void ejbActivate() throws EJBException, RemoteException {
        //To change body of implemented methods use Options | File Templates.
    }


    public void ejbPassivate() throws EJBException, RemoteException {
        //To change body of implemented methods use Options | File Templates.
    }


    protected void rollback() {
        _context.setRollbackOnly();
    }


    public void ejbCreate() {

    }

    private SessionContext _context = null;


}
