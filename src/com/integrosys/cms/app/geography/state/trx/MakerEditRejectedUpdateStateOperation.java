package com.integrosys.cms.app.geography.state.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.state.bus.IState;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class MakerEditRejectedUpdateStateOperation extends AbstractStateTrxOperation{

	 /**
     * Defaulc Constructor
     */
     public MakerEditRejectedUpdateStateOperation()
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
         return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_STATE;
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
     	IStateTrxValue idxTrxValue = getStateTrxValue(anITrxValue);
         IState stage = idxTrxValue.getStagingState();
         
         idxTrxValue.setStagingState(stage);

         IStateTrxValue trxValue = createStagingState(idxTrxValue);
         trxValue = updateStateTrx(trxValue);
         return super.prepareResult(trxValue);
     }
}
