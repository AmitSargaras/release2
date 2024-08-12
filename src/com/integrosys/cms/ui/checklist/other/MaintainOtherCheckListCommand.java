/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.other;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2006/11/06 06:47:35 $ Tag: $Name: $
 */
public class MaintainOtherCheckListCommand extends AbstractCommand implements ICommonEventConstant {

	private ICheckListProxyManager checklistProxyManager;

	private ICheckListTemplateProxyManager checklistTemplateProxyManager;

	public void setCheckListProxyManager(ICheckListProxyManager checklistProxyManager) {
		this.checklistProxyManager = checklistProxyManager;
	}

	public void setCheckListTemplateProxyManager(ICheckListTemplateProxyManager checklistTemplateProxyManager) {
		this.checklistTemplateProxyManager = checklistTemplateProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public MaintainOtherCheckListCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "checkListID", "java.lang.String", REQUEST_SCOPE },
				{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE }, { "orgCode", "java.lang.String", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE }, { "secSubType", "java.lang.String", REQUEST_SCOPE },
				{ "dispatchToMaintain", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
					});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
				{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "no_template", "java.lang.String", REQUEST_SCOPE },
				{ "frame", "java.lang.String", SERVICE_SCOPE }, { "docNos", "java.util.ArrayList", SERVICE_SCOPE },
				 { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
//                { "colowner", "com.integrosys.cms.app.custodian.bus.CollateralCustodianInfo", REQUEST_SCOPE },
                { "isViewFlag", "java.lang.Boolean", REQUEST_SCOPE },
                });
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
        boolean isViewFlag = false;

		String tCheckListID = (String) map.get("checkListID");
		long checkListID = Long.parseLong(tCheckListID);
		String secType = (String) map.get("secType");
		String secSubType = (String) map.get("secSubType");
		String limitBkgLoc = (String) map.get("limitBkgLoc");
		String orgCode = (String) map.get("orgCode");

		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		long limitProfileID = limit.getLimitProfileID();
		String custCategory = "MAIN_BORROWER";
		String applicationType = "COM";
//		String tCollateralID = "200701010000130";
		long collateralID = 0L;
		ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,
				applicationType);
	//	ICollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
		ICheckList checkList = null;
		try {
		//	int wip = this.checklistProxyManager.allowCheckListTrx(owner);
			
//			int wip =0;
			
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			
			if(checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE){
			
				int count=dao.getPendingStageChecklistCount("O",limitProfileID);
				if(count>0){
				resultMap.put("wip", "wip");
				}
//			if (ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) {
//				resultMap.put("wip", "wip");
//			}
			}else{
			//	ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
				int count=dao.getPendingChecklistCount(checkListID);
				if(count>0){
				resultMap.put("wip", "wip");
				}
			}
			if(null==resultMap.get("wip")){
			//else {
				if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {

					if ((orgCode != null) && orgCode.equals("null")) {
						orgCode = null;
					}
				//	secType="CAM";
				//	secSubType="CAM";
					checkList = this.checklistProxyManager.getDefaultCAMCheckList(owner,"IN", secType, secSubType, "", "", "");
					checkList = linkInsuranceReceipt(checkList);
					resultMap.put("checkListTrxVal", null);
					resultMap.put("ownerObj", checkList.getCheckListOwner());
				}
				else {
					ICheckListTrxValue checkListTrxVal = this.checklistProxyManager.getCheckList(checkListID);
					checkList = checkListTrxVal.getCheckList();

//                    //[Start] Customize for alliance
//                    ICustodianProxyManager proxy = CustodianProxyManagerFactory.getCustodianProxyManager();
//                    CustodianSearchCriteria searchCriteria = new CustodianSearchCriteria();
//
//                    searchCriteria.setCheckListID(checkList.getCheckListID());
//                    searchCriteria.setCollateralID(owner.getCollateralID());
//                    searchCriteria.setDocType(ICMSConstant.DOC_TYPE_CAM);
//
//                    HashMap srmap =  proxy.getDocWithOwnerInfo(searchCriteria);
//                    resultMap.put("colowner", srmap.get(ICMSConstant.SEC_OWNER));
//                    //[End]

					if (checkList.getTemplateID() <= 0) {
						DefaultLogger.warn(this, "There is template id for checklist id [" + checkListID
								+ "], retrieving template id");

						ITemplate template = this.checklistTemplateProxyManager.getCollateralTemplate(secType,
								secSubType, limitBkgLoc);
						if (template != null) {
							checkList.setTemplateID(template.getTemplateID());
						}
					}

					resultMap.put("ownerObj", checkList.getCheckListOwner());
					resultMap.put("checkListTrxVal", checkListTrxVal);
				}

				if (checkList.getCheckListItemList() != null) {
					Arrays.sort(checkList.getCheckListItemList());
				}

				String checkListStatus = checkList.getCheckListStatus();
				// perform sorting only if checklist status is not NEW
				if ((checkListStatus == null)
						|| ((checkListStatus != null) && !checkListStatus.equals(ICMSConstant.STATE_CHECKLIST_NEW))) {
					ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
					checkList.setCheckListItemList(sortedItems);
				}

				String dispatchToMaintain = ("Y".equals(map.get("dispatchToMaintain")) || "Y".equals(checkList
						.getDisableCollaborationInd())) ? "Y" : "N";
				checkList.setDisableCollaborationInd(dispatchToMaintain);

				// CR-236
				String event = (String) map.get("event");
				if ("delete".equals(event)) {
					((OBCheckList) checkList).setObsolete(ICMSConstant.TRUE_VALUE);
				}else if("view".equals(event)){
                    isViewFlag = true;
                }

				resultMap.put("checkList", checkList);
                resultMap.put("isViewFlag",new Boolean(isViewFlag));
			}
			resultMap.put("frame", "true");// used to hide frames when user
			// comes from to do list

			ArrayList outputDocIds = null;
			if (checkList != null) {
				ArrayList docNos = new ArrayList();
				ICheckListItem[] itemList = checkList.getCheckListItemList();
				for (int count = 0; count < itemList.length; count++) {
					ICheckListItem item = itemList[count];
					long docNoLong = item.getCheckListItemRef();
					String docNo = String.valueOf(docNoLong);
					docNos.add(docNo);
				}
				outputDocIds = this.checklistProxyManager.getDocumentIdsForCheckList(docNos);
			}
			resultMap.put("docNos", outputDocIds);
			
			LimitDAO limitDao = new LimitDAO();
			try {
			String migratedFlag = "N";	
			boolean status = false;	
			 status = limitDao.getCAMMigreted("CMS_CHECKLIST",checkListID,"CHECKLIST_ID");
			
			if(status)
			{
				migratedFlag= "Y";
			}
			resultMap.put("migratedFlag", migratedFlag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		catch (TemplateNotSetupException e) {
			resultMap.put("no_template", "true");
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("fail to retrieve checklist template of security, type [" + secType
					+ "], subtype [" + secSubType + "]", ex);
		}
		catch (CheckListException ex) {
			throw new CommandProcessingException("fail to maintain security checklist", ex);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	/**
	 * Add newItems to the checklist. This will also spawn a specific insurance
	 * policy a predefined premium receipt. As for now, the matrix is stored in
	 * common code catetory entry.
	 * 
	 * @param checkList of type ICheckList
	 * @return ICheckList checklist updated with new checklist items
	 * @throws com.integrosys.cms.app.checklist.bus.CheckListException on any
	 *         errors encountered
	 */
	private ICheckList linkInsuranceReceipt(ICheckList checkList) throws CheckListException {
		ICheckListItem[] existingItems = checkList.getCheckListItemList();

		HashMap receiptMap = CheckListHelper.getPremiumReceiptMap();

		for (int i = existingItems.length - 1; i >= 0; i--) {
			ICheckListItem parentItem = existingItems[i];
			if (parentItem.getItem().getMonitorType() != null) {
				if (parentItem.getItem().getMonitorType().equals(ICMSConstant.INSURANCE_POLICY)
						&& !CheckListHelper.isExpired(parentItem.getItem())) {
					String childCode = (String) receiptMap.get(parentItem.getItem().getItemCode());
					if (childCode == null) {
						continue; // no receipt tied to the policy, so no need
						// to spawn.
					}
					ICheckListItem childItem = CheckListHelper.getPremiumReceiptItem(childCode, existingItems);
					if (childItem != null) {
						long ref = this.checklistProxyManager.generateCheckListItemSeqNo();
						parentItem.setCheckListItemRef(ref);
						childItem.setParentCheckListItemRef(ref);
					}
				}
			}
		}
		return checkList;
	}
}
