package com.integrosys.cms.app.ecbf.counterparty;

import java.math.BigDecimal;
import java.util.Date;

import com.hdfcAPI.bean.ClimesBorrowerVo;
import com.integrosys.cms.host.eai.support.ReflectionUtils;

public class OBECBFCustomerInterfaceLog implements IECBFCustomerInterfaceLog {

	private long id;
	private Date requestDateTime;
	private Date responseDateTime;
	private String errorMessage;
	private String requestMessage;
	private String responseMessage;
	private String interfaceStatus;
	
	private String address1;
	private String address2;
    private String assetClassification;
    private String assetClassificationValue;
    private String bulkBorrowerName;
    private String camDate;
    private String camExpiryDate;
    private String camNumber;
	private String camSRmExpiryDate;
    private BigDecimal camSanctionedAmount;
	private String camType;
    private String constitution;
    private String constitutionValue;
	private String contactNo;
    private String dateOfBirth;
    private String dateOfIncorporation;
    private String emailId;
    private String location;
    private String locationValue;
    private String panNumber;
    private String partyId;
    private String pinCode;
    private BigDecimal presentTurnOver;
    private BigDecimal previousTurnOver;
    private String ramRating;
    private String ramRatingValue;
    private String rmName;
    private String segmentName;
    private String segmentNameValue;
	private String status;
	
	private String responsePartyId;
	private String is_udf_upload;
	
	public OBECBFCustomerInterfaceLog() {}
	
	public OBECBFCustomerInterfaceLog(ClimesBorrowerVo request) {
		copyFields(request, this);
		
		if(request.getTransactionrefNum() != null && !request.getTransactionrefNum().equals("0"))
			this.setId(Long.parseLong(request.getTransactionrefNum()));
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Date getRequestDateTime() {
		return requestDateTime;
	}
	public void setRequestDateTime(Date requestDateTime) {
		this.requestDateTime = requestDateTime;
	}
	
	public Date getResponseDateTime() {
		return responseDateTime;
	}
	public void setResponseDateTime(Date responseDateTime) {
		this.responseDateTime = responseDateTime;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getAssetClassification() {
		return assetClassification;
	}
	public void setAssetClassification(String assetClassification) {
		this.assetClassification = assetClassification;
	}
	
	public String getBulkBorrowerName() {
		return bulkBorrowerName;
	}
	public void setBulkBorrowerName(String bulkBorrowerName) {
		this.bulkBorrowerName = bulkBorrowerName;
	}
	
	public String getCamDate() {
		return camDate;
	}
	public void setCamDate(String camDate) {
		this.camDate = camDate;
	}
	
	public String getCamExpiryDate() {
		return camExpiryDate;
	}
	public void setCamExpiryDate(String camExpiryDate) {
		this.camExpiryDate = camExpiryDate;
	}
	
	public String getCamNumber() {
		return camNumber;
	}
	public void setCamNumber(String camNumber) {
		this.camNumber = camNumber;
	}
	
	public String getCamSRmExpiryDate() {
		return camSRmExpiryDate;
	}
	public void setCamSRmExpiryDate(String camSRmExpiryDate) {
		this.camSRmExpiryDate = camSRmExpiryDate;
	}
	
	public BigDecimal getCamSanctionedAmount() {
		return camSanctionedAmount;
	}
	public void setCamSanctionedAmount(BigDecimal camSanctionedAmount) {
		this.camSanctionedAmount = camSanctionedAmount;
	}
	
	public String getCamType() {
		return camType;
	}
	public void setCamType(String camType) {
		this.camType = camType;
	}
	
	public String getConstitution() {
		return constitution;
	}
	public void setConstitution(String constitution) {
		this.constitution = constitution;
	}
	
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getDateOfIncorporation() {
		return dateOfIncorporation;
	}
	public void setDateOfIncorporation(String dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}
	
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	
	public BigDecimal getPresentTurnOver() {
		return presentTurnOver;
	}
	public void setPresentTurnOver(BigDecimal presentTurnOver) {
		this.presentTurnOver = presentTurnOver;
	}
	
	public BigDecimal getPreviousTurnOver() {
		return previousTurnOver;
	}
	public void setPreviousTurnOver(BigDecimal previousTurnOver) {
		this.previousTurnOver = previousTurnOver;
	}
	
	public String getRamRating() {
		return ramRating;
	}
	public void setRamRating(String ramRating) {
		this.ramRating = ramRating;
	}
	
	public String getRmName() {
		return rmName;
	}
	public void setRmName(String rmName) {
		this.rmName = rmName;
	}
	
	public String getSegmentName() {
		return segmentName;
	}
	public void setSegmentName(String segmentName) {
		this.segmentName = segmentName;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getInterfaceStatus() {
		return interfaceStatus;
	}
	public void setInterfaceStatus(String interfaceStatus) {
		this.interfaceStatus = interfaceStatus;
	}
	
	public String getRequestMessage() {
		return requestMessage;
	}
	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	public String getResponsePartyId() {
		return responsePartyId;
	}
	public void setResponsePartyId(String responsePartyId) {
		this.responsePartyId = responsePartyId;
		
	}
	
	public String getAssetClassificationValue() {
		return assetClassificationValue;
	}

	public void setAssetClassificationValue(String assetClassificationValue) {
		this.assetClassificationValue = assetClassificationValue;
	}

	public String getConstitutionValue() {
		return constitutionValue;
	}

	public void setConstitutionValue(String constitutionValue) {
		this.constitutionValue = constitutionValue;
	}

	public String getLocationValue() {
		return locationValue;
	}

	public void setLocationValue(String locationValue) {
		this.locationValue = locationValue;
	}

	public String getRamRatingValue() {
		return ramRatingValue;
	}

	public void setRamRatingValue(String ramRatingValue) {
		this.ramRatingValue = ramRatingValue;
	}

	public String getSegmentNameValue() {
		return segmentNameValue;
	}

	public void setSegmentNameValue(String segmentNameValue) {
		this.segmentNameValue = segmentNameValue;
	}

	public String getIs_udf_upload() {
		return is_udf_upload;
	}

	public void setIs_udf_upload(String is_udf_upload) {
		this.is_udf_upload = is_udf_upload;
	}
	
	public ClimesBorrowerVo toRequest() {
		ClimesBorrowerVo request = new ClimesBorrowerVo();
		
		if(this.getId() > 0)
			request.setTransactionrefNum(String.valueOf(this.getId()));
		
		copyFields(this, request);
		
		return request;
	}
	
	private void copyFields(Object src, Object dest) {
		ReflectionUtils.copyValuesByProperties(src, dest, copyProperties);
	}

}