package com.integrosys.cms.batch.reports;

import com.integrosys.component.commondata.app.bus.OBCodeCategoryEntry;

import java.util.Date;
import java.util.List;
import java.util.Collection;

/**
 * Jdbc Dao to interface with Report repositary.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface IReportSchedulerDAO {
	/**
	 * Given the collateral subtype code, retrieve the collateral subtype full
	 * name.
	 * 
	 * @param code collateral subtype code
	 * @return correspodning subtype name
	 */
	public String getCommoditySubType(String code);

	/**
	 * Retrieve reports that are generated between the dates supplied.
	 * 
	 * @param startDate start date of reports generated (inclusive)
	 * @param endDate end date of reports generated (inclusive)
	 * @return generated report between the dates supplied
	 */
	public OBReport[] getReportsPriorTo(Date startDate, Date endDate);

	/**
	 * Retrieve reports that are generated on the report date supplied, and
	 * fulfill all the conditions, ie, country code, organisation code
	 * (branches), and report category id
	 * 
	 * @param countryCode country code
	 * @param orgCodes organisation codes (branches)
	 * @param reportDate report generated date
	 * @param reportCategoryId report category id
	 * @return reports generated fulfull the criteria supplied
	 */
	public OBReport[] getMISReportsByCountryAndDate(String countryCode, String[] orgCodes, Date reportDate,
			String[] reportCategoryId, String centreCode);

	/**
	 * Query whether the master report (matching the report master id supplied)
	 * should be generated for the country code supplied.
	 * 
	 * @param reportMasterId report master id
	 * @param country country code
	 * @return true if master report should be generated for the country
	 */
	public boolean getIsReportShouldGeneratedByCountry(String reportMasterId, String country);

	/**
	 * Retrieve stock exchange description given the stock exchange code
	 * 
	 * @param stockExchange stock exchange symbolic code
	 * @return stock exchange description
	 */
	public String getStockExchangeDescription(String stockExchange);

	/**
	 * Retrieve the region name given the region code
	 * 
	 * @param region region code
	 * @return region name match the region code
	 */
	public String getRegionDescription(String region);

	/**
	 * Retrieve list of report with report master id and frequency populated.
	 * 
	 * @param scope scope of the report, MIS, SYS, etc
	 * @return report with master id and frequency
	 */
	public OBReport[] getReportJobsByScope(String scope);

	/**
	 * Retrieve scope of the report master for the given id
	 * 
	 * @param reportMasterId report master id
	 * @return scope of the report master for the supplied id
	 */
	public String getScopeByID(String reportMasterId);

	/**
	 * Retrieve report master generation frequency day(s) for the given id
	 * 
	 * @param reportMasterID report master id
	 * @return generation frequency day(s) for the supplied id
	 */
	public int[] getFrequencyDaysByID(String reportMasterID);

	/**
	 * Retrieve MIS report details for the given id (mis report number), mainly
	 * to use the title mask for display purpose, as well as frequency, scope.
	 * 
	 * @param MISReportID mis report id
	 * @return report details for the supplied id
	 */
	public OBReport getReportDetailsByReportID(String MISReportID);

	/**
	 * Retrieve MIS report config info for the given mis report number and
	 * country.
	 * 
	 * @param reportNumber mis report number
	 * @param country country code
	 * @return MIS report config matched the criteria supplied
	 */
	public OBReportConfig getReportConfigByReportNumber(String reportNumber, String country);

	/**
	 * Retrieve MIS report config info for the given report master id and
	 * country
	 * 
	 * @param reportMasterId report master id
	 * @param country country code
	 * @return report config matched the criteria supplieds
	 */
	public OBReportConfig getReportConfig(String reportMasterId, String country);

	/**
	 * retrieve file type and template name of the report master match the id
	 * supplied
	 * 
	 * @param reportMasterId report master id
	 * @return list of string contains report file type and template name.
	 */
	public List getReportInfoByID(String reportMasterId);

	/**
	 * Retrieve report master id by supplying mis report number
	 * 
	 * @param reportNumber mis report number
	 * @return report master id of master report match the number supplied
	 */
	public long getReportMasterID(String reportNumber) throws ReportException;

	/**
	 * Whether MIS report search is accessible by the team type membership.
	 * 
	 * @param teamTypeMembershipID team type membership id
	 * @return true if the team type membership can access mis report search
	 */
	public boolean getIsMISReportAccessibleByTeam(long teamTypeMembershipID) throws ReportException;

	/**
	 * Remove report request token id.
	 * 
	 * @param sid token id
	 * @return true if deleted rows are more than 0
	 */
	public boolean deleteViewReportRequest(long sid) throws ReportException;

	/**
	 * Create a report request token id
	 * 
	 * @param reportId generated report id
	 * @param userID user id
	 * @return token id generated
	 */
	public long createViewReportRequest(long reportId, long userID) throws ReportException;

	/**
	 * Whether concentration report search is accessible by the team type
	 * membership.
	 * 
	 * @param teamTypeMembershipID team type membership id
	 * @return true if the team type membership can access concentration report
	 *         search
	 */
	public boolean getIsConcReportAccessibleByTeam(long teamTypeMembershipID) throws ReportException;

	/**
	 * Whether system report search is accessible by the team type membership.
	 * 
	 * @param teamTypeMembershipID team type membership id
	 * @return true if the team type membership can access systems report search
	 */
	public boolean getIsSysReportAccessibleByTeam(long teamTypeMembershipID) throws ReportException;

	/**
	 * Retrieve report config by country code and scope. ie, report master id
	 * and frequency
	 * 
	 * @param country country code
	 * @param scope scope codes
	 * @return report config of country code and scope supplied
	 */
	public OBReport[] getReportJobsByCountry(String country, String scope);

	/**
	 * Retrieve all concentration reports generated at the report date supplied
	 * 
	 * @param reportDate report generated date
	 * @param reportCategoryId report category id
	 * @return all concentration reports matched the criteria supplied.
	 */
	public OBReport[] getConcentrationReportList(Date reportDate, String reportCategoryId);

	/**
	 * Insert error information of report generation.
	 * 
	 * @param reportConfig report config, contains error information.
	 */
	public void insertFailedReportInfoToDB(OBReportConfig reportConfig);

	/**
	 * Insert generated report information which ready for retrieval
	 * 
	 * @param reportConfig report config, contains report generation
	 *        information, such as path, type.
	 */
	public void insertGeneratedReportInfoToDB(OBReportConfig reportConfig);

	/**
	 * Retrieve report by id then append file name with the report type
	 * supplied, such as XLS, PDF.
	 * 
	 * @param reportId report id
	 * @param reportType report output format type, XLS, PDF.
	 * @return generated report matched the id supplied.
	 */
	public OBReport getReport(long reportId, String reportType);

	/**
	 * Retrieve business parameter value for the given parameter code. Normally
	 * is report generation meta info.
	 * 
	 * @param paramCode business parameter code
	 * @return parameter value matched the paramter code, which can be used
	 *         directly later in generation.
	 */
	public String getBizParamValue(String paramCode);

	/**
	 * Retrieve report generation frequency of the report master matched the id
	 * supplied
	 * 
	 * @param reportMasterId report master id
	 * @return report generation frequence
	 */
	public String getFrequencyForReport(String reportMasterId);

	/**
	 * Retrieve all concentration report generated at the date supplied, matched
	 * the rest of criterias as well.
	 * 
	 * @param reportDate report generated date
	 * @param reportCategoryId report category id
	 * @param allowedCountries allowed country code
	 * @param teamTypeID team type id
	 * @return generated concentration reports match the criterias supplied
	 */
	public OBReport[] getConcentrationReportList(Date reportDate, String reportCategoryId, String[] allowedCountries,
			long teamTypeID);

	/**
	 * Retrieve all concentration report generated at the date supplied, matched
	 * the rest of criterias as well.
	 * 
	 * @param reportDate report generated date
	 * @param reportCategoryId report category id
	 * @param allowedCountries allowed country code
	 * @return generated concentration reports match the criterias supplied
	 */
	public OBReport[] getConcentrationReportList(Date reportDate, String reportCategoryId, String[] allowedCountries);

	/**
	 * Retrieve all active generated reports which report master id fall between
	 * the both ids supplied (inclusive)
	 * 
	 * @param minReportMasterId smallest report master id
	 * @param maxReportMasterId largest report master id
	 * @return all generated reports which report master id fall between both
	 *         ids supplied (inclusive)
	 */
	public OBReport[] getAllActiveReportList(int minReportMasterId, int maxReportMasterId);

	/**
	 * Retrieve formatted branch code list of the given center code, eg. (aaa,
	 * bbb, ccc)
	 * 
	 * @param centerCode center code
	 * @return formatted branch code list.
	 */
	public String getFormattedBranchList(String centerCode);
	
	/**
	 * Get All What If Analysis reports 
	 * @param reportDate date of the report generated
	 * @param reportCategoryId report Id category
	 * @param allowedCountries only countries specified to limit the reports scope
	 * @return
	 */
	public OBReport[] getAllWhatIfCondReportList(Date reportDate, String reportCategoryId, String[] allowedCountries) ;

    public String[]  getCenterCodes(String status) ;

    public OBCodeCategoryEntry[] getCentreCodesByTeamID(long teamId,String countryCode) ;

    public OBReport[] getGroupExposureReportsByCountryAndDate(String countryName, String[] orgCodes, Date reportDate, String[]  reportCategoryId);

}
