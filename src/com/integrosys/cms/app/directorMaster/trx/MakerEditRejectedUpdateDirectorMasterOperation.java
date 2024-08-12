package com.integrosys.cms.app.directorMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;
import com.integrosys.cms.app.directorMaster.bus.OBDirectorMaster;

/**
 * Description:
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateDirectorMasterOperation extends AbstractDirectorMasterTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedUpdateDirectorMasterOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_DIRECTOR_MASTER;
        }

        /**
        * Process the transaction
        * 1.    Create Staging record
        * 2.    Update the transaction record
        * @param anITrxValue - ITrxValue
        * @return ITrxResult - the transaction result
        * @throws com.integrosys.base.businfra.transaction.TrxOperationException if encounters any error during the processing of the transaction
        */
        public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
        {
            IDirectorMasterTrxValue idxTrxValue = getDirectorMasterTrxValue(anITrxValue);
            IDirectorMaster stage = idxTrxValue.getStagingDirectorMaster();
            IDirectorMaster replicatedDirectorMaster= new OBDirectorMaster();
            replicatedDirectorMaster.setId(stage.getId());
            replicatedDirectorMaster.setDinNo((stage.getDinNo()));
            replicatedDirectorMaster.setName(stage.getName());
            replicatedDirectorMaster.setDirectorCode(stage.getDirectorCode());;
            replicatedDirectorMaster.setAction(stage.getAction());
            
            replicatedDirectorMaster.setCreateBy(stage.getCreateBy());
            replicatedDirectorMaster.setCreationDate(stage.getCreationDate());
            replicatedDirectorMaster.setDeprecated(stage.getDeprecated());
            replicatedDirectorMaster.setLastUpdateBy(stage.getLastUpdateBy());
            replicatedDirectorMaster.setLastUpdateDate(stage.getLastUpdateDate());
           
            
            replicatedDirectorMaster.setStatus(stage.getStatus());
            
            idxTrxValue.setStagingDirectorMaster(replicatedDirectorMaster);

            IDirectorMasterTrxValue trxValue = createStagingDirectorMaster(idxTrxValue);
            trxValue = updateDirectorMasterTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
