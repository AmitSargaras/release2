package com.integrosys.cms.app.chktemplate.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;
import java.rmi.RemoteException;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Apr 9, 2010
 * Time: 11:32:37 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EBDocAppItemHome extends EJBHome {
	/**
	 * Find by Primary Key which is the item ID.
	 *
	 * @param aItemID is long value of the contact ID
	 * @return EBItemLocal
	 * @throws FinderException on error
	 */
	public EBDocAppItem findByPrimaryKey(Long aItemID) throws FinderException, RemoteException;

	public EBDocAppItem create(Long aDocumentId , IDocumentAppTypeItem aDocumentAppTypeItem) throws CreateException, RemoteException;

}