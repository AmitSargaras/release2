/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/CashDeposit.java,v 1.1 2003/12/08 04:25:49 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus.cash;

import java.util.Date;

import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents cash deposit.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/12/08 04:25:49 $ Tag: $Name: $
 */
public class CashDeposit implements java.io.Serializable {

	/**
	 * Default constructor.
	 */
	public CashDeposit() {
		super();
	}

	private long cashDepositId;

	private long collateralId;

	private String securityId;

	private String changeIndicator;

	private String updateStatusIndicator;

	private String referenceNo;

	private String receiptNumber;

	private String amountCurrency;

	private Double amount;

	private Date maturityDate;

	private String sourceId;

	private Long cmsRefId;

	private String holdCode;

	private String ownOtherBankInd;

	private String groupAcctNo;

	private Long tenure;

	private String tenurePeriodType;

	private String status;

	public long getCashDepositId() {
		return cashDepositId;
	}

	public void setCashDepositId(long cashDepositId) {
		this.cashDepositId = cashDepositId;
	}

	public long getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getReceiptNumber() {
		return receiptNumber;
	}

	public void setReceiptNumber(String receiptNumber) {
		this.receiptNumber = receiptNumber;
	}

	public String getAmountCurrency() {
		return amountCurrency;
	}

	public void setAmountCurrency(String amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getMaturityDate() {
		return MessageDate.getInstance().getString(maturityDate);
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = MessageDate.getInstance().getDate(maturityDate);
	}

	public Date getJDOMaturityDate() {
		return maturityDate;
	}

	public void setJDOMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public Long getCmsRefId() {
		return cmsRefId;
	}

	public void setCmsRefId(Long cmsRefId) {
		this.cmsRefId = cmsRefId;
	}

	public String getOwnOtherBankInd() {
		return ownOtherBankInd;
	}

	public void setOwnOtherBankInd(String ownOtherBankInd) {
		this.ownOtherBankInd = ownOtherBankInd;
	}

	public String getGroupAcctNo() {
		return groupAcctNo;
	}

	public void setGroupAcctNo(String groupAcctNo) {
		this.groupAcctNo = groupAcctNo;
	}

	public Long getTenure() {
		return tenure;
	}

	public void setTenure(Long tenure) {
		this.tenure = tenure;
	}

	public String getTenurePeriodType() {
		return tenurePeriodType;
	}

	public void setTenurePeriodType(String tenurePeriodType) {
		this.tenurePeriodType = tenurePeriodType;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getHoldCode() {
		return holdCode;
	}

	public void setHoldCode(String holdCode) {
		this.holdCode = holdCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
