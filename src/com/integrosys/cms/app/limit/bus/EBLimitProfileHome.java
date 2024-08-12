/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitProfileHome.java,v 1.6 2003/07/10 06:28:03 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

//import com.integrosys.base.businfra.search.*;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * This is the Home interface to the EBLimitProfile Entity Bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/07/10 06:28:03 $ Tag: $Name: $
 */
public interface EBLimitProfileHome extends EJBHome {
	/**
	 * Create a Limit Profile. Does not create the limits.
	 * 
	 * @param value is the ILimitProfile object
	 * @return EBLimitProfile
	 * @throws CreateException, RemoteException
	 */
	public EBLimitProfile create(ILimitProfile value) throws CreateException, RemoteException;

	/**
	 * Find by primary Key
	 * 
	 * @param pk is the Long value of the primary key
	 * @return EBLimitProfile
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBLimitProfile findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by Customer ID and BCA Status
	 * 
	 * @param customerID is of type Long
	 * @param status is of type String
	 * @return Collection of EBLimit
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public Collection findByCustomerBCAStatus(Long customerID, String status) throws FinderException, RemoteException;
}