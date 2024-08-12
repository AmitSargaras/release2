/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/SecurityCharge.java,v 1.1 2003/12/05 13:01:31 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus;

import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Security charge class.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/12/05 13:01:31 $ Tag: $Name: $
 */
public class SecurityCharge implements java.io.Serializable {

	private long chargeDetailID;

	private long cMSSecurityId;

	private Integer securityRanking;

	private Double chargeAmount;

	private Date legallyChargedDate;

	private Date confirmChargedDate;

	private StandardCode chargeNature;

	private String PriorChargeChargee;

	private Double PriorChargeAmount;

	private String PriorChargeType;

	private String ChargeType;

	private String CaveatWaivedInd;

	private String CaveatRefNo;

	private Date expiryDate;

	private String PresentationNo;

	private Date PresentationDate;

	private Date DateLodged;

	private String SolicitorName;

	private String Redemption;

	private String changeIndicator;

	private String updateStatusIndicator;

	/**
	 * Default Constructor.
	 */
	public SecurityCharge() {
		super();
	}

	/**
	 * Get security charge currency code.
	 * 
	 * @return String
	 */

	public Integer getSecurityRanking() {
		return securityRanking;
	}

	public void setSecurityRanking(Integer securityRanking) {
		this.securityRanking = securityRanking;
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
	 * Set charge detail id.
	 * 
	 * @param chargeDetailID of type long
	 */
	public void setChargeDetailID(long chargeDetailID) {
		this.chargeDetailID = chargeDetailID;
	}

	public long getCMSSecurityId() {
		return cMSSecurityId;
	}

	public void setCMSSecurityId(long cMSSecurityId) {
		this.cMSSecurityId = cMSSecurityId;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public String getLegallyChargedDate() {
		return MessageDate.getInstance().getString(legallyChargedDate);
	}

	public void setLegallyChargedDate(String legallyChargedDate) {
		this.legallyChargedDate = MessageDate.getInstance().getDate(legallyChargedDate);
	}

	public Date getJDOLegallyChargedDate() {
		return this.legallyChargedDate;
	}

	public void setJDOLegallyChargedDate(Date legallyChargedDate) {
		this.legallyChargedDate = legallyChargedDate;
	}

	public String getConfirmChargedDate() {
		return MessageDate.getInstance().getString(confirmChargedDate);
	}

	public void setConfirmChargedDate(String confirmChargedDate) {
		this.confirmChargedDate = MessageDate.getInstance().getDate(confirmChargedDate);
	}

	public Date getJDOConfirmChargedDate() {
		return this.confirmChargedDate;
	}

	public void setJDOConfirmChargedDate(Date confirmChargedDate) {
		this.confirmChargedDate = confirmChargedDate;
	}

	public StandardCode getChargeNature() {
		return chargeNature;
	}

	public void setChargeNature(StandardCode chargeNature) {
		this.chargeNature = chargeNature;
	}

	public String getPriorChargeChargee() {
		return PriorChargeChargee;
	}

	public void setPriorChargeChargee(String priorChargeChargee) {
		PriorChargeChargee = priorChargeChargee;
	}

	public Double getPriorChargeAmount() {
		return PriorChargeAmount;
	}

	public void setPriorChargeAmount(Double priorChargeAmount) {
		PriorChargeAmount = priorChargeAmount;
	}

	public String getPriorChargeType() {
		return PriorChargeType;
	}

	public void setPriorChargeType(String priorChargeType) {
		PriorChargeType = priorChargeType;
	}

	public String getChargeType() {
		return ChargeType;
	}

	public void setChargeType(String chargeType) {
		ChargeType = chargeType;
	}

	public String getCaveatWaivedInd() {
		return CaveatWaivedInd;
	}

	public void setCaveatWaivedInd(String caveatWaivedInd) {
		CaveatWaivedInd = caveatWaivedInd;
	}

	public String getCaveatRefNo() {
		return CaveatRefNo;
	}

	public void setCaveatRefNo(String caveatRefNo) {
		CaveatRefNo = caveatRefNo;
	}

	public String getExpiryDate() {
		return MessageDate.getInstance().getString(expiryDate);
	}

	public void setExpiryDate(String expireDate) {
		this.expiryDate = MessageDate.getInstance().getDate(expireDate);
	}

	public Date getJDOExpiryDate() {
		return this.expiryDate;
	}

	public void setJDOExpiryDate(Date expireDate) {
		this.expiryDate = expireDate;
	}

	public String getPresentationNo() {
		return PresentationNo;
	}

	public void setPresentationNo(String presentationNo) {
		PresentationNo = presentationNo;
	}

	public String getPresentationDate() {
		return MessageDate.getInstance().getString(PresentationDate);
	}

	public void setPresentationDate(String presentationDate) {
		PresentationDate = MessageDate.getInstance().getDate(presentationDate);
	}

	public Date getJDOPresentationDate() {
		return this.PresentationDate;
	}

	public void setPresentationDate(Date presentationDate) {
		PresentationDate = presentationDate;
	}

	public String getDateLodged() {
		return MessageDate.getInstance().getString(DateLodged);
	}

	public void setDateLodged(String dateLodged) {
		DateLodged = MessageDate.getInstance().getDate(dateLodged);
	}

	public Date getJDODateLodged() {
		return this.DateLodged;
	}

	public void setJDODateLodged(Date dateLodged) {
		DateLodged = dateLodged;
	}

	public String getSolicitorName() {
		return SolicitorName;
	}

	public void setSolicitorName(String solicitorName) {
		SolicitorName = solicitorName;
	}

	public String getRedemption() {
		return Redemption;
	}

	public void setRedemption(String redemption) {
		Redemption = redemption;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}
}
