/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/CheckerApproveUpdateOperation.java,v 1.8 2005/08/30 09:48:22 hshii Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve the checklist updating
 *
 * @author $Author: lini $
 * @version $Revision: 1.8 $
 * @since $Date: 2005/08/30 09:48:22 $
 * Tag: $Name:  $
 */
public class CheckerApproveUpdateOperation
        extends AbstractExemptFacilityTrxOperation {

    /**
     * Default Constructor
     */
    public CheckerApproveUpdateOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_EXEMPT_FACILITY;
    }

    /**
     * Process the transaction
     * 1.	Update the actual data
     * 2.	Update the transaction record
     * @param anITrxValue - ITrxValue
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue)
            throws TrxOperationException {
        IExemptFacilityGroupTrxValue trxValue = super.getExemptFacilityGroupTrxValue(anITrxValue);
        trxValue = createStagingExemptFacilityGroup(trxValue);
        if ( trxValue.getExemptFacilityGroup() == null ||
                (trxValue.getExemptFacilityGroup() != null && trxValue.getExemptFacilityGroup().getExemptFacility().length == 0 )) {
            trxValue = super.createActualExemptFacilityGroup (trxValue);
        }
        else
        {
            trxValue = super.updateActualExemptFacilityGroup (trxValue);
        }
        trxValue = super.updateTransaction (trxValue);
        return super.prepareResult(trxValue);
    }
}