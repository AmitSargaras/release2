package com.integrosys.cms.app.checklist.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBDocumentshareLocalHome extends EJBLocalHome {

	public EBDocumentshareLocal create(Long CheckListItemID, IShareDoc anIShareDoc) throws CreateException;

	public EBDocumentshareLocal findByPrimaryKey(Long aDocShareId) throws FinderException;

	public Collection findByCheckListID(long aCheckListId) throws FinderException;

	// public List getShareDocumentItemId(long aCheckListId) throws
	// SearchDAOException ;
}