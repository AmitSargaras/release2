package com.integrosys.cms.app.checklist.proxy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.IShareDoc;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public class ShareCheckListProcessor {

	private HashMap shareCheckListIDUpdateMap = new HashMap();

	public ShareCheckListProcessor() {
	}

	// called by pre-process
	/* --------- R1.5 CR17 Pre-process starts --------------- */
	// public void prepareDistinctShareCheckListIDList(ICheckListItem[]
	// checkListItem, ICheckListItem[] stgCheckListItem) {
	public Long[] prepareDistinctShareCheckListIDList(ICheckListTrxValue trxValue) throws SearchDAOException {
		ICheckList orgCheckList = trxValue.getCheckList();
		// ICheckList newCheckList = trxValue.getStagingCheckList();
		ICheckListItem[] orgItemList = orgCheckList.getCheckListItemList();
		// ICheckListItem[] stgItemList = newCheckList.getCheckListItemList();

		// DefaultLogger.debug("ShareCheckListProcessor",
		// ">>>>>>>> (Checker Approve Op) prepareDistinctShareCheckListIDList "
		// );
		prepareShareCheckListIDListHelper(orgItemList);
		// prepareShareCheckListIDListHelper(stgItemList);
		DefaultLogger.debug("ShareCheckListProcessor",
				">>>>>>>> (Checker Approve Op) prepareDistinctShareCheckListIDList: list of checklist id to update: "
						+ shareCheckListIDUpdateMap);
		return determineShareCheckListsToUpdate();
		// mergeShareCheckListIDMaps(shareCheckListIDActualMap,
		// shareCheckListIDStagingMap);
	}

	private void prepareShareCheckListIDListHelper(ICheckListItem[] checkListItem) {

		if ((checkListItem != null) && (checkListItem.length > 0)) {
			for (int i = 0; i < checkListItem.length; i++) {
				IShareDoc[] shareDocList = checkListItem[i].getShareCheckList();
				if ((shareDocList != null) && (shareDocList.length > 0)) {
					for (int j = 0; j < shareDocList.length; j++) {
						Long checkListID = new Long(shareDocList[j].getCheckListId());
						if (shareCheckListIDUpdateMap.get(checkListID) == null) {
							shareCheckListIDUpdateMap.put(checkListID, "");
						}
					}
				}
			}
		}
	}

	/* --------- R1.5 CR17 Pre-process ends --------------- */

	// called by post-process
	/* --------- R1.5 CR17 starts --------------- */
	private Long[] determineShareCheckListsToUpdate() throws SearchDAOException {

		DefaultLogger.debug("ShareCheckListProcessor", ">>>>>>>> - determineShareCheckListsToUpdate");

		ArrayList updateList = new ArrayList();
		if ((shareCheckListIDUpdateMap != null) && !shareCheckListIDUpdateMap.isEmpty()) {
			Long[] shareCheckListIDList = (Long[]) shareCheckListIDUpdateMap.keySet().toArray(new Long[0]);
			HashMap shareCheckListIDWithStatusMap = CheckListDAOFactory.getCheckListDAO().getCheckListStatus(
					shareCheckListIDList);

			Iterator it = shareCheckListIDUpdateMap.keySet().iterator();
			while (it.hasNext()) {
				Long checkListID = (Long) it.next();
				String checkListStatus = (String) shareCheckListIDWithStatusMap.get(checkListID);
				if (!ICMSConstant.STATE_DELETED.equals(checkListStatus)) {
					updateList.add(checkListID);
				}
			}
		}

		return (Long[]) updateList.toArray(new Long[0]);
	}

	/*
	 * public void updateShareCheckListsStatus() throws TrxOperationException {
	 * //actual update of the checklist
	 * DefaultLogger.debug("ShareCheckListProcessor",
	 * ">>>>>>>> IN post process: calling actual update!!! -- updateList size: "
	 * + updateList.size()); ICheckListProxyManager mgr =
	 * CheckListProxyManagerFactory.getCheckListProxyManager(); for(int i=0;
	 * i<updateList.size(); i++) {
	 * DefaultLogger.debug("ShareCheckListProcessor",
	 * ">>>>>>>> (Checker Approve Op - updateShareCheckListsStatus) CheckList that is system-updated: "
	 * + updateList.get(i));
	 * mgr.systemUpdateCheckList(((Long)updateList.get(i)).longValue()); }
	 * 
	 * }catch(CheckListException e) { throw newTrxOperationException(
	 * "Error in updating the list of checklist impacted by the sharing of documents"
	 * , e); } }
	 */
	/* --------- R1.5 CR17 Post-process ends --------------- */

}
