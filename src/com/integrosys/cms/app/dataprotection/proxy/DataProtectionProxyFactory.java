/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/proxy/DataProtectionProxyFactory.java,v 1.2 2003/06/20 02:56:27 jtan Exp $
 */
package com.integrosys.cms.app.dataprotection.proxy;

/**
 * Describes this class Purpose: Description:
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public class DataProtectionProxyFactory {

	/**
	 * @return a data protection proxy
	 */
	public static DataProtectionProxyImpl getProxy() {
		return new DataProtectionProxyImpl();
	}
}
