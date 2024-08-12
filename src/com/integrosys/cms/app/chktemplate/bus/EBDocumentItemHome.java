/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBDocumentItemHome.java,v 1.2 2003/08/22 07:49:44 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

/**
 * This is the Home interface for the EBDocumentItem Entity Bean.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 07:49:44 $ Tag: $Name: $
 */
public interface EBDocumentItemHome extends EJBHome {
	/**
	 * Create a document item information
	 * 
	 * @param anIDocumentItem - IDocumentItem
	 * @return EBDocumentItem
	 * @throws CreateException is error at record creation
	 * @throws RemoteException on errors
	 */
	public EBDocumentItem create(IDocumentItem anIDocumentItem) throws CreateException, RemoteException;

	/**
	 * Find by Primary Key which is the document item ID.
	 * 
	 * @param aDocumentItemID - long
	 * @return EBDocumentItem
	 * @throws FinderException if the record is not found
	 * @throws RemoteException on errors
	 */
	public EBDocumentItem findByPrimaryKey(Long aDocumentItemID) throws FinderException, RemoteException;

	/**
	 * Get the list of document items
	 * @param aCriteria - DocumentSearchCriteria
	 * @return SearchResult - the result containing the list of document items
	 *         that satisfy the criteria
	 * @throws SearchDAOException is error at the DAO
	 * @throws RemoteException on errors
	 */
	public SearchResult getDocumentItemList(DocumentSearchCriteria aCriteria) throws SearchDAOException, RemoteException;
	
	public SearchResult getFilteredDocumentItemList(DocumentSearchCriteria aCriteria,List docCrit) throws SearchDAOException, RemoteException;

	/**
	 * Get the number of doc item under the same category and having the same
	 * description
	 * @param aCategory of String type
	 * @param aDescription of String type
	 * @return int - the number of doc items
	 * @throws SearchDAOException, RemoteException
	 */
	public int getNoOfDocItemByDesc(String aCategory, String aDescription) throws SearchDAOException, RemoteException;
	
	public Collection findByItemCode(String aItemCode) throws FinderException, RemoteException;
}
