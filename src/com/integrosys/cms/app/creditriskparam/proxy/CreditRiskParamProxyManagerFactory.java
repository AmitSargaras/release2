/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CreditRiskParamProxyManagerFactory
 *
 * Created on 9:45:54 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.creditriskparam.proxy;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 15, 2007 Time: 9:45:54 AM
 */
public final class CreditRiskParamProxyManagerFactory {

	private CreditRiskParamProxyManagerFactory() {

	}

	public static ICreditRiskParamProxy getICreditRiskParamProxy() {
		return new CreditRiskParamProxyManagerImpl();
	}

}