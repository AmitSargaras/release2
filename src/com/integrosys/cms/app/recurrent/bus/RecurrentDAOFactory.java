package com.integrosys.cms.app.recurrent.bus;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the checkList DAO
 *
 * @author $Author: hltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2003/07/30 01:59:05 $
 */

public class RecurrentDAOFactory {

	/**
	 * Get the recurrent checklist DAO
	 * @return IRecurrentCheckListDAO - the interface class for accessing the
	 *         DAO
	 * @throws com.integrosys.base.businfra.search.SearchDAOException
	 */
	public static IRecurrentCheckListDAO getRecurrentCheckListDAO() throws SearchDAOException {
		return new RecurrentCheckListDAO();
	}

}
