package com.integrosys.cms.app.custexposure.bus.group;

import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.cms.app.custexposure.bus.CustExposureException;

/**
 * Session Bean extension of Group Exposure abstract class
 * @author skchai
 *
 */
public class SBGroupExposureBusManagerBean extends AbstractGroupExposureBusManager implements SessionBean {

	private static final long serialVersionUID = 1L;
	
	/**
	 * SessionContext object
	 */
	private SessionContext ctx;

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws CustExposureException
	 *             on errors encountered
	 */
	protected void rollback() throws CustExposureException {
		ctx.setRollbackOnly();
	}

	public void ejbCreate() {
	}

	public void setSessionContext(SessionContext sessionContext)
			throws EJBException {
		context = sessionContext;
	}

	public void ejbRemove() throws EJBException {
	}

	public void ejbActivate() throws EJBException {
	}

	public void ejbPassivate() throws EJBException {
	}

	private SessionContext context;

}
