/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBSettleWarehouseReceiptBean.java,v 1.3 2004/08/24 03:53:40 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.deal.bus.doc.EBWarehouseReceiptLocal;
import com.integrosys.cms.app.commodity.deal.bus.doc.EBWarehouseReceiptLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for Settlement Warehouse Receipt.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/24 03:53:40 $ Tag: $Name: $
 */
public abstract class EBSettleWarehouseReceiptBean implements ISettleWarehouseReceipt, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during create/update settlement
	 * warehouse receipt.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getSettleWarehseRcptID", "getRefID" };

	/**
	 * Get settlement warehouse receipt id.
	 * 
	 * @return long
	 */
	public long getSettleWarehseRcptID() {
		return getEBSettleWarehseRcptID().longValue();
	}

	/**
	 * Set settlement warehouse receipt id.
	 * 
	 * @param settleWarehseRcptID of type long
	 */
	public void setSettleWarehseRcptID(long settleWarehseRcptID) {
		setEBSettleWarehseRcptID(new Long(settleWarehseRcptID));
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

	public abstract Long getEBSettleWarehseRcptID();

	public abstract void setEBSettleWarehseRcptID(Long eBSettleWarehseRcptID);

	public abstract BigDecimal getEBReleasedQty();

	public abstract void setEBReleasedQty(BigDecimal releasedQty);

	public abstract String getEBReleasedQtyUOM();

	public abstract void setEBReleasedQtyUOM(String uomCode);

	public abstract EBWarehouseReceiptLocal getWarehouseReceiptCMR();

	public abstract void setWarehouseReceiptCMR(EBWarehouseReceiptLocal warehouseReceipt);

	public abstract void setStatus(String status);

	/**
	 * Get the settlement warehouse receipt.
	 * 
	 * @return ISettleWarehouseReceipt
	 */
	public ISettleWarehouseReceipt getValue() {
		OBSettleWarehouseReceipt settleRcpt = new OBSettleWarehouseReceipt();
		AccessorUtil.copyValue(this, settleRcpt);
		return settleRcpt;
	}

	/**
	 * Set the settlement warehouse receipt to this entity.
	 * 
	 * @param settleRcpt is of type ISettleWarehouseReceipt
	 */
	public void setValue(ISettleWarehouseReceipt settleRcpt) {
		AccessorUtil.copyValue(settleRcpt, this, EXCLUDE_METHOD);
	}

	/**
	 * Get warehouse receipt.
	 * 
	 * @return IWarehouseReceipt
	 */
	public IWarehouseReceipt getWarehouseReceipt() {
		EBWarehouseReceiptLocal theEjb = getWarehouseReceiptCMR();
		return theEjb.getValue();
	}

	/**
	 * Set warehouse receipt.
	 * 
	 * @param warehouseReceipt of type IWarehouseReceipt
	 */
	public void setWarehouseReceipt(IWarehouseReceipt warehouseReceipt) {
	}

	/**
	 * Set warehouse receipt.
	 * 
	 * @param warehouseReceipt of type IWarehouseReceipt
	 */
	private void setWarehouseReceiptRef(IWarehouseReceipt warehouseReceipt) throws FinderException {
		EBWarehouseReceiptLocalHome ejbHome = getEBWarehouseReceiptLocalHome();
		EBWarehouseReceiptLocal theEjb = ejbHome.findByPrimaryKey(new Long(warehouseReceipt.getWarehouseReceiptID()));
		setWarehouseReceiptCMR(theEjb);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param settleRcpt of type ISettleWarehouseReceipt
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ISettleWarehouseReceipt settleRcpt) throws CreateException {
		try {
			String id = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_SETTLEMENT_WAREHOUSE, true);
			AccessorUtil.copyValue(settleRcpt, this, EXCLUDE_METHOD);
			setEBSettleWarehseRcptID(new Long(id));

			if (settleRcpt.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getSettleWarehseRcptID());
			}
			else {
				// else maintain this reference id.
				setRefID(settleRcpt.getRefID());
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
	 * @param settleRcpt of type ISettleWarehouseReceipt
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ISettleWarehouseReceipt settleRcpt) throws CreateException {
		try {
			setWarehouseReceiptRef(settleRcpt.getWarehouseReceipt());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
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