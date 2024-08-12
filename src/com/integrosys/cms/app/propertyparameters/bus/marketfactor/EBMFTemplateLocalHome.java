/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local home interface for EBMFTemplateBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFTemplateLocalHome extends EJBLocalHome {

	/**
	 * Find the local ejb object by primary key, the MF Template ID.
	 * 
	 * @param mFTemplateIDPK MF Template ID
	 * @return local MF Template ejb object
	 * @throws FinderException on error while finding the ejb
	 */
	public EBMFTemplateLocal findByPrimaryKey(Long mFTemplateIDPK) throws FinderException;

}