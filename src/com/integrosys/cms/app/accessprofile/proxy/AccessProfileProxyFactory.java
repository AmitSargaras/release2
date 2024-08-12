/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/accessprofile/proxy/AccessProfileProxyFactory.java,v 1.2 2003/11/03 07:47:27 btchng Exp $
 */
package com.integrosys.cms.app.accessprofile.proxy;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * Factory for producing proxies to access profile managers.
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/11/03 07:47:27 $ Tag: $Name: $
 */
public class AccessProfileProxyFactory {

	/**
	 * Gets a proxy to do function access profile.
	 * 
	 * @return The proxy. <code>null</code> if not able to get a proxy.
	 */
	public static IAccessProfileProxy getAccessProfileProxy() {

		if (accessProfileProxy == null) {
			String clazz = PropertyManager.getValue(ACCESS_PROFILE_PROXY_KEY, ACCESS_PROFILE_PROXY_DEFAULT);
			try {
				accessProfileProxy = (IAccessProfileProxy) (Class.forName(clazz)).newInstance();

			}
			catch (Exception e) {
				DefaultLogger.error("IAccessProfileProxyFactory", "cannot create new instance of " + clazz);
				return null;
			}
		}
		return accessProfileProxy;

	}

	private static final String ACCESS_PROFILE_PROXY_KEY = "com.integrosys.cms.app.accessprofile.proxy.AccessProfileProxyFactory.proxy";

	private static final String ACCESS_PROFILE_PROXY_DEFAULT = "com.integrosys.cms.app.accessprofile.proxy.AccessProfileProxyImpl";

	private static IAccessProfileProxy accessProfileProxy;
}
