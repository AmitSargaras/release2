package com.integrosys.cms.app.cci.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cci.bus.*;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.liquidation.bus.ILiquidationBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;


public class ReadCounterpartyDetailsOperation extends CMSTrxOperation implements ITrxReadOperation {

    public String getOperationName() {
        return ICMSConstant.ACTION_READ_CCI_COUNTER_PARTY;
    }

    public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
        try {


           // System.out.println("ReadCounterpartyDetailsOperation getTransaction start");
            ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(val);

            ICCICounterpartyDetailsTrxValue trxVal = (ICCICounterpartyDetailsTrxValue) val;
            String stagingRef = cmsTrxValue.getStagingReferenceID();
            String actualRef = cmsTrxValue.getReferenceID();

            long lactualRef = 0;
            if (actualRef != null)
                lactualRef = Long.parseLong(actualRef);
            DefaultLogger.debug(this, " lactualRef " + lactualRef);

            long lstagingRef = 0;
            if (stagingRef != null)
                lstagingRef = Long.parseLong(stagingRef);
            DefaultLogger.debug(this, " lstagingRef " + lstagingRef);

            ICCICounterpartyDetails stagingDetails = null;
            ICCICounterpartyDetails actualDetails = null;
            ILiquidationBusManager mgr1 = null;
            ICCICounterpartyDetailsBusManager mgr = null;
            boolean found = false;

            if (lstagingRef > 0) {
                // get staging CounterpartyDetails
                mgr = CCICounterpartyDetailsBusManagerFactory.getStagingCCICounterpartyDetailsBusManager();
                stagingDetails = mgr.getCCICounterpartyByGroupCCINo(lstagingRef);
                DefaultLogger.debug(this, "stagingDetails = " + stagingDetails);
            }

            if (lactualRef > 0) {
                // get actual CounterpartyDetails
                mgr = CCICounterpartyDetailsBusManagerFactory.getActualCCICounterpartyDetailsBusManager();
                actualDetails = mgr.getCCICounterpartyByGroupCCINo(lactualRef);
                DefaultLogger.debug(this, "actualDetails = " + actualDetails);
            }


            String actualRefID = null;
            if (actualDetails != null) {
                actualRefID = String.valueOf(actualDetails.getGroupCCINoRef());
                if (actualRefID != null) {
                    DefaultLogger.debug(this, "************ collateral id/ actualRefID" + actualRefID);

                    try {
                        cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(actualRefID, ICMSConstant.INSTANCE_LIQUIDATION);
                    }
                    catch (Exception e) {
                        // do nothing here coz the the first col Liqs created without trx
                    }
                }
                found = true;

            } else if (stagingDetails != null) {
                actualRefID = String.valueOf(stagingDetails.getGroupCCINoRef());
                if (actualRefID != null) {
                    DefaultLogger.debug(this, "************ group id/ stageRefID" + actualRefID);

                    try {
                        cmsTrxValue = getTrxManager().getTrxByStageRefIDAndTrxType(actualRefID, ICMSConstant.INSTANCE_LIQUIDATION);
                    }
                    catch (Exception e) {
                        // do nothing here coz the the first col Liqs created without trx
                    }
                }
                if (!trxVal.getStatus().equals(ICMSConstant.STATE_ND)) {
                    found = true;
                }

            }

             if (!found) {
                actualDetails = CCICounterpartyDetailsHelper.initialCounterpartyDetails(lactualRef);
            }

            ICCICounterpartyDetailsTrxValue newValue = new OBCCICounterpartyDetailsTrxValue(cmsTrxValue);

            if (stagingDetails == null){
                stagingDetails = actualDetails;
            }

             newValue.setCCICounterpartyDetails(actualDetails);
             newValue.setStagingCCICounterpartyDetails(stagingDetails);

            return newValue;

        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }


    /**
     * To get the remote handler for the staging stockFeedGroup session bean
     *
     * @return SBUnitTrustFeedBusManager - the remote handler for the staging stockFeedGroup session bean
     */
    private SBCCICounterpartyDetailsBusManager getStagingSBCCICounterpartyDetailsBusManager() {
        SBCCICounterpartyDetailsBusManager remote = (SBCCICounterpartyDetailsBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CCI_COUNTERPARTY_DETAILS_BUS_MANAGER_STAGING_JNDI,
                SBCCICounterpartyDetailsBusManagerHome.class.getName());
        return remote;
    }


    /**
     * To get the remote handler for the staging stockFeedGroup session bean
     *
     * @return SBUnitTrustFeedBusManager - the remote handler for the staging stockFeedGroup session bean
     */
    private SBCCICounterpartyDetailsBusManager getSBCCICounterpartyDetailsBusManager() {
        SBCCICounterpartyDetailsBusManager remote = (SBCCICounterpartyDetailsBusManager) BeanController.getEJB(
                ICMSJNDIConstant.SB_CCI_COUNTERPARTY_DETAILS_BUS_MANAGER_JNDI,
                SBCCICounterpartyDetailsBusManagerHome.class.getName());
        return remote;
    }

}
