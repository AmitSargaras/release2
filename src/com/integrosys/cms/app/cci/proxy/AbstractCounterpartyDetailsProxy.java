package com.integrosys.cms.app.cci.proxy;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.*;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.cci.bus.*;
import com.integrosys.cms.app.cci.trx.CounterpartyDetailsTrxControllerFactory;
import com.integrosys.cms.app.cci.trx.ICCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.cci.trx.OBCCICounterpartyDetailsTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.customer.bus.OBCustomerSearchResult;
import com.integrosys.cms.app.customer.bus.SBCustomerManager;
import com.integrosys.cms.app.customer.bus.SBCustomerManagerHome;
import com.integrosys.cms.app.transaction.*;
import com.integrosys.cms.host.eai.customer.EAICustomerHelper;
import com.integrosys.cms.host.eai.customer.SearchDetailResult;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public abstract class AbstractCounterpartyDetailsProxy implements
        ICCICustomerProxy {

    /**
     * Formulate the checklist trx object
     *
     * @param anITrxContext -
     *                      ITrxContext
     * @param aTrxValue     -
     * @return ICheckListTrxValue - the checklist trx interface formulated
     */

    protected ICCICounterpartyDetailsTrxValue formulateTrxValue(
            ITrxContext anITrxContext, ICCICounterpartyDetailsTrxValue aTrxValue) {
        aTrxValue.setTrxContext(anITrxContext);
        aTrxValue.setTransactionType(ICMSConstant.INSTANCE_CCI_COUNTER_PARTY);

        return aTrxValue;
    }

    /**
     * @param anICMSTrxValue
     * @param anOBCMSTrxParameter -
     *                            OBCMSTrxParameter
     * @return ICMSTrxResult - the trx result interface
     */
    protected ITrxValue operateForResult(ICMSTrxValue anICMSTrxValue,
                                         OBCMSTrxParameter anOBCMSTrxParameter)
            throws CCICounterpartyDetailsException {

        try {
            ITrxController controller = (new CounterpartyDetailsTrxControllerFactory())
                    .getController(anICMSTrxValue, anOBCMSTrxParameter);

            if (controller == null) {
                throw new CCICounterpartyDetailsException(
                        "ITrxController is null!!!");
            }

            ITrxResult result = controller.operate(anICMSTrxValue,
                    anOBCMSTrxParameter);

            ITrxValue obj = result.getTrxValue();
            ICMSTrxValue cmsTrxValue1 = (ICMSTrxValue) obj;
            if (cmsTrxValue1 != null) {
//                System.out.println("cmsTrxValue1.getCurrentTrxHistoryID() = "+ cmsTrxValue1.getCurrentTrxHistoryID());
            }
            return obj;

        } catch (TransactionException e) {
            rollback();
            throw new CCICounterpartyDetailsException(e);
        } catch (Exception ex) {
            rollback();
            throw new CCICounterpartyDetailsException(ex.toString());
        }
    }

    protected abstract void rollback() throws CCICounterpartyDetailsException;

    /**
     * Formulate the checklist Trx Object
     *
     * @param ctx            -
     *                       ITrxContext
     * @param anICMSTrxValue -
     *                       ICMSTrxValue
     * @return IUnitTrustFeedGroupTrxValue - the checklist trx interface
     *         formulated
     */
    protected ICCICounterpartyDetailsTrxValue formulateTrxValue(
            ITrxContext ctx, ICMSTrxValue anICMSTrxValue,
            ICCICounterpartyDetails aICCICounterpartyDetails) {

        ICCICounterpartyDetailsTrxValue trxValue = null;

        if (anICMSTrxValue != null) {
            trxValue = new OBCCICounterpartyDetailsTrxValue(anICMSTrxValue);
        } else {
            trxValue = new OBCCICounterpartyDetailsTrxValue();
        }
        trxValue = formulateTrxValue(ctx,
                (ICCICounterpartyDetailsTrxValue) trxValue);

        trxValue.setStagingCCICounterpartyDetails(aICCICounterpartyDetails);
        // trxValue.setCCICounterpartyDetails(aICCICounterpartyDetails);

        return trxValue;
    }

    /**
     * Helper method to contruct transaction value.
     *
     * @param ctx       of type ITrxContext
     * @param aTrxValue of type ITrxValue
     * @return transaction value
     */
    protected ICCICounterpartyDetailsTrxValue constructTrxValue(
            ITrxContext ctx, ICCICounterpartyDetailsTrxValue aTrxValue) {
        aTrxValue.setTrxContext(ctx);
        aTrxValue.setTransactionType(ICMSConstant.INSTANCE_CCI_COUNTER_PARTY);
        return aTrxValue;
    }

    // Business Method Starts here

    /**
     * Used in ListCounterpartyCommand for search CCI customer and customer if
     * msgRefNo available , retrieve Result List else fire External Search
     * Message
     *
     * @param criteria
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */
    public SearchResult searchCCICustomer(CounterpartySearchCriteria criteria)
            throws CCICounterpartyDetailsException {
        try {

            boolean isDebug = PropertyManager.getInstance().getBoolean(
                    "customer.search.debug", false);

            //if (criteria.getCustomerSeach()) {
            //return getExternalSearchResult(criteria);
            //}
            //else{
            SBCCICounterpartyDetailsBusManager mgr = getBusManager();
            return mgr.searchCCICustomer(criteria);
            //}


        } catch (Exception e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }

    /*
      * (non-Javadoc)
      *
      * @see com.integrosys.cms.app.cci.proxy.ICCICustomerProxy#searchExternalCustomer(com.integrosys.cms.app.cci.bus.CounterpartySearchCriteria)
      */

    public String searchExternalCustomer(CounterpartySearchCriteria criteria)
            throws Exception {
        // TODO Auto-generated method stub
        // Issue Search

        EAICustomerHelper instance = EAICustomerHelper.getInstance();

        String msgRefNo = instance.searchCustomerMultiple(criteria
                .getCifSource(), criteria.getLegalID(), criteria.getDbKey(), criteria.getIdNO(),
                criteria.getCustomerName());

        return msgRefNo;

    }

    /**
     * if msgRefNo available , retrieve Result List else fire External Search
     * Message
     *
     * @param criteria
     * @return SearchResult
     */
    private SearchResult getExternalSearchResult(CounterpartySearchCriteria criteria)
            throws Exception {

        DefaultLogger.debug(this, "* getExternalSearchResult *");

        String msgRefNo = criteria.getMsgRefNo();

        if (StringUtils.isEmpty(msgRefNo))
            throw new Exception("Invalid MsgRefNo");

        List list = new ArrayList();

        EAICustomerHelper instance = EAICustomerHelper.getInstance();

        // SearchHeader
        // sh=instance.getSearchCustomerMultipleHeader(msgRefNo);

        // Retrieve Results based on MsgRefNo
        SearchDetailResult[] srs = new SearchDetailResult[0]; //instance
        //.getSearchCustomerMultipleResults(criteria.getMsgRefNo());


        for (int i = 0; i < srs.length; i++) {

            SearchDetailResult sdr = srs[i];

            com.integrosys.cms.app.customer.bus.OBCustomerSearchResult customer = new com.integrosys.cms.app.customer.bus.OBCustomerSearchResult();

            customer.setLegalName(sdr.getCustomerNameShort());
            customer.setLegalReference(sdr.getCIFId());
            customer.setIdNo(sdr.getIDNumber());
            customer.setIncorporationDate(sdr.getJDOBirthDate());


            // Set DOB
            try {
                // Set Date For Display Purpose
                //customer.setDob(DateUtil.formatDate("dd/MMM/yyyy",sdr.getJDOBirthDate()));
                customer.setDob(sdr.getJDOBirthDate());

            } catch (Exception e) {

            }

            //customer.setI
//			customer.setLeIDType(criteria.getCifSource());

            customer.setSourceID(criteria.getCifSource());

            //String add=sdr.getAddress();
            OBCustomerAddress add = new OBCustomerAddress();
            customer.setAddress(add);

            String addStr = StringUtils.defaultString(sdr.getAddress());

            if (addStr.length() > 0)
                add.setAddressLine1(StringUtils.substring(sdr.getAddress(), 0, 100));

            if (addStr.length() > 100)
                add.setAddressLine2(StringUtils.substring(sdr.getAddress(), 100, 200));

            customer.setCustomerName(sdr.getCustomerNameShort());

            list.add(customer);

        }

        SearchResult sr = new SearchResult(0, 0, 0, list);

        return sr;

    }

    /**
     * Used in ListCounterpartyCommand for search CCI customer and customer
     *
     * @param lmt_profile_id
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */
    public String searchCustomer(long lmt_profile_id)
            throws CCICounterpartyDetailsException {
        try {
            SBCCICounterpartyDetailsBusManager mgr = getBusManager();
            return mgr.searchCustomer(lmt_profile_id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }


    /**
     * Used in ListCounterpartyCommand for search CCI customer and customer
     *
     * @param cciObj
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */
    public HashMap isExistCCICustomer(long groupCCINo, String[] cciObj)
            throws CCICounterpartyDetailsException {
        try {
            SBCCICounterpartyDetailsBusManager mgr = getBusManager();
            return mgr.isExistCCICustomer(groupCCINo, cciObj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }


    /**
     * This helper method used to set address for the  customer in external search..
     *
     * @param customer
     * @return
     * @throws CCICounterpartyDetailsException
     *
     */

    private OBCustomerAddress setCustomerAddress(OBCustomerSearchResult customer) throws CCICounterpartyDetailsException {
        if (customer == null) {
            return null;
        }
        ICCICounterparty obj = new OBCCICounterparty();
        obj.setLmpLeID(customer.getLegalReference());
        obj.setSourceID(customer.getSourceID());

        try {
            SBCCICounterpartyDetailsBusManager mgr = getBusManager();
            return mgr.getCustomerAddress(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }

    }

    //

    protected SBCCICounterpartyDetailsBusManager getBusManager()
            throws CCICounterpartyDetailsException {
        SBCCICounterpartyDetailsBusManager theEjb = (SBCCICounterpartyDetailsBusManager) BeanController
                .getEJB(
                        ICMSJNDIConstant.SB_CCI_COUNTERPARTY_DETAILS_BUS_MANAGER_JNDI,
                        SBCCICounterpartyDetailsBusManagerHome.class.getName());

        if (theEjb == null)
            throw new CCICounterpartyDetailsException(
                    "SBCCICounterpartyDetailsBusManager for Staging is null!");

        return theEjb;
    }

    private SBCustomerManager getCustomerManager() throws Exception {
        SBCustomerManager home = (SBCustomerManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CUSTOMER_MGR_JNDI,
                SBCustomerManagerHome.class.getName());

        if (null != home) {
            return home;
        } else {
            throw new Exception("SBCustomerManager for Actual is null!");
        }
    }

    private ICCICounterpartyDetailsTrxValue setCustomerAddress(ICCICounterpartyDetailsTrxValue trxValue) {
        try {
            if (trxValue.getStagingCCICounterpartyDetails() != null) {
                ICCICounterparty[] staging = trxValue.getStagingCCICounterpartyDetails().getICCICounterparty();
                if (staging != null) {
                    for (int i = 0; i < staging.length; i++) {
                        ICCICounterpartyDetailsBusManager mgr = CCICounterpartyDetailsBusManagerFactory.getActualCCICounterpartyDetailsBusManager();
                        OBCustomerAddress oAddress = mgr.getCustomerAddress(staging[i]);
                        staging[i].setAddress(oAddress);
                    }
                    trxValue.getStagingCCICounterpartyDetails().setICCICounterparty(staging);
                }
            }

            if (trxValue.getCCICounterpartyDetails() != null) {
                ICCICounterparty[] actual = trxValue.getCCICounterpartyDetails().getICCICounterparty();
                if (actual != null) {
                    for (int i = 0; i < actual.length; i++) {
                        ICCICounterpartyDetailsBusManager mgr = CCICounterpartyDetailsBusManagerFactory.getActualCCICounterpartyDetailsBusManager();
                        OBCustomerAddress oAddress = mgr.getCustomerAddress(actual[i]);
                        actual[i].setAddress(oAddress);
                    }
                    trxValue.getCCICounterpartyDetails().setICCICounterparty(actual);
                }
            }


        } catch (Exception e) {

        }
        return trxValue;
    }

    public ICCICounterpartyDetailsTrxValue getCCICounterpartyDetailsByTrxID(
            ITrxContext ctx, String trxID)
            throws CCICounterpartyDetailsException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_CCIN_BY_TRX_ID);
        OBCCICounterpartyDetailsTrxValue trxValue = new OBCCICounterpartyDetailsTrxValue();
        trxValue.setTransactionID(trxID);
        ICCICounterpartyDetailsTrxValue trxNew = (ICCICounterpartyDetailsTrxValue) operate(constructTrxValue(ctx,
                trxValue), param);
        trxNew = setCustomerAddress(trxNew);
        return trxNew;
    }

    public ICCICounterpartyDetailsTrxValue getCCICounterpartyDetailsByGroupCCINo(
            ITrxContext ctx, String groupCCINo)
            throws CCICounterpartyDetailsException {
        ICCICounterpartyDetails details = null;
        try {
            SBCustomerManager mgr = getCustomerManager();
            details = mgr.getCCICounterpartyDetails(groupCCINo);
        } catch (CCICounterpartyDetailsException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }

        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_READ_CCIN_BY_GROUP_CCINO);
        OBCCICounterpartyDetailsTrxValue trxValue = new OBCCICounterpartyDetailsTrxValue();
        // trxValue.setReferenceID(details.getGroupCCINoRef()+"");
        // trxValue.setTrxReferenceID(details.getGroupCCINoRef()+"");

        // trxValue.setStagingReferenceID(details.getGroupCCINoRef()+"");
        // todo to check modified to setReferenceID
        trxValue.setReferenceID(details.getGroupCCINoRef() + "");

        ICCICounterpartyDetailsTrxValue newTransValue = (ICCICounterpartyDetailsTrxValue) operate(
                constructTrxValue(ctx, trxValue), param);
        newTransValue.setCCICounterpartyDetails(details);
        newTransValue = setCustomerAddress(newTransValue);
        return newTransValue;

    }

    public ICCICounterpartyDetails getCCICounterpartyDetails(String groupCCINo)
            throws CCICounterpartyDetailsException {
        try {
            SBCustomerManager mgr = getCustomerManager();
            return mgr.getCCICounterpartyDetails(groupCCINo);
        } catch (CCICounterpartyDetailsException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CCICounterpartyDetailsException("Caught Exception!", e);
        }
    }

    public ICCICounterpartyDetailsTrxValue makerSubmitICCICustomer(
            ITrxContext ctx, ICCICounterpartyDetailsTrxValue aTrxValue,
            ICCICounterpartyDetails counterpartyDetails)
            throws CCICounterpartyDetailsException {

        checkParameters(ctx, aTrxValue, counterpartyDetails);
        aTrxValue = formulateTrxValue(ctx, aTrxValue, counterpartyDetails);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_CC_COUNTER_PARTY);
        return operate(aTrxValue, param);

    }

    public ICCICounterpartyDetailsTrxValue makerDeleteICCICustomer(
            ITrxContext ctx, ICCICounterpartyDetailsTrxValue aTrxValue,
            ICCICounterpartyDetails counterpartyDetails)
            throws CCICounterpartyDetailsException {

        checkParameters(ctx, aTrxValue, counterpartyDetails);
        aTrxValue = formulateTrxValue(ctx, aTrxValue, counterpartyDetails);
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_DELETE_CC_COUNTER_PARTY);
        return operate(aTrxValue, param);

    }

    public ICCICounterpartyDetailsTrxValue checkerApproveUpdateCCI(
            ITrxContext ctx, ICCICounterpartyDetailsTrxValue aTrxValue)
            throws CCICounterpartyDetailsException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE);
        return (ICCICounterpartyDetailsTrxValue) operate(constructTrxValue(ctx,
                aTrxValue), param);

    }

    public ICCICounterpartyDetailsTrxValue checkerRejectUpdateCCI(
            ITrxContext ctx, ICCICounterpartyDetailsTrxValue aTrxValue)
            throws CCICounterpartyDetailsException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_CHECKER_REJECT);
        return (ICCICounterpartyDetailsTrxValue) operate(constructTrxValue(ctx,
                aTrxValue), param);

    }

    public ICCICounterpartyDetailsTrxValue makerCancelUpdateCCI(
            ITrxContext ctx, ICCICounterpartyDetailsTrxValue aTrxValue)
            throws CCICounterpartyDetailsException {
        OBCMSTrxParameter param = new OBCMSTrxParameter();
        param.setAction(ICMSConstant.ACTION_MAKER_CANCEL);
        return (ICCICounterpartyDetailsTrxValue) operate(constructTrxValue(ctx,
                aTrxValue), param);

    }

    /**
     * Helper method to perform the document item transactions.
     *
     * @param aTrxValue           -
     *                            IDocumentItemTrxValue
     * @param anOBCMSTrxParameter -
     *                            OBCMSTrxParameter
     * @return IUnitTrustFeedGroupTrxValue - the trx interface
     */
    protected ICCICounterpartyDetailsTrxValue operate(
            ICCICounterpartyDetailsTrxValue aTrxValue,
            OBCMSTrxParameter anOBCMSTrxParameter)
            throws CCICounterpartyDetailsException {
        ITrxValue trxValue = operateForResult(aTrxValue, anOBCMSTrxParameter);
        return (ICCICounterpartyDetailsTrxValue) trxValue;
    }

    // helper method

    protected SBCMSTrxManager getTrxManager() throws TrxOperationException {
        SBCMSTrxManager mgr = (SBCMSTrxManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CMS_TRX_MGR_HOME, SBCMSTrxManagerHome.class
                        .getName());
        if (null == mgr) {
            throw new TrxOperationException("SBCMSTrxManager is null!");
        } else {
            return mgr;
        }
    }

    private void checkParameters(ITrxContext ctx,
                                 ICCICounterpartyDetailsTrxValue aICCICounterpartyDetailsTrxValue,
                                 ICCICounterpartyDetails aICCICounterpartyDetails)
            throws CCICounterpartyDetailsException {

        if (aICCICounterpartyDetailsTrxValue == null) {
            throw new CCICounterpartyDetailsException(
                    "The ICCICounterpartyDetailsTrxValue to be updated is null!!!");
		}
		if (aICCICounterpartyDetails == null) {
			throw new CCICounterpartyDetailsException(
					"The ICCICounterpartyDetails to be updated is null !!!");
		}
	}

}
