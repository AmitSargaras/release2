package com.integrosys.cms.app.contractfinancing.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBAdvanceLocal extends EJBLocalObject {

	/**
	 * Return the Advance ID of the Advance Object
	 * @return long - the Advance ID
	 */
	public long getAdvanceID();

	/**
	 * Return the common reference of the Advance Object
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the Advance information.
	 * @return IAdvance
	 */
	public IAdvance getValue() throws ContractFinancingException;

	/**
	 * Persist a advance information
	 * @param value is of type IAdvance
	 */
	public void setValue(IAdvance value) throws ContractFinancingException;

	/**
	 * Get the deleted indicator
	 * @return boolean - the delete indicator
	 */
	public boolean getIsDeleted();

	/**
	 * Get the deleted indicator
	 * @param isDeleted - the deleted indicator
	 */
	public void setIsDeleted(boolean isDeleted);

	/**
	 * Synchronize payment items.
	 * @param value - IAdvance object that contains the list of payment items
	 */
	public void synchronizePaymentItemList(IAdvance value) throws ContractFinancingException;

}
