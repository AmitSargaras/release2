package com.integrosys.cms.host.eai.limit.bus;

import java.io.Serializable;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * An entity represents BNM codes for a facility.
 * @author Thurein
 * @author Chong Jun Yong
 * @since v1.1
 */
public class FacilityBNM implements Serializable {

	private static final long serialVersionUID = 3493780522414944299L;

	private long facilityMasterId;

	private StandardCode BNMIndustryCode;

	private StandardCode BNMSectorCode;

	private StandardCode BNMStateCode;

	private StandardCode BNMBumiNRCCCode;

	private StandardCode BNMSmallScaleCode;

	private StandardCode BNMPrescribedRateCode;

	private StandardCode BNMRelationshipCode;

	private StandardCode BNMExemptCode;

	private StandardCode BNMPurposeCode;

	private StandardCode BNMGoodFinancedCd1;

	private StandardCode BNMGoodFinancedCd2;

	private StandardCode baselSAFinalised;

	private StandardCode baselIRB;

	private StandardCode baselSAConcept;

	private String BNMExempt;

	private String updateStatusIndicator;

	private String changeIndicator;

	public StandardCode getBaselIRB() {
		return baselIRB;
	}

	public StandardCode getBaselSAConcept() {
		return baselSAConcept;
	}

	public StandardCode getBaselSAFinalised() {
		return baselSAFinalised;
	}

	public StandardCode getBNMBumiNRCCCode() {
		return BNMBumiNRCCCode;
	}

	public String getBNMExempt() {
		return BNMExempt;
	}

	public StandardCode getBNMExemptCode() {
		return BNMExemptCode;
	}

	public StandardCode getBNMGoodFinancedCd1() {
		return BNMGoodFinancedCd1;
	}

	public StandardCode getBNMGoodFinancedCd2() {
		return BNMGoodFinancedCd2;
	}

	public StandardCode getBNMIndustryCode() {
		return BNMIndustryCode;
	}

	public StandardCode getBNMPrescribedRateCode() {
		return BNMPrescribedRateCode;
	}

	public StandardCode getBNMPurposeCode() {
		return BNMPurposeCode;
	}

	public StandardCode getBNMRelationshipCode() {
		return BNMRelationshipCode;
	}

	public StandardCode getBNMSectorCode() {
		return BNMSectorCode;
	}

	public StandardCode getBNMSmallScaleCode() {
		return BNMSmallScaleCode;
	}

	public StandardCode getBNMStateCode() {
		return BNMStateCode;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setBaselIRB(StandardCode baselIRB) {
		this.baselIRB = baselIRB;
	}

	public void setBaselSAConcept(StandardCode baselSAConcept) {
		this.baselSAConcept = baselSAConcept;
	}

	public void setBaselSAFinalised(StandardCode baselSAFinalised) {
		this.baselSAFinalised = baselSAFinalised;
	}

	public void setBNMBumiNRCCCode(StandardCode bumiNRCCCode) {
		BNMBumiNRCCCode = bumiNRCCCode;
	}

	public void setBNMExempt(String exempt) {
		BNMExempt = exempt;
	}

	public void setBNMExemptCode(StandardCode exemptCode) {
		BNMExemptCode = exemptCode;
	}

	public void setBNMGoodFinancedCd1(StandardCode goodFinancedCd1) {
		BNMGoodFinancedCd1 = goodFinancedCd1;
	}

	public void setBNMGoodFinancedCd2(StandardCode goodFinancedCd2) {
		BNMGoodFinancedCd2 = goodFinancedCd2;
	}

	public void setBNMIndustryCode(StandardCode industryCode) {
		BNMIndustryCode = industryCode;
	}

	public void setBNMPrescribedRateCode(StandardCode prescribedRateCode) {
		BNMPrescribedRateCode = prescribedRateCode;
	}

	public void setBNMPurposeCode(StandardCode purposeCode) {
		BNMPurposeCode = purposeCode;
	}

	public void setBNMRelationshipCode(StandardCode relationshipCode) {
		BNMRelationshipCode = relationshipCode;
	}

	public void setBNMSectorCode(StandardCode sectorCode) {
		BNMSectorCode = sectorCode;
	}

	public void setBNMSmallScaleCode(StandardCode smallScaleCode) {
		BNMSmallScaleCode = smallScaleCode;
	}

	public void setBNMStateCode(StandardCode stateCode) {
		BNMStateCode = stateCode;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("FacilityBNM [");
		buf.append("BNMBumiNRCCCode=");
		buf.append(BNMBumiNRCCCode);
		buf.append(", BNMExempt=");
		buf.append(BNMExempt);
		buf.append(", BNMExemptCode=");
		buf.append(BNMExemptCode);
		buf.append(", BNMGoodFinancedCd1=");
		buf.append(BNMGoodFinancedCd1);
		buf.append(", BNMGoodFinancedCd2=");
		buf.append(BNMGoodFinancedCd2);
		buf.append(", BNMIndustryCode=");
		buf.append(BNMIndustryCode);
		buf.append(", BNMPrescribedRateCode=");
		buf.append(BNMPrescribedRateCode);
		buf.append(", BNMPurposeCode=");
		buf.append(BNMPurposeCode);
		buf.append(", BNMRelationshipCode=");
		buf.append(BNMRelationshipCode);
		buf.append(", BNMSectorCode=");
		buf.append(BNMSectorCode);
		buf.append(", BNMSmallScaleCode=");
		buf.append(BNMSmallScaleCode);
		buf.append(", BNMStateCode=");
		buf.append(BNMStateCode);
		buf.append(", baselIRB=");
		buf.append(baselIRB);
		buf.append(", baselSAFinalised=");
		buf.append(baselSAFinalised);
		buf.append(", baselSAConcept=");
		buf.append(baselSAConcept);
		buf.append("]");
		return buf.toString();
	}

}
