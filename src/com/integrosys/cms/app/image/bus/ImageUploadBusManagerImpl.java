package com.integrosys.cms.app.image.bus;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.imageTag.bus.IImageTagDao;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.systemBank.bus.ISystemBank;
import com.integrosys.cms.app.systemBank.bus.ISystemBankDao;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.ui.image.IImageUploadAdd;
import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 * This interface defines the operations that are provided by a image
 * 
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/02 11:32:23 $ Tag: $Name: $
 */
public class ImageUploadBusManagerImpl extends AbstractImageUploadBusManager implements IImageUploadBusManager {

	/**
     * 
     * This method give the entity name of 
     * staging system bank table  
     * 
     */	
	public String getImageUploadName() {
		return IImageUploadDao.ACTUAL_IMAGEUPLOAD;
	}
	
	public String getImageUploadDetailsName() {
		return IImageUploadDao.ACTUAL_IMAGE_DETAILS;
	}
	

	/**
	 * @return WorkingCopy-- updated Image Upload Object
	 * @param working copy-- Entry of Actual Table
	 * @param image Copy-- Entry Of Staging Table
	 * 
	 * After Approval From Checker the Working Copy
	 * is updated as per the image copy.
	 * 
	 */
	public IImageUploadAdd updateToWorkingCopy(IImageUploadAdd workingCopy, IImageUploadAdd imageCopy)
	 throws ImageUploadException,TrxParameterException,TransactionException,ConcurrentUpdateException{
		IImageUploadAdd updated;
		try{
		workingCopy.setCustId(imageCopy.getCustId());
		workingCopy.setCustName(imageCopy.getCustName());
		workingCopy.setImgFileName(imageCopy.getImgFileName());
		workingCopy.setImageFilePath(imageCopy.getImageFilePath());
		workingCopy.setImgSize(imageCopy.getImgSize());
		
		updated = updateImageUpload(workingCopy);
	}catch (ImageUploadException e) {
		throw new ImageUploadException("Error while Copying copy to main file");
	}

		return updateImageUpload(updated);
	}
	
	public IImageUploadAdd createActualImageUploadAdd(IImageUploadAdd imageData)
	throws ImageUploadException {

		return getImageUploadDao().createActualImageUploadAdd(imageData);
	}
	
	
	
	public IImageUploadDetails createImageUploadDetail(
			IImageUploadDetails imageUploadDetails) throws ImageUploadException {
		if (!(imageUploadDetails == null)) {
			return getImageUploadDao().createImageUploadDetail(
					IImageUploadDao.ACTUAL_IMAGE_DETAILS, imageUploadDetails);
		} else {
			throw new ImageUploadException(
					"ERROR- Image Upload object   is null. ");
		}
	}
	
	/**
	 * This method creates Image Upload Map
	 */
	public void createImageUploadMap(IImageUploadDetailsMap imageData)
	throws ImageUploadException {

		getImageUploadDao().createImageUploadMap(IImageUploadDao.STAGE_IMAGE_MAP, imageData);
	}
	
	/**
	 * @return list of Image  for upload
	 */

	
	public List getUploadImageList(String uploadId) throws ImageUploadException {

		return (List) getImageUploadDao().getUploadImageList(
				IImageUploadDao.ACTUAL_IMAGE_MAP, uploadId);
	}
	
	
	/**
	 * This method creates Image Upload Detail for checker
	 */
	public Long checkerCreateImageUploadDetailsMap(IImageUploadDetailsMap imageData)
	throws ImageUploadException {

		return getImageUploadDao().createImageUploadDetailsMap(
				IImageUploadDao.ACTUAL_IMAGE_MAP, imageData);
	}

	public void removeUploadedImage(IImageUploadAdd obImage) {
		try{
		if(obImage.getStatus()==3)
			getImageUploadDao().removeImagesUpload(IImageUploadDao.ACTUAL_UPLOADED_IMAGES, obImage);
		else
			getImageUploadDao().removeImagesUpload(IImageUploadDao.TEMP_IMAGEUPLOAD, obImage);
		}catch (ImageUploadException e) {
			// schedular has moved the images to temp remove from actual
			try{
				getImageUploadDao().removeImagesUpload(IImageUploadDao.ACTUAL_UPLOADED_IMAGES, obImage);
			}catch (Exception ex) {
				throw new ImageUploadException("Unable to delete image", ex);
			}
		}
	}
	
	public List listTempImagesUpload() throws ImageUploadException {
		return getImageUploadDao().listTempImagesUpload();
	}
	
	public void updateTempImageUpload(IImageUploadAdd obImage) throws ImageUploadException {
		getImageUploadDao().updateTempImageUpload(obImage);
	}
	
	public IImageUploadAdd getTempImageUploadById(long ImgId)throws ImageUploadException {
		return getImageUploadDao().getTempImageUploadById(ImgId);
	}
	
	public String getSequenceNo() {
		return getImageUploadDao().getSequenceNo();
	}
}
