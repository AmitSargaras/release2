package com.integrosys.cms.host.eai.limit.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.cms.app.collateral.bus.ILimitChargeMap;
import com.integrosys.cms.app.collateral.bus.OBLimitChargeMap;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.OBLimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commoncodeentry.bus.ICommonCodeEntry;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

public class LimitJdbcImpl extends JdbcDaoSupport implements ILimitJdbc {

	private String selectLimitProfileIdByOldCMSId = "SELECT CMS_LSP_LMT_PROFILE_ID FROM SCI_LSP_LMT_PROFILE WHERE CMS_LSP_LMT_PROFILE_ID = ? ";

	private String selectLimitIdByLmtProfileIDAndOldLimitId = "SELECT CMS_LSP_APPR_LMTS_ID FROM SCI_LSP_APPR_LMTS WHERE CMS_LIMIT_PROFILE_ID = ? and CMS_LSP_APPR_LMTS_ID = ? ";

	private String selectLimitSecurityMapByLimitProfileIdAndOldLimitSecMapId = "SELECT CHARGE_ID FROM CMS_LIMIT_SECURITY_MAP WHERE CMS_LSP_LMT_PROFILE_ID = ? and CHARGE_ID = ?";

	private String updateRenewInd = "UPDATE SCI_LSP_LMT_PROFILE SET CMS_BCA_RENEWAL_IND = ? WHERE CMS_LSP_LMT_PROFILE_ID = ?";

	public Long getLimitProfileIdByCMSLimitProfileID(long oldCmsLimitProfileId) {
		try {
			long cmsLimitProfileId = getJdbcTemplate().queryForLong(selectLimitProfileIdByOldCMSId,
					new Object[] { new Long(oldCmsLimitProfileId) });
			return new Long(cmsLimitProfileId);
		}
		catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}
	}

	public Long getLmtIdByLmtProfileIdAndOldLmtId(long limitProfileId, long oldLimitNo) {
		try {
			long cmsLimitId = getJdbcTemplate().queryForLong(selectLimitIdByLmtProfileIDAndOldLimitId,
					new Object[] { new Long(limitProfileId), new Long(oldLimitNo) });
			return new Long(cmsLimitId);
		}
		catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}
	}

	public Long getLmtSecMapIdByLmtProfileIdAndOldLmtSecMapId(long limitProfileId, long oldLimitSecMapId) {
		try {
			long cmsLimitSecMapId = getJdbcTemplate().queryForLong(
					selectLimitSecurityMapByLimitProfileIdAndOldLimitSecMapId,
					new Object[] { new Long(limitProfileId), new Long(oldLimitSecMapId) });
			return new Long(cmsLimitSecMapId);
		}
		catch (IncorrectResultSizeDataAccessException ex) {
			return null;
		}
	}

	public void updateRenewInd(long limitProfileId, String renewStatus) {
		getJdbcTemplate().update(updateRenewInd, new Object[] { renewStatus, new Long(limitProfileId) });
	}

	public String getLOSLimitIdByCMSLimitId(long CMSLimitId) {
		String query = "Select LOS_LMT_ID from SCI_LSP_APPR_LMTS where CMS_LSP_APPR_LMTS_ID =?";

		return (String) getJdbcTemplate().queryForObject(query, new Object[] { new Long(CMSLimitId) }, String.class);
	}

	public String getCifNumberByCmsLimitProfileId(long cmsLimitProfileId) {
		return (String) getJdbcTemplate().queryForObject(
				"SELECT llp_le_id FROM sci_lsp_lmt_profile where cms_lsp_lmt_profile_id = ? ",
				new Object[] { new Long(cmsLimitProfileId) }, String.class);
	}
	
	public String getPartyStatus(String cifId) {
		String str = null;
		String partyFlag = "";
		
			try{
			str= (String) getJdbcTemplate().queryForObject(
				"SELECT status FROM sci_le_sub_profile where lsp_le_id = ? ",
				new Object[] { new String(cifId) }, String.class);
			
			 if(str!=null && str.equalsIgnoreCase("INACTIVE")){
				partyFlag = "INACTIVE";
			}
			
			return partyFlag;
			}
			catch(Exception e){
				partyFlag = "NOTEXIST";
				return partyFlag;
			}
	}
	
	public String isPartyExist(String cifId) {
		String str = null;
			try{
			str= (String) getJdbcTemplate().queryForObject(
				"SELECT count(1) FROM sci_le_sub_profile where lsp_le_id = ? ",
				new Object[] { new String(cifId) }, String.class);
			return str;
			}
			catch(Exception e){
				return null;
			}
	}
	
	public String getCamByLlpLeId(String cifId) {
		String str = null;
			try{
			str= (String) getJdbcTemplate().queryForObject(
				"SELECT cms_lsp_lmt_profile_id FROM sci_lsp_lmt_profile where llp_le_id = ? ",
				new Object[] { new String(cifId) }, String.class);
			return str;
			}
			catch(Exception e){
				return null;
			}
	}
	public String getFacilityCodeByCMSLimitId(long CMSLimitId) {
		String query = "Select LMT_FAC_CODE from SCI_LSP_APPR_LMTS where CMS_LSP_APPR_LMTS_ID =?";
		
		return (String) getJdbcTemplate().queryForObject(query, new Object[] { new Long(CMSLimitId) }, String.class);
	}
	
	public Object getFacilityByMappingId(long mappingId){
		String query = "Select count(1) from SCI_LSP_APPR_LMTS where CMS_LSP_APPR_LMTS_ID =?";
		
		return (Object) getJdbcTemplate().queryForObject(query, new Object[] { new Long(mappingId) }, String.class);
	}

	public List getLineDetails(final String systemId,final String lineNo,final String serialNo,final String liabBranch) {
		List customerSysXRefList = null;
		String query = "SELECT FACILITY_SYSTEM,RELEASED_AMOUNT,CLOSE_FLAG,STATUS,CMS_LSP_SYS_XREF_ID FROM "
				+ " SCI_LSP_SYS_XREF WHERE FACILITY_SYSTEM_ID = '"+systemId+"' AND LINE_NO = '"+lineNo+"' AND "
						+ " SERIAL_NO = '"+serialNo+"' AND LIAB_BRANCH = '"+liabBranch+"' order by CMS_LSP_SYS_XREF_ID desc";
		try {
			 customerSysXRefList = (ArrayList) getJdbcTemplate().queryForObject(query, new Object[] {}, new RowMapper() {

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					List<ICustomerSysXRef> customerSysXRefs = new ArrayList();
					do {
					ICustomerSysXRef customerSysXRef = new OBCustomerSysXRef();
					customerSysXRef.setFacilitySystem(rs.getString("FACILITY_SYSTEM"));
					customerSysXRef.setReleasedAmount(rs.getString("RELEASED_AMOUNT"));
					customerSysXRef.setCloseFlag(rs.getString("CLOSE_FLAG"));
					customerSysXRef.setStatus(rs.getString("STATUS"));
					customerSysXRef.setXRefID(Long.parseLong(rs.getString("CMS_LSP_SYS_XREF_ID")));
					customerSysXRefs.add(customerSysXRef);
					}while(rs.next());
					return customerSysXRefs;
				}
				});
			return customerSysXRefList;
		}catch(Exception e) {
			return customerSysXRefList;
		}
	}
	
	public String updateLineDetails(String systemId, String lineNo, String serialNo, String liabBranch, String releasedAmount, String closeFlag, String xrefID) {
		String stagingQuery = "";
		String mainQuery = "";
				
		if("Y" == closeFlag || "Y".equals(closeFlag)) {
			/*mainQuery = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT = '0', UTILIZED_AMOUNT = '0', CLOSE_FLAG = '"+closeFlag+"',LIABILITY_ID = '"+systemId+"'  "
					+ "WHERE FACILITY_SYSTEM_ID = '"+systemId+"' AND LINE_NO = '"+lineNo+"' AND SERIAL_NO = '"+serialNo+"' AND LIAB_BRANCH = '"+liabBranch+"' AND CMS_LSP_SYS_XREF_ID ='"+xrefID+"'";
			*/
			mainQuery = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT = '0', UTILIZED_AMOUNT = '0', CLOSE_FLAG = '"+closeFlag+"',LIABILITY_ID = '"+systemId+"'  "
					+ "WHERE FACILITY_SYSTEM_ID = '"+systemId+"' AND LINE_NO = '"+lineNo+"' AND SERIAL_NO = '"+serialNo+"' AND LIAB_BRANCH = '"+liabBranch+"'";
			
			stagingQuery = "UPDATE CMS_STAGE_LSP_SYS_XREF SET RELEASED_AMOUNT = '0', UTILIZED_AMOUNT = '0', CLOSE_FLAG = '"+closeFlag+"',LIABILITY_ID = '"+systemId+"' " 
								+ "WHERE FACILITY_SYSTEM_ID = '"+systemId+"' AND LINE_NO = '"+lineNo+"' AND SERIAL_NO = '"+serialNo+"' AND LIAB_BRANCH = '"+liabBranch+"'";
		}else {
			/*mainQuery = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT = '"+releasedAmount+"', CLOSE_FLAG = '"+closeFlag+"',LIABILITY_ID = '"+systemId+"' "
					+ "WHERE FACILITY_SYSTEM_ID = '"+systemId+"' AND LINE_NO = '"+lineNo+"' AND SERIAL_NO = '"+serialNo+"' AND LIAB_BRANCH = '"+liabBranch+"' AND CMS_LSP_SYS_XREF_ID ='"+xrefID+"'";
			*/
			mainQuery = "UPDATE SCI_LSP_SYS_XREF SET RELEASED_AMOUNT = '"+releasedAmount+"', CLOSE_FLAG = '"+closeFlag+"',LIABILITY_ID = '"+systemId+"' "
					+ "WHERE FACILITY_SYSTEM_ID = '"+systemId+"' AND LINE_NO = '"+lineNo+"' AND SERIAL_NO = '"+serialNo+"' AND LIAB_BRANCH = '"+liabBranch+"' ";
			
			stagingQuery = "UPDATE CMS_STAGE_LSP_SYS_XREF SET RELEASED_AMOUNT = '"+releasedAmount+"', CLOSE_FLAG = '"+closeFlag+"',LIABILITY_ID = '"+systemId+"' " 
								+ "WHERE FACILITY_SYSTEM_ID = '"+systemId+"' AND LINE_NO = '"+lineNo+"' AND SERIAL_NO = '"+serialNo+"' AND LIAB_BRANCH = '"+liabBranch+"'";
		}		
		
		try {
			getJdbcTemplate().update(stagingQuery, new Object[] {});
			getJdbcTemplate().update(mainQuery, new Object[] {});
			return "SUCCESS";
		}catch(Exception e) {
			return "FAIL";
		}
	}
	
	public List getFacilityDetails(String xrefID) {
		String query ="SELECT FAC.TOTAL_RELEASED_AMOUNT,FAC.LMT_ID "
				+ "FROM SCI_LSP_APPR_LMTS FAC "
				+ "WHERE FAC.CMS_LSP_APPR_LMTS_ID = "
				+ " (SELECT MAP.CMS_LSP_APPR_LMTS_ID "
				+ "FROM SCI_LSP_LMTS_XREF_MAP MAP "
				+ "WHERE MAP.CMS_LSP_SYS_XREF_ID = '"+xrefID+"' )";
		try {
			List customerSysXRef = (ArrayList) getJdbcTemplate().queryForObject(query, new Object[] {}, new RowMapper() {

				public Object mapRow(ResultSet rs, int rowNum) throws  SQLException {
					List<String> customerSysXRef = new ArrayList<String>();
					customerSysXRef.add(rs.getString("TOTAL_RELEASED_AMOUNT"));
					customerSysXRef.add(rs.getString("LMT_ID"));
					return customerSysXRef;
				}
				});
			return customerSysXRef;
		}catch(Exception e) {
			return null;
		}
	}
	
	public void updateFacilityDetails(String lmtId, String amount) {
		String mainQuery = "update SCI_LSP_APPR_LMTS set TOTAL_RELEASED_AMOUNT = '"+amount+"' where LMT_ID ='"+lmtId+"'";
		
		String stagingQuery ="update STAGE_LIMIT set TOTAL_RELEASED_AMOUNT = '"+amount+"' where LMT_ID ='"+lmtId+"'";
		
		try {
			getJdbcTemplate().update(stagingQuery, new Object[] {});
			getJdbcTemplate().update(mainQuery, new Object[] {});
			
		}catch(Exception e) {

		}
	}

	public Map<String, String> getDetailsForECBFLimit(Map<String, Object> paramMap, String action) {
		StringBuffer sql = new StringBuffer("select fac.cms_lsp_appr_lmts_id lmt_id,party.lsp_le_id party_id, ")
				.append("nvl((select status from transaction where TRANSACTION_TYPE = 'LIMIT' AND REFERENCE_ID ")
				.append("= fac.cms_lsp_appr_lmts_id), CMS_LIMIT_STATUS) lmt_status from SCI_LSP_APPR_LMTS FAC");
		
		if("A".equals(action)) {
			sql.append(" inner join CMS_FACILITY_NEW_MASTER facmst on (FACMST.NEW_FACILITY_NAME = FAC.FACILITY_NAME and FACMST.NEW_FACILITY_CODE = FAC.LMT_FAC_CODE)");
		}
		
		sql.append(" inner join SCI_LSP_LMT_PROFILE CAM ON CAM.CMS_LSP_LMT_PROFILE_ID = FAC.CMS_LIMIT_PROFILE_ID")
			.append(" inner join SCI_LE_SUB_PROFILE PARTY ON PARTY.CMS_LE_SUB_PROFILE_ID = CAM.CMS_CUSTOMER_ID");
		
		if("U".equals(action)) {
			sql.append(" inner join SCI_LSP_LMTS_XREF_MAP linkage on linkage.cms_lsp_appr_lmts_id = fac.CMS_LSP_APPR_LMTS_ID")
				.append(" inner join SCI_LSP_SYS_XREF line on line.CMS_LSP_SYS_XREF_ID = linkage.CMS_LSP_SYS_XREF_ID");
		}
		
		sql.append(" where CMS_LIMIT_STATUS = 'ACTIVE' and ");
		
		List<Object> values = new ArrayList<Object>();
		int i = 1;
		for(String key : paramMap.keySet()) {
			sql.append(" " + key + " = ?");
			values.add(paramMap.get(key));
			if(i < paramMap.size())
				sql.append(" and ");
			i++;
		}
		
		return (Map<String, String>) getJdbcTemplate().query(sql.toString(), values.toArray(), new ResultSetExtractor() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException {
				Map<String, String> resultMap = new HashMap<String, String>();
				if(rs.next()) {
					resultMap.put("LMT_ID", String.valueOf(rs.getLong("LMT_ID")));
					resultMap.put("PARTY_ID", rs.getString("PARTY_ID"));
					resultMap.put("LMT_STATUS", rs.getString("LMT_STATUS"));
				}
				return resultMap;
			}
			
		});
		
	}
	
	public ICommonCodeEntry getEntryByCodeAndCategory(String entryCode, String categoryCode){
		StringBuffer sql = new StringBuffer("SELECT entry_id id,entry_code code, entry_name code_value FROM COMMON_CODE_CATEGORY_ENTRY")
							.append(" WHERE entry_code = ? and category_code = ? and active_status = '1'");
		ICommonCodeEntry value = null;
		try {
			value = (ICommonCodeEntry) getJdbcTemplate().queryForObject(sql.toString(), new Object[] {entryCode, categoryCode}, new RowMapper() {
				@Override
				public ICommonCodeEntry mapRow(ResultSet rs, int idx) throws SQLException {
					ICommonCodeEntry value = new OBCommonCodeEntry();
					value.setEntryId(rs.getLong("id"));
					value.setEntryCode(rs.getString("code"));
					value.setEntryName(rs.getString("code_value"));
					return value;
				}
			});
		}catch(Exception ex) {
			ex.printStackTrace();
			DefaultLogger.error(this, "Exception caught while fetching data from COMMON_CODE_CATEGORY_ENTRY for entryCode " 
			+ entryCode + " categoryCode " + categoryCode, ex);
		}
		
		return value;
	}
	
	public String findSegment1ByPartyId(String partyId) {
		StringBuffer sql =  new StringBuffer(" select segment.entry_code from sci_le_sub_profile cust ") 
				.append(" inner join common_code_category_entry hdfcseg on hdfcseg.entry_code = ")
				.append(" cust.lsp_sgmnt_code_value and hdfcseg.category_code = '"+ CategoryCodeConstant.HDFC_SEGMENT+ "'") 
				.append(" inner join common_code_category_entry segment on segment.entry_name = ")
				.append(" upper(hdfcseg.entry_name) and segment.category_code = '" + com.integrosys.cms.ui.limit.CategoryCodeConstant.SEGMENT_1 + "'")
				.append(" where cust.lsp_le_id = ?");
		
		String value = null;
		
		try {
			List<String> result =  getJdbcTemplate().queryForList(sql.toString(), new Object[] {partyId}, String.class);
			if(result.size() > 0) {
				value = result.get(0);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			DefaultLogger.error(this, "Exception caught while fetching data from COMMON_CODE_CATEGORY_ENTRY for segment1 for " 
			+ " partyId " + partyId, ex);
		
		}
		
		return value;
	}
	
	public boolean isSystemExistsForParty(String partyId, String systemId) {
		StringBuffer sql = new StringBuffer("SELECT count(*) FROM SCI_LE_OTHER_SYSTEM")
								.append(" where CMS_LE_MAIN_PROFILE_ID = (select CMS_LE_MAIN_PROFILE_ID from sci_le_main_profile where lmp_le_id= ?)")
								.append(" and cms_le_other_sys_cust_id = ?");
		try {
			int count = getJdbcTemplate().queryForInt(sql.toString(), new Object[] { partyId, systemId });
			return count > 0;
		}catch(Exception ex) {
			ex.printStackTrace();
			DefaultLogger.error(this, "Exception caught while search system id " + systemId + " for partyId " + partyId, ex);
			return false;
		}
	}


	@Override
	public String getDefaultSegment1CodeValue(String defaultSegmentName) {
		String defaultSegment1 = null;
		
		String sql = "select entry_code from common_code_category_entry where category_code = ? and entry_name=UPPER(?)";
		
		try {
			defaultSegment1 = (String) getJdbcTemplate().queryForObject(sql, new Object[] { com.integrosys.cms.ui.limit.CategoryCodeConstant.SEGMENT_1,defaultSegmentName}, String.class);
		}catch(Exception ex) {
			ex.printStackTrace();
			DefaultLogger.error(this, "Exception caught in getDefaultSegment1CodeValue" , ex);
		
		}
		
		return defaultSegment1;
	}
	
	

}
