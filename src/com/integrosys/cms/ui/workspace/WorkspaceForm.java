package com.integrosys.cms.ui.workspace;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class WorkspaceForm extends TrxContextForm

implements Serializable {

	private String searchLegalName;

	private String searchCustomerName;
	
	private String searchLoginID;

	private String searchLeID;

	private String searchLeIDType;

	private String searchAANumber;

	private String sortBy;
	
	private String lastUpdatedBy;

	private String filterByType;
	
	private String transactionType="";
	
	private String toDoStatus;

	private String filterByValue;

	private String selectedItems[] = null;

	private String reassignTo;
	
	

	public String getFilterByType() {
		return filterByType;
	}

	public String getFilterByValue() {
		return filterByValue;
	}

	public String getReassignTo() {
		return reassignTo;
	}

	public String getSearchAANumber() {
		return searchAANumber;
	}

	public String getSearchCustomerName() {
		return searchCustomerName;
	}

	public String getSearchLegalName() {
		return searchLegalName;
	}

	public String getSearchLeID() {
		return searchLeID;
	}

	public String getSearchLeIDType() {
		return searchLeIDType;
	}

	public String[] getSelectedItems() {
		return selectedItems;
	}

	public String getSortBy() {
		return sortBy;
	}

	public String getSearchLoginID() {
		return searchLoginID;
	}

	public void setSearchLoginID(String searchLoginID) {
		this.searchLoginID = searchLoginID;
	}

	public void setFilterByType(String filterByType) {
		this.filterByType = filterByType;
	}

	public void setFilterByValue(String filterByValue) {
		this.filterByValue = filterByValue;
	}

	public void setReassignTo(String reassignTo) {
		this.reassignTo = reassignTo;
	}

	public void setSearchAANumber(String searchAANumber) {
		this.searchAANumber = searchAANumber;
	}

	public void setSearchCustomerName(String searchCustomerName) {
		this.searchCustomerName = searchCustomerName;
	}

	public void setSearchLegalName(String searchLegalName) {
		this.searchLegalName = searchLegalName;
	}

	public void setSearchLeID(String searchLeID) {
		this.searchLeID = searchLeID;
	}

	public void setSearchLeIDType(String searchLeIDType) {
		this.searchLeIDType = searchLeIDType;
	}

	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "todoMapper", "com.integrosys.cms.ui.todo.ToDoMapper" }, };

		return input;

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

	public String getToDoStatus() {
		return toDoStatus;
	}

	public void setToDoStatus(String toDoStatus) {
		this.toDoStatus = toDoStatus;
	}


}
