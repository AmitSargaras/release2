package com.integrosys.cms.app.otherbranch.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.otherbank.bus.OtherBankException;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;

public class CheckerApproveInsertOtherBankBranchMasterOperation extends AbstractOtherBankBranchTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_INSERT_APPROVE_MASTER;
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
        IOtherBankBranchTrxValue trxValue = getOtherBankBranchTrxValue(anITrxValue);   
      try{
        trxValue = actualMaster(trxValue);
        trxValue = updateOtherBankBranchMasterInsertTrx(trxValue); 
      }catch (TrxOperationException e) {
  		throw new TrxOperationException(e.getMessage());
  	}
      catch (Exception e) {
    	  throw new TrxOperationException(e.getMessage());
	}
       
        return super.prepareResult(trxValue);
    }


   
    private IOtherBankBranchTrxValue actualMaster(IOtherBankBranchTrxValue idxTrxValue) throws OtherBankException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IFileMapperId staging = idxTrxValue.getStagingFileMapperID();
            IFileMapperId actual = idxTrxValue.getFileMapperID();

            IFileMapperId updatedActual = getOtherBankBranchFileMapperIdBusManager().insertOtherBankBranch(staging,idxTrxValue);                                            
            idxTrxValue.setFileMapperID(updatedActual); 
            idxTrxValue.setReferenceID(String.valueOf(updatedActual.getId()));        
            return idxTrxValue;
        }
        catch (PropertyIdxException ex) {
            throw new TrxOperationException(ex);
        }
    }
 }
