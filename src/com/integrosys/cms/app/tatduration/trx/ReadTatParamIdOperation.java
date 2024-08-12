package com.integrosys.cms.app.tatduration.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.tatduration.bus.ITatParamConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 18, 2008
 */

public class ReadTatParamIdOperation extends AbstractTatParamTrxOperation implements ITrxReadOperation 
{
    /**
     * Default Constructor
     */
    public ReadTatParamIdOperation() 
    {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() 
    {
        return ITatParamConstant.ACTION_READ_TAT_PARAM;
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
    public ITrxValue getTransaction(ITrxValue val) throws TransactionException
    {
        try 
        {
            ICMSTrxValue trxValue = super.getCMSTrxValue(val);
            trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(), trxValue.getTransactionType());
            OBTatParamTrxValue newValue = new OBTatParamTrxValue(trxValue);

            String stagingRef = trxValue.getStagingReferenceID();
            String actualRef = trxValue.getReferenceID();

            DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

            if (null != stagingRef) 
                newValue.setStagingTatParam(getStageTatParamBusManager().getTatParam(new Long(stagingRef)));

            if (null != actualRef) 
                newValue.setTatParam(getTatParamBusManager().getTatParam(new Long(actualRef)));

            return newValue;
        }
        catch (Exception ex) 
        {
            throw new TrxOperationException(ex);
        }
    }
}
