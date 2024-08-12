package com.integrosys.cms.host.eai.limit.bus;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

import com.integrosys.cms.host.eai.StandardCode;

/**
 * <p>
 * An entity represent a Account information
 * <p>
 * May tied to more than 1 facility/limit
 * @author Chong Jun Yong
 * 
 */
public class SystemXReference implements java.io.Serializable {

	private static final long serialVersionUID = 2126671849776264735L;

	private Long cmsId;

	private Long cmsSubProfileId;

	private String CIFId;

	private Long subProfileId;

	private Long[] cmsLimitIds;

	private Set limitsSysXRefMapSet;

	private String accountNumber;

	private String accountType;

	private Long accountSequence;

	private StandardCode facilityType;

	private Long facilitySequenceNumber;

	private String accountDelinquentIndicator;

	private String accountStatus;

	private String hostAANumber;

	private String AANumber;

	private Date firstDisbursementDate;

	private Date finalDisbursementDate;

	private String updateStatusIndicator;

	private String changeIndicator;

	public String getAANumber() {
		return AANumber;
	}

	public String getAccountDelinquentIndicator() {
		return accountDelinquentIndicator;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public Long getAccountSequence() {
		return accountSequence;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public String getAccountType() {
		return accountType;
	}

	public String getChangeIndicator() {
		return changeIndicator;
	}

	public String getCIFId() {
		return CIFId;
	}

	public Long getCmsId() {
		return cmsId;
	}

	public Long[] getCmsLimitIds() {
		return cmsLimitIds;
	}

	public Long getCmsSubProfileId() {
		return cmsSubProfileId;
	}

	public Long getFacilitySequenceNumber() {
		return facilitySequenceNumber;
	}

	public StandardCode getFacilityType() {
		return facilityType;
	}

	public Date getFinalDisbursementDate() {
		return finalDisbursementDate;
	}

	public Date getFirstDisbursementDate() {
		return firstDisbursementDate;
	}

	public String getHostAANumber() {
		return hostAANumber;
	}

	public Set getLimitsSysXRefMapSet() {
		return limitsSysXRefMapSet;
	}

	public Long getSubProfileId() {
		return this.subProfileId;
	}

	public String getUpdateStatusIndicator() {
		return updateStatusIndicator;
	}

	public void setAANumber(String AANumber) {
		this.AANumber = AANumber;
	}

	public void setAccountDelinquentIndicator(String accountDelinquentIndicator) {
		this.accountDelinquentIndicator = accountDelinquentIndicator;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setAccountSequence(Long accountSequence) {
		this.accountSequence = accountSequence;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setChangeIndicator(String changeIndicator) {
		this.changeIndicator = changeIndicator;
	}

	public void setCIFId(String CIFId) {
		this.CIFId = CIFId;
	}

	public void setCmsId(Long cmsId) {
		this.cmsId = cmsId;
	}

	public void setCmsLimitIds(Long[] cmsLimitIds) {
		this.cmsLimitIds = cmsLimitIds;
	}

	public void setCmsSubProfileId(Long cmsSubProfileId) {
		this.cmsSubProfileId = cmsSubProfileId;
	}

	public void setFacilitySequenceNumber(Long facilitySequenceNumber) {
		this.facilitySequenceNumber = facilitySequenceNumber;
	}

	public void setFacilityType(StandardCode facilityType) {
		this.facilityType = facilityType;
	}

	public void setFinalDisbursementDate(Date finalDisbursementDate) {
		this.finalDisbursementDate = finalDisbursementDate;
	}

	public void setFirstDisbursementDate(Date firstDisbursementDate) {
		this.firstDisbursementDate = firstDisbursementDate;
	}

	public void setHostAANumber(String hostAANumber) {
		this.hostAANumber = hostAANumber;
	}

	public void setLimitsSysXRefMapSet(Set limitsSysXRefMapSet) {
		this.limitsSysXRefMapSet = limitsSysXRefMapSet;
	}

	public void setSubProfileId(Long subProfileId) {
		this.subProfileId = subProfileId;
	}

	public void setUpdateStatusIndicator(String updateStatusIndicator) {
		this.updateStatusIndicator = updateStatusIndicator;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Account : ");
		buf.append("Account Number [").append(this.accountNumber).append("], ");
		buf.append("Account Type [").append(this.accountType).append("], ");
		buf.append("Account Sequence [").append(this.accountSequence).append("], ");
		buf.append("Account Status [").append(this.accountStatus).append("], ");
		buf.append("Host AA Number [").append(this.hostAANumber).append("], ");
		buf.append("CMS Limit Ids [").append(ArrayUtils.toString(cmsLimitIds)).append("]");

		return buf.toString();
	}

}
