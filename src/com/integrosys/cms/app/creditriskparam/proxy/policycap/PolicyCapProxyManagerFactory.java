package com.integrosys.cms.app.creditriskparam.proxy.policycap;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class PolicyCapProxyManagerFactory {

	/**
	 * Default Constructor
	 */
	public PolicyCapProxyManagerFactory() {
	}

	/**
	 * Get the policy cap proxy manager.
	 * @return IPolicyCapProxyManager - the policy cap proxy manager
	 */
	public static IPolicyCapProxyManager getPolicyCapProxyManager() {
		return new PolicyCapProxyManagerImpl();
	}

}
