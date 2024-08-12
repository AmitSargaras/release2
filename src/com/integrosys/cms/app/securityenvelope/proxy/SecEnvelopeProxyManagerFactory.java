package com.integrosys.cms.app.securityenvelope.proxy;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.documentlocation.proxy.IDocumentAppItemProxyManager;

/**
 * Factory class that instantiate the ISecEnvelopeProxyManager.
 * 
 * @author $Author: ravi $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/14 03:59:24 $ Tag: $Name: $
 */
public class SecEnvelopeProxyManagerFactory {
	/**
	 * Default Constructor
	 */
	public SecEnvelopeProxyManagerFactory() {
	}

	/**
	 * Get the custodian proxy manager.
	 * @return ICustodianProxyManager - the custodian proxy manager
	 */
	public static ISecEnvelopeProxyManager getSecEnvelopeProxyManger() {
		return (SecEnvelopeProxyManagerImpl) BeanHouse.get("secEnvelopeProxy");
	}
}