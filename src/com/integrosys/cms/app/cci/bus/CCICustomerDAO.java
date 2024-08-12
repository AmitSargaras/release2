package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CCICustomerDAO extends CCICustomerConstant implements ICCICustomerDAO {

    /**
     * Default Constructor
     */
    public CCICustomerDAO() {
    }


    /**
     * Search for a list of customer documents based on the criteria
     *
     * @param criteria is of type CounterpartySearchCriteria
     * @return SearchResult - the object that contains a list of OBCustomerSearchResult objects or null if no records are found.
     * @throws com.integrosys.base.businfra.search.SearchDAOException
     *          if errors
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria) throws SearchDAOException {
        DefaultLogger.debug(this, ">>>>>>>> searchCCICustomer - Before search customer ");
        if (criteria == null) {
            throw new SearchDAOException("The CounterpartySearchCriteria is null !!!");
        }

        //FIXME: switch to use direct jdbc
        String useJDBC = PropertyManager.getValue("customerdao.use.jdbc");
        DBUtil dbUtil = null;

        ResultSet rs = null;
        SQLParameter params = SQLParameter.getInstance();

        try {
            dbUtil = getSearchCustomerSQL(criteria, params);
            long startTime = System.currentTimeMillis();
            rs = dbUtil.executeQuery();
            DefaultLogger.debug(this, ">>>>>>>> searchCCICustomer - query completed in " + (System.currentTimeMillis() - startTime));

            long processRSTime = System.currentTimeMillis();
            SearchResult searchResult = processResultSet(rs, criteria);


            if (searchResult == null) {
                DefaultLogger.debug(this, ">>>>>>>> searchCCICustomer - Process [0] in  : " + (System.currentTimeMillis() - processRSTime));
            } else {
                DefaultLogger.debug(this, ">>>>>>>> searchCCICustomer - Process [" + searchResult.getResultList().size() + "] in  : " + (System.currentTimeMillis() - processRSTime));
            }
            DefaultLogger.debug(this, ">>>>>>>> searchCCICustomer - After search customer");

            return searchResult;

        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in searchCCICustomer", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in searchCCICustomer", ex);
        } finally {
            finalize(dbUtil, rs);

        }
    }

    /**
     * This is used  for actual data  by ccno
     *
     * @param criteria
     * @return
     * @throws SearchDAOException
     */

    public ICCICounterpartyDetails getCCICounterpartyDetails(CounterpartySearchCriteria criteria) throws SearchDAOException {

        ICCICounterpartyDetails counterPartyDetails = null;
        ICCICounterparty obj = null;
        SQLParameter params = SQLParameter.getInstance();
        DBUtil dbUtil = null;
        ResultSet rs = null;
        long CCINo = 0;

        try {
            dbUtil = getSearchCustomerSQL(criteria, params);
            rs = dbUtil.executeQuery();
            counterPartyDetails = this.processCCICounterpartyDetails(rs);

        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getCCICounterpartyDetails", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in getCCICounterpartyDetails", ex);
        } finally {
            finalize(dbUtil, rs);
        }
        return counterPartyDetails;

    }

    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINo(long groupCCINo) throws SearchDAOException {


        ICCICounterpartyDetails counterPartyDetails = null;
        StringBuffer querySQL = new StringBuffer();
        ResultSet rs = null;
        DBUtil dbUtil = null;
        SQLParameter params = SQLParameter.getInstance();
        querySQL.append(SELECT_CCICOUNTERPARTY_BY_GROUPCCINO_ACTUAL);
        params.addLong(new Long(groupCCINo));

        DefaultLogger.debug(this, "SELECT_CCICOUNTERPARTY_BY_GROUPCCINO_ACTUAL  :\n " + querySQL.toString());
        DefaultLogger.debug(this, "getCCICounterpartyByGroupCCINo \n  groupCCINo : " + groupCCINo);

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            counterPartyDetails = this.processCCICounterpartyDetails(rs);
        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getCCICounterpartyByGroupCCINo", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in getCCICounterpartyByGroupCCINo", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return counterPartyDetails;
    }


    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINoRef(long groupCCINoRef) throws SearchDAOException {


        ICCICounterpartyDetails counterPartyDetails = null;
        StringBuffer querySQL = new StringBuffer();
        ResultSet rs = null;
        DBUtil dbUtil = null;
        SQLParameter params = SQLParameter.getInstance();
        querySQL.append(SELECT_CCICOUNTERPARTY_BY_GROUPCCINOREF);
        params.addLong(new Long(groupCCINoRef));

        DefaultLogger.debug(this, "getCCICounterpartyByGroupCCINoRef  :\n " + querySQL.toString());
        DefaultLogger.debug(this, "getCCICounterpartyByGroupCCINoRef  groupCCINoRef :\n " + groupCCINoRef);

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            counterPartyDetails = this.processCCICounterpartyDetails(rs);
        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getCCICounterpartyByGroupCCINoRef", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in getCCICounterpartyByGroupCCINoRef", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return counterPartyDetails;
    }


    public ICCICounterparty getCustomerDetails(ICCICounterparty aICCICounterparty) throws SearchDAOException {

        StringBuffer querySQL = new StringBuffer();
        StringBuffer addressSQL = new StringBuffer();
        ResultSet rs = null;
        DBUtil dbUtil = null;
        SQLParameter params = SQLParameter.getInstance();
        SQLParameter params1 = SQLParameter.getInstance();

        querySQL.append(SELECT_CUSTOMER_PART_SELECT_NEW);
        querySQL.append(SELECT_CUSTOMER_PART_FROMWHERE_NEW);
        querySQL.append(" AND SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = ?");
        params.addLong(new Long(aICCICounterparty.getSubProfileID()));

        addressSQL.append(CUSTOMER_ADDRESS);
        addressSQL.append("AND CMS_LE_SUB_PROFILE_ID = ?");
        params1.addLong(new Long(aICCICounterparty.getSubProfileID()));

        // System.out.println("CCICustomerDAO  getCustomerDetails (SQL) :\n " + querySQL.toString());
        //  System.out.println("CCICustomerDAO  addressSQL (SQL) :\n " + addressSQL.toString());
        // System.out.println("CCICustomerDAO  getSubProfileID() :" + aICCICounterparty.getSubProfileID());

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            this.processCustomerDetails(rs, aICCICounterparty);

            dbUtil = new DBUtil();
            dbUtil.setSQL(addressSQL.toString());
            CommonUtil.setSQLParams(params1, dbUtil);
            rs = dbUtil.executeQuery();
            this.setCustomerAddress(rs, aICCICounterparty);
        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getCustomerDetails", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in getCustomerDetails", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return aICCICounterparty;

    }


    public ICCICounterpartyDetails processCCICounterpartyDetails(ResultSet rs) throws SQLException {

        ICCICounterpartyDetails counterPartyDetails = null;
        ICCICounterparty[] iCCICounterparty = null;
        List iCCICounterpartyList = new ArrayList();
        long CCINo = ICMSConstant.LONG_INVALID_VALUE;
        long groupCCINoRef = ICMSConstant.LONG_INVALID_VALUE;

        while (rs.next()) {
            ICCICounterparty obj = new OBCCICounterparty();
            CCINo = rs.getLong("CMS_CCI_NO");
            groupCCINoRef = rs.getLong("CCI_REF_ID");
            obj.setGroupCCIMapID(rs.getLong("CCI_MAP_ID"));
            obj.setVersionTime(rs.getLong("VERSION_TIME"));
            String delInd = rs.getString("IS_DELETED");
            if (null != delInd && !"".equals(delInd) && delInd.equals("Y")) {
                obj.setDeletedInd(true);
            }
            obj.setGroupCCINo(CCINo);
            obj.setGroupCCINoRef(groupCCINoRef);

            obj.setLegalName(rs.getString(LEGAL_NAME));
            // obj.setDob(formatDate(rs.getDate("LMP_INC_DATE")));
            obj.setDob(rs.getTimestamp("LMP_INC_DATE"));
            obj.setLegalID(rs.getLong(LEGAL_ID));
            obj.setCustomerName(rs.getString(CUSTOMER_NAME));
            obj.setSubProfileID(rs.getLong(CUSTOMER_ID));
            obj.setSourceID(rs.getString("SOURCE_ID"));
            obj.setLmpLeID(rs.getString("LMP_LE_ID"));

            //String IdNO = rs.getString("LMP_ID_NUM") ;
            //if (!isEmpty(IdNO)){
            //     obj.setIdNO(rs.getString("LMP_ID_NUM"));
            //}else{
            obj.setIdNO(rs.getString("LMP_ID_NUMBER"));
            //}
            iCCICounterpartyList.add(obj);
        }
        if (iCCICounterpartyList.size() > 0) {
            iCCICounterparty = (ICCICounterparty[]) iCCICounterpartyList.toArray(new ICCICounterparty[0]);
            counterPartyDetails = new OBCCICounterpartyDetails();
            counterPartyDetails.setICCICounterparty(iCCICounterparty);
            counterPartyDetails.setGroupCCINo(CCINo);
            counterPartyDetails.setGroupCCINoRef(groupCCINoRef);
        }
        return counterPartyDetails;
    }


    private void processCustomerDetails(ResultSet rs, ICCICounterparty obj) throws SQLException {

        while (rs.next()) {
            obj.setLegalName(rs.getString(LEGAL_NAME));
            //obj.setDob(formatDate(rs.getDate("LMP_INC_DATE")));
            obj.setDob(rs.getTimestamp("LMP_INC_DATE"));
            obj.setLegalID(rs.getLong(LEGAL_ID));
            obj.setCustomerName(rs.getString(CUSTOMER_NAME));
            obj.setSubProfileID(rs.getLong(CUSTOMER_ID));
            obj.setSourceID(rs.getString("SOURCE_ID"));
            obj.setLmpLeID(rs.getString("LMP_LE_ID"));

            //String IdNO = rs.getString("LMP_ID_NUM") ;
            //if (!isEmpty(IdNO)){
            //obj.setIdNO(rs.getString("LMP_ID_NUM"));
            //}else{
            obj.setIdNO(rs.getString("LMP_ID_NUMBER"));
            //}

        }
    }


    private void setCustomerAddress(ResultSet rs, ICCICounterparty obj) throws SQLException {

        if (rs.next()) {
            OBCustomerAddress mailDetails = new OBCustomerAddress();
            mailDetails.setAddressLine1(rs.getString("LRA_ADDR_LINE_1"));
            mailDetails.setAddressLine2(rs.getString("LRA_ADDR_LINE_2"));
            mailDetails.setCity(rs.getString("LRA_CITY_TEXT"));
            mailDetails.setState(rs.getString("LRA_STATE"));
            mailDetails.setPostalCode(rs.getString("LRA_POST_CODE"));
            mailDetails.setCountryCode(rs.getString("LRA_CNTRY_ISO_CODE"));
            obj.setAddress(mailDetails);
        }
    }

    /**
     * Method to form the sql condition, given the criteria object
     *
     * @param criteria - customerDocSearchCriteria
     * @return String - the formatted sql condition
     * @throws com.integrosys.base.businfra.search.SearchDAOException
     *          if errors
     */
    private DBUtil getSearchCustomerSQL(CounterpartySearchCriteria criteria, SQLParameter params) throws SearchDAOException {
        return getSearchCustomerSQL(null, criteria, params, false);
    }

    /**
     * Method to form the sql condition, given the criteria object
     *
     * @param criteria - customerDocSearchCriteria
     * @return String - the formatted sql condition
     * @throws com.integrosys.base.businfra.search.SearchDAOException
     *          if errors
     */
    private DBUtil getSearchCustomerSQL(String prefix, CounterpartySearchCriteria criteria, SQLParameter params, boolean isCount)
            throws SearchDAOException {

        if (criteria == null) {
            throw new SearchDAOException("Criteria is null!!!");
        }

//        System.out.println("criteria.toString() = " + criteria.toString());

        try {
            StringBuffer buf = new StringBuffer();

            //search in the following priority...

            String customerName = criteria.getCustomerName();
            String legalIDType = criteria.getLeIDType();
            String legalID = criteria.getLegalID();

            String idNO = criteria.getIdNO();
            String CCINo = criteria.getGroupCCINo();

            if (!isEmpty(customerName) || "Y".equals(criteria.getAll())) {
                buf.append("AND UPPER(sci_le_sub_profile.LSP_SHORT_NAME) ");
                buf.append(" LIKE ? ");
                params.addString(customerName.trim().toUpperCase() + "%");
            }
            if ("Y".equals(criteria.getAll())) {
            } else {
                if (!isEmpty(CCINo)) {
                    buf.append("AND CMS_CCI_MAP.CMS_CCI_NO");
                    buf.append(" = ? ");
                    params.addLong(new Long(CCINo.trim()));
                }
            }
            if (!isEmpty(legalIDType)) {
                buf.append("AND sci_le_main_profile.SOURCE_ID");
                buf.append(" = ? ");
                params.addString(legalIDType.trim());
            }
            if (!isEmpty(legalID)) {
                buf.append("AND sci_le_main_profile.LMP_LE_ID");
                buf.append(" = ? ");
                params.addString(legalID.trim());
            }
            if (!isEmpty(idNO)) {

                buf.append("AND (");

                buf.append("UPPER(sci_le_main_profile.LMP_ID_OLD_NUM)");
                buf.append(" = ? ");
                params.addString(idNO.trim().toUpperCase());

                buf.append(" OR ");

                //buf.append("UCASE(sci_le_main_profile.LMP_ID_NUM)");
                //buf.append(" = ? ");
                //params.addString(idNO.trim().toUpperCase());

                //buf.append(" OR ");

                buf.append("UPPER(sci_le_main_profile.LMP_ID_NUMBER)");
                buf.append(" = ? ");
                params.addString(idNO.trim().toUpperCase());

                buf.append(")");

            }

            /* if (criteria.getByLimit()) {
                buf.append(" AND sci_lsp_lmt_profile.CMS_LSP_LMT_PROFILE_ID IS NOT NULL ");
            }*/

            ITrxContext ctx = criteria.getCtx();

            /* if (ctx != null) {
                if (buf.length() == 0) buf.append(" 1 = 1 ");
                ITeam team = ctx.getTeam();
                String[] country = team.getCountryCodes();
                String[] org = team.getOrganisationCodes();
                if (!CommonUtil.isEmptyArray(country) && !CommonUtil.isEmptyArray(org)) {
                    boolean includeNonBorrower = true;
                    boolean includeOrgCodeDAP = false;
                    getDAPFilterSQL(buf, params, team.getTeamID(), includeNonBorrower, includeOrgCodeDAP);
                } else {
                    throw new DAPFilterException("Country or Organisation List in Team is empty.");
                }
            }*/

            StringBuffer querySQL = new StringBuffer();
            String condition = buf.toString();


            if (isCount) {
                querySQL.append(SELECT_DUMMY_PART_SELECT);
            } else {
                if (criteria.getCustomerSeach()) {
                    querySQL.append(SELECT_DISTINCT_STMT);
                    querySQL.append(SELECT_CUSTOMER_CCI_CUST_SEARCH);
                    querySQL.append(SELECT_CUSTOMER_PART_SELECT);
                    querySQL.append(SELECT_CUSTOMER_PART_FROMWHERE_CUST_SEARCH);
                } else {
                    //querySQL.append(SELECT_CCI_CUSTOMER_NEW);
                    //querySQL.append(FROM_CCI_CUSTOMER_NEW);
                    querySQL.append(SELECT_DISTINCT_STMT);
                    querySQL.append(SELECT_CUSTOMER_CCI);
                    querySQL.append(SELECT_CUSTOMER_PART_SELECT);
                    querySQL.append(SELECT_CUSTOMER_PART_FROMWHERE);
                    querySQL.append(SELECT_CUSTOMER_CCI_PART_FROMWHERE);

                }
            }


            if (!isEmpty(condition)) {
                querySQL.append(condition);
            }


            if (!isCount) {
                querySQL.append(CUSTOMER_SORT_ORDER);
            }

            if (null != prefix) {
                String tmp = querySQL.toString();
                querySQL = new StringBuffer();
                querySQL.append(prefix);
                querySQL.append("(");
                querySQL.append(tmp);
                querySQL.append(") temp1");
            }
            DefaultLogger.debug(this, "getSearchCustomerSQL Search :\n " + querySQL.toString());

            DBUtil util = new DBUtil();
            util.setSQL(querySQL.toString());
            //params.addList(params.asList());
            CommonUtil.setSQLParams(params, util);
            return util;
        } catch (Exception e) {
            throw new SearchDAOException("Caught Exception!", e);
        }
    }

    /**
     * Helper method to get the DAP filter. Country filter by default.
     * Organisation Code filter added by turning on includeOrgCodeDAP flag.
     *
     * @param buf                - sql string buffer
     * @param params             - sql bind params
     * @param teamID             - team setup on which to base filter on
     * @param includeNonBorrower - boolean flag to include condition for non-borrower
     */
    private void getDAPFilterSQL(StringBuffer buf, SQLParameter params, long teamID,
                                 boolean includeNonBorrower, boolean includeOrgCodeDAP) {

        buf.append(" AND ( EXISTS ( SELECT 1 FROM BORROWER_LOCATION_VIEW WHERE ( ");
        buf.append("    lmtprofile = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID ");
        buf.append("    AND (( BORROWER_LOCATION_VIEW.CMS_ORIG_COUNTRY IN ");
        buf.append(COUNTRY_FILTER_SQL);
        params.addLong(new Long(teamID));

        if (includeOrgCodeDAP) {
            buf.append(" AND BORROWER_LOCATION_VIEW.PROF_ORG IN ");
            buf.append(ORGANISATION_FILTER_SQL);
            params.addLong(new Long(teamID));
        }

        buf.append(" ) OR ( BORROWER_LOCATION_VIEW.LIMIT_COUNTRY IN ");
        buf.append(COUNTRY_FILTER_SQL);
        params.addLong(new Long(teamID));

        if (includeOrgCodeDAP) {
            buf.append(" AND BORROWER_LOCATION_VIEW.LIMIT_ORG IN ");
            buf.append(ORGANISATION_FILTER_SQL);
            params.addLong(new Long(teamID));
        }

        buf.append(" ) OR (BORROWER_LOCATION_VIEW.SECURITY_LOCATION IN ");
        buf.append(COUNTRY_FILTER_SQL);
        params.addLong(new Long(teamID));

        if (includeOrgCodeDAP) {
            buf.append(" AND SECURITY_ORGANISATION IN ");
            buf.append(ORGANISATION_FILTER_SQL);
            params.addLong(new Long(teamID));
        }

        buf.append(" ))) ");


        buf.append(" ) ");
        if (includeNonBorrower) {
            appendNonBorrowerCond(buf, params, teamID, includeOrgCodeDAP);
        }
        buf.append(" ) ");
    }

    /**
     * Helper method to get the DAP filter for non-borrower. Country filter by default.
     * Organisation Code filter added by turning on includeOrgCodeDAP flag.
     *
     * @param buf    - sql string buffer
     * @param params - sql bind params
     * @param teamID - team setup on which to base filter on
     */
    private void appendNonBorrowerCond(StringBuffer buf, SQLParameter params, long teamID, boolean includeOrgCodeDAP) {
        buf.append(" OR ( ");
        buf.append(CUSTOMER_TABLE);
        buf.append(".");
        buf.append(CMS_CUSTOMER_IND);
        buf.append(" = 'Y' ");

        //// weiling : START
        //// weiling : added filtering for non borrower orig country and org code
        //// weiling : instead of using the coborrower location view
        buf.append(" AND ").append(CUSTOMER_TABLE).append(".").append(CUSTOMER_COUNTRY);
        buf.append(" IN ").append(COUNTRY_FILTER_SQL);
        params.addLong(new Long(teamID));

        if (includeOrgCodeDAP) {
            buf.append(" AND ").append(CUSTOMER_TABLE).append(".").append(CUSTOMER_ORGANISATION);
            buf.append(" IN ").append(ORGANISATION_FILTER_SQL);
            params.addLong(new Long(teamID));
        }

        buf.append(") ");

    }

    /**
     * Process the customer doc search result
     *
     * @param rs - ResultSet
     * @return list - the list of customer doc from the resultset
     * @throws java.sql.SQLException if errors
     */
    private SearchResult processResultSet(ResultSet rs, CounterpartySearchCriteria criteria) throws SQLException {
        List resultList = new ArrayList();

        //// weiling - find out when to terminate based on the paging criteria
        int startIndex = criteria.getStartIndex();
        int requiredItems = criteria.getNItems();
        int maxItems = requiredItems * 10; // 10 pages
        if (startIndex >= maxItems)
            maxItems = ((int) Math.ceil(startIndex + 1 / maxItems)) * maxItems;
        int endIndex = startIndex + 10;
        int count = 0;

        DefaultLogger.debug(this, ">>>>>>>> processResultSet - startIndex : " + startIndex);
        DefaultLogger.debug(this, ">>>>>>>> processResultSet - requiredItems : " + requiredItems);
        DefaultLogger.debug(this, ">>>>>>>> processResultSet - endIndex : " + endIndex);

        while (rs.next() && (count <= maxItems)) {
            //// weiling : forward the rs pointer to the startIndex
            //if (count < startIndex) {
            //continue;
            //}

            //// weiling : only instantiate the nitems required
            if (startIndex <= count) {
                if (count < endIndex) {
                    OBCustomerSearchResult result = processResultSet(rs, criteria.getCustomerSeach());
                    resultList.add(result);
                }
            }
            count++;
        }
        DefaultLogger.debug(this, "*********************RESULT SET SIZE********" + resultList.size());

        return (count == 0) ? null : new SearchResult(criteria.getStartIndex(), resultList.size(), count, resultList);
    }

    /**
     * Helper method to get process a single row of result
     *
     * @param rs - ResultSet
     * @return SearchResult - customer from the current record
     */
    private OBCustomerSearchResult processResultSet(ResultSet rs, boolean customerSeachOnly) throws SQLException {
        OBCustomerSearchResult obj = new OBCustomerSearchResult();
        obj.setLegalName(rs.getString(LEGAL_NAME));
        obj.setDob(rs.getTimestamp("LMP_INC_DATE"));
        obj.setLegalID(rs.getLong(LEGAL_ID));
        obj.setLegalReference(rs.getString(LEGAL_REF));
        obj.setLmpLeID(rs.getString(LEGAL_REF));
        obj.setCustomerName(rs.getString(CUSTOMER_NAME));
        obj.setSubProfileID(rs.getLong(CUSTOMER_ID));
        obj.setSubProfileReference(rs.getString(CUSTOMER_REF));
        obj.setGroupCCINo(rs.getString("CCI_NO"));
        obj.setSourceID(rs.getString("SOURCE_ID"));
        obj.setIdNo(rs.getString("LMP_ID_NUMBER"));

        if (customerSeachOnly) {
            obj.setOrigLocCntry(rs.getString("LMP_INC_CNTRY_ISO_CODE"));
        }

        return obj;
    }

    /**
     * @param lmt_profile_id
     * @return
     * @throws SearchDAOException
     */

    public String searchCustomer(long lmt_profile_id) throws SearchDAOException {

        String CMS_CUSTOMER_ID = "1130000000192811";
        StringBuffer querySQL = new StringBuffer();
        SQLParameter params = SQLParameter.getInstance();
//        System.out.println("CCICustomerDAO (searchCustomer) lmt_profile_id = " + lmt_profile_id);

        querySQL.append("select * from SCI_LSP_LMT_PROFILE ");
        querySQL.append(" where CMS_LSP_LMT_PROFILE_ID =  ");
        querySQL.append(lmt_profile_id);
        DBUtil dbUtil = null;
        ResultSet rs = null;


        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            rs = dbUtil.executeQuery();
            while (rs.next()) {
                CMS_CUSTOMER_ID = rs.getLong("CMS_CUSTOMER_ID") + "";
            }

        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in searchCustomer", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in searchCustomer", ex);
        } finally {
            finalize(dbUtil, rs);
        }
//        System.out.println("CMS_CUSTOMER_ID = " + CMS_CUSTOMER_ID);
        return CMS_CUSTOMER_ID;

    }

    public ICCICounterparty getCustomerAddress(ICCICounterparty value) throws SearchDAOException {

        StringBuffer querySQL = new StringBuffer();
        StringBuffer addressSQL = new StringBuffer();
        ResultSet rs = null;
        DBUtil dbUtil = null;
        SQLParameter params = SQLParameter.getInstance();
        addressSQL.append(CUSTOMER0_ADDRESS_ONLY);
        //addressSQL.append(" AND LSP.LSP_LE_ID = ? ");
        params.addString(value.getLmpLeID());
        // DefaultLogger.debug(this," getCustomerAddress  = " + value.getLmpLeID());
        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(CUSTOMER0_ADDRESS_ONLY);
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            this.setCustomerAddress(rs, value);
        } catch (SQLException ex) {
           // DefaultLogger.debug(this, "SQLException  in getCustomerAddress  = " + value.getLmpLeID());
            ex.printStackTrace();
        } catch (Exception ex) {
          //  DefaultLogger.debug(this, "Exception in getCustomerAddress  = " + value.getLmpLeID());
        } finally {
            finalize(dbUtil, rs);
        }

        return value;

    }


    public HashMap isExistCCICustomer(long groupCCINo, String[] cciObj) throws SearchDAOException {

        HashMap map = new HashMap();
        StringBuffer querySQL = new StringBuffer();
        ArrayList params = new ArrayList();

        querySQL.append(SELECT_CCI_CUSTOMER_NEW);
        querySQL.append(", 'Customer: \"'|| LSP_SHORT_NAME ||'\" already exists in CCI No: '|| to_char(CMS_CCI_NO) as ERRORMSG ");
        querySQL.append(FROM_CCI_CUSTOMER_NEW);

        if (groupCCINo != ICMSConstant.LONG_INVALID_VALUE) {
            querySQL.append("AND CMS_CCI_MAP.CMS_CCI_NO");
            querySQL.append(" <> ? ");
            params.add(new Long(groupCCINo));
        }

        if (!CommonUtil.isEmptyArray(cciObj)) {
            querySQL.append("AND sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID ");
            CommonUtil.buildSQLInList(cciObj, querySQL, params);
            for (int i = 0; i < cciObj.length; i++) {
                DefaultLogger.debug(this, "cciObj [" + i + "]  = " + cciObj[i]);
            }
        }
        DBUtil dbUtil = null;
        ResultSet rs = null;
        List list = new ArrayList();
        String check = ICMSConstant.FALSE_VALUE;

        DefaultLogger.debug(this, ">>>>>>>> isExistCCICustomer - querySQL  : \n" + querySQL.toString());

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            while (rs.next()) {
                check = ICMSConstant.TRUE_VALUE;
                list.add(rs.getString("ERRORMSG"));

            }
            map.put("ERRORMSG", list);
            map.put("isExistCCICustomer", check);


        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in isExistCCICustomer", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in isExistCCICustomer", ex);
        } finally {
            finalize(dbUtil, rs);
        }
        return map;

    }

    public HashMap getCommonCustomer(long subprofileID) throws SearchDAOException {

        ResultSet rs = null;
        DBUtil dbUtil = null;

        try {
            String sql = QUERY_SELECT_COMMON_CUSTOMER;
            dbUtil = new DBUtil();
            dbUtil.setSQL(sql);
            dbUtil.setLong(1, subprofileID);
            dbUtil.setLong(2, subprofileID);

            DefaultLogger.debug(this, "getCommonCustomer  :\n " + sql);
            DefaultLogger.debug(this, "getCommonCustomer  subprofileID :\n " + subprofileID);
            rs = dbUtil.executeQuery();

            HashMap map = new HashMap();

            while (rs.next()) {
                long subProfileId = rs.getLong(1);
                String sourceID = rs.getString(2);
                map.put(sourceID, new Long(subProfileId));

            }

            return map;


        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getCommonCustomer", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in getCommonCustomer", ex);
        } finally {
            finalize(dbUtil, rs);
        }

    }


}
