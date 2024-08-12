/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Entity bean implementation for NPL Info entity.
 * 
 * @author $Author: lini $<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBNPLInfoBean implements INPLInfo, EntityBean {
	private static final long serialVersionUID = -100410375813778887L;

	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the NPL Info. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getNplID" };

	public abstract Long getNplID();

	public abstract void setNplID(Long nplID);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public Collection getFacilityTypeList() {
		return null;
	}

	public void setFacilityTypeList(Collection facilityTypeList) {
	}

	/**
	 * @see com.integrosys.cms.app.liquidation.bus.OBNPLInfo#getValue
	 */
	public INPLInfo getValue() {
		OBNPLInfo npl = new OBNPLInfo();
		AccessorUtil.copyValue(this, npl);
		return npl;
	}

	/**
	 * @see com.integrosys.cms.app.liquidation.bus.OBNPLInfo#setValue
	 */
	public void setValue(INPLInfo nplInfo) throws VersionMismatchException {
		checkVersionMismatch(nplInfo);
		AccessorUtil.copyValue(nplInfo, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this NPL Info.
	 * 
	 * @param nplInfo of type INPLInfo
	 * @throws com.integrosys.base.businfra.common.exception.VersionMismatchException
	 *         if the entity version is invalid
	 */
	private void checkVersionMismatch(INPLInfo nplInfo) throws VersionMismatchException {
		if (getVersionTime() != nplInfo.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + nplInfo.getVersionTime());
		}
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param nplInfo of type INPLInfo
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(INPLInfo nplInfo) throws CreateException {

		long pk = ICMSConstant.LONG_INVALID_VALUE;
		// Creation is thru batch job

		try {
			pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_NPL, true));
		}
		catch (Exception e) {
			CreateException ce = new CreateException("failed to retrieve sequence using [" + ICMSConstant.SEQUENCE_NPL
					+ "]; nested exception is " + e);
			ce.initCause(e);
			throw ce;
		}
		DefaultLogger.debug(this, "Creating NPL Info with ID: " + pk);
		setNplID(new Long(pk));

		AccessorUtil.copyValue(nplInfo, this, EXCLUDE_METHOD);

		if (isStaging()) {
			if ((nplInfo.getRefID() != ICMSConstant.LONG_INVALID_VALUE) && (nplInfo.getRefID() > 0)) {
				setRefID(nplInfo.getRefID());
			}
			else if ((nplInfo.getNplID() != null)
					&& (nplInfo.getNplID().longValue() != ICMSConstant.LONG_INVALID_VALUE)) {
				setRefID(nplInfo.getNplID().longValue());
			}
			else {
				setRefID(pk);
			}
		}
		else {
			setRefID(pk);
		}

		DefaultLogger.debug(this, "Returning EBNPLInfoBean " + pk);
		return new Long(pk);
	}

	protected boolean isStaging() {
		return false;
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * @param nplInfo of type INPLInfo
	 */
	public void ejbPostCreate(INPLInfo nplInfo) {
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}

	public abstract String getBcaRefNum();

	public abstract void setBcaRefNum(String bcaRefNum);

	public abstract String getLimitID();

	public abstract void setLimitID(String limitID);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract String getDelinquent();

	public abstract void setDelinquent(String delinquent);

	public abstract String getAccountNo();

	public abstract void setAccountNo(String accountNo);

	public abstract long getInterestinSuspense();

	public abstract void setInterestinSuspense(long interestinSuspense);

	public abstract Date getDateVehicleDisposed();

	public abstract void setDateVehicleDisposed(Date dateVehicleDisposed);

	public abstract Date getDateNPLRegularised();

	public abstract void setDateNPLRegularised(Date dateNPLRegularised);

	public abstract long getSpecProvisionChrgAcc();

	public abstract void setSpecProvisionChrgAcc(long specProvisionChrgAcc);

	public abstract Date getDateRepossesed();

	public abstract void setDateRepossesed(Date dateRepossesed);

	public abstract String getFacilityType();

	public abstract void setFacilityType(String facilityType);

	public abstract String getAccountStatus();

	public abstract void setAccountStatus(String accountStatus);

	public abstract long getPartPaymentReceived();

	public abstract void setPartPaymentReceived(long partPaymentReceived);

	public abstract Date getLatestDateDoubtful();

	public abstract void setLatestDateDoubtful(Date latestDateDoubtful);

	public abstract Date getFirstDateDoubtful();

	public abstract void setFirstDateDoubtful(Date firstDateDoubtful);

	public abstract Date getJudgementDate();

	public abstract void setJudgementDate(Date judgementDate);

	public abstract long getJudgementSum();

	public abstract void setJudgementSum(long judgementSum);

	public abstract Date getDateWriteOff();

	public abstract void setDateWriteOff(Date dateWriteOff);

	public abstract long getAmountWrittenOff();

	public abstract void setAmountWrittenOff(long amountWrittenOff);

	public abstract String getCivilSuitNo();

	public abstract void setCivilSuitNo(String civilSuitNo);

	public abstract long getMonthsInstalmentsArrears();

	public abstract void setMonthsInstalmentsArrears(long monthsInstalmentsArrears);

	public abstract long getMonthsInterestArrears();

	public abstract void setMonthsInterestArrears(long monthsInterestArrears);

	public abstract long getRefID();

	public abstract void setRefID(long refID);

	public abstract String getCarCode();

	public abstract void setCarCode(String carCode);

	public abstract Date getCarDate();

	public abstract void setCarDate(Date carDate);

	public abstract String getSettled();

	public abstract void setSettled(String settled);

	public abstract long getAmountPartialWrittenOff();

	public abstract void setAmountPartialWrittenOff(long amountPartialWrittenOff);

	public abstract Date getNplDate();

	public abstract void setNplDate(Date date);
}