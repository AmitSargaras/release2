/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/MergeTemplateUtil.java,v 1.4 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.util.ArrayList;

import com.integrosys.base.techinfra.logger.DefaultLogger;


/**
 * Implements the IMergeTemplateUtil to provide the logic for merging the
 * templates
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class MergeTemplateUtil implements IMergeTemplateUtil {
	/**
	 * Implementation for preparing the template retrieval
	 * @param aParentTemplate - ITemplate
	 * @param aChildTemplate - ITemplate
	 * @return ITemplate - the template for retrieval
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException on errors
	 */
	public ITemplate prepareTemplateForRetrieval(ITemplate aParentTemplate, ITemplate aChildTemplate)
			throws CheckListTemplateException {
		if ((aParentTemplate == null) && (aChildTemplate == null)) {
			return null;
		}

		if (aParentTemplate == null) {
			return aChildTemplate;
		}

		if (aChildTemplate == null) {
			return convertParentToChild(aParentTemplate);
		}

		return performMerging(aParentTemplate, aChildTemplate);
	}

	/**
	 * To prepare the template for persistence based on merging the parent and
	 * child templates based on the implementation provided by the implementing
	 * class
	 * @param aParentTemplate - ITemplate
	 * @param aChildTemplate - ITemplate
	 * @return ITemplate - the template for persistence
	 * @throws CheckListTemplateException on errors
	 */
	public ITemplate prepareTemplateForPersistence(ITemplate aParentTemplate, ITemplate aChildTemplate)
			throws CheckListTemplateException {
		if ((aParentTemplate == null) && (aChildTemplate == null)) {
			return null;
		}

		if (aParentTemplate == null) {
			return aChildTemplate;
		}

		if (aChildTemplate == null) {
			return convertParentToChild(aParentTemplate);
		}

		return performMergingForPersistence(aParentTemplate, aChildTemplate);
	}

	/**
	 * Convert a Parent Template to a child template for retrieval
	 * @param aParentTemplate - ITemplate
	 * @return ITemplate - the converted template
	 * @throws CheckListTemplateException on errors
	 */
	private ITemplate convertParentToChild(ITemplate aParentTemplate) throws CheckListTemplateException {
		ITemplate template = aParentTemplate;
		template.setTemplateItemList(convertParentItemList(aParentTemplate.getTemplateItemList()));
		DefaultLogger.debug(this, "Number of Items converted to child: " + ((template.getTemplateItemList() == null) ? 0 : template.getTemplateItemList().length));
		return template;
	}

	/**
	 * To merge the parent and child templates based on the required logic for
	 * persistence
	 * @param aParentTemplate - ITemplate
	 * @param aChildTemplate - ITemplate
	 * @return ITemplate - the merged template
	 * @throws CheckListTemplateException on errors
	 */
	private ITemplate performMergingForPersistence(ITemplate aParentTemplate, ITemplate aChildTemplate)
			throws CheckListTemplateException {
		ITemplate newTemplate = aChildTemplate;
		ITemplateItem[] parentItemList = aParentTemplate.getTemplateItemList();
		ITemplateItem[] childItemList = aChildTemplate.getTemplateItemList();
		newTemplate.setTemplateItemList(getItemListForPersistence(parentItemList, childItemList));
		return newTemplate;
	}

	private ITemplateItem[] getItemListForPersistence(ITemplateItem[] aParentItemList, ITemplateItem[] aChildItemList)
			throws CheckListTemplateException {
		// To formulate check through the child and remove any of them that is
		// inherited
		if ((aParentItemList == null) || (aParentItemList.length == 0)) {
			return reconstructChildItemList(aChildItemList);
		}

		// To convert the parent items to the child items
		if ((aChildItemList == null) || (aChildItemList.length == 0)) {
			return convertParentItemList(aParentItemList);
		}

		// To merge the parent and child as some value in the child is to be
		// inherited from the parent items
		return mergeParentChildItemListForPersistence(aParentItemList, aChildItemList);
	}

	/**
	 * Merge the parent and child item list. Some of the attributes in the
	 * parent is not required to be persisted in the child
	 * @param aParentItemList - ITemplateItem[]
	 * @param aChildItemList - ITemplateItem[]
	 * @return ITemplateItem[] - the list of merged items
	 * @throws CheckListTemplateException on errors
	 */
	private ITemplateItem[] mergeParentChildItemListForPersistence(ITemplateItem[] aParentItemList,
			ITemplateItem[] aChildItemList) {
		ArrayList itemList = new ArrayList();
		ITemplateItem item = null;
		for (int ii = 0; ii < aChildItemList.length; ii++) {
			item = aChildItemList[ii];
			if (item.isInherited()) {
				for (int jj = 0; jj < aParentItemList.length; jj++) {
					if (item.getParentItemID() == aParentItemList[jj].getTemplateItemID()) {
						itemList.add(mergeTemplateItemForPersistence(aParentItemList[jj], item));
						break;
					}
				}
			}
			else {
				itemList.add(item);
			}
		}

		for (int ii = 0; ii < aParentItemList.length; ii++) {
			boolean inChild = false;
			for (int jj = 0; jj < aChildItemList.length; jj++) {
				if (aParentItemList[ii].getTemplateItemID() == aChildItemList[jj].getParentItemID()) {
					inChild = true;
					break;
				}
			}
			if (!inChild) {
				itemList.add(convertParentItemToChildForPersistence(aParentItemList[ii]));
			}
		}
		return (ITemplateItem[]) itemList.toArray(new ITemplateItem[0]);
	}

	/**
	 * To merge the parent and child templates based on the required logic
	 * @param aParentTemplate - ITemplate
	 * @param aChildTemplate - ITemplate
	 * @return ITemplate - the merged template
	 * @throws CheckListTemplateException on errors
	 */
	private ITemplate performMerging(ITemplate aParentTemplate, ITemplate aChildTemplate) throws CheckListTemplateException {
		ITemplate newTemplate = aChildTemplate;
		ITemplateItem[] parentItemList = aParentTemplate.getTemplateItemList();
		ITemplateItem[] childItemList = aChildTemplate.getTemplateItemList();

		newTemplate.setTemplateItemList(mergeTemplateItemList(parentItemList, childItemList));
		return newTemplate;
	}

	/**
	 * To merge the parent and child template items
	 * @param aParentItemList - ITemplateItem[]
	 * @param aChildItemList - ITemplateItem[]
	 * @return ITemplateItem[] - the list of merged template item
	 * @throws CheckListTemplateException on errors
	 */
	private ITemplateItem[] mergeTemplateItemList(ITemplateItem[] aParentItemList, ITemplateItem[] aChildItemList)
			throws CheckListTemplateException {
		// To formulate check through the child and remove any of them that is
		// inherited
		if ((aParentItemList == null) || (aParentItemList.length == 0)) {
			return reconstructChildItemList(aChildItemList);
		}

		// To convert the parent items to the child items
		if ((aChildItemList == null) || (aChildItemList.length == 0)) {
			return convertParentItemList(aParentItemList);
		}

		// To merge the parent and child as some value in the child is to be
		// inherited from the parent items
		return mergeParentChildItemList(aParentItemList, aChildItemList);
	}

	/**
	 * To reconstruct the child item list from the parent item list. Remove any
	 * item that used to be in the parent as parent has no items
	 * @param aChildItemList - ITemplateItem[]
	 * @return ITemplateItem[] - the reconstructed item list
	 * @throws CheckListTemplateException on errors
	 */
	private ITemplateItem[] reconstructChildItemList(ITemplateItem[] aChildItemList) throws CheckListTemplateException {
		if (aChildItemList == null) {
			return null;
		}

		ArrayList itemList = new ArrayList();
		for (int ii = 0; ii < aChildItemList.length; ii++) {
			if (!aChildItemList[ii].isInherited()) {
				itemList.add(aChildItemList[ii]);
			}
		}
		DefaultLogger.debug(this, "Number of items converted: " + itemList.size());
		return (ITemplateItem[]) itemList.toArray(new ITemplateItem[0]);
	}

	/**
	 * Convert the parent item to the child item. By populating value from
	 * parent to child
	 * @param aParentItemList - ITemplateItem[]
	 * @return ITemplateItem[] - the converted items
	 * @throws CheckListTemplateException on errors
	 */
	private ITemplateItem[] convertParentItemList(ITemplateItem[] aParentItemList) throws CheckListTemplateException {
		if (aParentItemList == null) {
			return null;
		}

		ArrayList itemList = new ArrayList();
		DefaultLogger.debug(this, "Number of items to convert: " + aParentItemList.length);
		for (int ii = 0; ii < aParentItemList.length; ii++) {
			itemList.add(convertParentItemToChild(aParentItemList[ii]));
		}
		DefaultLogger.debug(this, "Number of items converted: " + itemList.size());
		return (ITemplateItem[]) itemList.toArray(new ITemplateItem[0]);
	}

	/**
	 * Convert the parent item to the child item for persistence. By unsetting
	 * some of the child value that is to inherit from parent.
	 * @param aParentItemList - ITemplateItem[]
	 * @return ITemplateItem[] - the converted items
	 * @throws CheckListTemplateException on errors
	 */
	private ITemplateItem[] convertParentItemListForPersistence(ITemplateItem[] aParentItemList)
			throws CheckListTemplateException {
		if (aParentItemList == null) {
			return null;
		}

		ArrayList itemList = new ArrayList();
		for (int ii = 0; ii < aParentItemList.length; ii++) {
			itemList.add(convertParentItemToChildForPersistence(aParentItemList[ii]));
		}
		return (ITemplateItem[]) itemList.toArray(new ITemplateItem[0]);
	}

	/**
	 * To convert a parent template item to a child template item
	 * @param aParentItem - ITemplateItem
	 * @return ITemplateItem - the converted template item
	 */
	private ITemplateItem convertParentItemToChild(ITemplateItem aParentItem) {
		ITemplateItem item = aParentItem;
		item.setParentItemID(aParentItem.getTemplateItemID());
		item.setTemplateItemID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
		return item;
	}

	/**
	 * To convert a parent template item to a child template item for
	 * persistence
	 * @param aParentItem - ITemplateItem
	 * @return ITemplateItem - the converted template item
	 */
	private ITemplateItem convertParentItemToChildForPersistence(ITemplateItem aParentItem) {
		ITemplateItem item = aParentItem;
		item.setParentItemID(aParentItem.getTemplateItemID());
		item.setTemplateItemID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
		item.setItem(null);
		return item;
	}

	/**
	 * Merge the parent and child item list. Some of the attributes in the
	 * parent is to overwrite the child
	 * @param aParentItemList - ITemplateItem[]
	 * @param aChildItemList - ITemplateItem[]
	 * @return ITemplateItem[] - the list of merged items
	 * @throws CheckListTemplateException on errors
	 */
	private ITemplateItem[] mergeParentChildItemList(ITemplateItem[] aParentItemList, ITemplateItem[] aChildItemList)
			throws CheckListTemplateException {
		ArrayList itemList = new ArrayList();
		ITemplateItem item = null;
		for (int ii = 0; ii < aChildItemList.length; ii++) {
			item = aChildItemList[ii];
			if (item.isInherited()) {
				for (int jj = 0; jj < aParentItemList.length; jj++) {
					if (item.getParentItemID() == aParentItemList[jj].getTemplateItemID()) {
						itemList.add(mergeTemplateItem(aParentItemList[jj], item));
						break;
					}
				}
			}
			else {
				itemList.add(item);
			}
		}

		for (int ii = 0; ii < aParentItemList.length; ii++) {
			boolean inChild = false;
			for (int jj = 0; jj < aChildItemList.length; jj++) {
				if (aParentItemList[ii].getTemplateItemID() == aChildItemList[jj].getParentItemID()) {
					inChild = true;
					break;
				}
			}
			if (!inChild) {
				itemList.add(convertParentItemToChild(aParentItemList[ii]));
			}
		}
		return (ITemplateItem[]) itemList.toArray(new ITemplateItem[0]);
	}

	/**
	 * Merge a parent and child template item
	 * @param aParentItem - ITemplateItem
	 * @param aChildItem - ITemplateItem
	 * @return ITemplateItem - the merged template item
	 */
	private ITemplateItem mergeTemplateItem(ITemplateItem aParentItem, ITemplateItem aChildItem) {
		ITemplateItem item = aChildItem;
		item.setIsMandatoryInd(aParentItem.getIsMandatoryInd());
		item.setIsMandatoryDisplayInd(aParentItem.getIsMandatoryDisplayInd());
        item.setIsMandatoryForBorrowerInd(aParentItem.getIsMandatoryForBorrowerInd());
        item.setIsMandatoryForPledgorInd(aParentItem.getIsMandatoryForPledgorInd());
        item.setItem(aParentItem.getItem());
		return item;
	}

	/**
	 * Merge a parent and child template item for persistence
	 * @param aParentItem - ITemplateItem
	 * @param aChildItem - ITemplateItem
	 * @return ITemplateItem - the merged template item
	 */
	private ITemplateItem mergeTemplateItemForPersistence(ITemplateItem aParentItem, ITemplateItem aChildItem) {
		ITemplateItem item = aChildItem;
		item.setIsMandatoryInd(false);
		item.setIsMandatoryDisplayInd(false);
		item.setItem(null);
		return item;
	}
}
