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
public interface EBSettlementLocalHome extends EJBLocalHome {

	/**
	 * Create a Settlement object
	 * @param obj is the ISettlement object
	 * @return EBSettlement
	 * @throws javax.ejb.CreateException on error
	 */
	public EBSettlementLocal create(ISettlement obj) throws CreateException;

	/**
	 * Find by Primary Key which is the Settlement ID.
	 * @param pk of long type
	 * @return EBSettlement
	 * @throws javax.ejb.FinderException on error
	 */
	public EBSettlementLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBSettlement
	 * @throws javax.ejb.FinderException on error
	 */
	// public EBSettlementLocal findByCommonRef(long commonRef) throws
	// FinderException;

}
