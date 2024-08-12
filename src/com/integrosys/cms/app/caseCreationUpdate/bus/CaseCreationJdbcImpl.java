package com.integrosys.cms.app.caseCreationUpdate.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.common.bus.PaginationUtil;

/**
 * 
 * @author $Author: Abhijit R. $
 */

public class CaseCreationJdbcImpl extends JdbcDaoSupport implements
ICaseCreationJdbc {

	private static final String SELECT_CASECREATION_TRX = "SELECT id,description,LIMIT_PROFILE_ID,last_update_date,creation_Date,deprecated,status,branchCode,curr_Remarks,prev_Remarks from cms_caseCreationUpdate  ";
	
	private static final String SELECT_CASECREATION_TRX_SEARCH = "   SELECT  distinct caseC.id, caseC.description, sp.lsp_short_name as customerName , cc.entry_name as Segment , caseC.LIMIT_PROFILE_ID, caseC.last_update_date, caseC.creation_Date,  caseC.deprecated,   caseC.status,   caseC.branchCode , caseC.CURR_REMARKS , caseC.PREV_REMARKS  from cms_caseCreationUpdate caseC , sci_le_sub_profile sp   , sci_lsp_lmt_profile cam   , common_code_category_entry cc ,SCI_LE_REG_ADDR addr   ,CMS_REGION cr ,CMS_CASECREATION_ITEM cci    where   casec.limit_profile_id=cam.cms_lsp_lmt_profile_id   and sp.lsp_le_id= cam.llp_le_id  AND sp.status = 'ACTIVE'   and cc.entry_code=sp.LSP_SGMNT_CODE_VALUE   and cc.category_code='HDFC_SEGMENT' and addr.cms_le_main_profile_id=sp.cms_le_main_profile_id   and  addr.lra_type_value ='CORPORATE'   and cr.id=addr.lra_region  and cci.casecreationid=casec.id  ";
	
	private static final String SELECT_RECCASECREATION_TRX = "SELECT id,description,start_Date,end_Date,last_update_date,last_update_by,status from cms_caseCreationUpdate where deprecated='N' and IS_RECURRENT='on' ORDER BY start_Date asc ";
	
	private static final String SELECT_INSERT_CASECREATION_TRX = "SELECT id,description,start_Date,end_Date,last_update_date,last_update_by from cms_stage_caseCreationUpdate where deprecated='N' AND ID ";
	
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
	/**
	 * @return List of all CaseCreation according to the query passed.
	 */

	public SearchResult getAllCaseCreation(String searchBy, String searchText) {
		List resultList = null;
		String searchByValue = searchBy;
		String searchTextValue = searchText.toUpperCase();

		String GET_BRANCH_QUERY_STRING = SELECT_CASECREATION_TRX
		+ "AND ( " + searchByValue + " LIKE '" + searchTextValue
		+ "%' )";
		try {
			if (searchTextValue != null && searchTextValue.trim() != "") {
				resultList = getJdbcTemplate().query(GET_BRANCH_QUERY_STRING,
						new CaseCreationRowMapper());
			}

		} catch (Exception e) {
			throw new CaseCreationException("ERROR-- While retriving CaseCreation");
		}
		SearchResult searchresult = new SearchResult(0, resultList.size(),  resultList.size(), resultList);
		return searchresult;
	}

	/**
	 * @return List of all authorized CaseCreation
	 */
	public SearchResult getAllCaseCreation(long id) {
		List resultList = null;
		try {
			StringBuffer sql= new StringBuffer();
			sql.append(SELECT_CASECREATION_TRX);
			sql.append(" where limit_profile_id="+id);	
			sql.append(" ORDER BY creation_Date asc");
			resultList = getJdbcTemplate().query(sql.toString(),
					new CaseCreationRowMapper());

		} catch (Exception e) {
			throw new CaseCreationException("ERROR-- While retriving CaseCreation");
		}
		SearchResult searchresult = new SearchResult(0, resultList.size(),  resultList.size(), resultList);
		return searchresult;
		}
	
	public SearchResult getAllCaseCreationBranchMenu(String branchCode) {
		List resultList = null;
		try {
			StringBuffer sql= new StringBuffer();
			sql.append(SELECT_CASECREATION_TRX_SEARCH);
			sql.append(" AND casec.branchCode="+branchCode);	
//			sql.append(" ORDER BY creation_Date asc");
			sql.append(" ORDER BY LIMIT_PROFILE_ID ASC ");
			resultList = getJdbcTemplate().query(sql.toString(),
					new CaseCreationRowMapper());

		} catch (Exception e) {
			throw new CaseCreationException("ERROR-- While retriving CaseCreation");
		}
		SearchResult searchresult = new SearchResult(0, resultList.size(),  resultList.size(), resultList);
		return searchresult;
		}
	/**
	 * @return List of all CaseCreation according to the query passed.
	 */
	public List getAllCaseCreationSearch(String login) {
		String SELECT_CASECREATION_BY_SEARCH = "SELECT id,description,startDate,endDate,last_update_date from CMS_CASECREATION where deprecated='N' AND description  LIKE '"
			+ login + "%' ";

		List resultList = null;
		try {

			if (login == null || login.trim() == "") {
				resultList = getJdbcTemplate().query(
						SELECT_CASECREATION_TRX,
						new CaseCreationRowMapper());
			} else {
				resultList = getJdbcTemplate().query(
						SELECT_CASECREATION_BY_SEARCH,
						new CaseCreationRowMapper());
			}

		} catch (Exception e) {
			throw new CaseCreationException("ERROR-- While retriving CaseCreation");
		}
		return resultList;
	}

	public class CaseCreationRowMapper implements RowMapper {
		
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OBCaseCreation result = new OBCaseCreation();
			result.setDescription(rs.getString("description"));
			result.setCreationDate(rs.getDate("creation_Date"));
			result.setLastUpdateDate(rs.getDate("last_update_date"));
			result.setDeprecated(rs.getString("deprecated"));
			result.setId(rs.getLong("id"));
			result.setStatus(rs.getString("status"));
			result.setBranchCode(rs.getString("branchCode"));
			result.setCurrRemarks(rs.getString("curr_Remarks"));
			result.setPrevRemarks(rs.getString("prev_Remarks"));
			result.setLimitProfileId(rs.getLong("limit_profile_id"));
			return result;
		}
	}
	/**
	 * @return List of all CaseCreation according to the query passed.
	 */

	public ICaseCreation listCaseCreation(long caseCreationUpdateCode)
	throws SearchDAOException,CaseCreationException {
		String SELECT_CASECREATION_ID = "SELECT id,description,startDate,endDate,last_update_date from description  where id="
			+ caseCreationUpdateCode;
		ICaseCreation caseCreationUpdate = new OBCaseCreation();
		try {
			caseCreationUpdate = (ICaseCreation) getJdbcTemplate().query(
					SELECT_CASECREATION_ID,
					new CaseCreationRowMapper());
		} catch (Exception e) {
			throw new CaseCreationException("ERROR-- While retriving CaseCreation");
		}

		return caseCreationUpdate;

	}
	
	public List getCaseCreationListForYear(long year) {
		List resultList = null;
		OBCaseCreation caseCreationUpdate;
		try {
			resultList = getJdbcTemplate().query(SELECT_CASECREATION_TRX, new CaseCreationRowMapper());
		} catch (Exception e) {
			throw new CaseCreationException("ERROR-- While retriving CaseCreation");
		}
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		if (resultList != null) {
			Iterator iter = resultList.iterator();
			while (iter.hasNext()) {
				caseCreationUpdate = (OBCaseCreation)iter.next();
				//startDate.setTime(caseCreationUpdate.getStartDate());
				//endDate.setTime(caseCreationUpdate.getEndDate());
				if (startDate.get(Calendar.YEAR) != year && endDate.get(Calendar.YEAR) != year) {
					iter.remove();
				}
			}
		}
		return resultList;
	}

	public List getRecurrentCaseCreationListForYear(long year) {
		List resultList = null;
		OBCaseCreation caseCreationUpdate;
		try {
			resultList = getJdbcTemplate().query(SELECT_RECCASECREATION_TRX,	new CaseCreationRowMapper());
		} catch (Exception e) {
			throw new CaseCreationException("ERROR-- While retriving CaseCreation");
		}
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		if (resultList != null) {
			Iterator iter = resultList.iterator();
			while (iter.hasNext()) {
				caseCreationUpdate = (OBCaseCreation)iter.next();
				//startDate.setTime(caseCreationUpdate.getStartDate());
				//endDate.setTime(caseCreationUpdate.getEndDate());
				if (startDate.get(Calendar.YEAR) != year && endDate.get(Calendar.YEAR) != year) {
					iter.remove();
				}
			}
		}
		return resultList;
	}
	
	
	public SearchResult getAllCaseCreation() {
		List resultList = null;
		try {
			StringBuffer sql= new StringBuffer();
			sql.append(SELECT_CASECREATION_TRX_SEARCH);
			//sql.append(" where limit_profile_id="+id);	
//			sql.append(" ORDER BY creation_Date asc");
			sql.append(" ORDER BY LIMIT_PROFILE_ID ASC ");
			System.out.println("SearchResult getAllCaseCreation() method =>sql=>"+sql);
			resultList = getJdbcTemplate().query(sql.toString(),
					new CaseCreationRowMapper());
			System.out.println("After query execute in getAllCaseCreation().");

		} catch (Exception e) {
			System.out.println("ERROR-- While retriving CaseCreation=>  SearchResult getAllCaseCreation() method");
			throw new CaseCreationException("ERROR-- While retriving CaseCreation");
		}
		SearchResult searchresult = new SearchResult(0,  resultList.size(), resultList.size(), resultList);
		System.out.println("After query execute in getAllCaseCreation()=> searchresult.");
		return searchresult;
		}
	
	
	public SearchResult getAllCaseCreationBranchMenuSearch(String partyName , String caseId, String region , String segment, String status, String branchCode) {
		List resultList = null;
		try {
			StringBuffer sql= new StringBuffer();
			sql.append(SELECT_CASECREATION_TRX_SEARCH);
			sql.append(" and caseC.branchCode="+branchCode);	
			if(partyName!=null && !partyName.equals("")){
				sql.append(" and UPPER(sp.lsp_short_name)  LIKE UPPER ('"+ partyName.trim() + "%') ");
			}
			if(caseId!=null && !caseId.equals("")){
				sql.append(" and casec.id='"+ caseId.trim() + "' ");
			}
			if(region!=null && !region.equals("")){
				sql.append(" and cr.id = '"+ region.trim() + "' ");			
						}
			if(segment!=null && !segment.equals("")){
				
				sql.append(" and UPPER(cc.ENTRY_CODE)  LIKE UPPER ('"+ segment.trim() + "%') ");
			}
			
			if(status!=null && !status.equals("")){
				sql.append(" and cci.status = '"+ status.trim() + "' ");
			}

				
			sql.append(" ORDER BY creation_Date asc");
			resultList = getJdbcTemplate().query(sql.toString(),
					new CaseCreationRowMapper());

		} catch (Exception e) {
			throw new CaseCreationException("ERROR-- While retriving CaseCreation");
		}
		SearchResult searchresult = new SearchResult(0,  resultList.size(), resultList.size(), resultList);
		return searchresult;
		}
	
	public SearchResult getAllCaseCreationCPUTMenuSearch(String partyName , String caseId, String region , String segment, String status) {
		List resultList = null;
		try {
			StringBuffer sql= new StringBuffer();
			sql.append(SELECT_CASECREATION_TRX_SEARCH);
			//sql.append(" and caseC.branchCode="+branchCode);	
			if(partyName!=null && !partyName.equals("")){
				sql.append(" and UPPER(sp.lsp_short_name)  LIKE UPPER ('"+ partyName.trim() + "%') ");
			}
			if(caseId!=null && !caseId.equals("")){
				sql.append(" and casec.id='"+ caseId.trim() + "' ");
			}
			if(region!=null && !region.equals("")){
				sql.append(" and cr.id = '"+ region.trim() + "' ");			
						}
			if(segment!=null && !segment.equals("")){
				
				sql.append(" and UPPER(cc.ENTRY_CODE)  LIKE UPPER ('"+ segment.trim() + "%') ");
			}
			
			if(status!=null && !status.equals("")){
				sql.append(" and cci.status = '"+ status.trim() + "' ");
			}

				
			sql.append(" ORDER BY creation_Date asc");
			resultList = getJdbcTemplate().query(sql.toString(),
					new CaseCreationRowMapper());

		} catch (Exception e) {
			throw new CaseCreationException("ERROR-- While retriving CaseCreation");
		}
		SearchResult searchresult = new SearchResult(0,  resultList.size(), resultList.size(), resultList);
		return searchresult;
		}
}
