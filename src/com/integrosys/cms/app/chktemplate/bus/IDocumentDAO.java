/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IDocumentDAO.java,v 1.4 2003/08/26 03:41:01 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//ofa
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

/**
 * This interface defines the constant specific to the document table and the
 * methods required by the document
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/26 03:41:01 $ Tag: $Name: $
 */
public interface IDocumentDAO {
	public static final String DOC_ITEM_TABLE = "CMS_DOCUMENT_GLOBALLIST";

	public static final String DOCITEMTBL_ITEM_ID = "DOCUMENT_ID";
	
	public static final String DOCAPPTYPEITEMTBL_APP_TYPE = "APP_TYPE";

	public static final String DOCITEMTBL_ITEM_CODE = "DOCUMENT_CODE";
	
	public static final String DOCITEMTBL_TENURE_COUNT = "TENURE_COUNT";
	
	public static final String DOCITEMTBL_TENURE_TYPE = "TENURE_TYPE";
	
	public static final String DOCITEMTBL_STATEMENT_TYPE = "STATEMENT_TYPE";
	
	
	public static final String DOCITEMTBL_IS_RECURRENT= "IS_RECURRENT";
	
	public static final String DOCITEMTBL_RATING = "RATING";
	
	public static final String DOCITEMTBL_SEGMENT = "SEGMENT";
	
	public static final String DOCITEMTBL_TOTAL_SANC_AMT = "TOTAL_SANC_AMT";
	
	public static final String DOCITEMTBL_CLASSIFICATION = "CLASSIFICATION";
	
	public static final String DOCITEMTBL_GUARANTOR = "GUARANTOR";
	
	public static final String DOCITEMTBL_DEPRECATED = "DEPRECATED";
	
	public static final String DOCITEMTBL_STATUS = "STATUS";
	
	public static final String DOCITEMTBL_SKIP_IMG_TAG = "SKIP_IMG_TAG";

	public static final String DOCITEMTBL_ITEM_DESC = "DOCUMENT_DESCRIPTION";

	public static final String DOCITEMTBL_ITEM_CATEGORY = "CATEGORY";

	public static final String DOCITEMTBL_EXPIRY_DATE = "EXPIRY_DATE";

    public static final String DOCITEMTBL_DOC_VERSION = "DOC_VERSION";

    public static final String DOCITEMTBL_PRE_APPROVE = "IS_PRE_APPROVE";
    
    public static final String LOAN_APPLICATION_TYPE = "LOAN_APP_TYPE";
    
    public static final String DOCITEMTBL_FOR_BORROWER = "IS_FOR_BORROWER";
    
    public static final String DOCITEMTBL_FOR_PLEDGOR = "IS_FOR_PLEDGOR";

    public static final String DOCITEMTBL_VERSION_TIME = "VERSION_TIME";

	public static final String DOCITEMTBL_ITEM_ID_PREF = DOC_ITEM_TABLE + "." + DOCITEMTBL_ITEM_ID;

	public static final String DOCITEMTBL_ITEM_CODE_PREF = DOC_ITEM_TABLE + "." + DOCITEMTBL_ITEM_CODE;

	public static final String DOCITEMTBL_ITEM_DESC_PREF = DOC_ITEM_TABLE + "." + DOCITEMTBL_ITEM_DESC;

	public static final String DOCITEMTBL_ITEM_CATEGORY_PREF = DOC_ITEM_TABLE + "." + DOCITEMTBL_ITEM_CATEGORY;

	public static final String DOCITEMTBL_EXPIRY_DATE_PREF = DOC_ITEM_TABLE + "." + DOCITEMTBL_EXPIRY_DATE;

    public static final String DOCITEMTBL_DOC_VERSION_PREF = DOC_ITEM_TABLE + "." + DOCITEMTBL_DOC_VERSION;

    public static final String DOCITEMTBL_PRE_APPROVE_PREF = DOC_ITEM_TABLE + "." + DOCITEMTBL_PRE_APPROVE;

    public static final String DOCITEMTBL_VERSION_TIME_PREF = DOC_ITEM_TABLE + "." + DOCITEMTBL_VERSION_TIME;


    /***************** Dynamic Property Setup Table  **********************/
    public static final String DYNAMIC_PROPERTY_SETUP_TABLE = "CMS_DOC_DYNAMIC_PROPERTY_SETUP";
    public static final String DYNPROPSETUPTBL_ID = "SETUP_ID";
    public static final String DYNPROPSETUPTBL_SEC_SUBTYPE = "SECURITY_SUB_TYPE_ID";
    public static final String DYNPROPSETUPTBL_PROPERTY_NAME = "PROPERTY_NAME";
    public static final String DYNPROPSETUPTBL_PROPERTY_LABEL = "PROPERTY_LABEL";
    public static final String DYNPROPSETUPTBL_CATEGORY_CODE = "CATEGORY_CODE";
    public static final String DYNPROPSETUPTBL_INPUT_TYPE = "INPUT_TYPE";
    public static final String DYNPROPSETUPTBL_COMMONCODE_ENTRY_CODE = "ENTRY_CODE";
    public static final String DYNPROPSETUPTBL_COMMONCODE_ENTRY_NAME = "ENTRY_NAME";


    /***************** Interface Methods  **********************/

    /**
	 * Get the list of document items based on the criteria specified
	 * @param aCriteria - DocumentSearchCriteria
	 * @return SearchResult - this contains a collection of
	 *         DocumentSearchResultItem
	 * @throws SearchDAOException on errors
	 */
	public SearchResult searchDocumentItemList(DocumentSearchCriteria aCriteria) throws SearchDAOException;
	
	public SearchResult searchFilteredDocumentItemList(DocumentSearchCriteria aCriteria,List docCrit) throws SearchDAOException;

	/**
	 * Get the number of doc item under the same category and having the same
	 * description
	 * @param aCategory of String type
	 * @param aDescription of String type
	 * @return int - the number of doc items
	 * @throws SearchDAOException
	 */
	public int getNoOfDocItemByDesc(String aCategory, String aDescription) throws SearchDAOException;


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
    public boolean getIsDocumentCodeUnique(String docCode, String category) throws SearchDAOException;


    /**
     * Retrieves the set of dynamic properties for a given security subtype
     * @param securitySubtype - security subtype to retrieve the dynamic properties for
     * @return set of dynamic properties of IDynamicPropertySetup for the given subtype
     * @throws SearchDAOException if errors during retrieval
     */
    public IDynamicPropertySetup[] getDynamicPropertySetup(String securitySubtype) throws SearchDAOException; 

    
    
    public String[] searchDocumentItemByCode(String code) throws SearchDAOException ;
    
    public void deleteOldRecurrentDocument(long year);
}
