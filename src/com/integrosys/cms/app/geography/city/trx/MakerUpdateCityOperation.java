package com.integrosys.cms.app.geography.city.trx;

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

public class MakerUpdateCityOperation extends AbstractCityTrxOperation{
	
	/**
     * Defaulc Constructor
     */
    public MakerUpdateCityOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_CITY;
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
        ICityTrxValue trxValue = getCityTrxValue((anITrxValue));
        ICity staging = trxValue.getStagingCity();
        try {
            if (staging != null) {
                if (staging.getIdCity() != ICMSConstant.LONG_INVALID_VALUE) {
                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(staging.getIdCity()), ICMSConstant.INSTANCE_CITY);
                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
                }                
            }
            return trxValue;
        }
        
        catch (Exception ex) {
            throw new TrxOperationException("Exception in preProcess: " + ex.toString());
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
