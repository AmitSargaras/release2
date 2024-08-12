/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBSubLimitLocalHome.java,v 1.2 2004/08/18 08:00:51 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * Defines loan agency sub-limit create and finder methods for local clients.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/08/18 08:00:51 $ Tag: $Name: $
 */
public interface EBSubLimitLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Create loan agency sub-limit.
	 * 
	 * @param subLimit of type ISubLimit
	 * @return local sub-limit ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBSubLimitLocal create(ISubLimit subLimit) throws CreateException;

	/**
	 * Find sub-limit by its primary key, the subLimit id.
	 * 
	 * @param primaryKey borrower id
	 * @return local sub-limit ejb object
	 * @throws FinderException on error finding the subLimit
	 */
	public EBSubLimitLocal findByPrimaryKey(Long primaryKey) throws FinderException;

	/**
	 * Find all sub-limits.
	 * 
	 * @return a Collection of sub-limit local ejb object
	 * @throws FinderException on error finding the sub-limits
	 */
	public Collection findAll() throws FinderException;
}