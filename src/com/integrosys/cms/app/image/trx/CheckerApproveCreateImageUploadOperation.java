/**
 * 
 */
package com.integrosys.cms.app.image.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.image.bus.IImageUploadDetails;
import com.integrosys.cms.app.image.bus.ImageUploadException;
import com.integrosys.cms.app.image.bus.ImageUploadReplicationUtils;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.ImageTagReplicationUtils;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.ui.imageTag.ImageTagException;


/**
 * 
 * @author $Govind: Sahu $
 * @version $Revision: 0.0 $
 * @since $Date: Mar 28, 2011 11:27:06 AM $ Tag: $Name: $
 */
public class CheckerApproveCreateImageUploadOperation extends AbstractImageUploadTrxOperation {
	//private static final long serialVersionUID = -6138935003644406544L;

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_CREATE_IMAGE_UPLOAD;
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
		 IImageUploadDetailsTrxValue trxValue = getImageUploadDetailsTrxValue(anITrxValue);
	      try{
	        trxValue = createActualImageUpload(trxValue);
	      }catch (Exception e) {
			e.printStackTrace();
		}
	        trxValue = updateImageUploadDetailsTrx(trxValue);
	        return super.prepareResult(trxValue);
	    }


    /**
     * Create the actual property index
     *
     * @param anITrxValue of ITrxValue type
     * @return ICCDocumentLocationTrxValue - the document item trx value
     * @throws TrxOperationException on errors
     */
	 private IImageUploadDetailsTrxValue createActualImageUpload(IImageUploadDetailsTrxValue idxTrxValue) throws ImageUploadException, TrxParameterException, TransactionException, ConcurrentUpdateException {
	        try {
	            IImageUploadDetails staging = idxTrxValue.getStagingImageUploadDetails();
	            IImageUploadDetails replicatedImageUpload = ImageUploadReplicationUtils.replicateImageUploadForCreateStagingCopy(staging);
	            IImageUploadDetails actual = getImageUploadBusManager().createImageUploadDetail((staging));
	            idxTrxValue.setImageUploadDetails((actual));
	            idxTrxValue.setReferenceID(String.valueOf(actual.getId()));
	            getImageUploadBusManager().updateImageUploadDetails(actual);
	            return idxTrxValue;
	        }
	        catch (ImageUploadException ex) {
	            throw new TrxOperationException(ex);
	        }
	    }
}
