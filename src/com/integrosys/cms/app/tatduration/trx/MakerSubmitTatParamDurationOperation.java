package com.integrosys.cms.app.tatduration.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.ITatParamConstant;
import com.integrosys.cms.app.tatduration.bus.TatParamReplicationUtils;

/**
 * Describe this class. Purpose: Description:
 *
 * @author Cynthia<br>
 * @version R1.1
 * Date: Sep 1, 2008
 */
public class MakerSubmitTatParamDurationOperation extends AbstractTatParamTrxOperation 
{
	/**
	 * Default Constructor
	 */
	public MakerSubmitTatParamDurationOperation() {
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() 
	{
		return ITatParamConstant.ACTION_MAKER_SUBMIT_TAT_DURATION;
	}

	/**
	 * Process the transaction 1. Create staging record 2. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        DefaultLogger.debug(this, "in MakerSaveStageDurationOperation");
        anITrxValue = super.preProcess(anITrxValue);
        ITatParamTrxValue trxValue = getTatParamTrxValue(anITrxValue);
        ITatParam tatParamReplicated = TatParamReplicationUtils.replicateTatParamForCreateStagingCopy(trxValue.getStagingTatParam());
        trxValue.setStagingTatParam(tatParamReplicated);
        trxValue = createStagingTatParam(trxValue);
		trxValue = updateTatParamTransaction(trxValue);
		
		return prepareResult(trxValue);
	}

}
