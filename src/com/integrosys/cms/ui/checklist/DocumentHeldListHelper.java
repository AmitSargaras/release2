/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/DocumentHeldListHelper.java,v 1.2 2005/05/10 10:22:09 wltan Exp $
 */

package com.integrosys.cms.ui.checklist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;

import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.chktemplate.bus.IDocumentHeldItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/05/10 10:22:09 $ Tag: $Name: $
 */
public class DocumentHeldListHelper {

	/**
	 * Sorts the list of ICheckListItems based on their parent codes. The return
	 * value is the result of a prefix transversal on the internal tree.
	 * 
	 * @param item The items to be sorted
	 * @param IDocumentHeldItem[] An array holding the prefix-sorted items
	 */
	public static IDocumentHeldItem[] sortByParentPrefix(IDocumentHeldItem[] items) throws CheckListException {

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
		for (int i = 0; i < items.length; i++) {
			pool.put(new Long(items[i].getDocNo()), items[i]); // key =
																// checkListItemRef
																// (i.e. docNo),
																// value =
																// checkListItem
																// object

			// get the checkListItem in order to get the parentCheckListItemRef
			if (items[i].getDocNo() != 0) {
				ICheckListItem checkListItem = CheckListProxyManagerFactory.getCheckListProxyManager()
						.getCheckListItem(items[i].getDocNo());
				if (!parentItemRefVect.contains(new Long(checkListItem.getParentCheckListItemRef()))) {
					parentItemRefVect.add(new Long(checkListItem.getParentCheckListItemRef())); // add
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
			IDocumentHeldItem poolItem = (IDocumentHeldItem) i.next();

			Long parentItemRef = new Long(0);
			ICheckListItem checkListItem = CheckListProxyManagerFactory.getCheckListProxyManager().getCheckListItem(
					poolItem.getDocNo());
			if (checkListItem != null) {
				parentItemRef = new Long(checkListItem.getParentCheckListItemRef());
			}

			// if item has no parent but has children, i.e. is a top-most parent
			if (!pool.containsKey(parentItemRef) && !removedPool.containsKey(parentItemRef)
					&& parentItemRefVect.contains(new Long(checkListItem.getCheckListItemRef()))) {
				// add top-most parent as a child of the artificial root
				TreeNode node = new TreeNode(poolItem);
				root.addChild(poolItem.getDocNo(), node);
				removedPool.put(new Long(poolItem.getDocNo()), poolItem);
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
			attachDescendents(n.payload.getDocNo(), n, pool);
		}

		// flatten the tree by doing a prefixTransversal
		IDocumentHeldItem[] nonSingleItems = prefixTransversal(root);
		int totalItemCount = nonSingleItems.length + pool.size();
		IDocumentHeldItem[] result = new IDocumentHeldItem[totalItemCount];

		// do blind copy for the non-single items
		for (int i = 0; i < nonSingleItems.length; i++) {
			result[i] = nonSingleItems[i];
		}

		int count = nonSingleItems.length;
		// append remaining childless orphans to the back of the result array
		for (Iterator j = pool.values().iterator(); j.hasNext();) {
			IDocumentHeldItem poolItem = (IDocumentHeldItem) j.next();
			result[count++] = poolItem;
		}

		return result;
	}

	// pick elements from the pool whose's parentID is id and attach them to the
	// target
	private static void attachDescendents(long id, TreeNode target, Map pool) throws CheckListException {
		for (Iterator i = pool.values().iterator(); i.hasNext();) {
			IDocumentHeldItem poolItem = (IDocumentHeldItem) i.next();
			long itemRef = poolItem.getDocNo();
			ICheckListItem item = CheckListProxyManagerFactory.getCheckListProxyManager().getCheckListItem(itemRef);
			long parentItemRef = 0;
			if (item != null) {
				parentItemRef = item.getParentCheckListItemRef();
			}

			if (parentItemRef == id) {
				target.addChild(itemRef, new TreeNode(poolItem));
				i.remove();
			}
		}
		// now recursively call attachDescendents for each of the child of
		// target
		for (Iterator i = target.children.values().iterator(); i.hasNext();) {
			TreeNode node = (TreeNode) i.next();
			attachDescendents(node.payload.getDocNo(), node, pool);
		}
	}

	static class TreeNode {
		IDocumentHeldItem payload;

		SortedMap children = new TreeMap();

		TreeNode() {
		}

		TreeNode(IDocumentHeldItem payload) {
			this.payload = payload;
		}

		void addChild(long key, TreeNode tn) {
			children.put(new Long(key), tn);
		}
	}

	//
	private static IDocumentHeldItem[] prefixTransversal(TreeNode root) {
		ArrayList al = new ArrayList();

		if (root.payload != null) {
			al.add(root.payload);
		}

		for (Iterator i = root.children.values().iterator(); i.hasNext();) {
			addPayLoad((TreeNode) i.next(), al);
		}
		return (IDocumentHeldItem[]) al.toArray(new IDocumentHeldItem[al.size()]);
	}

	private static void addPayLoad(TreeNode node, List l) {
		if (node.payload != null) {
			l.add(node.payload);
		}

		for (Iterator i = node.children.values().iterator(); i.hasNext();) {
			addPayLoad((TreeNode) i.next(), l);
		}
	}

}
