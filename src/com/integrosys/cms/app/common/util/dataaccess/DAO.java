package com.integrosys.cms.app.common.util.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.integrosys.base.techinfra.dbsupport.ConnectionManagerFactory;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.IConnectionManager;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

public class DAO {
	private static Connection getJDBCConnection() throws DataAccessException {
		try {

			// Load the Oracle JDBC driver
			DriverManager.registerDriver((java.sql.Driver) (Class.forName(PropertyManager
					.getValue("dbconfig.batch.database.driver")).newInstance()));

			// Connect to the database
			// You must put a database name after the @ sign in the connection
			// URL.
			// You can use either the fully specified SQL*net syntax or a short
			// cut
			// syntax as <host>:<port>:<sid>.
			String dbUrl = PropertyManager.getValue("dbconfig.batch.database.url");
			String user = PropertyManager.getValue("dbconfig.batch.userId");
			String password = PropertyManager.getValue("dbconfig.batch.password");
			Connection conn = DriverManager.getConnection(dbUrl, user, password);
			return conn;
		}
		catch (Exception e) {
			throw new DataAccessException("Failed on SQL Execution", e.fillInStackTrace());
		}

	}

	private static Connection getJNDIConnection() throws DataAccessException {
		Connection conn = null;
		try {
			Context ctx = new InitialContext();
			if (ctx == null) {
				throw new Exception("Boom - No Context");
			}

			DataSource ds = (DataSource) ctx.lookup(PropertyManager.getValue("dbconfig.weblogic.datasource.jndiname"));

			if (ds != null) {
				conn = ds.getConnection();
			}
			if (conn == null) {
				throw new DataAccessException("Failed on DB Connection");
			}

			return conn;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new DataAccessException(e.getMessage(), e.fillInStackTrace());
		}
	}

	public static Connection getConnection() throws DataAccessException {
		try {
			IConnectionManager connectionManager = ConnectionManagerFactory.createConnectionManager();
			Connection con = connectionManager.createConnection();

			if (con.getAutoCommit()) {
				con.setAutoCommit(false);
			}

			return con;
		}
		catch (SQLException e) { // try local connection
			// e.printStackTrace();
			return getJDBCConnection();
			// throw new
			// DataAccessException("Failed on SQL Execution",e.fillInStackTrace
			// ());
		}
		catch (DBConnectionException e) {
			// e.printStackTrace();
			return getJDBCConnection();
			// throw new
			// DataAccessException("Failed on DB Connection",e.fillInStackTrace
			// ());
		}
	}
}