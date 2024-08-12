/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/DocumentDAO.java,v 1.13 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.util.TypeConverter;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.cms.app.documentlocation.bus.OBDocumentAppTypeItem;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * DAO for Document
 * @author $Author: czhou $
 * @version $Revision: 1.13 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */

public class DocumentDAO implements IDocumentDAO, ICMSTrxTableConstants {
	private DBUtil dbUtil;

	private static final String SELECT_DOCUMENT_ITEM = "SELECT  CMS_DOCUMENT_GLOBALLIST.DOCUMENT_ID, CMS_DOCUMENT_GLOBALLIST.DOCUMENT_CODE, "
			+ " CMS_DOCUMENT_GLOBALLIST.DOCUMENT_DESCRIPTION, CMS_DOCUMENT_GLOBALLIST.CATEGORY, "
			+ " CMS_DOCUMENT_GLOBALLIST.IS_PRE_APPROVE,CMS_DOCUMENT_GLOBALLIST.TENURE_COUNT,CMS_DOCUMENT_GLOBALLIST.SKIP_IMG_TAG,CMS_DOCUMENT_GLOBALLIST.TENURE_TYPE,CMS_DOCUMENT_GLOBALLIST.STATEMENT_TYPE,CMS_DOCUMENT_GLOBALLIST.IS_RECURRENT,CMS_DOCUMENT_GLOBALLIST.RATING,CMS_DOCUMENT_GLOBALLIST.SEGMENT,CMS_DOCUMENT_GLOBALLIST.TOTAL_SANC_AMT,CMS_DOCUMENT_GLOBALLIST.CLASSIFICATION,CMS_DOCUMENT_GLOBALLIST.GUARANTOR,CMS_DOCUMENT_GLOBALLIST.DEPRECATED,CMS_DOCUMENT_GLOBALLIST.STATUS as DocStatus, CMS_DOCUMENT_GLOBALLIST.DOC_VERSION, CMS_DOCUMENT_GLOBALLIST.EXPIRY_DATE, "
			+ " CMS_DOCUMENT_GLOBALLIST.LOAN_APP_TYPE,CMS_DOCUMENT_GLOBALLIST.IS_FOR_BORROWER,CMS_DOCUMENT_GLOBALLIST.IS_FOR_PLEDGOR,"
            + " TRANSACTION.TRANSACTION_ID, TRANSACTION.STATUS "
			+ " FROM CMS_DOCUMENT_GLOBALLIST,  TRANSACTION  WHERE CMS_DOCUMENT_GLOBALLIST.DOCUMENT_ID = TRANSACTION.REFERENCE_ID "
			+ " AND TRANSACTION.TRANSACTION_TYPE = 'DOCITEM'"
			+ " AND CMS_DOCUMENT_GLOBALLIST.DEPRECATED = 'N'"
			+ " AND CMS_DOCUMENT_GLOBALLIST.STATUS = 'ENABLE'";
			
	private static final String SELECT_DOCUMENT_ITEM_REC = "SELECT  CMS_DOCUMENT_GLOBALLIST.DOCUMENT_ID, CMS_DOCUMENT_GLOBALLIST.DOCUMENT_CODE, "
		+ " CMS_DOCUMENT_GLOBALLIST.DOCUMENT_DESCRIPTION, CMS_DOCUMENT_GLOBALLIST.CATEGORY, "
		+ " CMS_DOCUMENT_GLOBALLIST.IS_PRE_APPROVE,CMS_DOCUMENT_GLOBALLIST.TENURE_COUNT,CMS_DOCUMENT_GLOBALLIST.SKIP_IMG_TAG,CMS_DOCUMENT_GLOBALLIST.TENURE_TYPE,CMS_DOCUMENT_GLOBALLIST.STATEMENT_TYPE,CMS_DOCUMENT_GLOBALLIST.IS_RECURRENT,CMS_DOCUMENT_GLOBALLIST.RATING,CMS_DOCUMENT_GLOBALLIST.SEGMENT,CMS_DOCUMENT_GLOBALLIST.TOTAL_SANC_AMT,CMS_DOCUMENT_GLOBALLIST.CLASSIFICATION,CMS_DOCUMENT_GLOBALLIST.GUARANTOR,CMS_DOCUMENT_GLOBALLIST.DEPRECATED,CMS_DOCUMENT_GLOBALLIST.STATUS as DocStatus, CMS_DOCUMENT_GLOBALLIST.DOC_VERSION, CMS_DOCUMENT_GLOBALLIST.EXPIRY_DATE, "
		+ " CMS_DOCUMENT_GLOBALLIST.LOAN_APP_TYPE,CMS_DOCUMENT_GLOBALLIST.IS_FOR_BORROWER,CMS_DOCUMENT_GLOBALLIST.IS_FOR_PLEDGOR,"
        + " TRANSACTION.TRANSACTION_ID, TRANSACTION.STATUS "
		+ " FROM CMS_DOCUMENT_GLOBALLIST,  TRANSACTION , COMMON_CODE_CATEGORY_ENTRY WHERE CMS_DOCUMENT_GLOBALLIST.DOCUMENT_ID = TRANSACTION.REFERENCE_ID "
		+ " AND TRANSACTION.TRANSACTION_TYPE = 'DOCITEM'"
		+ " AND CMS_DOCUMENT_GLOBALLIST.DEPRECATED = 'N'"
		+ " AND CMS_DOCUMENT_GLOBALLIST.STATUS = 'ENABLE'"
		+ " AND common_code_category_entry.entry_code=CMS_DOCUMENT_GLOBALLIST.STATEMENT_TYPE"
	    + " AND common_code_category_entry.category_code='STATEMENT_TYPE' ";
//			For Db2
//          + " AND (CMS_DOCUMENT_GLOBALLIST.EXPIRY_DATE IS NULL OR DATE(CMS_DOCUMENT_GLOBALLIST.EXPIRY_DATE) >= CURRENT DATE)";
//			For Oracle
	// By Abhijit R
		//	+ " AND (CMS_DOCUMENT_GLOBALLIST.EXPIRY_DATE IS NULL OR TRUNC(CMS_DOCUMENT_GLOBALLIST.EXPIRY_DATE) >= TRUNC(CURRENT_DATE))";
    //Andy Wong, 21 May 2010: display expiring template when current date = expired date 

	private static final String SELECT_DOCUMENT_ITEM_COUNT = "SELECT COUNT(*) FROM CMS_DOCUMENT_GLOBALLIST, TRANSACTION "
			+ " WHERE CMS_DOCUMENT_GLOBALLIST.DOCUMENT_ID = TRANSACTION.REFERENCE_ID"
			+ " AND CMS_DOCUMENT_GLOBALLIST.DEPRECATED = 'N'";

	private static final String SELECT_DOCUMENT_ITEM_RECORD_COUNT = "SELECT COUNT(*) FROM CMS_DOCUMENT_GLOBALLIST";

    private static final String SELECT_DOCUMENT_CODE_RECORD_COUNT = "SELECT COUNT(DOCUMENT_ID) FROM CMS_DOCUMENT_GLOBALLIST WHERE UPPER(DOCUMENT_CODE) = ? ";

    private static final String SELECT_DYNAMIC_PROPERTY_SETUP_ALL =
            "SELECT setup.SETUP_ID, setup.SECURITY_SUB_TYPE_ID, setup.PROPERTY_NAME, setup.PROPERTY_LABEL, " +
            "setup.CATEGORY_CODE, setup.INPUT_TYPE, entry.ENTRY_CODE, entry.ENTRY_NAME  " +
            "FROM CMS_DOC_DYNAMIC_PROPERTY_SETUP setup, COMMON_CODE_CATEGORY_ENTRY entry " +
            "WHERE setup.CATEGORY_CODE = entry.CATEGORY_CODE " +
            "AND SECURITY_SUB_TYPE_ID = ? " +
            "ORDER BY setup.SETUP_ID ";



    /**
	 * Search for a list of document items based on the criteria
	 * @param aCriteria - DocumentSearchCriteria
	 * @return SearchResult - the object that contains a list of call document
	 *         items that satisfy the search criteria
	 * @throws SearchDAOException if errors
	 */
	public SearchResult searchDocumentItemList(DocumentSearchCriteria aCriteria) throws SearchDAOException {
		if (aCriteria == null) {
			throw new SearchDAOException("The DocumentSearchCriteria is null !!!");
		}
		String firstSort = aCriteria.getFirstSort();
		String secondSort = aCriteria.getSecondSort();
		
		int startIndex = aCriteria.getStartIndex();
		int nItems = aCriteria.getNItems();

		int numTotalRecords = getSearchDocumentRecordCount(aCriteria);
		if (numTotalRecords == 0) {
			return null;
		}
		String sql = getSearchDocumentItemsSQL(aCriteria);
		if (!isEmpty(sql)) {
			if(aCriteria.getDocumentType()!=null && aCriteria.getDocumentType().trim().equalsIgnoreCase("REC")){
				sql = SELECT_DOCUMENT_ITEM_REC + " AND " + sql;
			}else{
			sql = SELECT_DOCUMENT_ITEM + " AND " + sql;
			}
		}
		else {
			if(aCriteria.getDocumentType()!=null && aCriteria.getDocumentType().trim().equalsIgnoreCase("REC")){
				sql = SELECT_DOCUMENT_ITEM_REC;
			}else{
			sql = SELECT_DOCUMENT_ITEM;
			}
		}
		if(aCriteria.getDocumentType()!=null && aCriteria.getDocumentType().trim().equalsIgnoreCase("REC")){
			//firstSort =" common_code_category_entry.entry_name ";
			//secondSort =" CMS_DOCUMENT_GLOBALLIST.DOCUMENT_DESCRIPTION ";
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" ORDER BY ");
			strBuffer.append(" common_code_category_entry.entry_name ");
				strBuffer.append(", ");
				strBuffer.append(" CMS_DOCUMENT_GLOBALLIST.DOCUMENT_DESCRIPTION ");
				strBuffer.append(", ");
				strBuffer.append(" CMS_DOCUMENT_GLOBALLIST.EXPIRY_DATE ");
			sql = sql + strBuffer.toString();
				
		}else if (!isEmpty(firstSort)) {
			StringBuffer strBuffer = new StringBuffer();
			strBuffer.append(" ORDER BY ");
			strBuffer.append(firstSort.trim());
			if ((!isEmpty(secondSort)) && (!secondSort.equalsIgnoreCase(firstSort))) {
				strBuffer.append(", ");
				strBuffer.append(secondSort.trim());
			}
			sql = sql + strBuffer.toString();
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
			rs.getFetchSize();
			Vector list = processResultSet(rs, nItems);
			rs.close();
			return new SearchResult(0, list.size(), list.size(), list);
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

	
	public String[] searchDocumentItemByCode(String code) throws SearchDAOException {
		String[] tenure= new String[2];
 		String sql = "select tenure_count,tenure_type from cms_document_globallist where document_code='"+code+"'";
		ResultSet rs;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			// skip initial rows as specified by the startIndex.
			while ( rs.next()) {
				tenure[0]=rs.getString("tenure_count");
				tenure[1]=rs.getString("tenure_type");
			}
			rs.close();
			return tenure;
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
	 * Get the number of document items that satisfy the search criteria
	 * @param aCriteria - DocumentSearchCriteria
	 * @return int - the number of document items that satisfy the search
	 *         criteria
	 * @throws SearchDAOException if errors
	 */
	private int getSearchDocumentRecordCount(DocumentSearchCriteria aCriteria) throws SearchDAOException {
		String sql = getSearchDocumentItemsSQL(aCriteria);
		if (!isEmpty(sql)) {
			sql = SELECT_DOCUMENT_ITEM_COUNT + " AND " + sql;
		}
		else {
			sql = SELECT_DOCUMENT_ITEM_COUNT;
		}
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
			throw new SearchDAOException("SQLException in getSearchDocumentRecordCount", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getSearchDocumentRecordCount", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getSearchDocumentRecordCount", ex);
			}
		}
	}
	
	/**
	 * Get the number of doc item under the same category and having the same
	 * description
	 * @param aCategory of String type
	 * @param aDescription of String type
	 * @return int - the number of doc items
	 * @throws SearchDAOException
	 */
	public int getNoOfDocItemByDesc(String aCategory, String aDescription) throws SearchDAOException {

		StringBuffer criteria = new StringBuffer();
		if (!isEmpty(aCategory)) {
			criteria.append(" WHERE ");
			criteria.append(DOCITEMTBL_ITEM_CATEGORY_PREF);
			criteria.append(" = '");
			criteria.append(aCategory);
			criteria.append("'");
		}

		if (!isEmpty(aDescription)) {
			if (isEmpty(criteria.toString())) {
				criteria.append(" WHERE ");
			}
			else {
				criteria.append(" AND ");
			}
			criteria.append(DOCITEMTBL_ITEM_DESC_PREF);
			criteria.append(" = '");
			criteria.append(aDescription);
			criteria.append("'");
		}

		String sql = SELECT_DOCUMENT_ITEM_RECORD_COUNT + criteria.toString();

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
			throw new SearchDAOException("SQLException in getNoOfDocItemByDesc", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getNoOfDocItemByDesc", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getNoOfDocItemByDesc", ex);
			}
		}
	}

	/**
	 * Method to form the sql condition, given the criteria object
	 * @param aCriteria - DocumentSearchCriteria
	 * @return String - the formatted sql condition
	 * @throws SearchDAOException if errors
	 */
	private String getSearchDocumentItemsSQL(DocumentSearchCriteria aCriteria) throws SearchDAOException {
		if (aCriteria == null) {
			throw new SearchDAOException("Criteria is null!!!");
		}

		StringBuffer strBuffer = new StringBuffer();
		if (!isEmpty(aCriteria.getDocumentType())) {
			strBuffer.append(DOCITEMTBL_ITEM_CATEGORY_PREF);
			strBuffer.append(" = '");
			strBuffer.append(aCriteria.getDocumentType());
			strBuffer.append("'");
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
	 * Process the document item search result
	 * @param aResultSet - ResultSet
	 * @param aCountRequired - int
	 * @return Vector - the list of document items from the resultset
	 * @throws SQLException if errors
	 */
	private Vector processResultSet(ResultSet aResultSet, int aCountRequired) throws SQLException {
		Vector resultList = new Vector();
		List docAppType;
		long currentItemId;
		
		while (aResultSet.next()) {
			DocumentSearchResultItem docItem = new DocumentSearchResultItem();
			IItem item = new OBItem();
			
			
			

			item.setItemID(aResultSet.getLong(DOCITEMTBL_ITEM_ID));
			item.setItemCode(aResultSet.getString(DOCITEMTBL_ITEM_CODE));
			item.setItemDesc(aResultSet.getString(DOCITEMTBL_ITEM_DESC));
			item.setItemType(aResultSet.getString(DOCITEMTBL_ITEM_CATEGORY));
			item.setExpiryDate(aResultSet.getDate(DOCITEMTBL_EXPIRY_DATE));
            item.setDocumentVersion(aResultSet.getString(DOCITEMTBL_DOC_VERSION));
            item.setIsPreApprove(TypeConverter.convertStringToBooleanEquivalent(aResultSet.getString(DOCITEMTBL_PRE_APPROVE)));
            item.setTenureCount(aResultSet.getInt(DOCITEMTBL_TENURE_COUNT));
            item.setTenureType(aResultSet.getString(DOCITEMTBL_TENURE_TYPE));
            item.setSkipImgTag(aResultSet.getString(DOCITEMTBL_SKIP_IMG_TAG));
            item.setStatementType(aResultSet.getString(DOCITEMTBL_STATEMENT_TYPE));
            item.setIsRecurrent(aResultSet.getString(DOCITEMTBL_IS_RECURRENT));
            item.setRating(aResultSet.getString(DOCITEMTBL_RATING));
            item.setSegment(aResultSet.getString(DOCITEMTBL_SEGMENT));
            item.setTotalSancAmt(aResultSet.getString(DOCITEMTBL_TOTAL_SANC_AMT));
            item.setClassification(aResultSet.getString(DOCITEMTBL_CLASSIFICATION));
            item.setGuarantor(aResultSet.getString(DOCITEMTBL_GUARANTOR));
            item.setDeprecated(aResultSet.getString(DOCITEMTBL_DEPRECATED));
            item.setStatus(aResultSet.getString("DocStatus"));
           // item.setLoanApplicationType(aResultSet.getString(LOAN_APPLICATION_TYPE));
            item.setIsForBorrower(TypeConverter.convertStringToBooleanEquivalent(aResultSet.getString(DOCITEMTBL_FOR_BORROWER)));
            item.setIsForPledgor(TypeConverter.convertStringToBooleanEquivalent(aResultSet.getString(DOCITEMTBL_FOR_PLEDGOR)));
            //item.setLoanApplicationType(aResultSet.getString(DOCAPPTYPEITEMTBL_APP_TYPE));
            docItem.setItem(item);
			docItem.setTrxID(aResultSet.getString(TRXTBL_TRANSACTION_ID));
			docItem.setTrxStatus(aResultSet.getString(TRXTBL_STATUS));
			resultList.add(docItem);
			//DefaultLogger.debug(this, "*********************RESULT SET SIZE********" + resultList.size());
			if (aCountRequired != 0) {
				if (aCountRequired == resultList.size()) {
					break;
				}
			}
		}
		
		//resultList = groupDocumentCodeByAppType(resultList);
		
		return resultList;
	}
	
	private Vector groupDocumentCodeByAppType(Vector resultList)
	{
		String itemCode = "";
		DocumentSearchResultItem tempItem = null;
		List appList = new ArrayList();
		Vector sortedList = new Vector();
		for(int i = 0; i < resultList.size(); i++)
		{
			DocumentSearchResultItem docItem = (DocumentSearchResultItem)resultList.get(i);
			if(docItem.getItem().getItemCode().equalsIgnoreCase(itemCode))
			{
				tempItem = docItem;
				IDocumentAppTypeItem item = new OBDocumentAppTypeItem();
				//item.setAppType(docItem.getItem().getLoanApplicationType());
				item.setDocumentId(new Long(docItem.getItem().getItemID()));
				
				appList.add(item);
				if((i+1) == resultList.size())
				{
					tempItem.getItem().setCMRDocAppItemList(appList);
					sortedList.add(tempItem);
					appList = new ArrayList();
				}
			}
			else if (itemCode.length() == 0)
			{
				tempItem = docItem;
				IDocumentAppTypeItem item = new OBDocumentAppTypeItem();
				//item.setAppType(docItem.getItem().getLoanApplicationType());
				item.setDocumentId(new Long(docItem.getItem().getItemID()));
				appList.add(item);
				itemCode = docItem.getItem().getItemCode();
				//sortedList.add(tempItem);
			}
			else
			{
				
				tempItem.getItem().setCMRDocAppItemList(appList);
				sortedList.add(tempItem);
				tempItem = docItem;
				appList = new ArrayList();
				IDocumentAppTypeItem item = new OBDocumentAppTypeItem();
				//item.setAppType(docItem.getItem().getLoanApplicationType());
				item.setDocumentId(new Long(docItem.getItem().getItemID()));
				appList.add(item);
				itemCode = docItem.getItem().getItemCode();
				if((i+1) == resultList.size())
				{
					tempItem.getItem().setCMRDocAppItemList(appList);
					sortedList.add(tempItem);
					appList = new ArrayList();
				}
				
			}
		}
		
		return sortedList;
		
		
	}


    /**
     * Check that the document code entered is unique.
     * Business Rule: Even deleted document code cannot be reused.
     *
     * @param docCode - document code to be checked for uniqueness
     * @param category - category of document to check for. Takes one of these 3 values:
     *                   1. ICMSConstant.DOC_TYPE_CC - for CC
     *                   2. ICMSConstant.DOC_TYPE_SECURITY - for Security
     *                   3. null - will not check against specific category (unique for both category)
     *
     * @throws com.integrosys.base.businfra.search.SearchDAOException is errors in DAO
     * @return true if code is unique; false otherwise
     */
    public boolean getIsDocumentCodeUnique(String docCode, String category) throws SearchDAOException {
        int count = getDocumentCodeCount(docCode, category);
        return (count == 0);
    }

    //Helper method for getIsDocumentCodeUnique
    private int getDocumentCodeCount(String docCode, String category) throws SearchDAOException {

        boolean filterByCategory = (category != null);  //if category is NOT null = filtering required
        String sql = (filterByCategory) ? SELECT_DOCUMENT_CODE_RECORD_COUNT + " AND CATEGORY = ?" : SELECT_DOCUMENT_CODE_RECORD_COUNT;

        ResultSet rs;
        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(sql);
            dbUtil.setString(1, docCode.toUpperCase());

            if(filterByCategory) {
                dbUtil.setString(2, category);
            }

            rs = dbUtil.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            rs.close();
            return count;
        }
        catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getDocumentCodeCount", ex);
        }
        catch (Exception ex) {
            throw new SearchDAOException("Exception in getDocumentCodeCount", ex);
        }
        finally {
            try {
                dbUtil.close();
            }
            catch (SQLException ex) {
                throw new SearchDAOException("SQLException in getDocumentCodeCount", ex);
            }
        }
    }

    /**
     * Retrieves the set of dynamic properties for a given security subtype.
     *
     * The sql will return results in the following manner e.g.:
     *  SETUP_ID     SECURITY_SUB_TYPE_ID     PROPERTY_NAME     PROPERTY_LABEL     CATEGORY_CODE     INPUT_TYPE     ENTRY_CODE     ENTRY_NAME
     *  -----------  -----------------------  ----------------  -----------------  ----------------  -------------  -------------  -------------
     *  1            AB102                    GoodsStatus       label.goodsstatus  GOODS_STATUS      CB             N              New
     *  1            AB102                    GoodsStatus       label.goodsstatus  GOODS_STATUS      CB             R              Reconditioned
     *  1            AB102                    GoodsStatus       label.goodsstatus  GOODS_STATUS      CB             U              Used
     *  2            AB102                    PbtPbr            label.pbtpbr       PBR_PBT_IND       CB             PBT            PBT
     *  2            AB102                    PbtPbr            label.pbtpbr       PBR_PBT_IND       CB             PBR            PBR
     *
     * @param securitySubtype - security subtype to retrieve the dynamic properties for
     * @return set of dynamic properties of IDynamicPropertySetup for the given subtype
     * @throws SearchDAOException if errors during retrieval
     */
    public IDynamicPropertySetup[] getDynamicPropertySetup(String securitySubtype) throws SearchDAOException {

        String sql =SELECT_DYNAMIC_PROPERTY_SETUP_ALL;

        ResultSet rs;
        ArrayList resultList = new ArrayList();
        HashMap setupMap = new HashMap();
        HashMap entryCodeMap = new HashMap();
        HashMap entryDescMap = new HashMap();
        Long setupID = null;
        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(sql);
            dbUtil.setString(1, securitySubtype);
            rs = dbUtil.executeQuery();
            while(rs.next()) {
                setupID = new Long(rs.getLong(IDocumentDAO.DYNPROPSETUPTBL_ID));

                //Get the dynamic property setup for processing
                OBDynamicPropertySetup setup = (OBDynamicPropertySetup)setupMap.get(setupID);
                if(setup == null) {
                    setup = new OBDynamicPropertySetup();
                }

                setup.setPropertySetupID(setupID.longValue());
                setup.setSecSubtype(rs.getString(IDocumentDAO.DYNPROPSETUPTBL_SEC_SUBTYPE));
                setup.setProperty(rs.getString(IDocumentDAO.DYNPROPSETUPTBL_PROPERTY_NAME));
                setup.setLabel(rs.getString(IDocumentDAO.DYNPROPSETUPTBL_PROPERTY_LABEL));
                setup.setCategoryCode(rs.getString(IDocumentDAO.DYNPROPSETUPTBL_CATEGORY_CODE));
                setup.setInputType(rs.getString(IDocumentDAO.DYNPROPSETUPTBL_INPUT_TYPE));
                setupMap.put(setupID, setup);               //add to the hashmap

                //Get the entry codes for this dynamic property
                ArrayList entryCodeList = (ArrayList)entryCodeMap.get(setupID);
                ArrayList entryDescList = (ArrayList)entryDescMap.get(setupID);
                if(entryCodeList == null) {
                    entryCodeList = new ArrayList();
                    entryDescList = new ArrayList();        //these 2 list must be in-sync
                }

                entryCodeList.add(rs.getString(IDocumentDAO.DYNPROPSETUPTBL_COMMONCODE_ENTRY_CODE));
                entryDescList.add(rs.getString(IDocumentDAO.DYNPROPSETUPTBL_COMMONCODE_ENTRY_NAME));
                entryCodeMap.put(setupID, entryCodeList);
                entryDescMap.put(setupID, entryDescList);
            }

            rs.close();

            //Process the hashmaps - link up the entry codes and entry description the the setup object
            //Iterator it = setupMap.entrySet().iterator();
            Iterator it = setupMap.values().iterator();
            while(it.hasNext()) {
            	OBDynamicPropertySetup setup = (OBDynamicPropertySetup)it.next();
            	//Object setup = it.next();
            	DefaultLogger.debug(this, setup.getClass().getName() );
                setupID = new Long(setup.getPropertySetupID());
                ArrayList entryCodeList = (ArrayList)entryCodeMap.get(setupID);
                ArrayList entryDescList = (ArrayList)entryDescMap.get(setupID);
                setup.setEntryCodes((String[]) entryCodeList.toArray(new String[0]));
                setup.setEntryDescription((String[]) entryDescList.toArray(new String[0]));
                resultList.add(setup);
            }


            return (IDynamicPropertySetup[]) resultList.toArray(new IDynamicPropertySetup[0]);
        }
        catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getDynamicPropertySetup", ex);
        }
        catch (Exception ex) {
            throw new SearchDAOException("Exception in getDynamicPropertySetup", ex);
        }
        finally {
            try {
                dbUtil.close();
            }
            catch (SQLException ex) {
                throw new SearchDAOException("SQLException in getDocumentCodeCount", ex);
            }
        }
    }

    public void deleteOldRecurrentDocument(long year) { 
    	 try {
    		 String query = "update CMS_DOCUMENT_GLOBALLIST set deprecated='Y' , status='DISABLE' where CATEGORY='REC' and IS_RECURRENT='on' and TO_NUMBER(TO_CHAR(EXPIRY_DATE,'YYYY'))="+year;
     		
             dbUtil = new DBUtil();
             dbUtil.setSQL(query);
    		 dbUtil.executeUpdate();
    	   }
         catch (SQLException ex) {
             throw new SearchDAOException("SQLException in getDynamicPropertySetup", ex);
         }
         catch (Exception ex) {
             throw new SearchDAOException("Exception in getDynamicPropertySetup", ex);
         }
         finally {
             try {
                 dbUtil.close();
             }
             catch (SQLException ex) {
                 throw new SearchDAOException("SQLException in getDocumentCodeCount", ex);
             }
         }
    		
    	}


	public SearchResult searchFilteredDocumentItemList(DocumentSearchCriteria aCriteria, List docCrit)
			throws SearchDAOException {
		if (aCriteria == null) {
			throw new SearchDAOException("The DocumentSearchCriteria is null !!!");
		}
		String firstSort = aCriteria.getFirstSort();
		String secondSort = aCriteria.getSecondSort();
		
		int startIndex = aCriteria.getStartIndex();
		int nItems = aCriteria.getNItems();

		int numTotalRecords = getSearchDocumentRecordCount(aCriteria);
		if (numTotalRecords == 0) {
			return null;
		}
		String sql = getSearchDocumentItemsSQL(aCriteria);
		if (!isEmpty(sql)) {
			if(aCriteria.getDocumentType()!=null && aCriteria.getDocumentType().trim().equalsIgnoreCase("REC")){
				sql = SELECT_DOCUMENT_ITEM_REC + " AND " + sql;
			}else{
			sql = SELECT_DOCUMENT_ITEM + " AND " + sql;
			}
		}
		else {
			if(aCriteria.getDocumentType()!=null && aCriteria.getDocumentType().trim().equalsIgnoreCase("REC")){
				sql = SELECT_DOCUMENT_ITEM_REC;
			}else{
			sql = SELECT_DOCUMENT_ITEM;
			}
		}
		if(aCriteria.getDocumentType()!=null && aCriteria.getDocumentType().trim().equalsIgnoreCase("REC")){
			StringBuffer strBuffer = new StringBuffer();
			if(null!=docCrit.get(0)){
			if(!"".equals(docCrit.get(0)))
				strBuffer.append(" and LOWER(CMS_DOCUMENT_GLOBALLIST.DOCUMENT_DESCRIPTION) like '%"+docCrit.get(0).toString().toLowerCase().trim()+"%'");
			}
			if(null!=docCrit.get(1)){
			if(!"".equals(docCrit.get(1)))
				strBuffer.append(" and CMS_DOCUMENT_GLOBALLIST.STATEMENT_TYPE = '"+docCrit.get(1)+"'");
			}
			sql = sql + strBuffer.toString();
		}
		if(aCriteria.getDocumentType()!=null &&(aCriteria.getDocumentType().trim().equalsIgnoreCase("O")||aCriteria.getDocumentType().trim().equalsIgnoreCase("S")||aCriteria.getDocumentType().trim().equalsIgnoreCase("F")||aCriteria.getDocumentType().trim().equalsIgnoreCase("CAM"))){
			StringBuffer strBuffer = new StringBuffer();
			if(null!=docCrit.get(0)){
			if(!"".equals(docCrit.get(0)))
				strBuffer.append(" and LOWER(CMS_DOCUMENT_GLOBALLIST.DOCUMENT_CODE) = '"+docCrit.get(0).toString().toLowerCase().trim()+"'");
			}
			if(null!=docCrit.get(1)){
			if(!"".equals(docCrit.get(1)))
				strBuffer.append(" and LOWER(CMS_DOCUMENT_GLOBALLIST.DOCUMENT_DESCRIPTION) like '%"+docCrit.get(1).toString().toLowerCase().trim()+"%'");
			}
			if(null!=docCrit.get(2)){
			if(!"".equals(docCrit.get(2)))
				strBuffer.append(" and CMS_DOCUMENT_GLOBALLIST.TENURE_COUNT = "+docCrit.get(2)+"");
			}
			if(null!=docCrit.get(3)){
			if(!"".equals(docCrit.get(3)))
				strBuffer.append(" and CMS_DOCUMENT_GLOBALLIST.TENURE_TYPE = "+docCrit.get(3)+"");
			}
			sql = sql + strBuffer.toString();
			sql+=" ORDER BY CMS_DOCUMENT_GLOBALLIST.DOCUMENT_DESCRIPTION";
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
			rs.getFetchSize();
			Vector list = processResultSet(rs, nItems);
			rs.close();
			return new SearchResult(0, list.size(), list.size(), list);
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

	public String getDocumentDescByDocCode(String docCode)throws DBConnectionException, SQLException{

		ResultSet rs;
		String docDesc= null;
		
		try {
			String query = "select DOCUMENT_DESCRIPTION FROM CMS_DOCUMENT_GLOBALLIST WHERE DOCUMENT_CODE = '"+docCode+"' ";
			
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			rs = dbUtil.executeQuery();
//			if(rs.getFetchSize()>0){
				while(rs.next()){
					docDesc = rs.getString(1);
				}
//			}
			rs.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getDocumentDescByDocCode", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getDocumentDescByDocCode", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getDocumentDescByDocCode", ex);
			}
		}
		return docDesc;
	}
	public String[] getDocumentIdAndDescByDocCode(String docCode)throws DBConnectionException, SQLException{
		
		ResultSet rs;
		String[] docDetails= new String[5];
			
		try {
			String query = "select DOCUMENT_ID,DOCUMENT_DESCRIPTION,STATEMENT_TYPE," +
					" TENURE_COUNT,TENURE_TYPE FROM CMS_DOCUMENT_GLOBALLIST " +
					" WHERE DOCUMENT_CODE = '"+docCode+"' ";
			
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			rs = dbUtil.executeQuery();
//			if(rs.getFetchSize()>0){
				while(rs.next()){
					docDetails[0] = rs.getString(1);//Document Id
					docDetails[1] = rs.getString(2);//Document Description
					docDetails[2] = rs.getString(3);//Statement Type
					docDetails[3] = Integer.toString(rs.getInt(4));//Tenure Count
					docDetails[4] = rs.getString(5);//Tenure Type
				}
//			}
			rs.close();
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in getDocumentDescByDocCode", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in getDocumentDescByDocCode", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in getDocumentDescByDocCode", ex);
			}
		}
		return docDetails;
	}

	public Boolean checkDocumentAvailability(String docCode,String docCategory)throws DBConnectionException, SQLException{

		ResultSet rs;
		Boolean recordFound = false;
		StringBuffer sbQuery = new StringBuffer();
		try {
			sbQuery.append("select count(1) FROM CMS_DOCUMENT_GLOBALLIST WHERE DOCUMENT_CODE = '"+docCode+"' ");
			if(docCategory!=null && !docCategory.trim().isEmpty()){
				sbQuery.append(" AND CATEGORY = '"+docCategory.trim().toUpperCase()+"' ");
			}
			
			dbUtil = new DBUtil();
			dbUtil.setSQL(sbQuery.toString());
			rs = dbUtil.executeQuery();
			if(rs.next()){
				int count = rs.getInt(1);
				if(count==1){
					recordFound = true;
				}
			}
			rs.close();
		}
		catch (SQLException ex) {
			recordFound = false; 
			throw new SearchDAOException("SQLException in checkDocumentAvailability", ex);
		}
		catch (Exception ex) {
			recordFound = false; 
			throw new SearchDAOException("Exception in checkDocumentAvailability", ex);
		}
		finally {
			try {
				dbUtil.close();
			}
			catch (SQLException ex) {
				throw new SearchDAOException("SQLException in checkDocumentAvailability", ex);
			}
		}
		return recordFound;
	}
}
