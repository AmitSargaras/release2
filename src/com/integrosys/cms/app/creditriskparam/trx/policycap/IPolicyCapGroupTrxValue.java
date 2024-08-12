/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.trx.policycap;

import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Interface of PolicyCapGroup Transaction
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 29/Aug/2007 $ Tag: $Name: $
 */
public interface IPolicyCapGroupTrxValue extends ICMSTrxValue {

	/**
	 * Get the policy cap group business entity
	 * @return policyCapGroup
	 */
	public IPolicyCapGroup getPolicyCapGroup();

	/**
	 * Get the staging policy cap group business entity
	 * @return policyCapGroup
	 */
	public IPolicyCapGroup getStagingPolicyCapGroup();

	/**
	 * Set the policy cap group business entity
	 * @param policyCapGroup
	 */
	public void setPolicyCapGroup(IPolicyCapGroup policyCapGroup);

	/**
	 * Set the staging cap group business entity
	 * @param policyCapGroup
	 */
	public void setStagingPolicyCapGroup(IPolicyCapGroup policyCapGroup);
}
