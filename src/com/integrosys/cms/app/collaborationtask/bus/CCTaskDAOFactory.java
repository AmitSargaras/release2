/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/CCTaskDAOFactory.java,v 1.1 2003/08/31 13:56:24 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the CC task DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 13:56:24 $
 */

public class CCTaskDAOFactory {
	/**
	 * Get the CC task DAO
	 * @return ICCTaskDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static ICCTaskDAO getCCTaskDAO() throws SearchDAOException {
		return new CCTaskDAO();
	}
}
