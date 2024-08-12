/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * EBEarMarkHome
 *
 * Created on 10:03:09 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 10:03:09 AM
 */
public interface EBEarMarkHome extends EJBHome {
	public EBEarMark create() throws CreateException, RemoteException;

	public EBEarMark create(OBEarMark ob) throws CreateException, RemoteException;

	public EBEarMark findByPrimaryKey(java.lang.Long earMarkId) throws FinderException, RemoteException;

	public Collection findByEarMarkGroupId(java.lang.Long earMarkGroupId) throws FinderException, RemoteException;

	public Collection findByFeedId(java.lang.Long feedId) throws FinderException, RemoteException;
}