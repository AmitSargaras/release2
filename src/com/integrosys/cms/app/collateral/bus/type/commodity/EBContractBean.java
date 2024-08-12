/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBContractBean.java,v 1.15 2004/08/30 12:26:31 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityDifferential;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.profile.ISupplier;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for security parameter.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2004/08/30 12:26:31 $ Tag: $Name: $
 */

public abstract class EBContractBean implements IContract, EntityBean {

	protected boolean isStaging = false;

	public EBContractBean() {
		isStaging = false;
	}

	/** The container assigned reference to the entity. */
	private EntityContext context;

	public long getContractID() {
		return getEBContractID().longValue();
	}

	public void setContractID(long contractID) {
		setEBContractID(new Long(contractID));
	}

	public Amount getMainContractAmount() {
		return null;
	}

	/**
	 * Gets contracted quantity.
	 * 
	 * @return contracted quantity
	 */
	public Quantity getContractedQty() {
		if ((getEBContractedQty() == null) || (getEBContractedQtyUOM() == null)) {
			return null;
		}
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBContractedQtyUOM());
		return new Quantity(getEBContractedQty(), uom);
	}

	/**
	 * Sets contracted quantity.
	 * 
	 * @param aQty - contracted quantity
	 */
	public void setContractedQty(Quantity aQty) {
		setEBContractedQty(aQty == null ? null : aQty.getQuantity());
		setEBContractedQtyUOM(aQty == null ? null : aQty.getUnitofMeasure().getID());
	}

	/**
	 * Gets the contracted price.
	 * 
	 * @return Amount representing the contracted price
	 */
	public Amount getContractPrice() {
		if ((getEBContractPrice() == null) || (getEBContractPriceCcyCode() == null)) {
			return null;
		}
		return new Amount(getEBContractPrice(), new CurrencyCode(getEBContractPriceCcyCode()));
	}

	/**
	 * Sets the contracted price.
	 * 
	 * @param aPrice - Amount representing the contracted price
	 */
	public void setContractPrice(Amount aPrice) {
		setEBContractPrice((aPrice != null) ? aPrice.getAmountAsBigDecimal() : null);
		setEBContractPriceCcyCode((aPrice != null) ? aPrice.getCurrencyCode() : null);
	}

	/**
	 * Gets contracted quantity differential.
	 * 
	 * @return contracted quantity differential
	 */
	public QuantityDifferential getQtyDifferential() {
		if ((getEBQtyDifferential() == null) || (getEBContractedQtyUOM() == null)
				|| (getEBQtyDifferentialSign() == null)) {
			return null;
		}
		String sign = getEBQtyDifferentialSign();
		// use UOM specified for contracted quantity
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBContractedQtyUOM());
		return new QuantityDifferential(new Quantity(getEBQtyDifferential(), uom), sign);
	}

	/**
	 * Sets contracted quantity differential based on UOM for contracted
	 * quantity
	 * 
	 * @param aQtyDiff - contracted quantity differential
	 */
	public void setQtyDifferential(QuantityDifferential aQtyDiff) {
		if ((aQtyDiff == null) || (aQtyDiff.getQuantity() == null) || (aQtyDiff.getQuantity().getQuantity() == null)
				|| (aQtyDiff.getSign() == null) || (aQtyDiff.getQuantity().getUnitofMeasure() == null)) {
			setEBQtyDifferential(null);
			setEBQtyDifferentialSign(null);
		}
		else {
			setEBQtyDifferential(aQtyDiff.getQuantity().getQuantity());
			setEBQtyDifferentialSign(aQtyDiff.getSign().getName());
		}
	}

	public IApprovedCommodityType getApprovedCommodityType() {
		try {
			if (getApprovedCommodityTypeID() != null) {
				return getEBApprovedCommodityTypeLocalHome().findByPrimaryKey(getApprovedCommodityTypeID()).getValue();
			}
			else {
				return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new EJBException(e);
		}
	}

	public void setApprovedCommodityType(IApprovedCommodityType type) {
		setApprovedCommodityTypeID((type == null) ? null : new Long(type.getApprovedCommodityTypeID()));
	}

	public ISupplier getSupplier() {
		try {
			if (getSupplierID() != null) {
				return CommodityMainInfoManagerFactory.getManager().getSupplierByID(getSupplierID().longValue());
			}
		}
		catch (CommodityException e) {
			DefaultLogger.error(this, "Error while getting Isupplier for contract - " + getSupplierID(), e);
		}
		return null;
	}

	public void setSupplier(ISupplier supplier) {
		setSupplierID((supplier == null) ? null : new Long(supplier.getSupplierID()));
	}

	public abstract Long getEBContractID();

	public abstract void setEBContractID(Long pk);

	public abstract Long getApprovedCommodityTypeID();

	public abstract void setApprovedCommodityTypeID(Long typeID);

	public abstract Long getSupplierID();

	public abstract void setSupplierID(Long supplier);

	public abstract BigDecimal getEBContractPrice();

	public abstract void setEBContractPrice(BigDecimal price);

	public abstract String getEBContractPriceCcyCode();

	public abstract void setEBContractPriceCcyCode(String ccyCode);

	public abstract BigDecimal getEBContractedQty();

	public abstract void setEBContractedQty(BigDecimal qty);

	public abstract String getEBContractedQtyUOM();

	public abstract void setEBContractedQtyUOM(String uomCode);

	public abstract BigDecimal getEBQtyDifferential();

	public abstract void setEBQtyDifferential(BigDecimal qty);

	public abstract String getEBQtyDifferentialSign();

	public abstract void setEBQtyDifferentialSign(String sign);

	public abstract long getCommonRef();

	public abstract void setCommonRef(long x);

	public void softDelete() {
		setStatus(ICMSConstant.STATE_DELETED);
	}

	public IContract getValue() {
		OBContract value = new OBContract();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	public void setValue(IContract value) {
		AccessorUtil.copyValue(value, this, new String[] { "getContractPK", "setContractPK", "getContractID",
				"setContractID" });
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value - IContract object
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IContract value) throws CreateException {
		try {

			AccessorUtil.copyValue(value, this, new String[] { "getContractPK", "setContractPK", "getContractID",
					"setContractID" });
			if (isStaging) {
				String generatedPK = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_CONTRACT, true);
				this.setEBContractID(new Long(generatedPK));
				if (!(value.getCommonRef() > 0)) {
					this.setCommonRef(Long.parseLong(generatedPK));
				}
			}
			else {
				this.setEBContractID(new Long(value.getContractID()));
			}
			setStatus(ICMSConstant.STATE_ACTIVE);
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	protected EBApprovedCommodityTypeLocalHome getEBApprovedCommodityTypeLocalHome() {
		EBApprovedCommodityTypeLocalHome ejbHome = (EBApprovedCommodityTypeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_APPROVED_COMMODITY_TYPE_LOCAL_JNDI, EBApprovedCommodityTypeLocalHome.class
						.getName());

		if (ejbHome == null) {
			throw new EJBException("EBApprovedCommodityTypeLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param value object of IContract
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IContract value) throws CreateException {
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
