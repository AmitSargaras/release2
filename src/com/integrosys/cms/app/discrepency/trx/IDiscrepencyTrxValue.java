package com.integrosys.cms.app.discrepency.trx;

import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */

public interface IDiscrepencyTrxValue extends ICMSTrxValue{

	/**
	 * Get the discrepency business entity
	 * 
	 * @return IDisprepency
	 */
	public IDiscrepency getActualDiscrepency();

	/**
	 * Get the staging discrepency business entity
	 * 
	 * @return ICMSDiscrepency
	 */
	public IDiscrepency getStagingDiscrepency();

	/**
	 * Set the discrepency business entity
	 * 
	 * @param value is of type IDiscrepency
	 */
	public void setActualDiscrepency(IDiscrepency value);

	/**
	 * Set the staging discrepency business entity
	 * 
	 * @param value is of type IDiscrepency
	 */
	public void setStagingDiscrepency(IDiscrepency value);
}
