package com.integrosys.cms.app.commoncode.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class MakerUpdateCommonCodeTypeOperation extends AbstractCommonCodeTypeTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerUpdateCommonCodeTypeOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_COMMON_CODE_TYPE;
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICommonCodeTypeTrxValue trxValue = super.getCommonCodeTypeTrxValue(anITrxValue);
		trxValue = createStagingCommonCodeType(trxValue);
		trxValue = updateCommonCodeTypeTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}
