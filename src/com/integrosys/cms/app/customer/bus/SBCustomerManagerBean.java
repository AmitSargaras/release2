/*
* Copyright Integro Technologies Pte Ltd
* $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/SBCustomerManagerBean.java,v 1.17 2006/08/01 02:57:37 jzhai Exp $
*/
package com.integrosys.cms.app.customer.bus;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxControllerException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria;
import com.integrosys.cms.app.cci.bus.EBCCICounterpartyDetailsHome;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.OBCCICounterpartyDetails;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.trx.CustomerTrxControllerFactory;
import com.integrosys.cms.app.customer.trx.ICMSCustomerTrxValue;
import com.integrosys.cms.app.customer.trx.OBCMSCustomerTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;
import com.integrosys.cms.host.eai.customer.EAICustomerHelper;
import com.integrosys.cms.host.eai.customer.SearchDetailResult;
import com.integrosys.cms.host.eai.customer.SearchHeader;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;

/**
 * This session bean provides the implementation of
 * the AbstractCAManager, wrapped in an EJB mechanism.
 *
 * @author $Author: jzhai $
 * @version $Revision: 1.17 $
 * @since $Date: 2006/08/01 02:57:37 $
 *        Tag: $Name:  $
 */
public class SBCustomerManagerBean implements javax.ejb.SessionBean {
    /**
     * SessionContext object
     */
    private SessionContext _context = null;

    /**
     * Default Constructor
     */
    public SBCustomerManagerBean() {
    }

    /**
     * Create a new Customer
     *
     * @param obj is the ICMSCustomer to be created
     * @return ICMSCustomer containing the newly created customer records
     * @throws CustomerException on errors
     */
    public ICMSCustomer createCustomer(ICMSCustomer obj) throws CustomerException {
        try {
            if (null == obj) {
                throw new CustomerException("ICMSCustomer is null!");
            }
            long customerID = obj.getCustomerID();
            DefaultLogger.debug(this, "Creating for CustomerID: " + customerID);

            //create customer
            EBCMSCustomerHome custHome = getEBHomeCustomer();
            
            DefaultLogger.debug(this, "Instance of EBCMSCustomerHome --------- " + custHome); 
            
            /**
             *  Following statement added to make initial status of customer as Active
             */
           // obj.setStatus("ACTIVE");		// Sandeep Shinde
            
            EBCMSCustomer custRem = custHome.create(obj);
            long verTime = custRem.getVersionTime();

            //create child dependencies with checking on version time
            custRem.createDependants(obj, verTime);

            //return customer
            return custRem.getValue(); 
            
        }
        catch (CustomerException e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw e;
        }
        catch (CreateException e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            DefaultLogger.error(this, "CreateException:::"+e.getCause()+":::"+e.getMessage());
            throw new CustomerException("Caught CreateException: " + e.toString());
        }
        catch (Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            DefaultLogger.error(this, "Exception:::"+e.getCause()+":::"+e.getMessage());
            throw new CustomerException("Caught Exception: " + e.toString());
        }
    }

    /**
     * Update Customer details
     *
     * @param obj is the ICMSCustomer to be updated
     * @return ICMSCustomer containing the newly updated scustomer records
     * @throws CustomerException on errors
     */
    
    /*		//Without MakerChecker So Commented

     public ICMSCustomer updateCustomer(ICMSCustomer obj) throws CustomerException {
        try {
            if (null == obj) {
                throw new CustomerException("ICMSCustomer is null!");
            }
            long customerID = obj.getCustomerID();
            DefaultLogger.debug(this, "------------ SBCustomerManagerBean : updateCustomer "+customerID);

            EBCMSCustomerHome custHome = getEBHomeCustomer();
            EBCMSCustomer custRem = custHome.findByPrimaryKey(new Long(customerID));
           
            // Added on 16-03-2011 By Sandeep Shinde Start
            
            ICMSCustomer entfromDb = (OBCMSCustomer) custRem.getValue();
            entfromDb.getLegalEntity().setLegalConstitution(obj.getCMSLegalEntity().getLegalConstitution());
            entfromDb.getLegalEntity().setLEID(obj.getCMSLegalEntity().getLEID());            
            entfromDb.getLegalEntity().setShortName(obj.getLegalEntity().getShortName());
            entfromDb.setDomicileCountry(obj.getDomicileCountry());
            IContact[] contactArrFromDB = entfromDb.getCMSLegalEntity().getRegisteredAddress();
            IContact[] contactArrNew = obj.getCMSLegalEntity().getRegisteredAddress();
            for(int i =0; i< contactArrFromDB.length;i++){
            	contactArrFromDB[i].setAddressLine1(contactArrNew[i].getAddressLine1());
            	contactArrFromDB[i].setAddressLine2(contactArrNew[i].getAddressLine2());
            	contactArrFromDB[i].setState(contactArrNew[i].getState());
            	contactArrFromDB[i].setPostalCode(contactArrNew[i].getPostalCode());
            	contactArrFromDB[i].setCity(contactArrNew[i].getCity());
//            	contactArrFromDB[i].setCountryCode(contactArrNew[i].getCountryCode());
            }
            
            entfromDb.getCMSLegalEntity().setRegisteredAddress(contactArrFromDB);
//            entfromDb.setOfficialAddresses(obj.getOfficialAddresses());
            custRem.setValue(entfromDb);		
            
            // Added on 16-03-2011 By Sandeep Shinde End                    
            
//            custRem.setValue(obj);	// Commented on 16-03-2011 By Sandeep Shinde 
            return custRem.getValue();
        }
        catch (CustomerException e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw e;
        }
        catch (FinderException e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new CustomerException("Caught FinderException", e);
        }
        catch (Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new CustomerException("Caught Exception", e);
        }
    }*/
    	/*	With MakerChecker	By Sandeep Shinde on 30-03-2011	*/
    
    public ICMSCustomer updateCustomer(ICMSCustomer obj) throws CustomerException {
    	 try {
             if (null == obj) {
                 throw new CustomerException("ICMSCustomer is null!");
             }
             long customerID = obj.getCustomerID();
             DefaultLogger.debug(this, "Creating for CustomerID: " + customerID);

             EBCMSCustomerHome custHome = getEBHomeCustomer();
             EBCMSCustomer custRem = custHome.findByPrimaryKey(new Long(customerID));
             
             custRem.setValue(obj);
             
             return custRem.getValue();
         }            
        catch (CustomerException e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw e;
        }       
        catch (Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new CustomerException("Caught Exception", e);
        }
    }

    /**
     * Delete Customer. This method does not delete the Legal Entity
     *
     * @param customerID is of type long
     * @throws CustomerException on errors
     */
    
    public void deleteCustomer(long customerID) throws CustomerException,RemoteException {
    	DefaultLogger.debug(this, "-------------- SBCustomerManagerBean ------------------ "+customerID);
        try {        	
            EBCMSCustomerHome custHome = getEBHomeCustomer();
            EBCMSCustomer custRem = custHome.findByPrimaryKey(new Long(customerID));
            
            /**
             * Start
             *  Following lines of code added by
             *  @author sandiip.shinde
             *  @since 21-03-2011  
             */
            
            ICMSCustomer entfromDb = (OBCMSCustomer) custRem.getValue();
            entfromDb.setStatus("INACTIVE");
            custRem.setValue(entfromDb);
            DefaultLogger.debug(this, "Customer SuccessFully Deleted");
            /** End  */
            
//            custRem.remove();		//	Commented by sandiip.shinde because of adding above code
        }
        catch (Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new CustomerException("Caught Exception", e);
        }
    }

    /**
     * Delete Legal Entity. This method does not cascade delete customers. If any customer
     * details still exist, the delete will fail.
     *
     * @param legalID is of type long
     * @throws CustomerException on errors
     */
    public void deleteLegalEntity(long legalID) throws CustomerException {
        try {
            EBCMSLegalEntityHome home = getEBHomeLegalEntity();
            EBCMSLegalEntity rem = home.findByPrimaryKey(new Long(legalID));
            rem.remove();
        }
        catch (Exception e) {
            _context.setRollbackOnly();
            e.printStackTrace();
            throw new CustomerException("Caught Exception", e);
        }
    }

    /**
     * Get a Customer given a customer's ID
     *
     * @param customerID is of type long
     * @return ICMSCustomer if it can be found, or null if the customer
     *         does not exist.
     * @throws CustomerException on errors
     */
    public ICMSCustomer getCustomer(long customerID) throws CustomerException {
        try {
            EBCMSCustomerHome custHome = getEBHomeCustomer();	//	 calling this.getEBHomeCustomer();
            EBCMSCustomer custRem = custHome.findByPrimaryKey(new Long(customerID));
            OBCMSCustomer cust = (OBCMSCustomer) custRem.getValue();

            //CMSSP-428: Co-Borrower showing as Non-Borrower
            boolean isCoBorrowerInd = false;
            if (cust.getNonBorrowerInd()) {
                CustomerDAO dao = new CustomerDAO();
                isCoBorrowerInd = dao.isCoBorrower(cust.getCustomerID());
            }
            cust.setCoBorrowerInd(isCoBorrowerInd);

            return cust;
        }
        catch (FinderException e) {
            throw new CustomerException("No customer exist with CustomerID: " + customerID, e);
        }
        catch (CustomerException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException("Caught Exception", e);
        }
    }


    /**
     * Get a Customer given a customer's CIF number, it will search locally in DB, if not exists, will enquire from
     * source system
     *
     * @param cifNumber is of type String
     * @return ICMSCustomer if it can be found, or null if the customer
     *         does not exist.
     * @throws CustomerException on errors
     */
    public ICMSCustomer getCustomerByCIFNumber(String cifNumber, String sourceSystemId) throws CustomerException {
        long customerID = ICMSConstant.LONG_INVALID_VALUE;
        try {
            EBCMSCustomerHome custHome = getEBHomeCustomer();
            customerID = custHome.getCustomerByCIFNumber(cifNumber, sourceSystemId);
            if (customerID != ICMSConstant.LONG_INVALID_VALUE) {
                EBCMSCustomer custRem = custHome.findByPrimaryKey(new Long(customerID));
                OBCMSCustomer cust = (OBCMSCustomer) custRem.getValue();
                return cust;
            } else {
                DefaultLogger.debug(this, "No customer exist with cifNumber: " + cifNumber + " or CustomerID: " + customerID
                        + " in Local DB, get from Source system.");
                DefaultLogger.debug(this, "Proceed to enquire from source=" + sourceSystemId);
                if(cifNumber!=null){
                EAICustomerHelper.getInstance().getCustomerByCIFNumber(cifNumber, sourceSystemId);
                }
                return null;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException("Caught Exception", e);
        }
    }

    
    public List getCustomerByCIFNumber(String cifNumber, String sourceSystemId, String partyName, String partyId) throws CustomerException {
      /*  long customerID = ICMSConstant.LONG_INVALID_VALUE;*/
        try {
           /* EBCMSCustomerHome custHome = getEBHomeCustomer();
            customerID = custHome.getCustomerByCIFNumber(cifNumber, sourceSystemId, partyName);
            if (customerID != ICMSConstant.LONG_INVALID_VALUE) {
                EBCMSCustomer custRem = custHome.findByPrimaryKey(new Long(customerID));
                OBCMSCustomer cust = (OBCMSCustomer) custRem.getValue();
                return cust;
            } else {
                DefaultLogger.debug(this, "No customer exist with cifNumber: " + cifNumber + " or CustomerID: " + customerID
                        + " in Local DB, get from Source system.");
                DefaultLogger.debug(this, "Proceed to enquire from source=" + sourceSystemId);
                EAICustomerHelper.getInstance().getCustomerByCIFNumber(cifNumber, sourceSystemId);
                return null;
            }*/
        	 EBCMSCustomerHome custHome = getEBHomeCustomer();
          List  cust = custHome.getCustomerByCIFNumber(cifNumber, sourceSystemId, partyName,partyId);
          return cust;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException("Caught Exception", e);
        }
    }
    /**
     * Get a Customer given a customer's CIF number, it will search locally in DB only
     * source system
     *
     * @param cifNumber is of type String
     * @return ICMSCustomer if it can be found, or null if the customer
     *         does not exist.
     * @throws CustomerException on errors
     */
    public ICMSCustomer getCustomerByCIFNumberFromDB(String cifNumber, String sourceSystemId) throws CustomerException {
        long customerID = ICMSConstant.LONG_INVALID_VALUE;
        try {
            EBCMSCustomerHome custHome = getEBHomeCustomer();
            customerID = custHome.getCustomerByCIFNumber(cifNumber, sourceSystemId);
            if (customerID != ICMSConstant.LONG_INVALID_VALUE) {
                EBCMSCustomer custRem = custHome.findByPrimaryKey(new Long(customerID));
                OBCMSCustomer cust = (OBCMSCustomer) custRem.getValue();
                return cust;
            } else {
                return null;
            }

        }
        catch (FinderException e) {
            DefaultLogger.debug(this, "No customer exist with cifNumber: " + cifNumber + " or CustomerID: " + customerID
                    + " in Local DB");
            return null;
        }
        catch (CustomerException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException("Caught Exception", e);
        }
    }

    public ArrayList getMBlistByCBleId(long leid) throws CustomerException {
        try {
            EBCMSCustomerHome custHome = getEBHomeCustomer();
            return custHome.getMBlistByCBleId(leid);
        }
        catch (SearchDAOException e) {
            throw new CustomerException(e);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException("Caught Exception", e);
        }


    }

    /**
     * Search customer
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException on errors
     */
    public SearchResult searchCustomer(CustomerSearchCriteria criteria) throws SearchDAOException {
        try {
            EBCMSCustomerHome custHome = getEBHomeCustomer();
            SearchResult sr = custHome.searchCustomer(criteria);
            if (criteria.getCheckDAP() && criteria.getLimits() != null) {
                ILimitProxy proxy = LimitProxyFactory.getProxy();
                Iterator i = sr.getResultList().iterator();
                while (i.hasNext()) {
                    ICustomerSearchResult cust = (ICustomerSearchResult) i.next();
                    if (!cust.getIsDAPError()) {
                        ILimitProfile lp = proxy.getLimitProfile(cust.getLimitProfile().getLimitProfileID());
                        cust.setLimitProfile(lp);
                        cust.setCustomer(getCustomer(lp.getCustomerID()));
                    }
                }
            }else{
            	if(sr!=null){
            	  Iterator i = sr.getResultList().iterator();
                  while (i.hasNext()) {
                      ICustomerSearchResult cust = (ICustomerSearchResult) i.next();
                     
                          
                          cust.setCustomer(getCustomerByCIFNumber(cust.getLegalReference(), ""));
                      
                  }
            	}
            }
            return sr;
        }
        catch (SearchDAOException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new SearchDAOException("Caught Exception", e);
        }
    }
    
    public SearchResult searchCustomerImageUpload(CustomerSearchCriteria criteria) throws SearchDAOException {
        try {
            EBCMSCustomerHome custHome = getEBHomeCustomer(); 
            SearchResult sr = custHome.searchCustomerImageUpload(criteria);
            if (criteria.getCheckDAP() && criteria.getLimits() != null) {
                ILimitProxy proxy = LimitProxyFactory.getProxy();
                Iterator i = sr.getResultList().iterator();
                while (i.hasNext()) {
                    ICustomerSearchResult cust = (ICustomerSearchResult) i.next();
                    if (!cust.getIsDAPError()) {
                        ILimitProfile lp = proxy.getLimitProfile(cust.getLimitProfile().getLimitProfileID());
                        cust.setLimitProfile(lp);
                        cust.setCustomer(getCustomer(lp.getCustomerID()));
                    }
                }
            }else{
            	if(sr!=null){
            	  Iterator i = sr.getResultList().iterator();
                  while (i.hasNext()) {
                      ICustomerSearchResult cust = (ICustomerSearchResult) i.next();
                     
                          
                          cust.setCustomer(getCustomerByCIFNumber(cust.getLegalReference(), ""));
                      
                  }
            	}
            }
            return sr;
        }
        catch (SearchDAOException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new SearchDAOException("Caught Exception", e);
        }
    }

    /**
     * Search customer Information
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException on errors
     */
    public SearchResult searchCustomerInfoOnly(CustomerSearchCriteria criteria) throws SearchDAOException {
        try {
            EBCMSCustomerHome custHome = getEBHomeCustomer();
            SearchResult sr = custHome.searchCustomerInfoOnly(criteria);
            if (criteria.getCheckDAP() && criteria.getLimits() != null) {
                ILimitProxy proxy = LimitProxyFactory.getProxy();
                Iterator i = sr.getResultList().iterator();
                while (i.hasNext()) {
                    ICustomerSearchResult cust = (ICustomerSearchResult) i.next();
                    if (!cust.getIsDAPError()) {
                        ILimitProfile lp = proxy.getLimitProfile(cust.getLimitProfile().getLimitProfileID());
                        cust.setLimitProfile(lp);
                        cust.setCustomer(getCustomer(lp.getCustomerID()));
                    }
                }
            }
            return sr;
        }
        catch (SearchDAOException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new SearchDAOException("Caught Exception", e);
        }
    }

    /**
     * Search Group
     *
     * @param criteria is of type GroupSearchCriteria
     * @return SearchResult containing IGroupSearchResult objects
     * @throws SearchDAOException on errors
     */
    public SearchResult searchGroup(GroupSearchCriteria criteria) throws SearchDAOException {
        try {
            return new GroupDAO().searchGroup(criteria);
        }
        catch (SearchDAOException e) {
            throw e;
        }
    }

    /**
     * Retrieve the CMS Customer ID, given the SCI LE ID and SCI SubProfile ID
     *
     * @return long
     * @throws SearchDAOException if no records found
     * @throws CustomerException  on errors
     */
    public long searchCustomerID(long leid, long subProfileID) throws CustomerException, SearchDAOException {
        try {
            EBCMSCustomerHome custHome = getEBHomeCustomer();
            return custHome.searchCustomerID(leid, subProfileID);
        }
        catch (SearchDAOException e) {
            throw e;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new CustomerException("Caught Exception", e);
        }
    }

    /**
     * Get map of mailing details for a list of limit profile IDs.
     * One official address per limit profile ID if available.
     *
     * @param sciLimitProfileIDList - List
     * @return Map - (limitProfileID, OBCustomerMailingDetails)
     * @throws SearchDAOException, RemoteException on errors
     */
    public Map getCustomerMailingDetails(List sciLimitProfileIDList) throws SearchDAOException {
        try {
            return CustomerDAOFactory.getDAO().getCustomerMailingDetails(sciLimitProfileIDList);
        }
        catch (SearchDAOException e) {
            throw e;
        }
    }


    /**
     * Get map of fam code and customer name by a list of (le_id/lsp_id)
     *
     * @param sciLimitProfileIDList - List
     * @return Map - (limitProfileID, array of customername, famcode)
     * @throws SearchDAOException, RemoteException on errors
     */
    public Map getFamcodeCustNameByCustomer(List sciLimitProfileIDList) throws SearchDAOException {
        try {
            return CustomerDAOFactory.getDAO().getFamcodeCustNameByCustomer(sciLimitProfileIDList);
        }
        catch (SearchDAOException e) {
            throw e;
        }
    }


    //************* Private Methods **************
    /**
     * Method to get the EBHome for Customer
     *
     * @return EBCMSCustomerHome
     * @throws CustomerException on error
     */
    	// Scope of Method have be changed to protected from private by Sandeep Shinde
    
    protected EBCMSCustomerHome getEBHomeCustomer() throws CustomerException {
        EBCMSCustomerHome home = (EBCMSCustomerHome) BeanController.getEJBHome(
                ICMSJNDIConstant.EB_CUSTOMER_JNDI, EBCMSCustomerHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new CustomerException("EBCMSCustomerHome is null!");
        }
    }

    /**
     * Method to get the EBHome for Customer
     *
     * @return EBCMSCustomerHome
     * @throws CustomerException on error
     */
    private EBCCICounterpartyDetailsHome getEBCCICounterpartyDetailsHome()
            throws CustomerException {
        EBCCICounterpartyDetailsHome home = (EBCCICounterpartyDetailsHome) BeanController
                .getEJBHome(ICMSJNDIConstant.EB_CCI_COUNTERPARTY_DETAILS_JNDI,
                        EBCCICounterpartyDetailsHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new CustomerException("EBCCICounterpartyDetailsHome is null!");
        }
    }

    /**
     * Method to get EB Local Home for CMSLegalEntity
     *
     * @return EBCMSLegalEntityLocalHome
     * @throws CustomerException on errors
     */
    private EBCMSLegalEntityHome getEBHomeLegalEntity() throws CustomerException {
        EBCMSLegalEntityHome home = (EBCMSLegalEntityHome) BeanController.getEJBHome(
                ICMSJNDIConstant.EB_LEGAL_ENTITY_JNDI, EBCMSLegalEntityHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new CustomerException("EBCMSLegalEntityHome is null!");
        }
    }
    //************* EJB Methods *****************

    /* EJB Methods */

    /**
     * Called by the container to create a session bean instance. Its parameters typically
     * contain the information the client uses to customize the bean instance for its use.
     * It requires a matching pair in the bean class and its home interface.
     */
    public void ejbCreate() {
    }

    /**
     * A container invokes this method before it ends the life of the session object. This
     * happens as a result of a client's invoking a remove operation, or when a container
     * decides to terminate the session object after a timeout. This method is called with
     * no transaction context.
     */
    public void ejbRemove() {

    }

    /**
     * The activate method is called when the instance is activated from its 'passive' state.
     * The instance should acquire any resource that it has released earlier in the ejbPassivate()
     * method. This method is called with no transaction context.
     */
    public void ejbActivate() {

    }

    /**
     * The passivate method is called before the instance enters the 'passive' state. The
     * instance should release any resources that it can re-acquire later in the ejbActivate()
     * method. After the passivate method completes, the instance must be in a state that
     * allows the container to use the Java Serialization protocol to externalize and store
     * away the instance's state. This method is called with no transaction context.
     */
    public void ejbPassivate() {

    }

    /**
     * Set the associated session context. The container calls this method after the instance
     * creation. The enterprise Bean instance should store the reference to the context
     * object in an instance variable. This method is called with no transaction context.
     */
    public void setSessionContext(javax.ejb.SessionContext sc) {
        _context = sc;
    }

    /**
     * Search customer
     *
     * @param criteria is of type CustomerSearchCriteria
     * @return SearchResult containing ICustomerSearchResult objects
     * @throws SearchDAOException on errors
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria)
            throws SearchDAOException {
        try {
            EBCMSCustomerHome custHome = getEBHomeCustomer();
            SearchResult sr = custHome.searchCCICustomer(criteria);
            if (criteria.getCheckDAP() && criteria.getLimits() != null) {
                ILimitProxy proxy = LimitProxyFactory.getProxy();
                Iterator i = sr.getResultList().iterator();
                while (i.hasNext()) {
                    ICustomerSearchResult cust = (ICustomerSearchResult) i
                            .next();
                    if (!cust.getIsDAPError()) {
                        ILimitProfile lp = proxy.getLimitProfile(cust
                                .getLimitProfile().getLimitProfileID());
                        cust.setLimitProfile(lp);
                        cust.setCustomer(getCustomer(lp.getCustomerID()));
                    }
                }
            }
            return sr;
        } catch (SearchDAOException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SearchDAOException("Caught Exception", e);
        }
    }

    /**
     * @param groupCCINo
     * @return
     * @throws SearchDAOException
     */
    public ICCICounterpartyDetails getCCICounterpartyDetails(String groupCCINo)
            throws SearchDAOException {

        ICCICounterpartyDetails ob = new OBCCICounterpartyDetails();

        try {
            if (null != groupCCINo && !"".equals(groupCCINo)) {
                EBCCICounterpartyDetailsHome home = getEBCCICounterpartyDetailsHome();
                Long ccigroupCCINo = new Long(groupCCINo);
                ob = home.getCCICounterpartyDetails(groupCCINo);
            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new SearchDAOException("Caught Exception", e);
        }
        return ob;
    }

    /**
     * To retrieve Main Profile By CIFID , CIF Source , if record not found ,
     * create dummy record
     *
     * @param cifId
     * @param cifSource
     * @return
     * @throws SearchDAOException
     * @throws RemoteException
     * @ xdeprecated
     */
    public MainProfile getMainProfileOrCreateDummy(String cifId,
                                                   String cifSource, MainProfile mirrorMainProfile) throws SearchDAOException,
            RemoteException {
//		try {
//
//			CastorDb cdb = new CastorDb(false);
//
//
//			return EAICustomerHelper.getInstance().getMainProfileOrCreateDummy(
//					cdb, cifId, cifSource, mirrorMainProfile);
//
//		} catch (Exception e) {
//			throw new SearchDAOException("Caught Exception", e);
//		}
        return null;
    }

    public SearchHeader getSearchCustomerMultipleHeader(String msgRefNo)
            throws Exception {

//		return EAICustomerHelper.getInstance().getSearchCustomerMultipleHeader(
//				msgRefNo);
        return null;
    }

    public SearchDetailResult[] getSearchCustomerMultipleResults(String msgRefNo)
            throws Exception {

//		return EAICustomerHelper.getInstance()
//				.getSearchCustomerMultipleResults(msgRefNo);
		return null;
    }     
    
    

    /**
     * Method Added by Sandeep Shinde on 18-03-2011 so as to make Customer Status INACTIVE
     * @param aCMSCustomer
     * @return ICMSCustomer
     * @throws CustomerException
     * @throws RemoteException
     */
    public ICMSCustomerTrxValue getCustomerTrxValue(long id) throws CustomerException, RemoteException{
    	try {
			if (id == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				throw new CustomerException("Invalid CustomerId");
			}
		ICMSCustomerTrxValue trxValue = new OBCMSCustomerTrxValue();
	    trxValue.setReferenceID(String.valueOf(id));
	    trxValue.setTransactionType(ICMSConstant.INSTANCE_CUSTOMER);
	    OBCMSTrxParameter param = new OBCMSTrxParameter();
	    param.setAction(ICMSConstant.ACTION_READ_CUSTOMER);        
	
		ITrxController controller = (new CustomerTrxControllerFactory()).getController(trxValue, param);
		ITrxResult result = controller.operate(trxValue, param);
		return (ICMSCustomerTrxValue) result.getTrxValue();
		} catch (TrxParameterException e) {			
			e.printStackTrace();
		} catch (TrxControllerException e) {			
			e.printStackTrace();
		} catch (TransactionException e) {			
			e.printStackTrace();
		}
		return null;        

    }
   
//    public void deleteCustomer(ICMSCustomer aCMSCustomer)throws CustomerException {
//		DefaultLogger.debug(this,"Inside SBCustomerProxyBean");
//		try {
//			SBCustomerManager mgr = getCustomerManager();
//			mgr.deleteCustomer(aCMSCustomer);
//		}
//		catch (CustomerException e) {
//			throw e;
//		}	
//	}
}