/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/forex/CMSForexDAOFactory.java,v 1.1 2003/08/05 11:34:53 hltan Exp $
 */
package com.integrosys.cms.app.forex;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the forex DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/05 11:34:53 $
 */

public class CMSForexDAOFactory {
	/**
	 * Get the cms forex DAO
	 * @return ICMSForexDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static ICMSForexDAO getCMSForexDAO() throws SearchDAOException {
		return new CMSForexDAO();
	}

}
