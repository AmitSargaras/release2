package com.integrosys.cms.app.common.util.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class DAOContext {
	Connection conn = null;

	public DAOContext() { // conn = DAO.getConnection();
	}

	public DAOContext(Connection conn_) {
		this.conn = conn_;
	}

	public DAOContext(boolean silent) { // if ( ! silent ) conn =
										// DAO.getConnection();
	}

	public void beginTransaction() throws SQLException {
		if (conn.getAutoCommit()) {
			conn.setAutoCommit(false);
			DefaultLogger.debug(this, "** set Auto Commit to FALSE ** ");
		}
		// conn.setAutoCommit(false);
	}

	public void commitTransaction() throws SQLException {
		conn.commit();
	}

	public void rollbackTransaction() {
		try {
			if (conn != null) {
				conn.rollback();
			}
		}
		catch (SQLException e) {
		}
	}

	public Connection getConnection() throws DataAccessException {
		if (conn == null) {
			conn = DAO.getConnection();
		}
		return conn;
	}

	public void close() {
		try {
			conn.close();
		}
		catch (SQLException e) {
		}
	}

	public DataAccessPreparedStatement prepareStatement(IDAODescriptor das, SearchingParameters criteria)
			throws DataAccessException {

		String queryKey = (String) criteria.get(IDAODescriptor.QUERYTAG);
		String sql = das.getQuery(queryKey);
		StringTokenizer st = new StringTokenizer(sql, IDAODescriptor.SQLDELIMTER);
		StringBuffer sbf = new StringBuffer();
		String nxt, value;
		while (st.hasMoreTokens()) {
			nxt = st.nextToken();
			if (criteria.containsKey(nxt)) {
				value = (String) criteria.get(nxt);
				sbf.append(value);
			}
			else {
				sbf.append(nxt);
			}
		}

		if (sbf.length() > 0) {
			sql = sbf.toString();
		}

		return new DataAccessPreparedStatement(this, sql);

	}

}