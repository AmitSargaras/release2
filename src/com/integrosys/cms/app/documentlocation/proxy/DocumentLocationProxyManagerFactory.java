/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/proxy/DocumentLocationProxyManagerFactory.java,v 1.1 2004/02/17 02:12:19 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * Factory class that instantiate the IDocumentLocationProxyManager.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:19 $ Tag: $Name: $
 */
public class DocumentLocationProxyManagerFactory {

	private final static IDocumentLocationProxyManager documentLocationProxManager;

	static {
		IDocumentLocationProxyManager proxyManager = (IDocumentLocationProxyManager) BeanHouse
				.get("documentLocationProxy");

		if (proxyManager == null) {
			// suppress any method invocation, just return null.
			proxyManager = (IDocumentLocationProxyManager) Proxy.newProxyInstance(Thread.currentThread()
					.getContextClassLoader(), new Class[] { IDocumentLocationProxyManager.class },
					new InvocationHandler() {

						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (method.getName().equals("hasPendingCCDocumentLocationTrx")) {
								return Boolean.FALSE;
							}
							return null;
						}
					});
		}
		documentLocationProxManager = proxyManager;
	}

	/**
	 * Default Constructor
	 */
	public DocumentLocationProxyManagerFactory() {
	}

	/**
	 * Get the document location proxy manager.
	 * @return IDocumentLocationProxyManager - the document location proxy
	 *         manager
	 */
	public static IDocumentLocationProxyManager getProxyManager() {
		return documentLocationProxManager;
	}
}