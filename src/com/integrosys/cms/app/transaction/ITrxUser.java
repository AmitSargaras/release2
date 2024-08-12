/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/ITrxUser.java,v 1.4 2003/06/27 11:10:55 kllee Exp $
 */
package com.integrosys.cms.app.transaction;

import java.io.Serializable;

import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This interface represents the transaction user value object.
 * 
 * @author $Author: kllee $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/06/27 11:10:55 $ Tag: $Name: $
 * @deprecated Use <code>ITrxContext</code> instead
 */
public interface ITrxUser extends Serializable {
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
	 * Get Customer Segment Code
	 * 
	 * @return String
	 */
	public String getCustomerSegment();

	/**
	 * Set Customer Segment Code
	 * 
	 * @param value is of type String
	 */
	public void setCustomerSegment(String value);
}