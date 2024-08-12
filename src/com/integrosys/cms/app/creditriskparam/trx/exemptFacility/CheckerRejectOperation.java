/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/CheckerRejectOperation.java,v 1.6 2003/08/06 08:10:08 btchng Exp $
 */

package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

//java

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 *
 * @author $Author: lini $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/06 08:10:08 $
 * Tag: $Name:  $
 */
public class CheckerRejectOperation
        extends AbstractExemptFacilityTrxOperation {

    /**
     * Defaulc Constructor
     */
    public CheckerRejectOperation() {
        super();
    }


    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_REJECT_EXEMPT_FACILITY;
    }


    /**
     * Process the transaction
     * 1.    Update the transaction record
     * @param anITrxValue - ITrxValue
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue)
            throws TrxOperationException {
          try {
	        IExemptFacilityGroupTrxValue trxValue = super.getExemptFacilityGroupTrxValue(anITrxValue);
	        trxValue = super.updateTransaction (trxValue);
	        return super.prepareResult(trxValue);
	    }
	    catch (TrxOperationException e) {
	        throw e;
	    }
	    catch (Exception e) {
	        throw new TrxOperationException ("Exception caught!", e);
	    }
    }
}
