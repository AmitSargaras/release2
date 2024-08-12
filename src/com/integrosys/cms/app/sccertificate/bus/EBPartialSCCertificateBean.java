/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/bus/EBPartialSCCertificateBean.java,v 1.6 2006/05/30 10:19:38 czhou Exp $
 */
package com.integrosys.cms.app.sccertificate.bus;

//java
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.OBCreditGrade;

/**
 * Implementation for the partial scc entity bean
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/05/30 10:19:38 $ Tag: $Name: $
 */

public abstract class EBPartialSCCertificateBean implements EntityBean, ISCCertificate {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getSCCertID", "getSCCertRef" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getSCCertID", "getSCCertRef" };

	private static final String PSCC_REF_FORMATTER_CLASS = "com.integrosys.cms.app.sccertificate.bus.PSCCRefSequenceFormatter";

	//private static final String PSCC_REF_SEQUENCE_CLASS = "com.integrosys.base.techinfra.dbsupport.OracleSequencer";
    private static final String PSCC_REF_SEQUENCE_CLASS = "com.integrosys.base.techinfra.dbsupport.DB2Sequencer";

	/**
	 * Default Constructor
	 */
	public EBPartialSCCertificateBean() {
	}

	public abstract Long getCMPSCCertID();

	public abstract Long getCMPLimitProfileID();

	public abstract String getCreditOfficerCountry();

	public abstract String getCreditOfficerOrgCode();

	public abstract String getSeniorOfficerCountry();

	public abstract String getSeniorOfficerOrgCode();

	public abstract String getFamCode();

	public abstract String getFamName();

	public abstract Date getBcaApprovalDate();

	public abstract Date getBcaNextReviewDate();

	public abstract Date getBcaExtendedReviewDate();

	public abstract String getBcaOrigCtry();

	public abstract String getBcaOrigOrg();

	public abstract String getBcaApprovalAuthority();

	public abstract String getLegalName();

	public abstract String getCustomerName();

	public abstract String getCustSegmentCode();

	public abstract Date getBFLIssuedDate();

	public abstract String getCreditGradeCode();

    public abstract String getSCCertRef();

    public abstract Date getDateGenerated();

    public abstract String getCreditOfficerName();

    public abstract String getCreditOfficerSignNo();

    public abstract String getSeniorOfficerName();

    public abstract String getSeniorOfficerSignNo();

    public abstract Date getCreditOfficerDt();

    public abstract Date getSeniorOfficerDt();

    public abstract String getRemarks();

    public abstract long getVersionTime();

    public abstract char getHasCheck1();

    public abstract char getHasCheck2();

    public abstract char getHasCheck3();

    public abstract char getHasCheck4();

    public abstract String getInsuredWith();

    public abstract BigDecimal getInsuredWithAmt();

    public abstract String getAmbm();

    public abstract Date getExpiry();

    public abstract BigDecimal getSinkFundAmt();

    public abstract String getPmForPeriodOf();

    public abstract String getCommencingFrom();

    public abstract BigDecimal getFundReach();

    public abstract BigDecimal getFeesAmt();

    public abstract String getOthers();

	/**
	 * Helper method to get the scc ID
	 * @return long - the long value of the scc ID
	 */
	public long getSCCertID() {
		if (getCMPSCCertID() != null) {
			return getCMPSCCertID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID() {
		if (getCMPLimitProfileID() != null) {
			return getCMPLimitProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the credit officer booking location
	 * @return IBookingLocation - the credit officer location
	 */
	public IBookingLocation getCreditOfficerLocation() {
        if (getCreditOfficerCountry() == null || getCreditOfficerOrgCode() == null) {
            return null;
        } else {
            return new OBBookingLocation(getCreditOfficerCountry(), getCreditOfficerOrgCode());   
        }
	}

	/**
	 * Get the senior officer location
	 * @return IBookingLocation - the senior officer location
	 */
	public IBookingLocation getSeniorOfficerLocation() {
        if (getSeniorOfficerCountry() == null || getSeniorOfficerOrgCode() == null) {
            return null;
        } else {
            return new OBBookingLocation(getSeniorOfficerCountry(), getSeniorOfficerOrgCode());   
        }
	}

	/**
	 * Not implemented here
	 */
	public ISCCertificateItem[] getSCCItemList() {
		return null;
	}

	public ISCCertificateItem[] getCleanSCCertificateItemList() {
		return null; // Not Implemented
	}

	public ISCCertificateItem[] getNotCleanSCCertificateItemList() {
		return null; // Not Implemented
	}

	/**
	 * Not implemented here
	 */
	public IPartialSCCertificateItem[] getPartialSCCItemList() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public IPartialSCCertificateItem[] getCleanPSCCItemList() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public IPartialSCCertificateItem[] getNotCleanPSCCItemList() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public Amount getCleanApprovalAmount() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public Amount getApprovalAmount() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public Amount getTotalApprovalAmount() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public Amount getCleanActivatedAmount() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public Amount getActivatedAmount() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public Amount getTotalActivatedAmount() {
		return null;
	}

	/**
	 * Get certificate customer info.
	 * 
	 * @return ISCCertificateCustomerDetail
	 */
	public ISCCertificateCustomerDetail getCustDetails() {
		ISCCertificateCustomerDetail cust = new OBSCCertificateCustomerDetail();
		cust.setFamCode(getFamCode());
		cust.setFamName(getFamName());
		cust.setApprovalDate(getBcaApprovalDate());
		cust.setExtReviewDate(getBcaExtendedReviewDate());
		cust.setNextReviewDate(getBcaNextReviewDate());
		cust.setApprovalAuthority(getBcaApprovalAuthority());
		cust.setLegalName(getLegalName());
		cust.setCustomerName(getCustomerName());
		cust.setFinalBFLIssuedDate(getBFLIssuedDate());
		cust.setCustomerSegmentCode(getCustSegmentCode());
		IBookingLocation bkg = new OBBookingLocation();
		bkg.setCountryCode(getBcaOrigCtry());
		bkg.setOrganisationCode(getBcaOrigOrg());
		cust.setOriginatingLocation(bkg);
		if (getCreditGradeCode() != null) {
			OBCreditGrade cg = new OBCreditGrade();
			cg.setCGCode(getCreditGradeCode());
			cust.setCreditGrade(cg);
		}
		return cust;
	}

	// setters
	public abstract void setCMPSCCertID(Long aSCCertID);

	public abstract void setCMPLimitProfileID(Long aLimitProfileID);

	public abstract void setCreditOfficerCountry(String aCreditOfficerCountry);

	public abstract void setCreditOfficerOrgCode(String aCreditOfficerOrgCode);

	public abstract void setSeniorOfficerCountry(String aSeniorOfficerCountry);

	public abstract void setSeniorOfficerOrgCode(String aSeniorOfficerOrgCode);

	public abstract void setFamCode(String famCode);

	public abstract void setFamName(String famName);

	public abstract void setBcaApprovalDate(Date bcaApprovalDate);

	public abstract void setBcaNextReviewDate(Date bcaNextReviewDate);

	public abstract void setBcaExtendedReviewDate(Date bcaExtendedReviewDate);

	public abstract void setBcaOrigCtry(String bcaOrigCtry);

	public abstract void setBcaOrigOrg(String bcaOrigOrg);

	public abstract void setBcaApprovalAuthority(String bcaApprovalAuthority);

	public abstract void setLegalName(String legalName);

	public abstract void setCustomerName(String customerName);

	public abstract void setCustSegmentCode(String custSegmentCode);

	public abstract void setBFLIssuedDate(Date bFLIssuedDate);

	public abstract void setCreditGradeCode(String creditGradeCode);

    public abstract void setSCCertRef(String aSCCertRef);

    public abstract void setDateGenerated(Date dateGenerated);

    public abstract void setCreditOfficerName(String creditOfficerName);

    public abstract void setCreditOfficerSignNo(String creditOfficerSignNo);

    public abstract void setSeniorOfficerName(String seniorOfficerName);

    public abstract void setSeniorOfficerSignNo(String seniorOfficerSignNo);

    public abstract void setCreditOfficerDt(Date creditOfficerDt);

    public abstract void setSeniorOfficerDt(Date seniorOfficerDt);

    public abstract void setRemarks(String remarks);

    public abstract void setVersionTime(long versionTime);

    public abstract void setHasCheck1(char hasCheck1);

    public abstract void setHasCheck2(char hasCheck2);

    public abstract void setHasCheck3(char hasCheck3);

    public abstract void setHasCheck4(char hasCheck4);

    public abstract void setInsuredWith(String insuredWith);

    public abstract void setInsuredWithAmt(BigDecimal insuredWithAmt);

    public abstract void setAmbm(String ambm);

    public abstract void setExpiry(Date expiry);

    public abstract void setSinkFundAmt(BigDecimal sinkFundAmt);

    public abstract void setPmForPeriodOf(String pmForPeriodOf);

    public abstract void setCommencingFrom(String commencingFrom);

    public abstract void setFundReach(BigDecimal fundReach);

    public abstract void setFeesAmt(BigDecimal feesAmt);

    public abstract void setOthers(String others);

	/**
	 * Helper method to set the scc ID
	 * @param aSCCertID - long
	 */
	public void setSCCertID(long aSCCertID) {
		setCMPSCCertID(new Long(aSCCertID));
	}

	/**
	 * Helper method to set the limit profile ID
	 * @param aLimitProfileID - long
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		setCMPLimitProfileID(new Long(aLimitProfileID));
	}

	public void setCreditOfficerLocation(IBookingLocation anIBookingLocation) {
		//setCreditOfficerCountry(anIBookingLocation.getCountryCode() == null ? "" : anIBookingLocation.getCountryCode());
		//setCreditOfficerOrgCode(anIBookingLocation.getOrganisationCode() == null ? "" : anIBookingLocation.getOrganisationCode());
        setCreditOfficerCountry("");
        setCreditOfficerOrgCode("");
	}

	public void setSeniorOfficerLocation(IBookingLocation anIBookingLocation) {
		//setSeniorOfficerCountry(anIBookingLocation.getCountryCode() == null ? "" : anIBookingLocation.getCountryCode());
		//setSeniorOfficerOrgCode(anIBookingLocation.getOrganisationCode() == null ? "" : anIBookingLocation.getOrganisationCode());
        setSeniorOfficerCountry("");
        setSeniorOfficerOrgCode("");
	}

	/**
	 * Not implemented here
	 */
	public void setSCCItemList(ISCCertificateItem[] anISCCertificateItemList) {
		// do nothing
	}

	public void setCleanApprovalAmount(Amount aCleanApprovalAmount) {
		// do nothing
	}

	public void setApprovalAmount(Amount anApprovalAmount) {
		// do nothing
	}

	public void setTotalApprovalAmount(Amount aTotalApprovalAmount) {
		// do nothing
	}

	public void setCleanActivatedAmount(Amount aCleanActivatedAmount) {
		// do nothing
	}

	public void setActivatedAmount(Amount anActivatedAmount) {
		// do nothing
	}

	public void setTotalActivatedAmount(Amount aTotalActivatedAmount) {
		// do nothing
	}

	/**
	 * Set certificate customer info.
	 * 
	 * @param custDetails of type ISCCertificateCustomerDetail
	 */
	public void setCustDetails(ISCCertificateCustomerDetail custDetails) {
		if (custDetails != null) {
			setFamCode(custDetails.getFamCode());
			setFamName(custDetails.getFamName());
			setBcaApprovalDate(custDetails.getApprovalDate());
			setBcaNextReviewDate(custDetails.getNextReviewDate());
			setBcaExtendedReviewDate(custDetails.getExtReviewDate());
			setBcaApprovalAuthority(custDetails.getApprovalAuthority());
			setCustSegmentCode(custDetails.getCustomerSegmentCode());
			setBFLIssuedDate(custDetails.getFinalBFLIssuedDate());
			setLegalName(custDetails.getLegalName());
			setCustomerName(custDetails.getCustomerName());
			setBcaOrigCtry(custDetails.getOriginatingLocation() == null ? null : custDetails.getOriginatingLocation()
					.getCountryCode());
			setBcaOrigOrg(custDetails.getOriginatingLocation() == null ? null : custDetails.getOriginatingLocation()
					.getOrganisationCode());
		}
	}

	// ************** CMR methods ***************
	// Getters
	/**
	 * Get all scc items
	 * 
	 * @return Collection of EBPartialSCCertificateItemLocal objects
	 */
	public abstract Collection getCMRPartialSCCItems();

	// Setters
	/**
	 * Set all scc items
	 * 
	 * @param aParitalSCCertificateItemList is of type Collection of
	 *        EBPartialSCCertificateItemLocal objects
	 */
	public abstract void setCMRPartialSCCItems(Collection aParitalSCCertificateItemList);

	/**
	 * Return a scc object
	 * @return IPartialSCCertificate - the object containing the partial scc
	 *         object
	 * @throws SCCertificateException on errors
	 */
	public IPartialSCCertificate getValue() throws SCCertificateException {
		IPartialSCCertificate value = new OBPartialSCCertificate();
		AccessorUtil.copyValue(this, value, null);

		IPartialSCCertificateItem[] itemList = retrievePartialSCCItems();
		value.setSCCItemList(itemList);
		return value;
	}

	/**
	 * To retrieve the list of scc items
	 * @return IPartialSCCertificateItem[] - the list of partial scc items
	 * @throws SCCertificateException on error
	 */
	private IPartialSCCertificateItem[] retrievePartialSCCItems() throws SCCertificateException {
		try {
			Collection col = getCMRPartialSCCItems();
			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				ArrayList itemList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBPartialSCCertificateItemLocal local = (EBPartialSCCertificateItemLocal) iter.next();
					if (!local.getIsDeletedInd()) {
						IPartialSCCertificateItem obj = local.getValue();
						itemList.add(obj);
					}
				}
				return (IPartialSCCertificateItem[]) itemList.toArray(new IPartialSCCertificateItem[0]);
			}
		}
		catch (Exception ex) {
			throw new SCCertificateException("Exception at retrievePartialSCCItems: " + ex.toString());
		}
	}

	/**
	 * Set the partial scc object.
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @throws SCCertificateException on error
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IPartialSCCertificate anIPartialSCCertificate) throws SCCertificateException,
			ConcurrentUpdateException {
		try {
			if (getVersionTime() != anIPartialSCCertificate.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(anIPartialSCCertificate, this, EXCLUDE_METHOD_UPDATE);
			updatePartialSCCertificateItems(anIPartialSCCertificate.getPartialSCCItemList());
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new SCCertificateException("Exception in setValue: " + ex.toString());
		}
	}

	/**
	 * Create the child items that are under this partial scc
	 * @param anIPartialSCCertificate of IPartialSCCertificate type
	 * @throws SCCertificateException
	 */
	public void createPartialSCCertificateItems(IPartialSCCertificate anIPartialSCCertificate)
			throws SCCertificateException {
		updatePartialSCCertificateItems(anIPartialSCCertificate.getPartialSCCItemList());
	}

	private void updatePartialSCCertificateItems(IPartialSCCertificateItem[] anIPartialSCCItemList)
			throws SCCertificateException {
		try {
			Collection col = getCMRPartialSCCItems();
			if (anIPartialSCCItemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deletePartialSCCItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) {
				// create new records
				createPartialSCCItems(Arrays.asList(anIPartialSCCItemList));
			}
			else {
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (iter.hasNext()) {
					EBPartialSCCertificateItemLocal local = (EBPartialSCCertificateItemLocal) iter.next();
					long sccItemRef = local.getSCCertItemRef();
					boolean update = false;

					for (int ii = 0; ii < anIPartialSCCItemList.length; ii++) {
						IPartialSCCertificateItem newOB = anIPartialSCCItemList[ii];

						if (newOB.getSCCertItemRef() == sccItemRef) {
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
				for (int ii = 0; ii < anIPartialSCCItemList.length; ii++) {
					iter = col.iterator();
					IPartialSCCertificateItem newOB = anIPartialSCCItemList[ii];
					boolean found = false;

					while (iter.hasNext()) {
						EBPartialSCCertificateItemLocal local = (EBPartialSCCertificateItemLocal) iter.next();
						long ref = local.getSCCertItemRef();

						if (newOB.getSCCertItemRef() == ref) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deletePartialSCCItems(deleteList);
				createPartialSCCItems(createList);
			}
		}
		catch (SCCertificateException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new SCCertificateException("Exception in updateSCCertificateItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of partial scc items under the current scc
	 * @param aDeletionList - List
	 * @throws SCCertificateException on errors
	 */
	private void deletePartialSCCItems(List aDeletionList) throws SCCertificateException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			// Collection col = getCMRPartialSCCItems();
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBPartialSCCertificateItemLocal local = (EBPartialSCCertificateItemLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new SCCertificateException("Exception in deleteSCCItems: " + ex.toString());
		}
	}

	/**
	 * Create the list of partial scc items under the current scc
	 * @param aCreationList - List
	 * @throws SCCertificateException on errors
	 */
	private void createPartialSCCItems(List aCreationList) throws SCCertificateException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRPartialSCCItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBPartialSCCertificateItemLocalHome home = getEBPartialSCCertificateItemLocalHome();
			while (iter.hasNext()) {
				IPartialSCCertificateItem obj = (IPartialSCCertificateItem) iter.next();
				// preCreationProcess(obj);
				EBPartialSCCertificateItemLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new SCCertificateException("Exception in createSCCItems: " + ex.toString());
		}
	}

	/**
	 * Create a scc.
	 * @param anIPartialSCCertificate of ISCCertificate typef
	 * @return Long - the scc ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(IPartialSCCertificate anIPartialSCCertificate) throws CreateException {
		if (anIPartialSCCertificate == null) {
			throw new CreateException("anIPartialSCCertificate is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setSCCertID(pk);
			if (null == anIPartialSCCertificate.getSCCertRef()) {
				String certRef = (new SequenceManager(PSCC_REF_SEQUENCE_CLASS, PSCC_REF_FORMATTER_CLASS)).getSeqNum(
						ICMSConstant.SEQUENCE_PSCC_REF, true);
				setSCCertRef(certRef);
			}
			else {
				setSCCertRef(anIPartialSCCertificate.getSCCertRef());
			}
			AccessorUtil.copyValue(anIPartialSCCertificate, this, EXCLUDE_METHOD_CREATE);
			setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception in ejbCreate : " + ex.toString());
		}
	}

	/**
	 * EJB Post Create Method
	 * @param anIPartialSCCertificate - IPartialSCCertificate
	 */
	public void ejbPostCreate(IPartialSCCertificate anIPartialSCCertificate) throws CreateException {
	}

	/**
	 * To get the PSCCID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return long - the PSCC ID
	 * @throws SearchDAOException
	 */
	public long ejbHomeGetPSCCIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException {
		return SCCertificateDAOFactory.getPartialSCCertificateDAO().getPSCCIDbyLimitProfile(aLimitProfileID);
	}

	/**
	 * To get the number of sc certificate that satisfy the criteria
	 * @param aCriteria of SCCertificateSearchCriteria type
	 * @return int - the number of sc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetNoOfPartialSCCertificate(SCCertificateSearchCriteria aCriteria) throws SearchDAOException {
		return SCCertificateDAOFactory.getPartialSCCertificateDAO().getNoOfPartialSCCertificate(aCriteria);
	}

	/**
	 * To get the PSCC Trx ID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return String - the SCC Trx ID
	 * @throws SearchDAOException
	 */
	public String ejbHomeGetPSCCTrxIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException {
		return SCCertificateDAOFactory.getPartialSCCertificateDAO().getPSCCTrxIDbyLimitProfile(aLimitProfileID);
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

	/**
	 * A container invokes this method when the instance is taken out of the
	 * pool of available instances to become associated with a specific EJB
	 * object. This method transitions the instance to the ready state. This
	 * method executes in an unspecified transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * A container invokes this method on an instance before the instance
	 * becomes disassociated with a specific EJB object. After this method
	 * completes, the container will place the instance into the pool of
	 * available instances. This method executes in an unspecified transaction
	 * context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by loading it from the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbLoad() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by storing it to the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbStore() {
	}

	/**
	 * A container invokes this method before it removes the EJB object that is
	 * currently associated with the instance. It is invoked when a client
	 * invokes a remove operation on the enterprise Bean's home or remote
	 * interface. It transitions the instance from the ready state to the pool
	 * of available instances. It is called in the transaction context of the
	 * remove operation.
	 */
	public void ejbRemove() throws RemoveException {
	}

	/**
	 * Get the name of the sequence to be used for key generation
	 * @return String - the sequence to be used
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_PARTIAL_SC_CERTIFICATE;
	}

	/**
	 * Method to get EB Local Home for scc item
	 */
	protected EBPartialSCCertificateItemLocalHome getEBPartialSCCertificateItemLocalHome()
			throws SCCertificateException {
		EBPartialSCCertificateItemLocalHome home = (EBPartialSCCertificateItemLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_PSCC_ITEM_LOCAL_JNDI, EBPartialSCCertificateItemLocalHome.class
						.getName());
		if (home != null) {
			return home;
		}
		throw new SCCertificateException("EBPartialSCCertificatetItemLocalHome is null!");
	}

}