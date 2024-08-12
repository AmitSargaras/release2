package com.integrosys.cms.app.cci.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsBusManagerFactory;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetailsBusManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Created by IntelliJ IDEA.
 * User: Jitu
 * Date: Mar 26, 2007
 * Time: 11:51:10 AM
 */

public class ReadCounterpartyDetailsByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {

    /**
     * Default Constructor
     */
    public ReadCounterpartyDetailsByTrxIDOperation() {
        super();
        //System.out.println("ReadCounterpartyDetailsByTrxIDOperation iNSIDE  Constructor");
    }


    /**
     * Get the operation name of the current operation.
     *
     * @return the operation name of the current operation
     */

    public String getOperationName() {
       // System.out.println("ReadCounterpartyDetailsByTrxIDOperation iNSIDE  getOperationName");
        return ICMSConstant.ACTION_READ_CCIN_BY_TRX_ID;
    }


    /**
     * @param val
     * @return
     * @throws TransactionException
     */

    public ITrxValue getTransaction(ITrxValue val) throws TransactionException {

       // System.out.println("ReadCounterpartyDetailsByTrxIDOperation getTransaction = " + val.getTransactionID());

        ICCICounterpartyDetails stageRecords = null;
        ICCICounterpartyDetails actualRecords = null;
        ICCICounterpartyDetailsBusManager mgr = null;

        String stagingRef = null;
        String actualRef = null;

        try {

            ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());

           // System.out.println("ReadCounterpartyDetailsByTrxIDOperation cmsTrxValue " + cmsTrxValue.getTrxReferenceID());

            ICCICounterpartyDetailsTrxValue newTrxValue = new OBCCICounterpartyDetailsTrxValue(cmsTrxValue);

            if (newTrxValue != null) {
                stagingRef = cmsTrxValue.getStagingReferenceID();
                actualRef = cmsTrxValue.getReferenceID();
            }

           /* System.out.println("ReadCounterpartyDetailsByTrxIDOperation. " +
                                "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);*/


            if (stagingRef != null) {
                mgr = CCICounterpartyDetailsBusManagerFactory.getStagingCCICounterpartyDetailsBusManager();
                //stageRecords = mgr.getCCICounterpartyByGroupCCINo(Long.parseLong(stagingRef));
                stageRecords = mgr.getCCICounterpartyByGroupCCINoRef(Long.parseLong(stagingRef));
                DefaultLogger.debug(this, "stageRecords = " + stageRecords);
                newTrxValue.setStagingCCICounterpartyDetails(stageRecords);
            }


            if (actualRef != null) {
                mgr = CCICounterpartyDetailsBusManagerFactory.getActualCCICounterpartyDetailsBusManager();
                //actualRecords = mgr.getCCICounterpartyByGroupCCINo(Long.parseLong(actualRef));
                actualRecords = mgr.getCCICounterpartyByGroupCCINoRef(Long.parseLong(actualRef));
                if (actualRecords == null) {
                    actualRecords = CCICounterpartyDetailsHelper.initialCounterpartyDetails(Long.parseLong(actualRef));
                }
                newTrxValue.setCCICounterpartyDetails(actualRecords);
            }


            //System.out.println("ReadCounterpartyDetailsByTrxIDOperation getTransaction Exist");

            return newTrxValue;

        } catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }


}
