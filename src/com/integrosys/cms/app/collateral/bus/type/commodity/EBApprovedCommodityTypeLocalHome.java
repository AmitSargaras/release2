/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBApprovedCommodityTypeLocalHome.java,v 1.3 2004/08/18 02:44:31 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines approved commodity type's create and finder methods for local
 * clients.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/18 02:44:31 $ Tag: $Name: $
 */
public interface EBApprovedCommodityTypeLocalHome extends EJBLocalHome {
	/**
	 * Create approved commodity type.
	 * 
	 * @param value of type IApprovedCommodityType
	 * @return ejb object of approved commodity type
	 * @throws CreateException on error creating the approved commodity type
	 */
	public EBApprovedCommodityTypeLocal create(IApprovedCommodityType value) throws CreateException;

	/**
	 * Find approved commodity type by its primary key, the approved commodity
	 * type id.
	 * 
	 * @param pk approved commodity type id
	 * @return ejb object of approved commodity type
	 * @throws FinderException on error finding the approved commodity type
	 */
	public EBApprovedCommodityTypeLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Finds all approved commodity types.
	 * 
	 * @return a collection of approved commodity type ejb object
	 * @throws FinderException on error finding the approved commodity types
	 */
	public Collection findAll() throws FinderException;
}