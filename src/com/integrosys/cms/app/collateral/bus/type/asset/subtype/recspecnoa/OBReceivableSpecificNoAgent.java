/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/recspecnoa/OBReceivableSpecificNoAgent.java,v 1.9 2006/01/18 05:31:05 priya Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.recspecnoa;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateralSubType;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.OBReceivableCommon;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents asset of type Receivables Assigned - Specific Invoices
 * not via Agent.
 * 
 * @author $Author: priya $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/01/18 05:31:05 $ Tag: $Name: $
 */
public class OBReceivableSpecificNoAgent extends OBReceivableCommon implements IReceivableSpecificNoAgent {
	private String approvedBuyer;

	private String approvedBuyerLocation;

	private String ownAccNo;

	private boolean isOwnProceedsOfReceivables;

	private String ownAccNoLocation;

	// added for gcms
	private String projectName = "";

	private String blanketAssignment = "";

	private Date dateAwarded;

	private boolean letterInstructFlag;

	private boolean letterUndertakeFlag;

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
	 * Default Constructor.
	 */
	public OBReceivableSpecificNoAgent() {
		super();
		super.setCollateralSubType(new OBCollateralSubType(ICMSConstant.COLTYPE_ASSET_RECV_SPEC_NOAGENT));
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IReceivableSpecificNoAgent
	 */
	public OBReceivableSpecificNoAgent(IReceivableSpecificNoAgent obj) {
		this();
		AccessorUtil.copyValue(obj, this);
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
	public String getBankAccNoLocation() {
		return ownAccNoLocation != null ? ownAccNoLocation.trim() : ownAccNoLocation;
	}

	/**
	 * Set location of own bank account number.
	 * 
	 * @param ownAccNoLocation is of type String
	 */
	public void setBankAccNoLocation(String ownAccNoLocation) {
		if (ownAccNoLocation != null) {
			this.ownAccNoLocation = ownAccNoLocation.trim();
		}
		else {
			this.ownAccNoLocation = ownAccNoLocation;
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
		else if (!(obj instanceof OBReceivableSpecificNoAgent)) {
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