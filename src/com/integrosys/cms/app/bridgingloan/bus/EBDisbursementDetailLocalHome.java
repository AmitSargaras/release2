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
public interface EBDisbursementDetailLocalHome extends EJBLocalHome {

	/**
	 * Create a Disbursement Detail object
	 * @param obj is the IDisbursementDetail object
	 * @return EBDisbursementDetail
	 * @throws javax.ejb.CreateException on error
	 */
	public EBDisbursementDetailLocal create(IDisbursementDetail obj) throws CreateException;

	/**
	 * Find by Primary Key which is the Disbursement Detail ID.
	 * @param pk of long type
	 * @return EBDisbursementDetail
	 * @throws javax.ejb.FinderException on error
	 */
	public EBDisbursementDetailLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by unique common reference.
	 * @param commonRef of long type
	 * @return EBDisbursement
	 * @throws javax.ejb.FinderException on error
	 */
	// public EBDisbursementLocal findByCommonRef(long commonRef) throws
	// FinderException;

}
