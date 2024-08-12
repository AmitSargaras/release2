package com.integrosys.cms.app.custgrpi.trx;


import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.custgrpi.bus.*;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;


public abstract class AbstractCustGrpIdentifierTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {




    /**
     * Create the staging document item doc
     *
     * @param trxValue - ICustGrpIdentifierTrxValue
     * @return - the trx object containing the created staging document item
     * @throws TrxOperationException if errors
     */
    protected ICustGrpIdentifierTrxValue getCustGrpIdentifier(ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {
        return trxValue;
    }


    /**
     * Create the staging Record
     *
     * @param trxValue - ICustGrpIdentifierTrxValue
     * @return ICustGrpIdentifierTrxValue - the trx object containing the created staging document item
     * @throws TrxOperationException if errors
     */
    protected ICustGrpIdentifierTrxValue createStagingCustGrpIdentifier(ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {

           ICustGrpIdentifier stagingObj  = trxValue.getStagingCustGrpIdentifier();
           Debug("-->Before createDependants getStatus" + stagingObj.getStatus() );
         try {
            Debug(" Create createStagingCustGrpIdentifier for maker");
            ICustGrpIdentifierBusManager manager = CustGrpIdentifierBusManagerFactory.getStagingCustGrpIdentifierBusManager();
            stagingObj = manager.createCustGrpIdentifier(stagingObj);
            trxValue.setStagingCustGrpIdentifier(stagingObj);
            trxValue.setStagingReferenceID(String.valueOf(stagingObj.getGrpID()));
            Debug("  setting all the staging  records and then return same transaction  StagingReferenceID = " + trxValue.getStagingReferenceID());
            return trxValue;
        } catch (Exception e) {
            throw new TrxOperationException(e.toString());
        }
    }


     protected ICustGrpIdentifierTrxValue deleteStagingCustGrpIdentifier(ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {

           ICustGrpIdentifier stagingObj  = trxValue.getStagingCustGrpIdentifier();
           Debug("-->Before DeleteStagingCustGrpIdentifier getStatus" + stagingObj.getStatus() );
         try {
            Debug(" Create DeleteStagingCustGrpIdentifier for maker");
             stagingObj.setStatus(ICMSConstant.STATE_DELETED);
            ICustGrpIdentifierBusManager manager = CustGrpIdentifierBusManagerFactory.getStagingCustGrpIdentifierBusManager();
            stagingObj = manager.deleteCustGrpIdentifier(stagingObj);
            trxValue.setStagingCustGrpIdentifier(stagingObj);
            trxValue.setStagingReferenceID(String.valueOf(stagingObj.getGrpID()));
            Debug("  setting all the staging  records and then return same transaction  StagingReferenceID = " + trxValue.getStagingReferenceID());
            return trxValue;
        } catch (Exception e) {
            throw new TrxOperationException(e.toString());
        }
    }

    protected ICustGrpIdentifierTrxValue updateActualCustGrpIdentifier(
            ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {
        try {
            ICustGrpIdentifier actual = trxValue.getCustGrpIdentifier();
            ICustGrpIdentifier staging = trxValue.getStagingCustGrpIdentifier(); // update from staging

            if (actual != null) {
                staging.setVersionTime(actual.getVersionTime());
            }

            ICustGrpIdentifierBusManager mgr = CustGrpIdentifierBusManagerFactory.getActualCustGrpIdentifierBusManager();


            String strRefID = trxValue.getReferenceID();
            long refID = ICMSConstant.LONG_INVALID_VALUE;
            if (strRefID != null){
                refID = Long.parseLong(strRefID);
            }

            if (refID != ICMSConstant.LONG_INVALID_VALUE){
                actual = mgr.getCustGrpIdentifierByTrxIDRef(refID);
            }

            ICustGrpIdentifier actualCustGrp =null;
            if (actual == null) {
                Debug(" updateActualCustGrpIdentifier  (createCustGrpIdentifier) = " + refID);
                actualCustGrp = mgr.createCustGrpIdentifier(staging);
            } else {
                ICustGrpIdentifier updActual = (ICustGrpIdentifier) CommonUtil.deepClone(staging);
                updActual = mergeCustGrpIdentifier(actual, updActual);

                actualCustGrp = mgr.updateCustGrpIdentifier(updActual);
               // trxValue.setCustGrpIdentifier(actualCustGrp);


               // staging.setGrpIDRef(staging.getGrpID());
                //staging.setGrpID(actual.getGrpID());
                //setActualRecords(actual,staging) ;
               // Debug(" updateActualCustGrpIdentifier  getGrpID = " + staging.getGrpID());
               // Debug(" updateActualCustGrpIdentifier  getGrpIDRef = " + staging.getGrpIDRef());

               // actual = mgr.updateCustGrpIdentifier(staging);

            }

            trxValue.setCustGrpIdentifier(actualCustGrp);                      // set GrpID  for reference id
            trxValue.setReferenceID(String.valueOf(actualCustGrp.getGrpID()));

            return trxValue;
        } catch (CustGrpIdentifierException e) {
            throw new TrxOperationException("CustGrpIdentifierException caught!", e);
        } catch (Exception e) {
            throw new TrxOperationException("CustGrpIdentifierException  trans caught!", e);
        }
    }


     protected ICustGrpIdentifier mergeCustGrpIdentifier(ICustGrpIdentifier anOriginal, ICustGrpIdentifier aCopy) throws TrxOperationException {
        aCopy.setGrpID(anOriginal.getGrpID());
        aCopy.setVersionTime(anOriginal.getVersionTime());
        return aCopy;
    }


   private void setActualRecords(ICustGrpIdentifier actual, ICustGrpIdentifier staging){
       actual.setGrpNo( staging.getGrpNo());
       actual.setGroupName( staging.getGroupName());
       actual.setGroupType(staging.getGroupType());
       actual.setAccountMgmt(staging.getAccountMgmt());
       actual.setGroupCounty(staging.getGroupCounty());
       actual.setGroupCurrency(staging.getGroupCurrency());
       actual.setBusinessUnit(staging.getBusinessUnit());
       actual.setGroupAccountMgrID(staging.getGroupAccountMgrID());
       actual.setApprovedBy(staging.getApprovedBy());
       actual.setGroupRemarks(staging.getGroupRemarks());
       actual.setMasterGroupInd(staging.getMasterGroupInd());
       actual.setInternalLmt(staging.getInternalLmt());
       actual.setGroupLmt(staging.getGroupLmt());
       actual.setStatus(staging.getStatus());
       actual.setLastReviewDt(staging.getLastReviewDt());
       actual.setIsBGEL(staging.getIsBGEL());
       actual.setGroupAccountMgrCode(staging.getGroupAccountMgrCode());

   }



    /**
     * Create the Actual Record
     *
     * @param trxValue - ICustGrpIdentifierTrxValue
     * @return ICustGrpIdentifierTrxValue - the trx object containing the created staging document item
     * @throws TrxOperationException if errors
     */
    protected ICustGrpIdentifierTrxValue createActualCustGrpIdentifier(ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {
          try {
              ICustGrpIdentifier actual = trxValue.getCustGrpIdentifier();
              ICustGrpIdentifier staging = trxValue.getStagingCustGrpIdentifier(); // update from staging

              Debug("  createActual CustGrpIdentifier Only");

              if (actual != null) {
                  staging.setVersionTime(actual.getVersionTime());
              }

              ICustGrpIdentifierBusManager mgr = CustGrpIdentifierBusManagerFactory.getActualCustGrpIdentifierBusManager();


              String strRefID = trxValue.getReferenceID();
              long refID = 0;
              if (strRefID != null)
                  refID = Long.parseLong(strRefID);

              if (refID > 0)
//                  actual = mgr.getCCICounterpartyByGroupCCINo(refID);

                  if (actual == null) {
//                  actual = mgr.createCCICounterpartyDetails(staging);
                	  DefaultLogger.debug(this,"CREATED CustGrpIdentifier actual = " + actual);
                  } /*else {
                    actual = mgr.updateCCICounterpartyDetails(actual);
                    System.out.println("UPDATED CustGrpIdentifier actual = " + actual);
                }*/
//              trxValue.setCCICounterpartyDetails(actual); // set into actual
//              trxValue.setReferenceID(String.valueOf(actual.getGroupCCINoRef()));
              return trxValue;
//          } catch (CustGrpIdentifierException e) {
//              throw new TrxOperationException("CustGrpIdentifierException caught!", e);
          } catch (Exception e) {
              throw new TrxOperationException("CustGrpIdentifier  trans caught!", e);
          }
      }

    /**
     * Update the Actual Record
     *
     * @param trxValue - ICustGrpIdentifierTrxValue
     * @return ICustGrpIdentifierTrxValue - the trx object containing the created staging document item
     * @throws TrxOperationException if errors
     */
    protected ICustGrpIdentifierTrxValue updateActualCustGrpIdentifierOnly(ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {
          try {
              ICustGrpIdentifier actual = trxValue.getCustGrpIdentifier();
              ICustGrpIdentifier staging = trxValue.getStagingCustGrpIdentifier(); // update from staging

              Debug("  updateActualCustGrpIdentifierOnly");

              if (actual != null) {
                  staging.setVersionTime(actual.getVersionTime());
              }


              ICustGrpIdentifierBusManager mgr = CustGrpIdentifierBusManagerFactory.getActualCustGrpIdentifierBusManager();
              if (actual != null) {
                  // pass getStagingGroupIDRef to compare and then update Actual
                  Debug(" updateActualCustGrpIdentifierOnly pass getStagingGroupIDRef = " + trxValue.getStagingReferenceID());
                  if (trxValue.getStagingReferenceID() != null) {
                   //   actual.setStagingGroupIDRef(Long.parseLong(trxValue.getStagingReferenceID()));
                  }
              }
              actual = mgr.updateCustGrpIdentifier(actual);
              Debug(" UPDATED updateActualCustGrpIdentifierOnly  with = " + actual.getGrpID());

              trxValue.setCustGrpIdentifier(actual); // set into actual
              trxValue.setReferenceID(String.valueOf(actual.getGrpID()));
              //trxValue.setReferenceID(String.valueOf(actual.getGroupCCINoRef()));
              return trxValue;
          } catch (CustGrpIdentifierException e) {
              throw new TrxOperationException("CustGrpIdentifierException caught!", e);
          } catch (Exception e) {
              throw new TrxOperationException("CustGrpIdentifierException  trans caught!", e);
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
    protected ICustGrpIdentifierTrxValue updateActualCustGrpIdentifier_back(ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {
        try {
            ICustGrpIdentifier actual = trxValue.getCustGrpIdentifier();
            ICustGrpIdentifier staging = trxValue.getStagingCustGrpIdentifier(); // update from staging

            Debug("  updateActualCounterpartyDetails Only");

            if (actual != null) {
                staging.setVersionTime(actual.getVersionTime());
            }

            ICustGrpIdentifierBusManager mgr = CustGrpIdentifierBusManagerFactory.getActualCustGrpIdentifierBusManager();


            String strRefID = trxValue.getReferenceID();
            long refID = ICMSConstant.LONG_INVALID_VALUE;
            if (strRefID != null)
                refID = Long.parseLong(strRefID);

            if (actual == null) {
                actual = mgr.createCustGrpIdentifier(staging);
            }
            trxValue.setCustGrpIdentifier(actual); // set into actual
            trxValue.setReferenceID(String.valueOf(actual.getGrpID()));
            return trxValue;
        } catch (CustGrpIdentifierException e) {
            throw new TrxOperationException("CustGrpIdentifierException caught!", e);
        } catch (Exception e) {
            throw new TrxOperationException("CustGrpIdentifierException  trans caught!", e);
        }
    }


    protected ICustGrpIdentifierTrxValue deletedActualCustGrpIdentifier(ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {
        try {
            ICustGrpIdentifier actual = trxValue.getCustGrpIdentifier();


            ICustGrpIdentifier staging = trxValue.getStagingCustGrpIdentifier(); // update from staging

            Debug(" deletedActualCustGrpIdentifierOnly");


            ICustGrpIdentifierBusManager mgr = CustGrpIdentifierBusManagerFactory.getActualCustGrpIdentifierBusManager();
            if (actual != null) {
                // pass getStagingGroupCCINoRef to compare and then delete the actaual
                // pass getStagingGroupCCINoRef to compare and then update Actual
               // System.out.println("pass getStagingGroupIDRef = " + trxValue.getStagingReferenceID());
                if (trxValue.getStagingReferenceID() != null) {
//                    actual.setStagingGroupCCINoRef(Long.parseLong(trxValue.getStagingReferenceID()));
                }
                actual.setStatus(ICMSConstant.STATE_DELETED);
            }

            actual = mgr.updateCustGrpIdentifier(actual);
//            System.out.println("UPDATED deletedActualCustGrpIdentifierOnly actual = " + actual);

            trxValue.setCustGrpIdentifier(actual); // set into actual
           trxValue.setReferenceID(String.valueOf(actual.getGrpID()));
            return trxValue;
        } catch (CustGrpIdentifierException e) {
            throw new TrxOperationException("CustGrpIdentifierException caught!", e);
        } catch (Exception e) {
            throw new TrxOperationException("CustGrpIdentifierException  trans caught!", e);
        }
    }



    /**
     * Update a stockIndexFeedGroup transaction
     *
     * @param trxValue - ITrxValue
     * @return IStockIndexFeedGroupTrxValue - the document item specific transaction object created
     * @throws TrxOperationException if there is any processing errors
     */
    protected ICustGrpIdentifierTrxValue updateCustGrpIdentifierTransaction(ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {

        Debug("  Inside updateCounterpartyDetailsTransaction");

        try {
            trxValue = prepareTrxValue(trxValue);
            ICMSTrxValue tempValue = super.updateTransaction(trxValue);
            OBCustGrpIdentifierTrxValue newValue = new OBCustGrpIdentifierTrxValue(tempValue);
            newValue.setCustGrpIdentifier(trxValue.getCustGrpIdentifier());
            newValue.setStagingCustGrpIdentifier(trxValue.getStagingCustGrpIdentifier());
            return newValue;
        } catch (TransactionException tex) {
            throw new TrxOperationException(tex);
        } catch (Exception ex) {
            throw new TrxOperationException("General Exception: " + ex.toString());
        }
    }



    /**
     * Prepares a trx object
     */
    protected ICustGrpIdentifierTrxValue prepareTrxValue(ICustGrpIdentifierTrxValue trxValue) {
        if (trxValue != null) {
            ICustGrpIdentifier actual = trxValue.getCustGrpIdentifier();
            ICustGrpIdentifier staging = trxValue.getStagingCustGrpIdentifier();

/*
            if (actual != null) {
//            if (actual != null && trxValue.getReferenceID() == null) {
             //   trxValue.setReferenceID(String.valueOf(actual.getGrpID()));
            } else {
                trxValue.setReferenceID(null);
            }
            if (staging != null) {
           //     trxValue.setStagingReferenceID( String.valueOf(staging.getGrpID()));
            } else {
                trxValue.setStagingReferenceID(null);
            }*/


            return trxValue;
        }
        return null;
    }

    /**
     * Prepares a trx object
     */
    protected ICustGrpIdentifierTrxValue prepareUpdateTrxValue(ICustGrpIdentifierTrxValue trxValue) {
        if (trxValue != null) {
            ICustGrpIdentifier actual = trxValue.getCustGrpIdentifier();
            if (actual != null && trxValue.getReferenceID() == null) {
                trxValue.setReferenceID(String.valueOf(actual.getGrpID()));
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
    protected ITrxResult prepareResult(ICustGrpIdentifierTrxValue value) {
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
    protected ICustGrpIdentifierTrxValue getCustGrpIdentifierTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
        try {
            return (ICustGrpIdentifierTrxValue) anITrxValue;
        } catch (ClassCastException cex) {
            throw new TrxOperationException("The ITrxValue is not of type OBCustGrpIdentifierTrxValue: " + cex.toString());
        }
    }


    /**
     * Method to create a transaction record
     *
     * @param trxValue is of type ICustGrpIdentifierTrxValue
     * @return deal transaction value
     * @throws TrxOperationException on errors
     */
    protected ICustGrpIdentifierTrxValue createTransaction(ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {
        try {
            trxValue = prepareTrxValue(trxValue);
            ICMSTrxValue tempValue = super.createTransaction(trxValue);
            ICustGrpIdentifierTrxValue newValue = new OBCustGrpIdentifierTrxValue(tempValue);
            newValue.setCustGrpIdentifier(trxValue.getCustGrpIdentifier());
            newValue.setStagingCustGrpIdentifier(trxValue.getStagingCustGrpIdentifier());
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
     * @param trxValue is of type ICustGrpIdentifierTrxValue
     * @return deal transaction value
     * @throws TrxOperationException on errors updating the transaction
     */
    protected ICustGrpIdentifierTrxValue updateTransaction(ICustGrpIdentifierTrxValue trxValue) throws TrxOperationException {
        try {

            String ReferenceID = trxValue.getReferenceID();
            trxValue = prepareTrxValue(trxValue);
            if (ReferenceID != null && !"".equals(ReferenceID))
                trxValue.setReferenceID(ReferenceID);

            ICMSTrxValue tempValue = super.updateTransaction(trxValue);

            ICustGrpIdentifierTrxValue newValue = new OBCustGrpIdentifierTrxValue(tempValue);
            newValue.setCustGrpIdentifier(trxValue.getCustGrpIdentifier());
            newValue.setStagingCustGrpIdentifier(trxValue.getStagingCustGrpIdentifier());


            return newValue;
        }
        catch (TrxOperationException e) {
            throw e;
        } catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }



    /**
     * To get the remote handler for the staging stockFeedGroup session bean
     *
     * @return SBUnitTrustFeedBusManager - the remote handler for the staging stockFeedGroup session bean
     */
    protected SBCustGrpIdentifierBusManager getStagingCustGrpIdentifierBusManager() {
        SBCustGrpIdentifierBusManager remote = (SBCustGrpIdentifierBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CUST_GRP_IDENTIFIER_BUS_MANAGER_STAGING_JNDI,
                SBCustGrpIdentifierBusManagerHome.class.getName());
        return remote;
    }

    /**
     * To get the remote handler for the staging stockFeedGroup session bean
     *
     * @return SBUnitTrustFeedBusManager - the remote handler for the staging stockFeedGroup session bean
     */
    protected SBCustGrpIdentifierBusManager getSBCustGrpIdentifierBusManager() {
        SBCustGrpIdentifierBusManager remote = (SBCustGrpIdentifierBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CUST_GRP_IDENTIFIER_BUS_MANAGER_JNDI,
                SBCustGrpIdentifierBusManagerHome.class.getName());
        return remote;
    }

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

    private void Debug(String msg) {
    	DefaultLogger.debug(this,"AbstractCustGrpIdentifierTrxOperation = " + msg);
    }


}