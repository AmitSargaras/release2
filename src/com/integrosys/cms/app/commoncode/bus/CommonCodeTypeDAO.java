package com.integrosys.cms.app.commoncode.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CommonCodeTypeDAO implements ICMSTrxTableConstants {
	private DBUtil dbUtil;

	private static final String SELECT_COMMON_CATEOGORY_CODE = "SELECT COMMON_CODE_CATEGORY.CATEGORY_ID, COMMON_CODE_CATEGORY.CATEGORY_CODE, "
			+ " COMMON_CODE_CATEGORY.CATEGORY_NAME, COMMON_CODE_CATEGORY.REF_CATEGORY_CODE, TRANSACTION.TRANSACTION_ID, TRANSACTION.STATUS "
			+ " FROM COMMON_CODE_CATEGORY, TRANSACTION WHERE COMMON_CODE_CATEGORY.CATEGORY_ID = TRANSACTION.REFERENCE_ID "
			+ " AND TRANSACTION.TRANSACTION_TYPE = '" + ICMSConstant.INSTANCE_COMMON_CODE_TYPE_LIST + "' ";

	private static final String SELECT_MAINTAIN_REF_CATEGORY_CODE = 
		"SELECT COMMON_CODE_CATEGORY.CATEGORY_ID, COMMON_CODE_CATEGORY.CATEGORY_CODE, "
		+ " COMMON_CODE_CATEGORY.CATEGORY_NAME, COMMON_CODE_CATEGORY.REF_CATEGORY_CODE, '' AS TRANSACTION_ID, '' AS STATUS "
		+ " FROM COMMON_CODE_CATEGORY "
		+ " WHERE COMMON_CODE_CATEGORY.REF_CATEGORY_CODE is not null ";
	/**
	 * Search for a list of document items based on the criteria
	 * @param aCriteria - DocumentSearchCriteria
	 * @return SearchResult - the object that contains a list of call document
	 *         items that satisfy the search criteria
	 * @throws SearchDAOException if errors
	 */
	public SearchResult searchCommonCategoryList(CommonCodeTypeSearchCriteria aCriteria) throws SearchDAOException {
		if (aCriteria == null) {
			throw new SearchDAOException("The CommonCodeTypeSearchCriteria is null !!!");
		}
		String firstSort = aCriteria.getFirstSort();
		String secondSort = aCriteria.getSecondSort();
		int startIndex = aCriteria.getStartIndex();
		int nItems = aCriteria.getNItems();

		String selectSql = "";
		if (aCriteria.isMaintainRef()) {
			selectSql = SELECT_MAINTAIN_REF_CATEGORY_CODE;
		} else {
			selectSql = SELECT_COMMON_CATEOGORY_CODE;
		}
		String sql = getSearchCommonCategoryTypeSQL(aCriteria);
		if (!isEmpty(sql)) {
			sql = selectSql +  sql;
		}
		else {
			sql = selectSql;
		}

		if (!isEmpty(firstSort)) {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" ORDER BY ");
			strBuffer.append(firstSort.trim());
			if ((!isEmpty(secondSort)) && (secondSort.equalsIgnoreCase(firstSort))) {
				strBuffer.append(", ");
				strBuffer.append(secondSort.trim());
			}
			sql = sql + strBuffer.toString();
		}

		//DefaultLogger.debug(this, "SQL to execute = " + sql);
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

			return new SearchResult(startIndex, list.size(), list.size(), list);
		}
		catch (SQLException ex) {
			//ex.printStackTrace();
			throw new SearchDAOException("SQLException in searchCommonCategoryList", ex);
		}
		catch (Exception ex) {
			//ex.printStackTrace();
			throw new SearchDAOException("Exception in searchCommonCategoryList", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchCommonCategoryList", ex);
			}
		}
	}

	/**
	 * Method to form the sql condition, given the criteria object
	 * @param aCriteria - DocumentSearchCriteria
	 * @return String - the formatted sql condition
	 * @throws SearchDAOException if errors
	 */
	private String getSearchCommonCategoryTypeSQL(CommonCodeTypeSearchCriteria aCriteria) throws SearchDAOException {
		if (aCriteria == null) {
			throw new SearchDAOException("Criteria is null!!!");
		}

		StringBuffer strBuffer = new StringBuffer();

		if (aCriteria.getCategoryType() != -1) {
			strBuffer.append(" AND COMMON_CODE_CATEGORY.CATEGORY_TYPE");
			strBuffer.append(" = ");
			strBuffer.append(aCriteria.getCategoryType());
		}
		if (aCriteria.getCategoryID() != ICMSConstant.LONG_INVALID_VALUE) {
			strBuffer.append(" AND COMMON_CODE_CATEGORY.CATEGORY_ID");
			strBuffer.append(" = ");
			strBuffer.append(aCriteria.getCategoryID());
		}
				
		if (aCriteria.getActiveStatus() != null && !"".equals(aCriteria.getActiveStatus())) {
			strBuffer.append(" AND COMMON_CODE_CATEGORY.ACTIVE_STATUS");
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getActiveStatus());
			strBuffer.append("' ");
		}
		
		/*
		if (aCriteria.isMaintainRef()) {
			strBuffer.append("COMMON_CODE_CATEGORY.REF_CATEGORY_CODE is not null ");
		}
		*/
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
	 * Process the document item search result
	 * @param aResultSet - ResultSet
	 * @param aCountRequired - int
	 * @return Vector - the list of document items from the resultset
	 * @throws SQLException if errors
	 */
	private Vector processResultSet(ResultSet aResultSet, int aCountRequired) throws SQLException {
		Vector resultList = new Vector();
		
		while (aResultSet.next()) {
			CommonCodeTypeSearchResultItem categoryItem = new CommonCodeTypeSearchResultItem();

			ICommonCodeType commonCodeType = new OBCommonCodeType();
			commonCodeType.setCommonCategoryId(aResultSet.getLong("CATEGORY_ID"));
			commonCodeType.setCommonCategoryCode(aResultSet.getString("CATEGORY_CODE"));
			commonCodeType.setCommonCategoryName(aResultSet.getString("CATEGORY_NAME"));
			commonCodeType.setRefCategoryCode(aResultSet.getString("REF_CATEGORY_CODE"));
			categoryItem.setItem(commonCodeType);
			categoryItem.setTrxID(aResultSet.getString(TRXTBL_TRANSACTION_ID));
			categoryItem.setTrxStatus(aResultSet.getString(TRXTBL_STATUS));

			resultList.add(categoryItem);

			if (aCountRequired != 0) {
				if (aCountRequired == resultList.size()) {
					break;
				}
			}
		}

		return resultList;
	}
}
