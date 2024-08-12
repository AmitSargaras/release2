package com.integrosys.cms.ui.report;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.common.StartupDeleteSession;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.batch.reports.IReportRequestManager;
import com.integrosys.cms.batch.reports.ReportConstants;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.diaryitem.DiaryItemHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class ReportServlet extends HttpServlet {

    private static final long serialVersionUID = 4519309344609413453L;

    private IReportRequestManager reportRequestManager;

    private StartupDeleteSession startupDeleteSession;

    public void setStartupDeleteSession(StartupDeleteSession startupDeleteSession) {
        this.startupDeleteSession = startupDeleteSession;
    }

    public StartupDeleteSession getStartupDeleteSession() {
        return startupDeleteSession;
    }

    public IReportRequestManager getReportRequestManager() {
        return reportRequestManager;
    }

    public void setReportRequestManager(IReportRequestManager reportRequestManager) {
        this.reportRequestManager = reportRequestManager;
    }

    private final static String DIARY_REPORT_ID = "147";

    private final static String DEFICIENY_REPORT_ID = "128";

    private final static String BL_DISCLAIMER_REPORT_NUMBER = "ODBL001";

    private WebApplicationContext webApplicationContext;

    public void init() {
        webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        webApplicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(this,
                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);

        DefaultLogger.debug(this,"[StartupDeleteSession] Calling ...");
        boolean clusteringEnabled = PropertyManager.getBoolean("clustering.enabled", false);
        if (!clusteringEnabled) {
            getStartupDeleteSession().clearSession();
        } else {
        	DefaultLogger.debug(this,"[StartupDeleteSession] Clustered Environment, bypass session deletion...");
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    	DefaultLogger.debug(this,"entering ReportServlet.doGet");
        String flag = request.getParameter("flag");
        if (flag.equals("view")) {

            HttpSession session = request.getSession(false);
            ITeam userTeam = (ITeam) session.getAttribute(ICommonEventConstant.GLOBAL_SCOPE + "."
                    + IGlobalConstant.USER_TEAM);
            ICommonUser user = (ICommonUser) session.getAttribute(ICommonEventConstant.GLOBAL_SCOPE + "."
                    + IGlobalConstant.USER);

            boolean isOnlineReport = false;
            long reportId = ICMSConstant.LONG_INVALID_VALUE;

            String reportName = "";
            String path = "";
            String save = "";
            String event = request.getParameter("event");
            if ((event != null) && (event.length() > 0)) {
                String[] reportInfo = null;
                if (event.equals(ReportConstants.EVENT_GEN_DIARY)) {
                    isOnlineReport = true;
                    try {
                        reportInfo = generateDiaryRpt(request, userTeam, user);
                        reportId = Long.parseLong(DIARY_REPORT_ID);
                    }
                    catch (Exception ex) {
                        DefaultLogger.error(this, "failed to generate diary report", ex);
                        return;
                    }
                } else if (event.equals(ReportConstants.EVENT_GEN_DEFICIANCY)) {
                    isOnlineReport = true;
                    try {
                        reportInfo = generateDeficiencyRpt(request, userTeam, user);
                        reportId = Long.parseLong(DEFICIENY_REPORT_ID);
                    }
                    catch (Exception ex) {
                        DefaultLogger.error(this, "failed to generate deficiency report", ex);
                        return;
                    }
                } else if (event.equals(ReportConstants.EVENT_GEN_MIS_RPT)) {
                    isOnlineReport = true;
                    // String strReportId = request.getParameter("reportId");
                    String strReportNumber = request.getParameter("reportId");
                    String strDate = request.getParameter("param_date");
                    String country = request.getParameter("orig_country");

                    if ((strDate == null) || (country == null)) {
                        printMessage(response, "Report Date or country is null...");
                        return;
                    }
                    Date reportDate = DateUtil.convertDate(strDate);
                    try {
                        reportId = getReportMasterID(strReportNumber);
                        reportInfo = generateMISReport(request, String.valueOf(reportId), reportDate, country, user);

                        // reportId = Long.parseLong(strReportId);
                    }
                    catch (Exception ex) {
                        DefaultLogger.error(this, "failed to generete mis report, report id [" + reportId + "]", ex);
                        return;
                    }
                } else if (event.equals(ReportConstants.EVENT_GEN_BL_DISCLAIMER)) {
                    isOnlineReport = true;
                    String country = request.getParameter("orig_country");
                    String paramBuildupID = request.getParameter("buildupID");
                    Date reportDate = DateUtil.getDate();

                    try {
                        // buildup id should never be invalid since its
                        // generated and passed by system
                        long buildupID = Long.parseLong(paramBuildupID);
                        reportInfo = generateBLDisclaimerReport(request, BL_DISCLAIMER_REPORT_NUMBER, reportDate,
                                country, user, buildupID);
                        reportId = getReportMasterID(BL_DISCLAIMER_REPORT_NUMBER);

                    }
                    catch (Exception ex) {
                        DefaultLogger.error(this, "failed to generate briding loan disclaimer report, buildupID ["
                                + paramBuildupID + "]", ex);
                        return;
                    }
                }

                path = reportInfo[0];
                reportName = reportInfo[1];
                save = reportInfo[2];
                if (reportName == null) {
                    DefaultLogger.warn(this, "failed to generate online report, reportId [" + reportId + "]");
                    return;
                }
            }

            if (flag == null) {
                printMessage(response, "Flag is null = Please login to CMS to access the report... ");
                return;
            }

            if (session == null) {
                printMessage(response, "Session is null - Please login to CMS before trying to access the report... ");
                return;
            }

            if (user == null) {
                printMessage(response, "FAP - Please login or you don't have the access to the repot... ");
                return;
            }

            if (!isOnlineReport) {
                String strReportId = request.getParameter("reportId");

                if (strReportId == null) {
                    printMessage(response, "Report Id is null - Sorry, you don't have the access to this report... ");
                    return;
                }

                try {
                    reportId = Long.parseLong(strReportId);
                }
                catch (Exception e) {
                    printMessage(response,
                            "Report ID is not Long type - Sorry you don't have the access to this report... ");
                    return;
                }

                String scope = request.getParameter("scope");
                if (scope == null) {
                    printMessage(response, "SCOPE is null - Sorry you don't have the access to this report... ");
                    return;
                }

                if (scope.equals("MIS") || scope.equals("SYS")) {
                    DefaultLogger.debug(this, "<<<< At Scope MIS or SYS... ");
                    if (reportId != ICMSConstant.LONG_INVALID_VALUE) {
                        String country = request.getParameter("country");
                        if (country == null) {
                            printMessage(response,
                                    "MIS scope with country code is null -Please access the report using the CMS system.. ");
                            return;
                        }
                        if (!hasCountryAccess(userTeam, country)) {
                            printMessage(response,
                                    "Country not match - Sorry, you don't have the access right to this report.. ");
                            return;
                        }
                    }
                    try {
                        if (scope.equals("MIS")) {
                            if (!hasTeamAccessToMIS(userTeam, user)) {
                                printMessage(response, "Sorry, your team don't have the access to MIS report... ");
                                return;
                            }
                        } else if (scope.equals("SYS")) {
                            if (!hasTeamAccessToSYS(userTeam, user)) {
                                printMessage(response, "Sorry, your team don't have the access to System report... ");
                                return;
                            }
                        }
                    }
                    catch (Exception ex) {
                        DefaultLogger.error(this, "user or team does not have the access to the report, scope ["
                                + scope + "]", ex);
                        return;
                    }
                } else if (scope.equals("CON")) {
                    DefaultLogger.debug(this, "<<< At Scope CON... ");
                    try {
                        if (!hasTeamAccessToCON(userTeam, user)) {
                            printMessage(response, "Sorry, your team don't have the access to CON report... ");
                            return;
                        }
                    }
                    catch (Exception ex) {
                        DefaultLogger.error(this, "user or team does not have the access to the report, scope ["
                                + scope + "]", ex);
                        return;
                    }
                }
            }
            DefaultLogger.debug(this, "begin service...");

            // persist report request to DB
            try {
                long sid = createViewReportRequest(reportId, user.getUserID());
                DefaultLogger.debug(this, "<<< report id: " + reportId + "\tsid: " + sid);
                if (!isOnlineReport) {
                    reportName = request.getParameter("name");
                    path = request.getParameter("path");
                    save = request.getParameter("save");
                }

                DefaultLogger.debug(this, "report name: [" + reportName + "] path: [" + path + "]");

                String urlStr = getReportURL() + "/GetReport.image?name=" + reportName + "&path=" + path + "&sid="
                        + sid + "&save=" + save;
                DefaultLogger.debug(this, "urlStr: " + urlStr);

                response.sendRedirect(urlStr);

            }
            catch (Exception ex) {
                ex.printStackTrace();
                DefaultLogger.error(this, "<<<<<<< exception caught at createViewReportRequest or redirect... ");
            }
        } else if (flag.equals("check")) {
            DefaultLogger.debug(this, "<<<< inside flag is check.. ");
            String strSid = request.getParameter("sid");
            if (strSid == null) {
                printMessage(response, "Sid is null - Sorry you have no access to this report.. ");
                return;
            }
            long sid = 0;
            try {
                sid = Long.parseLong(strSid);
            }
            catch (Exception ex) {
                printMessage(response, "<<< exception caught - SID is not number.. ");
                return;
            }
            DefaultLogger.debug(this, "<<< checked - received sid: " + sid);
            try {
                if (deleteViewReportRequest(sid)) {
                    DefaultLogger.debug(this, "<<< has access ot this report - SID: " + sid);
                    printMessage(response, String.valueOf(sid * 3.142857142857142857 + 2.302585092994));
                } else {
                    DefaultLogger.debug(this, "<<< No access ot this report - SID: " + sid);
                    printMessage(response, "<<< No access to this report - SID: " + sid);
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
                DefaultLogger.error(this, "<<<< exception caught at deleteViewReportRequest .. ");
            }
        }
    }

    private boolean deleteViewReportRequest(long sid) throws Exception {
        return getReportRequestManager().deleteViewReportRequest(sid);
    }

    private long createViewReportRequest(long reportId, long userID) throws Exception {
        return getReportRequestManager().createViewReportRequest(reportId, userID);
    }

    private boolean hasTeamAccessToMIS(ITeam userTeam, ICommonUser user) throws Exception {
        long userRole = getUserRoleByTeamAndUser(userTeam, user);
        return getReportRequestManager().hasTeamAccessToMIS(userRole);
    }

    private boolean hasTeamAccessToSYS(ITeam userTeam, ICommonUser user) throws Exception {
        long userRole = getUserRoleByTeamAndUser(userTeam, user);
        return getReportRequestManager().hasTeamAccessToSYS(userRole);
    }

    private boolean hasTeamAccessToCON(ITeam userTeam, ICommonUser user) throws Exception {
        long userRole = getUserRoleByTeamAndUser(userTeam, user);
        return getReportRequestManager().hasTeamAccessToCON(userRole);
    }

    private long getUserRoleByTeamAndUser(ITeam userTeam, ICommonUser user) {
        long userRole = 0;
        for (int i = 0; i < userTeam.getTeamMemberships().length; i++) {
            for (int j = 0; j < userTeam.getTeamMemberships()[i].getTeamMembers().length; j++) {
                if (userTeam.getTeamMemberships()[i].getTeamMembers()[j].getTeamMemberUser().getUserID() == user
                        .getUserID()) {
                    userRole = userTeam.getTeamMemberships()[i].getTeamTypeMembership().getMembershipID();
                }
            }
        }
        return userRole;
    }

    private boolean hasCountryAccess(ITeam team, String country) {
        String[] countryList = team.getCountryCodes();

        for (int i = 0; i < countryList.length; i++) {
            if (countryList[i].equals(country)) {
                return true;
            }
        }
        return false;
    }

    private void printMessage(HttpServletResponse response, String message) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(message);
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response);
    }

    private String getReportURL() throws Exception {
        return PropertyUtil.getInstance("/reports.properties").getProperty(ICMSUIConstant.CMS_PUBLISH_REPORT_URL);
    }

    /**
     * Helper method to generate online diary report
     */
    private String[] generateDiaryRpt(HttpServletRequest request, ITeam team, ICommonUser user) throws Exception {
        DefaultLogger.debug(this, "<<< entering method generateDiaryRpt... ");
        HttpSession session = request.getSession(false);
        long teamTypeId = team.getTeamType().getTeamTypeID();

        String countryFilter = request.getParameter("countryFilter");
        String sessCountryFilter = (String) session
                .getAttribute("com.integrosys.cms.ui.diaryitem.DiaryItemAction.session.countryFilter");

        String searchLeID = request.getParameter("searchLeID");
        String searchCustomerName = request.getParameter("searchCustomerName");

        long lLegalID = ICMSConstant.LONG_INVALID_VALUE;

        try {
            lLegalID = Long.parseLong(searchLeID);
        }
        catch (Exception e) {
            lLegalID = ICMSConstant.LONG_INVALID_VALUE;
        }

        String[] allowedCountries;

        if (countryFilter != null) {
            allowedCountries = new String[]{countryFilter};
        } else if (sessCountryFilter != null) {
            allowedCountries = new String[]{sessCountryFilter};
        } else {
            allowedCountries = team.getCountryCodes();
        }

        String customerIndex = request.getParameter("customerIndex");

        if ((customerIndex != null) && DiaryItemHelper.isNull(searchCustomerName)) {
            searchCustomerName = customerIndex;
        }

        DefaultLogger.debug(this, "<<<<<< customerIndex: " + customerIndex + "\tsearchCustomerName:"
                + searchCustomerName);

        String exportFormat = getReportType(ReportConstants.DIARY_REPORT_TYPE);

        Date startExpDate = null;
        String strStartExpDate = request.getParameter("startExpiryDate");
        if (strStartExpDate != null) {
            startExpDate = DateUtil.convertDate(strStartExpDate);
        }

        Date endExpDate = null;
        String strEndExpDate = request.getParameter("endExpiryDate");
        if (strEndExpDate != null) {
            endExpDate = DateUtil.convertDate(strEndExpDate);
        }

        String reportName = getReportRequestManager().generateOnlineReport(DIARY_REPORT_ID, startExpDate, endExpDate,
                teamTypeId, allowedCountries, exportFormat, lLegalID, searchCustomerName, user.getLoginID());

        return (new String[]{"TEMP_REPORT", reportName, "ListofExpiredDiaryItems." + exportFormat.toLowerCase()});
    }

    /**
     * Helper method to generate online print deficiency report
     */
    private String[] generateDeficiencyRpt(HttpServletRequest request, ITeam team, ICommonUser user) throws Exception {
        HttpSession session = request.getSession(false);

        String[] allowedCountries = null;
        if (team != null) {
            allowedCountries = team.getCountryCodes();
        }

        ILimitProfile limitProfileOB = (ILimitProfile) session.getAttribute(ICommonEventConstant.GLOBAL_SCOPE + "."
                + IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
        DefaultLogger.info(this, "Generating deficiencey report for limit profile [" + limitProfileOB.getBCAReference()
                + "], key [" + limitProfileOB.getLimitProfileID() + "]");

        String exportFormat = getReportType(ReportConstants.DEFICIENY_REPORT_TYPE);
        String reportName = getReportRequestManager().generateOnlineReport(DEFICIENY_REPORT_ID, DateUtil.getDate(),
                limitProfileOB.getLimitProfileID(), exportFormat, allowedCountries, user.getLoginID());

        return (new String[]{"TEMP_REPORT", reportName,
                "ListOfCCandSecurityDeficientDocuments." + exportFormat.toLowerCase()});
    }

    /**
     * Helper method to generate online MIS report (by country and date)
     */
    private String[] generateMISReport(HttpServletRequest request, String reportNumber, Date date, String country,
                                       ICommonUser user) throws Exception {
        String exportFormat = getReportType(ReportConstants.MIS_REPORT_TYPE);

        String reportName = getReportRequestManager().generateOnlineReport(reportNumber, date, country, exportFormat,
                user.getLoginID());

        return (new String[]{"TEMP_REPORT", reportName, reportName + "." + exportFormat.toLowerCase()});
    }

    private String[] generateBLDisclaimerReport(HttpServletRequest request, String reportNumber, Date date,
                                                String country, ICommonUser user, long buildupID) throws Exception {
        String exportFormat = getReportType(ReportConstants.BL_DISCLAIMER_REPORT_TYPE);
        String reportName = reportRequestManager.generateOnlineReport(reportNumber, date, country, buildupID,
                exportFormat, user.getLoginID());
        return (new String[]{"TEMP_REPORT", reportName, reportName + "." + exportFormat.toLowerCase()});
    }

    private long getReportMasterID(String reportNumber) throws Exception {

        long reportMasterID = getReportRequestManager().getReportMasterID(reportNumber);
        DefaultLogger.debug("ReportServlet", "Report Master ID: " + reportMasterID + " (Given ReportNumber: "
                + reportNumber + ")");
        return reportMasterID;
    }

    private static String getReportType(String reportID) {

        String format = PropertyManager.getValue(reportID);
        if ((format == null) || (format.trim().length() == ICMSConstant.LONG_INVALID_VALUE)) {
            throw new RuntimeException("Error loading report type from property file");
        }

        DefaultLogger.debug("", "ReportFormat: " + format);

        return format;
    }
}
