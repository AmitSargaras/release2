/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/warehouse/EBWarehouseBean.java,v 1.6 2004/08/31 08:37:03 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.warehouse;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.CommodityException;

/**
 * Entity bean implementation for security parameter.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/08/31 08:37:03 $ Tag: $Name: $
 */

public abstract class EBWarehouseBean implements IWarehouse, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * Get the warehouse ID.
	 * 
	 * @return long - warehouse ID
	 */
	public long getWarehouseID() {
		return this.getEBWarehouseID().longValue();
	}

	/**
	 * Set the warehouse ID.
	 * 
	 * @param id - long
	 */
	public void setWarehouseID(long id) {
		this.setEBWarehouseID(new Long(id));
	}

	/**
	 * Get the warehouse's contact.
	 * 
	 * @return IWarehouseContact - warehouse's contact
	 */
	public IWarehouseContact getContact() {
		OBWarehouseContact contact = new OBWarehouseContact();
		contact.setAddressLine1(this.getAddressLine1());
		contact.setAddressLine2(this.getAddressLine2());
		contact.setCity(this.getCity());
		contact.setState(this.getState());
		contact.setPostalCode(this.getPostalCode());
		contact.setCountryCode(this.getCountryCode());
		contact.setAttentionParty(this.getContactName());
		contact.setEmailAddress(this.getEmailAddress());
		contact.setTelephoneNumer(this.getTelephoneNumber());
		contact.setTelex(this.getTelephoneExtension());
		contact.setFaxNumber(this.getFaxNumber());
		return contact;
	}

	/**
	 * Set the warehouse's contact.
	 * 
	 * @param contact - IWarehouseContact
	 */
	public void setContact(IWarehouseContact contact) {
		this.setAddressLine1(((contact == null) || (contact.getAddressLine1() == null)) ? null : contact
				.getAddressLine1());
		this.setAddressLine2(((contact == null) || (contact.getAddressLine2() == null)) ? null : contact
				.getAddressLine2());
		this.setCity(((contact == null) || (contact.getCity() == null)) ? null : contact.getCity());
		this.setState(((contact == null) || (contact.getState() == null)) ? null : contact.getState());
		this.setPostalCode(((contact == null) || (contact.getPostalCode() == null)) ? null : contact.getPostalCode());
		this.setContactName(((contact == null) || (contact.getAttentionParty() == null)) ? null : contact
				.getAttentionParty());
		this.setTelephoneNumber(((contact == null) || (contact.getTelephoneNumer() == null)) ? null : contact
				.getTelephoneNumer());
		this.setTelephoneExtension(((contact == null) || (contact.getTelex() == null)) ? null : contact.getTelex());
		this.setFaxNumber(((contact == null) || (contact.getFaxNumber() == null)) ? null : contact.getFaxNumber());
		this.setEmailAddress(((contact == null) || (contact.getEmailAddress() == null)) ? null : contact
				.getEmailAddress());
	}

	public abstract Long getEBWarehouseID();

	public abstract void setEBWarehouseID(Long id);

	public abstract String getAddressLine1();

	public abstract void setAddressLine1(String addressLine1);

	public abstract String getAddressLine2();

	public abstract void setAddressLine2(String addressLine2);

	public abstract String getCity();

	public abstract void setCity(String city);

	public abstract String getState();

	public abstract void setState(String state);

	public abstract String getPostalCode();

	public abstract void setPostalCode(String postalCode);

	public abstract String getContactName();

	public abstract void setContactName(String name);

	public abstract String getTelephoneNumber();

	public abstract void setTelephoneNumber(String telNumber);

	public abstract String getTelephoneExtension();

	public abstract void setTelephoneExtension(String ext);

	public abstract String getFaxNumber();

	public abstract void setFaxNumber(String faxNumber);

	public abstract String getEmailAddress();

	public abstract void setEmailAddress(String emailAddr);

	public IWarehouse getValue() throws CommodityException {
		OBWarehouse value = new OBWarehouse();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	public void setValue(IWarehouse value) throws VersionMismatchException, CommodityException,
			ConcurrentUpdateException {
		checkVersionMismatch(value);
		AccessorUtil.copyValue(value, this, new String[] { "getWarehouseID", "setWarehouseID", "getWarehousePK",
				"setWarehouseID" });
		this.setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this security parameter
	 * 
	 * @param value security parameter
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(IWarehouse value) throws VersionMismatchException {
		if (getVersionTime() != value.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + value.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value - IWarehouse object
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IWarehouse value) throws CreateException {
		AccessorUtil.copyValue(value, this, new String[] { "getWarehousePK", "setWarehousePK", "getWarehouseID",
				"setWarehouseID" });
		this.setEBWarehouseID(new Long(value.getWarehouseID()));
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
	public void ejbPostCreate(IWarehouse value) throws CreateException {
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
