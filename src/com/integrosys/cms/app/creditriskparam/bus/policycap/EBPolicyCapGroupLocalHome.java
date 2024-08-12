/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity Local Home Interface of PolicyCapGroup Bean
 * 
 * @author $Author: Siew Kheat $<br>
 * @version $Revision: $
 * @since $Date: 28/Aug/2007 $ Tag: $Name: $
 */
public interface EBPolicyCapGroupLocalHome extends EJBLocalHome {

	/**
	 * Create policy cap record.
	 * 
	 * @param policyCapGroup of type IPolicyCapGroup
	 * @return EBPolicyCapGroup - ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBPolicyCapGroupLocal create(IPolicyCapGroup policyCapGroup) throws CreateException;

	/**
	 * Find by primary Key, the policy cap group ID
	 * @param pk - Long
	 * @return EBPolicyCapGroup - the remote handler for the policy cap that has
	 *         the PK as specified
	 * @throws FinderException
	 */
	public EBPolicyCapGroupLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Get the policy cap group by given exchange code and bank entity
	 * @param exchangeCode
	 * @param bankEntity
	 * @return a collection that contains the policy cap group which belongs to
	 *         the exchange code and bank entity
	 * @throws FinderException
	 */
	public Collection findPolicyCapGroupByExchangeBank(String exchangeCode, String bankEntity) throws FinderException;

}
