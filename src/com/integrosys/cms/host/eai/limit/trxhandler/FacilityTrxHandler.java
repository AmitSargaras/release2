package com.integrosys.cms.host.eai.limit.trxhandler;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.transaction.OBTrxResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxLogException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.limit.bus.OBFacilityMaster;
import com.integrosys.cms.app.limit.proxy.IFacilityProxy;
import com.integrosys.cms.app.limit.trx.IFacilityTrxValue;
import com.integrosys.cms.app.limit.trx.OBFacilityTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAITransactionException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.AbstractCommonTrxHandler;
import com.integrosys.cms.host.eai.limit.AAMessageBody;
import com.integrosys.cms.host.eai.limit.bus.FacilityMaster;
import com.integrosys.cms.host.eai.limit.bus.LimitProfile;
import com.integrosys.cms.host.eai.limit.bus.Limits;

/**
 * EAI workflow handler for facility module.
 *
 * @author Chong Jun Yong
 */
public class FacilityTrxHandler extends AbstractCommonTrxHandler {

    private static final long serialVersionUID = -5503411850608051041L;

    private static final String FACILITY_TRX_KEY = "FACILITY.TRX.KEY";

    private static final String ACTION_SUBSCRIBE_CREATE = "SUBSCRIBE_CREATE";

    private IFacilityProxy facilityProxy;

    /**
     * List of application type, which are not including the facility master
     * information, for those application processing of the facility master need
     * to be skipped.
     */
    protected List facilityNotRequiredAppTypeList;

    /**
     * List of facility status to stp direct without user interference
     */
    private List facilityStatusDirectStpList;

    private boolean accessStpSystem = false;

    public IFacilityProxy getFacilityProxy() {
        return facilityProxy;
    }

    public List getFacilityNotRequiredAppTypeList() {
        return facilityNotRequiredAppTypeList;
    }

    public boolean isAccessStpSystem() {
        return accessStpSystem;
    }

    public void setFacilityProxy(IFacilityProxy facilityProxy) {
        this.facilityProxy = facilityProxy;
    }

    public void setFacilityNotRequiredAppTypeList(List facilityNotRequiredAppTypeList) {
        this.facilityNotRequiredAppTypeList = facilityNotRequiredAppTypeList;
    }

    public void setAccessStpSystem(boolean accessStpSystem) {
        this.accessStpSystem = accessStpSystem;
    }

    public List getFacilityStatusDirectStpList() {
        return facilityStatusDirectStpList;
    }

    public void setFacilityStatusDirectStpList(List facilityStatusDirectStpList) {
        this.facilityStatusDirectStpList = facilityStatusDirectStpList;
    }

    public Map getTransaction(Message msg, Map flatMessage) throws EAITransactionException, EAIMessageException {
        EAIMessage eaiMessage = (EAIMessage) msg;
        AAMessageBody aaMsgBody = (AAMessageBody) eaiMessage.getMsgBody();

        Map keyTrxValueMap = new HashMap();

        Vector limits = aaMsgBody.getLimits();

        if (limits == null) {
            limits = new Vector();
        }

        for (Iterator itr = limits.iterator(); itr.hasNext();) {
            Limits limit = (Limits) itr.next();
            FacilityMaster facility = limit.getFacilityMaster();

            IFacilityTrxValue trxValue = getFacilityProxy().retrieveFacilityMasterTransactionById(facility.getId());

            if ((trxValue == null) || StringUtils.isBlank(trxValue.getTransactionID())) {
                trxValue = new OBFacilityTrxValue();

                trxValue.setReferenceID(Long.toString(facility.getId()));

                IFacilityMaster facilityMaster = new OBFacilityMaster();
                facilityMaster.setId(facility.getId());

                trxValue.setFacilityMaster(facilityMaster);
                trxValue.setStatus(ICMSConstant.STATE_ND);
            }

            keyTrxValueMap.put(limit.getLimitGeneral().getLOSLimitId(), trxValue);
        }

        flatMessage.put(getTrxKey(), keyTrxValueMap);

        return flatMessage;
    }

    public String getTrxKey() {
        return FACILITY_TRX_KEY;
    }

    protected Map prepareTrxValuesMap(Message msg, Message stagingMsg, Map trxValuesMap) throws EAIMessageException {
        AAMessageBody aaMsgBody = (AAMessageBody) ((EAIMessage) msg).getMsgBody();
        AAMessageBody stgAaMsgBody = (AAMessageBody) ((EAIMessage) stagingMsg).getMsgBody();

        LimitProfile limitProfile = aaMsgBody.getLimitProfile();

        Vector stagingLimits = stgAaMsgBody.getLimits();
        if (stagingLimits == null)
            stagingLimits = new Vector();

        for (Iterator itr = stagingLimits.iterator(); itr.hasNext();) {
            Limits stagingLimit = (Limits) itr.next();
            FacilityMaster stagingFacility = stagingLimit.getFacilityMaster();

            IFacilityTrxValue trxValue = (IFacilityTrxValue) trxValuesMap.get(stagingLimit.getLimitGeneral()
                    .getLOSLimitId());
            IFacilityMaster stagingFacilityMaster = new OBFacilityMaster();
            stagingFacilityMaster.setId(stagingFacility.getId());

            trxValue.setStagingFacilityMaster(stagingFacilityMaster);
            trxValue.setStagingReferenceID(Long.toString(stagingFacilityMaster.getId()));

            populateCustomerAndAAInfo(trxValue, limitProfile);

            trxValuesMap.put(stagingLimit.getLimitGeneral().getLOSLimitId(), trxValue);
        }

        return trxValuesMap;
    }

    /**
     * Populate customer and AA info into facility trx value object for workflow
     * engine purpose.
     *
     * @param trxValue     the facility transaction value object
     * @param limitProfile limit profile
     */
    protected void populateCustomerAndAAInfo(IFacilityTrxValue trxValue, LimitProfile limitProfile) {
        OBTrxContext context = new OBTrxContext();

        OBCMSCustomer customer = new OBCMSCustomer();
        customer.setCustomerID(limitProfile.getCmsSubProfileId());

        context.setCustomer(customer);

        if (limitProfile.getOriginatingLocation() != null) {
            String origCountry = limitProfile.getOriginatingLocation().getOriginatingLocationCountry();
            String origOrganisation = limitProfile.getOriginatingLocation().getOriginatingLocationOrganisation();

            context.setTrxCountryOrigin(origCountry);
            context.setTrxOrganisationOrigin(origOrganisation);
        }

        trxValue.setLegalID(limitProfile.getCIFId());
        trxValue.setLimitProfileID(limitProfile.getLimitProfileId());
        trxValue.setLimitProfileReferenceNumber(limitProfile.getLOSAANumber());
        trxValue.setTrxContext(context);
        trxValue.setLoginId(limitProfile.getLoginId());
    }

    protected void hostExecution(Map trxValueMap, Message msg) throws EAITransactionException {

        AAMessageBody aaMsgBody = (AAMessageBody) ((EAIMessage) msg).getMsgBody();

        String aaType = aaMsgBody.getLimitProfile().getAAType();
        if (getFacilityNotRequiredAppTypeList().contains(aaType)) {
            return;
        }

        Vector limitVector = aaMsgBody.getLimits();
        if (limitVector == null)
            limitVector = new Vector();
        for (Iterator itr = limitVector.iterator(); itr.hasNext();) {
            Limits limit = (Limits) itr.next();

            IFacilityTrxValue trxValue = (IFacilityTrxValue) trxValueMap.get(limit.getLimitGeneral().getLOSLimitId());

            ICMSTrxValue storedTrxValue = null;
            if (StringUtils.isBlank(trxValue.getTransactionID())) {
                try {
                    // Andy Wong, 5 Jan 2009: update trx to LOADING for rejected and declined
                    // facility from LOS, direct Stp to SIBS
                    if (isAccessStpSystem()
                            && getFacilityStatusDirectStpList().contains(limit.getFacilityMaster().getFacilityStatusEntryCode())) {
                        trxValue.setFromState(ICMSConstant.STATE_PENDING_CREATE);
                        trxValue.setToState(ICMSConstant.STATE_LOADING);
                        trxValue.setOpDesc(ICMSConstant.ACTION_CHECKER_APPROVE_PASS_COL);
                    } else {
                        trxValue.setFromState(trxValue.getStatus());
                        trxValue.setToState(ICMSConstant.STATE_ACTIVE);
                        trxValue.setOpDesc(getOpDesc());
                    }

                    storedTrxValue = getCmsTrxManager().createTransaction(trxValue);
                    // Andy Wong, 5 Jan 2009: set transaction Id into trxValue
                    // OB to be used in stp later
                    trxValue.setTransactionID(storedTrxValue.getTransactionID());
                }
                catch (TransactionException ex) {
                    throw new EAITransactionException("failed to create workflow values [" + trxValue + "]", ex);
                }
                catch (RemoteException ex) {
                    throw new EAITransactionException("failed to create workflow values [" + trxValue
                            + "] using remote interface", ex.getCause());
                }
            } else {
                try {
                    // Andy Wong, 7 March 2009: cater for republished case when transaction existed
                    if (isAccessStpSystem()
                            && getFacilityStatusDirectStpList().contains(limit.getFacilityMaster().getFacilityStatusEntryCode())) {
                        trxValue.setFromState(ICMSConstant.STATE_PENDING_UPDATE);
                        trxValue.setToState(ICMSConstant.STATE_LOADING);
                        trxValue.setOpDesc(ICMSConstant.ACTION_CHECKER_APPROVE_PASS_COL);
                    }

                    // just to update transaction value, not need touch state
                    // changes.
                    storedTrxValue = getCmsTrxManager().updateTransaction(trxValue);
                }
                catch (TransactionException ex) {
                    throw new EAITransactionException("failed to update workflow values [" + trxValue + "]", ex);
                }
                catch (RemoteException ex) {
                    throw new EAITransactionException("failed to update workflow values [" + trxValue
                            + "] using remote interface", ex.getCause());
                }
            }

            OBTrxResult result = new OBTrxResult();
            result.setTrxValue(storedTrxValue);
            try {
                logProcess(result);
            }
            catch (TrxLogException ex) {
                throw new EAITransactionException("failed to log workflow values [" + trxValue + "]", ex);
            }
        }
    }

    public String getOperationName() {
        return null;
    }

    public String getOpDesc() {
        return ACTION_SUBSCRIBE_CREATE;
	}

}
