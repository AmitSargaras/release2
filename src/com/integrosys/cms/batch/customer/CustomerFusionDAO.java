/*
 * CustomerFusionDAO.java
 *
 * Created on May 22, 2007, 11:07 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.integrosys.cms.batch.customer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author OEM
 */

/**
 * Modified by IntelliJ IDEA.
 *
 * @author Andy Wong
 * @since June 2, 2009
 *        convert to Spring bean and using jdbcTemplete
 */
public final class CustomerFusionDAO extends JdbcDaoSupport implements ICustomerDAOConstants {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String findFusionIdSql = "SELECT \n" + "CMS_BATCH_CIF_FUSION_ID , OLD_LE_ID , NEW_LE_ID , \n"
            + "fusion.SOURCE_ID , PROCESSED_IND , TIME_RECEIVED , TIME_PROCESSED \n" + "FROM \n"
            + "CMS_BATCH_CIF_FUSION fusion , SCI_LE_MAIN_PROFILE profile \n" + "WHERE \n"
            + "fusion.SOURCE_ID = profile.SOURCE_ID \n" + "AND fusion.OLD_LE_ID = profile.LMP_LE_ID \n";

    private static final String updateFusionDBSql = "UPDATE CMS_BATCH_CIF_FUSION \n"
            + "SET PROCESSED_IND = ? , TIME_PROCESSED = ? \n" + "WHERE CMS_BATCH_CIF_FUSION_ID = ?";

    private static final String findMainProfile = "SELECT \n" + "CMS_LE_MAIN_PROFILE_ID \n" + "FROM \n"
            + "SCI_LE_MAIN_PROFILE \n" + "WHERE LMP_LE_ID = ? \n" + "AND SOURCE_ID = ? "
            + "ORDER BY CMS_LE_MAIN_PROFILE_ID DESC ";

    private static final String softDeleteMainProfile = "UPDATE SCI_LE_MAIN_PROFILE \n"
            + "SET UPDATE_STATUS_IND='D' \n" + "WHERE CMS_LE_MAIN_PROFILE_ID = ?";

    private static final String softDeleteSubProfile = "UPDATE SCI_LE_SUB_PROFILE \n" + "SET UPDATE_STATUS_IND='D' \n"
            + "WHERE CMS_LE_MAIN_PROFILE_ID = ?";

    private static final String updateOldLimitProfile = "UPDATE SCI_LSP_LMT_PROFILE \n"
            + "SET LLP_LE_ID = ? , CMS_CUSTOMER_ID = ? \n"
            + "WHERE CMS_CUSTOMER_ID IN ( SELECT CMS_LE_SUB_PROFILE_ID \n" + "FROM  SCI_LE_SUB_PROFILE \n"
            + "WHERE CMS_LE_MAIN_PROFILE_ID = ? ) ";

    private static final String getLimitInfo = "SELECT CMS_LE_SUB_PROFILE_ID \n" + "FROM \n" + "SCI_LE_SUB_PROFILE \n"
            + "WHERE CMS_LE_MAIN_PROFILE_ID = ?";

    private static final String getLimitProfileId = "SELECT limitProfile.CMS_LSP_LMT_PROFILE_ID \n" + "FROM \n"
            + "SCI_LE_SUB_PROFILE subProfile , SCI_LSP_LMT_PROFILE limitProfile \n"
            + "WHERE subProfile.CMS_LE_MAIN_PROFILE_ID = ? \n"
            + "AND subProfile.CMS_LE_SUB_PROFILE_ID = limitProfile.CMS_CUSTOMER_ID";

    private static final String updateJointBorrower = "UPDATE SCI_LSP_JOINT_BORROWER \n" + "SET LJB_LE_ID = ? \n"
            + "WHERE CMS_LMP_LIMIT_PROFILE_ID IN \n" + "( SELECT limits.CMS_LSP_LMT_PROFILE_ID \n"
            + "FROM SCI_LE_SUB_PROFILE subProfile , SCI_LSP_LMT_PROFILE limits \n"
            + "WHERE  subProfile.CMS_LE_MAIN_PROFILE_ID = ? \n"
            + "AND subProfile.CMS_LE_SUB_PROFILE_ID = limits.CMS_CUSTOMER_ID )";

    private static final String updateAppLimit = "UPDATE SCI_LSP_APPR_LMTS \n" + "SET LMT_LE_ID  = ? \n"
            + "WHERE CMS_LIMIT_PROFILE_ID IN \n" + "( SELECT limits.CMS_LSP_LMT_PROFILE_ID \n"
            + "FROM SCI_LE_SUB_PROFILE subProfile , SCI_LSP_LMT_PROFILE limits \n"
            + "WHERE  subProfile.CMS_LE_MAIN_PROFILE_ID = ? \n"
            + "AND subProfile.CMS_LE_SUB_PROFILE_ID = limits.CMS_CUSTOMER_ID )";

    private static final String updateXRefMap = "UPDATE SCI_LSP_LMTS_XREF_MAP SET LXM_LE_ID = ? \n"
            + "WHERE CMS_LSP_APPR_LMTS_ID IN ( SELECT CMS_LSP_APPR_LMTS_ID \n"
            + "FROM SCI_LE_SUB_PROFILE subProfile , SCI_LSP_LMT_PROFILE limits , SCI_LSP_APPR_LMTS appLmt \n"
            + "WHERE  subProfile.CMS_LE_MAIN_PROFILE_ID = ? \n"
            + "AND subProfile.CMS_LE_SUB_PROFILE_ID = limits.CMS_CUSTOMER_ID \n"
            + "AND limits.CMS_LSP_LMT_PROFILE_ID = appLmt.CMS_LIMIT_PROFILE_ID ) ";

    private static final String updateLmtSecMap = "UPDATE CMS_LIMIT_SECURITY_MAP SET SCI_LAS_LE_ID = ? \n"
            + "WHERE CMS_LSP_APPR_LMTS_ID IN ( SELECT CMS_LSP_APPR_LMTS_ID \n"
            + "FROM SCI_LE_SUB_PROFILE subProfile , SCI_LSP_LMT_PROFILE limits , SCI_LSP_APPR_LMTS appLmt \n"
            + "WHERE  subProfile.CMS_LE_MAIN_PROFILE_ID = ? \n"
            + "AND subProfile.CMS_LE_SUB_PROFILE_ID = limits.CMS_CUSTOMER_ID \n"
            + "AND limits.CMS_LSP_LMT_PROFILE_ID = appLmt.CMS_LIMIT_PROFILE_ID ) ";

    private static final String updateXRef = "UPDATE SCI_LSP_SYS_XREF SET LSX_LE_ID = ? ,\n"
            + "CMS_LE_SUB_PROFILE_ID = ? \n" + "WHERE CMS_LE_SUB_PROFILE_ID IN ( SELECT CMS_LE_SUB_PROFILE_ID \n"
            + "FROM SCI_LE_SUB_PROFILE \n" + "WHERE CMS_LE_MAIN_PROFILE_ID = ?  )";

    private static final String updateCredit = "UPDATE SCI_LE_CREDIT_GRADE \n" + "SET UPDATE_STATUS_IND = 'D' \n"
            + "WHERE CMS_MAIN_PROFILE_ID = ? ";

    private static final String updateOffAddr = "UPDATE SCI_LSP_OFF_ADDR SET LOA_LE_ID = ? , CMS_MAIN_PROFILE_ID = ? \n"
            + "WHERE CMS_MAIN_PROFILE_ID IN \n"
            + "( SELECT CMS_LE_SUB_PROFILE_ID \n"
            + "FROM SCI_LE_SUB_PROFILE \n"
            + "WHERE CMS_LE_MAIN_PROFILE_ID = ? ) ";

    private static final String countPledgor = "SELECT count ( pledgor.PLG_LE_ID  ) \n"
            + "FROM SCI_PLEDGOR_DTL pledgor , SCI_LE_MAIN_PROFILE profile \n"
            + "WHERE  profile.CMS_LE_MAIN_PROFILE_ID = ? \n" + "AND pledgor.PLG_LE_ID = profile.LMP_LE_ID \n"
            + "AND pledgor.SOURCE_ID = profile.SOURCE_ID ";

    private static final String updatePledgorLeId = "UPDATE SCI_PLEDGOR_DTL \n" + "SET PLG_LE_ID = ? \n"
            + "WHERE PLG_LE_ID = ? AND SOURCE_ID = ? ";

    private static final String softDeletePledgor = "UPDATE SCI_PLEDGOR_DTL \n" + "SET UPDATE_STATUS_IND = 'D' \n"
            + "WHERE PLG_LE_ID = ? AND SOURCE_ID = ? ";

    private static final String getPledgorDtlId = "SELECT pledgor.CMS_PLEDGOR_DTL_ID \n"
            + "FROM SCI_PLEDGOR_DTL pledgor , SCI_LE_MAIN_PROFILE profile \n"
            + "WHERE  profile.CMS_LE_MAIN_PROFILE_ID = ? \n" + "AND pledgor.PLG_LE_ID = profile.LMP_LE_ID \n"
            + "AND pledgor.SOURCE_ID = profile.SOURCE_ID ";

    private static final String updatePledgorDtlId = "UPDATE SCI_SEC_PLDGR_MAP \n" + "SET CMS_PLEDGOR_DTL_ID = ? \n"
            + "WHERE CMS_PLEDGOR_DTL_ID IN \n" + "( SELECT map.CMS_PLEDGOR_DTL_ID \n"
            + "FROM SCI_SEC_PLDGR_MAP map , SCI_PLEDGOR_DTL pledgor \n"
            + "WHERE map.CMS_PLEDGOR_DTL_ID = pledgor.CMS_PLEDGOR_DTL_ID \n" + "AND pledgor.PLG_LE_ID = ? \n"
            + "AND pledgor.SOURCE_ID = ? ) ";

    /**
     * Creates a new instance of CustomerFusionDAO
     */
    public CustomerFusionDAO() {

    }

    public void singleTransaction(String newLeId, String oldLeId, String source, long cmsLeMainProfileId,
                                  long oldCmsLeMainProfileId) throws Exception {

        softDeleteMainAndSubProfile(oldCmsLeMainProfileId);

        updateOldLimitProfile(newLeId, cmsLeMainProfileId, oldCmsLeMainProfileId);

        updateJointBorrower(newLeId, oldCmsLeMainProfileId);

        updateAppLimit(newLeId, oldCmsLeMainProfileId);

        updateXRefMap(newLeId, oldCmsLeMainProfileId);

        updateLimitSecurityMap(newLeId, oldCmsLeMainProfileId);

        updateXRef(newLeId, cmsLeMainProfileId, oldCmsLeMainProfileId);

        updateCreditGrade(oldCmsLeMainProfileId);

        updateOffAddr(newLeId, cmsLeMainProfileId, oldCmsLeMainProfileId);

        updatePledgor(newLeId, oldLeId, source, cmsLeMainProfileId, oldCmsLeMainProfileId);
    }

    public OBCustomerFusion[] getCustomerOldIdForFusion() {
        return (OBCustomerFusion[]) getJdbcTemplate().query(findFusionIdSql, new ResultSetExtractor() {
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                ArrayList list = new ArrayList();
                while (rs.next()) {
                    OBCustomerFusion fusion = new OBCustomerFusion();
                    fusion.setCifFusionId(rs.getLong("CMS_BATCH_CIF_FUSION_ID"));
                    fusion.setOldLeId(rs.getString("OLD_LE_ID"));
                    fusion.setNewLeId(rs.getString("NEW_LE_ID"));
                    fusion.setSourceId(rs.getString("SOURCE_ID"));
                    list.add(fusion);
                }
                return (OBCustomerFusion[]) list.toArray(new OBCustomerFusion[0]);
            }
        });
    }

    public void updateObCustomerFusion(OBCustomerFusion[] customers) throws Exception {
        logger.debug("updateFusionDBSql : " + updateFusionDBSql);
        for (int loop = 0; loop < customers.length; loop++) {
            getJdbcTemplate().update(updateFusionDBSql,
                    new Object[]{String.valueOf(customers[loop].getProcessedIndicator()), new Timestamp(System.currentTimeMillis()), new Long(customers[loop].getCifFusionId())});
        }
    }

    public long getCmsLeMainProfileId(String leId, String source) throws Exception {
        return getJdbcTemplate().queryForLong(findMainProfile, new Object[]{leId, source});
    }

    public long getLimitProfileId(long cmsLeMainProfileId) throws Exception {
        return getJdbcTemplate().queryForLong(getLimitProfileId, new Object[]{new Long(cmsLeMainProfileId)});
    }

    public void softDeleteMainAndSubProfile(long cmsLeMainProfileId) throws Exception {
        logger.debug("softDeleteMainProfile : " + softDeleteMainProfile);
        logger.debug("cmsLeMainProfileId : " + cmsLeMainProfileId);
        int rs = getJdbcTemplate().update(softDeleteMainProfile,
                new Object[]{new Long(cmsLeMainProfileId)});
        logger.debug("Number of rows affected : " + rs);

        logger.debug("softDeleteSubProfile : " + softDeleteSubProfile);
        logger.debug("cmsLeMainProfileId : " + cmsLeMainProfileId);
        rs = getJdbcTemplate().update(softDeleteSubProfile,
                new Object[]{new Long(cmsLeMainProfileId)});
        logger.debug("Number of rows affected : " + rs);
    }

    public void updateOldLimitProfile(String newLeId, long cmsLeMainProfileId, long oldLimitProfileId) throws Exception {
        logger.debug("getLimitInfo : " + getLimitInfo);
        logger.debug("cmsLeMainProfileId : " + cmsLeMainProfileId);
        long subProfileId;

        subProfileId = getJdbcTemplate().queryForLong(getLimitInfo,
                new Object[]{new Long(cmsLeMainProfileId)});

        if (subProfileId != 0) {
            logger.debug("CMS_LE_SUB_PROFILE_ID : " + subProfileId);
        } else {
            throw new Exception("Info limit profile info cannot be found for cmsLeMainProfileId : "
                    + cmsLeMainProfileId);
        }

        logger.debug("updateOldLimitProfile : " + updateOldLimitProfile);
        logger.debug("newLeId : " + newLeId);
        logger.debug("subProfileId : " + subProfileId);
        logger.debug("oldLimitProfileId : " + oldLimitProfileId);

        int num = getJdbcTemplate().update(updateOldLimitProfile,
                new Object[]{newLeId, new Long(subProfileId), new Long(oldLimitProfileId)});

        logger.debug("Number of rows affected : " + num);
    }

    public void updateJointBorrower(String newLeId, long oldLimitProfileId) throws Exception {
        logger.debug("updateJointBorrower : " + updateJointBorrower);
        logger.debug("newLeId : " + newLeId);
        logger.debug("oldLimitProfileId : " + oldLimitProfileId);

        int num = getJdbcTemplate().update(updateJointBorrower,
                new Object[]{newLeId, new Long(oldLimitProfileId)});

        logger.debug("Number of rows affected : " + num);
    }

    public void updateAppLimit(String newLeId, long oldLimitProfileId) throws Exception {
        logger.debug("updateAppLimit : " + updateAppLimit);
        logger.debug("newLeId : " + newLeId);
        logger.debug("oldLimitProfileId : " + oldLimitProfileId);

        int num = getJdbcTemplate().update(updateAppLimit,
                new Object[]{newLeId, new Long(oldLimitProfileId)});

        logger.debug("Number of rows affected : " + num);
    }

    public void updateXRefMap(String newLeId, long oldLimitProfileId) throws Exception {
        logger.debug("updateXRefMap : " + updateXRefMap);
        logger.debug("newLeId : " + newLeId);
        logger.debug("oldLimitProfileId : " + oldLimitProfileId);

        int num = getJdbcTemplate().update(updateXRefMap,
                new Object[]{newLeId, new Long(oldLimitProfileId)});

        logger.debug("Number of rows affected : " + num);
    }

    public void updateLimitSecurityMap(String newLeId, long oldLimitProfileId) throws Exception {
        logger.debug("updateLmtSecMap : " + updateLmtSecMap);
        logger.debug("newLeId : " + newLeId);
        logger.debug("oldLimitProfileId : " + oldLimitProfileId);

        int num = getJdbcTemplate().update(updateLmtSecMap,
                new Object[]{newLeId, new Long(oldLimitProfileId)});

        logger.debug("Number of rows affected : " + num);
    }

    public void updateXRef(String newLeId, long cmsLeMainProfileId, long oldLimitProfileId) throws Exception {
        logger.debug("getLimitInfo : " + getLimitInfo);
        logger.debug("cmsLeMainProfileId : " + cmsLeMainProfileId);
        long subProfileId;

        subProfileId = getJdbcTemplate().queryForLong(getLimitInfo,
                new Object[]{new Long(cmsLeMainProfileId)});

        if (subProfileId != 0) {
            logger.debug("CMS_LE_SUB_PROFILE_ID : " + subProfileId);
        } else {
            throw new Exception("Info limit profile info cannot be found for cmsLeMainProfileId : "
                    + cmsLeMainProfileId);
        }

        logger.debug("updateXRef : " + updateXRef);
        logger.debug("newLeId : " + newLeId);
        logger.debug("subProfileId : " + subProfileId);
        logger.debug("oldLimitProfileId : " + oldLimitProfileId);

        int num = getJdbcTemplate().update(updateXRef,
                new Object[]{newLeId, new Long(subProfileId), new Long(oldLimitProfileId)});

        logger.debug("Number of rows affected : " + num);
    }

    public void updateCreditGrade(long oldLimitProfileId) throws Exception {
        logger.debug("updateCredit : " + updateCredit);
        logger.debug("oldLimitProfileId : " + oldLimitProfileId);

        int num = getJdbcTemplate().update(updateCredit,
                new Object[]{new Long(oldLimitProfileId)});

        logger.debug("Number of rows affected : " + num);
    }

    public void updateOffAddr(String newLeId, long cmsLeMainProfileId, long oldLimitProfileId) throws Exception {
        logger.debug("updateOffAddr : " + updateOffAddr);
        logger.debug("newLeId : " + newLeId);
        logger.debug("cmsLeMainProfileId : " + cmsLeMainProfileId);
        logger.debug("oldLimitProfileId : " + oldLimitProfileId);

        int num = getJdbcTemplate().update(updateOffAddr,
                new Object[]{newLeId, new Long(cmsLeMainProfileId), new Long(oldLimitProfileId)});

        logger.debug("Number of rows affected : " + num);
    }

    public void updatePledgor(String newLeId, String oldLeId, String source, long cmsLeMainProfileId,
                              long oldLimitProfileId) throws Exception {
        logger.debug("countPledgor : " + countPledgor);
        logger.debug("oldLimitProfileId : " + oldLimitProfileId);

        long count = getJdbcTemplate().queryForLong(countPledgor,
                new Object[]{new Long(oldLimitProfileId)});

        if (count == 0) {
            return;
        }

        logger.debug("countPledgor : " + countPledgor);
        logger.debug("cmsLeMainProfileId : " + cmsLeMainProfileId);

        count = getJdbcTemplate().queryForLong(countPledgor,
                new Object[]{new Long(cmsLeMainProfileId)});

        if (count == 0) {
            // update the pledgor ( pointing to old Le Id ) to point to the new Le Id
            logger.debug("updatePledgorLeId : " + updatePledgorLeId);
            logger.debug("newLeId : " + newLeId);
            logger.debug("oldLeId : " + oldLeId);
            logger.debug("source : " + source);

            int num = getJdbcTemplate().update(updatePledgorLeId,
                    new Object[]{newLeId, oldLeId, source});

            logger.debug("Number of rows affected : " + num);
        } else {
            // update sec pledgor map ( pointing to old Le Id ) to point to the
            // new Le Id and soft delete the pledgor ( pointing to old Le Id )
            logger.debug("softDeletePledgor : " + softDeletePledgor);
            logger.debug("oldLeId : " + oldLeId);
            logger.debug("source : " + source);

            int num = getJdbcTemplate().update(softDeletePledgor,
                    new Object[]{oldLeId, source});

            logger.debug("Number of rows affected : " + num);

            logger.debug("getPledgorDtlId : " + getPledgorDtlId);
            logger.debug("cmsLeMainProfileId : " + cmsLeMainProfileId);

            String dtlId = (String) getJdbcTemplate().queryForObject(getPledgorDtlId,
                    new Object[]{new Long(cmsLeMainProfileId)}, String.class);

            if (StringUtils.isEmpty(dtlId)) {
                throw new Exception("Pledgor info cannot be found for cmsLeMainProfileId : " + cmsLeMainProfileId);
            }

            logger.debug("updatePledgorDtlId : " + updatePledgorDtlId);
            logger.debug("dtlId : " + dtlId);
            logger.debug("source : " + source);

            num = getJdbcTemplate().update(updatePledgorDtlId,
                    new Object[]{dtlId, oldLeId, source});

            logger.debug("Number of rows affected : " + num);
        }

    }
}
