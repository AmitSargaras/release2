/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBSettlementBean.java,v 1.7 2004/09/07 07:38:55 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

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
 * Entity bean implementation for Deal Settlement details.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2004/09/07 07:38:55 $ Tag: $Name: $
 */
public abstract class EBSettlementBean implements ISettlement, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/**
	 * A list of methods to be excluded during create/update settlement details.
	 */
	private static final String[] EXCLUDE_METHOD = new String[] { "getSettlementID", "getRefID" };

	/**
	 * Get settlement id.
	 * 
	 * @return long
	 */
	public long getSettlementID() {
		return getEBSettlementID().longValue();
	}

	/**
	 * Set settlement id.
	 * 
	 * @param settlementID of type long
	 */
	public void setSettlementID(long settlementID) {
		setEBSettlementID(new Long(settlementID));
	}

	/**
	 * Get payment amount.
	 * 
	 * @return Amount
	 */
	public Amount getPaymentAmt() {
		if (getEBPaymentAmt() == null) {
			return null;
		}
		return new Amount(getEBPaymentAmt(), new CurrencyCode(getPaymentAmtCcyCode()));
	}

	/**
	 * Set payment amount.
	 * 
	 * @param paymentAmt of type Amount
	 */
	public void setPaymentAmt(Amount paymentAmt) {
		setEBPaymentAmt(paymentAmt == null ? null : paymentAmt.getAmountAsBigDecimal());
		setPaymentAmtCcyCode(paymentAmt == null ? null : paymentAmt.getCurrencyCode());
	}

	public abstract Long getEBSettlementID();

	public abstract void setEBSettlementID(Long eBSettlementID);

	public abstract BigDecimal getEBPaymentAmt();

	public abstract void setEBPaymentAmt(BigDecimal eBPaymentAmt);

	public abstract String getPaymentAmtCcyCode();

	public abstract void setPaymentAmtCcyCode(String paymentAmtCcyCode);

	public abstract void setStatus(String status);

	/**
	 * Get the settlement details.
	 * 
	 * @return settlement
	 */
	public ISettlement getValue() {
		OBSettlement settlement = new OBSettlement();
		AccessorUtil.copyValue(this, settlement);
		return settlement;
	}

	/**
	 * Set the settlement details to this entity.
	 * 
	 * @param settlement is of type ISettlement
	 */
	public void setValue(ISettlement settlement) {
		AccessorUtil.copyValue(settlement, this, EXCLUDE_METHOD);
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param settlement of type ISettlement
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ISettlement settlement) throws CreateException {
		try {
			String settleID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_SETTLEMENT, true);
			AccessorUtil.copyValue(settlement, this, EXCLUDE_METHOD);
			setEBSettlementID(new Long(settleID));

			if (settlement.getRefID() == ICMSConstant.LONG_MIN_VALUE) {
				setRefID(getSettlementID());
			}
			else {
				// else maintain this reference id.
				setRefID(settlement.getRefID());
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
	 * @param settlement of type ISettlement
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ISettlement settlement) throws CreateException {
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