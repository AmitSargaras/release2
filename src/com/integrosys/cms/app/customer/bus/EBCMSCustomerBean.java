/*
* Copyright Integro Technologies Pte Ltd
* $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/EBCMSCustomerBean.java,v 1.22 2006/08/01 02:55:09 jzhai Exp $
*/
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.businfra.customer.ILegalEntity;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.cci.bus.CCICustomerDAOFactory;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.bus.ICCICustomerDAO;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

import javax.ejb.*;
import java.util.*;

/**
 * This entity bean represents the persistence for Customer details
 *
 * @author $Author: jzhai $
 * @version $Revision: 1.22 $
 * @since $Date: 2006/08/01 02:55:09 $
 *        Tag: $Name:  $
 */
public abstract class EBCMSCustomerBean implements EntityBean, ICMSCustomer {
    private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CUSTOMER;
    private static final String[] EXCLUDE_METHOD = new String[]{"getCustomerID", "getLEID"};

    /**
     * The Entity Context
     */
    protected EntityContext _context = null;

    /**
     * Default Constructor
     */
    public EBCMSCustomerBean() {
    }

    //************ Non-persistence method *************
    //Getters
    /**
     * Get the customer ID
     *
     * @return long
     */
    public long getCustomerID() {
        if (null != getCustomerPK()) {
            return getCustomerPK().longValue();
        } else {
            return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        }
    }

    /**
     * Get the Disclosure Agreement Indicator.
     *
     * @return boolean
     */
    public boolean getDisclosureAgreementInd() {
        String value = getDisclosureAgreementStr();
        if (null != value && value.equals(ICMSConstant.TRUE_VALUE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get treasury appropriateness indicator.
     *
     * @return boolean
     */
    public boolean getTreasuryAppInd() {
        String value = getTreasuryAppStr();
        if (null != value && value.equals(ICMSConstant.TRUE_VALUE)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Get the Legal Entity.
     *
     * @return ILegalEntity
     */
    public ILegalEntity getLegalEntity() {
        return null;
    }

    /**
     * Get the CMS Legal Entity.
     * Not implemented. see <code>retrieveCMSLegalEntity</code>
     *
     * @return null
     */
    public ICMSLegalEntity getCMSLegalEntity() {
        return null;
    }

    /**
     * Get Official Address List. The addresses should be sorted such
     * that the latest address is the element 0 of the array.
     * Not implemented. see <code>retrieveOfficialAddresses</code>
     *
     * @return null
     */
    public IContact[] getOfficialAddresses() {
        return null;
    }

   /* public ISystem[] getOtherSystem() {
        return null;
    }*/
    /**
     * Get all System X-Ref related to this customer
     * Not implemented. see <code>retrieveCustomerSysXRefs</code>
     *
     * @return null
     */
    public ICustomerSysXRef[] getCustomerSysXRefs() {
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
     * Get non borrower indicator
     *
     * @return boolean
     */
    public boolean getNonBorrowerInd() {
        String value = getNonBorrowerStr();
        if (null != value && value.equals(ICMSConstant.TRUE_VALUE)) {
            return true;
        } else {
            return false;
        }
    }

    //Setters
    /**
     * Set the customer ID
     *
     * @param value is of type long
     */
    public void setCustomerID(long value) {
        setCustomerPK(new Long(value));
    }

    /**
     * Set the CMS Legal Entity.
     * Not implemented. see <code>updateCMSLegalEntity</code>
     *
     * @param value is of type ICMSLegalEntity
     */
    public void setCMSLegalEntity(ICMSLegalEntity value) {
        //do nothing
    }

    /**
     * Set the Legal Entity.
     * Not implemented
     *
     * @param value is of type ILegalEntity
     */
    public void setLegalEntity(ILegalEntity value) {
        //do nothing
    }

    /**
     * Set Official Address List. The addresses should be sorted such
     * that the latest address is the element 0 of the array.
     * Not implemented. see <code>updateOfficialAddresses</code>
     *
     * @param value is of type IContact[]
     */
    public void setOfficialAddresses(IContact[] value) {
        //do nothing
    }
   /* public void setOtherSystem(ISystem[] value) {
        //do nothing
    }*/
    /**
     * Set all System X-Ref related to this customer
     * Not implemented. see <code>updateCustomerSysXRefs</code>
     *
     * @param value is of type ICustomerSysXRef[]
     */
    public void setCustomerSysXRefs(ICustomerSysXRef[] value) {
        //do nothing
    }
    
   

    /**
     * Set the Disclosure Agreement Indicator.
     *
     * @param value is of type boolean
     */
    public void setDisclosureAgreementInd(boolean value) {
        if (true == value) {
            setDisclosureAgreementStr(ICMSConstant.TRUE_VALUE);
        } else {
            setDisclosureAgreementStr(ICMSConstant.FALSE_VALUE);
        }
    }

    /**
     * Set treasury appropriateness indicator.
     *
     * @param value is of type boolean
     */
    public void setTreasuryAppInd(boolean value) {
        if (true == value) {
            setTreasuryAppStr(ICMSConstant.TRUE_VALUE);
        } else {
            setTreasuryAppStr(ICMSConstant.FALSE_VALUE);
        }
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
        } else {
            setOriginatingCountry(null);
            setOriginatingOrganisation(null);
        }
    }

    /**
     * Set the non-borrower indicator
     *
     * @param value is of type boolean
     */
    public void setNonBorrowerInd(boolean value) {
        if (true == value) {
            setNonBorrowerStr(ICMSConstant.TRUE_VALUE);
        } else {
            setNonBorrowerStr(ICMSConstant.FALSE_VALUE);
        }
    }

    //************** CMR methods ***************
    //Getters
    /**
     * Get all Official Addresses
     *
     * @return Collection of EBContactLocal objects
     */
    public abstract Collection getCMROfficialAddresses();
    
   /* public abstract Collection getCMROtherSystem();*/
    /**
     * Get All Customer System Cross References
     *
     * @return Collection of EBCustomerSysXRef objects
     */
   // public abstract Collection getCMRCustomerSysXRefs();
    /**
     * Get Legal Entity
     *
     * @return EBCMSLegalEntityLocal
     */
    public abstract EBCMSLegalEntityLocal getCMRLegalEntity();

    //Setters
    /**
     * Set all Official Addresses
     *
     * @param value is of type Collection of EBContactLocal objects
     */
    public abstract void setCMROfficialAddresses(Collection value);
    
    /*public abstract void setCMROtherSystem(Collection value);*/
    /**
     * Set All Customer System Cross References
     *
     * @param value is of type Collection of EBCustomerSysXRef objects
     */
    //public abstract void setCMRCustomerSysXRefs(Collection value);
    /**
     * Set Legal Entity
     *
     * @param value is of type EBCMSLegalEntityLocal
     */
    public abstract void setCMRLegalEntity(EBCMSLegalEntityLocal value);

    //********************** Abstract Methods **********************
    //Getters
    /**
     * Get the customer pk
     *
     * @return Long
     */
    public abstract Long getCustomerPK();

    /**
     * Get the LE fk
     *
     * @return Long
     */
    public abstract Long getLEFK();

    /**
     * Get the Disclosure Agreement Indicator.
     *
     * @return String
     */
    public abstract String getDisclosureAgreementStr();

    /**
     * Get treasury appropriateness indicator.
     *
     * @return String
     */
    public abstract String getTreasuryAppStr();

    /**
     * Get the originating country
     *
     * @return String
     */
    public abstract String getOriginatingCountry();

    /**
     * Get the originating organisation
     *
     * @return String
     */
    public abstract String getOriginatingOrganisation();

    /**
     * Get non-borrower indicator
     *
     * @return String
     */
    public abstract String getNonBorrowerStr();

    //Setters
    /**
     * Set the customer pk
     *
     * @param value is of type Long
     */
    public abstract void setCustomerPK(Long value);

    /**
     * Set the LE FK
     *
     * @param value is of the Long
     */
    public abstract void setLEFK(Long value);

    /**
     * Set the Disclosure Agreement Indicator.
     *
     * @param value is of type String
     */
    public abstract void setDisclosureAgreementStr(String value);

    /**
     * Set treasury appropriateness indicator.
     *
     * @param value is of type String
     */
    public abstract void setTreasuryAppStr(String Value);

    /**
     * Set the originating country
     *
     * @param value is of type String
     */
    public abstract void setOriginatingCountry(String value);

    /**
     * Set the originating organisation
     *
     * @param value is of type String
     */
    public abstract void setOriginatingOrganisation(String value);

    /**
     * Set the non-borrower indicator
     *
     * @param value is of type String
     */
    public abstract void setNonBorrowerStr(String value);

    public abstract long getVersionTime();
    //************************ ejbCreate methods ********************

    /**
     * Create a Customer
     *
     * @param value is the ICMSCustomer object
     * @return Long the primary key
     */
    public Long ejbCreate(ICMSCustomer value) throws CreateException {
        if (null == value) {
            throw new CreateException("ICMSCustomer is null!");
        }
        try {
            long pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
            /*
               long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
               if(value.getCustomerID() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
                   pk = Long.parseLong((new SequenceManager()).getSeqNum(SEQUENCE_NAME, true));
               }
               else {
                   pk = value.getCustomerID();
               }
               */
            DefaultLogger.debug(this, "Creating Customer with ID: " + pk);

            setCustomerID(pk);

            AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);

            setVersionTime(VersionGenerator.getVersionNumber());
            return new Long(pk);
        }catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.error(this, "Exception >>>> "+e.getCause()+":::"+e.getMessage());
            throw new CreateException("Unknown Exception Caught: " + e.toString());
        }
    }

    /**
     * Create a Customer
     *
     * @param value is the ICMSCustomer object
     */
    public void ejbPostCreate(ICMSCustomer value) throws CreateException {
        /*
          try {
              updateDependants(value);
          }
          catch(CustomerException e) {
              e.printStackTrace();
              throw new CreateException("Caught CustomerException: " + e.toString()):
          }
          */
        //do nothing
    }

    /**
     * Method to get an object representation from persistance
     *
     * @return ICMSCustomer
     * @throws CustomerException on error
     */
    public ICMSCustomer getValue() throws CustomerException {
        try {
            OBCMSCustomer value = new OBCMSCustomer();
            AccessorUtil.copyValue(this, value);
            value.setCMSLegalEntity(retrieveCMSLegalEntity());
            value.setOfficialAddresses(retrieveOfficialAddresses());
            //value.setCustomerSysXRefs(retrieveCustomerSysXRefs());

            return value;
        }
        catch (Exception e) {
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
     * @param value is of type ICMSCustomer
     * @throws ConcurrentUpdateException if version mismatch occurs
     * @throws CustomerException         on error
     */
    public void setValue(ICMSCustomer value) throws CustomerException, ConcurrentUpdateException {
        long beanVer = value.getVersionTime();
        long currentVer = getVersionTime();
        /*Comment on 15-03-2011 By Sandeep Shinde due to error while updating the record.
        Occurs due to cersion time Mismatch.*/
        /*if (beanVer != currentVer) {
            throw new ConcurrentUpdateException("Version mismatch!");
        }*/
        try {
            AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
            updateDependants(value);
            setVersionTime(VersionGenerator.getVersionNumber());
        }
        catch (Exception e) {
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
     * @param value   is of type ICMSCustomer
     * @param verTime is the version time to be compared against the beans' version
     * @throws CustomerException, ConcurrentUpdateException on error
     */
    public void createDependants(ICMSCustomer value, long verTime) throws CustomerException, ConcurrentUpdateException {
        if (verTime != getVersionTime()) {
            throw new ConcurrentUpdateException("Version mismatched!");
        } else {
            updateDependants(value);
        }
    }

    //************************** Private methods ***************************
    /**
     * Method to update child dependants
     */
    private void updateDependants(ICMSCustomer value) throws CustomerException {
        try {
            if (null == value) {
                throw new CustomerException("ICMSCustomer is null!");
            }
            //Official Address
            updateOfficialAddresses(value.getOfficialAddresses());
            //Customer X Ref
            //updateCustomerSysXRefs(value.getCustomerSysXRefs());

            //Legal Entity
            ICMSLegalEntity ent = value.getCMSLegalEntity();
            if (null != ent) {
                EBCMSLegalEntityLocalHome home = getEBLocalHomeLegalEntity();

                try {
                    EBCMSLegalEntityLocal local = home.findByPrimaryKey(new Long(ent.getLEID()));
                    // Added on 15-03-2011 By Sandeep Shinde Start
                    
                    ICMSLegalEntity entfromDb = local.getValue();
                    
                    entfromDb.setLegalConstitution(ent.getLegalConstitution());
                    entfromDb.setRegisteredAddress(ent.getRegisteredAddress());
                    //added by bharat waghela for customer start
                    entfromDb.setOtherSystem(ent.getOtherSystem());    
                    entfromDb.setBankList(ent.getBankList());
                    entfromDb.setDirector(ent.getDirector());
                    entfromDb.setVendor(ent.getVendor());
                    entfromDb.setCoBorrowerDetails(ent.getCoBorrowerDetails());
                    entfromDb.setSublineParty(ent.getSublineParty());               
                    entfromDb.setCriList(ent.getCriList()); //Shiv 290911
                    entfromDb.setCriFacList(ent.getCriFacList()); //Shiv 290911
                    entfromDb.setShortName(ent.getShortName());
                    entfromDb.setLegalName(ent.getShortName());
                    entfromDb.setMainBranch(ent.getMainBranch());
                    entfromDb.setBranchCode(ent.getBranchCode());
                    entfromDb.setCycle(ent.getCycle());
                    entfromDb.setPartyGroupName(ent.getPartyGroupName());
                    entfromDb.setRelationshipMgrEmpCode(ent.getRelationshipMgrEmpCode());
                    entfromDb.setRelationshipMgr(ent.getRelationshipMgr());
                    entfromDb.setRmRegion(ent.getRmRegion());
                    entfromDb.setEntity(ent.getEntity());
                    entfromDb.setRbiIndustryCode(ent.getRbiIndustryCode());
                    entfromDb.setCinLlpin(ent.getCinLlpin());
                    entfromDb.setAadharNumber(ent.getAadharNumber());
                    entfromDb.setIndustryName(ent.getIndustryName());
                    entfromDb.setPan(ent.getPan());
                    entfromDb.setSubLine(ent.getSubLine());
               		entfromDb.setTotalFundedLimit(ent.getTotalFundedLimit());
            		entfromDb.setTotalNonFundedLimit(ent.getTotalNonFundedLimit());
            		entfromDb.setFundedSharePercent(ent.getFundedSharePercent());
            		entfromDb.setNonFundedSharePercent(ent.getNonFundedSharePercent());
            		entfromDb.setMemoExposure(ent.getMemoExposure());
            		entfromDb.setTotalSanctionedLimit(ent.getTotalSanctionedLimit());
            		entfromDb.setMpbf(ent.getMpbf());
            		entfromDb.setFundedShareLimit(ent.getFundedShareLimit());
            		entfromDb.setNonFundedShareLimit(ent.getNonFundedShareLimit());
            		entfromDb.setFundedIncreDecre(ent.getFundedIncreDecre());
            		entfromDb.setNonFundedIncreDecre(ent.getNonFundedIncreDecre());
            		entfromDb.setMemoExposIncreDecre(ent.getMemoExposIncreDecre());
            		entfromDb.setBorrowerDUNSNo(ent.getBorrowerDUNSNo());
            		entfromDb.setClassActivity1(ent.getClassActivity1());
            		entfromDb.setClassActivity2(ent.getClassActivity2());
            		entfromDb.setClassActivity3(ent.getClassActivity3());
            		entfromDb.setWillfulDefaultStatus(ent.getWillfulDefaultStatus());
            		entfromDb.setSuitFilledStatus(ent.getSuitFilledStatus());
            		entfromDb.setSuitAmount(ent.getSuitAmount());
            		entfromDb.setCurrency(ent.getCurrency());
            		entfromDb.setPartyConsent(ent.getPartyConsent());
            		entfromDb.setCustomerSegment(ent.getCustomerSegment());
            		entfromDb.setRegOffice(ent.getRegOffice());
            		entfromDb.setSuitReferenceNo(ent.getSuitReferenceNo());
            		
            		//Uma Khot:Cam upload and Dp field calculation CR	
            		entfromDb.setDpSharePercent(ent.getDpSharePercent());
           		    //Start:Uma Khot:CRI Field addition enhancement
            		entfromDb.setIsRBIWilfulDefaultersList(ent.getIsRBIWilfulDefaultersList());
            		entfromDb.setNameOfBank(ent.getNameOfBank());
            		entfromDb.setIsDirectorMoreThanOne(ent.getIsDirectorMoreThanOne());
            		entfromDb.setNameOfDirectorsAndCompany(ent.getNameOfDirectorsAndCompany());
            		entfromDb.setIsBorrDefaulterWithBank(ent.getIsBorrDefaulterWithBank());	
            		entfromDb.setDetailsOfDefault(ent.getDetailsOfDefault());
                	entfromDb.setExtOfCompromiseAndWriteoff(ent.getExtOfCompromiseAndWriteoff());
                	entfromDb.setIsCibilStatusClean(ent.getIsCibilStatusClean());
                	entfromDb.setDetailsOfCleanCibil(ent.getDetailsOfCleanCibil());
            		  //End:Uma Khot:CRI Field addition enhancement
            		//Santosh LEI CR 11/09/2018
                	entfromDb.setLeiCode(ent.getLeiCode());
                	entfromDb.setLeiExpDate(ent.getLeiExpDate());
                	entfromDb.setDeferLEI(ent.getDeferLEI());
                	//End Santosh

                    entfromDb.setUdfData(ent.getUdfData());
                    local.setValue(entfromDb);		
                    
                    // Added on 15-03-2011 By Sandeep Shinde End                    
                    setCMRLegalEntity(local);
                    
                }
                catch (FinderException e) {
                    //create legal entity if it's not found

                    DefaultLogger.debug(this, "FinderException caught. Creating Legal Entity now.");
                    EBCMSLegalEntityLocal local = home.create(ent);

                    long verTime = local.getVersionTime();
                    local.createDependants(ent, verTime);

                    setCMRLegalEntity(local);
                }
            } else {
                throw new CustomerException("Missing Legal Entity during creation!");
            }
        }
        catch (CustomerException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException("Caught Unknown Exception: " + e.toString());
        }
    }

    /**
     * Method to update official addresses
     */
    private void updateOfficialAddresses(IContact[] addresses) throws CustomerException {
        try {
            Collection c = getCMROfficialAddresses();

            if (null == addresses) {
                if (null == c || c.size() == 0) {
                    return; //nothing to do
                } else {
                    //delete all records
                    deleteOfficialAddresses(new ArrayList(c));
                }
            } else if (null == c || c.size() == 0) {
                //create new records
                createOfficialAddresses(Arrays.asList(addresses));
            } else {
                Iterator i = c.iterator();
                ArrayList createList = new ArrayList();    //contains list of OBs
                ArrayList deleteList = new ArrayList(); //contains list of local interfaces

                //identify identify records for delete or udpate first
                while (i.hasNext()) {
                    EBContactLocal local = (EBContactLocal) i.next();

                    long contactID = local.getContactID();
                    boolean update = false;

                    for (int j = 0; j < addresses.length; j++) {
                        IContact newOB = addresses[j];

                        if (newOB.getContactID() == contactID) {
                            //perform update
                            local.setValue(newOB);
                            update = true;
                            break;
                        }
                    }
                    if (!update) {
                        //add for delete
                        deleteList.add(local);
                    }
                }
                //next identify records for add
                for (int j = 0; j < addresses.length; j++) {
                    i = c.iterator();
                    IContact newOB = addresses[j];
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
                        //add for adding
                        createList.add(newOB);
                    }
                }
                deleteOfficialAddresses(deleteList);
                createOfficialAddresses(createList);
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

    /**
     * Method to add official addresses.
     */
    private void createOfficialAddresses(List createList) throws CustomerException {
        if (null == createList || createList.size() == 0) {
            return; //do nothing
        }
        Collection c = getCMROfficialAddresses();
        Iterator i = createList.iterator();
        try {
            EBContactLocalHome home = getEBLocalHomeOfficialAddress();
            while (i.hasNext()) {
                IContact ob = (IContact) i.next();
                DefaultLogger.debug(this, "Creating Contact ID: " + ob.getContactID());
                EBContactLocal local = home.create(getCustomerID(), ob);
                c.add(local);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (e instanceof CustomerException) {
                throw (CustomerException) e;
            } else {
                throw new CustomerException("Caught Exception: " + e.toString());
            }
        }
    }

    /**
     * Method to delete official addresses
     */
    private void deleteOfficialAddresses(List deleteList) throws CustomerException {
        if (null == deleteList || deleteList.size() == 0) {
            return; //do nothing
        }
        try {
            Collection c = getCMROfficialAddresses();
            Iterator i = deleteList.iterator();
            while (i.hasNext()) {
                EBContactLocal local = (EBContactLocal) i.next();
                c.remove(local);
                local.remove();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (e instanceof CustomerException) {
                throw (CustomerException) e;
            } else {
                throw new CustomerException("Caught Exception: " + e.toString());
            }
        }
    }

    /**
     * Method to retrieve official addresses
     */
    private IContact[] retrieveOfficialAddresses() throws CustomerException {
        try {
            Collection c = getCMROfficialAddresses();
            if (null == c || c.size() == 0) {
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
        }
        catch (Exception e) {
            e.printStackTrace();
            if (e instanceof CustomerException) {
                throw (CustomerException) e;
            } else {
                throw new CustomerException("Caught Exception: " + e.toString());
            }
        }
    }
    /**
     * Method to update customer sys ref
     */
    /*
    private void updateCustomerSysXRefs(ICustomerSysXRef[] refs) throws CustomerException {
        try {
            Collection c = getCMRCustomerSysXRefs();

            if(null == refs) {
                if(null == c || c.size() == 0) {
                    return; //nothing to do
                }
                else {
                    //delete all records
                    deleteCustomerSysXRefs(new ArrayList(c));
                }
            }
            else if(null == c || c.size() == 0) {
                //create new records
                createCustomerSysXRefs(Arrays.asList(refs));
            }
            else {
                Iterator i = c.iterator();
                ArrayList createList = new ArrayList();    //contains list of OBs
                ArrayList deleteList = new ArrayList(); //contains list of local interfaces

                //identify identify records for delete or udpate first
                while(i.hasNext()) {
                    EBCustomerSysXRefLocal local = (EBCustomerSysXRefLocal)i.next();

                    long refID = local.getXRefID();
                    boolean update = false;

                    for(int j=0; j<refs.length; j++) {
                        ICustomerSysXRef newOB = refs[j];

                        if(newOB.getXRefID() == refID) {
                            //perform update
                            local.setValue(newOB);
                            update = true;
                            break;
                        }
                    }
                    if(!update) {
                        //add for delete
                        deleteList.add(local);
                    }
                }
                //next identify records for add
                for(int j=0; j<refs.length; j++) {
                    i = c.iterator();
                    ICustomerSysXRef newOB = refs[j];
                    boolean found = false;

                    while(i.hasNext()) {
                        EBCustomerSysXRefLocal local = (EBCustomerSysXRefLocal)i.next();
                        long id = local.getXRefID();

                        if(newOB.getXRefID() == id) {
                            found = true;
                            break;
                        }
                    }
                    if(!found) {
                        //add for adding
                        createList.add(newOB);
                    }
                }
                deleteCustomerSysXRefs(deleteList);
                createCustomerSysXRefs(createList);
            }
        }
        catch(CustomerException e) {
            throw e;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new CustomerException("Caught Exception: " + e.toString());
        }
    }
    */

    /**
     * Method to add customer sys references
     */
    /*
    private void createCustomerSysXRefs(List createList) throws CustomerException {
        if(null == createList || createList.size() == 0) {
            return; //do nothing
        }
        Collection c = getCMRCustomerSysXRefs();
        Iterator i = createList.iterator();
        try {
            EBCustomerSysXRefLocalHome home = getEBLocalHomeSysRef();
            while(i.hasNext()) {
                ICustomerSysXRef ob = (ICustomerSysXRef)i.next();
                DefaultLogger.debug(this, "Creating Customer System Ref ID: " + ob.getXRefID());
                EBCustomerSysXRefLocal local = home.create(getCustomerID(), ob);
                c.add(local);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            if(e instanceof CustomerException) {
                throw (CustomerException)e;
            }
            else {
                throw new CustomerException("Caught Exception: " + e.toString());
            }
        }
    }
    */

    /**
     * Method to delete official addresses
     */
    /*
    private void deleteCustomerSysXRefs(List deleteList) throws CustomerException {
        if(null == deleteList || deleteList.size() == 0) {
            return; //do nothing
        }
        try {
            Collection c = getCMRCustomerSysXRefs();
            Iterator i = deleteList.iterator();
            while(i.hasNext()) {
                EBCustomerSysXRefLocal local = (EBCustomerSysXRefLocal)i.next();
                c.remove(local);
                local.remove();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            if(e instanceof CustomerException) {
                throw (CustomerException)e;
            }
            else {
                throw new CustomerException("Caught Exception: " + e.toString());
            }
        }
    }
    */

    /**
     * Method to retrieve customer system x-ref
     */
    /*
    private ICustomerSysXRef[] retrieveCustomerSysXRefs() throws CustomerException {
        try {
            Collection c = getCMRCustomerSysXRefs();
            if(null == c || c.size() == 0) {
                return null;
            }
            else {
                ArrayList aList = new ArrayList();
                Iterator i = c.iterator();
                while(i.hasNext()) {
                    EBCustomerSysXRefLocal local = (EBCustomerSysXRefLocal)i.next();
                    ICustomerSysXRef ob = local.getValue();
                    aList.add(ob);
                }

                return (ICustomerSysXRef[])aList.toArray(new ICustomerSysXRef[0]);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            if(e instanceof CustomerException) {
                throw (CustomerException)e;
            }
            else {
                throw new CustomerException("Caught Exception: " + e.toString());
            }
        }
    }
    */

    /**
     * Method to retrieve legal entity
     */
    private ICMSLegalEntity retrieveCMSLegalEntity() throws CustomerException {
        try {
            EBCMSLegalEntityLocal local = getCMRLegalEntity();
            if (null != local) {
                return local.getValue();
            } else {
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if (e instanceof CustomerException) {
                throw (CustomerException) e;
            } else {
                throw new CustomerException("Caught Exception: " + e.toString());
            }
        }
    }

    //************************ BeanController Methods **************
    /**
     * Method to get EB Local Home for CMSLegalEntity
     *
     * @return EBCMSLegalEntityLocalHome
     * @throws CustomerException on errors
     */
    protected EBCMSLegalEntityLocalHome getEBLocalHomeLegalEntity() throws CustomerException {
        EBCMSLegalEntityLocalHome home = (EBCMSLegalEntityLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_LEGAL_ENTITY_LOCAL_JNDI, EBCMSLegalEntityLocalHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new CustomerException("EBCMSLegalEntityLocal is null!");
        }
    }

    /**
     * Method to get the EBLocalHome for Official Address
     *
     * @return EBContactLocalHome
     * @throws CustomerException on errors
     */
    protected EBContactLocalHome getEBLocalHomeOfficialAddress() throws CustomerException {
        EBContactLocalHome home = (EBContactLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_OFF_ADDRESS_LOCAL_JNDI, EBContactLocalHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new CustomerException("EBContactLocalHome is null!");
        }
    }

    /**
     * Method to get the EBLocalHome for Customer Sys Ref
     *
     * @return EBCustomerSysXRefLocalHome
     * @throws CustomerException on errors
     */
    protected EBCustomerSysXRefLocalHome getEBLocalHomeSysRef() throws CustomerException {
        EBCustomerSysXRefLocalHome home = (EBCustomerSysXRefLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_CUSTOMER_SYS_REF_LOCAL_JNDI, EBCustomerSysXRefLocalHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new CustomerException("EBCustomerSysXRefLocalHome is null!");
        }
    }
    
    protected EBLineCovenantLocalHome getEBLocalHomeLineCovenant() throws CustomerException {
        EBLineCovenantLocalHome home = (EBLineCovenantLocalHome) BeanController.getEJBLocalHome(
                ICMSJNDIConstant.EB_LINE_COVENANT_LOCAL_JNDI, EBLineCovenantLocalHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new CustomerException("EBLineCovenantLocalHome is null!");
        }
    }

    // ************************ ejbHome methods *********************
    /**
     * Search customer
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException on errors
     */
    public SearchResult ejbHomeSearchCustomer(CustomerSearchCriteria criteria) throws SearchDAOException {
        ICustomerDAO dao = CustomerDAOFactory.getDAO(criteria.getCtx());
        return dao.searchCustomer(criteria);
    }
    
    public SearchResult ejbHomeSearchCustomerImageUpload(CustomerSearchCriteria criteria) throws SearchDAOException {
        ICustomerDAO dao = CustomerDAOFactory.getDAO(criteria.getCtx());
        return dao.searchCustomerImageUpload(criteria);
    }
 
    /**
     * Search customer
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException on errors
     */
    public SearchResult ejbHomeSearchCustomerInfoOnly(CustomerSearchCriteria criteria) throws SearchDAOException {
        ICustomerDAO dao = CustomerDAOFactory.getDAO(criteria.getCtx());
        return dao.searchCustomer(criteria);
    }

    /**
     * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
     *
     * @return long
     * @throws SearchDAOException if no records found
     * @throws CustomerException  on errors
     */
    public long ejbHomeSearchCustomerID(long leid, long subProfileID) throws CustomerException, SearchDAOException {
        ICustomerDAO dao = CustomerDAOFactory.getDAO();
        return dao.searchCustomerID(leid, subProfileID);
    }

    /**
     * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
     *
     * @return long
     * @throws SearchDAOException if no records found
     * @throws CustomerException  on errors
     */
    public long ejbHomeGetCustomerByCIFNumber(String cifNumber, String sourceId) throws CustomerException, SearchDAOException {
        ICustomerDAO dao = CustomerDAOFactory.getDAO();
        return dao.searchCustomerByCIFNumber(cifNumber, sourceId);
    }
    
    public List ejbHomeGetCustomerByCIFNumber(String cifNumber, String sourceId,String partyName, String partyId) throws CustomerException, SearchDAOException {
        ICustomerDAO dao = CustomerDAOFactory.getDAO();
        return dao.searchCustomerByCIFNumber(cifNumber, sourceId,partyName, partyId);
    }

    public ArrayList ejbHomeGetMBlistByCBleId(long leid) throws CustomerException, SearchDAOException {
        ICustomerDAO dao = CustomerDAOFactory.getDAO();
        return dao.getMBlistByCBleId(leid);
    }


    //************************************************************************
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
        //perform cascade delete

        Collection c = getCMROfficialAddresses();
        if (null != c) {
            Iterator i = c.iterator();
            while (i.hasNext()) {
                EBContactLocal local = (EBContactLocal) i.next();
                i.remove();    //remove this local interface from the collection
                local.remove();        //remove the data
            }
        }
        /*
          c = getCMRCustomerSysXRefs();
          if(null != c) {
              Iterator i = c.iterator();
              while(i.hasNext()) {
                  EBCustomerSysXRefLocal local = (EBCustomerSysXRefLocal)i.next();
                  i.remove();	//remove this local interface from the collection
                     local.remove();		//remove the data
              }
          }
          */
        //this bean does not update LegalEntity. It only reads LegalEntity
        /*
          EBCMSLegalEntityLocal local = getCMRLegalEntity();
          if(null != local) {
              local.remove();
              setCMRLegalEntity(null);
          }
          */
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
     * Search CCIcustomer
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException on errors
     */
    public SearchResult ejbHomeSearchCCICustomer(CounterpartySearchCriteria criteria) throws SearchDAOException {
        ICCICustomerDAO dao = CCICustomerDAOFactory.getDAO(criteria.getCtx());
         return dao.searchCCICustomer(criteria);
     }
    
    public abstract String getCustomerReference();

	public abstract String getDomicileCountry();

	public abstract String getDomicileCountryRegNumber();
	
	public abstract String getSwiftCode();

	public abstract String getCustomerOriginType();

	public abstract Date getDisclosureAgreementDate();

	public abstract String getLanguageCode();

	public abstract String getServiceLevelPriority();

	public abstract String getOperationStatus();

	public abstract Date getOperationEffectiveDate();
	
	public abstract String getCCCStatus();
	
	public abstract void setCustomerReference(String value);

	public abstract void setDomicileCountry(String value);

	public abstract void setDomicileCountryRegNumber(String value);

	public  abstract void setSwiftCode(String value);

	public abstract void setCustomerOriginType(String value);

	public abstract void setDisclosureAgreementDate(Date value);

	public abstract void setLanguageCode(String value);

	public abstract void setServiceLevelPriority(String value);

	public abstract void setOperationStatus(String value);

	public abstract void setOperationEffectiveDate(Date value);

	public abstract String getCustomerName();

	public abstract Date getCustomerRelationshipStartDate();
	
	public abstract Date getDateOfIncorporation();

	public abstract void setCustomerName(String value);

	public abstract void setCustomerRelationshipStartDate(Date value);
	
	public abstract void setDateOfIncorporation(Date value);

	public abstract void setCCCStatus(String aCCCStatus);
	
	public abstract void setVersionTime(long versionTime);
	
	/*
	 * added by sandiip.shinde
	 */
	
	public abstract String getStatus();

	public abstract void setStatus(String status);
	
	public abstract String getCifId();

	public abstract void setCifId(String status);
	
	//--------------------------------------added by bharat weblogic--------------------------------------
	
	public abstract  String getPartyGroupName() ;

	public abstract  void setPartyGroupName(String partyGroupName);

	public abstract  String getRelationshipMgrEmpCode();	
	
	public abstract  void setRelationshipMgrEmpCode(String relationshipMgrEmpCode);
	
	public abstract  String getRelationshipMgr();

	public abstract  void setRelationshipMgr(String relationshipMgr);

	public abstract  String getRmRegion(); 

	public abstract  void setRmRegion(String rmRegion);

	public abstract  String getCycle();

	public abstract  void setCycle(String cycle);

	public abstract String getEntity(); 

	public abstract  void setEntity(String entity); 

	public abstract  String getRbiIndustryCode(); 

	public abstract  void setRbiIndustryCode(String rbiIndustryCode);

	public abstract  String getIndustryName(); 

	public abstract  void setIndustryName(String industryName); 
	
	//Santosh LEI CR 11/09/2018
	public abstract String getLeiCode();
	public abstract void setLeiCode(String leiCode);
	public abstract Date getLeiExpDate();
	public abstract void setLeiExpDate(Date leiExpDate);
	public abstract char getIsLeiValidated();
	public abstract void setIsLeiValidated(char isLeiValidated);
	public abstract String getDeferLEI();
	public abstract void setDeferLEI(String deferLEI);
	public abstract char getLeiValGenParamFlag();
	public abstract void setLeiValGenParamFlag(char leiValGenParamFlag);
	public abstract Date getLastModifiedLei();
	public abstract void setLastModifiedLei(Date lastModifiedLei);
	//End Santosh

	public abstract  String getPan(); 

	public abstract  void setPan(String pan);

	public abstract  String getRegion(); 
	
	public abstract  void setRegion(String region);
	//------------------------------------------------------------------------------------------------------
	public abstract  String getSubLine(); 

	public abstract  void setSubLine(String subLine); 

	public abstract  String getBankingMethod(); 

	public abstract  void setBankingMethod(String bankingMethod);

	public abstract  String getTotalFundedLimit(); 

	public abstract  void setTotalFundedLimit(String totalFundedLimit);

	public abstract  String getTotalNonFundedLimit(); 

	public abstract  void setTotalNonFundedLimit(String totalNonFundedLimit);

	public abstract  String getFundedSharePercent(); 

	public abstract  void setFundedSharePercent(String fundedSharePercent); 

	public abstract  String getNonFundedSharePercent(); 

	public abstract  void setNonFundedSharePercent(String nonFundedSharePercent); 

	public abstract  String getMemoExposure(); 

	public abstract  void setMemoExposure(String memoExposure);

	public abstract  String getTotalSanctionedLimit();
	
	public abstract  void setTotalSanctionedLimit(String totalSanctionedLimit);

	public abstract  String getMpbf();

	public abstract  void setMpbf(String mpbf) ;

	public abstract  String getFundedShareLimit() ;

	public abstract  void setFundedShareLimit(String fundedShareLimit);

	public abstract  String getNonFundedShareLimit(); 

	public abstract  void setNonFundedShareLimit(String nonFundedShareLimit);
	
	public abstract String getFundedIncreDecre();

	public abstract void setFundedIncreDecre(String fundedIncreDecre);
	
	public abstract String getNonFundedIncreDecre();

	public abstract void setNonFundedIncreDecre(String nonFundedIncreDecre);
	
	public abstract String getMemoExposIncreDecre();

	public abstract void setMemoExposIncreDecre(String memoExposIncreDecre);
	//------------------------------------------------------end------------------------------------------
	
	public abstract  String getBorrowerDUNSNo();

	public abstract  void setBorrowerDUNSNo(String borrowerDUNSNo);

	public abstract  String getClassActivity1() ;

	public abstract  void setClassActivity1(String classActivity1) ;

	public abstract  String getClassActivity2();

	public abstract  void setClassActivity2(String classActivity2);

	public abstract  String getClassActivity3() ;

	public  abstract void setClassActivity3(String classActivity3) ;

	public abstract  String getWillfulDefaultStatus();

	public abstract  void setWillfulDefaultStatus(String willfulDefaultStatus) ;

	public abstract  String getSuitFilledStatus() ;

	public abstract  void setSuitFilledStatus(String suitFilledStatus);

	public abstract  Date getDateOfSuit() ;

	public abstract  void setDateOfSuit(Date dateOfSuit) ;

	public abstract  String getSuitAmount() ;

	public abstract  void setSuitAmount(String suitAmount);

	public abstract  String getSuitReferenceNo();

	public abstract  void setSuitReferenceNo(String suitReferenceNo);

	public abstract  Date getDateWillfulDefault() ;

	public abstract  void setDateWillfulDefault(Date dateWillfulDefault);

	public abstract  String getRegOfficeDUNSNo() ;

	public abstract  void setRegOfficeDUNSNo(String regOfficeDUNSNo) ;

	public abstract   String getPartyConsent();  

	public abstract   void setPartyConsent(String partyConsent);  
	
	public abstract String getRegOffice();  

	public abstract void setRegOffice(String regOffice) ; 
	
	public abstract String getMainBranch();

	public abstract void setMainBranch(String mainBranch);
	
	public abstract String getBranchCode() ;

	public abstract void setBranchCode(String branchCode); 

	public abstract  String getCurrency() ;

	public abstract  void setCurrency(String currency);
	
	public abstract  String getCustomerNameUpper(); 

	public abstract  void setCustomerNameUpper(String customerNameUpper);
	
	public abstract String getCustomerSegment();

	public abstract void setCustomerSegment(String customerSegment);
	
	//------------------------------------------------------------------------------------------------
	
	public abstract Date getLastModifiedOn();

	public abstract void setLastModifiedOn(Date lastModifiedOn);

	public abstract char getPanValGenParamFlagValue();

	public abstract void setPanValGenParamFlagValue(char panValGenParamFlagValue);

	public abstract char getForm6061checked();

	public abstract void setForm6061checked(char form6061checked);

	public abstract char getIsPanValidated();

	public abstract void setIsPanValidated(char isPanValidated);
	
	 //Start:Uma Khot:CRI Field addition enhancement
	public abstract String getIsRBIWilfulDefaultersList();
	public abstract void setIsRBIWilfulDefaultersList(String isRBIWilfulDefaulterlist);
	public abstract String getNameOfBank();
	public abstract void setNameOfBank(String nameOfBank);
	public abstract String getIsDirectorMoreThanOne();
	public abstract void setIsDirectorMoreThanOne(String isDirectorMoreThanOne);
	public abstract String getNameOfDirectorsAndCompany();
	public abstract void setNameOfDirectorsAndCompany(String nameOfDirectorsAndCompany);
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
	
	public abstract  String getCinLlpin() ;
	public abstract  void setCinLlpin(String cinLlpin);
	public abstract  String getAadharNumber() ;
	public abstract  void setAadharNumber(String aadharNumber);
	
	public abstract String getPartyNameAsPerPan();
	public abstract void setPartyNameAsPerPan(String partyNameAsPerPan);
	
	public abstract String getSanctionedAmtUpdatedFlag();
	public abstract void setSanctionedAmtUpdatedFlag(String sanctionedAmtUpdatedFlag);
	
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
	
	public String[] getRevisedEmailIdsArrayList() {
		return null;
	}
	public void setRevisedEmailIdsArrayList(String[] revisedEmailIdsArrayList) {}

}