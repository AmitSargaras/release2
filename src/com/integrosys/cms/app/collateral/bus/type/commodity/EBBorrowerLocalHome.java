/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBBorrowerLocalHome.java,v 1.3 2004/08/17 11:57:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * Defines borrower create and finder methods for local clients.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/17 11:57:46 $ Tag: $Name: $
 */
public interface EBBorrowerLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Create loan agency borrower.
	 * 
	 * @param borrower of type IBorrower
	 * @return local borrower ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBBorrowerLocal create(IBorrower borrower) throws CreateException;

	/**
	 * Find borrower by its primary key, the borrower id.
	 * 
	 * @param pk borrower id
	 * @return local borrower ejb object
	 * @throws FinderException on error finding the borrower
	 */
	public EBBorrowerLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find all borrowers.
	 * 
	 * @return a Collection of borrower local ejb object
	 * @throws FinderException on error finding the borrowers
	 */
	public Collection findAll() throws FinderException;
}