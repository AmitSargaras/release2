package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public interface IPolicyCapTrxValue extends ICMSTrxValue {

	/**
	 * Get the policy cap business entity
	 * 
	 * @return IPolicyCap
	 */
	public IPolicyCap[] getPolicyCap();

	/**
	 * Get the staging policy cap business entity
	 * 
	 * @return IPolicyCap
	 */
	public IPolicyCap[] getStagingPolicyCap();

	/**
	 * Set the policy cap business entity
	 * 
	 * @param value is of type IPolicyCap
	 */
	public void setPolicyCap(IPolicyCap[] value);

	/**
	 * Set the staging policy cap business entity
	 * 
	 * @param value is of type IPolicyCap
	 */
	public void setStagingPolicyCap(IPolicyCap[] value);

}
