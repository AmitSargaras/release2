package com.integrosys.cms.app.cci.trx;


import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cci.bus.*;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;


public abstract class AbstractCounterpartyDetailsTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

    /**
     * Sets the next route requirements into the ITrxValue.
     *
     * @param value is of type ITrxValue
     * @return ITrxValue containing the required routing information for next user
     * @throws TransactionException on error
     */
    public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
        return value;
    }


    /**
     * Create the staging document item doc
     *
     * @param trxValue - ICCICounterpartyDetailsTrxValue
     * @return ICCICounterpartyDetailsTrxValue - the trx object containing the created staging document item
     * @throws TrxOperationException if errors
     */
    protected ICCICounterpartyDetailsTrxValue getCounterpartyDetails(
            ICCICounterpartyDetailsTrxValue trxValue) throws TrxOperationException {


        try {
            //SBCCICounterpartyDetailsBusManager mgr = getStagingSBCCICounterpartyDetailsBusManager() ;


            ICCICounterpartyDetailsBusManager mgr = CCICounterpartyDetailsBusManagerFactory.getStagingCCICounterpartyDetailsBusManager();


            ICCICounterpartyDetails stagingDetails = mgr.createCCICounterpartyDetails(trxValue.getStagingCCICounterpartyDetails());


            trxValue.setStagingCCICounterpartyDetails(stagingDetails);
            trxValue.setStagingReferenceID(String.valueOf(stagingDetails.getGroupCCINoRef()));


            return trxValue;
        } catch (CCICounterpartyDetailsException e) {
            throw new TrxOperationException(e.toString());
        } catch (Exception e) {
            throw new TrxOperationException(e.toString());
        }
    }


    /**
     * Create the staging document item doc
     *
     * @param trxValue - ICCICounterpartyDetailsTrxValue
     * @return ICCICounterpartyDetailsTrxValue - the trx object containing the created staging document item
     * @throws TrxOperationException if errors
     */
    protected ICCICounterpartyDetailsTrxValue createStagingCounterpartyDetails(
            ICCICounterpartyDetailsTrxValue trxValue) throws TrxOperationException {


        try {
             SBCCICounterpartyDetailsBusManager manager = getStagingSBCCICounterpartyDetailsBusManager() ;


           // ICCICounterpartyDetailsBusManager manager = CCICounterpartyDetailsBusManagerFactory.getStagingCCICounterpartyDetailsBusManager();


            ICCICounterpartyDetails stagingDetails = manager.createCCICounterpartyDetails(trxValue.getStagingCCICounterpartyDetails());


            trxValue.setStagingCCICounterpartyDetails(stagingDetails);
            trxValue.setStagingReferenceID(String.valueOf(stagingDetails.getGroupCCINoRef()));


            return trxValue;
        } catch (CCICounterpartyDetailsException e) {
            throw new TrxOperationException(e.toString());
        } catch (Exception e) {
            throw new TrxOperationException(e.toString());
        }
    }


    /**
     * Updates a list of actual liquidation using a list of
     * staging liquidation.
     *
     * @param trxValue of type ILiquidationTrxValue
     * @return updated liquidation transaction value
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          on errors updating the actual liquidation
     */
    protected ICCICounterpartyDetailsTrxValue updateActualCounterpartyDetails(
            ICCICounterpartyDetailsTrxValue trxValue) throws TrxOperationException {
        try {
            ICCICounterpartyDetails actual = trxValue.getCCICounterpartyDetails();
            ICCICounterpartyDetails staging = trxValue.getStagingCCICounterpartyDetails(); // update from staging


            if (actual != null) {
                staging.setVersionTime(actual.getVersionTime());
            }

            ICCICounterpartyDetailsBusManager mgr = CCICounterpartyDetailsBusManagerFactory.getActualCCICounterpartyDetailsBusManager();


            String strRefID = trxValue.getReferenceID();
            long refID = ICMSConstant.LONG_INVALID_VALUE;
            if (strRefID != null)
                refID = Long.parseLong(strRefID);


          /*   if (staging != null){
                   actual = mgr.getCCICounterpartyByGroupCCINo(staging.getGroupCCINo());
            }*/


//           if (refID > 0)
               actual = mgr.getCCICounterpartyByGroupCCINoRef(refID);

            if (actual == null) {
//                System.out.println("mgr.createCCICounterpartyDetails = " + refID);
                actual = mgr.createCCICounterpartyDetails(staging);
            } else {
//                System.out.println("mgr.updateCCICounterpartyDetails = " + refID);
                actual.setStagingGroupCCINoRef(staging.getStagingGroupCCINoRef());
                actual = mgr.updateCCICounterpartyDetails(actual);
            }
            trxValue.setCCICounterpartyDetails(actual); // set into actual
            trxValue.setReferenceID(String.valueOf(actual.getGroupCCINoRef()));
            return trxValue;
        } catch (CCICounterpartyDetailsException e) {
            throw new TrxOperationException("CCICounterpartyDetailsException caught!", e);
        } catch (Exception e) {
            throw new TrxOperationException("CCICounterpartyDetailsException  trans caught!", e);
        }
    }

    protected ICCICounterpartyDetailsTrxValue createActualCounterpartyDetails(
              ICCICounterpartyDetailsTrxValue trxValue) throws TrxOperationException {
          try {
              ICCICounterpartyDetails actual = trxValue.getCCICounterpartyDetails();
              ICCICounterpartyDetails staging = trxValue.getStagingCCICounterpartyDetails(); // update from staging


              if (actual != null) {
                  staging.setVersionTime(actual.getVersionTime());
              }

              ICCICounterpartyDetailsBusManager mgr = CCICounterpartyDetailsBusManagerFactory.getActualCCICounterpartyDetailsBusManager();


              String strRefID = trxValue.getReferenceID();
              long refID = 0;
              if (strRefID != null)
                  refID = Long.parseLong(strRefID);

              if (refID > 0)
                  actual = mgr.getCCICounterpartyByGroupCCINo(refID);

              if (actual == null) {
                  actual = mgr.createCCICounterpartyDetails(staging);
              } /*else {
                  actual = mgr.updateCCICounterpartyDetails(actual);
                  System.out.println("UPDATED CCICounterpartyDetails actual = " + actual);
              }*/
              trxValue.setCCICounterpartyDetails(actual); // set into actual
              trxValue.setReferenceID(String.valueOf(actual.getGroupCCINoRef()));
              return trxValue;
          } catch (CCICounterpartyDetailsException e) {
              throw new TrxOperationException("CCICounterpartyDetailsException caught!", e);
          } catch (Exception e) {
              throw new TrxOperationException("CCICounterpartyDetailsException  trans caught!", e);
          }
      }

       protected ICCICounterpartyDetailsTrxValue deletedActualCounterpartyDetailsOnly(
            ICCICounterpartyDetailsTrxValue trxValue) throws TrxOperationException {
        try {
            ICCICounterpartyDetails actual = trxValue.getCCICounterpartyDetails();




            ICCICounterpartyDetails staging = trxValue.getStagingCCICounterpartyDetails(); // update from staging


            if (actual != null) {
                staging.setVersionTime(actual.getVersionTime());
                ICCICounterparty[]  actualParty = actual.getICCICounterparty() ;
                if (actualParty != null && actualParty.length > 0) {
                    for (int index = 0; index < actualParty.length; index++) {
                        ICCICounterparty OB = actualParty[index];
                        OB.setDeletedInd(true);
                    }
                }
            }

            ICCICounterpartyDetailsBusManager mgr = CCICounterpartyDetailsBusManagerFactory.getActualCCICounterpartyDetailsBusManager();
            if (actual != null) {
                  // pass getStagingGroupCCINoRef to compare and then delete the actaual
                 // pass getStagingGroupCCINoRef to compare and then update Actual
                 if (trxValue.getStagingReferenceID() != null){
                    actual.setStagingGroupCCINoRef(Long.parseLong(trxValue.getStagingReferenceID()));
                 }
            }
            actual = mgr.updateCCICounterpartyDetails(actual);

            trxValue.setCCICounterpartyDetails(actual); // set into actual
            //trxValue.setReferenceID(String.valueOf(actual.getGroupCCINo()));
//            trxValue.setReferenceID(String.valueOf(actual.getGroupCCINoRef()));
            return trxValue;
        } catch (CCICounterpartyDetailsException e) {
            throw new TrxOperationException("CCICounterpartyDetailsException caught!", e);
        } catch (Exception e) {
            throw new TrxOperationException("CCICounterpartyDetailsException  trans caught!", e);
        }
    }


      protected ICCICounterpartyDetailsTrxValue updateActualCounterpartyDetailsOnly(
            ICCICounterpartyDetailsTrxValue trxValue) throws TrxOperationException {
        try {
            ICCICounterpartyDetails actual = trxValue.getCCICounterpartyDetails();
            ICCICounterpartyDetails staging = trxValue.getStagingCCICounterpartyDetails(); // update from staging


            if (actual != null) {
                staging.setVersionTime(actual.getVersionTime());
                 ICCICounterparty[]  actualParty = actual.getICCICounterparty() ;
                if (actualParty != null && actualParty.length > 0) {
                    for (int index = 0; index < actualParty.length; index++) {
                        ICCICounterparty OB = actualParty[index];
                        OB.setDeletedInd(true);
                    }
                }
            }


            ICCICounterpartyDetailsBusManager mgr = CCICounterpartyDetailsBusManagerFactory.getActualCCICounterpartyDetailsBusManager();
            if (actual != null) {
                 // pass getStagingGroupCCINoRef to compare and then update Actual
                 if (trxValue.getStagingReferenceID() != null){
                    actual.setStagingGroupCCINoRef(Long.parseLong(trxValue.getStagingReferenceID()));
                 }
            }
            actual = mgr.updateCCICounterpartyDetails(actual);

            trxValue.setCCICounterpartyDetails(actual); // set into actual
            //trxValue.setReferenceID(String.valueOf(actual.getGroupCCINo()));
//            trxValue.setReferenceID(String.valueOf(actual.getGroupCCINoRef()));
            return trxValue;
        } catch (CCICounterpartyDetailsException e) {
            throw new TrxOperationException("CCICounterpartyDetailsException caught!", e);
        } catch (Exception e) {
            throw new TrxOperationException("CCICounterpartyDetailsException  trans caught!", e);
        }
    }
    /**
     * Update a stockIndexFeedGroup transaction
     *
     * @param trxValue - ITrxValue
     * @return IStockIndexFeedGroupTrxValue - the document item specific transaction object created
     * @throws TrxOperationException if there is any processing errors
     */
    protected ICCICounterpartyDetailsTrxValue updateCounterpartyDetailsTransaction(
            ICCICounterpartyDetailsTrxValue trxValue) throws TrxOperationException {
        try {
            trxValue = prepareTrxValue(trxValue);
            ICMSTrxValue tempValue = super.updateTransaction(trxValue);
            OBCCICounterpartyDetailsTrxValue newValue = new OBCCICounterpartyDetailsTrxValue(tempValue);
            newValue.setCCICounterpartyDetails(trxValue.getCCICounterpartyDetails());
            newValue.setStagingCCICounterpartyDetails(trxValue.getStagingCCICounterpartyDetails());
            return newValue;
        } catch (TransactionException tex) {
            throw new TrxOperationException(tex);
        } catch (Exception ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }

    /**
     * To get the remote handler for the staging stockFeedGroup session bean
     * @return SBUnitTrustFeedBusManager - the remote handler for the staging stockFeedGroup session bean
     */

    protected SBCCICounterpartyDetailsBusManager getStagingSBCCICounterpartyDetailsBusManager() {
        SBCCICounterpartyDetailsBusManager remote = (SBCCICounterpartyDetailsBusManager)BeanController.getEJB(
                ICMSJNDIConstant.SB_CCI_COUNTERPARTY_DETAILS_BUS_MANAGER_STAGING_JNDI,
                SBCCICounterpartyDetailsBusManagerHome.class.getName());
        return remote;
    }

    /**
     * To get the remote handler for the staging stockFeedGroup session bean
     * @return SBUnitTrustFeedBusManager - the remote handler for the staging stockFeedGroup session bean
     */
     protected SBCCICounterpartyDetailsBusManager getSBCCICounterpartyDetailsBusManager() {
        SBCCICounterpartyDetailsBusManager remote = (SBCCICounterpartyDetailsBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CCI_COUNTERPARTY_DETAILS_BUS_MANAGER_JNDI,
                SBCCICounterpartyDetailsBusManagerHome.class.getName());
        return remote;
    }


    /**
     * Prepares a trx object
     */
    protected ICCICounterpartyDetailsTrxValue prepareTrxValue(ICCICounterpartyDetailsTrxValue trxValue) {
        if (trxValue != null) {
            ICCICounterpartyDetails actual = trxValue.getCCICounterpartyDetails();
            ICCICounterpartyDetails staging = trxValue.getStagingCCICounterpartyDetails();
            /*if (actual != null) {
                    System.out.println("actual.getGroupCCINo() = " + actual.getGroupCCINo() +
                                 " getGroupCCINoRef() = " + actual.getGroupCCINoRef() +
                                 " getStagingGroupCCINoRef() = " + actual.getStagingGroupCCINoRef());
            }
             if (staging != null) {
                    System.out.println("staging.getGroupCCINo() = " + staging.getGroupCCINo() +
                                 " getGroupCCINoRef() = " + staging.getGroupCCINoRef() +
                                 " getStagingGroupCCINoRef() = " + staging.getStagingGroupCCINoRef());
             }
*/


            if (actual != null ) {
//            if (actual != null && trxValue.getReferenceID() == null) {
                trxValue.setReferenceID(String.valueOf(actual.getGroupCCINoRef()));
            } else {
                trxValue.setReferenceID(null);
            }
            if (staging != null) {
                trxValue.setStagingReferenceID( String.valueOf(staging.getStagingGroupCCINoRef()));
            } else {
                trxValue.setStagingReferenceID(null);
            }

            return trxValue;
        }
        return null;
    }

    /**
     * Prepares a trx object
     */
    protected ICCICounterpartyDetailsTrxValue prepareUpdateTrxValue(ICCICounterpartyDetailsTrxValue trxValue) {
        if (trxValue != null) {
            ICCICounterpartyDetails actual = trxValue.getCCICounterpartyDetails();
            if (actual != null && trxValue.getReferenceID() == null) {
                trxValue.setReferenceID(String.valueOf(actual.getGroupCCINoRef()));
            } else {
                trxValue.setReferenceID(null);
            }

            return trxValue;
        }
        return null;
    }


    /**
     * Prepares a result object to be returned
     *
     * @param value is of type IUnitTrustFeedGroupTrxValue
     * @return ITrxResult
     */
    protected ITrxResult prepareResult(ICCICounterpartyDetailsTrxValue value) {
        OBCMSTrxResult result = new OBCMSTrxResult();
        result.setTrxValue(value);
        return result;
    }


    /**
     * Helper method to cast a generic trx value object to a document item specific trx value object
     *
     * @param anITrxValue - ITrxValue
     * @return IStockIndexFeedGroupTrxValue - the document item specific trx value object
     * @throws TrxOperationException if there is a ClassCastException
     */
    protected ICCICounterpartyDetailsTrxValue getICCICounterpartyDetailsTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ICCICounterpartyDetailsTrxValue) anITrxValue;
        } catch (ClassCastException cex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCCICounterpartyDetailsTrxValue: " + cex.toString());
        }
    }


    /**
     * Method to create a transaction record
     *
     * @param trxValue is of type ICCICounterpartyDetailsTrxValue
     * @return deal transaction value
     * @throws TrxOperationException on errors
     */
    protected ICCICounterpartyDetailsTrxValue createTransaction(ICCICounterpartyDetailsTrxValue trxValue) throws TrxOperationException {
        try {
            trxValue = prepareTrxValue(trxValue);
            ICMSTrxValue tempValue = super.createTransaction(trxValue);
            ICCICounterpartyDetailsTrxValue newValue = new OBCCICounterpartyDetailsTrxValue(tempValue);
            newValue.setCCICounterpartyDetails(trxValue.getCCICounterpartyDetails());
            newValue.setStagingCCICounterpartyDetails(trxValue.getStagingCCICounterpartyDetails());
            return newValue;
        } catch (TrxOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }


    /**
     * Method to update a transaction record.
     *
     * @param trxValue is of type ICCICounterpartyDetailsTrxValue
     * @return deal transaction value
     * @throws TrxOperationException on errors updating the transaction
     */
    protected ICCICounterpartyDetailsTrxValue updateTransaction(ICCICounterpartyDetailsTrxValue trxValue) throws TrxOperationException {
        try {
            String  ReferenceID= trxValue.getReferenceID() ;
            trxValue = prepareTrxValue(trxValue);
            if (ReferenceID != null && !"".equals(ReferenceID))
            trxValue.setReferenceID(ReferenceID);
            ICMSTrxValue tempValue = super.updateTransaction(trxValue);
            ICCICounterpartyDetailsTrxValue newValue = new OBCCICounterpartyDetailsTrxValue(tempValue);
            newValue.setCCICounterpartyDetails(trxValue.getCCICounterpartyDetails());
            newValue.setStagingCCICounterpartyDetails(trxValue.getStagingCCICounterpartyDetails());
            return newValue;
        }
        catch (TrxOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }

    private void debug(String  msg, ICCICounterpartyDetailsTrxValue trxValue){
        DefaultLogger.debug(this,"CheckerApproveUpdateCounterpartyDetailsOperation trxValue.getStagingReferenceID() = " + trxValue.getStagingReferenceID());

    }


}