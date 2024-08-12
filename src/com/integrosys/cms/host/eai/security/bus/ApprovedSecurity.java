/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.host.eai.security.bus;

import java.util.Date;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Entity represent a approved security, contains the general information about
 * a <b>collateral</b>, for more details, see it's subclasses.
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @since 2004/05/14
 */
public class ApprovedSecurity implements java.io.Serializable {

	private static final long serialVersionUID = 152119239804125391L;

	private String LOSSecurityId;

	private long CMSSecurityId;

	private long sharedSecurityId = ICMSConstant.LONG_INVALID_VALUE;

	private String securityReferenceNote;

	private StandardCode sourceSecurityType;

	private StandardCode securityType;

	private StandardCode securitySubType;

	private String currency;

	private String originalCurrency;

	private Double forcedSaleValue;

	private SecurityLocation securityLocation;

	private String custodianType;

	private String custodian;

	private Date securityExpiryDate;

	private String exchangeControlObtainedFlag;

	private String legalEnforcebilityFlag;

	private Date legalEnforcebilityDate;

	private String remargin;

	private StandardCode riskMitigationCategory;

	private String externalSeniorLien;

	private String cMSStatus = ICMSConstant.STATE_ACTIVE;

	private long cMSVersionTime = ICMSConstant.LONG_INVALID_VALUE;

	private String sourceId;

	private String changeIndicator;

	private String updateStatusIndicator;

	private Vector securityInstrument;

	private String comments;

	private String sciSecuritySubType;

	private Double cmv;

	private Double fsv;

	private String cmvCurrency;

	private String fsvCurrency;

	private String valuationType;

	private String valuer;

	private Date lastRemarginDate;

	private Date nextRemarginDate;

	private String borrowerDependencyCol;

	private String refEntryCode;

	private String chargeInfoDrawAmountUsageIndicator;

	private String chargeInfoPledgeAmountUsageIndicator;

	private String collateralStatus;

	private Date createDate;

	private String toBeDischargedInd;

	private String linkToCgcGuarantee;

	/**
	 * Default Constructor.
	 */
	public ApprovedSecurity() {
		super();
	}

	public String getBorrowerDependencyCol() {
		return borrowerDependencyCol;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public String getChargeInfoDrawAmountUsageIndicator() {
		return chargeInfoDrawAmountUsageIndicator;
	}

	public String getChargeInfoPledgeAmountUsageIndicator() {
		return chargeInfoPledgeAmountUsageIndicator;
	}

	public long getCMSSecurityId() {
		return CMSSecurityId;
	}

	public String getCMSStatus() {
		return cMSStatus;
	}

	public long getCMSVersionTime() {
		return cMSVersionTime;
	}

	public Double getCmv() {
		return cmv;
	}

	public String getCmvCurrency() {
		return cmvCurrency;
	}

	public String getCollateralStatus() {
		return collateralStatus;
	}

	public String getComments() {
		return comments;
	}

	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * To return Original Currency , if current currency = null check for null
	 * String
	 * @return
	 */
	public String getCurrency() {
		if (StringUtils.isEmpty(currency)) {
			return originalCurrency;
		}
		else {
			return currency;
		}
	}

	public String getCustodian() {
		return custodian;
	}

	public String getCustodianType() {
		return custodianType;
	}

	public String getExchangeControlObtainedFlag() {
		return exchangeControlObtainedFlag;
	}

	public String getExternalSeniorLien() {
		return externalSeniorLien;
	}

	public Double getForcedSaleValue() {
		return forcedSaleValue;
	}

	public Double getFsv() {
		return fsv;
	}

	public String getFsvCurrency() {
		return fsvCurrency;
	}

	public String getHostSecurityId() {
		return LOSSecurityId;
	}

	public Date getJDOLegalEnforcebilityDate() {
		return this.legalEnforcebilityDate;
	}

	public Date getJDOSecurityExpiryDate() {
		return this.securityExpiryDate;
	}

	public Date getLastRemarginDate() {
		return lastRemarginDate;
	}

	public String getLegalEnforcebilityDate() {
		return MessageDate.getInstance().getString(legalEnforcebilityDate);
	}

	public String getLegalEnforcebilityFlag() {
		return legalEnforcebilityFlag;
	}

	public String getLinkToCgcGuarantee() {
		return linkToCgcGuarantee;
	}

	public String getLOSSecurityId() {
		return LOSSecurityId;
	}

	public Date getNextRemarginDate() {
		return nextRemarginDate;
	}

	public String getOriginalCurrency() {
		return originalCurrency;
	}

	public String getRefEntryCode() {
		return refEntryCode;
	}

	public String getRemargin() {
		return remargin;
	}

	public StandardCode getRiskMitigationCategory() {
		return riskMitigationCategory;
	}

	public String getSciSecuritySubType() {
		return sciSecuritySubType;
	}

	public String getSecurityExpiryDate() {
		return MessageDate.getInstance().getString(this.securityExpiryDate);
	}

	public Vector getSecurityInstrument() {
		return securityInstrument;
	}

	public SecurityLocation getSecurityLocation() {
		return securityLocation;
	}

	public String getSecurityReferenceNote() {
		return securityReferenceNote;
	}

	public StandardCode getSecuritySubType() {
		return securitySubType;
	}

	public StandardCode getSecurityType() {
		return securityType;
	}

	public long getSharedSecurityId() {
		return sharedSecurityId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public StandardCode getSourceSecurityType() {
		return sourceSecurityType;
	}

	public String getToBeDischargedInd() {
		return toBeDischargedInd;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public String getValuationType() {
		return valuationType;
	}

	public String getValuer() {
		return valuer;
	}

	public void setBorrowerDependencyCol(String borrowerDependencyCol) {
		this.borrowerDependencyCol = borrowerDependencyCol;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setChargeInfoDrawAmountUsageIndicator(String chargeInfoDrawAmountUsageIndicator) {
		this.chargeInfoDrawAmountUsageIndicator = chargeInfoDrawAmountUsageIndicator;
	}

	public void setChargeInfoPledgeAmountUsageIndicator(String chargeInfoPledgeAmountUsageIndicator) {
		this.chargeInfoPledgeAmountUsageIndicator = chargeInfoPledgeAmountUsageIndicator;
	}

	public void setCMSSecurityId(long securityId) {
		CMSSecurityId = securityId;
	}

	public void setCMSStatus(String cMSStatus) {
		this.cMSStatus = cMSStatus;
	}

	public void setCMSVersionTime(long cMSVersionTime) {
		this.cMSVersionTime = cMSVersionTime;
	}

	public void setCmv(Double cmv) {
		this.cmv = cmv;
	}

	public void setCmvCurrency(String cmvCurrency) {
		this.cmvCurrency = cmvCurrency;
	}

	public void setCollateralStatus(String collateralStatus) {
		this.collateralStatus = collateralStatus;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setCustodian(String custodian) {
		this.custodian = custodian;
	}

	public void setCustodianType(String custodianType) {
		this.custodianType = custodianType;
	}

	public void setExchangeControlObtainedFlag(String exchangeControlObtainedFlag) {
		this.exchangeControlObtainedFlag = exchangeControlObtainedFlag;
	}

	public void setExternalSeniorLien(String externalSeniorLien) {
		this.externalSeniorLien = externalSeniorLien;
	}

	public void setForcedSaleValue(Double forcedSaleValue) {
		this.forcedSaleValue = forcedSaleValue;
	}

	public void setFsv(Double fsv) {
		this.fsv = fsv;
	}

	public void setFsvCurrency(String fsvCurrency) {
		this.fsvCurrency = fsvCurrency;
	}

	public void setHostSecurityId(String hostSecurityId) {
		this.LOSSecurityId = hostSecurityId;
	}

	public void setJDOLegalEnforcebilityDate(Date legalEnforcebilityDate) {
		this.legalEnforcebilityDate = legalEnforcebilityDate;
	}

	public void setJDOSecurityExpiryDate(Date securityExpiryDate) {
		this.securityExpiryDate = securityExpiryDate;
	}

	public void setLastRemarginDate(Date lastRemarginDate) {
		this.lastRemarginDate = lastRemarginDate;
	}

	public void setLegalEnforcebilityDate(String legalEnforcebilityDate) {
		this.legalEnforcebilityDate = MessageDate.getInstance().getDate(legalEnforcebilityDate);
	}

	public void setLegalEnforcebilityFlag(String legalEnforcebilityFlag) {
		this.legalEnforcebilityFlag = legalEnforcebilityFlag;
	}

	public void setLinkToCgcGuarantee(String linkToCgcGuarantee) {
		this.linkToCgcGuarantee = linkToCgcGuarantee;
	}

	public void setLOSSecurityId(String securityId) {
		LOSSecurityId = securityId;
	}

	public void setNextRemarginDate(Date nextRemarginDate) {
		this.nextRemarginDate = nextRemarginDate;
	}

	public void setOldSecurityId(String oldSecurityId) {
		this.LOSSecurityId = oldSecurityId;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	public void setRefEntryCode(String refEntryCode) {
		this.refEntryCode = refEntryCode;
	}

	public void setRemargin(String remargin) {
		this.remargin = remargin;
	}

	public void setRiskMitigationCategory(StandardCode riskMitigationCategory) {
		this.riskMitigationCategory = riskMitigationCategory;
	}

	public void setSciSecuritySubType(String sciSecuritySubType) {
		this.sciSecuritySubType = sciSecuritySubType;
	}

	public void setSecurityExpiryDate(String securityExpiryDate) {
		this.securityExpiryDate = MessageDate.getInstance().getDate(securityExpiryDate);
	}

	public void setSecurityInstrument(Vector securityInstrument) {
		this.securityInstrument = securityInstrument;
	}

	public void setSecurityLocation(SecurityLocation securityLocation) {
		this.securityLocation = securityLocation;
	}

	public void setSecurityReferenceNote(String securityReferenceNote) {
		this.securityReferenceNote = securityReferenceNote;
	}

	public void setSecuritySubType(StandardCode securitySubType) {
		this.securitySubType = securitySubType;
	}

	public void setSecurityType(StandardCode securityType) {
		this.securityType = securityType;
	}

	public void setSharedSecurityId(long sharedSecurityId) {
		this.sharedSecurityId = sharedSecurityId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setSourceSecurityType(StandardCode sourceSecurityType) {
		this.sourceSecurityType = sourceSecurityType;
	}

	public void setToBeDischargedInd(String toBeDischargedInd) {
		this.toBeDischargedInd = toBeDischargedInd;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public void setValuationType(String valuationType) {
		this.valuationType = valuationType;
	}

	public void setValuer(String valuer) {
		this.valuer = valuer;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("ApprovedSecurity [");
		buf.append("LOSSecurityId=");
		buf.append(LOSSecurityId);
		buf.append(", securityReferenceNote=");
		buf.append(securityReferenceNote);
		buf.append(", collateralStatus=");
		buf.append(collateralStatus);
		buf.append(", currency=");
		buf.append(currency);
		buf.append(", sciSecuritySubType=");
		buf.append(sciSecuritySubType);
		buf.append(", securityExpiryDate=");
		buf.append(securityExpiryDate);
		buf.append(", securityLocation=");
		buf.append(securityLocation);
		buf.append(", securitySubType=");
		buf.append(securitySubType);
		buf.append(", securityType=");
		buf.append(securityType);
		buf.append(", sourceId=");
		buf.append(sourceId);
		buf.append(", sourceSecurityType=");
		buf.append(sourceSecurityType);
		buf.append("]");
		return buf.toString();
	}

}
