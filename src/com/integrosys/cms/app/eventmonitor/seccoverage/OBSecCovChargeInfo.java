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

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class OBSecCovChargeInfo implements Serializable {
	private String chargeDetailID;

	private String chargeType;

	private String securityRank;

	private String priorChargeAmount;

	private String priorChargeCurrency;

	private String chargeAmount;

	private String chargeCurrency;

	private String limitID;

	/**
	 * @return Returns the chargeAmount.
	 */
	public String getChargeAmount() {
		return chargeAmount;
	}

	/**
	 * @param chargeAmount The chargeAmount to set.
	 */
	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	/**
	 * @return Returns the chargeCurrency.
	 */
	public String getChargeCurrency() {
		return chargeCurrency;
	}

	/**
	 * @param chargeCurrency The chargeCurrency to set.
	 */
	public void setChargeCurrency(String chargeCurrency) {
		this.chargeCurrency = chargeCurrency;
	}

	/**
	 * @return Returns the chargeDetailId.
	 */
	public String getChargeDetailID() {
		return chargeDetailID;
	}

	/**
	 * @param chargeDetailId The chargeDetailId to set.
	 */
	public void setChargeDetailId(String chargeDetailID) {
		this.chargeDetailID = chargeDetailID;
	}

	/**
	 * @return Returns the chargeType.
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * @param chargeType The chargeType to set.
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * @return Returns the limitId.
	 */
	public String getLimitID() {
		return limitID;
	}

	/**
	 * @param limitId The limitId to set.
	 */
	public void setLimitId(String limitID) {
		this.limitID = limitID;
	}

	/**
	 * @return Returns the priorChargeAmount.
	 */
	public String getPriorChargeAmount() {
		return priorChargeAmount;
	}

	/**
	 * @param priorChargeAmount The priorChargeAmount to set.
	 */
	public void setPriorChargeAmount(String priorChargeAmount) {
		this.priorChargeAmount = priorChargeAmount;
	}

	/**
	 * @return Returns the priorChargeCurrency.
	 */
	public String getPriorChargeCurrency() {
		return priorChargeCurrency;
	}

	/**
	 * @param priorChargeCurrency The priorChargeCurrency to set.
	 */
	public void setPriorChargeCurrency(String priorChargeCurrency) {
		this.priorChargeCurrency = priorChargeCurrency;
	}

	/**
	 * @return Returns the securityRank.
	 */
	public String getSecurityRank() {
		return securityRank;
	}

	/**
	 * @param securityRank The securityRank to set.
	 */
	public void setSecurityRank(String securityRank) {
		this.securityRank = securityRank;
	}
}
