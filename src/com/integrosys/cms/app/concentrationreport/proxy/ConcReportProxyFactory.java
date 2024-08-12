/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/concentrationreport/proxy/ConcReportProxyFactory.java,v 1.1 2003/08/25 11:18:30 btchng Exp $
 */
package com.integrosys.cms.app.concentrationreport.proxy;

/**
 * Produces concentration proxy.
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/25 11:18:30 $ Tag: $Name: $
 */
public class ConcReportProxyFactory {

	public static IConcReportProxy getProxy() {
		return new ConcReportProxyImpl();
	}
}
