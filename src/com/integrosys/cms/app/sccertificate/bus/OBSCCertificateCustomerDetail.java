/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/OBSCCertificateCustomerDetail.java,v 1.7 2005/07/20 03:36:37 lyng Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

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
 * @since $Date: 2005/07/20 03:36:37 $ Tag: $Name: $
 */
public class OBSCCertificateCustomerDetail implements ISCCertificateCustomerDetail {
	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String legalID = null;

	private String legalName = null;

	private String customerReference = null;

	private String customerName = null;

	private ICreditGrade creditGrade = null;

	private ICreditStatus creditStatus = null;

	private String customerSegmentCode = null;

	private Date approvalDate = null;

	private IBookingLocation orginatingLocation = null;

	private String famCode = null;

	private String famName = null;

	private Date nextReviewDate = null;

	private Date extReviewDate;

	private String approvalAuthority = null;

	private Date finalBFLIssuedDate = null;

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

	public String getCustomerSegmentCode() {
		return this.customerSegmentCode;
	}

	public Date getApprovalDate() {
		return this.approvalDate;
	}

	public IBookingLocation getOriginatingLocation() {
		return this.orginatingLocation;
	}

	public String getFamCode() {
		return this.famCode;
	}

	public String getFamName() {
		return this.famName;
	}

	public Date getNextReviewDate() {
		return this.nextReviewDate;
	}

	/**
	 * Get BCA extended review date.
	 * 
	 * @return Date
	 */
	public Date getExtReviewDate() {
		return extReviewDate;
	}

	public String getApprovalAuthority() {
		return this.approvalAuthority;
	}

	public Date getFinalBFLIssuedDate() {
		return this.finalBFLIssuedDate;
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

	public void setCustomerSegmentCode(String aCustomerSegmentCode) {
		this.customerSegmentCode = aCustomerSegmentCode;
	}

	public void setApprovalDate(Date anApprovalDate) {
		this.approvalDate = anApprovalDate;
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

	public void setNextReviewDate(Date aNextReviewDate) {
		this.nextReviewDate = aNextReviewDate;
	}

	/**
	 * Set BCA extended review date.
	 * 
	 * @param extReviewDate of type Date
	 */
	public void setExtReviewDate(Date extReviewDate) {
		this.extReviewDate = extReviewDate;
	}

	public void setApprovalAuthority(String anApprovalAuthority) {
		this.approvalAuthority = anApprovalAuthority;
	}

	public void setFinalBFLIssuedDate(Date aFinalBFLIssueDate) {
		this.finalBFLIssuedDate = aFinalBFLIssueDate;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
