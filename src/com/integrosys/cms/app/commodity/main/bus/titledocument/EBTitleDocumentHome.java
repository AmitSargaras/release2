/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/titledocument/EBTitleDocumentHome.java,v 1.2 2004/06/04 04:53:15 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.titledocument;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Defines TitleDOcument home methods for clients.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:53:15 $ Tag: $Name: $
 */
public interface EBTitleDocumentHome extends EJBHome {
	/**
	 * Create collateral record.
	 * 
	 * @param collateral of type ICollateral
	 * @return TitleDocument - ejb object
	 * @throws CreateException on error creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBTitleDocument create(ITitleDocument collateral) throws CreateException, RemoteException;

	/**
	 * Find collateral by primary key, the collateral id.
	 * 
	 * @param theID ID of the entity
	 * @return TitleDocument - ejb object
	 * @throws FinderException on error finding the collateral
	 * @throws RemoteException on error during remote method call
	 */
	public EBTitleDocument findByPrimaryKey(Long theID) throws FinderException, RemoteException;

	/**
	 * finds all TitleDocuements
	 * @return
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public Collection findAll() throws FinderException, RemoteException;

	public Collection findAllNotDeleted() throws FinderException, RemoteException;

	public Collection findByGroupID(Long groupID) throws FinderException, RemoteException;

	public EBTitleDocument findByCommonRef(Long commonRef) throws FinderException, RemoteException;

	public EBTitleDocument findByGroupIDCommonRef(Long groupID, Long commonRef) throws FinderException, RemoteException;

}
