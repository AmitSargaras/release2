package com.integrosys.cms.host.eai.limit.response;

import com.integrosys.cms.host.eai.limit.bus.FacilityMaster;

public class ResponseFacilityMaster extends FacilityMaster {
	private String losLimitId;

	public void setLosLimitId(String losLimitId) {
		this.losLimitId = losLimitId;
	}

	public String getLosLimitId() {
		return losLimitId;
	}

}
