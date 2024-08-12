package com.integrosys.cms.app.tatdoc.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 29, 2008
 * Time: 3:41:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class MakerCloseUpdateTatDocOperation extends AbstractTatDocTrxOperation {

    /**
     * Default Constructor
     */
    public MakerCloseUpdateTatDocOperation() {
    }


    /**
     * Get the operation name of the current operation
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_TAT_DOC;
    }


	/**
	 * Process (Update) the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
	 */
     public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
         ITatDocTrxValue trxValue = getTatDocTrxValue(anITrxValue);
         DefaultLogger.debug(this, "in MakerCloseUpdateTatDocOperation");
         trxValue = updateTatDocTransaction(trxValue);
         return prepareResult(trxValue);
     }



}
