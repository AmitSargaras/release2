package com.integrosys.cms.app.bridgingloan.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBBridgingLoan implements IBridgingLoan {
	// main contract info
	private long projectID = ICMSConstant.LONG_INVALID_VALUE;

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private long limitID = ICMSConstant.LONG_INVALID_VALUE;

	private String sourceLimit;

	private String productDescription;

	private String projectNumber;

	private Date contractDate;

	private Amount contractAmount;

	private float financePercent = ICMSConstant.FLOAT_INVALID_VALUE;

	private String collectionAccount;

	private String hdaAccount;

	private String projectAccount;

	private String currentAccount;

	private Date fullSettlementDate;

	private Date fullSettlementContractDate;

	private int noOfTypes = ICMSConstant.INT_INVALID_VALUE;

	private int noOfUnits = ICMSConstant.INT_INVALID_VALUE;

	private Date expectedStartDate;

	private Date expectedCompletionDate;

	private Date actualStartDate;

	private Date actualCompletionDate;

	private Date availabilityExpiryDate;

	private String blRemarks;

	private long versionTime = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd;

	private IPropertyType[] propertyTypeList;

	private IProjectSchedule[] projectScheduleList;

	private IDisbursement[] disbursementList;

	private ISettlement[] settlementList;

	private IBuildUp[] buildUpList;

	private IFDR[] fdrList;

	/**
	 * Default Constructor
	 */
	public OBBridgingLoan() {
	}

	public long getProjectID() {
		return projectID;
	}

	public void setProjectID(long projectID) {
		this.projectID = projectID;
	}

	public long getLimitProfileID() {
		return limitProfileID;
	}

	public void setLimitProfileID(long limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public long getLimitID() {
		return limitID;
	}

	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	public String getSourceLimit() {
		return sourceLimit;
	}

	public void setSourceLimit(String sourceLimit) {
		this.sourceLimit = sourceLimit;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Amount getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Amount contractAmount) {
		this.contractAmount = contractAmount;
	}

	public float getFinancePercent() {
		return financePercent;
	}

	public void setFinancePercent(float financePercent) {
		this.financePercent = financePercent;
	}

	public Amount getFinancedAmount() {
		if (financePercent == ICMSConstant.FLOAT_INVALID_VALUE) {
			return null;
		}

		return new Amount(contractAmount.getAmount() * financePercent / 100, contractAmount.getCurrencyCode());
	}

	public String getCollectionAccount() {
		return collectionAccount;
	}

	public void setCollectionAccount(String collectionAccount) {
		this.collectionAccount = collectionAccount;
	}

	public String getHdaAccount() {
		return hdaAccount;
	}

	public void setHdaAccount(String hdaAccountNo) {
		this.hdaAccount = hdaAccountNo;
	}

	public String getProjectAccount() {
		return projectAccount;
	}

	public void setProjectAccount(String projectAccount) {
		this.projectAccount = projectAccount;
	}

	public String getCurrentAccount() {
		return currentAccount;
	}

	public void setCurrentAccount(String currentAccount) {
		this.currentAccount = currentAccount;
	}

	public Date getFullSettlementDate() {
		return fullSettlementDate;
	}

	public void setFullSettlementDate(Date fullSettlementDate) {
		this.fullSettlementDate = fullSettlementDate;
	}

	public Date getFullSettlementContractDate() {
		return fullSettlementContractDate;
	}

	public void setFullSettlementContractDate(Date fullSettlementContractDate) {
		this.fullSettlementContractDate = fullSettlementContractDate;
	}

	public int getNoOfTypes() {
		return noOfTypes;
	}

	public void setNoOfTypes(int noOfTypes) {
		this.noOfTypes = noOfTypes;
	}

	public int getNoOfUnits() {
		return noOfUnits;
	}

	public void setNoOfUnits(int noOfUnits) {
		this.noOfUnits = noOfUnits;
	}

	public Date getExpectedStartDate() {
		return expectedStartDate;
	}

	public void setExpectedStartDate(Date expectedStartDate) {
		this.expectedStartDate = expectedStartDate;
	}

	public Date getExpectedCompletionDate() {
		return expectedCompletionDate;
	}

	public void setExpectedCompletionDate(Date expectedCompletionDate) {
		this.expectedCompletionDate = expectedCompletionDate;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualCompletionDate() {
		return actualCompletionDate;
	}

	public void setActualCompletionDate(Date actualCompletionDate) {
		this.actualCompletionDate = actualCompletionDate;
	}

	public Date getAvailabilityExpiryDate() {
		return availabilityExpiryDate;
	}

	public void setAvailabilityExpiryDate(Date availabilityExpiryDate) {
		this.availabilityExpiryDate = availabilityExpiryDate;
	}

	public String getBlRemarks() {
		return blRemarks;
	}

	public void setBlRemarks(String blRemarks) {
		this.blRemarks = blRemarks;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public IPropertyType[] getPropertyTypeList() {
		return propertyTypeList;
	}

	public void setPropertyTypeList(IPropertyType[] propertyTypeList) {
		this.propertyTypeList = propertyTypeList;
	}

	public IProjectSchedule[] getProjectScheduleList() {
		return projectScheduleList;
	}

	public void setProjectScheduleList(IProjectSchedule[] projectScheduleList) {
		this.projectScheduleList = projectScheduleList;
	}

	public IDisbursement[] getDisbursementList() {
		return disbursementList;
	}

	public void setDisbursementList(IDisbursement[] disbursementList) {
		this.disbursementList = disbursementList;
	}

	public ISettlement[] getSettlementList() {
		return settlementList;
	}

	public void setSettlementList(ISettlement[] settlementList) {
		this.settlementList = settlementList;
	}

	public IBuildUp[] getBuildUpList() {
		return buildUpList;
	}

	public void setBuildUpList(IBuildUp[] buildUpList) {
		this.buildUpList = buildUpList;
	}

	public IFDR[] getFdrList() {
		return fdrList;
	}

	public void setFdrList(IFDR[] fdrList) {
		this.fdrList = fdrList;
	}

	// ========================
	// Common Methods
	// ========================
	public boolean getIsDeletedInd() {
		return isDeletedInd;
	}

	public void setIsDeletedInd(boolean isDeletedInd) {
		this.isDeletedInd = isDeletedInd;
	}
}