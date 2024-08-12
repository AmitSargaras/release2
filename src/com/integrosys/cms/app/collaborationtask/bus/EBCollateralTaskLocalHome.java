/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/EBCollateralTaskLocalHome.java,v 1.1 2003/08/15 14:02:05 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//javax
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Local Home interface for the collateral collaboration task entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/15 14:02:05 $ Tag: $Name: $
 */

public interface EBCollateralTaskLocalHome extends EJBLocalHome {
	/**
	 * Create a collateral collaboration task
	 * @param anICollateralTask of ICollateralTask
	 * @return EBCollateralTaskLocal - the local handler for the created
	 *         collateral task
	 * @throws CreateException if creation fails
	 */
	public EBCollateralTaskLocal create(ICollateralTask anICollateralTask) throws CreateException;

	/**
	 * Find by primary Key, the collateral task ID
	 * @param aPK - Long
	 * @return EBCollateralTaskLocal - the local handler for the collateral task
	 *         that has the PK as specified
	 * @throws FinderException
	 */
	public EBCollateralTaskLocal findByPrimaryKey(Long aPK) throws FinderException;

}