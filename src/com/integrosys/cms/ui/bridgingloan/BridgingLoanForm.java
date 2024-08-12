/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * ActionForm for BridgingLoanAction.
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */

public class BridgingLoanForm extends TrxContextForm implements Serializable {
	private String projectID = "";

	private String limitID = "";

	private String limitProfileID = "";

	private String projectNumber = "";

	private String contractDate = "";

	private String contractCurrency = "";

	private String contractAmount = "";

	private String financePercent = "";

	private String financedAmount;

	private String collectionAccount = "";

	private String hdaAccount = "";

	private String projectAccount = "";

	private String currentAccount = "";

	private String fullSettlementDate = "";

	private String fullSettlementContractDate = "";

	private String noOfTypes = "";

	private String noOfUnits = "";

	private String expectedStartDate = "";

	private String expectedCompletionDate = "";

	private String actualStartDate = "";

	private String actualCompletionDate = "";

	private String availabilityExpiryDate = "";

	private String disbursementDate = "";

	private String blRemarks = "";

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getLimitID() {
		return limitID;
	}

	public void setLimitID(String limitID) {
		this.limitID = limitID;
	}

	public String getLimitProfileID() {
		return limitProfileID;
	}

	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getContractCurrency() {
		return contractCurrency;
	}

	public void setContractCurrency(String contractCurrency) {
		this.contractCurrency = contractCurrency;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getFinancePercent() {
		return financePercent;
	}

	public void setFinancePercent(String financePercent) {
		this.financePercent = financePercent;
	}

	public String getFinancedAmount() {
		return financedAmount;
	}

	public void setFinancedAmount(String financedAmount) {
		this.financedAmount = financedAmount;
	}

	public String getCollectionAccount() {
		return collectionAccount;
	}

	public void setCollectionAccount(String collectionAccount) {
		this.collectionAccount = collectionAccount;
	}

	public String getHdaAccount() {
		return hdaAccount;
	}

	public void setHdaAccount(String hdaAccount) {
		this.hdaAccount = hdaAccount;
	}

	public String getProjectAccount() {
		return projectAccount;
	}

	public void setProjectAccount(String projectAccount) {
		this.projectAccount = projectAccount;
	}

	public String getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(String currentAccount) {
		this.currentAccount = currentAccount;
	}

	public String getFullSettlementDate() {
		return fullSettlementDate;
	}

	public void setFullSettlementDate(String fullSettlementDate) {
		this.fullSettlementDate = fullSettlementDate;
	}

	public String getFullSettlementContractDate() {
		return fullSettlementContractDate;
	}

	public void setFullSettlementContractDate(String fullSettlementContractDate) {
		this.fullSettlementContractDate = fullSettlementContractDate;
	}

	public String getNoOfTypes() {
		return noOfTypes;
	}

	public void setNoOfTypes(String noOfTypes) {
		this.noOfTypes = noOfTypes;
	}

	public String getNoOfUnits() {
		return noOfUnits;
	}

	public void setNoOfUnits(String noOfUnits) {
		this.noOfUnits = noOfUnits;
	}

	public String getExpectedStartDate() {
		return expectedStartDate;
	}

	public void setExpectedStartDate(String expectedStartDate) {
		this.expectedStartDate = expectedStartDate;
	}

	public String getExpectedCompletionDate() {
		return expectedCompletionDate;
	}

	public void setExpectedCompletionDate(String expectedCompletionDate) {
		this.expectedCompletionDate = expectedCompletionDate;
	}

	public String getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(String actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public String getActualCompletionDate() {
		return actualCompletionDate;
	}

	public void setActualCompletionDate(String actualCompletionDate) {
		this.actualCompletionDate = actualCompletionDate;
	}

	public String getAvailabilityExpiryDate() {
		return availabilityExpiryDate;
	}

	public void setAvailabilityExpiryDate(String availabilityExpiryDate) {
		this.availabilityExpiryDate = availabilityExpiryDate;
	}

	public String getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(String disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public String getBlRemarks() {
		return blRemarks;
	}

	public void setBlRemarks(String blRemarks) {
		this.blRemarks = blRemarks;
	}

	public String[][] getMapper() {
		String[][] input = { { "bridgingLoanTrxValue", "com.integrosys.cms.ui.bridgingloan.BridgingLoanMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "objBridgingLoan", "com.integrosys.cms.ui.bridgingloan.BridgingLoanMapper" }, };
		return input;
	}
}