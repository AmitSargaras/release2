/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBCoBorrowerLimitBean.java,v 1.20 2006/09/22 13:10:45 jzhan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

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
import com.integrosys.cms.app.customer.bus.ICMSCustomer;

/**
 * This entity bean represents the persistence for Co Borrower Limit.
 * 
 * @author $Author: jzhan $
 * @version $Revision: 1.20 $
 * @since $Date: 2006/09/22 13:10:45 $ Tag: $Name: $
 */
public abstract class EBCoBorrowerLimitBean implements EntityBean, ICoBorrowerLimit {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_COBORROWER_LIMIT;

	private static final String[] EXCLUDE_METHOD = new String[] { "getLimitID", "getOuterLimitID", "getLEReference" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBCoBorrowerLimitBean() {
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
	 * Get Outer Limit ID
	 * 
	 * @return long
	 */
	public long getOuterLimitID() {
		if (null != getOuterLimitFK()) {
			return getOuterLimitFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
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
	 * Set Outer Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setOuterLimitID(long value) {
		setOuterLimitFK(new Long(value));
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
			setCurrencyCode(null);
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
		this.setActivatedLimit(value == null ? null : value.getAmountAsBigDecimal());
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

	public void setRequiredSecurityCoverages(float securityCoverages) {
		if (securityCoverages == ICMSConstant.FLOAT_INVALID_VALUE) {
			this.setRequiredSecurityCoveragesCMP(null);
		}
		else {
			this.setRequiredSecurityCoveragesCMP(new BigDecimal(securityCoverages));
		}
	}

	public float getRequiredSecurityCoverages() {
		return this.getRequiredSecurityCoveragesCMP() == null ? ICMSConstant.FLOAT_INVALID_VALUE
				: getRequiredSecurityCoveragesCMP().floatValue();
	}

	public void setCoActualSecurityCoverage(float coActualSecurityCoverage) {
		if (coActualSecurityCoverage == ICMSConstant.FLOAT_INVALID_VALUE) {
			this.setActualSecurityCoveragesCMP(null);
		}
		else {
			this.setActualSecurityCoveragesCMP(new BigDecimal(coActualSecurityCoverage));
		}
	}

	public float getCoActualSecurityCoverage() {
		return this.getActualSecurityCoveragesCMP() == null ? ICMSConstant.FLOAT_INVALID_VALUE
				: getActualSecurityCoveragesCMP().floatValue();
	}

	public void setLimitZerorised(boolean zerorised) {
		if (zerorised) {
			this.setLimitZerorisedCMP(ICMSConstant.TRUE_VALUE);
		}
		else {
			this.setLimitZerorisedCMP(ICMSConstant.FALSE_VALUE);
		}
	}

	public boolean getLimitZerorised() {
		if (ICMSConstant.TRUE_VALUE.equals(this.getLimitZerorisedCMP())) {
			return true;
		}
		return false;
	}

	public void setZerorisedDate(Date zerorisedDate) {
		this.setZerorisedDateCMP(zerorisedDate);
	}

	public Date getZerorisedDate() {
		return this.getZerorisedDateCMP();
	}

	public void setZerorisedReasons(String reasons) {
		this.setZerorisedReasonsCMP(reasons);
	}

	public String getZerorisedReasons() {
		return this.getZerorisedReasonsCMP();
	}

	public void setProductDesc(String productDesc) {

	}

	public String getProductDesc() {
		return null;
	}

	public void setIsChanged(boolean isChanged) {

	}

	public boolean getIsChanged() {
		return false;
	}

	public void setIsDAPError(boolean isDAPError) {

	}

	public boolean getIsDAPError() {
		return false;
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

	public ICMSCustomer getCustomer() {
		return null;
	}

	public void setCustomer(ICMSCustomer customer) {

	}

	public ICMSCustomer getCoBorrowerCust() {
		return null;
	}

	public void setCoBorrowerCust(ICMSCustomer cust) {

	}

	public ICMSCustomer getMainBorrowerCust() {
		return null;
	}

	public void setMainBorrowerCust(ICMSCustomer cust) {

	}

	/**
	 * Get outer limit booking location.
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getOuterLimitBookingLoc() {
		return null;
	}

	/**
	 * Set outer limit booking location.
	 * 
	 * @param outerLimitBookingLoc of type IBookingLocation
	 */
	public void setOuterLimitBookingLoc(IBookingLocation outerLimitBookingLoc) {
	}

	// R1.5 CR35 ----------
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
	 * Set All Collateral Allocations Not implemented.
	 * 
	 * @param value is of type ICollateralAllocation[]
	 */
	public void setCollateralAllocations(ICollateralAllocation[] value) {
		// do nothing
	}

	// --------------------

	public Set getCollateralAllocationsSet() {
		return null;
	}

	public void setCollateralAllocationsSet(Set collateralAllocationsSet) {
	}

	// ************** CMR methods ***************
	public abstract Collection getCMRCollateralAllocations(); // R1.5 CR35

	public abstract void setCMRCollateralAllocations(Collection value); // R1.5
																		// CR35

	// ********************** Abstract Methods **********************

	// Getters
	/**
	 * Get currency code
	 * 
	 * @return String
	 */
	public abstract String getCurrencyCode();

	/**
	 * Get Limit ID
	 * 
	 * @return Long
	 */
	public abstract Long getLimitPK();

	/**
	 * Get Outer Limit ID
	 * 
	 * @return Long
	 */
	public abstract Long getOuterLimitFK();

	/**
	 * Get Approved Limit Amount
	 * 
	 * @return double
	 */
	public abstract double getApprovedLimit();

	/**
	 * Get Activated Limit Amount
	 * 
	 * @return double
	 */
	public abstract BigDecimal getActivatedLimit();

	/**
	 * Get the booking country
	 * 
	 * @return String
	 */
	public abstract String getBookingCountry();

	/**
	 * Get the booking organisation
	 * 
	 * @return String
	 */
	public abstract String getBookingOrganisation();

	/**
	 * Get Limit Activated Indicator
	 * 
	 * @return String
	 */
	public abstract String getLimitActivatedStr();

	/**
	 * Get the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @return boolean
	 */
	public abstract String getExistingStr();

	// Setters
	/**
	 * Set currency code
	 * 
	 * @param value is of type String
	 */
	public abstract void setCurrencyCode(String value);

	/**
	 * Set Limit PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setLimitPK(Long value);

	/**
	 * Set Limit PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setOuterLimitFK(Long value);

	/**
	 * Set Approved Limit Amount
	 * 
	 * @param value is of type double
	 */
	public abstract void setApprovedLimit(double value);

	/**
	 * Set Activated Limit Amount
	 * 
	 * @param value is of type double
	 */
	public abstract void setActivatedLimit(BigDecimal value);

	/**
	 * Set the booking country
	 * 
	 * @param value is of type String
	 */
	public abstract void setBookingCountry(String value);

	/**
	 * Get the originating organisation
	 * 
	 * @param value is of type String
	 */
	public abstract void setBookingOrganisation(String value);

	/**
	 * Set Limit Activated Indicator
	 * 
	 * @param value is of type String
	 */
	public abstract void setLimitActivatedStr(String value);

	/**
	 * Set the is limit exisitng indicator. If true, this limit exist in the
	 * previous version of the BCA
	 * 
	 * @param value is of type boolean
	 */
	public abstract void setExistingStr(String value);

	public abstract void setRequiredSecurityCoveragesCMP(BigDecimal securityCoverages);

	public abstract BigDecimal getRequiredSecurityCoveragesCMP();

	public abstract void setActualSecurityCoveragesCMP(BigDecimal securityCoverages);

	public abstract BigDecimal getActualSecurityCoveragesCMP();

	public abstract void setLimitZerorisedCMP(String zerorised);

	public abstract String getLimitZerorisedCMP();

	public abstract void setZerorisedDateCMP(Date zerorisedDate);

	public abstract Date getZerorisedDateCMP();

	public abstract void setZerorisedReasonsCMP(String reasons);

	public abstract String getZerorisedReasonsCMP();

	public abstract String getCoLimitSecuredType();

	public abstract void setCoLimitSecuredType(String coLimitSecuredType);

	public abstract long getVersionTime();

	// ************************ ejbCreate methods ********************

	/**
	 * Create a co borrower limit
	 * 
	 * @param value is the ICoBorrowerLimit object
	 * @return Long the primary key
	 */
	public Long ejbCreate(ICoBorrowerLimit value) throws CreateException {
		if (null == value) {
			throw new CreateException("ICoBorrowerLimit is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));

			DefaultLogger.debug(this, "Creating Limit with ID: " + pk);

			setLimitID(pk);
			setOuterLimitID(value.getOuterLimitID());
			setLEReference(value.getLEReference());
			/*
			 * if(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE
			 * == value.getSID()) { setSID(pk); } else { setSID(value.getSID());
			 * }
			 */

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setVersionTime(VersionGenerator.getVersionNumber());

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Unknown Exception Caught: " + e);
		}
	}

	/**
	 * Create a Co Borrower Limit
	 * 
	 * @param value is the ICoBorrowerLimit object
	 */
	public void ejbPostCreate(ICoBorrowerLimit value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return ICoBorrowerLimit
	 */
	public ICoBorrowerLimit getValue() throws LimitException {
		try {
			OBCoBorrowerLimit value = new OBCoBorrowerLimit();
			AccessorUtil.copyValue(this, value);

			value.setCollateralAllocations(retrieveCollateralAllocations());

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
	 * @param value is of type ICoBorrowerLimit
	 * @throws ConcurrentUpdateException on error
	 */
	public void setValue(ICoBorrowerLimit value) throws ConcurrentUpdateException {
		long beanVer = value.getVersionTime();
		long currentVer = getVersionTime();
		if (beanVer != currentVer) {
			throw new ConcurrentUpdateException("Version mismatch!");
		}
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type ILimit
	 * @param verTime is the version time to be compared against the beans'
	 *        version
	 * @throws LimitException
	 * @throws ConcurrentUpdateException on error
	 */
	public void createDependants(ICoBorrowerLimit value, long verTime) throws LimitException, ConcurrentUpdateException {
		if (verTime != getVersionTime()) {
			throw new ConcurrentUpdateException("Version mismatched!");
		}
		else {
			createCollateralAllocation(value.getCollateralAllocations()); // only
																			// meant
																			// to
																			// be
																			// created
																			// in
																			// actual
																			// bean
		}
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
		// DefaultLogger.debug(this, "in ejbActivate...");
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

	// ************************** Private methods ***************************
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
					if ((temp != null) && (temp.getSCILimitSecMapID() > ob.getSCILimitSecMapID())) {
						continue;
					}
					hashmap.put(colId, ob);
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
			DefaultLogger.debug(this, "No Collateral Allocations to be created.");
			return; // do nothing
		}
		Collection c = getCMRCollateralAllocations();

		EBCollateralAllocationLocalHome home = getEBLocalHomeCollateralAllocation();

		try {
			for (int i = 0; i < allocList.length; i++) {
				ICollateralAllocation ob = allocList[i];
				EBCollateralAllocationLocal local = home.create(ob);

				c.add(local);
			}
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
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

    public abstract long getCustomerID();

    public abstract String getHostStatus();

    public abstract String getLEReference();

    public abstract String getLimitRef();

    public abstract String getOuterLimitRef();

    public abstract String getSourceId();

    public abstract String getStatus();

    public abstract void setCustomerID(long value);

    public abstract void setHostStatus(String value);

    public abstract void setLEReference(String lEReference);

    public abstract void setLimitRef(String value);

    public abstract void setOuterLimitRef(String value);

    public abstract void setSourceId(String sourceId);

    public abstract void setStatus(String value);

    public abstract void setVersionTime(long l);
    
}