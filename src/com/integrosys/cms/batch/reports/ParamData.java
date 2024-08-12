/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/ParamData.java,v 1.7 2007/03/13 06:27:06 jychong Exp $
 */

package com.integrosys.cms.batch.reports;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * Description: Data object for collecting common inputs
 * This class will contain ALL the possible input parameters from ALL entry points
 * This will be used by different entry points to initialise the required input parameters
 * for the report generation.  Different entry points will initialise different set of data and its up to
 * the appropriate subclass of ReportParameter to decide which are the parameters wanted and pick from here
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2007/03/13 06:27:06 $ Tag: $Name: $
 */

public class ParamData {

	private String country;

    private String centre;

    private Date reportDate;

	private String organisation;

	private long limitProfileID;

	private String[] allowedCountries;

	private long teamTypeMembershipID;

	private Date startExpDate;

	private Date endExpDate;

	private long leID;

	private String customerIndex;

	//private String reportFrequency;

	private long buildupID;

	public ParamData(long team_type_membership_id, Date startExpDate, Date endExpiryDate, String[] allowedCountries,
			long leID, String customerIndex) {
		this.teamTypeMembershipID = team_type_membership_id;
		this.startExpDate = startExpDate;
		this.endExpDate = endExpiryDate;
		this.allowedCountries = allowedCountries;
		this.leID = leID;
		this.customerIndex = customerIndex;
	}

	public String getCustomerIndex() {
		return customerIndex;
	}

	public void setCustomerIndex(String customerIndex) {
		this.customerIndex = customerIndex;
	}

	public long getLeID() {
		return leID;
	}

	public void setLeID(long leID) {
		this.leID = leID;
	}

	public long getTeamTypeMembershipID() {
		return teamTypeMembershipID;
	}

	public void setTeamTypeMembershipID(long teamTypeMembershipID) {
		this.teamTypeMembershipID = teamTypeMembershipID;
	}

	public String[] getAllowedCountries() {
		return allowedCountries;
	}

	public void setAllowedCountries(String[] allowedCountries) {
		this.allowedCountries = allowedCountries;
	}

	public Date getEndExpDate() {
		return endExpDate;
	}

	public void setEndExpDate(Date endExpDate) {
		this.endExpDate = endExpDate;
	}

	public Date getStartExpDate() {
		return startExpDate;
	}

	public void setStartExpDate(Date startExpDate) {
		this.startExpDate = startExpDate;
	}

	public ParamData(String country, Date reportDate, String organisation, long limitProfileID) {
		this.country = country;
		this.reportDate = reportDate;
		this.organisation = organisation;
		this.limitProfileID = limitProfileID;
	}

	public ParamData(String country) {
		this.country = country;
	}

	public ParamData(String country, Date reportDate, String organisation) {
		this.country = country;
		this.reportDate = reportDate;
		this.organisation = organisation;
	}

	public ParamData(long limitProfileID, Date reportDate, String[] allowedCountries) {
		this.limitProfileID = limitProfileID;
		this.reportDate = reportDate;
		this.allowedCountries = allowedCountries;
	}

	public ParamData(String country, Date reportDate, long buildupID) {
		this.country = country;
		this.reportDate = reportDate;
		this.buildupID = buildupID;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

    public String getCentre() {
        return centre;
    }

    public void setCentre(String centre) {
        this.centre = centre;
    }

    public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getOrganisation() {
		return organisation;
	}

	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	public long getLimitProfileID() {
		return limitProfileID;
	}

	public void setLimitProfileID(long limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

//	public void setReportFrequency(String reportFrequency) {
//		this.reportFrequency = reportFrequency;
//	}
//
//	public String getReportFrequency() {
//		return reportFrequency;
//	}

	public long getBuildupID() {
		return buildupID;
	}

	public void setBuildupID(long buildupID) {
		this.buildupID = buildupID;
	}

	public ParamData() {
	}


    /*********************
     * Helper Method
     *********************/
    protected String getCountryList() {
        return CommonUtil.arrayToDelimStr(getAllowedCountries());
    }


    public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
