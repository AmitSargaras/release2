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
public interface EBPaymentLocalHome extends EJBLocalHome {

	/**
	 * Create a Payment object
	 * @param obj is the IPayment object
	 * @return EBPayment
	 * @throws javax.ejb.CreateException on error
	 */
	public EBPaymentLocal create(IPayment obj) throws CreateException;

	/**
	 * Find by Primary Key which is the Payment ID.
	 * @param pk of long type
	 * @return EBPayment
	 * @throws javax.ejb.FinderException on error
	 */
	public EBPaymentLocal findByPrimaryKey(Long pk) throws FinderException;

}
