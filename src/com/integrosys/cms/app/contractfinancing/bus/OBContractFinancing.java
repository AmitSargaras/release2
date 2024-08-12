package com.integrosys.cms.app.contractfinancing.bus;

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
public class OBContractFinancing implements IContractFinancing {

	// main contract info
	private long contractID = ICMSConstant.LONG_INVALID_VALUE;

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private long limitID = ICMSConstant.LONG_INVALID_VALUE;

	private String sourceLimit;

	private String productDescription;

	private String contractNumber;

	private Date contractDate;

	private String awarderType;

	private String awarderTypeOthers;

	private String awarderName;

	private String contractType;

	private String contractTypeOthers;

	private Date startDate;

	private Date expiryDate;

	private Date extendedDate;

	private Amount contractAmount;

	private Amount actualFinanceAmount;

	private float financePercent = ICMSConstant.FLOAT_INVALID_VALUE;

	private Amount projectedProfit;

	private String collectionAccount;

	private String projectAccount;

	private Date facilityExpiryDate;

	private String sinkingFundInd;

	private String sinkingFundParty;

	private float buildUpFDR = ICMSConstant.FLOAT_INVALID_VALUE;

	private String contractDescription;

	private String remark;

	private boolean isDeleted;

	private IContractFacilityType facilityTypeList[];

	private IAdvance advanceList[];

	private ITNC tncList[];

	private IFDR fdrList[];

	private long versionTime = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default Constructor
	 */
	public OBContractFinancing() {
	}

	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
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

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public String getAwarderType() {
		return awarderType;
	}

	public void setAwarderType(String awarderType) {
		this.awarderType = awarderType;
	}

	public String getAwarderTypeOthers() {
		return awarderTypeOthers;
	}

	public void setAwarderTypeOthers(String awarderTypeOthers) {
		this.awarderTypeOthers = awarderTypeOthers;
	}

	public String getAwarderName() {
		return awarderName;
	}

	public void setAwarderName(String awarderName) {
		this.awarderName = awarderName;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractTypeOthers() {
		return contractTypeOthers;
	}

	public void setContractTypeOthers(String contractTypeOthers) {
		this.contractTypeOthers = contractTypeOthers;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getExtendedDate() {
		return extendedDate;
	}

	public void setExtendedDate(Date extendedDate) {
		this.extendedDate = extendedDate;
	}

	public Amount getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Amount contractAmount) {
		this.contractAmount = contractAmount;
	}

	public Amount getActualFinanceAmount() {
		return actualFinanceAmount;
	}

	public void setActualFinanceAmount(Amount actualFinanceAmount) {
		this.actualFinanceAmount = actualFinanceAmount;
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
		else {
			return new Amount(contractAmount.getAmount() * financePercent / 100, contractAmount.getCurrencyCode());
		}
	}

	public Amount getProjectedProfit() {
		return projectedProfit;
	}

	public void setProjectedProfit(Amount projectedProfit) {
		this.projectedProfit = projectedProfit;
	}

	public String getCollectionAccount() {
		return collectionAccount;
	}

	public void setCollectionAccount(String collectionAccount) {
		this.collectionAccount = collectionAccount;
	}

	public Date getFacilityExpiryDate() {
		return facilityExpiryDate;
	}

	public void setFacilityExpiryDate(Date facilityExpiryDate) {
		this.facilityExpiryDate = facilityExpiryDate;
	}

	public String getProjectAccount() {
		return projectAccount;
	}

	public void setProjectAccount(String projectAccount) {
		this.projectAccount = projectAccount;
	}

	public String getSinkingFundInd() {
		return sinkingFundInd;
	}

	public void setSinkingFundInd(String sinkingFundInd) {
		this.sinkingFundInd = sinkingFundInd;
	}

	public String getSinkingFundParty() {
		return sinkingFundParty;
	}

	public void setSinkingFundParty(String sinkingFundParty) {
		this.sinkingFundParty = sinkingFundParty;
	}

	public float getBuildUpFDR() {
		return buildUpFDR;
	}

	public void setBuildUpFDR(float buildUpFDR) {
		this.buildUpFDR = buildUpFDR;
	}

	public String getContractDescription() {
		return contractDescription;
	}

	public void setContractDescription(String contractDescription) {
		this.contractDescription = contractDescription;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public IContractFacilityType[] getFacilityTypeList() {
		return facilityTypeList;
	}

	public void setFacilityTypeList(IContractFacilityType[] facilityTypeList) {
		this.facilityTypeList = facilityTypeList;
	}

	public IAdvance[] getAdvanceList() {
		return advanceList;
	}

	public void setAdvanceList(IAdvance[] advanceList) {
		this.advanceList = advanceList;
	}

	public ITNC[] getTncList() {
		return tncList;
	}

	public void setTncList(ITNC[] tncList) {
		this.tncList = tncList;
	}

	public IFDR[] getFdrList() {
		return fdrList;
	}

	public void setFdrList(IFDR[] fdrList) {
		this.fdrList = fdrList;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}
