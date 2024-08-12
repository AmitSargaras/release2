package com.integrosys.cms.app.liquidation.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This Object represents Recovery Income
 * 
 * @author $Author: lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBRecoveryIncome implements IRecoveryIncome {

	private long liquidationID;

	// private Long recoveryID;

	private Long recoveryIncomeID;

	private String recoveryType;

	private Amount totalAmountRecovered;

	private Date recoveryDate;

	private String totalAmtRecoveredCurrency;

	private String remarks;

	private String status;

	private long versionTime;

	private long refID;

	/*
	 * public Long getRecoveryID() { return recoveryID; }
	 * 
	 * public void setRecoveryID(Long recoveryID) { this.recoveryID =
	 * recoveryID; }
	 */

	public Date getRecoveryDate() {
		return recoveryDate;
	}

	public void setRecoveryDate(Date recoveryDate) {
		this.recoveryDate = recoveryDate;
	}

	public Long getRecoveryIncomeID() {
		return recoveryIncomeID;
	}

	public void setRecoveryIncomeID(Long recoveryIncomeID) {
		this.recoveryIncomeID = recoveryIncomeID;
	}

	public Amount getTotalAmountRecovered() {
		return totalAmountRecovered;
	}

	public void setTotalAmountRecovered(Amount totalAmountRecovered) {
		this.totalAmountRecovered = totalAmountRecovered;
	}

	public String getTotalAmtRecoveredCurrency() {
		return totalAmtRecoveredCurrency;
	}

	public void setTotalAmtRecoveredCurrency(String totalAmtRecoveredCurrency) {
		this.totalAmtRecoveredCurrency = totalAmtRecoveredCurrency;
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
		else if (!(obj instanceof OBRecoveryIncome)) {
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
}
