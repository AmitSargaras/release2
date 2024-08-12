package com.integrosys.cms.app.custgrpi.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by a checker to approve Counterparty Details updated by a maker.
 *
 * @author $Author: Jitu<br>
 * @version $Revision: $
 * @since $Date: $
 *        Tag:      $Name: $
 */
public class MakerCancelUpdateCustGrpIdentifierOperation extends AbstractCustGrpIdentifierTrxOperation {
    /**
     * Default constructor.
     */
    public MakerCancelUpdateCustGrpIdentifierOperation() {
    }

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CANCEL;
    }

    /**
     * The following tasks are performed:
     * <p/>
     * 1. update actual Counterparty Details record
     * 2. update Transaction record
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          on error updating the transaction
     */

    public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
        try {
            ICustGrpIdentifierTrxValue trxValue = super.getCustGrpIdentifierTrxValue(value);
            //trxValue = super.createStagingCounterpartyDetails(trxValue);
            DefaultLogger.debug(this,"MakerCancelUpdateCounterpartyDetailsOperation = ");
            trxValue = super.updateTransaction(trxValue);
            return super.prepareResult(trxValue);
        }
        catch (TrxOperationException e) {
            throw e;
        }
        catch (Exception e) {
            throw new TrxOperationException("Exception caught!", e);
        }
    }
}
