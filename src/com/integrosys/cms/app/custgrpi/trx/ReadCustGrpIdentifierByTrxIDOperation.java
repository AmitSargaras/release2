package com.integrosys.cms.app.custgrpi.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custgrpi.bus.CustGrpIdentifierBusManagerFactory;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifierBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Created by IntelliJ IDEA.
 * User: Jitu
 * Date: Mar 26, 2007
 * Time: 11:51:10 AM
 */

public class ReadCustGrpIdentifierByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {

    /**
     * Default Constructor
     */
    public ReadCustGrpIdentifierByTrxIDOperation() {
        super();
    }


    /**
     * Get the operation name of the current operation.
     *
     * @return the operation name of the current operation
     */

    public String getOperationName() {
        return ICMSConstant.ACTION_READ_CCIN_BY_TRX_ID;
    }


    /**
     * @param val
     * @return
     * @throws TransactionException
     */

    public ITrxValue getTransaction(ITrxValue val) throws TransactionException {

        ICustGrpIdentifier stageRecords = null;
        ICustGrpIdentifier actualRecords = null;
        ICustGrpIdentifierBusManager mgr = null;

        String stagingRef = null;
        String actualRef = null;

        try {

            ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());

            ICustGrpIdentifierTrxValue newTrxValue = new OBCustGrpIdentifierTrxValue(cmsTrxValue);

            if (newTrxValue != null) {
                stagingRef = cmsTrxValue.getStagingReferenceID();
                actualRef = cmsTrxValue.getReferenceID();
            }

            Debug("Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);


            if (stagingRef != null) {
                mgr = CustGrpIdentifierBusManagerFactory.getStagingCustGrpIdentifierBusManager();
                stageRecords = mgr.getCustGrpIdentifierByTrxIDRef(Long.parseLong(stagingRef));
                DefaultLogger.debug(this, "stageRecords  getGroupName= " + stageRecords.getGroupName());
                newTrxValue.setStagingCustGrpIdentifier(stageRecords);
            }


            if (actualRef != null) {
                mgr = CustGrpIdentifierBusManagerFactory.getActualCustGrpIdentifierBusManager();
                actualRecords = mgr.getCustGrpIdentifierByTrxIDRef(Long.parseLong(actualRef));
                if (actualRecords == null) {
                    actualRecords = CustGrpIdentifierHelper.initialCustGrpIdentifier(Long.parseLong(actualRef));
                }
                newTrxValue.setCustGrpIdentifier(actualRecords);
            }

            return newTrxValue;

        } catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }

    /**
         *
         * @param msg
         */
        private void Debug(String msg) {
        	DefaultLogger.debug(this,"ReadCustGrpIdentifierByTrxIDOperation = " + msg);
        }



}
