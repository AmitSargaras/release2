/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/OBTrxUser.java,v 1.4 2003/06/27 11:10:55 kllee Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This class implementing the ITrxUser Interface
 * 
 * @author $Author: kllee $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/06/27 11:10:55 $ Tag: $Name: $
 * @deprecated Use <code>ITrxContext</code> instead
 */
public class OBTrxUser implements ITrxUser {
	private ITeam _team = null;

	private ICommonUser _user = null;

	private String remarks = null;

	private String _country = null;

	private String _segment = null;

	public OBTrxUser() {
	}

	public OBTrxUser(ICommonUser aUserID, ITeam aTeamID) {
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
	 * Get Customer Segment Code
	 * 
	 * @return String
	 */
	public String getCustomerSegment() {
		return _segment;
	}

	/**
	 * Set Customer Segment Code
	 * 
	 * @param value is of type String
	 */
	public void setCustomerSegment(String value) {
		_segment = value;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}