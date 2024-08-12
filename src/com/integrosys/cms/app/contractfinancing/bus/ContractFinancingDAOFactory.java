package com.integrosys.cms.app.contractfinancing.bus;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public class ContractFinancingDAOFactory {

	/**
	 * Get the contract finance DAO
	 * @return IContractFinanceDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static IContractFinancingDAO getContractFinancingDAO() throws SearchDAOException {
		return new ContractFinancingDAO();
	}

}
