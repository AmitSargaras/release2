package com.integrosys.cms.batch.sema;

import java.math.BigDecimal;
import java.util.Date;

public class OBFacilityFile implements IFacilityFile {

	public String recordType;

	public String newIC;

	public String oldIC;

	public String others;

	public Date statusDateNPL;

	public String outstandingSign;

	public BigDecimal outstandingBalance;

	public String userCode3;

	public String statusNPL;

    public String customerStatus;

    public long tempID;

	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	public String getNewIC() {
		return newIC;
	}

	public void setNewIC(String newIC) {
		this.newIC = newIC;
	}

	public String getOldIC() {
		return oldIC;
	}

	public void setOldIC(String oldIC) {
		this.oldIC = oldIC;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public Date getStatusDateNPL() {
		return statusDateNPL;
	}

	public void setStatusDateNPL(Date statusDateNPL) {
		this.statusDateNPL = statusDateNPL;
	}

	public String getOutstandingSign() {
		return outstandingSign;
	}

	public void setOutstandingSign(String outstandingSign) {
		this.outstandingSign = outstandingSign;
	}

	public BigDecimal getOutstandingBalance() {
		return outstandingBalance;
	}

	public void setOutstandingBalance(BigDecimal outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}

	public String getUserCode3() {
		return userCode3;
	}

	public void setUserCode3(String userCode3) {
		this.userCode3 = userCode3;
	}

	public String getStatusNPL() {
		return statusNPL;
	}

	public void setStatusNPL(String statusNPL) {
		this.statusNPL = statusNPL;
	}

    public String getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(String customerStatus) {
        this.customerStatus = customerStatus;
    }

    public long getTempID() {
		return tempID;
	}

	public void setTempID(long tempID) {
		this.tempID = tempID;
	}
}
