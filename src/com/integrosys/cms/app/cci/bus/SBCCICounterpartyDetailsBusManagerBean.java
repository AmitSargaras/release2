package com.integrosys.cms.app.cci.bus;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.*;


public class SBCCICounterpartyDetailsBusManagerBean implements ICCICounterpartyDetailsBusManager, SessionBean {


    /**
     * @param details
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */

    public ICCICounterpartyDetails createCCICounterpartyDetails(ICCICounterpartyDetails details) throws CCICounterpartyDetailsException {

        List list = new ArrayList();
        //long groupCCINo  = ICMSConstant.LONG_INVALID_VALUE;
        long groupCCINoRef = getGroupCCIRefID();

        try {
            EBCCICounterpartyDetailsHome home = getEBCCICounterpartyDetailsHome();
            if (details != null) {
                ICCICounterparty[]   aICCICounterparty = details.getICCICounterparty();
                if (aICCICounterparty != null && aICCICounterparty.length > 0) {
                    for (int i = 0; i < aICCICounterparty.length; i++) {
                        ICCICounterparty obj = (ICCICounterparty) aICCICounterparty[i];
                        obj.setGroupCCINoRef(groupCCINoRef);
                        EBCCICounterpartyDetails eb = home.create(obj);
                        obj = eb.getValue();
                        // groupCCINo = eb.getGroupCCINo() ;
                        groupCCINoRef = eb.getGroupCCINoRef();
                        list.add(obj);
                    }

                }

                if (list.size() > 0) {
                    ICCICounterparty[]  iCCICounterpartyList = (ICCICounterparty[]) list.toArray(new ICCICounterparty[0]);
                    details.setICCICounterparty(iCCICounterpartyList);
                    // details.setGroupCCINo(groupCCINo);
                    details.setGroupCCINoRef(groupCCINoRef);
                }
            }


            return details;

        } catch (CreateException e) {
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("CreateException", e);
        } catch (RemoteException e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("RemoteException", e);
        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("Other Exception", e);
        }
    }



    /**
     * @param actualDetails
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */

    public ICCICounterpartyDetails updateCCICounterpartyDetails(ICCICounterpartyDetails actualDetails) throws CCICounterpartyDetailsException {

        try {
            this.updateCounterpartyDetails(actualDetails, false);
        } catch (Exception e) {
            DefaultLogger.error(this, "", e);
            rollback();
            throw new CCICounterpartyDetailsException("RemoteException caught! " + e.toString());

        }
        return actualDetails;
    }

    /**
     * @param actualDetails
     * @param isItemDeleted
     * @throws CCICounterpartyDetailsException
     *
     */
    protected void updateCounterpartyDetails(ICCICounterpartyDetails actualDetails, boolean isItemDeleted) throws CCICounterpartyDetailsException {

        ICCICounterparty[]   stagingList = null;
        ICCICounterparty[]   actualList = null;
        if (actualDetails != null) {
            actualList = actualDetails.getICCICounterparty();
        }

        try {

            // ICCICounterpartyDetails dbdetails = CCICustomerDAOFactory.getDAO().getCCICounterpartyByGroupCCINo(details.getGroupCCINo());
            // Collection coll = getCMRShareCheckList();
            ICCICounterpartyDetails stagingDetails = getStagingCCICounterpartyByGroupCCINoRef(actualDetails.getGroupCCINo(), actualDetails.getStagingGroupCCINoRef());
            if (stagingDetails != null) {
                stagingList = stagingDetails.getICCICounterparty();
            } else {
                DefaultLogger.debug(this,"stagingDetails is Exist");
            }

            /*if (newList == null) {
                   if (actualList == null || actualList.length == 0) {
                       return;
                   } else {
                       // delete  all  records
                       deleteICCICounterparty(Arrays.asList(actualList));
                   }
             } else if (isItemDeleted) {
                // delete  all  records
                  deleteICCICounterparty(Arrays.asList(actualList));
            } else if (actualList == null || actualList.length == 0) {
                  //create all new records in database
                  createICCICounterparty(Arrays.asList(newList),details.getGroupCCINoRef());
            }*/
            //else{

             int nofOfrecs =0;
                  nofOfrecs =(stagingList != null) ? stagingList.length:0 ;


            nofOfrecs =(actualList != null) ? actualList.length:0 ;


            ArrayList createList = new ArrayList();
            ArrayList deleteList = new ArrayList();
            ICCICounterparty actualObj = null;
            ICCICounterparty stagingOB = null;

            //identify identify records for delete
            if (stagingList != null && stagingList.length > 0) {
                for (int i = 0; i < stagingList.length; i++) {
                    stagingOB = stagingList[i];
                    String newLimitProfileID = stagingOB.getSubProfileID()+"" ;
                  if (stagingOB.getDeletedInd()){
                    if (actualList != null && actualList.length > 0) {
                          for (int ii = 0; ii < actualList.length; ii++) {
                                actualObj = actualList[ii];
                                String actLimitProfileID = actualObj.getSubProfileID()+"" ;
                               // System.out.println("->"+ actLimitProfileID+"="+newLimitProfileID+"<-");
                                if (newLimitProfileID == actLimitProfileID || newLimitProfileID.trim().equals(actLimitProfileID.trim())) {
                                    deleteList.add(actualObj);
                                    break ;
                                }else{
                                }
                            }
                        }

                   }
                }
            }


            //next identify records for add   actualCounterpartyList
            if (stagingList != null && stagingList.length > 0) {
                for (int k = 0; k < stagingList.length; k++) {
                    stagingOB = stagingList[k];
                    boolean found = false;
                    if (actualList != null && actualList.length > 0) {
                        for (int kk = 0; kk < actualList.length; kk++) {
                            actualObj = actualList[kk];
                            // System.out.println(stagingOB.getSubProfileID() + " <=> " + actualObj.getSubProfileID());
                            if (stagingOB.getSubProfileID() == actualObj.getSubProfileID()) {
                                found = true;
                                // If found in actual, but deleted and added again, we need to add again, so we set found = false
                                for (Iterator iterator = deleteList.iterator(); iterator.hasNext();) {
                                    ICCICounterparty deleted = (ICCICounterparty) iterator.next();
                                    if (deleted.getSubProfileID() == stagingOB.getSubProfileID())
                                        found = false;
                                        break;
                                }
                                break;
                            }

                        }
                    }
                    if (!found) {
                        createList.add(stagingOB);
                    }
                }
            }

            deleteICCICounterparty(deleteList, actualDetails.getGroupCCINoRef());
            createICCICounterparty(createList, actualDetails.getGroupCCINoRef());

            // }
        } catch (Exception ex) {
            throw new CCICounterpartyDetailsException("Exception in updateCounterpartyDetails: " + ex.toString());
        }
    }

    /**
     * @param list
     * @param groupCCINoRef
     * @throws ConcurrentUpdateException
     * @throws FinderException
     * @throws CCICounterpartyDetailsException
     *
     * @throws RemoteException
     */

    protected void deleteICCICounterparty(List list, long groupCCINoRef) throws ConcurrentUpdateException, FinderException, CCICounterpartyDetailsException, RemoteException {
        EBCCICounterpartyDetailsHome home = getEBCCICounterpartyDetailsHome();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ICCICounterparty obj = (ICCICounterparty) list.get(i);
                obj.setDeletedInd(true);
                EBCCICounterpartyDetails detEjb = home.findByPrimaryKey(new Long(obj.getGroupCCIMapID()));
                detEjb.setValue(obj);
            }

        }
    }


    /**
     * @param list
     * @param groupCCINoRef
     * @throws CCICounterpartyDetailsException
     *
     * @throws RemoteException
     * @throws CreateException
     */

    protected void createICCICounterparty(List list, long groupCCINoRef) throws CCICounterpartyDetailsException, RemoteException, CreateException {
        EBCCICounterpartyDetailsHome home = getEBCCICounterpartyDetailsHome();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ICCICounterparty obj = (ICCICounterparty) list.get(i);
                obj.setGroupCCINoRef(groupCCINoRef);
                EBCCICounterpartyDetails eb = home.create(obj);
                obj = eb.getValue();
            }
        }

    }


    /**
     * @param groupCCINo
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */

    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINo(long groupCCINo) throws CCICounterpartyDetailsException {
        long groupCCINo1 = ICMSConstant.LONG_INVALID_VALUE;
        try {
            ICCICounterpartyDetails counterPartyDetails =null;
            ICCICounterparty[]  iCCICounterparty = null;
            EBCCICounterpartyDetailsHome home = getEBCCICounterpartyDetailsHome();
            Collection list = home.findByGroupCCINo(groupCCINo);

            List iCCICounterpartyList = new ArrayList();
            if (list != null) {
                Iterator itrExp = list.iterator();
                while (itrExp.hasNext()) {
                    EBCCICounterpartyDetails theEjb = (EBCCICounterpartyDetails) itrExp.next();
                    ICCICounterparty aICCICounterparty = theEjb.getValue();
                    groupCCINo1=aICCICounterparty.getGroupCCINo();
                    iCCICounterpartyList.add(aICCICounterparty);
                }
                if (iCCICounterpartyList.size() > 0) {
                    iCCICounterparty = (ICCICounterparty[]) iCCICounterpartyList.toArray(new ICCICounterparty[0]);
                    counterPartyDetails = new OBCCICounterpartyDetails();
                    counterPartyDetails.setICCICounterparty(iCCICounterparty);
                    counterPartyDetails.setGroupCCINo(groupCCINo1);
                }
            }

            return counterPartyDetails;

        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("Other Exception", e);
        }

    }

    /**
     * @param groupCCINo
     * @param stagingGroupCCINoRef
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */
    protected ICCICounterpartyDetails getStagingCCICounterpartyByGroupCCINoRef(long groupCCINo, long stagingGroupCCINoRef) throws CCICounterpartyDetailsException {
        try {
            ICCICounterpartyDetails counterPartyDetails = null;
            ICCICounterparty[]  iCCICounterparty = null;
            EBCCICounterpartyDetailsHome home = getStagingEBCCICounterpartyDetailsHome();
            Collection list = home.findByByGroupCCINoRef(stagingGroupCCINoRef);
            List iCCICounterpartyList = new ArrayList();
            if (list != null) {

                Iterator itrExp = list.iterator();
                while (itrExp.hasNext()) {
                    EBCCICounterpartyDetails theEjb = (EBCCICounterpartyDetails) itrExp.next();
                    ICCICounterparty aICCICounterparty = theEjb.getValue();
                    iCCICounterpartyList.add(aICCICounterparty);
                }
                if (iCCICounterpartyList.size() > 0) {
                    iCCICounterparty = (ICCICounterparty[]) iCCICounterpartyList.toArray(new ICCICounterparty[0]);
                    counterPartyDetails = new OBCCICounterpartyDetails();
                    counterPartyDetails.setICCICounterparty(iCCICounterparty);
                    counterPartyDetails.setGroupCCINo(groupCCINo);
                }
            }

            return counterPartyDetails;

        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("Other Exception", e);
        }

    }


    /**
     * @param groupCCINoRef
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */
    public ICCICounterpartyDetails getCCICounterpartyByGroupCCINoRef(long groupCCINoRef) throws CCICounterpartyDetailsException {

        long groupCCINo1 = ICMSConstant.LONG_INVALID_VALUE;
       try {
            ICCICounterpartyDetails counterPartyDetails =null;
            ICCICounterparty[]  iCCICounterparty = null;
            EBCCICounterpartyDetailsHome home = getEBCCICounterpartyDetailsHome();
            Collection list = home.findByByGroupCCINoRef(groupCCINoRef);

            List iCCICounterpartyList = new ArrayList();
            if (list != null) {
                Iterator itrExp = list.iterator();
                while (itrExp.hasNext()) {
                    EBCCICounterpartyDetails theEjb = (EBCCICounterpartyDetails) itrExp.next();
                    ICCICounterparty aICCICounterparty = theEjb.getValue();
                    groupCCINo1 = aICCICounterparty.getGroupCCINo();
                    if (!aICCICounterparty.getDeletedInd()){
                        iCCICounterpartyList.add(aICCICounterparty);
                    }

                }
                if (iCCICounterpartyList.size() > 0) {
                    iCCICounterparty = (ICCICounterparty[]) iCCICounterpartyList.toArray(new ICCICounterparty[0]);
                    counterPartyDetails = new OBCCICounterpartyDetails();
                    counterPartyDetails.setICCICounterparty(iCCICounterparty);
                    counterPartyDetails.setGroupCCINo(groupCCINo1);
                    counterPartyDetails.setGroupCCINoRef(groupCCINoRef);
                }
            }
            return counterPartyDetails;
        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("Other Exception", e);
        }




       /* try {
            return CCICustomerDAOFactory.getDAO().getCCICounterpartyByGroupCCINoRef(groupCCINoRef);
        } catch (SearchDAOException e) {
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("SearchDAOException", e);
        } catch (Exception e) {
            context.setRollbackOnly();
            DefaultLogger.error(this, "", e);
            throw new CCICounterpartyDetailsException("Other Exception", e);
        }*/
    }



    /**
     * Used in ListCounterpartyCommand  for
     *  search CCI customer and customer
     * @param criteria
     * @return
     * @throws CCICounterpartyDetailsException
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria) throws CCICounterpartyDetailsException {
        try {
            EBCCICounterpartyDetailsHome mgr = getEBCCICounterpartyDetailsHome();
            return mgr.searchCCICustomer(criteria);
         } catch (RemoteException e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }


     public String searchCustomer(long lmt_profile_id) throws CCICounterpartyDetailsException {
        try {
            EBCCICounterpartyDetailsHome mgr = getEBCCICounterpartyDetailsHome();
            return mgr.searchCustomer(lmt_profile_id);
         } catch (RemoteException e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }



     public  HashMap  isExistCCICustomer(long groupCCINo, String[]   cciObj) throws CCICounterpartyDetailsException {
        try {
            EBCCICounterpartyDetailsHome mgr = getEBCCICounterpartyDetailsHome();
            return mgr.isExistCCICustomer(groupCCINo,cciObj);
         } catch (RemoteException e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }

    /**
     *  This helper method used to set address for the  customer in external search..
     * @param value
     * @return
     * @throws CCICounterpartyDetailsException
     */
      public  OBCustomerAddress getCustomerAddress(ICCICounterparty value) throws CCICounterpartyDetailsException {
        try {
            EBCCICounterpartyDetailsHome mgr = getEBCCICounterpartyDetailsHome();
            return mgr.getCustomerAddress(value);
         } catch (RemoteException e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }



    protected long getGroupCCIRefID() {
        long groupCCIRefID = ICMSConstant.LONG_INVALID_VALUE;
        try {
            groupCCIRefID = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CCI_REF_ID_SEQ, true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("SBCCICounterpartyDetailsBusManagerBean--> groupCCIRefID = " + groupCCIRefID);
        return groupCCIRefID;
    }

    protected EBCCICounterpartyDetailsHome getEBCCICounterpartyDetailsHome() {
        return (EBCCICounterpartyDetailsHome) BeanController.getEJBHome(
                ICMSJNDIConstant.EB_CCI_COUNTERPARTY_DETAILS_JNDI,
                EBCCICounterpartyDetailsHome.class.getName());
    }

    protected EBCCICounterpartyDetailsHome getStagingEBCCICounterpartyDetailsHome() {
        return (EBCCICounterpartyDetailsHome) BeanController.getEJBHome(
                ICMSJNDIConstant.EB_CCI_COUNTERPARTY_DETAILS_STAGING_JNDI,
                EBCCICounterpartyDetailsHome.class.getName());
    }


    /**
     * SessionContext object
     */
    private SessionContext ctx;

    /**
     * Method to rollback a transaction
     *
     * @throws CCICounterpartyDetailsException
     *          on errors encountered
     */
    protected void rollback() throws CCICounterpartyDetailsException {
        ctx.setRollbackOnly();
    }


    public void ejbCreate() {
    }


    public void setSessionContext(SessionContext sessionContext) throws EJBException {
        context = sessionContext;
    }


    public void ejbRemove() throws EJBException {
    }


    public void ejbActivate() throws EJBException {
    }


    public void ejbPassivate() throws EJBException {
    }


    private SessionContext context;
}
