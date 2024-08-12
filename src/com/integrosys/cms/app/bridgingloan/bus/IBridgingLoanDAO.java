package com.integrosys.cms.app.bridgingloan.bus;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 23, 2007 Tag: $Name$
 */
public interface IBridgingLoanDAO extends IBridgingLoanTableConstants {

	/**
	 * Get the list of bridging loan summary info.
	 * @param aLimitProfileID of long type
	 * @return IBridgingLoanSummary[] - the list of bridging loan summary
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on errors
	 */
	public IBridgingLoanSummary[] getBridgingLoanSummaryList(long aLimitProfileID) throws SearchDAOException;

	public HashMap getLimitDetailsByLimitID(long limitPk) throws SearchDAOException;
}
