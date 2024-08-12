/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ITemplateDAO.java,v 1.8 2003/10/30 10:01:34 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//ofa
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.chktemplate.bus.TemplateSearchCriteria;

/**
 * This interface defines the constant specific to the template table and the
 * methods required by the template
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2003/10/30 10:01:34 $ Tag: $Name: $
 */
public interface ITemplateDAO {
	// For the template master table
	public static final String TMP_TABLE = "CMS_DOCUMENT_MASTERLIST";

	public static final String TMPTBL_TEMPLATE_ID = "MASTERLIST_ID";

	public static final String TMPTBL_COUNTRY = "COUNTRY_ISO_CODE";

	public static final String TMPTBL_TEMPLATE_TYPE = "CATEGORY";

	public static final String TMPTBL_LAW = "APPLICABLE_LAW";

	public static final String TMPTBL_LEGAL_CONSTITUTION = "CUSTOMER_TYPE_CODE";

	public static final String TMPTBL_COLLATERAL_TYPE = "SECURITY_TYPE_ID";

	public static final String TMPTBL_COLLATERAL_SUB_TYPE = "SECURITY_SUB_TYPE_ID";

	public static final String TMPTBL_TEMPLATE_ID_PREF = TMP_TABLE + "." + TMPTBL_TEMPLATE_ID;

	public static final String TMPTBL_COUNTRY_PREF = TMP_TABLE + "." + TMPTBL_COUNTRY;

	public static final String TMPTBL_TEMPLATE_TYPE_PREF = TMP_TABLE + "." + TMPTBL_TEMPLATE_TYPE;

	public static final String TMPTBL_LAW_PREF = TMP_TABLE + "." + TMPTBL_LAW;

	public static final String TMPTBL_LEGAL_CONSTITUTION_PREF = TMP_TABLE + "." + TMPTBL_LEGAL_CONSTITUTION;

	public static final String TMPTBL_COLLATERAL_TYPE_PREF = TMP_TABLE + "." + TMPTBL_COLLATERAL_TYPE;

	public static final String TMPTBL_COLLATERAL_SUB_TYPE_PREF = TMP_TABLE + "." + TMPTBL_COLLATERAL_SUB_TYPE;

	// For the staging template master table
	public static final String STAGE_TMP_TABLE = "STAGE_DOCUMENT_MASTERLIST";

	public static final String STAGE_TMPTBL_TEMPLATE_ID_PREF = STAGE_TMP_TABLE + "." + TMPTBL_TEMPLATE_ID;

	public static final String STAGE_TMPTBL_COUNTRY_PREF = STAGE_TMP_TABLE + "." + TMPTBL_COUNTRY;

	public static final String STAGE_TMPTBL_TEMPLATE_TYPE_PREF = STAGE_TMP_TABLE + "." + TMPTBL_TEMPLATE_TYPE;

	public static final String STAGE_TMPTBL_LAW_PREF = STAGE_TMP_TABLE + "." + TMPTBL_LAW;

	public static final String STAGE_TMPTBL_LEGAL_CONSTITUTION_PREF = STAGE_TMP_TABLE + "." + TMPTBL_LEGAL_CONSTITUTION;

	public static final String STAGE_TMPTBL_COLLATERAL_TYPE_PREF = STAGE_TMP_TABLE + "." + TMPTBL_COLLATERAL_TYPE;

	public static final String STAGE_TMPTBL_COLLATERAL_SUB_TYPE_PREF = STAGE_TMP_TABLE + "."
			+ TMPTBL_COLLATERAL_SUB_TYPE;

	// might be moved to some common package later on if required.
	public static final String APPLICABLE_LAW_TABLE = "APPLICABLE_LAW";

	public static final String ALTBL_LAW_CODE = "LAW_CODE";

	public static final String ALTBL_LAW_CODE_DESC = "LAW_CODE_DESC";

	public static final String ALTBL_LAW_CODE_PREF = APPLICABLE_LAW_TABLE + "." + ALTBL_LAW_CODE;

	public static final String ALTBL_LAW_CODE_DESC_PREF = APPLICABLE_LAW_TABLE + "." + ALTBL_LAW_CODE_DESC;

	// might be moved to some common package later on if required.
	public static final String CUST_TYPE_TABLE = "CUSTOMER_LEGAL_CONSTITUTION";

	public static final String CSTBL_CUST_TYPE_CODE = "CUSTOMER_LEGAL_CONST_CODE";

	public static final String CSTBL_CUST_TYPE_DESC = "CUSTOMER_LEGAL_CONST_DESC";

	public static final String CSTBL_CUST_TYPE_CODE_PREF = CUST_TYPE_TABLE + "." + CSTBL_CUST_TYPE_CODE;

	public static final String CSTBL_CUST_TYPE_DESC_PREF = CUST_TYPE_TABLE + "." + CSTBL_CUST_TYPE_DESC;

	/**
	 * To get the list of law and customer types
	 * @param aLaw of String type
	 * @return LawSearchResultItem[] - the list of laws and the customer tyoes
	 * @throws SearchDAOException on errors
	 */
	public LawSearchResultItem[] getLawCustomerTypes(String[] aLaw) throws SearchDAOException;

	/**
	 * Get get the list of Templates based on the criteria specified
	 * @param aCriteria - TemplateSearchCriteria
	 * @return SearchResult - this contains a collection of
	 *         CCTemplateSearchResultItem
	 * @throws SearchDAOException on errors
	 */
	public SearchResult searchTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException;
}
