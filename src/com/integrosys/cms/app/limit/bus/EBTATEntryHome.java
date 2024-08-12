/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBTATEntryHome.java,v 1.2 2003/08/25 07:37:18 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

//import com.integrosys.base.businfra.search.*;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * This is the Home interface to the EBTATEntry Entity Bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/25 07:37:18 $ Tag: $Name: $
 */
public interface EBTATEntryHome extends EJBHome {
	/**
	 * Create a Customer
	 * 
	 * @param limitProfileID is the limit profile ID in long value
	 * @param value is the ITATEntry object
	 * @return EBTATEntry
	 * @throws CreateException, RemoteException
	 */
	public EBTATEntry create(long limitProfileID, ITATEntry value) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the customer ID
	 * 
	 * @param pk is the Long value of the primary key
	 * @return EBTATEntry
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBTATEntry findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by limit profile ID
	 * 
	 * @param profileID is the Long value of the limit profile ID
	 * @return Collection of EBLimitLocal
	 * @throws FinderException, RemoteException on error
	 */
	public Collection findByLimitProfile(Long profileID) throws FinderException, RemoteException;
}