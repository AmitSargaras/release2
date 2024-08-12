/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/recgenagent/IReceivableGeneralAgent.java,v 1.5 2003/07/28 05:18:03 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.recgenagent;

import java.util.Date;

import com.integrosys.cms.app.collateral.bus.type.asset.subtype.IReceivableCommon;

/**
 * This interface represents Asset of type Receivables Assigned - General
 * Invoices via Agent.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/07/28 05:18:03 $ Tag: $Name: $
 */
public interface IReceivableGeneralAgent extends IReceivableCommon {
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
	public String getOwnAccNoLocation();

	/**
	 * Set location of own bank account number.
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
	 * Get agent bank location.
	 * 
	 * @return String
	 */
	public String getAgentBankLocation();

	/**
	 * Set agent bank location.
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