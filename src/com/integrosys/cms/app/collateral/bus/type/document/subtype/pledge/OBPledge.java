/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.collateral.bus.type.document.subtype.pledge;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.document.OBDocumentCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a document collateral of type Negative Pledge.
 * 
 * @author $Author: jerlin $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/03/16 $ Tag: $Name: $
 */
public class OBPledge extends OBDocumentCollateral implements IPledge {
	private String projectName;

	private Date awardedDate;

	private String letterInstructFlag;

	private String letterUndertakeFlag;

	private String blanketAssignment;

	/**
	 * Default Constructor.
	 */
	public OBPledge() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_DOC_PLEDGE));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IPledge
	 */
	public OBPledge(IPledge obj) {
		this();
		AccessorUtil.copyValue(obj, this);
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
		else if (!(obj instanceof OBPledge)) {
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