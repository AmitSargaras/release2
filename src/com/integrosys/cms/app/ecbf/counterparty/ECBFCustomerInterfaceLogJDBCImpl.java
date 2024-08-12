package com.integrosys.cms.app.ecbf.counterparty;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class ECBFCustomerInterfaceLogJDBCImpl extends JdbcDaoSupport implements IECBFCustomerInterfaceLogJDBC {

	@Override
	public Map<String, Long> findAllFailedRequest(Date date) throws Exception {
		StringBuffer sql = new StringBuffer("select max(id) id ,partyid from CMS_ECBF_CUST_INTERFACE_LOG")
				.append(" where INTERFACESTATUS = 'F' and trunc(REQUESTDATETIME) = trunc(?) group by partyid");
		
		return (Map<String, Long>) getJdbcTemplate().query(sql.toString(),new Object[] {date} ,new ResultSetExtractor() {
			
			@Override
			public Map<String, Long> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Long> returnData = new HashMap<String, Long>();
				while(rs.next()) {
					returnData.put(rs.getString("partyid"), rs.getLong("id"));
				}
				return returnData;
			}
		});
		
	}

}