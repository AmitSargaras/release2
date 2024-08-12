/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.io.Serializable;

/**
 * Purpose: Interface of Policy Cap Group Description:
 * 
 * @author $Author: siewkheat $<br>
 * @version $Revision: 1.0 $
 * @since $Date: 27/AUG/2007 $ Tag: $Name: $
 */
public interface IPolicyCapGroup extends Serializable {

	/**
	 * Set policy cap group id
	 * @param groupID
	 */
	public void setPolicyCapGroupID(long groupID);

	/**
	 * Get Policy Group Id
	 * @return
	 */
	public long getPolicyCapGroupID();

	/**
	 * Set Stock Exchange
	 * @param stockExchange
	 */
	public void setStockExchange(String stockExchange);

	/**
	 * Get Stock Exchange
	 * @return
	 */
	public String getStockExchange();

	/**
	 * Set Bank Entity
	 * @param bankEntity
	 */
	public void setBankEntity(String bankEntity);

	/**
	 * Get Bank Entity
	 * @return
	 */
	public String getBankEntity();

	/**
	 * Set Version Time
	 * @param versionTime
	 */
	public void setVersionTime(long versionTime);

	/**
	 * Get Version Time
	 * @return
	 */
	public long getVersionTime();

	/**
	 * Get Policy cap child in collection
	 * @return an array of policyCap
	 */
	public IPolicyCap[] getPolicyCapArray();

	/**
	 * Set Policy Cap child
	 * @param policyCapArray
	 */
	public void setPolicyCapArray(IPolicyCap[] policyCapArray);
}
