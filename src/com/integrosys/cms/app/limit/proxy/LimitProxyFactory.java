/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/proxy/LimitProxyFactory.java,v 1.1 2003/07/09 08:59:25 kllee Exp $
 */
package com.integrosys.cms.app.limit.proxy;

import com.integrosys.base.techinfra.context.BeanHouse;

/**
 * This factory class will load ILimitProxy implemenations.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/09 08:59:25 $ Tag: $Name: $
 */
public class LimitProxyFactory {
	/**
	 * Create a default limit proxy implementation
	 * 
	 * @return ILimitProxy
	 */
	public static ILimitProxy getProxy() {
		return (ILimitProxy) BeanHouse.get("limitProxy");
	}
}