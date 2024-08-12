/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/OBCCCertificateCustomerDetail.java,v 1.7 2005/08/26 04:18:11 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.customer.bus.ICreditGrade;
import com.integrosys.cms.app.customer.bus.ICreditStatus;

/**
 * This class provides the implementation for the ICertificateCustomerDetail
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/26 04:18:11 $ Tag: $Name: $
 */
public class OBCCCertificateCustomerDetail implements ICCCertificateCustomerDetail {
	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String legalID = null;

	private String legalName = null;

	private String customerReference = null;

	private String customerName = null;

	private ICreditGrade creditGrade = null;

	private ICreditStatus creditStatus = null;

	private Date finalBFLIssuedDate = null;

	private String customerSegmentCode = null;

	private Date approvalDate = null;

	private Date nextReviewDate;

	private Date extReviewDate;

	private String approvalAuthority;

	private IBookingLocation orginatingLocation = null;

	private String famCode = null;

	private String famName = null;

	/**
	 * Get the customer ID
	 * @return long - the customer ID
	 */
	public long getCustomerID() {
		return this.customerID;
	}

	/**
	 * Get the legal ID
	 * @return String - the legal ID
	 */
	public String getLegalID() {
		return this.legalID;
	}

	/**
	 * Get the legal name
	 * @return String - the legal name
	 */
	public String getLegalName() {
		return this.legalName;
	}

	/**
	 * Get the customer reference
	 * @return String - the customer reference
	 */
	public String getCustomerReference() {
		return this.customerReference;
	}

	/**
	 * Get the customer name
	 * @return String - the customer name
	 */
	public String getCustomerName() {
		return this.customerName;
	}

	/**
	 * Get the credit grade
	 * @return ICreditGrade - the credit grade
	 */
	public ICreditGrade getCreditGrade() {
		return this.creditGrade;
	}

	/**
	 * Get the credit status
	 * @return ICreditStatus - the credit status
	 */
	public ICreditStatus getCreditStatus() {
		return this.creditStatus;
	}

	public Date getFinalBFLIssuedDate() {
		return this.finalBFLIssuedDate;
	}

	public String getCustomerSegmentCode() {
		return this.customerSegmentCode;
	}

	public Date getApprovalDate() {
		return this.approvalDate;
	}

	/**
	 * Get bca next annual review date.
	 * 
	 * @return Date
	 */
	public Date getNextReviewDate() {
		return nextReviewDate;
	}

	/**
	 * Get extended next review date.
	 * 
	 * @return Date
	 */
	public Date getExtReviewDate() {
		return extReviewDate;
	}

	/**
	 * Get BCA approval authority.
	 * 
	 * @return String
	 */
	public String getApprovalAuthority() {
		return approvalAuthority;
	}

	public IBookingLocation getOriginatingLocation() {
		return this.orginatingLocation;
	}

	public String getFamCode() {
		if (this.famCode == null) {
			return "";
		}
		return this.famCode;
	}

	public String getFamName() {
		if (this.famName == null) {
			return "";
		}
		return this.famName;
	}

	/**
	 * Set the customer ID
	 * @param aCustomerID of long type
	 */
	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
	}

	/**
	 * Set the legal ID
	 * @param aLegalID of String type
	 */
	public void setLegalID(String aLegalID) {
		this.legalID = aLegalID;
	}

	/**
	 * Set the legal name
	 * @param aLegalName of String type
	 */
	public void setLegalName(String aLegalName) {
		this.legalName = aLegalName;
	}

	/**
	 * Set the customer reference
	 * @param aCustomerReference of String type
	 */
	public void setCustomerReference(String aCustomerReference) {
		this.customerReference = aCustomerReference;
	}

	/**
	 * Set the customer name
	 * @param aCustomerName of String type
	 */
	public void setCustomerName(String aCustomerName) {
		this.customerName = aCustomerName;
	}

	/**
	 * Set the credit grade
	 * @param aCreditGrade of ICreditGrade type
	 */
	public void setCreditGrade(ICreditGrade aCreditGrade) {
		this.creditGrade = aCreditGrade;
	}

	/**
	 * Set the credit status
	 * @param aCreditStatus of ICreditStatus type
	 */
	public void setCreditStatus(ICreditStatus aCreditStatus) {
		this.creditStatus = aCreditStatus;
	}

	public void setFinalBFLIssuedDate(Date aFinalBFLIssuedDate) {
		this.finalBFLIssuedDate = aFinalBFLIssuedDate;
	}

	public void setCustomerSegmentCode(String aCustomerSegmentCode) {
		this.customerSegmentCode = aCustomerSegmentCode;
	}

	public void setApprovalDate(Date anApprovalDate) {
		this.approvalDate = anApprovalDate;
	}

	/**
	 * Set BCA next annual review date.
	 * 
	 * @param nextReviewDate of type Date
	 */
	public void setNextReviewDate(Date nextReviewDate) {
		this.nextReviewDate = nextReviewDate;
	}

	/**
	 * Set next extended review date.
	 * 
	 * @param extReviewDate of type Date
	 */
	public void setExtReviewDate(Date extReviewDate) {
		this.extReviewDate = extReviewDate;
	}

	/**
	 * Set BCA approval authority.
	 * 
	 * @param approvalAuthority of type String
	 */
	public void setApprovalAuthority(String approvalAuthority) {
		this.approvalAuthority = approvalAuthority;
	}

	public void setOriginatingLocation(IBookingLocation anOriginatingLocation) {
		this.orginatingLocation = anOriginatingLocation;
	}

	public void setFamCode(String aFamCode) {
		this.famCode = aFamCode;
	}

	public void setFamName(String aFamName) {
		this.famName = aFamName;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
