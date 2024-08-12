/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/IReportRequestManager.java,v 1.5 2006/09/07 05:22:34 hshii Exp $
 */

package com.integrosys.cms.batch.reports;

import com.integrosys.component.commondata.app.bus.OBCodeCategoryEntry;

import java.util.Collection;
import java.util.Date;

/**
 * This interface defines the list of attributes that will be available to the
 * generation of a diary item
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/07 05:22:34 $ Tag: $Name: $
 */

public interface IReportRequestManager {

	/**
	 * retrieves all MIS reports description filtered by country
	 * @param countryName
	 * @return OBReport[]
	 */
	public OBReport[] getMISReportsByCountryAndDate(String countryName, String[] orgCodes, Date reportDate,
			String[] reportCategoryId, String centreCode) throws ReportException;

	/**
	 * retrieves a report by its id and type
	 * @param reportId
	 * @return OBReport
	 */
	public OBReport getReport(long reportId, String reportType) throws ReportException;

	/**
	 * Retrieve concentration report list filter by country and team type
	 * @param reportDate
	 * @param reportCategoryId
	 * @param allowedCountries
	 * @param teamTypeID
	 * @return OBReport[]
	 */
	public OBReport[] getConcentrationReportList(Date reportDate, String reportCategoryId, String[] allowedCountries,
			long teamTypeID) throws ReportException;

	/**
	 * Retrieve what if analysis report list filter by country and team type
	 * @param reportDate
	 * @param reportCategoryId
	 * @param allowedCountries
	 * @param teamTypeID
	 * @return OBReport[]
	 */
	public OBReport[] getWhatIfCondReportList(Date reportDate, String reportCategoryId, String[] allowedCountries,
			long teamTypeID) throws ReportException;
			
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
			String exportFormat, String[] allowedCountries, String loginID) throws ReportException;

	/**
	 * Generate Online Report on demand
	 * @param reportMasterID
	 * @param startExpDate
	 * @param endExpDate
	 * @param team_type_membership_id
	 * @param allowedCountries
	 * @param exportFormat
	 * @param leID
	 * @param customerIndex
	 * @param loginID
	 * @return String
	 */
	public String generateOnlineReport(String reportMasterID, Date startExpDate, Date endExpDate,
			long team_type_membership_id, String[] allowedCountries, String exportFormat, long leID,
			String customerIndex, String loginID) throws ReportException;

	/**
	 * Generate Online Report on demand
	 * @param reportNumber
	 * @param reportDate
	 * @param country
	 * @param exportFormat
	 * @param loginID
	 * @return String
	 */
	public String generateOnlineReport(String reportNumber, Date reportDate, String country, String exportFormat,
			String loginID) throws ReportException;

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
			String exportFormat, String loginID) throws ReportException;

	/**
	 * Check whether the teamTypeMembershipID has access to MIS Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToMIS(long teamTypeMembershipID) throws ReportException;

	/**
	 * Check whether the teamTypeMembershipID has access to MIS Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToSYS(long teamTypeMembershipID) throws ReportException;

	/**
	 * Check whether the teamTypeMembershipID has access to Concentration Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToCON(long teamTypeMembershipID) throws ReportException;

	public long createViewReportRequest(long reportId, long userID) throws ReportException;

	public boolean deleteViewReportRequest(long sid) throws ReportException;

	public long getReportMasterID(String reportNumber) throws ReportException;

	public IReportRequest createReportRequest(IReportRequest IReportRequest) throws ReportException;

	public OBReport getReportDetailsByReportID(String MISReportID) throws ReportException;

	public IReportRequest updateReportRequest(IReportRequest obj) throws ReportException;

	public IReportRequest getReportRequest(long requestID) throws ReportException;

	public Collection getReportRequest(String status) throws ReportException;


    
    public String[]  getCenterCodes(String status) throws ReportException;

    public OBReport[]  getReportJobsByScope(String scope) throws ReportException;

    public OBCodeCategoryEntry[]  getCentreCodesByTeamID(long teamID,String countryCode) throws ReportException;

    /**
     * retrieves all Group Exposure  reports description filtered by country
     * @param countryName
     * @return OBReport[]
     */
    public OBReport[] getGroupExposureReportsByCountryAndDate(String countryName, String[] orgCodes, Date reportDate, String[] reportCategoryId)
        throws ReportException;
}