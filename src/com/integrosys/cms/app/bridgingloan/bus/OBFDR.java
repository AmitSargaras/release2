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
public class OBFDR implements IFDR {
	private long fdrID = ICMSConstant.LONG_INVALID_VALUE;

	private long projectID = ICMSConstant.LONG_INVALID_VALUE;

	private Date fdrDate;

	private String accountNo;

	private Amount fdrAmount;

	private String referenceNo;

	private String remarks;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd;

	/**
	 * Default Constructor
	 */
	public OBFDR() {
	}

	public long getFdrID() {
		return fdrID;
	}

	public void setFdrID(long fdrID) {
		this.fdrID = fdrID;
	}

	public long getProjectID() {
		return projectID;
	}

	public void setProjectID(long projectID) {
		this.projectID = projectID;
	}

	public Date getFdrDate() {
		return fdrDate;
	}

	public void setFdrDate(Date fdrDate) {
		this.fdrDate = fdrDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Amount getFdrAmount() {
		return fdrAmount;
	}

	public void setFdrAmount(Amount fdrAmount) {
		this.fdrAmount = fdrAmount;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
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
