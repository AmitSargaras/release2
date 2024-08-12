package com.integrosys.cms.app.tatduration.trx;

import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Describe this class. Purpose: Description:
 *
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 27, 2008 
 */
public interface ITatParamTrxValue extends ICMSTrxValue 
{

	/**
	 * Get the tat document business entity
	 *
	 * @return ITatDoc
	 */
	public ITatParam getTatParam();

	/**
	 * Get the staging tat document business entity
	 *
	 * @return ITatDoc
	 */
	public ITatParam getStagingTatParam();

	/**
	 * Set the tat document business entity
	 *
	 * @param value is of type ITatDoc
	 */
	public void setTatParam(ITatParam value);

	/**
	 * Set the staging tat document business entity
	 *
	 * @param value is of type ITatDoc
	 */
	public void setStagingTatParam(ITatParam value);
}
