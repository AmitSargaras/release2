package com.integrosys.cms.app.liquidation.bus;

import java.util.Collection;
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This Object represents NPL Info
 * 
 * @author $Author: lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBNPLInfo implements INPLInfo {

	private Long nplID;

	private String bcaRefNum;

	private String limitID;

	private String status;

	private String delinquent;

	private String accountNo;

	/*
	 * private boolean staffAccountFlag; private String judgementFlag ;
	 */
	private long interestinSuspense;

	private Date dateVehicleDisposed;

	private Date dateNPLRegularised;

	private long specProvisionChrgAcc;

	private Date dateRepossesed;

	private String facilityType;

	private String accountStatus;

	private long partPaymentReceived;

	private Date latestDateDoubtful;

	private Date firstDateDoubtful;

	private Date judgementDate;

	private long judgementSum;

	private Date dateWriteOff;

	private long amountWrittenOff;

	private String civilSuitNo;

	private long monthsInstalmentsArrears;

	private long monthsInterestArrears;

	private long versionTime;

	private String carCode;

	private Date carDate;

	private long refID;

	private String settled;

	private long amountPartialWrittenOff;

	private Date nplDate;

	private Collection facilityTypeList;

	public Long getNplID() {
		return nplID;
	}

	public void setNplID(Long nplID) {
		this.nplID = nplID;
	}

	public String getBcaRefNum() {
		return bcaRefNum;
	}

	public void setBcaRefNum(String bcaRefNum) {
		this.bcaRefNum = bcaRefNum;
	}

	public String getLimitID() {
		return limitID;
	}

	public void setLimitID(String limitID) {
		this.limitID = limitID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDelinquent() {
		return delinquent;
	}

	public void setDelinquent(String delinquent) {
		this.delinquent = delinquent;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/*
	 * public boolean isStaffAccountFlag() { return staffAccountFlag; }
	 * 
	 * public void setStaffAccountFlag(boolean staffAccountFlag) {
	 * this.staffAccountFlag = staffAccountFlag; }
	 * 
	 * public String getJudgementFlag() { return judgementFlag; }
	 * 
	 * public void setJudgementFlag(String judgementFlag) { this.judgementFlag =
	 * judgementFlag; }
	 */

	public long getInterestinSuspense() {
		return interestinSuspense;
	}

	public void setInterestinSuspense(long interestinSuspense) {
		this.interestinSuspense = interestinSuspense;
	}

	/**
	 * public Date getDateVehicleDisposed() { return dateVehicleDisposed; }
	 * 
	 * public void setDateVehicleDisposed(Date dateVehicleDisposed) {
	 * this.dateVehicleDisposed = dateVehicleDisposed; }
	 **/
	public Date getDateNPLRegularised() {
		return dateNPLRegularised;
	}

	public void setDateNPLRegularised(Date dateNPLRegularised) {
		this.dateNPLRegularised = dateNPLRegularised;
	}

	public long getSpecProvisionChrgAcc() {
		return specProvisionChrgAcc;
	}

	public void setSpecProvisionChrgAcc(long specProvisionChrgAcc) {
		this.specProvisionChrgAcc = specProvisionChrgAcc;
	}

	/*
	 * public Date getDateRepossesed() { return dateRepossesed; }
	 * 
	 * public void setDateRepossesed(Date dateRepossesed) { this.dateRepossesed
	 * = dateRepossesed; }
	 */
	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}

	public long getPartPaymentReceived() {
		return partPaymentReceived;
	}

	public void setPartPaymentReceived(long partPaymentReceived) {
		this.partPaymentReceived = partPaymentReceived;
	}

	public Date getLatestDateDoubtful() {
		return latestDateDoubtful;
	}

	public void setLatestDateDoubtful(Date latestDateDoubtful) {
		this.latestDateDoubtful = latestDateDoubtful;
	}

	/**
	 * public Date getFirstDateDoubtful() { return firstDateDoubtful; }
	 * 
	 * public void setFirstDateDoubtful(Date firstDateDoubtful) {
	 * this.firstDateDoubtful = firstDateDoubtful; }
	 **/
	public Date getJudgementDate() {
		return judgementDate;
	}

	public void setJudgementDate(Date judgementDate) {
		this.judgementDate = judgementDate;
	}

	public long getJudgementSum() {
		return judgementSum;
	}

	public void setJudgementSum(long judgementSum) {
		this.judgementSum = judgementSum;
	}

	public Date getDateWriteOff() {
		return dateWriteOff;
	}

	public void setDateWriteOff(Date dateWriteOff) {
		this.dateWriteOff = dateWriteOff;
	}

	public long getAmountWrittenOff() {
		return amountWrittenOff;
	}

	public void setAmountWrittenOff(long amountWrittenOff) {
		this.amountWrittenOff = amountWrittenOff;
	}

	/**
	 * public String getCivilSuitNo() { return civilSuitNo; }
	 * 
	 * public void setCivilSuitNo(String civilSuitNo) { this.civilSuitNo =
	 * civilSuitNo; }
	 **/
	public long getMonthsInstalmentsArrears() {
		return monthsInstalmentsArrears;
	}

	public void setMonthsInstalmentsArrears(long monthsInstalmentsArrears) {
		this.monthsInstalmentsArrears = monthsInstalmentsArrears;
	}

	public long getMonthsInterestArrears() {
		return monthsInterestArrears;
	}

	public void setMonthsInterestArrears(long monthsInterestArrears) {
		this.monthsInterestArrears = monthsInterestArrears;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBNPLInfo)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public String getCarCode() {
		return carCode;
	}

	public void setCarCode(String carCode) {
		this.carCode = carCode;
	}

	public Date getCarDate() {
		return carDate;
	}

	public void setCarDate(Date carDate) {
		this.carDate = carDate;
	}

	public long getRefID() {
		return refID;
	}

	public void setRefID(long refID) {
		this.refID = refID;
	}

	public String getSettled() {
		return settled;
	}

	public void setSettled(String settled) {
		this.settled = settled;
	}

	public long getAmountPartialWrittenOff() {
		return amountPartialWrittenOff;
	}

	public void setAmountPartialWrittenOff(long amountPartialWrittenOff) {
		this.amountPartialWrittenOff = amountPartialWrittenOff;
	}

	public Date getNplDate() {
		return nplDate;
	}

	public void setNplDate(Date date) {
		nplDate = date;
	}

	public Date getDateVehicleDisposed() {
		return dateVehicleDisposed;
	}

	public void setDateVehicleDisposed(Date dateVehicleDisposed) {
		this.dateVehicleDisposed = dateVehicleDisposed;
	}

	public Date getDateRepossesed() {
		return dateRepossesed;
	}

	public void setDateRepossesed(Date dateRepossesed) {
		this.dateRepossesed = dateRepossesed;
	}

	public Date getFirstDateDoubtful() {
		return firstDateDoubtful;
	}

	public void setFirstDateDoubtful(Date firstDateDoubtful) {
		this.firstDateDoubtful = firstDateDoubtful;
	}

	public String getCivilSuitNo() {
		return civilSuitNo;
	}

	public void setCivilSuitNo(String civilSuitNo) {
		this.civilSuitNo = civilSuitNo;
	}

	public Collection getFacilityTypeList() {
		return facilityTypeList;
	}

	public void setFacilityTypeList(Collection facilityTypeList) {
		this.facilityTypeList = facilityTypeList;
	}
}
