/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/ICCCertificateCustomerDetail.java,v 1.5 2005/08/26 04:18:11 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.customer.bus.ICreditGrade;
import com.integrosys.cms.app.customer.bus.ICreditStatus;

/**
 * This interface defines the list of attributes that is required for a
 * certificate customer
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/08/26 04:18:11 $ Tag: $Name: $
 */
public interface ICCCertificateCustomerDetail extends Serializable {
	/**
	 * Get the customer ID
	 * @return long - the customer ID
	 */
	public long getCustomerID();

	/**
	 * Get the legal ID
	 * @return String - the legal ID
	 */
	public String getLegalID();

	/**
	 * Get the legal name
	 * @return String - the legal name
	 */
	public String getLegalName();

	/**
	 * Get the customer reference
	 * @return String - the customer reference
	 */
	public String getCustomerReference();

	/**
	 * Get the customer name
	 * @return String - the customer name
	 */
	public String getCustomerName();

	/**
	 * Get the credit grade
	 * @return ICreditGrade - the credit grade
	 */
	public ICreditGrade getCreditGrade();

	/**
	 * Get the credit status
	 * @return ICreditStatus - the credit status
	 */
	public ICreditStatus getCreditStatus();

	public Date getFinalBFLIssuedDate();

	public String getCustomerSegmentCode();

	public Date getApprovalDate();

	/**
	 * Get bca next annual review date.
	 * 
	 * @return Date
	 */
	public Date getNextReviewDate();

	/**
	 * Get extended next review date.
	 * 
	 * @return Date
	 */
	public Date getExtReviewDate();

	/**
	 * Get BCA approval authority.
	 * 
	 * @return String
	 */
	public String getApprovalAuthority();

	public IBookingLocation getOriginatingLocation();

	public String getFamCode();

	public String getFamName();

	/**
	 * Set the customer ID
	 * @param aCustomerID of long type
	 */
	public void setCustomerID(long aCustomerID);

	/**
	 * Set the legal ID
	 * @param aLegalID of String type
	 */
	public void setLegalID(String aLegalID);

	/**
	 * Set the legal name
	 * @param aLegalName of String type
	 */
	public void setLegalName(String aLegalName);

	/**
	 * Set the customer reference
	 * @param aCustomerReference of String type
	 */
	public void setCustomerReference(String aCustomerReference);

	/**
	 * Set the customer name
	 * @param aCustomerName of String type
	 */
	public void setCustomerName(String aCustomerName);

	/**
	 * Set the credit grade
	 * @param aCreditGrade of ICreditGrade type
	 */
	public void setCreditGrade(ICreditGrade aCreditGrade);

	/**
	 * Set the credit status
	 * @param aCreditStatus of ICreditStatus type
	 */
	public void setCreditStatus(ICreditStatus aCreditStatus);

	public void setFinalBFLIssuedDate(Date aFinalBFLIssuedDate);

	public void setCustomerSegmentCode(String aCustomerSegmentCode);

	public void setApprovalDate(Date anApprovalDate);

	/**
	 * Set BCA next annual review date.
	 * 
	 * @param nextReviewDate of type Date
	 */
	public void setNextReviewDate(Date nextReviewDate);

	/**
	 * Set next extended review date.
	 * 
	 * @param extReviewDate of type Date
	 */
	public void setExtReviewDate(Date extReviewDate);

	/**
	 * Set BCA approval authority.
	 * 
	 * @param approvalAuthority of type String
	 */
	public void setApprovalAuthority(String approvalAuthority);

	public void setOriginatingLocation(IBookingLocation anOriginatingLocation);

	public void setFamCode(String aFamCode);

	public void setFamName(String aFamName);
}
