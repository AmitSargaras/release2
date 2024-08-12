/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/bus/EBCCCertificateBean.java,v 1.9 2005/08/26 04:18:11 lyng Exp $
 */
package com.integrosys.cms.app.cccertificate.bus;

//java
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
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.OBCreditGrade;

/**
 * Implementation for the cc certificate entity bean
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/08/26 04:18:11 $ Tag: $Name: $
 */

public abstract class EBCCCertificateBean implements EntityBean, ICCCertificate {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getCCCertID", "getCCCertRef" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getCCCertID", "getCCCertRef" };

	private static final String CCC_REF_FORMATTER_CLASS = "com.integrosys.cms.app.cccertificate.bus.CCCRefSequenceFormatter";

	//private static final String CCC_REF_SEQUENCE_CLASS = "com.integrosys.base.techinfra.dbsupport.OracleSequencer";
    private static final String CCC_REF_SEQUENCE_CLASS = "com.integrosys.base.techinfra.dbsupport.DB2Sequencer";

	/**
	 * Default Constructor
	 */
	public EBCCCertificateBean() {
	}

	public abstract Long getCMPCCCertID();

	public abstract Long getCMPLimitProfileID();

	public abstract Long getCMPSubProfileID();

	public abstract Long getCMPPledgorID();

	public abstract Long getCMPCheckListID();

	public abstract String getCreditOfficerCountry();

	public abstract String getCreditOfficerOrgCode();

	public abstract String getSeniorOfficerCountry();

	public abstract String getSeniorOfficerOrgCode();

	public abstract String getFamCode();

	public abstract String getFamName();

	public abstract Date getBcaApprovalDate();

	public abstract Date getBFLIssuedDate();

	public abstract Date getBcaNextReviewDate();

	public abstract Date getBcaExtendedReviewDate();

	public abstract String getCustSegmentCode();

	public abstract String getLegalName();

	public abstract String getCustomerName();

	public abstract String getBcaOrigCtry();

	public abstract String getBcaOrigOrg();

	public abstract String getBcaApprovalAuthority();

	public abstract String getCreditGradeCode();

    public abstract String getCCCertRef();

    public abstract String getCCCertCategory();

    public abstract Date getDateGenerated();

    public abstract String getCreditOfficerName();

    public abstract String getCreditOfficerSignNo();

    public abstract String getSeniorOfficerName();

    public abstract String getSeniorOfficerSignNo();

    public abstract Date getCreditOfficerDt();

    public abstract Date getSeniorOfficerDt();

    public abstract String getRemarks();

    public abstract long getVersionTime();

	public abstract void setCMPCCCertID(Long aCMPCCCertID);

	public abstract void setCMPLimitProfileID(Long aCMPLimitProfileID);

	public abstract void setCMPSubProfileID(Long aCMPSubProfileID);

	public abstract void setCMPPledgorID(Long aCMPPledgor);

	public abstract void setCMPCheckListID(Long aCMPCheckListID);

	public abstract void setCreditOfficerCountry(String aCreditOfficerCountry);

	public abstract void setCreditOfficerOrgCode(String aCreditOfficerOrgCode);

	public abstract void setSeniorOfficerCountry(String aSeniorOfficerCountry);

	public abstract void setSeniorOfficerOrgCode(String aSeniorOfficerOrgCode);

	public abstract void setFamCode(String famCode);

	public abstract void setFamName(String famName);

	public abstract void setBcaApprovalDate(Date bcaApprovalDate);

	public abstract void setBFLIssuedDate(Date bFLIssuedDate);

	public abstract void setBcaNextReviewDate(Date bcaNextReviewDate);

	public abstract void setBcaExtendedReviewDate(Date bcaExtendedReviewDate);

	public abstract void setCustSegmentCode(String custSegmentCode);

	public abstract void setLegalName(String legalName);

	public abstract void setCustomerName(String customerName);

	public abstract void setBcaOrigCtry(String bcaOrigCtry);

	public abstract void setBcaOrigOrg(String bcaOrigOrg);

	public abstract void setBcaApprovalAuthority(String bcaApprovalAuthority);

	public abstract void setCreditGradeCode(String creditGradeCode);

    public abstract void setCCCertRef(String aCCCertRef);

    public abstract void setCCCertCategory(String aCCCertCategory);

    public abstract void setDateGenerated(Date dateGenerated);

    public abstract void setCreditOfficerName(String creditOfficerName);

    public abstract void setCreditOfficerSignNo(String creditOfficerSignNo);

    public abstract void setSeniorOfficerName(String seniorOfficerName);

    public abstract void setSeniorOfficerSignNo(String seniorOfficerSignNo);

    public abstract void setCreditOfficerDt(Date creditOfficerDt);

    public abstract void setSeniorOfficerDt(Date seniorOfficerDt);

    public abstract void setRemarks(String remarks);

    public abstract void setVersionTime(long versionTime);

	/**
	 * Get certificate customer info.
	 * 
	 * @return ISCCertificateCustomerDetail
	 */
	public ICCCertificateCustomerDetail getCustDetails() {
		ICCCertificateCustomerDetail cust = new OBCCCertificateCustomerDetail();
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

	/**
	 * Get the list of CCCertificate items
	 * @return ICCCertificateItem[] - the list of CCCertificate item
	 */
	public long getCCCertID() {
		if (getCMPCCCertID() != null) {
			return getCMPCCCertID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID() {
		if (getCMPLimitProfileID() != null) {
			return getCMPLimitProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the subprofile ID
	 * @return long - the sub profile ID
	 */
	public long getSubProfileID() {
		if (getCMPSubProfileID() != null) {
			return getCMPSubProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the pledgor ID
	 * @return long - the pledgor ID
	 */
	public long getPledgorID() {
		if (getCMPPledgorID() != null) {
			return getCMPPledgorID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the checklist ID
	 * @return long - the checklist ID
	 */
	public long getCheckListID() {
		if (getCMPCheckListID() != null) {
			return getCMPCheckListID().longValue();
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
	 * Not implemented
	 */
	public long getOwnerID() {
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
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
	 * Not implemented
	 */
	public Amount getTotalActivatedAmount() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public ICCCertificateItem[] getCleanCCCertificateItemList() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public ICCCertificateItem[] getNotCleanCCCertificateItemList() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public ICCCertificateItem[] getCCCertificateItemList() {
		return null;
	}

	/**
	 * Set the CCCertID
	 * @param aCCCertID of long type
	 */
	public void setCCCertID(long aCCCertID) {
		setCMPCCCertID(new Long(aCCCertID));
	}

	public void setLimitProfileID(long aLimitProfileID) {
		setCMPLimitProfileID(new Long(aLimitProfileID));
	}

	public void setSubProfileID(long aSubProfileID) {
		setCMPSubProfileID(new Long(aSubProfileID));
	}

	public void setPledgorID(long aPledgorID) {
		setCMPPledgorID(new Long(aPledgorID));
	}

	public void setCheckListID(long aCheckListID) {
		setCMPCheckListID(new Long(aCheckListID));
	}

	public void setCreditOfficerLocation(IBookingLocation anIBookingLocation) {
		//setCreditOfficerCountry(anIBookingLocation.getCountryCode());
		//setCreditOfficerOrgCode(anIBookingLocation.getOrganisationCode());
        setCreditOfficerCountry("");
        setCreditOfficerOrgCode("");
	}

	public void setSeniorOfficerLocation(IBookingLocation anIBookingLocation) {
		//setSeniorOfficerCountry(anIBookingLocation.getCountryCode());
		//setSeniorOfficerOrgCode(anIBookingLocation.getOrganisationCode());
        setSeniorOfficerCountry("");
        setSeniorOfficerOrgCode("");
	}

	/**
	 * Set ccc customer details.
	 * 
	 * @param custDetails of type ICCCertificateCustomerDetail
	 */
	public void setCustDetails(ICCCertificateCustomerDetail custDetails) {
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
			setCreditGradeCode(custDetails.getCreditGrade() == null ? null : custDetails.getCreditGrade().getCGCode());
		}
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
	 * Not implemented
	 */
	public void setCCCertificateItemList(ICCCertificateItem[] anICCCertificateItemList) {
		// do nothing
	}

	/**
	 * Get all ccc items
	 * 
	 * @return Collection of EBCCCertificateItemLocal objects
	 */
	public abstract Collection getCMRCCCItems();

	// Setters
	/**
	 * Set all ccc items
	 * 
	 * @param aCCCertificateItemList is of type Collection of
	 *        EBCCCertificateItemLocal objects
	 */
	public abstract void setCMRCCCItems(Collection aCCCertificateItemList);

	/**
	 * Retrieve an instance of a cc certificate
	 * @return ICCCertificate - the object encapsulating the cc certificate info
	 * @throws CCCertificateException on errors
	 */
	public ICCCertificate getValue() throws CCCertificateException {
		ICCCertificate value = new OBCCCertificate();
		AccessorUtil.copyValue(this, value, null);

		ICCCertificateItem[] itemList = retrieveCCCItems();
		value.setCCCertificateItemList(itemList);
		return value;
	}

	/**
	 * To retrieve the list of ccc items
	 * @return ICCCertificateItem[] - the list of ccc items
	 * @throws CCCertificateException on error
	 */
	private ICCCertificateItem[] retrieveCCCItems() throws CCCertificateException {
		try {
			Collection col = getCMRCCCItems();
			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				ArrayList itemList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBCCCertificateItemLocal local = (EBCCCertificateItemLocal) iter.next();
					if (!local.getIsDeletedInd()) {
						ICCCertificateItem obj = local.getValue();
						itemList.add(obj);
					}
				}
				DefaultLogger.debug(this, "Number of items: " + itemList.size());
				return (ICCCertificateItem[]) itemList.toArray(new ICCCertificateItem[0]);
			}
		}
		catch (Exception ex) {
			throw new CCCertificateException("Exception at retrieveCCCItems: " + ex.toString());
		}
	}

	/**
	 * Set the cc certificate object
	 * @param anICCCertificate - ICCCertificate
	 * @throws CCCertificateException on errors
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICCCertificate anICCCertificate) throws CCCertificateException, ConcurrentUpdateException {
		try {
			if (getVersionTime() != anICCCertificate.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(anICCCertificate, this, EXCLUDE_METHOD_UPDATE);
			updateCCCertificateItems(anICCCertificate.getCCCertificateItemList());
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CCCertificateException("Exception in setValue: " + ex.toString());
		}
	}

	/**
	 * Create the child items that are under this ccc
	 * @param anICCCertificate of ICCCertificate type
	 * @throws CCCertificateException
	 */
	public void createCCCertificateItems(ICCCertificate anICCCertificate) throws CCCertificateException {
		updateCCCertificateItems(anICCCertificate.getCCCertificateItemList());
	}

	private void updateCCCertificateItems(ICCCertificateItem[] anICCCItemList) throws CCCertificateException {
		try {
			Collection col = getCMRCCCItems();
			if (anICCCItemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteCCCItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) {
				// create new records
				createCCCItems(Arrays.asList(anICCCItemList));
			}
			else {
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (iter.hasNext()) {
					EBCCCertificateItemLocal local = (EBCCCertificateItemLocal) iter.next();
					long cccItemRef = local.getCCCertItemRef();
					boolean update = false;

					for (int ii = 0; ii < anICCCItemList.length; ii++) {
						ICCCertificateItem newOB = anICCCItemList[ii];

						if (newOB.getCCCertItemRef() == cccItemRef) {
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
				for (int ii = 0; ii < anICCCItemList.length; ii++) {
					iter = col.iterator();
					ICCCertificateItem newOB = anICCCItemList[ii];
					boolean found = false;

					while (iter.hasNext()) {
						EBCCCertificateItemLocal local = (EBCCCertificateItemLocal) iter.next();
						long ref = local.getCCCertItemRef();

						if (newOB.getCCCertItemRef() == ref) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteCCCItems(deleteList);
				createCCCItems(createList);
			}
		}
		catch (CCCertificateException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new CCCertificateException("Exception in updateCCCertificateItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of ccc items under the current ccc
	 * @param aDeletionList - List
	 * @throws CCCertificateException on errors
	 */
	private void deleteCCCItems(List aDeletionList) throws CCCertificateException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBCCCertificateItemLocal local = (EBCCCertificateItemLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new CCCertificateException("Exception in deleteCCCItems: " + ex.toString());
		}
	}

	/**
	 * Create the list of ccc items under the current ccc
	 * @param aCreationList - List
	 * @throws CCCertificateException on errors
	 */
	private void createCCCItems(List aCreationList) throws CCCertificateException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRCCCItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBCCCertificateItemLocalHome home = getEBCCCertificateItemLocalHome();
			while (iter.hasNext()) {
				ICCCertificateItem obj = (ICCCertificateItem) iter.next();
				// preCreationProcess(obj);
				EBCCCertificateItemLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new CCCertificateException("Exception in createCCCItems: " + ex.toString());
		}
	}

	/**
	 * Get the cc certificate ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return long - the CCC ID
	 * @throws SearchDAOException on errors
	 */
	public long ejbHomeGetCCCID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException {
		return CCCertificateDAOFactory.getCCCertificateDAO().getCCCID(aCriteria);
	}

	/**
	 * Get the cc certificate trx ID
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return String - the CCC TRX ID
	 * @throws SearchDAOException on errors
	 */
	public String ejbHomeGetCCCTrxID(CCCertificateSearchCriteria aCriteria) throws SearchDAOException {
		return CCCertificateDAOFactory.getCCCertificateDAO().getCCCTrxID(aCriteria);
	}

	/**
	 * Get the list of cc certificate trx based on the limit profile ID
	 * @param aLimitProfileID of long type
	 * @return CCCertificateSearchResult[] - the list of cc certificate trx info
	 * @throws SearchDAOException on errors
	 */
	public CCCertificateSearchResult[] ejbHomeGetNoOfCCCGenerated(long aLimitProfileID) throws SearchDAOException {
		return CCCertificateDAOFactory.getCCCertificateDAO().getNoOfCCCGenerated(aLimitProfileID);
	}

	/**
	 * To get the number of cc certificate that satisfy the criteria
	 * @param aCriteria of CCCertificateSearchCriteria type
	 * @return int - the number of cc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetNoOfCCCertificate(CCCertificateSearchCriteria aCriteria) throws SearchDAOException {
		return CCCertificateDAOFactory.getCCCertificateDAO().getNoOfCCCertificate(aCriteria);
	}

	/**
	 * Create a cc certificate.
	 * @param anICCCertificate - ICCCertificate
	 * @return Long - the cc certificate ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICCCertificate anICCCertificate) throws CreateException {
		if (anICCCertificate == null) {
			throw new CreateException("ICCCertificate is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			DefaultLogger.debug(this, "Creating CC Certificate with ID: " + pk);
			setCCCertID(pk);
			if (null == anICCCertificate.getCCCertRef()) {
				String certRef = (new SequenceManager(CCC_REF_SEQUENCE_CLASS, CCC_REF_FORMATTER_CLASS)).getSeqNum(
						ICMSConstant.SEQUENCE_CCC_REF, true);
				setCCCertRef(certRef);
			}
			else {
				setCCCertRef(anICCCertificate.getCCCertRef());
			}
			AccessorUtil.copyValue(anICCCertificate, this, EXCLUDE_METHOD_CREATE);
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
	 * @param anICCCertificate - ICCCertificate
	 */
	public void ejbPostCreate(ICCCertificate anICCCertificate) throws CreateException {
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
		return ICMSConstant.SEQUENCE_CC_CERTIFICATE;
	}

	/**
	 * Method to get EB Local Home for ccc item
	 */
	protected EBCCCertificateItemLocalHome getEBCCCertificateItemLocalHome() throws CCCertificateException {
		EBCCCertificateItemLocalHome home = (EBCCCertificateItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CCC_ITEM_LOCAL_JNDI, EBCCCertificateItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CCCertificateException("EBCCCertificatetItemLocalHome is null!");
	}

}