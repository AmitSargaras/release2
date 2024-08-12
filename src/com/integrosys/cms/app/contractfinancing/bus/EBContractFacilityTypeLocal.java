package com.integrosys.cms.app.contractfinancing.bus;

import javax.ejb.EJBLocalObject;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBContractFacilityTypeLocal extends EJBLocalObject {

	/**
	 * Return the contract facility type ID of the contract facility type
	 * @return long - the contract facility type ID
	 */
	public long getFacilityTypeID();

	/**
	 * Return the contract facility type reference of the contract facility type
	 * @return long - the contract facility type reference
	 */
	public long getCommonRef();

	/**
	 * Return an object representation of the contract facility type
	 * information.
	 * @return IContractFacilityType
	 */
	public IContractFacilityType getValue();

	/**
	 * Persist a facility type information
	 * @param value is of type IContractFacilityType
	 */
	public void setValue(IContractFacilityType value) throws ContractFinancingException;

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
