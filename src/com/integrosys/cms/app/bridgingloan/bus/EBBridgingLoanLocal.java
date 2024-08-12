package com.integrosys.cms.app.bridgingloan.bus;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 22, 2007 Tag: $Name$
 */
public interface EBBridgingLoanLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of a bridging loan
	 * @return IBridgingLoan - the object encapsulating the bridging loan info
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoan getValue() throws BridgingLoanException;

	/**
	 * Set the bridging loan object
	 * @param bridgingLoan - IBridgingLoan
	 * @throws BridgingLoanException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IBridgingLoan bridgingLoan) throws BridgingLoanException, ConcurrentUpdateException;

}
