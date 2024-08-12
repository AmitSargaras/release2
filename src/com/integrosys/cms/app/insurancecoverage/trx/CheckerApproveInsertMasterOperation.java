package com.integrosys.cms.app.insurancecoverage.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 18, 2008
 */

public class CheckerApproveInsertMasterOperation extends AbstractInsuranceCoverageTrxOperation {
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
        IInsuranceCoverageTrxValue trxValue = getInsuranceCoverageTrxValue(anITrxValue);
      try{
        trxValue = actualMaster(trxValue);
        trxValue = updateMasterInsertTrx(trxValue);
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
     * @throws InsuranceCoverageException 
     */
    private IInsuranceCoverageTrxValue actualMaster(IInsuranceCoverageTrxValue idxTrxValue) throws InsuranceCoverageException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IFileMapperId staging = idxTrxValue.getStagingFileMapperID();
            IFileMapperId actual = idxTrxValue.getFileMapperID();

            IFileMapperId updatedActual = getInsuranceCoverageFileMapperIdBusManager().insertInsuranceCoverage(staging,idxTrxValue);           
            idxTrxValue.setFileMapperID(updatedActual);
            idxTrxValue.setReferenceID(String.valueOf(updatedActual.getId()));        
            return idxTrxValue;
        }
        catch (PropertyIdxException ex) {
            throw new TrxOperationException(ex);
        }
    }
 }
