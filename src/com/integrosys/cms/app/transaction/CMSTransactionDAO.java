/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSTransactionDAO.java,v 1.121 2006/11/24 06:29:44 jychong Exp $
 */
package com.integrosys.cms.app.transaction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchResultSetProcessUtils;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.bus.PaginationBean;
import com.integrosys.cms.app.common.bus.PaginationBeanFactory;
import com.integrosys.cms.app.common.bus.PaginationUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.dataaccess.DAOContext;
import com.integrosys.cms.app.common.util.dataaccess.DataAccessException;
import com.integrosys.cms.app.common.util.dataaccess.IDAODescriptor;
import com.integrosys.cms.app.common.util.dataaccess.SearchingParameters;
import com.integrosys.component.notification.bus.OBNotificationRecipient;

/**
 * CMS Transaction Jdbc DAO, mainly for workflow, such as Maker/Checker. Used in
 * To Do List, To Track List, and Task Management.
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.121 $
 * @since $Date: 2006/11/24 06:29:44 $
 */
public class CMSTransactionDAO extends JdbcTemplateAdapter implements ICMSTransactionDAO {

	private static final String UNION_ALL = " UNION ALL ";

	private static final String BORROWER_SELECT_PART = "SELECT DISTINCT A.TRX_REFERENCE_ID, A.TRANSACTION_ID, A.CUR_TRX_HISTORY_ID, A.MIN_EMPLOYEE_GRADE, \n"
			+ "A.REFERENCE_ID, A.TRANSACTION_TYPE, A.TRANSACTION_SUBTYPE, A.STATUS, \n"
			+ "A.LEGAL_ID, A.LIMIT_PROFILE_ID, A.LEGAL_NAME as LNAME, A.CUSTOMER_NAME AS CNAME, \n"
			+ "A.CUSTOMER_ID, A.TRANSACTION_DATE, A.TRX_ORIGIN_COUNTRY, B.USER_STATE, B.USER_TRX_TYPE, \n"
			+ "C.LLP_BCA_REF_APPR_DATE, C.CMS_ORIG_COUNTRY, C.CMS_BCA_CREATE_DATE, D.COUNTRY_NAME, \n"
			+ "SP.LSP_ID, MP.LMP_LE_ID, SP.LSP_SHORT_NAME AS CUSTOMER_NAME, MP.LMP_LONG_NAME AS LEGAL_NAME, \n"
			+ "UPPER(MP.LMP_LONG_NAME) AS UPPER_LEGAL_NAME, A.DEAL_NO, \n";

	private static final String BORROWER_UNION_FLAG = "' ' AS TASK_FLAG ";

	private static final String BORROWER_CONDITION_PART = " FROM  SCI_LE_SUB_PROFILE SP  LEFT OUTER JOIN  SCI_LE_MAIN_PROFILE MP  \n"
			+ "ON  SP.CMS_LE_MAIN_PROFILE_ID  = MP.CMS_LE_MAIN_PROFILE_ID    \n"
			+ "RIGHT OUTER JOIN  TRANSACTION A  ON  A.CUSTOMER_ID  = SP.CMS_LE_SUB_PROFILE_ID  , \n"
			+ "CMS_TRX_TOTRACK B, \n"
			+ "SCI_LSP_LMT_PROFILE C, \n"
			+ "COUNTRY D, \n"
			+ "sci_lsp_appr_lmts L  \n"
			+ "WHERE	L.CMS_LIMIT_PROFILE_ID  = C.CMS_LSP_LMT_PROFILE_ID \n"
			+ "	 AND	L.CMS_LIMIT_STATUS  != 'DELETED' \n"
			+ "	 AND	A.TRANSACTION_TYPE  = B.TRANSACTION_TYPE \n"
			+ "	 AND	A.STATUS  = B.CURR_STATE \n"
			+ "	 AND	(A.FROM_STATE  = B.FROM_STATE OR B.FROM_STATE  IS NULL) \n"
			+ "	 AND	(A.TRANSACTION_SUBTYPE  = B.TRANSACTION_SUBTYPE \n"
			+ "     OR (A.TRANSACTION_SUBTYPE  IS NULL AND B.TRANSACTION_SUBTYPE IS NULL)) \n"
			+ "	 AND	A.LIMIT_PROFILE_ID  = C.CMS_LSP_LMT_PROFILE_ID \n"
			+ "	 AND	C.CMS_ORIG_COUNTRY  = D.COUNTRY_ISO_CODE \n"
			+ "	 AND	C.CMS_BCA_COMPLETE_IND  != '"
			+ ICMSConstant.TRUE_VALUE + "' \n";

	private static final String BORROWER_COL_TASK_SQL = BORROWER_SELECT_PART + "'S' AS TASK_FLAG "
			+ BORROWER_CONDITION_PART + " AND A.LIMIT_PROFILE_ID IN ( " + "SELECT LIMIT_PROFILE_ID "
			+ "FROM TRANSACTION aa, CMS_COLLATERAL_TASK cc " + "WHERE aa.TRANSACTION_TYPE = '"
			+ ICMSConstant.INSTANCE_COLLATERAL_TASK + "'" + " AND aa.REFERENCE_ID = cc.TASK_ID ";

	private static final String BORROWER_CC_TASK_SQL = BORROWER_SELECT_PART + "'C' AS TASK_FLAG "
			+ BORROWER_CONDITION_PART + " AND A.LIMIT_PROFILE_ID IN ( " + "SELECT LIMIT_PROFILE_ID "
			+ "FROM TRANSACTION aa, CMS_CC_TASK cc " + "WHERE aa.TRANSACTION_TYPE = '" + ICMSConstant.INSTANCE_CC_TASK
			+ "'" + " AND aa.REFERENCE_ID = cc.TASK_ID ";

	private static final String BORROWER_LIMIT_SQL = BORROWER_SELECT_PART + "'L' AS TASK_FLAG "
			+ BORROWER_CONDITION_PART + " AND A.LIMIT_PROFILE_ID IN ( " + "SELECT LIMIT_PROFILE_ID "
			+ "FROM TRANSACTION aa, SCI_LSP_APPR_LMTS lt " + "WHERE aa.TRANSACTION_TYPE = '"
			+ ICMSConstant.INSTANCE_LIMIT + "'" + " AND aa.REFERENCE_ID = lt.CMS_LSP_APPR_LMTS_ID ";

	private static final String NONBORROWER_SQL = "SELECT A.TRX_REFERENCE_ID, A.TRANSACTION_ID, A.CUR_TRX_HISTORY_ID, A.REFERENCE_ID, A.TRANSACTION_TYPE, A.TRANSACTION_SUBTYPE, A.STATUS, A.MIN_EMPLOYEE_GRADE, "
			+ " A.LEGAL_ID, A.LIMIT_PROFILE_ID, A.LEGAL_NAME AS LNAME, A.CUSTOMER_NAME AS CNAME, A.CUSTOMER_ID, A.TRANSACTION_DATE,"
			+ " A.TRX_ORIGIN_COUNTRY, ' ' AS USER_STATE, ' ' AS USER_TRX_TYPE, cast(null as timestamp) AS LLP_BCA_REF_APPR_DATE, SP.CMS_SUB_ORIG_COUNTRY AS CMS_ORIG_COUNTRY,"
			+ " A.TRANSACTION_DATE AS CMS_BCA_CREATE_DATE, D.COUNTRY_NAME, "
			+ " SP.LSP_ID, MP.LMP_LE_ID, SP.LSP_SHORT_NAME AS CUSTOMER_NAME, MP.LMP_LONG_NAME AS LEGAL_NAME, UPPER(MP.LMP_LONG_NAME) AS UPPER_LEGAL_NAME, A.DEAL_NO, ' ' AS TASK_FLAG "
			+ "FROM TRANSACTION A, COUNTRY D, SCI_LE_SUB_PROFILE SP, SCI_LE_MAIN_PROFILE MP "
			+ "WHERE SP.CMS_LE_MAIN_PROFILE_ID = MP.CMS_LE_MAIN_PROFILE_ID"
			+ " AND A.CUSTOMER_ID = SP.CMS_LE_SUB_PROFILE_ID AND A.REFERENCE_ID = SP.CMS_LE_SUB_PROFILE_ID"
			+ " AND SP.CMS_SUB_ORIG_COUNTRY = D.COUNTRY_ISO_CODE AND A.TRANSACTION_TYPE = '"
			+ ICMSConstant.INSTANCE_CUSTOMER
			+ "'"
			+ " AND SP.CMS_NON_BORROWER_IND = 'Y'"
			+ " AND (SP.UPDATE_STATUS_IND IS NULL OR SP.UPDATE_STATUS_IND <> 'D') ";

	private static final String TRX_HISTORY_LOG_SQL = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY TR_HISTORY_ID DESC) RNO, RESULT.* "
			+ "FROM (SELECT HIST.TR_HISTORY_ID, HIST.TRANSACTION_ID, "
			+ "(SELECT USER_NAME FROM CMS_USER WHERE USER_ID=HIST.USER_ID) AS USER_NAME, "
			+ "TS_TO_CHAR(HIST.TRANSACTION_DATE,'dd/mm/yyyy hh24:mi') LOG_DATE, "
			+ "TS_TO_CHAR(HIST.creation_date,'dd/mm/yyyy') TRANSACTION_DATE, "
			+ "(SELECT ABBREVIATION FROM CMS_TEAM WHERE TEAM_ID=HIST.TEAM_ID) AS TEAM_NAME, "
			+ "HIST.REMARKS, HIST.TO_USER_ID, HIST.TO_GROUP_ID, "
			+ "HIST.TEAM_TYPE_ID, HIST.TO_GROUP_TYPE_ID "
			+ "FROM TRANS_HISTORY HIST, TRANSACTION TRANS "
			+ "WHERE TRANS.TRANSACTION_ID= ? "
			+ "AND HIST.TRANSACTION_ID=TRANS.TRANSACTION_ID " + "ORDER BY HIST.TR_HISTORY_ID DESC) RESULT) SRESULT ";

	// ////////////////////////////////////////////////////////////////
	private static final String TODO_SELECT = "SELECT row_number() over(partition by a.transaction_id order by a.transaction_id) rnum, "
			+ "		  a.trx_reference_id, a.transaction_id, a.cur_trx_history_id, a.MIN_EMPLOYEE_GRADE,"
			+ "       a.reference_id, a.transaction_type, a.transaction_subtype, a.status,"
			+ "       a.legal_id, a.limit_profile_id, a.limit_profile_ref_num, a.legal_name AS lname,"
			+ "       a.customer_name AS cname, a.customer_id, a.transaction_date,"
			+ "       a.trx_origin_country, a.user_info, b.user_action, b.url, b.totrack_url,"
			+ "       f2.user_state, f2.user_trx_type, f.stateid,"
			+ "	      (SELECT country_name FROM country WHERE country_iso_code = a.trx_origin_country) country_name,"
			+ "	      sp.lsp_id, sp.lsp_le_id, sp.lsp_short_name AS customer_name,"
			+ "       sp.lsp_short_name  AS legal_name,"
			+ "       UPPER (sp.lsp_short_name ) AS upper_legal_name, ' ' AS task_flag, a.deal_no";

	// shared condition for both borrower and non-borrower
	private static final String TODO_CONDITION = " FROM CMS_STATEMATRIX_ACTION b, "
			+ "   TR_STATE_MATRIX f, "
			+ "   CMS_TRX_TOTRACK f2, "
			+ "   CMS_TEAM c, "
			+ "   CMS_TEAM_TYPE_MEMBERSHIP e, "
			+ "   TRANSACTION a LEFT OUTER JOIN  "
			+ "   SCI_LE_SUB_PROFILE sp ON sp.CMS_LE_SUB_PROFILE_ID = a.CUSTOMER_ID LEFT OUTER JOIN  "
			+ "   SCI_LSP_LMT_PROFILE lmt ON lmt.CMS_LSP_LMT_PROFILE_ID = a.LIMIT_PROFILE_ID " 
			//+ "LEFT OUTER JOIN  "
			//+ "   SCI_LE_MAIN_PROFILE mp ON mp.CMS_LE_MAIN_PROFILE_ID = sp.CMS_LE_MAIN_PROFILE_ID "
			+ "WHERE b.TEAM_MEMBERSHIP_TYPE_ID = ? "
			// + "      AND ((a.TRANSACTION_TYPE IN ('COL') "
			// +
			// "            AND (a.TRX_REFERENCE_ID IN (DECIMAL(a.TRANSACTION_ID, 19), -999999999)) "
			// + "            OR (a.TRANSACTION_TYPE NOT IN ('COL')))) "
			+ "      AND a.TRANSACTION_TYPE = f2.TRANSACTION_TYPE "
			+ "      AND a.TRANSACTION_TYPE NOT IN ('DISCREPENCY') "
			+ "      AND a.STATUS = f2.CURR_STATE "
			+ "      AND (a.FROM_STATE = f2.FROM_STATE OR f2.FROM_STATE IS NULL) "
			+ "      AND a.TRANSACTION_TYPE = f.STATEINS "
			+ "      AND (a.TRANSACTION_SUBTYPE = f2.TRANSACTION_SUBTYPE OR (a.TRANSACTION_SUBTYPE IS NULL AND f2.TRANSACTION_SUBTYPE IS NULL)) "
			+ "      AND f.FROMSTATE = a.STATUS " + "      AND f.STATEID = b.STATE_ID "
			+ "      AND a.TEAM_ID = c.TEAM_ID " + "      AND b.TEAM_MEMBERSHIP_TYPE_ID = e.TEAM_TYPE_MEMBERSHIP_ID "
			+ "      AND e.TEAM_TYPE_ID = c.TEAM_TYPE_ID " + "		 AND ((a.USER_ID <> ? AND a.team_membership_id <> ?)"
			+ "      OR  (a.team_membership_id = ?))";

	private static final String TODO_CONDITION_BORROWER = "   AND (sp.cms_non_borrower_ind = 'N' OR sp.cms_non_borrower_ind IS NULL)";

	private static final String TODO_CONDITION_NONBORROWER = "   AND sp.cms_non_borrower_ind = 'Y'";

	private static final String TODO_CONDITION_MULTILEVEL = " FROM CMS_STATEMATRIX_ACTION b, "
			+ "   TR_STATE_MATRIX f, "
			+ "   CMS_TRX_TOTRACK f2, "
			+ "   TRANSACTION a LEFT OUTER JOIN  "
			+ "   SCI_LE_SUB_PROFILE sp ON sp.CMS_LE_SUB_PROFILE_ID = a.CUSTOMER_ID LEFT OUTER JOIN  "
			+ "   SCI_LSP_LMT_PROFILE lmt ON lmt.CMS_LSP_LMT_PROFILE_ID = a.LIMIT_PROFILE_ID LEFT OUTER JOIN  "
			+ "   SCI_LE_MAIN_PROFILE mp ON mp.CMS_LE_MAIN_PROFILE_ID = sp.CMS_LE_MAIN_PROFILE_ID "
			+ "WHERE b.TEAM_MEMBERSHIP_TYPE_ID = ? "
			+ "      AND a.TRANSACTION_TYPE IN ('DEFER_REQ','WAIVER_REQ','CHECKLIST','COMMODITY_DEAL') "
			+ "      AND a.TRANSACTION_TYPE = f2.TRANSACTION_TYPE "
			+ "      AND a.STATUS = f2.CURR_STATE "
			+ "      AND (a.FROM_STATE = f2.FROM_STATE OR f2.FROM_STATE IS NULL) "
			+ "      AND a.TRANSACTION_TYPE = f.STATEINS "
			+ "      AND (a.TRANSACTION_SUBTYPE = f2.TRANSACTION_SUBTYPE OR (a.TRANSACTION_SUBTYPE IS NULL AND f2.TRANSACTION_SUBTYPE IS NULL)) "
			+ "      AND f.FROMSTATE = a.STATUS " + "      AND f.STATEID = b.STATE_ID ";

	private static final String PENDING_CASES_SQL = "select pf.cms_lsp_lmt_profile_id, pf.cms_customer_id, "
			+ " (select sp.LSP_SHORT_NAME " + " from sci_le_sub_profile sp "
			+ " where sp.cms_le_sub_profile_id = pf.cms_customer_id) lsp_short_name, "
			+ " pf.llp_le_id, pf.llp_segment_code_value segment_code, "
			+ " (select entry_name from common_code_category_entry "
			+ " where category_code = pf.llp_segment_code_num "
			+ " and entry_code = pf.llp_segment_code_value) segment_value, "
			+ " pf.llp_bca_ref_num, pf.llp_bca_ref_appr_date, pf.source_id, trx.status, "
			+ " (select entry_name from common_code_category_entry " + " where category_code = '37' "
			+ " and entry_code = pf.source_id) source_name, "
			+ " pf.cms_orig_organisation org_code, (select distinct entry_name "
			+ " from common_code_category_entry	where category_code = '40' "
			+ " and entry_code = pf.CMS_ORIG_ORGANISATION " + " and entry_source = pf.source_id) org_name "
			+ " from sci_lsp_lmt_profile pf, transaction trx, security_pending_cases_view sec_view "
			+ " where trx.transaction_type = 'LIMITPROFILE' " + " and trx.reference_id = pf.cms_lsp_lmt_profile_id "
			+ " and pf.cms_lsp_lmt_profile_id = sec_view.cms_lsp_lmt_profile_id "
			+ " and pf.cms_bca_status <> 'DELETED' ";

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
	 * Default Constructor
	 */
	public CMSTransactionDAO() {
	}

	/**
	 * Gets the transaction history logs by transaction id and number of rows
	 * @param trxID - transaction id
	 * @param maxRow - number of rows return, if ICMSConstant.INT_INVALID_VALUE
	 *        show all the records
	 * @return Collection
	 * @throws SearchDAOException
	 */
	public Collection getTransactionLogs(String trxID, int maxRow) throws SearchDAOException {
		String theSql = TRX_HISTORY_LOG_SQL;

		if (maxRow != ICMSConstant.INT_INVALID_VALUE) {
			theSql += "WHERE SRESULT.RNO < ? ";
		}

		theSql += "ORDER BY SRESULT.TRANSACTION_DATE DESC, SRESULT.TR_HISTORY_ID DESC";

		List paramList = new ArrayList();
		paramList.add(trxID);
		if (maxRow != ICMSConstant.INT_INVALID_VALUE) {
			paramList.add(new Integer(maxRow));
		}

		return getJdbcTemplate().query(theSql, paramList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBCMSTrxHistoryLog historyLog = new OBCMSTrxHistoryLog();
				historyLog.setLogDate(rs.getString("LOG_DATE"));
				historyLog.setLogUserName(rs.getString("USER_NAME"));
				historyLog.setLogGroupName(rs.getString("TEAM_NAME"));
				historyLog.setComment(rs.getString("REMARKS"));

				return historyLog;
			}
		});

	}

	/**
	 * gets the count of to do transactions
	 * @param criteria
	 * @return int
	 * @throws SearchDAOException
	 */
	public int getTransactionCount(CMSTrxSearchCriteria criteria) throws SearchDAOException {

		List paramList = new ArrayList();

		DefaultLogger.info(this, "IN method getTransactionCount");

		if (ICMSConstant.TODO_ACTION.equals(criteria.getSearchIndicator())) {
			if (criteria.isOnlyMembershipRecords()) {
				String countSql = this.getQuery(criteria, paramList, true);
				return this.getCountFromQuery(countSql, paramList);
			}
			else {
				StringBuffer queryBuf = new StringBuffer("SELECT COUNT(*) FROM TRANSACTION A WHERE 1 = 1 ");

				if ((criteria.getTransactionTypes() != null) && (criteria.getTransactionTypes().length > 0)) {
					queryBuf.append(" AND A.TRANSACTION_TYPE IN (");
					int trxTypeCount = criteria.getTransactionTypes().length;
					for (int i = 0; i < trxTypeCount; i++) {
						String s = criteria.getTransactionTypes()[i];
						queryBuf.append("'").append(s).append("'");
						if (i != criteria.getTransactionTypes().length - 1) {
							queryBuf.append(", ");
						}
					}
					queryBuf.append(") ");
					if ((trxTypeCount == 1)
							&& ICMSConstant.INSTANCE_LIMIT_PROFILE.equals(criteria.getTransactionTypes()[0])) {
						queryBuf.append(" AND A.REFERENCE_ID = C.CMS_LSP_LMT_PROFILE_ID ");
					}
				}

				if (criteria.getCurrentState() != null) {
					queryBuf.append(" AND A.STATUS ");
					if (criteria.isCurrentState()) {
						queryBuf.append(" = ");
					}
					else {
						queryBuf.append(" != ");
					}
					queryBuf.append("'").append(criteria.getCurrentState()).append("'");
				}
				return this.getCountFromQuery(queryBuf.toString(), null);

			}
		}
		else if (ICMSConstant.TOTRACK_ACTION.equals(criteria.getSearchIndicator())) {
			String toTrackQueryCountSql = this.getTotrackQuery(criteria, paramList, true);
			return this.getCountFromQuery(toTrackQueryCountSql, paramList);

		}
		return 0;
	}

	/**
	 * Retrieves the current workflow transactions
	 * 
	 * @param criteria
	 * @return SearchResult
	 * @throws SearchDAOException
	 */
	public SearchResult searchWorkflowTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException {

		DefaultLogger.info("CMSTransactionDAO.searchWorkflowTransactions", "IN method searchWorkflowTransactions");

		long start = System.currentTimeMillis();
		int numTotalRecords = 0;

		List paramList = new ArrayList();

		String theSQL = getWorkflowTrxQuery(criteria, paramList, false);

		PaginationBean pgBean = new PaginationBean(criteria.getStartIndex() + 1, criteria.getStartIndex() + 10);

		try {
			if (criteria.getNItems() < 0) {
				numTotalRecords = getJdbcTemplate().queryForInt(this.paginationUtil.formCountQuery(theSQL),
						paramList.toArray());
			}
			else {
				numTotalRecords = criteria.getNItems();
			}

			Collection result = (Collection) getJdbcTemplate().query(
					this.paginationUtil.formPagingQuery(theSQL, pgBean), paramList.toArray(), new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException,
								org.springframework.dao.DataAccessException {
							return processWorkflowResultSet(rs, false, true);
						}

					});

			return new SearchResult(criteria.getStartIndex(), 0, numTotalRecords, result);

		}
		finally {
			time(start, "searchWorkflowTransactions");
		}
	}

	public Collection searchNextRouteList(CMSTrxSearchCriteria criteria) throws SearchDAOException {
		DAOContext daoContext = null;
		try {
			criteria.setSearchIndicator(IDAODescriptor.NextRouteListTAG);
			CMSTrxSearchCriteriaParameterizer parizer = new CMSTrxSearchCriteriaParameterizer();
			SearchingParameters searching = parizer.map(criteria);
			CMSTransactionExtDAO extDAO = new CMSTransactionExtDAO();
			daoContext = new DAOContext();
			Collection retcol = extDAO.searchNextRouteList(daoContext, searching);
			return retcol;

		}
		catch (DataAccessException e) {
			throw new SearchDAOException("Failed on Searching Transaction", e.fillInStackTrace());
		}
		finally {
			if (daoContext != null) {
				daoContext.close();
			}
		}

	}

	/**
	 * 
	 * @param transactionId the Transaction ID
	 * @return Collection of Transaction Logs
	 * @throws SearchDAOException
	 */
	public Collection getTransactionLogs(String transactionId) throws SearchDAOException {
		DAOContext daoContext = null;
		try {
			SearchingParameters searching = new SearchingParameters();
			searching.put(IDAODescriptor.QUERYTAG, "TransactionLog");
			searching.put(IDAODescriptor.TRANSACTION_ID, transactionId);
			CMSTransactionExtDAO extDAO = new CMSTransactionExtDAO();
			daoContext = new DAOContext();
			Collection retcol = extDAO.getTransactionLogs(daoContext, searching);
			return retcol;
		}
		catch (DataAccessException e) {
			throw new SearchDAOException("Failed on Searching Transaction", e.fillInStackTrace());
		}
		finally {
			if (daoContext != null) {
				daoContext.close();
			}
		}

	}

	public Collection getTransactionLogs(long transactionId) throws SearchDAOException {
		return this.getTransactionLogs(Long.toString(transactionId));
	}

	public SearchResult searchTransactions(CMSTrxSearchCriteria criteria) throws SearchDAOException {
		if (ICMSConstant.TODO_ACTION.equals(criteria.getSearchIndicator())) {
			if (!criteria.isOnlyMembershipRecords()) {
				return searchAllTransactions(criteria);
			}
			return searchTodoTransactions(criteria);
		}
		else if (ICMSConstant.TOTRACK_ACTION.equals(criteria.getSearchIndicator())) {
			return searchTotrackTransactions(criteria);
		}

		return new SearchResult(0, 0, 0, new ArrayList());
	}

	public SearchResult searchPendingCases(final CMSTrxSearchCriteria criteria) throws SearchDAOException {
		DefaultLogger.debug(this, "<<<<< In method searchPendingCases... ");

		long start = System.currentTimeMillis();

		List paramList = new ArrayList();

		StringBuffer sqlBuf = new StringBuffer();
		if (ICMSConstant.CREDIT_FOLDER.equals(criteria.getPendingTask())) {
			sqlBuf.append("SELECT cms_lsp_lmt_profile_id ");
			sqlBuf.append("FROM cms_aa_pending_perfection ppcf");
			sqlBuf.append(CMSTransactionDAOQueryHelper.getPendingPerfectCreditFolderCondition(criteria, paramList));
			sqlBuf.append(" ORDER BY cms_lsp_lmt_profile_id DESC ");
		}
		else {
			sqlBuf.append(getPendingCasesSQL(criteria, paramList));
		}

		if (StringUtils.isBlank(sqlBuf.toString())) {
			return new SearchResult(0, 0, 0, new ArrayList());
		}

		try {
			PaginationBean pagingBean = PaginationBeanFactory.buildPaginationBean(criteria,
					this.recordsPerPageForPagination, this.totalPageForPagination);

			List cmsLimitProfileIdList = getJdbcTemplate().query(
					this.paginationUtil.formPagingQuery(sqlBuf.toString(), pagingBean), paramList.toArray(),
					new RowMapper() {

						public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
							return new Long(rs.getLong("cms_lsp_lmt_profile_id"));
						}

					});
			if (cmsLimitProfileIdList.isEmpty()) {
				return emptySearchResult();
			}

			StringBuffer buf = new StringBuffer();
			buf.append("SELECT lp.cms_lsp_lmt_profile_id, cms_customer_id, ");
			buf.append("(SELECT sp.lsp_short_name FROM sci_le_sub_profile sp ");
			buf.append("WHERE sp.cms_le_sub_profile_id = lp.cms_customer_id) lsp_short_name, ");
			buf.append("llp_le_id, los_bca_ref_num, llp_bca_ref_num, source_id,  ");
			buf.append("(SELECT entry_name FROM common_code_category_entry ");
			buf.append("WHERE category_code = ? AND entry_code = lp.source_id) source_name, ");
			buf.append("cms_orig_organisation org_code, ");
			buf.append("(SELECT DISTINCT entry_name from common_code_category_entry ");
			buf.append("WHERE category_code = ? AND entry_code = lp.cms_orig_organisation ) org_name, ");
			buf.append("CASE WHEN file_from_biz_center_date IS NOT NULL ");
			buf.append("THEN 'IN PROGRESS' ELSE 'NEW' END AS status, ");
			buf.append("llp_bca_ref_appr_date FROM sci_lsp_lmt_profile lp ");
			buf.append("LEFT OUTER JOIN cms_tat_document tat ");
			buf.append("ON lp.cms_lsp_lmt_profile_id = tat.cms_lsp_lmt_profile_id ");
			buf.append("WHERE lp.cms_lsp_lmt_profile_id = ? ");

			List resultList = new ArrayList();
			int count = 0;
			for (Iterator itr = cmsLimitProfileIdList.iterator(); itr.hasNext();) {
				Long cmsLimitProfileId = (Long) itr.next();
				if (count < this.recordsPerPageForPagination) {
					OBPendingCasesSearchResult result = (OBPendingCasesSearchResult) getJdbcTemplate().query(
							buf.toString(), new Object[] { "37", "40", cmsLimitProfileId }, new ResultSetExtractor() {

								public Object extractData(ResultSet rs) throws SQLException,
										org.springframework.dao.DataAccessException {
									rs.next();
									return processPendingCasesResultSet(rs);
								}
							});

					resultList.add(result);
				}
				count++;
			}

			boolean hasTotalCountForCurrentSearch = criteria.getTotalCountForCurrentTotalPages() != null;

			return new SearchResult(criteria.getStartIndex(), resultList.size(),
					(hasTotalCountForCurrentSearch ? criteria.getTotalCountForCurrentTotalPages().intValue() : count
							+ criteria.getStartIndex()), resultList);

		}
		finally {
			time(start, "searchPendingCases");
		}
	}

	private OBPendingCasesSearchResult processPendingCasesResultSet(ResultSet rs) throws SQLException {
		OBPendingCasesSearchResult sr = new OBPendingCasesSearchResult();
		sr.setLimitProfileID(rs.getLong("CMS_LSP_LMT_PROFILE_ID"));
		sr.setCustomerID(rs.getLong("CMS_CUSTOMER_ID"));
		sr.setCustomerName(rs.getString("LSP_SHORT_NAME"));
		sr.setLeID(rs.getString("LLP_LE_ID"));
		// sr.setCmsSegmentCode(rs.getString("SEGMENT_CODE"));
		// sr.setCmsSegmentValue(rs.getString("SEGMENT_VALUE"));
		sr.setLosBcaRefNum(rs.getString("LOS_BCA_REF_NUM"));
		sr.setBcaRefNum(rs.getString("LLP_BCA_REF_NUM"));
		sr.setSourceID(rs.getString("SOURCE_ID"));
		sr.setSourceName(rs.getString("SOURCE_NAME"));
		sr.setOrgCode(rs.getString("ORG_CODE"));
		sr.setOrgName(rs.getString("ORG_NAME"));
		sr.setTrxStatus(rs.getString("STATUS"));
		Timestamp ts = rs.getTimestamp("LLP_BCA_REF_APPR_DATE");
		sr.setBcaApprovedDate((ts == null) ? null : new Date(ts.getTime()));

		return sr;
	}

	private String getPendingCasesSQL(CMSTrxSearchCriteria criteria, List paramList) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(PENDING_CASES_SQL);
		if (criteria.getPendingTask() != null) {

			DefaultLogger.debug(this, "<<<<<<<<<< pendingTask: " + criteria.getPendingTask());
			if (ICMSConstant.EXISTING_CASES.equals(criteria.getPendingTask())) {
				String existingSql = " and (pf.is_migrated_ind = ? "
						+ " and sec_view.sec_count = sec_view.sec_migrated_count "
						+ " and sec_view.sec_count > sec_view.sec_perfected_count) ";

				buffer.append(existingSql);
				paramList.add(ICMSConstant.TRUE_VALUE);
			}
			else if (ICMSConstant.NEW_CASES.equals(criteria.getPendingTask())) {
				String newSql = "and (pf.is_migrated_ind = ? "
						+ " or (sec_view.sec_count > sec_view.sec_migrated_count "
						+ " and sec_view.sec_count > sec_view.sec_perfected_count)) ";
				buffer.append(newSql);
				paramList.add(ICMSConstant.FALSE_VALUE);
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}

		buffer.append(CMSTransactionDAOQueryHelper.getPendingCasesCondition(criteria, paramList));
		buffer.append(CMSTransactionDAOQueryHelper.getPendingCasesSortOrder(criteria));

		return buffer.toString();
	}

	public int getAllTransactionCount(CMSTrxSearchCriteria criteria) throws SearchDAOException {
		List paramList = new ArrayList();
		String sql = getAllTrxQuery(criteria, paramList, true);

		return getCountFromQuery(sql, paramList);
	}

	public int getWorkflowTrxCount(CMSTrxSearchCriteria criteria) throws SearchDAOException {
		List paramList = new ArrayList();
		String sql = getWorkflowTrxQuery(criteria, new ArrayList(), true);

		return getCountFromQuery(sql, paramList);
	}

	public SearchResult searchAllTransactions(final CMSTrxSearchCriteria criteria) throws SearchDAOException {

		List paramList = new ArrayList();

		DefaultLogger.info(this, "IN method searchAllTransactions");
		long start = System.currentTimeMillis();

		String theSQL = getAllTrxQuery(criteria, paramList, false);
		try {

			Collection result = (Collection) getJdbcTemplate().query(theSQL, paramList.toArray(),
					new ResultSetExtractor() {

						public Object extractData(ResultSet rs) throws SQLException,
								org.springframework.dao.DataAccessException {
							return processTrxResultSet(criteria, rs, false, true);
						}

					});

			if (result.size() == 0) {
				return emptySearchResult();
			}

			return new SearchResult(criteria.getStartIndex(), 0, result.size() + criteria.getStartIndex(), result);
		}
		finally {
			time(start, "searchAllTransactions");
		}

	}

	public Collection getLPTodoList() throws SearchDAOException {
		String query = "SELECT ACTION_ID, ACTION_NAME, PROCESS_URL, VIEW_URL, PROCESS_MEMBERSHIP_TYPE_ID FROM CMS_LP_TODO";

		return getJdbcTemplate().query(query, new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				OBLPTodoObject todoOb = new OBLPTodoObject();
				todoOb.setActionName(rs.getString("ACTION_NAME"));
				todoOb.setProcessURL(rs.getString("PROCESS_URL"));
				todoOb.setViewURL(rs.getString("VIEW_URL"));
				todoOb.setProcessMembershipID(rs.getLong("PROCESS_MEMBERSHIP_TYPE_ID"));
				todoOb.setActionID(rs.getString("ACTION_ID"));

				return todoOb;
			}

		});
	}

	public String getTrxSubTypeByTrxID(long transactionID) throws SearchDAOException {

		String query = "SELECT TRANSACTION_SUBTYPE FROM TRANSACTION WHERE TRANSACTION_ID = ?";

		return (String) getJdbcTemplate().query(query, new Object[] { new Long(transactionID) },
				new ResultSetExtractor() {

					public Object extractData(ResultSet rs) throws SQLException,
							org.springframework.dao.DataAccessException {
						String trxSubType = "";

						while (rs.next()) {
							trxSubType = rs.getString("TRANSACTION_SUBTYPE");
						}

						return trxSubType;
					}

				});
	}

	public List retrieveListOfTransactionIdsByReferenceIdsAndType(List referenceIdList, String transactionType) {
		if (referenceIdList == null || referenceIdList.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		List argList = new ArrayList();
		StringBuffer buf = new StringBuffer("SELECT transaction_id FROM transaction WHERE reference_id ");
		CommonUtil.buildSQLInList(referenceIdList, buf, argList);
		buf.append(" AND transaction_type = ?");
		argList.add(transactionType);

		return getJdbcTemplate().query(buf.toString(), argList.toArray(), new RowMapper() {

			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("transaction_id");
			}
		});
	}

	private SearchResult searchTodoTransactions(final CMSTrxSearchCriteria criteria) throws SearchDAOException {

		DefaultLogger.info(this, "IN method searchTodoTransactions");

		long start = System.currentTimeMillis();

		List paramList = new ArrayList();

		String sql = this.getQuery(criteria, paramList, false);
		System.out.println("CMSTransactionDAO.java=>SearchResult searchTodoTransactions()=>sql=>"+sql);
		System.out.println("CMSTransactionDAO.java=>SearchResult searchTodoTransactions()=>paramList=>"+paramList);
		try {
			return (SearchResult) getJdbcTemplate().query(sql, paramList.toArray(), new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException,
						org.springframework.dao.DataAccessException {
					return processTrxResultSet(criteria, rs, true, false);
				}
			});
		}
		finally {
			time(start, "searchTodoTransactions");
		}
	}

	private SearchResult processTrxResultSet(CMSTrxSearchCriteria criteria, ResultSet rs, final boolean isWithURLs,
			final boolean isAllQuery) throws SQLException {

		final boolean isTodoQuery = ICMSConstant.TODO_ACTION.equals(criteria.getSearchIndicator());

		SearchResult result = SearchResultSetProcessUtils.processResultSet(criteria, rs,
				new SearchResultSetProcessUtils.ResultSetProcessAction() {

					public Object doInResultSet(ResultSet rs) throws SQLException {
						OBCMSTrxSearchResult sr = new OBCMSTrxSearchResult();
						sr.setTransactionID(rs.getString("TRANSACTION_ID"));
						sr.setReferenceID(rs.getString("REFERENCE_ID"));
						sr.setTransactionType(rs.getString("TRANSACTION_TYPE"));
						
						if(sr.getTransactionType().equalsIgnoreCase("COL"))
							sr.setCustomerName(rs.getString("CNAME"));
						else
							sr.setCustomerName(rs.getString("CUSTOMER_NAME"));
						
						if (sr.getCustomerName() == null) {
							sr.setCustomerName(rs.getString("CNAME"));
						}
						if(sr.getTransactionType().equalsIgnoreCase("COL")){
						sr.setLegalName(rs.getString("LNAME"));
						if (sr.getLegalName() == null) {
							sr.setLegalName(rs.getString("LEGAL_NAME"));
						}
						}
						else
							sr.setLegalName(rs.getString("LNAME"));
						sr.setLeID(rs.getString("LEGAL_ID"));
						sr.setOriginatingLocation(rs.getString("COUNTRY_NAME"));
						sr.setStatus(rs.getString("STATUS"));
						sr.setSubProfileID(rs.getString("CUSTOMER_ID"));
						sr.setTransactionDate(rs.getDate("TRANSACTION_DATE"));
						
						sr.setTransactionSubType(rs.getString("TRANSACTION_SUBTYPE"));
						sr.setLimitProfileID(rs.getString("LIMIT_PROFILE_ID"));
						sr.setLimitProfileReferenceNumber(rs.getString("LIMIT_PROFILE_REF_NUM"));
						sr.setUserState(rs.getString("USER_STATE"));
						sr.setTrxHistoryID(rs.getString("CUR_TRX_HISTORY_ID"));
						sr.setUserTransactionType(rs.getString("USER_TRX_TYPE"));
						sr.setDealNo(rs.getString("DEAL_NO"));
						sr.setSciLegalID(rs.getString("LSP_LE_ID"));
						sr.setSciSubprofileID(rs.getLong("LSP_ID"));
						sr.setActionUrls(new HashMap());
						sr.setTotrackActionUrls(new HashMap());
						sr.setFam("-");
						sr.setUserInfo(rs.getString("USER_INFO"));

						if (isAllQuery) {
							sr.setSciApprovedDate(rs.getDate("LLP_BCA_REF_APPR_DATE"));
							sr.setTransactionDate(rs.getDate("CMS_BCA_CREATE_DATE"));
						}

						if (!isTodoQuery) {
							sr.getTotrackActionUrls().put("View", rs.getString("TOTRACK_URL"));
						}

						String taskFlag = rs.getString("TASK_FLAG");
						if ("S".equals(taskFlag)) {
							sr.setSecColTask(true);
						}
						else if ("C".equals(taskFlag)) {
							sr.setCCColTask(true);
						}
						else if ("L".equals(taskFlag)) {
							sr.setLimitTask(true);
						}
						else {
							sr.setMainTask(true);
						}
						
						try {
							sr.setMinEmployeeGradeLoa(rs.getString("MIN_EMPLOYEE_GRADE"));
						}
						catch (SQLException e) {
							e.printStackTrace();
						}
						

						return sr;
					}
				});

		if (isTodoQuery && isWithURLs && result != null && result.getResultList() != null
				&& !result.getResultList().isEmpty()) {
			for (Iterator itr = result.getResultList().iterator(); itr.hasNext();) {
				final OBCMSTrxSearchResult sr = (OBCMSTrxSearchResult) itr.next();
				String status = sr.getStatus();
				String transactionType = sr.getTransactionType();

                //Andy Wong, 27 April 2010: fix query by including additional condition: team_membership_type_id
                // causing wrong url to be returned when same fromstate have multiple action with different role
				getJdbcTemplate().query(
						"select user_action, url, totrack_url from cms_statematrix_action action, tr_state_matrix matrix "
								+ "where action.state_id = matrix.stateid and matrix.fromstate = ? and stateins = ? and TEAM_MEMBERSHIP_TYPE_ID = ?",
						new Object[] { status, transactionType, new Long(criteria.getTeamTypeMembershipID())}, new ResultSetExtractor() {

							public Object extractData(ResultSet rs) throws SQLException,
									org.springframework.dao.DataAccessException {
								while (rs.next()) {
									sr.getActionUrls().put(rs.getString("USER_ACTION"), rs.getString("URL"));
								}
								return null;
							}
						});
			}
		}

		return result;
	}

	private String processTransactionFAM(String aLimitProfileID, String aCustomerID) {
		String famName = "-";

		if ((null != aLimitProfileID) && (Long.parseLong(aLimitProfileID) > 0)) {
			famName = getFAMName(Long.parseLong(aLimitProfileID), true);
		}
		else {
			if (null != aCustomerID) {
				famName = getFAMName(Long.parseLong(aCustomerID), false);
			}
		}

		return famName;
	}

	private String getFAMName(long aID, boolean aIsBCAInd) throws SearchDAOException {
		String theCustomerSQL = "SELECT EMP.LEM_EMP_NAME, EMP.LEM_EMP_TYPE_VALUE, EMP.LEM_PRINCIPAL_FAM_IND FROM "
				+ "SCI_LE_SUB_PROFILE C, SCI_LSP_EMP_MAP EMP WHERE C.CMS_LE_SUB_PROFILE_ID = EMP.CMS_LE_SUB_PROFILE_ID "
				+ "AND C.CMS_SUB_ORIG_COUNTRY = EMP.LEM_EMP_BKG_LOC_CTRY AND C.CMS_SUB_ORIG_ORGANISATION = EMP.LEM_EMP_BKG_LOC_ORG "
				+ "AND (EMP.UPDATE_STATUS_IND <> 'D' OR EMP.UPDATE_STATUS_IND is null) "
				+ "AND C.CMS_LE_SUB_PROFILE_ID = ? ";

		String theBCASQL = "SELECT EMP.LEM_EMP_NAME, EMP.LEM_EMP_TYPE_VALUE, EMP.LEM_PRINCIPAL_FAM_IND FROM "
				+ "SCI_LSP_LMT_PROFILE C, SCI_LSP_EMP_MAP EMP WHERE C.CMS_CUSTOMER_ID = EMP.CMS_LE_SUB_PROFILE_ID "
				+ "AND C.CMS_ORIG_COUNTRY = EMP.LEM_EMP_BKG_LOC_CTRY AND C.CMS_ORIG_ORGANISATION = EMP.LEM_EMP_BKG_LOC_ORG "
				+ "AND (EMP.UPDATE_STATUS_IND <> 'D' OR EMP.UPDATE_STATUS_IND is null) "
				+ "AND C.CMS_LSP_LMT_PROFILE_ID = ? ";

		String sql = (aIsBCAInd) ? theBCASQL : theCustomerSQL;

		return (String) getJdbcTemplate().query(sql, new Object[] { new Long(aID) }, new ResultSetExtractor() {

			public Object extractData(ResultSet rs) throws SQLException, org.springframework.dao.DataAccessException {
				String famName = "-";

				while (rs.next()) {

					if ("FAM".equals(rs.getString("LEM_EMP_TYPE_VALUE"))) {
						if (famName == null) {
							famName = rs.getString("LEM_EMP_NAME");
						}
						else if ("Y".equals(rs.getString("LEM_PRINCIPAL_FAM_IND"))) {
							famName = rs.getString("LEM_EMP_NAME");
						}
					}
				}

				return famName;
			}

		});

	}

	private int getCountFromQuery(String countSQL, List paramList) throws SearchDAOException {
		if (paramList == null) {
			paramList = Collections.EMPTY_LIST;
		}

		DefaultLogger.info(this, "IN method getCountFromQuery");
		long start = System.currentTimeMillis();

		try {
			return getJdbcTemplate().queryForInt(countSQL, paramList.toArray());
		}
		finally {
			time(start, "getCountFromQuery");
		}

	}

	private SearchResult searchTotrackTransactions(final CMSTrxSearchCriteria criteria) throws SearchDAOException {

		long start = System.currentTimeMillis();

		List paramList = new ArrayList();

		paramList.clear();
		String theSQL = getTotrackQuery(criteria, paramList, false);
		System.out.println("Query for searchTotrackTransactions=>theSQL=>"+theSQL);
		System.out.println("Query for searchTotrackTransactions=>paramList=>"+paramList);
		try {
			return (SearchResult) getJdbcTemplate().query(theSQL, paramList.toArray(), new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException,
						org.springframework.dao.DataAccessException {
					return processTrxResultSet(criteria, rs, true, false);
				}

			});
		}
		finally {
			time(start, "searchTotrackTransactions");
		}
	}

	private String getAllTrxQuery(CMSTrxSearchCriteria criteria, List paramList, boolean isCountOnly) {
		String selectPart = "SELECT A.TRX_REFERENCE_ID, A.TRANSACTION_ID, A.CUR_TRX_HISTORY_ID, A.REFERENCE_ID, A.TRANSACTION_TYPE, A.TRANSACTION_SUBTYPE, A.STATUS, A.MIN_EMPLOYEE_GRADE, "
				+ "A.LEGAL_ID, A.LIMIT_PROFILE_ID, A.LIMIT_PROFILE_REF_NUM, A.LEGAL_NAME AS LNAME, A.CUSTOMER_NAME AS CNAME, A.CUSTOMER_ID, A.TRANSACTION_DATE, "
				+ "A.TRX_ORIGIN_COUNTRY, A.USER_INFO, B.USER_STATE, B.USER_TRX_TYPE, C.LLP_BCA_REF_APPR_DATE, C.CMS_ORIG_COUNTRY, "
				+ "C.CMS_BCA_CREATE_DATE, D.COUNTRY_NAME, "
				+ "SP.LSP_ID, MP.LMP_LE_ID, SP.LSP_SHORT_NAME AS CUSTOMER_NAME, MP.LMP_LONG_NAME AS LEGAL_NAME, UPPER(MP.LMP_LONG_NAME) AS UPPER_LEGAL_NAME, A.DEAL_NO, ' ' AS TASK_FLAG ";

		String conditionPart = "FROM CMS_TRX_TOTRACK B, "
				+ "SCI_LSP_LMT_PROFILE C, "
				+ "COUNTRY D, "
				+ "TRANSACTION A LEFT OUTER JOIN  "
				+ "SCI_LE_SUB_PROFILE SP ON SP.CMS_LE_SUB_PROFILE_ID = A.CUSTOMER_ID LEFT OUTER JOIN  "
				+ "SCI_LE_MAIN_PROFILE MP ON MP.CMS_LE_MAIN_PROFILE_ID = SP.CMS_LE_MAIN_PROFILE_ID "
				+ "WHERE A.TRANSACTION_TYPE = B.TRANSACTION_TYPE "
				+ "AND A.STATUS = B.CURR_STATE "
				+ "AND (A.FROM_STATE = B.FROM_STATE OR B.FROM_STATE IS NULL) "
				+ "AND (A.TRANSACTION_SUBTYPE = B.TRANSACTION_SUBTYPE OR (A.TRANSACTION_SUBTYPE IS NULL AND B.TRANSACTION_SUBTYPE IS NULL)) "
				+ "AND A.LIMIT_PROFILE_ID = C.CMS_LSP_LMT_PROFILE_ID " + "AND C.CMS_ORIG_COUNTRY = D.COUNTRY_ISO_CODE ";

		if (!isCountOnly) {
			StringBuffer queryBuf = new StringBuffer(selectPart).append(conditionPart);
			prepareQueryCondition(criteria, paramList, queryBuf);
			return queryBuf.append(" ORDER BY CMS_BCA_CREATE_DATE, A.TRANSACTION_ID ").toString();
		}
		else {
			StringBuffer countOnlyQueryBuf = new StringBuffer("SELECT COUNT(*) ").append(conditionPart);
			prepareQueryCondition(criteria, paramList, countOnlyQueryBuf);
			return countOnlyQueryBuf.toString();
		}
	}

	private void prepareQueryCondition(CMSTrxSearchCriteria criteria, List paramList, StringBuffer conditionBuf) {
		if ((criteria.getTransactionTypes() != null) && (criteria.getTransactionTypes().length > 0)) {
			conditionBuf.append(" AND A.TRANSACTION_TYPE IN (");
			int trxTypeCount = criteria.getTransactionTypes().length;
			for (int i = 0; i < trxTypeCount; i++) {
				String s = criteria.getTransactionTypes()[i];
				conditionBuf.append("'").append(s).append("'");
				if (i != criteria.getTransactionTypes().length - 1) {
					conditionBuf.append(", ");
				}

			}
			conditionBuf.append(") ");

			if ((trxTypeCount == 1) && ICMSConstant.INSTANCE_LIMIT_PROFILE.equals(criteria.getTransactionTypes()[0])) {
				conditionBuf.append(" AND A.REFERENCE_ID = C.CMS_LSP_LMT_PROFILE_ID ");
			}
		}

		if (criteria.getCurrentState() != null) {
			conditionBuf.append(" AND A.STATUS ");
			if (criteria.isCurrentState()) {
				conditionBuf.append(" = ");
			}
			else {
				conditionBuf.append(" != ");
			}
			conditionBuf.append("'").append(criteria.getCurrentState()).append("'");
		}

		conditionBuf.append(CMSTransactionDAOQueryHelper.getTransactionFilterQuery(criteria, paramList));
	}

	/**
	 * Helper method to get workflow trx query given criteria.
	 * 
	 * @param criteria - CMSTrxSearchCriteria
	 * @param isCountOnly - boolean
	 */
	private String getWorkflowTrxQuery(CMSTrxSearchCriteria criteria, List paramList, boolean isCountOnly) {

		DefaultLogger.info("CMSTransactionDAO.getWorkflowTrxQuery", "IN method getWorkflowTrxQuery ");

		// Determine type of customer to be included in search
		// //////////////////////
		String customerType = criteria.getCustomerType();
		boolean isBorrowerAndNonBorrowerIncluded = (customerType == null);
		boolean isOnlyBorrower = false;
		boolean isOnlyNonBorrower = false;
		if (customerType != null) {
			isOnlyBorrower = (ICMSConstant.CUSTOMER_TYPE_BORROWER.equals(customerType));
			isOnlyNonBorrower = (ICMSConstant.CUSTOMER_TYPE_NONBORROWER.equals(customerType));
		}

		StringBuffer theUltimateQueryBuf = new StringBuffer();
		// Borrowers query
		// //////////////////////////////////////////////////////////
		if (isBorrowerAndNonBorrowerIncluded || isOnlyBorrower) {
			StringBuffer commonTrxFilterBuf = new StringBuffer();
			prepareWorkflowQueryCondition(criteria, paramList, commonTrxFilterBuf);

			commonTrxFilterBuf.append(getWorkflowTransactionFilterQuery(criteria, paramList));

			// construct country and org in list only once
			final String[] countries = criteria.getAllowedCountries();
			final String[] organisations = criteria.getAllowedOrganisations();
			StringBuffer countryInListBuf = null;
			StringBuffer orgInListBuf = null;
			ArrayList countryParams = null;
			ArrayList orgParams = null;
			if (!CommonUtil.isEmptyArray(countries)) {
				countryInListBuf = new StringBuffer();
				countryParams = new ArrayList();
				CommonUtil.buildSQLInList(countries, countryInListBuf, countryParams);
			}

			if (!CommonUtil.isEmptyArray(organisations)) {
				orgInListBuf = new StringBuffer();
				orgParams = new ArrayList();
				CommonUtil.buildSQLInList(organisations, orgInListBuf, orgParams);
			}

			// construct the workflow query
			theUltimateQueryBuf.append(BORROWER_SELECT_PART);
			theUltimateQueryBuf.append(BORROWER_UNION_FLAG);
			theUltimateQueryBuf.append(BORROWER_CONDITION_PART);
			theUltimateQueryBuf.append(commonTrxFilterBuf.toString());

			if (!criteria.isCurrentState()) {
				ArrayList commonTrxFilterParams = new ArrayList(paramList);
				ArrayList colTaskParams = new ArrayList();
				ArrayList ccTaskParams = new ArrayList();
				ArrayList limitParams = new ArrayList();

				theUltimateQueryBuf.append(UNION_ALL);
				theUltimateQueryBuf.append(getBorrowerCollateralTaskQuery(commonTrxFilterParams, colTaskParams,
						commonTrxFilterBuf, countryInListBuf, countryParams));
				paramList.addAll(colTaskParams);

				if (criteria.getTeamTypeMembershipID() == ICMSConstant.TEAM_TYPE_CPC_MAKER) {
					theUltimateQueryBuf.append(UNION_ALL);
					theUltimateQueryBuf.append(getBorrowerCCTaskQuery(commonTrxFilterParams, ccTaskParams,
							commonTrxFilterBuf, countryInListBuf, countryParams, orgInListBuf, orgParams));
					paramList.addAll(ccTaskParams);
				}

				if (criteria.getTeamTypeMembershipID() == ICMSConstant.TEAM_TYPE_SSC_MAKER
						||criteria.getTeamTypeMembershipID() == ICMSConstant.TEAM_TYPE_SSC_MAKER_WFH) {
					theUltimateQueryBuf.append(UNION_ALL);
					theUltimateQueryBuf.append(getBorrowerLimitQuery(commonTrxFilterParams, limitParams,
							commonTrxFilterBuf, countryInListBuf, countryParams, orgInListBuf, orgParams));
					paramList.addAll(limitParams);
				}
			}
		}

		// union
		// ///////////////////////////////////////////////////////////////////
		if (isBorrowerAndNonBorrowerIncluded) {
			theUltimateQueryBuf.append(UNION_ALL);
		}

		// Non-borrowers query
		// //////////////////////////////////////////////////////
		if (isBorrowerAndNonBorrowerIncluded || isOnlyNonBorrower) {
			theUltimateQueryBuf.append(this.getNonBorrowerQuery(criteria, paramList));
		}

		if (!isCountOnly) {
			if (CMSTransactionUtil.isSortedByLegalName(criteria) || !criteria.isCurrentState()) {
				return theUltimateQueryBuf.append(" ORDER BY UPPER_LEGAL_NAME, CMS_BCA_CREATE_DATE, TRANSACTION_ID")
						.toString();
			}
			else {
				return theUltimateQueryBuf.append(" ORDER BY CMS_BCA_CREATE_DATE, TRANSACTION_ID ").toString();
			}
		}
		else {
			return new StringBuffer("SELECT COUNT(DISTINCT TRANSACTION_ID) FROM (").append(theUltimateQueryBuf).append(
					") AS TEMP1").toString();
		}
	}

	/**
	 * Helper method to get SQL query for borrower with collateral collaboration
	 * task.
	 * 
	 * @param commonTrxFilterParams - SQLParameter containing all the params for
	 *        the common trx filter condition.
	 * @param colTaskParams - ArrayList
	 * @param commonTrxFilterBuf - StringBuffer
	 * @param countryInListBuf - StringBuffer
	 * @param countryParams - ArrayList
	 * @return String
	 */
	private String getBorrowerCollateralTaskQuery(ArrayList commonTrxFilterParams, ArrayList colTaskParams,
			StringBuffer commonTrxFilterBuf, StringBuffer countryInListBuf, ArrayList countryParams) {
		StringBuffer colUnionBuf = new StringBuffer();
		colUnionBuf.append(BORROWER_COL_TASK_SQL);
		if (!CommonUtil.isEmptyList(countryParams)) {
			colUnionBuf.append(" AND cc.SECURITY_LOCATION ");
			colUnionBuf.append(countryInListBuf.toString());
			colTaskParams.addAll(countryParams);
		}
		colUnionBuf.append(")");
		colUnionBuf.append(commonTrxFilterBuf.toString());
		colTaskParams.addAll(commonTrxFilterParams);
		return colUnionBuf.toString();
	}

	/**
	 * Helper method to get SQL query for borrower with cc collaboration task.
	 * 
	 * @param commonTrxFilterParams - ArrayList containing all the params for
	 *        the common trx filter condition.
	 * @param ccTaskParams - ArrayList
	 * @param commonTrxFilterBuf - StringBuffer
	 * @param countryInListBuf - StringBuffer
	 * @param countryParams - ArrayList
	 * @param orgInListBuf - StringBuffer
	 * @param orgParams - ArrayList
	 * @return String
	 */
	private String getBorrowerCCTaskQuery(ArrayList commonTrxFilterParams, ArrayList ccTaskParams,
			StringBuffer commonTrxFilterBuf, StringBuffer countryInListBuf, ArrayList countryParams,
			StringBuffer orgInListBuf, ArrayList orgParams) {
		StringBuffer ccUnionBuf = new StringBuffer();
		ccUnionBuf.append(BORROWER_CC_TASK_SQL);
		if (!CommonUtil.isEmptyList(countryParams)) {
			ccUnionBuf.append(" AND cc.DMCL_CNTRY_ISO_CODE ");
			ccUnionBuf.append(countryInListBuf.toString());
			ccTaskParams.addAll(countryParams);
		}
		if (!CommonUtil.isEmptyList(orgParams)) {
			ccUnionBuf.append(" AND cc.ORG_CODE ");
			ccUnionBuf.append(orgInListBuf.toString());
			ccTaskParams.addAll(orgParams);
		}
		ccUnionBuf.append(") ");
		ccUnionBuf.append(commonTrxFilterBuf.toString());
		ccTaskParams.addAll(commonTrxFilterParams);
		return ccUnionBuf.toString();
	}

	/**
	 * Helper method to get SQL query for borrower with limit.
	 * 
	 * @param commonTrxFilterParams - ArrayList containing all the params for
	 *        the common trx filter condition.
	 * @param lmtParams - ArrayList
	 * @param commonTrxFilterBuf - StringBuffer
	 * @param countryInListBuf - StringBuffer
	 * @param countryParams - ArrayList
	 * @param orgInListBuf - StringBuffer
	 * @param orgParams - ArrayList
	 * @return String
	 */
	private String getBorrowerLimitQuery(ArrayList commonTrxFilterParams, ArrayList lmtParams,
			StringBuffer commonTrxFilterBuf, StringBuffer countryInListBuf, ArrayList countryParams,
			StringBuffer orgInListBuf, ArrayList orgParams) {
		StringBuffer limitUnionBuf = new StringBuffer();
		limitUnionBuf.append(BORROWER_LIMIT_SQL);

		if (!CommonUtil.isEmptyList(countryParams)) {
			limitUnionBuf.append(" AND lt.CMS_BKG_COUNTRY ");
			limitUnionBuf.append(countryInListBuf.toString());
			lmtParams.addAll(countryParams);
		}

		if (!CommonUtil.isEmptyList(orgParams)) {
			limitUnionBuf.append(" AND lt.CMS_BKG_ORGANISATION ");
			limitUnionBuf.append(orgInListBuf.toString());
			lmtParams.addAll(orgParams);
		}

		limitUnionBuf.append(") ");
		limitUnionBuf.append(commonTrxFilterBuf.toString());
		lmtParams.addAll(commonTrxFilterParams);

		return limitUnionBuf.toString();
	}

	private void prepareWorkflowQueryCondition(CMSTrxSearchCriteria criteria, List paramList, StringBuffer conditionBuf) {

		DefaultLogger.info(this, "IN method prepareWorkflowQueryCondition");

		String[] trxTypes = criteria.getTransactionTypes();

		if (!CommonUtil.isEmptyArray(trxTypes)) {
			conditionBuf.append(" AND A.TRANSACTION_TYPE ");
			CommonUtil.buildSQLInList(trxTypes, conditionBuf, paramList);
		}

		if (criteria.getCurrentState() != null) {
			conditionBuf.append(" AND A.STATUS ");
			if (criteria.isCurrentState()) {
				conditionBuf.append(" = ");
			}
			else {
				conditionBuf.append(" != ");
			}

			conditionBuf.append("?");
			paramList.add(criteria.getCurrentState());
		}

	}

	/*
	 * Dispatcher to get Query for Maker/Chker(Origianl) or FAM (Modified)
	 */
	private String getQuery(CMSTrxSearchCriteria criteria, List paramList, boolean isCountOnly) {
		String tt = criteria.getTeamType();

		if ((tt != null) && (tt.equals("FAM"))) {
			return getQueryByFAM(criteria, paramList, isCountOnly);
		}
		else {
			return getQueryByMC(criteria, paramList, isCountOnly);
		}
	}

	private String getQueryByFAM(CMSTrxSearchCriteria criteria, List paramList, boolean isCountOnly) {
		DefaultLogger.info(this, "IN method getQueryByFAM + isCountOnly");

		String selectPart = "SELECT A.TRX_REFERENCE_ID, A.TRANSACTION_ID, A.CUR_TRX_HISTORY_ID, A.REFERENCE_ID, A.TRANSACTION_TYPE, A.TRANSACTION_SUBTYPE, A.STATUS, A.MIN_EMPLOYEE_GRADE, "
				+ "A.LEGAL_ID, A.LIMIT_PROFILE_ID, A.LEGAL_NAME AS LNAME, A.CUSTOMER_NAME AS CNAME, A.CUSTOMER_ID, A.TRANSACTION_DATE, "
				+ "A.TRX_ORIGIN_COUNTRY, B.USER_ACTION, B.URL, B.TOTRACK_URL, F2.USER_STATE, F2.USER_TRX_TYPE, "
				+ "CO.COUNTRY_NAME, SP.LSP_ID, "
				+ "MP.LMP_LE_ID, SP.LSP_SHORT_NAME AS CUSTOMER_NAME, MP.LMP_LONG_NAME AS LEGAL_NAME, UPPER(MP.LMP_LONG_NAME) AS UPPER_LEGAL_NAME, A.DEAL_NO, ' ' AS TASK_FLAG ";

		StringBuffer conditionPart = new StringBuffer(
				"FROM  SCI_LE_SUB_PROFILE SP  LEFT OUTER JOIN  SCI_LE_MAIN_PROFILE MP  ON  SP.CMS_LE_MAIN_PROFILE_ID  = MP.CMS_LE_MAIN_PROFILE_ID    RIGHT OUTER JOIN  TRANSACTION A  ON  A.CUSTOMER_ID  = SP.CMS_LE_SUB_PROFILE_ID    LEFT OUTER JOIN  SCI_LSP_LMT_PROFILE LMT  ON  A.LIMIT_PROFILE_ID  = LMT.CMS_LSP_LMT_PROFILE_ID    LEFT OUTER JOIN  COUNTRY CO  ON  A.TRX_ORIGIN_COUNTRY  = CO.COUNTRY_ISO_CODE  , "
						+ " CMS_STATEMATRIX_ACTION B, "
						+ " TR_STATE_MATRIX F, "
						+ " CMS_TRX_TOTRACK F2, "
						+ " CMS_TEAM C, "
						+ " CMS_TEAM_TYPE_MEMBERSHIP E  "
						+ " WHERE A.TRANSACTION_TYPE  = F2.TRANSACTION_TYPE "
						+ " AND	  ((A.TRANSACTION_TYPE  in ( 'COL'  ) AND (A.TRX_REFERENCE_ID  IS NULL OR TO_CHAR(A.TRX_REFERENCE_ID)  = A.TRANSACTION_ID))  OR A.TRANSACTION_TYPE  not in ( 'COL' )) "
						+ " AND	  A.STATUS  = F2.CURR_STATE "
						+ " AND	  (A.FROM_STATE  = F2.FROM_STATE OR F2.FROM_STATE  IS NULL) "
						+ " AND	  A.TRANSACTION_TYPE  = F.STATEINS "
						+ " AND	  (A.TRANSACTION_SUBTYPE  = F2.TRANSACTION_SUBTYPE OR (A.TRANSACTION_SUBTYPE  IS NULL AND F2.TRANSACTION_SUBTYPE)) "
						+ " AND	  ((A.TO_GROUP_TYPE_ID  = '"
						+ criteria.getTeamTypeMembershipID()
						+ "') OR	(A.TO_GROUP_TYPE_ID  is null) OR (A.TO_GROUP_TYPE_ID  = '-1') OR	(A.TO_GROUP_TYPE_ID  = '"
						+ Long.toString(ICMSConstant.LONG_INVALID_VALUE)
						+ "')) "
						+ " AND	  F.FROMSTATE  = A.STATUS "
						+ " AND	  F.STATEID  = B.STATE_ID "
						+ " AND	  A.TEAM_ID  = C.TEAM_ID "
						+ " AND	  B.TEAM_MEMBERSHIP_TYPE_ID  = E.TEAM_TYPE_MEMBERSHIP_ID "
						+ " AND	  E.TEAM_TYPE_ID  = C.TEAM_TYPE_ID "
						+ " AND	  B.TEAM_MEMBERSHIP_TYPE_ID  = "
						+ criteria.getTeamTypeMembershipID());

		String addlfilter = CMSTransactionDAOQueryHelper.getTransactionFilterQuery(criteria, paramList);

		conditionPart.append(addlfilter);

		String sortPart;
		if ((criteria.getLegalName() != null) && !criteria.getLegalName().trim().equals("")) {
			sortPart = " ORDER BY LEGAL_NAME ";
		}
		else {
			sortPart = " ORDER BY TRANSACTION_DATE DESC, TRANSACTION_ID, USER_ACTION DESC ";
		}

		StringBuffer theUltimateQuery = new StringBuffer(selectPart).append(conditionPart).append(" UNION ALL ")
				.append(getFAMQuery2(criteria, paramList));

		if (isCountOnly) {
			return new StringBuffer("SELECT COUNT(DISTINCT TRANSACTION_ID) FROM (").append(theUltimateQuery).append(
					") AS TEMP1").toString();
		}
		else {
			return theUltimateQuery.append(sortPart).toString();
		}
	}

	// private String getFAMQuery2(CMSTrxSearchCriteria criteria, boolean
	// isDistinctID) {
	private String getFAMQuery2(CMSTrxSearchCriteria criteria, List paramList) {

		DefaultLogger.info(this, "IN method getFAMQuery2 ");

		// String selectCountPart = "SELECT COUNT(DISTINCT TRANSACTION_ID) ";
		StringBuffer resultBuf = new StringBuffer();
		String selectPart = "SELECT A.TRX_REFERENCE_ID, A.TRANSACTION_ID, A.CUR_TRX_HISTORY_ID, A.REFERENCE_ID, A.TRANSACTION_TYPE, A.TRANSACTION_SUBTYPE, A.STATUS, A.MIN_EMPLOYEE_GRADE, "
				+ "A.LEGAL_ID, A.LIMIT_PROFILE_ID, A.LEGAL_NAME as LNAME, A.CUSTOMER_NAME AS CNAME, A.CUSTOMER_ID, A.TRANSACTION_DATE, "
				+ "A.TRX_ORIGIN_COUNTRY, B.USER_ACTION, B.URL, B.TOTRACK_URL, F2.USER_STATE, F2.USER_TRX_TYPE, "
				+ "CO.COUNTRY_NAME, SP.LSP_ID, "
				+ "MP.LMP_LE_ID, SP.LSP_SHORT_NAME AS CUSTOMER_NAME, MP.LMP_LONG_NAME AS LEGAL_NAME, UPPER(MP.LMP_LONG_NAME) AS UPPER_LEGAL_NAME, A.DEAL_NO, ' ' AS TASK_FLAG ";

		resultBuf.append(selectPart);

		resultBuf
				.append("FROM  SCI_LE_SUB_PROFILE SP  LEFT OUTER JOIN  SCI_LE_MAIN_PROFILE MP  ON  SP.CMS_LE_MAIN_PROFILE_ID  = MP.CMS_LE_MAIN_PROFILE_ID    RIGHT OUTER JOIN  TRANSACTION A  ON  A.CUSTOMER_ID  = SP.CMS_LE_SUB_PROFILE_ID    LEFT OUTER JOIN  SCI_LSP_LMT_PROFILE LMT  ON  A.LIMIT_PROFILE_ID  = LMT.CMS_LSP_LMT_PROFILE_ID    LEFT OUTER JOIN  COUNTRY CO  ON  A.TRX_ORIGIN_COUNTRY  = CO.COUNTRY_ISO_CODE  , "
						+ " CMS_STATEMATRIX_ACTION B, "
						+ " TR_STATE_MATRIX F, "
						+ " CMS_TRX_TOTRACK F2 "
						+ " WHERE A.TRANSACTION_TYPE  = F2.TRANSACTION_TYPE "
						+ " AND A.STATUS  = F2.CURR_STATE "
						+ " AND (A.FROM_STATE  = F2.FROM_STATE  OR F2.FROM_STATE  IS NULL) "
						+ " AND A.TRANSACTION_TYPE  = F.STATEINS "
						+ " AND (A.TRANSACTION_SUBTYPE  = F2.TRANSACTION_SUBTYPE  OR (A.TRANSACTION_SUBTYPE  IS NULL AND F2.TRANSACTION_SUBTYPE IS NULL)) "
						+ " AND F.FROMSTATE  = A.STATUS "
						+ " AND A.TRANSACTION_TYPE  IN ('"
						+ ICMSConstant.INSTANCE_DEFERRAL_REQ
						+ "', '"
						+ ICMSConstant.INSTANCE_WAIVER_REQ
						+ "', '"
						+ ICMSConstant.INSTANCE_CHECKLIST
						+ "', '"
						+ ICMSConstant.INSTANCE_COMMODITY_DEAL
						+ "') "
						+ " AND ((A.TO_GROUP_TYPE_ID  = '"
						+ String.valueOf(criteria.getTeamTypeMembershipID())
						+ "') OR (A.TO_GROUP_TYPE_ID  IS NULL) OR (A.TO_GROUP_TYPE_ID  = '-1') OR (A.TO_GROUP_TYPE_ID  = '"
						+ String.valueOf(ICMSConstant.LONG_INVALID_VALUE)
						+ "')) "
						+ " AND F.STATEID  = B.STATE_ID "
						+ " AND B.TEAM_MEMBERSHIP_TYPE_ID  = " + String.valueOf(criteria.getTeamTypeMembershipID()));

		String addlfilter = CMSTransactionDAOQueryHelper.getTransactionFilterQuery(criteria, paramList);

		resultBuf.append(addlfilter);
		return resultBuf.toString();
	}

	/*
	 * Original Query user by Maker/Checker
	 */
	// Being of Query
	private String getQueryByMC(CMSTrxSearchCriteria criteria, List paramList, boolean isCountOnly) {

		DefaultLogger.info(this, "IN method getQueryByMC + isCountOnly - original query");

		StringBuffer result = new StringBuffer();

		DefaultLogger.info(this, "IN method getQueryByMC + isCountOnly - team type : "
				+ criteria.getTeamTypeMembershipID());

		result.append("SELECT * FROM (");
		result.append(TODO_SELECT);
		result.append(TODO_CONDITION);
		paramList.add(new Long(criteria.getTeamTypeMembershipID()));
		paramList.add(new Long(criteria.getUserID()));
		paramList.add(new Long(criteria.getTeamMembershipID()));
		paramList.add(new Long(criteria.getTeamMembershipID()));
		// result.append(TODO_CONDITION_BORROWER);
		result.append(CMSTransactionDAOQueryHelper.getTransactionFilterQuery(criteria, paramList));

		// result.append(UNION_ALL);
		// result.append(getQueryByMCForNB(criteria, paramList));

		// result.append(UNION_ALL);
		// result.append(getFAMQuery(criteria, paramList));

		if (isCountOnly) {
			return new StringBuffer("SELECT COUNT(DISTINCT TRANSACTION_ID) FROM (").append(result).append(") AS TEMP1")
					.toString();
		}
		else {// get field name on which sort is performed
			String sortPart="";
			if(criteria.getField()!=null&criteria.getSort()!=null){
				if("TRANSACTION_DATE".equalsIgnoreCase(criteria.getField()))
					sortPart = " ORDER BY "+criteria.getField()+" "+criteria.getSort();
				else
				sortPart = " ORDER BY lower("+criteria.getField()+") "+criteria.getSort();
			}
			else
				sortPart = " ORDER BY TRANSACTION_DATE DESC, TRANSACTION_ID ";
			return result.append(sortPart).append(") todo WHERE rnum = 1").toString();
		}
	}

	/**
	 * Maker/Checker Query for NonBorrower - required for FAM Name
	 * @param criteria
	 * @return String
	 */
	private String getQueryByMCForNB(CMSTrxSearchCriteria criteria, List paramList) {
		DefaultLogger.info(this, "IN method getMCQueryForNB");

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(TODO_SELECT);
		sqlBuf.append(TODO_CONDITION);

		paramList.add(new Long(criteria.getTeamTypeMembershipID()));
		paramList.add(new Long(criteria.getUserID()));
		paramList.add(new Long(criteria.getTeamMembershipID()));
		paramList.add(new Long(criteria.getTeamMembershipID()));

		sqlBuf.append(TODO_CONDITION_NONBORROWER);
		sqlBuf.append(CMSTransactionDAOQueryHelper.getTransactionFilterQuery(criteria, paramList));

		return sqlBuf.toString();
	}

	private String getFAMQuery(CMSTrxSearchCriteria criteria, List paramList) {
		DefaultLogger.info(this, "IN method getFAMQuery");

		/**
		 * the below query includes
		 * 
		 * ICMSConstant.INSTANCE_DEFERRAL_REQ ICMSConstant.INSTANCE_WAIVER_REQ
		 * ICMSConstant.INSTANCE_CHECKLIST ICMSConstant.INSTANCE_COMMODITY_DEAL
		 * 
		 */

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append(TODO_SELECT);
		sqlBuf.append(TODO_CONDITION_MULTILEVEL);
		paramList.add(new Long(criteria.getTeamTypeMembershipID()));
		sqlBuf.append(CMSTransactionDAOQueryHelper.getTransactionFilterQuery(criteria, paramList));
		return sqlBuf.toString();
	}

	private String getTotrackQuery(CMSTrxSearchCriteria criteria, List paramList, boolean isCountOnly) {
		DefaultLogger.info(this, "IN method getTotrackQuery+isCountOnly");

		if (isCountOnly) {
			return new ToTrackQueryCount(paramList, criteria).constructSQL();
		}
		else {
			StringBuffer buf = new StringBuffer();
			buf.append("SELECT * FROM (");
			buf.append(new ToTrackQueryNoCount(paramList, criteria).constructSQL());
			buf.append(") totrack WHERE rnum = 1");

			return buf.toString();
		}

	}

	private String getWorkflowTransactionFilterQuery(CMSTrxSearchCriteria criteria, List paramList) {

		DefaultLogger.info(this, "IN method getWorkflowTransactionFilterQuery");

		StringBuffer retQuery = new StringBuffer();

		String[] countries = criteria.getAllowedCountries();

		if (!CommonUtil.isEmptyArray(countries)) {
			retQuery.append(" AND C.CMS_ORIG_COUNTRY ");
			CommonUtil.buildSQLInList(countries, retQuery, paramList);
		}

		String[] segments = criteria.getAllowedSegments();

		if (!CommonUtil.isEmptyArray(segments)) {
			retQuery.append(" AND MP.LMP_SGMNT_CODE_VALUE ");
			CommonUtil.buildSQLInList(segments, retQuery, paramList);
		}

		String[] organisations = criteria.getAllowedOrganisations();

		if (!CommonUtil.isEmptyArray(organisations)) {
			retQuery.append(" AND ( C.CMS_ORIG_ORGANISATION ");
			CommonUtil.buildSQLInList(organisations, retQuery, paramList);
			retQuery.append(" OR L.CMS_BKG_ORGANISATION ");
			CommonUtil.buildSQLInList(organisations, retQuery, paramList);
			retQuery.append(")");
		}

		if ((criteria.getLimitProfileID() != null) && (criteria.getLimitProfileID().longValue() != 0)) {
			retQuery.append(" AND A.LIMIT_PROFILE_ID = ?");
			paramList.add(criteria.getLimitProfileID());
		}

		CMSTransactionDAOQueryHelper.appendCustomerFilter(criteria, paramList, retQuery);

		return retQuery.toString();
	}

	private String getNonBorrowerQuery(CMSTrxSearchCriteria criteria, List paramList) {
		DefaultLogger.info(this, "IN method getNonBorrowerQuery");

		StringBuffer nonBorrowerQueryBuf = new StringBuffer();
		nonBorrowerQueryBuf.append(NONBORROWER_SQL);
		CMSTransactionDAOQueryHelper.appendCustomerFilter(criteria, paramList, nonBorrowerQueryBuf);

		if (criteria.isCurrentState()) {
			nonBorrowerQueryBuf.append(" AND SP.CMS_CCC_STATUS = '").append(ICMSConstant.STATE_ACTIVE).append("' ");
		}
		else {
			nonBorrowerQueryBuf.append(" AND SP.CMS_CCC_STATUS = '").append(ICMSConstant.CCC_STARTED).append("' ");
		}

		String[] countries = criteria.getAllowedCountries();
		if (!CommonUtil.isEmptyArray(countries)) {
			nonBorrowerQueryBuf.append(" AND SP.CMS_SUB_ORIG_COUNTRY ");
			CommonUtil.buildSQLInList(countries, nonBorrowerQueryBuf, paramList);
		}

		return nonBorrowerQueryBuf.toString();
	}

	/**
	 * finds out how long a method call took
	 * @param start
	 * @param method
	 */
	private void time(long start, String method) {
		DefaultLogger.info(this, "**************************************************");
		DefaultLogger.info(this, "* Method - " + method + " took " + (System.currentTimeMillis() - start) + " ms *");
		DefaultLogger.info(this, "*************************************************");
	}

	protected SearchResult emptySearchResult() {
		return new SearchResult(0, 0, 0, Collections.EMPTY_LIST);
	}

	private Collection processWorkflowResultSet(ResultSet rs, boolean isWithURLs, boolean isAllQuery)
			throws SQLException {
		Collection al = new ArrayList();
		while (rs.next()) {
			OBCMSTrxSearchResult sr = new OBCMSTrxSearchResult();
			sr.setTransactionID(rs.getString("TRANSACTION_ID"));
			sr.setReferenceID(rs.getString("REFERENCE_ID"));
			sr.setCustomerName(rs.getString("CUSTOMER_NAME"));
			if (sr.getCustomerName() == null) {
				sr.setCustomerName(rs.getString("CNAME"));
			}
			// sr.setFam(null);
			sr.setLegalName(rs.getString("LEGAL_NAME"));
			if (sr.getLegalName() == null) {
				sr.setLegalName(rs.getString("LNAME"));
			}

			sr.setLeID(rs.getString("LEGAL_ID"));
			sr.setOriginatingLocation(rs.getString("COUNTRY_NAME"));
			sr.setStatus(rs.getString("STATUS"));
			sr.setSubProfileID(rs.getString("CUSTOMER_ID"));
			sr.setTransactionDate(rs.getDate("TRANSACTION_DATE"));
			sr.setTransactionType(rs.getString("TRANSACTION_TYPE"));
			sr.setTransactionSubType(rs.getString("TRANSACTION_SUBTYPE"));
			sr.setLimitProfileID(rs.getString("LIMIT_PROFILE_ID"));
			sr.setUserState(rs.getString("USER_STATE"));
			sr.setTrxHistoryID(rs.getString("CUR_TRX_HISTORY_ID"));
			sr.setUserTransactionType(rs.getString("USER_TRX_TYPE"));
			sr.setDealNo(rs.getString("DEAL_NO"));
			sr.setSciLegalID(rs.getString("LMP_LE_ID"));
			sr.setSciSubprofileID(rs.getLong("LSP_ID"));
			sr.setActionUrls(new HashMap());
			sr.setTotrackActionUrls(new HashMap());
			sr.setFam("-");

			if (isAllQuery) {
				sr.setSciApprovedDate(rs.getDate("LLP_BCA_REF_APPR_DATE"));
				sr.setTransactionDate(rs.getDate("CMS_BCA_CREATE_DATE"));
			}

			al.add(sr);
			if (isWithURLs) {
				sr.getActionUrls().put(rs.getString("USER_ACTION"), rs.getString("URL"));
				sr.getTotrackActionUrls().put("View", rs.getString("TOTRACK_URL"));
				sr.setFam(processTransactionFAM(rs.getString("LIMIT_PROFILE_ID"), rs.getString("CUSTOMER_ID")));
			}

			String taskFlag = rs.getString("TASK_FLAG");
			if ("S".equals(taskFlag)) {
				sr.setSecColTask(true);
			}
			else if ("C".equals(taskFlag)) {
				sr.setCCColTask(true);
			}
			else if ("L".equals(taskFlag)) {
				sr.setLimitTask(true);
			}
			else {
				sr.setMainTask(true);
			}
		}

		return al;
	}
	
	
	
	// get transaction type based on access for different users
	public List getTransactionTypeList(String teamID) throws SearchDAOException{
		DBUtil dbUtil = null;
		List transList = new ArrayList();
		try {
			dbUtil = new DBUtil();
			if(teamID.equalsIgnoreCase("1004")||teamID.equalsIgnoreCase("1005")
					||teamID.equalsIgnoreCase("1031")||teamID.equalsIgnoreCase("1032")) // cpu_adm / cpu_chk
			dbUtil.setSQL("select transaction_type,transaction_subtype,name from transaction_type_name where access_name=2");
			else if(teamID.equalsIgnoreCase("1006")||teamID.equalsIgnoreCase("1007")
					||teamID.equalsIgnoreCase("1027")||teamID.equalsIgnoreCase("1029")
					||teamID.equalsIgnoreCase("1024")||teamID.equalsIgnoreCase("1028")
					||teamID.equalsIgnoreCase("1038")||teamID.equalsIgnoreCase("1039"))// cpuadm_a / br_maker
				dbUtil.setSQL("select transaction_type,transaction_subtype,name from transaction_type_name where access_name=3");
			else if(teamID.equalsIgnoreCase("1001")||teamID.equalsIgnoreCase("1002")
					||teamID.equalsIgnoreCase("1034")||teamID.equalsIgnoreCase("1035"))
				dbUtil.setSQL("select transaction_type,transaction_subtype,name from transaction_type_name where access_name=4");
			else if(teamID.equalsIgnoreCase("1021")||teamID.equalsIgnoreCase("1022"))
				dbUtil.setSQL("select transaction_type,transaction_subtype,name from transaction_type_name where access_name=3");
			else if(teamID.equalsIgnoreCase("1025")||teamID.equalsIgnoreCase("1026"))
				dbUtil.setSQL("select transaction_type,transaction_subtype,name from transaction_type_name where access_name=2 and name = 'Maintain Relationship Manager'");
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				String trans[] = new String[3];
				trans[0]=rs.getString("transaction_type");
				trans[1]=rs.getString("transaction_subtype");
				trans[2]=rs.getString("name");
				transList.add(trans);
			}
			return transList;
		}
		catch (DBConnectionException dbe) {
			throw new SearchDAOException(dbe);
		}
		catch (NoSQLStatementException ne) {
			throw new SearchDAOException(ne);
		}
		catch (SQLException se) {
			throw new SearchDAOException(se);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException e) {
				throw new SearchDAOException(e);
			}
		}
	}


}
