/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/ISettlement.java,v 1.4 2004/09/07 07:38:29 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents a Commodity Settlement details.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/09/07 07:38:29 $ Tag: $Name: $
 */
public interface ISettlement extends Serializable {
	/**
	 * Get settlement id.
	 * 
	 * @return long
	 */
	public long getSettlementID();

	/**
	 * Set settlement id.
	 * 
	 * @param settlementID of type long
	 */
	public void setSettlementID(long settlementID);

	/**
	 * Get date of payment.
	 * 
	 * @return Date
	 */
	public Date getPaymentDate();

	/**
	 * Set date of payment.
	 * 
	 * @param paymentDate of type Date
	 */
	public void setPaymentDate(Date paymentDate);

	/**
	 * Get payment amount.
	 * 
	 * @return Amount
	 */
	public Amount getPaymentAmt();

	/**
	 * Set payment amount.
	 * 
	 * @param paymentAmt of type Amount
	 */
	public void setPaymentAmt(Amount paymentAmt);

	/**
	 * Get common reference id for actual and staging.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Set common reference id for actual and staging.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Get cms business status.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}