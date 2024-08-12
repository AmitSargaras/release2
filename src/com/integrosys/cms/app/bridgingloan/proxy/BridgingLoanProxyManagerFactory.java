package com.integrosys.cms.app.bridgingloan.proxy;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 18, 2007 Tag: $Name$
 */
public class BridgingLoanProxyManagerFactory {
	/**
	 * Default Constructor
	 */
	public BridgingLoanProxyManagerFactory() {
	}

	/**
	 * Get the bridging loan proxy manager.
	 * @return IBridgingLoanProxyManager - the bridging loan proxy manager
	 */
	public static IBridgingLoanProxyManager getBridgingLoanProxyManager() {
		return new BridgingLoanProxyManagerImpl();
	}
}