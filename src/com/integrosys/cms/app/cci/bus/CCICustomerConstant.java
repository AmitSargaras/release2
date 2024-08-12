package com.integrosys.cms.app.cci.bus;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 20, 2007
 * Time: 5:53:38 PM
 * To change this template use File | Settings | File Templates.
 */

public class CCICustomerConstant {

    static String SELECT_DUMMY_PART_SELECT = "SELECT 1 ";
	
	protected  static String SELECT_DISTINCT_STMT =
            "SELECT  distinct \n";
			
	protected  static String SELECT_CUSTOMER_CCI_CUST_SEARCH =
            "(select CMS_CCI_NO from CMS_CCI_MAP where CMS_CCI_MAP.IS_DELETED <> 'Y' \n" +
			"AND CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID ) AS CCI_NO, ";
			
	protected  static String SELECT_CUSTOMER_CCI =
            "CMS_CCI_MAP.CMS_CCI_NO AS CCI_NO, \n";
			
    protected  static String SELECT_CUSTOMER_PART_SELECT =
            //"SELECT  distinct \n"
            "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n"
            + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n"
            + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n"
            + "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n"
            + "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n"
            + "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n"
            + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n"
            + "       SCI_LE_MAIN_PROFILE.LMP_INC_DATE, \n"
            + "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n"
            + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n"
            + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n"
            + "       SCI_LE_SUB_PROFILE.LSP_ID, \n"
            + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n"
            + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION, \n"
    		+ "       SCI_LE_MAIN_PROFILE.LMP_INC_CNTRY_ISO_CODE \n";


   protected  static String SELECT_CUSTOMER_PART_FROMWHERE_CUST_SEARCH =
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


   protected  static String SELECT_CUSTOMER_PART_FROMWHERE =
        "FROM SCI_LE_MAIN_PROFILE \n"
        + "  INNER JOIN SCI_LE_SUB_PROFILE ON SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n"
        + "    LEFT OUTER JOIN SCI_LSP_JOINT_BORROWER  \n"
        + "      ON SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID  \n"
        + "        AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <> 'D' \n"
        + "    LEFT OUTER JOIN SCI_LSP_LMT_PROFILE \n"
        + "      ON (SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LMP_LIMIT_PROFILE_ID \n"
        + "        OR SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID) \n"
        + "        AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = 'BANKING' \n"
        + "        AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' \n";
        //+ "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";

    protected  static String SELECT_CUSTOMER_CCI_PART_FROMWHERE =
            "    INNER JOIN CMS_CCI_MAP \n"
            + "     ON CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID  \n"
            + "     AND CMS_CCI_MAP.IS_DELETED <> 'Y' \n";


    protected  static String SELECT_CCI_CUSTOMER_NEW =
              "SELECT  distinct \n"
                      + "       CMS_CCI_MAP.CCI_MAP_ID, \n"
                      + "       CMS_CCI_MAP.CMS_CCI_NO, \n"
                      + "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n"
                      + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n"
                      + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n"
                      + "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n"
                      + "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n"
                      + "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n"
                      + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n"
                      + "       SCI_LE_MAIN_PROFILE.LMP_INC_DATE, \n"
                      + "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n"
                      + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n"
                      + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n"
                      + "       SCI_LE_SUB_PROFILE.LSP_ID, \n"
                      + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n"
                      + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION \n" ;

    protected   static String FROM_CCI_CUSTOMER_NEW =
              "FROM SCI_LE_MAIN_PROFILE, \n"
                      + "     CMS_CCI_MAP, \n"
                      + "     SCI_LE_SUB_PROFILE   \n"
                      + " WHERE 0=0 \n"
                      + " AND CMS_CCI_MAP.IS_DELETED <> 'Y' \n"
                      + " AND CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n"
                      + " AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";




    protected  static String SELECT_CUSTOMER_PART_SELECT_NEW =
            "SELECT  distinct \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n"
                    + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_INC_DATE, \n"
                    + "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n"
                    + "       SCI_LE_SUB_PROFILE.LSP_ID, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION \n" ;



   protected  static String SELECT_CUSTOMER_PART_FROMWHERE_NEW  =
        "FROM SCI_LE_MAIN_PROFILE, \n"
        + "  SCI_LE_SUB_PROFILE \n"
        + "    LEFT OUTER JOIN SCI_LSP_JOINT_BORROWER  \n"
        + "      ON SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID  \n"
        + "        AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <> 'D' \n"
        + "WHERE SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";

  protected static String CUSTOMER_ADDRESS=
          " SELECT LSP.LSP_LE_ID, LSP.LSP_ID, LSP.LSP_SHORT_NAME, LSP.LSP_ID, \n" +
          "        ADDR.LOA_ADDR_LINE_1, ADDR.LOA_ADDR_LINE_2,\n" +
          "       ADDR.LOA_CITY_TEXT, ADDR.LOA_STATE, ADDR.LOA_POST_CODE, \n" +
          "       ADDR.LOA_CNTRY_ISO_CODE\n" +
          "  FROM SCI_LE_SUB_PROFILE LSP, \n" +
          "       SCI_LSP_OFF_ADDR ADDR\n" +
          " WHERE ADDR.CMS_MAIN_PROFILE_ID = LSP.CMS_LE_SUB_PROFILE_ID \n" ;
//          "  AND CMS_LE_SUB_PROFILE_ID = ?";


    protected static String CUSTOMER0_ADDRESS_ONLY =
            "SELECT LSP.LSP_LE_ID, LSP.LSP_ID, LSP.LSP_SHORT_NAME, LSP.LSP_ID, \n" +
                    "                  ADDR.LRA_ADDR_LINE_1, ADDR.LRA_ADDR_LINE_2,\n" +
                    "                 ADDR.LRA_CITY_TEXT, ADDR.LRA_STATE, ADDR.LRA_POST_CODE, \n" +
                    "                 ADDR.LRA_CNTRY_ISO_CODE\n" +
                    "            FROM SCI_LE_MAIN_PROFILE MSP, \n" +
                    "            SCI_LE_SUB_PROFILE LSP,\n" +
                    "                 SCI_LE_REG_ADDR ADDR\n" +
                    "           WHERE MSP.CMS_LE_MAIN_PROFILE_ID = LSP.CMS_LE_MAIN_PROFILE_ID \n" +
                    "            AND  ADDR.CMS_LE_MAIN_PROFILE_ID = MSP.CMS_LE_MAIN_PROFILE_ID " +
                    "            and  LRA_TYPE_VALUE='REGISTERED' " +
                    "            AND LSP.LSP_LE_ID = ?    " +
//                    For Db2
//                    "            fetch first 1 rows only  ";
//                    For Oracle
                    "            AND ROWNUM<=1  ";



   protected  static String CUSTOMER_SORT_ORDER = " ORDER BY LSP_SHORT_NAME ";

	protected  static String QUERY_SELECT_COMMON_CUSTOMER =
            "select p.CMS_LE_SUB_PROFILE_ID, SOURCE_ID " +
			"from CMS_CCI_MAP p, SCI_LE_SUB_PROFILE c, SCI_LE_MAIN_PROFILE m " +
			"where c.CMS_LE_MAIN_PROFILE_ID = m.CMS_LE_MAIN_PROFILE_ID " +
			"and c.CMS_LE_SUB_PROFILE_ID=p.CMS_LE_SUB_PROFILE_ID " +
			"and CMS_CCI_NO= " +
			"( " +
			"select CMS_CCI_NO from CMS_CCI_MAP " +
			"where CMS_LE_SUB_PROFILE_ID=? " +
			"and IS_DELETED <> 'Y' " +
			") " +
			"and p.IS_DELETED <> 'Y' " +
			"and p.CMS_LE_SUB_PROFILE_ID <> ? " +
			"ORDER BY SOURCE_ID, CMS_LE_SUB_PROFILE_ID";
	
	
    //Table names
     static final String CMS_CCI_MAP = "CMS_CCI_MAP";
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
//     static final String BCA_REF = "LLP_BCA_REF_NUM";
     static final String BCA_APPROVED_DATE = "LLP_BCA_REF_APPR_DATE";
     static final String BCA_CUSTOMER_ID = "CMS_CUSTOMER_ID";
     static final String CMS_CUSTOMER_IND = "CMS_NON_BORROWER_IND";
     static final String CMS_BCA_STATUS = "CMS_BCA_STATUS";
     static final String BCA_ORIG_CNTRY = "CMS_ORIG_COUNTRY";
     static final String BCA_ORIG_ORG = "CMS_ORIG_ORGANISATION";
     static final String LMT_PROFILE_TYPE = "LMT_PROFILE_TYPE";
     static final String CMS_CREATE_IND = "CMS_CREATE_IND";
     static final String LIMIT_ID = "LIMIT_ID";



    static final String KEY_DELIMITER = ";";

    static final String COUNTRY_FILTER_SQL = "( SELECT COUNTRY_CODE FROM CMS_TEAM_COUNTRY_CODE WHERE TEAM_ID = ? ) ";
    static final String ORGANISATION_FILTER_SQL = " (SELECT ORGANISATION_CODE FROM CMS_TEAM_ORGANISATION_CODE WHERE TEAM_ID = ?) ";








      static String SELECT_CCI_PART_STAGING =
            "SELECT "
                    + "       CMS_CCI_MAP.CCI_MAP_ID, \n"
                    + "       CMS_CCI_MAP.CMS_CCI_NO, \n"
                    + "       CMS_CCI_MAP.CCI_REF_ID, \n"
                    + "       CMS_CCI_MAP.VERSION_TIME, \n"
                    + "       CMS_CCI_MAP.IS_DELETED, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n"
                    + "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n"
                    + "       SCI_LE_MAIN_PROFILE.LMP_INC_DATE, \n"
                    + "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n"
                    + "       SCI_LE_SUB_PROFILE.LSP_ID, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n"
                    + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION \n";


    static String FROM_WHERE_CCI_STAGING =
             "FROM SCI_LE_MAIN_PROFILE, \n"
                     + "    CMS_STAGE_CCI_MAP  CMS_CCI_MAP ,\n"
                     + "     SCI_LE_SUB_PROFILE  \n"
                     + "WHERE 0=0 \n"
                     + "AND CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n"
                     + "AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n"
                     + "AND (SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, SCI_LE_MAIN_PROFILE.LMP_LE_ID) \n"
                     + "IN (SELECT CMS_LSP_LMT_PROFILE_ID ,LE_ID \n"
                     + " FROM CMS_STAGE_CCI_MAP CMS_CCI_MAP \n"
                     + " WHERE 0=0 \n";




     protected  static String SELECT_CCICOUNTERPARTY_BY_GROUPCCINO_ACTUAL =
          " SELECT        \n" +
                  "       CMS_CCI_MAP.CCI_MAP_ID, \n" +
                  "       CMS_CCI_MAP.CMS_CCI_NO, \n"  +
                  "       CMS_CCI_MAP.CCI_REF_ID, \n" +
                  "       CMS_CCI_MAP.VERSION_TIME, \n"  +
                  "       CMS_CCI_MAP.IS_DELETED, \n"  +
                  "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n" +
                  "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n" +
                  "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n" +
                  "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n" +
                  "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n" +
                  "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n" +
                  "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n" +
                  "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n" +
                  "       SCI_LE_MAIN_PROFILE.LMP_INC_DATE, \n" +
                  "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n" +
                  "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n" +
                  "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n" +
                  "       SCI_LE_SUB_PROFILE.LSP_ID, \n" +
                  "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n" +
                  "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION \n" +
                  "FROM SCI_LE_MAIN_PROFILE,\n" +
                  "      ( SELECT *  \n" +
                  "        FROM CMS_CCI_MAP \n" +
                  "        WHERE  CMS_CCI_NO = ? \n" +
                  "               AND CMS_CCI_MAP.IS_DELETED <> 'Y' \n" +
                  "      ) as CMS_CCI_MAP ,\n" +
                  "      SCI_LE_SUB_PROFILE \n" +
                  " WHERE 0=0  \n" +
                  " AND CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n" +
                  " AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n" +
                  " ";


    protected  static String SELECT_CCICOUNTERPARTY_BY_GROUPCCINOREF =
              " SELECT        \n" +
                      "       CMS_CCI_MAP.CCI_MAP_ID, \n" +
                      "       CMS_CCI_MAP.CMS_CCI_NO, \n" +
                      "       CMS_CCI_MAP.CCI_REF_ID, \n" +
                      "       CMS_CCI_MAP.VERSION_TIME, \n" +
                      "       CMS_CCI_MAP.IS_DELETED, \n" +
                      "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n" +
                      "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n" +
                      "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n" +
                      "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n" +
                      "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n" +
                      "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n" +
                      "       SCI_LE_MAIN_PROFILE.LMP_ID_NUMBER, \n" +
                      "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n" +
                      "       SCI_LE_MAIN_PROFILE.LMP_INC_DATE, \n" +
                      "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n" +
                      "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n" +
                      "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n" +
                      "       SCI_LE_SUB_PROFILE.LSP_ID, \n" +
                      "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n" +
                      "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION \n" +
                      "FROM SCI_LE_MAIN_PROFILE,\n" +
                      "      ( SELECT  *  \n" +
                      "        FROM CMS_CCI_MAP \n" +
                      "        WHERE  CCI_REF_ID = ? \n" +
                      "               AND CMS_CCI_MAP.IS_DELETED <> 'Y' \n" +
                      "      ) as CMS_CCI_MAP ,\n" +
                      "      SCI_LE_SUB_PROFILE \n" +
                      " WHERE 0=0  \n" +
                      " AND CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n" +
                      " AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n" +
                      " ";



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
        if ((aValue != null) && (aValue.trim().length() > 0)) {
            return false;
        } else {
            return true;
        }
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
        }   catch (Exception e2) {
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


     public String formatDate(Date date) {
        try {
        	return DateUtil.formatDate("dd/MMM/yyyy", date);
//            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
//            return sdf2.format(date);
        }
        catch (Exception ex) {
        }
        return "";
    }


      /*protected  static String SELECT_CCI_CUSTOMER =
             "SELECT  distinct /n"
                + "       CMS_CCI_MAP.CCI_MAP_ID, \n"
                + "       CMS_CCI_MAP.CMS_CCI_NO, \n"
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
                + "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION \n";*/





 /* protected  static String SELECT_CCI_PART_ACTUAL =
              "SELECT  distinct /n"
                 + "       CMS_CCI_MAP.CCI_MAP_ID, \n"
                 + "       CMS_CCI_MAP.CMS_CCI_NO, \n"
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
*/

 /* protected  static String FROM_WHERE_CCI_ACTUAL =
             "FROM SCI_LE_MAIN_PROFILE, \n"
                     + "    CMS_CCI_MAP  CMS_CCI_MAP ,\n"
                     + "     SCI_LE_SUB_PROFILE  \n"
                     + " WHERE 0=0 \n"
                     + " AND CMS_CCI_MAP.IS_DELETED <> 'Y' \n"
                     + " AND CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n"
                     + " AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n"
                     + " AND (SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, SCI_LE_MAIN_PROFILE.LMP_LE_ID) \n"
                     + " IN (SELECT CMS_LSP_LMT_PROFILE_ID ,LE_ID \n"
                     + " FROM CMS_CCI_MAP CMS_CCI_MAP \n"
                     + " WHERE 0=0 \n" ;*/


  /*protected   static String FROM_CCI_CUSTOMER =
            "FROM SCI_LE_MAIN_PROFILE, \n"
                    + "     CMS_CCI_MAP ,"
                    + "     SCI_LE_SUB_PROFILE LEFT OUTER JOIN  SCI_LSP_LMT_PROFILE \n"
                    + "        ON SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n"
                    + "      AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = '" + ICMSConstant.AA_TYPE_BANK + "' \n"
                    + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' \n"
                    + " WHERE 0=0 \n"
                    + " AND CMS_CCI_MAP.IS_DELETED <> 'Y' \n"
                    + " AND CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID \n"
                    + " AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";
*/

/*protected  static String SELECT_CCICOUNTERPARTY_BY_GROUPCCINO =
          " SELECT        \n" +
                  "       CMS_CCI_MAP.CCI_MAP_ID, \n" +
                  "       CMS_CCI_MAP.CMS_CCI_NO, \n"  +
                  "       CMS_CCI_MAP.CCI_REF_ID, \n" +
                  "       CMS_CCI_MAP.VERSION_TIME, \n"  +
                  "       CMS_CCI_MAP.IS_DELETED, \n"  +
                  "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n" +
                  "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n" +
                  "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n" +
                  "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n" +
                  "       SCI_LE_MAIN_PROFILE.LMP_ID_NUM, \n" +
                  "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n" +
                  "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n" +
                  "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n" +
                  "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID, \n" +
                  "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n" +
                  "       SCI_LE_SUB_PROFILE.LSP_ID, \n" +
                  "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n" +
                  "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION \n" +
                  "FROM SCI_LE_MAIN_PROFILE,\n" +
                  "      ( SELECT *  \n" +
                  "        FROM CMS_CCI_MAP \n" +
                  "        WHERE  CCI_MAP_ID = ? \n" +
                  "               AND CMS_CCI_MAP.IS_DELETED <> 'Y' \n" +
                  "      ) as CMS_CCI_MAP ,\n" +
                  "      SCI_LE_SUB_PROFILE \n" +
                  " WHERE 0=0  \n" +
                  " AND CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n" +
                  " AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n" +
                  " ";*/


 /*    static String SELECT_CUSTOMER_PART_FROM_WHERE_CUSTOMER_SEACH_ONLY =
             "FROM SCI_LE_MAIN_PROFILE, \n"
                     + "     SCI_LE_SUB_PROFILE LEFT OUTER JOIN  \n"
                     + "       SCI_LSP_LMT_PROFILE ON SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID \n"
                     + "      AND SCI_LSP_LMT_PROFILE.LMT_PROFILE_TYPE = '" + ICMSConstant.AA_TYPE_BANK + "' \n"
                     + "      AND SCI_LSP_LMT_PROFILE.CMS_BCA_STATUS <> 'DELETED' \n"
                     + "WHERE 0=0 "
                     + "AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";
*/

}

