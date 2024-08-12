
package com.integrosys.cms.app.caseCreation.bus;

import java.util.Date;



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
	
	
	
	private long checklistitemid=0L;
	private long casecreationid =0L;
	private String itemtype="";
	private String status="";
	private String remark="";
	private Date caseDate;
	private Date requestedDate;
	private Date dispatchedDate;
	private Date receivedDate;
	private Date wrongReqDate;
	private long limitProfileId =0L;
	private String isAutoCase="";
	
	private String documentBarCode="";
	private String vaultNumber="";
	private Date retrievaldate;
	private Date vaultReceiptDate;
	private String userName ="";
	private String submittedTo ="";
	private String documentAmount="";
	private String vaultLocation="";
	private String stampDuty="";
	private String placeOfExecution="";
	private String fileBarCode="";
	private String boxBarCode ="";
	private String rackNumber ="";
	private String lotNumber="";
	
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
	
	
	public String[] getUpdatedReceivedDates() {
		return updatedReceivedDates;
	}
	public void setUpdatedReceivedDates(String[] updatedReceivedDates) {
		this.updatedReceivedDates = updatedReceivedDates;
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
	public String getDocumentBarCode() {
		return documentBarCode;
	}
	public void setDocumentBarCode(String documentBarCode) {
		this.documentBarCode = documentBarCode;
	}
	public String getVaultNumber() {
		return vaultNumber;
	}
	public void setVaultNumber(String vaultNumber) {
		this.vaultNumber = vaultNumber;
	}
	
	
	public Date getRetrievaldate() {
		return retrievaldate;
	}
	public void setRetrievaldate(Date retrievaldate) {
		this.retrievaldate = retrievaldate;
	}
	public Date getVaultReceiptDate() {
		return vaultReceiptDate;
	}
	public void setVaultReceiptDate(Date vaultReceiptDate) {
		this.vaultReceiptDate = vaultReceiptDate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSubmittedTo() {
		return submittedTo;
	}
	public void setSubmittedTo(String submittedTo) {
		this.submittedTo = submittedTo;
	}
	public String getDocumentAmount() {
		return documentAmount;
	}
	public void setDocumentAmount(String documentAmount) {
		this.documentAmount = documentAmount;
	}
	public String getVaultLocation() {
		return vaultLocation;
	}
	public void setVaultLocation(String vaultLocation) {
		this.vaultLocation = vaultLocation;
	}
	public String getStampDuty() {
		return stampDuty;
	}
	public void setStampDuty(String stampDuty) {
		this.stampDuty = stampDuty;
	}
	public String getPlaceOfExecution() {
		return placeOfExecution;
	}
	public void setPlaceOfExecution(String placeOfExecution) {
		this.placeOfExecution = placeOfExecution;
	}
	public String getFileBarCode() {
		return fileBarCode;
	}
	public void setFileBarCode(String fileBarCode) {
		this.fileBarCode = fileBarCode;
	}
	public String getBoxBarCode() {
		return boxBarCode;
	}
	public void setBoxBarCode(String boxBarCode) {
		this.boxBarCode = boxBarCode;
	}
	public String getRackNumber() {
		return rackNumber;
	}
	public void setRackNumber(String rackNumber) {
		this.rackNumber = rackNumber;
	}
	public String getLotNumber() {
		return lotNumber;
	}
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	public String getIsAutoCase() {
		return isAutoCase;
	}
	public void setIsAutoCase(String isAutoCase) {
		this.isAutoCase = isAutoCase;
	}
	public Date getWrongReqDate() {
		return wrongReqDate;
	}
	public void setWrongReqDate(Date wrongReqDate) {
		this.wrongReqDate = wrongReqDate;
	}
	public long getLimitProfileId() {
		return limitProfileId;
	}
	public void setLimitProfileId(long limitProfileId) {
		this.limitProfileId = limitProfileId;
	}
	public Date getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}
	public Date getDispatchedDate() {
		return dispatchedDate;
	}
	public void setDispatchedDate(Date dispatchedDate) {
		this.dispatchedDate = dispatchedDate;
	}
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public Date getCaseDate() {
		return caseDate;
	}
	public void setCaseDate(Date caseDate) {
		this.caseDate = caseDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public long getChecklistitemid() {
		return checklistitemid;
	}
	public void setChecklistitemid(long checklistitemid) {
		this.checklistitemid = checklistitemid;
	}
	public long getCasecreationid() {
		return casecreationid;
	}
	public void setCasecreationid(long casecreationid) {
		this.casecreationid = casecreationid;
	}
	public String getItemtype() {
		return itemtype;
	}
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}
	
	
	
	
	
	
	
}