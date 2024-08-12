package com.integrosys.cms.ui.bridgingloan.buildup;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * SalesProceedsForm
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class SalesProceedsForm extends TrxContextForm implements Serializable {
	private String proceedsDate = "";

	private String purpose = ""; // Purpose of Proceeds

	private String purposePercent = ""; // Purpose of Proceeds Percentage

	private String bankName = "";

	private String chequeNo = "";

	private String receiveCurrency = "";

	private String receiveAmount = ""; // Amount Received

	private String status = "";

	private String remarks = "";

	private String distributeDate = ""; // Date Payment Distributed

	private String distributeCurrency = "";

	private String distributeAmount = "";

	private String isToTL1 = ""; // TL/AC or TL

	// TODO: tL1Currency getter method not found
	private String tL1Currency = "";

	private String tL1Amount = "";

	private String isToOD = ""; // OD/AC or OD

	private String odCurrency = "";

	private String odAmount = "";

	private String isToFDR = ""; // Sinking FUND-FDR

	private String fdrCurrency = "";

	private String fdrAmount = "";

	private String isToHDA = ""; // Credit HDA

	private String hdaCurrency = "";

	private String hdaAmount = "";

	private String isToOthers = "";

	private String othersCurrency = "";

	private String othersAccount = "";

	private String othersAmount = "";

	public String getProceedsDate() {
		return proceedsDate;
	}

	public void setProceedsDate(String proceedsDate) {
		this.proceedsDate = proceedsDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPurposePercent() {
		return purposePercent;
	}

	public void setPurposePercent(String purposePercent) {
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

	public String getReceiveCurrency() {
		return receiveCurrency;
	}

	public void setReceiveCurrency(String receiveCurrency) {
		this.receiveCurrency = receiveCurrency;
	}

	public String getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(String receiveAmount) {
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

	public String getDistributeDate() {
		return distributeDate;
	}

	public String getDistributeCurrency() {
		return distributeCurrency;
	}

	public void setDistributeCurrency(String distributeCurrency) {
		this.distributeCurrency = distributeCurrency;
	}

	public void setDistributeDate(String distributeDate) {
		this.distributeDate = distributeDate;
	}

	public String getDistributeAmount() {
		return distributeAmount;
	}

	public void setDistributeAmount(String distributeAmount) {
		this.distributeAmount = distributeAmount;
	}

	public String getIsToTL1() {
		return isToTL1;
	}

	public void setIsToTL1(String isToTL1) {
		this.isToTL1 = isToTL1;
	}

	public String getTL1Currency() {
		return tL1Currency;
	}

	public void setTL1Currency(String tL1Currency) {
		this.tL1Currency = tL1Currency;
	}

	public String getTL1Amount() {
		return tL1Amount;
	}

	public void setTL1Amount(String tL1Amount) {
		this.tL1Amount = tL1Amount;
	}

	public String getIsToOD() {
		return isToOD;
	}

	public void setIsToOD(String isToOD) {
		this.isToOD = isToOD;
	}

	public String getOdCurrency() {
		return odCurrency;
	}

	public void setOdCurrency(String odCurrency) {
		this.odCurrency = odCurrency;
	}

	public String getOdAmount() {
		return odAmount;
	}

	public void setOdAmount(String odAmount) {
		this.odAmount = odAmount;
	}

	public String getIsToFDR() {
		return isToFDR;
	}

	public void setIsToFDR(String isToFDR) {
		this.isToFDR = isToFDR;
	}

	public String getFdrCurrency() {
		return fdrCurrency;
	}

	public void setFdrCurrency(String fdrCurrency) {
		this.fdrCurrency = fdrCurrency;
	}

	public String getFdrAmount() {
		return fdrAmount;
	}

	public void setFdrAmount(String fdrAmount) {
		this.fdrAmount = fdrAmount;
	}

	public String getIsToHDA() {
		return isToHDA;
	}

	public void setIsToHDA(String isToHDA) {
		this.isToHDA = isToHDA;
	}

	public String getHdaCurrency() {
		return hdaCurrency;
	}

	public void setHdaCurrency(String hdaCurrency) {
		this.hdaCurrency = hdaCurrency;
	}

	public String getHdaAmount() {
		return hdaAmount;
	}

	public void setHdaAmount(String hdaAmount) {
		this.hdaAmount = hdaAmount;
	}

	public String getIsToOthers() {
		return isToOthers;
	}

	public void setIsToOthers(String isToOthers) {
		this.isToOthers = isToOthers;
	}

	public String getOthersCurrency() {
		return othersCurrency;
	}

	public void setOthersCurrency(String othersCurrency) {
		this.othersCurrency = othersCurrency;
	}

	public String getOthersAccount() {
		return othersAccount;
	}

	public void setOthersAccount(String othersAccount) {
		this.othersAccount = othersAccount;
	}

	public String getOthersAmount() {
		return othersAmount;
	}

	public void setOthersAmount(String othersAmount) {
		this.othersAmount = othersAmount;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue" },
				{ "objBridgingLoan", "com.integrosys.cms.ui.bridgingloan.buildup.SalesProceedsMapper" }, };
		return input;
	}
}