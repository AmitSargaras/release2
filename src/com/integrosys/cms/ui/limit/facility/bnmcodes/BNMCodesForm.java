package com.integrosys.cms.ui.limit.facility.bnmcodes;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainForm;

public class BNMCodesForm extends FacilityMainForm {

	private String industryCodeEntryCode;// BNM Industry Code

	private String sectorCodeEntryCode;// BNM Sector Code

	private String stateCodeEntryCode;// BNM State Code

	private String bumiOrNrccCodeEntryCode;// BNM Bumi/NRCC Code

	private String smallScaleCodeCodeEntryCode;// BNM small scale code

	private String prescribedRateCodeEntryCode;// BNM Prescribed Rate Code

	private String relationshipCodeEntryCode;// BNM Relationship Code

	private String goodsFinancedCodeOneEntryCode;// BNM Goods Financed Cd1

	private String goodsFinancedCodeTwoEntryCode;// BNM Goods Financed Cd2

	private String exemptedCodeEntryCode;// BNM Exempted Code

	private String hostTierSequenceNumber;// BNM Tier Sequence No

	private String purposeCodeEntryCode;// BNM Purpose Code

	private String exemptedCodeIndicator;// Exmpted Code

	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.bnmcodes.BNMCodesMapper";

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	/**
	 * @return the industryCodeEntryCode
	 */
	public String getIndustryCodeEntryCode() {
		return industryCodeEntryCode;
	}

	/**
	 * @param industryCodeEntryCode the industryCodeEntryCode to set
	 */
	public void setIndustryCodeEntryCode(String industryCodeEntryCode) {
		this.industryCodeEntryCode = industryCodeEntryCode;
	}

	/**
	 * @return the sectorCodeEntryCode
	 */
	public String getSectorCodeEntryCode() {
		return sectorCodeEntryCode;
	}

	/**
	 * @param sectorCodeEntryCode the sectorCodeEntryCode to set
	 */
	public void setSectorCodeEntryCode(String sectorCodeEntryCode) {
		this.sectorCodeEntryCode = sectorCodeEntryCode;
	}

	/**
	 * @return the stateCodeEntryCode
	 */
	public String getStateCodeEntryCode() {
		return stateCodeEntryCode;
	}

	/**
	 * @param stateCodeEntryCode the stateCodeEntryCode to set
	 */
	public void setStateCodeEntryCode(String stateCodeEntryCode) {
		this.stateCodeEntryCode = stateCodeEntryCode;
	}

	/**
	 * @return the bumiOrNrccCodeEntryCode
	 */
	public String getBumiOrNrccCodeEntryCode() {
		return bumiOrNrccCodeEntryCode;
	}

	/**
	 * @param bumiOrNrccCodeEntryCode the bumiOrNrccCodeEntryCode to set
	 */
	public void setBumiOrNrccCodeEntryCode(String bumiOrNrccCodeEntryCode) {
		this.bumiOrNrccCodeEntryCode = bumiOrNrccCodeEntryCode;
	}

	/**
	 * @return the smallScaleCodeCodeEntryCode
	 */
	public String getSmallScaleCodeCodeEntryCode() {
		return smallScaleCodeCodeEntryCode;
	}

	/**
	 * @param smallScaleCodeCodeEntryCode the smallScaleCodeCodeEntryCode to set
	 */
	public void setSmallScaleCodeCodeEntryCode(String smallScaleCodeCodeEntryCode) {
		this.smallScaleCodeCodeEntryCode = smallScaleCodeCodeEntryCode;
	}

	/**
	 * @return the prescribedRateCodeEntryCode
	 */
	public String getPrescribedRateCodeEntryCode() {
		return prescribedRateCodeEntryCode;
	}

	/**
	 * @param prescribedRateCodeEntryCode the prescribedRateCodeEntryCode to set
	 */
	public void setPrescribedRateCodeEntryCode(String prescribedRateCodeEntryCode) {
		this.prescribedRateCodeEntryCode = prescribedRateCodeEntryCode;
	}

	/**
	 * @return the relationshipCodeEntryCode
	 */
	public String getRelationshipCodeEntryCode() {
		return relationshipCodeEntryCode;
	}

	/**
	 * @param relationshipCodeEntryCode the relationshipCodeEntryCode to set
	 */
	public void setRelationshipCodeEntryCode(String relationshipCodeEntryCode) {
		this.relationshipCodeEntryCode = relationshipCodeEntryCode;
	}

	/**
	 * @return the goodsFinancedCodeOneEntryCode
	 */
	public String getGoodsFinancedCodeOneEntryCode() {
		return goodsFinancedCodeOneEntryCode;
	}

	/**
	 * @param goodsFinancedCodeOneEntryCode the goodsFinancedCodeOneEntryCode to
	 *        set
	 */
	public void setGoodsFinancedCodeOneEntryCode(String goodsFinancedCodeOneEntryCode) {
		this.goodsFinancedCodeOneEntryCode = goodsFinancedCodeOneEntryCode;
	}

	/**
	 * @return the goodsFinancedCodeTwoEntryCode
	 */
	public String getGoodsFinancedCodeTwoEntryCode() {
		return goodsFinancedCodeTwoEntryCode;
	}

	/**
	 * @param goodsFinancedCodeTwoEntryCode the goodsFinancedCodeTwoEntryCode to
	 *        set
	 */
	public void setGoodsFinancedCodeTwoEntryCode(String goodsFinancedCodeTwoEntryCode) {
		this.goodsFinancedCodeTwoEntryCode = goodsFinancedCodeTwoEntryCode;
	}

	/**
	 * @return the exemptedCodeEntryCode
	 */
	public String getExemptedCodeEntryCode() {
		return exemptedCodeEntryCode;
	}

	/**
	 * @param exemptedCodeEntryCode the exemptedCodeEntryCode to set
	 */
	public void setExemptedCodeEntryCode(String exemptedCodeEntryCode) {
		this.exemptedCodeEntryCode = exemptedCodeEntryCode;
	}

	/**
	 * @return the hostTierSequenceNumber
	 */
	public String getHostTierSequenceNumber() {
		return hostTierSequenceNumber;
	}

	/**
	 * @param hostTierSequenceNumber the hostTierSequenceNumber to set
	 */
	public void setHostTierSequenceNumber(String hostTierSequenceNumber) {
		this.hostTierSequenceNumber = hostTierSequenceNumber;
	}

	/**
	 * @return the purposeCodeEntryCode
	 */
	public String getPurposeCodeEntryCode() {
		return purposeCodeEntryCode;
	}

	/**
	 * @param purposeCodeEntryCode the purposeCodeEntryCode to set
	 */
	public void setPurposeCodeEntryCode(String purposeCodeEntryCode) {
		this.purposeCodeEntryCode = purposeCodeEntryCode;
	}

	/**
	 * @return the exemptedCodeIndicator
	 */
	public String getExemptedCodeIndicator() {
		return exemptedCodeIndicator;
	}

	/**
	 * @param exemptedCodeIndicator the exemptedCodeIndicator to set
	 */
	public void setExemptedCodeIndicator(String exemptedCodeIndicator) {
		this.exemptedCodeIndicator = exemptedCodeIndicator;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
