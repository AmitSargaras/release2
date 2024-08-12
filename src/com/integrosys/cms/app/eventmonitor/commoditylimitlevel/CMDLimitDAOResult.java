/*   Copyright Integro Technologies Pte Ltd
 *   CMDLimitDAOResult.java created on  3:13:32 PM Jun 10, 2004
 *
 */
package com.integrosys.cms.app.eventmonitor.commoditylimitlevel;

import java.sql.SQLException;
import java.util.List;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.cms.app.common.util.dataaccess.DAOContext;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorDAOResult;

/**
 * @author heju
 * @version 1.0
 * @since Jun 10, 2004
 */
public class CMDLimitDAOResult extends AbstractMonitorDAOResult {
	private DAOContext daoCtx = null;

	/**
	 * @param results
	 * @param daoctx_
	 */
	public CMDLimitDAOResult(DAOContext daoctx_, List results) throws SQLException, DBConnectionException {
		// super(rs_, new DBUtil());
		super(results);
		daoCtx = daoctx_;
	}

	/**
	 * Overridden of super's close
	 */
	public void close() {
		// super.close();
		this.daoCtx.close();
	}
}
