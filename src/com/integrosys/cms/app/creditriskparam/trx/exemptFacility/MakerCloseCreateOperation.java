/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * This operation class is invoked by maker to close Exempted Institution rejected by a checker.
 *
 * @author   $Author: pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public class MakerCloseCreateOperation extends AbstractExemptFacilityTrxOperation
{
    /**
     * Default constructor.
     */
    public MakerCloseCreateOperation()
    {}

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CLOSE_EXEMPT_FACILITY;
    }

    /**
     * The following tasks are performed:
     *
     * 1. create staging Exempted Institution record
     * 2. update Transaction record
     *
     * @param value is of type ITrxValue
     * @return transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException on error updating the transaction
     */
    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
            IExemptFacilityGroupTrxValue trxValue = super.getExemptFacilityGroupTrxValue (value);
            //trxValue = super.createStagingExemptFacilityGroup (trxValue);
            trxValue = super.updateTransaction(trxValue);
            return super.prepareResult(trxValue);
        }
        catch(TrxOperationException e) {
            throw e;
        }
        catch(Exception e) {
            throw new TrxOperationException("Caught Exception!", e);
        }
    }
}
