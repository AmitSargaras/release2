/**
 * This interface represents an Limit Profile.
 *
 * @author $Author: jzhan $
 * @version $Revision: 1.3 $
 * @since $Date: 2006/04/28 11:12:52 $
 * Tag: $Name:  $
 */
package com.integrosys.cms.app.eventmonitor.seccoverage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class OBSecCovSecurityInfo implements Serializable {
	private String collateralID;

	private String fsv;

	private String fsvCurrency;

	private double totalLimitAmount;

	private double fsvBalance;

	private double genChargeSurplus;

	private double secLevelSecurityCoverage;

	private List charges;

	public OBSecCovSecurityInfo() {
		charges = new ArrayList();
	}

	/**
	 * @return Returns the fsv.
	 */
	public String getFsv() {
		return fsv;
	}

	/**
	 * @param fsv The fsv to set.
	 */
	public void setFsv(String fsv) {
		this.fsv = fsv;
	}

	/**
	 * @return Returns the fsvBalance.
	 */
	public double getFsvBalance() {
		return fsvBalance;
	}

	/**
	 * @param fsvBalance The fsvBalance to set.
	 */
	public void setFsvBalance(double fsvBalance) {
		this.fsvBalance = fsvBalance;
	}

	/**
	 * @return Returns the fsvCurrency.
	 */
	public String getFsvCurrency() {
		return fsvCurrency;
	}

	/**
	 * @param fsvCurrency The fsvCurrency to set.
	 */
	public void setFsvCurrency(String fsvCurrency) {
		this.fsvCurrency = fsvCurrency;
	}

	/**
	 * @return Returns the genChargeSurplus.
	 */
	public double getGenChargeSurplus() {
		return genChargeSurplus;
	}

	/**
	 * @param genChargeSurplus The genChargeSurplus to set.
	 */
	public void setGenChargeSurplus(double genChargeSurplus) {
		this.genChargeSurplus = genChargeSurplus;
	}

	/**
	 * @return Returns the secLevelSecurityCoverage.
	 */
	public double getSecLevelSecurityCoverage() {
		return secLevelSecurityCoverage;
	}

	/**
	 * @param secLevelSecurityCoverage The secLevelSecurityCoverage to set.
	 */
	public void setSecLevelSecurityCoverage(double secLevelSecurityCoverage) {
		this.secLevelSecurityCoverage = secLevelSecurityCoverage;
	}

	/**
	 * @return Returns the securityId.
	 */
	public String getCollateralID() {
		return collateralID;
	}

	/**
	 * @param securityId The securityId to set.
	 */
	public void setCollateralID(String collateralID) {
		this.collateralID = collateralID;
	}

	/**
	 * @return Returns the totalLimitAmount.
	 */
	public double getTotalLimitAmount() {
		return totalLimitAmount;
	}

	/**
	 * @param totalLimitAmount The totalLimitAmount to set.
	 */
	public void setTotalLimitAmount(double totalLimitAmount) {
		this.totalLimitAmount = totalLimitAmount;
	}

	/**
	 * @return Returns the charges.
	 */
	public List getCharges() {
		return charges;
	}

	public void addCharge(OBSecCovChargeInfo model) {
		charges.add(model);
	}
}
