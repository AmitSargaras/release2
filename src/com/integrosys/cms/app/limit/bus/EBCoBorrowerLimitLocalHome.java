/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBCoBorrowerLimitLocalHome.java,v 1.2 2003/08/01 04:07:45 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBCoBorrowerLimit Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/01 04:07:45 $ Tag: $Name: $
 */
public interface EBCoBorrowerLimitLocalHome extends EJBLocalHome {
	/**
	 * Create a co-borrower limit information type
	 * 
	 * @param value is the ICoBorrowerLimit object
	 * @return EBCoBorrowerLimitLocal
	 * @throws CreateException on error
	 */
	public EBCoBorrowerLimitLocal create(ICoBorrowerLimit value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param pk is Long value of the primary key
	 * @return EBCoBorrowerLimitLocal
	 * @throws FinderException on error
	 */
	public EBCoBorrowerLimitLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find CoBorrower from Limit ID
	 * 
	 * @param limitID is the Long value of the CoBorrower's outer Limit ID
	 * @return Collection of EBCoBorrowerLimit
	 * @throws FinderException on error
	 */
	public Collection findByCoBorrowerOuterLimitID(Long limitID) throws FinderException;
}