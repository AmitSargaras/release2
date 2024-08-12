package com.integrosys.cms.app.bridgingloan.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.bus.IArea;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBBuildUp implements IBuildUp {
	private long buildUpID = ICMSConstant.LONG_INVALID_VALUE;

	private long projectID = ICMSConstant.LONG_INVALID_VALUE;

	private String propertyType;

	private String unitID;

	private String blockNo;

	private String titleNo;

	private String unitNo;

	private boolean isUnitDischarged;

	private IArea approxArea;

	private IArea approxAreaSecondary;

	private Amount redemptionAmount;

	private Amount salesAmount;

	private Date salesDate;

	private String purchaserName;

	private String endFinancier;

	private String buRemarks;

	private Date tenancyDate;

	private String tenantName;

	private String tenancyPeriodUnit;

	private int tenancyPeriod = ICMSConstant.INT_INVALID_VALUE;

	private Date tenancyExpiryDate;

	private Amount rentalAmount;

	private String paymentFrequency;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd;

	private ISalesProceeds[] salesProceedsList;

	/**
	 * Default Constructor
	 */
	public OBBuildUp() {
	}

	public long getBuildUpID() {
		return buildUpID;
	}

	public void setBuildUpID(long buildUpID) {
		this.buildUpID = buildUpID;
	}

	public long getProjectID() {
		return projectID;
	}

	public void setProjectID(long projectID) {
		this.projectID = projectID;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getUnitID() {
		return unitID;
	}

	public void setUnitID(String unitID) {
		this.unitID = unitID;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}

	public String getTitleNo() {
		return titleNo;
	}

	public void setTitleNo(String titleNo) {
		this.titleNo = titleNo;
	}

	public boolean getIsUnitDischarged() {
		return isUnitDischarged;
	}

	public void setIsUnitDischarged(boolean isUnitDischarged) {
		this.isUnitDischarged = isUnitDischarged;
	}

	public IArea getApproxArea() {
		return approxArea;
	}

	public void setApproxArea(IArea approxArea) {
		this.approxArea = approxArea;
	}

	public IArea getApproxAreaSecondary() {
		return approxAreaSecondary;
	}

	public void setApproxAreaSecondary(IArea approxArea) {
		this.approxAreaSecondary = approxArea;
	}

	public Amount getRedemptionAmount() {
		return redemptionAmount;
	}

	public void setRedemptionAmount(Amount redemptionAmount) {
		this.redemptionAmount = redemptionAmount;
	}

	public Amount getSalesAmount() {
		return salesAmount;
	}

	public void setSalesAmount(Amount salesAmount) {
		this.salesAmount = salesAmount;
	}

	public Date getSalesDate() {
		return salesDate;
	}

	public void setSalesDate(Date salesDate) {
		this.salesDate = salesDate;
	}

	public String getPurchaserName() {
		return purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getEndFinancier() {
		return endFinancier;
	}

	public void setEndFinancier(String endFinancier) {
		this.endFinancier = endFinancier;
	}

	public String getBuRemarks() {
		return buRemarks;
	}

	public void setBuRemarks(String buRemarks) {
		this.buRemarks = buRemarks;
	}

	public Date getTenancyDate() {
		return tenancyDate;
	}

	public void setTenancyDate(Date tenancyDate) {
		this.tenancyDate = tenancyDate;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getTenancyPeriodUnit() {
		return tenancyPeriodUnit;
	}

	public void setTenancyPeriodUnit(String tenancyPeriodUnit) {
		this.tenancyPeriodUnit = tenancyPeriodUnit;
	}

	public int getTenancyPeriod() {
		return tenancyPeriod;
	}

	public void setTenancyPeriod(int tenancyPeriod) {
		this.tenancyPeriod = tenancyPeriod;
	}

	public Date getTenancyExpiryDate() {
		return tenancyExpiryDate;
	}

	public void setTenancyExpiryDate(Date tenancyExpiryDate) {
		this.tenancyExpiryDate = tenancyExpiryDate;
	}

	public Amount getRentalAmount() {
		return rentalAmount;
	}

	public void setRentalAmount(Amount rentalAmount) {
		this.rentalAmount = rentalAmount;
	}

	public String getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(String paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public ISalesProceeds[] getSalesProceedsList() {
		return salesProceedsList;
	}

	public void setSalesProceedsList(ISalesProceeds[] salesProceedsList) {
		this.salesProceedsList = salesProceedsList;
	}

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public boolean getIsDeletedInd() {
		return isDeletedInd;
	}

	public void setIsDeletedInd(boolean isDeletedInd) {
		this.isDeletedInd = isDeletedInd;
	}
}