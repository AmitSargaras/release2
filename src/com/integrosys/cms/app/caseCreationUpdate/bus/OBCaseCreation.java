
package com.integrosys.cms.app.caseCreationUpdate.bus;


import java.util.Date;

import org.apache.struts.upload.FormFile;

/**
 * @author abhijit.rudrakshawar 
 */
public class OBCaseCreation implements ICaseCreation {
	
	/**
	 * constructor
	 */
	public OBCaseCreation() {
		
	}
    private long id;
	
	private long versionTime;
	private String description;
	
	
	private String branchCode;
	private String status;
	private String deprecated;
	private Date creationDate;
	private String createBy;
	private Date lastUpdateDate;
	private String lastUpdateBy;
	private long limitProfileId;
	private String prevRemarks;
	private String currRemarks;
	private String[] updatedDocBarcodes;
	private String[] updatedDocAmounts;
	private String[] updatedVaultNumbers;
	private String[] updatedRetrievaldates;
	private String[] updatedVaultReceiptDates;
	private String[] updatedUserNames;
	private String[] updatedSubmittedTos;
	private String[] updatedVaultLocations;
	private String[] updatedStampDutys;
	private String[] updatedPlaceOfExecutions;
	private String[] updatedFileBarCodes;
	private String[] updatedBoxBarCodes;
	private String[] updatedRackNumbers;
	private String[] updatedLotNumbers;
	private String[] updatedReceivedDates;
	private String[] checkBoxValues;
	
	public String[] getCheckBoxValues() {
		return checkBoxValues;
	}
	public void setCheckBoxValues(String[] checkBoxValues) {
		this.checkBoxValues = checkBoxValues;
	}
	public String[] getUpdatedReceivedDates() {
		return updatedReceivedDates;
	}
	public void setUpdatedReceivedDates(String[] updatedReceivedDates) {
		this.updatedReceivedDates = updatedReceivedDates;
	}
	public String[] getUpdatedDocBarcodes() {
		return updatedDocBarcodes;
	}
	public void setUpdatedDocBarcodes(String[] updatedDocBarcodes) {
		this.updatedDocBarcodes = updatedDocBarcodes;
	}
	public String[] getUpdatedDocAmounts() {
		return updatedDocAmounts;
	}
	public void setUpdatedDocAmounts(String[] updatedDocAmounts) {
		this.updatedDocAmounts = updatedDocAmounts;
	}
	public String[] getUpdatedVaultNumbers() {
		return updatedVaultNumbers;
	}
	public void setUpdatedVaultNumbers(String[] updatedVaultNumbers) {
		this.updatedVaultNumbers = updatedVaultNumbers;
	}
	public String[] getUpdatedRetrievaldates() {
		return updatedRetrievaldates;
	}
	public void setUpdatedRetrievaldates(String[] updatedRetrievaldates) {
		this.updatedRetrievaldates = updatedRetrievaldates;
	}
	public String[] getUpdatedVaultReceiptDates() {
		return updatedVaultReceiptDates;
	}
	public void setUpdatedVaultReceiptDates(String[] updatedVaultReceiptDates) {
		this.updatedVaultReceiptDates = updatedVaultReceiptDates;
	}
	public String[] getUpdatedUserNames() {
		return updatedUserNames;
	}
	public void setUpdatedUserNames(String[] updatedUserNames) {
		this.updatedUserNames = updatedUserNames;
	}
	public String[] getUpdatedSubmittedTos() {
		return updatedSubmittedTos;
	}
	public void setUpdatedSubmittedTos(String[] updatedSubmittedTos) {
		this.updatedSubmittedTos = updatedSubmittedTos;
	}
	public String[] getUpdatedVaultLocations() {
		return updatedVaultLocations;
	}
	public void setUpdatedVaultLocations(String[] updatedVaultLocations) {
		this.updatedVaultLocations = updatedVaultLocations;
	}
	public String[] getUpdatedStampDutys() {
		return updatedStampDutys;
	}
	public void setUpdatedStampDutys(String[] updatedStampDutys) {
		this.updatedStampDutys = updatedStampDutys;
	}
	public String[] getUpdatedPlaceOfExecutions() {
		return updatedPlaceOfExecutions;
	}
	public void setUpdatedPlaceOfExecutions(String[] updatedPlaceOfExecutions) {
		this.updatedPlaceOfExecutions = updatedPlaceOfExecutions;
	}
	public String[] getUpdatedFileBarCodes() {
		return updatedFileBarCodes;
	}
	public void setUpdatedFileBarCodes(String[] updatedFileBarCodes) {
		this.updatedFileBarCodes = updatedFileBarCodes;
	}
	public String[] getUpdatedBoxBarCodes() {
		return updatedBoxBarCodes;
	}
	public void setUpdatedBoxBarCodes(String[] updatedBoxBarCodes) {
		this.updatedBoxBarCodes = updatedBoxBarCodes;
	}
	public String[] getUpdatedRackNumbers() {
		return updatedRackNumbers;
	}
	public void setUpdatedRackNumbers(String[] updatedRackNumbers) {
		this.updatedRackNumbers = updatedRackNumbers;
	}
	public String[] getUpdatedLotNumbers() {
		return updatedLotNumbers;
	}
	public void setUpdatedLotNumbers(String[] updatedLotNumbers) {
		this.updatedLotNumbers = updatedLotNumbers;
	}
	public String getPrevRemarks() {
		return prevRemarks;
	}
	public void setPrevRemarks(String prevRemarks) {
		this.prevRemarks = prevRemarks;
	}
	public String getCurrRemarks() {
		return currRemarks;
	}
	public void setCurrRemarks(String currRemarks) {
		this.currRemarks = currRemarks;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	public long getLimitProfileId() {
		return limitProfileId;
	}
	public void setLimitProfileId(long limitProfileId) {
		this.limitProfileId = limitProfileId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

	
	
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
	
	
	
	


	
	

//	public String toString() {
//		StringBuffer buf = new StringBuffer(getClass().getName());
//		buf.append("@").append(System.identityHashCode(this));
//
//		buf.append(" CIF [").append(customerReference).append("], ");
//		buf.append("Customer Name [").append(customerName).append("], ");
//		buf.append("Short Description [").append(description).append("], ");
//		buf.append("Expiry Date [").append(expiryDate).append("], ");
//		buf.append("Reminder Date [").append(dueDate).append("], ");
//		buf.append("Access Country [").append(allowedCountry).append("]");
//
//		return buf.toString();
//	}
}