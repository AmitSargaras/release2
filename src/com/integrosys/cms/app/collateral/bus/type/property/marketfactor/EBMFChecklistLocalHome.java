/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBMFChecklistBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFChecklistLocalHome extends EJBLocalHome {

	/**
	 * Find the local ejb object by primary key, the MF Checklist ID.
	 * 
	 * @param mFChecklistIDPK MF Checklist ID
	 * @return local MF Checklist ejb object
	 * @throws FinderException on error while finding the ejb
	 */
	public EBMFChecklistLocal findByPrimaryKey(Long mFChecklistIDPK) throws FinderException;

	/**
	 * Find the local MF Checklist ejb object by collateral ID.
	 * 
	 * @param collateralID collateral ID ID of type long
	 * @return EBMFChecklistLocal
	 * @throws FinderException on error while finding the ejb
	 */
	public EBMFChecklistLocal findByCollateralID(long collateralID) throws FinderException;
}