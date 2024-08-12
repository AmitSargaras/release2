package com.integrosys.cms.app.discrepency.trx;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;


/**
 * 
 * @author sandiip.shinde
 * @since 01-06-2011
 */

public class MakerEditRejectedCreateDiscrepencyOperation extends AbstractDiscrepencyTrxOperation{

	/**
     * Defaulc Constructor
     */
     public MakerEditRejectedCreateDiscrepencyOperation()
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
         return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_DISCREPENCY;
     }

     /**
     * Process the transaction
     * 1.    Create Staging record
     * 2.    Update the transaction record
     * @param anITrxValue - ITrxValue
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
     */
     public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
     {
         IDiscrepencyTrxValue idxTrxValue = getDiscrepencyTrxValue(anITrxValue);
         IDiscrepency stage = idxTrxValue.getStagingDiscrepency();
         idxTrxValue.setStagingDiscrepency(stage);

         IDiscrepencyTrxValue trxValue = createStagingDiscrepency(idxTrxValue);
         trxValue = updateDiscrepencyTrx(trxValue);
         return super.prepareResult(trxValue);
     }
}
