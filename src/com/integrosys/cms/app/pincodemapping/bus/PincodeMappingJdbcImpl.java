package com.integrosys.cms.app.pincodemapping.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


public class PincodeMappingJdbcImpl extends JdbcDaoSupport implements
IPincodeMappingJdbc  {

	private static final String SELECT_PINCODE_MAPPING_TRX = "SELECT id,state_id,pincode,last_update_date,status from CMS_STATE_PINCODE_MAP where status= 'ACTIVE' ORDER BY UPPER(pincode)";
	/**
	 * @return List of all authorized PincodeMapping
	 */

	public List getAllPincodeMapping() {
		List resultList = null;
		try {
			resultList = getJdbcTemplate().query(SELECT_PINCODE_MAPPING_TRX,
					new PincodeMappingRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
			throw new PincodeMappingException(
					"ERROR-- Unable to get PincodeMapping List");
		}

		return resultList;
	}
	public class PincodeMappingRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBPincodeMapping result = new OBPincodeMapping();
			/*result.setStateCode(rs.getString("state_code"));*/
			/*result.setStateId(Long.parseLong(rs.getString("state_id")));*/	
			result.setPincode(Long.parseLong(rs.getString("pincode")));	
			result.setStatus(rs.getString("status"));
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setId(rs.getLong("id"));
			return result;
		}
	}

	public Map<String, String> getActiveStatePinCodeMap() {
		String sql =  "SELECT STATE_ID, PINCODE FROM CMS_STATE_PINCODE_MAP where STATUS = ? AND DEPRECATED =? ";
		
		List<Object> param = new ArrayList<Object>();
		param.add("ACTIVE");
		param.add("N");
		
		try{
			return (HashMap) getJdbcTemplate().query(sql, param.toArray(), new ResultSetExtractor() {
				public HashMap extractData(ResultSet rs) throws SQLException, DataAccessException {
					
					HashMap<String,String> pincodeMap = new HashMap();
					while(rs.next()) {
						String stateId = rs.getString("STATE_ID");
						String pincode = rs.getString("PINCODE");
						pincodeMap.put(stateId,pincode );
					}
					return pincodeMap;
				}
			});
		}
		catch (Exception e) {
        	e.printStackTrace();
			return null;
		}
	}
}
