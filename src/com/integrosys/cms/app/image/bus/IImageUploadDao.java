package com.integrosys.cms.app.image.bus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.cms.ui.image.IImageUploadAdd;

/**
 * This interface defines the operations that are provided by a image 
 * @author $Govind: S $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/02 11:32:23 $ Tag: $Name: $
 */
public interface IImageUploadDao extends Serializable {
	// temp table
	public static final String TEMP_IMAGEUPLOAD = "tempOBImageUpload";
	public static final String STGE_TEMP_IMAGEUPLOAD = "stgeTempOBImageUpload";
	public static final String ACTUAL_UPLOADED_IMAGES = "actualUploadedImages";
	public static final String ACTUAL_IMAGEUPLOAD = "actualOBImageUpload";
	public static final String STAGE_IMAGEUPLOAD = "stageOBImageUpload";
	public static final String ACTUAL_IMAGE_DETAILS = "actualOBImageUploadDetails";
	public static final String STAGE_IMAGE_DETAILS = "stageOBImageUploadDetails";
	public static final String ACTUAL_IMAGE_MAP = "actualOBImageUploadMap";
	public static final String STAGE_IMAGE_MAP = "stageOBImageUploadMap";
	
	public void createImageUpload(IImageUploadAdd imageData)throws ImageUploadException;
	
	public void createImageUpload(IImageUploadAdd imageData, boolean isActual) throws ImageUploadException;
	
	public List getCustImageList(IImageUploadAdd imageData)throws ImageUploadException;
	
	public List getStoredCustImageList(String custId) throws ImageUploadException;
	
	public IImageUploadAdd createStageImageUpload(IImageUploadAdd imageData)throws ImageUploadException;
	
	public IImageUploadAdd getImageDetailByTrxIDDao(String trxId) throws ImageUploadException,TrxParameterException,TransactionException;
	
	public IImageUploadAdd getImageUploadById(long imgId) throws ImageUploadException;
	
	public IImageUploadAdd updateImageUpload(String entityName, IImageUploadAdd item) throws ImageUploadException;
	
	public IImageUploadAdd createActualImageUploadAdd(IImageUploadAdd imageData)throws ImageUploadException;
	
	public IImageUploadAdd getActualImageUploadById(long imgId) throws ImageUploadException;
	
	IImageUploadDetails createImageUploadDetail(String entityName,
			IImageUploadDetails imageUploadDetails) throws ImageUploadException;
	void createImageUploadMap(String entityName, IImageUploadDetailsMap imageData)
	throws ImageUploadException;
	
	List getUploadImageList(String entityName, String uploadId)
	throws ImageUploadException;
	
	IImageUploadDetails getImageUpload(String entityName, Serializable key)
	throws ImageUploadException;

	IImageUploadDetails updateImageUploadDetails(String entityName, IImageUploadDetails item)
	throws ImageUploadException;
	
	Long createImageUploadDetailsMap(String entityName, IImageUploadDetailsMap imageData)
	throws ImageUploadException;

	void removeImagesUpload(String entityName, IImageUploadAdd obImages)
	throws ImageUploadException;
	
	List listTempImagesUpload()	throws ImageUploadException;
	void updateTempImageUpload(IImageUploadAdd obImages)	throws ImageUploadException;
	public IImageUploadAdd getTempImageUploadById(long ImgId)throws ImageUploadException;
	
	public List getSubFolderNameList(String custID,String catgory) throws ImageUploadException;
	public List getDocumentNameList(String custID,String catgory,String subfolderName) throws ImageUploadException;
	public ArrayList getCustRemoveImageList(IImageUploadAdd imageData)throws ImageUploadException;
	
	public ArrayList getImageIdWithTagList(ArrayList custImageList)throws ImageUploadException;

	public String getSequenceNo();
	
	
	public List getFacilityDocumentNameList(String facCode) throws ImageUploadException;
	
	public List getSecurityDocumentNameList(String secCode) throws ImageUploadException;
}
