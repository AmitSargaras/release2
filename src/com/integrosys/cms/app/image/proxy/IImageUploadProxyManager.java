package com.integrosys.cms.app.image.proxy;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.image.bus.IImageUploadDetails;
import com.integrosys.cms.app.image.bus.IImageUploadDetailsMap;
import com.integrosys.cms.app.image.bus.ImageUploadException;
import com.integrosys.cms.app.image.trx.IImageUploadDetailsTrxValue;
import com.integrosys.cms.app.image.trx.IImageUploadTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.image.IImageUploadAdd;

/**
 * This class defines the operations that are provided by a image upload
 * @author $Govind: Sahu $<br>
 * @version $Revision: 0 $
 * @since $Date: 2011/03/07 11:32:23 $ Tag: $Name: $
 */


public interface IImageUploadProxyManager {
	
public void createImageUploadAdd(IImageUploadAdd imageUploadAdd) throws ImageUploadException;

public void createImageUploadAdd(IImageUploadAdd imageUploadAdd, boolean isActual) throws ImageUploadException;

public List getCustImageList(IImageUploadAdd imageUploadAdd) throws ImageUploadException;

public List getStoredCustImageList(String custId) throws ImageUploadException;

public IImageUploadAdd getImageDetailByTrxID(String trxId) throws ImageUploadException,TrxParameterException,TransactionException;

public IImageUploadTrxValue createStageImageUploadAdd(ITrxContext ctx,IImageUploadTrxValue imageTrxObj,IImageUploadAdd imageUploadAdd) throws ImageUploadException, TrxParameterException, TransactionException;

public IImageUploadTrxValue getImageUploadTrxValue(String aImageUpload) throws ImageUploadException,TrxParameterException,TransactionException;

public IImageUploadTrxValue getImageUploadTrxValue(long aImageUpload) throws ImageUploadException,TrxParameterException,TransactionException;

public IImageUploadDetailsTrxValue getImageUploadByTrxID(String aTrxID) throws ImageUploadException,TransactionException,CommandProcessingException;

public IImageUploadTrxValue checkerApproveImageUpload(ITrxContext anITrxContext, IImageUploadTrxValue anIImageUploadTrxValue) throws ImageUploadException,TrxParameterException,TransactionException;

public IImageUploadTrxValue checkerRejectImageUpload(ITrxContext anITrxContext, IImageUploadTrxValue anIImageUploadTrxValue) throws ImageUploadException,TrxParameterException,TransactionException;

public IImageUploadTrxValue makerCloseRejectedImageUpload(ITrxContext anITrxContext, IImageUploadTrxValue anIImageUploadTrxValue) throws ImageUploadException,TrxParameterException,TransactionException;

public IImageUploadTrxValue checkerApproveCreateImageUpload(ITrxContext anITrxContext, IImageUploadTrxValue anIImageUploadTrxValue) throws ImageUploadException,TrxParameterException,TransactionException;

public IImageUploadTrxValue getApproveImageUploadByTrxID(String imgId) throws ImageUploadException,TransactionException;

public IImageUploadDetailsTrxValue makerCreateImageUploadDetail(ITrxContext anITrxContext, IImageUploadDetails anICCImageUploadDetails)throws ImageUploadException,TrxParameterException,TransactionException;

public void createImageUploadMap(IImageUploadDetailsMap imageUploadDetailsMap)
throws ImageUploadException;

public List getUploadImageList(String uploadId) throws ImageUploadException;
public IImageUploadDetailsTrxValue checkerApproveImageUploadDetails(ITrxContext anITrxContext, IImageUploadDetailsTrxValue imageUploadDetailsTrxValue) throws ImageUploadException,TrxParameterException,TransactionException;

public Long checkerCreateImageUploadDetailsMap(IImageUploadDetailsMap imageTagMap)throws ImageUploadException;

public void removeUploadedImage(IImageUploadAdd obImage)throws ImageUploadException;

List listTempImagesUpload()	throws ImageUploadException;
void updateTempImageUpload(IImageUploadAdd obImages)	throws ImageUploadException;
IImageUploadAdd getTempImageUploadById(long ImgId)throws ImageUploadException;

public List getSubFolderNameList(String custID,String catgory) throws ImageUploadException;
public List getDocumentNameList(String custID,String catgory,String subfolderName) throws ImageUploadException;

public ArrayList getCustRemoveImageList(IImageUploadAdd imageUploadAdd) throws ImageUploadException;


public ArrayList getImageIdWithTagList(ArrayList custImageList) throws ImageUploadException;

public String getSequenceNo();

public List getFacilityDocumentNameList(String facCode) throws ImageUploadException;

public List getSecurityDocumentNameList(String secCode) throws ImageUploadException;

}
