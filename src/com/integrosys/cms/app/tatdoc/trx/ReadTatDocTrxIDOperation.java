package com.integrosys.cms.app.tatdoc.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tatdoc.bus.ITatDoc;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 29, 2008
 * Time: 12:15:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReadTatDocTrxIDOperation extends AbstractTatDocTrxOperation implements ITrxReadOperation {

    /**
     * Default Constructor
     */
    public ReadTatDocTrxIDOperation() {
        super();
    }


    /**
     * Get the operation name of the current operation
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_READ_TAT_DOC_ID;
    }


    /**
     * This method is used to read a transaction object
     * @param anITrxValue - the ITrxValue object containing the parameters
     *        required for retrieving a record, such as the transaction ID.
     * @return ITrxValue - containing the requested data.
     * @throws com.integrosys.base.businfra.transaction.TransactionException if
     *         any other errors occur.
     */
    public ITrxValue getTransaction(ITrxValue anITrxValue) throws TransactionException {
        try {
            ICMSTrxValue trxValue = (ICMSTrxValue) anITrxValue;
            trxValue = getTrxManager().getTransaction(trxValue.getTransactionID());

            DefaultLogger.debug(this, ">>>>>>>>> Trx Value reference id = " + trxValue.getReferenceID() + "\n" + trxValue);

            OBTatDocTrxValue newValue = new OBTatDocTrxValue(trxValue);
            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();

            DefaultLogger.debug(this, ">>>>>>>>> Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);
            DefaultLogger.debug(this, ">>>>>>>>> FromState: " + trxValue.getFromState() + " , Status: " + trxValue.getStatus());

            if (stagingRef != null) {
                long stagingID = Long.parseLong(stagingRef);
                ITatDoc staging = getStageTatDocBusManager().getTatDocByID(stagingID);
                newValue.setStagingTatDoc(staging);
            }

            if (actualRef != null) {
                long actualID = Long.parseLong(actualRef);
                ITatDoc actual = getTatDocBusManager().getTatDocByID(actualID);
                newValue.setTatDoc(actual);
            }

            return newValue;
        }
        catch (Exception ex) {
            throw new TrxOperationException(ex.toString());
        }
    }




}
