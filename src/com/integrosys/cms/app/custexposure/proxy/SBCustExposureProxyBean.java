package com.integrosys.cms.app.custexposure.proxy;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: JITENDRA
 * Date: May 30, 2008
 * Time: 11:14:18 AM
 * To change this template use File | Settings | File Templates.
 */
public class SBCustExposureProxyBean  extends AbstractCustExposureProxy implements SessionBean {

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
