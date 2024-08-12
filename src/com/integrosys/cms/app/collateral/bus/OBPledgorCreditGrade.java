/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBPledgorCreditGrade.java,v 1.1 2003/09/03 09:25:26 elango Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents pledgor credit grade information.
 * 
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/03 09:25:26 $ Tag: $Name: $
 */
public class OBPledgorCreditGrade implements IPledgorCreditGrade {
	private long creditGradeID = ICMSConstant.LONG_INVALID_VALUE;

	private long pledgorID = ICMSConstant.LONG_INVALID_VALUE;

	private long creditGradeIDRef = ICMSConstant.LONG_INVALID_VALUE;

	private long pledgorIDRef = ICMSConstant.LONG_INVALID_VALUE;

	private String creditGradeType;

	private String creditGradeCode;

	private Date creditGradeStartDate;

	private String creditGradeDesc;

	/**
	 * Default Constructor.
	 */
	public OBPledgorCreditGrade() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IPledgorCreditGrade
	 */
	public OBPledgorCreditGrade(IPledgorCreditGrade obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get pledgor credit grade id.
	 * 
	 * @return long
	 */
	public long getCreditGradeID() {
		return creditGradeID;
	}

	/**
	 * Set pledgor credit grade id.
	 * 
	 * @param creditGradeID of type long
	 */
	public void setCreditGradeID(long creditGradeID) {
		this.creditGradeID = creditGradeID;
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
	 * Get pledgor credit grade id SCI reference.
	 * 
	 * @return long
	 */
	public long getCreditGradeIDRef() {
		return creditGradeIDRef;
	}

	/**
	 * Set pledgor credit grade id SCI reference.
	 * 
	 * @param creditGradeIDRef of type long
	 */
	public void setCreditGradeIDRef(long creditGradeIDRef) {
		this.creditGradeIDRef = creditGradeIDRef;
	}

	/**
	 * Get pledgor id SCI reference.
	 * 
	 * @return long
	 */
	public long getPledgorIDRef() {
		return pledgorIDRef;
	}

	/**
	 * Set pledgor id SCI reference.
	 * 
	 * @param pledgorIDRef of type long
	 */
	public void setPledgorIDRef(long pledgorIDRef) {
		this.pledgorIDRef = pledgorIDRef;
	}

	/**
	 * Get pledgor credit grade type value.
	 * 
	 * @return String
	 */
	public String getCreditGradeType() {
		return creditGradeType;
	}

	/**
	 * Set pledgor credit grade type value.
	 * 
	 * @param creditGradeType of type String
	 */
	public void setCreditGradeType(String creditGradeType) {
		this.creditGradeType = creditGradeType;
	}

	/**
	 * Get pledgor credit grade code value.
	 * 
	 * @return String
	 */
	public String getCreditGradeCode() {
		return creditGradeCode;
	}

	/**
	 * Set pledgor credit grade code value.
	 * 
	 * @param creditGradeCode of type String
	 */
	public void setCreditGradeCode(String creditGradeCode) {
		this.creditGradeCode = creditGradeCode;
	}

	/**
	 * Get pledgor credit grade start date.
	 * 
	 * @return Date
	 */
	public Date getCreditGradeStartDate() {
		return creditGradeStartDate;
	}

	/**
	 * Set pledgor credit grade start date.
	 * 
	 * @param creditGradeStartDate of type Date
	 */
	public void setCreditGradeStartDate(Date creditGradeStartDate) {
		this.creditGradeStartDate = creditGradeStartDate;
	}

	/**
	 * Get pledgor credit grade description.
	 * 
	 * @return String
	 */
	public String getCreditGradeDesc() {
		return creditGradeDesc;
	}

	/**
	 * Set pledgor credit grade description.
	 * 
	 * @param creditGradeDesc of type String
	 */
	public void setCreditGradeDesc(String creditGradeDesc) {
		this.creditGradeDesc = creditGradeDesc;
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
		String hash = String.valueOf(creditGradeID);
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
		else if (!(obj instanceof OBPledgorCreditGrade)) {
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