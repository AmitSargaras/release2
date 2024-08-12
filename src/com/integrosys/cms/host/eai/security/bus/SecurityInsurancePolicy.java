package com.integrosys.cms.host.eai.security.bus;

import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: 27-Mar-2007 Time: 21:24:36 To
 * change this template use File | Settings | File Templates.
 */
public class SecurityInsurancePolicy implements java.io.Serializable {

	private static final long serialVersionUID = -3695111654071818319L;

	private long insurancePolicyId;

	private long cMSSecurityId;

	private String coverNoteNum;

	private String bankCustArrangeIns;

	private String securityId;

	private String policyNumber;

	private StandardCode insurerName;

	private StandardCode insuranceType;

	private Long profileId;

	private String documentNumber;

	private String insurancePolicyCurrency;

	private Double insurableAmount;

	private Double insuredAmount;

	private String effectiveDate;

	private String expiryDate;

	private String insuredAddress;

	private String insuredAgainst;

	private String sourceId;

	private String changeIndicator;

	private String updateStatusIndicator;

	private String insuranceId;

	private String conversionCurrency;

	private String insIssueDate;

	private Double insuranceExchangeRate;

	private String insuranceCompanyName;

	private String debitingACNo;

	private String acType;

	private String nonScheme_Scheme;

	private String insuranceremium;

	private String autoDebit;

	private String takafulCommission;

	private Double newAmtInsured;

	private String endorsementDate;

	private StandardCode buildingOccupation;

	private StandardCode buildingType;

	private String natureOfWork;

	private StandardCode wall;

	private Integer numberOfStorey;

	private StandardCode extensionWalls;

	private StandardCode endorsementCode;

	private StandardCode policyCustodian;

	private String insuranceClaimDate;

	private StandardCode typeOfFloor;

	private StandardCode typeOfPerils1;

	private StandardCode typeOfPerils2;

	private StandardCode typeOfPerils3;

	private StandardCode typeOfPerils4;

	private StandardCode typeOfPerils5;

	private String remark1;

	private String remark2;

	private String remark3;

	private Double grossPremium;

	private StandardCode roof;

	private StandardCode extensionRoof;

	private Double nettPermByBorrower;

	private Double nettPermToInsCo;

	private Double commissionEarned;

	private Double stampDuty;

	private Double insPremium;

	private String schemeInd;

	private Long LOSInsurancePolicyId;

	private String status;

	public SecurityInsurancePolicy() {
		super();
	}

	public String getAcType() {
		return acType;
	}

	public String getAutoDebit() {
		return autoDebit;
	}

	public String getBankCustArrangeIns() {
		return bankCustArrangeIns;
	}

	public StandardCode getBuildingOccupation() {
		return buildingOccupation;
	}

	public StandardCode getBuildingType() {
		return buildingType;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public long getCMSSecurityId() {
		return cMSSecurityId;
	}

	public Double getCommissionEarned() {
		return commissionEarned;
	}

	public String getConversionCurrency() {
		return conversionCurrency;
	}

	public String getCoverNoteNum() {
		return coverNoteNum;
	}

	public String getDebitingACNo() {
		return debitingACNo;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public StandardCode getEndorsementCode() {
		return endorsementCode;
	}

	public String getEndorsementDate() {
		return endorsementDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public StandardCode getExtensionRoof() {
		return extensionRoof;
	}

	public StandardCode getExtensionWalls() {
		return extensionWalls;
	}

	public Double getGrossPremium() {
		return grossPremium;
	}

	public String getInsIssueDate() {
		return insIssueDate;
	}

	public Double getInsPremium() {
		return insPremium;
	}

	public Double getInsurableAmount() {
		return insurableAmount;
	}

	public String getInsuranceClaimDate() {
		return insuranceClaimDate;
	}

	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}

	public Double getInsuranceExchangeRate() {
		return insuranceExchangeRate;
	}

	public String getInsuranceId() {
		return insuranceId;
	}

	public String getInsurancePolicyCurrency() {
		return insurancePolicyCurrency;
	}

	public long getInsurancePolicyId() {
		return insurancePolicyId;
	}

	public String getInsuranceremium() {
		return insuranceremium;
	}

	public StandardCode getInsuranceType() {
		return insuranceType;
	}

	public String getInsuredAddress() {
		return insuredAddress;
	}

	public String getInsuredAgainst() {
		return insuredAgainst;
	}

	public Double getInsuredAmount() {
		return insuredAmount;
	}

	public StandardCode getInsurerName() {
		return insurerName;
	}

	/** JDO For Dates **/

	public Date getJDOEffectiveDate() {
		return MessageDate.getInstance().getDate(effectiveDate);
	}

	public Date getJDOEndorsementDate() {
		return MessageDate.getInstance().getDate(endorsementDate);
	}

	public Date getJDOExpiryDate() {
		return MessageDate.getInstance().getDate(expiryDate);
	}

	public Date getJDOInsIssueDate() {
		return MessageDate.getInstance().getDate(insIssueDate);
	}

	public Date getJDOInsuranceClaimDate() {
		return MessageDate.getInstance().getDate(insuranceClaimDate);
	}

	public Double getJDOTakafulCommission() {
		if (takafulCommission != null)
			return new Double(takafulCommission);
		else
			return new Double(0d);
	}

	public Long getLOSInsurancePolicyId() {
		return LOSInsurancePolicyId;
	}

	public String getNatureOfWork() {
		return natureOfWork;
	}

	public Double getNettPermByBorrower() {
		return nettPermByBorrower;
	}

	public Double getNettPermToInsCo() {
		return nettPermToInsCo;
	}

	public Double getNewAmtInsured() {
		return newAmtInsured;
	}

	public String getNonScheme_Scheme() {
		return nonScheme_Scheme;
	}

	public Integer getNumberOfStorey() {
		return numberOfStorey;
	}

	public StandardCode getPolicyCustodian() {
		return policyCustodian;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public Long getProfileId() {
		return profileId;
	}

	public String getRemark1() {
		return remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public String getRemark3() {
		return remark3;
	}

	public StandardCode getRoof() {
		return roof;
	}

	public String getSchemeInd() {
		return schemeInd;
	}

	public String getSecurityId() {
		return securityId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public Double getStampDuty() {
		return stampDuty;
	}

	public String getStatus() {
		return status;
	}

	public String getTakafulCommission() {
		return takafulCommission;
	}

	public StandardCode getTypeOfFloor() {
		return typeOfFloor;
	}

	public StandardCode getTypeOfPerils1() {
		return typeOfPerils1;
	}

	public StandardCode getTypeOfPerils2() {
		return typeOfPerils2;
	}

	public StandardCode getTypeOfPerils3() {
		return typeOfPerils3;
	}

	public StandardCode getTypeOfPerils4() {
		return typeOfPerils4;
	}

	public StandardCode getTypeOfPerils5() {
		return typeOfPerils5;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public StandardCode getWall() {
		return wall;
	}

	public void setAcType(String acType) {
		this.acType = acType;
	}

	public void setAutoDebit(String autoDebit) {
		this.autoDebit = autoDebit;
	}

	public void setBankCustArrangeIns(String bankCustArrangeIns) {
		this.bankCustArrangeIns = bankCustArrangeIns;
	}

	public void setBuildingOccupation(StandardCode buildingOccupation) {
		this.buildingOccupation = buildingOccupation;
	}

	public void setBuildingType(StandardCode buildingType) {
		this.buildingType = buildingType;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCMSSecurityId(long cMSSecurityId) {
		this.cMSSecurityId = cMSSecurityId;
	}

	public void setCommissionEarned(Double commissionEarned) {
		this.commissionEarned = commissionEarned;
	}

	public void setConversionCurrency(String conversionCurrency) {
		this.conversionCurrency = conversionCurrency;
	}

	public void setCoverNoteNum(String coverNoteNum) {
		this.coverNoteNum = coverNoteNum;
	}

	public void setDebitingACNo(String debitingACNo) {
		this.debitingACNo = debitingACNo;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setEndorsementCode(StandardCode endorsementCode) {
		this.endorsementCode = endorsementCode;
	}

	public void setEndorsementDate(String endorsementDate) {
		this.endorsementDate = endorsementDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setExtensionRoof(StandardCode extensionRoof) {
		this.extensionRoof = extensionRoof;
	}

	public void setExtensionWalls(StandardCode extensionWalls) {
		this.extensionWalls = extensionWalls;
	}

	public void setGrossPremium(Double grossPremium) {
		this.grossPremium = grossPremium;
	}

	public void setInsIssueDate(String insIssueDate) {
		this.insIssueDate = insIssueDate;
	}

	public void setInsPremium(Double insPremium) {
		this.insPremium = insPremium;
	}

	public void setInsurableAmount(Double insurableAmount) {
		this.insurableAmount = insurableAmount;
	}

	public void setInsuranceClaimDate(String insuranceClaimDate) {
		this.insuranceClaimDate = insuranceClaimDate;
	}

	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}

	public void setInsuranceExchangeRate(Double insuranceExchangeRate) {
		this.insuranceExchangeRate = insuranceExchangeRate;
	}

	public void setInsuranceId(String insuranceId) {
		this.insuranceId = insuranceId;
	}

	public void setInsurancePolicyCurrency(String insurancePolicyCurrency) {
		this.insurancePolicyCurrency = insurancePolicyCurrency;
	}

	public void setInsurancePolicyId(long insurancePolicyId) {
		this.insurancePolicyId = insurancePolicyId;
	}

	public void setInsuranceremium(String insuranceremium) {
		this.insuranceremium = insuranceremium;
	}

	public void setInsuranceType(StandardCode insuranceType) {
		this.insuranceType = insuranceType;
	}

	public void setInsuredAddress(String insuredAddress) {
		this.insuredAddress = insuredAddress;
	}

	public void setInsuredAgainst(String insuredAgainst) {
		this.insuredAgainst = insuredAgainst;
	}

	public void setInsuredAmount(Double insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public void setInsurerName(StandardCode insurerName) {
		this.insurerName = insurerName;
	}

	public void setJDOEffectiveDate(Date effectiveDate) {
		this.effectiveDate = MessageDate.getInstance().getString(effectiveDate);
	}

	public void setJDOEndorsementDate(Date endorsementDate) {
		this.endorsementDate = MessageDate.getInstance().getString(endorsementDate);
	}

	public void setJDOExpiryDate(Date expiryDate) {
		this.expiryDate = MessageDate.getInstance().getString(expiryDate);
	}

	public void setJDOInsIssueDate(Date insIssueDate) {
		this.insIssueDate = MessageDate.getInstance().getString(insIssueDate);
	}

	public void setJDOInsuranceClaimDate(Date insuranceClaimDate) {
		this.insuranceClaimDate = MessageDate.getInstance().getString(insuranceClaimDate);
	}

	public void setJDOTakafulCommission(Double takafulCommission) {
		if (takafulCommission != null) {
			this.takafulCommission = String.valueOf(takafulCommission);
		}
	}

	public void setLOSInsurancePolicyId(Long LOSinsurancePolicyId) {
		this.LOSInsurancePolicyId = LOSinsurancePolicyId;
	}

	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}

	public void setNettPermByBorrower(Double nettPermByBorrower) {
		this.nettPermByBorrower = nettPermByBorrower;
	}

	public void setNettPermToInsCo(Double nettPermToInsCo) {
		this.nettPermToInsCo = nettPermToInsCo;
	}

	public void setNewAmtInsured(Double newAmtInsured) {
		this.newAmtInsured = newAmtInsured;
	}

	public void setNonScheme_Scheme(String nonScheme_Scheme) {
		this.nonScheme_Scheme = nonScheme_Scheme;
	}

	public void setNumberOfStorey(Integer numberOfStorey) {
		this.numberOfStorey = numberOfStorey;
	}

	public void setPolicyCustodian(StandardCode policyCustodian) {
		this.policyCustodian = policyCustodian;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}

	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}

	public void setRoof(StandardCode roof) {
		this.roof = roof;
	}

	public void setSchemeInd(String schemeInd) {
		this.schemeInd = schemeInd;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public void setStampDuty(Double stampDuty) {
		this.stampDuty = stampDuty;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTakafulCommission(String takafulCommission) {
		this.takafulCommission = takafulCommission;
	}

	public void setTypeOfFloor(StandardCode typeOfFloor) {
		this.typeOfFloor = typeOfFloor;
	}

	public void setTypeOfPerils1(StandardCode typeOfPerils1) {
		this.typeOfPerils1 = typeOfPerils1;
	}

	public void setTypeOfPerils2(StandardCode typeOfPerils2) {
		this.typeOfPerils2 = typeOfPerils2;
	}

	public void setTypeOfPerils3(StandardCode typeOfPerils3) {
		this.typeOfPerils3 = typeOfPerils3;
	}

	public void setTypeOfPerils4(StandardCode typeOfPerils4) {
		this.typeOfPerils4 = typeOfPerils4;
	}

	public void setTypeOfPerils5(StandardCode typeOfPerils5) {
		this.typeOfPerils5 = typeOfPerils5;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public void setWall(StandardCode wall) {
		this.wall = wall;
	}

	private String lastUpdatedBy;
	private String lastApproveBy;
	private Date lastUpdatedOn;
	private Date lastApproveOn;
	
	 //Uma Khot::Insurance Deferral maintainance
	private String insuranceStatus;
	private Date originalTargetDate;
	private Date nextDueDate;
	private Date dateDeferred;
	private String creditApprover;
	private Date waivedDate;

	public String getInsuranceStatus() {
		return insuranceStatus;
	}

	public void setInsuranceStatus(String insuranceStatus) {
		this.insuranceStatus = insuranceStatus;
	}

	public Date getOriginalTargetDate() {
		return originalTargetDate;
	}

	public void setOriginalTargetDate(Date originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}

	public Date getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public Date getDateDeferred() {
		return dateDeferred;
	}

	public void setDateDeferred(Date dateDeferred) {
		this.dateDeferred = dateDeferred;
	}

	public String getCreditApprover() {
		return creditApprover;
	}

	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}

	public Date getWaivedDate() {
		return waivedDate;
	}

	public void setWaivedDate(Date waivedDate) {
		this.waivedDate = waivedDate;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getLastApproveBy() {
		return lastApproveBy;
	}

	public void setLastApproveBy(String lastApproveBy) {
		this.lastApproveBy = lastApproveBy;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Date getLastApproveOn() {
		return lastApproveOn;
	}

	public void setLastApproveOn(Date lastApproveOn) {
		this.lastApproveOn = lastApproveOn;
	}
	
}
