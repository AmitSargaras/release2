package com.integrosys.cms.app.imageTag.bus;
/**
*@author abhijit.rudrakshawar
*
*OB for Image Tag 
*/


public class OBImageTag implements IImageTag {
	
	
	private static final long serialVersionUID = 1L;
	
	private String userID;

	private String subProfileID;

	private String legalName;

	private String customerName;

	private String legalID;

	private String leIDType;

	private String idNO;




	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getSubProfileID() {
		return subProfileID;
	}

	public void setSubProfileID(String subProfileID) {
		this.subProfileID = subProfileID;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getLegalID() {
		return legalID;
	}

	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	public String getLeIDType() {
		return leIDType;
	}

	public void setLeIDType(String leIDType) {
		this.leIDType = leIDType;
	}

	public String getIdNO() {
		return idNO;
	}

	public void setIdNO(String idNO) {
		this.idNO = idNO;
	}


	private Long custID;
	
	private String CustName = "";

	private String CustCIF = "";

	public Long getCustID() {
		return custID;
	}

	public void setCustID(Long custID) {
		this.custID = custID;
	}

	public String getCustName() {
		return CustName;
	}

	public void setCustName(String custName) {
		CustName = custName;
	}

	public String getCustCIF() {
		return CustCIF;
	}

	public void setCustCIF(String custCIF) {
		CustCIF = custCIF;
	}
	
}
