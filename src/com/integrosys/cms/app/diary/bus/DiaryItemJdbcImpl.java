package com.integrosys.cms.app.diary.bus;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.bus.PaginationBean;
import com.integrosys.cms.app.common.bus.PaginationBeanFactory;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.ui.diaryitem.DiaryItemHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This is the concrete implementation for the IDiaryItemDAO interface Data
 * access methods for Diary Item table
 * 
 * @author $Author: lini $
 * @version $Revision: 1.21 $
 * @since $Date: 2005/12/07 08:39:21 $ Tag: $Name: $
 */

public class DiaryItemJdbcImpl extends JdbcDaoSupport implements IDiaryItemJdbc {
	private final static String CMS_DIARY_ITEM = "CMS_DIARY_ITEM";

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
	 * retrieves the diary items that are due on the current date
	 * @param criteria
	 * @return SearchResult - indexed results
	 * @throws SearchDAOException
	 */
	public SearchResult getDiaryItemsDueFor(final DiaryItemSearchCriteria criteria) throws SearchDAOException {
		DefaultLogger.info(this, "Entering method getDiaryItemsDueFor");

		if (criteria == null) {
			throw new SearchDAOException("The DiaryItemSearchCriteria is null !!!");
		}

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();
		int numTotalRecords = getNoOfDiaryItemsDue(criteria.getTeam().getTeamID(), customerIndex, criteria.getLeID(),
				criteria.getAllowedCountries());
		if (numTotalRecords == 0) {
			return null;
		}

		DefaultLogger.debug(this, "Entering method getNonExpiredDiaryItems");

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ");
		sb.append("ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE,  (select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT"
				+ ", FAM ,DIARY_NUMBER,ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR ");
		sb.append(" FROM ").append(CMS_DIARY_ITEM);
		sb.append(" WHERE TEAM_ID = ?");
		sb.append(" AND ( TRUNC( ITEM_DUE_DATE ) = TRUNC(CAST(? AS TIMESTAMP)) ");
		sb.append("OR TRUNC( ITEM_EXPIRY_DATE ) = TRUNC(CAST(? AS TIMESTAMP)) )");

		if (customerIndex != null) {
			appendCustomerFilter(sb);
		}

		if (criteria.getLeID() != null) {
			appendLeIDFilter(sb);
		}

		sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);

		String sql = sb.toString();

		List argsList = new ArrayList();

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		argsList.add(new java.sql.Date(DateUtil.getDate().getTime()));
		argsList.add(new java.sql.Date(DateUtil.getDate().getTime()));

		if (customerIndex != null) {
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (criteria.getLeID() != null) {
			argsList.add(criteria.getLeID() + "%");
		}

		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}

		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);

		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSet(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						return new SearchResult(startIndex, results.size(), (criteria
								.getTotalCountForCurrentTotalPages() != null ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}

	/**
	 * retrieves the diary items based on the search criteria
	 * @param criteria
	 * @return SearchResult - indexed results
	 * @throws SearchDAOException
	 */
	public SearchResult getNonExpiredDiaryItems(final DiaryItemSearchCriteria criteria) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ");
		sb.append("ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE,  (select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT"
				+ ", FAM,DIARY_NUMBER,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = activity) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR  ");
		sb.append(" FROM ");
		sb.append(CMS_DIARY_ITEM);
		sb.append(" WHERE IS_DELETE='N' and STATUS <> 'Closed' and");
		sb.append(" TEAM_ID = ?");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			appendCustomerFilter(sb);
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			appendLeIDFilter(sb);
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSet(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}

	/**
	 * retrieves the number of diary items that are not expired
	 * @param team
	 * @param allowedCountries
	 * @return int - number of non-expired diary items
	 * @throws SearchDAOException
	 */
	public int getNoOfNonExpiredDiaryItems(ITeam team, String[] allowedCountries) throws SearchDAOException {
		throw new UnsupportedOperationException("getNoOfNonExpiredDiaryItems is not implemented");
	}

	/**
	 * appends the customer filter condition to the SQL
	 * @param sb
	 */
	private static void appendCustomerFilter(StringBuffer sb) {
		sb.append(" AND UPPER(CUSTOMER_NAME) LIKE ?");
	}

	/**
	 * appends the customer le reference filter condition to the SQL
	 * @param sb
	 */
	private static void appendLeIDFilter(StringBuffer sb) {
		sb.append(" AND LE_ID LIKE ?");
	}

	/**
	 * appends the country filter condition to the SQL
	 * @param sb
	 */
	private static void appendCountryFilter(StringBuffer sb) {
		sb.append(" AND ALLOWED_COUNTRY = ? ");
	}

	/**
	 * retrieves the number of diary items that due on the current date
	 * @param teamTypeID
	 * @param allowedCountries
	 * @return int - number of diary items due
	 * @throws SearchDAOException
	 */
	public int getNoOfDiaryItemsDueFor(ITeam team, String[] allowedCountries) throws SearchDAOException {
		DefaultLogger.debug(this, "Entering method getNoOfDiaryItemsDueFor");
		return getNoOfDiaryItemsDue(team.getTeamID(), null, null, allowedCountries);
	}

	/**
	 * retrieves the number of diary items that due on the current date indexed
	 * by customer name
	 * @param teamTypeID
	 * @param customerIndex
	 * @param leID
	 * @return int - number of diary items
	 * @throws SearchDAOException
	 */
	protected int getNoOfDiaryItemsDue(long teamId, String customerIndex, String leID, String[] allowedCountries)
			throws SearchDAOException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(*) ");
		sb.append(" FROM ");
		sb.append(CMS_DIARY_ITEM);
		sb.append(" WHERE ");
		if(StringUtils.isNotBlank(customerIndex))
		{
		sb.append(" UPPER(CUSTOMER_NAME) like ? AND ");
		}
		if(StringUtils.isNotBlank(leID))
		{
		sb.append("LE_ID like ? AND ");
		}
		sb.append(" TEAM_ID = ?");
		sb.append(" AND ( TRUNC( ITEM_DUE_DATE ) = TRUNC(CAST(? AS TIMESTAMP)) ");
		sb.append("OR TRUNC( ITEM_EXPIRY_DATE ) = TRUNC(CAST(? AS TIMESTAMP)) ) ");

		List argsList = new ArrayList();

		if (StringUtils.isNotBlank(customerIndex)) {
			//appendCustomerFilter(sb);
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(leID)) {
			//appendLeIDFilter(sb);
			argsList.add(leID + "%");
		}

		sb.append(getCountryCondition(allowedCountries));

		String sql = sb.toString();

		argsList.add(new Long(teamId));
		argsList.add(new java.sql.Date(DateUtil.getDate().getTime()));
		argsList.add(new java.sql.Date(DateUtil.getDate().getTime()));

		if(allowedCountries!=null)
		{
		for (int i = 0; i < allowedCountries.length; i++) {
			argsList.add(allowedCountries[i]);
		}
		}

		return getJdbcTemplate().queryForInt(sql, argsList.toArray());
	}
	
	private void appendDateRangeFilter(StringBuffer sb) {
		sb.append(" AND ( trunc(ITEM_EXPIRY_DATE) between ? and ? )");
	}

	private boolean hasDateFilter(java.util.Date startExpDate, java.util.Date endExpDate) {
		return (startExpDate != null) && (endExpDate != null);
	}

	/**
	 * helper method to prepare bind variables to SQL statement
	 * @param allowedCountries
	 * @return String - country where clause
	 */
	private String getCountryCondition(String[] allowedCountries) {
		StringBuffer sb = new StringBuffer();

		if ((allowedCountries != null) && (allowedCountries.length > 0)) {
			sb.append(" AND ALLOWED_COUNTRY IN (");
			for (int i = 0; i < allowedCountries.length; i++) {
				sb.append("?");
				if (i < allowedCountries.length - 1) {
					sb.append(",");
				}
			}
			sb.append(" )");
		}
		return sb.toString();
	}

	/**
	 * tests a string for its proposition
	 * @param s
	 * @return boolean - true if match
	 */
	private boolean isTrue(String s) {
		return ICMSConstant.TRUE_VALUE.equals(s) ? true : false;
	}

	private void appendSortOrder(StringBuffer sb, String customerIndex) {
		if ((customerIndex != null) && !customerIndex.trim().equals("")) {
			sb.append(" ORDER BY UPPER(CUSTOMER_NAME) ASC, ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC ");
		}
		else {
			sb.append(" ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC");
		}

	}

	private IDiaryItemUI processDiaryItemResultSet(ResultSet rs) throws SQLException {
		IDiaryItemUI item = new OBDiaryItem();
		item.setItemID(rs.getLong("ITEM_ID"));
		item.setCustomerReference(rs.getString("LE_ID"));
		item.setCustomerName(rs.getString("CUSTOMER_NAME"));
		item.setDescription(rs.getString("DESCRIPTION"));
		item.setDueDate(rs.getDate("ITEM_DUE_DATE"));
		item.setExpiryDate(rs.getDate("ITEM_EXPIRY_DATE"));
		item.setObsoleteInd(isTrue(rs.getString("IS_OBSOLETE")));
		item.setCustomerSegment(rs.getString("CUSTOMER_SEGMENT"));
		item.setFAM(rs.getString("FAM"));
		item.setDiaryNumber(rs.getLong("DIARY_NUMBER"));
		item.setActivity(rs.getString("ACTIVITY"));
		item.setDropLineOD(rs.getString("DROP_LINE_OD"));
		item.setMakerId(rs.getString("MAKER_ID"));
		item.setUploadFileError(rs.getString("OD_UPLOAD_FILE_ERROR"));
		return item;
	}
	
	private IDiaryItemUI processDiaryItemResultSetTodays(ResultSet rs) throws SQLException {
		IDiaryItemUI item = new OBDiaryItem();
		item.setItemID(rs.getLong("ITEM_ID"));
		item.setCustomerReference(rs.getString("LE_ID"));
		item.setCustomerName(rs.getString("CUSTOMER_NAME"));
		item.setDescription(rs.getString("DESCRIPTION"));
		item.setDueDate(rs.getDate("ITEM_DUE_DATE"));
		item.setExpiryDate(rs.getDate("ITEM_EXPIRY_DATE"));
		item.setObsoleteInd(isTrue(rs.getString("IS_OBSOLETE")));
		item.setCustomerSegment(rs.getString("CUSTOMER_SEGMENT"));
		item.setFAM(rs.getString("FAM"));
		item.setDiaryNumber(rs.getLong("DIARY_NUMBER"));
		item.setActivity(rs.getString("ACTIVITY"));
		item.setDropLineOD(rs.getString("DROP_LINE_OD"));
		item.setMakerId(rs.getString("MAKER_ID"));
		item.setUploadFileError(rs.getString("OD_UPLOAD_FILE_ERROR"));
		item.setMonth(rs.getString("MONTH"));
		return item;
	}


	public	List getRegionAndSegment(String legalReference) throws SearchDAOException {
		//	String sql = "select LMP_SGMNT_CODE_VALUE,region from SCI_LE_MAIN_PROFILE where LMP_LE_ID = '"+legalReference+"'";
			
			String sql =" SELECT LMP_SGMNT_CODE_VALUE,(SELECT ENTRY_NAME "+
					" FROM COMMON_CODE_CATEGORY_ENTRY  "+
					" WHERE CATEGORY_CODE = 'HDFC_SEGMENT' "+
					" and entry_code = LMP_SGMNT_CODE_VALUE "+
					" AND ACTIVE_STATUS   ='1') as LMP_SGMNT_ENTRY_VALUE "+
					" FROM SCI_LE_MAIN_PROFILE  WHERE LMP_LE_ID ='"+legalReference+"'";
			
			List resultList = getJdbcTemplate().query(sql, new RowMapper() { 

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
					String[] stringArray = new String[2];
		             stringArray[0] = rs.getString("LMP_SGMNT_CODE_VALUE");
		             stringArray[1] = rs.getString("LMP_SGMNT_ENTRY_VALUE");
		             return stringArray;
				
				}
			});
			 return resultList;
		}

	@Override
	public List getListOfFacilityBoardCategory(String partyId) {
		String sql = "select distinct entry_code,entry_name from common_code_category_entry where CATEGORY_CODE='FACILITY_CATEGORY' AND entry_code IN "
				+ "(select new_facility_category from CMS_FACILITY_NEW_MASTER where new_facility_code IN ( "
				+ "(select lmt_fac_code from sci_lsp_appr_lmts where CMS_LIMIT_STATUS='ACTIVE' AND cms_limit_profile_id = "
				+ "(select cms_lsp_lmt_profile_id from SCI_LSP_LMT_PROFILE where llp_le_id='" + partyId + "')))" + ")";
		DefaultLogger.debug(this,"In getListOfFacilityBoardCategory"+sql);
		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				String[] stringArray = new String[2];
				stringArray[0] = rs.getString("entry_code");
				stringArray[1] = rs.getString("entry_name");
				return stringArray;

			}
		});
		return resultList;
	}

	@Override
	public List getListOfFacilityLineNumber(String customerName, String facilityBoardCategory) {
		String sql = "select distinct line_number,line_number from CMS_FACILITY_NEW_MASTER where new_facility_category = '"
				+ facilityBoardCategory + "' "
				+ "and new_facility_code IN (select lmt_fac_code from sci_lsp_appr_lmts where CMS_LIMIT_STATUS ='ACTIVE' AND cms_limit_profile_id = "
				+ "(select cms_lsp_lmt_profile_id from SCI_LSP_LMT_PROFILE where llp_le_id='" + customerName + "'))";
		DefaultLogger.debug(this,"In getListOfFacilityLineNumber"+sql);
		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				String[] stringArray = new String[2];
				stringArray[0] = rs.getString("line_number");
				stringArray[1] = rs.getString("line_number");
				return stringArray;

			}
		});
		return resultList;
	}

	@Override
	public List getListOfFacilitySerialNumber(String customerName, String facilityBoardCategory,
			String facilityLineNo) {
		String sql = "select distinct SERIAL_NO,SERIAL_NO from sci_lsp_sys_xref where cms_lsp_sys_xref_id  IN"
				+ "(select cms_lsp_sys_xref_id from SCI_LSP_lmts_xref_map where cms_lsp_appr_lmts_id IN "
				+ "(select cms_lsp_appr_lmts_id from sci_lsp_appr_lmts where CMS_LIMIT_STATUS   ='ACTIVE' and cms_limit_profile_id = "
				+ "      (SELECT cms_lsp_lmt_profile_id " + "      FROM SCI_LSP_LMT_PROFILE "
				+ "      WHERE llp_le_id='" + customerName + "' " + "      ) " + "    AND lmt_fac_code ="
				+ "(select new_facility_code from CMS_FACILITY_NEW_MASTER where new_facility_category = '"
				+ facilityBoardCategory + "' "
				+ "and new_facility_code IN (select lmt_fac_code from sci_lsp_appr_lmts where CMS_LIMIT_STATUS ='ACTIVE' AND cms_limit_profile_id ="
				+ "(select cms_lsp_lmt_profile_id from SCI_LSP_LMT_PROFILE where llp_le_id='" + customerName
				+ "')) and LINE_NUMBER = '" + facilityLineNo + "' )))";
		DefaultLogger.debug(this,"In getListOfFacilitySerialNumber"+sql);
		List resultList = getJdbcTemplate().query(sql, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				String[] stringArray = new String[2];
				stringArray[0] = rs.getString("SERIAL_NO");
				stringArray[1] = rs.getString("SERIAL_NO");
				return stringArray;

			}
		});
		return resultList;
	}
	
	@Override
	public List getActivityList() {
		String sql = "select entry_code,entry_name from common_code_category_entry where category_code ='Activity_Type'";
				
				
	    List resultList = getJdbcTemplate().query(sql, new RowMapper() { 

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
			String[] stringArray = new String[2];
            stringArray[0] = rs.getString("entry_code");
            stringArray[1] = rs.getString("entry_name");
            return stringArray;
		
		}
	});
	 return resultList;
}
	
	@Override
	public List getFacilityBoardCategory() {
		String sql = "select entry_code,entry_name from common_code_category_entry where category_code ='FACILITY_CATEGORY'";
				
				
	    List resultList = getJdbcTemplate().query(sql, new RowMapper() { 

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
			String[] stringArray = new String[2];
            stringArray[0] = rs.getString("entry_code");
            stringArray[1] = rs.getString("entry_name");
            return stringArray;
		
		}
	});
	 return resultList;
}	
	
	@Override
	public List getActionList() {
		String sql = "select entry_code,entry_name from common_code_category_entry where category_code ='Action_Type'";
				
				
	    List resultList = getJdbcTemplate().query(sql, new RowMapper() { 

		public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
			String[] stringArray = new String[2];
            stringArray[0] = rs.getString("entry_code");
            stringArray[1] = rs.getString("entry_name");
            return stringArray;
		
		}
	});
	 return resultList;
}
	
	
	
	public String getDiarySequence() {
		String sql = "select CMS_DIARY_NUMBER_SEQ.nextval S from dual";
		long diarySeq = getJdbcTemplate().queryForLong(sql);
		return String.valueOf(diarySeq);
		
	}
	
	public void insertODScheduleData(StringBuffer sqlInsertQuery) {

		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL(sqlInsertQuery.toString());
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "File data is inserted into Table: "+noOfRecords);		
					dbUtil.commit();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in insertFileDataToTable:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in insertFileDataToTable:"+e.getMessage());
			}finally {
				try {
					finalize(dbUtil,rs);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}
	
	
	public static void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	
	public void deleteDiaryItem(String isDelete,long itemId) {

		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("Update CMS_DIARY_ITEM set STATUS ='Closed',IS_DELETE ='"+isDelete+"' where item_id="+itemId);
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "data has been inserted into Table: "+noOfRecords);		
					dbUtil.commit();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in delete Diary Item:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in delete Diary Item:"+e.getMessage());
			}finally {
				try {
					finalize(dbUtil,rs);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}

	public HashMap<String, String> getODTargetDateList(String diaryNumber) throws SearchDAOException {
		
		DBUtil dbUtil = null;
		
		try {
			dbUtil=new DBUtil();
			dbUtil.setSQL("select to_be_reduced_on,is_closed from CMS_DIARY_SCHEDULE_DATA where diary_number ="+diaryNumber);
			ResultSet rs = dbUtil.executeQuery();

			HashMap<String, String> hm = new HashMap<String, String>();
			while (rs.next()) {
				if(rs.getString("is_closed") != null) {
				hm.put(rs.getString("to_be_reduced_on"), rs.getString("is_closed"));
				}
				
			}
			rs.close();
			return hm;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in CMS_DIARY_SCHEDULE_DATA", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in CMS_DIARY_SCHEDULE_DATA", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in CMS_DIARY_SCHEDULE_DATA", ex);
			}
		}
	}
	
	
	public void closeAllODitems(long diaryNumber) {

		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("Update CMS_DIARY_SCHEDULE_DATA set IS_CLOSED ='Y' where diary_number ="+diaryNumber);
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "data has been update into CMS_DIARY_SCHEDULE_DATA: "+noOfRecords);		
					dbUtil.commit();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in update CMS_DIARY_SCHEDULE_DATA Item:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in update CMS_DIARY_SCHEDULE_DATA Item:"+e.getMessage());
			}finally {
				try {
					finalize(dbUtil,rs);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}

public List getMonthAndClosingAmountForOD(String applicationDate, long diaryNumber) {
		
		String sql = "select month,cal_bal from CMS_DIARY_SCHEDULE_DATA where to_be_reduced_on='"+applicationDate+"' and diary_number='"+diaryNumber+"'";
		
		
		
		List resultList = getJdbcTemplate().query(sql, new RowMapper() { 

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException, DataAccessException {
				String[] stringArray = new String[2];
	            stringArray[0] = rs.getString("month");
	            stringArray[1] = rs.getString("cal_bal");
	            return stringArray;
			
			}
		});
		return resultList;
		
	
	}

	@Override
	public SearchResult getGenericListWithSegment(final DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();
		segmentName = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segmentName+"')";
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ");
		sb.append("ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, (select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT"
				+ ", FAM,DIARY_NUMBER,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = activity) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR  ");
		sb.append(" FROM ");
		sb.append(CMS_DIARY_ITEM+ " item");
		sb.append(" WHERE ");
		sb.append(" TEAM_ID = ?");
		sb.append(" and STATUS <> 'Closed' and IS_DELETE='N' and DROP_LINE_OD='N' and CUSTOMER_SEGMENT="+segmentName+"");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			appendCustomerFilter(sb);
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			appendLeIDFilter(sb);
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSet(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}

	@Override
	public SearchResult getGenericListWithoutSegment(final DiaryItemSearchCriteria criteria) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ");
		sb.append("ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, (select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT, FAM,DIARY_NUMBER,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = activity) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR  ");
		sb.append(" FROM ");
		sb.append(CMS_DIARY_ITEM);
		sb.append(" WHERE ");
		sb.append(" TEAM_ID = ?");
		sb.append(" and STATUS <> 'Closed' and IS_DELETE='N'  and DROP_LINE_OD='N'");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			appendCustomerFilter(sb);
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			appendLeIDFilter(sb);
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSet(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}

	@Override
	public SearchResult getDropLineListWithSegment(final DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();
		segmentName = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segmentName+"')";
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ");
		sb.append("ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, (select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT, FAM,DIARY_NUMBER,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = activity) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR  ");
		sb.append(" FROM ");
		sb.append(CMS_DIARY_ITEM);
		sb.append(" WHERE ");
		sb.append(" TEAM_ID = ?");
		sb.append(" and STATUS <> 'Closed' and IS_DELETE='N'  and DROP_LINE_OD='Y' and CUSTOMER_SEGMENT="+segmentName+"");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			appendCustomerFilter(sb);
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			appendLeIDFilter(sb);
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSet(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}

	@Override
	public SearchResult getDropLineListWithoutSegment(final DiaryItemSearchCriteria criteria) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ");
		sb.append("ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, (select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT, FAM,DIARY_NUMBER,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = activity) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR  ");
		sb.append(" FROM ");
		sb.append(CMS_DIARY_ITEM);
		sb.append(" WHERE ");
		sb.append(" TEAM_ID = ?");
		sb.append(" and STATUS <> 'Closed' and IS_DELETE='N'  and DROP_LINE_OD='Y'");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			appendCustomerFilter(sb);
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			appendLeIDFilter(sb);
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSet(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}

	@Override
	public SearchResult getTotalListWithSegment(final DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();
		segmentName = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segmentName+"')";
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ");
		sb.append("ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, (select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT, FAM,DIARY_NUMBER,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = activity) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR  ");
		sb.append(" FROM ");
		sb.append(CMS_DIARY_ITEM);
		sb.append(" WHERE ");
		sb.append(" TEAM_ID = ?");
		sb.append(" and STATUS <> 'Closed' and IS_DELETE='N'  and CUSTOMER_SEGMENT="+segmentName+"");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			appendCustomerFilter(sb);
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			appendLeIDFilter(sb);
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSet(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}
	
		
	
	@Override
	public SearchResult getTodayGenericListWithSegment(final DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();
		segmentName = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segmentName+"')";
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ");
		sb.append("ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, (select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT, FAM,DIARY_NUMBER,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = activity) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR  ");
		sb.append(" FROM ");
		sb.append(CMS_DIARY_ITEM);
		sb.append(" WHERE ");
		sb.append(" TEAM_ID = ? and CAST(ITEM_DUE_DATE AS DATE) = to_date((select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE'),'dd-mm-yyyy')");
		sb.append(" and STATUS <> 'Closed' and IS_DELETE='N' and DROP_LINE_OD='N' and CUSTOMER_SEGMENT="+segmentName+"");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			appendCustomerFilter(sb);
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			appendLeIDFilter(sb);
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSet(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}

	@Override
	public SearchResult getTodayGenericListWithoutSegment(final DiaryItemSearchCriteria criteria) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();
		
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ");
		sb.append("ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, (select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT, FAM,DIARY_NUMBER,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = activity) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR  ");
		sb.append(" FROM ");
		sb.append(CMS_DIARY_ITEM);
		sb.append(" WHERE ");
		sb.append(" TEAM_ID = ? and CAST(ITEM_DUE_DATE AS DATE) = to_date((select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE'),'dd-mm-yyyy')");
		sb.append(" and STATUS <> 'Closed' and IS_DELETE='N'  and DROP_LINE_OD='N'");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			appendCustomerFilter(sb);
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			appendLeIDFilter(sb);
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSet(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}

	@Override
	public SearchResult getTodayDropLineListWithSegment(final DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();
		segmentName = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segmentName+"')";
		StringBuffer sb = new StringBuffer();
		
		sb.append("select item.team_id ,item.DIARY_NUMBER,item.ITEM_ID, item.LE_ID,item.CUSTOMER_NAME, item.DESCRIPTION,  ");
		sb.append("sch.ITEM_DUE_DATE,item.ITEM_EXPIRY_DATE,item.IS_OBSOLETE,(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=item.CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT,item.FAM,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = item.ACTIVITY) as ACTIVITY,item.DROP_LINE_OD,item.MAKER_ID,item.OD_UPLOAD_FILE_ERROR,sch.month ");
		sb.append(" from CMS_DIARY_SCHEDULE_DATA sch ");
		sb.append(" left outer join cms_diary_item item on ");
		sb.append(" sch.DIARY_NUMBER = item.DIARY_NUMBER and item.team_id = ?");
		sb.append(" WHERE ");
		sb.append(" sch.IS_CLOSED='N' and ");
		sb.append(" CAST(sch.ITEM_DUE_DATE AS DATE) = to_date((select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE'),'dd-mm-yyyy') ");
		sb.append("  and item.CUSTOMER_SEGMENT="+segmentName+"");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			sb.append(" AND UPPER(item.CUSTOMER_NAME) LIKE ? ");
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			sb.append(" AND item.LE_ID LIKE ? ");
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSetTodays(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}

	@Override
	public SearchResult getTodayDropLineListWithoutSegment(final DiaryItemSearchCriteria criteria) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();

		StringBuffer sb = new StringBuffer();
		sb.append("select item.team_id ,item.DIARY_NUMBER,item.ITEM_ID, item.LE_ID,item.CUSTOMER_NAME, item.DESCRIPTION,  ");
		sb.append("sch.ITEM_DUE_DATE,item.ITEM_EXPIRY_DATE,item.IS_OBSOLETE,(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=item.CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT,item.FAM, (SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = item.ACTIVITY) as ACTIVITY,item.DROP_LINE_OD,item.MAKER_ID,item.OD_UPLOAD_FILE_ERROR,sch.month  ");
		sb.append(" from CMS_DIARY_SCHEDULE_DATA sch ");
		sb.append("left outer join cms_diary_item item on");
		sb.append(" sch.DIARY_NUMBER = item.DIARY_NUMBER and item.team_id = ? ");
		sb.append(" WHERE ");
		sb.append(" sch.IS_CLOSED='N' and ");
		sb.append(" CAST(sch.ITEM_DUE_DATE AS DATE) = to_date((select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE'),'dd-mm-yyyy')");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			sb.append(" AND UPPER(item.CUSTOMER_NAME) LIKE ? ");
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			sb.append(" AND item.LE_ID LIKE ? ");
			argsList.add(criteria.getLeID() + "%");
		}
		
		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSetTodays(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}

	@Override
	public SearchResult getTodayTotalListWithSegment(final DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();

		
		StringBuffer sb = new StringBuffer();
		segmentName = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segmentName+"')";
		sb.append("select DIARY_NUMBER,ITEM_ID, LE_ID,CUSTOMER_NAME, DESCRIPTION,ITEM_DUE_DATE,ITEM_EXPIRY_DATE,");
		sb.append("IS_OBSOLETE,(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT, FAM,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = ACTIVITY) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR,'' as MONTH ");
		sb.append(" FROM ");
		sb.append(" CMS_DIARY_ITEM item ");
		sb.append(" WHERE TEAM_ID = ? ");
		sb.append(" and item.STATUS <> 'Closed' and CUSTOMER_SEGMENT="+segmentName+"");
		sb.append(" and item.IS_DELETE='N' and CAST(item.ITEM_DUE_DATE AS DATE) <= to_date((select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE'),'dd-mm-yyyy')");
		if (!DiaryItemHelper.isNull(customerIndex)) {
			sb.append(" AND UPPER(item.CUSTOMER_NAME) LIKE ? ");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			sb.append(" AND item.LE_ID LIKE ? ");
		}
		sb.append(" union all ");
		sb.append(" select DIARY_NUMBER, ITEM_ID,LE_ID,CUSTOMER_NAME,'' as DESCRIPTION,ITEM_DUE_DATE,NULL as ITEM_EXPIRY_DATE, ");
		sb.append(" '' as IS_OBSOLETE,(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT,'' as FAM, (SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = ACTIVITY) as ACTIVITY, DROP_LINE_OD, MAKER_ID,'' as OD_UPLOAD_FILE_ERROR,MONTH ");
		sb.append(" from CMS_DIARY_SCHEDULE_DATA sch ");
		sb.append(" where TEAM_ID = ? and sch.IS_CLOSED = 'N' ");
		sb.append(" and sch.ITEM_DUE_DATE <= (select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE') and CUSTOMER_SEGMENT="+segmentName+"");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		if (!DiaryItemHelper.isNull(customerIndex)) {
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			argsList.add(criteria.getLeID() + "%");
		}
		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			sb.append(" AND UPPER(sch.CUSTOMER_NAME) LIKE ? ");
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			sb.append(" AND sch.LE_ID LIKE ? ");
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSetTodays(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}
	
	public void closeODitems(long diaryNumber, String month) {
		
		DefaultLogger.debug(this, "Inside closeODitems. ");

		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("Update CMS_DIARY_SCHEDULE_DATA set IS_CLOSED ='Y' where diary_number ='"+diaryNumber+"' and month ='"+month+"'");
					DefaultLogger.debug(this, "DiaryItemJdbcImpl.java=> closeODitems() =>SQL=> Update CMS_DIARY_SCHEDULE_DATA set IS_CLOSED ='Y' where diary_number ='"+diaryNumber+"' and month ='"+month+"'");
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "data has been update into CMS_DIARY_SCHEDULE_DATA: "+noOfRecords);		
					dbUtil.commit();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in update CMS_DIARY_SCHEDULE_DATA Item:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in update CMS_DIARY_SCHEDULE_DATA Item:"+e.getMessage());
			}finally {
				try {
					finalize(dbUtil,rs);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	
		
	}
	
	public int getMaxMonthOfODScheduleDiary(long diaryNumber) {
		String sql = "select max(to_number(month)) from CMS_DIARY_SCHEDULE_DATA where diary_number = '"+diaryNumber+"'";
		int diarySeq = getJdbcTemplate().queryForInt(sql);
		return diarySeq;
		
	}
	
	@Override
	public SearchResult getTodayTotalListWithoutSegment(final DiaryItemSearchCriteria criteria) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();

		StringBuffer sb = new StringBuffer();
		
		sb.append("select DIARY_NUMBER,ITEM_ID, LE_ID,CUSTOMER_NAME, DESCRIPTION,ITEM_DUE_DATE,ITEM_EXPIRY_DATE,");
		sb.append("IS_OBSOLETE,(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT, FAM,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = ACTIVITY) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR,'' as MONTH ");
		sb.append(" FROM ");
		sb.append(" CMS_DIARY_ITEM item ");
		sb.append(" WHERE TEAM_ID = ? and ");
		sb.append(" item.STATUS <> 'Closed'");
		sb.append(" and item.IS_DELETE='N' and CAST(item.ITEM_DUE_DATE AS DATE) <= to_date((select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE'),'dd-mm-yyyy')");
		if (!DiaryItemHelper.isNull(customerIndex)) {
			sb.append(" AND UPPER(item.CUSTOMER_NAME) LIKE ? ");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			sb.append(" AND item.LE_ID LIKE ? ");
		}
		sb.append(" union all ");
		sb.append(" select DIARY_NUMBER, ITEM_ID,LE_ID,CUSTOMER_NAME,'' as DESCRIPTION,ITEM_DUE_DATE,NULL as ITEM_EXPIRY_DATE, ");
		sb.append(" '' as IS_OBSOLETE,(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT,'' as FAM, (SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = ACTIVITY) as ACTIVITY, DROP_LINE_OD, MAKER_ID,'' as OD_UPLOAD_FILE_ERROR,MONTH ");
		sb.append(" from CMS_DIARY_SCHEDULE_DATA sch ");
		sb.append(" where TEAM_ID = ? and sch.IS_CLOSED = 'N' ");
		sb.append(" and sch.ITEM_DUE_DATE <= (select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE') ");
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		if (!DiaryItemHelper.isNull(customerIndex)) {
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			argsList.add(criteria.getLeID() + "%");
		}
		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			sb.append(" AND UPPER(sch.CUSTOMER_NAME) LIKE ? ");
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			sb.append(" AND sch.LE_ID LIKE ? ");
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSetTodays(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}
	
	
	
	
	
	@Override
	public SearchResult getTodayOverDueListWithSegment(final DiaryItemSearchCriteria criteria,String segmentName) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();

		StringBuffer sb = new StringBuffer();
		segmentName = "(select entry_code from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_name='"+segmentName+"')";
		sb.append("select DIARY_NUMBER,ITEM_ID, LE_ID,CUSTOMER_NAME, DESCRIPTION,ITEM_DUE_DATE,ITEM_EXPIRY_DATE,");
		sb.append("IS_OBSOLETE,(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT, FAM,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = ACTIVITY) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR,'' as MONTH ");
		sb.append(" FROM ");
		sb.append(" CMS_DIARY_ITEM item ");
		sb.append(" WHERE TEAM_ID = ? and ");
		sb.append(" item.DROP_LINE_OD='N' and item.STATUS <> 'Closed' and CUSTOMER_SEGMENT="+segmentName+"");
		sb.append(" and item.IS_DELETE='N' and CAST(item.ITEM_DUE_DATE AS DATE) < to_date((select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE'),'dd-mm-yyyy')");
		if (!DiaryItemHelper.isNull(customerIndex)) {
			sb.append(" AND UPPER(item.CUSTOMER_NAME) LIKE ? ");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			sb.append(" AND item.LE_ID LIKE ? ");
		}
		sb.append(" union all ");
		sb.append(" select DIARY_NUMBER, ITEM_ID,LE_ID,CUSTOMER_NAME,'' as DESCRIPTION,ITEM_DUE_DATE,NULL as ITEM_EXPIRY_DATE, ");
		sb.append(" '' as IS_OBSOLETE,(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=sch.CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT,'' as FAM,'' as ACTIVITY,DROP_LINE_OD,MAKER_ID,'' as OD_UPLOAD_FILE_ERROR,MONTH ");
		sb.append(" from CMS_DIARY_SCHEDULE_DATA sch ");
		sb.append(" where TEAM_ID = ? and sch.IS_CLOSED = 'N' ");
		sb.append(" and sch.ITEM_DUE_DATE < (select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE') and CUSTOMER_SEGMENT="+segmentName+"");
		
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		if (!DiaryItemHelper.isNull(customerIndex)) {
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			argsList.add(criteria.getLeID() + "%");
		}
		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			sb.append(" AND UPPER(sch.CUSTOMER_NAME) LIKE ? ");
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			sb.append(" AND sch.LE_ID LIKE ? ");
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSetTodays(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}
	
	
	
	@Override
	public SearchResult getTodayOverDueListWithoutSegment(final DiaryItemSearchCriteria criteria) throws SearchDAOException {
		Validate.notNull(criteria, "Diary Item Search Criteria must be provided.");

		final int startIndex = criteria.getStartIndex();
		String customerIndex = criteria.getCustomerIndex();

		StringBuffer sb = new StringBuffer();
		
		sb.append("select DIARY_NUMBER,ITEM_ID, LE_ID,CUSTOMER_NAME, DESCRIPTION,ITEM_DUE_DATE,ITEM_EXPIRY_DATE,");
		sb.append("IS_OBSOLETE,(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT, FAM,(SELECT entry_name FROM COMMON_CODE_CATEGORY_ENTRY WHERE CATEGORY_CODE = 'Activity_Type' AND ACTIVE_STATUS   = 1 AND entry_code = ACTIVITY) as ACTIVITY,DROP_LINE_OD,MAKER_ID,OD_UPLOAD_FILE_ERROR,'' as MONTH ");
		sb.append(" FROM ");
		sb.append(" CMS_DIARY_ITEM item ");
		sb.append(" WHERE TEAM_ID = ? and ");
		sb.append(" item.DROP_LINE_OD='N' and item.STATUS <> 'Closed'");
		sb.append(" and item.IS_DELETE='N' and CAST(item.ITEM_DUE_DATE AS DATE) < to_date((select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE'),'dd-mm-yyyy')");
		if (!DiaryItemHelper.isNull(customerIndex)) {
			sb.append(" AND UPPER(item.CUSTOMER_NAME) LIKE ? ");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			sb.append(" AND item.LE_ID LIKE ? ");
		}
		sb.append(" union all ");
		sb.append(" select DIARY_NUMBER, ITEM_ID, LE_ID,CUSTOMER_NAME,'' as DESCRIPTION,ITEM_DUE_DATE,NULL as ITEM_EXPIRY_DATE, ");
		sb.append(" '' as IS_OBSOLETE,(select entry_name  from COMMON_CODE_CATEGORY_ENTRY where CATEGORY_CODE = 'HDFC_SEGMENT' AND ACTIVE_STATUS = 1 and entry_code=sch.CUSTOMER_SEGMENT) as CUSTOMER_SEGMENT,'' as FAM,'' as ACTIVITY, DROP_LINE_OD, MAKER_ID,'' as OD_UPLOAD_FILE_ERROR,MONTH ");
		sb.append(" from CMS_DIARY_SCHEDULE_DATA sch ");
		sb.append(" where TEAM_ID = ? and sch.IS_CLOSED = 'N' ");
		sb.append(" and sch.ITEM_DUE_DATE < (select param_value from CMS_GENERAL_PARAM where param_code = 'APPLICATION_DATE') ");
		
		
		//For Db2
//		sb.append(" AND ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9) ");
//		sb.append("  OR  ((DAYS(CAST(? AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
		//For Oracle
		/*sb.append(" AND ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) < 9) ");
		sb.append("  OR  ((TRUNC(To_Date(? ,'yyyy-mm-dd')) - TRUNC(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = ?))");
*/
		List argsList = new ArrayList();

		Date currDate = new java.sql.Date(DateUtil.getDate().getTime());

		argsList.add(new Long(criteria.getTeam().getTeamID()));
		if (!DiaryItemHelper.isNull(customerIndex)) {
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			argsList.add(criteria.getLeID() + "%");
		}
		argsList.add(new Long(criteria.getTeam().getTeamID()));
		/*argsList.add(currDate);
		argsList.add(currDate);
		argsList.add(new java.lang.String("N"));*/

		if (!DiaryItemHelper.isNull(customerIndex)) {
			sb.append(" AND UPPER(sch.CUSTOMER_NAME) LIKE ? ");
			argsList.add(customerIndex.toUpperCase().trim() + "%");
		}

		if (StringUtils.isNotBlank(criteria.getLeID())) {
			sb.append(" AND sch.LE_ID LIKE ? ");
			argsList.add(criteria.getLeID() + "%");
		}

		if (hasDateFilter(criteria.getStartExpDate(), criteria.getEndExpDate())) {
			appendDateRangeFilter(sb);
			argsList.add(criteria.getStartExpDate());
			argsList.add(criteria.getEndExpDate());
		}

		/*if (criteria.getCountryFilter() != null) {
			appendCountryFilter(sb);
			argsList.add(criteria.getCountryFilter());
		}*/

		/*sb.append(getCountryCondition(criteria.getAllowedCountries()));
		appendSortOrder(sb, customerIndex);
		if(criteria.getAllowedCountries()!=null)
		{
		for (int i = 0; i < criteria.getAllowedCountries().length; i++) {
			argsList.add(criteria.getAllowedCountries()[i]);
		}
		}*/
		sb.append(" order by 1 desc");
		String sql = sb.toString();
		PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(startIndex,
				this.recordsPerPageForPagination, this.totalPageForPagination);
/*
 *todo: Sample Query need to remove Anil  
 select pagingTemp2.* from 
(select pagingTemp1.*, row_number() over ( ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC) as row_next 
	from ( SELECT ITEM_ID, LE_ID, CUSTOMER_NAME, DESCRIPTION,  ITEM_DUE_DATE, ITEM_EXPIRY_DATE, IS_OBSOLETE, CUSTOMER_SEGMENT, FAM   
		FROM CMS_DIARY_ITEM 
		WHERE  TEAM_ID = 20110125000000002 AND ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) < 9)   
		OR  ((DAYS(CAST(2011-04-27 AS TIMESTAMP)) - DAYS(ITEM_EXPIRY_DATE) >= 9) AND IS_OBSOLETE = 'N'))
		AND ALLOWED_COUNTRY IN ('MY', 'MY', 'MY', 'IN' )
		ORDER BY ITEM_EXPIRY_DATE ASC, ITEM_DUE_DATE ASC, CUSTOMER_NAME ASC
		)  pagingTemp1
)  pagingTemp2 
where pagingTemp2.row_next between 1 and 101
 */
		return (SearchResult) getJdbcTemplate().query(this.paginationUtil.formPagingQuery(sql, pagingBean),
				argsList.toArray(), new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						List results = new ArrayList();
						int count = 0;
						while (rs.next()) {
							if (count < recordsPerPageForPagination) {
								IDiaryItemUI result = processDiaryItemResultSetTodays(rs);
								results.add(result);
							}
							count++;
						}

						if (results.isEmpty()) {
							return null;
						}

						boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;
						return new SearchResult(startIndex, results.size(), (hasTotalCountForCurrentSearch ? criteria
								.getTotalCountForCurrentTotalPages().intValue() : count + startIndex), results);
					}
				});

	}
	
	public void updateItemId(long diaryNumber,Long itemid) {

		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("Update CMS_DIARY_SCHEDULE_DATA set item_id ='"+itemid+"' where diary_number = '"+diaryNumber+"' ");
					int noOfRecords=dbUtil.executeUpdate();
					DefaultLogger.debug(this, "data has been inserted into Table: "+noOfRecords);		
					dbUtil.commit();
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in delete Diary Item:"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "Exception in delete Diary Item:"+e.getMessage());
			}finally {
				try {
					finalize(dbUtil,rs);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
	}
	

	public Long getItemid(long diaryNumber) {
		String sql = "select item_id from CMS_DIARY_ITEM where diary_number = '"+diaryNumber+"'";
		Long itemid = getJdbcTemplate().queryForLong(sql);
		return itemid;
		
	}
	
	public String getRegionName(String regionId) {
		String regionName = null;
		DBUtil dbUtil = null;
		ResultSet rs=null;
		try{
					dbUtil=new DBUtil();
					dbUtil.setSQL("SELECT region_name FROM CMS_REGION WHERE  ID='"+regionId+"'");
					rs=dbUtil.executeQuery();
					while (rs.next()){	
					regionName = rs.getString("region_name");
					}
					DefaultLogger.debug(this, "SELECT region_name FROM CMS_REGION WHERE  ID"+regionName);		
			}  catch (SQLException e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "SELECT region_name FROM CMS_REGION WHERE  ID"+e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
				DefaultLogger.debug(this, "SELECT region_name FROM CMS_REGION WHERE  ID"+e.getMessage());
			}finally {
				try {
					finalize(dbUtil,rs);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return regionName;
		
	}
	
	
	
	
}

