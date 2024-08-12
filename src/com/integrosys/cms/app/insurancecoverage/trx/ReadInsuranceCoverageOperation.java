package com.integrosys.cms.app.insurancecoverage.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * @author dattatray.thorat
 * Read Insurance Coverage Operation 
 */

public class ReadInsuranceCoverageOperation extends AbstractInsuranceCoverageTrxOperation implements ITrxReadOperation {
    /**
     * Default Constructor
     */
    public ReadInsuranceCoverageOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_INSURANCE_COVERAGE;
    }

    /**
     * This method is used to read a transaction object
     *
     * @param anITrxValue the ITrxValue object containing the parameters required for
     *                    retrieving a record, such as the transaction ID.
     * @return ITrxValue - containing the requested data.
     * @throws com.integrosys.base.businfra.transaction.TransactionException
     *          if any other errors occur.
     */
    public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
    	try {
            ICMSTrxValue trxValue = super.getCMSTrxValue(anITrxValue);
            trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(), trxValue.getTransactionType());
            OBInsuranceCoverageTrxValue newValue = new OBInsuranceCoverageTrxValue(trxValue);

            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();

            DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

            if (null != stagingRef) {
                long stagingPK = Long.parseLong(stagingRef);
                newValue.setStagingInsuranceCoverage(getStagingInsuranceCoverageBusManager().getInsuranceCoverageById(stagingPK));
            }

            if (null != actualRef) {
                long actualPK = Long.parseLong(actualRef);

                newValue.setInsuranceCoverage(getInsuranceCoverageBusManager().getInsuranceCoverageById(actualPK));
            }

            return newValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex);
        }
    }
}
