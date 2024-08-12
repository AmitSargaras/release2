/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCustomerSysXRefHome.java,v 1.1 2003/07/14 08:22:46 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * This is the Home interface to the EBCustomerSysXRef Entity Bean
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/14 08:22:46 $ Tag: $Name: $
 */
public interface EBCustomerSysXRefHome extends EJBHome {
	/**
	 * Create a customer CustomerSysXRef information type
	 * 
	 * @param customerID is the Legal ID of type long
	 * @param value is the ICustomerSysXRef object
	 * @return EBCustomerSysXRef
	 * @throws CreateException, RemoteException on error
	 */
	public EBCustomerSysXRef create(long customerID, ICustomerSysXRef value) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the customer ID
	 * 
	 * @param pk is the Long value of the primary key
	 * @return EBSMECreditApplication
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBCustomerSysXRef findByPrimaryKey(Long pk) throws FinderException, RemoteException;
}