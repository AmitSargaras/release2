/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBGuarantorLocalHome.java,v 1.3 2004/08/17 11:57:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * Defines guarantor create and finder methods for local clients.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/17 11:57:46 $ Tag: $Name: $
 */
public interface EBGuarantorLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Create loan agency guarantor.
	 * 
	 * @param guarantor of type IGuarantor
	 * @return local guarantor ejb object
	 * @throws javax.ejb.CreateException on error creating the ejb
	 */
	public EBGuarantorLocal create(IGuarantor guarantor) throws CreateException;

	/**
	 * Find guarantor by its primary key, the guarantor id.
	 * 
	 * @param pk guarantor id
	 * @return local guarantor ejb object
	 * @throws FinderException on error finding the guarantor
	 */
	public EBGuarantorLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find all guarantors.
	 * 
	 * @return a Collection of guarantor local ejb object
	 * @throws FinderException on error finding the guarantors
	 */
	public Collection findAll() throws FinderException;
}