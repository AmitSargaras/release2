/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/user/proxy/CMSStdUserProxyFactory.java,v 1.1 2005/08/08 08:27:12 dli Exp $
 */
package com.integrosys.cms.app.user.proxy;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.component.user.app.proxy.ICommonUserProxy;

/**
 * This factory creates ICommonUserProxy object.
 * 
 * @author $Author: dli $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/08 08:27:12 $ Tag: $Name: $
 */
public class CMSStdUserProxyFactory {
	/**
	 * Default Constructor
	 */
	public CMSStdUserProxyFactory() {
	}

	/**
	 * Creates an ICommonUserProxy.
	 * 
	 * @return ICommonUserProxy
	 */
	public static ICommonUserProxy getUserProxy() {
		try {
			SBStdUserProxy up = (SBStdUserProxy) BeanController.getEJB("SBStdUserProxyHome",
					"com.integrosys.cms.app.user.proxy.SBStdUserProxyHome");
			return up;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
