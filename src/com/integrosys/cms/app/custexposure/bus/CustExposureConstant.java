package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA. 
 * User: jitendra 
 * Date: Nov 20, 2007 Time: 5:53:38 PM
 * To change this template use File | Settings | File Templates.
 */

public class CustExposureConstant {

	static String SELECT_DUMMY_PART_SELECT = "SELECT 1 ";

	protected static String SELECT_CUSTOMER_PART = "SELECT  distinct \n"
			+ "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n"
			+ "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n"
			+ "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n"
			+ "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n"
			+ "       SCI_LE_MAIN_PROFILE.LMP_ID_NUM, \n"
			+ "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n"
			+ "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n"
			+ "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n"
			+ "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n"
			+ "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n"
			+ "       SCI_LE_SUB_PROFILE.LSP_ID, \n"
			+ "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n"
			+ "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION \n";

	protected static String FROM_CUSTOMER_PART = "FROM SCI_LE_MAIN_PROFILE, \n"
			+ "  SCI_LE_SUB_PROFILE \n"
			+ "    LEFT OUTER JOIN SCI_LSP_JOINT_BORROWER  \n"
			+ "      ON SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID  \n"
			+ "        AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <> 'D' \n"
			+ "    LEFT OUTER JOIN SCI_LSP_LMT_PROFILE \n"
			+ "      ON (SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LMP_LIMIT_PROFILE_ID \n"
			+ "        OR SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID) \n"
			+ "        AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = 'BANKING' \n"
			+ "        AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' \n"
			+ "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";

	protected static String CUSTOMER_SORT_ORDER = " ORDER BY LSP_SHORT_NAME ";

	// Table names
	static final String CMS_GRP_CCI_LE_MAP = "CMS_GRP_CCI_LE_MAP";
	static final String LEGAL_ENTITY_TABLE = "SCI_LE_MAIN_PROFILE";
	static final String CUSTOMER_TABLE = "SCI_LE_SUB_PROFILE";
	static final String LIMIT_PROFILE_TABLE = "SCI_LSP_LMT_PROFILE";
	static final String TRANSACTION_TABLE = ICMSTrxTableConstants.TRX_TBL_NAME;
	static final String TRADE_AGREEMENT_TABLE = "CMS_TRADING_AGREEMENT";

	// Column values for legal entity table
	static final String LEGAL_NAME = "LMP_LONG_NAME";
	static final String LEGAL_ID = "CMS_LE_MAIN_PROFILE_ID";
	static final String LEGAL_REF = "LMP_LE_ID";
	static final String CUSTOMER_TYPE = "LMP_TYPE_VALUE";

	// Column values for customer table
	static final String CUSTOMER_NAME = "LSP_SHORT_NAME";
	static final String CUSTOMER_ID = "CMS_LE_SUB_PROFILE_ID";
	static final String CUSTOMER_REF = "LSP_ID";
	static final String CUSTOMER_LE_ID = "CMS_LE_MAIN_PROFILE_ID";
	static final String CUSTOMER_LE_REF = "LSP_LE_ID";
	static final String CUSTOMER_COUNTRY = "CMS_SUB_ORIG_COUNTRY";
	static final String CUSTOMER_ORGANISATION = "CMS_SUB_ORIG_ORGANISATION";

	static final String SOURCE_ID = "SOURCE_ID";
	static final String RELATED_ENTITY_NAME = "RELATED_ENTITY_NAME";
	static final String ENTITY_RELATION_SHIP = "ENTITY_RELATION";

	static final String SEGMENT_NAME = "LMP_SUB_SGMNT_CODE_VALUE";
	static final String COUNTRY_NAME = "LSP_DMCL_CNTRY_ISO_CODE";

	// Column values for limit profile table
	static final String BCA_ID = "CMS_LSP_LMT_PROFILE_ID";
	static final String BCA_REF = "LLP_BCA_REF_NUM";
	static final String BCA_APPROVED_DATE = "LLP_BCA_REF_APPR_DATE";
	static final String BCA_CUSTOMER_ID = "CMS_CUSTOMER_ID";
	static final String CMS_CUSTOMER_IND = "CMS_NON_BORROWER_IND";
	static final String CMS_BCA_STATUS = "CMS_BCA_STATUS";
	static final String BCA_ORIG_CNTRY = "CMS_ORIG_COUNTRY";
	static final String BCA_ORIG_ORG = "CMS_ORIG_ORGANISATION";
	static final String LMT_PROFILE_TYPE = "LMT_PROFILE_TYPE";
	static final String CMS_CREATE_IND = "CMS_CREATE_IND";
	static final String LIMIT_ID = "LIMIT_ID";

	// Column values for trading agreement table
	static final String AGREEMENT_ID = "AGREEMENT_ID";
	static final String AGREEMENT_TYPE = "AGREEMENT_TYPE";

	// Column values for trading agreement table
	static final String BRIDGING_LOAN_IND = "HAS_BRIDGING_LOAN";
	static final String CONTRACT_FINANCE_IND = "HAS_CONTRACT_FINANCE";

	// Column values for transaction table
	static final String TRX_ID = ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID;
	static final String TRX_TYPE = ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE;
	static final String TRX_REF_ID = ICMSTrxTableConstants.TRXTBL_REFERENCE_ID;
	static final String TRX_CUSTOMER_VALUE = ICMSConstant.INSTANCE_CUSTOMER;

	// Column values for customer table

	static final String KEY_DELIMITER = ";";

	static final String COUNTRY_FILTER_SQL = "( SELECT COUNTRY_CODE FROM CMS_TEAM_COUNTRY_CODE WHERE TEAM_ID = ? ) ";
	static final String ORGANISATION_FILTER_SQL = " (SELECT ORGANISATION_CODE FROM CMS_TEAM_ORGANISATION_CODE WHERE TEAM_ID = ?) ";
	
	static final String CMS_CCI_NO = "CMS_CCI_NO";
	static final String BANK_ENTITY_CODE = "BANK_ENTITY_CODE";
	static final String ACCOUNT_TYPE = "ACCOUNT_TYPE";

	/**
	 * Utiloty method to convert the array to a sql friendly string ( in Clause)
	 * 
	 * @param aArray
	 *            - a String array like Segment, or Country list
	 * @return String - SQL Friendly SQL list
	 */
	public String getSQLList(String[] aArray) {
		if (aArray == null)
			return null;
		StringBuffer buf = new StringBuffer();
		buf.append("(");
		for (int i = 0; i < aArray.length; i++) {
			buf.append("'");
			buf.append(aArray[i]);
			buf.append("'");
			if (i != aArray.length - 1)
				buf.append(",");
			else
				buf.append(")");

		}
		return buf.toString();

	}

	/**
	 * Utiloty method to check if a string value is null or empty
	 * 
	 * @param aValue
	 *            - String
	 * @return boolean - true if empty and false otherwise
	 */
	public boolean isEmpty(String aValue) {
		if ((aValue != null && aValue.trim().length() > 0 && !aValue.equals(""))) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isEmptyOrNull(String value) {
		if (value == null) {
			return true;
		}
		if ("".equals(value.trim())) {
			return true;
		}
		if ("null".equalsIgnoreCase(value.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil
	 *            database utility object
	 */
	public static void finalize(DBUtil dbUtil, ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e1) {
		}

		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		} catch (Exception e2) {
		}
	}

	/**
	 * Helper method to clean up result set
	 * 
	 * @param rs
	 *            of type ResultSet
	 * @throws com.integrosys.base.businfra.search.SearchDAOException
	 *             error in closing the result set
	 */
	public void finalize(ResultSet rs) throws SearchDAOException {
		try {
			if (null != rs) {
				rs.close();
			}
		} catch (Exception e) {
			throw new SearchDAOException("Error in closing the result set!", e);
		}
	}

	// inner class
	public class DAPFilterException extends Exception {

		private static final long serialVersionUID = 1L;

		public DAPFilterException(String msg) {
			super(msg);
		}
	}

}
