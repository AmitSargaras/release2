/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/CustodianDAOFactory.java,v 1.1 2003/06/19 12:39:17 hltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the custodian DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/06/19 12:39:17 $
 */

public class CustodianDAOFactory {
	/**
	 * Get the custodian DAO
	 * @return ICustodianDAO - the interface class for accessing the DAO
	 * @exception SearchDAOException
	 */
	public static ICustodianDAO getCustodianDAO() throws SearchDAOException {
		return new CustodianDAO();
	}
}
