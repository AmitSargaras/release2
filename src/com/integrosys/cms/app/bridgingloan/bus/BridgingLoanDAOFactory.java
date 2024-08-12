package com.integrosys.cms.app.bridgingloan.bus;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 23, 2007 Tag: $Name$
 */
public class BridgingLoanDAOFactory {

	/**
	 * Get the bridging loan DAO
	 * @return IBridgingLoanDAO - the interface class for accessing the DAO
	 * @throws com.integrosys.base.businfra.search.SearchDAOException
	 */
	public static IBridgingLoanDAO getBridgingLoanDAO() throws SearchDAOException {
		return new BridgingLoanDAO();
	}

}
