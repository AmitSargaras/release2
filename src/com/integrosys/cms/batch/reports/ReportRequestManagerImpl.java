/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/ReportRequestManagerImpl.java,v 1.5 2006/09/07 05:22:34 hshii Exp $
 */

package com.integrosys.cms.batch.reports;

import com.integrosys.component.commondata.app.bus.OBCodeCategoryEntry;

import java.util.Collection;
import java.util.Date;
import java.rmi.RemoteException;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a diary item
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/07 05:22:34 $ Tag: $Name: $
 */

public class ReportRequestManagerImpl implements IReportRequestManager {

	private IReportDao reportDao;

	private IReportSchedulerDAO reportSchedulerDao;

	/**
	 * @param reportSchedulerDao the reportSchedulerDao to set
	 */
	public void setReportSchedulerDao(IReportSchedulerDAO reportSchedulerDao) {
		this.reportSchedulerDao = reportSchedulerDao;
	}

	/**
	 * @return the reportSchedulerDao
	 */
	public IReportSchedulerDAO getReportSchedulerDao() {
		return reportSchedulerDao;
	}

	/**
	 * @param reportDao the reportDao to set
	 */
	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}

	/**
	 * @return the reportDao
	 */
	public IReportDao getReportDao() {
		return reportDao;
	}

	/**
	 * retrieves all MIS reports description filtered by country
	 * @param countryName
	 * @return OBReport[]
	 */
	public OBReport[] getMISReportsByCountryAndDate(String countryName, String[] orgCodes, Date reportDate,
			String[] reportCategoryId, String centreCode) throws ReportException {
		return getReportSchedulerDao().getMISReportsByCountryAndDate(countryName, orgCodes, reportDate,
				reportCategoryId,centreCode);
	}

	/**
	 * retrieves a report by its id and type
	 * @param reportId
	 * @return OBReport
	 */
	public OBReport getReport(long reportId, String reportType) throws ReportException {
		return getReportSchedulerDao().getReport(reportId, reportType);
	}

	/**
	 * Retrieve concentration report list filter by country and team type
	 * @param reportDate
	 * @param reportCategoryId
	 * @param allowedCountries
	 * @param teamTypeID
	 * @return OBReport[]
	 */
	public OBReport[] getConcentrationReportList(Date reportDate, String reportCategoryId, String[] allowedCountries,
			long teamTypeID) throws ReportException {
		return getReportSchedulerDao().getConcentrationReportList(reportDate, reportCategoryId, allowedCountries);
	}

	
	/**
	 * Retrieve what if analysis report list filter by country and team type
	 * @param reportDate
	 * @param reportCategoryId
	 * @param allowedCountries
	 * @param teamTypeID
	 * @return OBReport[]
	 */
	public OBReport[] getWhatIfCondReportList(Date reportDate, String reportCategoryId, String[] allowedCountries,
			long teamTypeID) throws ReportException {
		return getReportSchedulerDao().getAllWhatIfCondReportList(reportDate, reportCategoryId, allowedCountries);
	}
	
	/**
	 * Generate Online Report on demand
	 * @param reportMasterID
	 * @param reportDate
	 * @param limitProfileID
	 * @param exportFormat
	 * @param allowedCountries
	 * @param loginID
	 * @return String
	 */
	public String generateOnlineReport(String reportMasterID, Date reportDate, long limitProfileID,
			String exportFormat, String[] allowedCountries, String loginID) throws ReportException {
		
		return (new OnlineReportGenerator()).generateReport(reportMasterID, reportDate, limitProfileID, exportFormat,
				allowedCountries, loginID);
	}

	/**
	 * Generate Online Report on demand
	 * @param reportMasterID
	 * @param startExpDate
	 * @param endExpDate
	 * @param teamTypeMembershipId
	 * @param allowedCountries
	 * @param exportFormat
	 * @param leID
	 * @param customerIndex
	 * @param loginID
	 * @return String
	 */
	public String generateOnlineReport(String reportMasterID, Date startExpDate, Date endExpDate,
			long teamTypeMembershipId, String[] allowedCountries, String exportFormat, long leID, String customerIndex,
			String loginID) throws ReportException {
		return (new OnlineReportGenerator()).generateReport(reportMasterID, startExpDate, endExpDate, teamTypeMembershipId,
				allowedCountries, exportFormat, leID, customerIndex, loginID);
	}

	/**
	 * Generate Online Report on demand
	 * @param reportMasterID
	 * @param reportDate
	 * @param country
	 * @param exportFormat
	 * @param loginID
	 * @return String
	 */
	public String generateOnlineReport(String reportMasterID, Date reportDate, String country, String exportFormat,
			String loginID) throws ReportException {
		return (new OnlineReportGenerator()).generateReport(reportMasterID, reportDate, country, exportFormat, loginID);
	}

	/**
	 * Generate Online Report on demand - This method is used to generate
	 * Disclaimer Letter for Bridging Loan
	 * @param reportNumber
	 * @param reportDate
	 * @param country
	 * @param buildupID
	 * @param exportFormat
	 * @return String
	 */
	public String generateOnlineReport(String reportNumber, Date reportDate, String country, long buildupID,
			String exportFormat, String loginID) throws ReportException {
		return (new OnlineReportGenerator())
				.generateReport(reportNumber, reportDate, country, buildupID, exportFormat, loginID);
	}

	/**
	 * Check whether the teamTypeMembershipID has access to MIS Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToMIS(long teamTypeMembershipID) throws ReportException {
		return getReportSchedulerDao().getIsMISReportAccessibleByTeam(teamTypeMembershipID);
	}

	/**
	 * Check whether the teamTypeMembershipID has access to SYS Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToSYS(long teamTypeMembershipID) throws ReportException {
		return getReportSchedulerDao().getIsSysReportAccessibleByTeam(teamTypeMembershipID);
	}

	/**
	 * Check whether the teamTypeMembershipID has access to Concentration Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToCON(long teamTypeMembershipID) throws ReportException {
		return getReportSchedulerDao().getIsConcReportAccessibleByTeam(teamTypeMembershipID);
	}

	public long createViewReportRequest(long reportId, long userID) throws ReportException {
		return getReportSchedulerDao().createViewReportRequest(reportId, userID);
	}

	public boolean deleteViewReportRequest(long sid) throws ReportException {
		return getReportSchedulerDao().deleteViewReportRequest(sid);
	}

	public long getReportMasterID(String reportNumber) throws ReportException {
		return getReportSchedulerDao().getReportMasterID(reportNumber);
	}

	public IReportRequest createReportRequest(IReportRequest iReportRequest) throws ReportException {
		return getReportDao().createReportRequest(iReportRequest);
	}

	public OBReport getReportDetailsByReportID(String MISReportID) throws ReportException {
		return getReportSchedulerDao().getReportDetailsByReportID(MISReportID);
	}

	public IReportRequest getReportRequest(long requestID) throws ReportException {
		return getReportDao().getReportRequestByRequestID(new Long(requestID));
	}

	public Collection getReportRequest(String status) throws ReportException {
		return getReportDao().getReportRequestByStatus(status);
	}

	public IReportRequest updateReportRequest(IReportRequest reportRequest) throws ReportException {
		return getReportDao().updateReportRequest(reportRequest);
	}



    public String[] getCenterCodes(String scope) throws ReportException {
          return getReportSchedulerDao().getCenterCodes(scope);
    }

     public OBReport[] getReportJobsByScope(String scope) throws ReportException {
          return getReportSchedulerDao().getReportJobsByScope(scope);
    }

     public OBCodeCategoryEntry[]  getCentreCodesByTeamID(long teamID,String countryCode) throws ReportException{
         return getReportSchedulerDao().getCentreCodesByTeamID(teamID,countryCode);
     }

    /**
     * retrieves all Group Exposure  reports description filtered by country
     * @param countryName
     * @return OBReport[]
     */
    public OBReport[] getGroupExposureReportsByCountryAndDate(String countryName, String[] orgCodes, Date reportDate, String[] reportCategoryId) throws ReportException {
         return getReportSchedulerDao().getGroupExposureReportsByCountryAndDate(countryName, orgCodes, reportDate, reportCategoryId);
    }
}