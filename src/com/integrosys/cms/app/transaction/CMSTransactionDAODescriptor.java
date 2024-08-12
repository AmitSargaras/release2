/*
 * Created on Jun 4, 2004
 *
 */
package com.integrosys.cms.app.transaction;

import java.io.InputStream;

import com.integrosys.cms.app.common.util.dataaccess.DAOContext;
import com.integrosys.cms.app.common.util.dataaccess.DAODescriptor;
import com.integrosys.cms.app.common.util.dataaccess.DataAccessException;
import com.integrosys.cms.app.common.util.dataaccess.DataAccessPreparedStatement;
import com.integrosys.cms.app.common.util.dataaccess.IDAODescriptor;
import com.integrosys.cms.app.common.util.dataaccess.SearchingParameters;

/**
 * @author heju
 * 
 */
public class CMSTransactionDAODescriptor extends DAODescriptor {

	private final static String defaultResourceName = "/CMSTransactionDAODescriptor.xml";

	// Please ensure the ~.xml put in CLASSPATH "/" , here put it under
	// ./config.

	public CMSTransactionDAODescriptor() throws DataAccessException {
		super();
	}

	/**
	 * Overriden getQuery with prefix path
	 */
	public String getQuery(String queryID) throws DataAccessException {
		return super.getQuery("/TransactionDAO/SQL/" + queryID);

	}

	/**
	 * Implementation of getResource
	 * @see com.integrosys.cms.app.common.util.dataaccess.DAODescriptor#getResource()
	 */
	public InputStream getResource() {
		return this.getClass().getResourceAsStream(defaultResourceName);
	}

	// For a test
	public static void main(String[] argv) {
		try {
			DAOContext ctx = new DAOContext(true);
			IDAODescriptor das = new CMSTransactionDAODescriptor();
			SearchingParameters criteria = new SearchingParameters(IDAODescriptor.QUERYTAG,
					com.integrosys.cms.app.common.constant.ICMSConstant.TODO_ACTION);
			DataAccessPreparedStatement st = ctx.prepareStatement(das, criteria);
//			System.out.print(st);
		}
		catch (DataAccessException e) {
//			System.out.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
