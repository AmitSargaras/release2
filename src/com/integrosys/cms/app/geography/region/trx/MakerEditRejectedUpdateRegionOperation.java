package com.integrosys.cms.app.geography.region.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.region.bus.IRegion;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class MakerEditRejectedUpdateRegionOperation extends AbstractRegionTrxOperation{

	 /**
     * Defaulc Constructor
     */
     public MakerEditRejectedUpdateRegionOperation()
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
         return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_REGION;
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
     	IRegionTrxValue idxTrxValue = getRegionTrxValue(anITrxValue);
         IRegion stage = idxTrxValue.getStagingRegion();
         
         idxTrxValue.setStagingRegion(stage);

         IRegionTrxValue trxValue = createStagingRegion(idxTrxValue);
         trxValue = updateRegionTrx(trxValue);
         return super.prepareResult(trxValue);
     }
}
