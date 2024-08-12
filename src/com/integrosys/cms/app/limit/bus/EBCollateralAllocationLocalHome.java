/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBCollateralAllocationLocalHome.java,v 1.3 2003/08/01 02:48:01 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * This is the Local Home interface for the EBCollateralAllocation Entity Bean.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/01 02:48:01 $ Tag: $Name: $
 */
public interface EBCollateralAllocationLocalHome extends EJBLocalHome {
	/**
	 * Create a collateral allocation information type
	 * 
	 * @param value is the ICollateralAllocation object
	 * @return EBCollateralAllocationLocal
	 * @throws CreateException on error
	 */
	public EBCollateralAllocationLocal create(ICollateralAllocation value) throws CreateException;

	/**
	 * Find by Primary Key.
	 * 
	 * @param chargeID is of type Long
	 * @return EBCollateralAllocationLocal
	 * @throws FinderException on error
	 */
	public EBCollateralAllocationLocal findByPrimaryKey(Long chargeID) throws FinderException;
}