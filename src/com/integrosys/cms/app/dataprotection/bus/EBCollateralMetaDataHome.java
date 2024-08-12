/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/dataprotection/bus/EBCollateralMetaDataHome.java,v 1.2 2003/06/19 13:17:31 jtan Exp $
 */
package com.integrosys.cms.app.dataprotection.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Purpose: Entity Bean Home Interface for collateral field meta data
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public interface EBCollateralMetaDataHome extends EJBHome {
	/**
	 * Creates a new meta data info on the entity bean Method will not be used,
	 * as data will be preloaded into the database
	 * 
	 * @return Remote interface to EBCollateralMetaData Bean
	 * @throws CreateException if it fails to the bean
	 * @throws RemoteException if a remote call encounters error
	 */
	public EBCollateralMetaData create() throws CreateException, RemoteException;

	/**
	 * @param id
	 * @return collateral ejb object
	 * @throws FinderException - if it fails to locate the entry
	 * @throws RemoteException - if a remote call encounters error
	 */
	public EBCollateralMetaData findByPrimaryKey(String id) throws FinderException, RemoteException;

	/**
	 * @param type
	 * @return Collection of CollateralMeta data for a subtype
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public Collection findBySubType(String type) throws FinderException, RemoteException;
}
