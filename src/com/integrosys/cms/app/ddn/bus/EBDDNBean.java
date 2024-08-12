/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/ddn/bus/EBDDNBean.java,v 1.8 2005/08/29 08:12:56 whuang Exp $
 */
package com.integrosys.cms.app.ddn.bus;

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
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Implementation for the ddn entity bean
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/08/29 08:12:56 $ Tag: $Name: $
 */

public abstract class EBDDNBean implements EntityBean, IDDN {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getDDNID", "getDDNRef" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getDDNID", "getDDNRef" };

	private static final String DDN_REF_FORMATTER_CLASS = "com.integrosys.cms.app.ddn.bus.DDNRefSequenceFormatter";

	private static final String DDN_REF_SEQUENCE_CLASS = "com.integrosys.base.techinfra.dbsupport.OracleSequencer";

	/**
	 * Default Constructor
	 */
	public EBDDNBean() {
	}

	public abstract Long getCMPDDNID();

	public abstract Long getCMPLimitProfileID();

	public abstract String getIsSCCIssuedIndStr();

	public abstract String getCreditOfficerCountry();

	public abstract String getCreditOfficerOrgCode();

	public abstract String getSeniorOfficerCountry();

	public abstract String getSeniorOfficerOrgCode();

	public abstract void setBcaApprovalDate(Date approvedDate);

	public abstract Date getBcaApprovalDate();

	public abstract void setBcaApprovalAuthority(String approvalAuthority);

	public abstract String getBcaApprovalAuthority();

	public abstract void setBcaNextReviewDate(Date nextReviewDate);

	public abstract Date getBcaNextReviewDate();

	public abstract void setBcaExtReviewDate(Date extReviewDate);

	public abstract Date getBcaExtReviewDate();

	public abstract void setFamCode(String famCode);

	public abstract String getFamCode();

	public abstract void setFamName(String famName);

	public abstract String getFamName();

	public abstract void setBcaOrigCtry(String bcaOrigCtry);

	public abstract String getBcaOrigCtry();

	public abstract void setBcaOrigOrg(String bcaOrigOrg);

	public abstract String getBcaOrigOrg();

	public abstract void setLegalName(String legalName);

	public abstract String getLegalName();

	public abstract void setCustomerName(String customerName);

	public abstract String getCustomerName();

	public abstract void setCustSegmentCode(String custSegmentCode);

	public abstract String getCustSegmentCode();

	public abstract void setBFLIssuedDate(Date issuedDate);

	public abstract Date getBFLIssuedDate();

	public abstract void setCreditGradeCode(String creditCode);

	public abstract String getCreditGradeCode();

	public abstract void setSubProfileID(String subProfile);

	public abstract String getSubProfileID();

	public abstract void setLegalID(String legalID);

	public abstract String getLegalID();

    public abstract void setReleaseTo(String releaseTo);

    public abstract String getReleaseTo();

    public abstract void setDDNRef(String dDNRef);

    public abstract String getDDNRef();

    public abstract void setDateGenerated(Date dateGenerated);

    public abstract Date getDateGenerated();

    public abstract void setDeferredToDate(Date deferredToDate);

    public abstract Date getDeferredToDate();

    public abstract void setDaysValid(int daysValid);

    public abstract int getDaysValid();

    public abstract void setExtendedToDate(Date extendedToDate);

    public abstract Date getExtendedToDate();

    public abstract void setApprovalDate(Date approvalDate);

    public abstract Date getApprovalDate();

    public abstract void setApprovalBy(String approvalBy);

    public abstract String getApprovalBy();

    public abstract void setCreditOfficerName(String creditOfficerName);

    public abstract String getCreditOfficerName();

    public abstract void setCreditOfficerSignNo(String creditOfficerSignNo);

    public abstract String getCreditOfficerSignNo();

    public abstract void setSeniorOfficerName(String seniorOfficerName);

    public abstract String getSeniorOfficerName();

    public abstract void setSeniorOfficerSignNo(String seniorOfficerSignNo);

    public abstract String getSeniorOfficerSignNo();

    public abstract void setCreditOfficerDt(Date creditOfficerDt);

    public abstract Date getCreditOfficerDt();

    public abstract void setSeniorOfficerDt(Date seniorOfficerDt);

    public abstract Date getSeniorOfficerDt();

    public abstract void setRemarks(String remarks);

    public abstract String getRemarks();

    public abstract void setVersionTime(long versionTime);

    public abstract long getVersionTime();

    public abstract void setDocumentType(String documentType);

    public abstract String getDocumentType();

	/**
	 * Helper method to get the ddn ID
	 * @return long - the long value of the ddn ID
	 */
	public long getDDNID() {
		if (getCMPDDNID() != null) {
			return getCMPDDNID().longValue();
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

	public boolean getIsSCCIssuedInd() {
		if ((getIsSCCIssuedIndStr() != null) && getIsSCCIssuedIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Get the credit officer booking location
	 * @return IBookingLocation - the credit officer location
	 */
	public IBookingLocation getCreditOfficerLocation() {
		return new OBBookingLocation(getCreditOfficerCountry(), getCreditOfficerOrgCode());
	}

	/**
	 * Get the senior officer location
	 * @return IBookingLocation - the senior officer location
	 */
	public IBookingLocation getSeniorOfficerLocation() {
		return new OBBookingLocation(getSeniorOfficerCountry(), getSeniorOfficerOrgCode());
	}

	/**
	 * Not implemented
	 */
	public IDDNItem[] getCleanDDNItemList() {
		return null;
	}

	/**
	 * Not implemented
	 */
	public IDDNItem[] getNotCleanDDNItemList() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public IDDNItem[] getDDNItemList() {
		return null;
	}

	/**
	 * Get the approval amount for clean limits
	 * @return Amount - the clean approval amount
	 */
	public Amount getCleanApprovalAmount() {
		return null;
	}

	/**
	 * Get the approval amount (limit with security)
	 * @return Amount - the approval amount
	 */
	public Amount getApprovalAmount() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public Amount getTotalApprovalAmount() {
		return null;
	}

	/**
	 * Get the clean activated amount for clean limits
	 * @return Amount - the activated amount for clean limits
	 */
	public Amount getCleanActivatedAmount() {
		return null;
	}

	/**
	 * Get the activated amount (limit with security)
	 * @return Amount - the activated amount
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
	 * Get the clean ddn amount for clean limits
	 * @return Amount - the activated amount for clean limits
	 */
	public Amount getCleanDDNAmount() {
		return null;
	}

	/**
	 * Get the ddn amount (limit with security)
	 * @return Amount - the activated amount
	 */
	public Amount getDDNAmount() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public Amount getTotalDDNAmount() {
		return null;
	}

	// setters
	public abstract void setCMPDDNID(Long aDDNID);

	public abstract void setCMPLimitProfileID(Long aLimitProfileID);

	public abstract void setIsSCCIssuedIndStr(String anIsSCCGeneratedIndStr);

	public abstract void setCreditOfficerCountry(String aCreditOfficerCountry);

	public abstract void setCreditOfficerOrgCode(String aCreditOfficerOrgCode);

	public abstract void setSeniorOfficerCountry(String aSeniorOfficerCountry);

	public abstract void setSeniorOfficerOrgCode(String aSeniorOfficerOrgCode);

	/**
	 * Helper method to set the DDN ID
	 * @param aDDNID - long
	 */
	public void setDDNID(long aDDNID) {
		setCMPDDNID(new Long(aDDNID));
	}

	/**
	 * Helper method to set the limit profile ID
	 * @param aLimitProfileID - long
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		setCMPLimitProfileID(new Long(aLimitProfileID));
	}

	public void setIsSCCIssuedInd(boolean anIsSCCIssuedInd) {
		if (anIsSCCIssuedInd) {
			setIsSCCIssuedIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsSCCIssuedIndStr(ICMSConstant.FALSE_VALUE);
	}

	public void setCreditOfficerLocation(IBookingLocation anIBookingLocation) {
		if (anIBookingLocation != null) {
			setCreditOfficerCountry(anIBookingLocation.getCountryCode());
			setCreditOfficerOrgCode(anIBookingLocation.getOrganisationCode());
		}
	}

	public void setSeniorOfficerLocation(IBookingLocation anIBookingLocation) {
		if (anIBookingLocation != null) {
			setSeniorOfficerCountry(anIBookingLocation.getCountryCode());
			setSeniorOfficerOrgCode(anIBookingLocation.getOrganisationCode());
		}
	}

	/**
	 * Not implemented here
	 */
	public void setDDNItemList(IDDNItem[] anIDDNItemList) {
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

	public void setCleanDDNAmount(Amount aCleanDDNAmount) {
		// do nothing
	}

	public void setDDNAmount(Amount anDDNAmount) {
		// do nothing
	}

	public void setTotalDDNAmount(Amount aTotalDDNAmount) {
		// do nothing
	}

	// ************** CMR methods ***************
	// Getters
	/**
	 * Get all DDN items
	 * 
	 * @return Collection of EBDDNItemLocal objects
	 */
	public abstract Collection getCMRDDNItems();

	// Setters
	/**
	 * Set all DDN items
	 */
	public abstract void setCMRDDNItems(Collection aDDNItemList);

	/**
	 * Return a ddn object
	 * @return IDDN - the object containing the ddn object
	 * @throws DDNException on errors
	 */
	public IDDN getValue() throws DDNException {
		IDDN value = new OBDDN();
		AccessorUtil.copyValue(this, value, null);

		IDDNItem[] itemList = retrieveDDNItems();
		value.setDDNItemList(itemList);
		return value;
	}

	/**
	 * To retrieve the list of ddn items
	 * @return IDDNItem[] - the list of ddn items
	 * @throws DDNException on error
	 */
	private IDDNItem[] retrieveDDNItems() throws DDNException {
		try {
			Collection col = getCMRDDNItems();
			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				ArrayList itemList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBDDNItemLocal local = (EBDDNItemLocal) iter.next();
					if (!local.getIsDeletedInd()) {
						IDDNItem obj = local.getValue();
						itemList.add(obj);
					}
				}
				return (IDDNItem[]) itemList.toArray(new IDDNItem[0]);
			}
		}
		catch (Exception ex) {
			throw new DDNException("Exception at retrieveDDNItems: " + ex.toString());
		}
	}

	/**
	 * Set the DDN object.
	 * @param anIDDN of IDDN type
	 * @throws DDNException on error
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IDDN anIDDN) throws DDNException, ConcurrentUpdateException {
		try {
			if (getVersionTime() != anIDDN.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(anIDDN, this, EXCLUDE_METHOD_UPDATE);
			updateDDNItems(anIDDN.getDDNItemList());
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new DDNException("Exception in setValue: " + ex.toString());
		}
	}

	/**
	 * Create the child items that are under this DDN
	 * @param anIDDN of IDDN type
	 * @throws DDNException
	 */
	public void createDDNItems(IDDN anIDDN) throws DDNException {
		updateDDNItems(anIDDN.getDDNItemList());
	}

	private void updateDDNItems(IDDNItem[] anIDDNItemList) throws DDNException {
		try {
			Collection col = getCMRDDNItems();
			if (anIDDNItemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteDDNItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) {
				// create new records
				createDDNItems(Arrays.asList(anIDDNItemList));
			}
			else {
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (iter.hasNext()) {
					EBDDNItemLocal local = (EBDDNItemLocal) iter.next();
					long ddnItemRef = local.getDDNItemRef();
					boolean update = false;

					for (int ii = 0; ii < anIDDNItemList.length; ii++) {
						IDDNItem newOB = anIDDNItemList[ii];

						if (newOB.getDDNItemRef() == ddnItemRef) {
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
				for (int ii = 0; ii < anIDDNItemList.length; ii++) {
					iter = col.iterator();
					IDDNItem newOB = anIDDNItemList[ii];
					boolean found = false;

					while (iter.hasNext()) {
						EBDDNItemLocal local = (EBDDNItemLocal) iter.next();
						long ref = local.getDDNItemRef();

						if (newOB.getDDNItemRef() == ref) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteDDNItems(deleteList);
				createDDNItems(createList);
			}
		}
		catch (DDNException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new DDNException("Exception in updateDDNItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of DDN items under the current ddn
	 * @param aDeletionList - List
	 * @throws DDNException on errors
	 */
	private void deleteDDNItems(List aDeletionList) throws DDNException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection col = getCMRDDNItems();
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBDDNItemLocal local = (EBDDNItemLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new DDNException("Exception in deleteDDNItems: " + ex.toString());
		}
	}

	/**
	 * Create the list of DDN items under the current ddn
	 * @throws DDNException on errors
	 */
	private void createDDNItems(List aCreationList) throws DDNException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRDDNItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBDDNItemLocalHome home = getEBDDNItemLocalHome();
			while (iter.hasNext()) {
				IDDNItem obj = (IDDNItem) iter.next();
				// preCreationProcess(obj);
				EBDDNItemLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new DDNException("Exception in createDDNItems: " + ex.toString());
		}
	}

	/**
	 * Create a DDN.
	 * @param anIDDN of IDDN typef
	 * @return Long - the DDN ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(IDDN anIDDN) throws CreateException {
		if (anIDDN == null) {
			throw new CreateException("anIDDN is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
//            System.out.println("the pk no is" + pk);
			setDDNID(pk);
			if (null == anIDDN.getDDNRef()) {
				String ddnRef = (new SequenceManager()).getSeqNum(
						ICMSConstant.SEQUENCE_DDN_REF, true);
				setDDNRef(ddnRef);
//                  System.out.println("the ddnRef no is" + ddnRef);
			}
			else {
				setDDNRef(anIDDN.getDDNRef());
//                System.out.println("the anIDDN no is" + anIDDN);
			}
			AccessorUtil.copyValue(anIDDN, this, EXCLUDE_METHOD_CREATE);
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
	 * @param anIDDN - IDDN
	 */
	public void ejbPostCreate(IDDN anIDDN) throws CreateException {
	}

	/**
	 * To get the DDNID by limit profile ID
	 * @param aLimitProfileID of long type
	 * @return long - the DDN ID
	 * @throws SearchDAOException
	 */
	public long ejbHomeGetDDNIDbyLimitProfile(long aLimitProfileID) throws SearchDAOException {
		return DDNDAOFactory.getDDNDAO().getDDNIDbyLimitProfile(aLimitProfileID);
	}

	/**
	 * To get the number of sc certificate that satisfy the criteria
	 * @param aCriteria of DDNSearchCriteria type
	 * @return int - the number of sc certificate that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetNoOfDDN(DDNSearchCriteria aCriteria) throws SearchDAOException {
		return DDNDAOFactory.getDDNDAO().getNoOfDDN(aCriteria);
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
		return ICMSConstant.SEQUENCE_DDN;
	}

	/**
	 * Method to get EB Local Home for ddn item
	 */
	protected EBDDNItemLocalHome getEBDDNItemLocalHome() throws DDNException {
		EBDDNItemLocalHome home = (EBDDNItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_DDN_ITEM_LOCAL_JNDI, EBDDNItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new DDNException("EBDDNItemLocalHome is null!");
	}

}