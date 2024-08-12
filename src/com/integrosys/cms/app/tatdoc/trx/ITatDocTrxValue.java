package com.integrosys.cms.app.tatdoc.trx;

import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Describe this class. Purpose: Description:
 *
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 27, 2008 
 */
public interface ITatDocTrxValue extends ICMSTrxValue {

	/**
	 * Get the tat document business entity
	 *
	 * @return ITatDoc
	 */
	public ITatDoc getTatDoc();

	/**
	 * Get the staging tat document business entity
	 *
	 * @return ITatDoc
	 */
	public ITatDoc getStagingTatDoc();

	/**
	 * Set the tat document business entity
	 *
	 * @param value is of type ITatDoc
	 */
	public void setTatDoc(ITatDoc value);

	/**
	 * Set the staging tat document business entity
	 *
	 * @param value is of type ITatDoc
	 */
	public void setStagingTatDoc(ITatDoc value);
}
