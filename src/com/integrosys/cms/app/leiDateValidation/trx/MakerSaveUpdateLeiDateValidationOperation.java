package com.integrosys.cms.app.leiDateValidation.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.bus.LeiDateValidationReplicationUtils;
import com.integrosys.cms.app.leiDateValidation.trx.AbstractLeiDateValidationTrxOperation;
import com.integrosys.cms.app.leiDateValidation.trx.ILeiDateValidationTrxValue;

public class MakerSaveUpdateLeiDateValidationOperation extends AbstractLeiDateValidationTrxOperation  {

	/**
	 * Defaulc Constructor
	 */
	public MakerSaveUpdateLeiDateValidationOperation() {
		super();
	}
	
	/**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_LEI_DATE_VALIDATION;
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
        ILeiDateValidationTrxValue idxTrxValue = getLeiDateValidationTrxValue(anITrxValue);
        ILeiDateValidation stage = idxTrxValue.getStagingLeiDateValidation();
        ILeiDateValidation replicatedLeiDateValidation = LeiDateValidationReplicationUtils.replicateLeiDateValidationForCreateStagingCopy(stage);
        idxTrxValue.setStagingLeiDateValidation(replicatedLeiDateValidation);
        ILeiDateValidationTrxValue trxValue = createStagingLeiDateValidation(idxTrxValue);
        trxValue = updateLeiDateValidationTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
