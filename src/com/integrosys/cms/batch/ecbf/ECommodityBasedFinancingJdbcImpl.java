package com.integrosys.cms.batch.ecbf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class ECommodityBasedFinancingJdbcImpl extends JdbcDaoSupport implements IECommodityBasedFinancingJdbc {

	public final static String ECBF_DEFERRAL_RECURRENT_REPORT = "SELECT * FROM (SELECT DISTINCT  " + 
			"  'STATEMENTTYPE' category,   " + 
			"  main_profile.lsp_le_id  party_id, " + 
			"  main_profile.lsp_short_name party_name, " + 
			"  rm.rm_mgr_name rm_name, " + 
			"  CASE " + 
			"    WHEN checListItem.STATUS = 'AWAITING' " + 
			"    THEN TO_CHAR(checListItem.expiry_date,'DD/Mon/YYYY') " + 
			"    WHEN checListItem.status = 'DEFERRED' " + 
			"    THEN TO_CHAR(checListItem.expected_return_date,'DD/Mon/YYYY') " + 
			"  END due_date, " + 
			"  checListItem.DOC_DESCRIPTION remarks, " + 
			"  main_profile.pan " + 
			"FROM SCI_LE_SUB_PROFILE main_profile, " + 
			"  common_code_category_entry cc_segment, " + 
			"  cms_relationship_mgr rm, " + 
			"  cms_checklist checList, " + 
			"  cms_checklist_item checListItem, " + 
			"  SCI_LSP_LMT_PROFILE limit_profile,  " + 
			"  SCI_LSP_APPR_LMTS lmt "+
			"WHERE main_profile.lsp_sgmnt_code_value  =cc_segment.entry_code(+) " + 
			"AND rm.id(+)                             = main_profile.relation_mgr " + 
			"AND LIMIT_PROFILE.CMS_CUSTOMER_ID        = main_profile.cms_le_sub_profile_id " + 
			"AND limit_profile.CMS_LSP_LMT_PROFILE_ID = checList.cms_lsp_lmt_profile_id(+) " + 
			"AND limit_profile.CMS_LSP_LMT_PROFILE_ID = lmt.CMS_LIMIT_PROFILE_ID "+
			"AND checListItem.checklist_id(+)         = checList.checklist_id " + 
			"AND Upper(checListItem.status)          IN ('DEFERRED','AWAITING') " + 
			"AND CHECLIST.CATEGORY                    = 'REC' " + 
			"AND main_profile.status                  = 'ACTIVE' " + 
			"AND cc_segment.category_code             = 'HDFC_SEGMENT' " + 
			"AND checlistitem.is_deleted             != 'Y' " +
			"AND lmt.line_no in (select ent.ENTRY_NAME from common_code_category_entry ent " + 
			"    inner join common_code_category cat on cat.CATEGORY_ID = ent.CATEGORY_CODE_ID " + 
			"    where cat.CATEGORY_CODE = 'INSTA_PLEDGE_LOAN_LINES' AND ent.active_status='1')" + 
			") where due_date <= SYSDATE ";
	
	public final static String ECBF_DEFERRAL_DISCREPANCY_REPORT = "SELECT * FROM (SELECT  " + 
			" DISTINCT 'CREATIONREMARK' category,   " + 
			"party.lsp_le_id  party_id, " + 
			"party.lsp_short_name party_name, " + 
			"CASE " + 
			"    WHEN UPPER(descp.STATUS) = 'ACTIVE' " + 
			"    THEN TO_CHAR(descp.ORIGINAL_TARGET_DATE,'DD/Mon/YYYY') " + 
			"    WHEN UPPER(descp.STATUS) = 'DEFERED' " + 
			"    THEN " + 
			"    CASE " + 
			"        WHEN descp.NEXT_DUE_DATE IS NULL " + 
			"        THEN TO_CHAR(descp.DEFER_DATE,'DD/Mon/YYYY') " + 
			"        WHEN descp.NEXT_DUE_DATE IS NOT NULL " + 
			"        THEN TO_CHAR(descp.NEXT_DUE_DATE,'DD/Mon/YYYY') " + 
			"  END " + 
			"END due_date, " + 
			"rm.rm_mgr_name rm_name, " + 
			"get_common_code_desc(descp.discrepency,'DISCREPENCY') remarks, " + 
			"party.pan " + 
			"FROM SCI_LE_SUB_PROFILE party, " + 
			"  CMS_DISCREPENCY descp, " + 
			"  common_code_category_entry cc_segment, " + 
			"  cms_relationship_mgr rm, " + 
			"  common_code_category_entry cc_descp, " + 
			"  transaction trx, " + 
			"  SCI_LSP_LMT_PROFILE limit_profile,  " + 
			"  SCI_LSP_APPR_LMTS lmt "+
			"WHERE party.lsp_sgmnt_code_value=cc_segment.entry_code(+) " + 
			"AND LIMIT_PROFILE.CMS_CUSTOMER_ID = party.cms_le_sub_profile_id " + 
			"AND limit_profile.CMS_LSP_LMT_PROFILE_ID = lmt.CMS_LIMIT_PROFILE_ID "+
			"AND rm.id(+)                    = party.relation_mgr " + 
			"AND party.cms_le_sub_profile_id = descp.customer_id(+) " + 
			"AND cc_descp.category_code      ='DISCREPENCY' " + 
			"AND descp.discrepency           = cc_descp.entry_code(+) " + 
			"AND descp.status in ('DEFERED', 'ACTIVE' )" + 
			"AND party.status                = 'ACTIVE' " + 
			"AND trx.transaction_type        ='DISCREPENCY' " + 
			"AND trx.reference_id            =descp.id " + 
			"AND trx.status = 'ACTIVE' "+
			"AND lmt.line_no in (select ent.ENTRY_NAME from common_code_category_entry ent " + 
			"        inner join common_code_category cat on cat.CATEGORY_ID = ent.CATEGORY_CODE_ID " + 
			"    where cat.CATEGORY_CODE = 'INSTA_PLEDGE_LOAN_LINES' AND ent.active_status='1') " + 
			") WHERE due_date <= SYSDATE ";
	
	public final static String ECBF_DEFERRAL_LAD_REPORT = "SELECT * FROM (SELECT DISTINCT  " + 
			"  'DOC_NAME' category, " + 
			"  rm.rm_mgr_name rm_name, " + 
			"  sub_profile.LSP_LE_ID as PARTY_ID, " + 
			"  sub_profile.lsp_short_name AS party_name, " + 
			"  CASE " + 
			"        WHEN checklist_item.STATUS = 'AWAITING' " + 
			"        THEN TO_CHAR(checklist_item.completed_date,'DD/Mon/YYYY') " + 
			"        WHEN checklist_item.status = 'DEFERRED' " + 
			"        THEN TO_CHAR(checklist_item.expected_return_date,'DD/Mon/YYYY') " + 
			"    END  AS due_Date, " + 
			"    checklist_item.DOC_DESCRIPTION remarks, " + 
			"    sub_profile.pan " + 
			"FROM SCI_LE_SUB_PROFILE sub_profile , " + 
			"  SCI_LE_MAIN_PROFILE main_profile , " + 
			" " + 
			"  (SELECT entry_name, " + 
			"    entry_code " + 
			"  FROM common_code_category_entry " + 
			"  WHERE category_code = 'HDFC_SEGMENT' " + 
			"  ) cc_segment , " + 
			"  cms_relationship_mgr rm , " + 
			"  cms_party_group partygr , " + 
			"  sci_lsp_lmt_profile lmt_profile, " + 
			"  cms_checklist checklist, " + 
			"  cms_checklist_item checklist_item, " +
			"  SCI_LSP_APPR_LMTS lmt "+
			"where " + 
			"        sub_profile.cms_le_main_profile_id   = main_profile.cms_le_main_profile_id " + 
			"        AND sub_profile.lsp_le_id                =lmt_profile.llp_le_id " + 
			"        AND cc_segment.entry_code                =main_profile.lmp_sgmnt_code_value " + 
			"        AND rm.id                                = sub_profile.relation_mgr " + 
			"        AND sub_profile.party_grp_nm             = partygr.id(+) " + 
			"        AND sub_profile.status              != 'INACTIVE' " + 
			"        AND CHECKLIST.CATEGORY               = 'LAD' " + 
			"        AND sub_profile.cycle_nm             = 'LAD' " + 
			"        AND checklist.cms_lsp_lmt_profile_id = lmt_profile.CMS_LSP_LMT_PROFILE_ID " + 
			"        AND lmt_profile.CMS_LSP_LMT_PROFILE_ID = lmt.CMS_LIMIT_PROFILE_ID "+
			"        AND checklist.checklist_id           = checklist_item.checklist_id " + 
			"        AND checklist_item.status            in ('AWAITING','DEFERRED') " + 
			"        AND sub_profile.status              != 'INACTIVE' "+
			"        AND lmt.line_no in (select ent.ENTRY_NAME from common_code_category_entry ent " + 
			"        inner join common_code_category cat on cat.CATEGORY_ID = ent.CATEGORY_CODE_ID " + 
			"    where cat.CATEGORY_CODE = 'INSTA_PLEDGE_LOAN_LINES' AND ent.active_status='1') " + 
			") WHERE due_date <= SYSDATE ";

	
	
	@Override
	public List<OBEcbfDeferralReport> ecbfDeferralReportDataMapper(String sql) {
		System.out.println("ecbfDeferralReportDataMapper => SQL =>"+sql);
		return getJdbcTemplate().query(sql, new RowMapper<OBEcbfDeferralReport>() {

			public OBEcbfDeferralReport mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBEcbfDeferralReport report = new OBEcbfDeferralReport();
				report.setCategory(rs.getString("category"));
				report.setPartyId(rs.getString("party_id"));
				report.setPartyName(rs.getString("party_name"));				
				report.setDueDate(rs.getString("due_date"));
				report.setRmName(rs.getString("rm_name"));
				report.setRemarks(rs.getString("remarks"));
				report.setPan(rs.getString("pan"));

				return report;
			}
		});
	}


	@Override
	public List<OBEcbfDeferralReport> getEcbfDeferralReportData() {
		
		List<OBEcbfDeferralReport> result = ecbfDeferralReportDataMapper(ECBF_DEFERRAL_RECURRENT_REPORT);
		result.addAll(ecbfDeferralReportDataMapper(ECBF_DEFERRAL_DISCREPANCY_REPORT));
		result.addAll(ecbfDeferralReportDataMapper(ECBF_DEFERRAL_LAD_REPORT));
		
		return result;
	}

}
