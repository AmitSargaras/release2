package com.integrosys.cms.app.liquidation.bus;

import java.io.Serializable;
import java.util.Collection;

/**
 * This interface represents Liquidation including income, expenses
 * 
 * @author $Author: lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ILiquidation extends Serializable {

	public void setLiquidationID(Long liquidationID);

	public Long getLiquidationID();

	public void setCollateralID(long collateralID);

	public long getCollateralID();

	public Collection getRecoveryExpense();

	public void setRecoveryExpense(Collection recoveryExpense);

	public Collection getRecovery();

	public void setRecovery(Collection recovery);

	// public Collection getNPLInfo();
	// public void setNPLInfo(Collection liquidations);
	/*
	 * public Collection getRecoveryIncome(); public void
	 * setRecoveryIncome(Collection recoveryIncome);
	 */
	public long getVersionTime();

	public void setVersionTime(long versionTime);

	public void removeExpenseItems(int[] anItemIndexList);

	public void removeIncomeItems(int[] anItemIndexList, String recoveryType);

	public void addExpenseItem(IRecoveryExpense anItem);

	public void addIncomeItem(IRecoveryIncome anItem, String recoveryType);

	public void removeRecoveryItems(int[] anItemIndexList);

	public void addRecoveryItem(IRecovery anItem);

	public String getSecurityID();

	public void setSecurityID(String securityID);

	public String getSecurityType();

	public void setSecurityType(String securityType);

}
