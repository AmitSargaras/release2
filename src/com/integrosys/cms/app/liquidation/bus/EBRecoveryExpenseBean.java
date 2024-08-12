/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;

/**
 * Entity bean implementation for RecoveryExpense entity.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBRecoveryExpenseBean implements IRecoveryExpense, EntityBean {

	private static final long serialVersionUID = 4751748824864189147L;

	private EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getRecoveryExpenseID" };

	public abstract Long getRecoveryExpenseID();

	public abstract void setRecoveryExpenseID(Long recoveryExpenseID);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract void setStatus(String status);

	public abstract String getStatus();

	public abstract Double getExpenseAmt();

	public abstract void setExpenseAmt(Double expenseAmt);

	public abstract String getExpenseAmtCurrency();

	public abstract void setExpenseAmtCurrency(String expenseCurrency);

	public IRecoveryExpense getValue() {
		OBRecoveryExpense npl = new OBRecoveryExpense();
		AccessorUtil.copyValue(this, npl);
		return npl;
	}

	public void setValue(IRecoveryExpense recExpense) throws VersionMismatchException {
		checkVersionMismatch(recExpense);
		AccessorUtil.copyValue(recExpense, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	public Amount getExpenseAmount() {
		if (getExpenseAmt() != null) {
			return new Amount(getExpenseAmt().doubleValue(), getExpenseAmtCurrency());
		}
		else {
			return null;
		}
	}

	public void setExpenseAmount(Amount expenseAmount) {
		if (expenseAmount != null) {
			setExpenseAmt(new Double(expenseAmount.getAmountAsDouble()));
			setExpenseAmtCurrency(expenseAmount.getCurrencyCode());
		}
		else {
			setExpenseAmt(null);
		}
	}

	private void checkVersionMismatch(IRecoveryExpense recExpense) throws VersionMismatchException {
		if (getVersionTime() != recExpense.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + recExpense.getVersionTime());
		}
	}

	public Long ejbCreate(IRecoveryExpense recExpense) throws CreateException {

		long pk = ICMSConstant.LONG_MIN_VALUE;
		try {
			pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_RECOVERY_EXPENSE, true));
		}
		catch (Exception e) {
			CreateException ce = new CreateException("failed to retrieve sequence using ["
					+ ICMSConstant.SEQUENCE_RECOVERY_EXPENSE + "]; nested exception is " + e);
			ce.initCause(e);
			throw ce;
		}
		DefaultLogger.debug(this, "Creating Recovery Expense with ID: " + pk);
		setRecoveryExpenseID(new Long(pk));
		AccessorUtil.copyValue(recExpense, this, EXCLUDE_METHOD);

		if (isStaging()) {
			// if (recExpense.getRefID() != ICMSConstant.LONG_MIN_VALUE &&
			// recExpense.getRefID() > 0) {
			// setRefID(recExpense.getRefID());
			// } else
			if ((recExpense.getRecoveryExpenseID() != null)
					&& (recExpense.getRecoveryExpenseID().longValue() != ICMSConstant.LONG_MIN_VALUE)) {
				setRefID(recExpense.getRecoveryExpenseID().longValue());
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

	protected boolean isStaging() {
		return false;
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * @param recExpense of type IRecoveryExpense
	 */
	public void ejbPostCreate(IRecoveryExpense recExpense) {
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

	public abstract String getExpenseType();

	public abstract void setExpenseType(String expenseType);

	public abstract Date getDateOfExpense();

	public abstract void setDateOfExpense(Date dateOfExpense);

	public abstract String getRemarks();

	public abstract void setRemarks(String remarks);

	public abstract long getRefID();

	public abstract void setRefID(long refID);

	public abstract String getSettled();

	public abstract void setSettled(String settled);
}