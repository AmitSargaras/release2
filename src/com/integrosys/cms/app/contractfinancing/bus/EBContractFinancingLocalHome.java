package com.integrosys.cms.app.contractfinancing.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public interface EBContractFinancingLocalHome extends EJBLocalHome {

	/**
	 * Create contract finance record.
	 * 
	 * @param contractFinancing of type IContractFinance
	 * @return EBContractFinanceLocal - ejb object
	 * @throws javax.ejb.CreateException on error creating the ejb
	 */
	public EBContractFinancingLocal create(IContractFinancing contractFinancing) throws CreateException;

	/**
	 * Find by primary Key, the contract finance ID
	 * @param pk - Long
	 * @return EBContractFinanceLocal - the local handler for the contract
	 *         finance that has the PK as specified
	 * @throws javax.ejb.FinderException
	 */
	public EBContractFinancingLocal findByPrimaryKey(Long pk) throws FinderException;

}
