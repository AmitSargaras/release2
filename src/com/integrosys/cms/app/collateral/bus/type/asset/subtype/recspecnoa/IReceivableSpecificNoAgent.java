/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/recspecnoa/IReceivableSpecificNoAgent.java,v 1.6 2003/07/28 05:18:22 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.recspecnoa;

import java.util.Date;

import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IReceivableCommon;

/**
 * This interface represents Asset of type Receivables Assigned - Specific
 * Invoices not via Agent.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/07/28 05:18:22 $ Tag: $Name: $
 */
public interface IReceivableSpecificNoAgent extends IReceivableCommon {
	/**
	 * Get approved buyer.
	 * 
	 * @return String
	 */
	public String getApprovedBuyer();

	/**
	 * Set approved buyer.
	 * 
	 * @param approvedBuyer is of type String
	 */
	public void setApprovedBuyer(String approvedBuyer);

	/**
	 * Get approved buyer location.
	 * 
	 * @return String
	 */
	public String getApprovedBuyerLocation();

	/**
	 * Set approved buyer location.
	 * 
	 * @param approvedBuyerLocation is of type String
	 */
	public void setApprovedBuyerLocation(String approvedBuyerLocation);

	/**
	 * Get own bank account no to which proceeds of receivables credited.
	 * 
	 * @return String
	 */
	public String getOwnAccNo();

	/**
	 * Set own bank account no to which proceeds of receivables credited.
	 * 
	 * @param ownAccNo is of type String
	 */
	public void setOwnAccNo(String ownAccNo);

	/**
	 * Get proceeds of receivables controlled by own bank.
	 * 
	 * @return boolean
	 */
	public boolean getIsOwnProceedsOfReceivables();

	/**
	 * Set proceeds of receivables controlled by own bank.
	 * 
	 * @param isOwnProceedsOfReceivables is of type boolean
	 */
	public void setIsOwnProceedsOfReceivables(boolean isOwnProceedsOfReceivables);

	/**
	 * Get location of own bank account number.
	 * 
	 * @return String
	 */
	public String getBankAccNoLocation();

	/**
	 * Set location of own bank account number.
	 * 
	 * @param ownAccNoLocation is of type String
	 */
	public void setBankAccNoLocation(String ownAccNoLocation);

	public String getProjectName();

	public void setProjectName(String projectName);

	public String getBlanketAssignment();

	public void setBlanketAssignment(String blanketAssignment);

	public Date getDateAwarded();

	public void setDateAwarded(Date dateAwarded);

	public boolean getLetterInstructFlag();

	public void setLetterInstructFlag(boolean letterInstructFlag);

	public boolean getLetterUndertakeFlag();

	public void setLetterUndertakeFlag(boolean letterUndertakeFlag);
}
