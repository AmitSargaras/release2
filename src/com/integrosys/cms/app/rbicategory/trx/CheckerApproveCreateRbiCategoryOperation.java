package com.integrosys.cms.app.rbicategory.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;
import com.integrosys.cms.app.rbicategory.bus.IRbiCategory;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryException;
import com.integrosys.cms.app.rbicategory.bus.RbiCategoryReplicationUtils;



/**
 * @author Govind.Sahu
 * Abstract Rbi Category 
 */

public class CheckerApproveCreateRbiCategoryOperation extends AbstractRbiCategoryTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_RBI_CATEGORY;
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
        IRbiCategoryTrxValue trxValue = getRbiCategoryTrxValue(anITrxValue);
      try{
        trxValue = createActualRbiCategory(trxValue);
        trxValue = updateRbiCategoryTrx(trxValue);
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
     * @throws RbiCategoryException 
     */
    private IRbiCategoryTrxValue createActualRbiCategory(IRbiCategoryTrxValue idxTrxValue) throws RbiCategoryException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IRbiCategory staging = idxTrxValue.getStagingRbiCategory();
            IRbiCategory replicatedRbiCategory = RbiCategoryReplicationUtils.replicateRbiCategoryForCreateStagingCopy(staging);
            IRbiCategory actual = getRbiCategoryBusManager().createRbiCategory(replicatedRbiCategory);
            idxTrxValue.setRbiCategory(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
            return idxTrxValue;
        }
        catch (PropertyIdxException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
