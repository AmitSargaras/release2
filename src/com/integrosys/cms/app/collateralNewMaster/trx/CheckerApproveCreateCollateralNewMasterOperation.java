package com.integrosys.cms.app.collateralNewMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterException;
import com.integrosys.cms.app.collateralNewMaster.bus.CollateralNewMasterReplicationUtils;
import com.integrosys.cms.app.collateralNewMaster.bus.ICollateralNewMaster;

/**
 * @author abhijit.rudrakshawar
 */

public class CheckerApproveCreateCollateralNewMasterOperation extends AbstractCollateralNewMasterTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_COLLATERAL_NEW_MASTER;
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
        ICollateralNewMasterTrxValue trxValue = getCollateralNewMasterTrxValue(anITrxValue);
      try{
        trxValue = createActualCollateralNewMaster(trxValue);
        trxValue = updateCollateralNewMasterTrx(trxValue);
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
     * @throws CollateralNewMasterException 
     */
    private ICollateralNewMasterTrxValue createActualCollateralNewMaster(ICollateralNewMasterTrxValue idxTrxValue) throws CollateralNewMasterException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            ICollateralNewMaster staging = idxTrxValue.getStagingCollateralNewMaster();
            // Replicating is necessary or else stale object error will arise
            ICollateralNewMaster replicatedCollateralNewMaster = CollateralNewMasterReplicationUtils.replicateCollateralNewMasterForCreateStagingCopy(staging);
            ICollateralNewMaster actual = getCollateralNewMasterBusManager().createCollateralNewMaster(replicatedCollateralNewMaster);
            idxTrxValue.setCollateralNewMaster(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            getCollateralNewMasterBusManager().updateCollateralNewMaster(actual);
            return idxTrxValue;
        }
        catch (CollateralNewMasterException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
