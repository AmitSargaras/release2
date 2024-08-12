/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/liquidation/LiquidationForm.java,v 1 2007/02/08 Lini Exp $
 */
package com.integrosys.cms.ui.liquidation;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by Liquidation Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Lini$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class LiquidationForm extends TrxContextForm implements java.io.Serializable {

	private static final long serialVersionUID = 777100493108240272L;

	// Expenses
	private String dateOfExpense;

	private String expenseType;

	private String expenseAmt;

	private String expenseAmtCurrency;

	private String expenseRemarks;

	private String settled;

	// Recovery
	private String recoveryType;

	private String totalAmtRecovered;

	private String totalAmtRecoveredCurrency;

	private String recoveryRemarks;

	// Income
	private String dateAmtRecovered;

	private String amtRecovered;

	private String amtRecoveredCurrency;

	private String amtRecoveryRemarks;

	private String grandTotalExpense;

	private String grandTotalIncome;

	private String remarks;

	public String getDateOfExpense() {
		return dateOfExpense;
	}

	public void setDateOfExpense(String dateOfExpense) {
		this.dateOfExpense = dateOfExpense;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public String getExpenseAmt() {
		return expenseAmt;
	}

	public void setExpenseAmt(String expenseAmt) {
		this.expenseAmt = expenseAmt;
	}

	public String getExpenseAmtCurrency() {
		return expenseAmtCurrency;
	}

	public void setExpenseAmtCurrency(String expenseAmtCurrency) {
		this.expenseAmtCurrency = expenseAmtCurrency;
	}

	public String getRecoveryType() {
		return recoveryType;
	}

	public void setRecoveryType(String recoveryType) {
		this.recoveryType = recoveryType;
	}

	public String getTotalAmtRecovered() {
		return totalAmtRecovered;
	}

	public void setTotalAmtRecovered(String totalAmtRecovered) {
		this.totalAmtRecovered = totalAmtRecovered;
	}

	public String getTotalAmtRecoveredCurrency() {
		return totalAmtRecoveredCurrency;
	}

	public void setTotalAmtRecoveredCurrency(String totalAmtRecoveredCurrency) {
		this.totalAmtRecoveredCurrency = totalAmtRecoveredCurrency;
	}

	public String getExpenseRemarks() {
		return expenseRemarks;
	}

	public void setExpenseRemarks(String expenseRemarks) {
		this.expenseRemarks = expenseRemarks;
	}

	public String getRecoveryRemarks() {
		return recoveryRemarks;
	}

	public void setRecoveryRemarks(String recoveryRemarks) {
		this.recoveryRemarks = recoveryRemarks;
	}

	public String getDateAmtRecovered() {
		return dateAmtRecovered;
	}

	public void setDateAmtRecovered(String dateAmtRecovered) {
		this.dateAmtRecovered = dateAmtRecovered;
	}

	public String getAmtRecovered() {
		return amtRecovered;
	}

	public void setAmtRecovered(String amtRecovered) {
		this.amtRecovered = amtRecovered;
	}

	public String getAmtRecoveredCurrency() {
		return amtRecoveredCurrency;
	}

	public void setAmtRecoveredCurrency(String amtRecoveredCurrency) {
		this.amtRecoveredCurrency = amtRecoveredCurrency;
	}

	public String getAmtRecoveryRemarks() {
		return amtRecoveryRemarks;
	}

	public void setAmtRecoveryRemarks(String amtRecoveryRemarks) {
		this.amtRecoveryRemarks = amtRecoveryRemarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getGrandTotalExpense() {
		return grandTotalExpense;
	}

	public void setGrandTotalExpense(String grandTotalExpense) {
		this.grandTotalExpense = grandTotalExpense;
	}

	public String getGrandTotalIncome() {
		return grandTotalIncome;
	}

	public void setGrandTotalIncome(String grandTotalIncome) {
		this.grandTotalIncome = grandTotalIncome;
	}

	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */

	public String[][] getMapper() {

		String[][] input = { { "recovery", "com.integrosys.cms.ui.liquidation.LiquidationMapper" },
				{ "recoveryExpense", "com.integrosys.cms.ui.liquidation.LiquidationMapper" },
				{ "recoveryIncome", "com.integrosys.cms.ui.liquidation.LiquidationMapper" },
				{ "InitialLiquidation", "com.integrosys.cms.ui.liquidation.LiquidationMapper" },
				{ "Liquidations", "com.integrosys.cms.ui.liquidation.LiquidationMapper" },
				{ "LiquidationTrxValue", "com.integrosys.cms.ui.liquidation.LiquidationMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "mapper", "com.integrosys.cms.ui.liquidation.LiquidationMapper" } };
		return input;
	}

	public String getSettled() {
		return settled;
	}

	public void setSettled(String settled) {
		this.settled = settled;
	}

}
