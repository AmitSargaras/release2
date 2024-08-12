package com.integrosys.cms.app.imageTag.bus;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.ui.imageTag.ImageTagException;

/**
 * 
 * @author abhijit.rudrakshawar
 */
public interface IImageTagDao extends Serializable {
	public static final String ACTUAL_IMAGEUPLOAD = "actualOBImageUpload";
	public static final String ACTUAL_IMAGE_DETAILS = "actualOBImageTagDetails";
	public static final String STAGE_IMAGE_DETAILS = "stageOBImageTagDetails";
	public static final String ACTUAL_IMAGE_MAP = "actualOBImageTagMap";
	public static final String STAGE_IMAGE_MAP = "stageOBImageTagMap";

	Long createImageTagDetails(String entityName, IImageTagDetails imageData)
	throws ImageTagException;
	
	public IImageTagDetails getCustImageListForView(String entityName, String custId,String category,String id)
	throws ImageTagException;
	
	Long createImageTagMap(String entityName, IImageTagMap imageData)
	throws ImageTagException;
	
	int updateImageTagMap(String entityName, IImageTagMap imageData)
	throws ImageTagException;

	List getCustImageList(String entityName, String custId,String category)
	throws ImageTagException;

	List getTagImageList(String entityName, String tagId,String untaggedFilter)
	throws ImageTagException;

	List getImageTagMapList(String entityName, String tagId)
	throws ImageTagException;

	List getCollateralId(String entityName, String key)
	throws ImageTagException;

	IImageTagDetails updateImageTag(String entityName, IImageTagDetails item)
	throws ImageTagException;

	IImageTagDetails getImageTag(String entityName, Serializable key)
	throws ImageTagException;

	IImageTagDetails createImageTagDetail(String entityName,
			IImageTagDetails imageTagDetails) throws ImageTagException;
	
	IImageTagDetails getExistingImageTag(IImageTagDetails imageTagDetails) throws ImageTagException;

	List getCustImageListByCriteria(String entityName, IImageTagDetails tagDetails) throws ImageTagException;
	
	// code start:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	 List<String> getTagId(String custId);
	 List<String> getTaggedImageId(List<String> imageIdList,List<String> tagIdList);
	// code end:Uma Khot 02/09/2015 Phase 3 CR:Identifier to reflect tagged document
	 
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	 String getSystemBankId(String customerID);
	 String getOtherBankId(String customerID);
	 List<String> getSystemBankBranchName(String systemBankId);
	 List<String> getOtherBankBranchName(String otherBankId);
	 List populateBankList(List<String> bankBranchName);
	//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II
	 public List<String> getIFSCBankBranchName(String customerID);
	 List<String> getFacilityCodes(String custId);
	 List<String> getFacilityNames(String custId);
	 List<String> getOtherDocumentList();
	 List<String> getSecurityNameIds(String custId);
	 //Added By Prachit For image 

	List getTagUntagImageList(String string, String valueOf, String string2,String string3)throws ImageTagException;
	
	Map<Long, String> getImageIdTaggedStatusMap(String checklistDocItemId);

	List getImageIdDocDescMap(String checklistDocItemId, String checkListType);
	
	List<String> getTypeOfDocumentList();
	List<String> getSecurityOtherDocumentList();
	List<String> getStatementDocumentList();
	List<String> getcamNumberList(String custId);
	List<String> getCamDocumentList();
	List<String> getOtherMasterDocumentList();
	Map<String, String> getFacilityMap(String custId);
	
	List<Long> getImageTagIdForStockDetails(String partyId);
	
	Long getChecklistItemIdForViewStockStatement(String partyId,String docCode);
}
