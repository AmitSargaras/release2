package com.integrosys.cms.app.image.trx;

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
public class MakerCreateImageUploadOperation extends AbstractImageUploadTrxOperation {

   /**
    * Defaulc Constructor
    */
    public MakerCreateImageUploadOperation()
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
		return ICMSConstant.ACTION_MAKER_CREATE_IMAGE_UPLOAD;
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
	    IImageUploadDetailsTrxValue trxValue = super.getImageUploadDetailsTrxValue(anITrxValue);
        DefaultLogger.debug(this, "trxValue is null ? " + (trxValue==null));
        DefaultLogger.debug(this, " ---- trxValue.getStaging is null ? ----- " + (trxValue.getStagingImageUploadDetails()==null));

	    trxValue = createStagingImageUploadDetails(trxValue);
		trxValue = createImageUploadTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	* Create a property index transaction
	* @param anICCPropertyIdxTrxValue of ICCPropertyIdxTrxValue type
	* @return ICCPropertyIdxTrxValue
	* @throws TrxOperationException if there is any processing errors
	*/
	private IImageUploadDetailsTrxValue createImageUploadTransaction(IImageUploadDetailsTrxValue aniccImageUploadDetailsTrxValue) throws TrxOperationException,ImageTagException
	{
		try
		{
			aniccImageUploadDetailsTrxValue = prepareTrxValue(aniccImageUploadDetailsTrxValue);
			ICMSTrxValue trxValue = createTransaction(aniccImageUploadDetailsTrxValue);
            OBImageUploadDetailsTrxValue imageUploadDetailsTrxValue= new OBImageUploadDetailsTrxValue(trxValue);
            imageUploadDetailsTrxValue.setStagingImageUploadDetails(aniccImageUploadDetailsTrxValue.getStagingImageUploadDetails());
            imageUploadDetailsTrxValue.setImageUploadDetails(aniccImageUploadDetailsTrxValue.getImageUploadDetails());
	        return imageUploadDetailsTrxValue;
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
