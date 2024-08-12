package com.integrosys.cms.batch.reports;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJBObject;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description:
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.6 $
 * @since $Date: 2006/09/07 05:22:34 $ Tag: $Name: $
 */

public interface SBReportRequestManager extends EJBObject {
	public IReportRequest createReportRequest(IReportRequest req) throws Exception, RemoteException;

	public IReportRequest updateReportRequest(IReportRequest obj) throws Exception, RemoteException;

	public IReportRequest getReportRequest(long requestID) throws Exception, RemoteException;

	public Collection getReportRequest(String status) throws Exception, RemoteException;

	/**
	 * retrieves all MIS reports description filtered by country
	 * @param countryName
	 * @param orgCodes
	 * @param reportDate
	 * @param reportCategoryId
	 * @return OBReport[]
	 */
	public OBReport[] getMISReportsByCountryAndDate(String countryName, String[] orgCodes, Date reportDate,
			String[] reportCategoryId, String centreCode) throws ReportException, RemoteException;

	/**
	 * retrieves a report by its id and type
	 * @param reportId
	 * @return OBReport
	 */
	public OBReport getReport(long reportId, String reportType) throws ReportException, RemoteException;

	/**
	 * Retrieve concentration report list filter by country and team type
	 * @param reportDate
	 * @param reportCategoryId
	 * @param allowedCountries
	 * @param teamTypeID
	 * @return OBReport[]
	 */
	public OBReport[] getConcentrationReportList(Date reportDate, String reportCategoryId, String[] allowedCountries,
			long teamTypeID) throws ReportException, RemoteException;

	/**
	 * Retrieves all active reports that are being used in the system
	 * @param minReportMasterId the report master id to start from (min=1, as of
	 *        R1.4.1)
	 * @param maxReportMasterId the report master id to end at (max=150, as of
	 *        R1.4.1)
	 * @return OBReport[] all active reports whose report id is >=
	 *         minReportMasterId and <= maxReportMasterId
	 */
	public OBReport[] getAllActiveReportList(int minReportMasterId, int maxReportMasterId) throws RemoteException;

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
			String exportFormat, String[] allowedCountries, String loginID) throws ReportException, RemoteException;

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
			String customerIndex, String loginID) throws ReportException, RemoteException;

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
			String loginID) throws ReportException, RemoteException;

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
			String exportFormat, String loginID) throws ReportException, RemoteException;

	/**
	 * Check whether the teamTypeMembershipID has access to MIS Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToMIS(long teamTypeMembershipID) throws ReportException, RemoteException;

	/**
	 * Check whether the teamTypeMembershipID has access to MIS Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToSYS(long teamTypeMembershipID) throws ReportException, RemoteException;

	/**
	 * Check whether the teamTypeMembershipID has access to Concentration Report
	 * @param teamTypeMembershipID
	 * @return boolean
	 */
	public boolean hasTeamAccessToCON(long teamTypeMembershipID) throws ReportException, RemoteException;

	public long createViewReportRequest(long reportId, long userID) throws ReportException, RemoteException;

	public boolean deleteViewReportRequest(long sid) throws ReportException, RemoteException;

	public long getReportMasterID(String reportNumber) throws ReportException, RemoteException;

	public OBReport getReportDetailsByReportID(String MISReportID) throws ReportException, RemoteException;

    public String[]  getCenterCodes(String status) throws ReportException, RemoteException;
    
    public OBReport[] getGroupExposureReportsByCountryAndDate(String countryName, String[] orgCodes, Date reportDate, String[] reportCategoryId) throws ReportException, RemoteException;

}
