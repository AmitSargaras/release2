package com.integrosys.cms.app.contractfinancing.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBFDRLocal extends EJBLocalObject {

	/**
	 * Return the FDR ID of the FDR
	 * @return long - the FDR ID
	 */
	public long getFdrID();

	/**
	 * Return the common reference of the FDR
	 * @return long - the common reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the FDR information.
	 * @return IFDR
	 */
	public IFDR getValue();

	/**
	 * Persist a FDR information
	 * @param value is of type IFDR
	 */
	public void setValue(IFDR value) throws ContractFinancingException;

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
