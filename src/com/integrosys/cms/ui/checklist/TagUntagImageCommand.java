package com.integrosys.cms.ui.checklist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.ICheckListItemImageDetail;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.OBCheckListItemImageDetail;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.checklist.camreceipt.CAMReceiptAction;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.image.IImageUploadAdd;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;

public class TagUntagImageCommand extends AbstractCommand implements ICommonEventConstant, ITagUntagImageConstant {

	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{ "event", String.class.getName() , REQUEST_SCOPE },
			{ "selectedImageIds", String.class.getName() , REQUEST_SCOPE },
			{ "checkList", ICheckList.class.getName(), SERVICE_SCOPE },
			{ "checkListItem", ICheckListItem.class.getName() , SERVICE_SCOPE },
			{ TAG_UNTAG_IMAGE_DTL_LIST, List.class.getName(), SERVICE_SCOPE },
			{ IMG_ID_UNTAG_STATUS_MAP, Map.class.getName(), SERVICE_SCOPE },
			{ IS_IMAGE_TAG_PENDING, Boolean.class.getName(), SERVICE_SCOPE },
			{ "checkListTrxVal", ICheckListTrxValue.class.getName() , SERVICE_SCOPE },
			{ "itemRef", "java.lang.String", SERVICE_SCOPE },
			{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, ILimitProfile.class.getName(), GLOBAL_SCOPE }
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { 
			{ "event", String.class.getName() , REQUEST_SCOPE },
			{ "status", String.class.getName() , REQUEST_SCOPE },
			{ IMG_ID_UNTAG_STATUS_MAP, Map.class.getName() , REQUEST_SCOPE },
			{ IMG_ID_UNTAG_STATUS_MAP, Map.class.getName(), SERVICE_SCOPE },
			{ UPLOADED_DMS_IMG_IDS, List.class.getName() , SERVICE_SCOPE },
			{ ALL_IMG_IDS, LinkedList.class.getName() , SERVICE_SCOPE },
			{ ALL_IMAGES_UPLOAD_ADD_MAP, Map.class.getName() , SERVICE_SCOPE },
			{ TAG_UNTAG_IMAGE_DTL_LIST, List.class.getName(), REQUEST_SCOPE },
			{ TAG_UNTAG_IMAGE_DTL_LIST, List.class.getName(), SERVICE_SCOPE },
			{ "checkListItem", ICheckListItem.class.getName() , SERVICE_SCOPE },
			{ "itemRef", "java.lang.String", REQUEST_SCOPE },
			{ "itemRef", "java.lang.String", SERVICE_SCOPE },
			{ IS_IMAGE_TAG_PENDING, Boolean.class.getName(), SERVICE_SCOPE }
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		
		try {
			String event = (String) map.get("event");
			String selectedImageIds = (String) map.get("selectedImageIds");
			ICheckList checkList = (ICheckList) map.get("checkList");
			
			ICheckListItem checkListItem = (ICheckListItem) map.get("checkListItem");
			
			IImageTagProxyManager imageTagProxy = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
			ICheckListItem actual = null;
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			String itemReferenceId = (String) map.get("itemRef"); 
			long itemRef = 0;
			if(itemReferenceId != null && !"".equals(itemReferenceId)) {
			itemRef = Long.parseLong((String) map.get("itemRef"));
			System.out.println("itemReferenceId=>"+itemReferenceId);
			resultMap.put("itemRef",  itemReferenceId);
			}
			List imageListFromTagDetails = Collections.emptyList();
			List<String> selectedImageIdList = Collections.emptyList();
			List<ICheckListItemImageDetail> chkItemImageDetails = (List<ICheckListItemImageDetail>) map.get(TAG_UNTAG_IMAGE_DTL_LIST); 
			Map<Long, String> imageIdUnTaggedStatusMap = (Map<Long, String>) map.get(IMG_ID_UNTAG_STATUS_MAP);
//			Map<Long, String> imageIdDocDescsMap = (Map<Long, String>) map.get(IMG_ID_UNTAG_STATUS_MAP);
			List<String> imageIdDocDescsMap = Collections.emptyList();
			
			DefaultLogger.info(this, "For Event, "+ event +" imageIdUnTaggedStatusMap before processing :"+imageIdUnTaggedStatusMap);
			
			if(event != null && (event.startsWith(CAMReceiptAction.EVENT_PREPARE_TAG_UNTAG_IMAGE) || event.startsWith(CAMReceiptAction.EVENT_PREPARE_TAG_UNTAG_IMAGE_VIEW))) {
				
				List<Long> uploadedDMSImageIds = new ArrayList<Long>();
				LinkedList<Long> allImageIds = new LinkedList<Long>();
				Map<Long, IImageUploadAdd> allImagesUploadAddMap = new HashMap<Long, IImageUploadAdd>();
				ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
				IImageTagDetails tagDetails = CheckListHelper.populateImageTagDetails(checkList.getCheckListType(), limitProfile.getLEReference());
				
				if (checkListTrxVal.getCheckList() != null) {
					actual = CheckListHelper.getItem(checkListTrxVal.getCheckList().getCheckListItemList(), checkListItem.getCheckListItemRef());
				}
				
				if(chkItemImageDetails == null ) {
					chkItemImageDetails = Collections.emptyList();
				}
				
				if(imageIdUnTaggedStatusMap == null) {
					imageIdUnTaggedStatusMap = Collections.emptyMap();
				}
				
				imageListFromTagDetails = imageTagProxy.getCustImageListByCriteria(tagDetails);
				
				imageIdUnTaggedStatusMap = imageTagProxy.getImageIdTaggedStatusMap(String.valueOf(actual!= null ?actual.getCheckListItemID():""));
				
//				imageIdDocDescsMap = imageTagProxy.getImageIdDocDescMap(String.valueOf(actual!= null ?actual.getCheckListItemID():""),checkList.getCheckListType());
				
				if(checkListItem.getCheckListItemImageDetail() != null) {
					chkItemImageDetails = Arrays.asList(checkListItem.getCheckListItemImageDetail());
				}
				
				chkItemImageDetails = populateChkItemImageDetails(chkItemImageDetails, imageListFromTagDetails,imageIdDocDescsMap);
				
				List chkItemImageDetailsView =  new ArrayList();
				
				if((CAMReceiptAction.EVENT_PREPARE_TAG_UNTAG_IMAGE_VIEW).equals(event)) {
					for(ICheckListItemImageDetail chkitem :  chkItemImageDetails ) {
						if("Y".equals(chkitem.getIsSelectedInd())) {
							chkItemImageDetailsView.add(chkitem); 
						}
					}
					
					chkItemImageDetails = chkItemImageDetailsView;
				}
				
				for(IImageUploadAdd img : (List<IImageUploadAdd>)imageListFromTagDetails) {
					//If image is passed into DMS
					if(img.getStatus() == 3) {
						uploadedDMSImageIds.add(img.getImgId());
					}
					allImagesUploadAddMap.put(img.getImgId(), img);
				}
				
				for(ICheckListItemImageDetail img :  chkItemImageDetails) {
					allImageIds.add(img.getImageId());
				}
				
				DefaultLogger.info(this, "For Event, "+ event +" imageIdUnTaggedStatusMap after processing :"+imageIdUnTaggedStatusMap +"| allImageIds :"+allImageIds+ " | allImagesUploadAddMap size :"+allImagesUploadAddMap.size());
				
				resultMap.put(IMG_ID_UNTAG_STATUS_MAP, imageIdUnTaggedStatusMap);
				resultMap.put(UPLOADED_DMS_IMG_IDS, uploadedDMSImageIds);
				resultMap.put(ALL_IMG_IDS, allImageIds);
				resultMap.put(ALL_IMAGES_UPLOAD_ADD_MAP, allImagesUploadAddMap);
				resultMap.put(TAG_UNTAG_IMAGE_DTL_LIST, chkItemImageDetails );
				resultMap.put("event",  map.get("event"));
			}
			else if(event != null && event.startsWith(CAMReceiptAction.EVENT_TAG_UNTAG_IMAGE)) {
				
				chkItemImageDetails = (List) map.get(TAG_UNTAG_IMAGE_DTL_LIST);
				selectedImageIdList = getListFromDelimitedString(selectedImageIds, COMMA_DELIMITER);
				
				chkItemImageDetails = updateSelectedIndChkItemImageDetails(chkItemImageDetails,selectedImageIdList);
				checkListItem.setCheckListItemImageDetail((ICheckListItemImageDetail[]) chkItemImageDetails.toArray(new ICheckListItemImageDetail[0]));
				
				Boolean isImageTagPending = (Boolean) map.get(IS_IMAGE_TAG_PENDING);
				if(!CommonUtil.isEmptyList(chkItemImageDetails)) {
					String taggedFileNames = CheckListHelper.getTagUntaggedFileNameList(imageIdUnTaggedStatusMap, chkItemImageDetails.toArray(new ICheckListItemImageDetail[0]), true);
					String unTaggedFileNames = CheckListHelper.getTagUntaggedFileNameList(imageIdUnTaggedStatusMap, chkItemImageDetails.toArray(new ICheckListItemImageDetail[0]), false);
					
					List<ICheckListItemImageDetail> existingTaggedItems = getExistingTaggedItems(imageIdUnTaggedStatusMap, chkItemImageDetails);
					
					isImageTagPending = CommonUtil.isEmptyList(existingTaggedItems) && StringUtils.isBlank(taggedFileNames) && StringUtils.isBlank(unTaggedFileNames);
					
					DefaultLogger.info(this, "For Event, "+ event + " taggedFileNames : "+taggedFileNames+ " | isImageTagPending "+isImageTagPending);
				}
				
				resultMap.put("checkListItem", checkListItem );
				resultMap.put("status", checkListItem.getItemStatus());
				resultMap.put(IS_IMAGE_TAG_PENDING, isImageTagPending);
			}
			else if(event != null && event.startsWith(CAMReceiptAction.EVENT_PREPARE_TAG_UNTAG_IMAGE_FROM_RETRIEVE)){
				
				selectedImageIdList = getListFromDelimitedString(selectedImageIds, COMMA_DELIMITER);
				chkItemImageDetails = updateSelectedIndChkItemImageDetails(chkItemImageDetails,selectedImageIdList);
				
				DefaultLogger.info(this, "For Event, "+ event + " selectedImageIds : "+ selectedImageIds+ " | selectedImageIdList :"+selectedImageIdList);
				
				resultMap.put(TAG_UNTAG_IMAGE_DTL_LIST, chkItemImageDetails );
				resultMap.put(IMG_ID_UNTAG_STATUS_MAP, imageIdUnTaggedStatusMap );
				resultMap.put("status", checkListItem.getItemStatus());
				resultMap.put("event",  map.get("event"));
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught : ", e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private List<ICheckListItemImageDetail> getExistingTaggedItems(Map<Long, String> imageIdUnTaggedStatusMap,
			List<ICheckListItemImageDetail> chkItemImageDetails) {
		
		List<ICheckListItemImageDetail> existingTaggedItems = new ArrayList<ICheckListItemImageDetail>();
		
		for(ICheckListItemImageDetail chkItem : chkItemImageDetails) {
			if(ICMSConstant.NO.equals(chkItem.getIsSelectedInd()) && TAGGED.equals(imageIdUnTaggedStatusMap.get(chkItem.getImageId()))) {
				existingTaggedItems.add(chkItem);
			}
		}
		
		return existingTaggedItems;
	}

	private static <T> List<String> getListFromDelimitedString(String delimittedString, String delimitter) {
		if(StringUtils.isNotBlank(delimitter) &&  StringUtils.isNotBlank(delimittedString)) {
			List<String> delimitedList = new ArrayList<String>();
			String[] splittedStringArray =  delimittedString.split(delimitter);
			for(String str : splittedStringArray) {
				delimitedList.add(str.trim());
			}
			return delimitedList;
		}
		return Collections.emptyList();
	}
	
	private List<ICheckListItemImageDetail> populateChkItemImageDetails(List chkItemImageDetails, List imageListFromTagDetails,List imageIdDocDescsMap){
		List<ICheckListItemImageDetail> mergedChkItemImageDetails = new ArrayList<ICheckListItemImageDetail>();
		List<Long> imageIdListFromObChk = new ArrayList<Long>();
		String docDesc = "";
		
		for(ICheckListItemImageDetail chkDtl : (List<ICheckListItemImageDetail>) chkItemImageDetails) {
			imageIdListFromObChk.add(chkDtl.getImageId());
		}
		
		mergedChkItemImageDetails.addAll(chkItemImageDetails);
		
		for(IImageUploadAdd image: (List<IImageUploadAdd>) imageListFromTagDetails) {
			
			if(!imageIdListFromObChk.contains(image.getImgId())) {
				ICheckListItemImageDetail imageDetail = new OBCheckListItemImageDetail();
				imageDetail.setImageId(image.getImgId());
				imageDetail.setFileName(image.getImgFileName());
				imageDetail.setIsSelectedInd(ICMSConstant.NO);
				
				if(image.getFacilityDocName() != null && !"".equals(image.getFacilityDocName())) {
					docDesc = image.getFacilityDocName();
				}
				else if(image.getSecurityDocName() != null && !"".equals(image.getSecurityDocName())) {
					docDesc = image.getSecurityDocName();
				}
				else if(image.getCamDocName() != null && !"".equals(image.getCamDocName())) {
					docDesc = image.getCamDocName();
				}
				else if(image.getStatementDocName() != null && !"".equals(image.getStatementDocName())) {
					docDesc = image.getStatementDocName();
				}
				else if(image.getOthersDocsName() != null && !"".equals(image.getOthersDocsName())) {
					docDesc = image.getOthersDocsName();
				}else {
					docDesc = "";
				}
				
				imageDetail.setDocumentDescription(docDesc);
				imageDetail.setSubFolderName(image.getSubfolderName());
//				for(ArrayList image1: (ArrayList<ArrayList>) imageIdDocDescsMap) {
//					if(((Long)image1.get(0)) == (image.getImgId())) {
//				imageDetail.setDocumentDescription((String)image1.get(1));
//				}}
				mergedChkItemImageDetails.add(imageDetail);
			}
		}
		return mergedChkItemImageDetails;
	}
	
	private List<ICheckListItemImageDetail> updateSelectedIndChkItemImageDetails(
			List<ICheckListItemImageDetail> chkItemImageDetails, List<String> selectedImageIdList) {
		
		for(ICheckListItemImageDetail chkItemDtl : chkItemImageDetails) {
			if(selectedImageIdList.contains(String.valueOf(chkItemDtl.getImageId()))) {
				chkItemDtl.setIsSelectedInd(ICMSConstant.YES);
			}
			else {
				chkItemDtl.setIsSelectedInd(ICMSConstant.NO);
			}
		}
		
		return chkItemImageDetails;
	}
	
}
