package com.integrosys.cms.app.contractfinancing.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBTNCLocal extends EJBLocalObject {

	/**
	 * Return the TNC ID of the TNC
	 * @return long - the Terms and Conditions ID
	 */
	public long getTncID();

	/**
	 * Return the common reference of the TNC
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the TNC information.
	 * @return ITNC
	 */
	public ITNC getValue();

	/**
	 * Persist a TNC information
	 * @param value is of type ITNC
	 */
	public void setValue(ITNC value) throws ContractFinancingException;

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
