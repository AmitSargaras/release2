/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/bus/DocumentLocationDAOFactory.java,v 1.2 2004/03/17 10:27:20 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the document location DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/03/17 10:27:20 $
 */

public class DocumentLocationDAOFactory {
	/**
	 * Get the cc document location DAO
	 * @return ICCDocumentLocationDAO - the interface class for accessing the
	 *         DAO
	 * @throws SearchDAOException
	 */
	public static ICCDocumentLocationDAO getCCDocumentLocationDAO() throws SearchDAOException {
		return new CCDocumentLocationDAO();
	}
}
