/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/TemplateDAO.java,v 1.12 2005/04/02 06:53:45 bxu Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * DAO for Template
 * @author $Author: bxu $
 * @version $Revision: 1.12 $
 * @since $Date: 2005/04/02 06:53:45 $ Tag: $Name: $
 */

public class TemplateDAO implements ITemplateDAO, ICMSTrxTableConstants {
	private DBUtil dbUtil;

	private static final String SELECT_LAW_CUSTOMER_TYPE = "SELECT APPLICABLE_LAW.LAW_CODE, APPLICABLE_LAW.LAW_CODE_DESC, "
			+ " CUSTOMER_LEGAL_CONSTITUTION.CUSTOMER_LEGAL_CONST_CODE, "
			+ " CUSTOMER_LEGAL_CONSTITUTION.CUSTOMER_LEGAL_CONST_DESC "
			+ " FROM APPLICABLE_LAW, CUSTOMER_LEGAL_CONSTITUTION";

	private static final String SELECT_TEMPLATE_TRX = "SELECT CMS_DOCUMENT_MASTERLIST.MASTERLIST_ID, CMS_DOCUMENT_MASTERLIST.APPLICABLE_LAW,"
			+ " CMS_DOCUMENT_MASTERLIST.CUSTOMER_TYPE_CODE, SECURITY_SUB_TYPE_ID, "
			+ " TRANSACTION.TRANSACTION_ID, TRANSACTION.FROM_STATE, TRANSACTION.STATUS "
			+ " FROM CMS_DOCUMENT_MASTERLIST, TRANSACTION WHERE TRANSACTION.REFERENCE_ID = CMS_DOCUMENT_MASTERLIST.MASTERLIST_ID "
			+ " AND TRANSACTION.TRANSACTION_TYPE = 'TEMPLATE'";

	private static final String SELECT_TEMPLATE_TRX_COUNT = "SELECT COUNT(*) FROM CMS_DOCUMENT_MASTERLIST, TRANSACTION "
			+ " WHERE TRANSACTION.REFERENCE_ID = CMS_DOCUMENT_MASTERLIST.MASTERLIST_ID "
			+ " AND TRANSACTION.TRANSACTION_TYPE = 'TEMPLATE'";
	
	private static final String SEARCH_DOC_IN_MASTERLIST_ITEM = "select count(1) from cms_document_item " +
			" where masterlist_id in( " +
			" select MASTERLIST_ID from cms_document_masterlist " +
			" where security_sub_type_id in( " +
			" Select Lmt_Fac_Code From Sci_Lsp_Appr_Lmts " +
			" Where Cms_Lsp_Appr_Lmts_Id=? " +
			" and CMS_LIMIT_STATUS='ACTIVE')" +
			" and category= ? ) " +
			" and document_code=?";

	/**
	 * To get the list of law and customer types
	 * @param lawList of String type
	 * @return LawSearchResultItem[] - the list of laws and the customer tyoes
	 * @throws SearchDAOException on errors
	 */
	public LawSearchResultItem[] getLawCustomerTypes(String[] lawList) throws SearchDAOException {
		ResultSet rs;

        try {
			dbUtil = new DBUtil();
			StringBuffer sqlStr = new StringBuffer(SELECT_LAW_CUSTOMER_TYPE);

            if ((lawList != null) && (lawList.length > 0)) {
				sqlStr.append(" WHERE ");
				sqlStr.append(ALTBL_LAW_CODE_PREF);
				sqlStr.append(" IN (");
                for(int i=0; i<lawList.length; i++) {
                    sqlStr.append("'" + lawList[i] + "', ");
                }
                sqlStr.setCharAt(sqlStr.lastIndexOf(","), ')');  //replace the last ',' with ')'
            }
			sqlStr.append(" ORDER BY ");
			sqlStr.append(ALTBL_LAW_CODE_PREF);

			DefaultLogger.debug(this, ">>>>>>>>>>> getLawCustomerTypes SQL: \n" + sqlStr.toString());
			dbUtil.setSQL(sqlStr.toString());
			rs = dbUtil.executeQuery();
			ArrayList lawResultList = new ArrayList();
			ArrayList custTypeList = new ArrayList();
			LawSearchResultItem law = null;
			CustomerTypeResultItem custType = null;
			String tmpLaw = null;
            while (rs.next()) {
				if ((tmpLaw == null) || (!tmpLaw.equals(rs.getString(ALTBL_LAW_CODE)))) {
					if (tmpLaw != null) {
						law.setCustomerTypeList((CustomerTypeResultItem[]) custTypeList
								.toArray(new CustomerTypeResultItem[custTypeList.size()]));
						custTypeList = new ArrayList();
						lawResultList.add(law);
					}
					law = new LawSearchResultItem(rs.getString(ALTBL_LAW_CODE), rs.getString(ALTBL_LAW_CODE_DESC));
					tmpLaw = law.getLawCode();
				}
				custType = new CustomerTypeResultItem(rs.getString(CSTBL_CUST_TYPE_CODE), rs
						.getString(CSTBL_CUST_TYPE_DESC));
				custTypeList.add(custType);
			}
            if(law != null) {
                law.setCustomerTypeList((CustomerTypeResultItem[]) custTypeList
                        .toArray(new CustomerTypeResultItem[custTypeList.size()]));
                lawResultList.add(law);
            }
            rs.close();

            DefaultLogger.debug(this, "###########>>>>>>>>>>> lawResultList = " + lawResultList.size());
            return (LawSearchResultItem[]) lawResultList.toArray(new LawSearchResultItem[lawResultList.size()]);
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
			sql = sql + strBuffer.toString();
		}

        DefaultLogger.debug(this, ">>>>>>>>>>> searchTemplateList SQL: \n" + sql);
        ResultSet rs;
		try {
			dbUtil = new DBUtil();
			// DefaultLogger.debug(this, sql);
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
	
	// Method Added : To get MasterListId based on Facility Code
	public String searchMasterListId(String facilityCode) throws SearchDAOException {
		
		String SEARCH_MASTERLIST_ID = "SELECT CMS_DOCUMENT_MASTERLIST.MASTERLIST_ID " +
				"FROM CMS_DOCUMENT_MASTERLIST WHERE CMS_DOCUMENT_MASTERLIST.SECURITY_SUB_TYPE_ID = '"+facilityCode.trim().toUpperCase()+"'";
		ResultSet rs;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SEARCH_MASTERLIST_ID);
			rs = dbUtil.executeQuery();
			rs.next();
			String masterListID = rs.getString(1);
			rs.close();
			return masterListID;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in searchMasterListId", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in searchMasterListId", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in searchMasterListId", ex);
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
			strBuffer.append(TMPTBL_TEMPLATE_TYPE_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getTemplateType());
			strBuffer.append("'");
		}

		if (!isEmpty(aCriteria.getCountry())) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(TMPTBL_COUNTRY_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getCountry());
			strBuffer.append("'");
		}
		else {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(TMPTBL_COUNTRY_PREF);
			strBuffer.append(" IS NULL ");
		}

		if (!isEmpty(aCriteria.getCollateralType())) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(TMPTBL_COLLATERAL_TYPE_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getCollateralType());
			strBuffer.append("'");
		}

		if (!isEmpty(aCriteria.getCollateralSubType())) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(TMPTBL_COLLATERAL_SUB_TYPE_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getCollateralSubType());
			strBuffer.append("'");
		}

		if (!isEmpty(aCriteria.getLaw())) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(TMPTBL_LAW_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getLaw());
			strBuffer.append("'");
		}

		if (!isEmpty(aCriteria.getLegalConstitution())) {
			if (!isEmpty(strBuffer.toString())) {
				strBuffer.append(" AND ");
			}
			strBuffer.append(TMPTBL_LEGAL_CONSTITUTION_PREF);
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
	 * Utilty method to check if a string value is null or empty
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
			if (aCountRequired != 0) {
				if (aCountRequired == resultList.size()) {
					break;
				}
			}
		}
		return resultList;
	}
	
	// Method Added : To get Transaction Id By MasterListId from Transaction Table
	public String getTransactionIdByMasterListId(String masterListId) throws SearchDAOException {
		
		String SEARCH_TXN_ID = "SELECT TRANSACTION.TRANSACTION_ID " +
				"FROM TRANSACTION WHERE TRANSACTION.REFERENCE_ID = '"+masterListId+"'";
		ResultSet rs;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SEARCH_TXN_ID);
			rs = dbUtil.executeQuery();
			rs.next();
			String trxID = rs.getString(1);
			rs.close();
			return trxID;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getTransactionIdByMasterListId", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getTransactionIdByMasterListId", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getTransactionIdByMasterListId", ex);
			}
		}
	}
	
	/*
	 * Commented below method : Not require anymore to validate Document WSDL File
	 * 
	 * 
	 * public Boolean isDocumentFoundInMasterListItem(String mappingId,
			String docCategory,String documentCode)throws SearchDAOException {
		
		Boolean isRecFound = false;
		ResultSet rs;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SEARCH_DOC_IN_MASTERLIST_ITEM.toString());
			dbUtil.setString(1, mappingId);
			dbUtil.setString(2, docCategory.trim().toUpperCase());
			dbUtil.setString(3, documentCode);
			rs = dbUtil.executeQuery();
			
			while(rs.next()){
				int recCount = rs.getInt(1);
				if(recCount > 0){
					isRecFound = true;
				}
			}
			rs.close();
		}catch (SQLException ex) {
			throw new SearchDAOException("SQLException in isDocumentFoundInMasterListItem ", ex);
		}catch (Exception ex) {
			throw new SearchDAOException("Exception in isDocumentFoundInMasterListItem ", ex);
		}finally {
			try {
				dbUtil.close();
			}catch (SQLException ex) {
				throw new SearchDAOException("SQLException in isDocumentFoundInMasterListItem ", ex);
			}
		}
		
		return isRecFound;
	}*/
	
}
