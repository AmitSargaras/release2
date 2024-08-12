/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.util.Date;

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

/**
 * Entity bean implementation for RecoveryIncome entity.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBRecoveryIncomeBean implements IRecoveryIncome, EntityBean {

	private static final long serialVersionUID = 5377115609712931100L;

	private EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getRecoveryIncomeID" };

	public abstract Long getRecoveryIncomeID();

	public abstract void setRecoveryIncomeID(Long recoveryIncomeID);

	/*
	 * public abstract Long getRecoveryID(); public abstract void
	 * setRecoveryID(Long recoveryID);
	 */

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract Date getRecoveryDate();

	public abstract void setRecoveryDate(Date recoveryDate);

	public abstract Double getTotalAmtRecovered();

	public abstract void setTotalAmtRecovered(Double totalAmtRecovered);

	public abstract String getTotalAmtRecoveredCurrency();

	public abstract void setTotalAmtRecoveredCurrency(String totalAmtRecoveredCurr);

	public abstract void setStatus(String status);

	public IRecoveryIncome getValue() {
		OBRecoveryIncome npl = new OBRecoveryIncome();
		AccessorUtil.copyValue(this, npl);
		return npl;
	}

	public void setValue(IRecoveryIncome recIncome) throws VersionMismatchException {
		checkVersionMismatch(recIncome);
		AccessorUtil.copyValue(recIncome, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	public Amount getTotalAmountRecovered() {
		if (getTotalAmtRecovered() != null) {
			return new Amount(getTotalAmtRecovered().doubleValue(), getTotalAmtRecoveredCurrency());
		}
		else {
			return null;
		}
	}

	public void setTotalAmountRecovered(Amount totalAmountRecovered) {
		if (totalAmountRecovered != null) {
			setTotalAmtRecovered(new Double(totalAmountRecovered.getAmountAsDouble()));
			setTotalAmtRecoveredCurrency(totalAmountRecovered.getCurrencyCode());
		}
		else {
			setTotalAmtRecovered(null);
		}
	}

	private void checkVersionMismatch(IRecoveryIncome recIncome) throws VersionMismatchException {
		if (getVersionTime() != recIncome.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + recIncome.getVersionTime());
		}
	}

	public Long ejbCreate(IRecoveryIncome recIncome) throws CreateException {

		long pk = ICMSConstant.LONG_MIN_VALUE;
		try {
			pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_RECOVERY_INCOME, true));
		}
		catch (Exception e) {
			CreateException ce = new CreateException("failed to retrieve sequence using ["
					+ ICMSConstant.SEQUENCE_RECOVERY_INCOME + "]; nested exception is " + e);
			ce.initCause(e);
			throw ce;
		}
		DefaultLogger.debug(this, "Creating Recovery Income with ID: " + pk);
		setRecoveryIncomeID(new Long(pk));
		AccessorUtil.copyValue(recIncome, this, EXCLUDE_METHOD);

		if (isStaging()) {
			if ((recIncome.getRecoveryIncomeID() != null)
					&& (recIncome.getRecoveryIncomeID().longValue() != ICMSConstant.LONG_MIN_VALUE)) {
				setRefID(recIncome.getRecoveryIncomeID().longValue());
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
	 * @param recIncome of type IRecoveryIncome
	 */
	public void ejbPostCreate(IRecoveryIncome recIncome) {
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

	public abstract String getRemarks();

	public abstract void setRemarks(String remarks);

	public abstract String getStatus();

	public abstract long getRefID();

	public abstract void setRefID(long refID);
}