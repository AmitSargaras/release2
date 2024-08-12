package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import java.util.HashMap;
import java.util.Date;


public abstract class EBCCICounterpartyDetailsBean implements EntityBean, ICCICounterparty {


    private static final String[] EXCLUDE_METHOD = new String[]{"getGroupCCIMapID"};


    public abstract void setCMPGroupCCIMapID(Long cMPGroupCCIMapID);
    public abstract Long getCMPGroupCCIMapID();

    public abstract void setCMPGroupCCINoRef(Long groupCCINoRef);
    public abstract Long getCMPGroupCCINoRef();


    public abstract void setCMPGroupCCINo(Long cMPGroupCCINo);
    public abstract Long getCMPGroupCCINo();

    public abstract String getCMPDeletedInd();
    public abstract void setCMPDeletedInd(String cMPDeletedInd);

    public abstract void setCMPSubProfileID(Long cMPSubProfileID);
    public abstract Long getCMPSubProfileID();

    public abstract long getVersionTime();
    public abstract void setVersionTime(long version);

    public long getGroupCCINoRef() {
        if (getCMPGroupCCINoRef() != null) {
            return getCMPGroupCCINoRef().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }
    
    public void setGroupCCINoRef(long groupCCINoRef) {
        setCMPGroupCCINoRef(new Long(groupCCINoRef));
    }

    public long getGroupCCINo() {
        if (getCMPGroupCCINo() != null) {
            return getCMPGroupCCINo().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }
    public void setGroupCCINo(long cMPCCINo) {
        setCMPGroupCCINo(new Long(cMPCCINo));
    }

    public long getGroupCCIMapID() {
        if (getCMPGroupCCIMapID() != null) {
            return getCMPGroupCCIMapID().longValue();
        }
        return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
    }
     
    public void setGroupCCIMapID(long cMPGroupCCIMapID) {
        setCMPGroupCCIMapID(new Long(cMPGroupCCIMapID));
    }

    public void setSubProfileID(long limitProfileID) {
        setCMPSubProfileID(new Long(limitProfileID));
    }

     public long getSubProfileID() {
        if (getCMPSubProfileID() != null) {
            return getCMPSubProfileID().longValue();
        }
        return ICMSConstant.LONG_INVALID_VALUE;
    }

    public boolean getDeletedInd(){
        if(getCMPDeletedInd() != null && getCMPDeletedInd().equals(ICMSConstant.TRUE_VALUE)) {
            return true;
        }
        return false;
    }
    public void setDeletedInd(boolean deletedInd){
        if (deletedInd){
            setCMPDeletedInd(ICMSConstant.TRUE_VALUE);
            return;
        }
        setCMPDeletedInd(ICMSConstant.FALSE_VALUE);
    }

    //START HERE

    public Long ejbCreate(ICCICounterparty aICCICounterparty) throws CreateException {
        if (aICCICounterparty == null) {
            throw new CreateException("aICCICounterpartyDetails is null!");
        }
        long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
        OBCCICounterparty  obj  = (OBCCICounterparty) aICCICounterparty ;


        try {
            pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
            AccessorUtil.copyValue(aICCICounterparty, this, EXCLUDE_METHOD);
//            System.out.println("CCI >>>>> [EBCCICounterpartyDetailsBean] --> PK: " + pk);

            setGroupCCIMapID(pk);
            setVersionTime(VersionGenerator.getVersionNumber());

            return new Long(pk);

        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CreateException("Exception at ejbCreate: " + ex.toString());
        }
    }

    public ICCICounterparty getValue() throws CCICounterpartyDetailsException {
        ICCICounterparty value = new OBCCICounterparty();
        AccessorUtil.copyValue(this, value);
        setCustomerDetails(value)  ;

        return value;
    }

    public void setValue(ICCICounterparty aICCICounterparty) throws ConcurrentUpdateException, CCICounterpartyDetailsException {
        try {
            if (getVersionTime() != aICCICounterparty.getVersionTime()) {
                throw new ConcurrentUpdateException("Mismatch timestamp");
            }
            AccessorUtil.copyValue(aICCICounterparty, this, EXCLUDE_METHOD);

            setVersionTime(VersionGenerator.getVersionNumber());
        } catch (ConcurrentUpdateException ex) {
            _context.setRollbackOnly();
            throw ex;
        } catch (Exception ex) {
            _context.setRollbackOnly();
            throw new CCICounterpartyDetailsException("Exception in setValue: " + ex.toString());
        }
    }

    protected String getSequenceName() {
        return ICMSConstant.SEQUENCE_CMS_CCI_LE_MAP;
    }

    protected EBCCICounterpartyDetailsLocalHome getEBStockIndexFeedEntryLocalHome() throws CCICounterpartyDetailsException {

        EBCCICounterpartyDetailsLocalHome home = null;
        String ebHomeName = EBCCICounterpartyDetailsLocalHome.class.getName();
        String JNDIName = ICMSJNDIConstant.EB_CCI_COUNTERPARTY_DETAILS_JNDI;
        home = (EBCCICounterpartyDetailsLocalHome) BeanController.getEJBLocalHome(JNDIName, ebHomeName);

        if (home != null) {
            return home;
        }
        throw new CCICounterpartyDetailsException("EBCCICounterpartyDetailsBeanItemLocal is null!");
    }

    public ICCICounterparty setCustomerDetails(ICCICounterparty value) {

        ICCICounterparty customerDetails = null;
         try {
            customerDetails  = CCICustomerDAOFactory.getDAO().getCustomerDetails(value);
         }catch(Exception  e) {
             e.printStackTrace();
         }
        return customerDetails ;
       }

    public String getLmpLeID(){
        return null;
    }
    public void setLmpLeID(String str){
      // do nothing
    }

    // END HERE


    public void ejbPostCreate(ICCICounterparty aICCICounterparty) {
    }

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


    /**
     * The Entity Context
     */
    protected EntityContext _context = null;


    /**
     *  return  ICCICounterparty
     * @param groupCCINo
     * @return
     * @throws SearchDAOException
     */


     public  ICCICounterpartyDetails ejbHomeGetCCICounterpartyByGroupCCINo(long groupCCINo) throws SearchDAOException{

        try {
            return  CCICustomerDAOFactory.getDAO().getCCICounterpartyByGroupCCINo(groupCCINo);
         }catch(SearchDAOException  ex) {
            ex.printStackTrace();
        }
        return null;
     }

    /**
     * Return   ICCICounterpartyDetails with collection of  ICCICounterparty
     * @param groupCCINo
     * @return
     * @throws SearchDAOException
     */

    public  ICCICounterpartyDetails ejbHomeGetCCICounterpartyDetails(String groupCCINo) throws SearchDAOException{

            CounterpartySearchCriteria criteria = new CounterpartySearchCriteria() ;
            criteria.setGroupCCINo(groupCCINo);

           long   agroupCCINo =Long.parseLong(groupCCINo) ;

           try {
                return  CCICustomerDAOFactory.getDAO().getCCICounterpartyByGroupCCINo(agroupCCINo);
            }catch(SearchDAOException  ex) {
               ex.printStackTrace();
           }
            return null;
        }

    public String getLegalName() {  return null;}
    public void setLegalName(String str) {}


    public String getCustomerName(){  return null;}
    public void setCustomerName(String str){}


    public long getLegalID(){  return ICMSConstant.LONG_INVALID_VALUE;}
    public void setLegalID(long str){}


    public String getIdNO(){  return null;}
    public void setIdNO(String customerName){}


    public String getLeIDType(){  return null;}
    public void setLeIDType(String str){}


    public String getDeleteCustomerID(){  return null;}
    public void setDeleteCustomerID(String str){}

    public String getSourceID(){  return null;}
    public void setSourceID(String str){}


    public OBCustomerAddress getAddress(){return null;}
    public void setAddress(OBCustomerAddress address) {   }


    public Date getDob() { return null; }
    public void setDob(Date dob) {}

    public Date getIncorporationDate(){  return null; }
    public void setIncorporationDate(Date incorporationDate){};

    /**
     * Used in ListCounterpartyCommand  for
     *  search CCI customer and customer
     * @param criteria
     * @return
     * @throws SearchDAOException
     */
    public SearchResult ejbHomeSearchCCICustomer(CounterpartySearchCriteria criteria) throws SearchDAOException {
        ICCICustomerDAO dao = CCICustomerDAOFactory.getDAO(criteria.getCtx());
        return dao.searchCCICustomer(criteria);
    }

    /**
         * Used in ListCounterpartyCommand  for
         *  search CCI customer and customer
         * @param lmt_profile_id
         * @return
         * @throws SearchDAOException
         */
        public String ejbHomeSearchCustomer(long lmt_profile_id) throws SearchDAOException {
            ICCICustomerDAO dao = CCICustomerDAOFactory.getDAO();
            return dao.searchCustomer(lmt_profile_id);
        }


      public OBCustomerAddress ejbHomeGetCustomerAddress(ICCICounterparty value) throws SearchDAOException {
            ICCICustomerDAO dao = CCICustomerDAOFactory.getDAO();
             value = dao.getCustomerAddress(value);
            return value.getAddress();
        }


       /**
         * Used in CheckerApproveEditCounterpartyCommand  for
         *   to check if the customer is already in other cci
         * @param cciObj
         * @return
         * @throws SearchDAOException
         */
        public HashMap  ejbHomeIsExistCCICustomer(long groupCCINo, String[]  cciObj)  throws SearchDAOException {
            ICCICustomerDAO dao = CCICustomerDAOFactory.getDAO();
            return dao.isExistCCICustomer(groupCCINo, cciObj);
        }

}
