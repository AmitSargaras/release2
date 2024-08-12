/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitHome.java,v 1.4 2003/07/10 06:28:03 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

//import com.integrosys.base.businfra.search.*;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * This is the Home interface to the EBLimit Entity Bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/10 06:28:03 $ Tag: $Name: $
 */
public interface EBLimitHome extends EJBHome {
	/**
	 * Create a Customer
	 * 
	 * @param value is the ILimit object
	 * @return EBLimit
	 * @throws CreateException, RemoteException
	 */
	public EBLimit create(ILimit value) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the customer ID
	 * 
	 * @param pk is the Long value of the primary key
	 * @return EBLimit
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBLimit findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by limit profile ID
	 * 
	 * @param profileID is the Long value of the limit profile ID
	 * @param status the status to be excluded in this find
	 * @return Collection of EBLimit
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public Collection findByLimitProfile(Long profileID, String status) throws FinderException, RemoteException;
}