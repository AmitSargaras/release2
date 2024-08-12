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
public interface EBDisbursementLocalHome extends EJBLocalHome {

	/**
	 * Create a Disbursement object
	 * @param obj is the IDisbursement object
	 * @return EBDisbursement
	 * @throws javax.ejb.CreateException on error
	 */
	public EBDisbursementLocal create(IDisbursement obj) throws CreateException;

	/**
	 * Find by Primary Key which is the Disbursement ID.
	 * @param pk of long type
	 * @return EBDisbursement
	 * @throws javax.ejb.FinderException on error
	 */
	public EBDisbursementLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBDisbursement
	 * @throws javax.ejb.FinderException on error
	 */
	// public EBDisbursementLocal findByCommonRef(long commonRef) throws
	// FinderException;

}
