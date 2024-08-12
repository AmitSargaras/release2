package com.integrosys.cms.app.custgrpi.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.cci.bus.CCICustomerDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipDAO;
import com.integrosys.cms.app.custrelationship.bus.CustRelationshipException;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import org.apache.commons.lang.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class CustGrpIdentifierDAO extends CustGrpIdentifierCustomerConstant implements ICustGrpIdentifierDAO {

    protected static final String LEGAL_ENTITY_TABLE = "SCI_LE_MAIN_PROFILE";
    protected static final String CUSTOMER_TABLE = "SCI_LE_SUB_PROFILE";
    protected static final String LIMIT_PROFILE_TABLE = "SCI_LSP_LMT_PROFILE";
    protected static final String TRANSACTION_TABLE = ICMSTrxTableConstants.TRX_TBL_NAME;
    protected static final String TRADE_AGREEMENT_TABLE = "CMS_TRADING_AGREEMENT";

    //Column values for legal entity table
    protected static final String LEGAL_NAME = "LMP_LONG_NAME";
    protected static final String LEGAL_ID = "CMS_LE_MAIN_PROFILE_ID";
    protected static final String LEGAL_REF = "LMP_LE_ID";
    protected static final String CUSTOMER_TYPE = "LMP_TYPE_VALUE";

    //Column values for customer table
    protected static final String CUSTOMER_NAME = "LSP_SHORT_NAME";
    protected static final String CUSTOMER_ID = "CMS_LE_SUB_PROFILE_ID";
    protected static final String CUSTOMER_REF = "LSP_ID";
    protected static final String CUSTOMER_LE_ID = "CMS_LE_MAIN_PROFILE_ID";
    protected static final String CUSTOMER_LE_REF = "LSP_LE_ID";
    protected static final String CUSTOMER_COUNTRY = "CMS_SUB_ORIG_COUNTRY";
    protected static final String CUSTOMER_ORGANISATION = "CMS_SUB_ORIG_ORGANISATION";


    protected static final String SELECT_GROUP_MEMBER_SQL =
            "SELECT grp.GRP_ID, grp.grp_name, mem.entity_id \n";

    protected static final String SELECT_SUBGROUP_MEMBER_SQL =
            "SELECT grp.GRP_ID, grp.grp_name, submem.entity_id \n";

    protected static final String SELECT_GROUP_SQL =
            "SELECT grp.GRP_ID, grp.grp_name, grp.grp_lmt, grp.grp_no, grp.grp_currency \n";

    protected static final String SELECT_SUBGROUP_SQL =
            "SELECT grp.GRP_ID GRP_ID, subgrp.GRP_ID SUBGRP_ID \n";

    protected static final String FROM_MASTER_GROUP_SQL =
            "FROM cms_cust_grp grp, cms_group_member mem";

    protected static final String FROM_SUBGROUP_SQL =
            FROM_MASTER_GROUP_SQL + ", cms_cust_grp subgrp ";

    protected static final String FROM_SUBGROUP_MEMBER_SQL =
            FROM_SUBGROUP_SQL + ", cms_group_member submem ";

    protected static final String FROM_MASTER_GROUP_SUBPROFILE_SQL =
            FROM_MASTER_GROUP_SQL + ", sci_le_sub_profile sp \n";

    protected static final String FROM_SUBGROUP_SUBPROFILE_SQL =
            FROM_MASTER_GROUP_SUBPROFILE_SQL + ", cms_cust_grp subgrp, cms_group_member submem \n";

    protected static final String WHERE_MASTER_GROUP_SQL =
            "    WHERE grp.grp_id = mem.grp_id \n" +
                    //"      AND grp.IS_MASTER_GRP = 'Y' \n"   +
                    //"      AND sp.cms_le_sub_profile_id = mem.entity_id"   +
                    //"      AND sp.cms_le_sub_profile_id = ?"   +
                    "      AND mem.entity_type = '" + ICMSConstant.ENTITY_TYPE_CUSTOMER + "' \n" +
                    "      AND grp.status <> 'DELETED' \n" +
                    "      AND mem.status <> 'DELETED' \n";

    protected static final String WHERE_SUBGROUP_SQL =
            "    WHERE grp.grp_id = mem.grp_id \n" +
                    "      AND mem.entity_id = subgrp.grp_id \n" +
                    "      AND grp.IS_MASTER_GRP = 'Y' \n" +
                    "      AND subgrp.IS_MASTER_GRP = 'N' \n" +
                    "      AND mem.entity_type = '" + ICMSConstant.ENTITY_TYPE_GROUP + "' \n" +
                    "      AND grp.status <> 'DELETED' \n" +
                    "      AND mem.status <> 'DELETED' \n" +
                    "      AND subgrp.status <> 'DELETED' \n";

    protected static final String WHERE_SUBGROUP_MEMBER_SQL =
            "    WHERE grp.grp_id = mem.grp_id \n" +
                    "      AND mem.entity_id = subgrp.grp_id \n" +
                    "      AND subgrp.grp_id = submem.grp_id \n" +
                    "      AND grp.IS_MASTER_GRP = 'Y' \n" +
                    "      AND subgrp.IS_MASTER_GRP = 'N' \n" +
                    //"      AND sp.cms_le_sub_profile_id = submem.entity_id"   +
                    //"      AND sp.cms_le_sub_profile_id = ?"   +
                    "      AND mem.entity_type = '" + ICMSConstant.ENTITY_TYPE_GROUP + "' \n" +
                    "      AND grp.status <> 'DELETED' \n" +
                    "      AND mem.status <> 'DELETED' \n" +
                    "      AND subgrp.status <> 'DELETED' \n" +
                    "      AND submem.status <> 'DELETED' \n";


    protected static final String WHERE_MASTER_GROUP_SUBPROFILE_SQL =
            WHERE_MASTER_GROUP_SQL +
                    "      AND cms_le_sub_profile_id = mem.entity_id \n";

    protected static final String WHERE_SUBGROUP_SUBPROFILE_SQL =
            WHERE_SUBGROUP_SQL +
                    "      AND cms_le_sub_profile_id = submem.entity_id \n";

    protected static final String WHERE_MEMBER_IS_SUBGROUP_SQL =
            "    WHERE grp.grp_id = mem.grp_id \n" +
                    "      AND mem.entity_type = '" + ICMSConstant.ENTITY_TYPE_GROUP + "' \n" +
                    "      AND grp.status <> 'DELETED' \n" +
                    "      AND mem.status <> 'DELETED' \n";

    /**
     * Default Constructor
     */
    public CustGrpIdentifierDAO() {
    }


    public SearchResult searchEntryDetails(GroupMemberSearchCriteria criteria) throws SearchDAOException {
        DefaultLogger.debug(this, ">>>>>>>> searchEntryDetails - Before search customer ");
        if (criteria == null) {
            throw new SearchDAOException("The GroupMemberSearchCriteria is null !!!");
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
            DefaultLogger.debug(this, ">>>>>>>> searchEntryDetails - query completed in " + (System.currentTimeMillis() - startTime));
            long processRSTime = System.currentTimeMillis();
            SearchResult searchResult = processResultSet(rs, criteria);
            if (searchResult == null) {
                DefaultLogger.debug(this, ">>>>>>>> searchEntryDetails - Process [0] in  : " + (System.currentTimeMillis() - processRSTime));
            } else {
                DefaultLogger.debug(this, ">>>>>>>> searchEntryDetails - Process [" + searchResult.getResultList().size() + "] in  : " + (System.currentTimeMillis() - processRSTime));
            }
            DefaultLogger.debug(this, ">>>>>>>> searchEntryDetails - After search customer");
            return searchResult;

        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in searchEntryDetails", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in searchEntryDetails", ex);
        } finally {
            finalize(dbUtil, rs);

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
    private DBUtil getSearchCustomerSQL(GroupMemberSearchCriteria criteria, SQLParameter params)
            throws SearchDAOException {
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
    private DBUtil getSearchCustomerSQL(String prefix, GroupMemberSearchCriteria criteria, SQLParameter params, boolean isCount)
            throws SearchDAOException {

        if (criteria == null) {
            throw new SearchDAOException("Criteria is null!!!");
        } else {
            DefaultLogger.debug(this, "getSearchCustomerSQL criteria.toString() = " + criteria.toString());
        }

        try {
            StringBuffer buf = new StringBuffer();

            //search in the following priority...

            String customerName = criteria.getCustomerName();
            String legalIDType = criteria.getSourceType();
            String legalID = criteria.getLegalID();

            String idNO = criteria.getIdNO();


            if (!isEmpty(customerName) || "Y".equals(criteria.getAll())) {
                buf.append("AND UPPER(sci_le_sub_profile.LSP_SHORT_NAME) ");
                buf.append(" LIKE ? ");
                params.addString(customerName.trim().toUpperCase() + "%");
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

                buf.append("UPPER(sci_le_main_profile.LMP_INC_NUM_TEXT)");
                buf.append(" = ? ");
                params.addString(idNO.trim().toUpperCase());

                buf.append(")");

            }
            //else {
            //     throw new Exception("No criteria found to search the customer!!!");
            // }

            // if (criteria.getByLimit()) {
            //      buf.append(" AND sci_lsp_lmt_profile.CMS_LSP_LMT_PROFILE_ID IS NOT NULL ");
            //  }

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
                    querySQL.append(SELECT_CUSTOMER_PART_SELECT);
                    querySQL.append(SELECT_CUSTOMER_PART_FROMWHERE);
                } else {
                    // todo to be added for group
                }
            }


            if (!isEmpty(condition)) {
                //querySQL.append(" AND ");
                querySQL.append(condition);
                if (!criteria.getCustomerSeach()) {
//                        querySQL.append(" ) ");
                }
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
     * Process the customer doc search result
     *
     * @param rs - ResultSet
     * @return List - the list of customer doc from the resultset
     * @throws java.sql.SQLException if errors
     */
    private SearchResult processResultSet(ResultSet rs, GroupMemberSearchCriteria criteria) throws SQLException {
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
        obj.setLegalName(rs.getString("LMP_LONG_NAME"));
        obj.setLegalID(rs.getLong("CMS_LE_MAIN_PROFILE_ID"));
        obj.setLegalReference(rs.getString("LMP_LE_ID"));
        obj.setCustomerName(rs.getString("LSP_SHORT_NAME"));
        obj.setSubProfileID(rs.getLong("CMS_LE_SUB_PROFILE_ID"));
        obj.setSubProfileReference(rs.getString("LSP_ID"));
        if (!customerSeachOnly) {
            // obj.setGroupCCINo(rs.getString("CMS_GRP_CCI_NO"));
            // obj.setGroupCCIMapID(rs.getString("CCI_LE_MAP_ID"));
        }
        obj.setSourceID(rs.getString("SOURCE_ID"));
        obj.setLegalReference(rs.getString("LMP_LE_ID"));
        String IdNO = rs.getString("LMP_INC_NUM_TEXT");
        if (!isEmpty(IdNO)) {
            obj.setIdNo(IdNO);
        } else {
            obj.setIdNo(rs.getString("LMP_ID_OLD_NUM"));
        }

        return obj;
    }

    public IGroupSubLimit[] getGroupSubLimit(long grpID) throws SearchDAOException {

        List list = new ArrayList();
        StringBuffer querySQL = new StringBuffer();
        SQLParameter params = SQLParameter.getInstance();
        Debug(" (getGroupSubLimit) grpID = " + grpID);

        querySQL.append("SELECT * FROM CMS_GROUP_SUBLIMIT ");
        querySQL.append(" where GRP_ID = ?  ");
        params.addLong(new Long(grpID));
        DBUtil dbUtil = null;
        ResultSet rs = null;


        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            while (rs.next()) {
                IGroupSubLimit obj = new OBGroupSubLimit();
                obj.setGroupSubLimitID(rs.getLong("GRP_SUBLIMIT_ID"));
                obj.setGrpID(rs.getLong("GRP_ID"));
                obj.setSubLimitTypeCD(rs.getString("SUB_LIMIT_TYPE"));
                obj.setDescription(rs.getString("DESCRIPTION"));
                obj.setRemarks(rs.getString("REMARKS"));

                String currency = rs.getString("LIMIT_CURRENCY");
                if (currency != null) {
                    obj.setLimitAmt(new Amount(rs.getBigDecimal("LIMIT_AMT"), new CurrencyCode(currency)));
                }

                list.add(obj);
            }
            Debug("getGroupSubLimit = " + list.size());

        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getGroupSubLimit", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in getGroupSubLimit", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return (IGroupSubLimit[]) list.toArray(new IGroupSubLimit[0]);

    }


    protected static final String SEELCT_GROUP_SQL = "SELECT * FROM CMS_CUST_GRP WHERE IS_MASTER_GRP<>'Y' AND GRP_ID= ?";

    public IGroupMember setGroupDetails(IGroupMember obj) throws SearchDAOException {


        StringBuffer querySQL = new StringBuffer();
        StringBuffer addressSQL = new StringBuffer();
        ResultSet rs = null;
        DBUtil dbUtil = null;
        SQLParameter params = SQLParameter.getInstance();
        SQLParameter params1 = SQLParameter.getInstance();

        querySQL.append(SEELCT_GROUP_SQL);
        params.addLong(new Long(obj.getEntityID()));

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            this.processGroupDetails(rs, obj);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in setGroupDetails", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return obj;

    }

    private void processGroupDetails(ResultSet rs, IGroupMember obj) throws SQLException {
        while (rs.next()) {
            obj.setEntityName(rs.getString("GRP_NAME"));
            obj.setEntityID(rs.getLong("GRP_ID"));
            obj.setLmpLeID(rs.getLong("GRP_ID") + "");
            obj.setGrpNo(rs.getLong("GRP_NO"));

        }
    }


    public IGroupMember setCustomerDetails(IGroupMember obj) throws SearchDAOException {

        StringBuffer querySQL = new StringBuffer();
        StringBuffer addressSQL = new StringBuffer();
        ResultSet rs = null;
        DBUtil dbUtil = null;
        SQLParameter params = SQLParameter.getInstance();
        SQLParameter params1 = SQLParameter.getInstance();

        querySQL.append(SELECT_CUSTOMER_PART_SELECT_NEW);
        querySQL.append(SELECT_CUSTOMER_PART_FROMWHERE_NEW);
        querySQL.append(" AND SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID = ?");
        params.addLong(new Long(obj.getEntityID()));

        addressSQL.append(CUSTOMER_ADDRESS);
        params1.addLong(new Long(obj.getEntityID()));

        // Debug(" getCustomerDetails (SQL) :\n " + querySQL.toString());
        Debug(" getSubProfileID() :" + obj.getEntityID());

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            this.processCustomerDetails(rs, obj);

            dbUtil = new DBUtil();
            dbUtil.setSQL(CUSTOMER_ADDRESS);
            CommonUtil.setSQLParams(params1, dbUtil);
            rs = dbUtil.executeQuery();
            //  this.setCustomerAddress(rs,aICCICounterparty )  ;
        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in setCustomerDetails", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in setCustomerDetails", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return obj;

    }

    private void processCustomerDetails(ResultSet rs, IGroupMember obj) throws SQLException {

        if (rs.next()) {
//            obj.setEntityName(rs.getString(LEGAL_NAME));
            obj.setEntityName(rs.getString(CUSTOMER_NAME));
            obj.setEntityID(rs.getLong(CUSTOMER_ID));
            obj.setSourceID(rs.getString("SOURCE_ID"));
            obj.setLmpLeID(rs.getString("LMP_LE_ID"));

            String IdNO = rs.getString("LMP_INC_NUM_TEXT");
            if (!isEmpty(IdNO)) {
                obj.setIdNO(rs.getString("LMP_INC_NUM_TEXT"));
            } else {
                obj.setIdNO(rs.getString("LMP_ID_OLD_NUM"));
            }

            //Andy Wong, 6 July 2008: customize for ABG
            obj.setMembersCreditRating(rs.getString("CREDIT_RATE"));
            String currencyCode = rs.getString("LIMIT_CURRENCY");
            if (StringUtils.isNotEmpty(currencyCode)) {
                Amount entityAmt = new Amount();
                entityAmt.setCurrencyCode(currencyCode);
                entityAmt.setAmountAsBigDecimal(rs.getBigDecimal("LIMIT_AMOUNT"));
                obj.setEntityLmt(entityAmt);
            }

        }
    }


    public Map getGroupAccountMgr(Map inputMap) throws SearchDAOException {
        Map map = new HashMap();

        String sql = "select user.* \n" +
                "from cms_user user,\n" +
                "cms_team_member member,\n" +
                "cms_team_membership memship\n" +
                "where memship.TEAM_TYPE_MEMBERSHIP_ID = 28\n" +
                "and user.USER_ID = member.USER_ID\n" +
                "and member.TEAM_MEMBERSHIP_ID = memship.TEAM_MEMBERSHIP_ID";

        ResultSet rs = null;
        DBUtil dbUtil = null;
        List labels = new ArrayList();
        List values = new ArrayList();
        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(sql);
            rs = dbUtil.executeQuery();
            while (rs.next()) {
                values.add(String.valueOf(rs.getLong("USER_ID")));
                labels.add(rs.getString("USER_NAME"));
            }
            map = new HashMap();
            map.put("labels", labels);
            map.put("values", values);
        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getGroupAccountMgr", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in getGroupAccountMgr", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return map;
    }

    /**
     * For Searching the Group and Member
     *
     * @param criteria
     * @return
     * @throws SearchDAOException
     */
    public SearchResult searchGroup(CustGrpIdentifierSearchCriteria criteria) throws SearchDAOException {

        ResultSet rs = null;
        DBUtil dbUtil = null;
        StringBuffer buf = new StringBuffer();
        SQLParameter params = SQLParameter.getInstance();

        if (criteria == null) {
            return null;
        }

        if ("ByGroup".equals(criteria.getSearchType())) {
            getGroupLeByGroup(criteria, buf, params);
        } else {
            getGroupLeByGroupMember(criteria, buf, params);
        }

        buf.append(" order by cust_grp.GRP_NAME ");
        Debug(" searchGroup  SQL \n" + buf.toString());
        Debug(" searchGroup  getGroupName  \n" + criteria.getGroupName());

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(buf.toString());
            CommonUtil.setSQLParams(params, dbUtil);

            rs = dbUtil.executeQuery();
            long startTime = System.currentTimeMillis();
            rs = dbUtil.executeQuery();
            DefaultLogger.debug(this, ">>>>>>>> searchGroup - query completed in " + (System.currentTimeMillis() - startTime));

            long processRSTime = System.currentTimeMillis();
            SearchResult searchResult = processGroupResultSet(rs, criteria);


            if (searchResult == null) {
                DefaultLogger.debug(this, ">>>>>>>> searchGroup - Process [0] in  : " + (System.currentTimeMillis() - processRSTime));
            } else {
                DefaultLogger.debug(this, ">>>>>>>> searchGroup - Process [" + searchResult.getResultList().size() + "] in  : " + (System.currentTimeMillis() - processRSTime));
            }
            DefaultLogger.debug(this, ">>>>>>>> searchGroup - After search searchGroup");

            return searchResult;

        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new SearchDAOException("Error in getting collateral types: ", e);
        } finally {
            finalize(dbUtil, rs);
        }
    }

    private SearchResult processGroupResultSet(ResultSet rs, CustGrpIdentifierSearchCriteria criteria) throws SearchDAOException, SQLException {
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
            //// weiling : only instantiate the nitems required
            if (startIndex <= count) {
                if (count < endIndex) {
                    if (criteria.getForLimitBooking()) {
                        OBGroupSearchResult result = processGroupResultSet(rs);
                        resultList.add(result);
                    } else {
                        OBCustomerSearchResult result = processGroupResultSet(rs, criteria.getCustomerSeach());
                        resultList.add(result);
                    }
                }
            }
            count++;
        }
        DefaultLogger.debug(this, "*********************RESULT SET SIZE********" + resultList.size());

        return (count == 0) ? null : new SearchResult(criteria.getStartIndex(), resultList.size(), count, resultList);
    }

    private OBCustomerSearchResult processGroupResultSet(ResultSet rs, boolean customerSeachOnly) throws SQLException {
        OBCustomerSearchResult obj = new OBCustomerSearchResult();
        obj.setGrpID(rs.getLong("GRP_ID") + "");
        obj.setGrpNo(rs.getLong("GRP_NO") + "");
        obj.setGroupName(rs.getString("GRP_NAME"));
        return obj;
    }

    private OBGroupSearchResult processGroupResultSet(ResultSet rs) throws SearchDAOException, SQLException {
        OBGroupSearchResult obj = new OBGroupSearchResult();
        obj.setGrpID(rs.getLong("GRP_ID"));
        obj.setGrpNo(rs.getLong("GRP_NO") + "");
        obj.setGroupName(rs.getString("GRP_NAME"));

        String currency = rs.getString("grp_currency");
        if (currency != null) {
            obj.setGroupLmt(new Amount(rs.getBigDecimal("grp_lmt"), new CurrencyCode(currency)));
        }

        IGroupSubLimit[] subLimitList = getGroupSubLimit(obj.getGrpID());
        if (subLimitList != null) {
            for (int i = 0; i < subLimitList.length; i++) {

                if (subLimitList[i].getSubLimitTypeCD().equals(ICMSConstant.SUB_LIMIT_TYPE_BANK_ENTITY_ENTRY_CODE) &&
                        subLimitList[i].getDescription().equals(ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE)) {
                    obj.setConvLmt(subLimitList[i].getLimitAmt());

                } else if (subLimitList[i].getSubLimitTypeCD().equals(ICMSConstant.SUB_LIMIT_TYPE_BANK_ENTITY_ENTRY_CODE) &&
                        subLimitList[i].getDescription().equals(ICMSConstant.BANK_ENTITY_TYPE_ISLAMIC_ENTRY_CODE)) {
                    obj.setIslamLmt(subLimitList[i].getLimitAmt());

                } else if (subLimitList[i].getSubLimitTypeCD().equals(ICMSConstant.SUB_LIMIT_TYPE_BANK_ENTITY_ENTRY_CODE) &&
                        subLimitList[i].getDescription().equals(ICMSConstant.BANK_ENTITY_TYPE_INVESTMENT_ENTRY_CODE)) {
                    obj.setInvLmt(subLimitList[i].getLimitAmt());
                }

            }
        }

        return obj;
    }

    private void getGroupLeByGroup(CustGrpIdentifierSearchCriteria criteria, StringBuffer buf, SQLParameter params) {

        buf.append("SELECT DISTINCT * FROM cms_cust_grp cust_grp WHERE 0=0 and  cust_grp.STATUS <> 'DELETED' ");

        if (criteria.getMasterGroupInd()) {
            buf.append(" AND cust_grp.IS_MASTER_GRP <> 'Y' ");
        }
        if (!isEmptyOrNull(criteria.getGrpNo())) {
            buf.append(" AND cust_grp.GRP_NO = ? ");
            params.addString(criteria.getGrpNo());
        }
        if (!isEmptyOrNull(criteria.getGroupName())) {
            if (criteria.isExactSearch()) {
                buf.append(" AND Upper(cust_grp.GRP_NAME) = ?");
                params.addString(criteria.getGroupName().trim().toUpperCase());
            } else {
                buf.append(" AND Upper(cust_grp.GRP_NAME) LIKE ? ");
                params.addString(criteria.getGroupName().trim().toUpperCase() + "%");
            }
        }

    }

    private void getGroupLeByGroupMember(CustGrpIdentifierSearchCriteria criteria, StringBuffer buf, SQLParameter params) {

        String prefix =
                "SELECT DISTINCT cust_grp.GRP_ID, cust_grp.GRP_NO, cust_grp.GRP_NAME \n" +
                        "  FROM cms_cust_grp  cust_grp , \n" +
                        "       cms_group_member member, \n" +
                        "       sci_le_sub_profile subprofile, \n" +
                        "       sci_le_main_profile mainprofile \n" +
                        " WHERE cust_grp.GRP_ID = member.GRP_ID \n" +
                        "   AND member.ENTITY_TYPE = '" + ICMSConstant.ENTITY_TYPE_CUSTOMER + "' \n" +
                        "   AND member.ENTITY_ID = subprofile.CMS_LE_SUB_PROFILE_ID \n" +
                        "   AND cust_grp.STATUS <> 'DELETED' " +
                        "   AND member.STATUS <> 'DELETED'   " +
                        "   AND subprofile.CMS_LE_MAIN_PROFILE_ID = mainprofile.CMS_LE_MAIN_PROFILE_ID \n";

        buf.append(prefix);

        String customerName = criteria.getCustomerName();
        String legalIDType = criteria.getLeIDType();
        String legalID = criteria.getLegalID();
        String idNO = criteria.getIdNO();


        if (!isEmptyOrNull(customerName)) {
            buf.append(" and Upper(subprofile.LSP_SHORT_NAME) ");
            buf.append("  LIKE '" + customerName.trim().toUpperCase() + "%' ");

        } else if (!isEmptyOrNull(legalID)) {
            buf.append(" and mainprofile.LMP_LE_ID =");
            buf.append("'" + legalID.trim() + "' ");
            buf.append(" and mainprofile.SOURCE_ID=");
            buf.append("  '" + legalIDType.trim() + "' ");

        } else if (!isEmptyOrNull(idNO)) {
            buf.append(" and ");
            buf.append("(");
            buf.append(" Upper(mainprofile.LMP_ID_OLD_NUM) =");
            buf.append("'" + idNO.trim().toUpperCase() + "' ");

            buf.append(" OR ");
            buf.append(" Upper(mainprofile.LMP_INC_NUM_TEXT) =");
            buf.append("'" + idNO.trim().toUpperCase() + "' ");

            buf.append(")");

        } else {
        	DefaultLogger.debug(this,"No criteria found to search the customer!!!");
        }

    }

    public IGroupOtrLimit[] getGroupOtrLimit(long grpID) throws SearchDAOException {

        List list = new ArrayList();
        StringBuffer querySQL = new StringBuffer();
        SQLParameter params = SQLParameter.getInstance();
        Debug(" (getGroupOtrLimit) grpID = " + grpID);

        querySQL.append("SELECT * FROM CMS_GROUP_OTRLIMIT ");
        querySQL.append(" where GRP_ID = ?  ");
        params.addLong(new Long(grpID));
        DBUtil dbUtil = null;
        ResultSet rs = null;


        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            while (rs.next()) {
                IGroupOtrLimit obj = new OBGroupOtrLimit();
                obj.setGroupOtrLimitID(rs.getLong("GRP_OTRLIMIT_ID"));
                obj.setGrpID(rs.getLong("GRP_ID"));
                obj.setOtrLimitTypeCD(rs.getString("OTR_LIMIT_TYPE_CD"));
                obj.setDescription(rs.getString("DESCRIPTION"));
                obj.setRemarks(rs.getString("REMARKS"));
                list.add(obj);
            }
            Debug("getGroupOtrLimit = " + list.size());

        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getGroupOtrLimit", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in getGroupOtrLimit", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return (IGroupOtrLimit[]) list.toArray(new IGroupOtrLimit[0]);

    }

    /**
     * Method to retrive BGEL/group limit for internal limit to be applied selected in SC
     *
     * @param rating
     * @return
     * @throws SearchDAOException
     * @author Andy Wong
     */
    public Amount getGroupLimit(String intLmt, String rating) throws SearchDAOException {
        Amount amount = new Amount();

        StringBuffer sql = new StringBuffer();

        if (CustGroupUIHelper.INT_LMT_CREDIT_RATE.equals(intLmt)) {
            sql.append("select INT_CR_LMT_AMT, INT_CR_LMT_AMT_CUR_CODE ");
            sql.append("from CMS_INTERNAL_CREDIT_RATING ");
            sql.append("where INT_CR_CODE = '" + rating + "'");
        } else if (CustGroupUIHelper.INT_LMT_CAP_FUND_PERCENT.equals(intLmt)) {
            sql.append("select (CAP_FUND_AMT*CAST(GP5_LIM_PERCENTAGE/100 AS DECIMAL(5,2))*(CAST(INT_LIM_PERCENTAGE/100 AS DECIMAL(5,2)))), CAP_FUND_AMT_CUR_CODE ");
            sql.append("from CMS_INTERNAL_LIMIT_PARAMETER ");
            sql.append("where description_code = '" + rating + "'");
        } else if (CustGroupUIHelper.INT_LMT_GP5_REQ.equals(intLmt)) {
            sql.append("select (CAP_FUND_AMT*CAST(GP5_LIM_PERCENTAGE/100 AS DECIMAL(5,2))), CAP_FUND_AMT_CUR_CODE ");
            sql.append("from CMS_INTERNAL_LIMIT_PARAMETER ");
            sql.append("where description_code = '" + rating + "'");
        }

        ResultSet rs = null;
        DBUtil dbUtil = null;

        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(sql.toString());
            rs = dbUtil.executeQuery();
            if (rs.next()) {
                amount.setCurrencyCode(rs.getString(2));
                amount.setAmountAsBigDecimal(rs.getBigDecimal(1));
            }
        } catch (SQLException ex) {
            throw new SearchDAOException("SQLException in getGroupLimit", ex);
        } catch (Exception ex) {
            throw new SearchDAOException("Exception in getGroupLimit", ex);
        } finally {
            finalize(dbUtil, rs);
        }

        return amount;
    }

    public List retrieveBGELGroup(long subprofileID) throws SearchDAOException {

        Debug("subprofileID " + subprofileID);

        ArrayList resultList = new ArrayList();
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();

        ResultSet rs = null;
        DBUtil dbUtil = null;

        //A borrower is deemed to exist in a customer group if the customer is a member of the group, a director, shareholder (with >= 20% holding) and key management of any of the company group member.

        //String query1 = SELECT_GROUP_SQL + FROM_MASTER_GROUP_SUBPROFILE_SQL + WHERE_MASTER_GROUP_SUBPROFILE_SQL;
        //String query2 = SELECT_GROUP_SQL + FROM_SUBGROUP_SUBPROFILE_SQL + WHERE_SUBGROUP_SUBPROFILE_SQL;
        String query3 = SELECT_GROUP_SQL + FROM_MASTER_GROUP_SQL + WHERE_MASTER_GROUP_SQL;
        String query4 = SELECT_GROUP_SQL + FROM_SUBGROUP_MEMBER_SQL + WHERE_SUBGROUP_MEMBER_SQL;

        ArrayList params = new ArrayList();
        ArrayList subprofileIDList = new ArrayList();
        subprofileIDList.add(new Long(subprofileID));
        try {

            CustRelationshipDAO crelnDao = new CustRelationshipDAO();

            List memberList = crelnDao.retrieveShareholderDirector(subprofileID);

            CCICustomerDAO cciDao = new CCICustomerDAO();
            HashMap cciMap = cciDao.getCommonCustomer(subprofileID);
            if (!cciMap.isEmpty()) {
                Collection subprofileIDCol = cciMap.values();

                for (Iterator iterator = subprofileIDCol.iterator(); iterator.hasNext();) {
                    Long cciSubprofileId = (Long) iterator.next();
                    subprofileIDList.add(cciSubprofileId);
                }

            }

            dbUtil = new DBUtil();
            /*
               andCondition.append(" AND CMS_LE_SUB_PROFILE_ID ");

               CommonUtil.buildSQLInList(subprofileIDList, andCondition, params);
               Debug("\nparams 1= " + params);
               Debug("\nsubprofileIDList = " + subprofileIDList);
               strBuf.append( query1 ).append( andCondition );
               strBuf.append(" UNION \n");

               andCondition = new StringBuffer();
               andCondition.append(" AND CMS_LE_SUB_PROFILE_ID ");

               CommonUtil.buildSQLInList(subprofileIDList, andCondition, params);
               Debug("\nparams 2= " + params);
               strBuf.append( query2 ).append( andCondition );
               */
            memberList.addAll(subprofileIDList);

            //if( memberList.size() != 0 ) {

            //strBuf.append(" UNION \n");
            andCondition = new StringBuffer();
            andCondition.append(" AND mem.ENTITY_ID ");

            CommonUtil.buildSQLInList(memberList, andCondition, params);
            Debug("\nparams 3= " + params);

            strBuf.append(query3).append(andCondition);
            strBuf.append(" UNION \n");

            andCondition = new StringBuffer();
            andCondition.append(" AND submem.ENTITY_ID ");

            CommonUtil.buildSQLInList(memberList, andCondition, params);
            Debug("\nparams 4= " + params);

            strBuf.append(query4).append(andCondition);

            //}
            strBuf.append(" ORDER BY grp_id ");

            Debug("\nstrBuf = " + strBuf);
            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();


            while (rs.next()) {

                OBGroupSearchResult result = processGroupResultSet(rs);
                //Debug("\nresult = " + result);
                resultList.add(result);

            }

            return resultList;

        } catch (SQLException ex) {
            DefaultLogger.error(this, "", ex);
            throw new SearchDAOException("SQLException in retrieveBGELGroup", ex);

        } catch (CustRelationshipException ex) {
            DefaultLogger.error(this, "", ex);
            throw new SearchDAOException("CustRelationshipException in retrieveBGELGroup", ex);

        } catch (Exception ex) {
            DefaultLogger.error(this, "", ex);
            throw new SearchDAOException("Exception in retrieveBGELGroup", ex);
        }
        finally {
            finalize(dbUtil, rs);
        }
    }

    public List retrieveMasterGroupBySubGroupID(List subgroupIDList) throws SearchDAOException {

        Debug("subgroupIDList " + subgroupIDList);

        ArrayList resultList = new ArrayList();
        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();

        ResultSet rs = null;
        DBUtil dbUtil = null;

        String query = SELECT_GROUP_SQL + FROM_MASTER_GROUP_SQL + WHERE_MEMBER_IS_SUBGROUP_SQL;

        ArrayList params = new ArrayList();
        try {

            dbUtil = new DBUtil();

            andCondition.append(" AND mem.ENTITY_ID ");

            CommonUtil.buildSQLInList(subgroupIDList, andCondition, params);
            Debug("\nparams = " + params);

            strBuf.append(query).append(andCondition);

            strBuf.append(" ORDER BY grp_id ");

            Debug("\nstrBuf = " + strBuf);
            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();

            while (rs.next()) {

                OBGroupSearchResult result = processGroupResultSet(rs);
                //Debug("\nresult = " + result);
                resultList.add(result);

            }

            return resultList;

        } catch (SQLException ex) {
            DefaultLogger.error(this, "", ex);
            throw new SearchDAOException("SQLException in retrieveMasterGroupBySubGroupID", ex);

        } catch (Exception ex) {
            DefaultLogger.error(this, "", ex);
            throw new SearchDAOException("Exception in retrieveMasterGroupBySubGroupID", ex);
        }
        finally {
            finalize(dbUtil, rs);
        }
    }

    public HashMap retrieveSubGroupByGroupID(List groupIDList) throws SearchDAOException {

        Debug("subgroupIDList " + groupIDList);
        HashMap resultMap = new HashMap();

        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();

        ResultSet rs = null;
        DBUtil dbUtil = null;

        String query = SELECT_SUBGROUP_SQL + FROM_SUBGROUP_SQL + WHERE_SUBGROUP_SQL;

        ArrayList params = new ArrayList();
        try {

            dbUtil = new DBUtil();

            andCondition.append(" AND grp.grp_id ");

            CommonUtil.buildSQLInList(groupIDList, andCondition, params);
            Debug("\n params = " + params);

            strBuf.append(query).append(andCondition);

            strBuf.append(" ORDER BY grp_id ");

            Debug("\n retrieveSubGroupByGroupID strBuf = " + strBuf);
            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();

            String prevKey = null;

            List resultList = new ArrayList();
            while (rs.next()) {

                String grpID = rs.getString("GRP_ID");
                String subgrpID = rs.getString("SUBGRP_ID");
                String key = grpID;

                if (prevKey != null && !prevKey.equals(key)) {
                    resultMap.put(prevKey, resultList);

                    resultList = new ArrayList();
                }
                resultList.add(subgrpID);

                prevKey = key;

            }
            if (prevKey != null) {

                resultMap.put(prevKey, resultList);

            }
            return resultMap;


        } catch (SQLException ex) {
            DefaultLogger.error(this, "", ex);
            throw new SearchDAOException("SQLException in retrieveMasterGroupBySubGroupID", ex);

        } catch (Exception ex) {
            DefaultLogger.error(this, "", ex);
            throw new SearchDAOException("Exception in retrieveMasterGroupBySubGroupID", ex);
        }
        finally {
            finalize(dbUtil, rs);
        }
    }

    public HashMap retrieveMemberByGroupID(List groupIDList) throws SearchDAOException {

        Debug("groupIDList " + groupIDList);

        HashMap resultMap = new HashMap();

        StringBuffer strBuf = new StringBuffer();
        StringBuffer andCondition = new StringBuffer();

        ResultSet rs = null;
        DBUtil dbUtil = null;

        String query1 = SELECT_GROUP_MEMBER_SQL + FROM_MASTER_GROUP_SQL + WHERE_MASTER_GROUP_SQL;
        String query2 = SELECT_SUBGROUP_MEMBER_SQL + FROM_SUBGROUP_MEMBER_SQL + WHERE_SUBGROUP_MEMBER_SQL;

        ArrayList params = new ArrayList();
        try {

            dbUtil = new DBUtil();

            andCondition.append(" AND grp.GRP_ID ");

            CommonUtil.buildSQLInList(groupIDList, andCondition, params);
            Debug("\nparams 1= " + params);
            strBuf.append(query1).append(andCondition);
            strBuf.append(" UNION \n");

            andCondition = new StringBuffer();
            andCondition.append(" AND grp.GRP_ID ");

            CommonUtil.buildSQLInList(groupIDList, andCondition, params);
            Debug("\nparams 2= " + params);
            strBuf.append(query2).append(andCondition);

            strBuf.append(" ORDER BY grp_id ");

            Debug("\nstrBuf = " + strBuf);
            dbUtil.setSQL(strBuf.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            Long prevKey = null;

            ArrayList resultList = new ArrayList();
            while (rs.next()) {

                long grpID = rs.getLong("GRP_ID");
                long memberID = rs.getLong("ENTITY_ID");
                Long key = new Long(grpID);

                if (prevKey != null && !prevKey.equals(key)) {
                    resultMap.put(prevKey, resultList);

                    resultList = new ArrayList();
                }
                resultList.add(new Long(memberID));

                prevKey = key;

            }
            if (prevKey != null) {

                resultMap.put(prevKey, resultList);

            }
            return resultMap;

        } catch (SQLException ex) {
            DefaultLogger.error(this, "", ex);
            throw new SearchDAOException("SQLException in retrieveMasterGroupBySubGroupID", ex);

        } catch (Exception ex) {
            DefaultLogger.error(this, "", ex);
            throw new SearchDAOException("Exception in retrieveMasterGroupBySubGroupID", ex);
        }
        finally {
            finalize(dbUtil, rs);
        }
    }

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"CustGrpIdentifierDAO = " + msg);
    }

}
