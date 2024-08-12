/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/EBFinancingDocBean.java,v 1.4 2004/07/22 17:51:39 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for Commodity Financing Document entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/22 17:51:39 $ Tag: $Name: $
 */
public abstract class EBFinancingDocBean implements IFinancingDoc, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during create/update financing document.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getFinancingDocID", "getRefID" };

	/**
	 * Get financing document id.
	 * 
	 * @return long
	 */
	public long getFinancingDocID() {
		return getEBFinancingDocID().longValue();
	}

	/**
	 * Set financing document id.
	 * 
	 * @param financingDocID of type long
	 */
	public void setFinancingDocID(long financingDocID) {
		setEBFinancingDocID(new Long(financingDocID));
	}

	/**
	 * Get sales order amount.
	 * 
	 * @return Amount
	 */
	public Amount getOrderAmount() {
		if (getEBOrderAmount() == null) {
			return null;
		}
		return new Amount(getEBOrderAmount(), new CurrencyCode(getOrderAmtCcyCode()));
	}

	/**
	 * Set sales order amount.
	 * 
	 * @param orderAmount of type Amount
	 */
	public void setOrderAmount(Amount orderAmount) {
		setEBOrderAmount(orderAmount == null ? null : orderAmount.getAmountAsBigDecimal());
		setOrderAmtCcyCode(orderAmount == null ? null : orderAmount.getCurrencyCode());
	}

	/**
	 * Get sales order quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getOrderQuantity() {
		if ((getEBOrderQuantity() == null) || (getEBOrderQuantityUOM() == null)) {
			return null;
		}
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBOrderQuantityUOM());
		return new Quantity(getEBOrderQuantity(), uom);
	}

	/**
	 * Set sales order quantity.
	 * 
	 * @param orderQuantity of type Quantity
	 */
	public void setOrderQuantity(Quantity orderQuantity) {
		setEBOrderQuantity(orderQuantity == null ? null : orderQuantity.getQuantity());
		setEBOrderQuantityUOM(orderQuantity == null ? null : orderQuantity.getUnitofMeasure().getID());
	}

	public abstract Long getEBFinancingDocID();

	public abstract void setEBFinancingDocID(Long eBFinancingDocID);

	public abstract BigDecimal getEBOrderAmount();

	public abstract void setEBOrderAmount(BigDecimal orderAmount);

	public abstract String getOrderAmtCcyCode();

	public abstract void setOrderAmtCcyCode(String orderAmountCcyCode);

	public abstract BigDecimal getEBOrderQuantity();

	public abstract void setEBOrderQuantity(BigDecimal qty);

	public abstract String getEBOrderQuantityUOM();

	public abstract void setEBOrderQuantityUOM(String uomCode);

	public abstract void setStatus(String status);

	/**
	 * Get the commodity financing document business object.
	 * 
	 * @return financing document
	 */
	public IFinancingDoc getValue() {
		OBFinancingDoc doc = new OBFinancingDoc();
		AccessorUtil.copyValue(this, doc);
		return doc;
	}

	/**
	 * Set the commodity financing doc to this entity.
	 * 
	 * @param doc is of type IFinancingDoc
	 */
	public void setValue(IFinancingDoc doc) {
		AccessorUtil.copyValue(doc, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param doc of type IFinancingDoc
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IFinancingDoc doc) throws CreateException {
		try {
			String docID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_FIN_DOC, true);
			AccessorUtil.copyValue(doc, this, EXCLUDE_METHOD);
			setEBFinancingDocID(new Long(docID));

			if (doc.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getFinancingDocID());
			}
			else {
				// else maintain this reference id.
				setRefID(doc.getRefID());
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
	 * @param doc of type IFinancingDoc
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IFinancingDoc doc) throws CreateException {
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