package com.integrosys.cms.app.geography.city.trx;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.geography.region.bus.IRegion;


/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class MakerEditRejectedCreateCityOperation extends AbstractCityTrxOperation{

	/**
     * Defaulc Constructor
     */
     public MakerEditRejectedCreateCityOperation()
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
         return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_CITY;
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
