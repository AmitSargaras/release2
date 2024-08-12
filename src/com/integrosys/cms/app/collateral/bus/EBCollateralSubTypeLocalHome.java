/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBCollateralSubTypeLocalHome.java,v 1.7 2003/08/15 07:13:44 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Local home interface for EBCollateralTypeBean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/08/15 07:13:44 $ Tag: $Name: $
 */
public interface EBCollateralSubTypeLocalHome extends EJBLocalHome {
	/**
	 * Called by the client to create an local EJB object.
	 * 
	 * @param colType of type ICollateralSubType
	 * @return local collateral subtype ejb object
	 * @throws CreateException on error while creating the ejb
	 */
	public EBCollateralSubTypeLocal create(ICollateralSubType colType) throws CreateException;

	/**
	 * Find the local ejb object by primary key, the collateral type code.
	 * 
	 * @param typeCode collateral type code
	 * @return local collateral subtype ejb object
	 * @throws FinderException on error while finding the ejb
	 */
	public EBCollateralSubTypeLocal findByPrimaryKey(String typeCode) throws FinderException;

	/**
	 * Find the collateral sub types by its type code.
	 * 
	 * @param typeCode of type String
	 * @return a collection of collateral subtype ejb object
	 * @throws FinderException on error finding the subtype using the type code
	 */
	public Collection findByTypeCode(String typeCode) throws FinderException;

	/**
	 * Find all collateral subtypes.
	 * 
	 * @return a collection of collateral subtypes
	 * @throws FinderException on error finding collateral subtypes
	 */
	public Collection findAll() throws FinderException;

	/**
	 * Search security sub type based on its type code.
	 * 
	 * @param typeCode of type String
	 * @return a list of security subtypes
	 * @throws SearchDAOException on errror searching the security subtypes
	 */
	public ICollateralSubType[] searchByTypeCode(String typeCode) throws SearchDAOException;

	/**
	 * Find security subtypes by its group id.
	 * 
	 * @param groupID group id
	 * @return a collection of <code>EBCollateralSubType</code>s
	 * @throws FinderException on error finding the subtypes
	 */
	public Collection findByGroupID(long groupID) throws FinderException;
}