/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/SCCertificateDAOFactory.java,v 1.2 2003/08/11 12:48:22 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Factory class for the certificate DAO
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/11 12:48:22 $
 */

public class SCCertificateDAOFactory {
	/**
	 * Get the sc certificate DAO
	 * @return ISCCertificateDAO - the interface class for accessing the DAO
	 * @throws SearchDAOException
	 */
	public static ISCCertificateDAO getSCCertificateDAO() throws SearchDAOException {
		return new SCCertificateDAO();
	}

	/**
	 * Get the partial sc certificate DAO
	 * @return IPartialSCCertificateDAO - the interface class for accessing the
	 *         DAO
	 * @throws SearchDAOException
	 */
	public static IPartialSCCertificateDAO getPartialSCCertificateDAO() throws SearchDAOException {
		return new PartialSCCertificateDAO();
	}
}
