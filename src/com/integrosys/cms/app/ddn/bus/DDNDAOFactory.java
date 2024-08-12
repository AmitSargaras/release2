/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/DDNDAOFactory.java,v 1.1 2003/08/13 11:27:25 hltan Exp $
 */
package com.integrosys.cms.app.ddn.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the ddn DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/13 11:27:25 $
 */

public class DDNDAOFactory {
	/**
	 * Get the ddn DAO
	 * @return IDDNDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static IDDNDAO getDDNDAO() throws SearchDAOException {
		return new DDNDAO();
	}
}
