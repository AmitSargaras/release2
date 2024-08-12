package com.integrosys.cms.app.checklist.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class CheckListJdbcImpl extends JdbcDaoSupport implements ICheckListJdbc {

	@Override
	public Map<String, String> getChecklistItemsCollateralType(Long checklistId) {
		String sql = "SELECT sci.insurance_id,gci.cms_collateral_id,css.type_name,css.subtype_name " + 
				" FROM stage_checklist_item sci " + 
				" INNER JOIN cms_stage_gc_insurance gci ON gci.ins_code = sci.insurance_id " + 
				" INNER JOIN cms_security css ON css.cms_collateral_id = gci.cms_collateral_id " + 
				" WHERE sci.checklist_id=? AND insurance_id IS NOT NULL " + 
				" UNION " + 
				" SELECT sci.insurance_id,pol.cms_collateral_id,css.subtype_name,css.type_name FROM stage_checklist_item sci " + 
				" INNER JOIN cms_stage_insurance_policy pol ON CAST(pol.insurance_policy_id AS VARCHAR2(100)) = sci.insurance_id " + 
				" INNER JOIN cms_stage_security css ON css.cms_collateral_id = pol.cms_collateral_id " + 
				" WHERE sci.checklist_id=? AND insurance_id IS NOT NULL ";
		
		return getJdbcTemplate().query(sql, new Object[] { checklistId, checklistId },
				new ResultSetExtractor<Map<String, String>>() {

					public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<String, String> map = new HashMap<String, String>();

						while (rs.next()) {
							map.put(rs.getString("insurance_id"),
									rs.getString("type_name") + "/" + rs.getString("subtype_name"));
						}
						return map;
					}
				});
	}

}
