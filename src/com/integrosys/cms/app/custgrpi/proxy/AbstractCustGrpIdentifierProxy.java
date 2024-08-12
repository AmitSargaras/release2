package com.integrosys.cms.app.custgrpi.proxy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.SBCCICounterpartyDetailsBusManager;
import com.integrosys.cms.app.cci.bus.SBCCICounterpartyDetailsBusManagerHome;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.custgrpi.bus.*;
import com.integrosys.cms.app.custgrpi.trx.CustGrpIdentifierTrxControllerFactory;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;
import com.integrosys.cms.app.custgrpi.trx.OBCustGrpIdentifierTrxValue;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.transaction.*;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingDAO;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingException;

import java.util.Map;
import java.util.List;


public abstract class AbstractCustGrpIdentifierProxy implements ICustGrpIdentifierProxy {

    //   Business Method Starts here  for Group Profile

     //GEMS  Maker / Checker


    public ICustGrpIdentifierTrxValue maker2CancelUpdateCustGrpIdentifier
            (ITrxContext ctx,
             ICustGrpIdentifierTrxValue aTrxValue)
            throws CustGrpIdentifierException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER2_CANCEL);
        return (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, aTrxValue), param);

    }


    public ICustGrpIdentifierTrxValue checker2RejectUpdateCustGrpIdentifier
               (ITrxContext ctx,
                ICustGrpIdentifierTrxValue aTrxValue)
               throws CustGrpIdentifierException {
           OBCMSTrxParameter param = new OBCMSTrxParameter();
           param.setAction(ICMSConstant.ACTION_CHECKER2_REJECT);
           return (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, aTrxValue), param);

       }



    public ICustGrpIdentifierTrxValue checker2ApproveUpdateCustGrp
              (ITrxContext ctx,
               ICustGrpIdentifierTrxValue aTrxValue)
              throws CustGrpIdentifierException {
          OBCMSTrxParameter param = new OBCMSTrxParameter();
          param.setAction(ICMSConstant.ACTION_CHECKER2_APPROVE);
          return (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, aTrxValue), param);

      }

    public ICustGrpIdentifierTrxValue maker2SaveCustGrpIdentifier(
              ITrxContext ctx,
              ICustGrpIdentifierTrxValue aTrxValue,
              ICustGrpIdentifier obj)
              throws CustGrpIdentifierException {

          checkParameters(ctx, aTrxValue, obj);
          aTrxValue = formulateTrxValue(ctx, aTrxValue, obj);
          OBCMSTrxParameter param = new OBCMSTrxParameter();
          param.setAction(ICMSConstant.ACTION_MAKER2_SAVE_CUST_GRP_IDENTIFIER);
          return operate(aTrxValue, param);

      }
     public ICustGrpIdentifierTrxValue maker2SubmitCustGrpIdentifier(
            ITrxContext ctx,
            ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws CustGrpIdentifierException {

        checkParameters(ctx, aTrxValue, obj);
        aTrxValue = formulateTrxValue(ctx, aTrxValue, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER2_UPDATE_CUST_GRP_IDENTIFIER);
        return operate(aTrxValue, param);

    }


    // for GEMS AM Maker and GEMS AM Checker

      public ICustGrpIdentifierTrxValue makerDeleteCustGrpIdentifier(
            ITrxContext ctx,
            ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws CustGrpIdentifierException {

        checkParameters(ctx, aTrxValue, obj);
        aTrxValue = formulateTrxValue(ctx, aTrxValue, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_CUST_GRP_IDENTIFIER);
        return operate(aTrxValue, param);

    }



    public ICustGrpIdentifierTrxValue getCustGrpIdentifierByGrpID(
            ITrxContext ctx, String grpID)
            throws CustGrpIdentifierException {
        ICustGrpIdentifier obj = new OBCustGrpIdentifier();
        obj.setGrpID(Long.parseLong(grpID));
        try {
            SBCustGrpIdentifierBusManager mgr = getBusManager();
            obj = mgr.getCustGrpIdentifierByGrpID(obj);
        } catch (CustGrpIdentifierException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }

        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_BY_GROUP_ID);
        OBCustGrpIdentifierTrxValue trxValue = new OBCustGrpIdentifierTrxValue();
        trxValue.setReferenceID(obj.getGrpID() + "");
        ICustGrpIdentifierTrxValue newTransValue = (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, trxValue), param);
        newTransValue.setCustGrpIdentifier(obj);
        return newTransValue;
    }


    public ICustGrpIdentifierTrxValue makerSubmitCustGrpIdentifier(
            ITrxContext ctx,
            ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws CustGrpIdentifierException {

        checkParameters(ctx, aTrxValue, obj);
        aTrxValue = formulateTrxValue(ctx, aTrxValue, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CUST_GRP_IDENTIFIER);
        return operate(aTrxValue, param);

    }


    public ICustGrpIdentifierTrxValue checkerApproveUpdateCustGrp
            (ITrxContext ctx,
             ICustGrpIdentifierTrxValue aTrxValue)
            throws CustGrpIdentifierException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE);
        return (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, aTrxValue), param);

    }


    public ICustGrpIdentifierTrxValue getCustGrpIdentifierByTrxID(
            ITrxContext ctx, String trxID)
            throws CustGrpIdentifierException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_CCIN_BY_TRX_ID);
        OBCustGrpIdentifierTrxValue trxValue = new OBCustGrpIdentifierTrxValue();
        trxValue.setTransactionID(trxID);
        return (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, trxValue), param);
    }


    public ICustGrpIdentifierTrxValue checkerRejectUpdateCustGrpIdentifier
            (ITrxContext ctx,
             ICustGrpIdentifierTrxValue aTrxValue)
            throws CustGrpIdentifierException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT);
        return (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, aTrxValue), param);

    }


    public ICustGrpIdentifierTrxValue makerCancelUpdateCustGrpIdentifier
            (ITrxContext ctx,
             ICustGrpIdentifierTrxValue aTrxValue)
            throws CustGrpIdentifierException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CANCEL);
        return (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, aTrxValue), param);

    }


    public SearchResult searchEntryDetails(GroupMemberSearchCriteria criteria)
            throws CustGrpIdentifierException {

        try {
            SBCustGrpIdentifierBusManager mgr = getBusManager();
            return mgr.searchEntryDetails(criteria);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }

    }

    public List setEntityDetails(List list)  throws CustGrpIdentifierException{

        try {
            SBCustGrpIdentifierBusManager mgr = getBusManager();
            return mgr.setEntityDetails(list);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }
    }


     public ICustGrpIdentifierTrxValue makerSaveCustGrpIdentifier(
            ITrxContext ctx,
            ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws CustGrpIdentifierException {

        checkParameters(ctx, aTrxValue, obj);
        aTrxValue = formulateTrxValue(ctx, aTrxValue, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_SAVE_CUST_GRP_IDENTIFIER);
        return operate(aTrxValue, param);

    }


     public Map getGroupAccountMgrCodes(Map inputMap)
            throws CustGrpIdentifierException {

        try {
            SBCustGrpIdentifierBusManager mgr = getBusManager();
            return mgr.getGroupAccountMgrCodes(inputMap);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }

    }

     public Amount getGroupLimit(String intLmt, String rating)
            throws CustGrpIdentifierException {

        try {
            SBCustGrpIdentifierBusManager mgr = getBusManager();
            return mgr.getGroupLimit(intLmt, rating);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }

    }

     public SearchResult searchGroup(CustGrpIdentifierSearchCriteria criteria)
            throws CustGrpIdentifierException{

         try {
            SBCustGrpIdentifierBusManager mgr = getBusManager();
            return mgr.searchGroup(criteria);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }

     }

    //  End here , Business Method for Group Profile


    /**
     * Used in ListCounterpartyCommand  for
     * search CCI customer and customer
     *
     * @param ctx
     * @return
     * @throws CustGrpIdentifierException
     */
    public ICustGrpIdentifierTrxValue checkerApproveUpdateCCI
            (ITrxContext ctx,
             ICustGrpIdentifierTrxValue aTrxValue)
            throws CustGrpIdentifierException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE);
        return (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, aTrxValue), param);

    }


    public ICustGrpIdentifierTrxValue getCustGrpIdentifierByGroupCCINo(
            ITrxContext ctx, String groupCCINo)
            throws CustGrpIdentifierException {
        ICustGrpIdentifier obj = null;
        try {
            SBCustomerManager mgr = getCustomerManager();
        } catch (CustGrpIdentifierException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }

        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_CCIN_BY_GROUP_CCINO);
        OBCustGrpIdentifierTrxValue trxValue = new OBCustGrpIdentifierTrxValue();
        trxValue.setReferenceID(obj.getGrpID() + "");
        ICustGrpIdentifierTrxValue newTransValue = (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, trxValue), param);
        newTransValue.setCustGrpIdentifier(obj);
        return newTransValue;

    }


    public ICCICounterpartyDetails getCCICounterpartyDetails
            (String groupCCINo)
            throws CustGrpIdentifierException {
        try {
            SBCustomerManager mgr = getCustomerManager();
            return mgr.getCCICounterpartyDetails(groupCCINo);
        } catch (CustGrpIdentifierException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }
    }


    public ICustGrpIdentifierTrxValue makerSubmitICCICustomer(
            ITrxContext ctx,
            ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws CustGrpIdentifierException {

        checkParameters(ctx, aTrxValue, obj);
        aTrxValue = formulateTrxValue(ctx, aTrxValue, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CC_COUNTER_PARTY);
        return operate(aTrxValue, param);

    }

    public ICustGrpIdentifierTrxValue makerDeleteICCICustomer(
            ITrxContext ctx,
            ICustGrpIdentifierTrxValue aTrxValue,
            ICustGrpIdentifier obj)
            throws CustGrpIdentifierException {

        checkParameters(ctx, aTrxValue, obj);
        aTrxValue = formulateTrxValue(ctx, aTrxValue, obj);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_CC_COUNTER_PARTY);
        return operate(aTrxValue, param);

    }


    public ICustGrpIdentifierTrxValue makerCancelUpdateCCI
            (ITrxContext ctx,
             ICustGrpIdentifierTrxValue aTrxValue) throws CustGrpIdentifierException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CANCEL);
        return (ICustGrpIdentifierTrxValue) operate(constructTrxValue(ctx, aTrxValue), param);

    }

    public List retrieveMasterGroupBySubGroupID(List subgroupIDList) throws CustGrpIdentifierException {
        try {
            CustGrpIdentifierDAO dao = new CustGrpIdentifierDAO();
            return dao.retrieveMasterGroupBySubGroupID(subgroupIDList);
        } catch (SearchDAOException e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }
    }

    public boolean groupHasLimitBooking(List grpIDList) throws CustGrpIdentifierException {
        try {
            LimitBookingDAO dao = new LimitBookingDAO();
            return dao.groupHasLimitBooking(grpIDList);
        } catch (LimitBookingException e) {
            e.printStackTrace();
            throw new CustGrpIdentifierException("Caught Exception!", e);
        }
    }

    // End here Business Logic

    protected SBCMSTrxManager getTrxManager() throws TrxOperationException {
        SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController .getEJB(ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME, SBCMSTrxManagerHome.class.getName());
        if (null == mgr) {
            throw new TrxOperationException("SBCMSTrxManager is null!");
        } else {
            return mgr;
        }
    }


    protected SBCustGrpIdentifierBusManager getBusManager() throws CustGrpIdentifierException {
        SBCustGrpIdentifierBusManager theEjb = (SBCustGrpIdentifierBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CUST_GRP_IDENTIFIER_BUS_MANAGER_JNDI, SBCustGrpIdentifierBusManagerHome .class.getName());

        if (theEjb == null)
            throw new CustGrpIdentifierException("AbstractCustGrpIdentifierProxy for Staging is null!");

        return theEjb;
    }


    protected SBCCICounterpartyDetailsBusManager getBusManager1() throws CustGrpIdentifierException {
        SBCCICounterpartyDetailsBusManager theEjb = (SBCCICounterpartyDetailsBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CCI_COUNTERPARTY_DETAILS_BUS_MANAGER_JNDI, SBCCICounterpartyDetailsBusManagerHome .class.getName());

        if (theEjb == null)
            throw new CustGrpIdentifierException("AbstractCustGrpIdentifierProxy for Staging is null!");

        return theEjb;
    }

    private SBCustomerManager getCustomerManager() throws Exception {
        SBCustomerManager home = (SBCustomerManager) BeanController.
                getEJB(ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI, SBCustomerManagerHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new Exception("SBCustomerManager for Actual is null!");
        }
    }

    /**
     * @param anICMSTrxValue
     * @param anOBCMSTrxParameter - OBCMSTrxParameter
     * @return ICMSTrxResult - the trx result interface
     */
    protected ITrxValue operateForResult(
            ICMSTrxValue anICMSTrxValue,
            OBCMSTrxParameter anOBCMSTrxParameter)
            throws CustGrpIdentifierException {


        try {
            ITrxController controller = (new CustGrpIdentifierTrxControllerFactory()).getController(
                    anICMSTrxValue, anOBCMSTrxParameter);

            if (controller == null) {
                throw new CustGrpIdentifierException("ITrxController is null!!!");
            }


            ITrxResult result = controller.operate(anICMSTrxValue, anOBCMSTrxParameter);

            ITrxValue obj = result.getTrxValue();
            ICMSTrxValue cmsTrxValue1 = (ICMSTrxValue) obj;
            if (cmsTrxValue1 != null) {
                Debug("cmsTrxValue1.getCurrentTrxHistoryID() = " + cmsTrxValue1.getCurrentTrxHistoryID());
            }
            return obj;


        } catch (TransactionException e) {
            rollback();
            throw new CustGrpIdentifierException(e);
        } catch (Exception ex) {
            rollback();
            throw new CustGrpIdentifierException(ex.toString());
        }
    }

    protected abstract void rollback()
            throws CustGrpIdentifierException;

    /**
     * Formulate the checklist Trx Object
     *
     * @param ctx            - ITrxContext
     * @param anICMSTrxValue - ICMSTrxValue
     * @return IUnitTrustFeedGroupTrxValue - the checklist trx interface formulated
     */
    protected ICustGrpIdentifierTrxValue formulateTrxValue(
            ITrxContext ctx,
            ICMSTrxValue anICMSTrxValue,
            ICustGrpIdentifier obj) {

        ICustGrpIdentifierTrxValue trxValue = null;

        if (anICMSTrxValue != null) {
            trxValue = new OBCustGrpIdentifierTrxValue(anICMSTrxValue);
        } else {
            trxValue = new OBCustGrpIdentifierTrxValue();
        }
        trxValue = formulateTrxValue(ctx, (ICustGrpIdentifierTrxValue) trxValue);
        trxValue.setStagingCustGrpIdentifier(obj);
        return trxValue;
    }

    /**
     * Formulate the checklist trx object
     *
     * @param anITrxContext - ITrxContext
     * @param aTrxValue     -
     * @return ICheckListTrxValue - the checklist trx interface formulated
     */
    protected ICustGrpIdentifierTrxValue formulateTrxValue(
            ITrxContext anITrxContext,
            ICustGrpIdentifierTrxValue aTrxValue) {
        aTrxValue.setTrxContext(anITrxContext);
        aTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUST_GRP_IDENTIFIER);

        return aTrxValue;
    }

    /**
     * Helper method to contruct transaction value.
     *
     * @param ctx       of type ITrxContext
     * @param aTrxValue of type ITrxValue
     * @return transaction value
     */
    protected ICustGrpIdentifierTrxValue constructTrxValue(
            ITrxContext ctx,
            ICustGrpIdentifierTrxValue aTrxValue) {
        aTrxValue.setTrxContext(ctx);
        aTrxValue.setTransactionType(ICMSConstant.INSTANCE_CUST_GRP_IDENTIFIER);
        return aTrxValue;
    }


    /**
     * Helper method to perform the document item transactions.
     *
     * @param aTrxValue           - IDocumentItemTrxValue
     * @param anOBCMSTrxParameter - OBCMSTrxParameter
     * @return IUnitTrustFeedGroupTrxValue - the trx interface
     */
    protected ICustGrpIdentifierTrxValue operate(
            ICustGrpIdentifierTrxValue aTrxValue,
            OBCMSTrxParameter anOBCMSTrxParameter)
            throws CustGrpIdentifierException {
        ITrxValue trxValue = operateForResult(aTrxValue, anOBCMSTrxParameter);
        return (ICustGrpIdentifierTrxValue) trxValue;
    }


    private void checkParameters(ITrxContext ctx,
                                 ICustGrpIdentifierTrxValue trxValue,
                                 ICustGrpIdentifier aCustGrpIdentifier)
            throws CustGrpIdentifierException {

        if (trxValue == null) {
            throw new CustGrpIdentifierException("The ICustGrpIdentifierTrxValue to be updated is null!!!");
        }
        if (aCustGrpIdentifier == null) {
            throw new CustGrpIdentifierException("The ICustGrpIdentifier to be updated is null !!!");
        }
    }


    private void Debug(String msg) {
    	DefaultLogger.debug(this,"AbstractCustGrpIdentifierProxy = " + msg);
    }


}
