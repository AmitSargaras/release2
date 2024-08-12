/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/GenerateRequestDAOFactory.java,v 1.2 2003/09/12 02:29:26 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the certificate DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/12 02:29:26 $
 */

public class GenerateRequestDAOFactory {
	/**
	 * Get the waiver request DAO
	 * @return IWaiverRequestDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static IWaiverRequestDAO getWaiverRequestDAO() throws SearchDAOException {
		return new WaiverRequestDAO();
	}

	/**
	 * Get the deferral request DAO
	 * @return IDeferralRequestDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static IDeferralRequestDAO getDeferralRequestDAO() throws SearchDAOException {
		return new DeferralRequestDAO();
	}

}
