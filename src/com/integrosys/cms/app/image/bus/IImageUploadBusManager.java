package com.integrosys.cms.app.image.bus;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.image.IImageUploadAdd;
/**
 * This class defines the operations that are provided by a image upload
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/07 11:32:23 $ Tag: $Name: $
 */

public interface IImageUploadBusManager {
	public void createImageUploadAdd(IImageUploadAdd imageData) throws ImageUploadException;
	
	public void createImageUploadAdd(IImageUploadAdd imageUploadAdd, boolean isActual) throws ImageUploadException;
	
	public List getCustImageList(IImageUploadAdd imageData) throws ImageUploadException;
	
	public List getStoredCustImageList(String custId) throws ImageUploadException;
	
	public IImageUploadAdd createStageImageUploadAdd(IImageUploadAdd imageData) throws ImageUploadException,TrxParameterException,TransactionException;

	public IImageUploadAdd getImageUploadById(long imgId) throws ImageUploadException;
	
	public IImageUploadAdd getImageDetailByTrxIDDao(String trxId) throws ImageUploadException,TrxParameterException,TransactionException;
	 
	public IImageUploadAdd updateImageUpload(IImageUploadAdd item) throws ImageUploadException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IImageUploadAdd updateToWorkingCopy(IImageUploadAdd workingCopy, IImageUploadAdd imageCopy) throws ImageUploadException,TrxParameterException,TransactionException,ConcurrentUpdateException;
	
	public IImageUploadAdd createActualImageUploadAdd(IImageUploadAdd imageData) throws ImageUploadException;
	
	public IImageUploadAdd getActualImageUploadById(long imgId) throws ImageUploadException;
	
	IImageUploadDetails createImageUploadDetail(IImageUploadDetails imageUploadDetails)
	throws ImageUploadException;
	
	void createImageUploadMap(IImageUploadDetailsMap imageData) throws ImageUploadException;
	
	List getUploadImageList(String uploadId) throws ImageUploadException;
	
	IImageUploadDetails getImageUploadDetailsById(long id) throws ImageUploadException,
	TrxParameterException, TransactionException;
	
	IImageUploadDetails updateImageUploadDetails(IImageUploadDetails item)
	throws ImageUploadException, TrxParameterException,
	TransactionException, ConcurrentUpdateException;
	
	Long checkerCreateImageUploadDetailsMap(IImageUploadDetailsMap imageData)
	throws ImageUploadException;

	public void removeUploadedImage(IImageUploadAdd obImage);
	
	List listTempImagesUpload()	throws ImageUploadException;
	void updateTempImageUpload(IImageUploadAdd obImages)	throws ImageUploadException;
	
	public IImageUploadAdd getTempImageUploadById(long ImgId)throws ImageUploadException;
	
	public List getSubFolderNameList(String custID,String catgory) throws ImageUploadException;
	public List getDocumentNameList(String custID,String catgory,String subfolderName) throws ImageUploadException;
	public ArrayList getCustRemoveImageList(IImageUploadAdd imageData) throws ImageUploadException;
	
	
	public ArrayList getImageIdWithTagList(ArrayList custImageList) throws ImageUploadException;

	public String getSequenceNo();
	
	public List getFacilityDocumentNameList(String faccode) throws ImageUploadException;
	
	public List getSecurityDocumentNameList(String seccode) throws ImageUploadException;
	
}