/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/concentrationreport/proxy/IConcReportProxy.java,v 1.1 2003/08/25 11:18:30 btchng Exp $
 */
package com.integrosys.cms.app.concentrationreport.proxy;

import com.integrosys.cms.app.concentrationreport.bus.IRegion;

/**
 * Concentration report proxy.
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/25 11:18:30 $ Tag: $Name: $
 */
public interface IConcReportProxy {

	/**
	 * Gets all the region information.
	 * @return An array of information for each region. This array can be zero
	 *         in length.
	 */
	IRegion[] getAllRegions();
}
