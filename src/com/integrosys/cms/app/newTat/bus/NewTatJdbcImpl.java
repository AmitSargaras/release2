package com.integrosys.cms.app.newTat.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.common.bus.PaginationUtil;

/**
 * 
 * @author $Author: Abhijit R. $
 */

public class NewTatJdbcImpl extends JdbcDaoSupport implements INewTatJdbc {
	private DBUtil dbUtil = null;

	private int totalPageForPagination;

	private int recordsPerPageForPagination;

	private PaginationUtil paginationUtil;

	public void setTotalPageForPagination(int totalPageForPagination) {
		this.totalPageForPagination = totalPageForPagination;
	}

	public void setRecordsPerPageForPagination(int recordsPerPageForPagination) {
		this.recordsPerPageForPagination = recordsPerPageForPagination;
	}

	public void setPaginationUtil(PaginationUtil paginationUtil) {
		this.paginationUtil = paginationUtil;
	}

	public ArrayList getFilteredActualTat
	(String cusName,
			String cusId,
			String caseIni,
			String lastUpdate,
			String rgn,
			String sts,
			String sgmnt,
			String mdl){


		ArrayList resultList = null;
		try {
			if(null==cusName)
				cusName="";
			if(null==cusId)
				cusId="";
			if(null==caseIni)
				caseIni="";
			if(null==lastUpdate)
				lastUpdate="";
			if(null==rgn)
				rgn="";
			if(null==sts)
				sts="";
			if(null==sgmnt)
				sgmnt="";
			if(null==mdl)
				mdl="";


			if(cusName.equalsIgnoreCase("null"))
				cusName="";
			if(cusId.equalsIgnoreCase("null"))
				cusId="";
			if(caseIni.equalsIgnoreCase("null"))
				caseIni="";
			if(lastUpdate.equalsIgnoreCase("null"))
				lastUpdate="";
			if(rgn.equalsIgnoreCase("null"))
				rgn="";
			if(sts.equalsIgnoreCase("null"))
				sts="";
			if(sgmnt.equalsIgnoreCase("null"))
				sgmnt="";
			if(mdl.equalsIgnoreCase("null"))
				mdl="";

			String sql="SELECT ID, VERSION_TIME, CREATE_BY, CREATION_DATE, LAST_UPDATE_BY ,LAST_UPDATE_DATE,DEPRECATED,STATUS,CMS_LE_MAIN_PROFILE_ID,LSP_LE_ID,LSP_SHORT_NAME,CASE_ID,MODULE,"
				+"CASE_INITIATOR,RELATIONSHIP_MANAGER,DOC_STATUS,REMARKS,FACILITY_CATEGORY,FACILITY_NAME,CAM_TYPE,DEFERRAL_TYPE,LSS_COORDINATOR_BRANCH,TYPE,ACTIVITY_TIME,ACTUAL_ACTIVITY_TIME,"
				+"FACILITY_SYSTEM,FACILITY_MANUAL,AMOUNT,CURRENCY,LINE_NUMBER,SERIAL_NUMBER,REGION,SEGMENT,RM_REGION,DELAY_REASON,DELAY_REASON_TEXT  from CMS_NEW_TAT where deprecated='N' and STATUS != 'CLOSED'";
				
			
			if(!"".equalsIgnoreCase(cusName))
				sql+=" and trim(lower(LSP_SHORT_NAME)) like '"+(cusName.toLowerCase().trim())+"%'";
			if(!"".equalsIgnoreCase(cusId))
				sql+=" and trim(lower(LSP_LE_ID)) like '"+cusId.toLowerCase().trim()+"%'";
			if(!"".equalsIgnoreCase(caseIni))
				sql+=" and trim(lower(CASE_INITIATOR)) in (select lower(employee_id)  from cms_user where lower(user_name) like '"+caseIni.toLowerCase().trim()+"%')";
			if(!"".equalsIgnoreCase(lastUpdate))
				sql+=" and trim(lower(LAST_UPDATE_BY)) in (select lower(employee_id)  from cms_user where lower(user_name) like '"+lastUpdate.toLowerCase().trim()+"%')";
			if(!"".equalsIgnoreCase(rgn))
				sql+=" and trim(RM_REGION) = '"+rgn.trim()+"'";
			if(!"".equalsIgnoreCase(sts)){
				if(sts.equals("DOCUMENT_RESUBMITTED")){
					sts = "DOCUMENT_SUBMITTED";
					sql+=" and TYPE = 'FTNR' and trim(lower(STATUS)) like '"+sts.toLowerCase().trim()+"'";
				}
				else if(sts.equals("DOCUMENT_RESCANNED")){
					sts = "DOCUMENT_SCANNED";
					sql+=" and TYPE = 'FTNR' and trim(lower(STATUS)) like '"+sts.toLowerCase().trim()+"'";
				}
				else if(sts.equals("DOCUMENT_RERECEIVED")){
					sts = "DOCUMENT_RECEIVED";
					sql+=" and TYPE = 'FTNR' and trim(lower(STATUS)) like '"+sts.toLowerCase().trim()+"'";
				}else if(sts.equals("DOCUMENT_RERECEIVED")){
					sts = "DOCUMENT_RECEIVED";
					sql+=" and TYPE = 'FTNR' and trim(lower(STATUS)) like '"+sts.toLowerCase().trim()+"'";
				}
				else {
					sql+=" AND (TYPE  IS NULL OR TYPE = 'FTNR' OR type = 'FTR') and  trim(lower(STATUS)) like '"+sts.toLowerCase().trim()+"'";
				}
			}
			if(!"".equalsIgnoreCase(sgmnt))
				sql+=" and trim(SEGMENT) = '"+sgmnt.trim()+"'";
			if(!"".equalsIgnoreCase(mdl))
				sql+=" and trim(lower(MODULE)) like '"+mdl.toLowerCase().trim()+"%'";
			
			sql+= " order by decode "+
	         "(status, 'DOCUMENT_SUBMITTED',1,"+
	         " 'DOCUMENT_SCANNED',2,"+
	         " 'DOCUMENT_RECEIVED',3,"+
	         " 'DEFERRAL_RAISED',4,"+
			 " 'DEFERRAL_APPROVED',5,"+
			 " 'DOCUMENT_RESCANNED',6,"+
			 " 'DOCUMENT_RERECEIVED',7,"+
			 " 'CLIMS_UPDATED',8,"+
			 " 'LIMIT_RELEASED',9)";
			
			resultList = (ArrayList) getJdbcTemplate().query(sql,
					new TatRowMapper());

		} catch (Exception e) {
			throw new TatException("ERROR-- While retriving TAT");
		}
		//SearchResult searchresult = new SearchResult(0, 10, 100, resultList);
		return resultList;
	}

	public ArrayList getAuditTrailDetail(String caseid){


		ArrayList resultList = null;


		String sql="SELECT ID, VERSION_TIME, CREATE_BY, CREATION_DATE, LAST_UPDATE_BY ,LAST_UPDATE_DATE,DEPRECATED,STATUS,CMS_LE_MAIN_PROFILE_ID,LSP_LE_ID,LSP_SHORT_NAME,CASE_ID,MODULE,"
			+"CASE_INITIATOR,RELATIONSHIP_MANAGER,DOC_STATUS,REMARKS,FACILITY_CATEGORY,FACILITY_NAME,CAM_TYPE,DEFERRAL_TYPE,LSS_COORDINATOR_BRANCH,TYPE,ACTIVITY_TIME,ACTUAL_ACTIVITY_TIME,"
			+"FACILITY_SYSTEM,FACILITY_MANUAL,AMOUNT,CURRENCY,LINE_NUMBER,SERIAL_NUMBER,REGION,SEGMENT,RM_REGION,DELAY_REASON ,DELAY_REASON_TEXT from CMS_STAGE_NEW_TAT where deprecated='N' and STATUS != 'CLOSED'  ";

		if(!"".equalsIgnoreCase(caseid))
			sql+=" and trim(CASE_ID) = '"+(caseid)+"'";
		sql+=" ORDER BY id asc";
		resultList = (ArrayList) getJdbcTemplate().query(sql,
				new TatRowMapper());

		return resultList;
	}
	
	public ArrayList getVersionTime(String caseid){


		ArrayList resultList = null;


		String sql="SELECT ID, VERSION_TIME ,CASE_NO from CMS_NEW_TAT_REPORT_CASEBASE";

		if(!"".equalsIgnoreCase(caseid))
			sql+=" where trim(CASE_NO) = '"+(caseid)+"'";
		sql+=" ORDER BY id asc";
		resultList = (ArrayList) getJdbcTemplate().query(sql,
				new TatReportRowMapper());

		return resultList;
	}
	
	
	public ArrayList getTatCase(String caseid){

		ArrayList resultList = null;
		String sql="SELECT ID, VERSION_TIME, CREATE_BY, CREATION_DATE, LAST_UPDATE_BY ,LAST_UPDATE_DATE,DEPRECATED,STATUS,CMS_LE_MAIN_PROFILE_ID,LSP_LE_ID,LSP_SHORT_NAME,CASE_ID,MODULE,"
			+"CASE_INITIATOR,RELATIONSHIP_MANAGER,DOC_STATUS,REMARKS,FACILITY_CATEGORY,FACILITY_NAME,CAM_TYPE,DEFERRAL_TYPE,LSS_COORDINATOR_BRANCH,TYPE,ACTIVITY_TIME,ACTUAL_ACTIVITY_TIME,"
			+"FACILITY_SYSTEM,FACILITY_MANUAL,AMOUNT,CURRENCY,LINE_NUMBER,SERIAL_NUMBER,REGION,SEGMENT,RM_REGION,DELAY_REASON,DELAY_REASON_TEXT  from CMS_NEW_TAT where deprecated='N' and STATUS != 'CLOSED' ";

		if(!"".equalsIgnoreCase(caseid))
			sql+=" and trim(CASE_ID) = '"+(caseid)+"'";
		sql+=" ORDER BY id asc";
		
		
		resultList = (ArrayList) getJdbcTemplate().query(
				sql,
				new TatRowMapper());
		
		return resultList;
	}

	public class TatReportRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBNewTatReportCase result = new OBNewTatReportCase();
			result.setId(rs.getLong("ID"));
			result.setVersionTime(rs.getLong("VERSION_TIME"));
		
			if(rs.getString("CASE_NO")!=null){
				result.setCase_No(rs.getString("CASE_NO"));
			}
			

			return result;
		}
	}
	
	
	public class TatRowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBNewTat result = new OBNewTat();
			result.setId(rs.getLong("ID"));
			result.setVersionTime(rs.getLong("VERSION_TIME"));
			result.setCreateBy(rs.getString("CREATE_BY"));
				if(rs.getString("CREATION_DATE")!=null){
					result.setCreationDate(rs.getTimestamp("CREATION_DATE"));
				}
				if(rs.getString("LAST_UPDATE_DATE")!=null){
					result.setLastUpdateDate(rs.getTimestamp("LAST_UPDATE_DATE"));
				}
				if(rs.getString("ACTIVITY_TIME")!=null){
					result.setActivityTime(rs.getTimestamp("ACTIVITY_TIME"));
				}
				if(rs.getString("ACTUAL_ACTIVITY_TIME")!=null){
					result.setActualActivityTime(rs.getTimestamp("ACTUAL_ACTIVITY_TIME"));
				}
			if(rs.getString("CMS_LE_MAIN_PROFILE_ID")!=null){
				result.setCmsLeMainProfileId(rs.getLong("CMS_LE_MAIN_PROFILE_ID"));
			}
			if(rs.getString("CASE_ID")!=null){
				result.setCaseId(Long.parseLong(rs.getString("CASE_ID")));
			}
			result.setLastUpdateBy(rs.getString("LAST_UPDATE_BY"));
			result.setDeprecated(rs.getString("DEPRECATED"));
			result.setStatus(rs.getString("STATUS"));
			result.setLspLeId(rs.getString("LSP_LE_ID"));
			result.setLspShortName(rs.getString("LSP_SHORT_NAME"));
			result.setModule(rs.getString("MODULE"));
			result.setCaseInitiator(rs.getString("CASE_INITIATOR"));
			result.setRelationshipManager(rs.getString("RELATIONSHIP_MANAGER"));
			result.setDocStatus(rs.getString("DOC_STATUS"));
			result.setRemarks(rs.getString("REMARKS"));
			result.setFacilityCategory(rs.getString("FACILITY_CATEGORY"));
			result.setFacilityName(rs.getString("FACILITY_NAME"));
			result.setCamType(rs.getString("CAM_TYPE"));
			result.setDeferralType(rs.getString("DEFERRAL_TYPE"));
			result.setLssCoordinatorBranch(rs.getString("LSS_COORDINATOR_BRANCH"));
			result.setType(rs.getString("TYPE"));
			result.setFacilitySystem(rs.getString("FACILITY_SYSTEM"));
			result.setFacilityManual(rs.getString("FACILITY_MANUAL"));
			result.setAmount(rs.getString("AMOUNT"));
			result.setCurrency(rs.getString("CURRENCY"));
			result.setLineNumber(rs.getString("LINE_NUMBER"));
			result.setSerialNumber(rs.getString("SERIAL_NUMBER"));
			result.setRegion(rs.getString("REGION"));
			result.setRmRegion(rs.getString("RM_REGION"));
			result.setSegment(rs.getString("SEGMENT"));
			result.setDelayReason(rs.getString("DELAY_REASON"));
			result.setDelayReasonText(rs.getString("DELAY_REASON_TEXT"));
			return result;
		}
	}

	public class rowMapper implements RowMapper {

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			Long result = new Long(rs.getLong("DIFF_TIME"));
			System.out.println("*********"+rs.getLong("DIFF_TIME"));
			
			return result;
		}
	}
	
	
	public long getNewTatReportCaseByCaseId(String caseid){

		String sql="SELECT ID from CMS_NEW_TAT_REPORT_CASEBASE where Case_No = '"+caseid+"'";


		ResultSet rs = null;
		long id = 0;



		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);

			rs = dbUtil.executeQuery();
			if (rs.next()) {

				return id = rs.getLong("id");

			}


		}
		catch (SQLException e) {
			throw new SearchDAOException("Error in getDocumentationStatus" + e.getClass());
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getDocumentationStatus" + e.getClass());
		}
		finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}
		return id;


	}
	private void finalize(DBUtil dbUtil, ResultSet rs) throws SearchDAOException {
		try {
			if (null != rs) {
				rs.close();
			}
		}
		catch (Exception e) {
		}
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources!", e);
		}
	}
	public INewTat getTatDetail(String caseid, String status){


		ArrayList resultList = null;


		String sql="SELECT ID, VERSION_TIME, CREATE_BY, CREATION_DATE, LAST_UPDATE_BY ,LAST_UPDATE_DATE,DEPRECATED,STATUS,CMS_LE_MAIN_PROFILE_ID,LSP_LE_ID,LSP_SHORT_NAME,CASE_ID,MODULE,"
			+"CASE_INITIATOR,RELATIONSHIP_MANAGER,DOC_STATUS,REMARKS,FACILITY_CATEGORY,FACILITY_NAME,CAM_TYPE,DEFERRAL_TYPE,LSS_COORDINATOR_BRANCH,TYPE,ACTIVITY_TIME,ACTUAL_ACTIVITY_TIME,"
			+"FACILITY_SYSTEM,FACILITY_MANUAL,AMOUNT,CURRENCY,LINE_NUMBER,SERIAL_NUMBER,REGION,SEGMENT,RM_REGION,DELAY_REASON,DELAY_REASON_TEXT  from CMS_STAGE_NEW_TAT where deprecated='N' ";

		if(!"".equalsIgnoreCase(caseid))
			sql+=" and CASE_ID = '"+caseid.trim()+"'";
		sql+=" and STATUS = '"+status.trim()+"' order by id desc ";
		resultList = (ArrayList) getJdbcTemplate().query(sql,
				new TatRowMapper());
		if(resultList.size()>0){
			return (INewTat) resultList.get(0);
		}else{
			return null;
		}
	}
	
	public INewTat getFirstSubmitTime(String caseid, String status) {
		ArrayList resultList = null;


		String sql="SELECT ID, VERSION_TIME, CREATE_BY, CREATION_DATE, LAST_UPDATE_BY ,LAST_UPDATE_DATE,DEPRECATED,STATUS,CMS_LE_MAIN_PROFILE_ID,LSP_LE_ID,LSP_SHORT_NAME,CASE_ID,MODULE,"
			+"CASE_INITIATOR,RELATIONSHIP_MANAGER,DOC_STATUS,REMARKS,FACILITY_CATEGORY,FACILITY_NAME,CAM_TYPE,DEFERRAL_TYPE,LSS_COORDINATOR_BRANCH,TYPE,ACTIVITY_TIME,ACTUAL_ACTIVITY_TIME,"
			+"FACILITY_SYSTEM,FACILITY_MANUAL,AMOUNT,CURRENCY,LINE_NUMBER,SERIAL_NUMBER,REGION,SEGMENT,RM_REGION,DELAY_REASON,DELAY_REASON_TEXT  from CMS_STAGE_NEW_TAT where deprecated='N' ";

		if(!"".equalsIgnoreCase(caseid))
			sql+=" and CASE_ID = '"+caseid.trim()+"'";
		sql+=" and STATUS = '"+status.trim()+"'";
		resultList = (ArrayList) getJdbcTemplate().query(sql,
				new TatRowMapper());
		if(resultList.size()>0){
			return (INewTat) resultList.get(0);
		}else{
			return null;
		}
	}

	public String getDifferenceInMin(String processedDate,String processedTime, String receivedDate,String receivedTime,String module,long caseId){


		ArrayList resultList = null;


		String sql="  select "+
 
			" CAL_DIFF_DATE('"+receivedDate+"','"+receivedTime+"','"+processedDate+"','"+processedTime+"', "+
		 " (SELECT start_timing FROM cms_tat_master WHERE event_code ='"+module+"' "+
	  "  ), "+
  " (SELECT end_timing "+
		  " FROM cms_tat_master "+
  " WHERE event_code = '"+module+"'  "+
	  "  ),  ( (to_date('"+processedDate+"','DD-MM-YYYY') - to_date('"+receivedDate+"','DD-MM-YYYY')) -  "+
		  " (COALESCE ( "+
		  " (SELECT SUM((TRUNC(end_date) - TRUNC(start_date)+1)- (next_day(TRUNC(end_date), 'MONDAY')-next_day(TRUNC(start_date), 'MONDAY'))/7 ) "+
  "  FROM cms_holiday h "+
  "  WHERE TRUNC(h.start_date) BETWEEN to_date(TO_CHAR('"+receivedDate+"'),'DD-MM-YYYY') AND to_date(TO_CHAR('"+processedDate+"'),'DD-MM-YYYY') "+
  "  OR TRUNC(h.end_date) BETWEEN to_date(TO_CHAR('"+receivedDate+"'),'DD-MM-YYYY') AND to_date(TO_CHAR('"+processedDate+"'),'DD-MM-YYYY') "+
  " ),0) "+
  " + "+
  "  COALESCE ((select  (next_day(to_date(TO_CHAR('"+processedDate+"'),'DD-MM-YYYY'), 'MONDAY')-next_day(to_date(TO_CHAR('"+receivedDate+"'),'DD-MM-YYYY'), 'MONDAY'))/7 "+
		  " from tat_rpt_for_Branch abc where abc.case_No = "+caseId+"),0) "+
" ) "+
  "  ) * ((to_date('"+processedDate+"' "+
		  " || ' ' "+
  "  || "+
  " (SELECT end_timing FROM cms_tat_master WHERE event_code = '"+module+"'  "+
	  "  ), 'dd-mm-yyyy HH24:MI') - to_date('"+processedDate+"' "+
		  " || ' ' "+
  "  || "+
  " (SELECT start_timing FROM cms_tat_master WHERE event_code = '"+module+"'  "+
	  " ), 'dd-mm-yyyy HH24:MI') )*24 )) AS diff_time "+
  " from dual ";

		
		/*resultList = (ArrayList) getJdbcTemplate().query(sql,
				new rowMapper());
		if(resultList.size()>0){
			return (Long) resultList.get(0);
		}else{
			return null;
		}*/
		System.out.println("getDifferenceInMin() => sql=>"+sql);

		ResultSet rs = null;
		String  id = "";
	try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
          System.out.println(sql);
			rs = dbUtil.executeQuery();
			if (rs.next()) {

				return id = new String(rs.getString("diff_time"));

			}
}
		catch (SQLException e) {
			throw new SearchDAOException("Error in getDocumentationStatus" + e.getClass());
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in getDocumentationStatus" + e.getClass());
		}
		finally {
			try {
				finalize(dbUtil, rs);
			}
			catch (Exception e) {
				throw new SearchDAOException("Error in finalize" + e.getMessage());
			}
		}
		return id;
		}

}
