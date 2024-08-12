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
public class OBSalesProceeds implements ISalesProceeds {

	private long proceedsID = ICMSConstant.LONG_INVALID_VALUE;

	private Date proceedsDate;

	private String purpose;

	private float purposePercent = ICMSConstant.FLOAT_INVALID_VALUE;

	private String bankName;

	private String chequeNo;

	private Amount receiveAmount;

	private String status;

	private String remarks;

	private Date distributeDate;

	private Amount distributeAmount;

	private boolean isToTL1;

	private Amount tL1Amount;

	private boolean isToOD;

	private Amount odAmount;

	private boolean isToFDR;

	private Amount fdrAmount;

	private boolean isToHDA;

	private Amount hdaAmount;

	private boolean isToOthers;

	private String othersAccount;

	private Amount othersAmount;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd;

	/**
	 * Default Constructor
	 */
	public OBSalesProceeds() {
	}

	public long getProceedsID() {
		return proceedsID;
	}

	public void setProceedsID(long proceedsID) {
		this.proceedsID = proceedsID;
	}

	public Date getProceedsDate() {
		return proceedsDate;
	}

	public void setProceedsDate(Date proceedsDate) {
		this.proceedsDate = proceedsDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public float getPurposePercent() {
		return purposePercent;
	}

	public void setPurposePercent(float purposePercent) {
		this.purposePercent = purposePercent;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public Amount getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(Amount receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getDistributeDate() {
		return distributeDate;
	}

	public void setDistributeDate(Date distributeDate) {
		this.distributeDate = distributeDate;
	}

	public Amount getDistributeAmount() {
		return distributeAmount;
	}

	public void setDistributeAmount(Amount distributeAmount) {
		this.distributeAmount = distributeAmount;
	}

	public boolean getIsToTL1() {
		return isToTL1;
	}

	public void setIsToTL1(boolean isToTL1) {
		this.isToTL1 = isToTL1;
	}

	public Amount getTL1Amount() {
		return tL1Amount;
	}

	public void setTL1Amount(Amount tL1Amount) {
		this.tL1Amount = tL1Amount;
	}

	public boolean getIsToOD() {
		return isToOD;
	}

	public void setIsToOD(boolean isToOD) {
		this.isToOD = isToOD;
	}

	public Amount getOdAmount() {
		return odAmount;
	}

	public void setOdAmount(Amount odAmount) {
		this.odAmount = odAmount;
	}

	public boolean getIsToFDR() {
		return isToFDR;
	}

	public void setIsToFDR(boolean isToFDR) {
		this.isToFDR = isToFDR;
	}

	public Amount getFdrAmount() {
		return fdrAmount;
	}

	public void setFdrAmount(Amount fdrAmount) {
		this.fdrAmount = fdrAmount;
	}

	public boolean getIsToHDA() {
		return isToHDA;
	}

	public void setIsToHDA(boolean isToHDA) {
		this.isToHDA = isToHDA;
	}

	public Amount getHdaAmount() {
		return hdaAmount;
	}

	public void setHdaAmount(Amount hdaAmount) {
		this.hdaAmount = hdaAmount;
	}

	public boolean getIsToOthers() {
		return isToOthers;
	}

	public void setIsToOthers(boolean isToOthers) {
		this.isToOthers = isToOthers;
	}

	public String getOthersAccount() {
		return othersAccount;
	}

	public void setOthersAccount(String othersAccount) {
		this.othersAccount = othersAccount;
	}

	public Amount getOthersAmount() {
		return othersAmount;
	}

	public void setOthersAmount(Amount othersAmount) {
		this.othersAmount = othersAmount;
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
