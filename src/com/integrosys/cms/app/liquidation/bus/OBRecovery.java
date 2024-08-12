package com.integrosys.cms.app.liquidation.bus;

import java.util.Collection;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This Object represents Recovery
 * 
 * @author $Author: lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBRecovery implements IRecovery {

	// private long liquidationID;

	private Long recoveryID;

	private String recoveryType;

	private String remarks;

	private String status;

	private long versionTime;

	private Collection recoveryIncome;

	private long refID;

	// public long getLiquidationID() {
	// return liquidationID;
	// }
	//
	// public void setLiquidationID(long liquidationID) {
	// this.liquidationID = liquidationID;
	// }

	public Long getRecoveryID() {
		return recoveryID;
	}

	public void setRecoveryID(Long recoveryID) {
		this.recoveryID = recoveryID;
	}

	public String getRecoveryType() {
		return recoveryType;
	}

	public void setRecoveryType(String recoveryType) {
		this.recoveryType = recoveryType;
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

	public Collection getRecoveryIncome() {
		return recoveryIncome;
	}

	public void setRecoveryIncome(Collection recoveryIncome) {
		this.recoveryIncome = recoveryIncome;
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
		else if (!(obj instanceof OBRecovery)) {
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
