/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCustomerSysXRefBean.java,v 1.7 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.batch.common.BatchResourceFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This entity bean represents the persistence for CustomerSysXRef Information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public abstract class EBCustomerSysXRefBean implements EntityBean, ICustomerSysXRef {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CUSTOMER_X_REF;

	private static final String[] EXCLUDE_METHOD = new String[] { "getXRefID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBCustomerSysXRefBean() {
	}

	// ************* Non-persistent methods ***********
	/**
	 * Get Customer ID
	 * 
	 * @return long
	 */
	public long getCustomerID() {
		if (null != getCustomerFK()) {
			return getCustomerFK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get the XRef ID
	 * 
	 * @return long
	 */
	public long getXRefID() {
		if (null != getXRefPK()) {
			return getXRefPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Set Customer ID
	 * 
	 * @param value is of type long
	 */
	public void setCustomerID(long value) {
		setCustomerFK(new Long(value));
	}

	/**
	 * Set the XRef ID
	 * 
	 * @param value is of type long
	 */
	public void setXRefID(long value) {
		setXRefPK(new Long(value));
	}

	public Boolean getAccountDelinq() {
		if (ICMSConstant.TRUE_VALUE.equals(getAccountDelqInd())) {

			return new Boolean(true);
		}
		else if (ICMSConstant.FALSE_VALUE.equals(getAccountDelqInd())) {
			return new Boolean(false);
		}
		else {
			return null;
		}
	}

	public void setAccountDelinq(Boolean accountDelinq) {
		if (accountDelinq != null) {
			if (accountDelinq.booleanValue()) {
				setAccountDelqInd(ICMSConstant.TRUE_VALUE);
			}
			else {
				setAccountDelqInd(ICMSConstant.FALSE_VALUE);
			}
		}
		else {
			setAccountDelqInd(null);
		}
	}

	public Amount getRVForAccount() {
		Double rvVal = getEBAccountRv();
		if (rvVal != null) {
			return new Amount(rvVal.doubleValue(), getAccountRvCcy());
		}
		else {
			return null;
		}
	}

	public void setRVForAccount(Amount amt) {
	}

	public Amount getCollateralAllocatedAmt() {
		Double colAllocAmt = getEBCollateralAllocationAmt();
		if (colAllocAmt != null) {
			return new Amount(colAllocAmt.doubleValue(), getColAllocationAmtCcy());
		} else {
			return null;
		}
	}
	
	public void setCollateralAllocatedAmt(Amount amt) {		
	}
	
	public Amount getOutstandingAmt() {
		Double outstandingAmt = getEBOutstandingAmt();
		if (outstandingAmt != null) {
			return new Amount(outstandingAmt.doubleValue(), getOutstandingAmtCcy());
		} else {
			return null;
		}
		
	}
	
	public void setOutstandingAmt(Amount amt) {		
	}
	
	// ************** Abstract methods ************
	/**
	 * Get Customer ID
	 * 
	 * @return Long
	 */
	public abstract Long getCustomerFK();

	/**
	 * Get the XRef PK
	 * 
	 * @return Long
	 */
	public abstract Long getXRefPK();

	/**
	 * Set Customer ID
	 * 
	 * @param value is of type Long
	 */
	public abstract void setCustomerFK(Long value);

	/**
	 * Set the XRef PK
	 * 
	 * @param value is of type Long
	 */
	public abstract void setXRefPK(Long value);

	public abstract String getAccountDelqInd();

	public abstract void setAccountDelqInd(String accountDelqInd);

	public abstract Double getEBAccountRv();

	public abstract void setEBAccountRv(Double eBAccountRv);

	public abstract String getAccountRvCcy();

	public abstract void setAccountRvCcy(String currency);

	public abstract String getExternalAccountType();

	public abstract void setExternalAccountType(String accountType);    

	// *****************************************************
	/**
	 * Create a CustomerSysXRef Information
	 * 
	 * @param customerID the customer ID of type long
	 * @param value is the ICustomerSysXRef object
	 * @return Long the CustomerSysXRef ID primary key
	 * @throws CreateException on error
	 */
	public Long ejbCreate(long customerID, ICustomerSysXRef value) throws CreateException {
		if (null == value) {
			throw new CreateException("ICustomerSysXRef is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
			/*
			 * long pk =com.integrosys.cms.app.common.constant.ICMSConstant.
			 * LONG_INVALID_VALUE; if(value.getXRefID() ==
			 * com.integrosys.cms.app
			 * .common.constant.ICMSConstant.LONG_INVALID_VALUE) { pk =
			 * Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME,
			 * true)); } else { pk = value.getXRefID(); }
			 */
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setCustomerID(customerID); // //to be set by cmr
			setXRefID(pk);

			return new Long(pk);
		}
		catch (Exception e) {
			e.printStackTrace();
			_context.setRollbackOnly();
			throw new CreateException("Unknown Exception Caught: " + e.toString());
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param customerID the customer ID of type long
	 * @param value is the ICustomerSysXRef object
	 */
	public void ejbPostCreate(long customerID, ICustomerSysXRef value) {
		// do nothing
	}

	/**
	 * Return the OB representation of this object
	 * 
	 * @return ICustomerSysXRef
	 */
	public ICustomerSysXRef getValue() {
		OBCustomerSysXRef value = new OBCustomerSysXRef();
		AccessorUtil.copyValue(this, value);
		value.setXRefUdfData(retrieveXRefUdfInfo());
		value.setLineCovenant(retrieveLineCovenant());
		value.setXRefUdfData2(retrieveXRefUdfInfo2());
		value.setXRefCoBorrowerData(retrieveXRefCoBorrowerInfo());
		return value;
	}

	/**
	 * Persist a CustomerSysXRef information
	 * 
	 * @param value is of type ICustomerSysXRef
	 */
	public void setValue(ICustomerSysXRef value) throws CustomerException{
		
		try{	
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			updateDependants(value);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CustomerException) {
				throw (CustomerException) e;
			} else {
				throw new CustomerException("Caught Exception: " + e.toString());
			}
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
	public void ejbRemove() {
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

    public abstract String getExternalXRef();

    public abstract String getBookingLocation();

    public abstract String getExternalSystemCode();

    public abstract String getExternalCustomerID();

    public abstract String getExternalCustomerName();

    public abstract String getExternalAccountNo();

    public abstract String getExternalAccountStatus();

    public abstract void setExternalXRef(String value);

    public abstract void setBookingLocation(String value);

    public abstract void setExternalSystemCode(String value);

    public abstract void setExternalCustomerID(String value);

    public abstract void setExternalCustomerName(String value);

    public abstract void setExternalAccountNo(String value);

    public abstract void setExternalAccountStatus(String value);

    public abstract String getAccountStatus();

    public abstract void setAccountStatus(String accountStatus);

    public abstract Date getAcctEffectiveDate();

    public abstract void setAcctEffectiveDate(Date acctEffectiveDate);

    public abstract String getExternalSysCountry();

    public abstract void setExternalSysCountry(String externalSysCountry);

    public abstract String getExternalSystemCodeNum();

    public abstract void setExternalSystemCodeNum(String externalSystemCodeNum);

    public abstract String getBookingLoctnCountry();

    public abstract void setBookingLoctnCountry(String bookingLoctnCountry);

    public abstract String getBookingLoctnOrganisation();

    public abstract void setBookingLoctnOrganisation(String bookingLoctnOrganisation);
    
    public abstract Date getLastAllocationDate(); 
    
    public abstract void setLastAllocationDate(Date lastAllocationDate);
    
    public abstract String getColAllocationAmtCcy();

    public abstract void setColAllocationAmtCcy (String currency);
    
    public abstract String getOutstandingAmtCcy();
    
    public abstract void setOutstandingAmtCcy (String currency);

    public abstract Double getEBCollateralAllocationAmt();
    
    public abstract void setEBCollateralAllocationAmt (Double amt);
    
    public abstract Double getEBOutstandingAmt();
    
    public abstract void setEBOutstandingAmt (Double amt);
    
    
    //added by Shiv
    public abstract String getSerialNo();
	public abstract void setSerialNo(String serialNo);

	public abstract String getInterestRateType();
	public abstract void setInterestRateType(String interestRateType) ;

	public abstract String getIntRateFloatingType();
	public abstract void setIntRateFloatingType(String intRateFloatingType);

	public abstract String getIntRateFloatingRange();
	public abstract void setIntRateFloatingRange(String intRateFloatingRange);
	
	public abstract String getIntRateFloatingMarginFlag();
	public abstract void setIntRateFloatingMarginFlag(String intRateFloatingMarginFlag);

	public abstract String getIntRateFloatingMargin();
	public abstract void setIntRateFloatingMargin(String intRateFloatingMargin);

	public abstract String getReleasedAmount();
	public abstract void setReleasedAmount(String releasedAmount);
	
	public abstract String getUtilizedAmount();
	public abstract void setUtilizedAmount(String utilizedAmount);
	
	public abstract Date getReleaseDate();
	public abstract void setReleaseDate(Date releaseDate);
	
	public abstract void setIntradayLimitExpiryDate(Date intradayLimitExpiryDate);
	public abstract Date getIntradayLimitExpiryDate(); 
	
	public abstract void setDayLightLimit(String dayLightLimit); 
	public abstract String getDayLightLimit();
	
	public abstract void setIntradayLimitFlag(String intradayLimitFlag);
	public abstract String getIntradayLimitFlag(); 

	public abstract Date getDateOfReset();
	public abstract void setDateOfReset(Date dateOfReset);

	public abstract String getIsPermntSSICert();
	public abstract void setIsPermntSSICert(String isPermntSSICert);

	public abstract String getIsCapitalMarketExposer();
	public abstract void setIsCapitalMarketExposer(String isCapitalMarketExposer);

	public abstract String getIsRealEstateExposer();
	public abstract void setIsRealEstateExposer(String isRealEstateExposer);
	
	public abstract String getEstateType();
	public abstract void setEstateType(String estateType);

	public abstract String getIsPrioritySector();
	public abstract void setIsPrioritySector(String isPrioritySector);

	public abstract String getPrioritySector();
	public abstract void setPrioritySector(String prioritySector);
	
	public abstract String getSecurity1();
	public abstract void setSecurity1(String security1);
	
	public abstract String getSecurity2();
	public abstract void setSecurity2(String security2);

	public abstract String getSecurity3();
	public abstract void setSecurity3(String security3);
	
	public abstract String getSecurity4();
	public abstract void setSecurity4(String security4);
	
	public abstract String getSecurity5();
	public abstract void setSecurity5(String security5);

	public abstract String getSecurity6();
	public abstract void setSecurity6(String security6);
	
	public abstract String getFacilitySystem();
	public abstract void setFacilitySystem(String facilitySystem);
	
	public abstract String getFacilitySystemID();
	public abstract void setFacilitySystemID(String facilitySystemID);
	
	public abstract String getLineNo();
	public abstract void setLineNo(String lineNo);
	
	public abstract String getIntRateFix();
	public abstract void setIntRateFix(String intRateFix);
	
	public abstract String getCommRealEstateType();
	public abstract void setCommRealEstateType(String commRealEstateType);
	
	//Start:Code added for Upload Status
	public abstract String getUploadStatus();
	public abstract void setUploadStatus(String uploadStatus);
	//End  :Code added for Upload Status	
	//file upload audit trail at tranch level
	public abstract String getCreatedBy();
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedOn();
	public abstract void setCreatedOn(Date createdOn);
	
	public abstract String getUpdatedBy();
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedOn();
	public abstract void setUpdatedOn(Date updatedOn);
	
	public abstract String getHiddenSerialNo();
	public abstract void setHiddenSerialNo(String hiddenSerialNo);
	


	public abstract String getLiabBranch();
	public abstract void setLiabBranch(String liabBranch);

	public abstract String getCurrencyRestriction();
	public abstract void setCurrencyRestriction(String currencyRestriction);

	public abstract String getMainLineCode();
	public abstract void setMainLineCode(String mainLineCode);

	public abstract String getCurrency();
	public abstract void setCurrency(String currency);

	
	public abstract String getAvailable();
	public abstract void setAvailable(String available);

	public abstract String getFreeze();
	public abstract void setFreeze(String freeze);

	public abstract String getRevolvingLine();
	public abstract void setRevolvingLine(String revolvingLine);
	
	public abstract String getScmFlag();
	public abstract void setScmFlag(String scmFlag);
	
	public abstract void setVendorDtls(String vendorDtls);
	public abstract String getVendorDtls();

	public abstract String getCloseFlag();
	public abstract void setCloseFlag(String closeFlag);

	public abstract Date getLastavailableDate();
	public abstract void setLastavailableDate(Date lastavailableDate);

	public abstract Date getUploadDate();
	public abstract void setUploadDate(Date uploadDate);

	public abstract String getSegment();
	public abstract void setSegment(String segment);

	public abstract String getRuleId();
	public abstract void setRuleId(String ruleId);

	public abstract String getUncondiCancl();
	public abstract void setUncondiCancl(String uncondiCancl);


	public abstract String getLimitTenorDays();
	public abstract void setLimitTenorDays(String limitTenorDays);

	public abstract String getInternalRemarks();
	public abstract void setInternalRemarks(String internalRemarks);

	
	public abstract String getCoreStpRejectedReason();
	public abstract void setCoreStpRejectedReason(String coreStpRejectedReason);

	
	public abstract String getSourceRefNo();
	public abstract void setSourceRefNo(String sourceRefNo);

	public abstract Date getLimitStartDate();
	public abstract void setLimitStartDate(Date limitStartDate);

	public abstract String getAction();
	public abstract void setAction(String action);

	public abstract String getStatus();
	public abstract void setStatus(String status);
	
	//Start Santosh UBS-LIMIT CR
	public abstract String getLimitRestrictionFlag();
	public abstract void setLimitRestrictionFlag(String limitRestrictionFlag);

	public abstract String getBranchAllowedFlag();
	public abstract void setBranchAllowedFlag(String branchAllowedFlag);

	public abstract String getProductAllowedFlag();
	public abstract void setProductAllowedFlag(String productAllowedFlag);

	public abstract String getCurrencyAllowedFlag();
	public abstract void setCurrencyAllowedFlag(String currencyAllowedFlag);
	
	public abstract String getIsCapitalMarketExposerFlag();
	public abstract void setIsCapitalMarketExposerFlag(String isCapitalMarketExposerFlag); 

	public abstract String getSegment1Flag(); 
	public abstract void setSegment1Flag(String segment1Flag); 

	public abstract String getEstateTypeFlag(); 
	public abstract void setEstateTypeFlag(String estateTypeFlag); 

	public abstract String getPrioritySectorFlag(); 
	public abstract void setPrioritySectorFlag(String prioritySectorFlag); 

	/*public  ILimitXRefUdf[] getUdfData(){
		return null;
	}
	public  void setUdfData(ILimitXRefUdf[] udfData){
		
	}
	//End Santosh UBS-LIMIT CR	
*/	
	
	public abstract String getLimitRestriction();
	public abstract void setLimitRestriction(String limitRestriction);

	public abstract String getBranchAllowed();
	public abstract void setBranchAllowed(String branchAllowed);

	public abstract String getProductAllowed();
	public abstract void setProductAllowed(String productAllowed);

	public abstract String getCurrencyAllowed();
	public abstract void setCurrencyAllowed(String currencyAllowed);

	public abstract String getUdfAllowed();
	public abstract void setUdfAllowed(String udfAllowed);
	
	public abstract String getUdfAllowed_2();
	public abstract void setUdfAllowed_2(String udfAllowed_2);	
	
	public abstract String getUncondiCanclFlag(); 
	public abstract void setUncondiCanclFlag(String uncondiCanclFlag); 
	
	
	public abstract String getUdfDelete();
	public abstract void setUdfDelete(String UdfDelete);
	
	public abstract String getUdfDelete_2();
	public abstract void setUdfDelete_2(String UdfDelete_2);
	
	public abstract String getSendToCore();
	public abstract void setSendToCore(String sendToCore);
	
	public abstract String getSendToFile();
	public abstract void setSendToFile(String sendToFile);
	
	public abstract String getIsDayLightLimitAvailable();
	public abstract void setIsDayLightLimitAvailable(String isDayLightLimitAvailable);

	public abstract String getDayLightLimitApproved();
	public abstract void setDayLightLimitApproved(String dayLightLimitApproved);
	
	public abstract String getStockLimitFlag();
	public abstract void setStockLimitFlag(String stockLimitFlag);
	
	public abstract String getStockDocMonthForLmt();
	public abstract void setStockDocMonthForLmt(String stockDocMonthForLmt);

	public abstract String getStockDocYearForLmt();
	public abstract void setStockDocYearForLmt(String stockDocYearForLmt);
	
	public abstract Date getIdlEffectiveFromDate();
	public abstract void setIdlEffectiveFromDate(Date idlEffectiveFromDate);

	public abstract Date getIdlExpiryDate();
	public abstract void setIdlExpiryDate(Date idlExpiryDate);

	public abstract String getIdlAmount();
	public abstract void setIdlAmount(String idlAmount);
	
	//added by santosh UBS Limit upload
	// Method to create child dependants via CMR
	public void createDependants(long customerID,ICustomerSysXRef value)
			throws CustomerException {
		if (customerID != getCustomerID()) {
			throw new CustomerException("customerID mismatched!");
		} else {
			updateDependants(value);
		}
	}
	
	//Method to update child dependants
	private void updateDependants(ICustomerSysXRef value) throws CustomerException {
		updateXRefUdfInfo(value.getXRefUdfData(), value.getXRefID());
		updateLineCovenant(value.getLineCovenant(),value.getXRefID());
		updateXRefUdfInfo2(value.getXRefUdfData2(), value.getXRefID());
		updateXRefCoBorrower(value.getXRefCoBorrowerData(), value.getXRefID());
	}
	
	public abstract Collection getCMRXRefUdfInfo();
	public abstract void setCMRXRefUdfInfo(Collection value);
	
	public abstract Collection getCMRXRefUdfInfo2();
	public abstract void setCMRXRefUdfInfo2(Collection value);
	
	
	public abstract Collection getCMRXRefCoBorrower();
	public abstract void setCMRXRefCoBorrower(Collection value);
	
	
		private ILimitXRefUdf[] retrieveXRefUdfInfo() throws CustomerException {
			try {
				Collection c = getCMRXRefUdfInfo();
				if ((null == c) || (c.size() == 0)) {
					return null;
				} else {
					ArrayList aList = new ArrayList();
					Iterator i = c.iterator();
					while (i.hasNext()) {
						EBLimitXRefUdfLocal local = (EBLimitXRefUdfLocal) i.next();
						ILimitXRefUdf ob = local.getValue();
						aList.add(ob);
					}
					return (ILimitXRefUdf[]) aList.toArray(new ILimitXRefUdf[0]);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof CustomerException) {
					throw (CustomerException) e;
				} else {
					throw new CustomerException("Caught Exception: " + e.toString());
				}
			}
		}
		
		private ILimitXRefUdf2[] retrieveXRefUdfInfo2() throws CustomerException {
		try {
			Collection c = getCMRXRefUdfInfo2();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBLimitXRefUdfLocal2 local = (EBLimitXRefUdfLocal2) i.next();
					ILimitXRefUdf2 ob = local.getValue();
					aList.add(ob);
				}
				return (ILimitXRefUdf2[]) aList.toArray(new ILimitXRefUdf2[0]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CustomerException) {
				throw (CustomerException) e;
			} else {
				throw new CustomerException("Caught Exception: " + e.toString());
			}
		}
  }
		private void updateXRefUdfInfo(ILimitXRefUdf[] addr, long XRefID)	throws CustomerException {
			try {
				Collection c = getCMRXRefUdfInfo();
				if (null == addr) {
					if ((null == c) || (c.size() == 0)) {
						return; // nothing to do
					} 
					else {
						// delete all records
						deleteXRefUdfInfo(new ArrayList(c));
					}
				} 
				else if ((null == c) || (c.size() == 0)) {
					// create new records
					createXRefUdfInfo(Arrays.asList(addr), XRefID);
				} 
				else {
					Iterator i = c.iterator();
					ArrayList createList = new ArrayList(); // contains list of OBs
					ArrayList deleteList = new ArrayList(); // contains list of
					// local interfaces

					// identify identify records for delete or udpate first
					while (i.hasNext()) {
						EBLimitXRefUdfLocal local = (EBLimitXRefUdfLocal) i.next();
						long id = local.getId();
						boolean update = false;

						for (int j = 0; j < addr.length; j++) {
							ILimitXRefUdf newOB = addr[j];

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
						ILimitXRefUdf newOB = addr[j];
						boolean found = false;

						while (i.hasNext()) {
							EBLimitXRefUdfLocal local = (EBLimitXRefUdfLocal) i.next();
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
					deleteXRefUdfInfo(deleteList);
					createXRefUdfInfo(createList, XRefID);
				}
			} 
			catch (CustomerException e) {
				throw e;
			} 
			catch (Exception e) {
				e.printStackTrace();
				throw new CustomerException("Caught Exception: " + e.toString());
			}
		}
		
		private void updateXRefUdfInfo2(ILimitXRefUdf2[] addr, long XRefID)	throws CustomerException {
			try {
				Collection c = getCMRXRefUdfInfo2();
				if (null == addr) {
					if ((null == c) || (c.size() == 0)) {
						return; // nothing to do
					} 
					else {
						// delete all records
						deleteXRefUdfInfo2(new ArrayList(c));
					}
				} 
				else if ((null == c) || (c.size() == 0)) {
					// create new records
					createXRefUdfInfo2(Arrays.asList(addr), XRefID);
				} 
				else {
					Iterator i = c.iterator();
					ArrayList createList = new ArrayList(); // contains list of OBs
					ArrayList deleteList = new ArrayList(); // contains list of
					// local interfaces

					// identify identify records for delete or udpate first
					while (i.hasNext()) {
						EBLimitXRefUdfLocal2 local = (EBLimitXRefUdfLocal2) i.next();
						long id = local.getId();
						boolean update = false;

						for (int j = 0; j < addr.length; j++) {
							ILimitXRefUdf2 newOB = addr[j];

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
						ILimitXRefUdf2 newOB = addr[j];
						boolean found = false;

						while (i.hasNext()) {
							EBLimitXRefUdfLocal2 local = (EBLimitXRefUdfLocal2) i.next();
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
					deleteXRefUdfInfo2(deleteList);
					createXRefUdfInfo2(createList, XRefID);
				}
			} 
			catch (CustomerException e) {
				throw e;
			} 
			catch (Exception e) {
				e.printStackTrace();
				throw new CustomerException("Caught Exception: " + e.toString());
			}
		}
		
		
		private void createXRefUdfInfo(List createList, long XRefID) throws CustomerException {
			if ((null == createList) || (createList.size() == 0)) {
				return; // do nothing
			}
			Collection c = getCMRXRefUdfInfo();
			Iterator i = createList.iterator();
			try {
				EBLimitXRefUdfLocalHome home = getEBLocalHomeXRefUdfInfo();
				while (i.hasNext()) {
					ILimitXRefUdf ob = (ILimitXRefUdf) i.next();
					if (ob != null) {
						DefaultLogger.debug(this, "Creating BankingMethod ID: "	+ ob.getId());
						String serverType = (new BatchResourceFactory()).getAppServerType();
						DefaultLogger.debug(this,"=======Application server Type is(banking method) ======= : "+ serverType);
						if (serverType.equals(ICMSConstant.WEBSPHERE)) {
							ob.setXRefID(XRefID);
						}
						EBLimitXRefUdfLocal local = home.create(ob);
						c.add(local);
					}
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
				if (e instanceof CustomerException) {
					throw (CustomerException) e;
				} 
				else {
					throw new CustomerException("Caught Exception: " + e.toString());
				}
			}
		}
		
		private void createXRefUdfInfo2(List createList, long XRefID) throws CustomerException {
			if ((null == createList) || (createList.size() == 0)) {
				return; // do nothing
			}
			Collection c = getCMRXRefUdfInfo2();
			Iterator i = createList.iterator();
			try {
				EBLimitXRefUdfLocalHome2 home = getEBLocalHomeXRefUdfInfo2();
				while (i.hasNext()) {
					ILimitXRefUdf2 ob = (ILimitXRefUdf2) i.next();
					if (ob != null) {
						DefaultLogger.debug(this, "Creating BankingMethod ID: "	+ ob.getId());
						String serverType = (new BatchResourceFactory()).getAppServerType();
						DefaultLogger.debug(this,"=======Application server Type is(banking method) ======= : "+ serverType);
						if (serverType.equals(ICMSConstant.WEBSPHERE)) {
							ob.setXRefID(XRefID);
						}
						EBLimitXRefUdfLocal2 local = home.create(ob);
						c.add(local);
					}
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
				if (e instanceof CustomerException) {
					throw (CustomerException) e;
				} 
				else {
					throw new CustomerException("Caught Exception: " + e.toString());
				}
			}
		}
		private void deleteXRefUdfInfo(List deleteList) throws CustomerException {
			if ((null == deleteList) || (deleteList.size() == 0)) {
				return; // do nothing
			}
			try {
				Collection c = getCMRXRefUdfInfo();
				Iterator i = deleteList.iterator();
				while (i.hasNext()) {
					EBLimitXRefUdfLocal local = (EBLimitXRefUdfLocal) i.next();
					c.remove(local);
					local.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof CustomerException) {
					throw (CustomerException) e;
				} else {
					throw new CustomerException("Caught Exception: " + e.toString());
				}
			}
		}
		
		//For UDF -2
		private void deleteXRefUdfInfo2(List deleteList) throws CustomerException {
			if ((null == deleteList) || (deleteList.size() == 0)) {
				return; // do nothing
			}
			try {
				Collection c = getCMRXRefUdfInfo2();
				Iterator i = deleteList.iterator();
				while (i.hasNext()) {
					EBLimitXRefUdfLocal2 local = (EBLimitXRefUdfLocal2) i.next();
					c.remove(local);
					local.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof CustomerException) {
					throw (CustomerException) e;
				} else {
					throw new CustomerException("Caught Exception: " + e.toString());
				}
			}
		}
		
		
		protected EBLimitXRefUdfLocalHome getEBLocalHomeXRefUdfInfo() throws CustomerException {
			EBLimitXRefUdfLocalHome home = (EBLimitXRefUdfLocalHome) BeanController	.getEJBLocalHome(ICMSJNDIConstant.EB_XREF_UDF_LOCAL_JNDI, EBLimitXRefUdfLocalHome.class.getName());
			if (null != home) {
				return home;
			} else {
				throw new CustomerException("EBLimitXRefUdfLocalHome is null!");
			}
		}
		
		protected EBLimitXRefUdfLocalHome2 getEBLocalHomeXRefUdfInfo2() throws CustomerException {
			EBLimitXRefUdfLocalHome2 home = (EBLimitXRefUdfLocalHome2) BeanController	.getEJBLocalHome(ICMSJNDIConstant.EB_XREF_UDF_LOCAL_JNDI2, EBLimitXRefUdfLocalHome2.class.getName());
			if (null != home) {
				return home;
			} else {
				throw new CustomerException("EBLimitXRefUdfLocalHome2 is null!");
			}
		}
		public ILimitXRefUdf[] getXRefUdfData() {
			return null;
		}
		public void setXRefUdfData(ILimitXRefUdf[] udfData) {
			
		}
		
		public ILimitXRefUdf2[] getXRefUdfData2() {
			return null;
		}
		public void setXRefUdfData2(ILimitXRefUdf2[] udfData) {
			
		}
		
		public ILimitXRefCoBorrower[] getXRefCoBorrowerData() {
			return null;
		}
		public void setXRefCoBorrowerData(ILimitXRefCoBorrower[] coBorrowerData) {
			
		}
		
		
		public abstract String getCommRealEstateTypeFlag();
		public abstract void setCommRealEstateTypeFlag(String sendToFile);

		public abstract String getTenure();
		public abstract void setTenure(String tenure);
		
		public abstract String getSellDownPeriod();
		public abstract void setSellDownPeriod(String sellDownPeriod);

		public abstract String getLiabilityId();
		public abstract void setLiabilityId(String liabilityId);

		public abstract String getLimitRemarks();
		public abstract void setLimitRemarks(String limitRemarks);
		
		public abstract String getCheckerIdNew();
		public abstract void setCheckerIdNew(String checkerIdNew);
		
		//Covenant
		
		public abstract Collection getCMRLineCovenant();
		public abstract void setCMRLineCovenant(Collection value);
		
			private ILineCovenant[] retrieveLineCovenant() throws CustomerException {
				try {
					Collection c = getCMRLineCovenant();
					if ((null == c) || (c.size() == 0)) {
						return null;
					} else {
						ArrayList aList = new ArrayList();
						Iterator i = c.iterator();
						while (i.hasNext()) {
							EBLineCovenantLocal local = (EBLineCovenantLocal) i.next();
							ILineCovenant ob = local.getValue();
							aList.add(ob);
						}
						return (ILineCovenant[]) aList.toArray(new ILineCovenant[0]);
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof CustomerException) {
						throw (CustomerException) e;
					} else {
						throw new CustomerException("Caught Exception: " + e.toString());
					}
				}
			}
			
			private void updateLineCovenant(ILineCovenant[] line, long XRefID)	throws CustomerException {
				try {
					Collection c = getCMRLineCovenant();
					if (null == line) {
						if ((null == c) || (c.size() == 0)) {
							return; // nothing to do
						} 
						else {
							// delete all records
							deleteLineCovenant(new ArrayList(c));
						}
					} 
					else if ((null == c) || (c.size() == 0)) {
						// create new records
						createLineCovenant(Arrays.asList(line), XRefID);
					} 
					else {
						Iterator i = c.iterator();
						ArrayList createList = new ArrayList(); // contains list of OBs
						ArrayList deleteList = new ArrayList(); // contains list of
						// local interfaces

						// identify identify records for delete or udpate first
						while (i.hasNext()) {
							EBLineCovenantLocal local = (EBLineCovenantLocal) i.next();
							long id = local.getCovenantId();
							boolean update = false;

							for (int j = 0; j < line.length; j++) {
								ILineCovenant newOB = line[j];

								if (newOB.getCovenantId() == id) {
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
						for (int j = 0; j < line.length; j++) {
							i = c.iterator();
							ILineCovenant newOB = line[j];
							boolean found = false;

							while (i.hasNext()) {
								EBLineCovenantLocal local = (EBLineCovenantLocal) i.next();
								long id = local.getCovenantId();
								if (newOB.getCovenantId() == id) {
									found = true;
									break;
								}
							}
							
							if (!found) {
								// add for adding
								createList.add(newOB);
							}
						}
						deleteLineCovenant(deleteList);
						createLineCovenant(createList, XRefID);
					}
				} 
				catch (CustomerException e) {
					throw e;
				} 
				catch (Exception e) {
					e.printStackTrace();
					throw new CustomerException("Caught Exception: " + e.toString());
				}
			}
			
			private void createLineCovenant(List createList, long XRefID) throws CustomerException {
				if ((null == createList) || (createList.size() == 0)) {
					return; // do nothing
				}
				Collection c = getCMRLineCovenant();
				Iterator i = createList.iterator();
				try {
					EBLineCovenantLocalHome home = getEBLocalHomeLineCovenant();
					while (i.hasNext()) {
						ILineCovenant ob = (ILineCovenant) i.next();
						if (ob != null) {
							DefaultLogger.debug(this, "Creating BankingMethod ID: "	+ ob.getCovenantId());
							String serverType = (new BatchResourceFactory()).getAppServerType();
							DefaultLogger.debug(this,"=======Application server Type is(banking method) ======= : "+ serverType);
							if (serverType.equals(ICMSConstant.WEBSPHERE)) {
								ob.setLineFK(XRefID);
							}
							EBLineCovenantLocal local = home.create(ob);
							c.add(local);
						}
					}
				} 
				catch (Exception e) {
					e.printStackTrace();
					if (e instanceof CustomerException) {
						throw (CustomerException) e;
					} 
					else {
						throw new CustomerException("Caught Exception: " + e.toString());
					}
				}
			}
			
			private void deleteLineCovenant(List deleteList) throws CustomerException {
				if ((null == deleteList) || (deleteList.size() == 0)) {
					return; // do nothing
				}
				try {
					Collection c = getCMRLineCovenant();
					Iterator i = deleteList.iterator();
					while (i.hasNext()) {
						EBLineCovenantLocal local = (EBLineCovenantLocal) i.next();
						c.remove(local);
						local.remove();
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (e instanceof CustomerException) {
						throw (CustomerException) e;
					} else {
						throw new CustomerException("Caught Exception: " + e.toString());
					}
				}
			}
			
			protected EBLineCovenantLocalHome getEBLocalHomeLineCovenant() throws CustomerException {
				EBLineCovenantLocalHome home = (EBLineCovenantLocalHome) BeanController	.getEJBLocalHome(ICMSJNDIConstant.EB_LINE_COVENANT_LOCAL_JNDI, EBLineCovenantLocalHome.class.getName());
				if (null != home) {
					return home;
				} else {
					throw new CustomerException("EBLineCovenantLocalHome is null!");
				}
			}
			
			public ILineCovenant[] getLineCovenant() {
				return null;
			}
			public void setLineCovenant(ILineCovenant[] udfData) {
				
			}
		
		public abstract String getBankingArrangement();
		public abstract void setBankingArrangement(String bankingArrangement);
		
		public abstract String getAdhocLine();
		public abstract void setAdhocLine(String adhocLine);
		
		private ILimitXRefCoBorrower[] retrieveXRefCoBorrowerInfo() throws CustomerException {
			try {
				Collection c = getCMRXRefCoBorrower();
				if ((null == c) || (c.size() == 0)) {
					return null;
				} else {
					ArrayList aList = new ArrayList();
					Iterator i = c.iterator();
					while (i.hasNext()) {
						EBLimitXRefCoBorrowerLocal local = (EBLimitXRefCoBorrowerLocal) i.next();
						ILimitXRefCoBorrower ob = local.getValue();
						aList.add(ob);
					}
					return (ILimitXRefCoBorrower[]) aList.toArray(new ILimitXRefCoBorrower[0]);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof CustomerException) {
					throw (CustomerException) e;
				} else {
					throw new CustomerException("Caught Exception: " + e.toString());
				}
			}
	  }
		
		private void updateXRefCoBorrower(ILimitXRefCoBorrower[] addr, long XRefID)	throws CustomerException {
			try {
				Collection c = getCMRXRefCoBorrower();
				if (null == addr) {
					if ((null == c) || (c.size() == 0)) {
						return; // nothing to do
					} 
					else {
						// delete all records
						deleteXRefCoBorrower(new ArrayList(c));
					}
				} 
				else if ((null == c) || (c.size() == 0)) {
					// create new records
					createXRefCoBorrower(Arrays.asList(addr), XRefID);
				} 
				else {
					Iterator i = c.iterator();
					ArrayList createList = new ArrayList(); // contains list of OBs
					ArrayList deleteList = new ArrayList(); // contains list of
					// local interfaces

					// identify identify records for delete or udpate first
					while (i.hasNext()) {
						EBLimitXRefCoBorrowerLocal local = (EBLimitXRefCoBorrowerLocal) i.next();
						long id = local.getId();
						boolean update = false;

						for (int j = 0; j < addr.length; j++) {
							ILimitXRefCoBorrower newOB = addr[j];

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
						ILimitXRefCoBorrower newOB = addr[j];
						boolean found = false;

						while (i.hasNext()) {
							EBLimitXRefCoBorrowerLocal local = (EBLimitXRefCoBorrowerLocal) i.next();
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
					deleteXRefCoBorrower(deleteList);
					createXRefCoBorrower(createList, XRefID);
				}
			} 
			catch (CustomerException e) {
				throw e;
			} 
			catch (Exception e) {
				e.printStackTrace();
				throw new CustomerException("Caught Exception: " + e.toString());
			}
		}
		
		private void createXRefCoBorrower(List createList, long XRefID) throws CustomerException {
			if ((null == createList) || (createList.size() == 0)) {
				return; // do nothing
			}
			Collection c = getCMRXRefCoBorrower();
			Iterator i = createList.iterator();
			try {
				EBLimitXRefCoBorrowerLocalHome home = getEBLocalHomeXRefCoBorrower();
				while (i.hasNext()) {
					ILimitXRefCoBorrower ob = (ILimitXRefCoBorrower) i.next();
					if (ob != null) {
						System.out.println("EBCUstomerSysXRefBean    createXRefCoBorrower()  ob.getId());================"+ob.getId());
						DefaultLogger.debug(this, "Creating CoBorrower ID: "	+ ob.getId());
						String serverType = (new BatchResourceFactory()).getAppServerType();
						DefaultLogger.debug(this,"=======Application server Type is(Co Borrower) ======= : "+ serverType);
						if (serverType.equals(ICMSConstant.WEBSPHERE)) {
							ob.setXRefID(XRefID);
						}
						EBLimitXRefCoBorrowerLocal local = home.create(ob);
						c.add(local);
					}
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
				if (e instanceof CustomerException) {
					throw (CustomerException) e;
				} 
				else {
					throw new CustomerException("Caught Exception: " + e.toString());
				}
			}
		}
		private void deleteXRefCoBorrower(List deleteList) throws CustomerException {
			if ((null == deleteList) || (deleteList.size() == 0)) {
				return; // do nothing
			}
			try {
				Collection c = getCMRXRefCoBorrower();
				Iterator i = deleteList.iterator();
				while (i.hasNext()) {
					EBLimitXRefCoBorrowerLocal local = (EBLimitXRefCoBorrowerLocal) i.next();
					c.remove(local);
					local.remove();
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (e instanceof CustomerException) {
					throw (CustomerException) e;
				} else {
					throw new CustomerException("Caught Exception: " + e.toString());
				}
			}
		}
		
		protected EBLimitXRefCoBorrowerLocalHome getEBLocalHomeXRefCoBorrower() throws CustomerException {
			EBLimitXRefCoBorrowerLocalHome home = (EBLimitXRefCoBorrowerLocalHome) BeanController	.getEJBLocalHome(ICMSJNDIConstant.EB_XREF_COBORROWER_LOCAL_JNDI, EBLimitXRefCoBorrowerLocalHome.class.getName());
			if (null != home) {
				return home;
			} else {
				throw new CustomerException("EBLimitXRefCoBorrowerLocalHome is null!");
			}
		}
		
		
		
		
}