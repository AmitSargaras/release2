/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.creditriskparam.bus;

/**
 * OBCreditRiskParamGroup Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBCreditRiskParamGroup implements ICreditRiskParamGroup {

	private OBCreditRiskParam[] feedEntries;

	private String type;

	private String subType;

	private String subTypeDescription;

	private String stockType;

	private String stockTypeDescription;

	private long stockFeedGroupID;

	private long versionTime = 0;

	public OBCreditRiskParam[] getFeedEntries() {
		return this.feedEntries;
	}

	public String getType() {
		return this.type;
	}

	public String getSubType() {
		return this.subType;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getStockTypeDescription() {
		return stockTypeDescription;
	}

	public void setStockTypeDescription(String stockTypeDescription) {
		this.stockTypeDescription = stockTypeDescription;
	}

	public long getCreditRiskParamGroupID() {
		return this.stockFeedGroupID;
	}

	public void setFeedEntries(OBCreditRiskParam[] param) {
		this.feedEntries = param;
	}

	public void setType(String param) {
		this.type = param;
	}

	public void setSubType(String param) {
		this.subType = param;
	}

	public void setCreditRiskParamGroupID(long param) {
		this.stockFeedGroupID = param;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getSubTypeDescription() {
		return subTypeDescription;
	}

	public void setSubTypeDescription(String subTypeDescription) {
		this.subTypeDescription = subTypeDescription;
	}
}
