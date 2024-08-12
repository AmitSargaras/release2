/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.app.collateral.bus.type.document.subtype.pledge;

import java.util.Date;

import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;

/**
 * This interface represents a Document of type Negative Pledge.
 * 
 * @author $Author: jerlin $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/03/16 $ Tag: $Name: $
 */
public interface IPledge extends IDocumentCollateral {
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
	 * @return String
	 */
	public boolean getIsLetterInstruct();

	/**
	 * Helper method to set irrevocable letter of instruction indicator
	 * @param letterInstructFlag - String
	 */
	public void setIsLetterInstruct(boolean isLetterInstruct);

	/**
	 * Helper method to get the letter of undertaking from awarder indicator
	 * @return String
	 */
	public boolean getIsLetterUndertake();

	/**
	 * Helper method to set letter of undertaking from awarder indicator
	 * @param letterUndertakeFlag - String
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