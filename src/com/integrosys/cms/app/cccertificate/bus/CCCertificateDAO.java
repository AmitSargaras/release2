/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/CCCertificateDAO.java,v 1.18 2005/11/21 11:05:52 hshii Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * DAO for Template
 * @author $Author: hshii $
 * @version $Revision: 1.18 $
 * @since $Date: 2005/11/21 11:05:52 $ Tag: $Name: $
 */

public class CCCertificateDAO implements ICCCertificateDAO {
	private DBUtil dbUtil = null;

	private static String SELECT_ALL_CCCERT_TRX = "SELECT CCC_ID, CMS_LSP_LMT_PROFILE_ID, CMS_LE_SUB_PROFILE_ID, CMS_PLEDGOR_DTL_ID, SUB_CATEGORY, GENERATION_DATE, CREDIT_OFFICER_NAME, CREDIT_OFFICER_SIGN_NO, SENIOR_OFFICER_NAME, SENIOR_OFFICER_SIGN_NO, CMS_CCC_GENERATED.REMARKS, TRANSACTION_ID, STATUS "
			+ "FROM CMS_CCC_GENERATED, TRANSACTION WHERE TRANSACTION_TYPE = '"
			+ ICMSConstant.INSTANCE_CCC
			+ "' AND REFERENCE_ID = CCC_ID";

	private static String SELECT_CCCERT_TRX = "SELECT CMS_CCC_GENERATED.CCC_ID, CMS_CCC_GENERATED.CMS_LSP_LMT_PROFILE_ID, CMS_CCC_GENERATED.CMS_LE_SUB_PROFILE_ID, CMS_CCC_GENERATED.CMS_PLEDGOR_DTL_ID, CMS_CCC_GENERATED.SUB_CATEGORY, CMS_CCC_GENERATED.GENERATION_DATE, CMS_CCC_GENERATED.CREDIT_OFFICER_NAME, CMS_CCC_GENERATED.CREDIT_OFFICER_SIGN_NO, CMS_CCC_GENERATED.SENIOR_OFFICER_NAME, CMS_CCC_GENERATED.SENIOR_OFFICER_SIGN_NO, CMS_CCC_GENERATED.REMARKS, TRANSACTION.TRANSACTION_ID, TRANSACTION.STATUS FROM CMS_CCC_GENERATED, TRANSACTION WHERE TRANSACTION.TRANSACTION_TYPE = 'CCC' AND STATUS <> 'CLOSED' AND TRANSACTION.REFERENCE_ID = CMS_CCC_GENERATED.CCC_ID";

	private static String SELECT_STAGE_CCCERT_TRX = "SELECT STAGE_CCC_GENERATED.CCC_ID, STAGE_CCC_GENERATED.CMS_LSP_LMT_PROFILE_ID, STAGE_CCC_GENERATED.CMS_LE_SUB_PROFILE_ID, STAGE_CCC_GENERATED.CMS_PLEDGOR_DTL_ID, STAGE_CCC_GENERATED.SUB_CATEGORY, STAGE_CCC_GENERATED.GENERATION_DATE, STAGE_CCC_GENERATED.CREDIT_OFFICER_NAME, STAGE_CCC_GENERATED.CREDIT_OFFICER_SIGN_NO, STAGE_CCC_GENERATED.SENIOR_OFFICER_NAME, STAGE_CCC_GENERATED.SENIOR_OFFICER_SIGN_NO, STAGE_CCC_GENERATED.REMARKS, TRANSACTION.TRANSACTION_ID, TRANSACTION.STATUS FROM STAGE_CCC_GENERATED, TRANSACTION WHERE TRANSACTION.TRANSACTION_TYPE = 'CCC' AND STATUS <> 'CLOSED' AND TRANSACTION.STAGING_REFERENCE_ID = STAGE_CCC_GENERATED.CCC_ID";

	private static String SELECT_CCCERT_TRX_COUNT = "SELECT COUNT(*)  FROM STAGE_CCC_GENERATED, TRANSACTION WHERE TRANSACTION.TRANSACTION_TYPE = 'CCC' AND STATUS <> 'CLOSED' AND TRANSACTION.STAGING_REFERENCE_ID = STAGE_CCC_GENERATED.CCC_ID";

	/**
	 * Get a list of ccc info.
	 * 
	 * @param criteria cccertificate search criteria
	 * @return SearchResult contains a list of CCCertificateSearchResult
	 * @throws SearchDAOException on error searching the ccc
	 */
	public SearchResult getCCCbyLimitProfile(CCCertificateSearchCriteria criteria) throws SearchDAOException {
		try {
			String sql = null;

			if (criteria.isIncludeClosedCCC()) {
				sql = SELECT_ALL_CCCERT_TRX;
			}
			else {
				sql = SELECT_CCCERT_TRX;
			}

			ArrayList params = new ArrayList();
			String conditionPart = buildCCCSearchString(criteria, params);

			if (conditionPart != null) {
				sql = new StringBuffer().append(sql).append(conditionPart).toString();
			}

			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);

			CommonUtil.setSQLParams(params, dbUtil);

			ResultSet rs = dbUtil.executeQuery();
			ArrayList list = new ArrayList();
			while (rs.next()) {
				CCCertificateSearchResult ccc = new CCCertificateSearchResult();
				ccc.setCCCertID(rs.getLong(CERTBL_CCC_ID));
				ccc.setTrxID(rs.getString(TRX_ID));
				ccc.setTrxStatus(rs.getString(TRX_STATUS));
				list.add(ccc);
			}
			rs.close();
			return new SearchResult(0, 0, 0, list);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCCCbyLimitProfile", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (Exception ex) {
				throw new SearchDAOException("Exception in getCCCbyLimitProfile", ex);
			}
		}
	}

	/**
	 * Get the cc certificate ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return long - the CCC ID
	 * @throws SearchDAOException on errors
	 */
	public long getCCCID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException {

		String sql = SELECT_CCCERT_TRX;

		try {
			ArrayList params = new ArrayList();
			String conditionPart = buildCCCSearchString(aCriteria, params);

			if (conditionPart != null) {
				sql = new StringBuffer().append(sql).append(conditionPart).toString();
			}

			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);

			DefaultLogger.debug(this, "Parameters = " + params);
			CommonUtil.setSQLParams(params, dbUtil);

			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				long cccID = rs.getLong(CERTBL_CCC_ID);
				rs.close();
				return cccID;
			}
			rs.close();
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCCCID", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCCCID", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCCCID", ex);
			}
		}
	}

	/**
	 * Get the cc certificate trx ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return String - the CCC TRX ID
	 * @throws SearchDAOException on errors
	 */
	public String getCCCTrxID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException {

		String sql = SELECT_STAGE_CCCERT_TRX;

		try {
			ArrayList params = new ArrayList();
			String conditionPart = buildStageCCCSearchString(aCriteria, params);
			if (conditionPart != null) {
				sql += conditionPart;
			}

			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);

			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				String trxID = rs.getString(TRX_ID);
				rs.close();
				return trxID;
			}
			rs.close();
			return null;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCCCTrxID", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCCCTrxID", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCCCTrxID", ex);
			}
		}
	}

	/**
	 * Get the list of cc certificate trx based on the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return CCCertificateSearchResult[] - the list of cc certificate trx info
	 * @throws SearchDAOException on errors
	 */
	public CCCertificateSearchResult[] getNoOfCCCGenerated(long aLimitProfileID) throws SearchDAOException {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(" AND ");
		strBuf.append(CERTBL_LIMIT_PROFILE_ID_PREF);
		strBuf.append(" = ?");
		String sql = SELECT_CCCERT_TRX + strBuf.toString();

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, aLimitProfileID);
			ResultSet rs = dbUtil.executeQuery();
			CCCertificateSearchResult ccCert = null;
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				ccCert = new CCCertificateSearchResult();
				ccCert.setCCCertID(rs.getLong(CERTBL_CCC_ID));
				ccCert.setTrxID(rs.getString(TRX_ID));
				ccCert.setTrxStatus(rs.getString(TRX_STATUS));
				resultList.add(ccCert);
			}
			rs.close();
			return (CCCertificateSearchResult[]) resultList.toArray(new CCCertificateSearchResult[resultList.size()]);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCCCertificate", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCCCertificate", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCCCertificate", ex);
			}
		}
	}

	/**
	 * To get the number of cc certificate that satisfy the criteria
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return int - the number of cc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int getNoOfCCCertificate(CCCertificateSearchCriteria aCriteria) throws SearchDAOException {

		String sql = SELECT_CCCERT_TRX_COUNT;

		try {
			ArrayList params = new ArrayList();
			String conditionPart = buildStageCCCSearchString(aCriteria, params);
			if (conditionPart != null) {
				sql += conditionPart;
			}

			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);

			ResultSet rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getNoOfCCCertificate", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNoOfCCCertificate", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNoOfCCCertificate", ex);
			}
		}
	}

	/**
	 * to get cc certificate summary info - last ccc generation date and
	 * transaction id by checklist id
	 * @param checklistIDList of String[] type
	 * @return HashMap - checklist ID as key, and value is Object[2] => obj[0] =
	 *         last generation date, obj[1] = trx id
	 * @throws SearchDAOException on errors
	 */
	public HashMap getCCCInfo(List checklistIDList) throws SearchDAOException {
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer
				.append("SELECT CMS_CCC_GENERATED.CHECKLIST_ID, CMS_CCC_GENERATED.GENERATION_DATE, TRANSACTION.TRANSACTION_ID ");
		sqlBuffer.append("FROM CMS_CCC_GENERATED, TRANSACTION ");
		sqlBuffer.append("WHERE TRANSACTION.TRANSACTION_TYPE = 'CCC' ");
		sqlBuffer.append("AND STATUS <> 'CLOSED' ");
		sqlBuffer.append("AND TRANSACTION.REFERENCE_ID = CMS_CCC_GENERATED.CCC_ID ");
		sqlBuffer.append("AND CMS_CCC_GENERATED.CHECKLIST_ID ");

		ArrayList params = new ArrayList();

		CommonUtil.buildSQLInList(checklistIDList, sqlBuffer, params);

		String sql = sqlBuffer.toString();

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			CommonUtil.setSQLParams(params, dbUtil);

			HashMap resultMap = new HashMap();

			ResultSet rs = dbUtil.executeQuery();
			while (rs.next()) {
				Object[] obj = new Object[2];
				obj[0] = rs.getDate("GENERATION_DATE");
				obj[1] = rs.getString("TRANSACTION_ID");
				resultMap.put(rs.getString("CHECKLIST_ID"), obj);
			}
			rs.close();
			return resultMap;

		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getCCCInfo", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getCCCInfo", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getCCCInfo", ex);
			}
		}
	}

	/**
	 * helper method to test for valid values
	 * @param value
	 * @return boolean
	 */
	private static boolean isValid(long value) {
		return value != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding For CCC
	 * Staging Table
	 * 
	 * @param aCriteria
	 * @param params
	 * @return String - constructed SQL
	 */
	private String buildStageCCCSearchString(CCCertificateSearchCriteria aCriteria, ArrayList params) {
		StringBuffer buf = new StringBuffer();

		if (aCriteria == null) {
			return null;
		}

		if (isValid(aCriteria.getLimitProfileID())) {
			buf.append(" AND ");
			buf.append(STAGE_CERTBL_LIMIT_PROFILE_ID_PREF);
			buf.append(" = ?");
			params.add(new Long(aCriteria.getLimitProfileID()));
		}

		if (isValid(aCriteria.getSubProfileID())) {
			buf.append(" AND ");
			buf.append(STAGE_CERTBL_SUB_PROFILE_ID_PREF);
			buf.append(" = ?");
			params.add(new Long(aCriteria.getSubProfileID()));
		}

		if (isValid(aCriteria.getPledgorID())) {
			buf.append(" AND ");
			buf.append(STAGE_CERTBL_PLEDGOR_ID_PREF);
			buf.append(" = ?");
			params.add(new Long(aCriteria.getPledgorID()));
		}

		if (isValid(aCriteria.getCheckListID())) {
			buf.append(" AND ");
			buf.append(STAGE_CERTBL_CHECKLIST_ID_PREF);
			buf.append(" = ?");
			params.add(new Long(aCriteria.getCheckListID()));
		}

		if (aCriteria.getCategory() != null) {
			buf.append(" AND ");
			buf.append(STAGE_CERTBL_SUB_CATEGORY_PREF);
			buf.append(" = ?");
			params.add(aCriteria.getCategory());
		}

		/**
		 * construct a dynamic IN SQL list for binding
		 */

		String[] trxStatus = aCriteria.getTrxStatusList();

		if (!CommonUtil.isEmptyArray(trxStatus)) {
			buf.append(" AND ");
			buf.append(TRX_STATUS_PREF);
			CommonUtil.buildSQLInList(trxStatus, buf, params);

		}

		/*
		 * 9i JDBC if ((aCriteria.getTrxStatusList() != null) &&
		 * (aCriteria.getTrxStatusList().length > 0)) { String[] trxStatus =
		 * aCriteria.getTrxStatusList(); String trxValues =
		 * CommonUtil.arrayToDelimStr(trxStatus); buf.append(" AND ");
		 * buf.append(TRX_STATUS_PREF); buf.append(CommonUtil.SQL_STRING_SET);
		 * params.add(trxValues); }
		 */
		return buf.toString();
	}

	/**
	 * Constructs the SQL string based on the search criteria Populates the
	 * parameter list for DBUtil to perform JDBC variables binding
	 * 
	 * @param aCriteria
	 * @param params - list of parameters to be populated for binding
	 * @return String - constructed SQL
	 */
	private String buildCCCSearchString(CCCertificateSearchCriteria aCriteria, ArrayList params) {
		StringBuffer buf = new StringBuffer();

		if (aCriteria == null) {
			return null;
		}

		if (isValid(aCriteria.getLimitProfileID())) {
			buf.append(" AND ");
			buf.append(CERTBL_LIMIT_PROFILE_ID_PREF);
			buf.append(" = ?");
			params.add(new Long(aCriteria.getLimitProfileID()));
		}

		if (isValid(aCriteria.getSubProfileID())) {
			buf.append(" AND ");
			buf.append(CERTBL_SUB_PROFILE_ID_PREF);
			buf.append(" = ?");
			params.add(new Long(aCriteria.getSubProfileID()));
		}

		if (isValid(aCriteria.getPledgorID())) {
			buf.append(" AND ");
			buf.append(CERTBL_PLEDGOR_ID_PREF);
			buf.append(" = ?");
			params.add(new Long(aCriteria.getPledgorID()));
		}

		if (isValid(aCriteria.getCheckListID())) {
			buf.append(" AND ");
			buf.append(CERTBL_CHECKLIST_ID_PREF);
			buf.append(" = ?");
			params.add(new Long(aCriteria.getCheckListID()));
		}

		if (aCriteria.getCategory() != null) {
			buf.append(" AND ");
			buf.append(CERTBL_SUB_CATEGORY_PREF);
			buf.append(" = ?");
			params.add(aCriteria.getCategory());
		}

		/**
		 * construct a dynamic IN SQL list for binding
		 */

		String[] trxStatus = aCriteria.getTrxStatusList();

		if (!CommonUtil.isEmptyArray(trxStatus)) {

			buf.append(" AND ");
			buf.append(TRX_STATUS_PREF);
			CommonUtil.buildSQLInList(trxStatus, buf, params);

		}

		/*
		 * if ((aCriteria.getTrxStatusList() != null) &&
		 * (aCriteria.getTrxStatusList().length > 0)) { String[] trxStatus =
		 * aCriteria.getTrxStatusList(); String trxValues =
		 * CommonUtil.arrayToDelimStr(trxStatus); buf.append(" AND ");
		 * buf.append(TRX_STATUS_PREF); buf.append(CommonUtil.SQL_STRING_SET);
		 * params.add(trxValues); }
		 */
		return buf.toString();
	}

}
