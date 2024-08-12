package com.integrosys.cms.app.otherbank.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.ui.otherbankbranch.IOtherBank;

/**
 * @author dattatray.thorat
 *Maker Edit Rejected operation to   create rejected record by checker
 */
public class MakerEditRejectedCreateOtherBankOperation extends AbstractOtherBankTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedCreateOtherBankOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_OTHER_BANK;
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
            IOtherBankTrxValue idxTrxValue = getOtherBankTrxValue(anITrxValue);
            IOtherBank stage = idxTrxValue.getStagingOtherBank();
            IOtherBank replicatedOtherBank= new OBOtherBank();
            replicatedOtherBank.setId(stage.getId());
            replicatedOtherBank.setAddress(stage.getAddress());
            replicatedOtherBank.setCity(stage.getCity());
            replicatedOtherBank.setContactMailId(stage.getContactMailId());
            replicatedOtherBank.setContactNo(stage.getContactNo());
            replicatedOtherBank.setCountry(stage.getCountry());
            replicatedOtherBank.setCreatedBy(stage.getCreatedBy());
            replicatedOtherBank.setCreationDate(stage.getCreationDate());
            replicatedOtherBank.setDeprecated(stage.getDeprecated());
            replicatedOtherBank.setLastUpdateBy(stage.getLastUpdateBy());
            replicatedOtherBank.setLastUpdateDate(stage.getLastUpdateDate());
            replicatedOtherBank.setFaxNo(stage.getFaxNo());
            replicatedOtherBank.setRegion(stage.getRegion());
            replicatedOtherBank.setState(stage.getState());
            replicatedOtherBank.setStatus(stage.getStatus());
            replicatedOtherBank.setOtherBankCode(stage.getOtherBankCode());
            replicatedOtherBank.setOtherBankName(stage.getOtherBankName());
            replicatedOtherBank.setVersionTime(stage.getVersionTime());
            
            idxTrxValue.setStagingOtherBank(replicatedOtherBank);

            IOtherBankTrxValue trxValue = createStagingOtherBank(idxTrxValue);
            trxValue = updateOtherBankTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
