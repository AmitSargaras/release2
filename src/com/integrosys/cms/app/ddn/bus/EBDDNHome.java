/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/EBDDNHome.java,v 1.2 2003/10/22 09:23:30 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Home interface for the ddn entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/10/22 09:23:30 $ Tag: $Name: $
 */

public interface EBDDNHome extends EJBHome {
	/**
	 * Create a ddn
	 * @param anIDDN of IDDN type
	 * @return EBDDN - the remote handler for the created ddn
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBDDN create(IDDN anIDDN) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the ddn ID
	 * @param aPK of Long type
	 * @return EBDDN - the remote handler for the ddn that has the PK as
	 *         specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBDDN findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by primary Key, the ddn ID
	 * @param aLimitProfileID of Long type
	 * @return Collection - a collection of remote handlers for the ddn that has
	 *         the limit profile as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public Collection findByLimitProfileID(Long aLimitProfileID) throws FinderException, RemoteException;

	/**
	 * To get the DDNID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return long - the DDN ID
	 * @throws SearchDAOException
	 * @throws RemoteException on remote errors
	 */
	public long getDDNIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException, RemoteException;

	/**
	 * To get the number of ddn that satisfy the criteria
	 * @param aCriteria of DDNSearchCriteria type
	 * @return int - the number of ddn that satisfy the criteria
	 * @throws SearchDAOException on errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfDDN(DDNSearchCriteria aCriteria) throws SearchDAOException, RemoteException;

}