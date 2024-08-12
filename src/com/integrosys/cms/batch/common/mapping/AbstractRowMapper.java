package com.integrosys.cms.batch.common.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractRowMapper implements RowMapper{

	public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
		if (arg0 == null)
			return null;
	
		return doMapRow(arg0, arg1);
	}
	
	public abstract Object doMapRow(ResultSet arg0, int arg1) throws SQLException;

}
