package com.integrosys.cms.app.propertyindex.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerCreatePrIdxOperation extends AbstractPropertyIdxTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerCreatePrIdxOperation()
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
		return ICMSConstant.ACTION_MAKER_CREATE_PRIDX;
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
	{
	    IPropertyIdxTrxValue trxValue = super.getPropertyIdxTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingPrIdx()==null));

	    trxValue = createStagingPropertyIdx(trxValue);
		trxValue = createCCPropertyIdxTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IPropertyIdxTrxValue createCCPropertyIdxTransaction(IPropertyIdxTrxValue anICCPropertyIdxTrxValue) throws TrxOperationException
	{
		try
		{
            anICCPropertyIdxTrxValue = prepareTrxValue(anICCPropertyIdxTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICCPropertyIdxTrxValue);
            OBPropertyIdxTrxValue propertyIdxTrxValue = new OBPropertyIdxTrxValue (trxValue);
	        propertyIdxTrxValue.setStagingPrIdx (anICCPropertyIdxTrxValue.getStagingPrIdx());
	        propertyIdxTrxValue.setPrIdx(anICCPropertyIdxTrxValue.getPrIdx());
	        return propertyIdxTrxValue;
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
