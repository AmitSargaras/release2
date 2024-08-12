/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Entity Home Interface of PolicyCapGroup
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 28/Aug/2007 $ Tag: $Name: $
 */
public interface EBPolicyCapGroupHome extends EJBHome {

	/**
	 * Create policy cap record.
	 * 
	 * @param policyCapGroup of type IPolicyCapGroup
	 * @return EBPolicyCapGroup - ejb object
	 * @throws CreateException on error creating the ejb
	 * @throws RemoteException on error during remote method call
	 */
	public EBPolicyCapGroup create(IPolicyCapGroup policyCapGroup) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the policy cap group ID
	 * @param pk - Long
	 * @return EBPolicyCapGroup - the remote handler for the policy cap that has
	 *         the PK as specified
	 * @throws FinderException
	 * @throws RemoteException on remote errors
	 */
	public EBPolicyCapGroup findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Get the policy cap group by given exchange code and bank entity
	 * @param exchangeCode
	 * @param bankEntity
	 * @return a collection that contains the policy cap group which belongs to
	 *         the exchange code and bank entity
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public Collection findPolicyCapGroupByExchangeBank(String exchangeCode, String bankEntity) throws FinderException,
			RemoteException;

}
