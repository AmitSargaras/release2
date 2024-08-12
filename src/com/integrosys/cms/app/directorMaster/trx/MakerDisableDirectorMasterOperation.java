package com.integrosys.cms.app.directorMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterReplicationUtils;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;

/**
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 * Maker Update operation to  update system 
 */
public class MakerDisableDirectorMasterOperation extends AbstractDirectorMasterTrxOperation {
    /**
     * Defaulc Constructor
     */
    public MakerDisableDirectorMasterOperation() {
        super();
    }

    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_DISABLE_DIRECTOR_MASTER;
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
        IDirectorMasterTrxValue trxValue = getDirectorMasterTrxValue(anITrxValue);
     
        return trxValue;
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
        IDirectorMasterTrxValue idxTrxValue = getDirectorMasterTrxValue(anITrxValue);
        IDirectorMaster stage = idxTrxValue.getStagingDirectorMaster();
        IDirectorMaster replicatedDirectorMaster = DirectorMasterReplicationUtils.replicateDirectorMasterForCreateStagingCopy(stage);
        replicatedDirectorMaster.setDinNo(stage.getDinNo());
        idxTrxValue.setStagingDirectorMaster(replicatedDirectorMaster);
        IDirectorMasterTrxValue trxValue = disableStagingDirectorMaster(idxTrxValue);
        trxValue = updateDirectorMasterTrx(trxValue);
        return super.prepareResult(trxValue);
    }
}
