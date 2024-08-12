package com.integrosys.cms.ui.common;

//java
import java.util.ArrayList;
import java.util.Collection;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListItemOperation;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 9, 2003 Time: 5:29:11 PM
 * To change this template use Options | File Templates.
 */
public class DeletedCheckListItemActionList {
	private static ICheckListItemOperation[] operationList = null;

	private Collection actionLabel = new ArrayList();

	private Collection actionValue = new ArrayList();

	public static DeletedCheckListItemActionList getInstance(ICheckListItem anICheckListItem) {
		return new DeletedCheckListItemActionList(anICheckListItem);
	}

	// Priya - CMS - 3026 - Documents under DELETED CHECKLIST should have all
	// the functionality as Docs under ACTIVE checklist.
	private DeletedCheckListItemActionList(ICheckListItem anICheckListItem) {
		if (operationList == null) {
			operationList = getOperationList();

		}
		if (operationList != null) {
			String itemStatus = anICheckListItem.getItemStatus();
			for (int ii = 0; ii < operationList.length; ii++) {
				if (operationList[ii].getState().equals(itemStatus)) {
					if (isOperationAllowed(anICheckListItem, operationList[ii].getOperation())) {
						actionValue.add(operationList[ii].getOperation());
						if (operationList[ii].getOperationDesc().startsWith("VIEW")) {
							actionLabel.add("VIEW");
						}
						else {
							actionLabel.add(operationList[ii].getOperationDesc());
						}
					}
				}
			}
		}
	}

	public Collection getOperationLabels() {
		return this.actionLabel;
	}

	public Collection getOperationValues() {
		return this.actionValue;
	}

	private ICheckListItemOperation[] getOperationList() {
		try {
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			return proxy.getAllowableOperationList();
		}
		catch (Exception ex) {
			DefaultLogger.error(this, "Error retrieving the list of allowable operations !!!");
			return null;
		}
	}

	/*
	 * private boolean isOperationAllowed(ICheckListItem anICheckListItem,
	 * String anOperation) { // CMS-2774 : Allow UPDATE when item status in
	 * RECEIVED or COMPLETED if
	 * (ICMSConstant.ACTION_ITEM_UPDATE.equals(anOperation)) { if
	 * (anICheckListItem
	 * .getItemStatus().equals(ICMSConstant.STATE_ITEM_RECEIVED) ||
	 * anICheckListItem
	 * .getItemStatus().equals(ICMSConstant.STATE_ITEM_COMPLETED)) { return
	 * true; } } //Priya - CMS-3018 - AWAITING docs of deleted checklist should
	 * have all the actions applicable for Awaiting doc of Active checklist if
	 * (ICMSConstant.ACTION_ITEM_COMPLETE.equals(anOperation)) { if
	 * (anICheckListItem
	 * .getItemStatus().equals(ICMSConstant.STATE_ITEM_RECEIVED)) { return true;
	 * } } //Priya - CMS-3018 - AWAITING docs of deleted checklist should have
	 * all the actions applicable for Awaiting doc of Active checklist if
	 * (anICheckListItem
	 * .getItemStatus().equals(ICMSConstant.STATE_ITEM_AWAITING))
	 * 
	 * { System.out.println("operation "+anOperation +" item state " +
	 * anICheckListItem.getItemStatus()); return true;
	 * 
	 * } //Priya - CMS 3026 - Expired documents under deleted checklist does not
	 * have renewal functionality if
	 * (anICheckListItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_EXPIRED)
	 * ||
	 * anICheckListItem.getItemStatus().equals(ICMSConstant.STATE_ITEM_RENEWED))
	 * { if (isOperationAllowed(anICheckListItem.getCustodianDocStatus(),
	 * anOperation)) { System.out.println("operation "+anOperation
	 * +" item state " + anICheckListItem.getItemStatus()); return true; } }
	 * 
	 * 
	 * 
	 * if ((anICheckListItem.getCustodianDocStatus() == null) &&
	 * (!ICMSConstant.ACTION_ITEM_DELETE.equals(anOperation))) { return false; }
	 * if (isOperationAllowed(anICheckListItem.getCustodianDocStatus(),
	 * anOperation)) { if (ICMSConstant.ACTION_ITEM_APPROVE.equals(anOperation))
	 * { return false; } return true; } return false; }
	 */

	// Priya - CMS - 3026 - Documents under DELETED CHECKLIST should have all
	// the functionality as Docs under ACTIVE checklist.
	private boolean isOperationAllowed(ICheckListItem anICheckListItem, String anOperation) {
		if (isOperationAllowed(anICheckListItem.getCustodianDocStatus(), anOperation)) {
			if (ICMSConstant.ACTION_ITEM_DELETE.equals(anOperation)) {
				if (ICMSConstant.STATE_ITEM_DELETED.equals(anICheckListItem.getItemStatus())) {
					return false;
				}
			}
			if (ICMSConstant.ACTION_ITEM_APPROVE.equals(anOperation)) {
				return false;
			}
			return true;
			// allow delete operationexpired checklistItem and
			// awaiting(received)custDocstatus
		}
		else if (ICMSConstant.STATE_ITEM_EXPIRED.equals(anICheckListItem.getItemStatus())
				&& ICMSConstant.STATE_RECEIVED.equals(anICheckListItem.getCustodianDocStatus())) {
			if (ICMSConstant.ACTION_ITEM_DELETE.equals(anOperation)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * private boolean isOperationAllowed(String aCustodianStatus, String
	 * anOperation) { if ((aCustodianStatus != null) &&
	 * (aCustodianStatus.trim().length() >0)) { if
	 * (aCustodianStatus.equals(ICMSConstant.STATE_LODGED)) { // CMS-2084 //
	 * Allow temp uplift for a lodged item even if checklist is deleted if
	 * (anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_PERM_UPLIFT) ||
	 * anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_TEMP_UPLIFT)) { return
	 * true; } } else if
	 * (aCustodianStatus.equals(ICMSConstant.STATE_PERM_UPLIFTED)) { if
	 * (anOperation.equals(ICMSConstant.ACTION_ITEM_DELETE)) { return true; } }
	 * // CMS-2084 // Allow relodge for a temp-uplifted item even if checklist
	 * is deleted else if
	 * (aCustodianStatus.equals(ICMSConstant.STATE_TEMP_UPLIFTED)) { if
	 * (anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_RELODGE)) { return
	 * true; } } return false; } if
	 * ((anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_TEMP_UPLIFT)) ||
	 * (anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_PERM_UPLIFT))) {
	 * return false; } return true; } }
	 */

	// Priya - CMS - 3026 - Documents under DELETED CHECKLIST should have all
	// the functionality as Docs under ACTIVE checklist.
	private boolean isOperationAllowed(String aCustodianStatus, String anOperation) {
		if ((aCustodianStatus != null) && (aCustodianStatus.trim().length() > 0)) {
			// if cust status is not LODGED, do not allow temp/perm uplift
			if (!aCustodianStatus.equals(ICMSConstant.STATE_LODGED)) {
				if ((anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_TEMP_UPLIFT))
						|| (anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_PERM_UPLIFT))) {
					return false;
				}
			}

			// if cust status is not PERM UPLIFTED, do not allow delete
			// operation
			if (!aCustodianStatus.equals(ICMSConstant.STATE_PERM_UPLIFTED)) {
				if (ICMSConstant.ACTION_ITEM_DELETE.equals(anOperation)) {
					return false;
				}
			}

			// bernard - if cust status is not TEMP UPLIFTED, do not allow
			// relodge
			if (!aCustodianStatus.equals(ICMSConstant.STATE_TEMP_UPLIFTED)) {
				if (anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_RELODGE)) {
					return false;
				}
			}
			return true;
		}

		if ((anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_TEMP_UPLIFT))
				|| (anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_PERM_UPLIFT))
				|| (anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_RELODGE))) {
			return false;
		}
		return true;
	}
}