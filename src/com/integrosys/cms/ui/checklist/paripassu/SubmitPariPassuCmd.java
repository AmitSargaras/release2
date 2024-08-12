package com.integrosys.cms.ui.checklist.paripassu;

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
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.IPariPassuCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBPariPassuCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class SubmitPariPassuCmd  extends AbstractCommand implements ICommonEventConstant {
	
	private ICheckListProxyManager checklistProxyManager;
	private boolean isMaintainChecklistWithoutApproval = false;

	public void setMaintainChecklistWithoutApproval(
			boolean isMaintainChecklistWithoutApproval) {
		this.isMaintainChecklistWithoutApproval = isMaintainChecklistWithoutApproval;
	}
	public void setChecklistProxyManager(
			ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}
	public SubmitPariPassuCmd() {
	}
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
					{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
					{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
					{ "mandatoryRows", "java.lang.String", REQUEST_SCOPE },
					{ "checkedInVault", "java.lang.String", REQUEST_SCOPE },
					{ "checkedExtCustodian", "java.lang.String", REQUEST_SCOPE },
					{ "checkedAudit", "java.lang.String", REQUEST_SCOPE },
					{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
					{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
					{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }}

	        );
	    }

	 public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
	}
	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			DefaultLogger.debug(this, "Inside doExecute()");
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");

			ICheckList checkList = (ICheckList) map.get("checkList");


			ICheckListItem temp[] = checkList.getCheckListItemList();
/*			if (temp != null) {
				for (int i = 0; i < temp.length; i++) {

					*//**
					 * skip update of deleted items
					 *//*
					if (isItemDeleted(checkList, i)) {
						continue;
					}

					if (!checkList.getCheckListItemList()[i].getIsInherited()) {
						if (hmMandatoryRows.containsKey(String.valueOf(i))) {
							checkList.getCheckListItemList()[i].setIsMandatoryInd(true);
						}
						else {
							checkList.getCheckListItemList()[i].setIsMandatoryInd(false);
						}
					}
					if (hmCheckedInVault.containsKey(String.valueOf(i))) {
						checkList.getCheckListItemList()[i].setIsInVaultInd(true);
					}
					else {
						checkList.getCheckListItemList()[i].setIsInVaultInd(false);
					}
					if (hmCheckedExtCustodian.containsKey(String.valueOf(i))) {
						checkList.getCheckListItemList()[i].setIsExtCustInd(true);
					}
					else {
						checkList.getCheckListItemList()[i].setIsExtCustInd(false);
					}
					if (hmCheckedAudit.containsKey(String.valueOf(i))) {
						checkList.getCheckListItemList()[i].setIsAuditInd(true);
					}
					else {
						checkList.getCheckListItemList()[i].setIsAuditInd(false);
					}
				}
			}*/

			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

			if (CheckListUtil.isInCountry(ctx.getTeam(), ctx.getLimitProfile())) {
				ctx.setTrxCountryOrigin(ctx.getLimitProfile().getOriginatingLocation().getCountryCode());
				ctx.setTrxOrganisationOrigin(ctx.getLimitProfile().getOriginatingLocation().getOrganisationCode());
			}
			else {
				if (checkList.getCheckListLocation() != null) {
					ctx.setTrxCountryOrigin(checkList.getCheckListLocation().getCountryCode());
					ctx.setTrxOrganisationOrigin(checkList.getCheckListLocation().getOrganisationCode());
				}
			}
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			String custCategory = "MAIN_BORROWER";
			String applicationType = "COM";
//			String tCollateralID = "200701010000130";
			long paripassuID = 0L;
			ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, paripassuID, custCategory,
					applicationType);
			checkList.setCheckListOwner(owner);
			checkList.setCheckListType("PARIPASSU");
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

						if (isItemDeleted(checkList, i)) {
							continue;
						}
						/*if (!checkList.getCheckListItemList()[i].getIsInherited()) {
							if (hmMandatoryRows.containsKey(String.valueOf(i))) {
								checkList.getCheckListItemList()[i].setIsMandatoryInd(true);
							}
							else {
								checkList.getCheckListItemList()[i].setIsMandatoryInd(false);
							}
						}
						if (hmCheckedInVault.containsKey(String.valueOf(i))) {
							checkList.getCheckListItemList()[i].setIsInVaultInd(true);
						}
						else {
							checkList.getCheckListItemList()[i].setIsInVaultInd(false);
						}
						if (hmCheckedExtCustodian.containsKey(String.valueOf(i))) {
							checkList.getCheckListItemList()[i].setIsExtCustInd(true);
						}
						else {
							checkList.getCheckListItemList()[i].setIsExtCustInd(false);
						}
						if (hmCheckedAudit.containsKey(String.valueOf(i))) {
							checkList.getCheckListItemList()[i].setIsAuditInd(true);
						}
						else {
							checkList.getCheckListItemList()[i].setIsAuditInd(false);
						}*/
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
		private HashMap getMapFromString(String commaSepInput) {
			HashMap hm = new HashMap();
			StringTokenizer st = new StringTokenizer(commaSepInput, ",");
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				hm.put(key, key);
			}
			return hm;
		}

		private boolean isItemDeleted(ICheckList checkList, int i) {
			return ICMSConstant.STATE_DELETED.equals(checkList.getCheckListItemList()[i].getItemStatus());
		}
}
