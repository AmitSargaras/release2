package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 22, 2007 Tag: $Name$
 */
public interface EBBridgingLoanLocalHome extends EJBLocalHome {

	/**
	 * Create a bridging loan object
	 * @param bridgingLoanObj - IBridgingLoan
	 * @return EBBridgingLoanLocal - the bridging loan being created
	 * @throws CreateException on errors
	 */
	public EBBridgingLoanLocal create(IBridgingLoan bridgingLoanObj) throws CreateException;

	/**
	 * Get the bridging loan object by the primary key
	 * @param pk - primary key used to retrieve the trxValue
	 * @return EBBridgingLoan
	 * @throws FinderException on errors
	 */
	public EBBridgingLoanLocal findByPrimaryKey(Long pk) throws FinderException;

}
