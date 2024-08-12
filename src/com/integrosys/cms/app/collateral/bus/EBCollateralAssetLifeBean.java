/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus;

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
 * Entity bean implementation for collateral asset life entity.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBCollateralAssetLifeBean extends OBCollateralAssetLife implements ICollateralAssetLife,
		EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during update to the security asset
	 * life.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getSubTypeCode" };

	public abstract String getSubTypeCode();

	public abstract void setSubTypeCode(String subTypeCode);

	public abstract int getLifeSpan();

	public abstract void setLifeSpan(int lifeSpanValue);

	public abstract long getGroupID();

	public abstract void setGroupID(long groupID);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	/**
	 * Get the collateral type business object.
	 * 
	 * @return collateral type object
	 */
	public ICollateralAssetLife getValue() {
		OBCollateralAssetLife assetLife = new OBCollateralAssetLife();
		AccessorUtil.copyValue(this, assetLife);
		return assetLife;
	}

	/**
	 * Set the collateral assetLife to this entity.
	 * 
	 * @param colType is of type ICollateralAssetLife
	 * @throws VersionMismatchException if the assetLife's version is invalid
	 */
	public void setValue(ICollateralAssetLife assetLife) throws VersionMismatchException {
		checkVersionMismatch(assetLife);
		AccessorUtil.copyValue(assetLife, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Set the life span value for security assetLife.
	 * 
	 * @param assetLife of type ICollateralAssetLife
	 * @throws VersionMismatchException if the assetLife's version is invalid
	 */
	public void setLifeSpanValue(ICollateralAssetLife assetLife) throws VersionMismatchException {
		checkVersionMismatch(assetLife);
		setLifeSpan(assetLife.getLifeSpan());
		setStatus(assetLife.getStatus());
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this assetLife.
	 * 
	 * @param assetLife of type ICollateralAssetLife
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(ICollateralAssetLife assetLife) throws VersionMismatchException {
		if (getVersionTime() != assetLife.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + assetLife.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param assetLife of type ICollateralAssetLife
	 * @throws CreateException on error creating the entity object
	 */
	public String ejbCreate(ICollateralAssetLife assetLife) throws CreateException {
		try {
			String id = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COL_ASSETLIFE, true);
			AccessorUtil.copyValue(assetLife, this);
			if (assetLife.getGroupID() == ICMSConstant.LONG_MIN_VALUE) {
				setGroupID(Long.parseLong(id));
			}
			setVersionTime(VersionGenerator.getVersionNumber());
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * @param assetLife of type ICollateralAssetLife
	 */
	public void ejbPostCreate(ICollateralAssetLife assetLife) {
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