/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralSubTypeHome.java,v 1.5 2003/08/15 07:13:44 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Remote home interface for EBCollateralTypeBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/08/15 07:13:44 $ Tag: $Name: $
 */
public interface EBCollateralSubTypeHome extends EJBHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param colType of type ICollateralSubType
	 * @return collateral subtype ejb object
	 * @throws CreateException on error while creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBCollateralSubType create(ICollateralSubType colType) throws CreateException, RemoteException;

	/**
	 * Find the local ejb object by primary key, the collateral type code.
	 * 
	 * @param subTypeCode collateral type code
	 * @return local collateral subtype ejb object
	 * @throws FinderException on error while finding the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBCollateralSubType findByPrimaryKey(String subTypeCode) throws FinderException, RemoteException;

	/**
	 * Find the collateral sub types by its type code.
	 * 
	 * @param typeCode of type String
	 * @return a collection of collateral subtype ejb object
	 * @throws FinderException on error finding the subtype using the type code
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findByTypeCode(String typeCode) throws FinderException, RemoteException;

	/**
	 * Find all collateral subtypes.
	 * 
	 * @return a collection of collateral subtypes
	 * @throws FinderException on error finding collateral subtypes
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findAll() throws FinderException, RemoteException;

	/**
	 * Search security sub type based on its type code.
	 * 
	 * @param typeCode of type String
	 * @return a list of security subtypes
	 * @throws SearchDAOException on errror searching the security subtypes
	 * @throws RemoteException on error during remote method call
	 */
	public ICollateralSubType[] searchByTypeCode(String typeCode) throws SearchDAOException, RemoteException;

	/**
	 * Find security subtypes by its group id.
	 * 
	 * @param groupID group id
	 * @return a collection of <code>EBCollateralSubType</code>s
	 * @throws FinderException on error finding the subtypes
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findByGroupID(long groupID) throws FinderException, RemoteException;
}