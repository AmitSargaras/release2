package com.integrosys.cms.app.liquidation.bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

public abstract class EBLiquidationBean implements EntityBean, ILiquidation {

	private static final long serialVersionUID = 7584427556083277879L;

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the NPL Info. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getLiquidationID" };

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getLiquidationID", "getRecoveryExpense",
			"getRecovery" };

	public abstract Long getLiquidationID();

	public abstract void setLiquidationID(Long pkId);

	/**
	 * Getting the persistence value in Liquidation dto
	 * @return
	 */
	public ILiquidation getValue() {

		OBLiquidation liq = new OBLiquidation();
		AccessorUtil.copyValue(this, liq);
		return liq;
	}

	/**
	 * Setting value of persistence value from liquidation dto except
	 * EXCLUDE_METHOD
	 * @param rec
	 * @throws VersionMismatchException
	 */
	public void setValue(ILiquidation rec) throws VersionMismatchException {

		checkVersionMismatch(rec);
		AccessorUtil.copyValue(rec, this, EXCLUDE_METHOD_CREATE);
		if (rec.getRecoveryExpense() != null) {
			setRecoveryExpense(rec.getRecoveryExpense());
		}

		if (rec.getRecovery() != null) {
			setRecovery(rec.getRecovery());
		}
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Get list of Recovery Expense
	 */
	public Collection getRecoveryExpense() {
		Iterator i = getRecoveryExpenseCMR().iterator();
		Collection list = new ArrayList();

		while (i.hasNext()) {
			EBRecoveryExpenseLocal theEjb = (EBRecoveryExpenseLocal) i.next();
			IRecoveryExpense fao = theEjb.getValue();
			if ((fao.getStatus() == null)
					|| ((fao.getStatus() != null) && !fao.getStatus().equals(ICMSConstant.HOST_STATUS_DELETE))) {
				list.add(fao);
			}
		}
		return list;
	}

	/**
	 * Setting list of recoveryExpense to the persistence
	 */
	public void setRecoveryExpense(Collection recoveryExpense) {

		try {
			EBRecoveryExpenseLocalHome ejbHome = getEBRecoveryExpenseLocalHome();

			Iterator recoveryExpenseLocalIterator = getRecoveryExpenseCMR().iterator();

			// Removing the existing recovery expense that aren't
			// found in the newly passed collection
			while (recoveryExpenseLocalIterator.hasNext()) {
				EBRecoveryExpenseLocal reInLocal = (EBRecoveryExpenseLocal) recoveryExpenseLocalIterator.next();
				IRecoveryExpense reIn = reInLocal.getValue();

				boolean found = false;
				Iterator recoveryExpenseIterator = recoveryExpense.iterator();
				while (recoveryExpenseIterator.hasNext()) {
					IRecoveryExpense reOut = (IRecoveryExpense) recoveryExpenseIterator.next();

					if (reIn.getRecoveryExpenseID().longValue() == reOut.getRecoveryExpenseID().longValue()) {
						found = true;
					}
				}
				if (!found) {
					reInLocal.setStatus(ICMSConstant.HOST_STATUS_DELETE);
					recoveryExpenseLocalIterator.remove();
				}
			}

			// Updating the existing recovery expense and
			// collect the new recovery expense by adding them into
			// newRecoveryItems
			Iterator recoveryExpenseIterator = recoveryExpense.iterator();
			while (recoveryExpenseIterator.hasNext()) {
				IRecoveryExpense reOut = (IRecoveryExpense) recoveryExpenseIterator.next();

				boolean found = false;
				recoveryExpenseLocalIterator = getRecoveryExpenseCMR().iterator();

				while (recoveryExpenseLocalIterator.hasNext()) {

					EBRecoveryExpenseLocal ejbLocal = (EBRecoveryExpenseLocal) recoveryExpenseLocalIterator.next();
					IRecoveryExpense reIn = ejbLocal.getValue();
					if ((reIn.getStatus() != null) && reIn.getStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
						continue;
					}

					if (reIn.getRecoveryExpenseID().longValue() == reOut.getRecoveryExpenseID().longValue()) {
						try {
							// updating the existing recovery expense
							ejbLocal.setValue(reOut);
						}
						catch (VersionMismatchException e) {
							throw new LiquidationException("version mimatched when updating recovery expense", e);
						}

						found = true;
						break;
					}
				}

				if (!found) {
					try {
						// create a new recovery expense
						EBRecoveryExpenseLocal local = ejbHome.create(reOut);
						getRecoveryExpenseCMR().add(local);
					}
					catch (CreateException ce) {
						throw new LiquidationException("failed to create recovery expense using local home", ce);
					}
				}
				recoveryExpenseLocalIterator = getRecoveryExpenseCMR().iterator();
			}

		}
		catch (LiquidationException e) {
			DefaultLogger.debug(this, "Exception thrown at setRecoveryExpense", e);
		}
	}

	/**
	 * Get list of Recovery
	 */
	public Collection getRecovery() {
		Iterator i = getRecoveryCMR().iterator();
		Collection list = new ArrayList();

		while (i.hasNext()) {
			EBRecoveryLocal theEjb = (EBRecoveryLocal) i.next();
			IRecovery fao = theEjb.getValue();
			if ((fao.getStatus() == null)
					|| ((fao.getStatus() != null) && !fao.getStatus().equals(ICMSConstant.HOST_STATUS_DELETE))) {
				list.add(fao);
			}
		}

		return list;
	}

	public void setRecovery(Collection recovery) {

		try {
			EBRecoveryLocalHome ejbHome = getEBRecoveryLocalHome();

			Iterator recoveryLocalIterator = getRecoveryCMR().iterator();
			Collection removeList = new ArrayList();

			// Removing the existing recovery expense that aren't
			// found in the newly passed collection
			while (recoveryLocalIterator.hasNext()) {
				EBRecoveryLocal reInLocal = (EBRecoveryLocal) recoveryLocalIterator.next();
				IRecovery reIn = reInLocal.getValue();

				boolean found = false;
				Iterator recoveryIterator = recovery.iterator();
				while (recoveryIterator.hasNext()) {
					IRecovery reOut = (IRecovery) recoveryIterator.next();

					if (reIn.getRecoveryID().longValue() == reOut.getRecoveryID().longValue()) {
						found = true;
					}
				}
				if (!found) {
					removeList.add(reInLocal);
					// reInLocal.setStatus(ICMSConstant.HOST_STATUS_DELETE);
					// getRecoveryCMR().remove(reInLocal);
				}
			}

			for (Iterator i = removeList.iterator(); i.hasNext();) {
				EBRecoveryLocal reInLocal = (EBRecoveryLocal) i.next();
				reInLocal.setStatus(ICMSConstant.HOST_STATUS_DELETE);
				getRecoveryCMR().remove(reInLocal);
			}

			recoveryLocalIterator = getRecoveryCMR().iterator();

			// Updating the existing recovery and
			// collect the new recovery by adding them into newRecoveryItems
			Iterator recoveryIterator = recovery.iterator();
			while (recoveryIterator.hasNext()) {
				IRecovery reOut = (IRecovery) recoveryIterator.next();

				boolean found = false;
				recoveryLocalIterator = getRecoveryCMR().iterator();

				while (recoveryLocalIterator.hasNext()) {

					EBRecoveryLocal ejbLocal = (EBRecoveryLocal) recoveryLocalIterator.next();
					IRecovery reIn = ejbLocal.getValue();
					if ((reIn.getStatus() != null) && reIn.getStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
						continue;
					}

					if (reIn.getRecoveryID().longValue() == reOut.getRecoveryID().longValue()) {
						try {
							// updating the existing recovery
							ejbLocal.setValue(reOut);
						}
						catch (VersionMismatchException e) {
							throw new LiquidationException("version mismatched updating recovery details", e);
						}

						found = true;
						break;
					}
				}

				if (!found) {
					try {
						// create a new recovery expense
						EBRecoveryLocal local = ejbHome.create(reOut);
						getRecoveryCMR().add(local);
					}
					catch (CreateException ce) {
						throw new LiquidationException("failed to create using recovery local home", ce);
					}
				}
				recoveryLocalIterator = getRecoveryCMR().iterator();
			}

		}
		catch (LiquidationException e) {
			DefaultLogger.debug(this, "Exception thrown at setRecovery", e);
		}
	}

	public Long ejbCreate(ILiquidation liquidation) throws CreateException {

		long pk = 0;
		try {
			pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_LIQUIDATION, true));
		}
		catch (Exception e) {
			CreateException ce = new CreateException("failed to retrieve next sequence number using ["
					+ ICMSConstant.SEQUENCE_LIQUIDATION + "]; nested exception is " + e);
			ce.initCause(e);
			throw ce;
		}

		setLiquidationID(new Long(pk));
		AccessorUtil.copyValue(liquidation, this, EXCLUDE_METHOD_CREATE);
		setVersionTime(VersionGenerator.getVersionNumber());

		return liquidation.getLiquidationID();

	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * @param rec of type IRecovery
	 */
	public void ejbPostCreate(ILiquidation liquidation) throws CreateException {

		try {
			EBRecoveryExpenseLocalHome reHome = getEBRecoveryExpenseLocalHome();
			EBRecoveryLocalHome rcHome = getEBRecoveryLocalHome();

			Collection recoveryExpenseList = liquidation.getRecoveryExpense();
			Collection recoveryList = liquidation.getRecovery();

			if (recoveryExpenseList != null) {
				Iterator reIterator = recoveryExpenseList.iterator();
				while (reIterator.hasNext()) {
					IRecoveryExpense recoveryExpense = (IRecoveryExpense) reIterator.next();
					getRecoveryExpenseCMR().add(reHome.create(recoveryExpense));
				}
			}

			if (recoveryList != null) {
				Iterator rcIterator = recoveryList.iterator();
				while (rcIterator.hasNext()) {
					IRecovery recovery = (IRecovery) rcIterator.next();
					getRecoveryCMR().add(rcHome.create(recovery));
				}
			}

		}
		catch (LiquidationException e) {
			CreateException ce = new CreateException("failed to post create liqudation; nested exception is " + e);
			ce.initCause(e);
			throw ce;
		}
	}

	public abstract Collection getRecoveryExpenseCMR();

	public abstract void setRecoveryExpenseCMR(Collection recoveryExpense);

	public abstract Collection getRecoveryCMR();

	public abstract void setRecoveryCMR(Collection recovery);

	// public abstract Collection getNPLInfoCMR();
	// public abstract void setNPLInfoCMR(Collection liquidations);

	public void removeExpenseItems(int[] anItemIndexList) {
	}

	public void removeIncomeItems(int[] anItemIndexList, String recoveryType) {
	}

	public void addExpenseItem(IRecoveryExpense anItem) {
	}

	public void addIncomeItem(IRecoveryIncome anItem, String recoveryType) {
	}

	public void removeRecoveryItems(int[] anItemIndexList) {
	}

	public void addRecoveryItem(IRecovery anItem) {
	}

	private void checkVersionMismatch(ILiquidation rec) throws VersionMismatchException {

		if (getVersionTime() != rec.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + rec.getVersionTime());
		}
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}

	/**
	 * Getting the Recovery Local interface
	 * @return
	 * @throws LiquidationException
	 */
	protected EBRecoveryLocalHome getEBRecoveryLocalHome() throws LiquidationException {

		EBRecoveryLocalHome ejbHome = (EBRecoveryLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_RECOVERY_LOCAL_JNDI, EBRecoveryLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryLocalHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Getting the RecoveryExpense Local interface
	 * @return
	 * @throws LiquidationException
	 */
	protected EBRecoveryExpenseLocalHome getEBRecoveryExpenseLocalHome() throws LiquidationException {

		EBRecoveryExpenseLocalHome ejbHome = (EBRecoveryExpenseLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_RECOVERY_EXPENSE_LOCAL_JNDI, EBRecoveryExpenseLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryExpenseLocalHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Getting the NPLInfo Local interface
	 * @return
	 * @throws LiquidationException
	 */
	protected EBNPLInfoLocalHome getEBNPLInfoLocalHome() throws LiquidationException {

		EBNPLInfoLocalHome ejbHome = (EBNPLInfoLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_NPL_INFO_LOCAL_JNDI, EBNPLInfoLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBNPLInfoLocalHome is null!");
		}

		return ejbHome;
	}

	public abstract void setCollateralID(long collateralID);

	public abstract long getCollateralID();

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract String getSecurityID();

	public abstract void setSecurityID(String securityID);

	public abstract String getSecurityType();

	public abstract void setSecurityType(String securityType);
}
