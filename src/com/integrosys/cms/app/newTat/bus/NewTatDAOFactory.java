/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/new Tat/bus/new TatDAOFactory.java,v 1.7 2003/07/30 01:59:05 hltan Exp $
 */
package com.integrosys.cms.app.newTat.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the new Tat DAO
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/07/30 01:59:05 $
 */

public class NewTatDAOFactory {

    /**
	 * Get the new Tat DAO
	 * @return INewTatDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static INewTatDAO getNewTatDAO() throws SearchDAOException {
		return new NewTatDAO();
	}
	
	public static INewTatJdbc NewTatJdbcImpl() throws SearchDAOException {
		return new NewTatJdbcImpl();
	}


}
