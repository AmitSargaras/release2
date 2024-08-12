//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION

package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_DUE_DATE_AND_STOCK_DETAILS;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 2:56:38 PM
 * To change this template use Options | File Templates.
 */

public class AssetGenChargeStockDetailsForm extends CommonForm//extends AssetGenChargeForm 
implements Serializable {
	
	private String drawingPower = "";

	private String dueDate = "";
	private String docCode = "";
	private String dueDateStatus;
	private String stockLocation = "";
	private String stockLocationDetail;
	private String marginType = "";

	private String stockDetailType = "";
	
	private String components = "";
	private String amount = "";
	private String margin = "";
	private String lonable = "";
	private String hasInsurance = "";
	
	private String insuranceCompanyName;
	private String insuranceCompanyCategory;// Renamed to Insurance Coverage Type on UI
	private String insuredAmount;
	private String effectiveDateOfInsurance="";
	private String expiryDate="";
	private String insuranceDescription;// Renamed to Remarks on UI
	private String insurancePolicyNo;
	private String insuranceCoverNote;
	private String insuranceCurrency="INR";
	private String totalPolicyAmount;
	private String insuranceRecivedDate="";
	private String insuranceDefaulted;
	private String insurancePremium;
	
//	private String fundedShare;
	private String calculatedDP;
	private String trxID = "";
	private String lastUpdatedBy;
	private Date lastUpdatedOn;
	private String lastApprovedBy;
	private Date lastApprovedOn;
	
	//Uma Khot:Cam upload and Dp field calculation CR
	private String dpShare;
	private String stockdocMonth;
	private String stockdocYear;
	
	private String coverageAmount;
	private String adHocCoverageAmount;
	private String coveragePercentage;
	
	public String getStockdocMonth() {
		return stockdocMonth;
	}
	public void setStockdocMonth(String stockdocMonth) {
		this.stockdocMonth = stockdocMonth;
	}
	public String getStockdocYear() {
		return stockdocYear;
	}
	public void setStockdocYear(String stockdocYear) {
		this.stockdocYear = stockdocYear;
	}


	//Start Santosh
	private String applicableForDp;
	private String dpCalculateManually;

	public String getApplicableForDp() {
		return applicableForDp;
	}
	public void setApplicableForDp(String applicableForDp) {
		this.applicableForDp = applicableForDp;
	}
	
	public String getDpCalculateManually() {
		return dpCalculateManually;
	}

	public void setDpCalculateManually(String dpCalculateManually) {
		this.dpCalculateManually = dpCalculateManually;
	}
	//End Santosh
		
	public String getTrxID() {
		return trxID;
	}
	public void setTrxID(String trxID) {
		this.trxID = trxID;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getDocCode() {
		return docCode;
	}
	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}
	public String getDrawingPower() {
		return drawingPower;
	}
	public void setDrawingPower(String drawingPower) {
		this.drawingPower = drawingPower;
	}
	public String getStockLocation() {
		return stockLocation;
	}
	public void setStockLocation(String stockLocation) {
		this.stockLocation = stockLocation;
	}
	public String getStockLocationDetail() {
		return stockLocationDetail;
	}
	public void setStockLocationDetail(String stockLocationDetail) {
		this.stockLocationDetail = stockLocationDetail;
	}
	public String getMarginType() {
		return marginType;
	}
	public void setMarginType(String marginType) {
		this.marginType = marginType;
	}
	public String getComponents() {
		return components;
	}
	public void setComponents(String components) {
		this.components = components;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMargin() {
		return margin;
	}
	public void setMargin(String margin) {
		this.margin = margin;
	}
	public String getLonable() {
		return lonable;
	}
	public void setLonable(String lonable) {
		this.lonable = lonable;
	}
	public String getStockDetailType() {
		return stockDetailType;
	}
	public void setStockDetailType(String stockDetailType) {
		this.stockDetailType = stockDetailType;
	}
	public String getHasInsurance() {
		return hasInsurance;
	}
	public void setHasInsurance(String hasInsurance) {
		this.hasInsurance = hasInsurance;
	}
	
	public String getEffectiveDateOfInsurance() {
		return effectiveDateOfInsurance;
	}
	public void setEffectiveDateOfInsurance(String effectiveDateOfInsurance) {
		this.effectiveDateOfInsurance = effectiveDateOfInsurance;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getInsuranceCompanyCategory() {
		return insuranceCompanyCategory;
	}
	public void setInsuranceCompanyCategory(String insuranceCompanyCategory) {
		this.insuranceCompanyCategory = insuranceCompanyCategory;
	}
	public String getInsuranceCompanyName() {
		return insuranceCompanyName;
	}
	public void setInsuranceCompanyName(String insuranceCompanyName) {
		this.insuranceCompanyName = insuranceCompanyName;
	}
	public String getInsuranceDescription() {
		return insuranceDescription;
	}
	public void setInsuranceDescription(String insuranceDescription) {
		this.insuranceDescription = insuranceDescription;
	}
	public String getInsuredAmount() {
		return insuredAmount;
	}
	public void setInsuredAmount(String insuredAmount) {
		this.insuredAmount = insuredAmount;
	}
	
	public String getDueDateStatus() {
		return dueDateStatus;
	}
	public void setDueDateStatus(String dueDateStatus) {
		this.dueDateStatus = dueDateStatus;
	}


	public String getInsurancePolicyNo() {
		return insurancePolicyNo;
	}
	public void setInsurancePolicyNo(String insurancePolicyNo) {
		this.insurancePolicyNo = insurancePolicyNo;
	}
	public String getInsuranceCoverNote() {
		return insuranceCoverNote;
	}
	public void setInsuranceCoverNote(String insuranceCoverNote) {
		this.insuranceCoverNote = insuranceCoverNote;
	}
	public String getInsuranceCurrency() {
		return insuranceCurrency;
	}
	public void setInsuranceCurrency(String insuranceCurrency) {
		this.insuranceCurrency = insuranceCurrency;
	}
	public String getTotalPolicyAmount() {
		return totalPolicyAmount;
	}
	public void setTotalPolicyAmount(String totalPolicyAmount) {
		this.totalPolicyAmount = totalPolicyAmount;
	}
	public String getInsuranceRecivedDate() {
		return insuranceRecivedDate;
	}
	public void setInsuranceRecivedDate(String insuranceRecivedDate) {
		this.insuranceRecivedDate = insuranceRecivedDate;
	}
	public String getInsuranceDefaulted() {
		return insuranceDefaulted;
	}
	public void setInsuranceDefaulted(String insuranceDefaulted) {
		this.insuranceDefaulted = insuranceDefaulted;
	}
	public String getInsurancePremium() {
		return insurancePremium;
	}
	public void setInsurancePremium(String insurancePremium) {
		this.insurancePremium = insurancePremium;
	}

//	public String getFundedShare() {
//		return fundedShare;
//	}
//	public void setFundedShare(String fundedShare) {
//		this.fundedShare = fundedShare;
//	}
	public String getCalculatedDP() {
		return calculatedDP;
	}
	public void setCalculatedDP(String calculatedDP) {
		this.calculatedDP = calculatedDP;
	}

    public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	public String getLastApprovedBy() {
		return lastApprovedBy;
	}
	public void setLastApprovedBy(String lastApprovedBy) {
		this.lastApprovedBy = lastApprovedBy;
	}
	public Date getLastApprovedOn() {
		return lastApprovedOn;
	}
	public void setLastApprovedOn(Date lastApprovedOn) {
		this.lastApprovedOn = lastApprovedOn;
	}

	public String getCoverageAmount() {
		return coverageAmount;
	}
	public void setCoverageAmount(String coverageAmount) {
		this.coverageAmount = coverageAmount;
	}
	public String getCoveragePercentage() {
		return coveragePercentage;
	}
	public void setCoveragePercentage(String coveragePercentage) {
		this.coveragePercentage = coveragePercentage;
	}


	public static final String ASSETGENCHARGESTOCKDETAILSMAPPER = "com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeStockDetailsMapper";
	public String[][] getMapper() {
		/*String[][] input = { { "form.collateralObject", ASSETGENCHARGESTOCKDETAILSMAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "pledgorRemove", "com.integrosys.cms.ui.collateral.pledgor.PledgorRemoveMapper" },
				{ "pledgeRemove", "com.integrosys.cms.ui.collateral.pledge.PledgeRemoveMapper" }, };
		*/
		String[][] input = { 
				{ "form.stockDetailsObject", ASSETGENCHARGESTOCKDETAILSMAPPER },
				{ SESSION_DUE_DATE_AND_STOCK_DETAILS, ASSETGENCHARGESTOCKDETAILSMAPPER },
				};
		
		return input;
	}
	public String getDpShare() {
		return dpShare;
	}
	public void setDpShare(String dpShare) {
		this.dpShare = dpShare;
	}
	public String getAdHocCoverageAmount() {
		return adHocCoverageAmount;
	}
	public void setAdHocCoverageAmount(String adHocCoverageAmount) {
		this.adHocCoverageAmount = adHocCoverageAmount;
	}
	
	private String remarkByMaker;
	private String totalLoanable;

	private String migrationFlag_DP_CR;

	public String getRemarkByMaker() {
		return remarkByMaker;
	}
	public void setRemarkByMaker(String remarkByMaker) {
		this.remarkByMaker = remarkByMaker;
	}
	
	
	
	public String getTotalLoanable() {
		return totalLoanable;
	}
	public void setTotalLoanable(String totalLoanable) {
		this.totalLoanable = totalLoanable;
	}


	String stockComponentCat;


	public String getStockComponentCat() {
		return stockComponentCat;
	}
	public void setStockComponentCat(String stockComponentCat) {
		this.stockComponentCat = stockComponentCat;
	}
	public String getMigrationFlag_DP_CR() {
		return migrationFlag_DP_CR;
	}
	public void setMigrationFlag_DP_CR(String migrationFlag_DP_CR) {
		this.migrationFlag_DP_CR = migrationFlag_DP_CR;
	}
	
	public long cmsRefId;


	public long getCmsRefId() {
		return cmsRefId;
	}
	public void setCmsRefId(long cmsRefId) {
		this.cmsRefId = cmsRefId;
	}
	
	
}
