/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBApprovedCommodityTypeBean.java,v 1.11 2004/08/18 06:18:39 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for approved commodity type.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2004/08/18 06:18:39 $ Tag: $Name: $
 */
public abstract class EBApprovedCommodityTypeBean implements IApprovedCommodityType, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during create/update financing document.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getApprovedCommodityTypeID", "getCommonRef" };

	/**
	 * Get approved commodity type id.
	 * 
	 * @return long
	 */
	public long getApprovedCommodityTypeID() {
		return getEBApprovedCommodityTypeID().longValue();
	}

	/**
	 * Set approved commodity type id.
	 * 
	 * @param id of type long
	 */
	public void setApprovedCommodityTypeID(long id) {
		setEBApprovedCommodityTypeID(new Long(id));
	}

	/**
	 * Get approved commodity type profile.
	 * 
	 * @return IProfile
	 */
	public IProfile getProfile() {
		try {
			if (getProfileID() != null) {
				return CommodityMaintenanceProxyFactory.getProxy().getProfileByProfileID(getProfileID().longValue());
			}
			return null;
		}
		catch (CommodityException e) {
			DefaultLogger.error(this, "", e);
			throw new EJBException(e);
		}
	}

	/**
	 * Set approved commodity type profile.
	 * 
	 * @param profile of type IProfile
	 */
	public void setProfile(IProfile profile) {
		setProfileID(new Long(profile.getProfileID()));
	}

	public abstract Long getEBApprovedCommodityTypeID();

	public abstract void setEBApprovedCommodityTypeID(Long pk);

	public abstract Long getProfileID();

	public abstract void setProfileID(Long pID);

	/**
	 * Soft delete this approved comodity type.
	 */
	public void softDelete() {
		setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Get an instance of this approved commodity type.
	 * 
	 * @return IApprovedCommodityType
	 */
	public IApprovedCommodityType getValue() {
		OBApprovedCommodityType value = new OBApprovedCommodityType();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist the newly updated approved commodity type.
	 * 
	 * @param value of type IApprovedCommodityType
	 */
	public void setValue(IApprovedCommodityType value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value of type IApprovedCommodityType
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IApprovedCommodityType value) throws CreateException {
		try {
			String pk = (new SequenceManager())
					.getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_APPROVED_COMMODITY_TYPE, true);
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setEBApprovedCommodityTypeID(new Long(pk));

			if (value.getCommonRef() == ICMSConstant.LONG_MIN_VALUE) {
				this.setCommonRef(getApprovedCommodityTypeID());
			}
			else {
				// else maintain this reference id.
				setCommonRef(value.getCommonRef());
			}

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
	 * @param value of type IApprovedCommodityType
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IApprovedCommodityType value) throws CreateException {
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
}