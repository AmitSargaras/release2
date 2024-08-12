/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.document.subtype.deedassignment;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.document.OBDocumentCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a document collateral of type Deed of Assignments.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public class OBDeedAssignment extends OBDocumentCollateral implements IDeedAssignment {
	private String deedAssignmtTypeCode;

	private String projectName;

	private Date awardedDate;

	private String letterInstructFlag;

	private String letterUndertakeFlag;

	private String blanketAssignment;

	/**
	 * Default Constructor.
	 */
	public OBDeedAssignment() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_DOC_DEED_ASSIGNMENT));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IDeedAssignment
	 */
	public OBDeedAssignment(IDeedAssignment obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get deed of assignment type code.
	 * 
	 * @return String
	 */
	public String getDeedAssignmtTypeCode() {
		return deedAssignmtTypeCode;
	}

	/**
	 * Set deed of assignment type code.
	 * 
	 * @param deedAssignmtTypeCode of type String
	 */
	public void setDeedAssignmtTypeCode(String deedAssignmtTypeCode) {
		this.deedAssignmtTypeCode = deedAssignmtTypeCode;
	}

	/**
	 * Get project name.
	 * 
	 * @return String
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Set project name.
	 * 
	 * @param projectName of type String
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Get date of awarded.
	 * 
	 * @return Date
	 */
	public Date getAwardedDate() {
		return awardedDate;
	}

	/**
	 * Set date of awarded.
	 * 
	 * @param awardedDate of type Date
	 */
	public void setAwardedDate(Date awardedDate) {
		this.awardedDate = awardedDate;
	}

	/**
	 * Get Irrevocable letter of instruction Flag.
	 * 
	 * @return String
	 */
	public String getLetterInstructFlag() {
		return letterInstructFlag;
	}

	/**
	 * Set Irrevocable letter of instruction Flag.
	 * 
	 * @param letterInstructFlag of type String
	 */
	public void setLetterInstructFlag(String letterInstructFlag) {
		this.letterInstructFlag = letterInstructFlag;
	}

	/**
	 * Get Letter of undertaking from awarder Flag.
	 * 
	 * @return String
	 */
	public String getLetterUndertakeFlag() {
		return letterUndertakeFlag;
	}

	/**
	 * Set Letter of undertaking from awarder Flag.
	 * 
	 * @param letterUndertakeFlag of type String
	 */
	public void setLetterUndertakeFlag(String letterUndertakeFlag) {
		this.letterUndertakeFlag = letterUndertakeFlag;
	}

	/**
	 * Helper method to get the irrevocable letter of instruction indicator
	 * @return boolean
	 */
	public boolean getIsLetterInstruct() {
		if ((this.letterInstructFlag != null) && this.letterInstructFlag.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to set irrevocable letter of instruction indicator
	 * @param isLetterInstruct - boolean
	 */
	public void setIsLetterInstruct(boolean isLetterInstruct) {
		if (isLetterInstruct) {
			this.letterInstructFlag = ICMSConstant.TRUE_VALUE;
			return;
		}
		this.letterInstructFlag = ICMSConstant.FALSE_VALUE;
	}

	/**
	 * Helper method to get the letter of undertaking from awarder indicator
	 * @return boolean
	 */
	public boolean getIsLetterUndertake() {
		if ((this.letterUndertakeFlag != null) && this.letterUndertakeFlag.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to set letter of undertaking from awarder indicator
	 * @param isLetterUndertake - boolean
	 */
	public void setIsLetterUndertake(boolean isLetterUndertake) {
		if (isLetterUndertake) {
			this.letterUndertakeFlag = ICMSConstant.TRUE_VALUE;
			return;
		}
		this.letterUndertakeFlag = ICMSConstant.FALSE_VALUE;
	}

	/**
	 * Get blanket assignment.
	 * 
	 * @return String
	 */
	public String getBlanketAssignment() {
		return blanketAssignment;
	}

	/**
	 * Set blanket assignment.
	 * 
	 * @param iSDAProductDesc of type String
	 */
	public void setBlanketAssignment(String blanketAssignment) {
		this.blanketAssignment = blanketAssignment;
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
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBDeedAssignment)) {
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