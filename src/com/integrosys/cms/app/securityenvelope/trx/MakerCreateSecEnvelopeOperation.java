package com.integrosys.cms.app.securityenvelope.trx;


import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong
 * Date: Feb 2, 2010
 */
public class MakerCreateSecEnvelopeOperation extends AbstractSecEnvelopeTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerCreateSecEnvelopeOperation()
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
        DefaultLogger.debug(this,"In getOperationName................");
		return ICMSConstant.ACTION_MAKER_CREATE_SECENV;
	}


	/**
	* Process the transaction
	* 1.	Create the staging data
	* 2.	Create the transaction record
	* @param anITrxValue of ITrxValue type
	* @return ITrxResult - the transaction result
	* @throws TrxOperationException if encounters any error during the processing of the transaction
	*/
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException
	{   DefaultLogger.debug(this,"In performProcess.............");
	    ISecEnvelopeTrxValue trxValue = super.getSecEnvelopeTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingSecEnvelope() is null ? ----- " + (trxValue.getStagingSecEnvelope()==null));

	    trxValue = createStagingSecEnvelope(trxValue);
		trxValue = createCCSecEnvelopeTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a security envelope transaction
	* @param anICCSecEnvelopeTrxValue of ICCSecEnvelopeTrxValue type
	* @return ICCSecEnvelopeTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private ISecEnvelopeTrxValue createCCSecEnvelopeTransaction(ISecEnvelopeTrxValue anICCSecEnvelopeTrxValue) throws TrxOperationException
	{
		try
		{
            anICCSecEnvelopeTrxValue = prepareTrxValue(anICCSecEnvelopeTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCSecEnvelopeTrxValue);
            OBSecEnvelopeTrxValue secEnvelopeTrxValue = new OBSecEnvelopeTrxValue (trxValue);
	        secEnvelopeTrxValue.setStagingSecEnvelope (anICCSecEnvelopeTrxValue.getStagingSecEnvelope());
	        secEnvelopeTrxValue.setSecEnvelope(anICCSecEnvelopeTrxValue.getSecEnvelope());
	        return secEnvelopeTrxValue;
		}
		catch(TransactionException ex)
		{
			throw new TrxOperationException(ex);
		}
		catch(Exception ex)
		{
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}

}
