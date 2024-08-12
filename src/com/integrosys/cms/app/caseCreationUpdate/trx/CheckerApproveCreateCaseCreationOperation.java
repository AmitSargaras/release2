package com.integrosys.cms.app.caseCreationUpdate.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationException;
import com.integrosys.cms.app.caseCreationUpdate.bus.CaseCreationReplicationUtils;
import com.integrosys.cms.app.caseCreationUpdate.bus.ICaseCreation;

/**
 * @author abhijit.rudrakshawar
 */

public class CheckerApproveCreateCaseCreationOperation extends AbstractCaseCreationTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CASECREATION;
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
        ICaseCreationTrxValue trxValue = getCaseCreationTrxValue(anITrxValue);
      try{
        trxValue = createActualCaseCreation(trxValue);
        trxValue = updateCaseCreationTrx(trxValue);
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
     * @throws CaseCreationException 
     */
    private ICaseCreationTrxValue createActualCaseCreation(ICaseCreationTrxValue idxTrxValue) throws CaseCreationException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            ICaseCreation staging = idxTrxValue.getStagingCaseCreation();
            // Replicating is necessary or else stale object error will arise
            ICaseCreation replicatedCaseCreation = CaseCreationReplicationUtils.replicateCaseCreationForCreateStagingCopy(staging);
            ICaseCreation actual = getCaseCreationBusManager().createCaseCreation(replicatedCaseCreation);
            idxTrxValue.setCaseCreation(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getCaseCreationBusManager().updateCaseCreation(actual);
            return idxTrxValue;
        }
        catch (CaseCreationException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
