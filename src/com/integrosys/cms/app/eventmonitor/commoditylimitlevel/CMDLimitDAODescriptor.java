/*   Copyright Integro Technologies Pte Ltd
 *   CMDLimitDAODescriptor.java created on  3:09:54 PM Jun 10, 2004
 *
 */
package com.integrosys.cms.app.eventmonitor.commoditylimitlevel;

import java.io.InputStream;

import com.integrosys.cms.app.common.util.dataaccess.DAODescriptor;
import com.integrosys.cms.app.common.util.dataaccess.DataAccessException;

/**
 * @author heju
 * @version 1.0
 * @since Jun 10, 2004
 */
public class CMDLimitDAODescriptor extends DAODescriptor {
	private final static String defaultResourceName = "/CMSTransactionDAODescriptor.xml";

	public final static String LEGAL_TAGPATH = "/TransactionDAO/SQL/SecruityCoverageNotification/CMDLimit/LegalGroup";

	public final static String LIMIT_TAGPATH = "/TransactionDAO/SQL/SecruityCoverageNotification/CMDLimit/LimitInfo";

	/**
	 * @throws DataAccessException
	 */
	public CMDLimitDAODescriptor() throws DataAccessException {
		super();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.common.util.dataaccess.DAODescriptor#getResource()
	 */
	public InputStream getResource() {
		return this.getClass().getResourceAsStream(defaultResourceName);
	}
}
