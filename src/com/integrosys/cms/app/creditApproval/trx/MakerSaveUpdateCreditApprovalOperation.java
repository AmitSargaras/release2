package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.CreditApprovalReplicationUtils;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;

/**
 * Title: CLIMS 
 * Author: Govind.Sahu 
 * Date:2011/04/19
 */
public class MakerSaveUpdateCreditApprovalOperation extends AbstractCreditApprovalTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerSaveUpdateCreditApprovalOperation()
    {
        super();
    }

	/**
	* Get the operation name of the current operation
	*
	* @return String - the operation name of the current operation
	*/
	public String getOperationName()
	{
		return ICMSConstant.ACTION_MAKER_SAVE_UPDATE_CREDIT_APPROVAL;
	}


	/**
	* Process the transaction
	* 1.	Create the staging data
	* 2.	Create the transaction record
	* @param anITrxValue of ITrxValue type
	* @return ITrxResult - the transaction result
	* @throws TrxOperationException if encounters any error during the processing of the transaction
	*/
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
        ICreditApprovalTrxValue idxTrxValue = getCreditApprovalTrxValue(anITrxValue);
        ICreditApproval stage = idxTrxValue.getStagingCreditApproval();
        ICreditApproval replicatedCreditApproval = CreditApprovalReplicationUtils.replicateCreditApprovalForCreateStagingCopy(stage);
        idxTrxValue.setStagingCreditApproval(replicatedCreditApproval);

        ICreditApprovalTrxValue trxValue = createStagingCreditApproval(idxTrxValue);
        trxValue = updateCreditApprovalTransaction(trxValue);
        return super.prepareResult(trxValue);
    }

	
	

}
