package com.integrosys.cms.app.bridgingloan.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBDisbursement implements IDisbursement {
	private long disbursementID = ICMSConstant.LONG_INVALID_VALUE;

	private long projectID = ICMSConstant.LONG_INVALID_VALUE;

	private String purpose;

	private String disRemarks;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd;

	private IDisbursementDetail[] disbursementDetailList;

	/**
	 * Default Constructor
	 */
	public OBDisbursement() {
	}

	public long getDisbursementID() {
		return disbursementID;
	}

	public void setDisbursementID(long disbursementID) {
		this.disbursementID = disbursementID;
	}

	public long getProjectID() {
		return projectID;
	}

	public void setProjectID(long projectID) {
		this.projectID = projectID;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getDisRemarks() {
		return disRemarks;
	}

	public void setDisRemarks(String disRemarks) {
		this.disRemarks = disRemarks;
	}

	public IDisbursementDetail[] getDisbursementDetailList() {
		return disbursementDetailList;
	}

	public void setDisbursementDetailList(IDisbursementDetail[] disbursementDetailList) {
		this.disbursementDetailList = disbursementDetailList;
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
