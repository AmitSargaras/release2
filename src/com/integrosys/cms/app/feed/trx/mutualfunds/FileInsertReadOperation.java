package com.integrosys.cms.app.feed.trx.mutualfunds;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * @author govind.sahu
 * Read CreditApproval Operation 
 */

public class FileInsertReadOperation extends AbstractMutualFundsTrxOperation implements ITrxReadOperation {
    /**
     * Default Constructor
     */
    public FileInsertReadOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_FILE_INSERT;
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
            ICMSTrxValue trxValue = (ICMSTrxValue) getTrxManager().getTransaction(anITrxValue.getTransactionID());

            OBMutualFundsFeedGroupTrxValue newValue = new OBMutualFundsFeedGroupTrxValue(trxValue);

            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();

            DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

            if (stagingRef != null) {
                long stagingPK = (new Long(stagingRef)).longValue();
                IFileMapperId mutualFunds = getStagingMutualFundsFeedFileMapperIdBusManager().getInsertFileById(stagingPK);
                newValue.setStagingFileMapperID(mutualFunds);
            }else{
            	throw new TrxOperationException("Staging Reference Id is null");
            }

            if (actualRef != null) {
                long actualPK = (new Long(actualRef)).longValue();
                IFileMapperId creditApproval = getStagingMutualFundsFeedFileMapperIdBusManager().getInsertFileById(actualPK);
                newValue.setStagingFileMapperID(creditApproval);
            }
            return newValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex.getMessage());
        }
    }
}
