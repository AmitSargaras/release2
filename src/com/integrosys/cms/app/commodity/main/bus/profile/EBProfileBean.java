/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/EBProfileBean.java,v 1.12 2006/03/03 05:01:04 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

/**
 * Profile bean implementation class.
 *
 * @author  $Author: hmbao $<br>
 * @version $Revision: 1.12 $
 * @since   $Date: 2006/03/03 05:01:04 $
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for security parameter.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/03/03 05:01:04 $ Tag: $Name: $
 */

public abstract class EBProfileBean implements IProfile, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	public abstract Long getProfilePK();

	public abstract void setProfilePK(Long pk);

	public long getProfileID() {
		return this.getProfilePK().longValue();
	}

	public void setProfileID(long id) {
		this.setProfilePK(new Long(id));
	}

	public ISupplier[] getSuppliers() {
		Iterator i = getSuppliersCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			ISupplier supplier = ((EBSupplierLocal) i.next()).getValue();
			if ((supplier.getStatus() != null) && supplier.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			arrayList.add(supplier);
		}

		return (OBSupplier[]) arrayList.toArray(new OBSupplier[arrayList.size()]);
	}

	public void setSuppliers(ISupplier[] suppliers) {
	}

	public IBuyer[] getBuyers() {
		Iterator i = getBuyersCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			IBuyer buyer = ((EBBuyerLocal) i.next()).getValue();
			if ((buyer.getStatus() != null) && buyer.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			arrayList.add(buyer);
		}

		return (OBBuyer[]) arrayList.toArray(new OBBuyer[arrayList.size()]);
	}

	public void setBuyers(IBuyer[] buyers) {
	}

	public Amount getUnitPrice() {
		return null;
	}

	public void setUnitPrice(Amount unitPrice) {
	}

	public Date getUnitPriceDate() {
		return null;
	}

	public void setUnitPriceDate(Date unitPriceDate) {
	}

	public abstract String getCategory();

	public abstract void setCategory(String category);

	public abstract String getProductType();

	public abstract void setProductType(String productType);

	public abstract String getProductSubType();

	public abstract void setProductSubType(String productSubType);

	public abstract String getReuterSymbol();

	public abstract void setReuterSymbol(String reuterSymbol);

	public abstract String getDifferentialSign();

	public abstract void setDifferentialSign(String dSign);

	public abstract BigDecimal getPriceDifferential();

	public abstract void setPriceDifferential(BigDecimal priceDifferential);

	public abstract String getMarketName();

	public abstract void setMarketName(String marketName);

	public abstract String getPriceType();

	public abstract void setPriceType(String priceType);

	public abstract String getRICType();

	public abstract void setRICType(String ricType);

	public abstract String getCountryArea();

	public abstract void setCountryArea(String s);

	public abstract String getChains();

	public abstract void setChains(String s);

	public abstract String getOutrights();

	public abstract void setOutrights(String s);

	public abstract long getGroupID();

	public abstract void setGroupID(long grpID);

	public abstract long getCommonRef();

	public abstract void setCommonRef(long commonRef);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract Collection getBuyersCMR();

	public abstract void setBuyersCMR(Collection buyers);

	public abstract Collection getSuppliersCMR();

	public abstract void setSuppliersCMR(Collection suppliers);

	public IProfile getValue() throws CommodityException {
		OBProfile value = new OBProfile();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	public void setValue(IProfile value) throws VersionMismatchException, CommodityException, ConcurrentUpdateException {
		checkVersionMismatch(value);
		AccessorUtil.copyValue(value, this, new String[] { "getProfilePK", "setProfilePK", "getProfileID",
				"setProfileID" });
		setReferences(value, false);
		this.setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this security parameter
	 * 
	 * @param value security parameter
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(IProfile value) throws VersionMismatchException {
		if (getVersionTime() != value.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + value.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value - IProfile object
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IProfile value) throws CreateException {

		AccessorUtil.copyValue(value, this, new String[] { "getProfilePK", "setProfilePK", "getProfileID",
				"setProfileID", "setSuppliers", "getSuppliers", "setBuyers", "getBuyers" });
		DefaultLogger.debug(this, "$DEBUG:::value.getProfileID() = " + value.getProfileID());
		this.setProfilePK(new Long(value.getProfileID()));
		setVersionTime(VersionGenerator.getVersionNumber());
		return null;
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param profile object of IProfile
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IProfile profile) throws CreateException {
		setReferences(profile, true);
	}

	/**
	 * Set the references to this commodity profile.
	 * 
	 * @param profile of type IProfile
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(IProfile profile, boolean isAdd) {
		try {
			setSuppliersRef(profile.getSuppliers(), isAdd);
			setBuyersRef(profile.getBuyers(), isAdd);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new EJBException(e);
		}
	}

	/**
	 * Sets suppliers for commodity profile.
	 * 
	 * @param suppliers - ISupplier[]
	 * @param isAdd - boolean
	 * @throws CreateException
	 */
	private void setSuppliersRef(ISupplier[] suppliers, boolean isAdd) throws CreateException {
		// get existing suppliers for the deal
		Collection existingList = getSuppliersCMR();
		if (isAdd || existingList.isEmpty()) {
			if ((suppliers != null) && (suppliers.length != 0)) {
				addSupplier(Arrays.asList(suppliers), existingList);
			}
			return;
		}
		else {
			Iterator it = existingList.iterator();
			int count = 0;
			while (it.hasNext()) {
				DefaultLogger.debug(this, "##### existing[" + count++ + "] : "
						+ ((EBSupplierLocal) it.next()).getValue());
			}

			// existing list is NOT empty
			// if suppliers is empty , remove all suppliers
			if ((suppliers == null) || (suppliers.length == 0)) {
				removeSupplier(existingList);
				return;
			}

			// put existing list into hashmap - excludes deleted values
			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBSupplierLocal supplierLocal = (EBSupplierLocal) existingListIterator.next();
				ISupplier supplier = supplierLocal.getValue();
				if ((supplier.getStatus() == null) || !supplier.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					existingListMap.put(new Long(supplier.getCommonReferenceID()), supplierLocal);
				}
			}

			ArrayList newList = new ArrayList();

			// compare supplier with existing list
			for (int i = 0; i < suppliers.length; i++) {
				ISupplier supplier = suppliers[i];
				Long commonRefID = new Long(supplier.getCommonReferenceID());
				EBSupplierLocal theExistingEjb = (EBSupplierLocal) existingListMap.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue((OBSupplier) supplier);
					existingListMap.remove(commonRefID);
					DefaultLogger.debug(this, "##### updating : " + supplier.getSupplierID() + " - "
							+ supplier.getName());
				}
				else {
					newList.add(supplier);
				}
			}

			// add new supplier
			addSupplier(newList, existingList);

			// remove deleted supplier
			EBSupplierLocal[] deletedList = (EBSupplierLocal[]) existingListMap.values()
					.toArray(new EBSupplierLocal[0]);
			removeSupplier(Arrays.asList(deletedList));
		}
	}

	/**
	 * Helper method to add all suppliers specified in the collection.
	 * 
	 * @param newSuppliers
	 * @param existingSuppliers
	 * @throws CreateException
	 */
	private void addSupplier(Collection newSuppliers, Collection existingSuppliers) throws CreateException {
		EBSupplierLocalHome theEjbHome = _getSupplierLocalHome();
		Iterator i = newSuppliers.iterator();
		while (i.hasNext()) {
			OBSupplier supplier = (OBSupplier) i.next();
			DefaultLogger.debug(this, "##### adding : " + supplier.getSupplierID() + " - " + supplier.getName());
			existingSuppliers.add(theEjbHome.create(supplier));
		}
	}

	/**
	 * Helper method to remove all suppliers specified in the collection.
	 * 
	 * @param suppliers - Collection of suppliers to be removed. Cannot be null.
	 */
	private void removeSupplier(Collection suppliers) {
		Iterator i = suppliers.iterator();
		while (i.hasNext()) {
			EBSupplierLocal theEjb = (EBSupplierLocal) i.next();
			removeSupplier(theEjb);
		}
	}

	/**
	 * Helper method to remove the said supplier.
	 * 
	 * @param anEjb - EBSupplierLocal
	 */
	private void removeSupplier(EBSupplierLocal anEjb) {
		OBSupplier supplier = anEjb.getValue();
		DefaultLogger.debug(this, "##### soft deleting : " + supplier.getSupplierID() + " - " + supplier.getName());
		supplier.setStatus(ICMSConstant.STATE_DELETED);
		anEjb.setValue(supplier);
	}

	/**
	 * Sets buyers for commodity profile.
	 * 
	 * @param buyers - ISupplier[]
	 * @param isAdd - boolean
	 * @throws CreateException
	 */
	private void setBuyersRef(IBuyer[] buyers, boolean isAdd) throws CreateException {

		// get existing buyers for the deal
		Collection existingList = getBuyersCMR();
		if (isAdd || existingList.isEmpty()) {
			if ((buyers != null) && (buyers.length != 0)) {
				addBuyer(Arrays.asList(buyers), existingList);
			}
			return;
		}
		else {
			Iterator it = existingList.iterator();
			int count = 0;
			while (it.hasNext()) {
				DefaultLogger.debug(this, "##### existing[" + count++ + "] : " + ((EBBuyerLocal) it.next()).getValue());
			}

			// existing list is NOT empty
			// if buyers is empty , remove all buyers
			if ((buyers == null) || (buyers.length == 0)) {
				removeBuyer(existingList);
				return;
			}

			// put existing list into hashmap - excludes deleted values
			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBBuyerLocal buyerLocal = (EBBuyerLocal) existingListIterator.next();
				IBuyer buyer = buyerLocal.getValue();
				if ((buyer.getStatus() == null) || !buyer.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					existingListMap.put(new Long(buyer.getCommonReferenceID()), buyerLocal);
				}
			}

			ArrayList newList = new ArrayList();

			// compare buyers with existing list
			for (int i = 0; i < buyers.length; i++) {
				IBuyer buyer = buyers[i];
				Long commonRefID = new Long(buyer.getCommonReferenceID());
				EBBuyerLocal theExistingEjb = (EBBuyerLocal) existingListMap.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue((OBBuyer) buyer);
					existingListMap.remove(commonRefID);
				}
				else {
					newList.add(buyer);
				}
			}

			// add new buyer
			addBuyer(newList, existingList);

			// remove deleted buyer
			EBBuyerLocal[] deletedList = (EBBuyerLocal[]) existingListMap.values().toArray(new EBBuyerLocal[0]);
			removeBuyer(Arrays.asList(deletedList));
		}
	}

	/**
	 * Helper method to add all suppliers specified in the collection.
	 * 
	 * @param newBuyers
	 * @param existingBuyers
	 * @throws CreateException
	 */
	private void addBuyer(Collection newBuyers, Collection existingBuyers) throws CreateException {
		DefaultLogger.debug(this, "######## prepare to add buyer #########");
		EBBuyerLocalHome theEjbHome = _getBuyerLocalHome();
		Iterator i = newBuyers.iterator();
		while (i.hasNext()) {
			OBBuyer buyer = (OBBuyer) i.next();
			DefaultLogger.debug(this, "##### adding : " + buyer.getBuyerID() + " - " + buyer.getName());
			existingBuyers.add(theEjbHome.create(buyer));
		}
	}

	/**
	 * Helper method to remove all buyers specified in the collection.
	 * 
	 * @param buyers - Collection of buyers to be removed. Cannot be null.
	 */
	private void removeBuyer(Collection buyers) {
		Iterator i = buyers.iterator();
		while (i.hasNext()) {
			EBBuyerLocal theEjb = (EBBuyerLocal) i.next();
			removeBuyer(theEjb);
		}
	}

	/**
	 * Helper method to remove the said buyer.
	 * 
	 * @param anEjb - EBBuyerLocal
	 */
	private void removeBuyer(EBBuyerLocal anEjb) {
		OBBuyer buyer = anEjb.getValue();
		DefaultLogger.debug(this, "##### soft deleting : " + buyer.getBuyerID() + " - " + buyer.getName());
		buyer.setStatus(ICMSConstant.STATE_DELETED);
		anEjb.setValue(buyer);
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
	public void ejbRemove() throws RemoveException {
		// remove Suppliers
		if (getSuppliersCMR() != null) {
			Iterator iterator = getSuppliersCMR().iterator();
			while (iterator.hasNext()) {
				EBSupplierLocal local = (EBSupplierLocal) iterator.next();
				iterator.remove();
				local.remove();
			}
		}
		// remove Buyers
		if (getBuyersCMR() != null) {
			Iterator iterator = getBuyersCMR().iterator();
			while (iterator.hasNext()) {
				EBBuyerLocal local = (EBBuyerLocal) iterator.next();
				iterator.remove();
				local.remove();
			}
		}
	}

	protected EBSupplierLocalHome _getSupplierLocalHome() {
		return (EBSupplierLocalHome) BeanController.getEJBLocalHome(JNDIConstants.EB_SUPPLIER_LOCAL_BEAN,
				EBSupplierLocalHome.class.getName());
	}

	protected EBBuyerLocalHome _getBuyerLocalHome() {
		return (EBBuyerLocalHome) BeanController.getEJBLocalHome(JNDIConstants.EB_BUYER_LOCAL_BEAN,
				EBBuyerLocalHome.class.getName());
	}

	protected long generatePK(String sequenceName) throws CommodityException {
		try {
			String seq = new SequenceManager().getSeqNum(sequenceName, true);
			return Long.parseLong(seq);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommodityException("Exception in generating Sequence '" + sequenceName
					+ "' \n The exception is : " + e);
		}

	}

	protected boolean isStaging = false;
}
