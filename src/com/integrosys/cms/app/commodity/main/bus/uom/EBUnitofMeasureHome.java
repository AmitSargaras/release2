/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/uom/EBUnitofMeasureHome.java,v 1.2 2004/06/04 04:53:24 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.uom;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:53:24 $ Tag: $Name: $
 */
public interface EBUnitofMeasureHome extends EJBHome {
	/**
	 * Create UnitofMeasure.
	 * 
	 * @param anEntity of type IUnitofMeasure
	 * @return UnitofMeasure - ejb object
	 * @throws javax.ejb.CreateException on error creating the ejb
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBUnitofMeasure create(IUnitofMeasure anEntity) throws CreateException, RemoteException;

	/**
	 * Find UnitofMeasure by primary key, the UnitofMeasure id.
	 * 
	 * @param theID ID of the entity
	 * @return TitleDocument - ejb object
	 * @throws javax.ejb.FinderException on error finding the UnitofMeasure
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public EBUnitofMeasure findByPrimaryKey(Long theID) throws FinderException, RemoteException;

	/**
	 * Finds all UnitofMeasures associated with a group id.
	 * 
	 * @param groupID ID of the group
	 * @return Collection of IUnitofMeasures
	 * @throws javax.ejb.FinderException on error finding the UnitofMeasure
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public Collection findByGroupID(long groupID) throws FinderException, RemoteException;

	/**
	 * Finds all UnitofMeasures associated with a profile id.
	 * 
	 * @param profileID ID of the profile
	 * @return Collection of IUnitofMeasures
	 * @throws javax.ejb.FinderException on error finding the UnitofMeasure
	 * @throws java.rmi.RemoteException on error during remote method call
	 */
	public Collection findByProfileID(long profileID) throws FinderException, RemoteException;
}
