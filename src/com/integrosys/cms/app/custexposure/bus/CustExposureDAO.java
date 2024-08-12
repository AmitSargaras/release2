package com.integrosys.cms.app.custexposure.bus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.custgrpi.bus.IGroupMember;
import com.integrosys.cms.app.custgrpi.bus.OBGroupMember;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

public class CustExposureDAO extends CustExposureConstant implements ICustExposureDAO {

    protected static String SELECT_CCI_CUSTOMER =
                    "SELECT  distinct \n" +
                            "       CMS_CCI_MAP.CCI_MAP_ID, \n" +
                            "       CMS_CCI_MAP.CMS_CCI_NO,\n" +
                            "       SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID,\n" +
                            "       SCI_LE_SUB_PROFILE.LSP_SHORT_NAME, \n" +
                            "       SCI_LE_MAIN_PROFILE.LMP_LONG_NAME, \n" +
                            "       SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID, \n" +
                            "       SCI_LE_MAIN_PROFILE.LMP_LE_ID, \n" +
                            "       SCI_LE_MAIN_PROFILE.SOURCE_ID, \n" +
                            "       SCI_LE_MAIN_PROFILE.LMP_ID_NUM, \n" +
                            "       SCI_LE_MAIN_PROFILE.LMP_INC_NUM_TEXT, \n" +
                            "       SCI_LE_MAIN_PROFILE.LMP_TYPE_VALUE, \n" +
                            "       SCI_LE_MAIN_PROFILE.LMP_INC_DATE,       \n" +
                            "       SCI_LE_SUB_PROFILE.CMS_NON_BORROWER_IND, \n" +
                            "       SCI_LE_SUB_PROFILE.LSP_ID, \n" +
                            "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_COUNTRY, \n" +
                            "       SCI_LE_SUB_PROFILE.CMS_SUB_ORIG_ORGANISATION\n";



    protected static String FROM_WHERE_CCI_CUSTOMER =
                           "FROM SCI_LE_MAIN_PROFILE, \n" +
                            "    SCI_LE_SUB_PROFILE LEFT OUTER JOIN CMS_CCI_MAP\n" +
                            "      ON  (CMS_CCI_MAP.IS_DELETED <> 'Y' \n" +
                            "            AND CMS_CCI_MAP.CMS_LE_SUB_PROFILE_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID)\n" +
                            " WHERE 0=0       \n" +
                            "      AND SCI_LE_SUB_PROFILE.CMS_LE_MAIN_PROFILE_ID = SCI_LE_MAIN_PROFILE.CMS_LE_MAIN_PROFILE_ID \n";

    private static final String CUSTOMER_LIMIT_SQL =
                    "              SELECT main.LMP_LONG_NAME, \n" +
                    "                           main.CMS_LE_MAIN_PROFILE_ID, \n" +
                    "                           main.LMP_LE_ID, \n" +
                    "                           main.LMP_TYPE_VALUE,\n" +
                    "                           main.SOURCE_ID, \n" +
                    "                           main.LMP_INC_NUM_TEXT, \n" +
                    "                           sub.LSP_SHORT_NAME, \n" +
                    "                           sub.CMS_LE_SUB_PROFILE_ID, \n" +
                    "                           sub.CMS_NON_BORROWER_IND, \n" +
                    "                           sub.LSP_ID, \n" +
                    "                           sub.CMS_SUB_ORIG_COUNTRY, \n" +
                    "                           sub.CMS_SUB_ORIG_ORGANISATION, \n" +
                    "                           lmtProfile.CMS_LSP_LMT_PROFILE_ID, \n" +
                    "                           lmtProfile.LLP_BCA_REF_NUM, \n" +
                    "                           lmtProfile.LLP_BCA_REF_APPR_DATE, \n" +
                    "                           lmtProfile.LSP_LMT_PROFILE_SRC_ID_OLD, \n" +
                    "                           lmtProfile.CMS_BCA_STATUS, \n" +
                    "                           lmtProfile.CMS_ORIG_COUNTRY, \n" +
                    "                           lmtProfile.CMS_ORIG_ORGANISATION, \n" +
                    "                           lmtProfile.LMT_PROFILE_TYPE, \n" +
                    "                           lmtProfile.CMS_CREATE_IND \n" +
                    "                    FROM SCI_LE_MAIN_PROFILE main, \n" +
                    "                         SCI_LE_SUB_PROFILE sub  \n" +
                    "     					LEFT OUTER JOIN  \n" +
                	"						SCI_LSP_JOINT_BORROWER ON sub.CMS_LE_SUB_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LE_SUB_PROFILE_ID \n" +
                	"						AND SCI_LSP_JOINT_BORROWER.UPDATE_STATUS_IND <>'D' \n" +
                    "                        LEFT OUTER JOIN SCI_LSP_LMT_PROFILE lmtProfile\n" +
                    "                          ON (lmtProfile.CMS_LSP_LMT_PROFILE_ID = SCI_LSP_JOINT_BORROWER.CMS_LMP_LIMIT_PROFILE_ID \n" +
                    "							OR lmtProfile.CMS_CUSTOMER_ID = sub.CMS_LE_SUB_PROFILE_ID) \n" +
                    "                            AND lmtProfile.LMT_PROFILE_TYPE = 'BANKING' \n" +
                    "                            AND lmtProfile.CMS_BCA_STATUS <> 'DELETED' \n" +
                    "                    WHERE sub.CMS_LE_MAIN_PROFILE_ID = main.CMS_LE_MAIN_PROFILE_ID \n" +
                    "                       AND sub.CMS_LE_SUB_PROFILE_ID = ? ";


        private static final String SELECT_CHILD_CUSTOMER_RELATIONSHIP_TABLE_SQL=
                     "SELECT DISTINCT\n" +
                             "    subprofile.CMS_LE_SUB_PROFILE_ID,\n" +
                             "    subprofile.LSP_SHORT_NAME,   \n" +
                             "    rel.REL_VALUE,\n" +
                             "    (SELECT code.ENTRY_NAME \n" +
                             "                  FROM common_code_category_entry code\n" +
                             "                  WHERE code.ENTRY_CODE = rel.REL_VALUE\n" +
                             "                 AND code.CATEGORY_CODE = 'ENT_REL' \n" +
                             "    ) AS RELATION_NAME, \n" +
                             "	  'HAS' AS RELATION_TYPE \n" +
                             "FROM sci_le_rel rel, \n" +
                             "     sci_le_sub_profile subprofile \n" +
                             "WHERE rel.CMS_LE_SUB_PROFILE_ID = subprofile.CMS_LE_SUB_PROFILE_ID \n" +
                             "     AND rel.STATUS <> '" + ICMSConstant.DELETED + "' \n"+
                             "     AND rel.PARENT_SUB_PROFILE_ID = ? \n";


      private static final String SELECT_PARENT_CUSTOMER_RELATIONSHIP_TABLE_SQL=
                     "SELECT DISTINCT\n" +
                             "    subprofile.CMS_LE_SUB_PROFILE_ID,\n" +
                             "    subprofile.LSP_SHORT_NAME,   \n" +
                             "    rel.REL_VALUE,\n" +
                             "    (SELECT code.ENTRY_NAME \n" +
                             "                  FROM common_code_category_entry code\n" +
                             "                  WHERE code.ENTRY_CODE = rel.REL_VALUE\n" +
                             "                 AND code.CATEGORY_CODE = 'ENT_REL' \n" +
                             "    ) AS RELATION_NAME, \n" +
                             "	  'IS' AS RELATION_TYPE \n" +
                             "FROM sci_le_rel rel, \n" +
                             "     sci_le_sub_profile subprofile \n" +
                             "WHERE rel.PARENT_SUB_PROFILE_ID = subprofile.CMS_LE_SUB_PROFILE_ID \n" +
                             "     AND rel.STATUS <> '" + ICMSConstant.DELETED + "' \n"+
                             "     AND rel.CMS_LE_SUB_PROFILE_ID = ? \n";

    private static final String SELECT_GROUPRELATIONSHIP_SQL=
                        " SELECT DISTINCT cust_grp.GRP_ID, cust_grp.GRP_NO, " +
                        "  cust_grp.GRP_NAME, \n" +
                        "  (SELECT code.ENTRY_NAME \n" +
                        "                  FROM common_code_category_entry code\n" +
                        "                  WHERE code.ENTRY_CODE = member.RELATION_NAME\n" +
                        "                 AND code.CATEGORY_CODE = '" + ICMSUIConstant.GENT_REL + "' \n" +
                        "                ) as RELATION_NAME  \n" +
                        "  FROM cms_cust_grp  cust_grp , \n" +
                        "       cms_group_member member, \n" +
                        "       sci_le_sub_profile subprofile, \n" +
                        "       sci_le_main_profile mainprofile \n" +
                        " WHERE cust_grp.GRP_ID = member.GRP_ID \n" +
                        "   AND member.ENTITY_TYPE = '" + ICMSConstant.ENTITY_TYPE_CUSTOMER + "' \n" +
                        "   AND member.ENTITY_ID = subprofile.CMS_LE_SUB_PROFILE_ID \n" +
                        "   AND cust_grp.STATUS <> '" + ICMSConstant.DELETED + "' \n" +
                        "   AND member.STATUS <> '" + ICMSConstant.DELETED + "' \n" +
                        "   AND subprofile.CMS_LE_MAIN_PROFILE_ID = mainprofile.CMS_LE_MAIN_PROFILE_ID \n";




    private static final String SEARCH_CCI_NO_BY_SUB_PROFILE_ID = 
    	"Select cms_cci_no from cms_cci_map where cms_le_sub_profile_id = ? and IS_DELETED = 'N'";

    private static final String SELECT_GET_SECURITYID_SQL =
        "select cms_sec.SCI_SECURITY_DTL_ID\n" +
        " from  cms_security cms_sec,\n" +
        "       cms_guarantee cms_gua,\n" +
        "       sci_pledgor_dtl pledgor_dtl,\n" +
        "       sci_sec_pldgr_map pledgor_map \n" +
        "where cms_sec.SCI_SECURITY_TYPE_VALUE =  'GT' \n" +
        "      AND cms_sec.CMS_COLLATERAL_ID = cms_gua.CMS_COLLATERAL_ID\n" +
        "      AND cms_sec.CMS_COLLATERAL_ID = pledgor_map.CMS_COLLATERAL_ID\n" +
        "      AND pledgor_dtl.CMS_PLEDGOR_DTL_ID = pledgor_map.CMS_PLEDGOR_DTL_ID\n" + 
        "	   AND cms_sec.STATUS <> 'DELETED'\n" +
		" 	   AND pledgor_dtl.UPDATE_STATUS_IND <> 'D'\n" +
		" 	   AND pledgor_map.UPDATE_STATUS_IND <> 'D'\n";
    
    
    private static final String SELECT_CONTINGENT_LIABILITIES_AMOUNT_SQL =
        "SELECT sci_le_sub_profile.LSP_SHORT_NAME,\n" +
        "      cms_gua.GUARANTEE_AMT ,\n" +
        "      cms_gua.CURRENCY_CODE, \n" +
        "      cms_security.SUBTYPE_NAME, \n" +
        "      cms_security.SCI_SECURITY_DTL_ID, \n" +
        "      cms_security.SECURITY_ORGANISATION " +
        //"      sci_lsp_appr_lmts.CMS_BKG_ORGANISATION " + 
        "FROM CMS_GUARANTEE cms_gua,\n" +
        "      cms_security_sub_type, \n" +
        "      CMS_SECURITY_SOURCE source, \n" +
        "      cms_security \n" +
        "      LEFT OUTER JOIN cms_limit_security_map \n" +
        "      ON cms_security.CMS_COLLATERAL_ID = cms_limit_security_map.CMS_COLLATERAL_ID \n" +
        "      LEFT OUTER JOIN  sci_lsp_appr_lmts \n" +
        "       ON cms_limit_security_map.CMS_LSP_APPR_LMTS_ID = sci_lsp_appr_lmts.CMS_LSP_APPR_LMTS_ID \n" +
        "      LEFT OUTER JOIN sci_lsp_lmt_profile \n" +
        "            ON sci_lsp_appr_lmts.CMS_LIMIT_PROFILE_ID = sci_lsp_lmt_profile.CMS_LSP_LMT_PROFILE_ID \n" +
        "      LEFT OUTER JOIN sci_le_sub_profile    ON sci_lsp_lmt_profile.CMS_CUSTOMER_ID = sci_le_sub_profile.CMS_LE_SUB_PROFILE_ID \n" +
        "      LEFT OUTER JOIN sci_le_main_profile \n" +
        "            ON sci_le_sub_profile.CMS_LE_MAIN_PROFILE_ID = sci_le_main_profile.CMS_LE_MAIN_PROFILE_ID \n" +
        "WHERE  cms_security.SCI_SECURITY_TYPE_VALUE =  'GT'\n" +
        "      AND cms_security.CMS_COLLATERAL_ID = cms_gua.CMS_COLLATERAL_ID\n" +
        "      AND source.CMS_COLLATERAL_ID = cms_security.CMS_COLLATERAL_ID \n" +
        "      AND cms_security_sub_type.SECURITY_SUB_TYPE_ID = cms_security.SECURITY_SUB_TYPE_ID\n" ;

    private static final String SELECT_BANK_ENTITY_BY_ORG_CODE = 
    	" Select c2.ENTRY_NAME as BRANCH_NAME, c1.ENTRY_CODE AS BANK_ENTITY_CODE from cms_bank_entity_param b, \n" +
    	" common_code_category_entry c1, common_code_category_entry c2 \n" +
    	" where b.BRANCH_CODE = ? \n" + 
    	" and b.ENTITY_TYPE = c1.ENTRY_CODE \n" +
    	" and b.BRANCH_CODE = c2.ENTRY_CODE \n" +
    	" and c1.CATEGORY_CODE = '" + ICMSConstant.SUB_LIMIT_DESC_CATEGORY_CODE + "' \n" +	
    	" and c2.CATEGORY_CODE = '" + ICMSConstant.CATEGORY_CODE_BKGLOC + "'" + 
    	" and b.STATUS <> '" + ICMSConstant.DELETED + "'";

    private static final String SELECT_ACCOUNT_TYPE_BY_LIMIT = 
    	" Select xref.LSX_EXT_SYS_ACCT_TYPE as ACCOUNT_TYPE from sci_lsp_sys_xref xref, SCI_LSP_LMTS_XREF_MAP map " + 
    	" where xref.CMS_LSP_SYS_XREF_ID = map.CMS_LSP_SYS_XREF_ID " +
    	" and map.CMS_LSP_APPR_LMTS_ID = ? " +
    	" and xref.CMS_LE_SUB_PROFILE_ID = ? ";

    /**
     *   get getDependentExposureRecords for the customer with Sub Profile id
     *   1) getContingentLiabilities
     *   2) getCustomerRelationship
     *   3) getGroupRelationship
     * @param criteria
     * @return
     * @throws SearchDAOException
     */
    public Map getCustExposureRecords(CustExposureSearchCriteria criteria) throws SearchDAOException {
        Debug("getDependentExposureRecords Inside");
        Map map = new HashMap();
        ResultSet rs = null;
        DBUtil dbUtil = null;
        try {
            dbUtil = new DBUtil();
            Map map1 = this.getContingentLiabilitiesResults(criteria);
            Map map2 = this.getCustomerRelationshipResults(criteria);
            Map map3 = this.getGroupRelationshipResults(true, criteria);

            map.put("customer.contingentLiabilities", map1.get("customer.contingentLiabilities"));
            map.put("customer.customerRelationship",  map2.get("customer.customerRelationship"));
            map.put("customer.groupRelationship",     map3.get("customer.groupRelationship"));

        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
        } finally {
            finalize(dbUtil, rs);
        }
        return map;
    }
    
    private List getSecurityID(CustExposureSearchCriteria criteria) {
    	
    	DBUtil dbUtil = null;
    	ResultSet rs = null;
    	
		List list = new ArrayList();
		StringBuffer querySQL = new StringBuffer();
		SQLParameter params = SQLParameter.getInstance();
		querySQL.append(SELECT_GET_SECURITYID_SQL);

		Debug(">>>>>>>>> getSecurityID  criteria.getSourceId() :" + criteria.getSourceId());
		Debug(" >>>>>>>>> getSecurityID  criteria.getLEReference() :" + criteria.getLEReference());

		if (!isEmptyOrNull(criteria.getSourceId())) {
			querySQL.append("      AND pledgor_dtl.SOURCE_ID =  ? \n");
			params.addString(criteria.getSourceId());
		}
		
		if (!isEmptyOrNull(criteria.getLEReference())) {
			querySQL.append("      AND pledgor_dtl.PLG_LE_ID =  ? \n");
			params.addString(criteria.getLEReference());
		}

		Debug("getSecurityID : " + querySQL);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(querySQL.toString());
			CommonUtil.setSQLParams(params, dbUtil);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				list.add(rs.getString("SCI_SECURITY_DTL_ID"));
			}
			
		} catch (Exception e) {
			DefaultLogger.error(this, "", e);
		}

		return list;

	}

    /**
     *  for   getContingentLiabilitiesResults
     * @param criteria
     * @param dbUtil
     * @param rs
     * @return   map
     */
    public Map getContingentLiabilitiesResults(CustExposureSearchCriteria criteria) {

        Map map = new HashMap();
		DBUtil dbUtil = null;
		ResultSet rs = null;

		StringBuffer querySQL = new StringBuffer();
		SQLParameter params = SQLParameter.getInstance();
		querySQL.append(SELECT_CONTINGENT_LIABILITIES_AMOUNT_SQL);

		List cols = this.getSecurityID(criteria);

		if (cols != null && !cols.isEmpty()) {
		} else {
			return map;
		}

		Debug(" >>>>>>>>> getSecurityID : " + cols);

		querySQL.append("      AND cms_security.SCI_SECURITY_DTL_ID IN ( ");
		String id = (String) cols.get(0);
		querySQL.append(" ? ");
		params.addString(id);

		for (int index = 1; index < cols.size(); index++) {
			querySQL.append(",");
			id = (String) cols.get(index);
			querySQL.append(" ? ");
			params.addString(id);
		}
		querySQL.append(" ) ");
		
		querySQL.append(" AND SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID <> " + criteria.getSubProfileID());

		Debug("\ngetContingentLiabilitiesResults SQL :\n " + querySQL.toString());

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(querySQL.toString());
			CommonUtil.setSQLParams(params, dbUtil);
			rs = dbUtil.executeQuery();
			map = this.processContingentLiabilities(rs);
		} catch (Exception e) {
			DefaultLogger.error(this, "", e);
		} finally {
			finalize(dbUtil, rs);
		}

		return map;
    }
    

    private Map processContingentLiabilities(ResultSet rs) throws SQLException {
        
    	Map map = new HashMap();
		List list = new ArrayList();
		Amount amount = null;
		BigDecimal amtChg;
		try {
			while (rs.next()) {
				IContingentLiabilities obj = new OBContingentLiabilities();
				obj.setBorrowerName(rs.getString("LSP_SHORT_NAME"));
				obj.setGuaranteeType(rs.getString("SUBTYPE_NAME"));
				obj.setCurrencyCode(rs.getString("CURRENCY_CODE"));
				amtChg = rs.getBigDecimal("GUARANTEE_AMT");
				if (amtChg != null) {
					amount = new Amount(amtChg.doubleValue(), obj
							.getCurrencyCode());
					obj.setGuaranteeAmt(amount);
				}
				//String orgCode = rs.getString("CMS_BKG_ORGANISATION");
				String orgCode = rs.getString("SECURITY_ORGANISATION");

				if (orgCode != null)
					obj.setBankEntity(getBankEntityByOrgCode(orgCode));
				else
					obj
							.setBankEntity(ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE);
				
				obj.setSourceSecurityId(rs.getString("SCI_SECURITY_DTL_ID"));

				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		Debug("\ncustomer.contingentLiabilities  " + list.size());
		map.put("customer.contingentLiabilities", list);
		return map;

    }



    /**
     * for  getCustomerRelationship

     * @param criteria
     * @return map
     */
    public Map getCustomerRelationshipResults(CustExposureSearchCriteria criteria) {
        
    	Map map = new HashMap();
		DBUtil dbUtil = null;
		ResultSet rs = null;

		StringBuffer querySQL = new StringBuffer();
		SQLParameter params = SQLParameter.getInstance();

		querySQL = new StringBuffer();
		querySQL.append(SELECT_CHILD_CUSTOMER_RELATIONSHIP_TABLE_SQL);
		querySQL.append("\n Union All \n");
		querySQL.append(SELECT_PARENT_CUSTOMER_RELATIONSHIP_TABLE_SQL);

		params.addLong(Long.valueOf(criteria.getSubProfileID()));
		params.addLong(Long.valueOf(criteria.getSubProfileID()));

		Debug("\ngetCustomerRelationshipResults SQL :\n " + querySQL.toString());

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(querySQL.toString());
			CommonUtil.setSQLParams(params, dbUtil);
			rs = dbUtil.executeQuery();
			map = this.processCustomerExposureRelationship(rs);
		} catch (Exception e) {
			DefaultLogger.error(this, "", e);
		} finally {
			finalize(dbUtil, rs);
		}
		return map;
    }
    
    private Map processCustomerExposureRelationship(ResultSet rs) throws SQLException {
        
    	Map map = new HashMap();
		List list = new ArrayList();
		
		while (rs.next()) {
			OBCustExposureEntityRelationship obj = new OBCustExposureEntityRelationship();
			obj.setSubProfileID(rs.getLong(CUSTOMER_ID));
			obj.setRelatedEntiySubProfileId(rs.getLong(CUSTOMER_ID));
			obj.setCustomerName(rs.getString(CUSTOMER_NAME));
			obj.setRelationshipValue(rs.getString("REL_VALUE"));
			obj.setRelationName(rs.getString("RELATION_NAME"));
			obj.setRelationType(rs.getString("RELATION_TYPE"));
			list.add(obj);
		}
		
		Debug("customer.customerRelationship  : " + list.size());
		map.put("customer.customerRelationship", list);
		return map;

    }


    /**
     * for getGroupRelationship
     *
     * @param criteria
     * @return map
     */
    public Map getGroupRelationshipResults(boolean isCustomerExposure, CustExposureSearchCriteria criteria) {
        
    	Map map = new HashMap();
		DBUtil dbUtil = null;
		ResultSet rs = null;

		StringBuffer querySQL = new StringBuffer();
		SQLParameter params = SQLParameter.getInstance();
		querySQL.append(SELECT_GROUPRELATIONSHIP_SQL);

		if (!isEmptyOrNull(criteria.getSubProfileID())) {
			querySQL.append("   AND subprofile.CMS_LE_SUB_PROFILE_ID = ?");
			params.addLong(Long.valueOf(criteria.getSubProfileID()));
		} else {
			Debug("No criteria found for  getGroupRelationship!!!");
		}

		Debug("\ngetGroupRelationshipResults SQL :\n " + querySQL.toString());

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(querySQL.toString());
			CommonUtil.setSQLParams(params, dbUtil);
			rs = dbUtil.executeQuery();
			map = this.processGroupRelationship(rs);

		} catch (Exception e) {
			DefaultLogger.error(this, "", e);
		} finally {
			finalize(dbUtil, rs);
		}
		return map;
    }
    
    /**
     * Process result set by producing the a map of 'customer.groupRelationship|<group relationship list>
     * @param rs
     * @return
     * @throws SQLException
     */
    private Map processGroupRelationship(ResultSet rs) throws SQLException {
        
    	Map map = new HashMap();
		List list = new ArrayList();
		while (rs.next()) {
			ICustExposureGroupRelationship obj = new OBCustExposureGroupRelationship();
			obj.setGroupName(rs.getString("GRP_NAME"));
			IGroupMember member = new OBGroupMember();
			member.setRelationName(rs.getString("RELATION_NAME"));
			member.setGrpID(rs.getLong("GRP_ID"));
			member.setGrpNo(rs.getLong("GRP_NO"));
			obj.setGroupMember(member);
			list.add(obj);
		}

		map = new HashMap();
		map.put("customer.groupRelationship", list);
		return map;

      }

    /**
     * get limitProfileID by SubProfileId
     * @param subProfileID
     * @return
     * @throws SearchDAOException
     */
    public List getlimitProfileIDBySubProfileID(long subProfileID) throws SearchDAOException {
        
    	DBUtil dbUtil = null;
		ResultSet rs = null;
		List result = new ArrayList();
		try {
			dbUtil = new DBUtil();
			String sql = CUSTOMER_LIMIT_SQL;
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, subProfileID);

			DefaultLogger.debug(this,
					">>>>>>>> getlimitProfileIDBySubProfileID SQL : " + sql);
			rs = dbUtil.executeQuery();
			while (rs.next()) {
				long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;
				limitProfileID = rs.getLong("CMS_LSP_LMT_PROFILE_ID");
				result.add( new Long( limitProfileID ) );
			}	
			return result;
			
		} catch (Exception e) {
			DefaultLogger.error(this,
					"Caught Exception in getlimitProfileIDBySubProfileID!", e);
					
            throw new SearchDAOException("Error in getlimitProfileIDBySubProfileID ", e);
		} finally {
			finalize(dbUtil, rs);
		}
    }

    
    /**
     *  This will return the cci no that the current customer 
     *  tie to. There should be only one cci created for one customer. 
     *  
     * @param subProfileId
     * @return cciNo
     */
    public long getCCINoBySubProfileId(long subProfileId) {

        ResultSet rs = null;
        DBUtil dbUtil = null;

         Debug("getCCINoBySubProfileId Param subprofileId ; " + subProfileId);

        StringBuffer querySQL = new StringBuffer();
        SQLParameter params = SQLParameter.getInstance();
        querySQL.append(SEARCH_CCI_NO_BY_SUB_PROFILE_ID);
        params.addLong(new Long(subProfileId));
        long cciNo = -1;
        
        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            
            while (rs.next()) {
            	cciNo = rs.getLong(CMS_CCI_NO);
            }
     
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
        } finally {
            finalize(dbUtil, rs);
        }
        
        return cciNo;

    }
    
    public String getBankEntityByOrgCode(String orgCode) {

        ResultSet rs = null;
        DBUtil dbUtil = null;

         Debug("getBankEntityByOrgCode Param orgCode : " + orgCode);

        StringBuffer querySQL = new StringBuffer();
        SQLParameter params = SQLParameter.getInstance();
        querySQL.append(SELECT_BANK_ENTITY_BY_ORG_CODE);
        params.addString(orgCode);
        String bankEntity = ICMSConstant.BANK_ENTITY_TYPE_CONVENTION_ENTRY_CODE;
        
        try {
            dbUtil = new DBUtil();
            dbUtil.setSQL(querySQL.toString());
            CommonUtil.setSQLParams(params, dbUtil);
            rs = dbUtil.executeQuery();
            
            while (rs.next()) {
            	bankEntity = rs.getString(BANK_ENTITY_CODE);
            }
     
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
        } finally {
            finalize(dbUtil, rs);
        }
        
        return bankEntity;
    }
    
    
    /**
     * Get customer account type
     * @param limitId
     * @param customerId
     * @return
     */
    public String getCustomerAccountType(long limitId, long customerId) {

        ResultSet rs = null;
		DBUtil dbUtil = null;

		Debug("getCustomerAccountType Param (limitId, customerId): " + limitId
				+ " " + customerId);

		StringBuffer querySQL = new StringBuffer();
		SQLParameter params = SQLParameter.getInstance();
		querySQL.append(SELECT_ACCOUNT_TYPE_BY_LIMIT);
		params.addLong(new Long(limitId));
		params.addLong(new Long(customerId));
		String accountType = "";

		Debug("query : " + SELECT_ACCOUNT_TYPE_BY_LIMIT);

		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(querySQL.toString());
			CommonUtil.setSQLParams(params, dbUtil);
			rs = dbUtil.executeQuery();

			while (rs.next()) {
				accountType = rs.getString(ACCOUNT_TYPE);
			}

		} catch (Exception e) {
			DefaultLogger.error(this, "", e);
		} finally {
			finalize(dbUtil, rs);
		}

		return accountType;
    }
    
    
    /**
	 * Retrieve the latest OMV
	 * @param collateralId
	 * @param sourceType
	 * @return
	 * @throws Exception
	 */
	public Amount retrieveLatestValuationByCollateralId(String collateralId) throws Exception {
		String query = "SELECT valuation_id, valuation_date, valuation_currency, cmv, fsv, SOURCE_TYPE, source_id \n" +
					   "  FROM cms_valuation \n" +
					   " WHERE cms_collateral_id = ? \n" +
					   "   AND valuation_date IS NOT NULL \n" +
					   " ORDER BY valuation_date ";

		DBUtil dbUtil = null;
		ResultSet rs = null;
		
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(query);
			
			dbUtil.setString(1, collateralId);
			
			rs = dbUtil.executeQuery();
			
			HashMap valMap = new HashMap();
			while (rs.next()) {
				IValuation val = new OBValuation();
				val.setCMV(new Amount(rs.getDouble("cmv"), rs.getString("valuation_currency")));
				val.setSourceType(rs.getString("SOURCE_TYPE"));
				
				valMap.put( val.getSourceType(), val );
			}
			// System valuation has the priority first, if not found, then take the valuation from source
			IValuation foundVal = (IValuation) valMap.get( ICMSConstant.VALUATION_SOURCE_TYPE_A );
			if( foundVal == null )
			{
				foundVal = (IValuation) valMap.get( ICMSConstant.VALUATION_SOURCE_TYPE_S );
			}
			
			if (foundVal != null)
				return foundVal.getCMV();
			else
				return null;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		finally {
			finalize(dbUtil, rs);
		}
	}
    
    private void Debug(String msg) {
    	DefaultLogger.debug(this,"CustExposureDAO = " + msg);
    }
    

}
