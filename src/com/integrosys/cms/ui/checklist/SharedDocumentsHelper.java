package com.integrosys.cms.ui.checklist;

import java.util.ArrayList;

import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/08/07 03:40:52 $ Tag: $Name: $
 */
public class SharedDocumentsHelper {

	// merge viewable checklist item from actual into staging. call this method
	// before sorting.
	// called by maintain checklist, update receipt (both cc and security)
	public static void mergeViewableCheckListItemIntoStaging(ICheckList actualChkList, ICheckList stgChkList) {

		if ((actualChkList == null) || (stgChkList == null)) {
			return;
		}

		ICheckListItem[] actualItemList = actualChkList.getCheckListItemList();
		ICheckListItem[] stgItemList = stgChkList.getCheckListItemList();

		// if(actualItemList.length == stgItemList.length) { //can't use this
		// 'cos of maintain checklist
		// return;
		// }

		ArrayList mergedItemList = new ArrayList();
		if (actualItemList != null) {
			for (int i = 0; i < actualItemList.length; i++) {
				if (actualItemList[i].getViewable()) {
					mergedItemList.add(actualItemList[i]);
				}
			}
		}

		if (mergedItemList.size() > 0) {
			if (stgItemList != null) {
				for (int i = 0; i < stgItemList.length; i++) {
					mergedItemList.add(stgItemList[i]);
				}
			}
			stgChkList.setCheckListItemList((ICheckListItem[]) mergedItemList.toArray(new ICheckListItem[0]));
		}

	}

}
