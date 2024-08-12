/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * The SBInterestRateBusManagerBean acts as the facade to the Entity Beans for
 * interest actual data.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class SBInterestRateBusManagerBean implements IInterestRateBusManager, SessionBean {
	/** SessionContext object */
	private SessionContext ctx;

	/**
	 * Default Constructor
	 */
	public SBInterestRateBusManagerBean() {
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager#getInterestRate
	 */
	public IInterestRate[] getInterestRate(String intRateType, Date monthYear) throws InterestRateException {

		try {
			IInterestRateDAO dao = InterestRateDAOFactory.getDAO();
			IInterestRate[] collection = dao.getInterestRateByMonth(intRateType, monthYear);
			return collection;
		}
		catch (SearchDAOException e) {
			throw new InterestRateException("SearchDAOException caught at getInterestRate " + e.toString());
		}
		catch (Exception e) {
			throw new InterestRateException("Exception caught at getInterestRate: " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager#getInterestRateByGroupID
	 */
	public IInterestRate[] getInterestRateByGroupID(long groupID) throws InterestRateException {
		try {
			EBInterestRateHome ejbHome = getEBInterestRateHome();
			Iterator i = ejbHome.findByGroupID(groupID).iterator();

			ArrayList arrList = new ArrayList();

			while (i.hasNext()) {
				EBInterestRate theEjb = (EBInterestRate) i.next();

				arrList.add(theEjb.getValue());
			}

			return (IInterestRate[]) arrList.toArray(new OBInterestRate[0]);
		}
		catch (FinderException e) {
			throw new InterestRateException("FinderException caught at getInterestRateByGroupID " + e.toString());
		}
		catch (Exception e) {
			throw new InterestRateException("Exception caught at getInterestRateByGroupID " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager#updateInterestRates
	 */
	public IInterestRate[] updateInterestRates(IInterestRate[] intRates) throws InterestRateException {
		EBInterestRateHome ejbHome = getEBInterestRateHome();
		try {
			ArrayList arrList = new ArrayList();

			for (int i = 0; i < intRates.length; i++) {
				EBInterestRate theEjb = ejbHome.findByPrimaryKey(intRates[i].getIntRateID());
				theEjb.setIntRateValue(intRates[i]);

				arrList.add(theEjb.getValue());

			}
			return (IInterestRate[]) arrList.toArray(new OBInterestRate[0]);
		}
		catch (FinderException e) {

			DefaultLogger.error(this, "", e);
			rollback();
			throw new InterestRateException("FinderException caught! " + e.toString());
		}
		catch (VersionMismatchException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new InterestRateException("VersionMismatchException caught! " + e.toString(),
					new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new InterestRateException("RemoteException caught! " + e.toString());
		}
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRateBusManager#createInterestRates
	 */
	public IInterestRate[] createInterestRates(IInterestRate[] intRates) throws InterestRateException {
		if ((intRates == null) || (intRates.length == 0)) {
			throw new InterestRateException("IInterestRate[] is null");
		}

		EBInterestRateHome ejbHome = getEBInterestRateHome();

		try {
			ArrayList arrList = new ArrayList();
			long groupID = ICMSConstant.LONG_MIN_VALUE;

			for (int i = 0; i < intRates.length; i++) {
				OBInterestRate intRate = new OBInterestRate(intRates[i]);
				intRate.setGroupID(groupID);
				EBInterestRate theEjb = ejbHome.create(intRate);
				intRate = (OBInterestRate) theEjb.getValue();
				groupID = intRate.getGroupID();

				arrList.add(intRate);
			}
			return (IInterestRate[]) arrList.toArray(new OBInterestRate[0]);
		}
		catch (CreateException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new InterestRateException("CreateException caught! " + e.toString());
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new InterestRateException("RemoteException caught! " + e.toString());
		}
	}

	/**
	 * Get DAO implementation for interestrate dao.
	 * 
	 * @return IInterestRateDAO
	 */
	protected IInterestRateDAO getInterestRateDAO() {
		return InterestRateDAOFactory.getDAO();
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws InterestRateException on errors encountered
	 */
	protected void rollback() throws InterestRateException {
		ctx.setRollbackOnly();
	}

	/**
	 * Get home interface of EBInterestRateBean.
	 * 
	 * @return interestrate home interface
	 * @throws InterestRateException on errors encountered
	 */
	protected EBInterestRateHome getEBInterestRateHome() throws InterestRateException {
		EBInterestRateHome ejbHome = (EBInterestRateHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_INT_RATE_JNDI,
				EBInterestRateHome.class.getName());

		if (ejbHome == null) {
			throw new InterestRateException("EBInterestRateHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface. No implementation is required for this bean.
	 */
	public void ejbCreate() {
	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(SessionContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {
	}
}