package com.integrosys.cms.app.baselmaster.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.baselmaster.bus.BaselReplicationUtils;
import com.integrosys.cms.app.baselmaster.bus.IBaselMaster;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.component.bus.ComponentReplicationUtils;
import com.integrosys.cms.app.component.bus.IComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

public class MakerDeleteBaselOperation extends AbstractBaselTrxOperation{
	
	  public MakerDeleteBaselOperation() {
	        super();
	    }

	    /**
	     * Get the operation name of the current operation
	     *
	     * @return String - the operation name of the current operation
	     */
	    public String getOperationName() {
	        return ICMSConstant.ACTION_MAKER_DELETE_BASEL;
	    }

	    /**
	     * Pre process.
	     * Prepares the transaction object for persistance
	     * Get the parent  transaction ID to be appended as trx parent ref
	     *
	     * @param anITrxValue is of type ITrxValue
	     * @return ITrxValue
	     * @throws com.integrosys.base.businfra.transaction.TrxOperationException
	     *          on error
	     */
	    public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
	        anITrxValue = super.preProcess(anITrxValue);
	        IBaselMasterTrxValue trxValue = getBaselTrxValue(anITrxValue);
	        IBaselMaster staging = trxValue.getStagingBaselMaster();
	        IBaselMaster actual = trxValue.getBaselMaster();
	        try {
	            if (staging != null) {
	                

	                if (staging.getId() != ICMSConstant.LONG_INVALID_VALUE) {
	                	ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(actual.getId()), ICMSConstant.INSTANCE_BASEL);
	                    trxValue.setTrxReferenceID(parentTrx.getTransactionID());
	                }
	                
	            }else{
	            	throw new TrxOperationException("Staging Value is null");
	            }
	            return trxValue;
	        }
	        
	        catch (Exception ex) {
	            throw new TrxOperationException("Exception in preProcess: " + ex.getMessage());
	        }
	    }

	    /**
	     * Process the transaction
	     * 1.	Create the staging data
	     * 2.	Update the transaction record
	     *
	     * @param anITrxValue of ITrxValue type
	     * @return ITrxResult - the transaction result
	     * @throws TrxOperationException if encounters any error during the processing of the transaction
	     */
	    public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
	    	IBaselMasterTrxValue idxTrxValue = getBaselTrxValue(anITrxValue);
	    	IBaselMaster stage = idxTrxValue.getStagingBaselMaster();
	    	IBaselMaster replicatedBasel = BaselReplicationUtils.replicateBaselForCreateStagingCopy(stage);
	     //   replicatedHoliday.getSystemBankCode().setId(stage.getSystemBankCode().getId());
	        idxTrxValue.setStagingBaselMaster(replicatedBasel);

	        IBaselMasterTrxValue trxValue = createStagingBasel(idxTrxValue);
	        trxValue = updateBaselTrx(trxValue);
	        return super.prepareResult(trxValue);
	    }

}
