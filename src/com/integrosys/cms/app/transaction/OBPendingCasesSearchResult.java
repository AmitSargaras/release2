/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/OBCMSTrxSearchResult.java,v 1.12 2005/09/22 02:39:44 whuang Exp $
 */
package com.integrosys.cms.app.transaction;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBPendingCasesSearchResult implements Serializable {

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private long customerID = ICMSConstant.LONG_INVALID_VALUE;

	private String customerName;

	private String leID;

	private String cmsSegmentCode;

	private String cmsSegmentValue;

	private String losBcaRefNum;

	private String bcaRefNum;

	private String sourceID;

	private String sourceName;

	private String orgCode;

	private String orgName;

	private String trxStatus;

	private Date bcaApprovedDate;

	public Date getBcaApprovedDate() {
		return bcaApprovedDate;
	}

	public String getBcaRefNum() {
		return bcaRefNum;
	}

	public String getCmsSegmentCode() {
		return cmsSegmentCode;
	}

	public String getCmsSegmentValue() {
		return cmsSegmentValue;
	}

	public long getCustomerID() {
		return customerID;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getLeID() {
		return leID;
	}

	public long getLimitProfileID() {
		return limitProfileID;
	}

	public String getLosBcaRefNum() {
		return losBcaRefNum;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public String getSourceID() {
		return sourceID;
	}

	public String getSourceName() {
		return sourceName;
	}

	public String getTrxStatus() {
		return trxStatus;
	}

	public void setBcaApprovedDate(Date bcaApprovedDate) {
		this.bcaApprovedDate = bcaApprovedDate;
	}

	public void setBcaRefNum(String bcaRefNum) {
		this.bcaRefNum = bcaRefNum;
	}

	public void setCmsSegmentCode(String cmsSegmentCode) {
		this.cmsSegmentCode = cmsSegmentCode;
	}

	public void setCmsSegmentValue(String cmsSegmentValue) {
		this.cmsSegmentValue = cmsSegmentValue;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public void setLeID(String leID) {
		this.leID = leID;
	}

	public void setLimitProfileID(long limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public void setLosBcaRefNum(String losBcaRefNum) {
		this.losBcaRefNum = losBcaRefNum;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public void setTrxStatus(String trxStatus) {
		this.trxStatus = trxStatus;
	}
}
