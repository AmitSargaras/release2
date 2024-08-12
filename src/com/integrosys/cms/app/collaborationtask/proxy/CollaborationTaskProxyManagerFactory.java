/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/proxy/CollaborationTaskProxyManagerFactory.java,v 1.1 2003/08/15 14:02:47 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * Factory class that instantiate the ICollaborationTaskProxyManager.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/15 14:02:47 $ Tag: $Name: $
 */
public class CollaborationTaskProxyManagerFactory {

	private static final ICollaborationTaskProxyManager collaborationTaskProxyManager;

	static {
		ICollaborationTaskProxyManager proxyManager = (ICollaborationTaskProxyManager) BeanHouse
				.get("collaborationTaskProxy");

		if (proxyManager == null) {
			// suppress any method invocation, just return null.
			proxyManager = (ICollaborationTaskProxyManager) Proxy.newProxyInstance(Thread.currentThread()
					.getContextClassLoader(), new Class[] { ICollaborationTaskProxyManager.class },
					new InvocationHandler() {

						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							if (Boolean.TYPE == method.getReturnType()) {
								return Boolean.FALSE;
							}
							return null;
						}
					});
		}

		collaborationTaskProxyManager = proxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CollaborationTaskProxyManagerFactory() {
	}

	/**
	 * Get the collaboration task proxy manager.
	 * @return ICollaborationTaskProxyManager - the collaboration task proxy
	 *         manager
	 */
	public static ICollaborationTaskProxyManager getProxyManager() {
		return collaborationTaskProxyManager;
	}
}