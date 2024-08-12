/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/EBLimitProfileBean.java,v 1.31 2006/10/16 06:52:56 jzhan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

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
import com.integrosys.cms.batch.common.BatchResourceFactory;

/**
 * This entity bean represents the persistence for Limit Profile.
 * 
 * @author $Author: jzhan $
 * @version $Revision: 1.31 $
 * @since $Date: 2006/10/16 06:52:56 $ Tag: $Name: $
 */
public abstract class EBLimitProfileBean implements EntityBean, ILimitProfile {

	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_LIMIT_PROFILE;

	private static final String[] EXCLUDE_METHOD = new String[] { "getLimitProfileID", "getCustomerID",
			"getLEReference" };

	public static final String EXCLUDE_STATUS = ICMSConstant.STATE_DELETED;

	public static final String EMPTY_STR = "";

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBLimitProfileBean() {
	}

	// ************ Non-persistence method *************
	// Getters
	/**
	 * Get Limit Profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		if (null != getLimitProfilePK()) {
			return getLimitProfilePK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get All limits associated to this profile Not Implemented. See
	 * <code>retrieveLimits</code>
	 * 
	 * @return ILimit[]
	 */
	public ILimit[] getLimits() {
		return null;
	}

	/**
	 * Get All limits associated to this profile Not Implemented.
	 * 
	 * @return ILimit[]
	 */
	public ILimit[] getNonDeletedLimits() {
		return null;
	}

	/**
	 * Get Originating Location
	 * 
	 * @return IBookingLocation
	 */
	public IBookingLocation getOriginatingLocation() {
		String origCountry = getOriginatingCountry();
		String origOrg = getOriginatingOrganisation();

		OBBookingLocation ob = new OBBookingLocation();
		ob.setCountryCode(origCountry);
		ob.setOrganisationCode(origOrg);

		return ob;
	}

	/**
	 * Get product program compliance indicator
	 * 
	 * @return boolean
	 */
	public boolean getProductProgCompliantInd() {
		String value = getProductProgCompliantStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get credit policy compliance indicator
	 * 
	 * @return boolean
	 */
	public boolean getCreditPolicyCompliantInd() {
		String value = getCreditPolicyCompliantStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get under-write standard compliance indicator
	 * 
	 * @return boolean
	 */
	public boolean getUnderwriteStandardCompliantInd() {
		String value = getUnderwriteStandardCompliantStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Is Country Risk Approval required indicator
	 * 
	 * @return boolean
	 */
	public boolean getCRApprovalRequiredInd() {
		String value = getCRApprovalRequiredStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 *Identify is the BCA is a renewed BCA
	 * 
	 * @return boolean
	 */
	public boolean getRenewalInd() {
		String value = getRenewalStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Identify if BCA has been completed. Completed means all limits must have
	 * been activated, SCC (if applicable) has been generated, and CCC has been
	 * generated
	 * 
	 * @return boolean
	 */
	public boolean getBCACompleteInd() {
		String value = getBCACompleteStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get the list of TAT entries associated to this limit profile. If TAT has
	 * not been created yet, this will return null. Not implemented. see
	 * <code>retrieveTATEntries</code>
	 * 
	 * @return ITATEntry[]
	 */
	public ITATEntry[] getTATEntries() {
		return null;
	}

	public Set getLimitsSet() {
		return null;
	}

	public Set getTATEntriesSet() {
		return null;
	}

	public void setLimitsSet(Set limitsSet) {
	}

	public void setTATEntriesSet(Set entriesSet) {
	}

	/**
	 * Get the trading agreement associated to this limit profile. If trading
	 * agreement has not been created yet, this will return null. Not
	 * implemented. see <code>retrieveTradingAgreement</code>
	 * 
	 * @return ITradingAgreement
	 */
	public ITradingAgreement getTradingAgreement() {
		return null;
	}

	/**
	 * Get BFL required indicator
	 * 
	 * @return boolean
	 */
	public boolean getBFLRequiredInd() {
		String value = getBFLRequiredStr();
		if (null == value) {
			return true; // requirement is BFL is true by default
		}
		else if (value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else { // this is the case when it is really false
			return false;
		}
	}

	/**
	 * Get full doc review indicator
	 * 
	 * @return boolean
	 */
	public boolean getFullDocReviewInd() {
		String value = getFullDocReviewStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Identify if all CCC has been generated for this limit profile
	 * 
	 * @return boolean
	 */
	public boolean getCCCCompleteInd() {
		String value = getCCCCompleteStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Identify if all SCC has been generated for this limit profile
	 * 
	 * @return boolean
	 */
	public boolean getSCCCompleteInd() {
		String value = getSCCCompleteStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean getBCALocalInd() {
		String value = getBCALocalIndStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Get this limit profile's latest clean type/ special clean type BFL
	 * issuance Date
	 * 
	 * @return Date
	 */
	public Date getCleanSpecialBFLIssuanceDate() {
		return null;
	}

	/**
	 * Get the required security coverage percentage
	 * 
	 * @return float
	 */
	public double getRequiredSecurityCoverage() {
		return getEBRequiredSecurityCoverage() == null ? ICMSConstant.DOUBLE_INVALID_VALUE
				: getEBRequiredSecurityCoverage().doubleValue();
	}

	/**
	 * Get the actual security coverage percentage
	 * 
	 * @return float
	 */
	public double getActualSecurityCoverage() {
		return getEBActualSecurityCoverage() == null ? ICMSConstant.DOUBLE_INVALID_VALUE
				: getEBActualSecurityCoverage().doubleValue();
	}

	/**
	 * Get Customer Reference
	 * 
	 * @return long
	 */
	public long getCustRef() {
		if (null != getCustomerReference()) {
			return getCustomerReference().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get CMS create indicator
	 * 
	 * @return boolean
	 */
	public boolean getCMSCreateInd() {
		String value = getCMSCreateIndStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	// Setters
	/**
	 * Set Limit Profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value) {
		setLimitProfilePK(new Long(value));
	}

	/**
	 * Set All limits associated to this profile Not implemented.
	 * 
	 * @param value is of type ILimit[]
	 */
	public void setLimits(ILimit[] value) {
		// do nothing
	}

	/**
	 * Set Originating Location
	 * 
	 * @param value is of type IBookingLocation
	 */
	public void setOriginatingLocation(IBookingLocation value) {
		if (null != value) {
			setOriginatingCountry(value.getCountryCode());
			setOriginatingOrganisation(value.getOrganisationCode());
		}
		else {
			setOriginatingCountry(null);
			setOriginatingOrganisation(null);
		}
	}

	/**
	 * Set product program compliance indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setProductProgCompliantInd(boolean value) {
		if (true == value) {
			setProductProgCompliantStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setProductProgCompliantStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set credit policy compliance indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCreditPolicyCompliantInd(boolean value) {
		if (true == value) {
			setCreditPolicyCompliantStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setCreditPolicyCompliantStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set under-write standard compliance indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setUnderwriteStandardCompliantInd(boolean value) {
		if (true == value) {
			setUnderwriteStandardCompliantStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setUnderwriteStandardCompliantStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Is Country Risk Approval required indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCRApprovalRequiredInd(boolean value) {
		if (true == value) {
			setCRApprovalRequiredStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setCRApprovalRequiredStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set the BCA renewal indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setRenewalInd(boolean value) {
		if (true == value) {
			setRenewalStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setRenewalStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Identify if BCA has been completed. Completed means all limits must have
	 * been activated, SCC (if applicable) has been generated, and CCC has been
	 * generated
	 * 
	 * @param value is of type boolean
	 */
	public void setBCACompleteInd(boolean value) {
		if (true == value) {
			setBCACompleteStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setBCACompleteStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set the list of TAT entries associated to this limit profile. If TAT has
	 * not been created yet, this will return null. Not implemented.
	 * 
	 * @param value is of type ITATEntry[]
	 */
	public void setTATEntries(ITATEntry[] value) {
		// do nothing.
	}

	/**
	 * Set the trading agreement associated to this limit profile. If trading
	 * agreement has not been created yet, this will return null. Not
	 * implemented.
	 * 
	 * @param value is of type ITradingAgreement
	 */
	public void setTradingAgreement(ITradingAgreement value) {
		// do nothing.
	}

	/**
	 * Set BFL Required Indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setBFLRequiredInd(boolean value) {
		if (true == value) {
			setBFLRequiredStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setBFLRequiredStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set full doc review indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setFullDocReviewInd(boolean value) {
		if (true == value) {
			setFullDocReviewStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setFullDocReviewStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Identify if all CCC has been generated for this limit profile
	 * 
	 * @param value is of type boolean
	 */
	public void setCCCCompleteInd(boolean value) {
		if (true == value) {
			setCCCCompleteStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setCCCCompleteStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Identify if all SCC has been generated for this limit profile
	 * 
	 * @param value is of type boolean
	 */
	public void setSCCCompleteInd(boolean value) {
		if (true == value) {
			setSCCCompleteStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setSCCCompleteStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Identify if the BCA is local or foreign True means local, false mean
	 * foreign
	 * 
	 * @param value of type boolean
	 */
	public void setBCALocalInd(boolean value) {
		if (true == value) {
			setBCALocalIndStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setBCALocalIndStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set the required security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setRequiredSecurityCoverage(double value) {
		setEBRequiredSecurityCoverage(value == ICMSConstant.DOUBLE_INVALID_VALUE ? null : new BigDecimal(value));
	}

	/**
	 * Set the actual security coverage percentage
	 * 
	 * @param value is of type float
	 */
	public void setActualSecurityCoverage(double value) {
		setEBActualSecurityCoverage(value == ICMSConstant.DOUBLE_INVALID_VALUE ? null : new BigDecimal(value));
	}

	/**
	 * Set Customer Reference
	 * 
	 * @param value is of type long
	 */
	public void setCustRef(long value) {
		setCustomerReference(new Long(value));
	}

	/**
	 * Set CMS create indicator
	 * 
	 * @param value is of type boolean
	 */
	public void setCMSCreateInd(boolean value) {
		if (true == value) {
			setCMSCreateIndStr(ICMSConstant.TRUE_VALUE);
		}
		else {
			setCMSCreateIndStr(ICMSConstant.FALSE_VALUE);
		}
	}

	// ********************** Abstract Methods **********************

	// Getters
	public abstract Long getLimitProfilePK();

	public abstract String getProductProgCompliantStr();

	public abstract String getCreditPolicyCompliantStr();

	public abstract String getUnderwriteStandardCompliantStr();

	public abstract String getCRApprovalRequiredStr();

	public abstract String getBFLRequiredStr();

	public abstract String getRenewalStr();

	public abstract String getBCACompleteStr();

	public abstract String getOriginatingCountry();

	public abstract String getOriginatingOrganisation();

	public abstract String getFullDocReviewStr();

	public abstract String getCCCCompleteStr();

	public abstract String getSCCCompleteStr();

	public abstract String getBCALocalIndStr();

	public abstract BigDecimal getEBRequiredSecurityCoverage();

	public abstract BigDecimal getEBActualSecurityCoverage();

	public abstract String getAAType();

	public abstract String getSourceID();

	public abstract String getBCAStatus();

	public abstract Long getCustomerReference();

	public abstract String getCMSCreateIndStr();

	public abstract String getApplicationType();

	public abstract String getApplicationLawType();
	
	public abstract String getCamType();  //786001
	
	public abstract Date getCamLoginDate();  //786001
	
	public abstract long getNoOfTimesExtended();
	
	public abstract double getTotalSactionedAmount();
	
	public abstract String getRelationshipManager();
	
	public abstract String getControllingBranch();
	
	public abstract String getCommitteApproval();
	
	//Start:Code added for Fully Cash Collateral
	public abstract String getFullyCashCollateral();
	//End:Code added for Fully Cash Collateral
	
	public abstract String getBoardApproval();
	
	public abstract String getAssetClassification();
	
	
	public abstract String getRbiAssetClassification();

	public abstract long getVersionTime();

	// Setters
	public abstract void setLimitProfilePK(Long value);

	public abstract void setProductProgCompliantStr(String value);

	public abstract void setCreditPolicyCompliantStr(String value);

	public abstract void setUnderwriteStandardCompliantStr(String value);

	public abstract void setCRApprovalRequiredStr(String value);

	public abstract void setBFLRequiredStr(String value);

	public abstract void setRenewalStr(String value);

	public abstract void setOriginatingCountry(String value);

	public abstract void setOriginatingOrganisation(String value);

	public abstract void setFullDocReviewStr(String value);

	public abstract void setBCACompleteStr(String value);

	public abstract void setCCCCompleteStr(String value);

	public abstract void setSCCCompleteStr(String value);

	public abstract void setBCALocalIndStr(String value);

	public abstract void setEBRequiredSecurityCoverage(BigDecimal ebRequiredSecurityCoverage);

	public abstract void setEBActualSecurityCoverage(BigDecimal ebRequiredSecurityCoverage);

	public abstract void setAAType(String value);

	public abstract void setSourceID(String value);

	public abstract void setBCAStatus(String value);

	public abstract void setCustomerReference(Long value);

	public abstract void setCMSCreateIndStr(String value);

	public abstract void setApplicationType(String applicationType);

	public abstract void setApplicationLawType(String applicationLawType);
	
	public abstract void setCamType(String camType);
	
	public abstract void setCamLoginDate(Date camType);  //786001
	
	public abstract void setNoOfTimesExtended(long noOfTimesExtended);
	
	public abstract void setTotalSactionedAmount(double totalSactionedAmount);
	
	public abstract void setRelationshipManager(String relationshipManager);
	
	public abstract void setControllingBranch(String controllingBranch);
	
	public abstract void setCommitteApproval(String committeApproval);
	
	//Start:Code added for Fully Cash Collateral
	public abstract void setFullyCashCollateral(String fullyCashCollateral);
	//End  :Code added for Fully Cash Collateral
	
	public abstract void setBoardApproval(String boardApproval);
	
	public abstract void setAssetClassification(String assetClassification);
	
	public abstract void setRbiAssetClassification(String rbiAssetClassification);

	public abstract String getDocRemarks();
	
	public abstract void setDocRemarks(String docRemarks);
	// ************************ ejbCreate methods ********************

	/**
	 * Create a Limit Profile
	 * 
	 * @param value is the ILimitProfile object
	 * @return Long the primary key
	 */
	public Long ejbCreate(ILimitProfile value) throws CreateException {
		if (null == value) {
			throw new CreateException("ILimitProfile is null!");
		}
		try {
			long customerID = value.getCustomerID();
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == customerID) {
				throw new CreateException("Customer ID is not initalised: " + customerID);
			}
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));

			DefaultLogger.debug(this, "Creating Limit Profile with ID: " + pk);

			setLimitProfileID(pk);
			setCustomerID(customerID);
			setLEReference(value.getLEReference());

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);

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
	 * @param value is the ILimitProfile object
	 */
	public void ejbPostCreate(ILimitProfile value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @param loadDependants is a boolean value indicating of child dependants
	 *        should be loaded
	 * @return ILimitProfile
	 * @throws LimitException on error
	 */
	public ILimitProfile getValue(boolean loadDependants) throws LimitException {
		try {
			OBLimitProfile value = new OBLimitProfile();
			AccessorUtil.copyValue(this, value);

			if (true == loadDependants) {
				value.setLimits(retrieveLimits());
				value.setTATEntries(retrieveTATEntry());
				value.setTradingAgreement(retrieveTradingAgreement());
				value.setUdfData(retrieveUdfInfo());
			}

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
	 * @param value is of type ILimitProfile
	 * @throws ConcurrentUpdateException if version mismatch occurs
	 * @throws LimitException on error
	 */
	public void setValue(ILimitProfile value) throws LimitException, ConcurrentUpdateException {
		long beanVer = value.getVersionTime();
		long currentVer = getVersionTime();
		if (beanVer != currentVer) {
			throw new ConcurrentUpdateException("Version mismatch!");
		}
		try {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setVersionTime(VersionGenerator.getVersionNumber());
			updateTATEntry(value.getTATEntries());
			updateTradingAgreement(value.getTradingAgreement());
			updateUdfInfo(value.getUdfData(), value.getLimitProfileID());
		}
		catch (Exception e) {
			throw new LimitException("Caught Exception!", e);
		}
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.EBLimitProfile#setStatusDeleted
	 */
	public void setStatusDeleted(ILimitProfile value) throws LimitException, ConcurrentUpdateException {
		checkVersionMismatch(value);
		setBCAStatus(ICMSConstant.STATE_DELETED);
		setVersionTime(VersionGenerator.getVersionNumber());

		removeTradingAgreement(value.getTradingAgreement());

	}

	/**
	 * Check the version of this limit profile.
	 * 
	 * @param value of type ILimitProfile
	 * @throws ConcurrentUpdateException if the entity version is invalid
	 */
	private void checkVersionMismatch(ILimitProfile value) throws ConcurrentUpdateException {
		if (getVersionTime() != value.getVersionTime()) {
			throw new ConcurrentUpdateException("Mismatch timestamp! " + value.getVersionTime());
		}
	}

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value is of type ILimitProfile
	 * @param verTime is the version time to be compared against the beans'
	 *        version
	 * @throws LimitException ConcurrentUpdateException on error
	 */
	public void createDependants(ILimitProfile value, long verTime) throws LimitException, ConcurrentUpdateException {
		if (verTime != getVersionTime()) {
			throw new ConcurrentUpdateException("Version mismatched!");
		}
		else {
			updateTATEntry(value.getTATEntries());
			createTradingAgreement(value.getTradingAgreement());
			updateUdfInfo(value.getUdfData(), value.getLimitProfileID());

		}
	}

	// ************************ BeanController Methods **************
	/**
	 * Method to get EB Local Home for EBLimit
	 * 
	 * @return EBLimitLocalHome
	 * @throws LimitException on errors
	 */
	protected EBLimitLocalHome getEBLocalHomeLimit() throws LimitException {
		EBLimitLocalHome home = (EBLimitLocalHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_LIMIT_LOCAL_JNDI,
				EBLimitLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBLimitLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for EBTATEntry
	 * 
	 * @return EBTATEntryLocalHome
	 * @throws LimitException on errors
	 */
	protected EBTATEntryLocalHome getEBLocalHomeTATEntry() throws LimitException {
		EBTATEntryLocalHome home = (EBTATEntryLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_TAT_ENTRY_LOCAL_JNDI, EBTATEntryLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBTATEntryLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for EBTradingAgreement
	 * 
	 * @return EBTTradingAgreementLocalHome
	 * @throws LimitException on errors
	 */
	protected EBTradingAgreementLocalHome getEBLocalHomeTradingAgreement() throws LimitException {
		EBTradingAgreementLocalHome home = (EBTradingAgreementLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_TRADING_AGREEMENT_LOCAL_JNDI, EBTradingAgreementLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new LimitException("EBTradingAgreementLocalHome is null!");
		}
	}

	// ************************** Protected methods ***************************
	/**
	 * Method to retrieve limits
	 * 
	 * @return ILimit[]
	 * @throws LimitException on errors
	 */
	protected ILimit[] retrieveLimits() throws LimitException {
		try {
			EBLimitLocalHome home = getEBLocalHomeLimit();
			Collection c = home.findByLimitProfile(new Long(getLimitProfileID()), EXCLUDE_STATUS);

			Iterator i = c.iterator();
			ArrayList aList = new ArrayList(c.size());
			while (i.hasNext()) {
				EBLimitLocal local = (EBLimitLocal) i.next();
				ILimit limit = local.getValue();
				aList.add(limit);
			}
			return (ILimit[]) aList.toArray(new ILimit[0]);
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
	 * Method to update TAT entries. A delete-insert will be performed. This
	 * method is not implemented via CMR due to time constraint.
	 */
	private void updateTATEntry(ITATEntry[] entries) throws LimitException {
		try {
			EBTATEntryLocalHome home = getEBLocalHomeTATEntry();
			Collection c = home.findByLimitProfile(new Long(getLimitProfileID()));

			// delete all
			if (null != c) {
				int count = 0;
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBTATEntryLocal local = (EBTATEntryLocal) i.next();

					local.remove();
					count++;
				}
				DefaultLogger.debug(this, "Number of TAT Entries deleted: " + count);
			}

			// insert all
			if (null != entries) {
				long limitProfileID = getLimitProfileID();
				int count = 0;
				for (int i = 0; i < entries.length; i++) {
					home.create(limitProfileID, entries[i]);
					count++;
				}
				DefaultLogger.debug(this, "Number of TAT Entries inserted: " + count);
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
	 * Method to retrieve TAT Entries. TAT is not managed with CMR as
	 * insert/update is achieved via SB directly to the EB of TAT Entry.
	 * 
	 * @return ITATEntry[]
	 * @throws LimitException on errors
	 */
	private ITATEntry[] retrieveTATEntry() throws LimitException {
		try {
			EBTATEntryLocalHome home = getEBLocalHomeTATEntry();
			Collection c = home.findByLimitProfile(new Long(getLimitProfileID()));

			Iterator i = c.iterator();
			ArrayList aList = new ArrayList(c.size());
			while (i.hasNext()) {
				EBTATEntryLocal local = (EBTATEntryLocal) i.next();
				ITATEntry entry = local.getValue();
				aList.add(entry);
			}
			return (ITATEntry[]) aList.toArray(new ITATEntry[0]);
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
	 * Method to create Trading Agreement. This method is not implemented via
	 * CMR due to time constraint.
	 */
	private void createTradingAgreement(ITradingAgreement value) throws LimitException {
		try {
			EBTradingAgreementLocalHome home = getEBLocalHomeTradingAgreement();
			DefaultLogger.debug(this, "createTradingAgreement");

			// insert
			if (null != value) {
				long limitProfileID = getLimitProfileID();
				DefaultLogger.debug(this, "createTradingAgreement, limitProfileID=" + limitProfileID);

				EBTradingAgreementLocal ebAgreement = home.create(limitProfileID, value);

				long verTime = ebAgreement.getVersionTime();
				// create child dependencies with checking on version time
				ebAgreement.createDependants(value, verTime);

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
	 * Method to update Trading Agreement. A delete-insert will be performed.
	 * This method is not implemented via CMR due to time constraint.
	 */
	private void updateTradingAgreement(ITradingAgreement value) throws LimitException {
		try {
			EBTradingAgreementLocalHome home = getEBLocalHomeTradingAgreement();
			Collection c = home.findByLimitProfile(new Long(getLimitProfileID()), EXCLUDE_STATUS);
			DefaultLogger.debug(this, "updateTradingAgreement,getLimitProfileID()" + getLimitProfileID());

			// update
			if (null != c) {
				int count = 0;
				Iterator i = c.iterator();
				if (i.hasNext()) {
					EBTradingAgreementLocal local = (EBTradingAgreementLocal) i.next();
					local.setValue(value);
				}
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

	private void removeTradingAgreement(ITradingAgreement value) throws LimitException {
		try {
			EBTradingAgreementLocalHome home = getEBLocalHomeTradingAgreement();
			Collection c = home.findByLimitProfile(new Long(getLimitProfileID()), EXCLUDE_STATUS);

			// delete
			if (null != c) {
				int count = 0;
				Iterator i = c.iterator();
				if (i.hasNext()) {
					EBTradingAgreementLocal local = (EBTradingAgreementLocal) i.next();
					local.setStatusDeleted(value);
				}
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
	 * Method to retrieve Trading Agreement. Trading Agreement is not managed
	 * with CMR as insert/update is achieved via SB directly to the EB of
	 * Trading Agreement.
	 * 
	 * @return ITradingAgreement
	 * @throws LimitException on errors
	 */
	private ITradingAgreement retrieveTradingAgreement() throws LimitException {
		try {
			ITradingAgreement entry = null;
			EBTradingAgreementLocalHome home = getEBLocalHomeTradingAgreement();
			Collection c = home.findByLimitProfile(new Long(getLimitProfileID()), "");

			Iterator i = c.iterator();
			if (i.hasNext()) {
				EBTradingAgreementLocal local = (EBTradingAgreementLocal) i.next();
				entry = local.getValue();
				DefaultLogger.debug(this, "retrieveTradingAgreement,entry=" + entry);
			}
			return entry;
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
		try {
			// remove limits
			EBLimitLocalHome home = getEBLocalHomeLimit();
			Collection c = home.findByLimitProfile(new Long(getLimitProfileID()), EMPTY_STR);

			Iterator i = c.iterator();
			while (i.hasNext()) {
				EBLimitLocal local = (EBLimitLocal) i.next();
				i.remove(); // remove this local interface from the collection
			}

			// remove tat entries
			EBTATEntryLocalHome tatHome = getEBLocalHomeTATEntry();
			c = tatHome.findByLimitProfile(new Long(getLimitProfileID()));

			i = c.iterator();
			while (i.hasNext()) {
				EBTATEntryLocal local = (EBTATEntryLocal) i.next();
				i.remove(); // remove this local interface from the collection
			}

			// remove trading agreement
			EBTradingAgreementLocalHome agreeHome = getEBLocalHomeTradingAgreement();
			c = agreeHome.findByLimitProfile(new Long(getLimitProfileID()), EMPTY_STR);

			i = c.iterator();
			while (i.hasNext()) {
				EBTradingAgreementLocal local = (EBTradingAgreementLocal) i.next();
				i.remove(); // remove this local interface from the collection
			}

		}
		catch (FinderException e) {
			// do nothing
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RemoveException("Exception during cascade delete:" + e.toString());
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

	public abstract Date getApprovalDate();

	public abstract String getApproverEmployeeID1();

	public abstract String getApproverEmployeeID2();

	public abstract String getApproverEmployeeName1();

	public abstract String getApproverEmployeeName2();
	
	public abstract String getApproverEmployeeName3();
	
	public abstract String getApproverEmployeeName4();
	
	public abstract String getApproverEmployeeName5();

//	public abstract String getApprovingOfficerGrade();	
	
	//Added by Pramod Katkar for New Filed CR on 20-08-2013
	public abstract String getRamRating() ;	
	
	public abstract String getRamRatingYear();	
	
	public abstract String getRamRatingType();	
	//End by Pramod Katkar

	public abstract Date getBCACreateDate();

	public abstract String getBCAReference();

	public abstract Date getBflIndUpdateDate();

	public abstract long getCustomerID();

	public abstract double getExpectedLoss();

	public abstract Date getExtendedBFLIssuanceDate();

	public abstract Date getExtendedNextReviewDate();

	public abstract String getHostStatus();

	public abstract String getLEReference();

	public abstract String getLimitProfileRef();

	public abstract Date getNextAnnualReviewDate();

	public abstract Date getNextInterimReviewDate();

	public abstract double getProjectedProfit();

	public abstract String getSegment();

	public abstract Date getTATCreateDate();

	public abstract void setApprovalDate(Date value);

	public abstract void setApproverEmployeeID1(String value);

	public abstract void setApproverEmployeeID2(String value);

	public abstract void setApproverEmployeeName1(String value);

	public abstract void setApproverEmployeeName2(String value);
	
	public abstract void setApproverEmployeeName3(String value);
	
	public abstract void setApproverEmployeeName4(String value);
	
	public abstract void setApproverEmployeeName5(String value);

//	public abstract void setApprovingOfficerGrade(double value);
	public abstract void setRamRating(String ramRating);
	public abstract void setRamRatingYear(String ramRatingYear);
	public abstract void setRamRatingType(String ramRatingType);

	public abstract void setBCACreateDate(Date value);

	public abstract void setBCAReference(String value);

	public abstract void setBflIndUpdateDate(Date value);

	public abstract void setCustomerID(long value);

	public abstract void setExpectedLoss(double value);

	public abstract void setExtendedBFLIssuanceDate(Date extendedBFLIssuanceDate);

	public abstract void setExtendedNextReviewDate(Date value);

	public abstract void setHostStatus(String value);

	public abstract void setLEReference(String lEReference);

	public abstract void setLimitProfileRef(String value);

	public abstract void setNextAnnualReviewDate(Date value);

	public abstract void setNextInterimReviewDate(Date value);

	public abstract void setProjectedProfit(double value);

	public abstract void setSegment(String segment);

	public abstract void setTATCreateDate(Date value);

	public abstract void setVersionTime(long l);

	public abstract String getLosLimitProfileReference();

	public abstract void setLosLimitProfileReference(String losLimitProfileReference);
	
	public abstract String getMigratedInd();
	
	public abstract void setMigratedInd(String migratedInd);
	
	//Start:Code added for Total Sanctioned Amount For Facility
	public abstract String getTotalSanctionedAmountFacLevel() ;

	public abstract void setTotalSanctionedAmountFacLevel(String totalSanctionedAmountFacLevel) ;
	//End  :Code added for Total Sanctioned Amount For Facility

	// Added by Pradeep For UDF - 13th Oct 2011
	
	public abstract Collection getCMRUdfInfo();
	public abstract void setCMRUdfInfo(Collection value);
	
	public ILimitProfileUdf[] getUdfList() {
		return null;
	}
	public void setUdfList(ILimitProfileUdf[] value) {
		// do nothing
	}
	
	public void setObOtherCovenant(IOtherCovenant obOtherCovenant) {
		// do nothing
	}
	
	public IOtherCovenant getObOtherCovenant() {
		return null;
	}
	
	//Start:Uma Khot:Added for Valid Rating CR
	public abstract Date getRamRatingFinalizationDate();
	public abstract void setRamRatingFinalizationDate(Date ramRatingFinalizationDate);
	//End:Uma Khot:Added for Valid Rating CR
	
	private ILimitProfileUdf[] retrieveUdfInfo() throws LimitException {
		try {
			Collection c = getCMRUdfInfo();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBLimitProfileUdfLocal local = (EBLimitProfileUdfLocal) i.next();
					ILimitProfileUdf ob = local.getValue();
					aList.add(ob);
				}
				return (ILimitProfileUdf[]) aList.toArray(new ILimitProfileUdf[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof LimitException) {
				throw (LimitException) e;
			} else {
				throw new LimitException("Caught Exception: " + e.toString());
			}
		}
	}
	
	private void updateUdfInfo(ILimitProfileUdf[] addr, long LEID)	throws LimitException {
		try {
			Collection c = getCMRUdfInfo();
			if (null == addr) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} 
				else {
					// delete all records
					deleteUdfInfo(new ArrayList(c));
				}
			} 
			else if ((null == c) || (c.size() == 0)) {
				// create new records
				createUdfInfo(Arrays.asList(addr), LEID);
			} 
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBLimitProfileUdfLocal local = (EBLimitProfileUdfLocal) i.next();
					long id = local.getId();
					boolean update = false;

					for (int j = 0; j < addr.length; j++) {
						ILimitProfileUdf newOB = addr[j];

						if (newOB.getId() == id) {
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
				for (int j = 0; j < addr.length; j++) {
					i = c.iterator();
					ILimitProfileUdf newOB = addr[j];
					boolean found = false;

					while (i.hasNext()) {
						EBLimitProfileUdfLocal local = (EBLimitProfileUdfLocal) i.next();
						long id = local.getId();
						if (newOB.getId() == id) {
							found = true;
							break;
						}
					}
					
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteUdfInfo(deleteList);
				createUdfInfo(createList, LEID);
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
	
	private void createUdfInfo(List createList, long LEID) throws LimitException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRUdfInfo();
		Iterator i = createList.iterator();
		try {
			EBLimitProfileUdfLocalHome home = getEBLimitProfileUdfLocalUdfInfo();
			while (i.hasNext()) {
				ILimitProfileUdf ob = (ILimitProfileUdf) i.next();
				if (ob != null) {
					DefaultLogger.debug(this, "Creating BankingMethod ID: "	+ ob.getId());
					String serverType = (new BatchResourceFactory()).getAppServerType();
					DefaultLogger.debug(this,"=======Application server Type is(banking method) ======= : "+ serverType);
					if (serverType.equals(ICMSConstant.WEBSPHERE)) {
						ob.setLimitProfileId(LEID);
					}
					EBLimitProfileUdfLocal local = home.create(ob);
					c.add(local);
				}
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof LimitException) {
				throw (LimitException) e;
			} 
			else {
				throw new LimitException("Caught Exception: " + e.toString());
			}
		}
	}
	
	private void deleteUdfInfo(List deleteList) throws LimitException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRUdfInfo();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBLimitProfileUdfLocal local = (EBLimitProfileUdfLocal) i.next();
				c.remove(local);
				local.remove();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof LimitException) {
				throw (LimitException) e;
			} else {
				throw new LimitException("Caught Exception: " + e.toString());
			}
		}
	}
	

	protected EBLimitProfileUdfLocalHome getEBLimitProfileUdfLocalUdfInfo() throws LimitException {
		EBLimitProfileUdfLocalHome home = (EBLimitProfileUdfLocalHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_LIMIT_PROFILE_UDF_LOCAL_JNDI, EBLimitProfileUdfLocalHome.class.getName());
		if (null != home) {
			return home;
		} else {
			throw new LimitException("EBCMSCustomerUdfLocalHome is null!");
		}
	}
	
	public ILimitProfileUdf[] getUdfData() {
		return null;
	}
	public void setUdfData(ILimitProfileUdf[] udfData) {
		
	}
	
	//Start:Uma Khot:CRI Field addition enhancement
	public abstract String getIsSpecialApprGridForCustBelowHDB8();
	public abstract void setIsSpecialApprGridForCustBelowHDB8(String isSpecialApprGridForCustBelowHDB8) ;
	public abstract String getIsSingleBorrowerPrudCeiling();
	public abstract void setIsSingleBorrowerPrudCeiling(String isSingleBorrowerPrudCeiling);
	public abstract String getDetailsOfRbiApprovalForSingle();
	public abstract void setDetailsOfRbiApprovalForSingle(String detailsOfRbiApprovalForSingle);
	public abstract String getIsGroupBorrowerPrudCeiling();
	public abstract void setIsGroupBorrowerPrudCeiling(String isGroupBorrowerPrudCeiling);
	public abstract String getDetailsOfRbiApprovalForGroup();
	public abstract void setDetailsOfRbiApprovalForGroup(String detailsOfRbiApprovalForGroup);
	public abstract String getIsNonCooperativeBorrowers();
	public abstract void setIsNonCooperativeBorrowers(String isNonCooperativeBorrowers);
	public abstract String getIsDirectorAsNonCooperativeForOther();
	public abstract void setIsDirectorAsNonCooperativeForOther(String isDirectorAsNonCooperativeForOther);
	public abstract String getNameOfDirectorsAndCompany();
	public abstract void setNameOfDirectorsAndCompany(String nameOfDirectorsAndCompany);
	 //End:Uma Khot:CRI Field addition enhancement
	
	public abstract String getRbiApprovalForSingle();
	public abstract void setRbiApprovalForSingle(String rbiApprovalForSingle);
	public abstract String getRbiApprovalForGroup();
	public abstract void setRbiApprovalForGroup(String rbiApprovalForGroup);

}