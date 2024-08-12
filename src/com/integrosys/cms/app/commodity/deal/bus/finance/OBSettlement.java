/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/OBSettlement.java,v 1.6 2004/09/07 07:38:29 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a Commodity Settlement details.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/09/07 07:38:29 $ Tag: $Name: $
 */
public class OBSettlement implements ISettlement {
	private long settlementID;

	private Date paymentDate;

	private Amount paymentAmt;

	private long refID = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	/**
	 * Default Constructor.
	 */
	public OBSettlement() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ISettlement
	 */
	public OBSettlement(ISettlement obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get settlement id.
	 * 
	 * @return long
	 */
	public long getSettlementID() {
		return settlementID;
	}

	/**
	 * Set settlement id.
	 * 
	 * @param settlementID of type long
	 */
	public void setSettlementID(long settlementID) {
		this.settlementID = settlementID;
	}

	/**
	 * Get date of payment.
	 * 
	 * @return Date
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * Set date of payment.
	 * 
	 * @param paymentDate of type Date
	 */
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * Get payment amount.
	 * 
	 * @return Amount
	 */
	public Amount getPaymentAmt() {
		return paymentAmt;
	}

	/**
	 * Set payment amount.
	 * 
	 * @param paymentAmt of type Amount
	 */
	public void setPaymentAmt(Amount paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	/**
	 * Get common reference id for actual and staging.
	 * 
	 * @return long
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * Set common reference id for actual and staging.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Get cms business status.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(settlementID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof ISettlement)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}