/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/recspecagent/OBReceivableSpecificAgent.java,v 1.9 2006/01/18 05:30:15 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.recspecagent;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBReceivableCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Asset of type Receivables Assigned - Specific Invoices
 * via Agent.
 * 
 * @author $Author: priya $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/01/18 05:30:15 $ Tag: $Name: $
 */
public class OBReceivableSpecificAgent extends OBReceivableCommon implements IReceivableSpecificAgent {

	/**
	 * Default Constructor.
	 */
	public OBReceivableSpecificAgent() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_RECV_SPEC_AGENT));
	}

	private String agentName;

	private String agentLocation;

	private String approvedBuyer;

	private String approvedBuyerLocation;

	private String ownAccNo;

	private boolean isOwnProceedsOfReceivables;

	private String ownAccNoLocation;

	private String agentBankReceivables;

	private String agentBankLocation;

	// added for gcms
	private String projectName = "";

	private String blanketAssignment = "";

	private Date dateAwarded;

	private boolean letterInstructFlag;

	private boolean letterUndertakeFlag;

	public boolean isOwnProceedsOfReceivables() {
		return isOwnProceedsOfReceivables;
	}

	public void setOwnProceedsOfReceivables(boolean ownProceedsOfReceivables) {
		isOwnProceedsOfReceivables = ownProceedsOfReceivables;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getBlanketAssignment() {
		return blanketAssignment;
	}

	public void setBlanketAssignment(String blanketAssignment) {
		this.blanketAssignment = blanketAssignment;
	}

	public Date getDateAwarded() {
		return dateAwarded;
	}

	public void setDateAwarded(Date dateAwarded) {
		this.dateAwarded = dateAwarded;
	}

	public boolean getLetterInstructFlag() {
		return letterInstructFlag;
	}

	public void setLetterInstructFlag(boolean letterInstructFlag) {
		this.letterInstructFlag = letterInstructFlag;
	}

	public boolean getLetterUndertakeFlag() {
		return letterUndertakeFlag;
	}

	public void setLetterUndertakeFlag(boolean letterUndertakeFlag) {
		this.letterUndertakeFlag = letterUndertakeFlag;
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IReceivableSpecificAgent
	 */
	public OBReceivableSpecificAgent(IReceivableSpecificAgent obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get agent name.
	 * 
	 * @return String
	 */
	public String getAgentName() {
		return agentName;
	}

	/**
	 * Set agent name.
	 * 
	 * @param agentName is of type String
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	/**
	 * Get agent location.
	 * 
	 * @return String
	 */
	public String getAgentLocation() {
		return agentLocation;
	}

	/**
	 * Set agent location.
	 * 
	 * @param agentLocation is of type String
	 */
	public void setAgentLocation(String agentLocation) {
		this.agentLocation = agentLocation;
	}

	/**
	 * Get approved buyer.
	 * 
	 * @return String
	 */
	public String getApprovedBuyer() {
		return approvedBuyer;
	}

	/**
	 * Set approved buyer.
	 * 
	 * @param approvedBuyer is of type String
	 */
	public void setApprovedBuyer(String approvedBuyer) {
		this.approvedBuyer = approvedBuyer;
	}

	/**
	 * Get approved buyer location.
	 * 
	 * @return String
	 */
	public String getApprovedBuyerLocation() {
		return approvedBuyerLocation;
	}

	/**
	 * Set approved buyer location.
	 * 
	 * @param approvedBuyerLocation is of type String
	 */
	public void setApprovedBuyerLocation(String approvedBuyerLocation) {
		this.approvedBuyerLocation = approvedBuyerLocation;
	}

	/**
	 * Get proceeds of receivables credited to own bank A/c No.
	 * 
	 * @return String
	 */
	public String getOwnAccNo() {
		return ownAccNo;
	}

	/**
	 * Set proceeds of receivables credited to own bank A/c No.
	 * 
	 * @param ownAccNo is of type String
	 */
	public void setOwnAccNo(String ownAccNo) {
		this.ownAccNo = ownAccNo;
	}

	/**
	 * Get proceeds of receivables controlled by own bank.
	 * 
	 * @return boolean
	 */
	public boolean getIsOwnProceedsOfReceivables() {
		return isOwnProceedsOfReceivables;
	}

	/**
	 * Set proceeds of receivables controlled by own bank.
	 * 
	 * @param isOwnProceedsOfReceivables is of type boolean
	 */
	public void setIsOwnProceedsOfReceivables(boolean isOwnProceedsOfReceivables) {
		this.isOwnProceedsOfReceivables = isOwnProceedsOfReceivables;
	}

	/**
	 * Get location of own bank account no.
	 * 
	 * @return String
	 */
	public String getOwnAccNoLocation() {
		return ownAccNoLocation != null ? ownAccNoLocation.trim() : ownAccNoLocation;
	}

	/**
	 * Set location of own bank account no.
	 * 
	 * @param ownAccNoLocation is of type String
	 */
	public void setOwnAccNoLocation(String ownAccNoLocation) {
		if (ownAccNoLocation != null) {
			this.ownAccNoLocation = ownAccNoLocation.trim();
		}
		else {
			this.ownAccNoLocation = ownAccNoLocation;
		}
	}

	/**
	 * Get proceeds of receivables controlled by agent bank.
	 * 
	 * @return String
	 */
	public String getAgentBankReceivables() {
		return agentBankReceivables;
	}

	/**
	 * Set proceeds of receivables controlled by agent bank.
	 * 
	 * @param agentBankReceivables is of type String
	 */
	public void setAgentBankReceivables(String agentBankReceivables) {
		this.agentBankReceivables = agentBankReceivables;
	}

	/**
	 * Get location of agent bank.
	 * 
	 * @return String
	 */
	public String getAgentBankLocation() {
		return agentBankLocation != null ? agentBankLocation.trim() : agentBankLocation;
	}

	/**
	 * Set location of agent bank.
	 * 
	 * @param agentBankLocation is of type String
	 */
	public void setAgentBankLocation(String agentBankLocation) {
		if (agentBankLocation != null) {
			this.agentBankLocation = agentBankLocation.trim();
		}
		else {
			this.agentBankLocation = agentBankLocation;
		}
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
		else if (!(obj instanceof OBReceivableSpecificAgent)) {
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