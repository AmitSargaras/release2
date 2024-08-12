/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/IPledgor.java,v 1.6 2004/03/24 02:58:04 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;

import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface represents pledgor information.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/03/24 02:58:04 $ Tag: $Name: $
 */
public interface IPledgor extends Serializable {
	/**
	 * Get pledgor ID, the primary key in CMS.
	 * 
	 * @return long
	 */
	public long getPledgorID();

	/**
	 * Set pledgor ID, the primary key in CMS.
	 * 
	 * @param pledgorID is of type long
	 */
	public void setPledgorID(long pledgorID);

	/**
	 * Get system generate pledgor id from SCI.
	 * 
	 * @return long
	 */
	public long getSysGenPledgorID();

	/**
	 * Set system generate pledgor id from SCI.
	 * 
	 * @param sysGenPledgorID of type long
	 */
	public void setSysGenPledgorID(long sysGenPledgorID);

	/**
	 * Get plegor's name.
	 * 
	 * @return String
	 */
	public String getPledgorName();

	/**
	 * Set the pledgor name.
	 * 
	 * @param pledgorName is of type String
	 */
	public void setPledgorName(String pledgorName);

	/**
	 * Get pledgor relationship number.
	 * 
	 * @return String
	 */
	public String getPledgorRelshipID();

	/**
	 * Set pledgor relationship number.
	 * 
	 * @param pledgorRelshipID of type String
	 */
	public void setPledgorRelshipID(String pledgorRelshipID);

	/**
	 * Get pledgor's relationship.
	 * 
	 * @return String
	 */
	public String getPledgorRelship();

	/**
	 * Set pledgor's relationship.
	 * 
	 * @param pledgorRelship is of type String
	 */
	public void setPledgorRelship(String pledgorRelship);

	/**
	 * Get legal type number.
	 * 
	 * @return long
	 */
	public long getLegalTypeID();

	/**
	 * Set legal type number.
	 * 
	 * @param legalTypeID of type long
	 */
	public void setLegalTypeID(long legalTypeID);

	/**
	 * Get legal type value.
	 * 
	 * @return String
	 */
	public String getLegalType();

	/**
	 * Set legal type value.
	 * 
	 * @param legalType of type String
	 */
	public void setLegalType(String legalType);

	/**
	 * Get pledgor's legal id.
	 * 
	 * @return String
	 */
	public String getLegalID();

	/**
	 * Set pledgor's legal id.
	 * 
	 * @param legalID of type String
	 */
	public void setLegalID(String legalID);

	/**
	 * Get domicile country code.
	 * 
	 * @return String
	 */
	public String getDomicileCountry();

	/**
	 * Set domicile country code.
	 * 
	 * @param domicileCountry of type String
	 */
	public void setDomicileCountry(String domicileCountry);

	/**
	 * Get segment code.
	 * 
	 * @return String
	 */
	public String getSegmentCode();

	/**
	 * Set segment code.
	 * 
	 * @param segmentCode of type String
	 */
	public void setSegmentCode(String segmentCode);

	/**
	 * Get pledgor credit grades.
	 * 
	 * @return IPledgorCreditGrade[]
	 */
	public IPledgorCreditGrade[] getCreditGrades();

	/**
	 * Set pledgor credit grades.
	 * 
	 * @param creditGrades of type IPledgorCreditGrade[]
	 */
	public void setCreditGrades(IPledgorCreditGrade[] creditGrades);

	/**
	 * Get pledgor instruction booking location.
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getBookingLocation();

	/**
	 * Set pledgor instruction booking location.
	 * 
	 * @param bookingLocation of type IBookingLocation
	 */
	public void setBookingLocation(IBookingLocation bookingLocation);

	/**
	 * Get pledgor update status indicator.
	 * 
	 * @return String
	 */
	public String getPledgorStatus();

	/**
	 * Set pledgor update status indicator.
	 * 
	 * @param pledgorStatus of type String
	 */
	public void setPledgorStatus(String pledgorStatus);

	/**
	 * Get pledgor's id country.
	 * 
	 * @return String
	 */
	public String getPlgIdCountry();

	/**
	 * Set pledgor's id country.
	 * 
	 * @param plgIdCountry of type String
	 */
	public void setPlgIdCountry(String plgIdCountry);

	/**
	 * Get pledgor's id no.
	 * 
	 * @return String
	 */
	public String getPlgIdNumText();

	/**
	 * Set pledgor's id no.
	 * 
	 * @param plgIdNumText of type String
	 */
	public void setPlgIdNumText(String plgIdNumText);

	/**
	 * Get pledgor's id type code.
	 * 
	 * @return String
	 */
	public String getPlgIdTypeCode();

	/**
	 * Set pledgor's id type code.
	 * 
	 * @param plgIdTypeCode of type String
	 */
	public void setPlgIdTypeCode(String plgIdTypeCode);

	/**
	 * Get pledgor's id type value.
	 * 
	 * @return String
	 */
	public String getPlgIdType();

	/**
	 * Set pledgor's id type value.
	 * 
	 * @param plgIdType of type String
	 */
	public void setPlgIdType(String plgIdType);

	/**
	 * Get source ID.
	 * 
	 * @return String
	 */
	public String getSourceId();

	/**
	 * Set source ID.
	 * 
	 * @param sourceId of type String
	 */
	public void setSourceId(String sourceId);

	/**
	 * Get source (code) of pledgor's legal id.
	 * 
	 * @return String
	 */
	public String getLegalIDSourceCode();

	/**
	 * Set source (code) of pledgor's legal id.
	 * 
	 * @param value of type String
	 */
	public void setLegalIDSourceCode(String value);

	/**
	 * Get source (value) of pledgor's legal id.
	 * 
	 * @return String
	 */
	public String getLegalIDSource();

	/**
	 * Set source (value) of pledgor's legal id.
	 * 
	 * @param value of type String
	 */
	public void setLegalIDSource(String value);
}
