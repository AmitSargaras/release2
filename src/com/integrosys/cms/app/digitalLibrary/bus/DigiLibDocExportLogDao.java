package com.integrosys.cms.app.digitalLibrary.bus;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementSetter;

import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;

public class DigiLibDocExportLogDao extends JdbcTemplateAdapter {
	
	public boolean addExportLog(final Map<String, Object> parameterMap) {
		String sqlString = "INSERT INTO CMS_DIGILIB_DOC_EXPORT_LOG("
				+ "REFERENCE_NO,PARTY_ID,PAN_NO,CLIMS_SYSTEM_ID,PARTY_NAME,INTERFACE_STATUS,INTERFACE_FAILED_REASON,REQUEST_TEXT,REQUEST_DATE,RESPONSE_TEXT,RESPONSE_DATE) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		getJdbcTemplate().update(sqlString, new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement preparedStatement) throws SQLException {
				preparedStatement.setString(1, (String) parameterMap.get("REFERENCE_NO"));
				preparedStatement.setString(2, (String) parameterMap.get("PARTY_ID"));
				preparedStatement.setString(3, (String) parameterMap.get("PAN_NO"));
				preparedStatement.setString(4, (String) parameterMap.get("CLIMS_SYSTEM_ID"));
				preparedStatement.setString(5, (String) parameterMap.get("PARTY_NAME"));
				preparedStatement.setString(6, (String) parameterMap.get("INTERFACE_STATUS"));
				preparedStatement.setString(7, (String) parameterMap.get("INTERFACE_FAILED_REASON"));
				preparedStatement.setString(8, (String) parameterMap.get("REQUEST_TEXT"));
				preparedStatement.setTimestamp(9, (Timestamp) parameterMap.get("REQUEST_DATE"));
				preparedStatement.setString(10, (String) parameterMap.get("RESPONSE_TEXT"));
				preparedStatement.setTimestamp(11, (Timestamp) parameterMap.get("RESPONSE_DATE"));
			}
		}

		);
		return true;
	}
}
