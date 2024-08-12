package com.integrosys.cms.app.imageTag.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 * 
 * @author abhijit.rudrakshawar
 */

public interface IImageTagBusManager {
	Long createImageTagMap(IImageTagMap imageData) throws ImageTagException;

	Long checkerCreateImageTagMap(IImageTagMap imageData) throws ImageTagException;
	Long checkerApproveUpdateImageTagMap(IImageTagMap imageData) throws ImageTagException;
	Long createImageTagDetails(IImageTagDetails imageData) throws ImageTagException;

	List getCustImageList(String custId,String category) throws ImageTagException;
	List getTagImageList(String tagId,String untaggedFilter) throws ImageTagException;
	List getImageTagMapList(String tagId) throws ImageTagException;
	List getCollateralId(String key) throws ImageTagException;

	IImageTagDetails getImageTagById(long id) throws ImageTagException,TrxParameterException, TransactionException;

	IImageTagDetails createImageTagDetail(IImageTagDetails imageTagDetails) throws ImageTagException;

	IImageTagDetails updateImageTag(IImageTagDetails item) throws ImageTagException, TrxParameterException, 
	TransactionException, ConcurrentUpdateException;
	
	IImageTagDetails getExistingImageTag(IImageTagDetails imageTagDetails) throws ImageTagException;
	
	IImageTagDetails updateToWorkingCopy(IImageTagDetails workingCopy, IImageTagDetails imageCopy) throws ImageTagException,TrxParameterException,TransactionException,ConcurrentUpdateException;

	List getCustImageListByCriteria(IImageTagDetails tagDetails)throws ImageTagException;
	
	// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	List<String> getTagId(String custId);
	List<String> getTaggedImageId(List<String> imageIdList,List<String> tagIdList);
	// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	
	Map<Long, String> getImageIdTaggedStatusMap(String checklistDocItemId);

	List getImageIdDocDescMap(String checklistDocItemId, String checkListType);
	
}