/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBMFChecklistItem Entity Bean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFChecklistItemLocalHome extends EJBLocalHome {

	/**
	 * Create a local MF Checklist Item Entity Bean
	 * 
	 * @param value is the IMFChecklistItem object
	 * @return EBMFChecklistItemLocal
	 * @throws CreateException on error
	 */
	public EBMFChecklistItemLocal create(IMFChecklistItem value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the primary key
	 * @return EBMFChecklistItemLocal
	 * @throws FinderException on error
	 */
	public EBMFChecklistItemLocal findByPrimaryKey(Long pk) throws FinderException;

}