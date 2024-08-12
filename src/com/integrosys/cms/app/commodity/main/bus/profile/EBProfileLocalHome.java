/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/EBProfileLocalHome.java,v 1.2 2004/06/04 04:53:01 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.profile;

/**
 * Defines Profile home methods for clients.
 *
 * @author  $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since   $Date: 2004/06/04 04:53:01 $
 * Tag:     $Name:  $
 */

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines TitleDOcument home methods for clients.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:53:01 $ Tag: $Name: $
 */
public interface EBProfileLocalHome extends EJBLocalHome {
	/**
	 * Create collateral record.
	 * 
	 * @param collateral of type ICollateral
	 * @return Profile - ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBProfileLocal create(IProfile collateral) throws CreateException;

	/**
	 * Find collateral by primary key, the collateral id.
	 * 
	 * @param theID ID of the entity
	 * @return Profile - ejb object
	 * @throws FinderException on error finding the collateral
	 */
	public EBProfileLocal findByPrimaryKey(Long theID) throws FinderException;

	/**
	 * finds all TitleDocuements
	 * @return
	 * @throws FinderException
	 */
	public Collection findAll() throws FinderException;

	public Collection findAllNotDeleted() throws FinderException;

	public Collection findByGroupID(Long groupID) throws FinderException;

	public EBProfileLocal findByCommonRef(Long commonRef) throws FinderException;

	public EBProfileLocal findByGroupIDCommonRef(Long groupID, Long commonRef) throws FinderException;
}
