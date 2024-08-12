package com.integrosys.cms.app.systemBankBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchReplicationUtils;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 18, 2008
 */

public class CheckerApproveCreateSystemBankBranchOperation extends AbstractSystemBankBranchTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_SYSTEM_BANK_BRANCH;
    }

    /**
     * Process the transaction
     * 1.	Create the actual data
     * 2.	Update the transaction record
     *
     * @param anITrxValue of ITrxValue type
     * @return ITrxResult - the transaction result
     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
     *          if encounters any error during the processing of the transaction
     */
    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        ISystemBankBranchTrxValue trxValue = getSystemBankBranchTrxValue(anITrxValue);
      try{
        trxValue = actualSystemBankBranch(trxValue);
        trxValue = updateSystemBankBranchTrx(trxValue);
      }catch (TrxOperationException e) {
  		throw new TrxOperationException(e.getMessage());
  	}
      catch (Exception e) {
    	  throw new TrxOperationException(e.getMessage());
	}
       
        return super.prepareResult(trxValue);
    }


    /**
     * Create the actual property index
     *
     * @param anITrxValue of ITrxValue type
     * @return ICCDocumentLocationTrxValue - the document item trx value
     * @throws ConcurrentUpdateException 
     * @throws TransactionException 
     * @throws TrxParameterException 
     * @throws SystemBankBranchException 
     */
    private ISystemBankBranchTrxValue actualSystemBankBranch(ISystemBankBranchTrxValue idxTrxValue) throws SystemBankBranchException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            ISystemBankBranch staging = idxTrxValue.getStagingSystemBankBranch();
            ISystemBankBranch replicatedSystemBankBranch = SystemBankBranchReplicationUtils.replicateSystemBankBranchForCreateStagingCopy(staging);
            ISystemBankBranch actual = getSystemBankBranchBusManager().createSystemBankBranch(replicatedSystemBankBranch);
            idxTrxValue.setSystemBankBranch(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getSystemBankBranchBusManager().updateSystemBankBranch(actual);
            return idxTrxValue;
        }
        catch (PropertyIdxException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
