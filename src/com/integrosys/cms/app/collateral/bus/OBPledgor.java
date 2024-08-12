/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBPledgor.java,v 1.6 2004/03/24 03:06:44 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents pledgor information.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/03/24 03:06:44 $ Tag: $Name: $
 */
public class OBPledgor implements IPledgor {
	private long pledgorID = ICMSConstant.LONG_MIN_VALUE;

	private String legalID;

	private long sysGenPledgorID;

	private String pledgorName;

	private String pledgorRelshipID;

	private String pledgorRelship;

	private long legalTypeID;

	private String legalType;

	private String domicileCountry;

	private String segmentCode;

	private IPledgorCreditGrade[] creditGrades;

	private IBookingLocation bookingLocation;

	private String pledgorStatus;

	private String sourceId;

	private String plgIdCountry;

	private String plgIdNumText;

	private String plgIdTypeCode;

	private String plgIdType;

	private String legalIDSourceCode;

	private String legalIDSource;

	/**
	 * Default Constructor.
	 */
	public OBPledgor() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IPledgor
	 */
	public OBPledgor(IPledgor obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get pledgor ID, the primary key in CMS.
	 * 
	 * @return long
	 */
	public long getPledgorID() {
		return pledgorID;
	}

	/**
	 * Set pledgor ID, the primary key in CMS.
	 * 
	 * @param pledgorID is of type long
	 */
	public void setPledgorID(long pledgorID) {
		this.pledgorID = pledgorID;
	}

	/**
	 * Get pledgor's legal id.
	 * 
	 * @return String
	 */
	public String getLegalID() {
		return legalID;
	}

	/**
	 * Set pledgor's legal id.
	 * 
	 * @param legalID of type String
	 */
	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	/**
	 * Get system generate pledgor id from SCI.
	 * 
	 * @return long
	 */
	public long getSysGenPledgorID() {
		return sysGenPledgorID;
	}

	/**
	 * Set system generate pledgor id from SCI.
	 * 
	 * @param sysGenPledgorID of type long
	 */
	public void setSysGenPledgorID(long sysGenPledgorID) {
		this.sysGenPledgorID = sysGenPledgorID;
	}

	/**
	 * Get plegor's name.
	 * 
	 * @return String
	 */
	public String getPledgorName() {
		return pledgorName;
	}

	/**
	 * Set the pledgor name.
	 * 
	 * @param pledgorName is of type String
	 */
	public void setPledgorName(String pledgorName) {
		this.pledgorName = pledgorName;
	}

	/**
	 * Get pledgor relationship number.
	 * 
	 * @return String
	 */
	public String getPledgorRelshipID() {
		return pledgorRelshipID;
	}

	/**
	 * Set pledgor relationship number.
	 * 
	 * @param pledgorRelshipID of type long
	 */
	public void setPledgorRelshipID(String pledgorRelshipID) {
		this.pledgorRelshipID = pledgorRelshipID;
	}

	/**
	 * Get pledgor's relationship.
	 * 
	 * @return String
	 */
	public String getPledgorRelship() {
		return pledgorRelship;
	}

	/**
	 * Set pledgor's relationship.
	 * 
	 * @param pledgorRelship is of type String
	 */
	public void setPledgorRelship(String pledgorRelship) {
		this.pledgorRelship = pledgorRelship;
	}

	/**
	 * Get legal type number.
	 * 
	 * @return long
	 */
	public long getLegalTypeID() {
		return legalTypeID;
	}

	/**
	 * Set legal type number.
	 * 
	 * @param legalTypeID of type long
	 */
	public void setLegalTypeID(long legalTypeID) {
		this.legalTypeID = legalTypeID;
	}

	/**
	 * Get legal type value.
	 * 
	 * @return String
	 */
	public String getLegalType() {
		return legalType;
	}

	/**
	 * Set legal type value.
	 * 
	 * @param legalType of type String
	 */
	public void setLegalType(String legalType) {
		this.legalType = legalType;
	}

	/**
	 * Get domicile country code.
	 * 
	 * @return String
	 */
	public String getDomicileCountry() {
		return domicileCountry;
	}

	/**
	 * Set domicile country code.
	 * 
	 * @param domicileCountry of type String
	 */
	public void setDomicileCountry(String domicileCountry) {
		this.domicileCountry = domicileCountry;
	}

	/**
	 * Get segment code.
	 * 
	 * @return String
	 */
	public String getSegmentCode() {
		return segmentCode;
	}

	/**
	 * Set segment code.
	 * 
	 * @param segmentCode of type String
	 */
	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}

	/**
	 * Get pledgor credit grades.
	 * 
	 * @return IPledgorCreditGrade[]
	 */
	public IPledgorCreditGrade[] getCreditGrades() {
		return creditGrades;
	}

	/**
	 * Set pledgor credit grades.
	 * 
	 * @param creditGrades of type IPledgorCreditGrade[]
	 */
	public void setCreditGrades(IPledgorCreditGrade[] creditGrades) {
		this.creditGrades = creditGrades;
	}

	/**
	 * Get pledgor instruction booking location.
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getBookingLocation() {
		return bookingLocation;
	}

	/**
	 * Set pledgor instruction booking location.
	 * 
	 * @param bookingLocation of type IBookingLocation
	 */
	public void setBookingLocation(IBookingLocation bookingLocation) {
		this.bookingLocation = bookingLocation;
	}

	/**
	 * Get pledgor update status indicator.
	 * 
	 * @return String
	 */
	public String getPledgorStatus() {
		return pledgorStatus;
	}

	/**
	 * Set pledgor update status indicator.
	 * 
	 * @param pledgorStatus of type String
	 */
	public void setPledgorStatus(String pledgorStatus) {
		this.pledgorStatus = pledgorStatus;
	}

	/**
	 * @return Returns the plgIdCountry.
	 */
	public String getPlgIdCountry() {
		return plgIdCountry;
	}

	/**
	 * @param plgIdCountry The plgIdCountry to set.
	 */
	public void setPlgIdCountry(String plgIdCountry) {
		this.plgIdCountry = plgIdCountry;
	}

	/**
	 * @return Returns the plgIdNumText.
	 */
	public String getPlgIdNumText() {
		return plgIdNumText;
	}

	/**
	 * @param plgIdNumText The plgIdNumText to set.
	 */
	public void setPlgIdNumText(String plgIdNumText) {
		this.plgIdNumText = plgIdNumText;
	}

	/**
	 * @return Returns the plgIdTypeCode.
	 */
	public String getPlgIdTypeCode() {
		return plgIdTypeCode;
	}

	/**
	 * @param plgIdTypeCode The plgIdTypeCode to set.
	 */
	public void setPlgIdTypeCode(String plgIdTypeCode) {
		this.plgIdTypeCode = plgIdTypeCode;
	}

	/**
	 * @return Returns the plgIdType.
	 */
	public String getPlgIdType() {
		return plgIdType;
	}

	/**
	 * @param plgIdType The plgIdType to set.
	 */
	public void setPlgIdType(String plgIdType) {
		this.plgIdType = plgIdType;
	}

	/**
	 * @return Returns the sourceId.
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId The sourceId to set.
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.IPledgor#getLegalIDSourceCode
	 */
	public String getLegalIDSourceCode() {
		return this.legalIDSourceCode;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.IPledgor#setLegalIDSourceCode
	 */
	public void setLegalIDSourceCode(String value) {
		this.legalIDSourceCode = value;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.IPledgor#getLegalIDSource
	 */
	public String getLegalIDSource() {
		return this.legalIDSource;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.IPledgor#setLegalIDSource
	 */
	public void setLegalIDSource(String value) {
		this.legalIDSource = value;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(pledgorID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBPledgor)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}