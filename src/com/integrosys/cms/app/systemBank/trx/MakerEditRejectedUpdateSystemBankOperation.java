package com.integrosys.cms.app.systemBank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.OBSystemBank;

/**
 * @author abhijit.rudrakshawar
 *Maker Edit Rejected operation to   update rejected record by checker
 */
public class MakerEditRejectedUpdateSystemBankOperation extends AbstractSystemBankTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedUpdateSystemBankOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_UPDATE_SYSTEM_BANK;
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
            ISystemBankTrxValue idxTrxValue = getSystemBankTrxValue(anITrxValue);
            ISystemBank stage = idxTrxValue.getStagingSystemBank();
            ISystemBank replicatedSystemBank= new OBSystemBank();
            replicatedSystemBank.setId(stage.getId());
            replicatedSystemBank.setAddress(stage.getAddress());
            replicatedSystemBank.setCityTown(stage.getCityTown());
            replicatedSystemBank.setContactMail(stage.getContactMail());
            replicatedSystemBank.setContactNumber(stage.getContactNumber());
            replicatedSystemBank.setFaxNumber(stage.getFaxNumber());
            replicatedSystemBank.setCountry(stage.getCountry());
            replicatedSystemBank.setCreateBy(stage.getCreateBy());
            replicatedSystemBank.setCreationDate(stage.getCreationDate());
            replicatedSystemBank.setDeprecated(stage.getDeprecated());
            replicatedSystemBank.setLastUpdateBy(stage.getLastUpdateBy());
            replicatedSystemBank.setLastUpdateDate(stage.getLastUpdateDate());
            replicatedSystemBank.setMasterId(stage.getMasterId());
            replicatedSystemBank.setRegion(stage.getRegion());
            replicatedSystemBank.setState(stage.getState());
            replicatedSystemBank.setStatus(stage.getStatus());
            replicatedSystemBank.setSystemBankCode(stage.getSystemBankCode());
            replicatedSystemBank.setSystemBankName(stage.getSystemBankName());
            replicatedSystemBank.setVersionTime(stage.getVersionTime());
            
            idxTrxValue.setStagingSystemBank(replicatedSystemBank);

            ISystemBankTrxValue trxValue = createStagingSystemBank(idxTrxValue);
            trxValue = updateSystemBankTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
