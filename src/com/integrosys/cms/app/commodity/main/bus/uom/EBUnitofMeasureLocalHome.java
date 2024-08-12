/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/EBUnitofMeasureLocalHome.java,v 1.3 2004/08/19 05:12:44 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines user-defined unit of measure create and finder methods for local
 * clients.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/19 05:12:44 $ Tag: $Name: $
 */
public interface EBUnitofMeasureLocalHome extends EJBLocalHome {
	/**
	 * Create UnitofMeasure record.
	 * 
	 * @param anEntity of type IUnitofMeasure
	 * @return UnitofMeasure - ejb object
	 * @throws javax.ejb.CreateException on error creating the ejb
	 */
	public EBUnitofMeasureLocal create(IUnitofMeasure anEntity) throws CreateException;

	/**
	 * Find UnitofMeasure by primary key, the UnitofMeasure id.
	 * 
	 * @param theID ID of the entity
	 * @return UnitofMeasure - ejb object
	 * @throws javax.ejb.FinderException on error finding the collateral
	 */
	public EBUnitofMeasureLocal findByPrimaryKey(Long theID) throws FinderException;

	/**
	 * Finds all UnitofMeasures associated with a group id.
	 * 
	 * @param groupID ID of the profile
	 * @return Collection of IUnitofMeasures
	 * @throws javax.ejb.FinderException on error finding the UnitofMeasure
	 */
	public Collection findByGroupID(long groupID) throws FinderException;

	/**
	 * Finds all UnitofMeasures associated with a profile id.
	 * 
	 * @param profileID ID of the profile
	 * @return Collection of IUnitofMeasures
	 * @throws javax.ejb.FinderException on error finding the UnitofMeasure
	 */
	public Collection findByProfileID(long profileID) throws FinderException;

}
