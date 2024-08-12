/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
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

/**
 * Entity bean implementation for Recovery entity.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBRecoveryBean implements IRecovery, EntityBean {

	private static final long serialVersionUID = 2841511912604522797L;

	private EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getRecoveryID" };

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getRecoveryID", "getRecoveryIncome" };

	public abstract Long getRecoveryID();

	public abstract void setRecoveryID(Long recoveryID);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract void setStatus(String status);

	public abstract String getStatus();

	public abstract Collection getRecoveryIncomeCMR();

	public abstract void setRecoveryIncomeCMR(Collection recoveryIncome);

	public IRecovery getValue() {
		OBRecovery npl = new OBRecovery();
		AccessorUtil.copyValue(this, npl);
		return npl;
	}

	public void setValue(IRecovery rec) throws VersionMismatchException {
		checkVersionMismatch(rec);
		AccessorUtil.copyValue(rec, this, EXCLUDE_METHOD_CREATE);

		if (rec.getRecoveryIncome() != null) {
			setRecoveryIncome(rec.getRecoveryIncome());
		}

		setVersionTime(VersionGenerator.getVersionNumber());
	}

	public void setRecoveryIncome(Collection incomeColl) {
		try {
			this.setRecoveryIncomeRef(incomeColl, false);
		}
		catch (LiquidationException le) {
			DefaultLogger.debug(this, "throw exception while setting recovery income in setRecoveryIncome()", le);
			le.printStackTrace();
		}
	}

	/**
	 * Get a list of fao info of this asset.
	 * 
	 * @return Map (faoID, IFixedAssetOthers)
	 */
	public Collection getRecoveryIncome() {
		Iterator i = getRecoveryIncomeCMR().iterator();
		Collection map = new ArrayList();

		while (i.hasNext()) {
			EBRecoveryIncomeLocal theEjb = (EBRecoveryIncomeLocal) i.next();
			IRecoveryIncome fao = theEjb.getValue();
			if ((fao.getStatus() == null)
					|| ((fao.getStatus() != null) && !fao.getStatus().equals(ICMSConstant.HOST_STATUS_DELETE))) {
				map.add(fao);
			}
		}
		return map;
	}

	/**
	 * set a list of recoveryIncome as an asset.
	 * 
	 * @param recoveryIncomeMap of type Map
	 */
	private void setRecoveryIncomeRef(Collection recoveryIncomeList, boolean isAdd) throws LiquidationException {
		// if (recoveryIncomeMap == null || recoveryIncomeMap.isEmpty()) {
		// removeAllRecoveryIncomes();
		// return;
		// }

		EBRecoveryIncomeLocalHome ejbHome = getEBRecoveryIncomeLocalHome();

		Collection existingList = getRecoveryIncomeCMR();

		// if the collection is a newly added, add them in and exit
		if (isAdd || (existingList.size() == 0)) {
			if (recoveryIncomeList != null) {
				for (Iterator iterator = recoveryIncomeList.iterator(); iterator.hasNext();) {
					IRecoveryIncome recoveryIncome = (IRecoveryIncome) iterator.next();
					try {
						getRecoveryIncomeCMR().add(ejbHome.create(recoveryIncome));
					}
					catch (CreateException e) {
						throw new LiquidationException("failed to create recovery income using local home", e);
					}
				}
			}
			return;
		}

		// remove existing item from persistence by comparing with the existing
		// list
		// by updating the status to STATE_DELETED
		removeRecoveryIncome(existingList, recoveryIncomeList);

		Iterator iterator = existingList.iterator();
		Iterator itr = recoveryIncomeList.iterator();
		ArrayList newItems = new ArrayList();

		// Update those items
		while (itr.hasNext()) {
			boolean found = false;
			IRecoveryIncome recoveryIncome = (IRecoveryIncome) itr.next();

			while (iterator.hasNext()) {
				EBRecoveryIncomeLocal theEjb = (EBRecoveryIncomeLocal) iterator.next();
				IRecoveryIncome value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
					continue;
				}

				if (recoveryIncome.getRecoveryIncomeID().equals(value.getRecoveryIncomeID())) {
					try {
						theEjb.setValue(recoveryIncome);
					}
					catch (VersionMismatchException e) {
						throw new LiquidationException("version mismatched updating recovery income", e);
					}
					found = true;
					break;
				}
			}
			if (!found) {
				newItems.add(recoveryIncome);
			}
			iterator = existingList.iterator();
		}

		iterator = newItems.iterator();

		// those
		while (iterator.hasNext()) {
			try {
				existingList.add(ejbHome.create((IRecoveryIncome) iterator.next()));
			}
			catch (CreateException e) {
				throw new LiquidationException("failed to create recovery income", e);
			}
		}
	}

	// /**
	// * Helper method to delete all the recoveryIncome.
	// */
	// private void removeAllRecoveryIncomes()
	// {
	// Collection c = getRecoveryIncomeCMR();
	// Iterator iterator = c.iterator();
	//
	// while ( iterator.hasNext() )
	// {
	// EBRecoveryIncomeLocal theEjb = (EBRecoveryIncomeLocal) iterator.next();
	// deleteRecoveryIncome (theEjb);
	// }
	// }

	/**
	 * Helper method to delete recoveryIncome in recoveryIncomeCol that are not
	 * contained in recoveryIncomeMap.
	 * 
	 * @param recoveryIncomeCol a list of old Insurance Info
	 * @param recoveryIncomeList a Map of newly updated recoveryIncome
	 */
	private void removeRecoveryIncome(Collection recoveryIncomeCol, Collection recoveryIncomeList) {
		Iterator iterator = recoveryIncomeCol.iterator();

		while (iterator.hasNext()) {
			EBRecoveryIncomeLocal theEjb = (EBRecoveryIncomeLocal) iterator.next();
			IRecoveryIncome recoveryIncome = theEjb.getValue();
			if ((recoveryIncome.getStatus() != null)
					&& recoveryIncome.getStatus().equals(ICMSConstant.HOST_STATUS_DELETE)) {
				continue;
			}

			boolean found = false;

			Iterator itr = recoveryIncomeList.iterator();
			while (itr.hasNext()) {
				IRecoveryIncome recoveryIncomeObj = (IRecoveryIncome) itr.next();
				if (recoveryIncomeObj.getRecoveryIncomeID().equals(recoveryIncome.getRecoveryIncomeID())) {
					found = true;
					break;
				}
			}
			if (!found) {
				theEjb.setStatus(ICMSConstant.HOST_STATUS_DELETE);
				// deleteRecoveryIncome (theEjb);
			}
		}
	}

	/**
	 * Helper method to delete a recoveryIncome.
	 * 
	 * @param theEjb of type EBRecoveryIncomeLocal
	 */
	// private void deleteRecoveryIncome (EBRecoveryIncomeLocal theEjb)
	// {
	// // theEjb.deleteRecoveryIncome(theEjb.getValue());
	// //theEjb.setStatus (ICMSConstant.STATE_DELETED);
	// }
	private void checkVersionMismatch(IRecovery rec) throws VersionMismatchException {
		if (getVersionTime() != rec.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + rec.getVersionTime());
		}
	}

	public Long ejbCreate(IRecovery rec) throws CreateException {
		try {

			long pk = ICMSConstant.LONG_MIN_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_RECOVERY, true));
			DefaultLogger.debug(this, "Creating Recovery  with ID: " + pk);
			setRecoveryID(new Long(pk));
			AccessorUtil.copyValue(rec, this, EXCLUDE_METHOD_CREATE);

			if (isStaging()) {
				// if (rec.getRefID() != ICMSConstant.LONG_MIN_VALUE &&
				// rec.getRefID() > 0)
				// setRefID(rec.getRefID());
				if ((rec.getRecoveryID() != null) && (rec.getRecoveryID().longValue() != ICMSConstant.LONG_MIN_VALUE)) {
					setRefID(rec.getRecoveryID().longValue());
				}
				else {
					setRefID(pk);
				}
			}
			else {
				setRefID(pk);
			}

			setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * Set the necessary child collection
	 * @param recovery
	 * @param isAdd
	 * @throws CreateException
	 */
	private void setReferences(IRecovery recovery, boolean isAdd) throws CreateException {
		try {
			setRecoveryIncomeRef(recovery.getRecoveryIncome(), isAdd);
		}
		catch (LiquidationException e) {
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * @param rec of type IRecovery
	 */
	public void ejbPostCreate(IRecovery rec) throws CreateException {
		setReferences(rec, true);
	}

	protected boolean isStaging() {
		return false;
	}

	protected EBRecoveryIncomeLocalHome getEBRecoveryIncomeLocalHome() throws LiquidationException {
		EBRecoveryIncomeLocalHome ejbHome = (EBRecoveryIncomeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_RECOVERY_INCOME_LOCAL_JNDI, EBRecoveryIncomeLocalHome.class.getName());

		if (ejbHome == null) {
			throw new LiquidationException("EBRecoveryIncomeLocalHome is null!");
		}

		return ejbHome;
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

	public abstract String getRecoveryType();

	public abstract void setRecoveryType(String recoveryType);

	public abstract String getRemarks();

	public abstract void setRemarks(String remarks);

	public abstract void setRefID(long refID);

	public abstract long getRefID();
}