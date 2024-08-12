/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/common/IContext.java,v 1.4 2003/08/07 12:50:12 sathish Exp $
 */
package com.integrosys.cms.app.common;

import java.io.Serializable;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This interface represents a general context data structure.
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/07 12:50:12 $ Tag: $Name: $
 */
public interface IContext extends Serializable {
	/**
	 * Get the User Team
	 * 
	 * @return ITeam
	 */
	public ITeam getTeam();

	/**
	 * Set the User Team
	 * 
	 * @param value is of type ITeam
	 */
	public void setTeam(ITeam value);

	/**
	 * Get the User object
	 * 
	 * @return ICommonUser
	 */
	public ICommonUser getUser();

	/**
	 * Set the user Object
	 * 
	 * @param value is of type ICommonUser
	 */
	public void setUser(ICommonUser value);

	/**
	 * Get the Remarks of the Trx User
	 * @return long
	 */
	public String getRemarks();

	/**
	 * Set the Remarks of the Trx User
	 * @param aRemarks - long
	 */
	public void setRemarks(String aRemark);

	/**
	 * Get the customer object
	 * 
	 * @return ICMSCustomer
	 */
	public ICMSCustomer getCustomer();

	/**
	 * Set the customer object
	 * 
	 * @param value is of type ICMSCustomer
	 */
	public void setCustomer(ICMSCustomer value);

	/**
	 * Get the Limit Profile
	 * 
	 * @return ILimitProfile
	 */
	public ILimitProfile getLimitProfile();

	/**
	 * Set the Limit Profile
	 * 
	 * @param value is of type ILimitProfile
	 */
	public void setLimitProfile(ILimitProfile value);

	/**
	 * Get the collateral ID
	 * 
	 * @return long
	 */
	public long getCollateralID();

	/**
	 * Set the collateral ID
	 * 
	 * @param value is of type long
	 */
	public void setCollateralID(long value);

	/**
	 * Get Trx Country of Origination
	 * 
	 * @return String
	 */
	public String getTrxCountryOrigin();

	/**
	 * Set Trx Country of Origination
	 * 
	 * @param value is of type String
	 */
	public void setTrxCountryOrigin(String value);

	/**
	 * Get Trx Organisation of Origination
	 * 
	 * @return String
	 */
	public String getTrxOrganisationOrigin();

	/**
	 * Set Trx Organisation of Origination
	 * 
	 * @param value is of type String
	 */
	public void setTrxOrganisationOrigin(String value);

	public String getTrxSegment();

	public void setTrxSegment(String value);

}