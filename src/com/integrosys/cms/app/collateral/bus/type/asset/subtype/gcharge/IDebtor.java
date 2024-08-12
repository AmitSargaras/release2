/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/IDebtor.java,v 1.7 2005/08/12 03:32:36 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.base.businfra.currency.Amount;

/*
 * Description: This interface represents Debtor - a sub-type General Charge.
 * @author $Author: wltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/12 03:32:36 $
 * Tag: $Name:  $
 */
public interface IDebtor extends IValuationDetails {

	/**
	 * To get cmsAssetGchrgDebtorID
	 * @return long cmsAssetGchrgDebtorID
	 */
	public long getAssetGCDebtorID();

	/**
	 * To set assetGCDebtorID
	 * @param assetGCDebtorID assetGCDebtorID of type long
	 */
	public void setAssetGCDebtorID(long assetGCDebtorID);

	/**
	 * To get applicablePeriod
	 * @return int applicablePeriod
	 */
	public int getApplicablePeriod();

	/**
	 * To set applicablePeriod
	 * @param applicablePeriod applicablePeriod of type int
	 */
	public void setApplicablePeriod(int applicablePeriod);

	/**
	 * To get applicablePeriodUnit
	 * @return String applicablePeriodUnit
	 */
	public String getApplicablePeriodUnit();

	/**
	 * To set applicablePeriodUnit
	 * @param applicablePeriodUnit applicablePeriodUnit of type String
	 */
	public void setApplicablePeriodUnit(String applicablePeriodUnit);

	/**
	 * Get gross value for debtor.
	 * @return Amount
	 */
	public Amount getGrossValue();

	/**
	 * Set gross value for debtor.
	 * @param grossValue - Amount
	 */
	public void setGrossValue(Amount grossValue);

	/**
	 * Get net value for debtor.
	 * @return Amount
	 */
	public Amount getNetValue();

	/**
	 * Set net value for debtor.
	 * @param netValue - Amount
	 */
	public void setNetValue(Amount netValue);

	/**
	 * To get mth1DebAmt
	 * @return Amount mth1DebAmt
	 */
	public Amount getMonth1DebtAmount();

	/**
	 * To set month1DebtAmt
	 * @param month1DebtAmt month1DebtAmt of type Amount
	 */
	public void setMonth1DebtAmount(Amount month1DebtAmt);

	/**
	 * To get mth2DebAmt
	 * @return Amount mth2DebAmt
	 */
	public Amount getMonth2DebtAmount();

	/**
	 * To set month2DebtAmt
	 * @param month2DebtAmt month2DebtAmt of type Amount
	 */
	public void setMonth2DebtAmount(Amount month2DebtAmt);

	/**
	 * To get mth3DebAmt
	 * @return Amount mth3DebAmt
	 */
	public Amount getMonth3DebtAmount();

	/**
	 * To set month3DebtAmt
	 * @param month3DebtAmt month3DebtAmt of type Amount
	 */
	public void setMonth3DebtAmount(Amount month3DebtAmt);

	/**
	 * To get mth4DebAmt
	 * @return Amount mth4DebAmt
	 */
	public Amount getMonth4DebtAmount();

	/**
	 * To set month4DebtAmt
	 * @param month4DebtAmt month4DebtAmt of type Amount
	 */
	public void setMonth4DebtAmount(Amount month4DebtAmt);

	/**
	 * To get mth5DebAmt
	 * @return Amount mth5DebAmt
	 */
	public Amount getMonth5DebtAmount();

	/**
	 * To set month5DebtAmt
	 * @param month5DebtAmt month5DebtAmt of type Amount
	 */
	public void setMonth5DebtAmount(Amount month5DebtAmt);

	/**
	 * To get mth6DebAmt
	 * @return Amount mth6DebAmt
	 */
	public Amount getMonth6DebtAmount();

	/**
	 * To set month6DebtAmt
	 * @param month6DebtAmt month6DebtAmt of type Amount
	 */
	public void setMonth6DebtAmount(Amount month6DebtAmt);

	/**
	 * To get mth7DebAmt
	 * @return Amount mth7DebAmt
	 */
	public Amount getMonth7DebtAmount();

	/**
	 * To set month7DebtAmt
	 * @param month7DebtAmt month7DebtAmt of type Amount
	 */
	public void setMonth7DebtAmount(Amount month7DebtAmt);

	/**
	 * To get mth8DebAmt
	 * @return Amount mth8DebAmt
	 */
	public Amount getMonth8DebtAmount();

	/**
	 * To set month8DebtAmt
	 * @param month8DebtAmt month8DebtAmt of type Amount
	 */
	public void setMonth8DebtAmount(Amount month8DebtAmt);

	/**
	 * To get mth9DebAmt
	 * @return Amount mth9DebAmt
	 */
	public Amount getMonth9DebtAmount();

	/**
	 * To set month9DebtAmt
	 * @param month9DebtAmt month9DebtAmt of type Amount
	 */
	public void setMonth9DebtAmount(Amount month9DebtAmt);

	/**
	 * To get mth10DebAmt
	 * @return Amount mth10DebAmt
	 */
	public Amount getMonth10DebtAmount();

	/**
	 * To set month10DebtAmt
	 * @param month10DebtAmt month10DebtAmt of type Amount
	 */
	public void setMonth10DebtAmount(Amount month10DebtAmt);

	/**
	 * To get mth11DebAmt
	 * @return Amount mth11DebAmt
	 */
	public Amount getMonth11DebtAmount();

	/**
	 * To set month11DebtAmt
	 * @param month11DebtAmt month11DebtAmt of type Amount
	 */
	public void setMonth11DebtAmount(Amount month11DebtAmt);

	/**
	 * To get mth12DebAmt
	 * @return Amount mth12DebAmt
	 */
	public Amount getMonth12DebtAmount();

	/**
	 * To set month12DebtAmt
	 * @param month12DebtAmt month12DebtAmt of type Amount
	 */
	public void setMonth12DebtAmount(Amount month12DebtAmt);

	/**
	 * To get mthMoreThan12DebAmt
	 * @return Amount mthMoreThan12DebAmt
	 */
	public Amount getMonthMoreThan12DebtAmount();

	/**
	 * To set monthMoreThan12DebtAmt
	 * @param monthMoreThan12DebtAmt monthMoreThan12DebtAmt of type Amount
	 */
	public void setMonthMoreThan12DebtAmount(Amount monthMoreThan12DebtAmt);

	/**
	 * To get debt amount currency
	 * @return String denoting the currency
	 */
	public String getDebtAmountCurrency();

	/**
	 * To set debt amount currency
	 * @param debtAmtCurrency - String
	 */
	public void setDebtAmountCurrency(String debtAmtCurrency);

	/**
	 * To get debtor total applicable amount
	 * @return Amount totalApplicableAmt
	 */
	public Amount getTotalApplicableAmt();

	/**
	 * To set total applicable amount
	 * @param totalApplicableAmt total debtor applicable amount of type Amount
	 */
	public void setTotalApplicableAmt(Amount totalApplicableAmt);
}
