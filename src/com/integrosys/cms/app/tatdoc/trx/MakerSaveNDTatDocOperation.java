package com.integrosys.cms.app.tatdoc.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 *
 * @author Cynthia<br>
 * @version R1.1
 * Date: Sep 1, 2008
 */
public class MakerSaveNDTatDocOperation extends AbstractTatDocTrxOperation {

	/**
	 * Default Constructor
	 */
	public MakerSaveNDTatDocOperation() {
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_TAT_DOC;
	}

	/**
	 * Process the transaction 1. Create staging record 2. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        DefaultLogger.debug(this, "in MakerSaveNDTatDocOperation");
        ITatDocTrxValue trxValue = getTatDocTrxValue(anITrxValue);
        trxValue = createStagingTatDoc(trxValue);
		trxValue = createTatDocTransaction(trxValue);
		return prepareResult(trxValue);
	}

}
