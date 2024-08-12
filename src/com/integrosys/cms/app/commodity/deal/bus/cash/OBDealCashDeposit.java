/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/cash/OBDealCashDeposit.java,v 1.4 2005/10/10 01:30:27 pooja Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.cash;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: pooja $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/10/10 01:30:27 $ Tag: $Name: $
 */
public class OBDealCashDeposit implements IDealCashDeposit {

	private long depositID = ICMSConstant.LONG_INVALID_VALUE;

	private long commonReferenceID = ICMSConstant.LONG_INVALID_VALUE;

	private String type;

	private String referenceNo;

	private Amount amount;

	private String locationCountryCode;

	private Date maturityDate;

	private String status = ICMSConstant.STATE_ACTIVE;

	private Date lastUpdatedDate;

	public OBDealCashDeposit() {
	}

	public OBDealCashDeposit(IDealCashDeposit iValue) {
		if (iValue != null) {
			AccessorUtil.copyValue(iValue, this);
		}
	}

	public long getCashDepositID() {
		return depositID;
	}

	public void setCashDepositID(long depositID) {
		this.depositID = depositID;
	}

	public String getDepositType() {
		return type;
	}

	public void setDepositType(String type) {
		// TODO : chk for valid deposit types, consider creating object types
		this.type = type;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public String getLocationCountryCode() {
		return locationCountryCode;
	}

	public void setLocationCountryCode(String locationCountryCode) {
		this.locationCountryCode = locationCountryCode;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public long getCommonReferenceID() {
		return commonReferenceID;
	}

	public void setCommonReferenceID(long commonReferenceID) {
		this.commonReferenceID = commonReferenceID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		// TODO : to verify the valid status, consider using object types
		this.status = status;
	}

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OBDealCashDeposit)) {
			return false;
		}

		final OBDealCashDeposit obCashDeposit = (OBDealCashDeposit) o;

		if (commonReferenceID != obCashDeposit.commonReferenceID) {
			return false;
		}
		if (depositID != obCashDeposit.depositID) {
			return false;
		}
		if (amount != null ? !amount.equals(obCashDeposit.amount) : obCashDeposit.amount != null) {
			return false;
		}
		if (referenceNo != null ? !referenceNo.equals(obCashDeposit.referenceNo) : obCashDeposit.referenceNo != null) {
			return false;
		}
		if (status != null ? !status.equals(obCashDeposit.status) : obCashDeposit.status != null) {
			return false;
		}
		if (type != null ? !type.equals(obCashDeposit.type) : obCashDeposit.type != null) {
			return false;
		}
		if (locationCountryCode != null ? !locationCountryCode.equals(obCashDeposit.locationCountryCode)
				: obCashDeposit.locationCountryCode != null) {
			return false;
		}
		if (maturityDate != null ? !maturityDate.equals(obCashDeposit.maturityDate)
				: obCashDeposit.maturityDate != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (int) (depositID ^ (depositID >>> 32));
		result = 29 * result + (int) (commonReferenceID ^ (commonReferenceID >>> 32));
		result = 29 * result + (type != null ? type.hashCode() : 0);
		result = 29 * result + (referenceNo != null ? referenceNo.hashCode() : 0);
		result = 29 * result + (amount != null ? amount.hashCode() : 0);
		result = 29 * result + (locationCountryCode != null ? locationCountryCode.hashCode() : 0);
		result = 29 * result + (maturityDate != null ? maturityDate.hashCode() : 0);
		result = 29 * result + (status != null ? status.hashCode() : 0);
		return result;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}