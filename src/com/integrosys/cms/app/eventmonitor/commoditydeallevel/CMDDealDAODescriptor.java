/*   Copyright Integro Technologies Pte Ltd
 *   CMDDealDAODescriptor.java
 *
 */
package com.integrosys.cms.app.eventmonitor.commoditydeallevel;

import java.io.InputStream;

import com.integrosys.cms.app.common.util.dataaccess.DAODescriptor;
import com.integrosys.cms.app.common.util.dataaccess.DataAccessException;

/**
 * @author heju
 * @version 1.0
 * @since Jun 10, 2004
 */

public class CMDDealDAODescriptor extends DAODescriptor {
	public final static String LEGAL_TAGPATH = "/TransactionDAO/SQL/SecruityCoverageNotification/CMDDeal/LegalGroup";

	public final static String DEAL_TAGPATH = "/TransactionDAO/SQL/SecruityCoverageNotification/CMDDeal/DealInfo";

	private final static String defaultResourceName = "/CMSTransactionDAODescriptor.xml";

	public CMDDealDAODescriptor() throws DataAccessException {
		super();
	}

	/**
	 * Implementation of getResource
	 * @see com.integrosys.cms.app.common.util.dataaccess.DAODescriptor#getResource()
	 */
	public InputStream getResource() {
		return this.getClass().getResourceAsStream(defaultResourceName);
	}

}
