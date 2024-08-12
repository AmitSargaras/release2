/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/SBDDNBusManager.java,v 1.4 2005/06/08 06:39:06 htli Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//java
import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Session bean remote interface for the services provided by the DDN bus
 * manager
 * 
 * @author $Author: htli $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/06/08 06:39:06 $ Tag: $Name: $
 */
public interface SBDDNBusManager extends EJBObject {
	/**
	 * Get the DDN by the DDN ID
	 * @param aDDNID of long type
	 * @return IDDN - the ddn
	 * @throws DDNException on errors @ throws RemoteExcepion on remote errors
	 */
	public IDDN getDDN(long aDDNID) throws DDNException, RemoteException;

	/**
	 * Get the DDN by the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return IDDN - the DDN
	 * @throws DDNException on errors @ throws RemoteExcepion on remote errors
	 */
	public IDDN getDDNByLimitProfileID(long aLimitProfileID, String type) throws DDNException, RemoteException;

	/**
	 * To get the number of DDN that satisfy the criteria
	 * @param aCriteria of DDNSearchCriteria type
	 * @return int - the number of DDN that satisfy the criteria
	 * @throws DDNException on errors
	 * @throws SearchDAOException on DAO errors
	 * @throws RemoteException on remote errors
	 */
	public int getNoOfDDN(DDNSearchCriteria aCriteria) throws DDNException, SearchDAOException, RemoteException;

	/**
	 * Create a DDN
	 * @param anIDDN of IDDN type
	 * @return IDDN - the DDN created
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDDN createDDN(IDDN anIDDN) throws DDNException, RemoteException;

	/**
	 * Update a DDN
	 * @param anIDDN of IDDN
	 * @return IDDN - the DDN updated
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public IDDN updateDDN(IDDN anIDDN) throws ConcurrentUpdateException, DDNException, RemoteException;

	/**
	 * Update the scc issued indicator
	 * @param aLimitProfileID of long type
	 * @param anIndicator of boolean type
	 * @throws ConcurrentUpdateException if there is a concurrent update
	 * @throws DDNException on errors
	 * @throws RemoteException on remote errors
	 */
	public void setSCCIssuedIndicator(long aLimitProfileID, boolean anIndicator) throws ConcurrentUpdateException,
			DDNException, RemoteException;
}
