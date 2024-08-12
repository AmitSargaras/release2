/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/concentrationreport/proxy/ConcReportProxyImpl.java,v 1.1 2003/08/25 11:18:30 btchng Exp $
 */
package com.integrosys.cms.app.concentrationreport.proxy;

import com.integrosys.cms.app.concentrationreport.bus.IRegion;
import com.integrosys.cms.app.concentrationreport.bus.IRegionDAO;
import com.integrosys.cms.app.concentrationreport.bus.RegionDAOFactory;

/**
 * 
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/25 11:18:30 $ Tag: $Name: $
 */
public class ConcReportProxyImpl implements IConcReportProxy {

	/**
	 * Gets all the region information.
	 * @return An array of information for each region. This array can be zero
	 *         in length.
	 */
	public IRegion[] getAllRegions() {
		IRegionDAO dao = RegionDAOFactory.getRegionDAO();
		return dao.getAllRegions();
	}

}
