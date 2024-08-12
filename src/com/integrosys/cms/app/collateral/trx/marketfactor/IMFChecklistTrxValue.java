/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.marketfactor;

import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Contains actual MF Checklist and staging MF Checklist for transaction usage.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IMFChecklistTrxValue extends ICMSTrxValue {
	/**
	 * Gets the actual MF Checklist objects in this transaction.
	 * 
	 * @return The actual IMFChecklist
	 */
	public IMFChecklist getMFChecklist();

	/**
	 * Sets the actual MF Checklist objects for this transaction.
	 * 
	 * @param value the actual IMFChecklist
	 */
	public void setMFChecklist(IMFChecklist value);

	/**
	 * Gets the staging MF Checklist objects in this transaction.
	 * 
	 * @return the staging IMFChecklist
	 */
	public IMFChecklist getStagingMFChecklist();

	/**
	 * Sets the staging MF Checklist objects for this transaction.
	 * 
	 * @param value the staging IMFChecklist
	 */
	public void setStagingMFChecklist(IMFChecklist value);

}
