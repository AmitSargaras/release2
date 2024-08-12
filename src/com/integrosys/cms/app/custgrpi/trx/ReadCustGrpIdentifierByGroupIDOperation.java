package com.integrosys.cms.app.custgrpi.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifierBusManager;
import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;

/**
 * Created by IntelliJ IDEA.
 * User: Jitu
 * Date: Mar 26, 2007
 * Time: 11:51:10 AM
 */

public class ReadCustGrpIdentifierByGroupIDOperation extends CMSTrxOperation implements ITrxReadOperation {

    /**
     * Default Constructor
     */
    public ReadCustGrpIdentifierByGroupIDOperation() {
        super();
    }


    /**
     * Get the operation name of the current operation.
     *
     * @return the operation name of the current operation
     */

    public String getOperationName() {
        return ICMSConstant.ACTION_READ_BY_GROUP_ID;
    }


    /**
     * @param val
     * @return
     * @throws com.integrosys.base.businfra.transaction.TransactionException
     */

    public ITrxValue getTransaction(ITrxValue val) throws TransactionException {

         Debug(" getTransactionID = " + val.getTransactionID());

        ICustGrpIdentifier stageRecords = null;
        ICustGrpIdentifier actualRecords = null;
        ICustGrpIdentifierBusManager mgr = null;

        String stagingRef = null;
        String actualRef = null;

        try {

            ICMSTrxValue trxValue = super.getCMSTrxValue(val);

            Debug(" cmsTrxValue getTransactionID " + trxValue.getTransactionID()+
                 "+ getReferenceID " + trxValue.getReferenceID() +
                 "+ getStagingReferenceID " + trxValue.getStagingReferenceID() +
                 "+" );

            ICMSTrxValue cmsTrxValue =   getTrxManager().findTrxByRefIDAndTrxType(trxValue.getReferenceID(), trxValue.getTransactionType());
            ICustGrpIdentifierTrxValue newTrxValue = new OBCustGrpIdentifierTrxValue(cmsTrxValue);

            if (newTrxValue != null) {
                stagingRef = cmsTrxValue.getStagingReferenceID();
                actualRef = cmsTrxValue.getReferenceID();
            }
            return newTrxValue;

        } catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }


      private void Debug(String msg) {
    	  DefaultLogger.debug(this,"SBCustGrpIdentifierBusManagerBean = " + msg);
    }

}
