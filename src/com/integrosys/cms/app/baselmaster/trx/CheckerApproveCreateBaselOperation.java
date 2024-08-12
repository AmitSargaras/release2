package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.baselmaster.bus.BaselMasterException;
import com.integrosys.cms.app.baselmaster.bus.BaselReplicationUtils;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.ComponentReplicationUtils;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;

public class CheckerApproveCreateBaselOperation extends AbstractBaselTrxOperation{
	

	
	  public String getOperationName() {
	        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_BASEL;
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
	    	IBaselMasterTrxValue trxValue = getBaselTrxValue(anITrxValue);
	      try{
	        trxValue = createActualBasel(trxValue);
	        trxValue = updateBaselTrx(trxValue);
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
	     * @throws ComponentException 
	     */
	    private IBaselMasterTrxValue createActualBasel(IBaselMasterTrxValue idxTrxValue) throws BaselMasterException, TrxParameterException, TransactionException, ConcurrentUpdateException {
	        try {
	        	IBaselMaster staging = idxTrxValue.getStagingBaselMaster();
	            // Replicating is necessary or else stale object error will arise
	        	IBaselMaster replicatedComponent = BaselReplicationUtils.replicateBaselForCreateStagingCopy(staging);
	        	IBaselMaster actual = getBaselBusManager().createBasel(replicatedComponent);
	            idxTrxValue.setBaselMaster(actual);
	            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
	            getBaselBusManager().updateBasel(actual);
	            return idxTrxValue;
	        }
	        catch (BaselMasterException ex) {
	            throw new TrxOperationException(ex);
	        }
	    }



}
