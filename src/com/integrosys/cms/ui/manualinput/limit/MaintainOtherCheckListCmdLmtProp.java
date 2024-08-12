/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.HashSet;
import java.util.Set;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.context.BeanHouse;
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
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.IInsuranceGCJdbc;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2006/11/06 06:47:35 $ Tag: $Name: $
 */
public class MaintainOtherCheckListCmdLmtProp extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public MaintainOtherCheckListCmdLmtProp() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "request.ITrxResult", "com.integrosys.cms.app.transaction.ICMSTrxResult",REQUEST_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
				{"collateralSet","java.util.HashSet",SERVICE_SCOPE},});
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
				{ "collInsurance", "java.util.HashMap", SERVICE_SCOPE },
				{ "insuranceList", "java.util.ArrayList", SERVICE_SCOPE },
				
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
        
		
       Set collateralSet = (HashSet) map.get("collateralSet");
       
       int size = collateralSet.size();
       
		
	if(size>0){
		
		List insuranceList=new ArrayList();
		IInsuranceGCJdbc insuranceGCJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
		
		
		Map<String,Long> collInsurance=new HashMap<String, Long>();
		Iterator it1 = collateralSet.iterator();
		while(it1.hasNext()){
			Long collateralIDLong = (Long)it1.next();
			long collateralID = collateralIDLong;
			SearchResult insuranceListColl=(SearchResult)insuranceGCJdbc.getAllInsurancePolicy(collateralID);
			
			List insuranceList1=(List)insuranceListColl.getResultList();
			for(int k=0; k <insuranceList1.size();k++){
				IInsurancePolicy actualObj=(IInsurancePolicy) insuranceList1.get(k);
				collInsurance.put(actualObj.getRefID(),collateralIDLong);
			}
			
			insuranceList.addAll(insuranceListColl.getResultList());
		}
		
		resultMap.put("collInsurance", collInsurance);
		resultMap.put("insuranceList", insuranceList);
		
		
		if(insuranceList.size()>0){
		ILimitTrxValue itrxValue = (ILimitTrxValue) map.get("lmtTrxObj");
		String secType = "O";
		String secSubType = "O";
		
		long limitProfileID =itrxValue.getLimitProfileID();
		
		String custCategory = "MAIN_BORROWER";
		String applicationType = "COM";

		long collateralID = 0L;
		ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,
				applicationType);
		//ICollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
		ICheckList checkList = null;
		
		ICheckListProxyManager checklistProxyManager =(ICheckListProxyManager)BeanHouse.get("checklistProxy");
		ICheckListTemplateProxyManager checklistTemplateProxyManager =(ICheckListTemplateProxyManager)BeanHouse.get("templateProxy");
		try {
			
			long checkListID= -999999999l;
			ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
			String checkListId=dao.getChecklistId("O",limitProfileID);
			if(null!=checkListId){
				 checkListID = Long.parseLong(checkListId);
			}
			int wip =0;
			if (ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) {
				resultMap.put("wip", "wip");
			}
			else {
				if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {

					checkList = checklistProxyManager.getDefaultCAMCheckList(owner,"IN", secType, secSubType, "", "", "");
			//		checkList = linkInsuranceReceipt(checkList);
					resultMap.put("checkListTrxVal", null);
					resultMap.put("ownerObj", checkList.getCheckListOwner());
				}
				else {
					ICheckListTrxValue checkListTrxVal = checklistProxyManager.getCheckList(checkListID);
					checkList = checkListTrxVal.getCheckList();


					if (checkList.getTemplateID() <= 0) {
						DefaultLogger.warn(this, "There is template id for checklist id [" + checkListID
								+ "], retrieving template id");

						ITemplate template = checklistTemplateProxyManager.getCollateralTemplate(secType,
								secSubType, "IN");
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

				String dispatchToMaintain = ("Y".equals(checkList.getDisableCollaborationInd())) ? "Y" : "N";
				checkList.setDisableCollaborationInd(dispatchToMaintain);

				// CR-236
				String event = (String) map.get("event");
				if ("delete".equals(event)) {
					((OBCheckList) checkList).setObsolete(ICMSConstant.TRUE_VALUE);
				}else if("view".equals(event)){
                    isViewFlag = true;
                }
				resultMap.put("checkList", checkList);
			}

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
				outputDocIds = checklistProxyManager.getDocumentIdsForCheckList(docNos);
			}
			resultMap.put("docNos", outputDocIds);
		
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
		}
	}
	
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
