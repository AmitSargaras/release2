package com.integrosys.cms.app.checklist.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Nov 15, 2005 Time: 5:48:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EBDocumentshareHome extends EJBHome {

	public EBDocumentshare create(Long aCheckListItemID, IShareDoc anIShareDoc) throws CreateException, RemoteException;

	public EBDocumentshare findByPrimaryKey(Long documentCheckListId) throws FinderException, RemoteException;

}
