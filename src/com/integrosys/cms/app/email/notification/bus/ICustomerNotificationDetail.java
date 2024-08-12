package com.integrosys.cms.app.email.notification.bus;

import java.io.Serializable;

public interface ICustomerNotificationDetail extends Serializable{
	
	public String getPartyName();
	public void setPartyName(String partyName) ;
	
	public String getLadDueDate() ;
	public void setLadDueDate(String ladDueDate);
	
	public String getLadExpiryDate();
	public void setLadExpiryDate(String ladExpiryDate);
	
	public String getCamExpiryDate() ;
	public void setCamExpiryDate(String camExpiryDate);
	
	public String getCamCreationDate();
	public void setCamCreationDate(String camCreationDate) ;
	
	public String getSecurityMaturityDate();
	public void setSecurityMaturityDate(String securityMaturityDate) ;
	
	public String getSecuritySubType() ;
	public void setSecuritySubType(String securitySubType) ;

	public String getInsuranceMaturityDate();
	public void setInsuranceMaturityDate(String insuranceMaturityDate);

	public String getDrawingPower();
	public void setDrawingPower(String drawingPower) ;
	
	public String getReleasableAmount() ;
	public void setReleasableAmount(String releasableAmount) ;
	
	public String getStatementType();
	public void setStatementType(String statementType);
	
	public String getDocDueDate();
	public void setDocDueDate(String docDueDate) ;
	
	public String getPartyId();
	public void setPartyId(String partyId) ;
	
	public String getInsMsgString();
	public void setInsMsgString(String insMsgString);

	public String getSegment();
	public void setSegment(String segment);
	
	public String getBranch();
	public void setBranch(String branch);
	
	public String getFacilityLineNo();
	public void setFacilityLineNo(String facilityLineNo);
	
	public String getFacilitySerialNo();
	public void setFacilitySerialNo(String facilitySerialNo);
	
	public String getDescription();
	public void setDescription(String description);
	
	public String getClosingBalance();
	public void setClosingBalance(String closingBalance);
	
	public OBCaseCreationNotificationDetail[] getCaseCreationNotificationDetail() ;
	public void setCaseCreationNotificationDetail(OBCaseCreationNotificationDetail[] caseCreationNotificationDetail) ;
}
