/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSLegalEntityBean.java,v 1.15 2005/01/07 09:00:13 pooja Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import org.springframework.util.CollectionUtils;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.batch.common.BatchResourceFactory;

/**
 * This entity bean represents the persistence for Legal Entity details
 * 
 * @author $Author: pooja $
 * @version $Revision: 1.15 $
 * @since $Date: 2005/01/07 09:00:13 $ Tag: $Name: $
 */
public abstract class EBCMSLegalEntityBean implements EntityBean,
		ICMSLegalEntity {
	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_LEGAL_ENTITY;

	private static final String[] EXCLUDE_METHOD = new String[] { "getLEID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBCMSLegalEntityBean() {
	}

	// ************ Non-persistence method *************
	// Getters
	/**
	 * Get the legal ID
	 * 
	 * @return long
	 */
	public long getLEID() {
		if (null != getLEPK()) {
			return getLEPK().longValue();
		} else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Get the top 1000 enterprise indicator.
	 * 
	 * @return boolean
	 */
	public boolean getTop1000Ind() {
		String value = getTop1000Str();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get the black listed status.
	 * 
	 * @return boolean
	 */
	public boolean getBlackListedInd() {
		String value = getBlackListedStr();
		if ((null != value) && value.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Get Legal Registered Address Not implemented. see
	 * <code>retrieveRegisteredAddress</code>
	 * 
	 * @return IContact[]
	 */
	public IContact[] getRegisteredAddress() {
		return null;
	}

	public ISystem[] getOtherSystem() {
		return null;
	}

	public IVendor[] getVendor() {
		return null;
	}
	
	public IDirector[] getDirector() {
		return null;
	}

	public ISubline[] getSublineParty() {
		return null;
	}

	public IBankingMethod[] getBankList() {
		return null;
	}

	/**
	 * Get a list of credit grades Not implemented. see
	 * <code>retrieveCreditGrades</code>
	 * 
	 * @return ICreditGrade[]
	 */
	public ICreditGrade[] getCreditGrades() {
		return null;
	}

	/**
	 * Get ISIC Codes Not implemented. see <code>retrieveISICCode</code>
	 * 
	 * @return IISICCode[]
	 */
	public IISICCode[] getISICCode() {
		return null;
	}

	/**
	 * Get Credit Status Not implemented. see <code>retrieveCreditStatus</code>
	 * 
	 * @return ICreditStatus[]
	 */
	public ICreditStatus[] getCreditStatus() {
		return null;
	}

	/**
	 * Get KYC Records Not implemented. see <code>retrieveKYCRecords</code>
	 * 
	 * @return IKYC[]
	 */
	public IKYC[] getKYCRecords() {
		return null;
	}

	public boolean isNonBorrower() {
		return false;
	}

	// Setters
	/**
	 * Set the LEID
	 * 
	 * @param value
	 *            is of type long
	 */
	public void setLEID(long value) {
		setLEPK(new Long(value));
	}

	/**
	 * Set the top 1000 enterprise indicator.
	 * 
	 * @param value
	 *            is of type boolean
	 */
	public void setTop1000Ind(boolean value) {
		if (true == value) {
			setTop1000Str(ICMSConstant.TRUE_VALUE);
		} else {
			setTop1000Str(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set the black listed status.
	 * 
	 * @param value
	 *            is of type boolean
	 */
	public void setBlackListedInd(boolean value) {
		if (true == value) {
			setBlackListedStr(ICMSConstant.TRUE_VALUE);
		} else {
			setBlackListedStr(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * Set Legal Registered Address Not implemented. see
	 * <code>updateRegisteredAddress</code>
	 * 
	 * @param value
	 *            is of type IContact[]
	 */
	public void setRegisteredAddress(IContact[] value) {
		// do nothing
	}

	public void setOtherSystem(ISystem[] value) {
		// do nothing
	}

	public void setDirector(IDirector[] value) {
		// do nothing
	}

	public void setVendor(IVendor[] value) {
		// do nothing
	}
	
	public void setSublineParty(ISubline[] value) {
		// do nothing
	}

	public void setBankList(IBankingMethod[] value) {
		// do nothing
	}

	/**
	 * Set a list of credit grades Not implemented. see
	 * <code>updateCreditGrades</code>
	 * 
	 * @param value
	 *            is of type ICreditGrade[]
	 */
	public void setCreditGrades(ICreditGrade[] value) {
		// do nothing
	}

	/**
	 * Set ISIC Codes Not implemented. see <code>updateISICCode</code>
	 * 
	 * @param value
	 *            is of type IISICCode[]
	 */
	public void setISICCode(IISICCode[] value) {
		// do nothing
	}

	/**
	 * Set Credit Status Not implemented. see <code>updateCreditStatus</code>
	 * 
	 * @param value
	 *            is of type ICreditStatus[]
	 */
	public void setCreditStatus(ICreditStatus[] value) {
		// do nothing
	}

	/**
	 * Set KYC Records Not implemented. see <code>updateKYCRecords</code>
	 * 
	 * @param value
	 *            is of type IKYC[]
	 */
	public void setKYCRecords(IKYC[] value) {
		// do nothing
	}

	// ********* CMR methods ****************
	// Getters
	/**
	 * Get Legal Registered Address
	 * 
	 * @return Collection of EBContactLocal objects
	 */
	public abstract Collection getCMRRegisteredAddress();

	public abstract Collection getCMROtherSystem();
	
	public abstract Collection getCMRVendorDetails();

	public abstract Collection getCMRDirector();

	public abstract Collection getCMRSubline();

	public abstract Collection getCMRBankingMethod();

	/**
	 * Get a list of credit grades
	 * 
	 * @return Collection of EBCreditGradeLocal objects
	 */
	public abstract Collection getCMRCreditGrades();

	/**
	 * Get ISIC Codes
	 * 
	 * @return Collection of EBISICCodeLocal objects
	 */
	public abstract Collection getCMRISICCode();

	/**
	 * Get Credit Status
	 * 
	 * @return Collection of EBCreditStatusLocal objects
	 */
	public abstract Collection getCMRCreditStatus();

	/**
	 * Get KYC Records
	 * 
	 * @return Collection of EBKYCLocal objects
	 */
	public abstract Collection getCMRKYCRecords();

	// Seters
	/**
	 * Set Legal Registered Address
	 * 
	 * @param value
	 *            is of type Collection of EBContactLocal objects
	 */
	public abstract void setCMRRegisteredAddress(Collection value);

	public abstract void setCMROtherSystem(Collection value);
	
	public abstract void setCMRVendorDetails(Collection value);
	
	public abstract void setCMRDirector(Collection value);

	public abstract void setCMRSubline(Collection value);

	public abstract void setCMRBankingMethod(Collection value);

	/**
	 * Set a list of credit grades
	 * 
	 * @param value
	 *            is a Collection of EBCreditGradeLocal objects
	 */
	public abstract void setCMRCreditGrades(Collection value);

	/**
	 * Set ISIC Codes
	 * 
	 * @param value
	 *            is of type Collection of EBISICCodeLocal objects
	 */
	public abstract void setCMRISICCode(Collection value);

	/**
	 * Set Credit Status
	 * 
	 * @param value
	 *            is of type Collection of EBCreditStatusLocal objects
	 */
	public abstract void setCMRCreditStatus(Collection value);

	/**
	 * Set KYC Records
	 * 
	 * @param value
	 *            is a Collection of EBKYCLocal objects
	 */
	public abstract void setCMRKYCRecords(Collection value);

	// ********** Abstract Methods *************
	// Getters
	/**
	 * Get the LE PK
	 * 
	 * @return Long
	 */
	public abstract Long getLEPK();

	/**
	 * Get the top 1000 enterprise indicator.
	 * 
	 * @return String
	 */
	public abstract String getTop1000Str();

	/**
	 * Get the black listed status.
	 * 
	 * @return String
	 */
	public abstract String getBlackListedStr();

	// Setters
	/**
	 * Set the LE PK
	 * 
	 * @param value
	 *            is of type Long
	 */
	public abstract void setLEPK(Long value);

	/**
	 * Set the top 1000 enterprise indicator.
	 * 
	 * @param value
	 *            is of type String
	 */
	public abstract void setTop1000Str(String value);

	/**
	 * Set the black listed status.
	 * 
	 * @param value
	 *            is of type String
	 */
	public abstract void setBlackListedStr(String Value);

	public abstract String getBusinessGroup();

	public abstract void setBusinessGroup(String value);

	public abstract Double getTFAAmount();

	public abstract void setTFAAmount(Double value);

	public abstract String getIncomeRange();

	public abstract void setIncomeRange(String value);

	public abstract String getBusinessSector();

	public abstract void setBusinessSector(String value);

	public abstract String getIDTypeCode();

	public abstract void setIDTypeCode(String value);

	public abstract String getIDType();

	public abstract void setIDType(String value);

	// public abstract String getNewIDNumber();
	// public abstract void setNewIDNumber(String value);

	public abstract String getCountryPR();

	public abstract void setCountryPR(String value);

	public abstract String getLanguagePrefer();

	public abstract void setLanguagePrefer(String value);

	public abstract String getLegalConstitution();

	public abstract void setLegalConstitution(String value);

	public abstract String getCustomerType();

	public abstract void setCustomerType(String value);

	public abstract String getLegalName();

	public abstract void setLegalName(String value);

	public abstract String getShortName();

	public abstract void setShortName(String value);

	public abstract String getLegalRegCountry();

	public abstract void setLegalRegCountry(String value);

	public abstract String getLegalRegNumber();

	public abstract void setLegalRegNumber(String value);

	public abstract Date getRelationshipStartDate();

	public abstract void setRelationshipStartDate(Date value);

	public abstract String getLegalIDSourceCode();

	public abstract void setLegalIDSourceCode(String value);

	public abstract String getLegalIDSource();

	public abstract void setLegalIDSource(String value);

	public abstract long getVersionTime();

	// ************************ ejbCreate methods ********************

	/**
	 * Create a Customer
	 * 
	 * @param value
	 *            is the ICMSLegalEntity object
	 * @return Long the primary key
	 */
	public Long ejbCreate(ICMSLegalEntity value) throws CreateException {
		if (null == value) {
			throw new CreateException("ICMSLegalEntity is null!");
		}
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(
					SEQUENCE_NAME, true));
			/*
			 * long pk =com.integrosys.cms.app.common.constant.ICMSConstant.
			 * LONG_INVALID_VALUE; if(value.getLEID() ==
			 * com.integrosys.cms.app.common
			 * .constant.ICMSConstant.LONG_INVALID_VALUE) { pk =
			 * Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME,
			 * true)); } else { pk = value.getLEID(); }
			 */
			DefaultLogger.debug(this, "Creating Customer with ID: " + pk);

			setLEID(pk);

			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);

			setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CreateException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * Create a Customer
	 * 
	 * @param value
	 *            is the ICMSLegalEntity object
	 */
	public void ejbPostCreate(ICMSLegalEntity value) throws CreateException {
		// do nothing
	}

	/**
	 * Method to get an object representation from persistance
	 * 
	 * @return ICMSLegalEntity
	 * @throws CustomerException
	 *             on error
	 */
	public ICMSLegalEntity getValue() throws CustomerException {
		try {
			OBCMSLegalEntity value = new OBCMSLegalEntity();
			AccessorUtil.copyValue(this, value);

			value.setRegisteredAddress(retrieveRegisteredAddress());
			value.setOtherSystem(retrieveOtherSystem());
			value.setVendor(retrieveVendorDtls());
			value.setCoBorrowerDetails(getCoBorrowerDetails());
			value.setDirector(retrieveDirector());
			value.setSublineParty(retrieveSublineParty());
			value.setBankList(retrieveBankingMethod());
			value.setCreditGrades(retrieveCreditGrades());
			value.setISICCode(retrieveISICCode());
			value.setCriList(retrieveCriInfo());  //Shiv 220811
			value.setCriFacList(retrieveCriFac());  //Shiv 030811
			// value.setCreditStatus(retrieveCreditStatus());
			// value.setKYCRecords(retrieveKYCRecords());
			value.setUdfData(retrieveUdfInfo());
//			value.setUdfMtdList(retrieveUdfMtd());
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CustomerException) {
				throw (CustomerException) e;
			} else {
				throw new CustomerException("Caught Exception: " + e.toString());
			}
		}
	}

	/**
	 * Method to set an object representation into persistance
	 * 
	 * @param value
	 *            is of type ICMSLegalEntity
	 * @throws ConcurrentUpdateException
	 *             if version mismatch occurs
	 * @throws CustomerException
	 *             on error
	 */
	public void setValue(ICMSLegalEntity value) throws CustomerException,
			ConcurrentUpdateException {
		long beanVer = value.getVersionTime();
		long currentVer = getVersionTime();
		if (beanVer != currentVer) {
			throw new ConcurrentUpdateException("Version mismatch!");
		}
		try {
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			updateDependants(value);
			setVersionTime(VersionGenerator.getVersionNumber());
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof CustomerException) {
				throw (CustomerException) e;
			} else {
				throw new CustomerException("Caught Exception: " + e.toString());
			}
		}
	}

	/**
	 * Method to create child dependants via CMR
	 * 
	 * @param value
	 *            is of type ICMSLegalEntity
	 * @param verTime
	 *            is the version time to be compared against the beans' version
	 * @throws CustomerException
	 *             , ConcurrentUpdateException on error
	 */
	public void createDependants(ICMSLegalEntity value, long verTime)
			throws CustomerException, ConcurrentUpdateException {
		if (verTime != getVersionTime()) {
			throw new ConcurrentUpdateException("Version mismatched!");
		} else {
			updateDependants(value);
		}
	}

	// ************************** Private methods ***************************
	/**
	 * Method to update child dependants
	 */
	private void updateDependants(ICMSLegalEntity value)
			throws CustomerException {
		updateRegisteredAddress(value.getRegisteredAddress(), value.getLEID());
		updateOtherSystem(value.getOtherSystem(), value.getLEID());
		updateDirector(value.getDirector(), value.getLEID());
		updateVendorDtls(value.getVendor(),value.getLEID());
		updateCoBorrowerDetails(value.getCoBorrowerDetails(),value.getLEID());
		updateSublineParty(value.getSublineParty(), value.getLEID());
		updateBankingMethod(value.getBankList(), value.getLEID());
		updateCreditGrades(value.getCreditGrades());
		updateISICCode(value.getISICCode());
		updateCriInfo(value.getCriList(), value.getLEID());
		updateCriFac(value.getCriFacList(), value.getLEID());
		updateUdfInfo(value.getUdfData(), value.getLEID());
		//updateUdfMtd(value.getUdfMtdList(),value.getLEID());
		// updateCreditStatus(value.getCreditStatus());
		// updateKYCRecords(value.getKYCRecords());
	}
	private void updateUdfMtd(ICMSCustomerUdf[] addr, long LEID)	throws CustomerException {
		try {
			Collection c = getCMRUdfInfo();
			if (null == addr) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} 
				else {
					// delete all records
					deleteUdfMtd(new ArrayList(c));
				}
			} 
			else if ((null == c) || (c.size() == 0)) {
				// create new records
				createUdfMtd(Arrays.asList(addr), LEID);
			} 
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBCMSCustomerUdfLocal local = (EBCMSCustomerUdfLocal) i.next();
					long id = local.getId();
					boolean update = false;

					for (int j = 0; j < addr.length; j++) {
						ICMSCustomerUdf newOB = addr[j];

						if ((newOB!=null) && (newOB.getId() == id)) {
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
					ICMSCustomerUdf newOB = addr[j];
					boolean found = false;

					while (i.hasNext()) {
						EBCMSCustomerUdfLocal local = (EBCMSCustomerUdfLocal) i.next();
						long id = local.getId();
						if ((newOB!=null) && (newOB.getId() == id)) {
							found = true;
							break;
						}
					}
					
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteUdfMtd(deleteList);
				createUdfMtd(createList, LEID);
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
	
	private void createUdfMtd(List createList, long LEID) throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRUdfInfo();
		Iterator i = createList.iterator();
		try {
			EBCMSCustomerUdfLocalHome home = getEBLocalHomeUdfInfo();
			while (i.hasNext()) {
				ICMSCustomerUdf ob = (ICMSCustomerUdf) i.next();
				if (ob != null) {
					DefaultLogger.debug(this, "Creating UdfMethod ID: "	+ ob.getId());
					String serverType = (new BatchResourceFactory()).getAppServerType();
					DefaultLogger.debug(this,"=======Application server Type is(Udf method) ======= : "+ serverType);
					if (serverType.equals(ICMSConstant.WEBSPHERE)) {
						ob.setLEID(LEID);
					}
					EBCMSCustomerUdfLocal local = home.create(ob);
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
	
	private void deleteUdfMtd(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRUdfInfo();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBCMSCustomerUdfLocal local = (EBCMSCustomerUdfLocal) i.next();
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
	/**
	 * Method to update credit addr
	 */
	private void updateRegisteredAddress(IContact[] addr, long LEID)
			throws CustomerException {
		try {
			Collection c = getCMRRegisteredAddress();

			if (null == addr) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} else {
					// delete all records
					deleteRegisteredAddress(new ArrayList(c));
				}
			} else if ((null == c) || (c.size() == 0)) {
				// create new records
				createRegisteredAddress(Arrays.asList(addr), LEID);
			} else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBContactLocal local = (EBContactLocal) i.next();

					long contactID = local.getContactID();
					boolean update = false;

					for (int j = 0; j < addr.length; j++) {
						IContact newOB = addr[j];

						if (newOB.getContactID() == contactID) {
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
					IContact newOB = addr[j];
					boolean found = false;

					while (i.hasNext()) {
						EBContactLocal local = (EBContactLocal) i.next();
						long id = local.getContactID();

						if (newOB.getContactID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteRegisteredAddress(deleteList);
				createRegisteredAddress(createList, LEID);
			}
		} catch (CustomerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}

	private void updateOtherSystem(ISystem[] addr, long LEID)
			throws CustomerException {
		try {
			Collection c = getCMROtherSystem();

			if (null == addr) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} else {
					// delete all records
					deleteOtherSystem(new ArrayList(c));
				}
			} else if ((null == c) || (c.size() == 0)) {
				// create new records
				createOtherSystem(Arrays.asList(addr), LEID);
			} else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBSystemLocal local = (EBSystemLocal) i.next();

					long systemID = local.getSystemID();
					boolean update = false;

					for (int j = 0; j < addr.length; j++) {
						ISystem newOB = addr[j];

						if (newOB.getSystemID() == systemID) {
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
					ISystem newOB = addr[j];
					boolean found = false;

					while (i.hasNext()) {
						EBSystemLocal local = (EBSystemLocal) i.next();
						long id = local.getSystemID();

						if (newOB.getSystemID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteOtherSystem(deleteList);
				createOtherSystem(createList, LEID);
			}
		} catch (CustomerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}

	private void updateDirector(IDirector[] addr, long LEID)
			throws CustomerException {
		try {
			Collection c = getCMRDirector();

			if (null == addr) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} else {
					// delete all records
					deleteDirector(new ArrayList(c));
				}
			} else if ((null == c) || (c.size() == 0)) {
				// create new records
				createDirector(Arrays.asList(addr), LEID);
			} else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBDirectorLocal local = (EBDirectorLocal) i.next();

					long directorID = local.getDirectorID();
					boolean update = false;

					for (int j = 0; j < addr.length; j++) {
						IDirector newOB = addr[j];

						if (newOB.getDirectorID() == directorID) {
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
					IDirector newOB = addr[j];
					boolean found = false;

					while (i.hasNext()) {
						EBDirectorLocal local = (EBDirectorLocal) i.next();
						long id = local.getDirectorID();

						if (newOB.getDirectorID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteDirector(deleteList);
				createDirector(createList, LEID);
			}
		} catch (CustomerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}
	
	private void updateVendorDtls(IVendor[] name, long LEID)
			throws CustomerException {
		try {
			Collection c = getCMRVendorDetails();

			if (null == name) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} else {
					// delete all records
					deleteVendorDtls(new ArrayList(c));
				}
			} else if ((null == c) || (c.size() == 0)) {
				// create new records
				createVendorDtls(Arrays.asList(name), LEID);
			} else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBVendorDetailsLocal local = (EBVendorDetailsLocal) i.next();

					long vendorId = local.getVendorId();
					boolean update = false;

					for (int j = 0; j < name.length; j++) {
						IVendor newOB = name[j];

						if (newOB.getVendorId() == vendorId) {
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
				for (int j = 0; j < name.length; j++) {
					i = c.iterator();
					IVendor newOB = name[j];
					boolean found = false;

					while (i.hasNext()) {
						EBVendorDetailsLocal local = (EBVendorDetailsLocal) i.next();
						long id = local.getVendorId();

						if (newOB.getVendorId() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteVendorDtls(deleteList);
				createVendorDtls(createList, LEID);
			}
		} catch (CustomerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception in Vendor: " + e.toString());
		}
	}

	private void updateSublineParty(ISubline[] addr, long LEID)
			throws CustomerException {
		try {
			Collection c = getCMRSubline();

			if (null == addr) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} else {
					// delete all records
					deleteSublineParty(new ArrayList(c));
				}
			} else if ((null == c) || (c.size() == 0)) {
				// create new records
				createSublineParty(Arrays.asList(addr), LEID);
			} else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBSublineLocal local = (EBSublineLocal) i.next();

					long sublineID = local.getSublineID();
					boolean update = false;

					for (int j = 0; j < addr.length; j++) {
						ISubline newOB = addr[j];

						if (newOB.getSublineID() == sublineID) {
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
					ISubline newOB = addr[j];
					boolean found = false;

					while (i.hasNext()) {
						EBSublineLocal local = (EBSublineLocal) i.next();
						long id = local.getSublineID();

						if (newOB.getSublineID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteSublineParty(deleteList);
				createSublineParty(createList, LEID);
			}
		} catch (CustomerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}

	private void updateBankingMethod(IBankingMethod[] addr, long LEID)
			throws CustomerException {
		try {
			Collection c = getCMRBankingMethod();

			if (null == addr) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} else {
					// delete all records
					deleteBankingMethod(new ArrayList(c));
				}
			} else if ((null == c) || (c.size() == 0)) {
				// create new records
				createBankingMethod(Arrays.asList(addr), LEID);
			} else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBBankingMethodLocal local = (EBBankingMethodLocal) i
							.next();

					long bankingMethodID = local.getBankingMethodID();
					boolean update = false;

					for (int j = 0; j < addr.length; j++) {
						IBankingMethod newOB = addr[j];

						if (newOB.getBankingMethodID() == bankingMethodID) {
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
					IBankingMethod newOB = addr[j];
					boolean found = false;

					while (i.hasNext()) {
						EBBankingMethodLocal local = (EBBankingMethodLocal) i
								.next();
						long id = local.getBankingMethodID();

						if (newOB.getBankingMethodID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteBankingMethod(deleteList);
				createBankingMethod(createList, LEID);
			}
		} catch (CustomerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * Method to add credit addr.
	 */
	private void createRegisteredAddress(List createList, long LEID)
			throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRRegisteredAddress();
		Iterator i = createList.iterator();
		try {
			EBContactLocalHome home = getEBLocalHomeRegAddress();
			while (i.hasNext()) {
				IContact ob = (IContact) i.next();
				DefaultLogger.debug(this, "Creating RegisteredAddress ID: "
						+ ob.getContactID());
				String serverType = (new BatchResourceFactory())
						.getAppServerType();
				DefaultLogger.debug(this,
						"=======Application server Type is (address)======= : "
								+ serverType);
				if (serverType.equals(ICMSConstant.WEBSPHERE)) {
					ob.setLEID(LEID);
				}
				EBContactLocal local = home.create(getLEID(), ob);
				c.add(local);
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

	// added by bharat waghela for customer to system mapping
	private void createOtherSystem(List createList, long LEID)
			throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMROtherSystem();
		Iterator i = createList.iterator();
		try {
			EBSystemLocalHome home = getEBLocalHomeOtherSystem();
			while (i.hasNext()) {
				ISystem ob = (ISystem) i.next();
				if (ob != null) {
					DefaultLogger.debug(this, "Creating OtherSystem ID: "
							+ ob.getSystemID());
					String serverType = (new BatchResourceFactory())
							.getAppServerType();
					DefaultLogger.debug(this,
							"=======Application server Type is (other system)======= : "
									+ serverType);
					if (serverType.equals(ICMSConstant.WEBSPHERE)) {
						ob.setLEID(LEID);
					}
					EBSystemLocal local = home.create(ob);
					c.add(local);
				}
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

	private void createDirector(List createList, long LEID)
			throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRDirector();
		Iterator i = createList.iterator();
		try {
			EBDirectorLocalHome home = getEBLocalHomeDirector();
			while (i.hasNext()) {
				IDirector ob = (IDirector) i.next();
				if (ob != null) {
					DefaultLogger.debug(this, "Creating Director ID: "
							+ ob.getDirectorID());
					String serverType = (new BatchResourceFactory())
							.getAppServerType();
					DefaultLogger.debug(this,
							"=======Application server Type is(director) ======= : "
									+ serverType);
					if (serverType.equals(ICMSConstant.WEBSPHERE)) {
						ob.setLEID(LEID);
					}
					EBDirectorLocal local = home.create(ob);
					c.add(local);
				}
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
	
	private void createVendorDtls(List createList, long LEID)
			throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRVendorDetails();
		Iterator i = createList.iterator();
		try {
			EBVendorDetailsLocalHome home = getEBLocalHomeVendor();
			while (i.hasNext()) {
				IVendor ob = (IVendor) i.next();
				if (ob != null) {
					DefaultLogger.debug(this, "Creating Vendor ID: "
							+ ob.getVendorId());
					String serverType = (new BatchResourceFactory())
							.getAppServerType();
					DefaultLogger.debug(this,
							"=======Application server Type is(Vendor) ======= : "
									+ serverType);
					if (serverType.equals(ICMSConstant.WEBSPHERE)) {
						ob.setLEID(LEID);
					}
					EBVendorDetailsLocal local = home.create(ob);
					c.add(local);
				}
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

	private void createSublineParty(List createList, long LEID)
			throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRSubline();
		Iterator i = createList.iterator();
		try {
			EBSublineLocalHome home = getEBLocalHomeSubline();
			while (i.hasNext()) {
				ISubline ob = (ISubline) i.next();
				if (ob != null) {
					DefaultLogger.debug(this, "Creating Subline ID: "
							+ ob.getSublineID());
					String serverType = (new BatchResourceFactory())
							.getAppServerType();
					DefaultLogger.debug(this,
							"=======Application server Type is(subline) ======= : "
									+ serverType);
					if (serverType.equals(ICMSConstant.WEBSPHERE)) {
						ob.setLEID(LEID);
					}
					EBSublineLocal local = home.create(ob);
					c.add(local);
				}
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

	private void createBankingMethod(List createList, long LEID)
			throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRBankingMethod();
		Iterator i = createList.iterator();
		try {
			EBBankingMethodLocalHome home = getEBLocalHomeBankingMethod();
			while (i.hasNext()) {
				IBankingMethod ob = (IBankingMethod) i.next();
				if (ob != null) {
					DefaultLogger.debug(this, "Creating BankingMethod ID: "
							+ ob.getBankingMethodID());
					String serverType = (new BatchResourceFactory())
							.getAppServerType();
					DefaultLogger.debug(this,
							"=======Application server Type is(banking method) ======= : "
									+ serverType);
					if (serverType.equals(ICMSConstant.WEBSPHERE)) {
						ob.setLEID(LEID);
					}
					EBBankingMethodLocal local = home.create(ob);
					c.add(local);
				}
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

	// ------------------------------------end------------------------------------------

	/**
	 * Method to delete credit addr
	 */
	private void deleteRegisteredAddress(List deleteList)
			throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRRegisteredAddress();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBContactLocal local = (EBContactLocal) i.next();
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

	private void deleteOtherSystem(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMROtherSystem();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBSystemLocal local = (EBSystemLocal) i.next();
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

	private void deleteDirector(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRDirector();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBDirectorLocal local = (EBDirectorLocal) i.next();
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
	
	private void deleteVendorDtls(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRVendorDetails();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBVendorDetailsLocal local = (EBVendorDetailsLocal) i.next();
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

	private void deleteSublineParty(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRSubline();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBSublineLocal local = (EBSublineLocal) i.next();
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

	private void deleteBankingMethod(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRBankingMethod();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBBankingMethodLocal local = (EBBankingMethodLocal) i.next();
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

	/**
	 * Method to retrieve credit addr
	 */
	private IContact[] retrieveRegisteredAddress() throws CustomerException {
		try {
			Collection c = getCMRRegisteredAddress();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBContactLocal local = (EBContactLocal) i.next();
					IContact ob = local.getValue();
					aList.add(ob);
				}

				return (IContact[]) aList.toArray(new IContact[0]);
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

	private ISystem[] retrieveOtherSystem() throws CustomerException {
		try {
			Collection c = getCMROtherSystem();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBSystemLocal local = (EBSystemLocal) i.next();
					ISystem ob = local.getValue();
					aList.add(ob);
				}

				return (ISystem[]) aList.toArray(new ISystem[0]);
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

	private IDirector[] retrieveDirector() throws CustomerException {
		try {
			Collection c = getCMRDirector();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBDirectorLocal local = (EBDirectorLocal) i.next();
					IDirector ob = local.getValue();
					aList.add(ob);
				}

				return (IDirector[]) aList.toArray(new IDirector[0]);
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
	
	private IVendor[] retrieveVendorDtls() throws CustomerException {
		try {
			Collection c = getCMRVendorDetails();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBVendorDetailsLocal local = (EBVendorDetailsLocal) i.next();
					IVendor ob = local.getValue();
					aList.add(ob);
				}

				return (IVendor[]) aList.toArray(new IVendor[0]);
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


	private ISubline[] retrieveSublineParty() throws CustomerException {
		try {
			Collection c = getCMRSubline();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBSublineLocal local = (EBSublineLocal) i.next();
					ISubline ob = local.getValue();
					aList.add(ob);
				}

				return (ISubline[]) aList.toArray(new ISubline[0]);
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

	private IBankingMethod[] retrieveBankingMethod() throws CustomerException {
		try {
			Collection c = getCMRBankingMethod();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBBankingMethodLocal local = (EBBankingMethodLocal) i
							.next();
					IBankingMethod ob = local.getValue();
					aList.add(ob);
				}
				IBankingMethod[] bankingMethods = (IBankingMethod[]) aList.toArray(new IBankingMethod[0]);
				Arrays.sort(bankingMethods, new BankingMethodComparator());
				return bankingMethods;	
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

	/**
	 * Method to update credit grades
	 */
	private void updateCreditGrades(ICreditGrade[] grades)
			throws CustomerException {
		try {
			Collection c = getCMRCreditGrades();

			if (null == grades) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} else {
					// delete all records
					deleteCreditGrades(new ArrayList(c));
				}
			} else if ((null == c) || (c.size() == 0)) {
				// create new records
				createCreditGrades(Arrays.asList(grades));
			} else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBCreditGradeLocal local = (EBCreditGradeLocal) i.next();

					long cgID = local.getCGID();
					boolean update = false;

					for (int j = 0; j < grades.length; j++) {
						ICreditGrade newOB = grades[j];

						if (newOB.getCGID() == cgID) {
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
				for (int j = 0; j < grades.length; j++) {
					i = c.iterator();
					ICreditGrade newOB = grades[j];
					boolean found = false;

					while (i.hasNext()) {
						EBCreditGradeLocal local = (EBCreditGradeLocal) i
								.next();
						long id = local.getCGID();

						if (newOB.getCGID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteCreditGrades(deleteList);
				createCreditGrades(createList);
			}
		} catch (CustomerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * Method to add credit grades.
	 */
	private void createCreditGrades(List createList) throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRCreditGrades();
		Iterator i = createList.iterator();
		try {
			EBCreditGradeLocalHome home = getEBLocalHomeCreditGrade();
			while (i.hasNext()) {
				ICreditGrade ob = (ICreditGrade) i.next();
				DefaultLogger.debug(this, "Creating CreditGrade ID: "
						+ ob.getCGID());
				EBCreditGradeLocal local = home.create(getLEID(), ob);
				c.add(local);
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

	/**
	 * Method to delete credit grades
	 */
	private void deleteCreditGrades(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRCreditGrades();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBCreditGradeLocal local = (EBCreditGradeLocal) i.next();
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

	/**
	 * Method to retrieve credit grades
	 */
	private ICreditGrade[] retrieveCreditGrades() throws CustomerException {
		try {
			Collection c = getCMRCreditGrades();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBCreditGradeLocal local = (EBCreditGradeLocal) i.next();
					ICreditGrade ob = local.getValue();
					aList.add(ob);
				}

				ICreditGrade[] cgList = (ICreditGrade[]) aList
						.toArray(new ICreditGrade[0]);
				Arrays.sort(cgList, new CreditGradeComparator());
				return cgList;
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

	/**
	 * Method to update credit code
	 */
	private void updateISICCode(IISICCode[] code) throws CustomerException {
		try {
			Collection c = getCMRISICCode();

			if (null == code) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} else {
					// delete all records
					deleteISICCode(new ArrayList(c));
				}
			} else if ((null == c) || (c.size() == 0)) {
				// create new records
				createISICCode(Arrays.asList(code));
			} else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBISICCodeLocal local = (EBISICCodeLocal) i.next();

					long isicID = local.getISICID();
					boolean update = false;

					for (int j = 0; j < code.length; j++) {
						IISICCode newOB = code[j];

						if (newOB.getISICID() == isicID) {
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
				for (int j = 0; j < code.length; j++) {
					i = c.iterator();
					IISICCode newOB = code[j];
					boolean found = false;

					while (i.hasNext()) {
						EBISICCodeLocal local = (EBISICCodeLocal) i.next();
						long id = local.getISICID();

						if (newOB.getISICID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteISICCode(deleteList);
				createISICCode(createList);
			}
		} catch (CustomerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * Method to add credit code.
	 */
	private void createISICCode(List createList) throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRISICCode();
		Iterator i = createList.iterator();
		try {
			EBISICCodeLocalHome home = getEBLocalHomeISICCode();
			while (i.hasNext()) {
				IISICCode ob = (IISICCode) i.next();
				DefaultLogger.debug(this, "Creating ISICCode ID: "
						+ ob.getISICID());
				EBISICCodeLocal local = home.create(getLEID(), ob);
				c.add(local);
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

	/**
	 * Method to delete credit code
	 */
	private void deleteISICCode(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRISICCode();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBISICCodeLocal local = (EBISICCodeLocal) i.next();
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

	/**
	 * Method to retrieve credit code
	 */
	private IISICCode[] retrieveISICCode() throws CustomerException {
		try {
			Collection c = getCMRISICCode();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBISICCodeLocal local = (EBISICCodeLocal) i.next();
					IISICCode ob = local.getValue();

					/*
					 * Changed to avoid the null reference exception
					 */
					if ("D" != ob.getISICStatus()) {
						aList.add(ob);
					}
					// if (!ob.getISICStatus().equals("D")) {
					// aList.add(ob);
					// }
				}

				return (IISICCode[]) aList.toArray(new IISICCode[0]);
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

	/**
	 * Method to update credit status
	 */
	private void updateCreditStatus(ICreditStatus[] status)
			throws CustomerException {
		try {
			Collection c = getCMRCreditStatus();

			if (null == status) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} else {
					// delete all records
					deleteCreditStatus(new ArrayList(c));
				}
			} else if ((null == c) || (c.size() == 0)) {
				// create new records
				createCreditStatus(Arrays.asList(status));
			} else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBCreditStatusLocal local = (EBCreditStatusLocal) i.next();

					long csID = local.getCSID();
					boolean update = false;

					for (int j = 0; j < status.length; j++) {
						ICreditStatus newOB = status[j];

						if (newOB.getCSID() == csID) {
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
				for (int j = 0; j < status.length; j++) {
					i = c.iterator();
					ICreditStatus newOB = status[j];
					boolean found = false;

					while (i.hasNext()) {
						EBCreditStatusLocal local = (EBCreditStatusLocal) i
								.next();
						long id = local.getCSID();

						if (newOB.getCSID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteCreditStatus(deleteList);
				createCreditStatus(createList);
			}
		} catch (CustomerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * Method to add credit status.
	 */
	private void createCreditStatus(List createList) throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRCreditStatus();
		Iterator i = createList.iterator();
		try {
			EBCreditStatusLocalHome home = getEBLocalHomeCreditStatus();
			while (i.hasNext()) {
				ICreditStatus ob = (ICreditStatus) i.next();
				DefaultLogger.debug(this, "Creating CreditStatus ID: "
						+ ob.getCSID());
				EBCreditStatusLocal local = home.create(getLEID(), ob);
				c.add(local);
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

	/**
	 * Method to delete credit status
	 */
	private void deleteCreditStatus(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRCreditStatus();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBCreditStatusLocal local = (EBCreditStatusLocal) i.next();
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

	/**
	 * Method to retrieve credit status
	 */
	private ICreditStatus[] retrieveCreditStatus() throws CustomerException {
		try {
			Collection c = getCMRCreditStatus();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBCreditStatusLocal local = (EBCreditStatusLocal) i.next();
					ICreditStatus ob = local.getValue();
					aList.add(ob);
				}

				ICreditStatus[] csList = (ICreditStatus[]) aList
						.toArray(new ICreditStatus[0]);
				Arrays.sort(csList, new CreditStatusComparator());
				return csList;
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

	/**
	 * Method to update KYC records
	 */
	private void updateKYCRecords(IKYC[] records) throws CustomerException {
		try {
			Collection c = getCMRKYCRecords();

			if (null == records) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				} else {
					// delete all records
					deleteKYCRecords(new ArrayList(c));
				}
			} else if ((null == c) || (c.size() == 0)) {
				// create new records
				createKYCRecords(Arrays.asList(records));
			} else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBKYCLocal local = (EBKYCLocal) i.next();

					long kycID = local.getKYCID();
					boolean update = false;

					for (int j = 0; j < records.length; j++) {
						IKYC newOB = records[j];

						if (newOB.getKYCID() == kycID) {
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
				for (int j = 0; j < records.length; j++) {
					i = c.iterator();
					IKYC newOB = records[j];
					boolean found = false;

					while (i.hasNext()) {
						EBKYCLocal local = (EBKYCLocal) i.next();
						long id = local.getKYCID();

						if (newOB.getKYCID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteKYCRecords(deleteList);
				createKYCRecords(createList);
			}
		} catch (CustomerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * Method to add KYC records.
	 */
	private void createKYCRecords(List createList) throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRKYCRecords();
		Iterator i = createList.iterator();
		try {
			EBKYCLocalHome home = getEBLocalHomeKYC();
			while (i.hasNext()) {
				IKYC ob = (IKYC) i.next();
				DefaultLogger.debug(this, "Creating KYC ID: " + ob.getKYCID());
				EBKYCLocal local = home.create(getLEID(), ob);
				c.add(local);
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

	/**
	 * Method to delete KYC records
	 */
	private void deleteKYCRecords(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRKYCRecords();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBKYCLocal local = (EBKYCLocal) i.next();
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

	/**
	 * Method to retrieve KYC records
	 */
	private IKYC[] retrieveKYCRecords() throws CustomerException {
		try {
			Collection c = getCMRKYCRecords();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBKYCLocal local = (EBKYCLocal) i.next();
					IKYC ob = local.getValue();
					aList.add(ob);
				}

				return (IKYC[]) aList.toArray(new IKYC[0]);
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

	// ************************ BeanController Methods **************
	/**
	 * Method to get EB Local Home for Registered Address
	 */
	protected EBContactLocalHome getEBLocalHomeRegAddress()
			throws CustomerException {
		EBContactLocalHome home = (EBContactLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_REG_ADDRESS_LOCAL_JNDI,
						EBContactLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBContactLocalHome is null!");
		}
	}

	protected EBSystemLocalHome getEBLocalHomeOtherSystem()
			throws CustomerException {
		EBSystemLocalHome home = (EBSystemLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_SYSTEM_LOCAL_JNDI,
						EBSystemLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBSystemLocalHome is null!");
		}
	}

	protected EBDirectorLocalHome getEBLocalHomeDirector()
			throws CustomerException {
		EBDirectorLocalHome home = (EBDirectorLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_DIRECTOR_LOCAL_JNDI,
						EBDirectorLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBDirectorLocalHome is null!");
		}
	}
	
	protected EBVendorDetailsLocalHome getEBLocalHomeVendor()
			throws CustomerException {
		EBVendorDetailsLocalHome home = (EBVendorDetailsLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_VENDOR_LOCAL_JNDI,
						EBVendorDetailsLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBVendorDetailsLocalHome is null!");
		}
	}

	protected EBSublineLocalHome getEBLocalHomeSubline()
			throws CustomerException {
		EBSublineLocalHome home = (EBSublineLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_SUBLINE_LOCAL_JNDI,
						EBSublineLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBSublineLocalHome is null!");
		}
	}

	protected EBBankingMethodLocalHome getEBLocalHomeBankingMethod()
			throws CustomerException {
		EBBankingMethodLocalHome home = (EBBankingMethodLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_BANKING_LOCAL_JNDI,
						EBBankingMethodLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBBankingMethodLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for Credit Grade
	 */
	protected EBCreditGradeLocalHome getEBLocalHomeCreditGrade()
			throws CustomerException {
		EBCreditGradeLocalHome home = (EBCreditGradeLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_CREDIT_GRADE_LOCAL_JNDI,
						EBCreditGradeLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBCreditGradeLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for ISIC Code
	 */
	protected EBISICCodeLocalHome getEBLocalHomeISICCode()
			throws CustomerException {
		EBISICCodeLocalHome home = (EBISICCodeLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_ISIC_CODE_LOCAL_JNDI,
						EBISICCodeLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBISICCodeLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for ISIC Code
	 */
	protected EBCreditStatusLocalHome getEBLocalHomeCreditStatus()
			throws CustomerException {
		EBCreditStatusLocalHome home = (EBCreditStatusLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_CREDIT_STATUS_LOCAL_JNDI,
						EBCreditStatusLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBCreditStatusLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for KYC
	 */
	protected EBKYCLocalHome getEBLocalHomeKYC() throws CustomerException {
		EBKYCLocalHome home = (EBKYCLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_KYC_LOCAL_JNDI, EBKYCLocalHome.class
						.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBKYCLocalHome is null!");
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
		Collection c = getCMRRegisteredAddress();
		if (null != c) {
			Iterator i = c.iterator();
			while (i.hasNext()) {
				EBContactLocal local = (EBContactLocal) i.next();
				i.remove(); // remove this local interface from the collection
				local.remove(); // remove the data
			}
		}

		c = getCMRCreditGrades();
		if (null != c) {
			Iterator i = c.iterator();
			while (i.hasNext()) {
				EBCreditGradeLocal local = (EBCreditGradeLocal) i.next();
				i.remove(); // remove this local interface from the collection
				local.remove(); // remove the data
			}
		}

		c = getCMRISICCode();
		if (null != c) {
			Iterator i = c.iterator();
			while (i.hasNext()) {
				EBISICCodeLocal local = (EBISICCodeLocal) i.next();
				i.remove(); // remove this local interface from the collection
				local.remove(); // remove the data
			}
		}

		c = getCMRCreditStatus();
		if (null != c) {
			Iterator i = c.iterator();
			while (i.hasNext()) {
				EBCreditStatusLocal local = (EBCreditStatusLocal) i.next();
				i.remove(); // remove this local interface from the collection
				local.remove(); // remove the data
			}
		}

		c = getCMRKYCRecords();
		if (null != c) {
			Iterator i = c.iterator();
			while (i.hasNext()) {
				EBKYCLocal local = (EBKYCLocal) i.next();
				i.remove(); // remove this local interface from the collection
				local.remove(); // remove the data
			}
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

	// Getters
	public abstract String getLEReference();

	public abstract String getCustomerSegment();

	public abstract String getCustomerSubSegment();

	public abstract String getBaselSegment();

	public abstract String getBaselSubSegment();

	public abstract String getCustomerBizType();

	public abstract String getEnvironmentRisk();

	public abstract String getOperationStatus();

	public abstract Date getOperationEffectiveDate();

	public abstract String getOperationDescription();

	public abstract void setLEReference(String value);

	public abstract void setCustomerSegment(String value);

	public abstract void setCustomerSubSegment(String value);

	public abstract void setBaselSegment(String value);

	public abstract void setBaselSubSegment(String value);

	public abstract void setCustomerBizType(String value);

	public abstract void setEnvironmentRisk(String value);

	public abstract void setOperationStatus(String value);

	public abstract void setOperationEffectiveDate(Date value);

	public abstract void setOperationDescription(String value);

	public abstract String getIdOldNO();

	public abstract void setIdOldNO(String idOldNO);

	public abstract Date getIncorporateDate();

	public abstract void setIncorporateDate(Date incorporateDate);

	public abstract String getSourceID();

	public abstract void setSourceID(String sourceID);

	public abstract void setVersionTime(long l);

	public abstract String getAccountOfficerID();

	public abstract void setAccountOfficerID(String accountOfficerID);

	public abstract String getAccountOfficerName();

	public abstract void setAccountOfficerName(String accountOfficerName);

	// ------------------------------------------added by bharat
	// weblogic---------start---------------------

	public abstract String getPartyGroupName();

	public abstract void setPartyGroupName(String partyGroupName);

	public abstract  String getRelationshipMgrEmpCode();

	public abstract  void setRelationshipMgrEmpCode(String relationshipMgrEmpCode);
	
	public abstract String getRelationshipMgr();

	public abstract void setRelationshipMgr(String relationshipMgr);

	public abstract String getRmRegion();

	public abstract void setRmRegion(String rmRegion);

	public abstract String getCycle();

	public abstract void setCycle(String cycle);

	public abstract String getEntity();

	public abstract void setEntity(String entity);

	public abstract String getRbiIndustryCode();

	public abstract void setRbiIndustryCode(String rbiIndustryCode);
	
    public abstract Date getDateOfIncorporation();
	
	public abstract void setDateOfIncorporation(Date value);
	
	public abstract String getAadharNumber();
	
	public abstract void setAadharNumber(String aadharNumber);
	
	public abstract String getCinLlpin();
	
	public abstract void setCinLlpin(String cinLlpin);
	
	public abstract String getPartyNameAsPerPan();
	public abstract void setPartyNameAsPerPan(String partyNameAsPerPan);

	public abstract String getIndustryName();

	public abstract void setIndustryName(String industryName);
	
	
	//NEW CAM UI FORMAT START
	
	public abstract String getListedCompany();  
	public abstract void setListedCompany(String value);

	public abstract String getIsinNo();  
	public abstract void setIsinNo(String value);
	
	public abstract String getRaroc();  
	public abstract void setRaroc(String value);
	
	public abstract Date getRaraocPeriod();  
	public abstract void setRaraocPeriod(Date value);
	
	public abstract String getYearEndPeriod();  
	public abstract void setYearEndPeriod(String value);
	
	public abstract String getCreditMgrEmpId();
	public abstract void setCreditMgrEmpId(String creditMgrEmpId);

	public abstract String getPfLrdCreditMgrEmpId(); 
	public abstract void setPfLrdCreditMgrEmpId(String pfLrdCreditMgrEmpId);

	public abstract String getCreditMgrSegment();
	public abstract void setCreditMgrSegment(String creditMgrSegment);
	
	public abstract String getMultBankFundBasedLeadBankPer();
	public abstract void setMultBankFundBasedLeadBankPer(String multBankFundBasedLeadBankPer);

	public abstract String getMultBankFundBasedHdfcBankPer();
	public abstract void setMultBankFundBasedHdfcBankPer(String multBankFundBasedHdfcBankPer);

	public abstract String getMultBankNonFundBasedLeadBankPer();
	public abstract void setMultBankNonFundBasedLeadBankPer(String multBankNonFundBasedLeadBankPer);

	public abstract String getMultBankNonFundBasedHdfcBankPer();
	public abstract void setMultBankNonFundBasedHdfcBankPer(String multBankNonFundBasedHdfcBankPer);

	public abstract String getConsBankFundBasedLeadBankPer();
	public abstract void setConsBankFundBasedLeadBankPer(String consBankFundBasedLeadBankPer) ;

	public abstract String getConsBankFundBasedHdfcBankPer();
	public abstract void setConsBankFundBasedHdfcBankPer(String consBankFundBasedHdfcBankPer);

	public abstract String getConsBankNonFundBasedLeadBankPer();
	public abstract void setConsBankNonFundBasedLeadBankPer(String consBankNonFundBasedLeadBankPer);

	public abstract String getConsBankNonFundBasedHdfcBankPer();
	public abstract void setConsBankNonFundBasedHdfcBankPer(String consBankNonFundBasedHdfcBankPer);

	public abstract String getFinalBankMethodList();
	public abstract void setFinalBankMethodList(String finalBankMethodList);
	
	//NEW CAM UI FORMAT END
	
	//Santosh LEI CR 11/09/2018
	public abstract String getLeiCode();
	public abstract void setLeiCode(String leiCode);
	public abstract Date getLeiExpDate();
	public abstract void setLeiExpDate(Date leiExpDate);
//	public abstract char getIsLeiValidated();
//	public abstract void setIsLeiValidated(char isLeiValidated);
	public abstract String getDeferLEI();
	public abstract void setDeferLEI(String deferLEI);
	public abstract char getLeiValGenParamFlag();
	public abstract void setLeiValGenParamFlag(char leiValGenParamFlag);
	public abstract Date getLastModifiedLei();
	public abstract void setLastModifiedLei(Date lastModifiedLei);
	//End Santosh

	public abstract String getPan();

	public abstract void setPan(String pan);

	public abstract String getRegion();

	public abstract void setRegion(String region);

	// -------------------------------------------------------------------------------------------------------
	public abstract String getSubLine();

	public abstract void setSubLine(String subLine);

	public abstract String getBankingMethod();

	public abstract void setBankingMethod(String bankingMethod);

	public abstract String getTotalFundedLimit();

	public abstract void setTotalFundedLimit(String totalFundedLimit);

	public abstract String getTotalNonFundedLimit();

	public abstract void setTotalNonFundedLimit(String totalNonFundedLimit);

	public abstract String getFundedSharePercent();

	public abstract void setFundedSharePercent(String fundedSharePercent);

	public abstract String getNonFundedSharePercent();

	public abstract void setNonFundedSharePercent(String nonFundedSharePercent);

	public abstract String getMemoExposure();

	public abstract void setMemoExposure(String memoExposure);

	public abstract String getTotalSanctionedLimit();

	public abstract void setTotalSanctionedLimit(String totalSanctionedLimit);

	public abstract String getMpbf();

	public abstract void setMpbf(String mpbf);

	public abstract String getFundedShareLimit();

	public abstract void setFundedShareLimit(String fundedShareLimit);

	public abstract String getNonFundedShareLimit();

	public abstract void setNonFundedShareLimit(String nonFundedShareLimit);
	
	public abstract String getFundedIncreDecre();

	public abstract void setFundedIncreDecre(String fundedIncreDecre);
	
	public abstract String getNonFundedIncreDecre();

	public abstract void setNonFundedIncreDecre(String nonFundedIncreDecre);
	
	public abstract String getMemoExposIncreDecre();

	public abstract void setMemoExposIncreDecre(String memoExposIncreDecre);

	// -------------------------------------------------------------------------------------------------------
	public abstract String getBorrowerDUNSNo();

	public abstract void setBorrowerDUNSNo(String borrowerDUNSNo);

	public abstract String getClassActivity1();

	public abstract void setClassActivity1(String classActivity1);

	public abstract String getClassActivity2();

	public abstract void setClassActivity2(String classActivity2);

	public abstract String getClassActivity3();

	public abstract void setClassActivity3(String classActivity3);

	public abstract String getWillfulDefaultStatus();

	public abstract void setWillfulDefaultStatus(String willfulDefaultStatus);

	public abstract String getSuitFilledStatus();

	public abstract void setSuitFilledStatus(String suitFilledStatus);

	public abstract Date getDateOfSuit();

	public abstract void setDateOfSuit(Date dateOfSuit);

	public abstract String getSuitAmount();

	public abstract void setSuitAmount(String suitAmount);

	public abstract String getSuitReferenceNo();

	public abstract void setSuitReferenceNo(String suitReferenceNo);

	public abstract Date getDateWillfulDefault();

	public abstract void setDateWillfulDefault(Date dateWillfulDefault);

	public abstract String getRegOfficeDUNSNo();

	public abstract void setRegOfficeDUNSNo(String regOfficeDUNSNo);

	public abstract String getPartyConsent();

	public abstract void setPartyConsent(String partyConsent);

	public abstract String getRegOffice();  

	public abstract void setRegOffice(String regOffice) ; 
	
	public abstract String getMainBranch();

	public abstract void setMainBranch(String mainBranch);
	
	public abstract String getBranchCode() ;

	public abstract void setBranchCode(String branchCode); 
	
	public abstract  String getCurrency() ;

	public abstract  void setCurrency(String currency);

	public abstract Collection getCMRCriInfo();

	public abstract void setCMRCriInfo(Collection value);
	
	public abstract Collection getCMRCriFac();

	public abstract void setCMRCriFac(Collection value);

	// ------------------------------------------added by bharat
	// weblogic------------end------------------
	
	//added by shiv 220811
	
	public ICriInfo[] getCriList() {
		return null;
	}
	
	public void setCriList(ICriInfo[] value) {
		// do nothing
	}

	private ICriInfo[] retrieveCriInfo() throws CustomerException {
		try {
			Collection c = getCMRCriInfo();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBCriInfoLocal local = (EBCriInfoLocal) i.next();
					
					ICriInfo ob = local.getValue();
					aList.add(ob);
				}

				return (ICriInfo[]) aList.toArray(new ICriInfo[0]);
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
	
	private void updateCriInfo(ICriInfo[] addr, long LEID)
	throws CustomerException {
try {
	Collection c = getCMRCriInfo();

	if (null == addr) {
		if ((null == c) || (c.size() == 0)) {
			return; // nothing to do
		} else {
			// delete all records
			deleteCriInfo(new ArrayList(c));
		}
	} else if ((null == c) || (c.size() == 0)) {
		// create new records
		createCriInfo(Arrays.asList(addr), LEID);
	} else {
		Iterator i = c.iterator();
		ArrayList createList = new ArrayList(); // contains list of OBs
		ArrayList deleteList = new ArrayList(); // contains list of
		// local interfaces

		// identify identify records for delete or udpate first
		while (i.hasNext()) {
			EBCriInfoLocal local = (EBCriInfoLocal) i
					.next();

			long criInfoID = local.getCriInfoID();
			boolean update = false;

			for (int j = 0; j < addr.length; j++) {
				ICriInfo newOB = addr[j];

				if (newOB.getCriInfoID() == criInfoID) {
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
			ICriInfo newOB = addr[j];
			boolean found = false;

			while (i.hasNext()) {
				EBCriInfoLocal local = (EBCriInfoLocal) i
						.next();
				long id = local.getCriInfoID();

				if (newOB.getCriInfoID() == id) {
					found = true;
					break;
				}
			}
			if (!found) {
				// add for adding
				createList.add(newOB);
			}
		}
		deleteCriInfo(deleteList);
		createCriInfo(createList, LEID);
	}
} catch (CustomerException e) {
	throw e;
} catch (Exception e) {
	e.printStackTrace();
	throw new CustomerException("Caught Exception: " + e.toString());
}
}
	private void createCriInfo(List createList, long LEID)
	throws CustomerException {
if ((null == createList) || (createList.size() == 0)) {
	return; // do nothing
}
Collection c = getCMRCriInfo();
Iterator i = createList.iterator();
try {
	EBCriInfoLocalHome home = getEBLocalHomeCriInfo();
	while (i.hasNext()) {
		ICriInfo ob = (ICriInfo) i.next();
		if (ob != null) {
			DefaultLogger.debug(this, "Creating BankingMethod ID: "
					+ ob.getCriInfoID());
			String serverType = (new BatchResourceFactory())
					.getAppServerType();
			DefaultLogger.debug(this,
					"=======Application server Type is(banking method) ======= : "
							+ serverType);
			if (serverType.equals(ICMSConstant.WEBSPHERE)) {
				ob.setLEID(LEID);
			}
			EBCriInfoLocal local = home.create(ob);
			c.add(local);
		}
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
	
	private void deleteCriInfo(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRCriInfo();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBCriInfoLocal local = (EBCriInfoLocal) i.next();
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
	

	protected EBCriInfoLocalHome getEBLocalHomeCriInfo()
			throws CustomerException {
		EBCriInfoLocalHome home = (EBCriInfoLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_CRI_LOCAL_JNDI,
						EBCriInfoLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBCriInfoLocalHome is null!");
		}
	}
	
	
//added by shiv 290811
	
	public ICriFac[] getCriFacList() {
		return null;
	}
	
	public void setCriFacList(ICriFac[] value) {
		// do nothing
	}

	private ICriFac[] retrieveCriFac() throws CustomerException {
		try {
			Collection c = getCMRCriFac();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBCriFacLocal local = (EBCriFacLocal) i.next();
					
					ICriFac ob = local.getValue();
					aList.add(ob);
				}

				return (ICriFac[]) aList.toArray(new ICriFac[0]);
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
	
	private void updateCriFac(ICriFac[] addr, long LEID)
	throws CustomerException {
try {
	Collection c = getCMRCriFac();

	if (null == addr) {
		if ((null == c) || (c.size() == 0)) {
			return; // nothing to do
		} else {
			// delete all records
			deleteCriFac(new ArrayList(c));
		}
	} else if ((null == c) || (c.size() == 0)) {
		// create new records
		createCriFac(Arrays.asList(addr), LEID);
	} else {
		Iterator i = c.iterator();
		ArrayList createList = new ArrayList(); // contains list of OBs
		ArrayList deleteList = new ArrayList(); // contains list of
		// local interfaces

		// identify identify records for delete or udpate first
		while (i.hasNext()) {
			EBCriFacLocal local = (EBCriFacLocal) i
					.next();

			long criFacID = local.getCriFacID();
			boolean update = false;

			for (int j = 0; j < addr.length; j++) {
				ICriFac newOB = addr[j];

				if (newOB.getCriFacID() == criFacID) {
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
			ICriFac newOB = addr[j];
			boolean found = false;

			while (i.hasNext()) {
				EBCriFacLocal local = (EBCriFacLocal) i
						.next();
				long id = local.getCriFacID();

				if (newOB.getCriFacID() == id) {
					found = true;
					break;
				}
			}
			if (!found) {
				// add for adding
				createList.add(newOB);
			}
		}
		deleteCriFac(deleteList);
		createCriFac(createList, LEID);
	}
} catch (CustomerException e) {
	throw e;
} catch (Exception e) {
	e.printStackTrace();
	throw new CustomerException("Caught Exception: " + e.toString());
}
}
	private void createCriFac(List createList, long LEID)
	throws CustomerException {
if ((null == createList) || (createList.size() == 0)) {
	return; // do nothing
}
Collection c = getCMRCriFac();
Iterator i = createList.iterator();
try {
	EBCriFacLocalHome home = getEBLocalHomeCriFac();
	while (i.hasNext()) {
		ICriFac ob = (ICriFac) i.next();
		if (ob != null) {
			DefaultLogger.debug(this, "Creating BankingMethod ID: "
					+ ob.getCriFacID());
			String serverType = (new BatchResourceFactory())
					.getAppServerType();
			DefaultLogger.debug(this,
					"=======Application server Type is(banking method) ======= : "
							+ serverType);
			if (serverType.equals(ICMSConstant.WEBSPHERE)) {
				ob.setLEID(LEID);
			}
			EBCriFacLocal local = home.create(ob);
			c.add(local);
		}
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
	
	private void deleteCriFac(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRCriFac();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBCriFacLocal local = (EBCriFacLocal) i.next();
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
	

	protected EBCriFacLocalHome getEBLocalHomeCriFac()
			throws CustomerException {
		EBCriFacLocalHome home = (EBCriFacLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_CRI_FAC_LOCAL_JNDI,
						EBCriFacLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBCriFacLocalHome is null!");
		}
	}

	// Added by Pradeep For UDF - 13th Oct 2011
	
	public abstract Collection getCMRUdfInfo();
	public abstract void setCMRUdfInfo(Collection value);
	
	public ICMSCustomerUdf[] getUdfList() {
		return null;
	}
	public void setUdfList(ICMSCustomerUdf[] value) {
		// do nothing
	}
	
	private ICMSCustomerUdf[] retrieveUdfInfo() throws CustomerException {
		try {
			Collection c = getCMRUdfInfo();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBCMSCustomerUdfLocal local = (EBCMSCustomerUdfLocal) i.next();
					ICMSCustomerUdf ob = local.getValue();
					aList.add(ob);
				}
				return (ICMSCustomerUdf[]) aList.toArray(new ICMSCustomerUdf[0]);
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
	
	private ICMSCustomerUdf[] retrieveUdfMtd() throws CustomerException {
		try {
			Collection c = getCMRUdfInfo();
			if ((null == c) || (c.size() == 0)) {
				return null;
			} else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBCMSCustomerUdfLocal local = (EBCMSCustomerUdfLocal) i.next();
					ICMSCustomerUdf ob = local.getValue();
					aList.add(ob);
				}
				return (ICMSCustomerUdf[]) aList.toArray(new ICMSCustomerUdf[0]);
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
	
	private void updateUdfInfo(ICMSCustomerUdf[] addr, long LEID)	throws CustomerException {
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
					EBCMSCustomerUdfLocal local = (EBCMSCustomerUdfLocal) i.next();
					long id = local.getId();
					boolean update = false;

					for (int j = 0; j < addr.length; j++) {
						ICMSCustomerUdf newOB = addr[j];

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
					ICMSCustomerUdf newOB = addr[j];
					boolean found = false;

					while (i.hasNext()) {
						EBCMSCustomerUdfLocal local = (EBCMSCustomerUdfLocal) i.next();
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
		catch (CustomerException e) {
			throw e;
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}
	
	private void createUdfInfo(List createList, long LEID) throws CustomerException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRUdfInfo();
		Iterator i = createList.iterator();
		try {
			EBCMSCustomerUdfLocalHome home = getEBLocalHomeUdfInfo();
			while (i.hasNext()) {
				ICMSCustomerUdf ob = (ICMSCustomerUdf) i.next();
				if (ob != null) {
					DefaultLogger.debug(this, "Creating BankingMethod ID: "	+ ob.getId());
					String serverType = (new BatchResourceFactory()).getAppServerType();
					DefaultLogger.debug(this,"=======Application server Type is(banking method) ======= : "+ serverType);
					if (serverType.equals(ICMSConstant.WEBSPHERE)) {
						ob.setLEID(LEID);
					}
					EBCMSCustomerUdfLocal local = home.create(ob);
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
	
	private void deleteUdfInfo(List deleteList) throws CustomerException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRUdfInfo();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBCMSCustomerUdfLocal local = (EBCMSCustomerUdfLocal) i.next();
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
	

	protected EBCMSCustomerUdfLocalHome getEBLocalHomeUdfInfo() throws CustomerException {
		EBCMSCustomerUdfLocalHome home = (EBCMSCustomerUdfLocalHome) BeanController	.getEJBLocalHome(ICMSJNDIConstant.EB_CMS_CUSTOMER_UDF_LOCAL_JNDI, EBCMSCustomerUdfLocalHome.class.getName());
		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBCMSCustomerUdfLocalHome is null!");
		}
	}

	public ICMSCustomerUdf[] getUdfData() {
		return null;
	}
	public void setUdfData(ICMSCustomerUdf[] udfData) {
		
	}
	public ICMSCustomerUdf[] getUdfMtdList() {
		return null;
	}
	public void setUdfMtdList(ICMSCustomerUdf[] udfData) {
		
	}
	public abstract Collection getCoBorrowerDetailsCMR();
	
	public abstract void setCoBorrowerDetailsCMR(Collection stock);
	
	public String getCoBorrowerDetailsInd() {
		return null;
	}

	public void setCoBorrowerDetailsInd(String coBorrowerDetailsInd) {
		
	}
	
	public List<ICoBorrowerDetails> getCoBorrowerDetails() {
		Collection<?> cmr = getCoBorrowerDetailsCMR();
		if (CollectionUtils.isEmpty(cmr))
			return null;
		List<ICoBorrowerDetails> list = new ArrayList<ICoBorrowerDetails>();
		
		try {
			Iterator<?> iter = cmr.iterator();
			while (iter.hasNext()) {
				EBCoBorrowerDetailsLocal local = (EBCoBorrowerDetailsLocal) iter.next();
				ICoBorrowerDetails coBorrowerDetails = local.getValue();
				list.add(coBorrowerDetails);
			}
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception retrieving Co-Borrower data", e);
			//TODO throw Exception
		}
		return list;
	}
	
	public void setCoBorrowerDetails(List<ICoBorrowerDetails> coBorrowerDetailsList) {
		
	}
	
	protected EBCoBorrowerDetailsLocalHome getEBCoBorrowerDetailsLocalHome() throws CustomerException {
		EBCoBorrowerDetailsLocalHome home = (EBCoBorrowerDetailsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CO_BORROWER_DETAILS_JNDI, EBCoBorrowerDetailsLocalHome.class.getName());

		if (null != home) {
			return home;
		} else {
			throw new CustomerException("EBCoBorrowerDetailsLocalHome is null!");
		}
	}
	
	private void updateCoBorrowerDetails(List<ICoBorrowerDetails> coBorrowerDetailsList, long mainProfileId) throws CustomerException {
		try {
			Collection cmr = getCoBorrowerDetailsCMR();

			if (coBorrowerDetailsList == null) {
				if (CollectionUtils.isEmpty(cmr)) {
					return;
				} else {
					deleteCoBorrowerDetails(new ArrayList(cmr));
				}
			} else if (CollectionUtils.isEmpty(cmr)) {
				createCoBorrowerDetails(coBorrowerDetailsList, mainProfileId);
			} else {
				Iterator iterator = cmr.iterator();
				List<ICoBorrowerDetails> createList = new ArrayList<ICoBorrowerDetails>();
				List<EBCoBorrowerDetailsLocal> deleteList = new ArrayList<EBCoBorrowerDetailsLocal>();

				while (iterator.hasNext()) {
					EBCoBorrowerDetailsLocal local = (EBCoBorrowerDetailsLocal) iterator.next();
					boolean update = false;

					for (ICoBorrowerDetails coBorrowerDetails : coBorrowerDetailsList) {
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

				for (ICoBorrowerDetails coBorrowerDetails : coBorrowerDetailsList) {
					iterator = cmr.iterator();
					boolean found = false;

					while (iterator.hasNext()) {
						EBCoBorrowerDetailsLocal local = (EBCoBorrowerDetailsLocal) iterator.next();
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
				createCoBorrowerDetails(createList, mainProfileId);
			}
		} catch (CustomerException e) {
			DefaultLogger.error(this, "Exception While updating Co-Borrower Details", e);
			throw e;
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception While updating Co-Borrower Details", e);
			throw new CustomerException("Caught Exception: " + e.toString());
		}
	}
	
	private void deleteCoBorrowerDetails(List<EBCoBorrowerDetailsLocal> coBorrowerDetailsList) {
		if (CollectionUtils.isEmpty(coBorrowerDetailsList)) {
			return;
		}

		Collection cmr = getCoBorrowerDetailsCMR();
		Iterator<EBCoBorrowerDetailsLocal> iterator = coBorrowerDetailsList.iterator();
		while (iterator.hasNext()) {
			EBCoBorrowerDetailsLocal local = iterator.next();
			cmr.remove(local);
			try {
				local.remove();
			} catch (EJBException e) {
				DefaultLogger.error(this, "Exception while deleting Co-Borrower Details item(s)", e);
				throw new CustomerException(e.getMessage(), e);
			} catch (RemoveException e) {
				DefaultLogger.error(this, "Exception while deleting Co-Borrower Details item(s)", e);
				throw new CustomerException(e.getMessage(), e);
			}
		}
	}
	
	private void createCoBorrowerDetails(List<ICoBorrowerDetails> coBorrowerDetailsList, long mainProfileId) {
		if (CollectionUtils.isEmpty(coBorrowerDetailsList)) {
			return;
		}
		Collection cmr = getCoBorrowerDetailsCMR();
		Iterator<ICoBorrowerDetails> iterator = coBorrowerDetailsList.iterator();
		EBCoBorrowerDetailsLocalHome home = getEBCoBorrowerDetailsLocalHome();
		while (iterator.hasNext()) {
			ICoBorrowerDetails coBorrowerDetails = iterator.next();
			if (coBorrowerDetails != null) {
				String serverType = (new BatchResourceFactory()).getAppServerType();
				DefaultLogger.debug(this, "Creating Co-Borrower Details ID: " + coBorrowerDetails.getId()
						+ ", Application Server Type : " + serverType);
				
				if (serverType.equals(ICMSConstant.WEBSPHERE)) {
					coBorrowerDetails.setMainProfileId(mainProfileId);
				}
				
				EBCoBorrowerDetailsLocal local;
				try {
					local = home.create(coBorrowerDetails);
				} catch (CreateException e) {
					DefaultLogger.error(this, "Exception while creating Co-Borrower Details item(s)", e);
					throw new CustomerException(e.getMessage(), e);
				}
				cmr.add(local);
			}
		}
	}
	
	// -------------------------------------
	
	//Start:Uma Khot:CRI Field addition enhancement
	public abstract String getIsRBIWilfulDefaultersList();
	public abstract void setIsRBIWilfulDefaultersList(String isRBIWilfulDefaulterlist);
	public abstract String getNameOfBank();
	public abstract void setNameOfBank(String nameOfBank);
	public abstract String getIsDirectorMoreThanOne();
	public abstract void setIsDirectorMoreThanOne(String isDirectorMoreThanOne);
	public abstract String getNameOfDirectorsAndCompany();
	public abstract void setNameOfDirectorsAndCompany(String nameOfDirectorAndCompany);
	public abstract String getIsBorrDefaulterWithBank();
	public abstract void setIsBorrDefaulterWithBank(String isBorrDefaulterWithBank);
	public abstract String getDetailsOfDefault();
	public abstract void setDetailsOfDefault(String detailsOfDefault);
	public abstract String getExtOfCompromiseAndWriteoff();
	public abstract void setExtOfCompromiseAndWriteoff(String extOfCompromiseAndWriteoff);
	public abstract String getIsCibilStatusClean();
	public abstract void setIsCibilStatusClean(String isCibilStatusClean);
	public abstract String getDetailsOfCleanCibil();
	public abstract void setDetailsOfCleanCibil(String detailsOfCleanCibil);
	  //End:Uma Khot:CRI Field addition enhancement
	
	//Uma Khot:Cam upload and Dp field calculation CR
	public abstract String getDpSharePercent();

	public abstract void setDpSharePercent(String dpSharePercent);
	
	public abstract String getExceptionalCases();
	public abstract void setExceptionalCases(String exceptionalCases);
	
}