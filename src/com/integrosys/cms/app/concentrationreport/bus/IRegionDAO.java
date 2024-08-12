/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/concentrationreport/bus/IRegionDAO.java,v 1.1 2003/08/25 11:18:30 btchng Exp $
 */
package com.integrosys.cms.app.concentrationreport.bus;

/**
 * Represents a region DAO.
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/25 11:18:30 $ Tag: $Name: $
 */
public interface IRegionDAO {

	/**
	 * Gets all the region information.
	 * @return An array of information for each region. This array can be zero
	 *         in length.
	 */
	IRegion[] getAllRegions();
}
