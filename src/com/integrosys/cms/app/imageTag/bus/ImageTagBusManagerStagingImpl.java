package com.integrosys.cms.app.imageTag.bus;

import java.util.List;
import java.util.Map;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 * @author abhijit.rudrakshawar
 * Bus Manager Implication for Image Tag 
 */
public class ImageTagBusManagerStagingImpl extends AbstractImageTagBusManager {

    /**
     * 
     * This method give the entity name of 
     * staging Image Tag table  
     * 
     */
	
	public String getImageTagName() {
        return IImageTagDao.STAGE_IMAGE_DETAILS;
    }

	/**
	 * This method returns exception as staging
	 *  Image Tag can never be working copy
	 */
    
    public IImageTag updateToWorkingCopy(IImageTag workingCopy, IImageTag imageCopy)
            throws ImageTagException,TrxParameterException,TransactionException,ConcurrentUpdateException {
        throw new IllegalStateException("'updateToWorkingCopy' should not be implemented.");
    }

	
	public Long createImageTagDetails(IImageTagDetails imageData)
			throws ImageTagException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long createImageTagMap(IImageTagMap imageData)
			throws ImageTagException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List getCollateralId(String key) throws ImageTagException {
		// TODO Auto-generated method stub
		return null;
	}

	

	
	public List getCustImageList(String custId,String category) throws ImageTagException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Long checkerCreateImageTagMap(IImageTagMap imageData)
			throws ImageTagException {
		// TODO Auto-generated method stub
		return null;
	}

	public Long checkerApproveUpdateImageTagMap(IImageTagMap imageData)
			throws ImageTagException {
		// TODO Auto-generated method stub
		return null;
	}

	public IImageTagDetails getExistingImageTag(IImageTagDetails imageTagDetails)
			throws ImageTagException {
		// TODO Auto-generated method stub
		return null;
	}

	public IImageTagDetails updateToWorkingCopy(IImageTagDetails workingCopy,IImageTagDetails imageCopy) throws ImageTagException,
	TrxParameterException, TransactionException,ConcurrentUpdateException {
		// TODO Auto-generated method stub
		return null;
	}

	public List getTagImageList(String tagId,String untaggedFilter) throws ImageTagException {
		return (List) getImageTagDao().getTagImageList(IImageTagDao.STAGE_IMAGE_MAP, tagId,untaggedFilter);
	}	
	public List getImageTagMapList(String tagId) throws ImageTagException {
		return (List) getImageTagDao().getImageTagMapList(IImageTagDao.STAGE_IMAGE_MAP, tagId);
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.app.imageTag.bus.IImageTagBusManager#getCustImageListByCriteria(com.integrosys.cms.app.imageTag.bus.IImageTagDetails)
	 */
	public List getCustImageListByCriteria(IImageTagDetails tagDetails) throws ImageTagException {
		// TODO Auto-generated method stub
		return null;
	}

	// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	
	public List<String> getTagId(String custId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getTaggedImageId(List<String> imageIdList,
			List<String> tagIdList) {
		// TODO Auto-generated method stub
		return null;
	}

	// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	
	public Map<Long, String> getImageIdTaggedStatusMap(String checklistDocItemId){
		return null;
	}
	
	public List getImageIdDocDescMap(String checklistDocItemId,String checkListType){
		return null;
	}

}