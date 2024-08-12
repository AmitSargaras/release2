/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/MakerCreateOperation.java,v 1.2 2003/08/06 05:42:09 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

//java

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This operation allows a maker to submit an forex feed group
 * It is the same as the MakerUpdateOperation except that it transits to a different state and
 * routed to checker for approval, both of which is handled by transaction manager
 * As such, this class just returns a different operation name.
 *
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/06 05:42:09 $
 * Tag: $Name:  $
 */
public class MakerCreateOperation
        extends MakerUpdateOperation {

    /**
     * Defaulc Constructor
     */
    public MakerCreateOperation() {
        super();
    }


    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CREATE_EXEMPT_FACILITY;
    }

    public ITrxResult performProcess(ITrxValue value)
            throws TrxOperationException {
        try {
            IExemptFacilityGroupTrxValue trxValue =
                    (IExemptFacilityGroupTrxValue)(value);
            DefaultLogger.debug(this," inside performProcess before create staging");
            trxValue = createStagingExemptFacilityGroup(trxValue);

//			if (trxValue.getStatus().equals (ICMSConstant.STATE_ND))
//			    trxValue = (IExemptFacilityGroupTrxValue)super.createTransaction (trxValue);
//		    else
				trxValue = super.updateTransaction(trxValue);

            return prepareResult(trxValue);

        } catch (Exception e) {
            throw new TrxOperationException("Caught Exception!", e);
        }
    }

}