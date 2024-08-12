package com.integrosys.cms.app.segmentWiseEmail.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.productMaster.bus.ProductMasterException;
import com.integrosys.cms.app.segmentWiseEmail.bus.ISegmentWiseEmail;
import com.integrosys.cms.app.segmentWiseEmail.bus.SegmentWiseEmailException;
import com.integrosys.cms.app.segmentWiseEmail.bus.SegmentWiseEmailReplicationUtils;

public class CheckerApproveCreateSegmentWiseEmailOperation extends AbstractSegmentWiseEmailTrxOperation{

	public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_SEGMENT_WISE_EMAIL;
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
        ISegmentWiseEmailTrxValue trxValue = getSegmentWiseEmailTrxValue(anITrxValue);
      try{
        trxValue = createActualSegmentWiseEmail(trxValue);
        trxValue = updateSegmentWiseEmailTrx(trxValue);
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
     * @throws SegmentWiseEmailException 
     */
    private ISegmentWiseEmailTrxValue createActualSegmentWiseEmail(ISegmentWiseEmailTrxValue idxTrxValue) 
    		throws SegmentWiseEmailException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            ISegmentWiseEmail staging = idxTrxValue.getStagingSegmentWiseEmail();
            // Replicating is necessary or else stale object error will arise
            ISegmentWiseEmail replicatedSegmentWiseEmail = SegmentWiseEmailReplicationUtils.replicateSegmentWiseEmailForCreateStagingCopy(staging);
            ISegmentWiseEmail actual = getSegmentWiseEmailBusManager().createSegmentWiseEmail(replicatedSegmentWiseEmail);
            idxTrxValue.setSegmentWiseEmail(actual);
            idxTrxValue.setReferenceID(String.valueOf(actual.getID()));
            getSegmentWiseEmailBusManager().updateSegmentWiseEmail(actual);
            return idxTrxValue;
        }
        catch (SegmentWiseEmailException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
