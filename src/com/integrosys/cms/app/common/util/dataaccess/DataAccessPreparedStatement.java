/*
 * Created on Jun 7, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package com.integrosys.cms.app.common.util.dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author heju
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Generation - Code and Comments
 */
public class DataAccessPreparedStatement {
	private String sql;

	DAOContext ctx;

	public DataAccessPreparedStatement(DAOContext ctx_, String sql_) {
		this.sql = sql_;
		this.ctx = ctx_;
	}

	public ResultSet executeQuery() throws DataAccessException {
		PreparedStatement stmt;
		try {
			stmt = ctx.getConnection().prepareStatement(this.sql);
			ResultSet rslt = stmt.executeQuery();
			return rslt;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException(
					"Failed on Statement Execution in DataAccessPreparedStatement.executeQuery()", e.fillInStackTrace());
		}
		finally {
			/*
			 * try { if ( stmt != null )stmt.close(); } catch ( SQLException e)
			 * { }
			 */
		}

	}

	public int countOfQuery() throws DataAccessException {
		PreparedStatement stmt = null;
		try {
			// DefaultLogger.debug(this, this.sql);
			String countSQL = "SELECT COUNT(*) CNT FROM (" + this.sql + ")";
			stmt = ctx.getConnection().prepareStatement(countSQL);
			ResultSet rslt = stmt.executeQuery();
			int count = 0;
			if (rslt != null) {
				if (rslt.next()) {
					count = rslt.getInt("CNT");
				}
			}
			return count;
		}
		catch (SQLException e) {
			throw new DataAccessException(
					"Failed on Statement Execution in DataAccessPreparedStatement.executeQuery()", e.fillInStackTrace());
		}
		finally {

			try {
				if (stmt != null) {
					stmt.close();
				}
			}
			catch (SQLException e) {
			}
		}

	}

	public String toString() {
		return this.sql;
	}
}
