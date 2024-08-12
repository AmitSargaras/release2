package com.integrosys.cms.app.eventmonitor.commoditylimitlevel;

import java.util.Collection;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jun 7, 2003 Time: 4:06:34 PM To
 * change this template use Options | File Templates.
 */
public class OBLimitInfo implements java.io.Serializable {

	private String limitId;

	private String limitFacilityType;

	private String reqCoverage;

	private String actCoverage;

	private Amount shortFallValue;

	private Amount approvedLimit;

	private Amount activatedLimit;

	private Amount operationalLimit;

	// private OBSecInfo secInfo[];
	private Collection secInfos;

	public Amount getActivatedLimit() {
		return activatedLimit;
	}

	public void setActivatedLimit(Amount activatedLimit) {
		this.activatedLimit = activatedLimit;
	}

	public Amount getApprovedLimit() {
		return approvedLimit;
	}

	public void setApprovedLimit(Amount approvedLimit) {
		this.approvedLimit = approvedLimit;
	}

	public Amount getOperationalLimit() {
		return operationalLimit;
	}

	public void setOperationalLimit(Amount operationalLimit) {
		this.operationalLimit = operationalLimit;
	}

	public Collection getSecInfo() {
		return secInfos;
	}

	public OBSecInfo[] getSecInfoList() {
		return (OBSecInfo[]) secInfos.toArray(new OBSecInfo[0]);
	}

	public void setSecInfo(Collection secInfos_) {
		this.secInfos = secInfos_;
	}

	public String[] getSecId() {
		return secId;
	}

	public void setSecId(String[] secId) {
		this.secId = secId;
	}

	public String[] getSecsubType() {
		return secsubType;
	}

	public void setSecsubType(String[] secsubType) {
		this.secsubType = secsubType;
	}

	public String[] getSecType() {
		return secType;
	}

	public void setSecType(String[] secType) {
		this.secType = secType;
	}

	private String[] secId;

	private String[] secType;

	private String[] secsubType;

	public String getActCoverage() {
		return actCoverage;
	}

	public void setActCoverage(String actCoverage) {
		this.actCoverage = actCoverage;
	}

	public String getLimitFacilityType() {
		return limitFacilityType;
	}

	public void setLimitFacilityType(String limitFacilityType) {
		this.limitFacilityType = limitFacilityType;
	}

	public String getLimitId() {
		return limitId;
	}

	public void setLimitId(String limitId) {
		this.limitId = limitId;
	}

	public String getReqCoverage() {
		return reqCoverage;
	}

	public void setReqCoverage(String reqCoverage) {
		this.reqCoverage = reqCoverage;
	}

	public Amount getShortFallValue() {
		return shortFallValue;
	}

	public void setShortFallValue(Amount shortFallValue) {
		this.shortFallValue = shortFallValue;
	}

	public String toString() {
		String result = limitId + ";" + limitFacilityType + ";" + reqCoverage + ";" + actCoverage + "\n";
		if (secInfos != null) {
			java.util.Iterator e = secInfos.iterator();
			while (e.hasNext()) {
				OBSecInfo di = (OBSecInfo) e.next();
				result += "----------" + di.toString() + "\n";
			}

		}
		return result;
	}
}
