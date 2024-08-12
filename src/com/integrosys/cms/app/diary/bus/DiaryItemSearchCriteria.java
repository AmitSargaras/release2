/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/DiaryItemSearchCriteria.java,v 1.6 2005/10/11 06:41:21 wltan Exp $
 */
package com.integrosys.cms.app.diary.bus;

//ofa
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This is a helper class that will contains all the possible search criteria
 * used by Diary Item.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/10/11 06:41:21 $ Tag: $Name: $
 */
public class DiaryItemSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = -1347185107736980992L;

	private long teamTypeID;

	private String[] allowedCountries;

	private String countryFilter;

	private Date startExpDate;

	private String customerIndex;

	private String leID;

	private ITeam team;

	private Date endExpDate;

	private Integer totalCountForCurrentTotalPages;

	/**
	 * 
	 * Default Constructor
	 */
	public DiaryItemSearchCriteria() {
	}

	/**
	 * retrieves the country allowed for viewing
	 * @return String - country code
	 */
	public String[] getAllowedCountries() {
		return allowedCountries;
	}

	/**
	 * retrieves the country filter
	 * @return String
	 */
	public String getCountryFilter() {
		return countryFilter;
	}

	/**
	 * retrieves the customer index for searching
	 * @return String
	 */
	public String getCustomerIndex() {
		return customerIndex;
	}

	public Date getEndExpDate() {
		return endExpDate;
	}

	/**
	 * retrieves the customer le reference for searching
	 * @return String
	 */
	public String getLeID() {
		return leID;
	}

	public Date getStartExpDate() {
		return startExpDate;
	}

	public ITeam getTeam() {
		return team;
	}

	/**
	 * retrieves the team type id used in search
	 * @return long - team id
	 */
	public long getTeamTypeID() {
		return teamTypeID;
	}

	// Getters

	public Integer getTotalCountForCurrentTotalPages() {
		return totalCountForCurrentTotalPages;
	}

	/**
	 * sets the allowed country for viewing
	 * @param allowedCountries
	 */
	public void setAllowedCountries(String[] allowedCountries) {
		this.allowedCountries = allowedCountries;
	}

	/**
	 * filters the diary records by country
	 * @param countryFilter
	 */
	public void setCountryFilter(String countryFilter) {
		this.countryFilter = countryFilter;
	}

	/**
	 * sets the customer index for searching
	 * @param customerIndex
	 */
	public void setCustomerIndex(String customerIndex) {
		this.customerIndex = customerIndex;
	}

	public void setEndExpDate(Date endExpDate) {
		this.endExpDate = endExpDate;
	}

	/**
	 * sets the customer le reference for searching
	 * @param leID
	 */
	public void setLeID(String leID) {
		this.leID = leID;
	}

	public void setStartExpDate(Date startExpDate) {
		this.startExpDate = startExpDate;
	}

	public void setTeam(ITeam team) {
		this.team = team;
	}

	/**
	 * sets the team type id to be used in search
	 * @param teamTypeID - long
	 */
	public void setTeamTypeID(long teamTypeID) {
		this.teamTypeID = teamTypeID;
	}

	public void setTotalCountForCurrentTotalPages(Integer totalCountForCurrentTotalPages) {
		this.totalCountForCurrentTotalPages = totalCountForCurrentTotalPages;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("DiaryItemSearchCriteria: {");
		buf.append("Team Type ID: [").append(this.teamTypeID).append("], ");
		buf.append("Allowed Countries: [").append(ArrayUtils.toString(allowedCountries)).append("], ");
		buf.append("countryFilter: [").append(this.countryFilter).append("], ");
		buf.append("Customer Name Index: [").append(this.customerIndex).append("], ");
		buf.append("LE ID: [").append(this.leID).append("], ");
		buf.append("Start Expiry Date: [").append(this.startExpDate).append("], ");
		buf.append("End Expiry Date: [").append(this.endExpDate).append("], ");
		buf.append("Total Count For Current Total Pages: [").append(this.totalCountForCurrentTotalPages).append("] ");
		buf.append("}");
		return buf.toString();
	}

}
