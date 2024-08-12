package com.integrosys.cms.host.eai.limit.response;

import java.util.Vector;

import com.integrosys.cms.host.eai.EAIBody;

public class ResponseMessageBody extends EAIBody {

	private String losAANumber;

	private String stpDate;

	private String userId;

	private String userName;

	private Vector facilityList;

	public String getLosAANumber() {
		return losAANumber;
	}

	public String getStpDate() {
		return stpDate;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public Vector getFacilityList() {
		return facilityList;
	}

	public void setLosAANumber(String losAANumber) {
		this.losAANumber = losAANumber;
	}

	public void setStpDate(String stpDate) {
		this.stpDate = stpDate;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setFacilityList(Vector facilityList) {
		this.facilityList = facilityList;
	}
}
