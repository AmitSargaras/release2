package com.integrosys.cms.batch.reports;

import com.integrosys.base.techinfra.dbsupport.ISequenceFormatter;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.DB2DateConverter;
import com.integrosys.component.commondata.app.bus.OBCodeCategoryEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description: A DAO object to store and retrieve the reports The DAO
 * constructs the relevant SQL queries depending on the report generation
 * frequency
 *
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.45 $
 * @since $Date: 2006/10/09 07:43:01 $ Tag: $Name: $
 */
public class ReportSchedulerDAO extends JdbcDaoSupport implements IReportSchedulerDAO {

    private final Logger logger = LoggerFactory.getLogger(ReportSchedulerDAO.class);

    private ISequenceFormatter sequenceFormatter;

    // Declare database information
    public static final String REPORT_TABLE = "CMS_REPORT";

    public static final String REPORT_SEQUENCE = "CMS_REPORT_SEQ";

    public static final String REPORT_REQUEST_SEQUENCE = "CMS_AUTH_REPORT_SEQ";

    public static final String COMMODITY_TYPE = "CMS_SECURITY_SUB_TYPE";

    private static final String CALL_SP_AUDIT = "{ call PKG_AUDIT.SP_START }";

    private static final String REPORT_TEMPLATE_INFO_SQL = "SELECT FILE_TYPE, TEMPLATE_NAME FROM CMS_REPORT_TEMPLATE WHERE "
            + "STATUS = '" + ICMSConstant.STATE_ACTIVE + "' AND REPORT_MASTER_ID = ? ";

    public static final String REPORT_MASTER_ID_SQL = "SELECT REPORT_MASTER_ID FROM CMS_REPORT_MASTER"
            + " WHERE MIS_REPORT_ID = ? AND STATUS = '" + ICMSConstant.STATE_ACTIVE + "'";

    public static final String LIST_REPORT_SQL = " SELECT report.REPORT_ID, report.TITLE, report.FILENAME, report.FOLDER_PATH, \n"
            + "       report.HAS_PDF,  report.HAS_XLS,  report.HAS_WORD, \n"
            + "       report.PDF_NAME, report.XLS_NAME, report.WORD_NAME,\n"
            + "       master.MIS_REPORT_ID,  \n"
            + "       report.REPORT_DATE  \n"
            + " FROM cms_report report, \n"
            + "     cms_report_master master \n"
            + " WHERE 0=0 \n"
            + " AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID \n"
            + " AND MASTER.STATUS = '"
            + ICMSConstant.STATE_ACTIVE + "' \n";

    public static final String LMS_GRP_REPORT_SQL =
            "SELECT DISTINCT report.report_id, report.title, report.filename, has_pdf, has_xls,\n" +
                    "       has_word, master.mis_report_id, folder_path, pdf_name, xls_name,word_name,grp_id\n" +
                    "  FROM cms_report report, cms_report_master master \n" +
                    "WHERE 0=0 \n" +
                    " AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID \n" +
                    " AND MASTER.STATUS = '" + ICMSConstant.STATE_ACTIVE + "' \n";

    // Constant for report formats type
    public static final String PDF_FILE_TYPE = "PDF";

    public static final String XLS_FILE_TYPE = "XLS";

    public static final String DOC_FILE_TYPE = "DOC";

    /**
     * @see com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo.ScheduleStatus#FAILURE
     */
    private static final int DEFAULT_REPORT_FAILED_STATUS = 3;

    /**
     * @param sequenceFormater the sequenceFormater to set
     */
    public void setSequenceFormatter(ISequenceFormatter sequenceFormater) {
        this.sequenceFormatter = sequenceFormater;
    }

    /**
     * @return the sequenceFormater
     */
    public ISequenceFormatter getSequenceFormatter() {
        return sequenceFormatter;
    }

    public String getCommoditySubType(String code) {
        logger.debug("Entering method getCommoditySubType");

        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append("SELECT subtype_name FROM CMS_SECURITY_SUB_TYPE WHERE security_sub_type_id = ?");

        return (String) getJdbcTemplate().query(sqlBuf.toString(), new Object[]{code}, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getString(1);
                }
                return new String();
            }
        });
    }

    public OBReport[] getReportsPriorTo(Date startDate, Date endDate) {
        logger.debug("Entering method getReportsPriorTo");

        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append(" SELECT REPORT.REPORT_ID, REPORT.TITLE, REPORT.FILENAME, ");
        sqlBuf.append(" REPORT.SCOPE_CODE, MIS_REPORT_ID, TRUNC(DATE_CREATED) ");
        sqlBuf.append(" FROM CMS_REPORT REPORT, CMS_REPORT_MASTER MASTER ");
        sqlBuf.append(" WHERE REPORT.DATE_CREATED between ? and ? ");
        sqlBuf.append(" AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID ");

        List reportList = getJdbcTemplate().query(sqlBuf.toString(),
                new Object[]{new java.sql.Date(startDate.getTime()), new java.sql.Date(endDate.getTime())},
                new RowMapper() {

                    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                        OBReport report = new OBReport();
                        report.setReportId(rs.getLong(1));
                        report.setTitle(rs.getString(2));
                        report.setFileName(rs.getString(3));
                        report.setScope(rs.getString(4));
                        report.setMISReportNumber(rs.getString(5));
                        report.setCreatedDate(rs.getDate(6));

                        return report;
                    }

                });

        return (OBReport[]) reportList.toArray(new OBReport[0]);
    }

    /**
     * Helper method to check a report search country has reports generated by
     * org code
     *
     * @param country - String
     * @return boolean
     */
    private boolean hasCountryOrgMap(String country) {
        logger.info("Entering method hasCountryOrgMap... ");

        String sql = "select * from cms_report_country_org_map where country = ? ";

        Boolean hasCountryOrgMap = (Boolean) getJdbcTemplate().query(sql, new Object[]{country},
                new ResultSetExtractor() {

                    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                        return Boolean.valueOf(rs.next());
                    }
                });

        return hasCountryOrgMap.booleanValue();

    }

    public OBReport[] getMISReportsByCountryAndDate(String countryCode, String[] orgCodes, Date reportDate,
                                                    String[] reportCategoryId, String centreCode) {
        logger.debug("Entering method getMISReportsByCountryAndDate");

        logger.debug("@@@@@@@ parameter --> countryName= " + countryCode + " reportDate= " + reportDate
                + " reportCategoryId = " + reportCategoryId + " centreCode = " + centreCode);

        String reportDateStr = DB2DateConverter.getDateAsString(reportDate);
        String db2ReportAppendDate = " DATE('" + reportDateStr + "') ";

        boolean hasOrgMap = hasCountryOrgMap(countryCode);

        List argList = new ArrayList();

        StringBuffer sqlBuf = new StringBuffer();

        // SQL start here
        // // retrieve all Daily reports for the selected date
        sqlBuf.append(LIST_REPORT_SQL);
        sqlBuf.append(" AND report.FREQUENCY = '" + ReportConstants.DAILY + "' ");
        sqlBuf.append(" AND DATE(report.REPORT_DATE) =  ");
        sqlBuf.append(db2ReportAppendDate);

        appendReportCategoryIdFilter(reportCategoryId, sqlBuf, argList);

        if (countryCode != null && !countryCode.equals("")) {
            sqlBuf.append(" AND report.SCOPE_CODE = ?");
            argList.add(countryCode);
        }

        if (centreCode != null && !centreCode.equals("")) {
            sqlBuf.append(" AND report.ORG_CODE = ?");
            argList.add(centreCode);
        }

        if (hasOrgMap) {
            appendOrgFilter(orgCodes, sqlBuf, argList);
        }

        // // retrieve all weekly reports in the current week
        sqlBuf.append("\n UNION \n");
        sqlBuf.append(LIST_REPORT_SQL);
        sqlBuf.append(" AND report.FREQUENCY = '" + ReportConstants.WEEKLY + "' ");

        sqlBuf.append(" AND (  date(report.REPORT_DATE) between  ");
        sqlBuf.append(db2ReportAppendDate);
        sqlBuf.append(" -6 DAYS  AND ");
        sqlBuf.append(db2ReportAppendDate);
        sqlBuf.append("  )");

        appendReportCategoryIdFilter(reportCategoryId, sqlBuf, argList);

        if (countryCode != null && !countryCode.equals("")) {
            sqlBuf.append(" AND report.SCOPE_CODE = ?");
            argList.add(countryCode);
        }

        if (centreCode != null && !centreCode.equals("")) {
            sqlBuf.append(" AND report.ORG_CODE = ?");
            argList.add(centreCode);
        }

        if (hasOrgMap) {
            appendOrgFilter(orgCodes, sqlBuf, argList);
        }

        // //// retrieve all monthly reports in the current month
        sqlBuf.append("\n UNION \n");
        sqlBuf.append(LIST_REPORT_SQL);
        sqlBuf.append(" AND report.FREQUENCY = '" + ReportConstants.MONTHLY + "' ");

        sqlBuf.append(" AND DATE(report.REPORT_DATE) BETWEEN ADD_MONTHS ( ( ");
        sqlBuf.append(db2ReportAppendDate);
        sqlBuf.append(" + 1 DAYS ),-1 ) AND ");
        sqlBuf.append(db2ReportAppendDate);

        appendReportCategoryIdFilter(reportCategoryId, sqlBuf, argList);

        if (countryCode != null && !countryCode.equals("")) {
            sqlBuf.append(" AND report.SCOPE_CODE = ?");
            argList.add(countryCode);
        }

        if (centreCode != null && !centreCode.equals("")) {
            sqlBuf.append(" AND report.ORG_CODE = ?");
            argList.add(centreCode);
        }

        if (hasOrgMap) {
            appendOrgFilter(orgCodes, sqlBuf, argList);
        }

        // // retrieve all bimonthly reports fortnightly
        sqlBuf.append("\n UNION \n");
        sqlBuf.append(LIST_REPORT_SQL);
        sqlBuf.append(" AND report.FREQUENCY = '" + ReportConstants.BI_MONTHLY + "' ");

        sqlBuf.append(" AND DATE(report.REPORT_DATE) = Rpt_Bimonthly_Day( ");
        sqlBuf.append(db2ReportAppendDate);
        sqlBuf.append(", master.report_master_id )");

        appendReportCategoryIdFilter(reportCategoryId, sqlBuf, argList);

        if (countryCode != null && !countryCode.equals("")) {
            sqlBuf.append(" AND report.SCOPE_CODE = ?");
            argList.add(countryCode);
        }

        if (centreCode != null && !centreCode.equals("")) {
            sqlBuf.append(" AND report.ORG_CODE = ?");
            argList.add(centreCode);
        }

        if (hasOrgMap) {
            appendOrgFilter(orgCodes, sqlBuf, argList);
        }

        sqlBuf.append("\n UNION \n");

        // // retrieve all Quarterly reports fortnightly
        sqlBuf.append(LIST_REPORT_SQL);
        sqlBuf.append(" AND report.FREQUENCY = '" + ReportConstants.QUARTERLY + "' ");

        sqlBuf.append(" AND DATE(report.REPORT_DATE) BETWEEN   ");
        sqlBuf.append(db2ReportAppendDate + " - 3 MONTH ");
        sqlBuf.append("  AND ");
        sqlBuf.append(db2ReportAppendDate + " - 1 DAYS ");

        appendReportCategoryIdFilter(reportCategoryId, sqlBuf, argList);

        if (countryCode != null && !countryCode.equals("")) {
            sqlBuf.append(" AND report.SCOPE_CODE = ?");
            argList.add(countryCode);
        }

        if (centreCode != null && !centreCode.equals("")) {
            sqlBuf.append(" AND report.ORG_CODE = ?");
            argList.add(centreCode);
        }

        if (hasOrgMap) {
            appendOrgFilter(orgCodes, sqlBuf, argList);
        }

        sqlBuf.append(" ORDER BY MIS_REPORT_ID ");

        // end here

        String sql = sqlBuf.toString();

        logger.debug("getMISReportsByCountryAndDate() SQL : \n" + sql);

        List reportList = getJdbcTemplate().query(sql, argList.toArray(), new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                OBReport report = new OBReport();
                report.setReportId(rs.getLong("REPORT_ID"));
                report.setTitle(rs.getString("TITLE"));
                report.setFileName(rs.getString("FILENAME"));
                report.setFolderPath(rs.getString("FOLDER_PATH"));
                report.setHasPDF(checkHasFile(rs.getString("HAS_PDF")));
                report.setHasXLS(checkHasFile(rs.getString("HAS_XLS")));
                report.setHasWord(checkHasFile(rs.getString("HAS_WORD")));
                report.setPdfName(rs.getString("PDF_NAME"));
                report.setXlsName(rs.getString("XLS_NAME"));
                report.setWordName(rs.getString("WORD_NAME"));
                report.setMISReportNumber(rs.getString("MIS_REPORT_ID"));

                return report;
            }
        });

        return (OBReport[]) reportList.toArray(new OBReport[0]);
    }

    /**
     * convert the has file indicator from String to boolean If 'Y' then return
     * ICMSConstant.TRUE_VALUE else return ICMSConstant.FALSE_VALUE
     *
     * @param strInd
     * @return boolean
     */
    private boolean checkHasFile(String strInd) {
        if ((strInd != null) && strInd.equals(ICMSConstant.TRUE_VALUE)) {
            return true;
        }
        return false;
    }

    public boolean getIsReportShouldGeneratedByCountry(String reportMasterId, String country) {
        logger.info("Entering method isReportGenForCountry");

        String sql = " SELECT count(*) " + " FROM cms_report_master rm LEFT OUTER JOIN cms_report_country_map rcm "
                + " ON rm.report_master_id = rcm.report_master_id "
                + " WHERE (country_iso_code = ? OR country_iso_code IS NULL) " + " AND rm.report_master_id = ?";

        int count = getJdbcTemplate().queryForInt(sql, new Object[]{country, reportMasterId});

        return (count > 0);
    }

    public String getStockExchangeDescription(String stockExchange) {
        logger.info("Entering method getStockExchangeDescription");

        String sql = "SELECT COUNTRY_NAME FROM VW_STOCK_EXCHANGE_META_INFO WHERE STOCK_EXCHANGE = ? ";

        return (String) getJdbcTemplate().query(sql, new Object[]{stockExchange}, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getString(1);
                }
                return null;
            }
        });
    }

    public String getRegionDescription(String region) {
        logger.info("Entering method getRegionDescription");

        String sql = "SELECT REGION_NAME FROM REGION WHERE REGION_CODE = ?";

        return (String) getJdbcTemplate().query(sql, new Object[]{region}, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                rs.next();
                return rs.getString(1);
            }
        });
    }

    public OBReport[] getReportJobsByScope(String scope) {
        logger.info("Entering method getReportJobsByScope - Scope: " + scope);

        String sql = "SELECT REPORT_MASTER_ID, FREQUENCY "
                + " FROM CMS_REPORT_MASTER WHERE Upper(SCOPE) = ? AND STATUS = '" + ICMSConstant.STATE_ACTIVE + "'";

        List reportList = getJdbcTemplate().query(sql, new Object[]{scope.toUpperCase()}, new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                OBReport rpt = new OBReport();
                rpt.setReportMasterId(rs.getString(1));
                rpt.setFrequency(rs.getString(2));

                return rpt;
            }

        });

        return (OBReport[]) reportList.toArray(new OBReport[0]);
    }

    public String getScopeByID(String reportMasterId) {
        logger.info("Entering method getScopeByID");

        String sql = "SELECT SCOPE FROM CMS_REPORT_MASTER WHERE REPORT_MASTER_ID = ?";

        return (String) getJdbcTemplate().query(sql, new Object[]{reportMasterId}, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    return rs.getString(1);
                }
                return null;
            }
        });
    }

    public int[] getFrequencyDaysByID(String reportMasterId) {
        logger.info("Entering method getFrequencyDaysByID");

        String sql = "SELECT FREQ_DAY_1, FREQ_DAY_2 FROM CMS_REPORT_FREQUENCY WHERE REPORT_MASTER_ID = ? AND STATUS = 'ACTIVE'";

        return (int[]) getJdbcTemplate().query(sql, new Object[]{reportMasterId}, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    int[] frequencyDays = new int[2];
                    frequencyDays[0] = rs.getInt(1);
                    frequencyDays[1] = rs.getInt(2);
                    return frequencyDays;
                }
                return null;
            }
        });
    }

    public OBReport getReportDetailsByReportID(String MISReportID) {
        String sql = "SELECT REPORT_MASTER_ID, SCOPE, REPORT_NAME, "
                + " FREQUENCY, REPORT_FILE_NAME, TITLE_MASK, REPORT_CATEGORY_ID " + " FROM CMS_REPORT_MASTER"
                + " WHERE STATUS = '" + ICMSConstant.STATE_ACTIVE + "' AND MIS_REPORT_ID = ?";

        return (OBReport) getJdbcTemplate().query(sql, new Object[]{MISReportID}, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    OBReport objReport = new OBReport();
                    objReport.setReportMasterId(rs.getString("REPORT_MASTER_ID"));
                    objReport.setReportId(rs.getLong("REPORT_MASTER_ID"));
                    objReport.setScope(rs.getString("SCOPE"));
                    objReport.setFrequency(rs.getString("FREQUENCY"));
                    objReport.setTitle(rs.getString("TITLE_MASK"));
                    return objReport;
                }
                return null;
            }
        });
    }

    public OBReportConfig getReportConfigByReportNumber(String reportNumber, String country) {
        String sql = "SELECT REPORT_MASTER_ID, SCOPE, MIS_REPORT_ID, REPORT_RECIPIENT, "
                + " FREQUENCY, REPORT_NAME, TITLE_MASK, REPORT_CATEGORY_ID FROM CMS_REPORT_MASTER"
                + " WHERE STATUS = '" + ICMSConstant.STATE_ACTIVE + "' AND MIS_REPORT_ID = ?";

        return getReportConfig(sql, reportNumber, country);
    }

    public OBReportConfig getReportConfig(String reportMasterId, String country) {
        String sql = "SELECT REPORT_MASTER_ID, SCOPE, MIS_REPORT_ID, REPORT_RECIPIENT, "
                + " FREQUENCY, REPORT_NAME, TITLE_MASK, REPORT_CATEGORY_ID FROM CMS_REPORT_MASTER"
                + " WHERE STATUS = '" + ICMSConstant.STATE_ACTIVE + "' AND REPORT_MASTER_ID = ?";

        return getReportConfig(sql, reportMasterId, country);
    }

    /**
     * retrieves the report parameter by report master ID
     *
     * @param reportIdParam @ return ReportParameter
     */
    private OBReportConfig getReportConfig(String sql, String reportIdParam, String country) {
        logger.info("Entering method getReportParameter");

        OBReportConfig config = (OBReportConfig) getJdbcTemplate().query(sql, new Object[]{reportIdParam},
                new ResultSetExtractor() {

                    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.next()) {
                            OBReportConfig config = new OBReportConfig();
                            config.setReportMasterId(rs.getString("REPORT_MASTER_ID"));
                            config.setScope(rs.getString("SCOPE"));
                            config.setFrequency(rs.getString("FREQUENCY"));
                            config.setReportName(rs.getString("REPORT_NAME"));
                            config.setTitleMask(rs.getString("TITLE_MASK"));
                            config.setReportCategoryId(rs.getString("REPORT_CATEGORY_ID"));
                            config.setReportNumber(rs.getString("MIS_REPORT_ID"));
                            config.setRecipients(rs.getString("REPORT_RECIPIENT"));
                            return config;
                        }
                        return null;
                    }
                });

        final List exportFormat = new ArrayList();
        final List fileName = new ArrayList();
        getJdbcTemplate().query(REPORT_TEMPLATE_INFO_SQL, new Object[]{config.getReportMasterId()},
                new ResultSetExtractor() {

                    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                        while (rs.next()) {
                            exportFormat.add(rs.getString("FILE_TYPE"));
                            fileName.add(rs.getString("TEMPLATE_NAME"));
                        }
                        return null;
                    }

                });

        config.setReportFileNames((String[]) fileName.toArray(new String[0]));
        config.setExportFormats((String[]) exportFormat.toArray(new String[0]));

        if (country != null) {
            // setting organisation code for report generation
            String query = " SELECT ORGANISATION FROM CMS_REPORT_COUNTRY_ORG_MAP WHERE COUNTRY = ? "
                    + " AND REPORT_MASTER_ID = ? ";

            List organisationList = getJdbcTemplate().query(query,
                    new Object[]{country, config.getReportMasterId()}, new RowMapper() {

                        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                            if (rs.next())
                                return rs.getString(1);
                            return null;
                        }
                    });

            if (organisationList != null)
                config.setOrganisations((String[]) organisationList.toArray(new String[0]));
            else
                config.setOrganisations(null);
        }

        return config;
    }

    public List getReportInfoByID(String reportMasterId) {
        logger.info("Entering method getReportInfoByID");

        List reportInfo = new ArrayList();
        final List exportFormat = new ArrayList();
        final List fileName = new ArrayList();
        getJdbcTemplate().query(REPORT_TEMPLATE_INFO_SQL, new Object[]{reportMasterId}, new ResultSetExtractor() {

            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                while (rs.next()) {
                    exportFormat.add(rs.getString("FILE_TYPE"));
                    fileName.add(rs.getString("TEMPLATE_NAME"));
                }
                return null;
            }
        });

        reportInfo.add((String[]) fileName.toArray(new String[0]));
        reportInfo.add((String[]) exportFormat.toArray(new String[0]));

        return reportInfo;
    }

    public long getReportMasterID(String reportNumber) throws ReportException {
        logger.info("Entering method getReportMasterID");

        Long reportMasterId = (Long) getJdbcTemplate().query(REPORT_MASTER_ID_SQL, new Object[]{reportNumber},
                new ResultSetExtractor() {

                    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                        if (rs.next()) {
                            return new Long(rs.getLong("REPORT_MASTER_ID"));
                        }
                        return null;
                    }
                });
        return reportMasterId.longValue();
    }

    public boolean getIsMISReportAccessibleByTeam(long teamTypeMembershipID) throws ReportException {
        logger.debug("<<<<<<< teamTypeMembershipID.. " + teamTypeMembershipID);

        String sql = "Select count(*) from cms_function_access where action = 'MISReportSearch' and role_type = ?";

        int count = getJdbcTemplate().queryForInt(sql, new Object[]{new Long(teamTypeMembershipID)});
        return (count > 0);
    }

    public boolean deleteViewReportRequest(long sid) throws ReportException {
        logger.info("Entering method deleteViewReportRequest");

        int count = getJdbcTemplate().update("Delete from CMS_AUTH_REPORT WHERE SID = ?",
                new Object[]{new Long(sid)});

        return (count > 0);
    }

    public long createViewReportRequest(long reportId, long userID) throws ReportException {
        logger.info("Entering method createViewReportRequest");

        long sid = 0;

        String sql = "INSERT INTO CMS_AUTH_REPORT (REPORT_ID, USER_ID, " + "SID, REQUEST_TS ) VALUES (?, ?, ?, ?)";
        sid = generateReportRequestKey();

        int count = getJdbcTemplate().update(
                sql,
                new Object[]{new Long(reportId), new Long(userID), new Long(sid),
                        new java.sql.Timestamp((new Date()).getTime())});

        logger.debug("<<<<<< " + count + " row(s) inserted.. ");

        return sid;
    }

    public boolean getIsConcReportAccessibleByTeam(long teamTypeMembershipID) throws ReportException {
        logger.debug("<<<<<<< teamTypeMembershipID.. " + teamTypeMembershipID);

        String sql = "Select count(*) from cms_function_access where action = 'ConcentrationReportSearch'"
                + " and role_type = ?";

        int count = getJdbcTemplate().queryForInt(sql, new Object[]{new Long(teamTypeMembershipID)});

        return (count > 0);
    }

    public boolean getIsSysReportAccessibleByTeam(long teamTypeMembershipID) throws ReportException {
        logger.debug("<<<<<<< teamTypeMembershipID.. " + teamTypeMembershipID);

        String sql = "Select count(*) from cms_function_access  where action = 'SystemReportSearch'"
                + " and role_type = ?";

        int count = getJdbcTemplate().queryForInt(sql, new Object[]{new Long(teamTypeMembershipID)});

        return (count > 0);
    }

    public OBReport[] getReportJobsByCountry(String country, String scope) {
        logger.info("Entering method getReportJobsByCountry");

        String sql = "SELECT RM.REPORT_MASTER_ID, FREQUENCY FROM "
                + " CMS_REPORT_COUNTRY_MAP RCM, CMS_REPORT_MASTER RM WHERE RCM.REPORT_MASTER_ID = RM.REPORT_MASTER_ID "
                + " AND RCM.COUNTRY_ISO_CODE = ? AND RM.SCOPE = ? ";

        List reportList = getJdbcTemplate().query(sql, new Object[]{country, scope}, new RowMapper() {

            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                OBReport rpt = new OBReport();
                rpt.setReportMasterId(rs.getString(1));
                rpt.setFrequency(rs.getString(2));

                return rpt;
            }
        });

        return (OBReport[]) reportList.toArray(new OBReport[0]);

    }

    public OBReport[] getConcentrationReportList(Date reportDate, String reportCategoryId) {
        logger.info("Entering method getConcentrationReportList");

        String reportDateStr = DB2DateConverter.getDateAsString(reportDate);
        String db2ReportAppendDate = " DATE('" + reportDateStr + "') ";
        logger.info("getConcentrationReportList(reportDate) = " + db2ReportAppendDate);

        StringBuffer sqlBuf = new StringBuffer();

        // retrieve all reports for the selected date
        sqlBuf.append(" SELECT REPORT.REPORT_ID, REPORT.TITLE, REPORT.FILENAME, ");
        sqlBuf.append(" REPORT.FOLDER_PATH, HAS_PDF, HAS_XLS, HAS_WORD, MASTER.SCOPE, ");
        sqlBuf.append(" REPORT.PDF_NAME, REPORT.XLS_NAME, REPORT.WORD_NAME  ");
        sqlBuf.append(" FROM CMS_REPORT REPORT, CMS_REPORT_MASTER MASTER ");

        sqlBuf.append(" WHERE DATE(REPORT.REPORT_DATE) =  ");
        sqlBuf.append(db2ReportAppendDate);

        sqlBuf.append(" AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID ");
        sqlBuf.append(" AND REPORT.FREQUENCY IN ('Daily', 'On Demand') ");
        sqlBuf.append(" AND MASTER.REPORT_CATEGORY_ID = ?");
        sqlBuf.append(" AND MASTER.STATUS = '" + ICMSConstant.STATE_ACTIVE + "'");

        // retrieve all weekly reports in the current week
        sqlBuf.append(" UNION ");
        sqlBuf.append(" SELECT REPORT.REPORT_ID, REPORT.TITLE, REPORT.FILENAME, ");
        sqlBuf.append(" REPORT.FOLDER_PATH, HAS_PDF, HAS_XLS, HAS_WORD, MASTER.SCOPE, ");
        sqlBuf.append(" REPORT.PDF_NAME, REPORT.XLS_NAME, REPORT.WORD_NAME  ");
        sqlBuf.append(" FROM CMS_REPORT REPORT, CMS_REPORT_MASTER MASTER ");

        sqlBuf.append(" WHERE REPORT.FREQUENCY = 'Weekly'");
        sqlBuf.append(" AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID ");

        sqlBuf.append(" AND DATE(REPORT.REPORT_DATE) BETWEEN ");
        sqlBuf.append(db2ReportAppendDate);
        sqlBuf.append("- 6 DAYS AND ");
        sqlBuf.append(db2ReportAppendDate);

        sqlBuf.append(" AND MASTER.REPORT_CATEGORY_ID = ?");
        sqlBuf.append(" AND MASTER.STATUS = '" + ICMSConstant.STATE_ACTIVE + "'");

        // retrieve all monthly reports in the current month
        sqlBuf.append(" UNION ");
        sqlBuf.append(" SELECT REPORT.REPORT_ID, REPORT.TITLE, REPORT.FILENAME, ");
        sqlBuf.append(" REPORT.FOLDER_PATH, HAS_PDF, HAS_XLS, HAS_WORD, MASTER.SCOPE, ");
        sqlBuf.append(" REPORT.PDF_NAME, REPORT.XLS_NAME, REPORT.WORD_NAME  ");
        sqlBuf.append(" FROM CMS_REPORT REPORT, CMS_REPORT_MASTER MASTER ");
        sqlBuf.append(" WHERE REPORT.FREQUENCY = 'Monthly'");
        sqlBuf.append(" AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID ");

        sqlBuf.append(" AND DATE(REPORT.REPORT_DATE) BETWEEN ADD_MONTHS ( ( ");
        sqlBuf.append(db2ReportAppendDate);
        sqlBuf.append(" + 1 DAYS ),-1 ) AND ");
        sqlBuf.append(db2ReportAppendDate);

        sqlBuf.append(" AND MASTER.REPORT_CATEGORY_ID = ?");
        sqlBuf.append(" AND MASTER.STATUS = '" + ICMSConstant.STATE_ACTIVE + "'");
        // retrieve on demand reports that has been generated

        String sql = sqlBuf.toString();
        logger.debug("getConcentrationReportList(reportDate) SQL STMT: " + sql);

        try {
            List reportList = getJdbcTemplate().query(sql,
                    new Object[]{reportCategoryId, reportCategoryId, reportCategoryId}, new RowMapper() {

                        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                            OBReport report = new OBReport();
                            report.setReportId(rs.getLong("REPORT_ID"));
                            report.setTitle(rs.getString("TITLE"));
                            report.setFileName(rs.getString("FILENAME"));
                            report.setFolderPath(rs.getString("FOLDER_PATH"));
                            report.setHasPDF(checkHasFile(rs.getString("HAS_PDF")));
                            report.setHasXLS(checkHasFile(rs.getString("HAS_XLS")));
                            report.setHasWord(checkHasFile(rs.getString("HAS_WORD")));
                            report.setPdfName(rs.getString("PDF_NAME"));
                            report.setXlsName(rs.getString("XLS_NAME"));
                            report.setWordName(rs.getString("WORD_NAME"));
                            report.setScope(rs.getString("SCOPE"));

                            return report;
                        }
                    });
            return (OBReport[]) reportList.toArray(new OBReport[0]);
        }
        catch (DataAccessException ex) {
            logger.warn("failed to retrieve concentration report, returning null object", ex);
            return null;
        }
    }

    public void insertFailedReportInfoToDB(OBReportConfig reportConfig) {
        logger.info("Entering method insertFailedReportInfoToDB for failed ");

        String sql = "INSERT INTO CMS_REPORT_ERRLOG ( ID, REPORT_MASTER_ID, " + "GENERATION_DATE, REPORT_DATE, "
                + "SCOPE_CODE, ORG_CODE, FREQUENCY, REMARKS, REPORT_FORMAT) " + "VALUES (?,?,?,?,?,?,?,?,?)";

        List argList = new ArrayList(9);
        if (reportConfig.getPdfStatus() == DEFAULT_REPORT_FAILED_STATUS) {
            setParameterFromReportConfig(reportConfig, argList);
            argList.add(8, ReportConstants.PDF_REPORT);
        } else if (reportConfig.getXlsStatus() == DEFAULT_REPORT_FAILED_STATUS) {
            setParameterFromReportConfig(reportConfig, argList);
            argList.add(8, ReportConstants.EXCEL_REPORT);
        } else if (reportConfig.getWordStatus() == DEFAULT_REPORT_FAILED_STATUS) {
            setParameterFromReportConfig(reportConfig, argList);
            argList.add(8, ReportConstants.WORD_REPORT);
        }

        getJdbcTemplate().update(sql, argList.toArray());
    }

    /**
     * helper method to set failed report DBUtil
     */
    private void setParameterFromReportConfig(OBReportConfig reportConfig, List argList) {
        argList.add(0, new Long(generateReportKey()));
        argList.add(1, reportConfig.getReportMasterId());
        argList.add(2, new java.sql.Timestamp(reportConfig.getStartTime().getTime()));
        argList.add(3, new java.sql.Timestamp(reportConfig.getReportDate().getTime()));
        argList.add(4, reportConfig.getScope());
        argList.add(5, reportConfig.getOrganisation());
        argList.add(6, reportConfig.getFrequency());
        argList.add(7, reportConfig.getRemarks());
    }

    public void insertGeneratedReportInfoToDB(OBReportConfig reportConfig) {
        logger.info("Entering method insertGeneratedReportInfoToDB success , Organisation : "
                + reportConfig.getOrganisation());

        long key = generateReportKey();

        String sql = "INSERT INTO CMS_REPORT ( REPORT_ID, REPORT_MASTER_ID, "
                + "DATE_CREATED, TITLE, FILENAME, PDF_NAME, "
                + "XLS_NAME, WORD_NAME, SCOPE_CODE,REPORT_DATE, HAS_PDF, "
                + "HAS_XLS, HAS_WORD, FOLDER_PATH, FREQUENCY, ORG_CODE, START_TIME, END_TIME ) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        List argList = new ArrayList();
        argList.add(new Long(key));
        argList.add(reportConfig.getReportMasterId());
        argList.add(new java.sql.Timestamp(reportConfig.getStartTime().getTime()));
        argList.add(reportConfig.getTitleMask());
        argList.add(reportConfig.getReportName());
        argList.add(reportConfig.getPdfFileName());
        argList.add(reportConfig.getXlsFileName());
        argList.add(reportConfig.getWordFileName());
        argList.add(reportConfig.getScope());
        argList.add(new java.sql.Date(reportConfig.getReportDate().getTime()));
        argList.add(checkHasReport(reportConfig.getPdfFileName()));
        argList.add(checkHasReport(reportConfig.getXlsFileName()));
        argList.add(checkHasReport(reportConfig.getWordFileName()));
        argList.add(reportConfig.getFolderPath());
        argList.add(reportConfig.getFrequency());
        argList.add(reportConfig.getOrganisation());
        argList.add(new java.sql.Timestamp(reportConfig.getStartTime().getTime()));
        argList.add(new java.sql.Timestamp(reportConfig.getEndTime().getTime()));

        getJdbcTemplate().update(sql, argList.toArray());

    }

    /**
     * Helper method to check file has generated for the format
     */
    private String checkHasReport(String strName) {
        if ((strName != null) && (strName.length() > 0)) {
            return ICMSConstant.TRUE_VALUE;
        }
        return ICMSConstant.FALSE_VALUE;
    }

    public OBReport getReport(long reportId, final String reportType) {

        logger.info("Entering method getReport");

        String sql = "SELECT PDF_NAME, XLS_NAME, WORD_NAME, FILENAME, "
                + "FOLDER_PATH FROM CMS_REPORT WHERE REPORT_ID = ? ";

        try {
            return (OBReport) getJdbcTemplate().query(sql, new Object[]{new Long(reportId)},
                    new ResultSetExtractor() {

                        public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                            rs.next();

                            OBReport report = new OBReport();
                            report.setPdfName(rs.getString(1));
                            report.setXlsName(rs.getString(2));
                            report.setWordName(rs.getString(3));
                            report.setFolderPath(rs.getString(5));

                            String fileName = rs.getString(4) + "." + reportType.toLowerCase();
                            report.setFileName(fileName);

                            return report;
                        }
                    });
        }
        catch (DataAccessException ex) {
            logger.warn("failed to retrieve report object by reportId [" + reportId + "], returning null object", ex);
            return null;
        }

    }

    /**
     * generates primary key for report request
     *
     * @return int - primary key
     * @throws Exception
     */
    private long generateReportRequestKey() {
    	//For Db2
//        String sql = "VALUES nextval FOR " + REPORT_REQUEST_SEQUENCE;
        //For Oracle
        String sql = "SELECT " + REPORT_REQUEST_SEQUENCE+".NEXTVAL FROM DUAL";

        long seq = getJdbcTemplate().queryForLong(sql);

        try {
            String seqStr = getSequenceFormatter().formatSeq(String.valueOf(seq));
            return Long.parseLong(seqStr);
        }
        catch (Exception ex) {
            IllegalStateException ise = new IllegalStateException("failed to format sequence ["
                    + REPORT_REQUEST_SEQUENCE + "] using formatter [" + getSequenceFormatter() + "]");
            ise.initCause(ex);
            throw ise;
        }
    }

    /**
     * generates primary key for report
     *
     * @return int - primary key
     * @throws Exception
     */
    private long generateReportKey() {
    	//FOR DB2
//        String sql = "VALUES nextval FOR " + REPORT_SEQUENCE;
    	//FOR ORACLE
        String sql = "SELECT " + REPORT_SEQUENCE+".NEXTVAL FROM DUAL";

        long seq = getJdbcTemplate().queryForLong(sql);

        try {
            String seqStr = getSequenceFormatter().formatSeq(String.valueOf(seq));
            return Long.parseLong(seqStr);
        }
        catch (Exception ex) {
            IllegalStateException ise = new IllegalStateException("failed to format sequence ["
                    + REPORT_REQUEST_SEQUENCE + "] using formatter [" + getSequenceFormatter() + "]");
            ise.initCause(ex);
            throw ise;
        }
    }

    public String getBizParamValue(String paramCode) {
        logger.info("Entering method getBizParamValue");

        String sql = "SELECT PARAMETER_VALUE FROM BUS_PARAM WHERE PARAMETER_CODE = ?";

        try {
            return (String) getJdbcTemplate().query(sql, new Object[]{paramCode}, new ResultSetExtractor() {

                public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                    if (rs.next()) {
                        return rs.getString(1);
                    }
                    return null;
                }
            });
        }
        catch (DataAccessException ex) {
            logger.warn("failed to retrieve biz param value for code [" + paramCode + "], returning null object", ex);
            return null;
        }

    }

    public String getFrequencyForReport(String reportMasterId) {
        logger.info("Entering method getFrequencyForReport");

        final String sql = "SELECT FREQUENCY FROM CMS_REPORT_MASTER WHERE REPORT_MASTER_ID = ? ";

        try {
            return (String) getJdbcTemplate().query(sql, new Object[]{reportMasterId}, new ResultSetExtractor() {

                public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                    if (rs.next()) {
                        return rs.getString(1);
                    }
                    return null;
                }
            });
        }
        catch (DataAccessException ex) {
            logger.warn("failed to retrieve report frequency for report master id [" + reportMasterId
                    + "], returning null object", ex);
            return null;
        }

    }

    /**
     * This method is used to get the report based on their reportCategoryId
     *
     * @param reportCategoryId
     * @param sqlBuf
     */
    private void appendReportCategoryIdFilter(String[] reportCategoryId, StringBuffer sqlBuf, List argList) {
        if (!CommonUtil.isEmptyArray(reportCategoryId)) {
            sqlBuf.append(" AND ( MASTER.REPORT_CATEGORY_ID  ");
            CommonUtil.buildSQLInList(reportCategoryId, sqlBuf, argList);
            sqlBuf.append(" ) ");
        }
    }

    private void appendOrgFilter(String[] orgCodes, StringBuffer sqlBuf, List argList) {
        if (!CommonUtil.isEmptyArray(orgCodes)) {
            sqlBuf.append(" AND (REPORT.ORG_CODE IS NULL OR REPORT.ORG_CODE ");
            CommonUtil.buildSQLInList(orgCodes, sqlBuf, argList);
            sqlBuf.append(" ) ");
        }
    }

    private void addMISFilter(String[] allowedCountries, StringBuffer sqlBuf, List argList) {
        if ((allowedCountries == null) || (allowedCountries.length == 0)) {
            sqlBuf.append(" AND MASTER.SCOPE NOT IN ( 'Country', 'Exchange' ) ");
        } else {
            sqlBuf.append(" AND ( MASTER.SCOPE = 'Country' ");
            sqlBuf.append(" AND REPORT.SCOPE_CODE ");
            CommonUtil.buildSQLInList(allowedCountries, sqlBuf, argList);
            sqlBuf.append(" OR ( MASTER.SCOPE = 'Exchange' ");
            sqlBuf.append(" AND EXISTS ( SELECT 1 FROM CMS_STOCK_EXCHANGE ");
            sqlBuf.append(" WHERE COUNTRY_ISO_CODE ");
            CommonUtil.buildSQLInList(allowedCountries, sqlBuf, argList);
            sqlBuf.append(" AND STOCK_EXCHANGE = REPORT.SCOPE_CODE )))");
        }
    }

    public OBReport[] getConcentrationReportList(Date reportDate, String reportCategoryId, String[] allowedCountries,
                                                 long teamTypeID) {

        logger.info("teamTypeID " + teamTypeID + "allowedCountries " + allowedCountries);

        if (teamTypeID == ICMSConstant.TEAM_TYPE_GROUPMGM) {
            return getConcentrationReportList(reportDate, reportCategoryId, null);
        } else if (teamTypeID == ICMSConstant.TEAM_TYPE_MIS) {
            return getConcentrationReportList(reportDate, reportCategoryId, allowedCountries);
        } else if (teamTypeID == ICMSConstant.TEAM_TYPE_SCO) {
            return getConcentrationReportList(reportDate, reportCategoryId, allowedCountries);
        }
        return getConcentrationReportList(reportDate, reportCategoryId);
    }

    public OBReport[] getConcentrationReportList(Date reportDate, String reportCategoryId, String[] allowedCountries) {

        String reportDateStr = DB2DateConverter.getDateAsString(reportDate);
        String db2ReportAppendDate = " DATE('" + reportDateStr + "') ";
        logger.debug("getConcentrationReportList(date,countries) = " + db2ReportAppendDate);

        List argList = new ArrayList();

        StringBuffer sqlBuf = new StringBuffer();
        // retrieve all reports for the selected date
        sqlBuf.append(" SELECT REPORT.REPORT_ID,  REPORT.TITLE, REPORT.FILENAME, ");
        sqlBuf.append(" REPORT.FOLDER_PATH, HAS_PDF, HAS_XLS, HAS_WORD, ");
        sqlBuf.append(" REPORT.PDF_NAME, REPORT.XLS_NAME, REPORT.WORD_NAME , MASTER.SCOPE ");
        sqlBuf.append(" FROM CMS_REPORT REPORT, CMS_REPORT_MASTER MASTER ");
        sqlBuf.append(" WHERE DATE(REPORT.REPORT_DATE) =  ");
        sqlBuf.append(db2ReportAppendDate);

        sqlBuf.append(" AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID ");
        sqlBuf.append(" AND REPORT.FREQUENCY IN ('Daily', 'On Demand') ");
        sqlBuf.append(" AND MASTER.REPORT_CATEGORY_ID = ?");
        argList.add(reportCategoryId);
        sqlBuf.append(" AND MASTER.STATUS = '" + ICMSConstant.STATE_ACTIVE + "'");
        addMISFilter(allowedCountries, sqlBuf, argList);

        // retrieve all weekly reports in the current week
        sqlBuf.append(" UNION ");
        sqlBuf.append(" SELECT REPORT.REPORT_ID,  REPORT.TITLE, REPORT.FILENAME, ");
        sqlBuf.append(" REPORT.FOLDER_PATH, HAS_PDF, HAS_XLS, HAS_WORD, ");
        sqlBuf.append(" REPORT.PDF_NAME, REPORT.XLS_NAME, REPORT.WORD_NAME ,MASTER.SCOPE ");
        sqlBuf.append(" FROM CMS_REPORT REPORT, CMS_REPORT_MASTER MASTER ");
        sqlBuf.append(" WHERE REPORT.FREQUENCY = 'Weekly'");
        sqlBuf.append(" AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID ");
        sqlBuf.append(" AND DATE(REPORT.REPORT_DATE) BETWEEN ");
        sqlBuf.append(db2ReportAppendDate);
        sqlBuf.append("- 6 DAYS AND ");
        sqlBuf.append(db2ReportAppendDate);

        sqlBuf.append(" AND MASTER.REPORT_CATEGORY_ID = ?");
        argList.add(reportCategoryId);
        sqlBuf.append(" AND MASTER.STATUS = '" + ICMSConstant.STATE_ACTIVE + "'");
        addMISFilter(allowedCountries, sqlBuf, argList);

        // retrieve all monthly reports in the current month
        sqlBuf.append(" UNION ");
        sqlBuf.append(" SELECT REPORT.REPORT_ID, REPORT.TITLE, REPORT.FILENAME, ");
        sqlBuf.append(" REPORT.FOLDER_PATH, HAS_PDF, HAS_XLS, HAS_WORD, ");
        sqlBuf.append(" REPORT.PDF_NAME, REPORT.XLS_NAME, REPORT.WORD_NAME ,MASTER.SCOPE ");
        sqlBuf.append(" FROM CMS_REPORT REPORT, CMS_REPORT_MASTER MASTER ");
        sqlBuf.append(" WHERE REPORT.FREQUENCY = 'Monthly'");
        sqlBuf.append(" AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID ");

        sqlBuf.append(" AND DATE(REPORT.REPORT_DATE) BETWEEN ADD_MONTHS ( ( ");
        sqlBuf.append(db2ReportAppendDate);
        sqlBuf.append(" + 1 DAYS ),-1 ) AND ");
        sqlBuf.append(db2ReportAppendDate);

        sqlBuf.append(" AND MASTER.REPORT_CATEGORY_ID = ?");
        argList.add(reportCategoryId);
        sqlBuf.append(" AND MASTER.STATUS = '" + ICMSConstant.STATE_ACTIVE + "'");
        addMISFilter(allowedCountries, sqlBuf, argList);

        // retrieve quarterly reports that has been generated
        sqlBuf.append(" UNION ");
        sqlBuf.append(" SELECT REPORT.REPORT_ID, REPORT.TITLE, REPORT.FILENAME, ");
        sqlBuf.append(" REPORT.FOLDER_PATH, HAS_PDF, HAS_XLS, HAS_WORD, ");
        sqlBuf.append(" REPORT.PDF_NAME, REPORT.XLS_NAME, REPORT.WORD_NAME ,MASTER.SCOPE ");
        sqlBuf.append(" FROM CMS_REPORT REPORT, CMS_REPORT_MASTER MASTER ");
        sqlBuf.append(" WHERE REPORT.FREQUENCY = '" + ReportConstants.QUARTERLY + "'");
        sqlBuf.append(" AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID ");
        sqlBuf.append(" AND DATE(report.REPORT_DATE) BETWEEN   ");
        sqlBuf.append(db2ReportAppendDate + " - 3 MONTH ");
        sqlBuf.append("  AND ");
        sqlBuf.append(db2ReportAppendDate);
        sqlBuf.append(" AND MASTER.REPORT_CATEGORY_ID = ?");
        argList.add(reportCategoryId);
        sqlBuf.append(" AND MASTER.STATUS = '" + ICMSConstant.STATE_ACTIVE + "'");
        addMISFilter(allowedCountries, sqlBuf, argList);

        String sql = sqlBuf.toString();
        logger.debug("getConcentrationReportList(date,countries) SQL STMT: " + sql);

        try {
            List reportList = getJdbcTemplate().query(sql, argList.toArray(), new RowMapper() {

                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OBReport report = new OBReport();
                    report.setReportId(rs.getLong("REPORT_ID"));
                    report.setTitle(rs.getString("TITLE"));
                    report.setFileName(rs.getString("FILENAME"));
                    report.setFolderPath(rs.getString("FOLDER_PATH"));
                    report.setHasPDF(checkHasFile(rs.getString("HAS_PDF")));
                    report.setHasXLS(checkHasFile(rs.getString("HAS_XLS")));
                    report.setHasWord(checkHasFile(rs.getString("HAS_WORD")));
                    report.setPdfName(rs.getString("PDF_NAME"));
                    report.setXlsName(rs.getString("XLS_NAME"));
                    report.setWordName(rs.getString("WORD_NAME"));
                    report.setScope(rs.getString("SCOPE"));

                    return report;
                }
            });
            return (OBReport[]) reportList.toArray(new OBReport[0]);
        }
        catch (DataAccessException ex) {
            logger.warn("failed to retrieve concentration report for the countries[" + allowedCountries
                    + "], returning null object", ex);
            return null;
        }

    }

    public OBReport[] getAllWhatIfCondReportList(Date reportDate, String reportCategoryId, String[] allowedCountries) {

        String reportDateStr = DB2DateConverter.getDateAsString(reportDate);
        String db2ReportAppendDate = " DATE('" + reportDateStr + "') ";
        logger.debug("getAllWhatIfCondReportList(date,countries) = " + db2ReportAppendDate);

        List argList = new ArrayList();

        StringBuffer sqlBuf = new StringBuffer();

        sqlBuf.append(" SELECT REPORT.REPORT_ID, REPORT.TITLE, REPORT.FILENAME, ");
        sqlBuf.append(" REPORT.FOLDER_PATH, HAS_PDF, HAS_XLS, HAS_WORD, ");
        sqlBuf.append(" REPORT.PDF_NAME, REPORT.XLS_NAME, REPORT.WORD_NAME ,MASTER.SCOPE, MASTER.MIS_REPORT_ID  ");
        sqlBuf.append(" FROM CMS_REPORT REPORT, CMS_REPORT_MASTER MASTER ");
        sqlBuf.append(" WHERE REPORT.FREQUENCY IN ('Daily', 'On Demand') ");
        sqlBuf.append(" AND REPORT.REPORT_MASTER_ID = MASTER.REPORT_MASTER_ID ");
        sqlBuf.append(" AND DATE(REPORT.DATE_CREATED) =  ");
        sqlBuf.append(db2ReportAppendDate);

        sqlBuf.append(" AND MASTER.REPORT_CATEGORY_ID = ?");
        argList.add(reportCategoryId);
        sqlBuf.append(" AND MASTER.STATUS = '" + ICMSConstant.STATE_ACTIVE + "'");
        addMISFilter(allowedCountries, sqlBuf, argList);
        // retrieve on demand reports that has been generated

        String sql = sqlBuf.toString();
        logger.debug("getAllWhatIfCondReportList(date,countries) SQL STMT: " + sql);

        try {
            List reportList = getJdbcTemplate().query(sql, argList.toArray(), new RowMapper() {

                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OBReport report = new OBReport();
                    report.setReportId(rs.getLong("REPORT_ID"));
                    report.setTitle(rs.getString("TITLE"));
                    report.setFileName(rs.getString("FILENAME"));
                    report.setFolderPath(rs.getString("FOLDER_PATH"));
                    report.setHasPDF(checkHasFile(rs.getString("HAS_PDF")));
                    report.setHasXLS(checkHasFile(rs.getString("HAS_XLS")));
                    report.setHasWord(checkHasFile(rs.getString("HAS_WORD")));
                    report.setPdfName(rs.getString("PDF_NAME"));
                    report.setXlsName(rs.getString("XLS_NAME"));
                    report.setWordName(rs.getString("WORD_NAME"));
                    report.setScope(rs.getString("SCOPE"));
                    report.setMISReportNumber(rs.getString("MIS_REPORT_ID"));

                    return report;
                }
            });
            return (OBReport[]) reportList.toArray(new OBReport[0]);
        }
        catch (DataAccessException ex) {
            logger.warn("failed to retrieve concentration report for the countries[" + allowedCountries
                    + "], returning null object", ex);
            return null;
        }

    }

    public OBReport[] getAllActiveReportList(int minReportMasterId, int maxReportMasterId) {
        logger.info("Entering method getAllActiveReportList");

        String sql = "SELECT REPORT_TEMPLATE_ID, REPORT_MASTER_ID, TEMPLATE_NAME, FILE_TYPE "
                + " FROM CMS_REPORT_TEMPLATE WHERE STATUS = 'ACTIVE' "
                + " AND REPORT_MASTER_ID >= ? AND REPORT_MASTER_ID <= ? " + " ORDER BY REPORT_MASTER_ID ";
        logger.debug("getAllActiveReportList SQL STMT: " + sql);

        try {
            List reportList = getJdbcTemplate().query(sql,
                    new Object[]{new Integer(minReportMasterId), new Integer(maxReportMasterId)}, new RowMapper() {

                        public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                            OBReport report = new OBReport();
                            report.setReportId(rs.getLong(1));
                            report.setReportMasterId(rs.getString(2));
                            report.setFileName(rs.getString(3));
                            setFileType(report, rs.getString(4));

                            return report;
                        }
                    });
            return (OBReport[]) reportList.toArray(new OBReport[0]);
        }
        catch (DataAccessException ex) {
            logger.warn("failed to retrieve active report list for report master id between [" + minReportMasterId
                    + "] and [" + maxReportMasterId + "], returning null object", ex);
            return null;
        }

    }

    /**
     * Set the file type based on cms_report_template (column: file_type) into
     * OBReport object. If file type is "PDF", OBReport object's "HasPDF" will
     * be set to true and "HasXLS" and "HasWord" will be set to false.
     * <p/>
     * Currently only used by getAllActiveReportList.
     *
     * @param report   The report object to set the file type for
     * @param fileType String containing the file type, i.e. "PDF", "XLS", "DOC"
     */
    private void setFileType(OBReport report, String fileType) {
        report.setHasPDF(false);
        report.setHasXLS(false);
        report.setHasWord(false);

        if (fileType.equals(PDF_FILE_TYPE)) {
            report.setHasPDF(true);
        } else if (fileType.equals(XLS_FILE_TYPE)) {
            report.setHasXLS(true);
        } else if (fileType.equals(DOC_FILE_TYPE)) {
            report.setHasWord(true);
        }
    }

    public String getFormattedBranchList(String centerCode) {
        String sql = "select distinct branch.ENTRY_CODE BRANCH_CODE "
                + " from COMMON_CODE_CATEGORY_ENTRY branch, COMMON_CODE_CATEGORY_ENTRY centre "
                + " where branch.CATEGORY_CODE = '40' and centre.CATEGORY_CODE = 'CENTRE' "
                + " and branch.REF_ENTRY_CODE = centre.ENTRY_CODE " + "and centre.ENTRY_CODE = ? ";

        try {
            return (String) getJdbcTemplate().query(sql, new Object[]{centerCode}, new ResultSetExtractor() {

                public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                    StringBuffer sb = new StringBuffer("(");

                    while (rs.next()) {
                        sb.append("'").append(rs.getString("BRANCH_CODE")).append("',");
                    }

                    sb.setCharAt(sb.length() - 1, ')');
                    return sb.toString();
                }
            });
        }
        catch (DataAccessException ex) {
            logger.warn("failed to retrieve formatted branch list for center code [" + centerCode
                    + "], returning null object", ex);
            return null;
        }
    }

    public String[] getCenterCodes(String country) {
        logger.info("Entering method getCenterCodes");

        String newSQL = " SELECT DISTINCT centre.ENTRY_CODE, centre.ENTRY_NAME "
                + " FROM common_code_category_entry branch, common_code_category_entry centre "
                + " WHERE branch.CATEGORY_CODE = '40' " + " AND centre.CATEGORY_CODE = '"
                + ReportConstants.PARAM_CENTRE_CODE + "'" + " AND branch.REF_ENTRY_CODE = centre.ENTRY_CODE "
                + " order by centre.ENTRY_NAME asc ";

        logger.debug("getCenterCodes SQL STMT: " + newSQL);
        List argList = new ArrayList();
        try {
            List reportList = getJdbcTemplate().query(newSQL, argList.toArray(), new RowMapper() {
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    String centerCode = rs.getString("ENTRY_CODE");
                    return centerCode;
                }
            });
            return (String[]) reportList.toArray(new String[0]);
        }
        catch (DataAccessException ex) {
            logger.warn("failed to retrieve concentration report for the countries[" + country
                    + "], returning null object", ex);
            return null;
        }

    }

    public OBCodeCategoryEntry[] getCentreCodesByTeamID(long teamID, String countryCode) {
        logger.info("Entering method getCentreCodesByTeamID " + teamID + ", countryCode : " + countryCode);

        StringBuffer sqlBuf = new StringBuffer();
        sqlBuf.append("select distinct centre.ENTRY_CODE, centre.ENTRY_NAME  ");
        sqlBuf.append("from COMMON_CODE_CATEGORY_ENTRY branch, COMMON_CODE_CATEGORY_ENTRY centre ");
        sqlBuf.append("where branch.CATEGORY_CODE = '40' and centre.CATEGORY_CODE = 'CENTRE' ");
        if (countryCode != null && !countryCode.equals("")) {
            sqlBuf.append(" and branch.COUNTRY = '" + countryCode + "'");
        }

        sqlBuf.append("  and branch.REF_ENTRY_CODE = centre.ENTRY_CODE ");
        sqlBuf.append("  and  branch.ENTRY_CODE in ( select ORG_GROUP_CODE ");
        sqlBuf.append("from CMS_TEAM_ORG_GROUP_CODE where TEAM_ID = ? ) ");
        sqlBuf.append(" order by centre. ENTRY_NAME asc  ");

        String sql = sqlBuf.toString();
        logger.debug("getCentreCodesByTeamID SQL STMT: " + sql);
        try {
            List reportList = getJdbcTemplate().query(sql, new Object[]{new Long(teamID)}, new RowMapper() {
                public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                    OBCodeCategoryEntry obj = new OBCodeCategoryEntry();
                    obj.setEntryCode(rs.getString("ENTRY_CODE"));
                    obj.setEntryName(rs.getString("ENTRY_NAME"));
                    return obj;
                }
            });
            return (OBCodeCategoryEntry[]) reportList.toArray(new OBCodeCategoryEntry[0]);
        }
        catch (DataAccessException ex) {
            logger.warn("failed to retrieve getCenterCodesByTeamID  for the teamID [" + teamID
                    + "], returning null object", ex);
            return null;
        }
    }

    /**
     * retrieves all Group Exposure Reports  description filtered by country
     *
     * @param countryName
     * @return OBReport
     */
    public OBReport[] getGroupExposureReportsByCountryAndDate(String countryName, String[] orgCodes, Date reportDate, String[] reportCategoryId) {
        String reportDateStr = DB2DateConverter.getDateAsString(reportDate);
        String db2ReportAppendDate = " DATE('" + reportDateStr + "') ";

        boolean hasOrgMap = hasCountryOrgMap(countryName);
        StringBuffer sqlBuf = new StringBuffer();
        List argList = new ArrayList();

//        retrieve all reports for the selected date
        sqlBuf.append(LMS_GRP_REPORT_SQL);
        sqlBuf.append("AND REPORT.FREQUENCY = 'Daily'  \n");
        sqlBuf.append("AND DATE(REPORT.REPORT_DATE) =  ");
        sqlBuf.append(db2ReportAppendDate + "\n");
        appendReportCategoryIdFilter(reportCategoryId, sqlBuf, argList);
        sqlBuf.append("\n AND REPORT.SCOPE_CODE = ? ");
        argList.add(countryName);

        if (hasOrgMap) {
            appendOrgFilter(orgCodes, sqlBuf, argList);
        }

        sqlBuf.append("\n ORDER BY MASTER.MIS_REPORT_ID ");
        String sql = sqlBuf.toString();
        logger.debug(" getGroupExposureReportsByCountryAndDate SQL : " + sql);

        List reportList = getJdbcTemplate().query(sql, argList.toArray(), new RowMapper() {
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                OBReport report = new OBReport();
                report.setReportId(rs.getLong(1));
                report.setTitle(rs.getString(2));
                report.setFileName(rs.getString(3));
                report.setHasPDF(checkHasFile(rs.getString(4)));
                report.setHasXLS(checkHasFile(rs.getString(5)));
                report.setHasWord(checkHasFile(rs.getString(6)));
                report.setMISReportNumber(rs.getString(7));
                report.setFolderPath(rs.getString(8));
                report.setPdfName(rs.getString(9));
                report.setXlsName(rs.getString(10));
                report.setWordName(rs.getString(11));
                report.setGroupID(rs.getString(12)); 
				return report;
			}
		});
           
        return (OBReport[]) reportList.toArray(new OBReport[0]);
    }  

}
