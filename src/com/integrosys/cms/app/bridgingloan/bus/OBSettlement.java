package com.integrosys.cms.app.bridgingloan.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBSettlement implements ISettlement {
	private long settlementID = ICMSConstant.LONG_INVALID_VALUE;

	private long projectID = ICMSConstant.LONG_INVALID_VALUE;

	private Date settlementDate;

	private Amount settledAmount;

	private Amount outstandingAmount;

	private String remarks;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd;

	/**
	 * Default Constructor
	 */
	public OBSettlement() {
	}

	public long getProjectID() {
		return projectID;
	}

	public void setProjectID(long projectID) {
		this.projectID = projectID;
	}

	public long getSettlementID() {
		return settlementID;
	}

	public void setSettlementID(long settlementID) {
		this.settlementID = settlementID;
	}

	public Date getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public Amount getSettledAmount() {
		return settledAmount;
	}

	public void setSettledAmount(Amount settledAmount) {
		this.settledAmount = settledAmount;
	}

	public Amount getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(Amount outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public boolean getIsDeletedInd() {
		return isDeletedInd;
	}

	public void setIsDeletedInd(boolean isDeletedInd) {
		this.isDeletedInd = isDeletedInd;
	}
}