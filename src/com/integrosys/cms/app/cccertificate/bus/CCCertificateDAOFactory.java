/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/CCCertificateDAOFactory.java,v 1.2 2003/08/05 11:31:37 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the certificate DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/05 11:31:37 $
 */

public class CCCertificateDAOFactory {
	/**
	 * Get the cc certificate DAO
	 * @return ICCCertificateDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static ICCCertificateDAO getCCCertificateDAO() throws SearchDAOException {
		return new CCCertificateDAO();
	}

}
