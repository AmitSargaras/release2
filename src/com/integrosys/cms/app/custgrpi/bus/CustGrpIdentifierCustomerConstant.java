package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

import java.sql.ResultSet;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 20, 2007
 * Time: 5:53:38 PM
 * To change this template use File | Settings | File Templates.
 */

public class CustGrpIdentifierCustomerConstant {

    static String SELECT_DUMMY_PART_SELECT = "SELECT 1 ";


    protected static String SELECT_CUSTOMER =
            "SELECT  distinct \n"
                    + "       CMS_CCI_MAP.CCI_MAP_ID, \n"
                    + "       CMS_CCI_MAP.CMS_CCI_NO, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n"
                    + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_ID_OLD_NUM, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n"
                    + "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n"
                    + "       SCI_LE_SUB_PROFILE.LSP_ID, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION \n";

    protected static String FROM_CUSTOMER =
            "FROM SCI_LE_MAIN_PROFILE, \n"
                    + "     CMS_CCI_MAP, \n"
                    + "     SCI_LE_SUB_PROFILE   \n"
                    + " WHERE 0=0 \n"
                    + " AND CMS_CCI_MAP.IS_DELETED <> 'Y' \n"
                    + " AND CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n"
                    + " AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";


    protected static String SELECT_CUSTOMER_PART_SELECT =
            "SELECT  distinct \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n"
                    + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_ID_OLD_NUM, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n"
                    + "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n"
                    + "       SCI_LE_SUB_PROFILE.LSP_ID, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION \n";

    protected static String SELECT_CUSTOMER_PART_SELECT_NEW =
            "SELECT  distinct \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n"
                    + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_ID_OLD_NUM, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_INC_DATE, \n"
                    + "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n"
                    + "       SCI_LE_SUB_PROFILE.LSP_ID, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION, \n"
                    //Andy Wong, 6 July 2008: customize for ABG to get member credit rating and entity limit
                    + " (select LCG_CRDT_GRADE_CODE_VALUE from SCI_LE_CREDIT_GRADE where SCI_LE_CREDIT_GRADE.CMS_MAIN_PROFILE_ID=SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID and LCG_CRDT_GRADE_TYPE_VALUE='C' AND UPDATE_STATUS_IND <> 'D') CREDIT_RATE, \n"
                    + " (select LIMIT_CURRENCY from CMS_ENTITY_LIMIT where CMS_ENTITY_LIMIT.CMS_LE_SUB_PROFILE_ID= SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID AND STATUS <> 'DELETED'), \n"
                    + " (select LIMIT_AMOUNT from CMS_ENTITY_LIMIT where CMS_ENTITY_LIMIT.CMS_LE_SUB_PROFILE_ID= SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID AND STATUS <> 'DELETED') \n";


    protected static String SELECT_CUSTOMER_PART_FROMWHERE_NEW =
            "FROM SCI_LE_MAIN_PROFILE, \n"
                    + "  SCI_LE_SUB_PROFILE \n"
                    + "    LEFT OUTER JOIN SCI_LSP_JOINT_BORROWER  \n"
                    + "      ON SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID  \n"
                    + "        AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <> 'D' \n"
                    + "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";

    protected static String CUSTOMER_ADDRESS =
            " SELECT LSP.LSP_LE_ID, LSP.LSP_ID, LSP.LSP_SHORT_NAME, LSP.LSP_ID, \n" +
                    "        ADDR.LOA_ADDR_LINE_1, ADDR.LOA_ADDR_LINE_2,\n" +
                    "       ADDR.LOA_CITY_TEXT, ADDR.LOA_STATE, ADDR.LOA_POST_CODE, \n" +
                    "       ADDR.LOA_CNTRY_ISO_CODE\n" +
                    "  FROM SCI_LE_SUB_PROFILE LSP, \n" +
                    "       SCI_LSP_OFF_ADDR ADDR\n" +
                    " WHERE ADDR.CMS_MAIN_PROFILE_ID = LSP.CMS_LE_SUB_PROFILE_ID \n" +
                    "  AND CMS_LE_SUB_PROFILE_ID = ?";


    protected static String SELECT_CUSTOMER_PART_FROMWHERE =
            "FROM SCI_LE_MAIN_PROFILE, \n"
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

    protected static String CUSTOMER_SORT_ORDER =
            " ORDER BY LSP_SHORT_NAME ";

    protected static String SELECT_CUSTOMER_BY_SCI_ID =
            "SELECT CMS_LE_SUB_PROFILE_ID FROM SCI_LE_SUB_PROFILE WHERE LSP_LE_ID = ? AND LSP_ID = ?";

    protected static String SELECT_CUSTOMER_BY_CIFNO_SOURCE =
            "SELECT CMS_LE_SUB_PROFILE_ID " +
                    "FROM SCI_LE_SUB_PROFILE INNER JOIN SCI_LE_MAIN_PROFILE " +
                    "ON SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID " +
                    "WHERE LSP_LE_ID = ? AND SOURCE_ID = ?";


    //Table names
    static final String CMS_GRP_CCI_LE_MAP = "CMS_GRP_CCI_LE_MAP";
    static final String LEGAL_ENTITY_TABLE = "SCI_LE_MAIN_PROFILE";
    static final String CUSTOMER_TABLE = "SCI_LE_SUB_PROFILE";
    static final String LIMIT_PROFILE_TABLE = "SCI_LSP_LMT_PROFILE";
    static final String TRANSACTION_TABLE = ICMSTrxTableConstants.TRX_TBL_NAME;
    static final String TRADE_AGREEMENT_TABLE = "CMS_TRADING_AGREEMENT";

    //Column values for legal entity table
    static final String LEGAL_NAME = "LMP_LONG_NAME";
    static final String LEGAL_ID = "CMS_LE_MAIN_PROFILE_ID";
    static final String LEGAL_REF = "LMP_LE_ID";
    static final String CUSTOMER_TYPE = "LMP_TYPE_VALUE";

    //Column values for customer table
    static final String CUSTOMER_NAME = "LSP_SHORT_NAME";
    static final String CUSTOMER_ID = "CMS_LE_SUB_PROFILE_ID";
    static final String CUSTOMER_REF = "LSP_ID";
    static final String CUSTOMER_LE_ID = "CMS_LE_MAIN_PROFILE_ID";
    static final String CUSTOMER_LE_REF = "LSP_LE_ID";
    static final String CUSTOMER_COUNTRY = "CMS_SUB_ORIG_COUNTRY";
    static final String CUSTOMER_ORGANISATION = "CMS_SUB_ORIG_ORGANISATION";

    static final String SEGMENT_NAME = "LMP_SUB_SGMNT_CODE_VALUE";
    static final String COUNTRY_NAME = "LSP_DMCL_CNTRY_ISO_CODE";

    //Column values for limit profile table
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

    //Column values for trading agreement table
    static final String AGREEMENT_ID = "AGREEMENT_ID";
    static final String AGREEMENT_TYPE = "AGREEMENT_TYPE";

    //Column values for trading agreement table
    static final String BRIDGING_LOAN_IND = "HAS_BRIDGING_LOAN";
    static final String CONTRACT_FINANCE_IND = "HAS_CONTRACT_FINANCE";

    //Column values for transaction table
    static final String TRX_ID = ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID;
    static final String TRX_TYPE = ICMSTrxTableConstants.TRXTBL_TRANSACTION_TYPE;
    static final String TRX_REF_ID = ICMSTrxTableConstants.TRXTBL_REFERENCE_ID;
    static final String TRX_CUSTOMER_VALUE = ICMSConstant.INSTANCE_CUSTOMER;

    static final String SELECT_CUST_WITHOUT_TRX =
            "select     mp.LMP_LONG_NAME, mp.CMS_LE_MAIN_PROFILE_ID, mp.LMP_LE_ID, " +
                    "   mp.LMP_TYPE_VALUE, sp.LSP_SHORT_NAME, sp.CMS_LE_SUB_PROFILE_ID, " +
                    "   sp.LSP_ID, lp.CMS_LSP_LMT_PROFILE_ID, lp.LLP_BCA_REF_NUM, " +
                    "   lp.LLP_BCA_REF_APPR_DATE, lp.CMS_ORIG_COUNTRY, sp.CMS_NON_BORROWER_IND, " +
                    "   lp.CMS_BCA_STATUS " +
                    "from   SCI_LE_MAIN_PROFILE mp, " +
                    "       SCI_LE_SUB_PROFILE sp, " +
                    "       SCI_LSP_LMT_PROFILE lp " +
                    "where mp.CMS_LE_MAIN_PROFILE_ID = sp.CMS_LE_MAIN_PROFILE_ID " +
                    "       and sp.CMS_LE_SUB_PROFILE_ID = lp.CMS_CUSTOMER_ID " +
                    "       and lp.CMS_LSP_LMT_PROFILE_ID = ?";


    static final String SELECT_CUST_MAIL_DETAILS_1 =
            "SELECT lsp.lsp_le_id, lsp.lsp_id, lsp.lsp_short_name, lsp.lsp_id," +
                    "  addr.loa_addr_line_1, addr.loa_addr_line_2, addr.loa_city_text," +
                    "  addr.loa_state, addr.loa_post_code, addr.loa_cntry_iso_code " +
                    "FROM sci_le_sub_profile lsp," +
                    "  sci_lsp_off_addr addr, " +
                    "(SELECT DISTINCT llp.llp_le_id le_id, llp.llp_lsp_id lsp_id" +
                    "   FROM sci_lsp_lmt_profile llp" +
                    "  WHERE (llp.llp_le_id, llp.llp_lsp_id)";

    static final String SELECT_CUST_MAIL_DETAILS_2 =
            "    AND (llp.update_status_ind IS NULL OR llp.update_status_ind != 'D' )) req_customer " +
                    "WHERE lsp.lsp_le_id = req_customer.le_id " +
                    "AND lsp.lsp_id = req_customer.lsp_id " +
                    "AND addr.cms_main_profile_id = lsp.cms_le_sub_profile_id ";

    static final String SELECT_COUNT_CO_BORROWER_LIMIT =
            "SELECT COUNT(*) FROM SCI_LSP_CO_BORROW_LMT " +
                    "WHERE (UPDATE_STATUS_IND <> 'D' OR UPDATE_STATUS_IND IS NULL) " +
                    "AND CMS_CUSTOMER_ID = ?";

    static final String SELECT_MB_INFO_LIMIT_LIST =
            "SELECT DISTINCT pf.LLP_LE_ID, pf.CMS_ORIG_COUNTRY, pf.CMS_ORIG_ORGANISATION, sub.LSP_SHORT_NAME " +
                    "FROM SCI_LSP_LMT_PROFILE pf, SCI_LSP_APPR_LMTS lmts, SCI_LE_SUB_PROFILE sub " +
                    "WHERE lmts.cms_limit_profile_id = pf.cms_lsp_lmt_profile_id " +
                    "AND pf.cms_customer_id = sub.CMS_LE_SUB_PROFILE_ID ";

    static final String SELECT_CB_INFO_LIMIT_LIST =
            "SELECT DISTINCT lsp.LSP_LE_ID, lsp.CMS_SUB_ORIG_COUNTRY, lsp.CMS_SUB_ORIG_ORGANISATION,lsp.LSP_SHORT_NAME " +
                    "FROM SCI_LE_SUB_PROFILE lsp,SCI_LSP_CO_BORROW_LMT clmts " +
                    "WHERE clmts.CMS_CUSTOMER_ID = lsp.CMS_LE_SUB_PROFILE_ID " +
                    "AND CMS_LSP_CO_BORROW_LMT_ID ";

    static final String getMBByCBSql = " SELECT  " +
            " SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n" +
            " LMP_LONG_NAME, \n" +
            " CMS_ORIG_COUNTRY,\n" +
            " CMS_ORIG_ORGANISATION, \n" +
            " LLP_BCA_REF_NUM,\n" +
            " LLP_BCA_REF_APPR_DATE\n" +
            "  FROM " +
            " SCI_LSP_LMT_PROFILE,\n" +
            " SCI_LE_SUB_PROFILE,\n" +
            " SCI_LE_MAIN_PROFILE\n" +
            " WHERE \n" +
            " SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID=SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID\n" +
            " AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID=SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID\n" +
            " AND   SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID\n" +
            "       IN (\n" +
            "           SELECT " +
            "            DISTINCT  SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID\n" +
            "           FROM \n" +
            "            SCI_LSP_CO_BORROW_LMT,\n" +
            "            SCI_LSP_APPR_LMTS\n" +
            "           WHERE \n" +
            "            SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID=SCI_LSP_CO_BORROW_LMT.CMS_LIMIT_ID\n" +
            "     AND    SCI_LSP_CO_BORROW_LMT.CMS_CUSTOMER_ID=? \n" +
            "          )";

    static final String KEY_DELIMITER = ";";

    static final String COUNTRY_FILTER_SQL = "( SELECT COUNTRY_CODE FROM CMS_TEAM_COUNTRY_CODE WHERE TEAM_ID = ? ) ";
    static final String ORGANISATION_FILTER_SQL = " (SELECT ORGANISATION_CODE FROM CMS_TEAM_ORGANISATION_CODE WHERE TEAM_ID = ?) ";


    static String SELECT_CUSTOMER_PART_FROM_WHERE_CUSTOMER_SEACH_ONLY =
            "FROM SCI_LE_MAIN_PROFILE, \n"
                    + "     SCI_LE_SUB_PROFILE LEFT OUTER JOIN  \n"
                    + "       SCI_LSP_LMT_PROFILE ON SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n"
                    + "      AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = '" + ICMSConstant.AA_TYPE_BANK + "' \n"
                    + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' \n"
                    + "WHERE 0=0 "
                    + "AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";


    /**
     * Utiloty method to convert the array to a sql friendly string ( in Clause)
     *
     * @param aArray - a String  array like Segment, or Country list
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
     * @param aValue - String
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
     * @param dbUtil database utility object
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
     * @param rs of type ResultSet
     * @throws com.integrosys.base.businfra.search.SearchDAOException
     *          error in closing the result set
     */
    public void finalize(ResultSet rs) throws SearchDAOException {
        try {
            if (null != rs) {
                rs.close();
            }
        }
        catch (Exception e) {
            throw new SearchDAOException("Error in closing the result set!", e);
        }
    }

    //inner class
    public class DAPFilterException extends Exception {
        public DAPFilterException(String msg) {
            super(msg);
        }
    }


}
