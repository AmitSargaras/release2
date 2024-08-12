/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * OBEarMark
 *
 * Created on 10:57:51 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 10:57:51 AM
 */
public class OBEarMark implements IEarMark {
	private Long earMarkId = new Long(ICMSConstant.LONG_INVALID_VALUE);

	private long earMarkGroupId;

	private long feedId;

	private String customerName;

	private String sourceSystem;

	private String securityId;

	private String aaNumber;

	private String branchName;

	private String branchCode;

	private String cifNo;

	private String accountNo;

	private long earMarkUnits;

	private Date earMarkingDate;

	private String earMarkStatus;

	private boolean holdingInd;

	private String releaseStatus;

	private boolean infoCorrectInd;

	private String infoIncorrectDetails;

	private boolean waiveApproveInd;

	private long versionTime;

	private boolean status;

	private Date dateMaxCapBreach;

	private String purposeOfEarmarking;

	public String getAaNumber() {
		return aaNumber;
	}

	public void setAaNumber(String aaNumber) {
		this.aaNumber = aaNumber;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCifNo() {
		return cifNo;
	}

	public void setCifNo(String cifNo) {
		this.cifNo = cifNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public long getEarMarkGroupId() {
		return earMarkGroupId;
	}

	public void setEarMarkGroupId(long earMarkGroupId) {
		this.earMarkGroupId = earMarkGroupId;
	}

	public Long getEarMarkId() {
		return earMarkId;
	}

	public void setEarMarkId(Long earMarkId) {
		this.earMarkId = earMarkId;
	}

	public String getEarMarkStatus() {
		return earMarkStatus;
	}

	public void setEarMarkStatus(String earMarkStatus) {
		this.earMarkStatus = earMarkStatus;
	}

	public long getEarMarkUnits() {
		return earMarkUnits;
	}

	public void setEarMarkUnits(long earMarkUnits) {
		this.earMarkUnits = earMarkUnits;
	}

	public Date getEarMarkingDate() {
		return earMarkingDate;
	}

	public void setEarMarkingDate(Date earMarkingDate) {
		this.earMarkingDate = earMarkingDate;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public boolean getHoldingInd() {
		return holdingInd;
	}

	public void setHoldingInd(boolean holdingInd) {
		this.holdingInd = holdingInd;
	}

	public boolean getInfoCorrectInd() {
		return infoCorrectInd;
	}

	public void setInfoCorrectInd(boolean infoCorrectInd) {
		this.infoCorrectInd = infoCorrectInd;
	}

	public String getInfoIncorrectDetails() {
		return infoIncorrectDetails;
	}

	public void setInfoIncorrectDetails(String infoIncorrectDetails) {
		this.infoIncorrectDetails = infoIncorrectDetails;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getSecurityId() {
		return securityId;
	}

	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	public String getSourceSystem() {
		return sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public boolean getWaiveApproveInd() {
		return waiveApproveInd;
	}

	public void setWaiveApproveInd(boolean waiveApproveInd) {
		this.waiveApproveInd = waiveApproveInd;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getDateMaxCapBreach() {
		return dateMaxCapBreach;
	}

	public void setDateMaxCapBreach(Date dateMaxCapBreach) {
		this.dateMaxCapBreach = dateMaxCapBreach;
	}

	public void setPurposeOfEarmarking(String purposeOfEarmarking) {
		this.purposeOfEarmarking = purposeOfEarmarking;
	}

	public String getPurposeOfEarmarking() {
		return this.purposeOfEarmarking;
	}
}
