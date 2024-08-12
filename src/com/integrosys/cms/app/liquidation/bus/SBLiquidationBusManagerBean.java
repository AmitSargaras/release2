/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * The SBLiquidationBusManagerBean acts as the facade to the Entity Beans for
 * Liquidation actual data.
 * 
 * @author $Author: lini$<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class SBLiquidationBusManagerBean implements ILiquidationBusManager, SessionBean {

	private static final long serialVersionUID = 1796849649375970755L;

	/** SessionContext object */
	private SessionContext ctx;

	/**
	 * Default Constructor
	 */
	public SBLiquidationBusManagerBean() {
	}

	public Collection getNPLInfo(long collateralID) throws LiquidationException {

		try {
			LiquidationDAO dao = LiquidationDAOFactory.getDAO();
			return dao.getNPLInfo(collateralID);
		}
		catch (SearchDAOException e) {
			throw new LiquidationException("SearchDAOException caught at getLiquidation ", e);
		}
	}

	/**
	 * @see com.integrosys.cms.app.liquidation.bus.ILiquidationBusManager#getLiquidation
	 */
	public ILiquidation getLiquidation(long liquidationID) throws LiquidationException {

		if (liquidationID == 0) {
			throw new LiquidationException("liquidationID is invalid");
		}

		try {
//			System.out.println("SBLiquidationBusManagerBean getLiquidation : " + liquidationID);
			EBLiquidationHome liquidationHome = getLiquidationHome();
			EBLiquidation remote = liquidationHome.findByPrimaryKey(new Long(liquidationID));

			ILiquidation liquidation = remote.getValue();
			return liquidation;
		}
		catch (FinderException e) {
			throw new LiquidationException("failed to find liquidation using the id [" + liquidationID + "] ", e);
		}
		catch (RemoteException e) {
			throw new LiquidationException("failed to work on liquidation home remote, throwing root cause ", e
					.getCause());
		}
	}

	public ILiquidation createLiquidation(ILiquidation liquidation) throws LiquidationException {
		if (liquidation == null) {
			throw new LiquidationException("ILiquidation is null");
		}

		EBLiquidationHome liquidationHome = getLiquidationHome();

		try {
			EBLiquidation ejbLiqLocal = liquidationHome.create(liquidation);
			ILiquidation value = ejbLiqLocal.getValue();

			return value;
		}
		catch (CreateException e) {
			rollback();
			throw new LiquidationException("failed to create liquidation", e);
		}
		catch (RemoteException e) {
			rollback();
			throw new LiquidationException("failed to work on liqudation home remote, throwing root cause", e
					.getCause());
		}
	}

	public ILiquidation updateLiquidation(ILiquidation liquidation) throws LiquidationException {
		ILiquidation liq = new OBLiquidation();

		EBLiquidationHome liquidationHome = getLiquidationHome();
		EBLiquidation ejbLiqLocal = null;

		try {
			ejbLiqLocal = liquidationHome.findByPrimaryKey(liquidation.getLiquidationID());
			ejbLiqLocal.setValue(liquidation);
			liq = ejbLiqLocal.getValue();
			return liq;
		}
		catch (FinderException fe) {
			DefaultLogger.warn(this, "Liquidation not found for " + liquidation.getLiquidationID());
			try {
				ejbLiqLocal = liquidationHome.create(liquidation);
				liq = ejbLiqLocal.getValue();
				return liq;
			}
			catch (RemoteException ex) {
				rollback();
				throw new LiquidationException("failed to work on liquidation remote home, throwing root cause", ex
						.getCause());
			}
			catch (CreateException e) {
				rollback();
				throw new LiquidationException("failed to create liquidation after failed to search liquidation", e);
			}

		}
		catch (VersionMismatchException e) {
			rollback();
			throw new LiquidationException("version mismatched when updating liquidation",
					new ConcurrentUpdateException(e.getMessage()));
		}
		catch (RemoteException e) {
			rollback();
			throw new LiquidationException("failed to work on liquidation remote home, throwing root cause", e
					.getCause());
		}

	}

	/**
	 * Get DAO implementation for liquidation dao.
	 * 
	 * @return ILiquidationDAO
	 */
	protected LiquidationDAO getLiquidationDAO() {
		return LiquidationDAOFactory.getDAO();
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws LiquidationException on errors encountered
	 */
	protected void rollback() throws LiquidationException {
		ctx.setRollbackOnly();
	}

	protected EBRecoveryIncomeHome getEBRecoveryIncomeHome() throws LiquidationException {
		EBRecoveryIncomeHome ejbHome = (EBRecoveryIncomeHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_RECOVERY_INCOME_JNDI, EBRecoveryIncomeHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryIncomeHome is null!");
		}

		return ejbHome;
	}

	protected EBLiquidationHome getLiquidationHome() throws LiquidationException {

		EBLiquidationHome ejbHome = (EBLiquidationHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_LIQUIDATION_JNDI,
				EBLiquidationHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBLiquidationHome is null!");
		}

		return ejbHome;
	}

	protected EBRecoveryHome getEBRecoveryHome() throws LiquidationException {
		EBRecoveryHome ejbHome = (EBRecoveryHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_RECOVERY_JNDI,
				EBRecoveryHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryHome is null!");
		}

		return ejbHome;
	}

	protected EBRecoveryExpenseHome getEBRecoveryExpenseHome() throws LiquidationException {
		EBRecoveryExpenseHome ejbHome = (EBRecoveryExpenseHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_RECOVERY_EXPENSE_JNDI, EBRecoveryExpenseHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryExpenseHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Get home interface of EBNPLInfoBean.
	 * 
	 * @return liquidation home interface
	 * @throws LiquidationException on errors encountered
	 */
	protected EBNPLInfoHome getEBNPLInfoHome() throws LiquidationException {
		EBNPLInfoHome ejbHome = (EBNPLInfoHome) BeanController.getEJBHome(ICMSJNDIConstant.EB_NPL_INFO_JNDI,
				EBNPLInfoHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBNPLInfoHome is null!");
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