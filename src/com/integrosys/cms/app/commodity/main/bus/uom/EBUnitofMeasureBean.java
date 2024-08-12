/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/EBUnitofMeasureBean.java,v 1.9 2004/08/20 02:46:00 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for user defined unit of measure.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.9 $
 * @since $Date: 2004/08/20 02:46:00 $ Tag: $Name: $
 */
public abstract class EBUnitofMeasureBean implements IUnitofMeasure, EntityBean {

	private EntityContext context;

	private static final String[] EXCLUDE_METHOD = new String[] { "getUnitofMeasureID"/*
																					 * ,
																					 * "getCommonReferenceID"
																					 */};

	/**
	 * Get the user-defined unit of measure ID.
	 * 
	 * @return long - ID for the unit of measure
	 */
	public long getUnitofMeasureID() {
		return getEBUnitofMeasureID().longValue();
	}

	/**
	 * Set the user-defined unit of measure ID
	 * 
	 * @param unitofMeasureID
	 */
	public void setUnitofMeasureID(long unitofMeasureID) {
		setEBUnitofMeasureID(new Long(unitofMeasureID));
	}

	/**
	 * Get the profile ID for which the unit of measure is being defined for.
	 * 
	 * @return long - ID for the commodity profile
	 */
	public long getProfileID() {
		return getEBProfileID().longValue();
	}

	/**
	 * Set the profile ID for which the unit of meausre is being defined for.
	 * 
	 * @param profileID
	 */
	public void setProfileID(long profileID) {
		setEBProfileID(new Long(profileID));
	}

	/**
	 * Get the group ID to which the unit of measure belongs. Since a set of
	 * unit of measure can be submitted in a singal transaction, this group ID
	 * identify the set of unit of measure that were submitted together.
	 * 
	 * @return long - ID for the group
	 */
	public long getGroupID() {
		return getEBGroupID().longValue();
	}

	/**
	 * Set the group ID to which the unit of meausre belongs to. Since a set of
	 * unit of measure can be submitted in a singal transaction, this group ID
	 * identify the set of unit of measure that were submitted together.
	 * 
	 * @param groupID
	 */
	public void setGroupID(long groupID) {
		setEBGroupID(new Long(groupID));
	}

	/**
	 * Get the equivalent quantity in the unit of measure commonly used in the
	 * market for the commodity.
	 * 
	 * @return Quantity - quantity
	 */
	public Quantity getMarketQuantity() {
		if ((getEBMarketQuantity() == null) || (getEBMarketUOM() == null)) {
			return null;
		}
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBMarketUOM());
		return new Quantity(getEBMarketQuantity(), uom);
	}

	/**
	 * Set the equivalent quantity in the unit of measure commonly used in the
	 * market for the commodity.
	 * 
	 * @param quantity - the equivalent quantity
	 */
	public void setMarketQuantity(Quantity quantity) {
		setEBMarketQuantity(quantity == null ? null : quantity.getQuantity());
		setEBMarketUOM(quantity == null ? null : quantity.getUnitofMeasure().getID());
	}

	/**
	 * Get the equivalent quanity in the metric unit of measure for the
	 * user-defined unit of measure.
	 * 
	 * @return Quantity - quantiy
	 */
	public Quantity getMetricQuantity() {
		if ((getEBMetricQuantity() == null) || (getEBMetricUOM() == null)) {
			return null;
		}
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBMetricUOM());
		return new Quantity(getEBMetricQuantity(), uom);
	}

	/**
	 * Set the equivalent quantity in the metric unit of measure for the
	 * user-defined unit of measure.
	 * 
	 * @param quantity - metric quantity
	 */
	public void setMetricQuantity(Quantity quantity) {
		setEBMetricQuantity(quantity == null ? null : quantity.getQuantity());
		setEBMetricUOM(quantity == null ? null : quantity.getUnitofMeasure().getID());
	}

	/**
	 * Get the common reference ID for the unit of measure. Since a set of unit
	 * of measure can be submitted in a singal transaction, this common
	 * reference ID identifies the staging record from which an actual biz
	 * record is created. In other words, the related staging and actual record
	 * will have the same common reference ID.
	 * 
	 * @return long - ID for the group
	 */
	public long getCommonReferenceID() {
		return getEBCommonReferenceID().longValue();
	}

	/**
	 * Set the common reference ID to which the unit of meausre belongs to.
	 * Since a set of unit of measure can be submitted in a singal transaction,
	 * this common reference ID identifies the staging record from which an
	 * actual biz record is created. In other words, the related staging and
	 * actual record will have the same common reference ID.
	 * 
	 * @param commonRefID
	 */
	public void setCommonReferenceID(long commonRefID) {
		setEBCommonReferenceID(new Long(commonRefID));
	}

	/**
	 * Get commodity profile information.
	 * 
	 * @return IProfile
	 */
	public IProfile getCommodityProfile() {
		return null;
	}

	/**
	 * Set commodity profile information.
	 * 
	 * @param commodityProfile of type IProfile
	 */
	public void setCommodityProfile(IProfile commodityProfile) {
	}

	public abstract Long getEBUnitofMeasureID();

	public abstract void setEBUnitofMeasureID(Long unitofMeasureID);

	public abstract Long getEBProfileID();

	public abstract void setEBProfileID(Long profileID);

	public abstract Long getEBGroupID();

	public abstract void setEBGroupID(Long groupID);

	public abstract Long getEBCommonReferenceID();

	public abstract void setEBCommonReferenceID(Long commonRefID);

	public abstract BigDecimal getEBMarketQuantity();

	public abstract void setEBMarketQuantity(BigDecimal quantity);

	public abstract String getEBMarketUOM();

	public abstract void setEBMarketUOM(String uom);

	public abstract BigDecimal getEBMetricQuantity();

	public abstract void setEBMetricQuantity(BigDecimal quantity);

	public abstract String getEBMetricUOM();

	public abstract void setEBMetricUOM(String uom);

	/**
	 * Retrieve an instance of a UnitofMeasure
	 * 
	 * @return IUnitofMeasure - the object encapsulating the UnitofMeasure info
	 * @throws CommodityException - wrapper of any exceptions within.
	 */
	public IUnitofMeasure getValue() throws CommodityException {
		OBUnitofMeasure value = new OBUnitofMeasure();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Set the UnitofMeasure object
	 * 
	 * @param value - an object of IUnitofMeasure
	 * @throws ConcurrentUpdateException which is thrown when more than one
	 *         client accessing the method same time.
	 */
	public void setValue(IUnitofMeasure value) throws VersionMismatchException, CommodityException,
			ConcurrentUpdateException {
		checkVersionMismatch(value);
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		this.setVersionTime(VersionGenerator.getVersionNumber());
		DefaultLogger.debug(this, "########## UOM setValue : setting version number " + getVersionTime());
	}

	/**
	 * Check the version of this UnitofMeasure
	 * 
	 * @param value security parameter
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the entity version is invalid
	 */
	private void checkVersionMismatch(IUnitofMeasure value) throws VersionMismatchException {
		if (getVersionTime() != value.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + value.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param uom - IUnitofMeasure object
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(IUnitofMeasure uom) throws CreateException {
		try {
			String uomID = (new SequenceManager()).getSeqNum(CommodityConstant.SEQUENCE_UOM, true);
			AccessorUtil.copyValue(uom, this, EXCLUDE_METHOD);
			setEBUnitofMeasureID(new Long(uomID));

			if (uom.getCommonReferenceID() == ICMSConstant.LONG_MIN_VALUE) {
				this.setCommonReferenceID(getUnitofMeasureID());
			}
			else {
				// else maintain this reference id.
				setCommonReferenceID(uom.getCommonReferenceID());
			}
			this.setVersionTime(VersionGenerator.getVersionNumber());
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * Matching method of ejbCreate. The container invokes the matching
	 * ejbPostCreate method on an instance after it invokes the ejbCreate method
	 * with the same arguments. It executes in the same transaction context as
	 * that of the matching ejbCreate method.
	 * 
	 * @param uom is of type IUnitofMeasure
	 * @throws CreateException on error creating references for this unit of
	 *         measure.
	 */
	public void ejbPostCreate(IUnitofMeasure uom) throws CreateException {
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
	 * A container invokes this method on an instance before the instance
	 * becomes disassociated with a specific EJB object. After this method
	 * completes, the container will place the instance into the pool of
	 * available instances. This method executes in an unspecified transaction
	 * context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by loading it from the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbLoad() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by storing it to the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbStore() {
	}

	/**
	 * A container invokes this method before it removes the EJB object that is
	 * currently associated with the instance. It is invoked when a client
	 * invokes a remove operation on the enterprise Bean's home or remote
	 * interface. It transitions the instance from the ready state to the pool
	 * of available instances. It is called in the transaction context of the
	 * remove operation.
	 */
	public void ejbRemove() throws RemoveException {
	}
}
