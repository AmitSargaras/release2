/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/CheckListHelper.java,v 1.10 2005/12/22 10:13:06 vishal Exp $
 */

package com.integrosys.cms.ui.checklist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.ICheckListItemImageDetail;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.PropertiesConstantHelper;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyConstant;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;
import com.integrosys.cms.app.discrepency.bus.NoSuchDiscrepencyException;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.imageTag.bus.IImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.IImageTagMap;
import com.integrosys.cms.app.imageTag.bus.ImageTagDaoImpl;
import com.integrosys.cms.app.imageTag.bus.OBImageTagDetails;
import com.integrosys.cms.app.imageTag.bus.OBImageTagMap;
import com.integrosys.cms.app.imageTag.proxy.IImageTagProxyManager;
import com.integrosys.cms.app.imageTag.trx.IImageTagTrxValue;
import com.integrosys.cms.app.imageTag.trx.OBImageTagTrxValue;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.image.IImageUploadAdd;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.imageTag.IImageTagConstants;
import com.integrosys.cms.ui.imageTag.ImageTagException;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: vishal $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/12/22 10:13:06 $ Tag: $Name: $
 */
public class CheckListHelper {
	/**
	 * Method to get premium receipt list.
	 * 
	 * @return a HashMap of key:insurance code, value:premium receipt code
	 */
	public static HashMap getPremiumReceiptMap() {
		return CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_INSURANCE_RECEIPT);
	}

	/**
	 * Method to get premium receipt item.
	 * 
	 * @param childCode of type String
	 * @param docList an ArrayList of new documents of type IItem
	 * @return checklist document
	 */
	public static ICheckListItem getPremiumReceiptItem(String childCode, ArrayList docList) {
		Iterator i = docList.iterator();
		while (i.hasNext()) {
			IItem doc = (IItem) i.next();
			if ((doc.getMonitorType() != null) && doc.getMonitorType().equals(ICMSConstant.PREMIUM_RECEIPT)) {
				if ((childCode != null) && doc.getItemCode().equals(childCode)) {
					ICheckListItem item = new OBCheckListItem();
					item.setItem(doc);
					return item;
				}
			}
		}
		return null;
	}

	/**
	 * Method to get premium receipt checklist item.
	 * 
	 * @param childCode of type String
	 * @param checkListItems of type ICheckListItem[]
	 * @return checklist document
	 */
	public static ICheckListItem getPremiumReceiptItem(String childCode, ICheckListItem[] checkListItems) {
		for (int i = checkListItems.length - 1; i >= 0; i--) {
			IItem doc = checkListItems[i].getItem();
			if ((doc.getMonitorType() != null) && doc.getMonitorType().equals(ICMSConstant.PREMIUM_RECEIPT)) {
				if ((childCode != null) && doc.getItemCode().equals(childCode)) {
					return checkListItems[i];
				}
			}
		}
		return null;
	}

	/**
	 * Reset checklist item.
	 * 
	 * @param checkListItem of type ICheckListItem
	 * @return newly created checklist item
	 */
	public static ICheckListItem resetCheckListItem(ICheckListItem checkListItem) {
		IItem item = checkListItem.getItem();
		ICheckListItem newItem = new OBCheckListItem();
		newItem.setItem(item);
		return newItem;
	}

	public static String formatSecurityRank(ILimitCharge limitCharge) {
		String securityRank = null;

		if (limitCharge.getSecurityRank() == 0) {
			securityRank = "-";
		}
		else if (limitCharge.getSecurityRank() == 1) {
			securityRank = limitCharge.getSecurityRank() + "st Charge";
		}
		else if (limitCharge.getSecurityRank() == 2) {
			securityRank = limitCharge.getSecurityRank() + "nd Charge";
		}
		else if (limitCharge.getSecurityRank() == 3) {
			securityRank = limitCharge.getSecurityRank() + "rd Charge";
		}
		else {
			securityRank = limitCharge.getSecurityRank() + "th Charge";
		}

		return securityRank;
	}

	public static boolean isNewCheckListItem(ICheckListItem[] itemList, long itemRef) {
		if ((itemRef == 0) || (itemRef == ICMSConstant.LONG_INVALID_VALUE)) {
			return true;
		}
		if (itemList != null) {
			for (int i = 0; i < itemList.length; i++) {
				if (itemList[i].getCheckListItemRef() == itemRef) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Check if the given item reference/ID is a new checklist item.
	 * 
	 * @param itemList of type ICheckListLitem[]
	 * @param itemRef of type long
	 * @param itemPK of type long
	 * @return boolean
	 */
	public static boolean isNewCheckListItem(ICheckListItem[] itemList, long itemRef, long itemPK) {
		if ((itemRef == 0) || (itemRef == ICMSConstant.LONG_INVALID_VALUE) || (itemPK == 0)
				|| (itemPK == ICMSConstant.LONG_INVALID_VALUE)) {
			return true;
		}
		if (itemList != null) {
			for (int i = 0; i < itemList.length; i++) {
				if (itemList[i].getCheckListItemRef() == itemRef) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Traverses the document tree to determine if itemRef is a parent.
	 * @param checkListItems array of ICheckList objects representing tree
	 * @param itemRef checklist item to query for parenthood
	 * @return boolean - true if itemRef is a parent
	 */
	public static boolean isParent(ICheckListItem[] checkListItems, long itemRef) {
		if ((checkListItems != null) && (itemRef != 0) && (itemRef != ICMSConstant.LONG_INVALID_VALUE)) {
			for (int i = 0; i < checkListItems.length; i++) // iterates through
			// ICheckListItem
			// array
			{
				long parentItemRef = checkListItems[i].getParentCheckListItemRef();
				if ((parentItemRef != 0) && (parentItemRef != ICMSConstant.LONG_INVALID_VALUE)
						&& (parentItemRef == itemRef)) {
					return true; // cos child of itemRef is found
				}
			}
		}
		return false; // no child of itemRef is found
	}

	/**
	 * Sorts the list of ICheckListItems based on their parent codes. The return
	 * value is the result of a prefix transversal on the internal tree.
	 * 
	 * @param items The items to be sorted
	 */
	public static ICheckListItem[] sortByParentPrefix(ICheckListItem[] items) {

		if (items == null) {
			return null;
		}
		else if (items.length <= 1) {
			return items;
		}

		// first put all the items into a Map, keyed by itemRef
		SortedMap pool = new TreeMap();
		HashMap removedPool = new HashMap();
		Vector parentItemRefVect = new Vector();
		Vector newItems = new Vector();
		for (int i = 0; i < items.length; i++) {
			if (items[i].getCheckListItemRef() == ICMSConstant.LONG_INVALID_VALUE) {
				newItems.add(items[i]);
			}
			else {
				pool.put(new Long(items[i].getCheckListItemRef()), items[i]); // key
				// =
				// checkListItemRef
				// ,
				// value
				// =
				// checkListItem
				// object
				if (!parentItemRefVect.contains(new Long(items[i].getParentCheckListItemRef()))) {
					parentItemRefVect.add(new Long(items[i].getParentCheckListItemRef())); // add
					// unique
					// parentItemRef
					// to
					// vector
				}
			}
		}

		// There is no fixed ID for the root. Items with parentID 0 or
		// orphans are attached to the artifical root.
		TreeNode root = new TreeNode();

		// for each item in the global pool, check if the parent exists
		for (Iterator i = pool.values().iterator(); i.hasNext();) {
			ICheckListItem poolItem = (ICheckListItem) i.next();

			Long parentItemRef = new Long(poolItem.getParentCheckListItemRef());

			// if item has no parent but has children, i.e. is a top-most parent
			if (!pool.containsKey(parentItemRef) && !removedPool.containsKey(parentItemRef)
					&& parentItemRefVect.contains(new Long(poolItem.getCheckListItemRef()))) {
				// add top-most parent as a child of the artificial root
				TreeNode node = new TreeNode(poolItem);
				root.addChild(poolItem.getCheckListItemRef(), node);
				removedPool.put(new Long(poolItem.getCheckListItemRef()), poolItem);
				i.remove(); // remove the node from the global pool
			}
		}

		// by this point, all items remaining in the global pool either have a
		// parent or are childless orphans
		// get the child of the root in sorted order
		Collection rootChilds = root.children.values();

		// now recurse into each of the child of the parent and attach their
		// children
		for (Iterator i = rootChilds.iterator(); i.hasNext();) {
			TreeNode n = (TreeNode) i.next();
			attachDescendents(n.payload.getCheckListItemRef(), n, pool);
		}

		// flatten the tree by doing a prefixTransversal
		ICheckListItem[] nonSingleItems = prefixTransversal(root);
		// int totalItemCount = nonSingleItems.length + pool.size();
		int totalItemCount = items.length;
		ICheckListItem[] result = new ICheckListItem[totalItemCount];

		// do blind copy for the non-single items
		for (int i = 0; i < nonSingleItems.length; i++) {
			result[i] = nonSingleItems[i];
		}

		int count = nonSingleItems.length;
		// append remaining childless orphans to the back of the result array
		for (Iterator j = pool.values().iterator(); j.hasNext();) {
			ICheckListItem poolItem = (ICheckListItem) j.next();
			result[count++] = poolItem;
		}

		if (newItems != null) {
			for (Iterator j = newItems.iterator(); j.hasNext();) {
				ICheckListItem newItem = (ICheckListItem) j.next();
				result[count++] = newItem;
			}
		}
		return result;
	}

	// pick elements from the pool whose's parentID is id and attach them to the
	// target
	private static void attachDescendents(long id, TreeNode target, Map pool) {
		for (Iterator i = pool.values().iterator(); i.hasNext();) {
			ICheckListItem poolItem = (ICheckListItem) i.next();
			long itemRef = poolItem.getCheckListItemRef();
			long parentItemRef = poolItem.getParentCheckListItemRef();

			if (parentItemRef == id) {
				target.addChild(itemRef, new TreeNode(poolItem));
				i.remove();
			}
		}
		// now recursively call attachDescendents for each of the child of
		// target
		for (Iterator i = target.children.values().iterator(); i.hasNext();) {
			TreeNode node = (TreeNode) i.next();
			attachDescendents(node.payload.getCheckListItemRef(), node, pool);
		}
	}

	static class TreeNode {
		ICheckListItem payload;

		SortedMap children = new TreeMap();

		TreeNode() {
		}

		TreeNode(ICheckListItem payload) {
			this.payload = payload;
		}

		void addChild(long key, TreeNode tn) {
			children.put(new Long(key), tn);
		}
	}

	//
	private static ICheckListItem[] prefixTransversal(TreeNode root) {
		ArrayList al = new ArrayList();

		if (root.payload != null) {
			al.add(root.payload);
		}

		for (Iterator i = root.children.values().iterator(); i.hasNext();) {
			addPayLoad((TreeNode) i.next(), al);
		}
		return (ICheckListItem[]) al.toArray(new ICheckListItem[al.size()]);
	}

	private static void addPayLoad(TreeNode node, List l) {
		if (node.payload != null) {
			l.add(node.payload);
		}

		for (Iterator i = node.children.values().iterator(); i.hasNext();) {
			addPayLoad((TreeNode) i.next(), l);
		}
	}

	public static boolean isExpired(IItem anItem) {
		Date expiryDate = anItem.getExpiryDate();

		if (expiryDate != null) {
            //Andy Wong, 21 May 2010: strip current time when comparing against expiry date, template expiry date doesnt include time
			Date currentDate = DateUtil.clearTime(DateUtil.getDate());

			if (expiryDate.getTime() < currentDate.getTime()) {
				return true;
			}
		}
		return false;
	}

	public static boolean toDisplayCheckListItem(ICheckListItem checkListItem) {
		return !(PropertiesConstantHelper.isFilterPreApprovalDocuments() && checkListItem.getItem().getIsPreApprove() && !checkListItem
				.getIsLockedInd());
	}
	
	public static String getTagUntaggedFileNameList(Map<Long, String> imageIdUnTaggedStatusMap, ICheckListItemImageDetail[] checkListItemImageDetails, boolean isTagged) {
		List<String> fileNamesList = new ArrayList<String>();
		long imageId = 0;
		for (Long key : imageIdUnTaggedStatusMap.keySet()) {
			System.out.println("getTagUntaggedFileNameList => key ==>"+key);
			imageId = key;
	    }
		
		if(checkListItemImageDetails != null) {
			for(ICheckListItemImageDetail chkDtl : checkListItemImageDetails) {
				if(ICMSConstant.YES.equals(chkDtl.getIsSelectedInd()) || imageId == chkDtl.getImageId()){
//				if(imageId == chkDtl.getImageId()){  || imageId == chkDtl.getImageId()
					String status =  imageIdUnTaggedStatusMap.get(chkDtl.getImageId());
					if((!isTagged && ITagUntagImageConstant.TAGGED.equals(status)) || 
							(isTagged && (status == null || ITagUntagImageConstant.UN_TAGGED.equals(status)))) {
						fileNamesList.add(chkDtl.getFileName());
					}
				}
			}
		}
		return fileNamesList.isEmpty() ? null : StringUtils.join(fileNamesList.toArray(), ITagUntagImageConstant.COMMA_DELIMITER);
	}
	
	public static void processImageDetailsTrx(ICheckListItemImageDetail[] checkListItemImageDetails, long checkListItemID, Map<Long, String> chkItemIdMap, 
			String checkListType, String cifNumber, OBTrxContext ctx) throws ImageTagException, CommandProcessingException, TransactionException {
		List<Long> selectedImageIds = new ArrayList<Long>();
		
		List<ICheckListItemImageDetail> selectedImageChkItems = new ArrayList<ICheckListItemImageDetail>();
		if(checkListItemImageDetails != null) {
			List<ICheckListItemImageDetail> chkImglist = Arrays.asList(checkListItemImageDetails);
			
			for(ICheckListItemImageDetail dtl : chkImglist) {
				if(ICMSConstant.YES.equals(dtl.getIsSelectedInd())) {
					selectedImageIds.add(dtl.getImageId());
					selectedImageChkItems.add(dtl);
				}
			}
			
			Long[] selectedImageIdsArray = selectedImageIds.toArray(new Long[0]); 
			
			DefaultLogger.info(CheckListHelper.class.getName() , "For checkListItemID : "+checkListItemID+ " | checkListType: "+checkListType + 
					" | cifNumber: "+cifNumber + " | chkItemIdMap : "+chkItemIdMap+ " | selectedImageIds: "+ selectedImageIds);
			
			ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
			String categoryCode = "";
			
			if(!selectedImageChkItems.isEmpty()) {
				IImageTagDetails imageTagDetails = new OBImageTagDetails();
				IImageTagMap imageTagMapvalues = new OBImageTagMap();
				IImageTagTrxValue trxValueIn = new OBImageTagTrxValue();
				IImageTagTrxValue trxValueOutMakerSubmit = new OBImageTagTrxValue();	
				
				imageTagDetails.setCustId(cifNumber);
				imageTagDetails.setDocDesc(chkItemIdMap.get(checkListItemID));
				if(ICMSConstant.CHECKLIST_CAM.equals(checkListType)) {
					imageTagDetails.setDocType(IImageTagConstants.CAM_DOC);
//					imageTagDetails.setCategory(IImageTagConstants.IMG_CATEGORY_CAM);
					categoryCode = imageTagDaoImpl.getCategorycode(IImageTagConstants.IMG_CATEGORY_CAM);
					imageTagDetails.setCategory(categoryCode);
				}
				else if(ICMSConstant.CHECKLIST_OTHER.equals(checkListType)) {
					imageTagDetails.setDocType(IImageTagConstants.OTHER_DOC);
//					imageTagDetails.setCategory(IImageTagConstants.IMG_CATEGORY_OTHERS);
					categoryCode = imageTagDaoImpl.getCategorycode(IImageTagConstants.IMG_CATEGORY_OTHERS);
					imageTagDetails.setCategory(categoryCode);
				}
				else if(ICMSConstant.CHECKLIST_RECURRENT.equals(checkListType)) {
					imageTagDetails.setDocType(IImageTagConstants.RECURRENTDOC_DOC);
//					imageTagDetails.setCategory(IImageTagConstants.IMG_CATEGORY_STATEMENT);
					categoryCode = imageTagDaoImpl.getCategorycode(IImageTagConstants.IMG_CATEGORY_STATEMENT);
					imageTagDetails.setCategory(categoryCode);
				}
				else if(ICMSConstant.CHECKLIST_FACILITY_F.equals(checkListType)) {
					imageTagDetails.setDocType(IImageTagConstants.FACILITY_DOC);
					categoryCode = imageTagDaoImpl.getCategorycode(IImageTagConstants.IMG_CATEGORY_FACILITY);
					imageTagDetails.setCategory(categoryCode);
				}
				else if(ICMSConstant.CHECKLIST_SECURITY_S.equals(checkListType)) {
					imageTagDetails.setDocType(IImageTagConstants.SECURITY_DOC);
					categoryCode = imageTagDaoImpl.getCategorycode(IImageTagConstants.IMG_CATEGORY_SECURITY);
					imageTagDetails.setCategory(categoryCode);
				}
				
				IImageTagProxyManager imageTagProxy = (IImageTagProxyManager) BeanHouse.get("imageTagProxy");
				IImageTagDetails existingTagDetails = imageTagProxy.getExistingImageTag(imageTagDetails);
				Map<Long,String> selectedImageIdStatusMap = imageTagProxy.getImageIdTaggedStatusMap(chkItemIdMap.get(checkListItemID));
				
				//Maker Submit
				{
					if(existingTagDetails!=null){
						trxValueIn = imageTagProxy.getImageTagTrxByID((Long.toString(existingTagDetails.getId())));
						
						if(trxValueIn.getLimitProfileReferenceNumber()== null){
							String customerId = Long.toString(trxValueIn.getCustomerID());
							String camId = CollateralDAOFactory.getDAO().getCamIdByCustomerID(customerId);
							trxValueIn.setLimitProfileReferenceNumber(camId);
						}
						
						if("ACTIVE".equals(trxValueIn.getStatus())) {
							trxValueOutMakerSubmit = imageTagProxy.makerUpdateImageTagDetails(ctx,trxValueIn, imageTagDetails);
							imageTagMapvalues.setTagId(Long.parseLong(trxValueOutMakerSubmit.getStagingReferenceID()));
							for (int i = 0; i < selectedImageIdsArray.length; i++) {
								imageTagMapvalues.setImageId(selectedImageIdsArray[i]);
								
								DefaultLogger.info(CheckListHelper.class.getName() , "Creating staging records for :"+imageTagMapvalues.getImageId()+ 
										" | UntaggedStatus : "+imageTagMapvalues.getUntaggedStatus());
								
								imageTagProxy.createImageTagMap(imageTagMapvalues);
							}
						}
					}
					else {
						trxValueOutMakerSubmit = imageTagProxy.makerCreateImageTagDetails(ctx, imageTagDetails);
						imageTagMapvalues.setTagId(Long.parseLong(trxValueOutMakerSubmit.getStagingReferenceID()));
						for (int i = 0; i < selectedImageIdsArray.length; i++) {
							imageTagMapvalues.setImageId(selectedImageIdsArray[i]);
							
							DefaultLogger.info(CheckListHelper.class.getName() , "Creating new staging records for :"+imageTagMapvalues.getImageId()+ 
									" | UntaggedStatus : "+imageTagMapvalues.getUntaggedStatus());
							
							imageTagProxy.createImageTagMap(imageTagMapvalues);
						}
					}
				}
				//End Maker submit
				
				//Checker approve
				{
					IImageTagDetails imageTag = (OBImageTagDetails) trxValueOutMakerSubmit.getStagingImageTagDetails();
					
					if(imageTag != null) {
						List imageList = imageTagProxy.getStagingTagImageList(Long.toString(imageTag.getId()),IImageTagConstants.STATUS_ALL);
						
						IImageTagTrxValue trxValueOutCheckerApprove = imageTagProxy.checkerApproveImageTag(ctx, trxValueOutMakerSubmit);
						if (trxValueOutCheckerApprove.getReferenceID() != null) {
							imageTagMapvalues.setTagId(Long.parseLong(trxValueOutCheckerApprove.getReferenceID()));
						} else {
							imageTagMapvalues.setTagId(Long.parseLong(trxValueOutCheckerApprove.getStagingReferenceID()));
						}
						
						imageList = imageTagProxy.getTagImageList(Long.toString(imageTagMapvalues.getTagId()),IImageTagConstants.STATUS_ALL);
						HashMap existingImagesMap=new HashMap(); 
						if(imageList!=null && imageList.size()>0){
							for (int i = 0; i < imageList.size(); i++) {
								IImageUploadAdd imageDetail = (IImageUploadAdd) imageList.get(i);
								existingImagesMap.put(imageDetail.getImgId(),imageDetail);
							} 
						}
						
						for (int i = 0; i < selectedImageIdsArray.length; i++) {
							if(!existingImagesMap.containsKey(selectedImageIdsArray[i])){
								imageTagMapvalues.setImageId(selectedImageIdsArray[i]);
								imageTagProxy.checkerCreateImageTagMap(imageTagMapvalues);
								
								DefaultLogger.info(CheckListHelper.class.getName() , "Approving create staging records for :"+imageTagMapvalues.getImageId()+ 
										" with UntaggedStatus as : "+imageTagMapvalues.getUntaggedStatus());
								
							}else{
								imageTagMapvalues.setImageId(selectedImageIdsArray[i]);
								String updatedStatus = ICMSConstant.YES;
								if(ITagUntagImageConstant.TAGGED.equals(selectedImageIdStatusMap.get(selectedImageIdsArray[i]))) {
									updatedStatus = ICMSConstant.YES;
								}
								else {
									updatedStatus = ICMSConstant.NO;
								}
								imageTagMapvalues.setUntaggedStatus(updatedStatus);
								
								DefaultLogger.info(CheckListHelper.class.getName() , "Approving update staging records for :"+imageTagMapvalues.getImageId()+ 
										" with UntaggedStatus as : "+imageTagMapvalues.getUntaggedStatus());
								
								imageTagProxy.checkerApproveUpdateImageTagMap(imageTagMapvalues);
							}
						}
					}
				}
				//End Checker approve
			}
		}
	}
	
	public static ICheckListItem getItem(ICheckListItem temp[], long itemRef) {
		ICheckListItem item = null;
		if (temp == null) {
			return item;
		}
		for (int i = 0; i < temp.length; i++) {
			if (temp[i].getCheckListItemRef() == itemRef) {
				item = temp[i];
			}
			else {
				continue;
			}
		}
		return item;
	}

	
	public static IImageTagDetails populateImageTagDetails(String checkListType, String cifNumber) {
		IImageTagDetails tagDetails = new OBImageTagDetails();
		tagDetails.setCustId(cifNumber);
		ImageTagDaoImpl imageTagDaoImpl=new ImageTagDaoImpl();
		String categoryCode = "";
		
		if(ICMSConstant.CHECKLIST_CAM.equals(checkListType)) {
			categoryCode = imageTagDaoImpl.getCategorycode(IImageTagConstants.IMG_CATEGORY_CAM);
			tagDetails.setCategory(categoryCode);
		}
		else if(ICMSConstant.CHECKLIST_OTHER.equals(checkListType)) {
			categoryCode = imageTagDaoImpl.getCategorycode(IImageTagConstants.IMG_CATEGORY_OTHERS);
			tagDetails.setCategory(categoryCode);
		}
		else if(ICMSConstant.CHECKLIST_RECURRENT.equals(checkListType)) {
//			tagDetails.setCategory(IImageTagConstants.IMG_CATEGORY_STATEMENT);
			categoryCode = imageTagDaoImpl.getCategorycode(IImageTagConstants.IMG_CATEGORY_STATEMENT);
			tagDetails.setCategory(categoryCode);
		}
		else if(ICMSConstant.CHECKLIST_FACILITY_F.equals(checkListType)) {
			categoryCode = imageTagDaoImpl.getCategorycode(IImageTagConstants.IMG_CATEGORY_FACILITY);
			tagDetails.setCategory(categoryCode);
		}
		else if(ICMSConstant.CHECKLIST_SECURITY_S.equals(checkListType)) {
			categoryCode = imageTagDaoImpl.getCategorycode(IImageTagConstants.IMG_CATEGORY_SECURITY);
			tagDetails.setCategory(categoryCode);
		}
		return tagDetails;
		
	}
	
	public static String getLabelByLabelValue(String fieldName, List<LabelValueBean> labelValueList) {
		if(StringUtils.isNotBlank(fieldName) && !CommonUtil.isEmptyList(labelValueList)) {
			for(LabelValueBean item : labelValueList) {
				if(item.getValue() != null && fieldName.equals(item.getValue().trim())) {
					return item.getLabel();
				}
			}
		}
		return null;
	}
	
	public static List<String> getAllowedCorporateEntities() {
		String allowedCorporateEntities = PropertyManager.getValue("roc.charge.corporate.parameter.value");
		System.out.println("getAllowedCorporateEntities()=>allowedCorporateEntities=>"+allowedCorporateEntities);
		return getListFromDelimitedString(allowedCorporateEntities, ",");
	}
	
	public static <T> List<String> getListFromDelimitedString(String delimittedString, String delimitter) {
		if(StringUtils.isNotBlank(delimitter) &&  StringUtils.isNotBlank(delimittedString)) {
			List<String> delimitedList = new ArrayList<String>();
			String[] splittedStringArray =  delimittedString.split(delimitter);
			for(String str : splittedStringArray) {
				delimitedList.add(str.trim());
				System.out.println("getAllowedCorporateEntities()=>delimitedList.add=>"+str.trim()+"*");
			}
			return delimitedList;
		}
		return Collections.emptyList();
	}

	public static void processDiscrepencyTrx(ICheckListItem iCheckListItem, ICMSCustomer customer, OBTrxContext ctx) throws NoSuchDiscrepencyException, TrxParameterException, TransactionException, ConcurrentUpdateException {
		
		//Start Maker Submit Discrepency
		IDiscrepency makerDiscrepency = new OBDiscrepency();
		makerDiscrepency.setDiscrepencyType(IDiscrepencyConstant.DEFAULT_DISCREPENCY_TYPE);
		makerDiscrepency.setCritical(IDiscrepencyConstant.DEFAULT_DISCREPENCY_CRITICAL);
		makerDiscrepency.setDiscrepencyRemark(IDiscrepencyConstant.DEFAULT_REMARKS);
		makerDiscrepency.setDiscrepency(IDiscrepencyConstant.DEFAULT_DISCREPENCY_CODE);
		makerDiscrepency.setCreationDate(DateUtil.clearTime(DateUtil.getDate()));
		makerDiscrepency.setOriginalTargetDate(UIUtil.addDate(makerDiscrepency.getCreationDate(), Calendar.DATE, IDiscrepencyConstant.DEFAULT_TARGET_DAYS));
		makerDiscrepency.setStatus("PENDING_CREATE");
	    makerDiscrepency.setCustomerId(customer.getCustomerID());
	    IDiscrepencyProxyManager discrepencyProxy = (IDiscrepencyProxyManager) BeanHouse.get("discrepencyProxy");
	    IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
	    
		IDiscrepencyTrxValue makerTrxValueOut = new OBDiscrepencyTrxValue();
		makerTrxValueOut = discrepencyProxy.makerCreateDiscrepency(ctx, makerTrxValueOut, makerDiscrepency);
		
		OBDiscrepency updateStage = new OBDiscrepency();
		AccessorUtil.copyValue(makerDiscrepency, updateStage, IDiscrepencyConstant.EXCLUDE_FOR_MAKER);
		updateStage.setId(Long.parseLong(makerTrxValueOut.getStagingReferenceID()));
		updateStage.setTransactionStatus(makerTrxValueOut.getStatus());
		
		discrepencyDAO.updateStageDiscrepency(updateStage);
		//End Maker Submit
		
		//Checker Approve Start
		IDiscrepency checkerDiscrepency = (OBDiscrepency) makerTrxValueOut.getStagingDiscrepency();
		if(checkerDiscrepency.getStatus().equals("PENDING_CREATE")){
			checkerDiscrepency.setStatus("ACTIVE");
		}
		checkerDiscrepency.setTransactionStatus("ACTIVE");
		makerTrxValueOut.setStagingDiscrepency(checkerDiscrepency);
		
		IDiscrepencyTrxValue checkerTrxValueOut = discrepencyProxy.checkerApproveDiscrepency(ctx, makerTrxValueOut);
		
		long id = updateStage.getId();
		System.out.println("ChecklistHelper.java=>Dicrepancy for NO =>Stagig Ref Id=>"+id);
		long refId = Long.parseLong(getTrxRefIds(id));
		
		OBDiscrepency updateActual = (OBDiscrepency)checkerTrxValueOut.getActualDiscrepency();
		AccessorUtil.copyValue(makerDiscrepency, updateActual, IDiscrepencyConstant.EXCLUDE_FOR_CHECKER_ACTUAL);
		updateActual.setTransactionStatus(checkerTrxValueOut.getStatus());
		updateActual.setId(refId);
		discrepencyDAO.updateDiscrepency(updateActual);
		//createDiscrepencyRoc
		
		OBDiscrepency checkerStagingDiscrepency = new OBDiscrepency();
		AccessorUtil.copyValue(makerDiscrepency, checkerStagingDiscrepency, IDiscrepencyConstant.EXCLUDE_FOR_CHECKER_STAGING);
		checkerStagingDiscrepency.setId(Long.parseLong(checkerTrxValueOut.getStagingReferenceID()));
		checkerStagingDiscrepency.setTransactionStatus(checkerTrxValueOut.getStatus());
		checkerStagingDiscrepency.setStatus(checkerTrxValueOut.getActualDiscrepency().getStatus());
		
		discrepencyDAO.updateStageDiscrepency(checkerStagingDiscrepency);
		//Checker Approve End
	}
	
	public static String getTrxRefIds(long stageRefId) {
//		List filenameOFimageList = new ArrayList();
		String refId="";
		DBUtil dbUtil=null;
		try {
			String sql = "SELECT DISTINCT REFERENCE_ID FROM TRANSACTION WHERE STAGING_REFERENCE_ID='"+stageRefId+"' ";
		
		System.out.println("checklisthelper.java => getTrxRefId() Query sql=>"+sql);
		
			 dbUtil=new DBUtil();
			 ResultSet rs=null;
			 
			dbUtil.setSQL(sql);
			rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					refId=rs.getString("REFERENCE_ID");
					System.out.println("checklisthelper.java => getTrxRefId() REFERENCE_ID=>"+refId);
				}
			}
			rs.close();
		}catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{ 
			try {
				dbUtil.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return refId;
	}
	
}
