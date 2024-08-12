/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/common/OBContext.java,v 1.4 2005/08/16 12:18:44 lyng Exp $
 */
package com.integrosys.cms.app.common;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This class implementing the ITrxContext Interface
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/08/16 12:18:44 $ Tag: $Name: $
 */
public class OBContext implements IContext {
	private ITeam _team = null;

	private ICommonUser _user = null;

	private String remarks = null;

	private ICMSCustomer _customer = null;

	private ILimitProfile _limitProfile = null;

	private long _collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _country = null;

	private String _org = null;

	private String _segment = null;

	public OBContext() {
	}

	public OBContext(ICommonUser aUserID, ITeam aTeamID) {
		setTeam(aTeamID);
		setUser(aUserID);
	}

	/**
	 * Get the User Team
	 * 
	 * @return ITeam
	 */
	public ITeam getTeam() {
		return _team;
	}

	/**
	 * Set the User Team
	 * 
	 * @param value is of type ITeam
	 */
	public void setTeam(ITeam value) {
		_team = value;
	}

	/**
	 * Get the User object
	 * 
	 * @return ICommonUser
	 */
	public ICommonUser getUser() {
		return _user;
	}

	/**
	 * Set the user Object
	 * 
	 * @param value is of type ICommonUser
	 */
	public void setUser(ICommonUser value) {
		_user = value;
	}

	/**
	 * Get the Remarks of the Trx User
	 * @return long
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * Set the Remarks of the Trx User
	 * @param aRemarks - long
	 */
	public void setRemarks(String aRemarks) {
		this.remarks = aRemarks;
	}

	/**
	 * Get the customer object
	 * 
	 * @return ICMSCustomer
	 */
	public ICMSCustomer getCustomer() {
		return _customer;
	}

	/**
	 * Set the customer object
	 * 
	 * @param value is of type ICMSCustomer
	 */
	public void setCustomer(ICMSCustomer value) {
		_customer = value;
	}

	/**
	 * Get the Limit Profile
	 * 
	 * @return ILimitProfile
	 */
	public ILimitProfile getLimitProfile() {
		return _limitProfile;
	}

	/**
	 * Set the Limit Profile
	 * 
	 * @param value is of type ILimitProfile
	 */
	public void setLimitProfile(ILimitProfile value) {
		_limitProfile = value;
	}

	/**
	 * Get the collateral ID
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return _collateralID;
	}

	/**
	 * Set the collateral ID
	 * 
	 * @param value is of type long
	 */
	public void setCollateralID(long value) {
		_collateralID = value;
	}

	/**
	 * Get Trx Country of Origination
	 * 
	 * @return String
	 */
	public String getTrxCountryOrigin() {
		return _country;
	}

	/**
	 * Set Trx Country of Origination
	 * 
	 * @param value is of type String
	 */
	public void setTrxCountryOrigin(String value) {
		_country = value;
	}

	/**
	 * Get Trx Organisation of Origination
	 * 
	 * @return String
	 */
	public String getTrxOrganisationOrigin() {
		return _org;
	}

	/**
	 * Set Trx Organisation of Origination
	 * 
	 * @param value is of type String
	 */
	public void setTrxOrganisationOrigin(String value) {
		_org = value;
	}

	public String getTrxSegment() {
		return _segment;
	}

	public void setTrxSegment(String value) {
		this._segment = value;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return ">>>>>>> OBContext.toString() <<<<<<";
		// return AccessorUtil.printMethodValue(this);
	}
}