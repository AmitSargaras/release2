/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.caseCreationUpdate;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijit R$
 *Form Bean for CaseCreation Master
 */

public class MaintainCaseCreationForm extends TrxContextForm implements Serializable {

	private String description;
 
	private String versionTime;

	private String lastUpdateDate;
	private String disableForSelection;
	
	
	private String creationDate;
	
	
	private String createBy;
	//private String lastUpdateDate;
	private String lastUpdateBy;
	private String txtSegment;
	private String txtRegion;
	
	private String branchCode;
	private String status;
	private String deprecated;
	private String id;
	private String limitProfileId;
	private String coordinator1Name;
	private String coordinator2Name;
	private String remarkCheck;
	private String submittedssTo;
	private String removedSrNum;
	private String vaultLocationss;
	
	private String statusOfDocument="";
	private String[] updatedDocBarcode;
	private String[] updatedDocAmount;
	private String[] updatedVaultNumber;
	private String[] updatedRetrievaldate;
	private String[] updatedVaultReceiptDate;
	private String[] updatedUserName;
	private String[] updatedSubmittedTo;
	private String[] updatedVaultLocation;
	private String[] updatedStampDuty;
	private String[] updatedPlaceOfExecution;
	private String[] updatedFileBarCode;
	private String[] updatedBoxBarCode;
	private String[] updatedRackNumber;
	private String[] updatedLotNumber;
	private String[] updatedReceivedDate;
	private String[] checkBoxValues;
	
	
	
	public String getVaultLocationss() {
		return vaultLocationss;
	}
	public void setVaultLocationss(String vaultLocationss) {
		this.vaultLocationss = vaultLocationss;
	}
	public String getRemovedSrNum() {
		return removedSrNum;
	}
	public void setRemovedSrNum(String removedSrNum) {
		this.removedSrNum = removedSrNum;
	}
	public String getStatusOfDocument() {
		return statusOfDocument;
	}
	public void setStatusOfDocument(String statusOfDocument) {
		this.statusOfDocument = statusOfDocument;
	}
	public String getSubmittedssTo() {
		return submittedssTo;
	}
	public void setSubmittedssTo(String submittedssTo) {
		this.submittedssTo = submittedssTo;
	}
	public String[] getCheckBoxValues() {
		return checkBoxValues;
	}
	public void setCheckBoxValues(String[] checkBoxValues) {
		this.checkBoxValues = checkBoxValues;
	}
	public String[] getUpdatedReceivedDate() {
		return updatedReceivedDate;
	}
	public void setUpdatedReceivedDate(String[] updatedReceivedDate) {
		this.updatedReceivedDate = updatedReceivedDate;
	}
	public String[] getUpdatedVaultNumber() {
		return updatedVaultNumber;
	}
	public void setUpdatedVaultNumber(String[] updatedVaultNumber) {
		this.updatedVaultNumber = updatedVaultNumber;
	}
	public String[] getUpdatedRetrievaldate() {
		return updatedRetrievaldate;
	}
	public void setUpdatedRetrievaldate(String[] updatedRetrievaldate) {
		this.updatedRetrievaldate = updatedRetrievaldate;
	}
	public String[] getUpdatedVaultReceiptDate() {
		return updatedVaultReceiptDate;
	}
	public void setUpdatedVaultReceiptDate(String[] updatedVaultReceiptDate) {
		this.updatedVaultReceiptDate = updatedVaultReceiptDate;
	}
	public String[] getUpdatedUserName() {
		return updatedUserName;
	}
	public void setUpdatedUserName(String[] updatedUserName) {
		this.updatedUserName = updatedUserName;
	}
	public String[] getUpdatedSubmittedTo() {
		return updatedSubmittedTo;
	}
	public void setUpdatedSubmittedTo(String[] updatedSubmittedTo) {
		this.updatedSubmittedTo = updatedSubmittedTo;
	}
	public String[] getUpdatedVaultLocation() {
		return updatedVaultLocation;
	}
	public void setUpdatedVaultLocation(String[] updatedVaultLocation) {
		this.updatedVaultLocation = updatedVaultLocation;
	}
	public String[] getUpdatedStampDuty() {
		return updatedStampDuty;
	}
	public void setUpdatedStampDuty(String[] updatedStampDuty) {
		this.updatedStampDuty = updatedStampDuty;
	}
	public String[] getUpdatedPlaceOfExecution() {
		return updatedPlaceOfExecution;
	}
	public void setUpdatedPlaceOfExecution(String[] updatedPlaceOfExecution) {
		this.updatedPlaceOfExecution = updatedPlaceOfExecution;
	}
	public String[] getUpdatedFileBarCode() {
		return updatedFileBarCode;
	}
	public void setUpdatedFileBarCode(String[] updatedFileBarCode) {
		this.updatedFileBarCode = updatedFileBarCode;
	}
	public String[] getUpdatedBoxBarCode() {
		return updatedBoxBarCode;
	}
	public void setUpdatedBoxBarCode(String[] updatedBoxBarCode) {
		this.updatedBoxBarCode = updatedBoxBarCode;
	}
	public String[] getUpdatedRackNumber() {
		return updatedRackNumber;
	}
	public void setUpdatedRackNumber(String[] updatedRackNumber) {
		this.updatedRackNumber = updatedRackNumber;
	}
	public String[] getUpdatedLotNumber() {
		return updatedLotNumber;
	}
	public void setUpdatedLotNumber(String[] updatedLotNumber) {
		this.updatedLotNumber = updatedLotNumber;
	}
	public String[] getUpdatedDocBarcode() {
		return updatedDocBarcode;
	}
	public void setUpdatedDocBarcode(String[] updatedDocBarcode) {
		this.updatedDocBarcode = updatedDocBarcode;
	}
	
	
	public String[] getUpdatedDocAmount() {
		return updatedDocAmount;
	}
	public void setUpdatedDocAmount(String[] updatedDocAmount) {
		this.updatedDocAmount = updatedDocAmount;
	}
	
	
	
	public String getRemarkCheck() {
		return remarkCheck;
	}
	public void setRemarkCheck(String remarkCheck) {
		this.remarkCheck = remarkCheck;
	}
	public String[][] getMapper() {
		String[][] input = {  { "caseCreationUpdateObj", CASECREATION_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	
	public static final String CASECREATION_MAPPER = "com.integrosys.cms.ui.caseCreationUpdate.CaseCreationMapper";

	public static final String CASECREATION_LIST_MAPPER = "com.integrosys.cms.ui.caseCreationUpdate.CaseCreationListMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	
	
	
	public String getCoordinator1Name() {
		return coordinator1Name;
	}
	public void setCoordinator1Name(String coordinator1Name) {
		this.coordinator1Name = coordinator1Name;
	}
	public String getCoordinator2Name() {
		return coordinator2Name;
	}
	public void setCoordinator2Name(String coordinator2Name) {
		this.coordinator2Name = coordinator2Name;
	}
	public String getTxtSegment() {
		return txtSegment;
	}
	public void setTxtSegment(String txtSegment) {
		this.txtSegment = txtSegment;
	}
	public String getTxtRegion() {
		return txtRegion;
	}
	public void setTxtRegion(String txtRegion) {
		this.txtRegion = txtRegion;
	}
	public String getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getDisableForSelection() {
		return disableForSelection;
	}
	public void setDisableForSelection(String disableForSelection) {
		this.disableForSelection = disableForSelection;
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getLimitProfileId() {
		return limitProfileId;
	}
	public void setLimitProfileId(String limitProfileId) {
		this.limitProfileId = limitProfileId;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	

}
