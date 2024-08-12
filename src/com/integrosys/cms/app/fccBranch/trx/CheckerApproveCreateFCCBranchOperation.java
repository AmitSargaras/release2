package com.integrosys.cms.app.fccBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchException;
import com.integrosys.cms.app.fccBranch.bus.FCCBranchReplicationUtils;
import com.integrosys.cms.app.fccBranch.bus.IFCCBranch;

/**
 * @author abhijit.rudrakshawar
 */

public class CheckerApproveCreateFCCBranchOperation extends AbstractFCCBranchTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_FCCBRANCH;
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
        IFCCBranchTrxValue trxValue = getFCCBranchTrxValue(anITrxValue);
      try{
        trxValue = createActualFCCBranch(trxValue);
        trxValue = updateFCCBranchTrx(trxValue);
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
     * @throws FCCBranchException 
     */
    private IFCCBranchTrxValue createActualFCCBranch(IFCCBranchTrxValue idxTrxValue) throws FCCBranchException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IFCCBranch staging = idxTrxValue.getStagingFCCBranch();
            // Replicating is necessary or else stale object error will arise
            IFCCBranch replicatedFCCBranch = FCCBranchReplicationUtils.replicateFCCBranchForCreateStagingCopy(staging);
            IFCCBranch actual = getFccBranchBusManager().createFCCBranch(replicatedFCCBranch);
            idxTrxValue.setFCCBranch(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getFccBranchBusManager().updateFCCBranch(actual);
            return idxTrxValue;
        }
        catch (FCCBranchException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
