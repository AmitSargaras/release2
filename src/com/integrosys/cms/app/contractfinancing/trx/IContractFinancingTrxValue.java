package com.integrosys.cms.app.contractfinancing.trx;

import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public interface IContractFinancingTrxValue extends ICMSTrxValue {

	/**
	 * Get the contract finance business entity
	 * 
	 * @return IContractFinancing
	 */
	public IContractFinancing getContractFinancing();

	/**
	 * Get the staging contract finance business entity
	 * 
	 * @return IContractFinancing
	 */
	public IContractFinancing getStagingContractFinancing();

	/**
	 * Set the contract finance business entity
	 * 
	 * @param value is of type IContractFinancing
	 */
	public void setContractFinancing(IContractFinancing value);

	/**
	 * Set the staging contract finance business entity
	 * 
	 * @param value is of type IContractFinancing
	 */
	public void setStagingContractFinancing(IContractFinancing value);
}
