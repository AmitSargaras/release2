package com.integrosys.cms.app.discrepency.bus;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchResultSetProcessUtils;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.dbsupport.NoSQLStatementException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.app.transaction.OBCMSTrxSearchResult;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;

/**
 * 
 * @author $Author: Abhijit R. $
 */

public class DiscrepancyJdbcImpl extends JdbcDaoSupport implements
IDiscrepancyJdbc {
	// ////////////////////////////////////////////////////////////////
	private static final String TODO_SELECT = "select customer_id , count(1),customer_name,user_info,lsp_le_id,limit_profile_ref_num,transaction_type, login_id ,transaction_date from (SELECT * FROM  (SELECT row_number() over(partition BY a.transaction_id order by a.transaction_id) rnum,"
	+" a.trx_reference_id,    a.transaction_id,    a.cur_trx_history_id,    a.reference_id, a.login_id,   a.transaction_type,    a.transaction_subtype,    a.status,"
	+" a.legal_id,    a.limit_profile_id,    a.limit_profile_ref_num,    a.legal_name    AS lname,    a.customer_name AS cname,    a.customer_id,    to_char(a.transaction_date,'DD-Mon-YYYY') as transaction_date , "
	+" a.trx_origin_country,    a.user_info,    b.user_action,    b.url,    b.totrack_url,    f2.user_state,    f2.user_trx_type,    f.stateid,    (SELECT country_name"
	+" FROM country    WHERE country_iso_code = a.trx_origin_country    ) country_name,    sp.lsp_id,    sp.lsp_le_id,    sp.lsp_short_name          AS customer_name,"
	+" sp.lsp_short_name          AS legal_name,    UPPER (sp.lsp_short_name ) AS upper_legal_name,    ' '                        AS task_flag,    a.deal_no  FROM CMS_STATEMATRIX_ACTION b,"
	+" TR_STATE_MATRIX f,    CMS_TRX_TOTRACK f2,    CMS_TEAM c,    CMS_TEAM_TYPE_MEMBERSHIP e,    TRANSACTION a  LEFT OUTER JOIN SCI_LE_SUB_PROFILE sp  ON sp.CMS_LE_SUB_PROFILE_ID = a.CUSTOMER_ID"
	+" LEFT OUTER JOIN SCI_LSP_LMT_PROFILE lmt  ON lmt.CMS_LSP_LMT_PROFILE_ID   = a.LIMIT_PROFILE_ID  WHERE b.TEAM_MEMBERSHIP_TYPE_ID = ?  AND a.TRANSACTION_TYPE          = f2.TRANSACTION_TYPE"
	+" AND a.TRANSACTION_TYPE          ='DISCREPENCY'  AND a.STATUS                    = f2.CURR_STATE  AND (a.FROM_STATE               = f2.FROM_STATE  OR f2.FROM_STATE               IS NULL)"
	+" AND a.TRANSACTION_TYPE          = f.STATEINS  AND (a.TRANSACTION_SUBTYPE      = f2.TRANSACTION_SUBTYPE  OR (a.TRANSACTION_SUBTYPE      IS NULL  AND f2.TRANSACTION_SUBTYPE     IS NULL))"
	+" AND f.FROMSTATE                 = a.STATUS  AND f.STATEID                   = b.STATE_ID  AND a.TEAM_ID                   = c.TEAM_ID  AND b.TEAM_MEMBERSHIP_TYPE_ID   = e.TEAM_TYPE_MEMBERSHIP_ID"
	+" AND e.TEAM_TYPE_ID              = c.TEAM_TYPE_ID  AND ((a.USER_ID                <> ?  AND a.team_membership_id       <> ?)  OR (a.team_membership_id        = ?))"
	+" AND A.TEAM_ID                   = ?  ORDER BY TRANSACTION_DATE DESC,    TRANSACTION_ID  ) todo WHERE rnum = 1) group by customer_id, customer_name, user_info, lsp_le_id, limit_profile_ref_num, transaction_type, login_id, transaction_date ";

	
	
	private static final String TODO_SELECT_BRANCH_DISCREPANCY_ADDED = "select customer_id , count(1),customer_name,user_info,lsp_le_id,limit_profile_ref_num,transaction_type, login_id ,transaction_date from (SELECT * FROM  (SELECT row_number() over(partition BY a.transaction_id order by a.transaction_id) rnum,"
			+" a.trx_reference_id,    a.transaction_id,    a.cur_trx_history_id,    a.reference_id, a.login_id,   a.transaction_type,    a.transaction_subtype,    a.status,"
			+" a.legal_id,    a.limit_profile_id,    a.limit_profile_ref_num,    a.legal_name    AS lname,    a.customer_name AS cname,    a.customer_id,    to_char(a.transaction_date,'DD-Mon-YYYY') as transaction_date , "
			+" a.trx_origin_country,    a.user_info,    b.user_action,    b.url,    b.totrack_url,    f2.user_state,    f2.user_trx_type,    f.stateid,    (SELECT country_name"
			+" FROM country    WHERE country_iso_code = a.trx_origin_country    ) country_name,    sp.lsp_id,    sp.lsp_le_id,    sp.lsp_short_name          AS customer_name,"
			+" sp.lsp_short_name          AS legal_name,    UPPER (sp.lsp_short_name ) AS upper_legal_name,    ' '                        AS task_flag,    a.deal_no  FROM CMS_STATEMATRIX_ACTION b,"
			+" TR_STATE_MATRIX f,    CMS_TRX_TOTRACK f2,    CMS_TEAM c,    CMS_TEAM_TYPE_MEMBERSHIP e,    TRANSACTION a  LEFT OUTER JOIN SCI_LE_SUB_PROFILE sp  ON sp.CMS_LE_SUB_PROFILE_ID = a.CUSTOMER_ID"
			+" LEFT OUTER JOIN SCI_LSP_LMT_PROFILE lmt  ON lmt.CMS_LSP_LMT_PROFILE_ID   = a.LIMIT_PROFILE_ID  WHERE b.TEAM_MEMBERSHIP_TYPE_ID IN (? , ?)  AND a.TRANSACTION_TYPE          = f2.TRANSACTION_TYPE"
			+" AND a.TRANSACTION_TYPE          ='DISCREPENCY'  AND a.STATUS                    = f2.CURR_STATE  AND (a.FROM_STATE               = f2.FROM_STATE  OR f2.FROM_STATE               IS NULL)"
			+" AND a.TRANSACTION_TYPE          = f.STATEINS  AND (a.TRANSACTION_SUBTYPE      = f2.TRANSACTION_SUBTYPE  OR (a.TRANSACTION_SUBTYPE      IS NULL  AND f2.TRANSACTION_SUBTYPE     IS NULL))"
			+" AND f.FROMSTATE                 = a.STATUS  AND f.STATEID                   = b.STATE_ID  AND a.TEAM_ID                   = c.TEAM_ID  AND b.TEAM_MEMBERSHIP_TYPE_ID   = e.TEAM_TYPE_MEMBERSHIP_ID"
			+" AND e.TEAM_TYPE_ID              = c.TEAM_TYPE_ID  AND ((a.USER_ID                <> ?  AND a.team_membership_id       <> ?)  OR (a.team_membership_id        IN (? , ?)))"
			+" AND A.TEAM_ID                   IN (? , ?)  "
			+"  AND ((b.TEAM_MEMBERSHIP_TYPE_ID = ?) OR (b.TEAM_MEMBERSHIP_TYPE_ID = ? AND a.TRANSACTION_TYPE = ? )) "
			+ " ORDER BY TRANSACTION_DATE DESC,    TRANSACTION_ID  ) todo WHERE rnum = 1) group by customer_id, customer_name, user_info, lsp_le_id, limit_profile_ref_num, transaction_type, login_id, transaction_date ";
	
	
	
	private static final String TODO_SELECT_BULK = "select * from cms_stage_discrepency ds,(  SELECT staging_reference_id,transaction_id, remarks as trans_remarks  FROM    (SELECT *    FROM   "
	+" (SELECT row_number() over(partition BY a.transaction_id order by a.transaction_id) rnum,        a.trx_reference_id,        a.transaction_id, a.remarks, "
	+" a.cur_trx_history_id,        a.reference_id,        a.transaction_type,        a.transaction_subtype,"
	+" a.status,         a.staging_reference_id,        a.legal_id,        a.limit_profile_id,        a.limit_profile_ref_num,   "
	+" a.legal_name    AS lname,        a.customer_name AS cname,        a.customer_id,        a.transaction_date,        a.trx_origin_country,        a.user_info,   "
	+" b.user_action,        b.url,        b.totrack_url,  f2.user_state,"
	+" f2.user_trx_type,        f.stateid,        (SELECT country_name        FROM country        WHERE country_iso_code = a.trx_origin_country        ) country_name,  "
	+" sp.lsp_id,"
	+" sp.lsp_le_id,        sp.lsp_short_name          AS customer_name,        sp.lsp_short_name          AS legal_name,        UPPER (sp.lsp_short_name ) AS upper_legal_name,  ' '       "
	+" AS task_flag,        a.deal_no      FROM CMS_STATEMATRIX_ACTION b,        TR_STATE_MATRIX f,        CMS_TRX_TOTRACK f2,        CMS_TEAM c,        CMS_TEAM_TYPE_MEMBERSHIP e,    "
	+" TRANSACTION a      LEFT OUTER JOIN SCI_LE_SUB_PROFILE sp      ON sp.CMS_LE_SUB_PROFILE_ID = a.CUSTOMER_ID      LEFT OUTER JOIN SCI_LSP_LMT_PROFILE lmt    "
	+" ON lmt.CMS_LSP_LMT_PROFILE_ID   = a.LIMIT_PROFILE_ID      WHERE b.TEAM_MEMBERSHIP_TYPE_ID = ? "
	+" AND a.TRANSACTION_TYPE          = f2.TRANSACTION_TYPE      AND a.TRANSACTION_TYPE          ='DISCREPENCY'   AND a.login_id          =?  AND to_char(a.transaction_date)          =to_char(to_date(?,'dd-Mon-yy'))      AND a.STATUS                    = f2.CURR_STATE"
	+" AND (a.FROM_STATE               = f2.FROM_STATE      OR f2.FROM_STATE               IS NULL)      AND a.TRANSACTION_TYPE          = f.STATEINS"
	+" AND (a.TRANSACTION_SUBTYPE      = f2.TRANSACTION_SUBTYPE      OR (a.TRANSACTION_SUBTYPE      IS NULL      AND f2.TRANSACTION_SUBTYPE     IS NULL))"
	+" AND f.FROMSTATE                 = a.STATUS      AND f.STATEID                   = b.STATE_ID      AND a.TEAM_ID                   = c.TEAM_ID"
	+" AND b.TEAM_MEMBERSHIP_TYPE_ID   = e.TEAM_TYPE_MEMBERSHIP_ID      AND e.TEAM_TYPE_ID              = c.TEAM_TYPE_ID"
	+" and a.customer_id               = ?      AND ((a.USER_ID                <> ?      AND a.team_membership_id       <> ?)"
	+" OR (a.team_membership_id        = ?))      AND A.TEAM_ID                   = ?      ORDER BY TRANSACTION_DATE DESC,"
	+" TRANSACTION_ID      ) todo    WHERE rnum = 1    ) ) temp where temp.staging_reference_id=ds.id";

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
			+ "      AND a.STATUS = f2.CURR_STATE "
			+ "      AND (a.FROM_STATE = f2.FROM_STATE OR f2.FROM_STATE IS NULL) "
			+ "      AND a.TRANSACTION_TYPE = f.STATEINS "
			+ "      AND (a.TRANSACTION_SUBTYPE = f2.TRANSACTION_SUBTYPE OR (a.TRANSACTION_SUBTYPE IS NULL AND f2.TRANSACTION_SUBTYPE IS NULL)) "
			+ "      AND f.FROMSTATE = a.STATUS " + "      AND f.STATEID = b.STATE_ID "
			+ "      AND a.TEAM_ID = c.TEAM_ID " + "      AND b.TEAM_MEMBERSHIP_TYPE_ID = e.TEAM_TYPE_MEMBERSHIP_ID "
			+ "      AND e.TEAM_TYPE_ID = c.TEAM_TYPE_ID " + "		 AND ((a.USER_ID <> ? AND a.team_membership_id <> ?)"
			+ "      OR  (a.team_membership_id = ?))";
	
	
	private static final String TODO_SELECT_BY_CRITERIA = "select customer_id , count(1),customer_name,user_info,lsp_le_id,limit_profile_ref_num,transaction_type, login_id ,transaction_date from (SELECT * FROM  (SELECT row_number() over(partition BY a.transaction_id order by a.transaction_id) rnum,"
		+" a.trx_reference_id,    a.transaction_id,    a.cur_trx_history_id,    a.reference_id, a.login_id,   a.transaction_type,    a.transaction_subtype,    a.status,"
		+" a.legal_id,    a.limit_profile_id,    a.limit_profile_ref_num,    a.legal_name    AS lname,    a.customer_name AS cname,    a.customer_id,    to_char(a.transaction_date,'DD-Mon-YYYY') as transaction_date , "
		+" a.trx_origin_country,    a.user_info,    b.user_action,    b.url,    b.totrack_url,    f2.user_state,    f2.user_trx_type,    f.stateid,    (SELECT country_name"
		+" FROM country    WHERE country_iso_code = a.trx_origin_country    ) country_name,    sp.lsp_id,    sp.lsp_le_id,    sp.lsp_short_name          AS customer_name,"
		+" sp.lsp_short_name          AS legal_name,    UPPER (sp.lsp_short_name ) AS upper_legal_name,    ' '                        AS task_flag,    a.deal_no  FROM CMS_STATEMATRIX_ACTION b,"
		+" TR_STATE_MATRIX f,    CMS_TRX_TOTRACK f2,    CMS_TEAM c,    CMS_TEAM_TYPE_MEMBERSHIP e,    TRANSACTION a  LEFT OUTER JOIN SCI_LE_SUB_PROFILE sp  ON sp.CMS_LE_SUB_PROFILE_ID = a.CUSTOMER_ID"
		+" LEFT OUTER JOIN SCI_LSP_LMT_PROFILE lmt  ON lmt.CMS_LSP_LMT_PROFILE_ID   = a.LIMIT_PROFILE_ID  WHERE b.TEAM_MEMBERSHIP_TYPE_ID = ?  AND a.TRANSACTION_TYPE          = f2.TRANSACTION_TYPE"
		+" AND a.TRANSACTION_TYPE          ='DISCREPENCY'  AND a.STATUS                    = f2.CURR_STATE  AND (a.FROM_STATE               = f2.FROM_STATE  OR f2.FROM_STATE               IS NULL)"
		+" AND a.TRANSACTION_TYPE          = f.STATEINS  AND (a.TRANSACTION_SUBTYPE      = f2.TRANSACTION_SUBTYPE  OR (a.TRANSACTION_SUBTYPE      IS NULL  AND f2.TRANSACTION_SUBTYPE     IS NULL))"
		+" AND f.FROMSTATE                 = a.STATUS  AND f.STATEID                   = b.STATE_ID  AND a.TEAM_ID                   = c.TEAM_ID  AND b.TEAM_MEMBERSHIP_TYPE_ID   = e.TEAM_TYPE_MEMBERSHIP_ID"
		+" AND e.TEAM_TYPE_ID              = c.TEAM_TYPE_ID  AND ((a.USER_ID                <> ?  AND a.team_membership_id       <> ?)  OR (a.team_membership_id        = ?))"
		+" AND A.TEAM_ID                   = ?  ORDER BY TRANSACTION_DATE DESC,    TRANSACTION_ID  ) todo WHERE rnum = 1)  ";
	
	private static final String TODO_SELECT_BY_CRITERIA_NEW = "select customer_id , count(1),customer_name,user_info,lsp_le_id,limit_profile_ref_num,transaction_type, login_id ,transaction_date from (SELECT * FROM  (SELECT row_number() over(partition BY a.transaction_id order by a.transaction_id) rnum,"
			+" a.trx_reference_id,    a.transaction_id,    a.cur_trx_history_id,    a.reference_id, a.login_id,   a.transaction_type,    a.transaction_subtype,    a.status,"
			+" a.legal_id,    a.limit_profile_id,    a.limit_profile_ref_num,    a.legal_name    AS lname,    a.customer_name AS cname,    a.customer_id,    to_char(a.transaction_date,'DD-Mon-YYYY') as transaction_date , "
			+" a.trx_origin_country,    a.user_info,    b.user_action,    b.url,    b.totrack_url,    f2.user_state,    f2.user_trx_type,    f.stateid,    (SELECT country_name"
			+" FROM country    WHERE country_iso_code = a.trx_origin_country    ) country_name,    sp.lsp_id,    sp.lsp_le_id,    sp.lsp_short_name          AS customer_name,"
			+" sp.lsp_short_name          AS legal_name,    UPPER (sp.lsp_short_name ) AS upper_legal_name,    ' '                        AS task_flag,    a.deal_no  FROM CMS_STATEMATRIX_ACTION b,"
			+" TR_STATE_MATRIX f,    CMS_TRX_TOTRACK f2,    CMS_TEAM c,    CMS_TEAM_TYPE_MEMBERSHIP e,    TRANSACTION a  LEFT OUTER JOIN SCI_LE_SUB_PROFILE sp  ON sp.CMS_LE_SUB_PROFILE_ID = a.CUSTOMER_ID"
			+" LEFT OUTER JOIN SCI_LSP_LMT_PROFILE lmt  ON lmt.CMS_LSP_LMT_PROFILE_ID   = a.LIMIT_PROFILE_ID  WHERE b.TEAM_MEMBERSHIP_TYPE_ID IN (? , ?)  AND a.TRANSACTION_TYPE          = f2.TRANSACTION_TYPE"
			+" AND a.TRANSACTION_TYPE          ='DISCREPENCY'  AND a.STATUS                    = f2.CURR_STATE  AND (a.FROM_STATE               = f2.FROM_STATE  OR f2.FROM_STATE               IS NULL)"
			+" AND a.TRANSACTION_TYPE          = f.STATEINS  AND (a.TRANSACTION_SUBTYPE      = f2.TRANSACTION_SUBTYPE  OR (a.TRANSACTION_SUBTYPE      IS NULL  AND f2.TRANSACTION_SUBTYPE     IS NULL))"
			+" AND f.FROMSTATE                 = a.STATUS  AND f.STATEID                   = b.STATE_ID  AND a.TEAM_ID                   = c.TEAM_ID  AND b.TEAM_MEMBERSHIP_TYPE_ID   = e.TEAM_TYPE_MEMBERSHIP_ID"
			+" AND e.TEAM_TYPE_ID              = c.TEAM_TYPE_ID  AND ((a.USER_ID                <> ?  AND a.team_membership_id       <> ?)  OR (a.team_membership_id        IN (? , ?)))"
			+" AND A.TEAM_ID                  IN (? , ?)  "
			+ "  AND ((b.TEAM_MEMBERSHIP_TYPE_ID = ?) OR (b.TEAM_MEMBERSHIP_TYPE_ID = ? AND a.TRANSACTION_TYPE = ?)) "
			+ " ORDER BY TRANSACTION_DATE DESC,    TRANSACTION_ID  ) todo WHERE rnum = 1)  ";

		
		
	
	//-----------------------------------------------------------------------------------------------
	
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
	public HashMap searchBulkTransactions(final CMSTrxSearchCriteria criteria,long customerId) throws SearchDAOException {
		

		DefaultLogger.info(this, "IN method searchBulkTransactions");

		long start = System.currentTimeMillis();

		List paramList = new ArrayList();
		paramList.add(new Long(criteria.getTeamTypeMembershipID()));
		paramList.add(criteria.getLoginID());
		paramList.add(criteria.getTransactionDate());
		paramList.add(new Long(customerId));
		paramList.add(new Long(criteria.getUserID()));
		paramList.add(new Long(criteria.getTeamMembershipID()));
		paramList.add(new Long(criteria.getTeamMembershipID()));
		paramList.add(new Long(criteria.getTeamID()));
		
		String sql = TODO_SELECT_BULK;
		
		System.out.println("paramList=>"+paramList);
		System.out.println("DiscrepancyJdbcImpl.java=> searchBulkTransactions() => sql=>"+sql);

		try {
			return (HashMap) getJdbcTemplate().query(sql, paramList.toArray(), new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException,
						org.springframework.dao.DataAccessException {
					return processTrxResultSetBulk(criteria, rs);
				}
			});
		}
		finally {
			time(start, "searchTodoTransactions");
		}
	}
	
	public SearchResult searchAllTransactions(final CMSTrxSearchCriteria criteria) throws SearchDAOException {

		List paramList = new ArrayList();

		DefaultLogger.info(this, "IN method searchAllTransactions");
		long start = System.currentTimeMillis();

		String theSQL = "";
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
	
	private SearchResult searchTodoTransactions(final CMSTrxSearchCriteria criteria) throws SearchDAOException {

		DefaultLogger.info(this, "IN method searchTodoTransactions");

		long start = System.currentTimeMillis();
		String sql = "";
		List paramList = new ArrayList();
		
		String teamid = Long.toString(criteria.getTeamTypeMembershipID());
		String teamIdCPUADM = Integer.toString(ICMSConstant.TEAM_TYPE_SSC_CHECKER);
		/*if (((teamIdCPUADM).equals(teamid))) {

			int teamId = ICMSConstant.TEAM_TYPE_BRANCH_MAKER;

			long teamMembershipIdForBranchUser = getTeamMemberShipIdForBranchUser(teamId);
			long teamIdForBranchUser = getTeamIdForBranchUser(teamId);
			System.out.println("DISCREPENCY =>teamMembershipIdForBranchUser =>" + teamMembershipIdForBranchUser
					+ " teamIdForBranchUser=>" + teamIdForBranchUser);

			paramList.add(new Long(criteria.getTeamTypeMembershipID()));
			paramList.add(new Long(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER)); // BRACH CHECKER USER TeamTypeMembershipID
			paramList.add(new Long(criteria.getUserID()));
			paramList.add(new Long(criteria.getTeamMembershipID()));
			paramList.add(new Long(criteria.getTeamMembershipID()));
			paramList.add(new Long(teamMembershipIdForBranchUser)); // BRANCH USER TeamMembershipID
			paramList.add(new Long(criteria.getTeamID()));
			paramList.add(new Long(teamIdForBranchUser));
			paramList.add(new Long(criteria.getTeamTypeMembershipID()));
			paramList.add(new Long(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER)); // BRACH CHECKER USER TeamTypeMembershipID
			paramList.add(ICMSConstant.INSTANCE_DISCREPENCY); // TRANSACTION TYPE ADDED THIS CONDITION FOR BRANCH USER
																// RECORDS 'DISCREPENCY'
			
			sql = TODO_SELECT_BRANCH_DISCREPANCY_ADDED; //TODO_SELECT_BRANCH_DISCREPANCY_ADDED
		} else {*/

			paramList.add(new Long(criteria.getTeamTypeMembershipID()));
			paramList.add(new Long(criteria.getUserID()));
			paramList.add(new Long(criteria.getTeamMembershipID()));
			paramList.add(new Long(criteria.getTeamMembershipID()));
			paramList.add(new Long(criteria.getTeamID()));
			
			sql = TODO_SELECT;
//		}
//		sql = TODO_SELECT; //TODO_SELECT_BRANCH_DISCREPANCY_ADDED
		System.out.println("DiscrepancyJdbcImpl.java=>SearchResult searchTodoTransactions()=>sql=>"+sql);
		System.out.println("DiscrepancyJdbcImpl.java=>SearchResult searchTodoTransactions()=>paramList=>"+paramList);
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
	
	private SearchResult searchTotrackTransactions(final CMSTrxSearchCriteria criteria) throws SearchDAOException {

		long start = System.currentTimeMillis();

		List paramList = new ArrayList();

		paramList.clear();
		String theSQL = "";

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
	

	
	private SearchResult processTrxResultSet(CMSTrxSearchCriteria criteria, ResultSet rs, final boolean isWithURLs,
			final boolean isAllQuery) throws SQLException {
		
		final boolean isTodoQuery = ICMSConstant.TODO_ACTION.equals(criteria.getSearchIndicator());

		SearchResult result = SearchResultSetProcessUtils.processResultSet(criteria, rs,
				new SearchResultSetProcessUtils.ResultSetProcessAction() {

					public Object doInResultSet(ResultSet rs) throws SQLException {
						 DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
						OBCMSTrxSearchResult sr = new OBCMSTrxSearchResult();
						//sr.setTransactionID(rs.getString("TRANSACTION_ID"));
						//sr.setReferenceID(rs.getString("REFERENCE_ID"));
						sr.setTransactionType(rs.getString("TRANSACTION_TYPE"));
						
						if(sr.getTransactionType().equalsIgnoreCase("COL"))
							sr.setCustomerName(rs.getString("CNAME"));
						else
							sr.setCustomerName(rs.getString("CUSTOMER_NAME"));
						
						if (sr.getCustomerName() == null) {
							sr.setCustomerName(rs.getString("CNAME"));
						}
						//sr.setLegalName(rs.getString("LEGAL_NAME"));
						/*if (sr.getLegalName() == null) {
							sr.setLegalName(rs.getString("LNAME"));
						}*/

						/*sr.setLeID(rs.getString("LEGAL_ID"));
						sr.setOriginatingLocation(rs.getString("COUNTRY_NAME"));*/
						//sr.setStatus(rs.getString("STATUS"));
						sr.setSubProfileID(rs.getString("CUSTOMER_ID"));
						
						try {
							sr.setTransactionDate(df.parse(rs.getString("TRANSACTION_DATE")));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
						//sr.setTransactionSubType(rs.getString("TRANSACTION_SUBTYPE"));
						//sr.setLimitProfileID(rs.getString("LIMIT_PROFILE_ID"));
						sr.setLimitProfileReferenceNumber(rs.getString("LIMIT_PROFILE_REF_NUM"));
						//sr.setUserState(rs.getString("USER_STATE"));
						//sr.setTrxHistoryID(rs.getString("CUR_TRX_HISTORY_ID"));
						//sr.setUserTransactionType(rs.getString("USER_TRX_TYPE"));
						//sr.setDealNo(rs.getString("DEAL_NO"));
						sr.setSciLegalID(rs.getString("LSP_LE_ID"));
						//sr.setSciSubprofileID(rs.getLong("LSP_ID"));
						sr.setActionUrls(new HashMap());
						sr.setTotrackActionUrls(new HashMap());
						sr.setFam("-");
						sr.setUserInfo(rs.getString("USER_INFO"));
						sr.setLogin_id(rs.getString("LOGIN_ID"));
						/*if (isAllQuery) {
							sr.setSciApprovedDate(rs.getDate("LLP_BCA_REF_APPR_DATE"));
							sr.setTransactionDate(rs.getDate("CMS_BCA_CREATE_DATE"));
						}*/

						if (!isTodoQuery) {
							sr.getTotrackActionUrls().put("View", rs.getString("TOTRACK_URL"));
						}

						//String taskFlag = rs.getString("TASK_FLAG");
					/*	if ("S".equals(taskFlag)) {
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
						}*/

						return sr;
					}
				});

		
		return result;
	}
	private HashMap processTrxResultSetBulk(CMSTrxSearchCriteria criteria, ResultSet rs) throws SQLException {

		HashMap result = new HashMap();
		HashMap infoMap = new HashMap();
		HashMap remarkMap = new HashMap();
		ArrayList resultList = new ArrayList();
		while(rs.next()) {
						OBDiscrepency sr = new OBDiscrepency();
						sr.setAcceptedDate(rs.getDate("ACCEPTED_DATE"));
						sr.setCreationDate(rs.getDate("CREATION_DATE"));
						sr.setNextDueDate( rs.getDate("NEXT_DUE_DATE"));
						sr.setOriginalTargetDate(rs.getDate("ORIGINAL_TARGET_DATE"));
						sr.setRecDate(rs.getDate("RECEIVE_DATE"));
						sr.setWaiveDate(rs.getDate("WAIVE_DATE"));
						sr.setDeferDate(rs.getDate("DEFER_DATE"));
						sr.setStatus(rs.getString("STATUS"));
						sr.setCreditApprover(rs.getString("CREDIT_APPROVER"));
						sr.setDocRemarks(rs.getString("DOC_REMARKS"));
						sr.setDiscrepencyRemark(rs.getString("REMARKS"));
						sr.setDiscrepency(rs.getString("DISCREPENCY"));
						sr.setDiscrepencyType(rs.getString("DISCREPENCY_TYPE"));
						sr.setApprovedBy(rs.getString("WAIVED_BY"));
						sr.setCritical(rs.getString("CRITICAL"));
						sr.setCounter(rs.getLong("DISCREPENCY_COUNTER"));
						sr.setCustomerId(rs.getLong("CUSTOMER_ID"));
						long discrepancyId=rs.getLong("ID");
						sr.setId(discrepancyId);
						sr.setTotalDeferedDays(rs.getString("TOTAL_DEFERED_DAYS"));
						sr.setOriginalDeferedDays(rs.getString("ORG_DATE_DEFERED_DAYS"));
						sr.setDeferedCounter(rs.getLong("DEFERED_COUNTER"));
						sr.setTransactionStatus(rs.getString("TRANSACTION_STATUS"));
						String transactionId= rs.getString("transaction_id");
						String trans_remarks= rs.getString("trans_remarks");
						String discrepancyIdString=String.valueOf(discrepancyId);
						resultList.add(sr);
						infoMap.put(discrepancyIdString, transactionId);
						remarkMap.put(discrepancyIdString, trans_remarks);
						

						
					}
		result.put("list", resultList);
		result.put("map", infoMap);
		result.put("remarks_map", remarkMap);
		

		return result;
	}
	
	
	protected SearchResult emptySearchResult() {
		return new SearchResult(0, 0, 0, Collections.EMPTY_LIST);
	}

	private void time(long start, String method) {
		DefaultLogger.info(this, "**************************************************");
		DefaultLogger.info(this, "* Method - " + method + " took " + (System.currentTimeMillis() - start) + " ms *");
		DefaultLogger.info(this, "*************************************************");
	}
	
	
	
	
	public SearchResult searchTransactionsByCriteria(CMSTrxSearchCriteria criteria) throws SearchDAOException {
			return searchTodoTransactionsByCriteria(criteria);
	}	
	
	
	private SearchResult searchTodoTransactionsByCriteria(final CMSTrxSearchCriteria criteria) throws SearchDAOException {

		DefaultLogger.info(this, "IN method searchTodoTransactionsByCriteria");

		long start = System.currentTimeMillis();

		List paramList = new ArrayList();
		paramList.add(new Long(criteria.getTeamTypeMembershipID()));
		paramList.add(new Long(criteria.getUserID()));
		paramList.add(new Long(criteria.getTeamMembershipID()));
		paramList.add(new Long(criteria.getTeamMembershipID()));
		paramList.add(new Long(criteria.getTeamID()));
		
		String sql = TODO_SELECT_BY_CRITERIA;
		String customerName=criteria.getCustomerName()!=null?criteria.getCustomerName().toUpperCase():"";
		String legalId= criteria.getLegalID()!=null?criteria.getLegalID().toUpperCase():"";
		String aaNumber= criteria.getAaNumber()!=null?criteria.getAaNumber().toUpperCase():"";
		String lastUpdatedBy= criteria.getLastUpdatedBy()!=null?criteria.getLastUpdatedBy().toUpperCase():"";
		
		
//		String condition =" where upper(lsp_le_id) like '%"+legalId+"%'  and upper(customer_name) like '%"+customerName+"%'  and upper(user_info) like '%"+lastUpdatedBy+"%' ";
		String condition =" where upper(lsp_le_id) like ?  and upper(customer_name) like ?  and upper(user_info) like ? ";
		
		paramList.add("%"+legalId+"%");
		paramList.add("%"+customerName+"%");
		paramList.add("%"+lastUpdatedBy+"%");
		
		if(!aaNumber.equals("")){
//			condition=condition+" and upper(limit_profile_ref_num) like '%"+aaNumber+"%' ";	
			condition=condition+" and upper(limit_profile_ref_num) like ? ";	
			paramList.add("%"+aaNumber+"%");
		}
		condition=condition+" group by customer_id, customer_name, user_info, lsp_le_id, limit_profile_ref_num, transaction_type, login_id ,transaction_date  ";
		sql=sql+condition;
		
		System.out.println("IN method searchTodoTransactionsByCriteria => sql=>"+sql);
		System.out.println("IN method searchTodoTransactionsByCriteria => paramList=>"+paramList);
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
	
	public SearchResult sortTransactions(final CMSTrxSearchCriteria criteria) throws SearchDAOException,
			RemoteException {
		DefaultLogger.info(this, "IN method sortTransactions");

		long start = System.currentTimeMillis();
		String sql = "";
		String teamid = Long.toString(criteria.getTeamTypeMembershipID());
		String teamIdCPUADM = Integer.toString(ICMSConstant.TEAM_TYPE_SSC_CHECKER);

		List paramList = new ArrayList();
		
		/*if((teamIdCPUADM).equals(teamid)) {
			
			int teamId = ICMSConstant.TEAM_TYPE_BRANCH_MAKER;

			long teamMembershipIdForBranchUser = getTeamMemberShipIdForBranchUser(teamId);
			long teamIdForBranchUser = getTeamIdForBranchUser(teamId);
			System.out.println("DISCREPENCY =>teamMembershipIdForBranchUser =>" + teamMembershipIdForBranchUser
					+ " teamIdForBranchUser=>" + teamIdForBranchUser);

			paramList.add(new Long(criteria.getTeamTypeMembershipID()));
			paramList.add(new Long(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER)); // BRACH CHECKER USER TeamTypeMembershipID
			paramList.add(new Long(criteria.getUserID()));
			paramList.add(new Long(criteria.getTeamMembershipID()));
			paramList.add(new Long(criteria.getTeamMembershipID()));
			paramList.add(new Long(teamMembershipIdForBranchUser)); // BRANCH USER TeamMembershipID
			paramList.add(new Long(criteria.getTeamID()));
			paramList.add(new Long(teamIdForBranchUser));
			paramList.add(new Long(criteria.getTeamTypeMembershipID()));
			paramList.add(new Long(ICMSConstant.TEAM_TYPE_BRANCH_CHECKER)); // BRACH CHECKER USER TeamTypeMembershipID
			paramList.add(ICMSConstant.INSTANCE_DISCREPENCY); // TRANSACTION TYPE ADDED THIS CONDITION FOR BRANCH USER
																// RECORDS 'DISCREPENCY'
			sql = TODO_SELECT_BY_CRITERIA_NEW;
		}
		else {*/
		paramList.add(new Long(criteria.getTeamTypeMembershipID()));
		paramList.add(new Long(criteria.getUserID()));
		paramList.add(new Long(criteria.getTeamMembershipID()));
		paramList.add(new Long(criteria.getTeamMembershipID()));
		paramList.add(new Long(criteria.getTeamID()));
		sql = TODO_SELECT_BY_CRITERIA;
//		}
//		sql = TODO_SELECT_BY_CRITERIA;
		String customerName=criteria.getCustomerName()!=null?criteria.getCustomerName().toUpperCase():"";
		String legalId= criteria.getLegalID()!=null?criteria.getLegalID().toUpperCase():"";
		String aaNumber= criteria.getAaNumber()!=null?criteria.getAaNumber().toUpperCase():"";
		String lastUpdatedBy= criteria.getLastUpdatedBy()!=null?criteria.getLastUpdatedBy().toUpperCase():"";
		
		
//		String condition =" where upper(lsp_le_id) like '%"+legalId+"%'  and upper(customer_name) like '%"+customerName+"%'  and upper(user_info) like '%"+lastUpdatedBy+"%' ";
		String condition =" where upper(lsp_le_id) like ?  and upper(customer_name) like ?  and upper(user_info) like ? ";
		paramList.add("%"+legalId+"%");
		paramList.add("%"+customerName+"%");
		paramList.add("%"+lastUpdatedBy+"%");
		
		if(!aaNumber.equals("")){
//			condition=condition+" and upper(limit_profile_ref_num) like '%"+aaNumber+"%' ";	
			condition=condition+" and upper(limit_profile_ref_num) like ? ";	
			
			paramList.add("%"+aaNumber+"%");
		}
		condition=condition+" group by customer_id, customer_name, user_info, lsp_le_id, limit_profile_ref_num, transaction_type, login_id ,transaction_date  ";
		sql=sql+condition;
		String orderByCondition="";
		
		if(criteria.getSortBy()!=null && !criteria.getSortBy().equals("")){
		orderByCondition=" ORDER BY "+criteria.getSortBy()+" "+criteria.getFilterByType()+" ";
		sql=sql+orderByCondition;
		}
		//sql=sql+orderByCondition;
		System.out.println("DiscrepancyJdbcImpl.java =>sortTransactions ()=> On Cancle event =>sql=>"+sql);
		System.out.println("DiscrepancyJdbcImpl.java =>sortTransactions ()=> On Cancle event =>paramList=>"+paramList);
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
	
	//-----------------------------------------------------------------------------------------------
	
	
	
	
	
	
	public ArrayList listDiscrepancy(long customerId) throws SearchDAOException {

		long start = System.currentTimeMillis();

		List paramList = new ArrayList();

		paramList.add(new Long(customerId));
		String theSQL = "select transaction_id from transaction trx , cms_discrepency ds where trx.transaction_type='DISCREPENCY' and  trx.reference_id =ds.id and  ds.customer_id=? order by ds.creation_date desc";

		try {
			return (ArrayList) getJdbcTemplate().query(theSQL, paramList.toArray(), new ResultSetExtractor() {

				public Object extractData(ResultSet rs) throws SQLException,
						org.springframework.dao.DataAccessException {
					return processTrxResultSetForList( rs);
				}

			});
		}
		finally {
			time(start, "searchTotrackTransactions");
		}
	}
	
	private ArrayList processTrxResultSetForList( ResultSet rs) throws SQLException {
		
		IDiscrepencyProxyManager discrepencyProxy = (IDiscrepencyProxyManager)BeanHouse.get("discrepencyProxy");
		ArrayList discrepencyList= new ArrayList();
		
		while(rs.next()){
			String trxId=rs.getString("transaction_id");
			
				discrepencyList.add(trxId);
			
			
		}


		
		return discrepencyList;
	}
	
	public List<Long> getDeferralIdsForValuation2(long custId) {
		StringBuffer sql = new StringBuffer("select id from cms_discrepency cd,common_code_category_entry ccce ")
								.append("where ccce.entry_code =  cd.discrepency and cd.customer_id =? and ccce.entry_name='Valuation Report – Second'")
								.append(" and cd.status='ACTIVE' and cd.TRANSACTION_STATUS='ACTIVE' ");
		
		DefaultLogger.debug(this, "getDeferralIdsForValuation2 sql:"+sql);
		
		return getJdbcTemplate().queryForList(sql.toString(), new Object[] { custId}, Long.class);
	}
	
	public long getTeamMemberShipIdForBranchUser(int teamID) throws SearchDAOException{
		DBUtil dbUtil = null;
		long  teamMembershipIdForBranchUser = 0;
		try {
			dbUtil = new DBUtil();
			String sql = "SELECT TEAM_MEMBERSHIP_ID FROM CMS_TEAM_MEMBER WHERE USER_ID = (SELECT USER_ID FROM CMS_USER WHERE TEAM_TYPE_MEMBERSHIP_ID = '"+teamID+"' AND ROWNUM = 1)";
			dbUtil.setSQL(sql);
			System.out.println("getTeamMemberShipIdForBranchUser() =>sql=>"+sql);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				teamMembershipIdForBranchUser=rs.getLong("TEAM_MEMBERSHIP_ID");
				System.out.println("getTeamMemberShipIdForBranchUser() for branch user=>teamMembershipIdForBranchUser=>"+teamMembershipIdForBranchUser);
			}
			rs.close();
			return teamMembershipIdForBranchUser;
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
	
	
	public long getTeamIdForBranchUser(int teamID) throws SearchDAOException{
		DBUtil dbUtil = null;
		long  teamIdForBranchUser = 0;
		try {
			dbUtil = new DBUtil();
			String sql = "SELECT TEAM_ID FROM CMS_TEAM WHERE TEAM_TYPE_ID = (SELECT DISTINCT TEAM_TYPE_ID FROM CMS_TEAM_TYPE_MEMBERSHIP WHERE TEAM_TYPE_MEMBERSHIP_ID = '"+teamID+"')";
			dbUtil.setSQL(sql);
			System.out.println("getTeamIdForBranchUser() =>sql=>"+sql);
			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				teamIdForBranchUser=rs.getLong("TEAM_ID");
				System.out.println("getTeamIdForBranchUser() for branch user=>teamIdForBranchUser=>"+teamIdForBranchUser);
			}
			rs.close();
			return teamIdForBranchUser;
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
