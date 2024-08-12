package com.integrosys.cms.app.commoncodeentry.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;

/**
 * @author $Author: BaoHongMan $<br>
 * @version $Id$
 */
public class CommonCodeEntryDAO extends JdbcTemplateAdapter {

	private static final String REVERT_STG_UPDATE = "UPDATE STAGE_COMMON_CODE_ENTRY STG_CCE "
			+ "SET (ENTRY_CODE,ENTRY_NAME,ACTIVE_STATUS,ENTRY_SOURCE,COUNTRY,REF_ENTRY_CODE,STATUS,IS_NEW) = "
			+ "	(SELECT ENTRY_CODE,ENTRY_NAME,ACTIVE_STATUS,ENTRY_SOURCE,COUNTRY,REF_ENTRY_CODE,STATUS, 'Z' "
			+ " FROM COMMON_CODE_CATEGORY_ENTRY CCE WHERE CCE.ENTRY_ID = STG_CCE.ENTRY_ID) "
			+ " WHERE STG_CCE.STAGING_REFERENCE_ID= ? AND STG_CCE.IS_NEW = 'U'";

	private static final String REVERT_STG_INSERT = "DELETE FROM STAGE_COMMON_CODE_ENTRY STG_CCE WHERE STG_CCE.STAGING_REFERENCE_ID = ? AND STG_CCE.IS_NEW = 'I'";

	public void clearUpdateFlag(Long groupId) {

		String query = "UPDATE STAGE_COMMON_CODE_ENTRY SET IS_NEW = 'Z' "
				+ " WHERE STAGING_REFERENCE_ID = ? AND IS_NEW <> 'Z' ";

		getJdbcTemplate().update(query, new Object[] { groupId });
	}

	public void revertToActual(Long groupId) {

		getJdbcTemplate().update(REVERT_STG_UPDATE, new Object[] { groupId });

		getJdbcTemplate().update(REVERT_STG_INSERT, new Object[] { groupId });

	}
	public boolean toBoolean(String s){
		if("1".equals(s))
		return true;
		else
			return false;
	}
	public Collection findByEntryValues(Long id,String desc,String value)
	throws DBConnectionException, SQLException{
		List code_entries = null;
		DBUtil dbUtil = null;
		dbUtil = new DBUtil();
		String sql="select * from common_code_category_entry where category_code_id = "+id;
		if(null!=desc)
			sql=sql+" and lower(entry_name) like '%"+desc.toLowerCase().trim()+"%'";
		if(null!=value)
			sql=sql+" and lower(entry_code) like '%"+value.toLowerCase().trim()+"%'";
		return getJdbcTemplate().query(sql, new RowMapper(){
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				OBCommonCodeEntry obentry=new OBCommonCodeEntry();
				obentry.setEntryName(rs.getString("ENTRY_NAME"));
				obentry.setEntryCode(rs.getString("ENTRY_CODE"));
				obentry.setCategoryCodeId(Long.parseLong(rs.getString("CATEGORY_CODE_ID")));
				obentry.setCountry(rs.getString("COUNTRY"));
				obentry.setStatus(rs.getString("STATUS"));
				obentry.setActiveStatus(toBoolean(rs.getString("ACTIVE_STATUS")));
				
				//Uma:Prod issue:Duplicate entries are getting created while disabling common code entries
				obentry.setEntryId(Long.parseLong(rs.getString("ENTRY_ID")));
				obentry.setCategoryCode(rs.getString("CATEGORY_CODE"));
				return obentry;
			}});
}
	public String getEntryNameByEntrycodeAndCategoryCode(String entryCode,String categoryCode)throws DBConnectionException, SQLException{
		List<Map<String,Object>> list = new ArrayList(); 
		try {
			String query = "select ENTRY_NAME FROM common_code_category_entry WHERE ENTRY_CODE = '"+entryCode+"' " +
					"and CATEGORY_CODE = '"+categoryCode+"'";
			list = getJdbcTemplate().queryForList(query);
			
			if(list!=null && list.size()>0){
				return (String)list.get(0).get("ENTRY_NAME");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public String getEntryNameByEntrycodeAndCCode(String entryCode,String categoryCode)throws DBConnectionException, SQLException{
		List<Map<String,Object>> list = new ArrayList();
		Map<String,String> entryNameMap = new HashMap<String, String>();
		
		if(entryNameMap!=null && entryNameMap.get(entryCode+"::"+categoryCode)!=null){
			return (String)entryNameMap.get(entryCode+"::"+categoryCode);
		}else{
			try {
				String query = "select ENTRY_NAME FROM common_code_category_entry WHERE ENTRY_CODE = '"+entryCode+"' " +
						"and CATEGORY_CODE = '"+categoryCode+"'";
				list = getJdbcTemplate().queryForList(query);
				
				if(list!=null && list.size()>0){
					entryNameMap.put(entryCode+"::"+categoryCode, (String)list.get(0).get("ENTRY_NAME"));
					return (String)list.get(0).get("ENTRY_NAME");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}