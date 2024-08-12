package com.integrosys.cms.app.imageTag.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 * Title: CLIMS 
 * Description:
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Jan 18, 2008
 */
public class MakerCreateImageTagOperation extends AbstractImageTagTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerCreateImageTagOperation()
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
		return ICMSConstant.ACTION_MAKER_CREATE_IMAGE_TAG;
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
	    IImageTagTrxValue trxValue = super.getImageTagTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStagingPrIdx() is null ? ----- " + (trxValue.getStagingImageTagDetails()==null));

	    trxValue = createStagingImageTagDetails(trxValue);
		trxValue = createImageTagTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IImageTagTrxValue createImageTagTransaction(IImageTagTrxValue aniccImageTagTrxValue) throws TrxOperationException,ImageTagException
	{
		try
		{
			aniccImageTagTrxValue = prepareTrxValue(aniccImageTagTrxValue);
			ICMSTrxValue trxValue = createTransaction(aniccImageTagTrxValue);
            OBImageTagTrxValue imageTagTrxValue= new OBImageTagTrxValue(trxValue);
            imageTagTrxValue.setStagingImageTagDetails(aniccImageTagTrxValue.getStagingImageTagDetails());
            imageTagTrxValue.setImageTagDetails(aniccImageTagTrxValue.getImageTagDetails());
	        return imageTagTrxValue;
		}
		catch(ImageTagException se)
		{
			throw new ImageTagException("Error in Create System Bank Branch Operation ");
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
