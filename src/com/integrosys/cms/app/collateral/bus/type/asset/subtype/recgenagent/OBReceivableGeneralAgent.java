/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/recgenagent/OBReceivableGeneralAgent.java,v 1.8 2006/01/18 05:26:40 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.recgenagent;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBReceivableCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents asset of type Receivables Assigned - General Invoices
 * via Agent.
 * 
 * @author $Author: priya $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/01/18 05:26:40 $ Tag: $Name: $
 */
public class OBReceivableGeneralAgent extends OBReceivableCommon implements IReceivableGeneralAgent {
	private String agentName;

	private String agentLocation;

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

	/**
	 * Default Constructor.
	 */
	public OBReceivableGeneralAgent() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_RECV_GEN_AGENT));

	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IReceivableGeneralAgent
	 */
	public OBReceivableGeneralAgent(IReceivableGeneralAgent obj) {
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
	 * Get own bank account no to which proceeds of receivables credited.
	 * 
	 * @return String
	 */
	public String getOwnAccNo() {
		return ownAccNo;
	}

	/**
	 * Set own bank account no to which proceeds of receivables credited.
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
	 * Get location of own bank account number.
	 * 
	 * @return String
	 */
	public String getOwnAccNoLocation() {
		return ownAccNoLocation != null ? ownAccNoLocation.trim() : ownAccNoLocation;
	}

	/**
	 * Set location of own bank account number.
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
	 * Get agent bank location.
	 * 
	 * @return String
	 */
	public String getAgentBankLocation() {
		return agentBankLocation != null ? agentBankLocation.trim() : agentBankLocation;
	}

	/**
	 * Set agent bank location.
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
		else if (!(obj instanceof OBReceivableGeneralAgent)) {
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