package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Apr 9, 2010
 * Time: 11:32:37 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EBDocumentItemLocalHome extends EJBLocalHome {
	/**
	 * Create a document item information
	 *
	 * @param anIDocumentItem - IDocumentItem
	 * @return EBDocumentItemLocal
	 * @throws CreateException is error at record creation
	 * @throws RemoteException on errors
	 */
	public EBDocumentItemLocal create(IDocumentItem anIDocumentItem) throws CreateException;

	/**
	 * Find by Primary Key which is the document item ID.
	 *
	 * @param aDocumentItemID - long
	 * @return EBDocumentItemLocal
	 * @throws FinderException if the record is not found
	 * @throws RemoteException on errors
	 */
	public EBDocumentItemLocal findByPrimaryKey(Long aDocumentItemID) throws FinderException;

	/**
	 * Get the list of document items
	 * @param aCriteria - DocumentSearchCriteria
	 * @return SearchResult - the result containing the list of document items
	 *         that satisfy the criteria
	 * @throws SearchDAOException is error at the DAO
	 * @throws RemoteException on errors
	 */
	public SearchResult getDocumentItemList(DocumentSearchCriteria aCriteria) throws SearchDAOException;

	/**
	 * Get the number of doc item under the same category and having the same
	 * description
	 * @param aCategory of String type
	 * @param aDescription of String type
	 * @return int - the number of doc items
	 * @throws SearchDAOException, RemoteException
	 */
	public int getNoOfDocItemByDesc(String aCategory, String aDescription) throws SearchDAOException;

	public Collection findByItemCode(String aItemCode) throws FinderException;
}