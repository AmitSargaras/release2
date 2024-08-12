package com.integrosys.cms.app.systemBank.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * @author abhijit.rudrakshawar
 * Read System Bank Operation By Trx vID
 */

public class ReadSystemBankIDOperation extends AbstractSystemBankTrxOperation implements ITrxReadOperation {
    /**
     * Default Constructor
     */
    public ReadSystemBankIDOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_SYSTEM_BANK_ID;
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
    public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
    	try {
            ICMSTrxValue trxValue = (ICMSTrxValue) getTrxManager().getTransaction(anITrxValue.getTransactionID());

            OBSystemBankTrxValue newValue = new OBSystemBankTrxValue(trxValue);

            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();

            DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

            if (stagingRef != null) {
                long stagingPK = (new Long(stagingRef)).longValue();
                newValue.setStagingSystemBank(getStagingSystemBankBusManager().getSystemBankById(stagingPK));
            }

            if (actualRef != null) {
                long actualPK = (new Long(actualRef)).longValue();
                newValue.setSystemBank(getSystemBankBusManager().getSystemBankById(actualPK));
            }
            return newValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex.toString());
        }
    }
}
