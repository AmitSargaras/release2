package com.integrosys.cms.app.imageTag.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.systemBankBranch.bus.SystemBankBranchException;
import com.integrosys.cms.ui.imageTag.ImageTagException;

public class ImageTagBusManagerImpl extends AbstractImageTagBusManager {

	private IImageTagDao imageTagDao;

	public IImageTagDao getImageTagDao() {
		return imageTagDao;
	}

	public void setImageTagDao(IImageTagDao imageTagDao) {
		this.imageTagDao = imageTagDao;
	}

	
	/**
	 * This method creates Image Tag Detail
	 */
	public Long createImageTagDetails(IImageTagDetails imageData)
	throws ImageTagException {

		return getImageTagDao().createImageTagDetails(
				IImageTagDao.STAGE_IMAGE_DETAILS, imageData);
	}

	/**
	 * This method creates Image Tag Map
	 */
	public Long createImageTagMap(IImageTagMap imageData)
	throws ImageTagException {

		return getImageTagDao().createImageTagMap(IImageTagDao.STAGE_IMAGE_MAP,
				imageData);
	}

	/**
	 * This method creates Image Tag Detail for checker
	 */
	public Long checkerCreateImageTagMap(IImageTagMap imageData)
	throws ImageTagException {

		return getImageTagDao().createImageTagMap(
				IImageTagDao.ACTUAL_IMAGE_MAP, imageData);
	}

	public Long checkerApproveUpdateImageTagMap(IImageTagMap imageData)
	throws ImageTagException {
		return new Long(getImageTagDao().updateImageTagMap(IImageTagDao.ACTUAL_IMAGE_MAP, imageData));
	}
	/**
	 * @return list of Customers
	 */

	public List getCustImageList(String custId,String category) throws ImageTagException {

		return (List) getImageTagDao().getCustImageList(
				IImageTagDao.ACTUAL_IMAGEUPLOAD, custId,category);
	}
	/**
	 * @return list of Image  for tagging
	 */

	
	public  List getTagImageList(String tagId,String untaggedFilter) throws ImageTagException {
		return (List) getImageTagDao().getTagImageList(IImageTagDao.ACTUAL_IMAGE_MAP, tagId,untaggedFilter);
	}
	public List getImageTagMapList(String tagId) throws ImageTagException {
		
		return (List) getImageTagDao().getImageTagMapList(IImageTagDao.ACTUAL_IMAGE_MAP, tagId);
	}
	/**
	 * @return list of Security ID for tagging
	 */

	public List getCollateralId(String key) throws ImageTagException {

		return (List) getImageTagDao().getCollateralId(
				IImageTagDao.ACTUAL_IMAGEUPLOAD, key);
	}
	
	/**
	 * This method creates Image Tag Detail
	 * @return ImageTagDetails Object
	 */
	public IImageTagDetails createImageTagDetail(
			IImageTagDetails imageTagDetails) throws ImageTagException {
		if (!(imageTagDetails == null)) {
			return getImageTagDao().createImageTagDetail(
					IImageTagDao.ACTUAL_IMAGE_DETAILS, imageTagDetails);
		} else {
			throw new SystemBankBranchException(
					"ERROR- System Bank Branch object   is null. ");
		}
	}

	
	public IImageTagDetails getExistingImageTag(IImageTagDetails imageTagDetails)
			throws ImageTagException {
		return getImageTagDao().getExistingImageTag(imageTagDetails);
	}

	public IImageTagDetails updateToWorkingCopy(IImageTagDetails workingCopy,IImageTagDetails imageCopy) throws ImageTagException,
			TrxParameterException, TransactionException,ConcurrentUpdateException {
		try{
			IImageTagDetails tagDetails= new OBImageTagDetails();
			tagDetails= workingCopy;
			AccessorUtil.copyValue(imageCopy, tagDetails, new String[] { "id","versionTime" });
			DefaultLogger.debug(this, "Using accesor util>>>>>"+tagDetails.getId());
			return updateImageTag(tagDetails);
		}catch (Exception e) {
			throw new SystemBankBranchException("Error while Copying copy to main file");
		}
	}

	public String getImageTagName() {
		return IImageTagDao.ACTUAL_IMAGE_DETAILS;
	}

	public List getCustImageListByCriteria(IImageTagDetails tagDetails) throws ImageTagException {
		return (List) getImageTagDao().getCustImageListByCriteria(IImageTagDao.ACTUAL_IMAGEUPLOAD, tagDetails);
	}
	
	// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	public List<String> getTagId(String custId){
		return getImageTagDao().getTagId(custId);
	}
	
	public List<String> getTaggedImageId(List<String> imageIdList,List<String> tagIdList){
		return getImageTagDao().getTaggedImageId(imageIdList,tagIdList);
	}
	// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	
	public Map<Long, String> getImageIdTaggedStatusMap(String checklistDocItemId){
		return getImageTagDao().getImageIdTaggedStatusMap(checklistDocItemId); 
	}
	
	public List getImageIdDocDescMap(String checklistDocItemId,String checkListType){
		return getImageTagDao().getImageIdDocDescMap(checklistDocItemId,checkListType);
	}
}
