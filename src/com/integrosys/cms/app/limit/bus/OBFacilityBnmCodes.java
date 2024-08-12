package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;

/**
 * An entity represents BNM codes for a facility.
 * @author Chong Jun Yong
 * 
 */
public class OBFacilityBnmCodes implements IFacilityBnmCodes, Serializable {

	private static final long serialVersionUID = -3169384171248668945L;

	private long facilityMasterId;

	private String industryCodeCategoryCode;

	private String industryCodeEntryCode;

	private String sectorCodeCategoryCode;

	private String sectorCodeEntryCode;

	private String stateCodeCategoryCode;

	private String stateCodeEntryCode;

	private String bumiOrNrccCodeCategoryCode;

	private String bumiOrNrccCodeEntryCode;

	private String smallScaleCodeCodeCategoryCode;

	private String smallScaleCodeCodeEntryCode;

	private String prescribedRateCodeCategoryCode;

	private String prescribedRateCodeEntryCode;

	private String relationshipCodeCategoryCode;

	private String relationshipCodeEntryCode;

	private String goodsFinancedCodeOneCategoryCode;

	private String goodsFinancedCodeOneEntryCode;

	private String goodsFinancedCodeTwoCategoryCode;

	private String goodsFinancedCodeTwoEntryCode;

	private String bnmTierSeqCategoryCode;

	private String bnmTierSeqEntryCode;

	private String exemptedCodeCategoryCode;

	private String exemptedCodeEntryCode;

	private Character exemptedCodeIndicator;

	private Long hostTierSequenceNumber;

	private String purposeCodeCategoryCode;

	private String purposeCodeEntryCode;

	private String baselSAFinalisedCategoryCode;

	private String baselSAFinalisedEntryCode;

	private String baselIRBCategoryCode;

	private String baselIRBEntryCode;
	
	private String baselSAConceptCategoryCode;

	private String baselSAConceptEntryCode;

	public String getBaselIRBCategoryCode() {
		return baselIRBCategoryCode;
	}

	public String getBaselIRBEntryCode() {
		return baselIRBEntryCode;
	}

	public String getBaselSAConceptCategoryCode() {
		return baselSAConceptCategoryCode;
	}

	public String getBaselSAConceptEntryCode() {
		return baselSAConceptEntryCode;
	}

	public String getBaselSAFinalisedCategoryCode() {
		return baselSAFinalisedCategoryCode;
	}

	public String getBaselSAFinalisedEntryCode() {
		return baselSAFinalisedEntryCode;
	}

	/**
	 * @return the bnmTierSeqCategoryCode
	 */
	public String getBnmTierSeqCategoryCode() {
		return bnmTierSeqCategoryCode;
	}

	/**
	 * @return the bnmTierSeqEntryCode
	 */
	public String getBnmTierSeqEntryCode() {
		return bnmTierSeqEntryCode;
	}

	/**
	 * @return the bumiOrNrccCodeCategoryCode
	 */
	public String getBumiOrNrccCodeCategoryCode() {
		return bumiOrNrccCodeCategoryCode;
	}

	/**
	 * @return the bumiOrNrccCodeEntryCode
	 */
	public String getBumiOrNrccCodeEntryCode() {
		return bumiOrNrccCodeEntryCode;
	}

	/**
	 * @return the exemptedCodeCategoryCode
	 */
	public String getExemptedCodeCategoryCode() {
		return exemptedCodeCategoryCode;
	}

	/**
	 * @return the exemptedCodeEntryCode
	 */
	public String getExemptedCodeEntryCode() {
		return exemptedCodeEntryCode;
	}

	/**
	 * @return the exemptedCodeIndicator
	 */
	public Character getExemptedCodeIndicator() {
		return exemptedCodeIndicator;
	}

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	/**
	 * @return the goodsFinancedCodeOneCategoryCode
	 */
	public String getGoodsFinancedCodeOneCategoryCode() {
		return goodsFinancedCodeOneCategoryCode;
	}

	/**
	 * @return the goodsFinancedCodeOneEntryCode
	 */
	public String getGoodsFinancedCodeOneEntryCode() {
		return goodsFinancedCodeOneEntryCode;
	}

	/**
	 * @return the goodsFinancedCodeTwoCategoryCode
	 */
	public String getGoodsFinancedCodeTwoCategoryCode() {
		return goodsFinancedCodeTwoCategoryCode;
	}

	/**
	 * @return the goodsFinancedCodeTwoEntryCode
	 */
	public String getGoodsFinancedCodeTwoEntryCode() {
		return goodsFinancedCodeTwoEntryCode;
	}

	/**
	 * @return the hostTierSequenceNumber
	 */
	public Long getHostTierSequenceNumber() {
		return hostTierSequenceNumber;
	}

	/**
	 * @return the industryCodeCategoryCode
	 */
	public String getIndustryCodeCategoryCode() {
		return industryCodeCategoryCode;
	}

	/**
	 * @return the industryCodeEntryCode
	 */
	public String getIndustryCodeEntryCode() {
		return industryCodeEntryCode;
	}

	/**
	 * @return the prescribedRateCodeCategoryCode
	 */
	public String getPrescribedRateCodeCategoryCode() {
		return prescribedRateCodeCategoryCode;
	}

	/**
	 * @return the prescribedRateCodeEntryCode
	 */
	public String getPrescribedRateCodeEntryCode() {
		return prescribedRateCodeEntryCode;
	}

	/**
	 * @return the purposeCodeCategoryCode
	 */
	public String getPurposeCodeCategoryCode() {
		return purposeCodeCategoryCode;
	}

	/**
	 * @return the purposeCodeEntryCode
	 */
	public String getPurposeCodeEntryCode() {
		return purposeCodeEntryCode;
	}

	/**
	 * @return the relationshipCodeCategoryCode
	 */
	public String getRelationshipCodeCategoryCode() {
		return relationshipCodeCategoryCode;
	}

	/**
	 * @return the relationshipCodeEntryCode
	 */
	public String getRelationshipCodeEntryCode() {
		return relationshipCodeEntryCode;
	}

	/**
	 * @return the sectorCodeCategoryCode
	 */
	public String getSectorCodeCategoryCode() {
		return sectorCodeCategoryCode;
	}

	/**
	 * @return the sectorCodeEntryCode
	 */
	public String getSectorCodeEntryCode() {
		return sectorCodeEntryCode;
	}

	/**
	 * @return the smallScaleCodeCodeCategoryCode
	 */
	public String getSmallScaleCodeCodeCategoryCode() {
		return smallScaleCodeCodeCategoryCode;
	}

	/**
	 * @return the smallScaleCodeCodeEntryCode
	 */
	public String getSmallScaleCodeCodeEntryCode() {
		return smallScaleCodeCodeEntryCode;
	}

	/**
	 * @return the stateCodeCategoryCode
	 */
	public String getStateCodeCategoryCode() {
		return stateCodeCategoryCode;
	}

	/**
	 * @return the stateCodeEntryCode
	 */
	public String getStateCodeEntryCode() {
		return stateCodeEntryCode;
	}

	public void setBaselIRBCategoryCode(String baselIRBCategoryCode) {
		this.baselIRBCategoryCode = baselIRBCategoryCode;
	}

	public void setBaselIRBEntryCode(String baselIRBEntryCode) {
		this.baselIRBEntryCode = baselIRBEntryCode;
	}

	public void setBaselSAConceptCategoryCode(String baselSAConceptCategoryCode) {
		this.baselSAConceptCategoryCode = baselSAConceptCategoryCode;
	}

	public void setBaselSAConceptEntryCode(String baselSAConceptEntryCode) {
		this.baselSAConceptEntryCode = baselSAConceptEntryCode;
	}

	public void setBaselSAFinalisedCategoryCode(String baselSAFinalisedCategoryCode) {
		this.baselSAFinalisedCategoryCode = baselSAFinalisedCategoryCode;
	}

	public void setBaselSAFinalisedEntryCode(String baselSAFinalisedEntryCode) {
		this.baselSAFinalisedEntryCode = baselSAFinalisedEntryCode;
	}

	/**
	 * @param bnmTierSeqCategoryCode the bnmTierSeqCategoryCode to set
	 */
	public void setBnmTierSeqCategoryCode(String bnmTierSeqCategoryCode) {
		this.bnmTierSeqCategoryCode = bnmTierSeqCategoryCode;
	}

	/**
	 * @param bnmTierSeqEntryCode the bnmTierSeqEntryCode to set
	 */
	public void setBnmTierSeqEntryCode(String bnmTierSeqEntryCode) {
		this.bnmTierSeqEntryCode = bnmTierSeqEntryCode;
	}

	/**
	 * @param bumiOrNrccCodeCategoryCode the bumiOrNrccCodeCategoryCode to set
	 */
	public void setBumiOrNrccCodeCategoryCode(String bumiOrNrccCodeCategoryCode) {
		this.bumiOrNrccCodeCategoryCode = bumiOrNrccCodeCategoryCode;
	}

	/**
	 * @param bumiOrNrccCodeEntryCode the bumiOrNrccCodeEntryCode to set
	 */
	public void setBumiOrNrccCodeEntryCode(String bumiOrNrccCodeEntryCode) {
		this.bumiOrNrccCodeEntryCode = bumiOrNrccCodeEntryCode;
	}

	/**
	 * @param exemptedCodeCategoryCode the exemptedCodeCategoryCode to set
	 */
	public void setExemptedCodeCategoryCode(String exemptedCodeCategoryCode) {
		this.exemptedCodeCategoryCode = exemptedCodeCategoryCode;
	}

	/**
	 * @param exemptedCodeEntryCode the exemptedCodeEntryCode to set
	 */
	public void setExemptedCodeEntryCode(String exemptedCodeEntryCode) {
		this.exemptedCodeEntryCode = exemptedCodeEntryCode;
	}

	/**
	 * @param exemptedCodeIndicator the exemptedCodeIndicator to set
	 */
	public void setExemptedCodeIndicator(Character exemptedCodeIndicator) {
		this.exemptedCodeIndicator = exemptedCodeIndicator;
	}

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	/**
	 * @param goodsFinancedCodeOneCategoryCode the
	 *        goodsFinancedCodeOneCategoryCode to set
	 */
	public void setGoodsFinancedCodeOneCategoryCode(String goodsFinancedCodeOneCategoryCode) {
		this.goodsFinancedCodeOneCategoryCode = goodsFinancedCodeOneCategoryCode;
	}

	/**
	 * @param goodsFinancedCodeOneEntryCode the goodsFinancedCodeOneEntryCode to
	 *        set
	 */
	public void setGoodsFinancedCodeOneEntryCode(String goodsFinancedCodeOneEntryCode) {
		this.goodsFinancedCodeOneEntryCode = goodsFinancedCodeOneEntryCode;
	}

	/**
	 * @param goodsFinancedCodeTwoCategoryCode the
	 *        goodsFinancedCodeTwoCategoryCode to set
	 */
	public void setGoodsFinancedCodeTwoCategoryCode(String goodsFinancedCodeTwoCategoryCode) {
		this.goodsFinancedCodeTwoCategoryCode = goodsFinancedCodeTwoCategoryCode;
	}

	/**
	 * @param goodsFinancedCodeTwoEntryCode the goodsFinancedCodeTwoEntryCode to
	 *        set
	 */
	public void setGoodsFinancedCodeTwoEntryCode(String goodsFinancedCodeTwoEntryCode) {
		this.goodsFinancedCodeTwoEntryCode = goodsFinancedCodeTwoEntryCode;
	}

	/**
	 * @param hostTierSequenceNumber the hostTierSequenceNumber to set
	 */
	public void setHostTierSequenceNumber(Long hostTierSequenceNumber) {
		this.hostTierSequenceNumber = hostTierSequenceNumber;
	}

	/**
	 * @param industryCodeCategoryCode the industryCodeCategoryCode to set
	 */
	public void setIndustryCodeCategoryCode(String industryCodeCategoryCode) {
		this.industryCodeCategoryCode = industryCodeCategoryCode;
	}

	/**
	 * @param industryCodeEntryCode the industryCodeEntryCode to set
	 */
	public void setIndustryCodeEntryCode(String industryCodeEntryCode) {
		this.industryCodeEntryCode = industryCodeEntryCode;
	}

	/**
	 * @param prescribedRateCodeCategoryCode the prescribedRateCodeCategoryCode
	 *        to set
	 */
	public void setPrescribedRateCodeCategoryCode(String prescribedRateCodeCategoryCode) {
		this.prescribedRateCodeCategoryCode = prescribedRateCodeCategoryCode;
	}

	/**
	 * @param prescribedRateCodeEntryCode the prescribedRateCodeEntryCode to set
	 */
	public void setPrescribedRateCodeEntryCode(String prescribedRateCodeEntryCode) {
		this.prescribedRateCodeEntryCode = prescribedRateCodeEntryCode;
	}

	/**
	 * @param purposeCodeCategoryCode the purposeCodeCategoryCode to set
	 */
	public void setPurposeCodeCategoryCode(String purposeCodeCategoryCode) {
		this.purposeCodeCategoryCode = purposeCodeCategoryCode;
	}

	/**
	 * @param purposeCodeEntryCode the purposeCodeEntryCode to set
	 */
	public void setPurposeCodeEntryCode(String purposeCodeEntryCode) {
		this.purposeCodeEntryCode = purposeCodeEntryCode;
	}

	/**
	 * @param relationshipCodeCategoryCode the relationshipCodeCategoryCode to
	 *        set
	 */
	public void setRelationshipCodeCategoryCode(String relationshipCodeCategoryCode) {
		this.relationshipCodeCategoryCode = relationshipCodeCategoryCode;
	}

	/**
	 * @param relationshipCodeEntryCode the relationshipCodeEntryCode to set
	 */
	public void setRelationshipCodeEntryCode(String relationshipCodeEntryCode) {
		this.relationshipCodeEntryCode = relationshipCodeEntryCode;
	}

	/**
	 * @param sectorCodeCategoryCode the sectorCodeCategoryCode to set
	 */
	public void setSectorCodeCategoryCode(String sectorCodeCategoryCode) {
		this.sectorCodeCategoryCode = sectorCodeCategoryCode;
	}

	/**
	 * @param sectorCodeEntryCode the sectorCodeEntryCode to set
	 */
	public void setSectorCodeEntryCode(String sectorCodeEntryCode) {
		this.sectorCodeEntryCode = sectorCodeEntryCode;
	}

	/**
	 * @param smallScaleCodeCodeCategoryCode the smallScaleCodeCodeCategoryCode
	 *        to set
	 */
	public void setSmallScaleCodeCodeCategoryCode(String smallScaleCodeCodeCategoryCode) {
		this.smallScaleCodeCodeCategoryCode = smallScaleCodeCodeCategoryCode;
	}

	/**
	 * @param smallScaleCodeCodeEntryCode the smallScaleCodeCodeEntryCode to set
	 */
	public void setSmallScaleCodeCodeEntryCode(String smallScaleCodeCodeEntryCode) {
		this.smallScaleCodeCodeEntryCode = smallScaleCodeCodeEntryCode;
	}

	/**
	 * @param stateCodeCategoryCode the stateCodeCategoryCode to set
	 */
	public void setStateCodeCategoryCode(String stateCodeCategoryCode) {
		this.stateCodeCategoryCode = stateCodeCategoryCode;
	}

	/**
	 * @param stateCodeEntryCode the stateCodeEntryCode to set
	 */
	public void setStateCodeEntryCode(String stateCodeEntryCode) {
		this.stateCodeEntryCode = stateCodeEntryCode;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBFacilityBnmCodes [");
		buf.append("baselIRBCategoryCode=");
		buf.append(baselIRBCategoryCode);
		buf.append(", baselIRBEntryCode=");
		buf.append(baselIRBEntryCode);
		buf.append(", baselSAFinalisedCategoryCode=");
		buf.append(baselSAFinalisedCategoryCode);
		buf.append(", baselSAFinalisedEntryCode=");
		buf.append(baselSAFinalisedEntryCode);
		buf.append(", baselSAConceptCategoryCode=");
		buf.append(baselSAConceptCategoryCode);
		buf.append(", baselSAConceptEntryCode=");
		buf.append(baselSAConceptEntryCode);
		buf.append(", bnmTierSeqCategoryCode=");
		buf.append(bnmTierSeqCategoryCode);
		buf.append(", bnmTierSeqEntryCode=");
		buf.append(bnmTierSeqEntryCode);
		buf.append(", bumiOrNrccCodeCategoryCode=");
		buf.append(bumiOrNrccCodeCategoryCode);
		buf.append(", bumiOrNrccCodeEntryCode=");
		buf.append(bumiOrNrccCodeEntryCode);
		buf.append(", exemptedCodeCategoryCode=");
		buf.append(exemptedCodeCategoryCode);
		buf.append(", exemptedCodeEntryCode=");
		buf.append(exemptedCodeEntryCode);
		buf.append(", exemptedCodeIndicator=");
		buf.append(exemptedCodeIndicator);
		buf.append(", goodsFinancedCodeOneCategoryCode=");
		buf.append(goodsFinancedCodeOneCategoryCode);
		buf.append(", goodsFinancedCodeOneEntryCode=");
		buf.append(goodsFinancedCodeOneEntryCode);
		buf.append(", goodsFinancedCodeTwoCategoryCode=");
		buf.append(goodsFinancedCodeTwoCategoryCode);
		buf.append(", goodsFinancedCodeTwoEntryCode=");
		buf.append(goodsFinancedCodeTwoEntryCode);
		buf.append(", industryCodeCategoryCode=");
		buf.append(industryCodeCategoryCode);
		buf.append(", industryCodeEntryCode=");
		buf.append(industryCodeEntryCode);
		buf.append(", prescribedRateCodeCategoryCode=");
		buf.append(prescribedRateCodeCategoryCode);
		buf.append(", prescribedRateCodeEntryCode=");
		buf.append(prescribedRateCodeEntryCode);
		buf.append(", purposeCodeCategoryCode=");
		buf.append(purposeCodeCategoryCode);
		buf.append(", purposeCodeEntryCode=");
		buf.append(purposeCodeEntryCode);
		buf.append(", relationshipCodeCategoryCode=");
		buf.append(relationshipCodeCategoryCode);
		buf.append(", relationshipCodeEntryCode=");
		buf.append(relationshipCodeEntryCode);
		buf.append(", sectorCodeCategoryCode=");
		buf.append(sectorCodeCategoryCode);
		buf.append(", sectorCodeEntryCode=");
		buf.append(sectorCodeEntryCode);
		buf.append(", smallScaleCodeCodeCategoryCode=");
		buf.append(smallScaleCodeCodeCategoryCode);
		buf.append(", smallScaleCodeCodeEntryCode=");
		buf.append(smallScaleCodeCodeEntryCode);
		buf.append(", stateCodeCategoryCode=");
		buf.append(stateCodeCategoryCode);
		buf.append(", stateCodeEntryCode=");
		buf.append(stateCodeEntryCode);
		buf.append("]");
		return buf.toString();
	}

}
