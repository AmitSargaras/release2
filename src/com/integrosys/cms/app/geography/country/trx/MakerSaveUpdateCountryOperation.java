package com.integrosys.cms.app.geography.country.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.geography.country.bus.CountryReplicationUtils;
import com.integrosys.cms.app.geography.country.bus.ICountry;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerSaveUpdateCountryOperation extends AbstractCountryTrxOperation{

	/**
     * Defaulc Constructor
     */
    public MakerSaveUpdateCountryOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_UPDATE_SAVE_COUNTRY;
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
    	ICountryTrxValue idxTrxValue = getCountryTrxValue(anITrxValue);
    	ICountry stage = idxTrxValue.getStagingCountry();
    	ICountry replicatedCountry = CountryReplicationUtils.replicateCountryForCreateStagingCopy(stage);
        idxTrxValue.setStagingCountry(replicatedCountry);

        ICountryTrxValue trxValue = createStagingCountry(idxTrxValue);
        trxValue = updateCountryTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
