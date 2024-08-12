package com.integrosys.cms.app.ecbf.counterparty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hdfcAPI.bean.ClimesBorrowerVo;

public interface IECBFCustomerInterfaceLog extends Serializable{
	
	List<String> copyProperties = Arrays.asList("address1", "address2", "assetClassification",// "assetClassificationValue",
			"bulkBorrowerName", "camDate", "camExpiryDate", "camNumber", "camSRmExpiryDate", "camSanctionedAmount", 
			"camType", "constitution", //"constitutionValue", 
			"contactNo", "dateOfBirth", "dateOfIncorporation", "emailId", "location", //"locationValue", 
			"panNumber",
			"partyId", "pinCode", "presentTurnOver", "previousTurnOver",
			"ramRating", //"ramRatingValue", 
			"rmName", "segmentName",
			//"segmentNameValue",
			"status");

	public long getId();
	public void setId(long id);
	
	public Date getRequestDateTime() ;
	public void setRequestDateTime(Date requestDateTime) ;
	
	public Date getResponseDateTime() ;
	public void setResponseDateTime(Date responseDateTime);
	
	public String getErrorMessage();
	public void setErrorMessage(String errorMessage) ;
	
	public String getInterfaceStatus();
	public void setInterfaceStatus(String interfaceStatus) ;
	
	public String getRequestMessage() ;
	public void setRequestMessage(String requestMessage) ;
	
	public String getResponseMessage() ;
	public void setResponseMessage(String responseMessage) ;
	
	public String getAddress1();
	public void setAddress1(String address1);

	public String getAddress2();
	public void setAddress2(String address2);

	public String getAssetClassification();
	public void setAssetClassification(String assetClassification);

	public String getBulkBorrowerName();
	public void setBulkBorrowerName(String bulkBorrowerName);

	public String getCamDate();
	public void setCamDate(String camDate);

	public String getCamExpiryDate();
	public void setCamExpiryDate(String camExpiryDate);

	public String getCamNumber();
	public void setCamNumber(String camNumber);

	public String getCamSRmExpiryDate();
	public void setCamSRmExpiryDate(String camSRmExpiryDate);

	public BigDecimal getCamSanctionedAmount();
	public void setCamSanctionedAmount(BigDecimal camSanctionedAmount);

	public String getCamType();
	public void setCamType(String camType);

	public String getConstitution();
	public void setConstitution(String constitution);

	public String getContactNo();
	public void setContactNo(String contactNo);

	public String getDateOfBirth();
	public void setDateOfBirth(String dateOfBirth);

	public String getDateOfIncorporation();
	public void setDateOfIncorporation(String dateOfIncorporation);

	public String getEmailId();
	public void setEmailId(String emailId);

	public String getLocation();
	public void setLocation(String location);

	public String getPanNumber();
	public void setPanNumber(String panNumber);

	public String getPartyId();
	public void setPartyId(String partyId);

	public String getPinCode();
	public void setPinCode(String pinCode) ;

	public BigDecimal getPresentTurnOver();
	public void setPresentTurnOver(BigDecimal presentTurnOver);

	public BigDecimal getPreviousTurnOver();
	public void setPreviousTurnOver(BigDecimal previousTurnOver);

	public String getRamRating();
	public void setRamRating(String ramRating);

	public String getRmName();
	public void setRmName(String rmName);

	public String getSegmentName(); 
	public void setSegmentName(String segmentName);
	
	public String getAssetClassificationValue();
	public void setAssetClassificationValue(String assetClassificationValue);

	public String getConstitutionValue();
	public void setConstitutionValue(String constitutionValue);

	public String getLocationValue();
	public void setLocationValue(String locationValue);

	public String getRamRatingValue();
	public void setRamRatingValue(String ramRatingValue);

	public String getSegmentNameValue();
	public void setSegmentNameValue(String segmentNameValue);
	
	public String getResponsePartyId();
	public void setResponsePartyId(String responsePartyId);
	

	public String getIs_udf_upload();
    public void setIs_udf_upload(String is_udf_upload);

	public ClimesBorrowerVo toRequest();
}