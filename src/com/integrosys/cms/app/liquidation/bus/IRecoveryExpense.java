/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.liquidation.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents Recovery Expenses
 * 
 * @author $Author: lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IRecoveryExpense extends Serializable {
	// public long getLiquidationID();
	// public void setLiquidationID(long liquidationID);
	public Long getRecoveryExpenseID();

	public void setRecoveryExpenseID(Long recoveryExpenseID);

	public String getExpenseType();

	public void setExpenseType(String expenseType);

	public Date getDateOfExpense();

	public void setDateOfExpense(Date dateOfExpense);

	public Amount getExpenseAmount();

	public void setExpenseAmount(Amount expenseAmount);

	public String getExpenseAmtCurrency();

	public void setExpenseAmtCurrency(String expenseCurrency);

	public String getRemarks();

	public void setRemarks(String remarks);

	public String getStatus();

	public void setStatus(String status);

	public long getVersionTime();

	public void setVersionTime(long versionTime);

	public long getRefID();

	public void setRefID(long refID);

	public String getSettled();

	public void setSettled(String settled);
}
