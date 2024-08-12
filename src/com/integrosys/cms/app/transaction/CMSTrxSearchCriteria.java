/*      
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSTrxSearchCriteria.java,v 1.12 2006/11/07 08:39:44 jzhan Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the collateral.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/11/07 08:39:44 $ Tag: $Name: $
 */
public class CMSTrxSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = 1625223928615618459L;

	private String searchIndicator;
	
	private String lastUpdatedBy;
	
	private String status;
	
	private String sort;
	
	private String field;

	private long teamTypeMembershipID;

	private String[] allowedCountries;

	private String[] allowedOrganisations;

	private String[] allowedSegments;

	private String[] transactionTypes = null;

	private String currentState = null;

	private boolean isCurrentState = true;

	private Long limitProfileID = null;

	private boolean isOnlyMembershipRecords = true;

	private long userID = -1;

	private String teamType = "";

	private long transactionID = -1;

	private long teamID = ICMSConstant.LONG_INVALID_VALUE;

	private String customerType;
	
	private String transactionType;

	private String legalName;

	private String legalID;
	
	private String loginID;

	private String legalIDType;

	private String customerName;

	private String aaNumber;

	private long teamMembershipID;

	private String sortBy;

	private String filterByType;

	private String filterByValue;

	private String pendingTask;
	
	
	
	private String transactionDate;
	
	

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	
	private Integer totalCountForCurrentTotalPages;

	public String getAaNumber() {
		return aaNumber;
	}

	public String[] getAllowedCountries() {
		return allowedCountries;
	}

	public String[] getAllowedOrganisations() {
		return allowedOrganisations;
	}

	public String[] getAllowedSegments() {
		return allowedSegments;
	}

	public String getCurrentState() {
		return currentState;
	}

	/**
	 * Gets the start of the customer name to search for. eg. if searching for
	 * customer with name "ABC", this should return "A", "AB" or "ABC".
	 * 
	 * @return String
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Get the customer type included in the search. Valid values are
	 * ICMSConstant.CUSTOMER_TYPE_BORROWER and
	 * ICMSConstant.CUSTOMER_TYPE_NONBORROWER
	 * 
	 * @return String
	 */
	public String getCustomerType() {
		return customerType;
	}

	public String getFilterByType() {
		return filterByType;
	}

	public String getFilterByValue() {
		return filterByValue;
	}

	/**
	 * Gets the legal ID to search for.
	 * 
	 * @return String
	 */
	public String getLegalID() {
		return legalID;
	}

	public String getLegalIDType() {
		return legalIDType;
	}

	/**
	 * Gets the start of the legal name to search for. eg. if searching for
	 * legal entity with name "ABC", this should return "A", "AB" or "ABC".
	 * 
	 * @return String
	 */
	public String getLegalName() {
		return legalName;
	}

	public Long getLimitProfileID() {
		return limitProfileID;
	}

	public String getPendingTask() {
		return pendingTask;
	}

	public String getSearchIndicator() {
		return searchIndicator;
	}

	public String getSortBy() {
		return sortBy;
	}

	/**
	 * Gets the teamID to search for
	 * 
	 * @ return long
	 */
	public long getTeamID() {
		return teamID;
	}

	public long getTeamMembershipID() {
		return teamMembershipID;
	}

	public String getTeamType() {
		return teamType;
	}

	public long getTeamTypeMembershipID() {
		return teamTypeMembershipID;
	}

	public Integer getTotalCountForCurrentTotalPages() {
		return totalCountForCurrentTotalPages;
	}

	public long getTransactionID() {
		return transactionID;
	}

	public String[] getTransactionTypes() {
		return transactionTypes;
	}

	/**
	 * @return Returns the userID.
	 */
	public long getUserID() {
		return userID;
	}

	public boolean isCurrentState() {
		return isCurrentState;
	}

	public boolean isOnlyMembershipRecords() {
		return isOnlyMembershipRecords;
	}

	public void setAaNumber(String aaNumber) {
		this.aaNumber = aaNumber;
	}

	public void setAllowedCountries(String[] allowedCountries) {
		this.allowedCountries = allowedCountries;
	}

	public void setAllowedOrganisations(String[] allowedOrganisations) {
		this.allowedOrganisations = allowedOrganisations;
	}

	public void setAllowedSegments(String[] allowedSegments) {
		this.allowedSegments = allowedSegments;
	}

	public void setCurrentState(boolean currentState) {
		isCurrentState = currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	/**
	 * Sets the start of the customer name to search for. eg. if searching for
	 * customer with name "ABC", this should be set to "A", "AB" or "ABC".
	 * 
	 * @param customerName - String
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Set the customer type to include in the search. Valid values are
	 * ICMSConstant.CUSTOMER_TYPE_BORROWER and
	 * ICMSConstant.CUSTOMER_TYPE_NONBORROWER
	 * 
	 * @param customerType - String
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public void setFilterByType(String filterByType) {
		this.filterByType = filterByType;
	}

	public void setFilterByValue(String filterByValue) {
		this.filterByValue = filterByValue;
	}

	/**
	 * Sets the legal ID to search for.
	 * 
	 * @param legalID - String
	 */
	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	public void setLegalIDType(String legalIDType) {
		this.legalIDType = legalIDType;
	}

	/**
	 * Sets the start of the legal name to search for. eg. if searching for
	 * legal entity with name "ABC", this should be set to "A", "AB" or "ABC".
	 * 
	 * @param legalName - String
	 */
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public void setLimitProfileID(Long limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public void setOnlyMembershipRecords(boolean onlyMembershipRecords) {
		isOnlyMembershipRecords = onlyMembershipRecords;
	}

	public void setPendingTask(String pendingTask) {
		this.pendingTask = pendingTask;
	}

	public void setSearchIndicator(String searchIndicator) {
		this.searchIndicator = searchIndicator;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	/**
	 * Sets the team ID to search for
	 * 
	 * @param teamID - long
	 */
	public void setTeamID(long teamID) {
		this.teamID = teamID;
	}

	public void setTeamMembershipID(long teamMembershipID) {
		this.teamMembershipID = teamMembershipID;
	}

	public void setTeamType(String teamType) {
		this.teamType = teamType;
	}

	public void setTeamTypeMembershipID(long teamTypeMembershipID) {
		this.teamTypeMembershipID = teamTypeMembershipID;
	}

	public void setTotalCountForCurrentTotalPages(Integer totalCountForCurrentTotalPages) {
		this.totalCountForCurrentTotalPages = totalCountForCurrentTotalPages;
	}

	public void setTransactionID(long transactionID) {
		this.transactionID = transactionID;
	}

	public void setTransactionTypes(String[] transactionTypes) {
		this.transactionTypes = transactionTypes;
	}

	/**
	 * @param userID The userID to set.
	 */
	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getLoginID() {
		return loginID;
	}

	public void setLoginID(String loginID) {
		this.loginID = loginID;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}
	
	

}
