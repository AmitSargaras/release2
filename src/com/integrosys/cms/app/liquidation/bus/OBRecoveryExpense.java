package com.integrosys.cms.app.liquidation.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This Object represents NPL Info
 * 
 * @author $Author: lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBRecoveryExpense implements IRecoveryExpense {

	// private long liquidationID;

	private Long recoveryExpenseID;

	private Date dateOfExpense;

	private String expenseType;

	private Amount expenseAmount;

	// private long expenseAmt;
	private String expenseAmtCurrency;

	private String remarks;

	private String status;

	private long versionTime;

	private long refID;

	private String settled;

	// public long getLiquidationID() {
	// return liquidationID;
	// }
	//
	// public void setLiquidationID(long liquidationID) {
	// this.liquidationID = liquidationID;
	// }

	public Long getRecoveryExpenseID() {
		return recoveryExpenseID;
	}

	public void setRecoveryExpenseID(Long recoveryExpenseID) {
		this.recoveryExpenseID = recoveryExpenseID;
	}

	public Date getDateOfExpense() {
		return dateOfExpense;
	}

	public void setDateOfExpense(Date dateOfExpense) {
		this.dateOfExpense = dateOfExpense;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public String getExpenseAmtCurrency() {
		return expenseAmtCurrency;
	}

	public void setExpenseAmtCurrency(String expenseAmtCurrency) {
		this.expenseAmtCurrency = expenseAmtCurrency;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public Amount getExpenseAmount() {
		return expenseAmount;
	}

	public void setExpenseAmount(Amount expenseAmount) {
		this.expenseAmount = expenseAmount;
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
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBRecoveryExpense)) {
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

	public long getRefID() {
		return refID;
	}

	public void setRefID(long refID) {
		this.refID = refID;
	}

	public String getSettled() {
		return settled;
	}

	public void setSettled(String settled) {
		this.settled = settled;
	}
}
