/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.cc;

import java.util.HashMap;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: jychong $<br>
 * @version $Revision: 1.16.12.1 $
 * @since $Date: 2006/12/14 12:22:26 $ Tag: $Name: DEV_20060126_B286V1 $
 */
public class SubmitCCCheckListCommand extends AbstractCommand {

	private ICheckListProxyManager checklistProxyManager;

	private boolean isMaintainChecklistWithoutApproval = false;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public void setMaintainChecklistWithoutApproval(boolean isMaintainChecklistWithoutApproval) {
		this.isMaintainChecklistWithoutApproval = isMaintainChecklistWithoutApproval;
	}

	/**
	 * Default Constructor
	 */
	public SubmitCCCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "mandatoryRows", "java.lang.String", REQUEST_SCOPE },
				{ "checkedInVault", "java.lang.String", REQUEST_SCOPE },
				{ "checkedExtCustodian", "java.lang.String", REQUEST_SCOPE },
				{ "checkedAudit", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");

		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		String mandatoryRows = (String) map.get("mandatoryRows");
		String checkedInVault = (String) map.get("checkedInVault");
		String checkedExtCustodian = (String) map.get("checkedExtCustodian");
		String checkedAudit = (String) map.get("checkedAudit");
		ICheckList checkList = (ICheckList) map.get("checkList");

		HashMap hmMandatoryRows = getMapFromString(mandatoryRows);
		HashMap hmCheckedInVault = getMapFromString(checkedInVault);
		HashMap hmCheckedExtCustodian = getMapFromString(checkedExtCustodian);
		HashMap hmCheckedAudit = getMapFromString(checkedAudit);

		ICheckListItem temp[] = checkList.getCheckListItemList();

		if (temp != null) {
			for (int i = 0; i < temp.length; i++) {

				if (ICMSConstant.STATE_DELETED.equals(checkList.getCheckListItemList()[i].getItemStatus())) {
					continue;
				}

				if (!checkList.getCheckListItemList()[i].getIsInherited()) {
					checkList.getCheckListItemList()[i].setIsMandatoryInd(hmMandatoryRows
							.containsKey(String.valueOf(i)));
				}
				checkList.getCheckListItemList()[i].setIsInVaultInd(hmCheckedInVault.containsKey(String.valueOf(i)));
				checkList.getCheckListItemList()[i].setIsExtCustInd(hmCheckedExtCustodian
						.containsKey(String.valueOf(i)));
				checkList.getCheckListItemList()[i].setIsAuditInd(hmCheckedAudit.containsKey(String.valueOf(i)));
			}
		}

		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

		if (CheckListUtil.isInCountry(ctx.getTeam(), limit, customer)) {
			ctx.setTrxCountryOrigin(CheckListUtil.getColTrxCountry(limit, customer));
			ctx.setTrxOrganisationOrigin(CheckListUtil.getColTrxOrgCode(limit, customer));
		}
		else {
			try {
				ctx.setTrxCountryOrigin(CheckListUtil.getCCTrxCountry(checkList));
				ctx.setTrxOrganisationOrigin(CheckListUtil.getCCTrxOrgCode(checkList));
			}
			catch (CheckListException ex) {
				throw new CommandProcessingException("failed to set transaction country and organisation", ex);
			}
		}

		if (checkListTrxVal == null) {
			if (this.isMaintainChecklistWithoutApproval) {
				try {
					checkListTrxVal = this.checklistProxyManager.makerCreateCheckListWithoutApproval(ctx, checkList);
				}
				catch (CheckListException ex) {
					throw new CommandProcessingException("failed to submit checklist creation workflow", ex);
				}
			}
			else {
				try {
					checkListTrxVal = this.checklistProxyManager.makerCreateCheckList(ctx, checkList);
				}
				catch (CheckListException ex) {
					throw new CommandProcessingException("failed to submit checklist creation workflow", ex);
				}
			}
		}
		else {
			ICheckListItem temp1[] = checkList.getCheckListItemList();
			if (temp1 != null) {
				for (int i = 0; i < temp1.length; i++) {

					/**
					 * skip update of deleted items
					 */
					if (isItemDeleted(checkList, i)) {
						DefaultLogger.debug(this, "skipping DELETED ITEM !!!");
						continue;

					}

					if (!checkList.getCheckListItemList()[i].getIsInherited()) {
						checkList.getCheckListItemList()[i].setIsMandatoryInd(hmMandatoryRows.containsKey(String
								.valueOf(i)));
					}
					checkList.getCheckListItemList()[i]
							.setIsInVaultInd(hmCheckedInVault.containsKey(String.valueOf(i)));
					checkList.getCheckListItemList()[i].setIsExtCustInd(hmCheckedExtCustodian.containsKey(String
							.valueOf(i)));
					checkList.getCheckListItemList()[i].setIsAuditInd(hmCheckedAudit.containsKey(String.valueOf(i)));
				}
			}

			if (this.isMaintainChecklistWithoutApproval) {
				try {
					checkListTrxVal = this.checklistProxyManager.makerUpdateCheckListWithoutApproval(ctx,
							checkListTrxVal, checkList);
				}
				catch (CheckListException ex) {
					throw new CommandProcessingException("failed to submit checklist update workflow", ex);
				}
			}
			else {
				try {
					checkListTrxVal = this.checklistProxyManager.makerUpdateCheckList(ctx, checkListTrxVal, checkList);
				}
				catch (CheckListException ex) {
					throw new CommandProcessingException("failed to submit checklist update workflow", ex);
				}
			}
		}
		resultMap.put("request.ITrxValue", checkListTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private boolean isItemDeleted(ICheckList checkList, int i) {
		return ICMSConstant.STATE_DELETED.equals(checkList.getCheckListItemList()[i].getItemStatus());
	}

	private HashMap getMapFromString(String commaSepInput) {
		HashMap hm = new HashMap();
		StringTokenizer st = new StringTokenizer(commaSepInput, ",");
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			hm.put(key, key);
		}
		return hm;
	}

}
