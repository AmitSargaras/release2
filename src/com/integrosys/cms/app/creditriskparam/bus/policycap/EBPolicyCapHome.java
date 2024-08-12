package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public interface EBPolicyCapHome extends EJBHome {

	/**
	 * Create policy cap record.
	 * 
	 * @param policyCap of type IPolicyCap
	 * @return EBPolicyCap - ejb object
	 * @throws CreateException on error creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBPolicyCap create(IPolicyCap policyCap) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the policy cap ID
	 * @param pk - Long
	 * @return EBPolicyCap - the remote handler for the policy cap that has the
	 *         PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBPolicyCap findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find the Policy Cap(s) given the Exchange Code
	 * @param exchangeCode of String type
	 * @return Collection of policy cap - the Policy Cap List for the given
	 *         exchange
	 * @throws FinderException on finders errors
	 * @throws RemoteException on remote errors
	 */
	// public Collection findPolicyCapByExchange(String exchangeCode) throws
	// FinderException, RemoteException;

	/**
	 * Get the Policy Cap by the Group ID
	 * @param groupID of Long type
	 * @return Collection of policy cap - the Policy Cap List for the given
	 *         group id
	 * @throws FinderException on finders errors
	 * @throws RemoteException on remote errors
	 */
	public Collection findPolicyCapByGroupID(Long groupID) throws FinderException, RemoteException;
}
