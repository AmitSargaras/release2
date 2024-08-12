/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/OBDebtor.java,v 1.16 2005/08/12 03:32:36 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/*
 * This is OBDebtor class
 * @author $Author: wltan $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2005/08/12 03:32:36 $
 * Tag: $Name:  $
 */
public class OBDebtor extends OBValuationDetails implements IDebtor {
	private CurrencyCode debtAmtCCYCode;

	private static int TOTAL_NUM_MONTH = 13;

	// debtAmt replaces the 13 debt amt
	private Amount[] debtAmt = new Amount[TOTAL_NUM_MONTH];

	private Amount grossValue;

	private Amount netValue;

	private long assetGCDebtorID = ICMSConstant.LONG_MIN_VALUE;

	private int applicablePeriod = ICMSConstant.INT_INVALID_VALUE;

	// defaulted to month
	private String applicablePeriodUnit = ICMSConstant.TIME_FREQ_MONTH;

	private Amount totalApplicableAmt;

	/**
	 * Default Constructor.
	 */
	public OBDebtor() {
	}

	/**
	 * Construct the object from its interface.
	 * @param obj is of type IDebtor
	 */
	public OBDebtor(IDebtor obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * To get assetGCDebtorID
	 * @return long assetGCDebtorID
	 */
	public long getAssetGCDebtorID() {
		return assetGCDebtorID;
	}

	/**
	 * To set assetGCDebtorID
	 * @param cmsAssetGchrgDebtorID assetGCDebtorID of type long
	 */
	public void setAssetGCDebtorID(long cmsAssetGchrgDebtorID) {
		this.assetGCDebtorID = cmsAssetGchrgDebtorID;
	}

	/**
	 * To get applicablePeriod
	 * @return int applicablePeriod
	 */
	public int getApplicablePeriod() {
		return applicablePeriod;
	}

	/**
	 * To set applicablePeriod
	 * @param applicablePeriod applicablePeriod of type int
	 */
	public void setApplicablePeriod(int applicablePeriod) {
		this.applicablePeriod = applicablePeriod;
	}

	/**
	 * To get applicablePeriodUnit
	 * @return String applicablePeriodUnit
	 */
	public String getApplicablePeriodUnit() {
		return applicablePeriodUnit;
	}

	/**
	 * To set applicablePeriodUnit
	 * @param applicablePeriodUnit applicablePeriodUnit of type String
	 */
	public void setApplicablePeriodUnit(String applicablePeriodUnit) {
		this.applicablePeriodUnit = applicablePeriodUnit;
	}

	/**
	 * Get debtor gross value.
	 * 
	 * @return Amount
	 */
	public Amount getGrossValue() {
		return grossValue;
	}

	/**
	 * Set debtor gross value.
	 * 
	 * @param grossValue - Amount
	 */
	public void setGrossValue(Amount grossValue) {
		this.grossValue = grossValue;
	}

	/**
	 * Get debtor net value.
	 * 
	 * @return Amount
	 */
	public Amount getNetValue() {
		return netValue;
	}

	/**
	 * Set debtor net value
	 * 
	 * @param netValue - Amount
	 */
	public void setNetValue(Amount netValue) {
		this.netValue = netValue;
	}

	/**
	 * To get month1DebtAmt
	 * @return Amount month1DebtAmt
	 */

	public Amount getMonth1DebtAmount() {
		return debtAmt[0];
	}

	/**
	 * To set month1DebtAmt
	 * @param mth1DebAmt month1DebtAmt of type Amount
	 */
	public void setMonth1DebtAmount(Amount mth1DebAmt) {
		this.debtAmt[0] = mth1DebAmt;
	}

	/**
	 * To get month2DebtAmt
	 * @return Amount month2DebtAmt
	 */

	public Amount getMonth2DebtAmount() {
		return debtAmt[1];
	}

	/**
	 * To set month2DebtAmt
	 * @param mth2DebAmt month2DebtAmt of type Amount
	 */
	public void setMonth2DebtAmount(Amount mth2DebAmt) {
		this.debtAmt[1] = mth2DebAmt;
	}

	/**
	 * To get month3DebtAmt
	 * @return Amount month3DebtAmt
	 */
	public Amount getMonth3DebtAmount() {
		return debtAmt[2];
	}

	/**
	 * To set month3DebtAmt
	 * @param mth3DebAmt month3DebtAmt of type Amount
	 */
	public void setMonth3DebtAmount(Amount mth3DebAmt) {
		this.debtAmt[2] = mth3DebAmt;
	}

	/**
	 * To get month4DebtAmt
	 * @return Amount month4DebtAmt
	 */
	public Amount getMonth4DebtAmount() {
		return debtAmt[3];
	}

	/**
	 * To set month4DebtAmt
	 * @param mth4DebAmt month4DebtAmt of type Amount
	 */
	public void setMonth4DebtAmount(Amount mth4DebAmt) {
		this.debtAmt[3] = mth4DebAmt;
	}

	/**
	 * To get month5DebtAmt
	 * @return Amount month5DebtAmt
	 */

	public Amount getMonth5DebtAmount() {
		return debtAmt[4];
	}

	/**
	 * To set month5DebtAmt
	 * @param mth5DebAmt month5DebtAmt of type Amount
	 */
	public void setMonth5DebtAmount(Amount mth5DebAmt) {
		this.debtAmt[4] = mth5DebAmt;
	}

	/**
	 * To get month6DebtAmt
	 * @return Amount month6DebtAmt
	 */

	public Amount getMonth6DebtAmount() {
		return debtAmt[5];
	}

	/**
	 * To set month6DebtAmt
	 * @param mth6DebAmt month6DebtAmt of type Amount
	 */
	public void setMonth6DebtAmount(Amount mth6DebAmt) {
		this.debtAmt[5] = mth6DebAmt;
	}

	/**
	 * To get month7DebtAmt
	 * @return Amount month7DebtAmt
	 */
	public Amount getMonth7DebtAmount() {
		return debtAmt[6];
	}

	/**
	 * To set month7DebtAmt
	 * @param mth7DebAmt month7DebtAmt of type Amount
	 */
	public void setMonth7DebtAmount(Amount mth7DebAmt) {
		this.debtAmt[6] = mth7DebAmt;
	}

	/**
	 * To get month8DebtAmt
	 * @return Amount month8DebtAmt
	 */
	public Amount getMonth8DebtAmount() {
		return debtAmt[7];
	}

	/**
	 * To set month8DebtAmt
	 * @param mth8DebAmt month8DebtAmt of type Amount
	 */
	public void setMonth8DebtAmount(Amount mth8DebAmt) {
		this.debtAmt[7] = mth8DebAmt;
	}

	/**
	 * To get month9DebtAmt
	 * @return Amount month9DebtAmt
	 */
	public Amount getMonth9DebtAmount() {
		return debtAmt[8];
	}

	/**
	 * To set month9DebtAmt
	 * @param mth9DebAmt month9DebtAmt of type Amount
	 */
	public void setMonth9DebtAmount(Amount mth9DebAmt) {
		this.debtAmt[8] = mth9DebAmt;
	}

	/**
	 * To get month10DebtAmt
	 * @return Amount month10DebtAmt
	 */
	public Amount getMonth10DebtAmount() {
		return debtAmt[9];
	}

	/**
	 * To set month10DebtAmt
	 * @param mth10DebAmt month10DebtAmt of type Amount
	 */
	public void setMonth10DebtAmount(Amount mth10DebAmt) {
		this.debtAmt[9] = mth10DebAmt;
	}

	/**
	 * To get month11DebtAmt
	 * @return Amount month11DebtAmt
	 */
	public Amount getMonth11DebtAmount() {
		return debtAmt[10];
	}

	/**
	 * To set month11DebtAmt
	 * @param mth11DebAmt month11DebtAmt of type Amount
	 */
	public void setMonth11DebtAmount(Amount mth11DebAmt) {
		this.debtAmt[10] = mth11DebAmt;
	}

	/**
	 * To get month12DebtAmt
	 * @return Amount month12DebtAmt
	 */
	public Amount getMonth12DebtAmount() {
		return debtAmt[11];
	}

	/**
	 * To set month12DebtAmt
	 * @param mth12DebAmt month12DebtAmt of type Amount
	 */
	public void setMonth12DebtAmount(Amount mth12DebAmt) {
		this.debtAmt[11] = mth12DebAmt;
	}

	/**
	 * To get monthMoreThan12DebtAmt
	 * @return Amount monthMoreThan12DebtAmt
	 */
	public Amount getMonthMoreThan12DebtAmount() {
		return debtAmt[12];
	}

	/**
	 * To set monthMoreThan12DebtAmt
	 * @param mthMoreThan12DebAmt monthMoreThan12DebtAmt of type Amount
	 */
	public void setMonthMoreThan12DebtAmount(Amount mthMoreThan12DebAmt) {
		this.debtAmt[12] = mthMoreThan12DebAmt;
	}

	/**
	 * To get debt amount currency
	 * @return String denoting the currency
	 */
	public String getDebtAmountCurrency() {
		return (debtAmtCCYCode == null) ? null : debtAmtCCYCode.getCode();
	}

	/**
	 * To set debt amount currency
	 * @param debtAmtCCYCode - String
	 */
	public void setDebtAmountCurrency(String debtAmtCCYCode) {
		this.debtAmtCCYCode = (debtAmtCCYCode == null) ? null : new CurrencyCode(debtAmtCCYCode);
	}

	/**
	 * To get debtor total applicable amount
	 * @return Amount totalApplicableAmt
	 */
	public Amount getTotalApplicableAmt() {
		return this.totalApplicableAmt;
	}

	/**
	 * To set total applicable amount
	 * @param totalApplicableAmt total debtor applicable amount of type Amount
	 */
	public void setTotalApplicableAmt(Amount totalApplicableAmt) {
		this.totalApplicableAmt = totalApplicableAmt;
	}

	////////////////////////////////////////////////////////////////////////////
	// //////
	// /////// Methods to calculate derived values
	// ////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	// //////

	/**
	 * Get calculated gross value in Debt Amount Currency. Also the total debt
	 * amount.
	 * 
	 * @return Amount - Debtor Gross Value
	 */
	public Amount getCalculatedGrossValue() {
		Amount derivedTotal = null;
		try {
			for (int i = 0; i < debtAmt.length; i++) {
				derivedTotal = add(derivedTotal, debtAmt[i]);
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating debtor gross amount : " + e.toString());
		}
		return derivedTotal;
	}

	/**
	 * Get calculated net value for debtor.
	 * 
	 * @param debtorMargin
	 * @return Amount - Debtor Net Value
	 */
	/*
	 * public Amount getCalculatedNetValue(double debtorMargin) { Amount
	 * applicableDebtAmt = getCalculatedApplicableDebtAmount(); if
	 * (applicableDebtAmt == null || debtorMargin < 0) { return null; } if
	 * (debtorMargin == 0) { return new Amount(0, getDebtAmountCurrency()); }
	 * return (debtorMargin == 100) ? applicableDebtAmt :
	 * applicableDebtAmt.multiply(new BigDecimal(debtorMargin)); }
	 */

	/**
	 * Get calculated applicable debt amount for debtor in Debt Amount Currency.
	 * 
	 */
	public Amount getCalculatedApplicableDebtAmount() {
		int applicablePeriod = getApplicablePeriod();
		if (applicablePeriod == ICMSConstant.INT_INVALID_VALUE) {
			return null;
		}
		// A max of TOTAL_NUM_MONTH debt amount be specified.
		// If the specified figure > TOTAL_NUM_MONTH, then defaults the
		// applicable period to TOTAL_NUM_MONTH
		applicablePeriod = (applicablePeriod > TOTAL_NUM_MONTH) ? TOTAL_NUM_MONTH : applicablePeriod;
		Amount applicableDebtAmount = null;
		try {
			for (int i = 0; i <= (applicablePeriod - 1); i++) {
				applicableDebtAmount = add(applicableDebtAmount, debtAmt[i]);
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating debtor applicable debt amt : " + e.toString());
		}
		return applicableDebtAmount;
	}

	/**
	 * Get calculated overdue debt amount based on the specified applicable
	 * period in Debt Amount Currency. Debt amount that do not fall under the
	 * applicable period is considered overdue.
	 * 
	 * @return Amount - Overdue Amount
	 */
	public Amount getCalculatedOverdueDebtAmount() {
		Amount totalDebtAmt = getGrossValue();
		Amount applicableDebtAmt = getCalculatedApplicableDebtAmount();
		if (grossValue == null) {
			return null;
		}
		Amount overdueAmt = null;
		try {
			overdueAmt = (applicableDebtAmt == null) ? grossValue : totalDebtAmt.subtract(applicableDebtAmt);
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating debtor overdue debt amt : " + e.toString());
		}
		return overdueAmt;
	}

	/**
	 * Helper method to add amt to total.
	 * 
	 * @param total - Amount
	 * @param amtToAdd - Amount
	 * @return Amount new total
	 */
	private Amount add(Amount total, Amount amtToAdd) throws ChainedException {
		if (total == null) {
			return amtToAdd;
		}
		return (amtToAdd == null) ? total : total.add(amtToAdd);
	}

	/**
	 * Return a String representation of this object.
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Return the hash code
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(assetGCDebtorID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBDebtor)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}
