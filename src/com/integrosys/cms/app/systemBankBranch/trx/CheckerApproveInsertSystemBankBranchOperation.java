package com.integrosys.cms.app.systemBankBranch.trx;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.systemBankBranch.bus.ISystemBankBranch;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchReplicationUtils;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.otherbankbranch.IOtherBranch;

/**
 * Title: CLIMS
 * Description:
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 18, 2008
 */

public class CheckerApproveInsertSystemBankBranchOperation extends AbstractSystemBankBranchTrxOperation {
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
        ISystemBankBranchTrxValue trxValue = getSystemBankBranchTrxValue(anITrxValue);
      try{
        trxValue = actualSystemBankBranch(trxValue);
        trxValue = updateSystemBankBranchInsertTrx(trxValue);
        //String tempTrxValue = trxValue.getCurrentTrxHistoryID();
        //trxValue = createInsertSystemBankBranch(trxValue);
        //trxValue.setCurrentTrxHistoryID(tempTrxValue);
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
            IFileMapperId staging = idxTrxValue.getStagingFileMapperID();
            IFileMapperId actual = idxTrxValue.getFileMapperID();

            IFileMapperId updatedActual = getFileMapperIdSysBusManager().insertSystemBankBranch(staging,idxTrxValue);           
            idxTrxValue.setFileMapperID(updatedActual);
            idxTrxValue.setReferenceID(String.valueOf(updatedActual.getId()));        
            return idxTrxValue;
        }
        catch (PropertyIdxException ex) {
            throw new TrxOperationException(ex);
        }
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
    private ISystemBankBranchTrxValue createInsertSystemBankBranch(ISystemBankBranchTrxValue idxTrxValue) throws SystemBankBranchException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {          
            //get create Transacton for SystemBankBranch
        	idxTrxValue = prepareTrxValue(idxTrxValue);
            idxTrxValue.setFromState("PENDING CREATE");
            idxTrxValue.setTransactionType("SYSTEM_BANK_BRANCH");
            idxTrxValue.setStatus("ACTIVE");
            
            List listId = getFileMapperIdSysBusManager().getFileMasterList(idxTrxValue.getTransactionID());
            
            for (int i = 0; i < listId.size(); i++) {
    			HashMap map = (HashMap) listId.get(i);
    			 String regStage = map.get("SYS_ID").toString();
    			 ISystemBankBranch refSystemBankBranch = getFileMapperIdSysBusManager().insertActualSystemBankBranch(regStage);
    			 
    			 idxTrxValue.setReferenceID(Long.toString(refSystemBankBranch.getId()));
    			 idxTrxValue.setStagingReferenceID(regStage);
    			 
    				System.out.println("val = " + map.get("SYS_ID"));
    				
    				ICMSTrxValue  trxValue = createTransaction(idxTrxValue);
    				OBSystemBankBranchTrxValue systemBankBranchTrxValue = new OBSystemBankBranchTrxValue (trxValue);
    				systemBankBranchTrxValue.setStagingSystemBankBranch (idxTrxValue.getStagingSystemBankBranch());
    	            systemBankBranchTrxValue.setSystemBankBranch(idxTrxValue.getSystemBankBranch());
    				super.prepareResult(systemBankBranchTrxValue);
    				
    		}
                        
            return idxTrxValue;
        }
        catch (PropertyIdxException ex) {
            throw new TrxOperationException(ex);
        }
    }
 }
