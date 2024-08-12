/*   Copyright Integro Technologies Pte Ltd
 *   AbstractDAODescriptor.java
 *
 */
package com.integrosys.cms.app.common.util.dataaccess;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author heju
 * @version 1.0
 * @since Jun 10, 2004
 */
public abstract class DAODescriptor implements IDAODescriptor {

	private Descriptor descriptor;

	public DAODescriptor() throws DataAccessException {
		// InputStream in =
		// getResourceClass().getResourceAsStream(getResourceName());
		InputStream in = getResource();
		if (in == null) {
			throw new DataAccessException("Null on getting input source.");
		}

		try {
			this.descriptor = new Descriptor(in, false);
		}
		finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (IOException e) {
				}
			}
			;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.app.common.util.dataaccess.IDAODescriptor#getQuery
	 * (java.lang.String)
	 */
	public String getQuery(String queryID) throws DataAccessException {
		return this.descriptor.getData(queryID);
	}

	abstract public InputStream getResource();
	// abstract public Class getResourceClass();
	// abstract public String getResourceName();

}
