/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/ISCCertificateCustomerDetail.java,v 1.6 2005/07/20 03:36:37 lyng Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

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
 * @version $Revision: 1.6 $
 * @since $Date: 2005/07/20 03:36:37 $ Tag: $Name: $
 */
public interface ISCCertificateCustomerDetail extends Serializable {
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

	public String getCustomerSegmentCode();

	public Date getApprovalDate();

	public IBookingLocation getOriginatingLocation();

	public String getFamCode();

	public String getFamName();

	public Date getNextReviewDate();

	/**
	 * Get BCA extended review date.
	 * 
	 * @return Date
	 */
	public Date getExtReviewDate();

	public String getApprovalAuthority();

	public Date getFinalBFLIssuedDate();

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

	public void setCustomerSegmentCode(String aCustomerSegmentCode);

	public void setApprovalDate(Date anApprovalDate);

	public void setOriginatingLocation(IBookingLocation anOriginatingLocation);

	public void setFamCode(String aFamCode);

	public void setFamName(String aFamName);

	public void setNextReviewDate(Date aNextReviewDate);

	/**
	 * Set BCA extended review date.
	 * 
	 * @param extReviewDate of type Date
	 */
	public void setExtReviewDate(Date extReviewDate);

	public void setApprovalAuthority(String anApprovalAuthority);

	public void setFinalBFLIssuedDate(Date aFinalBFLIssuedDate);
}
