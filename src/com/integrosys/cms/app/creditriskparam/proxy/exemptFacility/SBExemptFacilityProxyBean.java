/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/SBExemptFacilityProxyBean.java,v 1.17 2005/01/12 06:36:33 hshii Exp $
*/
package com.integrosys.cms.app.creditriskparam.proxy.exemptFacility;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.rmi.RemoteException;

/**
 * @author $Author$<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/01/12 06:36:33 $
 * Tag: $Name:  $
 */
public class SBExemptFacilityProxyBean extends AbstractExemptFacilityProxy implements SessionBean {

    public void setSessionContext(SessionContext context)
            throws EJBException, RemoteException {
        _context = context;
    }


    public void ejbRemove()
            throws EJBException, RemoteException {
        //To change body of implemented methods use Options | File Templates.
    }


    public void ejbActivate()
            throws EJBException, RemoteException {
        //To change body of implemented methods use Options | File Templates.
    }


    public void ejbPassivate()
            throws EJBException, RemoteException {
        //To change body of implemented methods use Options | File Templates.
    }


    protected void rollback() {
        _context.setRollbackOnly();
    }

    public void ejbCreate() {

    }

    /**
     * SessionContext object
     */
    private SessionContext _context = null;
}
