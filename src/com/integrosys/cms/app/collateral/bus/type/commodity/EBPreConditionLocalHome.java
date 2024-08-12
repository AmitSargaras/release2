/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBPreConditionLocalHome.java,v 1.1 2005/07/15 06:25:01 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * Defines pre-condition create and finder methods for local clients.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/07/15 06:25:01 $ Tag: $Name: $
 */
public interface EBPreConditionLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Create pre-condition.
	 * 
	 * @param preCondition of type IPreCondition
	 * @return local pre-condition ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBPreConditionLocal create(IPreCondition preCondition) throws CreateException;

	/**
	 * Find pre-condition by its primary key, the pre-condition id.
	 * 
	 * @param primaryKey precondition id
	 * @return local precondition ejb object
	 * @throws FinderException on error finding the precondition
	 */
	public EBPreConditionLocal findByPrimaryKey(Long primaryKey) throws FinderException;
}