/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBLimitCharge.java,v 1.19 2006/02/28 05:18:46 pratheepa Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents limit charge of the collateral.
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/02/28 05:18:46 $ Tag: $Name: $
 */
public class OBLimitCharge implements ILimitCharge, Comparable {

	private static final long serialVersionUID = 6529402406576964056L;

	private long chargeDetailID = ICMSConstant.LONG_MIN_VALUE;

	private String isLEByChargeRanking;

	private String isLEByJurisdiction;

	private String isLEByGovernLaws;

	private Date lEDateByChargeRanking;

	private Date lEDateByJurisdiction;

	private Date lEDateByGovernLaws;

	// Fields isLE & lEDate and getters/setters for the same added by Pratheepa
	// for CR235.
	private String isLE;

	private Date lEDate;

	private ICollateralLimitMap[] limitMaps;

	private long collateralID = ICMSConstant.LONG_MIN_VALUE;

	private String natureOfCharge;

	private Amount chargeAmount;

	private String chargeCcyCode;

	private Date legalChargeDate;

	private String priorChargeChargee;

	private int securityRank;

	private Amount priorChargeAmount;

	private String priorChargeCcyCode;

	private String chargeType;

	private Date chargeConfirmationDate;

	private long refID = ICMSConstant.LONG_MIN_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	private String priorChargeType;

	// Caveate info - for property subtype only
	private Boolean caveatWaivedInd;

	private String caveatReferenceNo;

	private Date expiryDate;

	private String presentationNo;

	private Date presentationDate;

	private Date lodgedDate;

	private String solicitorName;

	private String redemption;

	private String folio;

	private String jilid;

	private String partyCharge;

	private String hostCollateralId;

	/**
	 * Default Constructor.
	 */
	public OBLimitCharge() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ILimitCharge
	 */
	public OBLimitCharge(ILimitCharge obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get caveat reference no
	 * 
	 * @return String
	 */
	public String getCaveatReferenceNo() {
		return this.caveatReferenceNo;
	}

	/**
	 * Get caveat waived indicator
	 * 
	 * @return boolean
	 */
	public Boolean getCaveatWaivedInd() {
		return this.caveatWaivedInd;
	}

	/**
	 * Get charge amount.
	 * 
	 * @return Amount
	 */
	public Amount getChargeAmount() {
		return chargeAmount;
	}

	/**
	 * Get charge amount currency code.
	 * 
	 * @return String
	 */
	public String getChargeCcyCode() {
		return chargeCcyCode;
	}

	/**
	 * Get date of confirmation of charge.
	 * 
	 * @return Date
	 */
	public Date getChargeConfirmationDate() {
		return chargeConfirmationDate;
	}

	/**
	 * Get charge detail id.
	 * 
	 * @return long
	 */
	public long getChargeDetailID() {
		return chargeDetailID;
	}

	/**
	 * Get type of the charge: general or specific.
	 * 
	 * @return String
	 */
	public String getChargeType() {
		return chargeType;
	}

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return collateralID;
	}

	/**
	 * Get expiry date
	 * 
	 * @return Date
	 */
	public Date getExpiryDate() {
		return this.expiryDate;
	}

	/**
	 * Get caveat folio
	 * 
	 * @return String
	 */

	public String getFolio() {
		return folio;
	}

	public String getHostCollateralId() {
		return this.hostCollateralId;
	}

	/**
	 * Get if the Legal Enforceability .
	 * 
	 * @return String
	 */
	public String getIsLE() {
		return isLE;
	}

	/**
	 * Get if the Legal Enforceability by charge ranking.
	 * 
	 * @return String
	 */
	public String getIsLEByChargeRanking() {
		return isLEByChargeRanking;
	}

	/**
	 * Get if the Legal Enforceability by governing laws.
	 * 
	 * @return String
	 */
	public String getIsLEByGovernLaws() {
		return isLEByGovernLaws;
	}

	/**
	 * Get if the Legal Enforceability by jurisdiction.
	 * 
	 * @return String
	 */
	public String getIsLEByJurisdiction() {
		return isLEByJurisdiction;
	}

	/**
	 * Get caveat jilid
	 * 
	 * @return String
	 */

	public String getJilid() {
		return jilid;
	}

	/**
	 * Get legal enforceability date .
	 * 
	 * @return Date
	 */
	public Date getLEDate() {
		return lEDate;
	}

	/**
	 * Get legal enforceability date by charge ranking.
	 * 
	 * @return Date
	 */
	public Date getLEDateByChargeRanking() {
		return lEDateByChargeRanking;
	}

	/**
	 * Get legal enforceability date by governing laws.
	 * 
	 * @return Date
	 */
	public Date getLEDateByGovernLaws() {
		return lEDateByGovernLaws;
	}

	/**
	 * Get legal enforceability date by jurisdiction.
	 * 
	 * @return Date
	 */
	public Date getLEDateByJurisdiction() {
		return lEDateByJurisdiction;
	}

	/**
	 * Get date legally charged.
	 * 
	 * @return Date
	 */
	public Date getLegalChargeDate() {
		return legalChargeDate;
	}

	/**
	 * Get limit maps of this charge.
	 * 
	 * @return long
	 */
	public ICollateralLimitMap[] getLimitMaps() {
		return limitMaps;
	}

	/**
	 * Get lodged date
	 * 
	 * @return Date
	 */
	public Date getLodgedDate() {
		return this.lodgedDate;
	}

	/**
	 * Get nature of charge.
	 * 
	 * @return String
	 */
	public String getNatureOfCharge() {
		return natureOfCharge;
	}

	/**
	 * Get caveat partyCharge
	 * 
	 * @return String
	 */

	public String getPartyCharge() {
		return partyCharge;
	}

	/**
	 * Get presentation date
	 * 
	 * @return Date
	 */
	public Date getPresentationDate() {
		return this.presentationDate;
	}

	/**
	 * Get presentation number
	 * 
	 * @return String
	 */
	public String getPresentationNo() {
		return this.presentationNo;
	}

	/**
	 * Get prior charge amount.
	 * 
	 * @return Amount
	 */
	public Amount getPriorChargeAmount() {
		return priorChargeAmount;
	}

	/**
	 * Get prior charge amount currency code.
	 * 
	 * @return String
	 */
	public String getPriorChargeCcyCode() {
		return priorChargeCcyCode;
	}

	/**
	 * Get chargee of prior charge.
	 * 
	 * @return String
	 */
	public String getPriorChargeChargee() {
		return priorChargeChargee;
	}

	/**
	 * Get prior charge relationship with the bank: external, internal, or NA.
	 * 
	 * @return String
	 */
	public String getPriorChargeType() {
		return priorChargeType;
	}

	public String getRedemption() {
		return this.redemption;
	}

	/**
	 * Get reference id between staging and actual data.
	 * 
	 * @return long
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * Get security ranking.
	 * 
	 * @return int
	 */
	public int getSecurityRank() {
		return securityRank;
	}

	/**
	 * Get solicitor name
	 * 
	 * @return String
	 */
	public String getSolicitorName() {
		return this.solicitorName;
	}

	/**
	 * Get status of this charge.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set caveat reference no
	 * 
	 * @caveatReferenceNo of type String
	 */
	public void setCaveatReferenceNo(String caveatReferenceNo) {
		this.caveatReferenceNo = caveatReferenceNo;
	}

	/**
	 * Set caveat waived indicator
	 * 
	 * @caveatWaivedInd of type boolean
	 */
	public void setCaveatWaivedInd(Boolean caveatWaivedInd) {
		this.caveatWaivedInd = caveatWaivedInd;
	}

	/**
	 * Set charge amount.
	 * 
	 * @param chargeAmount is of type Amount
	 */
	public void setChargeAmount(Amount chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	/**
	 * Set charge amount currency code.
	 * 
	 * @param chargeCcyCode of type String
	 */
	public void setChargeCcyCode(String chargeCcyCode) {
		this.chargeCcyCode = chargeCcyCode;
	}

	/**
	 * Set date of confirmation of charge.
	 * 
	 * @param chargeConfirmationDate of type Date
	 */
	public void setChargeConfirmationDate(Date chargeConfirmationDate) {
		this.chargeConfirmationDate = chargeConfirmationDate;
	}

	/**
	 * Set charge detail id.
	 * 
	 * @param chargeDetailID of type long
	 */
	public void setChargeDetailID(long chargeDetailID) {
		this.chargeDetailID = chargeDetailID;
	}

	/**
	 * Set type of the charge: general or specific.
	 * 
	 * @param chargeType of type String
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	/**
	 * Set expiry date
	 * 
	 * @expiryDate of type Date
	 */
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * Set caveat folio
	 * 
	 * @caveat folio of type String
	 */

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public void setHostCollateralId(String hostCollateralId) {
		this.hostCollateralId = hostCollateralId;
	}

	/**
	 * Set the legal enforceability .
	 * 
	 * @param isLE is of type String
	 */
	public void setIsLE(String isLE) {
		this.isLE = isLE;
	}

	/**
	 * Set the legal enforceability by charge ranking.
	 * 
	 * @param isLEByChargeRanking is of type String
	 */
	public void setIsLEByChargeRanking(String isLEByChargeRanking) {
		this.isLEByChargeRanking = isLEByChargeRanking;
	}

	/**
	 * Set if the legal enforceability by governing laws.
	 * 
	 * @param isLEByGovernLaws is of type String
	 */
	public void setIsLEByGovernLaws(String isLEByGovernLaws) {
		this.isLEByGovernLaws = isLEByGovernLaws;
	}

	/**
	 * Set the legal enforceability by jurisdiction.
	 * 
	 * @param isLEByJurisdiction is of type String
	 */
	public void setIsLEByJurisdiction(String isLEByJurisdiction) {
		this.isLEByJurisdiction = isLEByJurisdiction;
	}

	/**
	 * Set caveat jilid
	 * 
	 * @caveat jilid of type String
	 */

	public void setJilid(String jilid) {
		this.jilid = jilid;
	}

	/**
	 * Get legal enforceability date .
	 * 
	 * @param lEDate is of type Date
	 */
	public void setLEDate(Date lEDate) {
		this.lEDate = lEDate;
	}

	/**
	 * Set legal enforceability date by charge ranking.
	 * 
	 * @param lEDateByChargeRanking is of type Date
	 */
	public void setLEDateByChargeRanking(Date lEDateByChargeRanking) {
		this.lEDateByChargeRanking = lEDateByChargeRanking;
	}

	/**
	 * Set legal enforceability date by governing laws.
	 * 
	 * @param lEDateByGovernLaws is of type Date
	 */
	public void setLEDateByGovernLaws(Date lEDateByGovernLaws) {
		this.lEDateByGovernLaws = lEDateByGovernLaws;
	}

	/**
	 * Get legal enforceability date by jurisdiction.
	 * 
	 * @param lEDateByJurisdiction is of type Date
	 */
	public void setLEDateByJurisdiction(Date lEDateByJurisdiction) {
		this.lEDateByJurisdiction = lEDateByJurisdiction;
	}

	/**
	 * Set date legally charged.
	 * 
	 * @param legalChargeDate of type Date
	 */
	public void setLegalChargeDate(Date legalChargeDate) {
		this.legalChargeDate = legalChargeDate;
	}

	/**
	 * Set limit maps of this charge.
	 * 
	 * @param limitMaps is of type ICollateralLimitMap[]
	 */
	public void setLimitMaps(ICollateralLimitMap[] limitMaps) {
		this.limitMaps = limitMaps;
	}

	/**
	 * Set lodged date
	 * 
	 * @lodgedDate of type Date
	 */
	public void setLodgedDate(Date lodgedDate) {
		this.lodgedDate = lodgedDate;
	}

	/**
	 * Set nature of charge.
	 * 
	 * @param natureOfCharge of type String
	 */
	public void setNatureOfCharge(String natureOfCharge) {
		this.natureOfCharge = natureOfCharge;
	}

	/**
	 * Set caveat partyCharge
	 * 
	 * @caveat partyCharge of type String
	 */

	public void setPartyCharge(String partyCharge) {
		this.partyCharge = partyCharge;
	}

	/**
	 * Get presentation date
	 * 
	 * @presentationDate of type Date
	 */
	public void setPresentationDate(Date presentationDate) {
		this.presentationDate = presentationDate;
	}

	/**
	 * Set presentation number
	 * 
	 * @presentationNo of type String
	 */
	public void setPresentationNo(String presentationNo) {
		this.presentationNo = presentationNo;
	}

	/**
	 * Set prior charge amount.
	 * 
	 * @param priorChargeAmount of type Amount
	 */
	public void setPriorChargeAmount(Amount priorChargeAmount) {
		this.priorChargeAmount = priorChargeAmount;
	}

	/**
	 * Set prior charge amount currency code.
	 * 
	 * @param priorChargeCcyCode of type String
	 */
	public void setPriorChargeCcyCode(String priorChargeCcyCode) {
		this.priorChargeCcyCode = priorChargeCcyCode;
	}

	/**
	 * Set chargee of prior charge.
	 * 
	 * @param priorChargeChargee of type String
	 */
	public void setPriorChargeChargee(String priorChargeChargee) {
		this.priorChargeChargee = priorChargeChargee;
	}

	/**
	 * Set prior charge relationship with the bank: external, internal, or NA.
	 * 
	 * @param priorChargeType of type String
	 */
	public void setPriorChargeType(String priorChargeType) {
		this.priorChargeType = priorChargeType;
	}

	public void setRedemption(String redemption) {
		this.redemption = redemption;
	}

	/**
	 * Set reference id between staging and actual data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Set security ranking.
	 * 
	 * @param securityRank of type int
	 */
	public void setSecurityRank(int securityRank) {
		this.securityRank = securityRank;
	}

	/**
	 * Set solicitor name
	 * 
	 * @solicitorName of type String
	 */
	public void setSolicitorName(String solicitorName) {
		this.solicitorName = solicitorName;
	}

	/**
	 * Set status of this charge.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBLimitCharge)) {
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

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(chargeDetailID);
		return hash.hashCode();
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer("LimitCharge@");
		buf.append(System.identityHashCode(this)).append(", ");
		buf.append("CMS Collateral Id [").append(collateralID).append("], ");
		buf.append("Charge Ranking [").append(securityRank).append("], ");
		buf.append("Charge Amount [").append(chargeAmount).append("], ");
		buf.append("Charge Type [").append(chargeType).append("], ");
		buf.append("Legal Charge Date [").append(legalChargeDate).append("]");

		return buf.toString();
	}

	public int compareTo(Object other) {
		int otherSecurityRank = (other == null) ? Integer.MAX_VALUE : ((ILimitCharge) other).getSecurityRank();

		return (this.securityRank == otherSecurityRank) ? 0 : ((this.securityRank > otherSecurityRank) ? 1 : -1);
	}
}