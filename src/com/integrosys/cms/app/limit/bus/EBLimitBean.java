/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitBean.java,v 1.34 2005/10/20 08:34:43 lyng Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import org.springframework.util.CollectionUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.batch.common.BatchResourceFactory;

/**
 * This entity bean represents the persistence for Limit.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.34 $
 * @since $Date: 2005/10/20 08:34:43 $ Tag: $Name: $
 */
public abstract class EBLimitBean implements EntityBean, ILimit {
	private static final long serialVersionUID = 8157311227383296759L;

	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_LIMIT;

	private static final String[] EXCLUDE_METHOD = new String[] { "getLimitID", "getLimitProfileID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBLimitBean() {
	}

	// ************ Non-persistence method *************
	// Getters
	/**
	 * Get Limit ID
	 * 
	 * @return long
	 */
	public long getLimitID() {
		if (null != getLimitPK()) {
			return getLimitPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get Limit Profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		if (null != getLimitProfileFK()) {
			return getLimitProfileFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get outer limit profile id.
	 * 
	 * @return long
	 */
	public long getOuterLimitProfileID() {
		return (getEBOuterLimitProfileID() != null ? getEBOuterLimitProfileID().longValue()
				: ICMSConstant.LONG_INVALID_VALUE);
	}

	/**
	 * Get Approved Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getApprovedLimitAmount() {
		String ccy = getCurrencyCode();
		double amt = getApprovedLimit();

		return new Amount(amt, ccy);
	}

	/**
	 * Get Activated Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getActivatedLimitAmount() {
		return getActivatedLimit() == null ? null
				: new Amount(getActivatedLimit(), new CurrencyCode(getCurrencyCode()));
	}

	/**
	 * Get Limit Activated Indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitActivatedInd() {
		String value = getLimitActivatedStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get Limit Advise Indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitAdviseInd() {
		String value = getLimitAdviseStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get Limit Committed Indicator
	 * 
	 * @return boolean
	 */
	public boolean getLimitCommittedInd() {
		String value = getLimitCommittedStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get Shared Limit Indicator
	 * 
	 * @return boolean
	 */
	public boolean getSharedLimitInd() {
		String value = getSharedLimitStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @return boolean
	 */
	public boolean getExistingInd() {
		String value = getExistingStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Check if the limit is zerorised.
	 * 
	 * @return boolean
	 */
	public boolean getIsLimitZerorised() {
		return getEBIsLimitZerorised() == null ? false : getEBIsLimitZerorised().equals(ICMSConstant.TRUE_VALUE);
	}

	/**
	 * Get All Collateral Allocations Not implemented. See
	 * <code>retrieveCollateralAllocations</code>
	 * 
	 * @return ICollateralAllocation[]
	 */
	public ICollateralAllocation[] getCollateralAllocations() {
		return null;
	}

	public ICollateralAllocation[] getNonDeletedCollateralAllocations() {
		return null;
	}

	/**
	 * Get all co-borrower limits. Not implemented. See
	 * <code>retrieveCoBorrowerLimits</code>
	 * 
	 * @return ICoBorrowerLimit[]
	 */
	public ICoBorrowerLimit[] getCoBorrowerLimits() {
		return null;
	}

	/**
	 * Get all co-borrower limits whose status is not DELETED.
	 * 
	 * @return ICoBorrowerLimit[]
	 */
	public ICoBorrowerLimit[] getNonDeletedCoBorrowerLimits() {
		return null;
	}

	public boolean getIsDAPError() {
		return false;
	}

	/**
	 * Get operational limit if the limit is tied to commodities.
	 * 
	 * @return Amount
	 */
	public Amount getOperationalLimit() {
		if (getEBOperationalLimit() != null) {
			return new Amount(getEBOperationalLimit(), new CurrencyCode(getCurrencyCode()));
		}
		return null;
	}

	/**
	 * Get the required security coverage percentage
	 * 
	 * @return float
	 */
	public String getRequiredSecurityCoverage() {					// Shiv 190911
		return getEBRequiredSecurityCoverage() == null ? "0"
				: getEBRequiredSecurityCoverage().toString();
	}

	/**
	 * Get the actual security coverage percentage
	 * 
	 * @return float
	 */
	public float getActualSecurityCoverage() {
		return getEBActualSecurityCoverage() == null ? ICMSConstant.FLOAT_INVALID_VALUE : getEBActualSecurityCoverage()
				.floatValue();
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimit#getActualSecCoverageAmt
	 */
	public Amount getActualSecCoverageAmt() {
		String ccy = getCurrencyCode();
		BigDecimal amt = getEBActualSecCoverageAmt();

		if (amt != null && ccy != null) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimit#setActualSecCoverageAmt
	 */
	public void setActualSecCoverageAmt(Amount value) {
		if (null != value) {
			setEBActualSecCoverageAmt(value.getAmountAsBigDecimal());
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimit#getActualSecCoverageOMVAmt
	 */
	public Amount getActualSecCoverageOMVAmt() {
		String ccy = getCurrencyCode();
		BigDecimal amt = getEBOMVSecCoverageAmt();

		if (amt != null && ccy != null) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimit#setActualSecCoverageOMVAmt
	 */
	public void setActualSecCoverageOMVAmt(Amount value) {
		if (null != value) {
			setEBOMVSecCoverageAmt(value.getAmountAsBigDecimal());
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimit#getActualSecCoverageFSVAmt
	 */
	public Amount getActualSecCoverageFSVAmt() {
		String ccy = getCurrencyCode();
		BigDecimal amt = getEBFSVSecCoverageAmt();

		if (amt != null && ccy != null) {

			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.ILimit#setActualSecCoverageFSVAmt
	 */
	public void setActualSecCoverageFSVAmt(Amount value) {
		if (null != value) {
			setEBFSVSecCoverageAmt(value.getAmountAsBigDecimal());
		}
	}

	/**
	 * Get the limit system x-ref Not implemented. see
	 * <code>retrieveLimitSysXRef()</code>
	 * 
	 * @return ILimitSysXRef[]
	 */
	public ILimitSysXRef[] getLimitSysXRefs() {
		return null;
	}
	
	public ILimitCovenant[] getLimitCovenant() {
		return null;
	}

	/**
	 * Get Booking Location
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getBookingLocation() {
		String country = getBookingCountry();
		String org = getBookingOrganisation();

		OBBookingLocation ob = new OBBookingLocation();
		ob.setCountryCode(country);
		ob.setOrganisationCode(org);

		return ob;
	}

	public String getProductCode() {
		return null;
	}

	public abstract String getFacilityCode();

	public abstract long getFacilitySequence();

	public abstract String getLimitProfileReferenceNumber();

	public abstract String getLosLimitRef();

	public abstract void setFacilityCode(String facilityCode);

	public abstract void setFacilitySequence(long facilitySequence);

	public abstract void setLimitProfileReferenceNumber(String limitProfileReferenceNumber);

	public abstract void setLosLimitRef(String losLimitRef);

	// Setters
	/**
	 * Set Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitID(long value) {
		setLimitPK(new Long(value));
	}

	/**
	 * Set Limit Profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value) {
		setLimitProfileFK(new Long(value));
	}

	public void setOuterLimitProfileID(long value) {
		setEBOuterLimitProfileID(value == ICMSConstant.LONG_INVALID_VALUE ? null : new Long(value));
	}

	/**
	 * Check if inner and outer limit are of the same BCA.
	 * 
	 * @return boolean
	 */
	public boolean getIsInnerOuterSameBCA() {
		return true;
	}

	/**
	 * Set Approved Limit Amount. Currency code to be persisted is based on the
	 * currency from Approved Limit
	 * 
	 * @param value is of type Amount
	 */
	public void setApprovedLimitAmount(Amount value) {
		if (null != value) {
			setCurrencyCode(value.getCurrencyCode());
			setApprovedLimit(value.getAmount());
		}
		else {
			// setCurrencyCode(null);
			setApprovedLimit(0);
		}
	}

	/**
	 * Set Activated Limit Amount. Currency code is ignored. Currency code used
	 * is based on approved limit amount.
	 * 
	 * @param value is of type Amount
	 */
	public void setActivatedLimitAmount(Amount value) {
		setActivatedLimit(value == null ? null : value.getAmountAsBigDecimal());
		// no need to set the currency code as it is from approved limit
		// currency.
	}

	/**
	 * Set Limit Activated Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitActivatedInd(boolean value) {
		if (true == value) {
			setLimitActivatedStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setLimitActivatedStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set Limit Advise Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitAdviseInd(boolean value) {
		if (true == value) {
			setLimitAdviseStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setLimitAdviseStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set Limit Committed Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setLimitCommittedInd(boolean value) {
		if (true == value) {
			setLimitCommittedStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setLimitCommittedStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set Shared Limit Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setSharedLimitInd(boolean value) {
		if (true == value) {
			setSharedLimitStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setSharedLimitStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @param value is of type boolean
	 */
	public void setExistingInd(boolean value) {
		if (true == value) {
			setExistingStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setExistingStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set an indicator whether the limit is zerorised.
	 * 
	 * @param isLimitZerorised of type boolean
	 */
	public void setIsLimitZerorised(boolean isLimitZerorised) {
		setEBIsLimitZerorised(isLimitZerorised ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Set All Collateral Allocations Not implemented.
	 * 
	 * @param value is of type ICollateralAllocation[]
	 */
	public void setCollateralAllocations(ICollateralAllocation[] value) {
		// do nothing
	}

	/**
	 * Set all co-borrower limits. Not implemented. See
	 * <code>updateCoBorrowerLimits</code>
	 * 
	 * @param value is of type ICoBorrowerLimit[]
	 */
	public void setCoBorrowerLimits(ICoBorrowerLimit[] value) {
		// do nothing
	}

	/**
	 * Set the limit system x-ref Not implemented. see
	 * <code>updateLimitSysXRef</code>
	 * 
	 * @param value is of type ILimitSysXRef[]
	 */
	public void setLimitSysXRefs(ILimitSysXRef[] value) {
		// do nothing
	}
	
	public void setLimitCovenant(ILimitCovenant[] value) {
		// do nothing
	}

	public void setIsDAPError(boolean isDAPError) {
	}

	/**
	 * Set Booking Location
	 * 
	 * @param value is of type IBookingLocation
	 */
	public void setBookingLocation(IBookingLocation value) {
		if (null != value) {
			setBookingCountry(value.getCountryCode());
			setBookingOrganisation(value.getOrganisationCode());
		}
		else {
			setBookingCountry(null);
			setBookingOrganisation(null);
		}
	}

	/**
	 * Set operational limit if the limit is tied to commodities.
	 * 
	 * @param operationalLimit of type Amount
	 */
	public void setOperationalLimit(Amount operationalLimit) {
		setEBOperationalLimit(operationalLimit == null ? null : operationalLimit.getAmountAsBigDecimal());
	}

	/**
	 * Set the required security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setRequiredSecurityCoverage(String value) {
		setEBRequiredSecurityCoverage(value == "" ? "0" : value.toString());
	}

	/**
	 * Set the actual security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setActualSecurityCoverage(float value) {
		setEBActualSecurityCoverage(value == ICMSConstant.FLOAT_INVALID_VALUE ? null : new BigDecimal(value));
	}

	public void setIsChanged(boolean isChanged) {

	}

	public boolean getIsChanged() {
		return false;
	}

	public Set getCoBorrowerLimitsSet() {
		return null;
	}

	public Set getCollateralAllocationsSet() {
		return null;
	}

	public Set getLimitSysXRefsSet() {
		return null;
	}
	
	public Set getLimitCovenantSet() {
		return null;
	}

	public void setCoBorrowerLimitsSet(Set coBorrowerLimitsSet) {
	}

	public void setCollateralAllocationsSet(Set collateralAllocationsSet) {
	}

	public void setLimitSysXRefsSet(Set limitSysXRefsSet) {
	}
	
	public void setLimitCovenantSet(Set limitCovenantSet) {
	}

	public void setIsZerorisedChanged(boolean isChanged) {

	}

	public boolean getIsZerorisedChanged() {
		return false;
	}

	public void setIsZerorisedDateChanged(boolean isChanged) {

	}

	public boolean getIsZerorisedDateChanged() {
		return false;
	}

	public void setIsZerorisedReasonChanged(boolean isChanged) {

	}

	public boolean getIsZerorisedReasonChanged() {
		return false;
	}

	public Amount getDrawingLimitAmount() {
		if (getDrawingLimit() != null) {
			return new Amount(getDrawingLimit().doubleValue(), getCurrencyCode());
		}
		else {
			return null;
		}
	}

	public void setDrawingLimitAmount(Amount drawingLimitAmount) {
		if (drawingLimitAmount != null) {
			setDrawingLimit(new Double(drawingLimitAmount.getAmountAsDouble()));
		}
	}

	public Amount getOutstandingAmount() {
		if (getOutstandingAmt() != null) {
			return new Amount(getOutstandingAmt().doubleValue(), getCurrencyCode());
		}
		else {
			return null;
		}
	}

	public void setOutstandingAmount(Amount outstandingAmount) {
		if (outstandingAmount != null) {
			setOutstandingAmt(new Double(outstandingAmount.getAmountAsDouble()));
		}
	}

	// ************** CMR methods ***************
	public abstract Collection getCMRCollateralAllocations();

	public abstract Collection getCMRLimitSysXRefs();
	
	//Specific Covenant
	public abstract Collection getCMRLimitCovenant();


	// Setters
	public abstract void setCMRCollateralAllocations(Collection value);

	public abstract void setCMRLimitSysXRefs(Collection value);
	
	//Specific Covenant
	public abstract void setCMRLimitCovenant(Collection value);

	// ********************** Abstract Methods **********************

	// Getters
	public abstract String getCurrencyCode();

	public abstract Long getLimitPK();

	public abstract Long getLimitProfileFK();

	public abstract Long getEBOuterLimitProfileID();

	public abstract double getApprovedLimit();

	public abstract BigDecimal getActivatedLimit();

	public abstract String getLimitActivatedStr();

	public abstract String getLimitAdviseStr();

	public abstract String getLimitCommittedStr();

	public abstract String getSharedLimitStr();

	public abstract String getExistingStr();

	public abstract String getEBIsLimitZerorised();

	public abstract String getBookingCountry();

	public abstract String getBookingOrganisation();

	public abstract BigDecimal getEBOperationalLimit();

	public abstract String getEBRequiredSecurityCoverage();

	public abstract BigDecimal getEBActualSecurityCoverage();

	public abstract BigDecimal getEBActualSecCoverageAmt();

	public abstract BigDecimal getEBOMVSecCoverageAmt();

	public abstract BigDecimal getEBFSVSecCoverageAmt();

	public abstract long getVersionTime();

	// Setters
	public abstract void setCurrencyCode(String value);

	public abstract void setLimitPK(Long value);

	public abstract void setLimitProfileFK(Long value);

	public abstract void setEBOuterLimitProfileID(Long value);

	public abstract void setApprovedLimit(double value);

	public abstract void setActivatedLimit(BigDecimal value);

	public abstract void setLimitActivatedStr(String value);

	public abstract void setLimitAdviseStr(String value);

	public abstract void setLimitCommittedStr(String value);

	public abstract void setSharedLimitStr(String value);

	public abstract void setExistingStr(String value);

	public abstract void setEBIsLimitZerorised(String eBIsLimitZerorised);

	public abstract void setBookingCountry(String value);

	public abstract void setBookingOrganisation(String value);

	public abstract void setEBOperationalLimit(BigDecimal ebOperationalLimit);

	public abstract void setEBRequiredSecurityCoverage(String ebRequiredSecurityCoverage);

	public abstract void setEBActualSecurityCoverage(BigDecimal ebActualSecurityCoverage);

	public abstract void setEBActualSecCoverageAmt(BigDecimal value);

	public abstract void setEBOMVSecCoverageAmt(BigDecimal value);

	public abstract void setEBFSVSecCoverageAmt(BigDecimal value);

	public abstract Double getDrawingLimit();

	public abstract void setDrawingLimit(Double drawingLimit);

	public abstract Double getOutstandingAmt();

	public abstract void setOutstandingAmt(Double outstandingAmt);

	// ************************ ejbCreate methods ********************

	/**
	 * Create a Limit Profile
	 * 
	 * @param value is the ILimit object
	 * @return Long the primary key
	 */
	public Long ejbCreate(ILimit value) throws CreateException {
		if (null == value) {
			throw new CreateException("ILimit is null!");
		}
		try {
			long limitProfileID = value.getLimitProfileID();
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == limitProfileID) {
				throw new CreateException("Limit Profile ID is uninitialised: " + limitProfileID);
			}

			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));

			setLimitID(pk);
			setLimitProfileID(limitProfileID);
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			if ((value.getLimitRef() == null) || value.getLimitRef().trim().equals("")) {
				setLimitRef(String.valueOf(pk));
			}

			setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		}
		catch (CreateException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Create a Limit Profile
	 * 
	 * @param value is the ILimit object
	 */
	public void ejbPostCreate(ILimit value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return ILimit
	 * @throws LimitException on error
	 */
	public ILimit getValue() throws LimitException {
		try {
			OBLimit value = new OBLimit();
			AccessorUtil.copyValue(this, value);

			value.setCollateralAllocations(retrieveCollateralAllocations());
			value.setCoBorrowerLimits(retrieveCoBorrowerLimits());
			value.setLimitSysXRefs(retrieveLimitSysXRefs());
			value.setCoBorrowerDetails(getCoBorrowerDetails());
			value.setLimitCovenant(retrieveLimitCovenant());
			return value;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value is of type ILimit
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws LimitException on error
	 */
	public ILimit setValue(ILimit value) throws LimitException, ConcurrentUpdateException {
		long beanVer = value.getVersionTime();
		long currentVer = getVersionTime();
		if (beanVer != currentVer) {
			throw new ConcurrentUpdateException("Version mismatch!");
		}
		try {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			value = updateDependants(value);
			setVersionTime(VersionGenerator.getVersionNumber());
			return value;
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Set an object representation into persistance
	 * 
	 * @param value is of type ILimit
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws LimitException on error
	 */
	public ILimit updateOperationalLimit(ILimit value) throws ConcurrentUpdateException, LimitException {
		long beanVer = value.getVersionTime();
		long currentVer = this.getVersionTime();
		if (beanVer != currentVer) {
			throw new ConcurrentUpdateException("Version mismatch!");
		}
		try {
			setOperationalLimit(value.getOperationalLimit());
			long versionTime = VersionGenerator.getVersionNumber();
			setVersionTime(versionTime);
			value.setVersionTime(versionTime);
			return value;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type ILimit
	 * @throws LimitException
	 * @throws ConcurrentUpdateException on error
	 */
	public void createDependants(ILimit value, long limitPK) throws LimitException, ConcurrentUpdateException {
		try {
			// set the foreign key for limitXref and limit sec map
			ICollateralAllocation[] alloc = value.getCollateralAllocations();
			if (alloc != null) {
				for (int i = 0; i < alloc.length; i++) {
					ICollateralAllocation next = alloc[i];
					next.setLimitID(limitPK);
				}
			}

			ILimitSysXRef[] xref = value.getLimitSysXRefs();
			List xrefList = new ArrayList();
			if (xref != null) {
				for (int i = 0; i < xref.length; i++) {
					ILimitSysXRef next = xref[i];
					next.setLimitFk(limitPK);
					xrefList.add(next);
				}
			}
			
			ILimitCovenant[] cov = value.getLimitCovenant();
			List covList = new ArrayList();
			if (cov != null) {
				for (int i = 0; i < cov.length; i++) {
					ILimitCovenant next = cov[i];
					next.setFacilityFK(limitPK);
					covList.add(next);
				}
			}

			createLimitSysXRefs(xrefList);
			createCollateralAllocation(alloc);
			createCoBorrowerDetails(value.getCoBorrowerDetails(), value.getLimitProfileID(), value.getLimitID());
			createLimitCovenant(covList);
			
			xrefList.clear();
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new LimitException("Error when create limit dependants");
		}
	}

	// ************************** Private methods ***************************
	/**
	 * Method to update child dependants
	 */
	private ILimit updateDependants(ILimit value) throws LimitException {
		try {
			if (null == value) {
				throw new LimitException("ILimit is null!");
			}
			// updateCoBorrowerLimits(value.getCoBorrowerLimits());
			// updateLimitSysXRefs(value.getLimitSysXRefs());
			// value.setCollateralAllocations(updateCollateralAllocation(value.getCollateralAllocations()));

			//Removing comment Govind :Due to not update actual CMS_LIMIT_SECURITY_MAP from CMS_STAGE_LIMIT_SECURITY_MAP			
			updateLimitSysXRefs(value.getLimitSysXRefs());
			updateLimitCovenant(value.getLimitCovenant());
			value.setCollateralAllocations(updateCollateralAllocation(value.getCollateralAllocations())); 
			updateCoBorrowerDetails(value.getCoBorrowerDetails(), value.getLimitProfileID(), value.getLimitID());
			return value;

		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Unknown Exception: " + e.toString());
		}
	}

	/**
	 * Method to retrieve CoBorrower limits
	 */
	private ICoBorrowerLimit[] retrieveCoBorrowerLimits() throws LimitException {
		try {
			EBCoBorrowerLimitLocalHome home = getEBLocalHomeCoBorrowerLimit();
			Collection c = home.findByCoBorrowerOuterLimitID(new Long(getLimitID()));

			Iterator i = c.iterator();
			ArrayList aList = new ArrayList(c.size());
			while (i.hasNext()) {
				EBCoBorrowerLimitLocal local = (EBCoBorrowerLimitLocal) i.next();
				ICoBorrowerLimit limit = local.getValue();
				limit.setOuterLimitBookingLoc(this.getBookingLocation());
				limit.setProductDesc(this.getProductDesc());
				aList.add(limit);
			}

			return (ICoBorrowerLimit[]) aList.toArray(new ICoBorrowerLimit[0]);
		}
		catch (LimitException e) {
			throw e;
		}
		catch (FinderException e) {
			return null;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to retrieve collateral allocations
	 */
	private ICollateralAllocation[] retrieveCollateralAllocations() throws LimitException {
		try {
			Collection c = getCMRCollateralAllocations();
			if ((null == c) || (c.size() == 0)) {
				return null;
			}
			else {
				HashMap hashmap = new HashMap();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBCollateralAllocationLocal local = (EBCollateralAllocationLocal) i.next();
					ICollateralAllocation ob = local.getValue();
					Long colId = new Long(ob.getCollateral().getCollateralID());
					ICollateralAllocation temp = (ICollateralAllocation) hashmap.get(colId);
					if ((temp != null) && // temp.getSCILimitSecMapID() >
							// ob.getSCILimitSecMapID() &&
							ICMSConstant.HOST_STATUS_DELETE.equals(ob.getHostStatus())) {
						continue;
					}
					if (temp != null
							&& colId.equals(new Long(temp.getCollateral().getCollateralID())) ) {
						if (ob.getChargeID() > temp.getChargeID()) {
							hashmap.put(colId, ob);
						}
					} else {
						hashmap.put(colId, ob);
					}
						
				}

				return (ICollateralAllocation[]) hashmap.values().toArray(new ICollateralAllocation[0]);
			}
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to create Collateral Allocations. This is to be implemented only
	 * by actual bean. Staging bean should provide a non-implementation.
	 * 
	 * @param allocList is of type ICollateralAllocation[]
	 * @throws LimitException on errors
	 */
	protected void createCollateralAllocation(ICollateralAllocation[] allocList) throws LimitException {
		if ((null == allocList) || (allocList.length == 0)) {
			DefaultLogger.info(this, "No Collateral Allocations to be created.");
			return; // do nothing
		}
		Collection c = getCMRCollateralAllocations();
		String sourceId = getLimitRef();
		EBCollateralAllocationLocalHome home = getEBLocalHomeCollateralAllocation();

		try {
			for (int i = 0; i < allocList.length; i++) {
				ICollateralAllocation ob = allocList[i];
				ob.setSourceLmtId(sourceId);
				EBCollateralAllocationLocal local = home.create(ob);

				c.add(local);
			}
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to update refs
	 */
	private void updateLimitSysXRefs(ILimitSysXRef[] refs) throws LimitException {
		try {
			Collection c = getCMRLimitSysXRefs();

			if (null == refs) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all Limits
					deleteLimitSysXRefs(new ArrayList(c));
				}
			}
			else if ((null == c) || (c.size() == 0)) {
				// create new records
				createLimitSysXRefs(Arrays.asList(refs));
			}
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBLimitSysXRefLocal local = (EBLimitSysXRefLocal) i.next();

					// long xrefID = local.getXRefID();
					long sid = local.getSID();
					boolean update = false;

					for (int j = 0; j < refs.length; j++) {
						ILimitSysXRef newOB = refs[j];

						// if(newOB.getXRefID() == xrefID) {
						if (newOB.getSID() == sid) {
							// perform update
							local.setValue(newOB);
							update = true;
							break;
						}
					}
					if (!update) {
						// add for delete
						deleteList.add(local);
					}
				}
				// next identify records for add
				for (int j = 0; j < refs.length; j++) {
					i = c.iterator();
					ILimitSysXRef newOB = refs[j];
					boolean found = false;

					while (i.hasNext()) {
						EBLimitSysXRefLocal local = (EBLimitSysXRefLocal) i.next();
						// long id = local.getXRefID();
						long sid = local.getSID();

						// if(newOB.getXRefID() == id) {
						if (newOB.getSID() == sid) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteLimitSysXRefs(deleteList);
				createLimitSysXRefs(createList);
			}
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Exception: " + e.toString());
		}
	}
	
	/**
	 * Method to update covenant
	 */
	private void updateLimitCovenant(ILimitCovenant[] refs) throws LimitException {
		try {
			Collection c = getCMRLimitCovenant();

			if (null == refs) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all Limits
					deleteLimitCovenant(new ArrayList(c));
				}
			}
			else if ((null == c) || (c.size() == 0)) {
				// create new records
				createLimitCovenant(Arrays.asList(refs));
			}
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBLimitCovenantLocal local = (EBLimitCovenantLocal) i.next();

					// long xrefID = local.getXRefID();
					long cid = local.getCovenantId();
					boolean update = false;

					for (int j = 0; j < refs.length; j++) {
						ILimitCovenant newOB = refs[j];

						// if(newOB.getXRefID() == xrefID) {
						if (newOB.getCovenantId() == cid) {
							// perform update
							local.setValue(newOB);
							update = true;
							break;
						}
					}
					if (!update) {
						// add for delete
						deleteList.add(local);
					}
				}
				// next identify records for add
				for (int j = 0; j < refs.length; j++) {
					i = c.iterator();
					ILimitCovenant newOB = refs[j];
					boolean found = false;

					while (i.hasNext()) {
						EBLimitCovenantLocal local = (EBLimitCovenantLocal) i.next();
						// long id = local.getXRefID();
						long cid = local.getCovenantId();

						// if(newOB.getXRefID() == id) {
						if (newOB.getCovenantId() == cid) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteLimitCovenant(deleteList);
				createLimitCovenant(createList);
			}
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Exception: " + e.toString());
		}
	}

	private ICollateralAllocation[] updateCollateralAllocation(ICollateralAllocation[] alloc) throws LimitException {
		try {
			ArrayList tmpList = new ArrayList();
			Collection c = getCMRCollateralAllocations();
			if (c != null) {
				Iterator iter = c.iterator();
				outer1: while (iter.hasNext()) {
					EBCollateralAllocationLocal local = (EBCollateralAllocationLocal) (iter.next());
					long nextColId = local.getValue().getCollateral().getCollateralID();
					if (alloc != null) {
						for (int j = 0; j < alloc.length; j++) {
							if (ICMSConstant.HOST_STATUS_DELETE.equals(local.getHostStatus())
									|| (nextColId == alloc[j].getCollateral().getCollateralID())) {
								// the collateral allocation is still there
								continue outer1;
							}
						}
					}

					// the original collateral allocation is not found in the
					// new list,
					// implies the collateral allocation is removed
					local.delete();
					tmpList.add(local.getValue());
				}
			}

			if (alloc != null) {
				List toAddList = new ArrayList();
				String sourceId = getLimitRef();
				outer2: for (int i = 0; i < alloc.length; i++) {
					ICollateralAllocation nextAlloc = alloc[i];
					if (!ICMSConstant.HOST_STATUS_DELETE.equals(nextAlloc.getHostStatus())) {
						if (c != null) {
							Iterator iter = c.iterator();
							while (iter.hasNext()) {
								EBCollateralAllocationLocal local = (EBCollateralAllocationLocal) (iter.next());
								long nextColId = local.getValue().getCollateral().getCollateralID();
								if ((nextAlloc.getCollateral().getCollateralID() == nextColId)
										&& !ICMSConstant.HOST_STATUS_DELETE.equals(local.getValue().getHostStatus())) {
									toAddList.add(nextAlloc);

									continue outer2;
								}
							}
						}
					}
					// the collateral allocation is not found in the old list,
					// so create a new collateral allocation
					nextAlloc.setSourceLmtId(sourceId);
					toAddList.add(nextAlloc);
				}

				EBCollateralAllocationLocalHome home = this.getEBLocalHomeCollateralAllocation();
				for (int i = 0; i < toAddList.size(); i++) {
					ICollateralAllocation newAlloc = (ICollateralAllocation) (toAddList.get(i));
					EBCollateralAllocationLocal local = home.create(newAlloc);
					c.add(local);
					// update new collateral allocation with correct charge ID
					newAlloc.setChargeID(local.getValue().getChargeID());

					tmpList.add(newAlloc);
				}
			}
			return (ICollateralAllocation[]) tmpList.toArray(new ICollateralAllocation[0]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Exception updating collateral");
		}
	}

	/**
	 * Method to add refs
	 */
	private void createLimitSysXRefs(List createList) throws LimitException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRLimitSysXRefs();
		Iterator i = createList.iterator();
		try {
			EBLimitSysXRefLocalHome home = getEBLocalHomeLimitSysXRef();
			while (i.hasNext()) {
				ILimitSysXRef ob = (ILimitSysXRef) i.next();
				EBLimitSysXRefLocal local = home.create(ob);

				c.add(local);
			}
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to delete refs
	 */
	private void deleteLimitSysXRefs(List deleteList) throws LimitException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRLimitSysXRefs();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBLimitSysXRefLocal local = (EBLimitSysXRefLocal) i.next();

				// c.remove(local);
				// local.remove();

				// do soft delete
				local.setStatus(ICMSConstant.STATE_DELETED);
			}
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to retrieve CoBorrower refs
	 */
	private ILimitSysXRef[] retrieveLimitSysXRefs() throws LimitException {
		try {
			Collection c = getCMRLimitSysXRefs();
			if ((null == c) || (c.size() == 0)) {
				return null;
			}
			else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBLimitSysXRefLocal local = (EBLimitSysXRefLocal) i.next();

					// filter off deleted ones
					if (!(ICMSConstant.STATE_DELETED.equals(local.getStatus()))) {
						ILimitSysXRef ob = local.getValue();
						aList.add(ob);
					}
				}

				return (ILimitSysXRef[]) aList.toArray(new ILimitSysXRef[0]);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Exception!", e);
		}
	}
	
	/**
	 * Method to add covenant
	 */
	private void createLimitCovenant(List createList) throws LimitException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRLimitCovenant();
		Iterator i = createList.iterator();
		try {
			EBLimitCovenantLocalHome home = getEBLocalHomeLimitCovenant();
			while (i.hasNext()) {
				ILimitCovenant ob = (ILimitCovenant) i.next();
				EBLimitCovenantLocal local = home.create(ob);

				c.add(local);
			}
		}
		catch (LimitException e) {
			throw e;
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to delete Covenant
	 */
	private void deleteLimitCovenant(List deleteList) throws LimitException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRLimitCovenant();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBLimitCovenantLocal local = (EBLimitCovenantLocal) i.next();
			}
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * Method to retrieve CoBorrower refs
	 */
	private ILimitCovenant[] retrieveLimitCovenant() throws LimitException {
		try {
			Collection c = getCMRLimitCovenant();
			if ((null == c) || (c.size() == 0)) {
				return null;
			}
			else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBLimitCovenantLocal local = (EBLimitCovenantLocal) i.next();
				}

				return (ILimitCovenant[]) aList.toArray(new ILimitCovenant[0]);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new LimitException("Caught Exception!", e);
		}
	}

	
	public abstract Collection getCoBorrowerDetailsCMR();
	
	public abstract void setCoBorrowerDetailsCMR(Collection stock);
		
	public List<IFacilityCoBorrowerDetails> getCoBorrowerDetails() {
		Collection<?> cmr = getCoBorrowerDetailsCMR();
		if (CollectionUtils.isEmpty(cmr))
			return null;
		List<IFacilityCoBorrowerDetails> list = new ArrayList<IFacilityCoBorrowerDetails>();
		
		try {
			Iterator<?> iter = cmr.iterator();
			while (iter.hasNext()) {
				EBFacilityCoBorrowerDetailsLocal local = (EBFacilityCoBorrowerDetailsLocal) iter.next();
				IFacilityCoBorrowerDetails coBorrowerDetails = local.getValue();
				list.add(coBorrowerDetails);
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception retrieving Facility Co-Borrower Details", e);
			//TODO throw Exception
		}
		return list;
	}
	
	public void setCoBorrowerDetails(List<IFacilityCoBorrowerDetails> coBorrowerDetailsList) {
		
	}
	
	protected EBFacilityCoBorrowerDetailsLocalHome getEBFacilityCoBorrowerDetailsLocalHome() throws CustomerException {
		EBFacilityCoBorrowerDetailsLocalHome home = (EBFacilityCoBorrowerDetailsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_FACILITY_CO_BORROWER_DETAILS_JNDI, EBFacilityCoBorrowerDetailsLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBFacilityCoBorrowerDetailsLocalHome is null!");
		}
	}
	
	private void updateCoBorrowerDetails(List<IFacilityCoBorrowerDetails> coBorrowerDetailsList, long mainProfileId, long limitId) throws CustomerException {
		try {
			Collection cmr = getCoBorrowerDetailsCMR();

			if (coBorrowerDetailsList == null) {
				if (CollectionUtils.isEmpty(cmr)) {
					return;
				} else {
					deleteCoBorrowerDetails(new ArrayList(cmr));
				}
			} else if (CollectionUtils.isEmpty(cmr)) {
				createCoBorrowerDetails(coBorrowerDetailsList, mainProfileId, limitId);
			} else {
				Iterator iterator = cmr.iterator();
				List<IFacilityCoBorrowerDetails> createList = new ArrayList<IFacilityCoBorrowerDetails>();
				List<EBFacilityCoBorrowerDetailsLocal> deleteList = new ArrayList<EBFacilityCoBorrowerDetailsLocal>();

				while (iterator.hasNext()) {
					EBFacilityCoBorrowerDetailsLocal local = (EBFacilityCoBorrowerDetailsLocal) iterator.next();
					boolean update = false;

					for (IFacilityCoBorrowerDetails coBorrowerDetails : coBorrowerDetailsList) {
						if (coBorrowerDetails.getId() == local.getCoBorrowerId()) {
							local.setValue(coBorrowerDetails);
							update = true;
							break;
						}
					}
					if (!update) {
						deleteList.add(local);
					}
				}

				for (IFacilityCoBorrowerDetails coBorrowerDetails : coBorrowerDetailsList) {
					iterator = cmr.iterator();
					boolean found = false;

					while (iterator.hasNext()) {
						EBFacilityCoBorrowerDetailsLocal local = (EBFacilityCoBorrowerDetailsLocal) iterator.next();
						if (coBorrowerDetails.getId() == local.getCoBorrowerId()) {
							found = true;
							break;
						}
					}
					if (!found) {
						createList.add(coBorrowerDetails);
					}
				}
				deleteCoBorrowerDetails(deleteList);
				createCoBorrowerDetails(createList, mainProfileId, limitId);
			}
		} catch (CustomerException e) {
			DefaultLogger.error(this, "Exception While updating Facility Co-Borrower Details", e);
			throw e;
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception While updating Facility Co-Borrower Details", e);
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}
	
	private void deleteCoBorrowerDetails(List<EBFacilityCoBorrowerDetailsLocal> coBorrowerDetailsList) {
		if (CollectionUtils.isEmpty(coBorrowerDetailsList)) {
			return;
		}

		Collection cmr = getCoBorrowerDetailsCMR();
		Iterator<EBFacilityCoBorrowerDetailsLocal> iterator = coBorrowerDetailsList.iterator();
		while (iterator.hasNext()) {
			EBFacilityCoBorrowerDetailsLocal local = iterator.next();
			cmr.remove(local);
			try {
				local.remove();
			} catch (EJBException e) {
				DefaultLogger.error(this, "Exception while deleting Facility Co-Borrower Details item(s)", e);
				throw new CustomerException(e.getMessage(), e);
			} catch (RemoveException e) {
				DefaultLogger.error(this, "Exception while deleting Facility Co-Borrower Details item(s)", e);
				throw new CustomerException(e.getMessage(), e);
			}
		}
	}
	
	private void createCoBorrowerDetails(List<IFacilityCoBorrowerDetails> coBorrowerDetailsList, long mainProfileId, long limitId) {
		if (CollectionUtils.isEmpty(coBorrowerDetailsList)) {
			return;
		}
		Collection cmr = getCoBorrowerDetailsCMR();
		Iterator<IFacilityCoBorrowerDetails> iterator = coBorrowerDetailsList.iterator();
		EBFacilityCoBorrowerDetailsLocalHome home = getEBFacilityCoBorrowerDetailsLocalHome();
		while (iterator.hasNext()) {
			IFacilityCoBorrowerDetails coBorrowerDetails = iterator.next();
			if (coBorrowerDetails != null) {
				String serverType = (new BatchResourceFactory()).getAppServerType();
				DefaultLogger.debug(this, "Creating Facility Co-Borrower Details ID: " + coBorrowerDetails.getId()
						+ ", Application Server Type : " + serverType);
				
				if (serverType.equals(ICMSConstant.WEBSPHERE)) {
					coBorrowerDetails.setMainProfileId(mainProfileId);
					coBorrowerDetails.setLimitId(limitId);
				}
				
				EBFacilityCoBorrowerDetailsLocal local;
				try {
					local = home.create(coBorrowerDetails);
				} catch (CreateException e) {
					DefaultLogger.error(this, "Exception while creating Facility Co-Borrower Details item(s)", e);
					throw new CustomerException(e.getMessage(), e);
				}
				cmr.add(local);
			}
		}
	}

	// ************************ BeanController Methods **************
	/**
	 * Method to get EB Local Home for EBCoBorrowerLimitLocalHome
	 * 
	 * @return EBLimitLocalHome
	 * @throws LimitException on errors
	 */
	protected EBCoBorrowerLimitLocalHome getEBLocalHomeCoBorrowerLimit() throws LimitException {
		EBCoBorrowerLimitLocalHome home = (EBCoBorrowerLimitLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COBORROWER_LIMIT_LOCAL_JNDI, EBCoBorrowerLimitLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBCoBorrowerLimitLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for EBLimitSysXRef
	 * 
	 * @return EBLimitSysXRefLocalHome
	 * @throws LimitException on errors
	 */
	protected EBLimitSysXRefLocalHome getEBLocalHomeLimitSysXRef() throws LimitException {
		EBLimitSysXRefLocalHome home = (EBLimitSysXRefLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIMIT_SYS_REF_LOCAL_JNDI, EBLimitSysXRefLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBLimitSysXRefLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for EBLimitCovenant
	 * 
	 * @return EBLimitCovenantLocalHome
	 * @throws LimitException on errors
	 */
	protected EBLimitCovenantLocalHome getEBLocalHomeLimitCovenant() throws LimitException {
		EBLimitCovenantLocalHome home = (EBLimitCovenantLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_LIMIT_COVENANT_LOCAL_JNDI, EBLimitCovenantLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBLimitCovenantLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for EBCollateralAllocation
	 * 
	 * @return EBCollateralAllocationLocalHome
	 * @throws LimitException on errors
	 */
	protected EBCollateralAllocationLocalHome getEBLocalHomeCollateralAllocation() throws LimitException {
		EBCollateralAllocationLocalHome home = (EBCollateralAllocationLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COLLATERAL_ALLOCATION_LOCAL_JNDI, EBCollateralAllocationLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBCollateralAllocationLocalHome is null!");
		}
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() throws RemoveException, EJBException {
		// perform cascade delete
		try {
			EBCoBorrowerLimitLocalHome home = getEBLocalHomeCoBorrowerLimit();
			Collection c = home.findByCoBorrowerOuterLimitID(new Long(getLimitID()));

			if (null != c) {
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBCoBorrowerLimitLocal local = (EBCoBorrowerLimitLocal) i.next();
					i.remove(); // remove this local interface from the
					// collection
					local.remove(); // remove the data
				}
			}

			c = getCMRLimitSysXRefs();
			if (null != c) {
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBLimitSysXRefLocal local = (EBLimitSysXRefLocal) i.next();
					i.remove(); // remove this local interface from the
					// collection
					local.remove(); // remove the data
				}
			}
			
			c = getCMRLimitCovenant();
			if (null != c) {
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBLimitCovenantLocal local = (EBLimitCovenantLocal) i.next();
					i.remove(); // remove this local interface from the
					// collection
					local.remove(); // remove the data
				}
			}
		}
		catch (Exception e) {
			RemoveException re = new RemoveException("Failed to remove the Limit instance, id [" + getLimitPK() + "]");
			re.initCause(e);
			throw re;
		}
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

	public abstract String getFacilityDesc();

	public abstract String getFacilityDescNum();

	public abstract String getHostStatus();

	public abstract Date getLastUpdatedDate();

	public abstract String getLimitCMMSN();

	public abstract String getLimitConditionText();

	public abstract String getLimitDesc();

	public abstract String getLimitDescNum();

	public abstract Date getLimitExpiryDate();

	public abstract String getLimitFee();

	public abstract float getLimitInterest();

	public abstract String getLimitRef();

	public abstract String getLimitSecuredType();

	public abstract String getLimitStatus();

	public abstract Long getLimitTenor();

	public abstract String getLimitTenorUnit();

	public abstract String getLimitTenorUnitNum();

	public abstract String getLimitType();

	public abstract String getLoanType();

	public abstract long getOuterLimitID();

	public abstract String getOuterLimitRef();

	public abstract String getProductDesc();

	public abstract String getProductDescNum();

	public abstract String getProductGroup();

	public abstract String getProductGroupNum();

	public abstract String getSourceId();

	public abstract Date getZerorisedDate();

	public abstract String getZerorisedReason();

	public abstract void setFacilityDesc(String value);

	public abstract void setFacilityDescNum(String facilityDescNum);

	public abstract void setHostStatus(String value);

	public abstract void setLastUpdatedDate(Date lastUpdatedDate);

	public abstract void setLimitCMMSN(String value);

	public abstract void setLimitConditionText(String value);

	public abstract void setLimitDesc(String limitDesc);

	public abstract void setLimitDescNum(String limitDescNum);

	public abstract void setLimitExpiryDate(Date value);

	public abstract void setLimitFee(String value);

	public abstract void setLimitInterest(float value);

	public abstract void setLimitRef(String value);

	public abstract void setLimitSecuredType(String value);

	public abstract void setLimitStatus(String value);

	public abstract void setLimitTenor(Long value);

	public abstract void setLimitTenorUnit(String value);

	public abstract void setLimitTenorUnitNum(String limitTenorUnitNum);

	public abstract void setLimitType(String value);

	public abstract void setLoanType(String loanType);

	public abstract void setOuterLimitID(long value);

	public abstract void setOuterLimitRef(String value);

	public abstract void setProductDesc(String value);

	public abstract void setProductDescNum(String productDescNum);

	public abstract void setProductGroup(String productGroup);

	public abstract void setProductGroupNum(String productGroupNum);

	public abstract void setSourceId(String sourceId);

	public abstract void setZerorisedDate(Date zerorisedDate);

	public abstract void setZerorisedReason(String zerorisedReason);

	public abstract void setVersionTime(long l);

	public abstract Double getInterestRate();

	public abstract void setInterestRate(Double interestRate);

	public abstract String getLEReference();

	public abstract void setLEReference(String lEReference);

	public abstract String getAccountType();

	public abstract void setAccountType(String accountType);

	public abstract Integer getInnerLimitOrdering();

	public abstract Long getOmnibusEnvelopeId();

	public abstract String getOmnibusEnvelopeName();

	public abstract void setInnerLimitOrdering(Integer innerLimitOrdering);

	public abstract void setOmnibusEnvelopeId(Long omnibusEnvelopeId);

	public abstract void setOmnibusEnvelopeName(String omnibusEnvelopeName);

	public abstract String getAcfNo();

	public abstract void setAcfNo(String acfNo);
    public abstract String getEBDisbursedInd();
    public abstract void setEBDisbursedInd(String disbursedInd);

    /**
     * To check if it is fully disbursed
     * @return true if FULLY disbursed
     */
	public String getDisbursedInd() {
		 return getEBDisbursedInd() == null ? ICMSConstant.DISBURSED_IND_NO : getEBDisbursedInd();
	} 

	public void setDisbursedInd(String disbursedInd) {
		setEBDisbursedInd (disbursedInd);
	}
	
	public abstract String getFacilityName();
	public abstract void setFacilityName(String facilityName);
	
	public abstract String getFacilitySystem();
	public abstract void setFacilitySystem(String facilitySystem);
	
	public abstract String getFacilitySystemID();
	public abstract void setFacilitySystemID(String facilitySystemID);
	
	public abstract String getLineNo();
	public abstract void setLineNo(String lineNo);
	
	public abstract String getPurpose();
	public abstract void setPurpose(String purpose);
	
	public abstract String getOtherPurpose();
	public abstract void setOtherPurpose(String otherPurpose);
	
	public abstract String getIsDP();
	public abstract void setIsDP(String isDP);
	
	public abstract String getRelationShipManager();
	public abstract void setRelationShipManager(String relationShipManager);
	
	public abstract String getIsReleased();
	public abstract void setIsReleased(String isReleased);
	
	public abstract String getGrade();
	public abstract void setGrade(String grade);
	
	public abstract String getIsSecured();
	public abstract void setIsSecured(String isSecured);
	
	public abstract String getIsAdhoc();
	public abstract void setIsAdhoc(String isAdhoc);
	
	public abstract String getFacilityCat();
	public abstract void setFacilityCat(String facilityCat);
	
	public abstract String getFacilityType();
	public abstract void setFacilityType(String facilityType);
	
	public abstract String getReleasableAmount();
	public abstract void setReleasableAmount(String releasableAmount);
	
	public abstract String getAdhocLmtAmount();
	public abstract void setAdhocLmtAmount(String adhocLmtAmount);
	
	public abstract String getIsAdhocToSum();
	public abstract void setIsAdhocToSum(String isAdhocToSum);
	
	public abstract String getGuarantee();
	public abstract void setGuarantee(String guarantee);
	
	public abstract String getSubPartyName();
	public abstract void setSubPartyName(String subPartyName);
	
	public abstract String getSubFacilityName();
	public abstract void setSubFacilityName(String subFacilityName);
	
	public abstract String getLiabilityID();
	public abstract void setLiabilityID(String liabilityID);

	public abstract String getTotalReleasedAmount();
	public abstract void setTotalReleasedAmount(String totalReleasedAmount);
	
	public abstract String getMainFacilityId();
	public abstract void setMainFacilityId(String mainFacilityId);
	
	public abstract char getIsFromCamonlineReq();
	public abstract void setIsFromCamonlineReq(char isFromCamonlineReq);
	
	public abstract String getSyndicateLoan();
	public abstract void setSyndicateLoan(String syndicateLoan);
	
	public abstract String getPurposeBoolean();
	public abstract void setPurposeBoolean(String purposeBoolean);
	
	public abstract String getLimitRemarks();
	public abstract void setLimitRemarks(String limitRemarks);
	public abstract String getProjectLoan();

	public abstract void setProjectLoan(String projectLoan);

	public abstract String getInfaProjectFlag();

	public abstract void setInfaProjectFlag(String infaProjectFlag);

	public abstract String getWhlmreupSCOD();

	public abstract void setWhlmreupSCOD(String whlmreupSCOD);

	public abstract Date getScodDate();

	public abstract void setScodDate(Date scodDate);

	public abstract String getRemarksSCOD();

	public abstract void setRemarksSCOD(String remarksSCOD);

	public abstract String getExstAssetClass();

	public abstract void setExstAssetClass(String exstAssetClass);

	public abstract Date getExstAssClassDate();

	public abstract void setExstAssClassDate(Date exstAssClassDate);

	public abstract String getRevisedAssetClass();

	public abstract void setRevisedAssetClass(String revisedAssetClass);

	public abstract Date getRevsdAssClassDate();

	public abstract void setRevsdAssClassDate(Date revsdAssClassDate);
	public abstract Date getActualCommOpsDate();

	public abstract void setActualCommOpsDate(Date actualCommOpsDate);

	public abstract String getLelvelDelaySCOD();

	public abstract void setLelvelDelaySCOD(String lelvelDelaySCOD);

	public abstract String getPrincipalInterestSchFlag();

	public abstract void setPrincipalInterestSchFlag(String principalInterestSchFlag);

	public abstract String getProjectDelayedFlag();

	public abstract void setProjectDelayedFlag(String projectDelayedFlag);

	public abstract String getReasonLevelOneDelay();

	public abstract void setReasonLevelOneDelay(String reasonLevelOneDelay);

	public abstract String getChngInRepaySchedule();

	public abstract void setChngInRepaySchedule(String chngInRepaySchedule);

	public abstract Date getEscodLevelOneDelayDate();

	public abstract void setEscodLevelOneDelayDate(Date escodLevelOneDelayDate);

	public abstract Date getEscodLevelSecondDelayDate();

	public abstract void setEscodLevelSecondDelayDate(Date escodLevelSecondDelayDate);

	public abstract String getReasonLevelThreeDelay();

	public abstract void setReasonLevelThreeDelay(String reasonLevelThreeDelay);

	public abstract Date getEscodLevelThreeDelayDate();

	public abstract void setEscodLevelThreeDelayDate(Date escodLevelThreeDelayDate);

	public abstract String getLegalOrArbitrationLevel2Flag();

	public abstract void setLegalOrArbitrationLevel2Flag(String legalOrArbitrationLevel2Flag);

	public abstract String getChngOfOwnPrjFlagNonInfraLevel2();

	public abstract void setChngOfOwnPrjFlagNonInfraLevel2(String chngOfOwnPrjFlagNonInfraLevel2);

	public abstract String getReasonBeyondPromoterLevel2Flag();

	public abstract void setReasonBeyondPromoterLevel2Flag(String reasonBeyondPromoterLevel2Flag);

	public abstract String getChngOfProjScopeNonInfraLevel2();

	public abstract void setChngOfProjScopeNonInfraLevel2(String chngOfProjScopeNonInfraLevel2);

	public abstract String getChngOfOwnPrjFlagInfraLevel2();

	public abstract void setChngOfOwnPrjFlagInfraLevel2(String chngOfOwnPrjFlagInfraLevel2);

	public abstract String getChngOfProjScopeInfraLevel2();

	public abstract void setChngOfProjScopeInfraLevel2(String chngOfProjScopeInfraLevel2);

	public abstract String getLegalOrArbitrationLevel3Flag();

	public abstract void setLegalOrArbitrationLevel3Flag(String legalOrArbitrationLevel3Flag);

	public abstract String getChngOfOwnPrjFlagNonInfraLevel3();

	public abstract void setChngOfOwnPrjFlagNonInfraLevel3(String chngOfOwnPrjFlagNonInfraLevel3);

	public abstract String getReasonBeyondPromoterLevel3Flag();

	public abstract void setReasonBeyondPromoterLevel3Flag(String reasonBeyondPromoterLevel3Flag);

	public abstract String getChngOfProjScopeNonInfraLevel3();

	public abstract void setChngOfProjScopeNonInfraLevel3(String chngOfProjScopeNonInfraLevel3);

	public abstract String getChngOfOwnPrjFlagInfraLevel3();

	public abstract void setChngOfOwnPrjFlagInfraLevel3(String chngOfOwnPrjFlagInfraLevel3);

	public abstract String getChngOfProjScopeInfraLevel3();

	public abstract void setChngOfProjScopeInfraLevel3(String chngOfProjScopeInfraLevel3);

	public abstract String getLeaglOrArbiProceed();

	public abstract void setLeaglOrArbiProceed(String leaglOrArbiProceed);

	public abstract String getDetailsRsnByndPro();

	public abstract void setDetailsRsnByndPro(String detailsRsnByndPro);

	public abstract String getRecvPriorOfSCOD();

	public abstract void setRecvPriorOfSCOD(String recvPriorOfSCOD);

	public abstract String getReasonLevelTwoDelay();

	public abstract void setReasonLevelTwoDelay(String reasonLevelTwoDelay);

	public abstract String getPromotersCapRunFlag();

	public abstract void setPromotersCapRunFlag(String promotersCapRunFlag);

	public abstract String getChangeInScopeBeforeSCODFlag();

	public abstract void setChangeInScopeBeforeSCODFlag(String changeInScopeBeforeSCODFlag);

	public abstract String getPromotersHoldEquityFlag();

	public abstract void setPromotersHoldEquityFlag(String promotersHoldEquityFlag);

	public abstract String getCostOverrunOrg25CostViabilityFlag();

	public abstract void setCostOverrunOrg25CostViabilityFlag(String costOverrunOrg25CostViabilityFlag);

	public abstract String getHasProjectViabReAssFlag();

	public abstract void setHasProjectViabReAssFlag(String hasProjectViabReAssFlag);

	public abstract String getProjectViabReassesedFlag();

	public abstract void setProjectViabReassesedFlag(String projectViabReassesedFlag);

	public abstract Date getRevsedESCODStpDate();

	public abstract void setRevsedESCODStpDate(Date revsedESCODStpDate);


	
	public abstract String getExstAssetClassL1() ;

	public abstract  void setExstAssetClassL1(String exstAssetClassL1) ;

	public abstract  Date getExstAssClassDateL1();

	public abstract  void setExstAssClassDateL1(Date exstAssClassDateL1);

	public  abstract String getExstAssetClassL2();

	public abstract  void setExstAssetClassL2(String exstAssetClassL2);

	public  abstract Date getExstAssClassDateL2();

	public abstract  void setExstAssClassDateL2(Date exstAssClassDateL2);

	public abstract  String getExstAssetClassL3();

	public abstract  void setExstAssetClassL3(String exstAssetClassL3);

	public  abstract Date getExstAssClassDateL3();

	public  abstract void setExstAssClassDateL3(Date exstAssClassDateL3);

	public abstract  String getRevisedAssetClassL1();

	public abstract  void setRevisedAssetClassL1(String revisedAssetClassL1);

	public abstract  Date getRevsdAssClassDateL1() ;

	public abstract  void setRevsdAssClassDateL1(Date revsdAssClassDateL1);

	public abstract  String getRevisedAssetClass_L2();

	public abstract  void setRevisedAssetClass_L2(String revisedAssetClass_L2);

	public abstract  Date getRevsdAssClassDate_L2();

	public abstract  void setRevsdAssClassDate_L2(Date revsdAssClassDate_L2);

	public  abstract String getRevisedAssetClass_L3();

	public  abstract void setRevisedAssetClass_L3(String revisedAssetClass_L3);

	public abstract  Date getRevsdAssClassDate_L3();

	public abstract  void setRevsdAssClassDate_L3(Date revsdAssClassDate_L3) ;

	public  abstract String getProjectDelayedFlagL2() ;

	public abstract  void setProjectDelayedFlagL2(String projectDelayedFlagL2) ;

	public abstract  String getProjectDelayedFlagL3() ;

	public abstract  void setProjectDelayedFlagL3(String projectDelayedFlagL3) ;

	public abstract  String getLeaglOrArbiProceedLevel3();

	public abstract  void setLeaglOrArbiProceedLevel3(String leaglOrArbiProceedLevel3) ;

	public abstract  String getDetailsRsnByndProLevel3();

	public abstract  void setDetailsRsnByndProLevel3(String detailsRsnByndProLevel3);

	public abstract  String getChngInRepayScheduleL2();

	public abstract  void setChngInRepayScheduleL2(String chngInRepayScheduleL2);

	public abstract  String getChngInRepayScheduleL3();

	public abstract  void setChngInRepayScheduleL3(String chngInRepayScheduleL3);

	public abstract  String getPromotersCapRunFlagL2();

	public abstract  void setPromotersCapRunFlagL2(String promotersCapRunFlagL2);

	public abstract  String getPromotersCapRunFlagL3();

	public abstract  void setPromotersCapRunFlagL3(String promotersCapRunFlagL3);

	public  abstract String getChangeInScopeBeforeSCODFlagL2() ;

	public  abstract void setChangeInScopeBeforeSCODFlagL2(String changeInScopeBeforeSCODFlagL2);

	public abstract  String getChangeInScopeBeforeSCODFlagL3() ;

	public abstract  void setChangeInScopeBeforeSCODFlagL3(String changeInScopeBeforeSCODFlagL3);

	public abstract  String getPromotersHoldEquityFlagL2() ;

	public abstract  void setPromotersHoldEquityFlagL2(String promotersHoldEquityFlagL2) ;

	public abstract  String getPromotersHoldEquityFlagL3();

	public abstract  void setPromotersHoldEquityFlagL3(String promotersHoldEquityFlagL3) ;
	

	public  abstract String getCostOverrunOrg25CostViabilityFlagL2() ;

	public abstract void setCostOverrunOrg25CostViabilityFlagL2(String costOverrunOrg25CostViabilityFlagL2);

	public abstract  String getCostOverrunOrg25CostViabilityFlagL3();

	public abstract  void setCostOverrunOrg25CostViabilityFlagL3(String costOverrunOrg25CostViabilityFlagL3);

	public abstract  String getHasProjectViabReAssFlagL2();

	public abstract  void setHasProjectViabReAssFlagL2(String hasProjectViabReAssFlagL2);

	public abstract  String getHasProjectViabReAssFlagL3() ;

	public abstract  void setHasProjectViabReAssFlagL3(String hasProjectViabReAssFlagL3) ;

	public  abstract String getProjectViabReassesedFlagL2() ;

	public abstract  void setProjectViabReassesedFlagL2(String projectViabReassesedFlagL2) ;

	public abstract  String getProjectViabReassesedFlagL3() ;

	public abstract  void setProjectViabReassesedFlagL3(String projectViabReassesedFlagL3) ;

	public abstract  Date getRevsedESCODStpDateL2() ;

	public abstract  void setRevsedESCODStpDateL2(Date revsedESCODStpDateL2) ;

	public abstract  Date getRevsedESCODStpDateL3();

	public abstract  void setRevsedESCODStpDateL3(Date revsedESCODStpDateL3);
	
	public abstract String getProjectFinance();

	public abstract void setProjectFinance(String projectFinance);

	public abstract String getIsDPRequired();
	public abstract void setIsDPRequired(String isDPRequired);
	
	public abstract String getBankingArrangement();
	public abstract void setBankingArrangement(String bankingArrangement);

	public abstract String getClauseAsPerPolicy();
	public abstract void setClauseAsPerPolicy(String clauseAsPerPolicy);
	
	public abstract Long getTenor();

	public abstract void setTenor(Long tenor);

	public abstract String getTenorUnit();

	public abstract void setTenorUnit(String tenorUnit);

	public abstract Double getMargin();

	public abstract void setMargin(Double margin);

	public abstract String getTenorDesc();

	public abstract void setTenorDesc(String tenorDesc);

	public abstract String getPutCallOption();

	public abstract void setPutCallOption(String putCallOption);

	public abstract Date getLoanAvailabilityDate();

	public abstract void setLoanAvailabilityDate(Date loanAvailabilityDate);

	public abstract Date getOptionDate();

	public abstract void setOptionDate(Date optionDate);
	
	public abstract String getRiskType();

	public abstract void setRiskType(String riskType);
	
	public abstract String getAdhocFacility();
	public abstract void setAdhocFacility(String adhocFacility);
	
	public abstract Date getAdhocLastAvailDate();
	public abstract void setAdhocLastAvailDate(Date adhocLastAvailDate);
	
	public abstract String getMultiDrawdownAllow();
	public abstract void setMultiDrawdownAllow(String multiDrawdownAllow);
	
	public abstract String getAdhocTenor();
	public abstract void setAdhocTenor(String adhocTenor);
	
	public abstract Date getAdhocFacilityExpDate();
	public abstract void setAdhocFacilityExpDate(Date adhocFacilityExpDate);
	
	public abstract String getGeneralPurposeLoan();
	public abstract void setGeneralPurposeLoan(String generalPurposeLoan);

}