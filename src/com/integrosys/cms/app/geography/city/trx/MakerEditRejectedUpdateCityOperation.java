package com.integrosys.cms.app.geography.city.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.city.bus.ICity;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class MakerEditRejectedUpdateCityOperation extends AbstractCityTrxOperation{

	 /**
     * Defaulc Constructor
     */
     public MakerEditRejectedUpdateCityOperation()
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
         return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_CITY;
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
     	ICityTrxValue idxTrxValue = getCityTrxValue(anITrxValue);
        ICity stage = idxTrxValue.getStagingCity();
         
         idxTrxValue.setStagingCity(stage);

         ICityTrxValue trxValue = createStagingCity(idxTrxValue);
         trxValue = updateCityTrx(trxValue);
         return super.prepareResult(trxValue);
     }
}
