package com.integrosys.cms.app.lad.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class GeneratePartyLADDaoImpl extends JdbcDaoSupport implements
		GeneratePartyLADDao {
	@Override
	public List getData(String rm_id, String party_id, String due_month,
			String due_year, String segment_id) {
		List result = null;

		String query = "SELECT D.Limit_Id, d.customer_id,a.RM_NAME,a.PARTY_NAME,a.SEGMENT,a.STATUS,a.TOTAL_RECORDS FROM CMS_LAD_FILTER a LEFT JOIN CMS_LAD_DETAIL d ON a.id=d.filter_ID WHERE d.status  ='PENDING' AND a.rm_id     ='"
				+ rm_id
				+ "' AND a.party_id  ='"
				+ party_id
				+ "' AND A.Due_Month ='"
				+ due_month
				+ "' AND A.Due_Year  ='"
				+ due_year + "' AND a.segment_id='" + segment_id + "'";

		DefaultLogger.debug(this, "getData---------------" + query);

		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.setFetchSize(100);
		result = jdbcTemplate.queryForList(query);

		return result;

	}	

	@Override
	public List<String[]> getLimitsForLadReport(long profileId) {

	/*	String sql = "Select (CASE  WHEN Sublimit='No' THEN Facilitycategory"
				+ "  Else Concat(Facilitycategory,'*sublimit') END)Facilitycategory,"
				+ "Sum(Totalreleasedamount) As Totalreleasedamount,"
				+ "Sum(Totalreleasableamount) As Totalreleasableamount ,"
				+ "Sublimit,"
				+ "Sum(Utilizedamount) As Utilizedamount "
				+ "From(Select C.Entry_Name Facilitycategory,"
				+ " A.Total_Released_Amount Totalreleasedamount,"
				+ " A.Releasable_Amount Totalreleasableamount,"
				+ "A.Lmt_Type_Value Sublimit,"
				+ "sum(Xref.Utilized_Amount) Utilizedamount "
				+ "FROM Sci_Lsp_Appr_Lmts A,"
				+ "Sci_Lsp_Sys_Xref Xref,"
				+ "Sci_Lsp_Lmts_Xref_Map Map,"
				+ "Common_Code_Category_Entry C "
				+ "Where A.Cms_Limit_Profile_Id ="
				+ profileId
				+ "And A.Cms_Lsp_Appr_Lmts_Id   = Map.Cms_Lsp_Appr_Lmts_Id "
				+ "And Map.Cms_Lsp_Sys_Xref_Id  = Xref.Cms_Lsp_Sys_Xref_Id "
//				+ "and Xref.Cms_Lsp_Sys_Xref_Id = Map.cms_lsp_sys_xref_id  "
				+ "And A.Cms_Limit_Status='ACTIVE'"
				+ " And C.Category_Code ='FACILITY_CATEGORY' "
				+ "And C.Entry_Code =A.Facility_Cat  "
				+" And A.Facility_Cat not in ( select excluded_facility_category from cms_excluded_facility where status='ACTIVE' and deprecated='N') "
				+ " Group By Entry_Name,Total_Released_Amount,Releasable_Amount,Lmt_Type_Value) Group By Facilitycategory,Sublimit  order by Facilitycategory asc";
*/
		
		/*String sql="WITH FAC AS(SELECT CMS_LSP_APPR_LMTS_ID,LMT_ID,CMS_LIMIT_PROFILE_ID, MAIN_FACILITY_ID, "+
				   " (SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE ='FACILITY_CATEGORY' AND ACTIVE_STATUS='1' AND  ENTRY_CODE=FACILITY_CAT) AS FACILITYCATEGORY, "+
				   " RELEASABLE_AMOUNT AS ,TRIM(LMT_CRRNCY_ISO_CODE) AS LMT_CRRNCY_ISO_CODE,LTRIM(SYS_CONNECT_BY_PATH(FACILITY_CAT, '\\'), '\\') AS FACILITY , "+
				   " LEVEL AS LEVELOF ,ROWNUM AS ROWNUMBER "+
				   " FROM   SCI_LSP_APPR_LMTS "+
				   " WHERE CMS_LIMIT_PROFILE_ID='"+profileId+"' AND CMS_LIMIT_STATUS='ACTIVE' "+
				   " AND FACILITY_CAT NOT IN ( SELECT EXCLUDED_FACILITY_CATEGORY FROM CMS_EXCLUDED_FACILITY WHERE STATUS='ACTIVE' AND DEPRECATED='N') "+ 
				   " START WITH MAIN_FACILITY_ID IS NULL CONNECT BY MAIN_FACILITY_ID =PRIOR  LMT_ID "+
				   " ORDER SIBLINGS BY LMT_ID), FACJOIN AS ( "+
				   " SELECT FACILITY,SUM( TO_NUMBER(FAC.RELEASABLE_AMOUNT)*(SELECT TO_NUMBER(BUY_RATE) FROM CMS_FOREX WHERE BUY_CURRENCY = FAC.LMT_CRRNCY_ISO_CODE) ) AS TOTALRELEASABLEAMOUNT, "+
				   " SUM(TO_NUMBER(RELEASED_AMOUNT) *(SELECT TO_NUMBER(BUY_RATE) FROM CMS_FOREX WHERE BUY_CURRENCY = FAC.LMT_CRRNCY_ISO_CODE) ) AS  TOTALRELEASEDAMOUNT , "+
				   " SUM(TO_NUMBER(UTILIZED_AMOUNT) * (SELECT TO_NUMBER(BUY_RATE) FROM CMS_FOREX WHERE BUY_CURRENCY = FAC.LMT_CRRNCY_ISO_CODE)) AS UTILIZEDAMOUNT "+
				   " FROM FAC,SCI_LSP_LMTS_XREF_MAP MAPP,SCI_LSP_SYS_XREF AMT "+
				   " WHERE MAPP.CMS_LSP_APPR_LMTS_ID=FAC.CMS_LSP_APPR_LMTS_ID "+
				   " AND AMT.CMS_LSP_SYS_XREF_ID=MAPP.CMS_LSP_SYS_XREF_ID GROUP BY FACILITY) "+
				   " SELECT ROWNUMBER,FACJOIN.FACILITY, FACILITYCATEGORY, TOTALRELEASABLEAMOUNT ,TOTALRELEASEDAMOUNT,UTILIZEDAMOUNT FROM (SELECT FACILITY, CASE WHEN LEVELOF>1 THEN FACILITYCATEGORY||'*' ELSE FACILITYCATEGORY END FACILITYCATEGORY, MIN(ROWNUMBER) ROWNUMBER FROM FAC GROUP BY FACILITY, CASE WHEN LEVELOF>1 THEN FACILITYCATEGORY||'*' ELSE  FACILITYCATEGORY END ) FINALFAC,FACJOIN WHERE FACJOIN.FACILITY=FINALFAC.FACILITY "+
				   " ORDER BY ROWNUMBER ";*/
		
		String sql=" WITH FAC AS(SELECT CMS_LSP_APPR_LMTS_ID,LMT_ID,CMS_LIMIT_PROFILE_ID,MAIN_FACILITY_ID, " +
		" (SELECT ENTRY_NAME FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE ='FACILITY_CATEGORY' AND ACTIVE_STATUS='1' AND  ENTRY_CODE=FACILITY_CAT) AS FACILITYCATEGORY, " +
		" RELEASABLE_AMOUNT  ,TRIM(LMT_CRRNCY_ISO_CODE) AS LMT_CRRNCY_ISO_CODE,LTRIM(SYS_CONNECT_BY_PATH(FACILITY_CAT, '\\'), '\\') AS FACILITY ," +
		" LEVEL AS LEVELOF ,ROWNUM AS ROWNUMBER FROM   SCI_LSP_APPR_LMTS " +
		" WHERE CMS_LIMIT_PROFILE_ID='"+profileId+"' AND CMS_LIMIT_STATUS='ACTIVE' AND CMS_REQ_SEC_COVERAGE <> 0" +
		" AND FACILITY_CAT NOT IN ( SELECT EXCLUDED_FACILITY_CATEGORY FROM CMS_EXCLUDED_FACILITY WHERE STATUS='ACTIVE' AND DEPRECATED='N')" +
		" START WITH MAIN_FACILITY_ID IS NULL CONNECT BY MAIN_FACILITY_ID =PRIOR  LMT_ID" +
		" ORDER SIBLINGS BY LMT_ID)," +
		" facrelease AS (SELECT SUM(RELEASABLE_AMOUNT *(SELECT TO_NUMBER(BUY_RATE) FROM CMS_FOREX WHERE BUY_CURRENCY = FAC.LMT_CRRNCY_ISO_CODE)) TOTALRELEASABLEAMOUNT,FACILITY  FROM FAC" +
		" GROUP BY FACILITY),FACJOIN AS (" +
		" SELECT FACILITY,SUM(TO_NUMBER(RELEASED_AMOUNT) *(SELECT TO_NUMBER(BUY_RATE) FROM CMS_FOREX WHERE BUY_CURRENCY = FAC.LMT_CRRNCY_ISO_CODE) ) AS  TOTALRELEASEDAMOUNT ," +
		" SUM(TO_NUMBER(UTILIZED_AMOUNT) * (SELECT TO_NUMBER(BUY_RATE) FROM CMS_FOREX WHERE BUY_CURRENCY = FAC.LMT_CRRNCY_ISO_CODE)) AS UTILIZEDAMOUNT" +
		" FROM FAC,SCI_LSP_LMTS_XREF_MAP MAPP,SCI_LSP_SYS_XREF AMT " +
		" WHERE MAPP.CMS_LSP_APPR_LMTS_ID=FAC.CMS_LSP_APPR_LMTS_ID AND AMT.CMS_LSP_SYS_XREF_ID=MAPP.CMS_LSP_SYS_XREF_ID" +
		" GROUP BY FACILITY) " +
		" SELECT ROWNUMBER,FACJOIN.FACILITY, FACILITYCATEGORY, TOTALRELEASABLEAMOUNT ,TOTALRELEASEDAMOUNT,UTILIZEDAMOUNT" +
		" FROM (SELECT FACILITY, CASE WHEN LEVELOF>1 THEN FACILITYCATEGORY||'*' ELSE FACILITYCATEGORY END FACILITYCATEGORY, MIN(ROWNUMBER) ROWNUMBER" +
		" FROM FAC GROUP BY FACILITY, CASE WHEN LEVELOF>1 THEN FACILITYCATEGORY||'*' ELSE FACILITYCATEGORY END ) FINALFAC,FACJOIN,facrelease WHERE FACJOIN.FACILITY=FINALFAC.FACILITY AND facrelease.FACILITY=FINALFAC.FACILITY" +
		" ORDER BY ROWNUMBER ";
		
		DefaultLogger.debug(this, "getLimitsForLadReport---------------" + sql);

		List<String[]> arrayList = null;

		try {
			JdbcTemplate jdbcTemplate = getJdbcTemplate();
			List<String[]> result = new ArrayList<String[]>();
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

			for (Map<String, Object> rs : list) {
				String[] arr = null;
				arr = new String[4];
				arr[0] = (null != rs.get("FACILITYCATEGORY")) ? (String) rs
						.get("FACILITYCATEGORY") : "-";
				arr[1] = (null != rs.get("TOTALRELEASEDAMOUNT")) ? ((BigDecimal) rs
						.get("TOTALRELEASEDAMOUNT")).toPlainString() : "0";
				arr[2] = (null != rs.get("TOTALRELEASABLEAMOUNT")) ? ((BigDecimal) rs
						.get("TOTALRELEASABLEAMOUNT")).toPlainString() : "0";
				arr[3] = (null != rs.get("UTILIZEDAMOUNT")) ? ((BigDecimal) rs
						.get("UTILIZEDAMOUNT")).toPlainString() : "0";
				result.add(arr);
			}

			return result;
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		}

	}

//	@Override
//	public List getAllData() {
//
//		String query = "SELECT * from CMS_LAD_FILTER order by 1 desc";
//		DefaultLogger.debug(this, "CMS LAD FILTER---------------" + query);
//		JdbcTemplate jdbcTemplate = getJdbcTemplate();
//		jdbcTemplate.setFetchSize(100);
//
//		return jdbcTemplate.queryForList(query);
//
//	}
	
	@Override
	public List getAllData(Boolean isRMUser,String isRMValue) {

		String query = "SELECT * from CMS_LAD_FILTER order by 1 desc";
		
		if(isRMUser){
			query = "SELECT * from CMS_LAD_FILTER where rm_id='"+isRMValue+"' order by 1 desc";
		}
		DefaultLogger.debug(this, "CMS LAD FILTER---------------" + query);
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.setFetchSize(100);

		return jdbcTemplate.queryForList(query);

	}

	@Override
	public void runProcedure(String rm_Name, String rmId, String party_name,
			String partyId, String dueMonth, String dueYear, String segment) {
		// TODO Auto-generated method stub

		DefaultLogger.debug(this,
				"call report_gen (?,?,?,?,?,?,?) begin ---------------");

		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update("call report_gen (?,?,?,?,?,?,?)",
				new Object[] { rm_Name, rmId, party_name, partyId, dueMonth,
						dueYear, segment });

		DefaultLogger.debug(this,
				"call report_gen (?,?,?,?,?,?,?) completed ---------------");
		/*
		 * jdbcTemplate.update("call report_gen (?, ?)",rm_Name,rmId,
		 * party_name, partyId,dueMonth,dueYear,segment);
		 */

	}

	@Override
	public void updateToCompletedAndFileName(String rmId, String partyId,
			String dueMonth, String dueYear, String segment, String dirName,
			String reportGenerationDate) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		String sql = "UPDATE CMS_LAD_FILTER set status='"
				+ ICMSConstant.LAD_SUCCESS + "',due_month='" + dueMonth
				+ "', report_name='" + dirName + "',report_generate_date='"
				+ reportGenerationDate + "'  where rm_Id='" + rmId
				+ "' AND party_Id='" + partyId + "' AND due_Month='" + dueMonth
				+ "' AND due_Year='" + dueYear + "' AND segment='" + segment
				+ "'";

//		DefaultLogger.debug(this,
//				"query for updateToCompletedAndFileName is present ---------------"
//						+ sql);

//		DefaultLogger.debug(this, sql);
		jdbcTemplate.update(sql);

	}

	/*
	 * private String getMonth(int dueMonth) { String monthString=null; switch
	 * (dueMonth) { case 1: monthString = "January"; break; case 2: monthString
	 * = "February"; break; case 3: monthString = "March"; break; case 4:
	 * monthString = "April"; break; case 5: monthString = "May"; break; case 6:
	 * monthString = "June"; break; case 7: monthString = "July"; break; case 8:
	 * monthString = "August"; break; case 9: monthString = "September"; break;
	 * case 10: monthString = "October"; break; case 11: monthString =
	 * "November"; break; case 12: monthString = "December"; break; default:
	 * monthString = "Invalid month"; break; } return monthString; }
	 */

	@Override
	public void updateToFail(String rmId, String partyId, String dueMonth,
			String dueYear, String segment, String reportGenerationDate) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update("UPDATE CMS_LAD_FILTER set status='"
				+ ICMSConstant.LAD_FAIL + "' where rm_Id='" + rmId
				+ "' AND party_Id='" + partyId + "' AND due_Month='" + dueMonth
				+ "' AND due_Year='" + dueYear + "' AND segment='" + segment
				+ "'");

	}

	@Override
	public void updateToPartialSuccess(String rmId, String partyId,
			String dueMonth, String dueYear, String segment,
			String reportGenerationDate) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update("UPDATE CMS_LAD_FILTER set status='"
				+ ICMSConstant.LAD_PARTIAL_SUCCESS + "' where rm_Id='" + rmId
				+ "' AND party_Id='" + partyId + "' AND due_Month='" + dueMonth
				+ "' AND due_Year='" + dueYear + "' AND segment='" + segment
				+ "'");

	}
	
	@Override
	public void updateToPartialSuccess(String rmId, String partyId,
			String dueMonth, String dueYear, String segment,String reportName,
			String reportGenerationDate) {
		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.update("UPDATE CMS_LAD_FILTER set status='"
				+ ICMSConstant.LAD_PARTIAL_SUCCESS + "' where rm_Id='" + rmId
				+ "' AND party_Id='" + partyId + "' AND due_Month='" + dueMonth
				+ "' AND due_Year='" + dueYear + "' AND report_name='" + reportName + "' AND segment='" + segment 
				+ "'");

	}

	@Override
	public List getLimits(Long limitProfileId) {

		String sql = "Select id,lad_id,lad_name,lad_due_date ,generation_date,IS_OPERATION_ALLOWED,receive_date,document_date,expiry_date,lad_counter,status,version_time,limit_profile_id from cms_lad where  limit_profile_id='"
				+ limitProfileId + "' order by generation_date desc";

		DefaultLogger.debug(this,
				"query for getLimits is present ---------------" + sql);

		try {

			JdbcTemplate jdbcTemplate = getJdbcTemplate();

			// DefaultLogger.debug(this, sql);
			ArrayList resultList = new ArrayList();
			List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql);
			DefaultLogger.debug(this, "document_date" + rs);
			ILAD ilad = null;
			for (Map<String, Object> row : rs) {

				ilad = new OBLAD();

				ilad.setDocument_date(row.get("document_date") != null ? (Date) row
						.get("document_date") : null);

				ilad.setExpiry_date(row.get("expiry_date") != null ? (Date) row
						.get("expiry_date") : null);

				ilad.setGeneration_date(row.get("generation_date") != null ? (Date) row
						.get("generation_date") : null);

				ilad.setId(row.get("id") != null ? ((BigDecimal) row.get("id"))
						.longValue() : 0l);

				ilad.setLad_counter(row.get("lad_counter") != null ? ((BigDecimal) row
						.get("lad_counter")).longValue() : 0l);

				ilad.setLad_due_date(row.get("lad_due_date") != null ? (Date) row
						.get("lad_due_date") : null);

				ilad.setLad_id(row.get("lad_id") != null ? ((BigDecimal) row
						.get("lad_id")).longValue() : 0l);

				ilad.setLad_name(row.get("lad_name") != null ? (String) row
						.get("lad_name") : null);

				ilad.setReceive_date(row.get("receive_date") != null ? (Date) row
						.get("receive_date") : null);

				ilad.setStatus(row.get("status") != null ? (String) row
						.get("status") : null);

				ilad.setIsOperationAllowed(row.get("IS_OPERATION_ALLOWED") != null ? (String) row
						.get("IS_OPERATION_ALLOWED") : null);

				ilad.setVersionTime(row.get("version_time") != null ? ((BigDecimal) row
						.get("version_time")).longValue() : 0l);

				ilad.setLimit_profile_id(row.get("limit_profile_id") != null ? ((BigDecimal) row
						.get("limit_profile_id")).longValue() : 0l);

				resultList.add(ilad);
			}
			return resultList;
		} catch (Exception ex) {
			throw new SearchDAOException(
					"Exception in getCheckListItemListForAudit", ex);
		}

	}

	@Override
	public Integer isFilterAlreadyPresent(String rm_id, String party_id,
			String due_month, String due_year, String segment_id) {
		String query1 = "Select Count(*)  FROM CMS_LAD_FILTER c  WHERE c.Rm_Id  ='"
				+ rm_id
				+ "'  AND C.Party_Id ='"
				+ party_id
				+ "'  And C.Due_Month='"
				+ due_month
				+ "'  And C.Due_Year ='"
				+ due_year + "'  AND C.Segment_Id ='" + segment_id + "' ";

		DefaultLogger.debug(this,
				"query for iffilter is present ---------------" + query1);

		JdbcTemplate jdbcTemplate = getJdbcTemplate();
		jdbcTemplate.setFetchSize(100);
		int count = jdbcTemplate.queryForInt(query1);
		return count;
	}
}
