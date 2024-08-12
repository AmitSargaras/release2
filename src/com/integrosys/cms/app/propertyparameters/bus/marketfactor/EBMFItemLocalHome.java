/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBMFItem Entity Bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFItemLocalHome extends EJBLocalHome {

	/**
	 * Create a local MF Item Entity Bean
	 * 
	 * @param value is the IMFItem object
	 * @return EBMFItemLocal
	 * @throws CreateException on error
	 */
	public EBMFItemLocal create(IMFItem value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the primary key
	 * @return EBMFItemLocal
	 * @throws FinderException on error
	 */
	public EBMFItemLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find by template ID
	 * 
	 * @param mFTemplateID is the Long value of the template ID
	 * @param status the status to be excluded in this find
	 * @return Collection of EBMFItemLocal
	 * @throws FinderException on error
	 */
	public Collection findByTemplateID(long mFTemplateID, String status) throws FinderException;
}