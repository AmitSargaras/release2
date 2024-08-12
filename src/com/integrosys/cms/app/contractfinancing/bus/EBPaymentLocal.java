package com.integrosys.cms.app.contractfinancing.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBPaymentLocal extends EJBLocalObject {

	/**
	 * Return the Payment ID of the Payment Object
	 * @return long - the Payment ID
	 */
	public long getPaymentID();

	/**
	 * Return the common reference of the Payment Object
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the Payment information.
	 * @return IPayment
	 */
	public IPayment getValue();

	/**
	 * Persist a payment information
	 * @param value is of type IPayment
	 */
	public void setValue(IPayment value) throws ContractFinancingException;

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

}
