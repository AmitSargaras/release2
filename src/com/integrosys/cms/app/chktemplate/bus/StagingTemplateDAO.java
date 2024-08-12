/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/StagingTemplateDAO.java,v 1.7 2005/04/02 06:49:01 bxu Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * DAO for Staging Template
 * @author $Author: bxu $
 * @version $Revision: 1.7 $
 * @since $Date: 2005/04/02 06:49:01 $ Tag: $Name: $
 */

public class StagingTemplateDAO extends TemplateDAO {
	private DBUtil dbUtil;

	private static final String SELECT_TEMPLATE_TRX = "SELECT STAGE_DOCUMENT_MASTERLIST.MASTERLIST_ID, STAGE_DOCUMENT_MASTERLIST.APPLICABLE_LAW,"
			+ " STAGE_DOCUMENT_MASTERLIST.CUSTOMER_TYPE_CODE, STAGE_DOCUMENT_MASTERLIST.SECURITY_SUB_TYPE_ID,"
			+ " TRANSACTION.TRANSACTION_ID, TRANSACTION.FROM_STATE, TRANSACTION.STATUS "
			+ " FROM STAGE_DOCUMENT_MASTERLIST, TRANSACTION WHERE "
			+ " TRANSACTION.STAGING_REFERENCE_ID = STAGE_DOCUMENT_MASTERLIST.MASTERLIST_ID "
			+ " AND TRANSACTION.TRANSACTION_TYPE = 'TEMPLATE'";

	private static final String SELECT_TEMPLATE_TRX_COUNT = "SELECT COUNT(*) FROM STAGE_DOCUMENT_MASTERLIST, TRANSACTION "
			+ " WHERE TRANSACTION.STAGING_REFERENCE_ID = STAGE_DOCUMENT_MASTERLIST.MASTERLIST_ID "
			+ " AND TRANSACTION.TRANSACTION_TYPE = 'TEMPLATE'";

	/**
	 * Get get the list of Templates based on the criteria specified
	 * @param aCriteria - TemplateSearchCriteria
	 * @return SearchResult - this contains a collection of
	 *         CCTemplateSearchResultItem
	 * @throws SearchDAOException on errors
	 */
	public SearchResult searchTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException {
		if (aCriteria == null) {
			throw new SearchDAOException("The CCTemplateSearchCriteria is null !!!");
		}
		String firstSort = aCriteria.getFirstSort();
		String secondSort = aCriteria.getSecondSort();
		int startIndex = aCriteria.getStartIndex();
		int nItems = aCriteria.getNItems();

		int numTotalRecords = getSearchTemplateListCount(aCriteria);
		if (numTotalRecords == 0) {
			return null;
		}

		String sql = getSearchTemplateSQL(aCriteria);
		if (!isEmpty(sql)) {
			sql = new StringBuffer().append(SELECT_TEMPLATE_TRX).append(" AND ").append(sql).toString();
		}
		else {
			sql = SELECT_TEMPLATE_TRX;
		}
		if (!isEmpty(firstSort)) {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" ORDER BY ");
			strBuffer.append(firstSort.trim());
			if ((!isEmpty(secondSort)) && (secondSort.equalsIgnoreCase(firstSort))) {
				strBuffer.append(", ");
				strBuffer.append(secondSort.trim());
			}
			sql = new StringBuffer().append(sql).append(strBuffer).toString();
		}
		ResultSet rs;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			// skip initial rows as specified by the startIndex.
			while ((startIndex-- > 0) && rs.next()) {
				;
			}
			Vector list = processResultSet(rs, nItems);
			rs.close();
			return new SearchResult(startIndex, list.size(), numTotalRecords, list);
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in searchDocumentItemList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
			}
		}
	}

	/**
	 * Get the number of templates that satisfy the search criteria
	 * @param aCriteria - TemplateSearchCriteria
	 * @return int - the number of templates that satisfy the search criteria
	 * @throws SearchDAOException if errors
	 */
	private int getSearchTemplateListCount(TemplateSearchCriteria aCriteria) throws SearchDAOException {
		String sql = getSearchTemplateSQL(aCriteria);
		if (!isEmpty(sql)) {
			sql = new StringBuffer().append(SELECT_TEMPLATE_TRX_COUNT).append(" AND ").append(sql).toString();
		}
		else {
			sql = SELECT_TEMPLATE_TRX_COUNT;
		}
		// DefaultLogger.debug(this, sql);
		ResultSet rs;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			rs.next();
			int count = rs.getInt(1);
			rs.close();
			return count;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getSearchTemplateListCount", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getSearchTemplateListCount", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getSearchTemplateListCount", ex);
			}
		}
	}

	/**
	 * Method to form the inner sql condition, given the criteria object
	 * @param aCriteria - TemplateSearchCriteria
	 * @return String - the formatted sql condition
	 * @throws SearchDAOException if errors
	 */
	private String getSearchTemplateSQL(TemplateSearchCriteria aCriteria) throws SearchDAOException {
		if (aCriteria == null) {
			throw new SearchDAOException("Criteria is null!!!");
		}

		StringBuffer strBuffer = new StringBuffer();
		if (!isEmpty(aCriteria.getTemplateType())) {
			strBuffer.append(STAGE_TMPTBL_TEMPLATE_TYPE_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getTemplateType());
			strBuffer.append("'");
		}

		if (!isEmpty(aCriteria.getCountry())) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(STAGE_TMPTBL_COUNTRY_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getCountry());
			strBuffer.append("'");
		}
		else {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(STAGE_TMPTBL_COUNTRY_PREF);
			strBuffer.append(" IS NULL ");
		}

		if (!isEmpty(aCriteria.getCollateralType())) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(STAGE_TMPTBL_COLLATERAL_TYPE_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getCollateralType());
			strBuffer.append("'");
		}

		if (!isEmpty(aCriteria.getCollateralSubType())) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(STAGE_TMPTBL_COLLATERAL_SUB_TYPE_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getCollateralSubType());
			strBuffer.append("'");
		}

		if (!isEmpty(aCriteria.getLaw())) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(STAGE_TMPTBL_LAW_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getLaw());
			strBuffer.append("'");
		}

		if (!isEmpty(aCriteria.getLegalConstitution())) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(STAGE_TMPTBL_LEGAL_CONSTITUTION_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getLegalConstitution());
			strBuffer.append("'");
		}

		if ((aCriteria.getTrxStatusList() != null) && (aCriteria.getTrxStatusList().length > 0)) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(TRX_TBL_NAME + "." + TRXTBL_STATUS);
			strBuffer.append(" IN (");
			String[] statusList = aCriteria.getTrxStatusList();
			for (int ii = 0; ii < statusList.length; ii++) {
				strBuffer.append("'");
				strBuffer.append(statusList[ii]);
				strBuffer.append("'");
				if (ii < statusList.length - 1) {
					strBuffer.append(", ");
				}
			}
			strBuffer.append(")");
		}
		return strBuffer.toString().trim();
	}

	/**
	 * Utiloty method to check if a string value is null or empty
	 * @param aValue - String
	 * @return boolean - true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		if ((aValue != null) && (aValue.trim().length() > 0)) {
			return false;
		}
		return true;
	}

	/**
	 * Process the template search result
	 * @param aResultSet - ResultSet
	 * @param aCountRequired - int
	 * @return Vector - the list of templates from the resultset
	 * @throws SQLException if errors
	 */
	private Vector processResultSet(ResultSet aResultSet, int aCountRequired) throws SQLException {
		Vector resultList = new Vector();
		while (aResultSet.next()) {
			TemplateSearchResultItem template = new TemplateSearchResultItem();
			template.setTemplateID(aResultSet.getLong(TMPTBL_TEMPLATE_ID));
			template.setLaw(aResultSet.getString(TMPTBL_LAW));
			template.setLegalConstitution(aResultSet.getString(TMPTBL_LEGAL_CONSTITUTION));
			template.setCollateralSubType(aResultSet.getString(TMPTBL_COLLATERAL_SUB_TYPE));
			template.setTrxID(aResultSet.getString(TRXTBL_TRANSACTION_ID));
			template.setTrxFromState(aResultSet.getString(TRXTBL_FROM_STATE));
			template.setTrxStatus(aResultSet.getString(TRXTBL_STATUS));
			resultList.add(template);
			DefaultLogger.debug(this, "*********************RESULT SET SIZE********" + resultList.size());
			if (aCountRequired != 0) {
				if (aCountRequired == resultList.size()) {
					break;
				}
			}
		}
		return resultList;
	}
}
