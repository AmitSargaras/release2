package com.integrosys.cms.ui.common;

//java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListItemOperation;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 9, 2003 Time: 5:29:11 PM
 * To change this template use Options | File Templates.
 */
public class CheckListItemActionList {
	private static ICheckListItemOperation[] operationList = null;

	private Collection actionLabel = new ArrayList();

	private Collection actionValue = new ArrayList();

	public static CheckListItemActionList getInstance(String anItemStatus, String aCustodianStatus) {
		return new CheckListItemActionList(anItemStatus, aCustodianStatus);
	}

	public static CheckListItemActionList getInstance(ICheckListItem anICheckListItem) {
		return new CheckListItemActionList(anICheckListItem);
	}

	public static CheckListItemActionList getInstance(String aSecType, String aSecSubType,
			ICheckListItem anICheckListItem) {
		return new CheckListItemActionList(aSecType, aSecSubType, anICheckListItem);
	}

	private CheckListItemActionList(String aSecType, String aSecSubType, ICheckListItem anICheckListItem) {
		if (operationList == null) {
			operationList = getOperationList();
		}
		if (operationList != null) {
			String itemStatus = anICheckListItem.getItemStatus();
			String custodianStatus = anICheckListItem.getCPCCustodianStatus();
			if(custodianStatus != null && (!custodianStatus.equalsIgnoreCase(ICMSConstant.STATE_ITEM_RECEIVED)))
			{
				itemStatus = custodianStatus;
			}
			for (int ii = 0; ii < operationList.length; ii++) {
				if (operationList[ii].getState().equals(itemStatus)) {
					if (isOperationAllowed(anICheckListItem, operationList[ii].getOperation())) {
						actionValue.add(operationList[ii].getOperation());
						if (operationList[ii].getOperationDesc().startsWith("VIEW")) {
							actionLabel.add("VIEW");
						}
						else if (operationList[ii].getOperationDesc().startsWith("UPDATE")) {
							actionLabel.add("UPDATE");
						}
						else {
							actionLabel.add(operationList[ii].getOperationDesc());
						}
					}
				}
			}
		}
		/**********************
		 * Unrequired status for dropdowns for security checklist
		 * 
		 * 
		if (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(aSecType)) {
			// if (ICMSConstant.STATE_ITEM_EXPIRED.equals(anICheckListItem.
			// getItemStatus()))
			if ((anICheckListItem.getExpiryDate() != null)
					&& !ICMSConstant.STATE_ITEM_DELETED.equals(anICheckListItem.getItemStatus())) {
				if (ICMSConstant.CAVEAT.equals(anICheckListItem.getItem().getMonitorType())) {
					actionValue.add("CAVEAT_PRINT_REMINDER");
					actionLabel.add("PRINT REMINDER");
				}
				else if (ICMSConstant.PROPERTY_VALUATION.equals(anICheckListItem.getItem().getMonitorType())) {
					actionValue.add("VAL_PRINT_REMINDER");
					actionLabel.add("PRINT REMINDER");
				}
			}
		}
		if (ICMSConstant.PREMIUM_RECEIPT.equals(anICheckListItem.getItem().getMonitorType())) {
			actionValue.add("PREMINUM_RECEIPT_PRINT_REMINDER");
			actionLabel.add("PRINT REMINDER");
		}
		if ((ICMSConstant.SECURITY_TYPE_ASSET.equals(aSecType) && !ICMSConstant.COLTYPE_ASSET_PDT_CHEQUE
				.equals(aSecSubType))
				|| (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(aSecType) && !(ICMSConstant.COLTYPE_PROP_LAND_URBAN
						.equals(aSecSubType) || ICMSConstant.COLTYPE_PROP_LAND_VACANT.equals(aSecSubType)))) {
			// if (ICMSConstant.STATE_ITEM_EXPIRED.equals(anICheckListItem.
			// getItemStatus())) {
			if ((anICheckListItem.getExpiryDate() != null)
					&& !ICMSConstant.STATE_ITEM_DELETED.equals(anICheckListItem.getItemStatus())) {
				if (ICMSConstant.INSURANCE_POLICY.equals(anICheckListItem.getItem().getMonitorType())) {
					actionValue.add("INSURANCE_PRINT_REMINDER");
					actionLabel.add("PRINT REMINDER");
				}
			}
		}
		*******/
	}

	private CheckListItemActionList(ICheckListItem anICheckListItem) {
		if (operationList == null) {
			operationList = getOperationList();
		}
		if (operationList != null) {
			String itemStatus = anICheckListItem.getItemStatus();
			String custodianStatus = anICheckListItem.getCPCCustodianStatus();
			if(custodianStatus != null && (!custodianStatus.equalsIgnoreCase(ICMSConstant.STATE_ITEM_RECEIVED)))
			{
				itemStatus = custodianStatus;
			}
			
			// System.out.println(
			// "> CheckListItemActionList(ICheckListItem): operationList.length="
			// +operationList.length);
			for (int ii = 0; ii < operationList.length; ii++) {
				// System.out.println("> operationList["+ii+"].getState()="+
				// operationList[ii].getState()+", itemStatus="+itemStatus);
				if (operationList[ii].getState().equals(itemStatus)) // the
				// FROMSTATE
				// must
				// equal
				// the
				// current
				// item
				// status
				{
					if (isOperationAllowed(anICheckListItem, operationList[ii].getOperation())) {
						actionValue.add(operationList[ii].getOperation());
						if (operationList[ii].getOperationDesc().startsWith("VIEW")) {
							actionLabel.add("VIEW");
						}
						else if (operationList[ii].getOperationDesc().startsWith("UPDATE")) {
							actionLabel.add("UPDATE");
						}
						else {
							actionLabel.add(operationList[ii].getOperationDesc());
						}
					}
				}
			}
		}
	}

	private CheckListItemActionList(String anItemStatus, String aCustodianStatus) {
		if (operationList == null) {
			operationList = getOperationList();
		}
		if (operationList != null) {
			for (int ii = 0; ii < operationList.length; ii++) {
				if (operationList[ii].getState().equals(anItemStatus)) {
					if (isOperationAllowed(aCustodianStatus, operationList[ii].getOperation())) {
						actionValue.add(operationList[ii].getOperation());
						if (operationList[ii].getOperationDesc().startsWith("VIEW")) {
							actionLabel.add("VIEW");
						}
						else if (operationList[ii].getOperationDesc().startsWith("UPDATE")) {
							actionLabel.add("UPDATE");
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
			ICheckListItemOperation[] operationList = proxy.getAllowableOperationList();
			if ((operationList != null) && (operationList.length > 0)) {
				Arrays.sort(operationList);
			}
			return operationList;
		}
		catch (CheckListException ex) {
			DefaultLogger.error(this, "Error retrieving the list of allowable operations.", ex);
			return null;
		}
	}

	private boolean isOperationAllowed(ICheckListItem anICheckListItem, String anOperation) {
		//if (isOperationAllowed(anICheckListItem.getCustodianDocStatus(), anOperation)) {
        if (isOperationAllowed(anICheckListItem.getCPCCustodianStatus(), anOperation)) {
			if (ICMSConstant.ACTION_ITEM_DELETE.equals(anOperation)) {
				// if
				// ((!ICMSConstant.STATE_ITEM_COMPLETED.equals(anICheckListItem
				// .getItemStatus())) || (anICheckListItem.getIsMandatoryInd()))
				/*
				 * if
				 * (!ICMSConstant.STATE_ITEM_COMPLETED.equals(anICheckListItem
				 * .getItemStatus()) &&
				 * !ICMSConstant.STATE_ITEM_EXPIRED.equals(anICheckListItem
				 * .getItemStatus()) &&
				 * !ICMSConstant.STATE_ITEM_AWAITING.equals(
				 * anICheckListItem.getItemStatus()) &&
				 * !ICMSConstant.STATE_ITEM_RENEWED
				 * .equals(anICheckListItem.getItemStatus())) {
				 */
				if (ICMSConstant.STATE_ITEM_DELETED.equals(anICheckListItem.getItemStatus())) {
					return false;
				}
			}
			if (ICMSConstant.ACTION_ITEM_APPROVE.equals(anOperation)) {
				return false;
			}
			// By Abhijit R for HDFC BAnk Specific disable REEDEM option
			if (ICMSConstant.ACTION_ITEM_REDEEM.equals(anOperation)) {
				return false;
			}
			return true;
			// allow delete operationexpired checklistItem and
			// awaiting(received)custDocstatus
		}
		else if (ICMSConstant.STATE_ITEM_EXPIRED.equals(anICheckListItem.getItemStatus())
				//&& ICMSConstant.STATE_RECEIVED.equals(anICheckListItem.getCustodianDocStatus())) {
                && ICMSConstant.STATE_RECEIVED.equals(anICheckListItem.getCPCCustodianStatus())) {
			if (ICMSConstant.ACTION_ITEM_DELETE.equals(anOperation)) {
				return true;
			}
		}
		return false;
	}

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

            //if cust status is LODGED, do not allow UPDATE_LODGED and LODGE
            if (aCustodianStatus.equals(ICMSConstant.STATE_LODGED)) {
                if (anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_UPDATE_LODGED) ||
                        anOperation.equals(ICMSConstant.ACTION_ITEM_ALLOW_LODGE)) {
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
