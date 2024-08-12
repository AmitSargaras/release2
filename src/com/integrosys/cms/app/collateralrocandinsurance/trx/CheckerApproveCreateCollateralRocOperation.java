package com.integrosys.cms.app.collateralrocandinsurance.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.CollateralRocException;
import com.integrosys.cms.app.collateralrocandinsurance.bus.CollateralRocReplicationUtils;
import com.integrosys.cms.app.collateralrocandinsurance.bus.ICollateralRoc;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class CheckerApproveCreateCollateralRocOperation extends AbstractCollateralRocTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_COLLATERAL_ROC;
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
        ICollateralRocTrxValue trxValue = getCollateralRocTrxValue(anITrxValue);
      try{
        trxValue = createActualCollateralRoc(trxValue);
        trxValue = updateCollateralRocTrx(trxValue);
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
     * @throws CollateralRocException 
     */
    private ICollateralRocTrxValue createActualCollateralRoc(ICollateralRocTrxValue idxTrxValue) throws CollateralRocException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            ICollateralRoc staging = idxTrxValue.getStagingCollateralRoc();
            // Replicating is necessary or else stale object error will arise
            ICollateralRoc replicatedCollateralRoc = CollateralRocReplicationUtils.replicateCollateralRocForCreateStagingCopy(staging);
            ICollateralRoc actual = getCollateralRocBusManager().createCollateralRoc(replicatedCollateralRoc);
            idxTrxValue.setCollateralRoc(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getCollateralRocBusManager().updateCollateralRoc(actual);
            return idxTrxValue;
        }
        catch (CollateralRocException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
