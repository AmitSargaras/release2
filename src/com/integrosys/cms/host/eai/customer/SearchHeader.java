package com.integrosys.cms.host.eai.customer;


/**
 * @author allen
 * 
 */
public final class SearchHeader implements java.io.Serializable {

	private String totalRecord;

	private String dBKey = "";

	private String SID;

	private String searchType;

	private String searchText;

	private String status;

	private String errMsg;

	private java.util.Date startTS;

	private java.util.Date stopTS;

	public final java.util.Date getStopTS() {
		return stopTS;
	}

	public final void setStopTS(java.util.Date stopTS) {
		this.stopTS = stopTS;
	}

	public final String getSID() {
		return SID;
	}

	public final void setSID(String sid) {
		SID = sid;
	}

	public final String getDBKey() {
		return dBKey;
	}

	public final void setDBKey(String key) {
		dBKey = key;
	}

	public final String getTotalRecord() {
		return totalRecord;
	}

	public final void setTotalRecord(String totalRecord) {
		this.totalRecord = totalRecord;
	}


	public final String getSearchType() {
		return searchType;
	}

	public final void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public final String getErrMsg() {
		return errMsg;
	}

	public final void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public final String getSearchText() {
		return searchText;
	}

	public final void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public final String getStatus() {
		return status;
	}

	public final void setStatus(String status) {
		this.status = status;
	}

	public final java.util.Date getStartTS() {
		return startTS;
	}

	public final void setStartTS(java.util.Date startTS) {
		this.startTS = startTS;
	}

}
