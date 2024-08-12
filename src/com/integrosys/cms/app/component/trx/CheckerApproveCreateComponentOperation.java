package com.integrosys.cms.app.component.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.bus.ComponentReplicationUtils;
import com.integrosys.cms.app.component.bus.ComponentException;

public class CheckerApproveCreateComponentOperation extends	AbstractComponentTrxOperation {
	
	  public String getOperationName() {
	        return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_COMPONENT;
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
	        IComponentTrxValue trxValue = getComponentTrxValue(anITrxValue);
	      try{
	        trxValue = createActualComponent(trxValue);
	        trxValue = updateComponentTrx(trxValue);
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
	    private IComponentTrxValue createActualComponent(IComponentTrxValue idxTrxValue) throws ComponentException, TrxParameterException, TransactionException, ConcurrentUpdateException {
	        try {
	            IComponent staging = idxTrxValue.getStagingComponent();
	            // Replicating is necessary or else stale object error will arise
	            IComponent replicatedComponent = ComponentReplicationUtils.replicateComponentForCreateStagingCopy(staging);
	            IComponent actual = getComponentBusManager().createComponent(replicatedComponent);
	            idxTrxValue.setComponent(actual);
	            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
	            getComponentBusManager().updateComponent(actual);
	            return idxTrxValue;
	        }
	        catch (ComponentException ex) {
	            throw new TrxOperationException(ex);
	        }
	    }

}
