/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/DDNDAO.java,v 1.21 2005/09/19 09:02:07 czhou Exp $
 */
package com.integrosys.cms.app.ddn.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.JdbcTemplateAdapter;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * DAO for DDN
 * @author $Author: czhou $
 * @version $Revision: 1.21 $
 * @since $Date: 2005/09/19 09:02:07 $ Tag: $Name: $
 */

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: 25 May 2010
 * <p/>
 * Conversion to apply Spring Jdbc template to replace DBUtil
 */

public class DDNDAO extends JdbcTemplateAdapter implements IDDNDAO {

    private static final String SELECT_DDN_TRX = "SELECT CMS_DDN_GENERATED.DDN_ID FROM CMS_DDN_GENERATED, TRANSACTION "
            + " WHERE TRANSACTION.TRANSACTION_TYPE = 'DDN' AND STATUS <> 'CLOSED' "
            + " AND TRANSACTION.REFERENCE_ID = CMS_DDN_GENERATED.DDN_ID";

    /*
      * private static final String SELECT_LLI_TRX =
      * "SELECT CMS_DDN_GENERATED.DDN_ID FROM CMS_DDN_GENERATED, TRANSACTION " +
      * " WHERE TRANSACTION.TRANSACTION_TYPE = 'LLI' AND STATUS <> 'CLOSED' " +
      * " AND TRANSACTION.REFERENCE_ID = CMS_DDN_GENERATED.DDN_ID";
      */

    private static final String SELECT_DDN_TRX_COUNT = "SELECT COUNT(*)  FROM STAGE_DDN_GENERATED, TRANSACTION"
            + " WHERE TRANSACTION.STAGING_REFERENCE_ID = STAGE_DDN_GENERATED.DDN_ID";

    /**
     * Check if DDN the latest certificate generated for the limit profile.
     *
     * @param limitProfileID limit profile id
     * @return true if it is the latest cert generated, otherwise false
     * @throws SearchDAOException on error checking the ddn
     */
    public boolean isDDNLatestCert(long limitProfileID) throws SearchDAOException {
        String scc = "SELECT transaction_date FROM trans_history where transaction_id in (SELECT transaction_id FROM transaction, cms_scc_generated WHERE transaction_type = '"
                + ICMSConstant.INSTANCE_SCC
                + "' AND reference_id = scc_id AND cms_lsp_lmt_profile_id = ?) AND status = '"
                + ICMSConstant.STATE_ACTIVE
                + "' AND from_state <> '"
                + ICMSConstant.STATE_REJECTED
                + "' ORDER BY transaction_date DESC";

        String ccc = "SELECT transaction_date FROM trans_history where transaction_id in (SELECT transaction_id FROM transaction, cms_ccc_generated WHERE transaction_type = '"
                + ICMSConstant.INSTANCE_CCC
                + "' AND reference_id = ccc_id AND sub_category = '"
                + ICMSConstant.CHECKLIST_MAIN_BORROWER
                + "' AND cms_lsp_lmt_profile_id = ?) AND status = '"
                + ICMSConstant.STATE_ACTIVE
                + "' AND from_state <> '"
                + ICMSConstant.STATE_REJECTED
                + "' ORDER BY transaction_date DESC";

        String pscc = "SELECT transaction_date FROM trans_history where transaction_id in (SELECT transaction_id FROM transaction, cms_pscc_generated WHERE transaction_type = '"
                + ICMSConstant.INSTANCE_PSCC
                + "' AND reference_id = pscc_id AND cms_lsp_lmt_profile_id = ?) AND status = '"
                + ICMSConstant.STATE_ACTIVE
                + "' AND from_state <> '"
                + ICMSConstant.STATE_REJECTED
                + "' ORDER BY transaction_date DESC";

        String ddn = "SELECT transaction_date FROM trans_history where transaction_id in (SELECT transaction_id FROM transaction, cms_ddn_generated WHERE transaction_type = '"
                + ICMSConstant.INSTANCE_DDN
                + "' AND reference_id = ddn_id AND cms_lsp_lmt_profile_id = ?) AND status = '"
                + ICMSConstant.STATE_ACTIVE
                + "' AND from_state <> '"
                + ICMSConstant.STATE_REJECTED
                + "' ORDER BY transaction_date DESC";

        try {
            Date sccDate = (Date) getJdbcTemplate().query(scc, new Object[]{new Long(limitProfileID)}, new ResultSetExtractor() {
                public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                    return processLatestCertInstanceResultSet(rs);
                }
            });

            Date cccDate = (Date) getJdbcTemplate().query(ccc, new Object[]{new Long(limitProfileID)}, new ResultSetExtractor() {
                public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                    return processLatestCertInstanceResultSet(rs);
                }
            });

            Date psccDate = (Date) getJdbcTemplate().query(pscc, new Object[]{new Long(limitProfileID)}, new ResultSetExtractor() {
                public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                    return processLatestCertInstanceResultSet(rs);
                }
            });

            Date ddnDate = (Date) getJdbcTemplate().query(ddn, new Object[]{new Long(limitProfileID)}, new ResultSetExtractor() {
                public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                    return processLatestCertInstanceResultSet(rs);
                }
            });

            boolean isDDN = true;
            if (ddnDate == null) {
                isDDN = false;
            } else {
                if (sccDate != null) {
                    if (ddnDate.compareTo(sccDate) < 0) {
                        isDDN = false;
                    }
                }
                if (cccDate != null) {
                    if (isDDN && (ddnDate.compareTo(cccDate) < 0)) {
                        isDDN = false;
                    }
                }
                if (psccDate != null) {
                    if (isDDN && (ddnDate.compareTo(psccDate) < 0)) {
                        isDDN = false;
                    }
                }
            }
            return isDDN;
        }
        catch (Exception e) {
            throw new SearchDAOException("Exception at isDDNLatestCert", e);
        }
    }

    private Date processLatestCertInstanceResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            return new Date(rs.getTimestamp("TRANSACTION_DATE").getTime());
        }
        rs.close();
        return null;
    }

    /**
     * To get the DDNID by limit profile ID
     *
     * @param aLimitProfileID of long type
     * @return long - the DDN ID
     * @throws SearchDAOException
     */

    public long getDDNIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException {
        StringBuffer buf = new StringBuffer();
        buf.append(" AND ");
        buf.append(DDNTBL_LIMIT_PROFILE_ID_PREF);
        buf.append(" = ?");
        String sql = SELECT_DDN_TRX + " " + buf.toString();

        Long ddnId = (Long) getJdbcTemplate().query(sql, new Object[]{new Long(aLimitProfileID)}, new ResultSetExtractor() {
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                Long ddnId = new Long(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
                if (rs.next()) {
                    ddnId = new Long(rs.getLong(DDNTBL_DDN_ID));
                }
                return ddnId;
            }
        });

        if (ddnId.longValue() < 0) {
            throw new SearchDAOException("Unable to find DDN ID by Limit Prodile ID: " + aLimitProfileID);
        }

        return ddnId.longValue();
    }

    /**
     * To get the LLI ID by limit profile ID
     * @param aLimitProfileID of long type
     * @return LLI id
     * @throws SearchDAOException on error searching the LLI id
     */
    /*
      * public long getLLIIDbyLimitProfile(long aLimitProfileID) throws
      * SearchDAOException { StringBuffer buf = new StringBuffer();
      * buf.append(" AND "); buf.append(DDNTBL_LIMIT_PROFILE_ID_PREF);
      * buf.append(" = ?"); String sql = SELECT_LLI_TRX + " " + buf.toString();
      *
      * try { dbUtil = new DBUtil(); dbUtil.setSQL(sql); dbUtil.setLong
      * (1,aLimitProfileID); ResultSet rs = dbUtil.executeQuery(); while
      * (rs.next()) { return rs.getLong(DDNTBL_DDN_ID); } return
      * com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE; }
      * catch (SQLException ex) { throw new
      * SearchDAOException("SQLException in getLLIIDbyLimitProfile", ex); } catch
      * (Exception ex) { throw new
      * SearchDAOException("Exception in getLLIIDbyLimitProfile", ex); } finally
      * { try { dbUtil.close(); } catch (SQLException ex) { throw new
      * SearchDAOException("SQLException in getLLIIDbyLimitProfile", ex); } } }
      */

    /**
     * To get the number of ddn that satisfy the criteria
     *
     * @param aCriteria of DDNSearchCriteria type
     * @return int - the number of ddn that satisfy the criteria
     * @throws SearchDAOException on errors
     */
    public int getNoOfDDN(DDNSearchCriteria aCriteria) throws SearchDAOException {
        String searchString = getStagingSearchString(aCriteria);
        String sql = SELECT_DDN_TRX_COUNT;

        if (searchString != null) {
            sql = sql + " AND " + searchString;
        }

        Integer count = (Integer) getJdbcTemplate().query(sql, new Object[]{}, new ResultSetExtractor() {
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                Integer count = new Integer(0);
                if (rs.next()) {
                    count = new Integer(rs.getInt(1));
                }
                return count;
            }
        });

        return count.intValue();
    }

    private String getStagingSearchString(DDNSearchCriteria aCriteria) {
        StringBuffer buf = new StringBuffer();

        if (aCriteria == null) {
            return null;
        }

        boolean sqlAppended = false;
        if (aCriteria.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
            buf.append(STAGE_DDNTBL_LIMIT_PROFILE_ID_PREF);
            buf.append(" = ");
            buf.append(aCriteria.getLimitProfileID());
            sqlAppended = true;
        }

        if ((aCriteria.getTrxStatusList() != null) && (aCriteria.getTrxStatusList().length > 0)) {
            String[] trxStatus = aCriteria.getTrxStatusList();
            if (sqlAppended) {
                buf.append(" AND ");
            }
            buf.append(TRX_STATUS_PREF);
            buf.append(" IN (");
            for (int ii = 0; ii < trxStatus.length; ii++) {
                if (ii > 0) {
                    buf.append(", ");
                }
                buf.append("'");
                buf.append(trxStatus[ii]);
                buf.append("'");
            }
            buf.append(")");
            sqlAppended = true;
        }
        return buf.toString();
    }

    /**
     * Utilty method to check if a string value is null or empty
     *
     * @param aValue - String
     * @return boolean - true if empty and false otherwise
     */
    private boolean isEmpty(String aValue) {
        if ((aValue != null) && (aValue.trim().length() > 0)) {
            return false;
        }
        return true;
    }
}
