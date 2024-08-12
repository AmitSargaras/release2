/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CheckListCustodianHelper.java,v 1.11 2005/11/23 09:34:08 whuang Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.custodian.bus.ICustodianDocItem;

/**
 * Utility class that provides helper method required by the CheckList Module
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/11/23 09:34:08 $ Tag: $Name: $
 */
public class CheckListCustodianHelper {

	public CheckListCustodianHelper getInstance() {
		return new CheckListCustodianHelper();
	}

	/**
	 * Translates the CPC and staging CPC statuses to determine the correct
	 * status to display.
	 * @param cpcStatus the CPC status
	 * @param stageCPCStatus the staging CPC status
	 * @return String - the status to be displayed
	 */
	public static String getCheckListCPCStatus(String cpcStatus, String stageCPCStatus) {
		// returns PENDING_COMPLETE when stageCPCStatus is PENDING_COMPLETE
		// if (ICMSConstant.STATE_ITEM_AWAITING.equals(cpcStatus)
		// && ICMSConstant.STATE_ITEM_PENDING_COMPLETE.equals(stageCPCStatus))
		// {
		// CMS-1989 : to cater for item status other than AWAITING
		if (!ICMSConstant.STATE_ITEM_COMPLETED.equals(cpcStatus)
				&& ICMSConstant.STATE_ITEM_PENDING_COMPLETE.equals(stageCPCStatus)) {
			return stageCPCStatus;
		}
		return cpcStatus;
	}

	/**
	 * Translates the CPC and current custodian statuses to determine the
	 * correct status to display.
	 * @param cpcStatus the current CPC status (ie. PENDING_ALLOW_RELODGE,
	 *        ALLOW_RELODGE)
	 * @param custodianStatus the current custodian status (ie. PENDING_LODGE,
	 *        LODGED)
	 * @return String - the status to be displayed
	 */
	public static String getCurrentCustodianStatus(String cpcStatus, String custodianStatus) {
		if (cpcStatus == null) {
			return ICMSConstant.STATE_RECEIVED;
		}

		if (custodianStatus == null) {
			return cpcStatus;
		}

		if (cpcStatus.indexOf(ICMSConstant.STATE_ITEM_PENDING_PREFIX) >= 0) {
			return cpcStatus;
		}

		if (custodianStatus.indexOf(ICMSConstant.STATE_ITEM_PENDING_PREFIX) >= 0) {
			return custodianStatus;
		}

		return getCheckListCustodianStatus(cpcStatus, custodianStatus);
	}

	/**
	 * Translates the CPC Custodian and Custodian statuses to determine the
	 * correct status to display.
	 * @param aCPCCustodianStatus the CPC Custodian status
	 * @param aCustodianStatus the transaction status
	 * @return String - the status to be displayed
	 */
	public static String getCheckListCustodianStatus(String aCPCCustodianStatus, String aCustodianStatus) {
		// DefaultLogger.debug(
		// "CheckListCustodianHelper.getCheckListCustodianStatus",
		// ">>>>>>> >>> cpc cust status : " + aCPCCustodianStatus);
		// DefaultLogger.debug(
		// "CheckListCustodianHelper.getCheckListCustodianStatus",
		// ">>>>>>> >>> cust doc item status : " + aCustodianStatus);

		// if there are no custodian doc item status or cpc cust status is
		// pending
		// return cpc cust status
		if ((aCustodianStatus == null)
				|| ((aCPCCustodianStatus != null)
						&& aCPCCustodianStatus.startsWith(ICMSConstant.STATE_ITEM_PENDING_PREFIX) && !ICMSConstant.STATE_ITEM_PENDING_UPDATE
						.equals(aCPCCustodianStatus))) {
			// DefaultLogger.debug(
			// "CheckListCustodianHelper.getCheckListCustodianStatus",
			// ">>>>>>> pending cpc cust status!");
			return aCPCCustodianStatus;
		}

		// return cpc cust status when custodian doc item status is pending
		if ((aCustodianStatus != null) && aCustodianStatus.startsWith(ICMSConstant.STATE_ITEM_PENDING_PREFIX)) {
			// DefaultLogger.debug(
			// "CheckListCustodianHelper.getCheckListCustodianStatus",
			// ">>>>>>> pending cust status!");
			return aCPCCustodianStatus;
		}

		// return allow_temp_uplift when item is not temp_uplifted yet
		if (ICMSConstant.STATE_ITEM_TEMP_UPLIFT_AUTHZ.equals(aCPCCustodianStatus)
				&& !(ICMSConstant.STATE_TEMP_UPLIFTED.equals(aCustodianStatus))) {
			// DefaultLogger.debug(
			// "CheckListCustodianHelper.getCheckListCustodianStatus",
			// ">>>>>>> allow temp uplift!");
			return ICMSConstant.STATE_ITEM_TEMP_UPLIFT_AUTHZ;
		}

		// return allow_perm_uplift when item is not perm_uplifted yet
		if (ICMSConstant.STATE_ITEM_PERM_UPLIFT_AUTHZ.equals(aCPCCustodianStatus)
				&& !(ICMSConstant.STATE_PERM_UPLIFTED.equals(aCustodianStatus))) {
			// DefaultLogger.debug(
			// "CheckListCustodianHelper.getCheckListCustodianStatus",
			// ">>>>>>> allow perm uplift!");
			return ICMSConstant.STATE_ITEM_PERM_UPLIFT_AUTHZ;
		}

		// return allow_relodge when item is not lodged yet
		if (ICMSConstant.STATE_ITEM_RELODGE_AUTHZ.equals(aCPCCustodianStatus)
				&& !(ICMSConstant.STATE_LODGED.equals(aCustodianStatus))) {
			// DefaultLogger.debug(
			// "CheckListCustodianHelper.getCheckListCustodianStatus",
			// ">>>>>>> allow relodge!");
			return ICMSConstant.STATE_ITEM_RELODGE_AUTHZ;
		}

		// DefaultLogger.debug(
		// "CheckListCustodianHelper.getCheckListCustodianStatus",
		// ">>>>>>> custodian state : " + aCustodianStatus);
		return aCustodianStatus;
	}

	/**
	 * Get the cpc custodian trx date for a checklist item. Takes into
	 * consideration the last update date of cpc cust status as well as the last
	 * update date of custodian doc item.
	 * 
	 * @param anItem - ICheckListItem
	 */
	public static Date getCPCCustodianTrxDate(ICheckListItem anItem) {
		if (anItem == null) {
			return null;
		}

		if (anItem.getIsInVaultInd()) {
			Date cpcTrxDate = (anItem.getCPCCustodianStatusUpdateDate() != null) ? DateUtil.initializeStartDate(anItem
					.getCPCCustodianStatusUpdateDate()) : null;
			Date custTrxDate = (anItem.getCustodianDocItemTrxDate() != null) ? DateUtil.initializeStartDate(anItem
					.getCustodianDocItemTrxDate()) : null;
			Date displayDate = compareTrxDate(cpcTrxDate, custTrxDate);
			return displayDate;
		}
		return null;
	}

	/**
	 * Get the cpc custodian trx date for a custodian doc item. Takes into
	 * consideration the last update date of cpc cust status as well as the last
	 * update date of custodian doc item.
	 * 
	 * @param anItem - ICheckListItem
	 */
	public static Date getCustodianTrxDate(ICustodianDocItem anItem) {
		if (anItem == null) {
			return null;
		}

		// no need to chk for invault since only invault items can be acted on
		// by custodian
		Date custTrxDate = (anItem.getLastUpdateDate() != null) ? DateUtil.initializeStartDate(anItem
				.getLastUpdateDate()) : null;
		Date cpcTrxDate = null;
		if (anItem.getCheckListItem() != null) {
			Date tempDate = anItem.getCheckListItem().getCPCCustodianStatusUpdateDate();
			cpcTrxDate = (tempDate != null) ? DateUtil.initializeStartDate(tempDate) : null;
		}
		Date displayDate = compareTrxDate(cpcTrxDate, custTrxDate);
		return displayDate;
	}

	/**
	 * compare trx date
	 * @param cpcTrxDate
	 * @param custTrxDate
	 * @return
	 */
	private static Date compareTrxDate(Date cpcTrxDate, Date custTrxDate) {
		if ((cpcTrxDate == null) && (custTrxDate == null)) {
			return null;
		}
		else if ((cpcTrxDate == null) && (custTrxDate != null)) {
			return custTrxDate;
		}
		else if ((cpcTrxDate != null) && (custTrxDate != null)) {
			if (cpcTrxDate.compareTo(custTrxDate) >= 0) {
				return cpcTrxDate;
			}
			else {
				return custTrxDate;
			}
		}
		return null;
	}
}
