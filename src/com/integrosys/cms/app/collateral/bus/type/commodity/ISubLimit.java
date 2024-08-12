/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/ISubLimit.java,v 1.4 2004/08/18 08:00:51 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.commodity;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents loan agency sub-limit.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/18 08:00:51 $ Tag: $Name: $
 */
public interface ISubLimit extends java.io.Serializable {
	/**
	 * Get sub-limit id.
	 * 
	 * @return long
	 */
	public long getSubLimitID();

	/**
	 * Set sub-limit id.
	 * 
	 * @param subLimitID of type long
	 */
	public void setSubLimitID(long subLimitID);

	/**
	 * Get sub-limit amount.
	 * 
	 * @return Amount
	 */
	public Amount getAmount();

	/**
	 * Set sub-limit amount.
	 * 
	 * @param amt of type Amount
	 */
	public void setAmount(Amount amt);

	/**
	 * Get sub-limit facility type.
	 * 
	 * @return String
	 */
	public String getFacilityType();

	/**
	 * Set sub-limit facility type.
	 * 
	 * @param type of type String
	 */
	public void setFacilityType(String type);

	/**
	 * Get sub-limit status, active or deleted.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set sub-limit status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Get common reference for actual and staging sub-limit.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set common reference for actual and staging sub-limit.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);
}
