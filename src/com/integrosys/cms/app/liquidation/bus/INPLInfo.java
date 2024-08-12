/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.liquidation.bus;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * This interface represents NPL Info
 * 
 * @author $Author: lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface INPLInfo extends Serializable {
	public Long getNplID();

	public void setNplID(Long nplID);

	public String getBcaRefNum();

	public void setBcaRefNum(String bcaRefNum);

	public String getLimitID();

	public void setLimitID(String limitID);

	public String getStatus();

	public void setStatus(String status);

	public String getDelinquent();

	public void setDelinquent(String delinquent);

	public String getAccountNo();

	public void setAccountNo(String accountNo);

	/*
	 * public boolean isStaffAccountFlag(); public void
	 * setStaffAccountFlag(boolean staffAccountFlag); public String
	 * getJudgementFlag(); public void setJudgementFlag(String judgementFlag);
	 */
	public long getInterestinSuspense();

	public void setInterestinSuspense(long interestinSuspense);

	public Date getDateVehicleDisposed();

	public void setDateVehicleDisposed(Date dateVehicleDisposed);

	public Date getDateNPLRegularised();

	public void setDateNPLRegularised(Date dateNPLRegularised);

	public long getSpecProvisionChrgAcc();

	public void setSpecProvisionChrgAcc(long specProvisionChrgAcc);

	public Date getDateRepossesed();

	public void setDateRepossesed(Date dateRepossesed);

	public String getFacilityType();

	public void setFacilityType(String facilityType);

	public String getAccountStatus();

	public void setAccountStatus(String accountStatus);

	public long getPartPaymentReceived();

	public void setPartPaymentReceived(long partPaymentReceived);

	public Date getLatestDateDoubtful();

	public void setLatestDateDoubtful(Date latestDateDoubtful);

	public Date getFirstDateDoubtful();

	public void setFirstDateDoubtful(Date firstDateDoubtful);

	public Date getJudgementDate();

	public void setJudgementDate(Date judgementDate);

	public long getJudgementSum();

	public void setJudgementSum(long judgementSum);

	public Date getDateWriteOff();

	public void setDateWriteOff(Date dateWriteOff);

	public long getAmountWrittenOff();

	public void setAmountWrittenOff(long amountWrittenOff);

	public String getCivilSuitNo();

	public void setCivilSuitNo(String civilSuitNo);

	public long getMonthsInstalmentsArrears();

	public void setMonthsInstalmentsArrears(long monthsInstalmentsArrears);

	public long getMonthsInterestArrears();

	public void setMonthsInterestArrears(long monthsInterestArrears);

	public long getVersionTime();

	public void setVersionTime(long versionTime);

	public long getRefID();

	public void setRefID(long refID);

	public String getCarCode();

	public void setCarCode(String carCode);

	public Date getCarDate();

	public void setCarDate(Date carDate);

	public String getSettled();

	public void setSettled(String settled);

	public long getAmountPartialWrittenOff();

	public void setAmountPartialWrittenOff(long amountPartialWrittenOff);

	public Date getNplDate();

	public void setNplDate(Date date);

	public Collection getFacilityTypeList();

	public void setFacilityTypeList(Collection facilityTypeList);
}
