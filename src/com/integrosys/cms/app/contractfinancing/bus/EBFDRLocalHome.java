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
public interface EBFDRLocalHome extends EJBLocalHome {

	/**
	 * Create a FDR object
	 * @param fdrObj is the IFDR object
	 * @return EBFDR
	 * @throws javax.ejb.CreateException on error
	 */
	public EBFDRLocal create(IFDR fdrObj) throws CreateException;

	/**
	 * Find by Primary Key which is the FDR ID.
	 * @param pk of long type
	 * @return EBFDR
	 * @throws javax.ejb.FinderException on error
	 */
	public EBFDRLocal findByPrimaryKey(Long pk) throws FinderException;

}
