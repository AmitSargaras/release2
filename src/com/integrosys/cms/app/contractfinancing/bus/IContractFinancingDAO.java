package com.integrosys.cms.app.contractfinancing.bus;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public interface IContractFinancingDAO extends IContractFinancingTableConstants {

	/**
	 * Get the list of contract finance summary info.
	 * @param aLimitProfileID of long type
	 * @return IContractFinancingSummary[] - the list of contract finance
	 *         summary
	 * @throws SearchDAOException on errors
	 */
	public IContractFinancingSummary[] getContractFinancingSummaryList(long aLimitProfileID) throws SearchDAOException;

	public HashMap getLimitDetailsByLimitID(long limitPk) throws SearchDAOException;
}
