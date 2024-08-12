package com.integrosys.cms.app.image.bus;

import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.image.IImageUploadAdd;

/**
 * @author govind.sahu
 * Bus Manager Implication for Image Upload  
 */
public class ImageUploadBusManagerStagingImpl extends AbstractImageUploadBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging system bank  table  
     * 
     */
	
    public String getImageUploadName() {
        return IImageUploadDao.STAGE_IMAGEUPLOAD;
    }
    
    public String getImageUploadDetailsName() {
		return IImageUploadDao.STAGE_IMAGE_DETAILS;
	}
    /**
	 * This method returns exception as staging
	 *  Image Upload can never be working copy
	 */

    public IImageUploadAdd updateToWorkingCopy(IImageUploadAdd workingCopy, IImageUploadAdd imageCopy)
            throws ImageUploadException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }
	
    public IImageUploadDetails createImageUploadDetail(
			IImageUploadDetails imageUploadDetails) throws ImageUploadException {
		if (!(imageUploadDetails == null)) {
			return getImageUploadDao().createImageUploadDetail(
					IImageUploadDao.STAGE_IMAGE_DETAILS, imageUploadDetails);
		} else {
			throw new ImageUploadException(
					"ERROR- Image Upload object   is null. ");
		}
	}
	
	
	public void createImageUploadMap(IImageUploadDetailsMap imageData)
			throws ImageUploadException {
		// TODO Auto-generated method stub
		
	}

	public List getUploadImageList(String uploadId) throws ImageUploadException {
		// TODO Auto-generated method stub
		return null;
	}

	//Added by Anil : Staging not required, hence no implementation.
	public void removeUploadedImage(IImageUploadAdd obImage) {
		// TODO Auto-generated method stub
		
	}

	public List listTempImagesUpload() throws ImageUploadException {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateTempImageUpload(IImageUploadAdd obImages) throws ImageUploadException {
		// TODO Auto-generated method stub
		
	}

	public IImageUploadAdd getTempImageUploadById(long ImgId) throws ImageUploadException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSequenceNo() {
		// TODO Auto-generated method stub
		return null;
	}

}