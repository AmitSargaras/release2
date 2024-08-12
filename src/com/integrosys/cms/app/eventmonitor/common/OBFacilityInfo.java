package com.integrosys.cms.app.eventmonitor.common;

import java.util.HashMap;

public class OBFacilityInfo implements java.io.Serializable {
	private String facilityID;

	private String facilityDesc;

	private HashMap accIDMap = new HashMap();

	public String getFacilityDesc() {
		return facilityDesc;
	}

	public void setFacilityDesc(String facilityDesc) {
		this.facilityDesc = facilityDesc;
	}

	public String getFacilityID() {
		return facilityID;
	}

	public void setFacilityID(String facilityID) {
		this.facilityID = facilityID;
	}

	public HashMap getAccIDMap() {
		return accIDMap;
	}

	public void setAccIDMap(HashMap accIDMap) {
		this.accIDMap = accIDMap;
	}
}