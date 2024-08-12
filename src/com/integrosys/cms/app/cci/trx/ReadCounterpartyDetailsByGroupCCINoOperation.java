package com.integrosys.cms.app.cci.trx;

import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetailsBusManager;
import com.integrosys.cms.app.cci.bus.CCICounterpartyDetailsBusManagerFactory;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Created by IntelliJ IDEA.
 * User: Jitu
 * Date: Mar 26, 2007
 * Time: 11:51:10 AM
 */

public class ReadCounterpartyDetailsByGroupCCINoOperation extends CMSTrxOperation implements ITrxReadOperation {

    /**
     * Default Constructor
     */
    public ReadCounterpartyDetailsByGroupCCINoOperation() {
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
        return ICMSConstant.ACTION_READ_CCIN_BY_GROUP_CCINO;
    }


    /**
     * @param val
     * @return
     * @throws com.integrosys.base.businfra.transaction.TransactionException
     */

    public ITrxValue getTransaction(ITrxValue val) throws TransactionException {

        //System.out.println("ReadCounterpartyDetailsByGroupCCINoOperation getTransactionID = " + val.getTransactionID());

        ICCICounterpartyDetails stageRecords = null;
        ICCICounterpartyDetails actualRecords = null;
        ICCICounterpartyDetailsBusManager mgr = null;

        String stagingRef = null;
        String actualRef = null;

        try {

            ICMSTrxValue trxValue = super.getCMSTrxValue(val);


          /*  System.out.println("ReadCounterpartyDetailsByGroupCCINoOperation cmsTrxValue getTransactionID " + trxValue.getTransactionID()+
                                                                                     "+ getReferenceID " + trxValue.getReferenceID() +
                                                                                     "+ getStagingReferenceID " + trxValue.getStagingReferenceID() +
                                                                                       "+" );
*/
//            ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());
            //ICMSTrxValue cmsTrxValue =   getTrxManager().getTrxByStageRefIDAndTrxType(trxValue.getStagingReferenceID(), trxValue.getTransactionType());
            // todo to check modified to setReferenceID
            ICMSTrxValue cmsTrxValue =   getTrxManager().findTrxByRefIDAndTrxType(trxValue.getReferenceID(), trxValue.getTransactionType());


            ICCICounterpartyDetailsTrxValue newTrxValue = new OBCCICounterpartyDetailsTrxValue(cmsTrxValue);

            if (newTrxValue != null) {
                stagingRef = cmsTrxValue.getStagingReferenceID();
                actualRef = cmsTrxValue.getReferenceID();
            }

           // System.out.println("ReadCounterpartyDetailsByGroupCCINoOperation. " +  "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);


           /* if (stagingRef != null) {
                mgr = CCICounterpartyDetailsBusManagerFactory.getStagingCCICounterpartyDetailsBusManager();
                //stageRecords = mgr.getCCICounterpartyByGroupCCINo(Long.parseLong(stagingRef));
                stageRecords = mgr.getCCICounterpartyByGroupCCINoRef(Long.parseLong(stagingRef));
                DefaultLogger.debug(this, "stageRecords = " + stageRecords);
                newTrxValue.setStagingCCICounterpartyDetails(stageRecords);
            }*/


           /* if (actualRef != null) {
                mgr = CCICounterpartyDetailsBusManagerFactory.getActualCCICounterpartyDetailsBusManager();
                //actualRecords = mgr.getCCICounterpartyByGroupCCINo(Long.parseLong(actualRef));
                actualRecords = mgr.getCCICounterpartyByGroupCCINoRef(Long.parseLong(actualRef));
                if (actualRecords == null) {
                    actualRecords = CCICounterpartyDetailsHelper.initialCounterpartyDetails(Long.parseLong(actualRef));
                }
                newTrxValue.setCCICounterpartyDetails(actualRecords);
            }*/


//            System.out.println("ReadCounterpartyDetailsByGroupCCINoOperation getTransaction Exist");

            return newTrxValue;

        } catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }


}
