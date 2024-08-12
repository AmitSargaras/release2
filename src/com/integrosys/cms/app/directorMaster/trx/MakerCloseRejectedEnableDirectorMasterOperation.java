package com.integrosys.cms.app.directorMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
  * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 * Maker Close operation to remove  update rejected by checker
 */

public class MakerCloseRejectedEnableDirectorMasterOperation extends AbstractDirectorMasterTrxOperation{

    private static final String DEFAULT_OPERATION_NAME = ICMSConstant.ACTION_MAKER_CLOSE_REJECTED_ENABLE_DIRECTOR_MASTER;

    private String operationName;

    /**
    * Defaulc Constructor
    */
    public MakerCloseRejectedEnableDirectorMasterOperation()
    {
        operationName = DEFAULT_OPERATION_NAME;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /**
    * Process the transaction
    * 1.    Update the transaction record
    * @param anITrxValue - ITrxValue
    * @return ITrxResult - the transaction result
    * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
    */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
    {
        IDirectorMasterTrxValue trxValue = super.getDirectorMasterTrxValue (anITrxValue);
        trxValue = updateDirectorMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
