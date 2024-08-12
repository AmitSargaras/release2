/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/OBRequest.java,v 1.3 2003/09/22 02:23:23 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class that provides the implementation for IRequest
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/22 02:23:23 $ Tag: $Name: $
 */
public class OBRequest implements IRequest {
	private long requestID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private IRequestHeader requestHeader = null;

	private IRequestSubject requestSubject = null;

	private IRequestDescription requestDescription = null;

	private IRequestItem[] requestItemList = null;

	private String proposedByName = null;

	private String proposedByDesignation = null;

	private String proposedBySignNo = null;

	private Date proposedByDate = null;

	private String supportedByName = null;

	private String supportedByDesignation = null;

	private String supportedByCoinNo = null;

	private Date supportedByDate = null;

	private String approvedBySCOName = null;

	private String approvedBySCODesignation = null;

	private String approvedBySCOCoinNo = null;

	private Date approvedBySCODate = null;

	private String approvedByRCOName = null;

	private String approvedByRCODesignation = null;

	private String approvedByRCOCoinNo = null;

	private Date approvedByRCODate = null;

	private String approvedByCCOName = null;

	private String approvedByCCODesignation = null;

	private String approvedByCCOCoinNo = null;

	private Date approvedByCCODate = null;

	private String name = null;

	private String creditCommittee = null;

	private String minsOfMeeting = null;

	private Date meetingDate = null;

	private String reason = null;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	public OBRequest() {
	}

	public long getRequestID() {
		return this.requestID;
	}

	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	public long getCustomerID() {
		return this.customerID;
	}

	public IRequestHeader getRequestHeader() {
		return this.requestHeader;
	}

	public IRequestSubject getRequestSubject() {
		return this.requestSubject;
	}

	public IRequestDescription getRequestDescription() {
		return this.requestDescription;
	}

	public IRequestItem[] getRequestItemList() {
		return this.requestItemList;
	}

	public String getProposedByName() {
		return this.proposedByName;
	}

	public String getProposedByDesignation() {
		return this.proposedByDesignation;
	}

	public String getProposedBySignNo() {
		return this.proposedBySignNo;
	}

	public Date getProposedByDate() {
		return this.proposedByDate;
	}

	public String getSupportedByName() {
		return this.supportedByName;
	}

	public String getSupportedByDesignation() {
		return this.supportedByDesignation;
	}

	public String getSupportedByCoinNo() {
		return this.supportedByCoinNo;
	}

	public Date getSupportedByDate() {
		return this.supportedByDate;
	}

	public String getApprovedBySCOName() {
		return this.approvedBySCOName;
	}

	public String getApprovedBySCODesignation() {
		return this.approvedBySCODesignation;
	}

	public String getApprovedBySCOCoinNo() {
		return this.approvedBySCOCoinNo;
	}

	public Date getApprovedBySCODate() {
		return this.approvedBySCODate;
	}

	public String getApprovedByRCOName() {
		return this.approvedByRCOName;
	}

	public String getApprovedByRCODesignation() {
		return this.approvedByRCODesignation;
	}

	public String getApprovedByRCOCoinNo() {
		return this.approvedByRCOCoinNo;
	}

	public Date getApprovedByRCODate() {
		return this.approvedByRCODate;
	}

	public String getApprovedByCCOName() {
		return this.approvedByCCOName;
	}

	public String getApprovedByCCODesignation() {
		return this.approvedByCCODesignation;
	}

	public String getApprovedByCCOCoinNo() {
		return this.approvedByCCOCoinNo;
	}

	public Date getApprovedByCCODate() {
		return this.approvedByCCODate;
	}

	public String getName() {
		return this.name;
	}

	public String getCreditCommittee() {
		return this.creditCommittee;
	}

	public String getMinsOfMeeting() {
		return this.minsOfMeeting;
	}

	public Date getMeetingDate() {
		return this.meetingDate;
	}

	public String getReason() {
		return this.reason;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public void setRequestID(long aRequestID) {
		this.requestID = aRequestID;
	}

	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
	}

	public void setRequestHeader(IRequestHeader anIRequestHeader) {
		this.requestHeader = anIRequestHeader;
	}

	public void setRequestSubject(IRequestSubject anIRequestSubject) {
		this.requestSubject = anIRequestSubject;
	}

	public void setRequestDescription(IRequestDescription anIRequestDescription) {
		this.requestDescription = anIRequestDescription;
	}

	public void setRequestItemList(IRequestItem[] aRequestItemList) {
		this.requestItemList = aRequestItemList;
	}

	public void setProposedByName(String aProposedByName) {
		this.proposedByName = aProposedByName;
	}

	public void setProposedByDesignation(String aProposedByDesignation) {
		this.proposedByDesignation = aProposedByDesignation;
	}

	public void setProposedBySignNo(String aProposedBySignNo) {
		this.proposedBySignNo = aProposedBySignNo;
	}

	public void setProposedByDate(Date aProposedByDate) {
		this.proposedByDate = aProposedByDate;
	}

	public void setSupportedByName(String aSupportedByName) {
		this.supportedByName = aSupportedByName;
	}

	public void setSupportedByDesignation(String aSupportedByDesignation) {
		this.supportedByDesignation = aSupportedByDesignation;
	}

	public void setSupportedByCoinNo(String aSupportedByCoinNo) {
		this.supportedByCoinNo = aSupportedByCoinNo;
	}

	public void setSupportedByDate(Date aSupportedByDate) {
		this.supportedByDate = aSupportedByDate;
	}

	public void setApprovedBySCOName(String anApprovedBySCOName) {
		this.approvedBySCOName = anApprovedBySCOName;
	}

	public void setApprovedBySCODesignation(String anApprovedBySCODesignation) {
		this.approvedBySCODesignation = anApprovedBySCODesignation;
	}

	public void setApprovedBySCOCoinNo(String anApprovedBySCOCoinNo) {
		this.approvedBySCOCoinNo = anApprovedBySCOCoinNo;
	}

	public void setApprovedBySCODate(Date anApprovedBySCODate) {
		this.approvedBySCODate = anApprovedBySCODate;
	}

	public void setApprovedByRCOName(String anApprovedByRCOName) {
		this.approvedByRCOName = anApprovedByRCOName;
	}

	public void setApprovedByRCODesignation(String anApprovedByRCODesignation) {
		this.approvedByRCODesignation = anApprovedByRCODesignation;
	}

	public void setApprovedByRCOCoinNo(String anApprovedByRCOCoinNo) {
		this.approvedByRCOCoinNo = anApprovedByRCOCoinNo;
	}

	public void setApprovedByRCODate(Date anApprovedByRCODate) {
		this.approvedByRCODate = anApprovedByRCODate;
	}

	public void setApprovedByCCOName(String anApprovedByCCOName) {
		this.approvedByCCOName = anApprovedByCCOName;
	}

	public void setApprovedByCCODesignation(String anApprovedByCCODesignation) {
		this.approvedByCCODesignation = anApprovedByCCODesignation;
	}

	public void setApprovedByCCOCoinNo(String anApprovedByCCOCoinNo) {
		this.approvedByCCOCoinNo = anApprovedByCCOCoinNo;
	}

	public void setApprovedByCCODate(Date anApprovedByCCODate) {
		this.approvedByCCODate = anApprovedByCCODate;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public void setCreditCommittee(String aCreditCommittee) {
		this.creditCommittee = aCreditCommittee;
	}

	public void setMinsOfMeeting(String aMinsOfMeeting) {
		this.minsOfMeeting = aMinsOfMeeting;
	}

	public void setMeetingDate(Date aMeetingDate) {
		this.meetingDate = aMeetingDate;
	}

	public void setReason(String aReason) {
		this.reason = aReason;
	}

	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
