package com.integrosys.cms.app.contractfinancing.proxy;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public class ContractFinancingProxyManagerFactory {

	/**
	 * Default Constructor
	 */
	public ContractFinancingProxyManagerFactory() {
	}

	/**
	 * Get the contract finance proxy manager.
	 * @return IContractFinancingProxyManager - the contract finance proxy
	 *         manager
	 */
	public static IContractFinancingProxyManager getContractFinancingProxyManager() {
		return new ContractFinancingProxyManagerImpl();
	}

}
