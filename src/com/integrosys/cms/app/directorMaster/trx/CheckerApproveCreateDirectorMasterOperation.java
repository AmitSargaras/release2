package com.integrosys.cms.app.directorMaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterReplicationUtils;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;
import com.integrosys.cms.app.propertyindex.bus.PropertyIdxException;

/**
 * Title: DIRECTOR MASTER
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-04 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public class CheckerApproveCreateDirectorMasterOperation extends AbstractDirectorMasterTrxOperation {
    public String getOperationName() {
        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_DIRECTOR_MASTER;
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
        IDirectorMasterTrxValue trxValue = getDirectorMasterTrxValue(anITrxValue);
      try{
        trxValue = createActualDirectorMaster(trxValue);
        trxValue = updateDirectorMasterTrx(trxValue);
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
     * @throws DirectorMasterException 
     */
    private IDirectorMasterTrxValue createActualDirectorMaster(IDirectorMasterTrxValue idxTrxValue) throws DirectorMasterException, TrxParameterException, TransactionException, ConcurrentUpdateException {
        try {
            IDirectorMaster staging = idxTrxValue.getStagingDirectorMaster();
            IDirectorMaster replicatedDirectorMaster = DirectorMasterReplicationUtils.replicateDirectorMasterForCreateStagingCopy(staging);                        
            IDirectorMaster updatedDirectorMaster = getDirectorMasterBusManager().createDirectorMaster(replicatedDirectorMaster);			
            idxTrxValue.setDirectorMaster(updatedDirectorMaster);            
            return idxTrxValue;
        }
        catch (PropertyIdxException ex) {
            throw new TrxOperationException(ex);
        }
    }
}
