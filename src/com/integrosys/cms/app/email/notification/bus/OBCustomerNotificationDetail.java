package com.integrosys.cms.app.email.notification.bus;


public class OBCustomerNotificationDetail implements ICustomerNotificationDetail {

	private String camExpiryDate;
	private String camCreationDate;
	private String ladDueDate;
	private String ladExpiryDate;
	private String partyName;

	private String securityMaturityDate;
	private String securitySubType;
	
	private String insuranceMaturityDate;

	private String drawingPower;
	private String releasableAmount;
	
	private String statementType;
	private String docDueDate;
	
	private String partyId;
	
	private String insMsgString;
	
	private String segment ;
	
	private String branch;
	
	private String facilityLineNo;
	
	private String facilitySerialNo;
	
	private String description;
	
	private OBCaseCreationNotificationDetail[] caseCreationNotificationDetail;
	
	private String closingBalance;
	
	
	
	
	
	public OBCaseCreationNotificationDetail[] getCaseCreationNotificationDetail() {
		return caseCreationNotificationDetail;
	}
	public void setCaseCreationNotificationDetail(
			OBCaseCreationNotificationDetail[] caseCreationNotificationDetail) {
		this.caseCreationNotificationDetail = caseCreationNotificationDetail;
	}
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getInsMsgString() {
		return insMsgString;
	}
	public void setInsMsgString(String insMsgString) {
		this.insMsgString = insMsgString;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getLadDueDate() {
		return ladDueDate;
	}
	public void setLadDueDate(String ladDueDate) {
		this.ladDueDate = ladDueDate;
	}
	public String getLadExpiryDate() {
		return ladExpiryDate;
	}
	public void setLadExpiryDate(String ladExpiryDate) {
		this.ladExpiryDate = ladExpiryDate;
	}
	public String getCamExpiryDate() {
		return camExpiryDate;
	}
	public void setCamExpiryDate(String camExpiryDate) {
		this.camExpiryDate = camExpiryDate;
	}
	public String getCamCreationDate() {
		return camCreationDate;
	}
	public void setCamCreationDate(String camCreationDate) {
		this.camCreationDate = camCreationDate;
	}
	public String getSecurityMaturityDate() {
		return securityMaturityDate;
	}
	public void setSecurityMaturityDate(String securityMaturityDate) {
		this.securityMaturityDate = securityMaturityDate;
	}
	public String getSecuritySubType() {
		return securitySubType;
	}
	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}
	public String getInsuranceMaturityDate() {
		return insuranceMaturityDate;
	}
	public void setInsuranceMaturityDate(String insuranceMaturityDate) {
		this.insuranceMaturityDate = insuranceMaturityDate;
	}
	public String getDrawingPower() {
		return drawingPower;
	}
	public void setDrawingPower(String drawingPower) {
		this.drawingPower = drawingPower;
	}
	public String getReleasableAmount() {
		return releasableAmount;
	}
	public void setReleasableAmount(String releasableAmount) {
		this.releasableAmount = releasableAmount;
	}
	/**
	 * @return the statementType
	 */
	public String getStatementType() {
		return statementType;
	}
	/**
	 * @param statementType the statementType to set
	 */
	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}
	/**
	 * @return the docDueDate
	 */
	public String getDocDueDate() {
		return docDueDate;
	}
	/**
	 * @param docDueDate the docDueDate to set
	 */
	public void setDocDueDate(String docDueDate) {
		this.docDueDate = docDueDate;
	}
	/**
	 * @return the partyId
	 */
	public String getPartyId() {
		return partyId;
	}
	/**
	 * @param partyId the partyId to set
	 */
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	
	public String getFacilityLineNo() {
		return facilityLineNo;
	}
	public void setFacilityLineNo(String facilityLineNo) {
		this.facilityLineNo = facilityLineNo;
	}
	public String getFacilitySerialNo() {
		return facilitySerialNo;
	}
	public void setFacilitySerialNo(String facilitySerialNo) {
		this.facilitySerialNo = facilitySerialNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getClosingBalance() {
		return closingBalance;
	}
	public void setClosingBalance(String closingBalance) {
		this.closingBalance = closingBalance;
	}

	
	
	
	}
