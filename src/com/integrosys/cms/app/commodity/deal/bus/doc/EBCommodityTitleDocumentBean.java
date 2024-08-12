/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/EBCommodityTitleDocumentBean.java,v 1.5 2006/02/20 07:24:06 pratheepa Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.commodity.main.bus.titledocument.EBTitleDocumentLocal;
import com.integrosys.cms.app.commodity.main.bus.titledocument.EBTitleDocumentLocalHome;
import com.integrosys.cms.app.commodity.main.bus.titledocument.ITitleDocument;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for Commodity Title Document entity.
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/02/20 07:24:06 $ Tag: $Name: $
 */
public abstract class EBCommodityTitleDocumentBean implements ICommodityTitleDocument, EntityBean {

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during create/update title document. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getTitleDocID", "getRefID" };

	/**
	 * Get commodity title document id.
	 * 
	 * @return long
	 */
	public long getTitleDocID() {
		return getEBTitleDocID().longValue();
	}

	/**
	 * Set commodity title document id.
	 * 
	 * @param titleDocID of type long
	 */
	public void setTitleDocID(long titleDocID) {
		setEBTitleDocID(new Long(titleDocID));
	}

	/**
	 * Get trust receipt amount.
	 * 
	 * @return Amount
	 */
	public Amount getTRAmount() {
		if (getEBTRAmount() == null) {
			return null;
		}
		return new Amount(getEBTRAmount(), new CurrencyCode(getTRAmountCcyCode()));
	}

	/**
	 * Set trust receipt amount.
	 * 
	 * @param tRAmount of type Amount
	 */
	public void setTRAmount(Amount tRAmount) {
		setEBTRAmount(tRAmount == null ? null : tRAmount.getAmountAsBigDecimal());
		setTRAmountCcyCode(tRAmount == null ? null : tRAmount.getCurrencyCode());
	}

	public abstract Long getEBTitleDocID();

	public abstract void setEBTitleDocID(Long eBTitleDocID);

	public abstract BigDecimal getEBTRAmount();

	public abstract void setEBTRAmount(BigDecimal eBTAmount);

	public abstract String getTRAmountCcyCode();

	public abstract void setTRAmountCcyCode(String tRAmountCcyCode);

	public abstract EBTitleDocumentLocal getTitleDocTypeCMR();

	public abstract void setTitleDocTypeCMR(EBTitleDocumentLocal titleDocTypeCMR);

	public abstract Collection getWarehouseReceiptsCMR();

	public abstract void setWarehouseReceiptsCMR(Collection warehouseReceiptsCMR);

	public abstract void setStatus(String status);

	/**
	 * Get title document type.
	 * 
	 * @return ITitleDocument
	 */
	public ITitleDocument getTitleDocType() {
		try {
			EBTitleDocumentLocal theEjb = getTitleDocTypeCMR();
			if (theEjb == null) {
				return null;
			}
			return getTitleDocTypeCMR().getValue();
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Set title document type.
	 * 
	 * @param titleDocType of type ITitleDocument
	 */
	public void setTitleDocType(ITitleDocument titleDocType) {
	}

	/**
	 * Get warehouse details.
	 * 
	 * @return IWarehouseReceipt[]
	 */
	// Modified by Pratheepa for CR129
	public IWarehouseReceipt[] getWarehouseReceipts() {
		Iterator i = getWarehouseReceiptsCMR().iterator();
		ArrayList arrayList = new ArrayList();
		DefaultLogger.debug(this, "Coming inside getWareHouseReceipts");
		while (i.hasNext()) {
			IWarehouseReceipt wareDoc = (((EBWarehouseReceiptLocal) i.next()).getValue());
			if ((wareDoc.getStatus() != null) && wareDoc.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				DefaultLogger.debug(this, "Coming Inside Status deleted");
				continue;
			}
			// arrayList.add ( ((EBWarehouseReceiptLocal) i.next()).getValue()
			// );
			arrayList.add(wareDoc);
		}
		return (OBWarehouseReceipt[]) arrayList.toArray(new OBWarehouseReceipt[0]);
	}

	/**
	 * Set warehouse details.
	 * 
	 * @param warehouseReceipts of type IWarehouseReceipt[]
	 */
	public void setWarehouseReceipts(IWarehouseReceipt[] warehouseReceipts) {
	}

	/**
	 * Set referenct to title document type.
	 * 
	 * @param titleDocType of type ITitleDocument
	 * @throws FinderException on error finding the title document type
	 */
	private void setTitleDocTypeRef(ITitleDocument titleDocType) throws FinderException {
		if (titleDocType == null) {
			return;
		}
		EBTitleDocumentLocalHome ejbHome = (EBTitleDocumentLocalHome) BeanController.getEJBLocalHome(
				JNDIConstants.EB_TITLE_DOCUMENT_LOCAL_BEAN, EBTitleDocumentLocalHome.class.getName());

		setTitleDocTypeCMR(ejbHome.findByPrimaryKey(new Long(titleDocType.getTitleDocumentID())));
	}

	/**
	 * Set the references to this title document.
	 * 
	 * @param titleDoc of type ICommodityTitleDocument
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(ICommodityTitleDocument titleDoc, boolean isAdd) {
		try {
			setWarehouseReceiptsRef(titleDoc.getWarehouseReceipts(), isAdd);
			if (isAdd) {
				setTitleDocTypeRef(titleDoc.getTitleDocType());
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new EJBException(e);
		}
	}

	/**
	 * Set warehouse details.
	 * 
	 * @param receipts of type IWarehouseReceipt[]
	 */
	private void setWarehouseReceiptsRef(IWarehouseReceipt[] receipts, boolean isAdd) throws CreateException {
		// remove all existing receipts
		if ((receipts == null) || (receipts.length == 0)) {
			removeAllWarehouseReceipts();
			return;
		}

		EBWarehouseReceiptLocalHome ejbHome = getEBWarehouseReceiptLocalHome();

		Collection c = getWarehouseReceiptsCMR();

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
				EBWarehouseReceiptLocal theEjb = (EBWarehouseReceiptLocal) iterator.next();
				IWarehouseReceipt value = theEjb.getValue();
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
			c.add(ejbHome.create((IWarehouseReceipt) iterator.next()));
		}
	}

	/**
	 * Get the commodity title document.
	 * 
	 * @return ICommodityTitleDocument
	 */
	public ICommodityTitleDocument getValue() {
		OBCommodityTitleDocument titleDoc = new OBCommodityTitleDocument();
		AccessorUtil.copyValue(this, titleDoc);
		return titleDoc;
	}

	/**
	 * Set the commodity title document to this entity.
	 * 
	 * @param titleDoc is of type ICommodityTitleDocument
	 */
	public void setValue(ICommodityTitleDocument titleDoc) {
		AccessorUtil.copyValue(titleDoc, this, EXCLUDE_METHOD);
		setReferences(titleDoc, false);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param titleDoc of type ICommodityTitleDocument
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICommodityTitleDocument titleDoc) throws CreateException {
		try {
			String titleDocID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_TITLE_DOC, true);
			AccessorUtil.copyValue(titleDoc, this, EXCLUDE_METHOD);
			setEBTitleDocID(new Long(titleDocID));

			if (titleDoc.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getTitleDocID());
			}
			else {
				// else maintain this reference id.
				setRefID(titleDoc.getRefID());
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
	 * @param titleDoc of type ICommodityTitleDocument
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ICommodityTitleDocument titleDoc) throws CreateException {
		DefaultLogger.debug(this, "Coming inside postCreate");
		setReferences(titleDoc, true);
	}

	/**
	 * Get warehouse receipt local home.
	 * 
	 * @return EBWarehouseReceiptLocalHome
	 */
	protected EBWarehouseReceiptLocalHome getEBWarehouseReceiptLocalHome() {
		EBWarehouseReceiptLocalHome ejbHome = (EBWarehouseReceiptLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_WAREHOUSE_RECEIPT_LOCAL_JNDI, EBWarehouseReceiptLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBWarehouseReceiptLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get title document type local home.
	 * 
	 * @return EBTitleDocumentLocalHome
	 */
	protected EBTitleDocumentLocalHome getEBTitleDocTypeLocalHome() {
		EBTitleDocumentLocalHome ejbHome = (EBTitleDocumentLocalHome) BeanController.getEJBLocalHome(
				JNDIConstants.EB_TITLE_DOCUMENT_LOCAL_BEAN, EBTitleDocumentLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBTitleDocumentLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Helper method to delete all warehouse details.
	 */
	private void removeAllWarehouseReceipts() {
		Collection c = getWarehouseReceiptsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBWarehouseReceiptLocal theEjb = (EBWarehouseReceiptLocal) iterator.next();
			deleteWarehouseReceipt(theEjb);
		}
	}

	/**
	 * Helper method to delete warehouse in receiptCol that are not contained in
	 * receiptList.
	 * 
	 * @param receiptCol a list of old warehouse details ejb object
	 * @param receiptList a list of newly updated warehouse details
	 */
	private void removeWarehouseReceipt(Collection receiptCol, IWarehouseReceipt[] receiptList) {
		Iterator iterator = receiptCol.iterator();

		while (iterator.hasNext()) {
			EBWarehouseReceiptLocal theEjb = (EBWarehouseReceiptLocal) iterator.next();
			IWarehouseReceipt receipt = theEjb.getValue();
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
	 * @param theEjb of type EBWarehouseReceiptLocal
	 */
	private void deleteWarehouseReceipt(EBWarehouseReceiptLocal theEjb) {
		DefaultLogger.debug(this, "Coming inside this:");

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