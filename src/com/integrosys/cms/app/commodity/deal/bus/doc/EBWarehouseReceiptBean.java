/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/EBWarehouseReceiptBean.java,v 1.6 2004/08/04 05:29:57 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

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
import com.integrosys.cms.app.commodity.JNDIConstants;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.main.bus.warehouse.EBWarehouseLocal;
import com.integrosys.cms.app.commodity.main.bus.warehouse.EBWarehouseLocalHome;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for Warehouse Receipt entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/08/04 05:29:57 $ Tag: $Name: $
 */
public abstract class EBWarehouseReceiptBean implements IWarehouseReceipt, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during create/update warehouse details. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getWarehouseReceiptID", "getRefID" };

	/**
	 * Get warehouse receipt id.
	 * 
	 * @return long
	 */
	public long getWarehouseReceiptID() {
		return getEBWarehouseReceiptID().longValue();
	}

	/**
	 * Set warehouse receipt id.
	 * 
	 * @param warehouseReceiptID of type long
	 */
	public void setWarehouseReceiptID(long warehouseReceiptID) {
		setEBWarehouseReceiptID(new Long(warehouseReceiptID));
	}

	/**
	 * Get quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getQuantity() {
		if ((getEBQuantity() == null) || (getEBQuantityUOM() == null)) {
			return null;
		}
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBQuantityUOM());
		return new Quantity(getEBQuantity(), uom);
	}

	/**
	 * Set quantity.
	 * 
	 * @param quantity of type Quantity
	 */
	public void setQuantity(Quantity quantity) {
		setEBQuantity(quantity == null ? null : quantity.getQuantity());
		setEBQuantityUOM(quantity == null ? null : quantity.getUnitofMeasure().getID());
	}

	public abstract Long getEBWarehouseReceiptID();

	public abstract void setEBWarehouseReceiptID(Long warehouseReceiptID);

	public abstract EBWarehouseLocal getWarehouseCMR();

	public abstract void setWarehouseCMR(EBWarehouseLocal warehouseCMR);

	public abstract BigDecimal getEBQuantity();

	public abstract void setEBQuantity(BigDecimal qty);

	public abstract String getEBQuantityUOM();

	public abstract void setEBQuantityUOM(String uomCode);

	public abstract void setStatus(String status);

	/**
	 * Get warehouse.
	 * 
	 * @return IWarehouse
	 */
	public IWarehouse getWarehouse() {
		try {
			EBWarehouseLocal theEjb = getWarehouseCMR();
			if (theEjb == null) {
				return null;
			}
			return theEjb.getValue();
		}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	/**
	 * Set warehouse.
	 * 
	 * @param warehouse of type IWarehouse
	 */
	public void setWarehouse(IWarehouse warehouse) {
	}

	/**
	 * Set reference to warehouse.
	 * 
	 * @param warehouse of type IWarehouse
	 */
	private void setWarehouseRef(IWarehouse warehouse) throws FinderException {
		if (warehouse == null) {
			return;
		}

		EBWarehouseLocalHome ejbHome = (EBWarehouseLocalHome) BeanController.getEJBLocalHome(
				JNDIConstants.EB_WAREHOUSE_LOCAL_BEAN, EBWarehouseLocalHome.class.getName());

		setWarehouseCMR(ejbHome.findByPrimaryKey(new Long(warehouse.getWarehouseID())));
	}

	/**
	 * Get the warehouse details.
	 * 
	 * @return warehouse details
	 */
	public IWarehouseReceipt getValue() {
		OBWarehouseReceipt receipt = new OBWarehouseReceipt();
		AccessorUtil.copyValue(this, receipt);
		return receipt;
	}

	/**
	 * Set the commodity warehouse details to this entity.
	 * 
	 * @param receipt is of type IWarehouseReceipt
	 */
	public void setValue(IWarehouseReceipt receipt) {
		AccessorUtil.copyValue(receipt, this, EXCLUDE_METHOD);
		setReferences(receipt);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param receipt of type IWarehouseReceipt
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IWarehouseReceipt receipt) throws CreateException {
		try {
			String receiptID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_WAREHOUSE_RECEIPT, true);
			AccessorUtil.copyValue(receipt, this, EXCLUDE_METHOD);
			setEBWarehouseReceiptID(new Long(receiptID));

			if (receipt.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getWarehouseReceiptID());
			}
			else {
				// else maintain this reference id.
				setRefID(receipt.getRefID());
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
	 * @param receipt of type IWarehouseReceipt
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IWarehouseReceipt receipt) throws CreateException {
		setReferences(receipt);
	}

	/**
	 * Set the references to this warehouse receipt
	 * 
	 * @param receipt of type IWarehouseReceipt
	 */
	private void setReferences(IWarehouseReceipt receipt) {
		try {
			setWarehouseRef(receipt.getWarehouse());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new EJBException(e);
		}
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