/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBHedgingContractInfoBean.java,v 1.9 2004/07/22 12:40:55 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.math.BigDecimal;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for Commoding Hedging Contract.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2004/07/22 12:40:55 $ Tag: $Name: $
 */
public abstract class EBHedgingContractInfoBean implements IHedgingContractInfo, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during create/update hedge contract. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getHedgingContractInfoID" };

	/**
	 * Get primary key hedging contract info id.
	 * 
	 * @return long
	 */
	public long getHedgingContractInfoID() {
		return getEBHedgingContractInfoID().longValue();
	}

	/**
	 * Set primary key hedging contract info id.
	 * 
	 * @param hedgingContractInfoID of type long
	 */
	public void setHedgingContractInfoID(long hedgingContractInfoID) {
		setEBHedgingContractInfoID(new Long(hedgingContractInfoID));
	}

	/**
	 * Get deal amount.
	 * 
	 * @return Amount
	 */
	public Amount getDealAmount() {
		if (getEBDealAmount() == null) {
			return null;
		}
		return new Amount(getEBDealAmount(), new CurrencyCode(getDealAmountCurrency()));
	}

	/**
	 * Set deal amount.
	 * 
	 * @param dealAmount of type Amount
	 */
	public void setDealAmount(Amount dealAmount) {
		setEBDealAmount(dealAmount == null ? null : dealAmount.getAmountAsBigDecimal());
		setDealAmountCurrency(dealAmount == null ? null : dealAmount.getCurrencyCode());
	}

	public abstract Long getEBHedgingContractInfoID();

	public abstract void setEBHedgingContractInfoID(Long eBHedgingContractInfoID);

	public abstract BigDecimal getEBDealAmount();

	public abstract void setEBDealAmount(BigDecimal eBDealAmount);

	public abstract String getDealAmountCurrency();

	public abstract void setDealAmountCurrency(String cur);

	/**
	 * Soft delete this hedging contract.
	 */
	public void softDelete() {
		setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Get commodity hedging contract information.
	 * 
	 * @return IHedgingContractInfo
	 */
	public IHedgingContractInfo getValue() {
		OBHedgingContractInfo value = new OBHedgingContractInfo();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Set hedging contract info.
	 * 
	 * @param value of type IHedgingContractInfo
	 */
	public void setValue(IHedgingContractInfo value) {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value of type IHedgingContractInfo
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IHedgingContractInfo value) throws CreateException {
		try {
			String generatedPK = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_HEDGING_CONTRACT,
					true);
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setEBHedgingContractInfoID(new Long(generatedPK));

			if (value.getCommonRef() == ICMSConstant.LONG_MIN_VALUE) {
				setCommonRef(getHedgingContractInfoID());
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
	 * @param value object of IHedgingContractInfo
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(IHedgingContractInfo value) throws CreateException {
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