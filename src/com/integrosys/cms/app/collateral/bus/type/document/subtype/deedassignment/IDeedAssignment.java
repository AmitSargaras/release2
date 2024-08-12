/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.document.subtype.deedassignment;

import java.util.Date;

import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;

/**
 * This interface represents a Document of type Deed of Assignments.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public interface IDeedAssignment extends IDocumentCollateral {

	/**
	 * Get deed of assignment type code.
	 * 
	 * @return String
	 */
	public String getDeedAssignmtTypeCode();

	/**
	 * Set deed of assignment type code.
	 * 
	 * @param deedAssignmtTypeCode of type String
	 */
	public void setDeedAssignmtTypeCode(String deedAssignmtTypeCode);

	/**
	 * Get project name.
	 * 
	 * @return String
	 */
	public String getProjectName();

	/**
	 * Set project name.
	 * 
	 * @param projectName of type String
	 */
	public void setProjectName(String projectName);

	/**
	 * Get date of awarded.
	 * 
	 * @return Date
	 */
	public Date getAwardedDate();

	/**
	 * Set date of awarded.
	 * 
	 * @param awardedDate of type Date
	 */
	public void setAwardedDate(Date awardedDate);

	/**
	 * Helper method to get the irrevocable letter of instruction indicator
	 * @return boolean
	 */
	public boolean getIsLetterInstruct();

	/**
	 * Helper method to set irrevocable letter of instruction indicator
	 * @param isLetterInstruct - boolean
	 */
	public void setIsLetterInstruct(boolean isLetterInstruct);

	/**
	 * Helper method to get the letter of undertaking from awarder indicator
	 * @return boolean
	 */
	public boolean getIsLetterUndertake();

	/**
	 * Helper method to set letter of undertaking from awarder indicator
	 * @param isLetterUndertake - boolean
	 */
	public void setIsLetterUndertake(boolean isLetterUndertake);

	/**
	 * Get blanket assignment.
	 * 
	 * @return String
	 */
	public String getBlanketAssignment();

	/**
	 * Set blanket assignment.
	 * 
	 * @param iSDAProductDesc of type String
	 */
	public void setBlanketAssignment(String blanketAssignment);

}