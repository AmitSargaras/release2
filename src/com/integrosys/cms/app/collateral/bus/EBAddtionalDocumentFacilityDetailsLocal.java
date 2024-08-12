package com.integrosys.cms.app.collateral.bus;

import javax.ejb.EJBLocalObject;

public interface EBAddtionalDocumentFacilityDetailsLocal extends EJBLocalObject {
	/**
	 * Get the collateral insurance business object.
	 * 
	 * @return insurance
	 */
	public IAddtionalDocumentFacilityDetails getValue();

	/**
	 * Set the collateral insurance to this entity.
	 * 
	 * @param insurance is of type IInsurance
	 */
	public void setValue(IAddtionalDocumentFacilityDetails addtionalDocumentFacilityDetails);

	/**
	 * Delete the insurance policy.
	 */
	public void delete();
}