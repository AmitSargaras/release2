/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/CollateralTaskDAOFactory.java,v 1.1 2003/08/22 03:31:05 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the collateral task DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/22 03:31:05 $
 */

public class CollateralTaskDAOFactory {
	/**
	 * Get the collateral task DAO
	 * @return ICollateralTaskDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static ICollateralTaskDAO getCollateralTaskDAO() throws SearchDAOException {
		return new CollateralTaskDAO();
	}
}
