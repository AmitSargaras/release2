package com.integrosys.cms.app.cci.proxy;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsException;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.trx.OBCCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.app.transaction.SBCMSTrxManager;
import com.integrosys.cms.app.transaction.SBCMSTrxManagerHome;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;
import java.util.HashMap;


public class SBCounterpartyDetailsProxyBean
        extends AbstractCounterpartyDetailsProxy implements SessionBean {

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
