/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealForm
 *
 * Created on 3:15:41 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.predeal;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 21, 2007 Time: 3:15:41 PM
 */
public class PreDealForm extends TrxContextForm implements Serializable {

	private static final String formName = "PreDealForm";

	private static final String mapperName = "com.integrosys.cms.ui.predeal.PreDealMapper";

	private static final String formName2 = "OBPreDealTrxValue";

	private String feedId;

	private String counterName;

	private String ric;

	private String isinCode;

	public PreDealForm() {

	}

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getCounterName() {
		return counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public String getRic() {
		return ric;
	}

	public void setRic(String ric) {
		this.ric = ric;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	private String customerName;

	private String sourceSystem;

	private String securityId;

	private String aaNumber;

	private String branchName;

	private String branchCode;

	private String cifNumber;

	private String accountNo;

	private String earMarkUnits;

	private String earMarkGroupId;

	private boolean holdingInd = false;

	private boolean infoCorrectInd = true;

	private boolean waiveApproveInd = false;

	private boolean status = false;

	private String infoIncorrectDetails;

	private String earMarkingDate;

	private String earMarkId;

	private String releaseStatus;

	private String infoCorrectIndStr;

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

	public String getCifNumber() {
		return cifNumber;
	}

	public void setCifNumber(String cifNumber) {
		this.cifNumber = cifNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getEarMarkUnits() {
		return earMarkUnits;
	}

	public void setEarMarkUnits(String earMarkUnits) {
		this.earMarkUnits = earMarkUnits;
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

	public String getEarMarkGroupId() {
		return earMarkGroupId;
	}

	public void setEarMarkGroupId(String earMarkGroupId) {
		this.earMarkGroupId = earMarkGroupId;
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

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getWaiveApproveInd() {
		return waiveApproveInd;
	}

	public void setWaiveApproveInd(boolean waiveApproveInd) {
		this.waiveApproveInd = waiveApproveInd;
	}

	public String getEarMarkId() {
		return earMarkId;
	}

	public void setEarMarkId(String earMarkId) {
		this.earMarkId = earMarkId;
	}

	public String getEarMarkingDate() {
		return earMarkingDate;
	}

	public void setEarMarkingDate(String earMarkingDate) {
		this.earMarkingDate = earMarkingDate;
	}

	public String getReleaseStatus() {
		return releaseStatus;
	}

	public void setReleaseStatus(String releaseStatus) {
		this.releaseStatus = releaseStatus;
	}

	public String getInfoIncorrectDetails() {
		return infoIncorrectDetails;
	}

	public void setInfoIncorrectDetails(String infoIncorrectDetails) {
		this.infoIncorrectDetails = infoIncorrectDetails;
	}

	public String[][] getMapper() {
		return new String[][] { { formName, mapperName }, { formName2, mapperName },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}

	public String getInfoCorrectIndStr() {
		return infoCorrectIndStr;
	}

	public void setInfoCorrectIndStr(String infoCorrectIndStr) {
		this.infoCorrectIndStr = infoCorrectIndStr;
	}

	/**
	 * @return the purposeOfEarmarking
	 */
	public String getPurposeOfEarmarking() {
		return purposeOfEarmarking;
	}

	/**
	 * @param purposeOfEarmarking the purposeOfEarmarking to set
	 */
	public void setPurposeOfEarmarking(String purposeOfEarmarking) {
		this.purposeOfEarmarking = purposeOfEarmarking;
	}

}