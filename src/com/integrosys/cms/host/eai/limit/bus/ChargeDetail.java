/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ChargeDetail
 *
 * Created on 11:53:46 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.host.eai.limit.bus;

import java.util.Arrays;
import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * An entity represents a charge detail, pertaining to a collateral and multiple
 * limits/facilities.
 * 
 * @author Eric
 * @author Chong Jun Yong
 * @since Apr 23, 2007
 */
public class ChargeDetail implements java.io.Serializable {

	private static final long serialVersionUID = 2555507836715392179L;

	// internal key
	private long cmsRefId;

	// los security id
	private String securityId;

	private long cmsSecurityId;

	// internal key - cms charge detail id
	private long chargeDetailId;

	// internal key - LOS LimitID
	private String[] limitId;

	private Long[] CMSLimitId;

	private String chargeType;

	private Double chargeAmount;

	private Integer securityRanking;

	private Date legallyChargedDate;

	private Date confirmChargedDate;

	private String priorChargeChargee;

	private String priorChargeType;

	private Double priorChargeAmount;

	private String updateStatusIndicator;

	private String changeIndicator;

	private String chargeCurrency;

	private StandardCode chargeNature;

	private String caveatWaivedInd;

	private String caveatRefNo;

	private Date expiryDate;

	private String presentationNo;

	private Date presentationDate;

	private Date dateLodged;

	private String solicitorName;

	private String priorChargeCurrency;

	private String partyCharge;
	
	private String chargePendingRedemption;
	
	private String status;

	public String getCaveatRefNo() {
		return caveatRefNo;
	}

	public String getCaveatWaivedInd() {
		return caveatWaivedInd;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public Double getChargeAmount() {
		return chargeAmount;
	}

	public String getChargeCurrency() {
		return this.chargeCurrency;
	}

	public long getChargeDetailId() {
		return chargeDetailId;
	}

	public StandardCode getChargeNature() {
		return chargeNature;
	}

	public String getChargePendingRedemption() {
		return this.chargePendingRedemption;
	}

	public String getChargeType() {
		return chargeType;
	}

	public Long[] getCMSLimitId() {
		return CMSLimitId;
	}

	public long getCmsRefId() {
		return cmsRefId;
	}

	public long getCmsSecurityId() {
		return cmsSecurityId;
	}

	public String getConfirmChargedDate() {
		return MessageDate.getInstance().getString(confirmChargedDate);
	}

	public String getDateLodged() {
		return MessageDate.getInstance().getString(dateLodged);
	}

	public String getExpiryDate() {
		return MessageDate.getInstance().getString(expiryDate);
	}

	public Date getJDOConfirmChargedDate() {
		return confirmChargedDate;
	}

	public Date getJDODateLodged() {
		return dateLodged;
	}

	public Date getJDOExpiryDate() {
		return expiryDate;
	}

	public Date getJDOLegallyChargedDate() {
		return legallyChargedDate;
	}

	public Date getJDOPresentationDate() {
		return presentationDate;
	}

	public String getLegallyChargedDate() {
		return MessageDate.getInstance().getString(legallyChargedDate);
	}

	public String[] getLimitId() {
		return limitId;
	}

	public String getPartyCharge() {
		return partyCharge;
	}

	public String getPresentationDate() {
		return MessageDate.getInstance().getString(presentationDate);
	}

	public String getPresentationNo() {
		return presentationNo;
	}

	public Double getPriorChargeAmount() {
		return priorChargeAmount;
	}

	public String getPriorChargeChargee() {
		return priorChargeChargee;
	}

	public String getPriorChargeCurrency() {
		return priorChargeCurrency;
	}

	public String getPriorChargeType() {
		return priorChargeType;
	}

	public String getSecurityId() {
		return securityId;
	}

	public Integer getSecurityRanking() {
		return securityRanking;
	}

	public String getSolicitorName() {
		return solicitorName;
	}

	public String getStatus() {
		return status;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setCaveatRefNo(String caveatRefNo) {
		this.caveatRefNo = caveatRefNo;
	}

	public void setCaveatWaivedInd(String caveatWaivedInd) {
		this.caveatWaivedInd = caveatWaivedInd;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

	public void setChargeCurrency(String chargeCurrency) {
		this.chargeCurrency = chargeCurrency;
	}

	public void setChargeDetailId(long chargeDetailId) {
		this.chargeDetailId = chargeDetailId;
	}

	public void setChargeNature(StandardCode chargeNature) {
		this.chargeNature = chargeNature;
	}

	public void setChargePendingRedemption(String chargePendingRedemption) {
		this.chargePendingRedemption = chargePendingRedemption;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public void setCMSLimitId(Long[] limitId) {
		CMSLimitId = limitId;
	}

	public void setCmsRefId(long cmsRefId) {
		this.cmsRefId = cmsRefId;
	}

	public void setCmsSecurityId(long cmsSecurityId) {
		this.cmsSecurityId = cmsSecurityId;
	}

	public void setConfirmChargedDate(String confirmChargedDate) {
		this.confirmChargedDate = MessageDate.getInstance().getDate(confirmChargedDate);
	}

	public void setDateLodged(String dateLodged) {
		this.dateLodged = MessageDate.getInstance().getDate(dateLodged);
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = MessageDate.getInstance().getDate(expiryDate);
	}

	public void setJDOConfirmChargedDate(Date jDOConfirmChargedDate) {
		this.confirmChargedDate = jDOConfirmChargedDate;
	}

	public void setJDODateLodged(Date jDODateLodged) {
		this.dateLodged = jDODateLodged;
	}

	public void setJDOExpiryDate(Date jDOExpiryDate) {
		this.expiryDate = jDOExpiryDate;
	}

	public void setJDOLegallyChargedDate(Date jDOLegallyChargedDate) {
		this.legallyChargedDate = jDOLegallyChargedDate;
	}

	public void setJDOPresentationDate(Date jDOPresentationDate) {
		this.presentationDate = jDOPresentationDate;
	}

	public void setLegallyChargedDate(String legallyChargedDate) {
		this.legallyChargedDate = MessageDate.getInstance().getDate(legallyChargedDate);
	}

	public void setLimitId(String[] limitId) {
		this.limitId = limitId;
	}

	public void setPartyCharge(String partyCharge) {
		this.partyCharge = partyCharge;
	}

	public void setPresentationDate(String presentationDate) {
		this.presentationDate = MessageDate.getInstance().getDate(presentationDate);
	}

	public void setPresentationNo(String presentationNo) {
		this.presentationNo = presentationNo;
	}

	public void setPriorChargeAmount(Double priorChargeAmount) {
		this.priorChargeAmount = priorChargeAmount;
	}

	public void setPriorChargeChargee(String priorChargeChargee) {
		this.priorChargeChargee = priorChargeChargee;
	}

	public void setPriorChargeCurrency(String priorChargeCurrency) {
		this.priorChargeCurrency = priorChargeCurrency;
	}

	public void setPriorChargeType(String priorChargeType) {
		this.priorChargeType = priorChargeType;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public void setSecurityRanking(Integer securityRanking) {
		this.securityRanking = securityRanking;
	}

	public void setSolicitorName(String solicitorName) {
		this.solicitorName = solicitorName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("ChargeDetail [");
		buf.append("securityId=");
		buf.append(securityId);
		buf.append(", cmsSecurityId=");
		buf.append(cmsSecurityId);
		buf.append(", CMSLimitId=");
		buf.append(CMSLimitId != null ? Arrays.asList(CMSLimitId) : null);
		buf.append(", limitId=");
		buf.append(limitId != null ? Arrays.asList(limitId) : null);
		buf.append(", chargeCurrency=");
		buf.append(chargeCurrency);
		buf.append(", chargeAmount=");
		buf.append(chargeAmount);
		buf.append(", chargeNature=");
		buf.append(chargeNature);
		buf.append(", chargeType=");
		buf.append(chargeType);
		buf.append(", legallyChargedDate=");
		buf.append(legallyChargedDate);
		buf.append(", partyCharge=");
		buf.append(partyCharge);
		buf.append(", securityRanking=");
		buf.append(securityRanking);
		buf.append("]");
		return buf.toString();
	}

}