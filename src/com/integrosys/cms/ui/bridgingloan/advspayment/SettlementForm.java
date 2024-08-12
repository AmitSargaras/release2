/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * ActionForm for SettlementAction.
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class SettlementForm extends TrxContextForm implements Serializable {
	private String settlementDate = "";

	private String settledCurrency = "";

	private String settledAmount = "";

	private String outstandingCurrency = "";

	private String outstandingAmount = "";

	private String remarks = "";

	public String getSettlementDate() {
		return settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public String getSettledCurrency() {
		return settledCurrency;
	}

	public void setSettledCurrency(String settledCurrency) {
		this.settledCurrency = settledCurrency;
	}

	public String getSettledAmount() {
		return settledAmount;
	}

	public void setSettledAmount(String settledAmount) {
		this.settledAmount = settledAmount;
	}

	public String getOutstandingCurrency() {
		return outstandingCurrency;
	}

	public void setOutstandingCurrency(String outstandingCurrency) {
		this.outstandingCurrency = outstandingCurrency;
	}

	public String getOutstandingAmount() {
		return outstandingAmount;
	}

	public void setOutstandingAmount(String outstandingAmount) {
		this.outstandingAmount = outstandingAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.ui.bridgingloan.advspayment.SettlementMapper" },
				{ "objBridgingLoan", "com.integrosys.cms.ui.bridgingloan.advspayment.SettlementMapper" }, };
		return input;
	}
}