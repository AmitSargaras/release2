package com.integrosys.cms.app.directorMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 04 May 2011) $
 * Tag : $Name$
 * Checker reject Operation to reject update made by maker
 */

public class CheckerRejectDirectorMasterOperation extends AbstractDirectorMasterTrxOperation{

    public CheckerRejectDirectorMasterOperation()
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
        return ICMSConstant.ACTION_CHECKER_REJECT_DIRECTOR_MASTER;
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
        IDirectorMasterTrxValue trxValue = super.getDirectorMasterTrxValue(anITrxValue);
        trxValue = super.updateDirectorMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
