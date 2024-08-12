package com.integrosys.cms.app.propertyparameters.proxy;

/**
 * Created by IntelliJ IDEA. User: zhaijian Date: Feb 1, 2007 Time: 10:28:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrPaProxyManagerFactory {

	/**
	 * Default Constructor
	 */
	public PrPaProxyManagerFactory() {
	}

	/**
	 * Get the document location proxy manager.
	 * @return IDocumentLocationProxyManager - the document location proxy
	 *         manager
	 */
	public static IPrPaProxyManager getProxyManager() {
		return new PrPaProxyManagerImpl();
	}
}
