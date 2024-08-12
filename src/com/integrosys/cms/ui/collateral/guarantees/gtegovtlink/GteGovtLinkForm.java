package com.integrosys.cms.ui.collateral.guarantees.gtegovtlink;

import java.io.Serializable;

import com.integrosys.cms.ui.collateral.guarantees.GuaranteesForm;

public class GteGovtLinkForm extends GuaranteesForm implements Serializable {

	public static final String GTEGOVTLINKMAPPER = "com.integrosys.cms.ui.collateral.guarantees.gtegovtlink.GteGovtLinkMapper";

	private String minimalFSV;

	public String getMinimalFSV() {
		return this.minimalFSV;
	}

	public void setMinimalFSV(String minimalFSV) {
		this.minimalFSV = minimalFSV;
	}

	private String feeDetailsID;

	private String effectiveDate;

	private String expirationDate;

	private String claimDate;

	private String perfectionDate;

	private String amountCGC;

	private String amountFee;

	private String Tenor;

	private String refID;

	private String status;

	private String currentScheme;

	private String borrowerDependency;

	private String securedPortion = "";

	private String securedAmountOrigin = "";

	private String unsecuredPortion = "";

	private String unsecuredAmountOrigin = "";

	private String securedAmountCalc = "";

	private String unsecuredAmountCalc = "";

	private String guaranteeAmtCalc = "";

	private String sourceSecuritySubtype;
	
	private String cancellationDateLG;

	public void setFeeDetailsID(String feeDetailsID) {
		this.feeDetailsID = feeDetailsID;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getAmountCGC() {
		return amountCGC;
	}

	public void setAmountCGC(String amountCGC) {
		this.amountCGC = amountCGC;
	}

	public String getAmountFee() {
		return amountFee;
	}

	public void setAmountFee(String amountFee) {
		this.amountFee = amountFee;
	}

	public String getTenor() {
		return Tenor;
	}

	public void setTenor(String tenor) {
		Tenor = tenor;
	}

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrentScheme() {
		return currentScheme;
	}

	public void setCurrentScheme(String currentScheme) {
		this.currentScheme = currentScheme;
	}

	public void reset() {
		super.reset();

	}

	public String[][] getMapper() {
		String[][] input = { { "form.collateralObject", GTEGOVTLINKMAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" }, };
		return input;
	}

	public String getBorrowerDependency() {
		return borrowerDependency;
	}

	public void setBorrowerDependency(String borrowerDependency) {
		this.borrowerDependency = borrowerDependency;
	}

	public String getPerfectionDate() {
		return perfectionDate;
	}

	public void setPerfectionDate(String perfectionDate) {
		this.perfectionDate = perfectionDate;
	}

	public String getClaimDate() {
		return claimDate;
	}

	public void setClaimDate(String claimDate) {
		this.claimDate = claimDate;
	}

	public String getSecuredPortion() {
		return securedPortion;
	}

	public void setSecuredPortion(String securedPortion) {
		this.securedPortion = securedPortion;
	}

	public String getSecuredAmountOrigin() {
		return securedAmountOrigin;
	}

	public void setSecuredAmountOrigin(String securedAmountOrigin) {
		this.securedAmountOrigin = securedAmountOrigin;
	}

	public String getUnsecuredPortion() {
		return unsecuredPortion;
	}

	public void setUnsecuredPortion(String unsecuredPortion) {
		this.unsecuredPortion = unsecuredPortion;
	}

	public String getUnsecuredAmountOrigin() {
		return unsecuredAmountOrigin;
	}

	public void setUnsecuredAmountOrigin(String unsecuredAmountOrigin) {
		this.unsecuredAmountOrigin = unsecuredAmountOrigin;
	}

	public String getSecuredAmountCalc() {
		return securedAmountCalc;
	}

	public void setSecuredAmountCalc(String securedAmountCalc) {
		this.securedAmountCalc = securedAmountCalc;
	}

	public String getUnsecuredAmountCalc() {
		return unsecuredAmountCalc;
	}

	public void setUnsecuredAmountCalc(String unsecuredAmountCalc) {
		this.unsecuredAmountCalc = unsecuredAmountCalc;
	}

	public String getGuaranteeAmtCalc() {
		return guaranteeAmtCalc;
	}

	public void setGuaranteeAmtCalc(String guaranteeAmtCalc) {
		this.guaranteeAmtCalc = guaranteeAmtCalc;
	}

	public String getSourceSecuritySubtype() {
		return sourceSecuritySubtype;
	}

	public void setSourceSecuritySubtype(String sourceSecuritySubtype) {
		this.sourceSecuritySubtype = sourceSecuritySubtype;
	}

	public String getCancellationDateLG() {
		return cancellationDateLG;
	}

	public void setCancellationDateLG(String cancellationDateLG) {
		this.cancellationDateLG = cancellationDateLG;
	}

}
