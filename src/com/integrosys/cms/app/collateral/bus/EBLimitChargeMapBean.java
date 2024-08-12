/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBLimitChargeMapBean.java,v 1.14 2006/08/23 03:30:57 nkumar Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for limit charge map entity.
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/08/23 03:30:57 $ Tag: $Name: $
 */
public abstract class EBLimitChargeMapBean extends OBCollateralLimitMap implements ILimitChargeMap, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the limit charge. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getLimitChargeMapID", "getChargeDetailID" };

	/**
	 * Get mapping id of limit and charge.
	 * 
	 * @return long
	 */
	public long getLimitChargeMapID() {
		if (getEBLimitChargeMapID() != null) {
			return getEBLimitChargeMapID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Set mapping id for limit and charge.
	 * 
	 * @param limitChargeMapID of type long
	 */
	public void setLimitChargeMapID(long limitChargeMapID) {
		setEBLimitChargeMapID(new Long(limitChargeMapID));
	}

	/**
	 * Get charge detail id.
	 * 
	 * @return long
	 */
	public long getChargeDetailID() {
		if (getEBChargeDetailID() != null) {
			return getEBChargeDetailID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Set charge detail id.
	 * 
	 * @param chargeDetailID of type long
	 */
	public void setChargeDetailID(long chargeDetailID) {
		setEBChargeDetailID(new Long(chargeDetailID));
	}

	/**
	 * Gets the CoBorrowerLimitID
	 * 
	 * @return long
	 */
	public long getCoBorrowerLimitID() {
		if (getEBCoBorrowerLimitID() != null) {
			return getEBCoBorrowerLimitID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Sets the CoBorrowerLimitID
	 * 
	 * @param CoBorrowerLimitID
	 */
	public void setCoBorrowerLimitID(long coBorrowerLimitID) {
		setEBCoBorrowerLimitID(coBorrowerLimitID == ICMSConstant.LONG_INVALID_VALUE ? null
				: new Long(coBorrowerLimitID));
	}

	/**
	 * Gets the LimitID
	 * 
	 * @return long
	 */
	public long getLimitID() {
		if (getEBLimitID() != null) {
			return getEBLimitID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Sets the LimitID
	 * 
	 * @param Limit ID
	 */
	public void setLimitID(long limitID) {
		setEBLimitID(limitID == ICMSConstant.LONG_INVALID_VALUE ? null : new Long(limitID));
	}

	public abstract Long getEBLimitChargeMapID();

	public abstract void setEBLimitChargeMapID(Long limitChargeMapID);

	public abstract Long getEBChargeDetailID();

	public abstract void setEBChargeDetailID(Long chargeDetailID);

	public abstract Long getEBLimitID();

	public abstract void setEBLimitID(Long eBLimitID);

	public abstract long getCollateralID();

	public abstract void setCollateralID(long collateralID);

	public abstract long getChargeID();

	public abstract void setChargeID(long chargeID);

	public abstract Long getEBCoBorrowerLimitID();

	public abstract void setEBCoBorrowerLimitID(Long eBCoBorrowerLimitID);

	public abstract String getCustomerCategory();

	public abstract void setCustomerCategory(String customerCategory);

	public abstract void setStatus(String status);

	public abstract long getCmsLimitProfileId();

	public abstract void setCmsLimitProfileId(long cmsLimitProfileId);

	/**
	 * Get the limit charge map business object.
	 * 
	 * @return ILimitChargeMap
	 */
	public ILimitChargeMap getValue() {
		OBLimitChargeMap chargeMap = new OBLimitChargeMap();
		AccessorUtil.copyValue(this, chargeMap);

		try {
			EBCollateralLimitMapLocalHome ejbHome = getEBCollateralLimitMapLocalHome();
			EBCollateralLimitMapLocal ejb = ejbHome.findByPrimaryKey(new Long(chargeMap.getChargeID()));
			ICollateralLimitMap map = ejb.getValue();
			AccessorUtil.copyValue(map, chargeMap);
		}
		catch (FinderException e) {
			// do nothing here.
		}

		return chargeMap;
	}

	/**
	 * Set the limit charge map to this entity.
	 * 
	 * @param chargeMap is of type ILimitChargeMap
	 */
	public void setValue(ILimitChargeMap chargeMap) {
		AccessorUtil.copyValue(chargeMap, this, EXCLUDE_METHOD);
	}

	/**
	 * Create the limit charge map record.
	 * 
	 * @param chargeMap the limit charge map
	 * @return Long
	 * @throws CreateException on error creating the charge map
	 */
	public Long ejbCreate(ILimitChargeMap chargeMap) throws CreateException {
		try {
			String mapID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_LIMIT_CHARGE_MAP, true);
			AccessorUtil.copyValue(chargeMap, this, EXCLUDE_METHOD);
			setEBLimitChargeMapID(new Long(mapID));
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
	 * 
	 * @param limitMap is of type ILimitChargeMap
	 */
	public void ejbPostCreate(ILimitChargeMap limitMap) {
	}

	/**
	 * Get collateral limit map local home.
	 * 
	 * @return EBCollateralLimitMapLocalHome
	 */
	protected EBCollateralLimitMapLocalHome getEBCollateralLimitMapLocalHome() {
		EBCollateralLimitMapLocalHome ejbHome = (EBCollateralLimitMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COL_LIMIT_MAP_LOCAL_JNDI, EBCollateralLimitMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCollateralLimitMapLocalHome is Null!");
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

	public abstract String getStatus();
}