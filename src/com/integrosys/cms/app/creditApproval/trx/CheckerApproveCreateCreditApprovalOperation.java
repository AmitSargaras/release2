/**
 * 
 */
package com.integrosys.cms.app.creditApproval.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 0.0 $
 * @since $Date: Apr 6, 2011 2:41:39 PM $ Tag: $Name: $
 */
public class CheckerApproveCreateCreditApprovalOperation  extends AbstractCreditApprovalTrxOperation {


	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_CREDIT_APPROVAL;
	}
	
	

	
	/********************/
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
    	ICreditApprovalTrxValue trxValue = getCreditApprovalTrxValue(anITrxValue);
      
        trxValue = createActualCreateCredit(trxValue);
        trxValue = updateCreditApprovalTransaction(trxValue);
        return super.prepareResult(trxValue);
    }


    /**
     * Create the actual credit approval
     *
     * @param anITrxValue of ITrxValue type
     * @return ICCDocumentLocationTrxValue - the document item trx value
     * @throws TrxOperationException on errors
     */
    private ICreditApprovalTrxValue createActualCreateCredit(ICreditApprovalTrxValue creditApprovalTrxValue) throws TrxOperationException {
        try {
        	creditApprovalTrxValue = createActualCreditApproval(creditApprovalTrxValue);
            return creditApprovalTrxValue;
        }
        catch (TrxOperationException cex) {
			 cex.printStackTrace();
			 DefaultLogger.debug(this, "Error in creating Credit approval " + cex.getMessage());
			throw new TrxOperationException("Error in creating Credit approval : ");
		}
    } 
}
