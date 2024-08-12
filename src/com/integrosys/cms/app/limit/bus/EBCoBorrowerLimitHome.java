/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBCoBorrowerLimitHome.java,v 1.1 2003/08/01 04:08:10 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

//import com.integrosys.base.businfra.search.*;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * This is the Home interface to the EBCoBorrowerLimit Entity Bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/01 04:08:10 $ Tag: $Name: $
 */
public interface EBCoBorrowerLimitHome extends EJBHome {
	/**
	 * Create a Customer
	 * 
	 * @param value is the ICoBorrowerLimit object
	 * @return EBCoBorrowerLimit
	 * @throws CreateException, RemoteException
	 */
	public EBCoBorrowerLimit create(ICoBorrowerLimit value) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the customer ID
	 * 
	 * @param pk is the Long value of the primary key
	 * @return EBCoBorrowerLimit
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBCoBorrowerLimit findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find CoBorrower from Limit ID
	 * 
	 * @param limitID is the Long value of the CoBorrower's outer Limit ID
	 * @return Collection of EBCoBorrowerLimit
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public Collection findByCoBorrowerOuterLimitID(Long limitID) throws FinderException, RemoteException;
}