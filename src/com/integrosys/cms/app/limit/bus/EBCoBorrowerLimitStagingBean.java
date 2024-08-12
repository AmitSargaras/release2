package com.integrosys.cms.app.limit.bus;

import java.util.Collection;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/08/03 03:06:45 $ Tag: $Name: $
 */
public abstract class EBCoBorrowerLimitStagingBean extends EBCoBorrowerLimitBean {

	/**
	 * Default Constructor
	 */
	public EBCoBorrowerLimitStagingBean() {
	}

	// Getters
	/**
	 * Get all Collateral Allocations Not implemented. always return null
	 * 
	 * @return Collection of EBCollateralAllocationLocal objects
	 */
	public Collection getCMRCollateralAllocations() {
		return null;
	}

	// Setters
	/**
	 * Get all Limits Not implemented.
	 * 
	 * @param value is a Collection of EBCollateralAllocationLocal objects
	 */
	public void setCMRCollateralAllocations(Collection value) {
		// do nothing
	}

	/**
	 * Method to create Collateral Allocations. This is to be implemented only
	 * by actual bean. Staging bean should provide a non-implementation.
	 * 
	 * @param allocList is of type ICollateralAllocation[]
	 * @throws LimitException on errors
	 */
	protected void createCollateralAllocation(ICollateralAllocation[] allocList) throws LimitException {
		// do nothing
	}

}
