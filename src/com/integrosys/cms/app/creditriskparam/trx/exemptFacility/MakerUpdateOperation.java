/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/MakerUpdateOperation.java,v 1.2 2003/08/06 05:42:09 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to update a checklist
 *
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/06 05:42:09 $
 * Tag: $Name:  $
 */
public class MakerUpdateOperation
        extends AbstractExemptFacilityTrxOperation {

    /**
     * Defaulc Constructor
     */
    public MakerUpdateOperation() {
        super();
    }


    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_EXEMPT_FACILITY;
    }


    /**
     * Process the transaction
     * 1.    Update the transaction record
     * @param value - ITrxValue
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess (ITrxValue value) throws TrxOperationException
    {
        try {
            DefaultLogger.debug(this,"value before super " + value);
            IExemptFacilityGroupTrxValue trxValue = super.getExemptFacilityGroupTrxValue(value);
            DefaultLogger.debug(this,"value after super " + trxValue);
            trxValue = super.createStagingExemptFacilityGroup(trxValue);
            DefaultLogger.debug(this,"value after createStagingExemptFacilityGroup " + trxValue);

            trxValue = super.updateTransaction(trxValue);
            DefaultLogger.debug(this,"value after updateTransaction " + trxValue);
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