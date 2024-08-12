package com.integrosys.cms.app.bridgingloan.bus;

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
public interface EBSalesProceedsLocalHome extends EJBLocalHome {

	/**
	 * Create a SalesProceeds object
	 * @param obj is the ISalesProceeds object
	 * @return EBSalesProceeds
	 * @throws javax.ejb.CreateException on error
	 */
	public EBSalesProceedsLocal create(ISalesProceeds obj) throws CreateException;

	/**
	 * Find by Primary Key which is the SalesProceeds ID.
	 * @param pk of long type
	 * @return EBSalesProceeds
	 * @throws javax.ejb.FinderException on error
	 */
	public EBSalesProceedsLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBSalesProceeds
	 * @throws javax.ejb.FinderException on error
	 */
	// public EBSalesProceedsLocal findByCommonRef(long commonRef) throws
	// FinderException;

}
