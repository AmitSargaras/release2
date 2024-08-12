/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/ILoanSchedule.java,v 1.4 2004/08/18 08:00:51 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents loan agency loan scheudle.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/18 08:00:51 $ Tag: $Name: $
 */
public interface ILoanSchedule extends java.io.Serializable {

	/**
	 * Get loan schedule id.
	 * 
	 * @return long
	 */
	public long getLoanScheduleID();

	/**
	 * Set loan schedule id.
	 * 
	 * @param loanScheduleID of type long
	 */
	public void setLoanScheduleID(long loanScheduleID);

	/**
	 * Get payment due date.
	 * 
	 * @return Date
	 */
	public Date getPaymentDate();

	/**
	 * Set payment due date.
	 * 
	 * @param dueDate of type Date
	 */
	public void setPaymentDate(Date dueDate);

	/**
	 * Get principal amount due.
	 * 
	 * @return Amount
	 */
	public Amount getPrincipalDueAmount();

	/**
	 * Set principal amount due.
	 * 
	 * @param principalDue of type Amount
	 */
	public void setPrincipalDueAmount(Amount principalDue);

	/**
	 * Get interest amount due.
	 * 
	 * @return Amount
	 */
	public Amount getInterestDueAmount();

	/**
	 * Set interest amount due.
	 * 
	 * @param interestDue of type Amount
	 */
	public void setInterestDueAmount(Amount interestDue);

	/**
	 * Get sub-limit status, active or deleted.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set sub-limit status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Get common reference for actual and staging sub-limit.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set common reference for actual and staging sub-limit.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);
}
