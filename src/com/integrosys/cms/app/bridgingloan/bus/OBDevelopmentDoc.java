package com.integrosys.cms.app.bridgingloan.bus;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBDevelopmentDoc implements IDevelopmentDoc {
	private long devDocID = ICMSConstant.LONG_INVALID_VALUE;

	private long scheduleID = ICMSConstant.LONG_INVALID_VALUE;

	private String docName;

	private String docRef;

	private Date receiveDate;

	private Date docDate;

	private String remarks;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd;

	/**
	 * Default Constructor
	 */
	public OBDevelopmentDoc() {
	}

	public long getDevDocID() {
		return devDocID;
	}

	public void setDevDocID(long devDocID) {
		this.devDocID = devDocID;
	}

	public long getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(long scheduleID) {
		this.scheduleID = scheduleID;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocRef() {
		return docRef;
	}

	public void setDocRef(String docRef) {
		this.docRef = docRef;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
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
