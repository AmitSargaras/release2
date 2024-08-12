/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/EBDDNLocalHome.java,v 1.1 2003/08/13 11:27:25 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Local Home interface for the ddn entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 11:27:25 $ Tag: $Name: $
 */

public interface EBDDNLocalHome extends EJBLocalHome {
	/**
	 * Create a ddn
	 * @param anIDDN of IDDN type
	 * @return EBDDNLocal - the local handler for the created ddn
	 * @throws CreateException if creation fails
	 */
	public EBDDNLocal create(IDDN anIDDN) throws CreateException;

	/**
	 * Find by primary Key, the ddn ID
	 * @param aPK of Long type
	 * @return EBDDN - the local handler for the ddn that has the PK as
	 *         specified
	 * @throws FinderException
	 */
	public EBDDNLocal findByPrimaryKey(Long aPK) throws FinderException;

	/**
	 * Find by primary Key, the ddn ID
	 * @param aLimitProfileID of Long type
	 * @return Collection - a collection of remote handlers for the ddn that has
	 *         the limit profile as specified
	 * @throws FinderException
	 */
	public Collection findByLimitProfileID(Long aLimitProfileID) throws FinderException;

	/**
	 * To get the number of ddn that satisfy the criteria
	 * @param aCriteria of DDNSearchCriteria type
	 * @return int - the number of ddn that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfDDN(DDNSearchCriteria aCriteria) throws SearchDAOException;

}