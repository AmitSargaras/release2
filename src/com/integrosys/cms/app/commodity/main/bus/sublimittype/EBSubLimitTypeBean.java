/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/sublimittype/EBSubLimitTypeBean.java,v 1.2 2005/10/07 02:47:24 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.sublimittype;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-16
 * @Tag 
 *      com.integrosys.cms.app.commodity.main.bus.sublimittype.EBSubLimitTypeBean
 *      .java
 */
public abstract class EBSubLimitTypeBean implements ISubLimitType, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * Get the subLimitTypeID.
	 * 
	 * @return long - subLimitTypeID
	 */
	public long getSubLimitTypeID() {
		return this.getSubLimitTypePK().longValue();
	}

	/**
	 * Set the subLimitTypeID.
	 * 
	 * @param id - long
	 */
	public void setSubLimitTypeID(long id) {
		this.setSubLimitTypePK(new Long(id));
	}

	public ISubLimitType getValue() throws CommodityException {
		OBSubLimitType value = new OBSubLimitType();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	public void setValue(ISubLimitType value) throws VersionMismatchException, CommodityException,
			ConcurrentUpdateException {
		checkVersionMismatch(value);
		AccessorUtil.copyValue(value, this, new String[] { "getSubLimitTypeID", "setSubLimitTypeID",
				"getSubLimitTypePK", "setSubLimitTypePK" });
		this.setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this security parameter
	 * 
	 * @param value security parameter
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(ISubLimitType value) throws VersionMismatchException {
		if (getVersionTime() != value.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + value.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value - ISubLimitType object
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ISubLimitType value) throws CreateException {
		AccessorUtil.copyValue(value, this, new String[] { "getSubLimitTypeID", "setSubLimitTypeID",
				"getSubLimitTypePK", "setSubLimitTypePK" });
		this.setSubLimitTypePK(new Long(value.getSubLimitTypeID()));
		if (value.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE) {
			setCommonRef(value.getSubLimitTypeID());
		}
		else {
			setCommonRef(value.getCommonRef());
		}
		setVersionTime(VersionGenerator.getVersionNumber());
		return null;
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param value object of IWarehouse
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ISubLimitType value) throws CreateException {
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
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

	public abstract void setSubLimitTypePK(Long subLimitTypeID);

	public abstract Long getSubLimitTypePK();

	public abstract String getLimitType();

	public abstract void setLimitType(String limitType);

	public abstract String getSubLimitType();

	public abstract void setSubLimitType(String subLimitType);

	public abstract long getGroupID();

	public abstract void setGroupID(long grpID);

	public abstract long getCommonRef();

	public abstract void setCommonRef(long commonRef);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract String getStatus();

	public abstract void setStatus(String status);
}
