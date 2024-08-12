/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBLoanSchedule.java,v 1.6 2004/08/19 03:09:22 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents loan agency's loan schedule.
 * 
 * @author wltan $
 * @version $
 * @since $Date: 2004/08/19 03:09:22 $ Tag: $Name: $
 */
public class OBLoanSchedule implements ILoanSchedule, java.io.Serializable {

	private long loanScheduleID = ICMSConstant.LONG_INVALID_VALUE;

	private Date paymentDate;

	private Amount principalDueAmount;

	private Amount interestDueAmount;

	private String status = ICMSConstant.STATE_ACTIVE;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default constructor
	 */
	public OBLoanSchedule() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param iValue is of type ILoanSchedule
	 */
	public OBLoanSchedule(ILoanSchedule iValue) {
		if (iValue != null) {
			AccessorUtil.copyValue(iValue, this);
		}
	}

	/**
	 * Get loan schedule id.
	 * 
	 * @return long
	 */
	public long getLoanScheduleID() {
		return loanScheduleID;
	}

	/**
	 * Set loan schedule id.
	 * 
	 * @param loanScheduleID of type long
	 */
	public void setLoanScheduleID(long loanScheduleID) {
		this.loanScheduleID = loanScheduleID;
	}

	/**
	 * Get payment due date.
	 * 
	 * @return Date
	 */
	public Date getPaymentDate() {
		return paymentDate;
	}

	/**
	 * Set payment due date.
	 * 
	 * @param dueDate of type Date
	 */
	public void setPaymentDate(Date dueDate) {
		this.paymentDate = dueDate;
	}

	/**
	 * Get principal amount due.
	 * 
	 * @return Amount
	 */
	public Amount getPrincipalDueAmount() {
		return principalDueAmount;
	}

	/**
	 * Set principal amount due.
	 * 
	 * @param principalDueAmount of type Amount
	 */
	public void setPrincipalDueAmount(Amount principalDueAmount) {
		this.principalDueAmount = principalDueAmount;
	}

	/**
	 * Get interest amount due.
	 * 
	 * @return Amount
	 */
	public Amount getInterestDueAmount() {
		return interestDueAmount;
	}

	/**
	 * Set interest amount due.
	 * 
	 * @param interestDueAmount of type Amount
	 */
	public void setInterestDueAmount(Amount interestDueAmount) {
		this.interestDueAmount = interestDueAmount;
	}

	/**
	 * Get sub-limit status, active or deleted.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set sub-limit status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get common reference for actual and staging sub-limit.
	 * 
	 * @return long
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/**
	 * Set common reference for actual and staging sub-limit.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
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
		String hash = String.valueOf(this.loanScheduleID);
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
		else if (!(obj instanceof ILoanSchedule)) {
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
