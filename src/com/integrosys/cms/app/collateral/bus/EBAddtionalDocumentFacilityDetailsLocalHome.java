package com.integrosys.cms.app.collateral.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface EBAddtionalDocumentFacilityDetailsLocalHome extends EJBLocalHome {
	/**
	 * Create a new Addtional Document Facility Details.
	 * 
	 * @param addFacDocDet of type IAddtionalDocumentFacilityDetails
	 * @return local addFacDocDet ejb object
	 * @throws CreateException on error creating the ejb object
	 */
	public EBAddtionalDocumentFacilityDetailsLocal create(IAddtionalDocumentFacilityDetails addFacDocDet) throws CreateException;

	/**
	 * Find the Addtional Document Facility Details entity bean by its primary key.
	 * 
	 * @param addFacDocDetID addFacDocDet id
	 * @return local addFacDocDet ejb object
	 * @throws FinderException on error finding the ejb object
	 */
	public EBAddtionalDocumentFacilityDetailsLocal findByPrimaryKey(Long addFacDocDetID) throws FinderException;
}