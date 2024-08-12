package com.integrosys.cms.app.contractfinancing.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBTNCLocalHome extends EJBLocalHome {

	/**
	 * Create a TNC object
	 * @param fdrObj is the ITNC object
	 * @return EBTNC
	 * @throws javax.ejb.CreateException on error
	 */
	// public EBTNCLocal create(Long fkey, ITNC fdrObj) throws
	// ContractFinancingException, CreateException;
	public EBTNCLocal create(ITNC fdrObj) throws CreateException;

	/**
	 * Find by Primary Key which is the TNC ID.
	 * @param pk of long type
	 * @return EBTNC
	 * @throws javax.ejb.FinderException on error
	 */
	public EBTNCLocal findByPrimaryKey(Long pk) throws FinderException;

}
