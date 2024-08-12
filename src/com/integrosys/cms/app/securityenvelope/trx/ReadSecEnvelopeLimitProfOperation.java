package com.integrosys.cms.app.securityenvelope.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Feb 9, 2010
 */

public class ReadSecEnvelopeLimitProfOperation extends AbstractSecEnvelopeTrxOperation implements ITrxReadOperation {
    /**
     * Default Constructor
     */
    public ReadSecEnvelopeLimitProfOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_SECENVELOPE_LIMITPROFILE;
    }

    /**
     * This method is used to read a transaction object
     *
     * @param val is the ITrxValue object containing the parameters required for
     *            retrieving a record, such as the transaction ID.
     * @return ITrxValue containing the requested data.
     * @throws com.integrosys.base.businfra.transaction.TransactionException
     *          if any other errors occur.
     */
    public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
        try {
            ICMSTrxValue trxValue = super.getCMSTrxValue(val);
            trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(), trxValue.getTransactionType());
            OBSecEnvelopeTrxValue newValue = new OBSecEnvelopeTrxValue(trxValue);

            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();

            DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

            if (null != stagingRef) {
                long stagingPK = Long.parseLong(stagingRef);
                newValue.setStagingSecEnvelope(getStagingSecEnvelopeBusManager().getSecEnvelope(stagingPK));
            }

            if (null != actualRef) {
                long actualPK = Long.parseLong(actualRef);

                newValue.setSecEnvelope(getSecEnvelopeBusManager().getSecEnvelope(actualPK));
            }

            return newValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
}
