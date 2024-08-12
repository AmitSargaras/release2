package com.integrosys.cms.app.bridgingloan.trx;

import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IBridgingLoanTrxValue extends ICMSTrxValue {

	/**
	 * Get the bridging loan business entity
	 * 
	 * @return IBridgingLoan
	 */
	public IBridgingLoan getBridgingLoan();

	/**
	 * Get the staging bridging loan business entity
	 * 
	 * @return IBridgingLoan
	 */
	public IBridgingLoan getStagingBridgingLoan();

	/**
	 * Set the bridging loan business entity
	 * 
	 * @param value is of type IBridgingLoan
	 */
	public void setBridgingLoan(IBridgingLoan value);

	/**
	 * Set the staging bridging loan business entity
	 * 
	 * @param value is of type IBridgingLoan
	 */
	public void setStagingBridgingLoan(IBridgingLoan value);
}
