/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/recspecagent/IReceivableSpecificAgent.java,v 1.6 2003/10/09 02:49:24 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.recspecagent;

import java.util.Date;

import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IReceivableCommon;

/**
 * This interface represents Asset of type Receivables Assigned - Specific
 * Invoices via Agent.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/10/09 02:49:24 $ Tag: $Name: $
 */
public interface IReceivableSpecificAgent extends IReceivableCommon {
	/**
	 * Get agent name.
	 * 
	 * @return String
	 */
	public String getAgentName();

	/**
	 * Set agent name.
	 * 
	 * @param agentName is of type String
	 */
	public void setAgentName(String agentName);

	/**
	 * Get agent location.
	 * 
	 * @return String
	 */
	public String getAgentLocation();

	/**
	 * Set agent location.
	 * 
	 * @param agentLocation is of type String
	 */
	public void setAgentLocation(String agentLocation);

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
	 * Get proceeds of receivables credited to own bank A/c No.
	 * 
	 * @return String
	 */
	public String getOwnAccNo();

	/**
	 * Set proceeds of receivables credited to own bank A/c No.
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
	 * Get location of own bank account no.
	 * 
	 * @return String
	 */
	public String getOwnAccNoLocation();

	/**
	 * Set location of own bank account no.
	 * 
	 * @param ownAccNoLocation is of type String
	 */
	public void setOwnAccNoLocation(String ownAccNoLocation);

	/**
	 * Get proceeds of receivables controlled by agent bank.
	 * 
	 * @return String
	 */
	public String getAgentBankReceivables();

	/**
	 * Set proceeds of receivables controlled by agent bank.
	 * 
	 * @param agentBankReceivables is of type String
	 */
	public void setAgentBankReceivables(String agentBankReceivables);

	/**
	 * Get location of agent bank.
	 * 
	 * @return String
	 */
	public String getAgentBankLocation();

	/**
	 * Set location of agent bank.
	 * 
	 * @param agentBankLocation is of type String
	 */
	public void setAgentBankLocation(String agentBankLocation);

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
