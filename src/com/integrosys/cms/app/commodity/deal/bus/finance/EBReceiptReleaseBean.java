/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBReceiptReleaseBean.java,v 1.3 2004/09/08 06:36:58 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for Deal Release details.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/09/08 06:36:58 $ Tag: $Name: $
 */
public abstract class EBReceiptReleaseBean implements IReceiptRelease, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during create/update release details. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getReleaseID", "getRefID" };

	/**
	 * Get warehouse receipt release id.
	 * 
	 * @return long
	 */
	public long getReleaseID() {
		return getEBReleaseID().longValue();
	}

	/**
	 * Set warehouse receipt release id.
	 * 
	 * @param releaseID of type long
	 */
	public void setReleaseID(long releaseID) {
		setEBReleaseID(new Long(releaseID));
	}

	/**
	 * Get released quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getReleasedQty() {
		if ((getEBReleasedQty() == null) || (getEBReleasedQtyUOM() == null)) {
			return null;
		}
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBReleasedQtyUOM());
		return new Quantity(getEBReleasedQty(), uom);
	}

	/**
	 * set released quantity.
	 * 
	 * @param releasedQty of type Quantity
	 */
	public void setReleasedQty(Quantity releasedQty) {
		setEBReleasedQty(releasedQty == null ? null : releasedQty.getQuantity());
		setEBReleasedQtyUOM(releasedQty == null ? null : releasedQty.getUnitofMeasure().getID());
	}

	/**
	 * Get released warehouse receipts.
	 * 
	 * @return ISettleWarehouseReceipt[]
	 */
	public ISettleWarehouseReceipt[] getSettleWarehouseReceipts() {
		Iterator i = getSettleWarehouseReceiptsCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			ISettleWarehouseReceipt map = ((EBSettleWarehouseReceiptLocal) i.next()).getValue();
			if ((map.getStatus() != null) && map.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			arrayList.add(map);
		}

		return (OBSettleWarehouseReceipt[]) arrayList.toArray(new OBSettleWarehouseReceipt[0]);
	}

	/**
	 * Set released warehouse receipts.
	 * 
	 * @param settleWarehouseReceipts of type ISettleWarehouseReceipt[]
	 */
	public void setSettleWarehouseReceipts(ISettleWarehouseReceipt[] settleWarehouseReceipts) {
	}

	public abstract Long getEBReleaseID();

	public abstract void setEBReleaseID(Long releaseID);

	public abstract BigDecimal getEBReleasedQty();

	public abstract void setEBReleasedQty(BigDecimal eBReleasedQty);

	public abstract String getEBReleasedQtyUOM();

	public abstract void setEBReleasedQtyUOM(String uomCode);

	public abstract Collection getSettleWarehouseReceiptsCMR();

	public abstract void setSettleWarehouseReceiptsCMR(Collection settleWarehouseReceiptsCMR);

	public abstract void setStatus(String status);

	// implemented in OB
	public Quantity getTotalReleasedQuantity() {
		return null;
	}

	/**
	 * Get the release details.
	 * 
	 * @return release details
	 */
	public IReceiptRelease getValue() {
		OBReceiptRelease release = new OBReceiptRelease();
		AccessorUtil.copyValue(this, release);
		return release;
	}

	/**
	 * Set the release details to this entity.
	 * 
	 * @param release is of type IReceiptRelease
	 */
	public void setValue(IReceiptRelease release) {
		AccessorUtil.copyValue(release, this, EXCLUDE_METHOD);
		setReferences(release, false);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param release of type IReceiptRelease
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IReceiptRelease release) throws CreateException {
		try {
			String releaseID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_RELEASE, true);
			AccessorUtil.copyValue(release, this, EXCLUDE_METHOD);
			setEBReleaseID(new Long(releaseID));

			if (release.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getReleaseID());
			}
			else {
				// else maintain this reference id.
				setRefID(release.getRefID());
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
	 * @param release of type IReceiptRelease
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IReceiptRelease release) throws CreateException {
		setReferences(release, true);
	}

	/**
	 * Set the references to this release details
	 * 
	 * @param release of type IReceiptRelease
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(IReceiptRelease release, boolean isAdd) {
		try {
			setWarehouseReceiptsRef(release.getSettleWarehouseReceipts(), isAdd);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new EJBException(e);
		}
	}

	/**
	 * Set settlement warehouse receipts.
	 * 
	 * @param receipts of type ISettleWarehouseReceipt[]
	 * @param isAdd true if the caller is from ejb post create, otherwise false
	 * @throws CreateException on error creating references
	 */
	public void setWarehouseReceiptsRef(ISettleWarehouseReceipt[] receipts, boolean isAdd) throws CreateException {
		// remove all existing receipts
		if ((receipts == null) || (receipts.length == 0)) {
			removeAllWarehouseReceipts();
			return;
		}

		EBSettleWarehouseReceiptLocalHome ejbHome = getEBSettleWarehouseReceiptLocalHome();

		Collection c = getSettleWarehouseReceiptsCMR();

		// add all newly added receipts
		if (isAdd || (c.size() == 0)) {
			for (int i = 0; i < receipts.length; i++) {
				c.add(ejbHome.create(receipts[i]));
			}
			return;
		}

		// remove existing receipts that are not in newly updated list.
		removeWarehouseReceipt(c, receipts);

		// update existing receipts and add new receipts.
		Iterator iterator = c.iterator();
		ArrayList newReceipts = new ArrayList();

		for (int i = 0; i < receipts.length; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBSettleWarehouseReceiptLocal theEjb = (EBSettleWarehouseReceiptLocal) iterator.next();
				ISettleWarehouseReceipt value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (receipts[i].getRefID() == value.getRefID()) {
					// update existing receipt
					theEjb.setValue(receipts[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newReceipts.add(receipts[i]);
			}
			iterator = c.iterator();
		}

		iterator = newReceipts.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((ISettleWarehouseReceipt) iterator.next()));
		}
	}

	/**
	 * Get settlement warehouse receipt local home.
	 * 
	 * @return EBSettleWarehouseReceiptLocalHome
	 */
	protected EBSettleWarehouseReceiptLocalHome getEBSettleWarehouseReceiptLocalHome() {
		EBSettleWarehouseReceiptLocalHome ejbHome = (EBSettleWarehouseReceiptLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SETTLE_WAREHOUSE_LOCAL_JNDI, EBSettleWarehouseReceiptLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBSettleWarehouseReceiptLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to delete all warehouse details.
	 */
	private void removeAllWarehouseReceipts() {
		Collection c = getSettleWarehouseReceiptsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBSettleWarehouseReceiptLocal theEjb = (EBSettleWarehouseReceiptLocal) iterator.next();
			deleteWarehouseReceipt(theEjb);
		}
	}

	/**
	 * Helper method to delete warehouse in receiptCol that are not contained in
	 * receiptList.
	 * 
	 * @param receiptCol a list of old settlement warehouse receipt ejb objects
	 * @param receiptList a list of newly updated settlement warehouse receipts
	 */
	private void removeWarehouseReceipt(Collection receiptCol, ISettleWarehouseReceipt[] receiptList) {
		Iterator iterator = receiptCol.iterator();

		while (iterator.hasNext()) {
			EBSettleWarehouseReceiptLocal theEjb = (EBSettleWarehouseReceiptLocal) iterator.next();
			ISettleWarehouseReceipt receipt = theEjb.getValue();
			if ((receipt.getStatus() != null) && receipt.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < receiptList.length; i++) {
				if (receiptList[i].getRefID() == receipt.getRefID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteWarehouseReceipt(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete a warehouse receipt
	 * 
	 * @param theEjb of type EBSettleWarehouseReceiptLocal
	 */
	private void deleteWarehouseReceipt(EBSettleWarehouseReceiptLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
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