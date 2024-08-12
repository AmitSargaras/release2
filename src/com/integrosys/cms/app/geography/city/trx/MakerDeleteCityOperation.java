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
import com.integrosys.cms.app.geography.city.bus.CityReplicationUtils;
import com.integrosys.cms.app.geography.city.bus.ICity;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * 
 * @author sandiip.shinde
 * @since 14-04-2011
 */

public class MakerDeleteCityOperation  extends AbstractCityTrxOperation{

	/**
     * Defaulc Constructor
     */
    public MakerDeleteCityOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_DELETE_CITY;
    }

    /**
     * Pre process.
     * Prepares the transaction object for persistance
     * Get the parent  transaction ID to be appended as trx parent ref
     *
     * @param anITrxValue is of type ITrxValue
     * @return ITrxValue
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          on error
     */
    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
        anITrxValue = super.preProcess(anITrxValue);
        ICityTrxValue trxValue = getCityTrxValue(anITrxValue);
        ICity staging = trxValue.getStagingCity();
        try {
            if (staging != null) {
                if (staging.getIdCity() != ICMSConstant.LONG_INVALID_VALUE) {
                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getIdCity()), ICMSConstant.INSTANCE_CITY);
                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
                }
                
            }else{
            	throw new TrxOperationException("Staging Value is null");
            }
            return trxValue;
        }
        
        catch (Exception ex) {
            throw new TrxOperationException("Exception in preProcess: " + ex.getMessage());
        }
    }

    /**
     * Process the transaction
     * 1.	Create the staging data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws TrxOperationException if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        ICityTrxValue idxTrxValue = getCityTrxValue(anITrxValue);
        ICity stage = idxTrxValue.getStagingCity();
        ICity replicatedCity = CityReplicationUtils.replicateCityForCreateStagingCopy(stage);
   
        idxTrxValue.setStagingCity(replicatedCity);

        ICityTrxValue trxValue = createStagingCity(idxTrxValue);
        trxValue = updateCityTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
