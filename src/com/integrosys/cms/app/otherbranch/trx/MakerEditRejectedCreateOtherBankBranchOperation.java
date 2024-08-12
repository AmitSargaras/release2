package com.integrosys.cms.app.otherbranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.otherbranch.bus.OBOtherBranch;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * @author dattatray.thorat
 *Maker Edit Rejected operation to   create rejected record by checker
 */
public class MakerEditRejectedCreateOtherBankBranchOperation extends AbstractOtherBankBranchTrxOperation{
    /**
        * Defaulc Constructor
        */
        public MakerEditRejectedCreateOtherBankBranchOperation()
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
            return ICMSConstant.ACTION_MAKER_EDIT_REJECTED_CREATE_OTHER_BANK_BRANCH;
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
            IOtherBankBranchTrxValue idxTrxValue = getOtherBankBranchTrxValue(anITrxValue);
            IOtherBranch stage = idxTrxValue.getStagingOtherBranch();
            IOtherBranch replicatedOtherBank= new OBOtherBranch();
            replicatedOtherBank.setId(stage.getId());
            replicatedOtherBank.setOtherBranchCode(stage.getOtherBranchCode());
            replicatedOtherBank.setOtherBranchName(stage.getOtherBranchName());
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
            replicatedOtherBank.setRbiCode(stage.getRbiCode());
            replicatedOtherBank.setRegion(stage.getRegion());
            replicatedOtherBank.setState(stage.getState());
            replicatedOtherBank.setStatus(stage.getStatus());
            replicatedOtherBank.setOtherBankCode(stage.getOtherBankCode());
            replicatedOtherBank.setVersionTime(stage.getVersionTime());
            
            idxTrxValue.setStagingOtherBranch(replicatedOtherBank);

            IOtherBankBranchTrxValue trxValue = createStagingOtherBankBranch(idxTrxValue);
            trxValue = updateOtherBankBranchTrx(trxValue);
            return super.prepareResult(trxValue);
        }

}
