/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.security;

import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateDAOFactory;
import com.integrosys.cms.app.chktemplate.bus.IDocumentDAO;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/10/09 05:39:20 $ Tag: $Name: $
 */
public class SubmitSecurityCheckListCommand extends AbstractCommand implements ICommonEventConstant {

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
	public SubmitSecurityCheckListCommand() {
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
				{ "mandatoryRows", "java.lang.String", REQUEST_SCOPE },{ "mandatoryDisplayRows", "java.lang.String", REQUEST_SCOPE },
				{ "checkedInVault", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ "checkedExtCustodian", "java.lang.String", REQUEST_SCOPE },
				{ "checkedAudit", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
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
		String mandatoryDisplayRows = (String) map.get("mandatoryDisplayRows");
		HashMap hmMandatoryDisplayRows = getMapFromString(mandatoryDisplayRows);
		String mandatoryRows = (String) map.get("mandatoryRows");
		String checkedInVault = (String) map.get("checkedInVault");
		String checkedExtCustodian = (String) map.get("checkedExtCustodian");
		String checkedAudit = (String) map.get("checkedAudit");
		ICheckList checkList = (ICheckList) map.get("checkList");

		HashMap hmMandatoryRows = getMapFromString(mandatoryRows);
		HashMap hmCheckedInVault = getMapFromString(checkedInVault);
		HashMap hmCheckedExtCustodian = getMapFromString(checkedExtCustodian);
		HashMap hmCheckedAudit = getMapFromString(checkedAudit);
		IDocumentDAO documentDao= CheckListTemplateDAOFactory.getDocumentDAO();
		ICheckListItem temp[] = checkList.getCheckListItemList();
		if (temp != null) {
			for (int i = 0; i < temp.length; i++) {

				/**
				 * skip update of deleted items
				 */
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
				if (!checkList.getCheckListItemList()[i].getIsInherited()) {
					if (hmMandatoryDisplayRows.containsKey(String.valueOf(i))) {
						checkList.getCheckListItemList()[i].setIsMandatoryDisplayInd(true);
					}
					else {
						checkList.getCheckListItemList()[i].setIsMandatoryDisplayInd(false);
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
				
				String tenure[] = new String[2];
				tenure=documentDao.searchDocumentItemByCode(checkList.getCheckListItemList()[i].getItemCode());
				if(tenure!=null){
				checkList.getCheckListItemList()[i].setTenureCount(Integer.parseInt(tenure[0]));
				checkList.getCheckListItemList()[i].setTenureType(tenure[1]);
				}
				
				
			}
		}

		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");

		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
		DefaultLogger.debug(this, ">>>>>>>>>> REMOVING THE SHARE CHECKLIST PORTION FROM SaveCheckListItemCommand ");
		Date d = DateUtil.getDate();
		
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date applicationDate=new Date();
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				 applicationDate=new Date(generalParamEntries[i].getParamValue());
			}
		}
		
		//date.setTime(d.getTime());
		applicationDate.setHours(d.getHours());
		applicationDate.setMinutes(d.getMinutes());
		applicationDate.setSeconds(d.getSeconds());
		DefaultLogger.debug(this,"date from general param:"+applicationDate);
		DefaultLogger.debug(this,"Login id from global scope:"+user.getLoginID());
		
		checkList.setUpdatedBy(user.getLoginID());
		checkList.setUpdatedDate(applicationDate);
		
		for(int i =0;i<checkList.getCheckListItemList().length;i++){
			if(checkList.getCheckListItemList()[i].getItemStatus().equals("AWAITING")){
				checkList.getCheckListItemList()[i].setUpdatedBy(user.getLoginID());
				checkList.getCheckListItemList()[i].setUpdatedDate(applicationDate);
//				checkList.getCheckListItemList()[i].setApprovedBy(user.getLoginID());
//				checkList.getCheckListItemList()[i].setApprovedDate(applicationDate);
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
					if (!checkList.getCheckListItemList()[i].getIsInherited()) {
						if (hmMandatoryDisplayRows.containsKey(String.valueOf(i))) {
							checkList.getCheckListItemList()[i].setIsMandatoryDisplayInd(true);
						}
						else {
							checkList.getCheckListItemList()[i].setIsMandatoryDisplayInd(false);
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
