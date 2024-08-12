/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/trx/IDDNTrxValue.java,v 1.3 2005/08/20 10:25:39 hshii Exp $
 */
package com.integrosys.cms.app.ddn.trx;

import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a ddn trx value.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/20 10:25:39 $ Tag: $Name: $
 */
public interface IDDNTrxValue extends ICMSTrxValue {
	/**
	 * Get the ddn business entity
	 * 
	 * @return IDDN
	 */
	public IDDN getDDN();

	/**
	 * Get the staging ddn business entity
	 * 
	 * @return IDDN
	 */
	public IDDN getStagingDDN();

	/**
	 * Set the ddn business entity
	 * 
	 * @param value is of type IDDN
	 */
	public void setDDN(IDDN value);

	/**
	 * Set the staging ddn business entity
	 * 
	 * @param value is of type IDDN
	 */
	public void setStagingDDN(IDDN value);

	/**
	 * Get the indicator for retrieving latest active remarks and user info
	 * 
	 * @return boolean
	 */
	public boolean getIsLatestActive();

	/**
	 * Set the indicator for retrieving latest active remarks and user info
	 * 
	 * @param isLatestActive is of type boolean
	 */
	public void setIsLatestActive(boolean isLatestActive);
}