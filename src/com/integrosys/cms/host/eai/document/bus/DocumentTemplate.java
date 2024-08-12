/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/DocumentSecurity.java,v 1.3 2003/12/08 07:13:37 lyng Exp $
 */
package com.integrosys.cms.host.eai.document.bus;


/**
 * This class represents approved security of type Document.
 * 
 * @author $Author: Iwan Satria $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2008/11/27 07:13:37 $ Tag: $Name: $
 */
public class DocumentTemplate  implements java.io.Serializable {
	/**
	 * Default constructor.
	 */
	public DocumentTemplate() {
		super();
	}

	private long masterlistID;
	
	private long versionTime;
	
	private String country;
	
	private String category;
	
	private String applicableLaw;
	
	private String securitySubType;
	
	private String securityType;
	
	private long fromTemplateID;
	
	private String customerType;
	
	private String collateralId;

	public String getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(String collateralId) {
		this.collateralId = collateralId;
	}

	public long getMasterlistID() {
		return masterlistID;
	}

	public void setMasterlistID(long masterlistID) {
		this.masterlistID = masterlistID;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getApplicableLaw() {
		return applicableLaw;
	}

	public void setApplicableLaw(String applicableLaw) {
		this.applicableLaw = applicableLaw;
	}

	public String getSecuritySubType() {
		return securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public long getFromTemplateID() {
		return fromTemplateID;
	}

	public void setFromTemplateID(long fromTemplateID) {
		this.fromTemplateID = fromTemplateID;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	
}
