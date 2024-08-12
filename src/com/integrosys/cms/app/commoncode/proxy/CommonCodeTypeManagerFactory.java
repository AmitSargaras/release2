package com.integrosys.cms.app.commoncode.proxy;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CommonCodeTypeManagerFactory {

	/**
	 * Default Constructor
	 */
	public CommonCodeTypeManagerFactory() {
	}

	/**
	 * Get the custodian proxy manager.
	 * @return ICustodianProxyManager - the custodian proxy manager
	 */
	public static ICommonCodeTypeProxy getCommonCodeTypeProxy() {
		return new CommonCodeTypeManagerImpl();
	}
}
