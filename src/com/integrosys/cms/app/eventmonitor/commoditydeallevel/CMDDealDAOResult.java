/*   Copyright Integro Technologies Pte Ltd
 *   CMDDealDAOResult.java
 *
 */
package com.integrosys.cms.app.eventmonitor.commoditydeallevel;

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
public class CMDDealDAOResult extends AbstractMonitorDAOResult {
	private DAOContext daoCtx = null;

	/**
	 * @param rs
	 * @param dbUtil
	 */
	public CMDDealDAOResult(DAOContext daoctx_, List results) throws SQLException, DBConnectionException {
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
