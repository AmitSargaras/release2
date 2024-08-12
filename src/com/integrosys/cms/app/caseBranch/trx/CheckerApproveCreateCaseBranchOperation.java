package com.integrosys.cms.app.caseBranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchException;
import com.integrosys.cms.app.caseBranch.bus.CaseBranchReplicationUtils;
import com.integrosys.cms.app.caseBranch.bus.ICaseBranch;

/**
 * @author abhijit.rudrakshawar
 */

public class CheckerApproveCreateCaseBranchOperation extends AbstractCaseBranchTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CASEBRANCH;
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
        ICaseBranchTrxValue trxValue = getCaseBranchTrxValue(anITrxValue);
      try{
        trxValue = createActualCaseBranch(trxValue);
        trxValue = updateCaseBranchTrx(trxValue);
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
     * @throws CaseBranchException 
     */
    private ICaseBranchTrxValue createActualCaseBranch(ICaseBranchTrxValue idxTrxValue) throws CaseBranchException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            ICaseBranch staging = idxTrxValue.getStagingCaseBranch();
            // Replicating is necessary or else stale object error will arise
            ICaseBranch replicatedCaseBranch = CaseBranchReplicationUtils.replicateCaseBranchForCreateStagingCopy(staging);
            ICaseBranch actual = getCaseBranchBusManager().createCaseBranch(replicatedCaseBranch);
            idxTrxValue.setCaseBranch(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getCaseBranchBusManager().updateCaseBranch(actual);
            return idxTrxValue;
        }
        catch (CaseBranchException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
