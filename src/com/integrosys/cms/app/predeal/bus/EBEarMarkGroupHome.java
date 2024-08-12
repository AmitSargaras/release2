/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * EBEarMarkGroupHome
 *
 * Created on 5:46:06 PM
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
 * Created by IntelliJ IDEA. User: Eric Date: Apr 2, 2007 Time: 5:46:06 PM
 */
public interface EBEarMarkGroupHome extends EJBHome {

	public EBEarMarkGroup findByPrimaryKey(java.lang.Long earMarkGroupId) throws FinderException, RemoteException;

	public EBEarMarkGroup findBySourceAndFeedId(String systemSourceId, java.lang.Long feedId) throws FinderException,
			RemoteException;

	public Collection findByFeedId(java.lang.Long feedId) throws FinderException, RemoteException;

	public EBEarMarkGroup create() throws CreateException, RemoteException;

	public EBEarMarkGroup create(OBEarMarkGroup ob) throws CreateException, RemoteException;
}
