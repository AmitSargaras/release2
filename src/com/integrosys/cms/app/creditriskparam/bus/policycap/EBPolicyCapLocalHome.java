package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public interface EBPolicyCapLocalHome extends EJBLocalHome {

	/**
	 * Create policy cap record.
	 * @param policyCap of type IPolicyCap
	 * @return EBPolicyCap - ejb object
	 * @throws javax.ejb.CreateException on error creating the ejb
	 */
	public EBPolicyCapLocal create(IPolicyCap policyCap) throws CreateException;

	/**
	 * Find by primary Key, the policy cap ID
	 * @param pk - Long
	 * @return EBPolicyCapLocal - the local handler for the policy cap that has
	 *         the PK as specified
	 * @throws FinderException
	 */
	public EBPolicyCapLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find the Policy Cap(s) given the Exchange Code
	 * @param exchangeCode of String type
	 * @return IPolicyCap[] - the Policy Cap List for the given exchange
	 * @throws javax.ejb.FinderException on finders errors
	 */
	// public Collection findPolicyCapByExchange(String exchangeCode) throws
	// FinderException;
	/**
	 * Get the Policy Cap by the Group ID
	 * @param groupID of Long type
	 * @return Collection of policy cap - the Policy Cap List for the given
	 *         group id
	 * @throws FinderException on finders errors
	 */
	public Collection findPolicyCapByGroupID(Long groupID) throws FinderException;
}
