/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.liquidation.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents Recovery Income
 * 
 * @author $Author: lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IRecoveryIncome extends Serializable {
	/*
	 * public Long getRecoveryID(); public void setRecoveryID(Long recoveryID);
	 */
	public Long getRecoveryIncomeID();

	public void setRecoveryIncomeID(Long recoveryIncomeID);

	public Date getRecoveryDate();

	public void setRecoveryDate(Date recoveryDate);

	public Amount getTotalAmountRecovered();

	public void setTotalAmountRecovered(Amount totalAmountRecovered);

	public String getTotalAmtRecoveredCurrency();

	public void setTotalAmtRecoveredCurrency(String totalAmtRecoveredCurr);

	public String getRemarks();

	public void setRemarks(String remarks);

	public String getStatus();

	public void setStatus(String status);

	public long getVersionTime();

	public void setVersionTime(long versionTime);

	public long getRefID();

	public void setRefID(long refID);

}
