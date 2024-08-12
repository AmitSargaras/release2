/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ILimitCharge.java,v 1.19 2006/02/28 05:19:11 pratheepa Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents limit charge of the collateral.
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/02/28 05:19:11 $ Tag: $Name: $
 */
public interface ILimitCharge extends Serializable {

	/** default value for first party charge , ie "1" */
	public static final String PARTY_CHARGE_FIRST_VALUE = "1";

	/** default value for first party charge , ie "3" */
	public static final String PARTY_CHARGE_THIRD_VALUE = "3";

	/**
	 * Get caveat reference no
	 * 
	 * @return String
	 */
	public String getCaveatReferenceNo();

	/**
	 * Get caveat waived indicator
	 * 
	 * @return boolean
	 */
	public Boolean getCaveatWaivedInd();

	/**
	 * Get charge amount.
	 * 
	 * @return Amount
	 */
	public Amount getChargeAmount();

	/**
	 * Get charge amount currency code.
	 * 
	 * @return String
	 */
	public String getChargeCcyCode();

	/**
	 * Get date of confirmation of charge.
	 * 
	 * @return Date
	 */
	public Date getChargeConfirmationDate();

	/**
	 * Get charge detail id.
	 * 
	 * @return long
	 */
	public long getChargeDetailID();

	/**
	 * Get type of the charge: general or specific.
	 * 
	 * @return String
	 */
	public String getChargeType();

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID();

	/**
	 * Get expiry date
	 * 
	 * @return Date
	 */
	public Date getExpiryDate();

	/**
	 * Get caveat folio
	 * 
	 * @return String
	 */

	public String getFolio();

	public String getHostCollateralId();

	/**
	 * Get if the Legal Enforceability .
	 * 
	 * @return String
	 */
	// Fields isLE & lEDate added by Pratheepa for CR235.
	public String getIsLE();

	/**
	 * Get if the Legal Enforceability by charge ranking.
	 * 
	 * @return String
	 */
	public String getIsLEByChargeRanking();

	/**
	 * Get if the Legal Enforceability by governing laws.
	 * 
	 * @return String
	 */
	public String getIsLEByGovernLaws();

	/**
	 * Get if the Legal Enforceability by jurisdiction.
	 * 
	 * @return String
	 */
	public String getIsLEByJurisdiction();

	/**
	 * Get caveat jilid
	 * 
	 * @return String
	 */

	public String getJilid();

	/**
	 * Get legal enforceability date .
	 * 
	 * @return Date
	 */
	public Date getLEDate();

	/**
	 * Get legal enforceability date by charge ranking.
	 * 
	 * @return Date
	 */
	public Date getLEDateByChargeRanking();

	/**
	 * Get legal enforceability date by governing laws.
	 * 
	 * @return Date
	 */
	public Date getLEDateByGovernLaws();

	/**
	 * Get legal enforceability date by jurisdiction.
	 * 
	 * @return Date
	 */
	public Date getLEDateByJurisdiction();

	/**
	 * Get date legally charged.
	 * 
	 * @return Date
	 */
	public Date getLegalChargeDate();

	/**
	 * Get limit maps of this charge.
	 * 
	 * @return long
	 */
	public ICollateralLimitMap[] getLimitMaps();

	/**
	 * Get lodged date
	 * 
	 * @return Date
	 */
	public Date getLodgedDate();

	/**
	 * Get nature of charge.
	 * 
	 * @return String
	 */
	public String getNatureOfCharge();

	/**
	 * Get caveat partyCharge
	 * 
	 * @return String
	 */

	public String getPartyCharge();

	/**
	 * Get presentation date
	 * 
	 * @return Date
	 */
	public Date getPresentationDate();

	/**
	 * Get presentation number
	 * 
	 * @return String
	 */
	public String getPresentationNo();

	/**
	 * Get prior charge amount.
	 * 
	 * @return Amount
	 */
	public Amount getPriorChargeAmount();

	/**
	 * Get prior charge amount currency code.
	 * 
	 * @return String
	 */
	public String getPriorChargeCcyCode();

	/**
	 * Get chargee of prior charge.
	 * 
	 * @return String
	 */
	public String getPriorChargeChargee();

	/**
	 * Get prior charge relationship with the bank: external, internal, or NA.
	 * 
	 * @return String
	 */
	public String getPriorChargeType();

	public String getRedemption();

	/**
	 * Get reference id between staging and actual data.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Get security ranking.
	 * 
	 * @return int
	 */
	public int getSecurityRank();

	/**
	 * Get solicitor name
	 * 
	 * @return String
	 */
	public String getSolicitorName();

	/**
	 * Get status of this charge.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set caveat reference no
	 * 
	 * @caveatReferenceNo of type String
	 */
	public void setCaveatReferenceNo(String caveatReferenceNo);

	/**
	 * Set caveat waived indicator
	 * 
	 * @caveatWaivedInd of type boolean
	 */
	public void setCaveatWaivedInd(Boolean caveatWaivedInd);

	/**
	 * Set charge amount.
	 * 
	 * @param chargeAmount is of type Amount
	 */
	public void setChargeAmount(Amount chargeAmount);

	/**
	 * Set charge amount currency code.
	 * 
	 * @param chargeCcyCode of type String
	 */
	public void setChargeCcyCode(String chargeCcyCode);

	/**
	 * Set date of confirmation of charge.
	 * 
	 * @param chargeConfirmationDate of type Date
	 */
	public void setChargeConfirmationDate(Date chargeConfirmationDate);

	/**
	 * Set charge detail id.
	 * 
	 * @param chargeDetailID of type long
	 */
	public void setChargeDetailID(long chargeDetailID);

	/**
	 * Set type of the charge: general or specific.
	 * 
	 * @param chargeType of type String
	 */
	public void setChargeType(String chargeType);

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID);

	/**
	 * Set expiry date
	 * 
	 * @expiryDate of type Date
	 */
	public void setExpiryDate(Date expiryDate);

	/**
	 * Set caveat folio
	 * 
	 * @caveat folio of type String
	 */

	public void setFolio(String folio);

	public void setHostCollateralId(String hostCollateralId);

	/**
	 * Set the legal enforceability by charge ranking.
	 * 
	 * @param isLEByChargeRanking is of type String
	 */
	public void setIsLE(String isLE);

	/**
	 * Set the legal enforceability by charge ranking.
	 * 
	 * @param isLEByChargeRanking is of type String
	 */
	public void setIsLEByChargeRanking(String isLEByChargeRanking);

	/**
	 * Set if the legal enforceability by governing laws.
	 * 
	 * @param isLEByGovernLaws is of type String
	 */
	public void setIsLEByGovernLaws(String isLEByGovernLaws);

	/**
	 * Set the legal enforceability by jurisdiction.
	 * 
	 * @param isLEByJurisdiction is of type String
	 */
	public void setIsLEByJurisdiction(String isLEByJurisdiction);

	/**
	 * Set caveat jilid
	 * 
	 * @caveat jilid of type String
	 */

	public void setJilid(String jilid);

	/**
	 * Set legal enforceability date .
	 * 
	 * @param lEDate is of type Date
	 */
	public void setLEDate(Date lEDate);

	/**
	 * Set legal enforceability date by charge ranking.
	 * 
	 * @param lEDateByChargeRanking is of type Date
	 */
	public void setLEDateByChargeRanking(Date lEDateByChargeRanking);

	/**
	 * Set legal enforceability date by governing laws.
	 * 
	 * @param lEDateByGovernLaws is of type Date
	 */
	public void setLEDateByGovernLaws(Date lEDateByGovernLaws);

	/**
	 * Get legal enforceability date by jurisdiction.
	 * 
	 * @param lEDateByJurisdiction is of type Date
	 */
	public void setLEDateByJurisdiction(Date lEDateByJurisdiction);

	/**
	 * Set date legally charged.
	 * 
	 * @param legalChargeDate of type Date
	 */
	public void setLegalChargeDate(Date legalChargeDate);

	/**
	 * Set limit maps of this charge.
	 * 
	 * @param limitMaps is of type ICollateralLimitMap[]
	 */
	public void setLimitMaps(ICollateralLimitMap[] limitMaps);

	/**
	 * Set lodged date
	 * 
	 * @lodgedDate of type Date
	 */
	public void setLodgedDate(Date lodgedDate);

	/**
	 * Set nature of charge.
	 * 
	 * @param natureOfCharge of type String
	 */
	public void setNatureOfCharge(String natureOfCharge);

	/**
	 * Set caveat partyCharge
	 * 
	 * @caveat partyCharge of type String
	 */

	public void setPartyCharge(String partyCharge);

	/**
	 * Get presentation date
	 * 
	 * @presentationDate of type Date
	 */
	public void setPresentationDate(Date presentationDate);

	/**
	 * Set presentation number
	 * 
	 * @presentationNo of type String
	 */
	public void setPresentationNo(String presentationNo);

	/**
	 * Set prior charge amount.
	 * 
	 * @param priorChargeAmount of type Amount
	 */
	public void setPriorChargeAmount(Amount priorChargeAmount);

	/**
	 * Set prior charge amount currency code.
	 * 
	 * @param priorChargeCcyCode of type String
	 */
	public void setPriorChargeCcyCode(String priorChargeCcyCode);

	/**
	 * Set chargee of prior charge.
	 * 
	 * @param priorChargeChargee of type String
	 */
	public void setPriorChargeChargee(String priorChargeChargee);

	/**
	 * Set prior charge relationship with the bank: external, internal, or NA.
	 * 
	 * @param priorChargeType of type String
	 */
	public void setPriorChargeType(String priorChargeType);

	public void setRedemption(String redemption);

	/**
	 * Set reference id between staging and actual data.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Set security ranking.
	 * 
	 * @param securityRank of type int
	 */
	public void setSecurityRank(int securityRank);

	/**
	 * Set solicitor name
	 * 
	 * @solicitorName of type String
	 */
	public void setSolicitorName(String solicitorName);

	/**
	 * Set status of this charge.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

}