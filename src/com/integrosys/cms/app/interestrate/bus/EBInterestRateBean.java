/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for interest rate entity.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBInterestRateBean implements IInterestRate, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the interest rate. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getIntRateID" };

	public abstract Long getIntRateID();

	public abstract void setIntRateID(Long intRateID);

	public abstract String getIntRateType();

	public abstract void setIntRateType(String intRateType);

	public abstract Date getIntRateDate();

	public abstract void setIntRateDate(Date intRateDate);

	public abstract Double getIntRatePercent();

	public abstract void setIntRatePercent(Double intRatePercent);

	public abstract long getGroupID();

	public abstract void setGroupID(long groupID);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#getValue
	 */
	public IInterestRate getValue() {
		OBInterestRate intRate = new OBInterestRate();
		AccessorUtil.copyValue(this, intRate);
		return intRate;
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#setValue
	 */
	public void setValue(IInterestRate intRate) throws VersionMismatchException {
		checkVersionMismatch(intRate);
		AccessorUtil.copyValue(intRate, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.bus.IInterestRate#setIntRateValue
	 */
	public void setIntRateValue(IInterestRate intRate) throws VersionMismatchException {
		checkVersionMismatch(intRate);
		setIntRatePercent(intRate.getIntRatePercent());
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this interest rate.
	 * 
	 * @param intRate of type IInterestRate
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(IInterestRate intRate) throws VersionMismatchException {
		if (getVersionTime() != intRate.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + intRate.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param intRate of type IInterestRate
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IInterestRate intRate) throws CreateException {
		try {

			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_INT_RATE, true));
			DefaultLogger.debug(this, "Creating Interest Rate with ID: " + pk);
			setIntRateID(new Long(pk));
			AccessorUtil.copyValue(intRate, this, EXCLUDE_METHOD);

			if (intRate.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
				setGroupID(pk);
			}
			if (intRate.getIntRatePercent() != null) {
				setVersionTime(VersionGenerator.getVersionNumber());
			}
			return new Long(pk);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * @param intRate of type IInterestRate
	 */
	public void ejbPostCreate(IInterestRate intRate) {
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
}