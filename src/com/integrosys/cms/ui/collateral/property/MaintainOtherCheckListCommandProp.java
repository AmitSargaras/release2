package com.integrosys.cms.ui.collateral.property;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
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
import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.host.eai.customer.bus.ICustomerDao;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.customer.CustomerListMapper;

public class MaintainOtherCheckListCommandProp extends AbstractCommand implements ICommonEventConstant {


	/**
	 * Default Constructor
	 */
	public MaintainOtherCheckListCommandProp() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
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

        ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		String secType = "O";
		String secSubType = "O";
		
		long limitProfileID =itrxValue.getCollateral().getCollateralLimits()[0].getCmsLimitProfileId();
		ICollateralLimitMap[] map1 = itrxValue.getCollateral().getCollateralLimits();
		
		String custCategory = "MAIN_BORROWER";
		String applicationType = "COM";
//		String tCollateralID = "200701010000130";
		long collateralID = 0L;
		ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, collateralID, custCategory,
				applicationType);
		//ICollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
		ICheckList checkList = null;
		
		ICheckListProxyManager checklistProxyManager =(ICheckListProxyManager)BeanHouse.get("checklistProxy");
		ICheckListTemplateProxyManager checklistTemplateProxyManager =(ICheckListTemplateProxyManager)BeanHouse.get("templateProxy");
		
		try {
			
			HashMap limitAndChecklist = new HashMap();
			HashMap limitAndChecklistTrxValue = new HashMap();
			
			
			ICustomerDAO customerDao =  CustomerDAOFactory.getDAO();
			List limitIdList = customerDao.getCollateralMappedCustomerLimitIdList(itrxValue.getCollateral().getCollateralID());
			for(int i=0;i< limitIdList.size();i++){
				long checkListID= -999999999l;
				ICheckListDAO dao = CheckListDAOFactory.getCheckListDAO();
				String limitId = (String) limitIdList.get(i);
				String checkListId=dao.getChecklistId("O",Long.valueOf(limitId));
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
						limitAndChecklistTrxValue.put(limitId,checkListTrxVal);
						//resultMap.put("checkListTrxVal", checkListTrxVal);
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
					//resultMap.put("checkList", checkList);
				}

				ArrayList outputDocIds = null;
				ArrayList outputDocIdsNew = null;
				if (checkList != null) {
					ArrayList docNos = new ArrayList();
					ICheckListItem[] itemList = checkList.getCheckListItemList();
					for (int count = 0; count < itemList.length; count++) {
						ICheckListItem item = itemList[count];
						long docNoLong = item.getCheckListItemRef();
						String docNo = String.valueOf(docNoLong);
						docNos.add(docNo);
					}
					System.out.println("MaintainOtherChecklistCommandProp.java=> Line no. 194=> docNos.size()=>"+docNos.size());
					if(docNos.size() <= 900) {
						outputDocIds = checklistProxyManager.getDocumentIdsForCheckList(docNos);
					}else {
						Object[] arr = docNos.toArray();
				        
				        
				        int chunk = 900; // chunk size to divide
				        for(int j=0;j<arr.length;j+=chunk){
				          String arrDocNosNew =  Arrays.toString(Arrays.copyOfRange(arr, j, Math.min(arr.length,j+chunk)));
				          if(!"[]".equals(arrDocNosNew)) {
				          String[] arrNew = arrDocNosNew.split(",");
				          for(int m=0 ; m< arrNew.length; m++){
				              arrNew[m] = arrNew[m].trim();
				            }
				          
				           arrNew[0] = arrNew[0].replace("[","");
				           arrNew[(arrNew.length) - 1] = arrNew[(arrNew.length) - 1].replace("]","");
				           List newDocNos = new ArrayList();
				           newDocNos = Arrays.asList(arrNew);
				           ArrayList newDocNosFinal = new ArrayList(newDocNos);
				           System.out.println("Going for checklistProxyManager.getDocumentIdsForCheckList(newDocNosFinal)..");
				           outputDocIdsNew = checklistProxyManager.getDocumentIdsForCheckList(newDocNosFinal);
				           System.out.println("After checklistProxyManager.getDocumentIdsForCheckList(newDocNosFinal)..");
				           if(outputDocIdsNew != null) {
				        	   if(outputDocIdsNew.size() > 0 ) {
				        		   for(int k=0;k<outputDocIdsNew.size();k++) {
				        			   outputDocIds.add(outputDocIdsNew.get(k));
				        		   }
				        	   }
				           }
				        }}     
					}
					if(outputDocIds == null) {
						outputDocIds = new ArrayList();
					}
					
//					outputDocIds = checklistProxyManager.getDocumentIdsForCheckList(docNos);
				}
				resultMap.put("docNos", outputDocIds);
				
				limitAndChecklist.put(limitId,checkList);
			}
			resultMap.put("limitAndChecklist", limitAndChecklist);
			resultMap.put("limitAndChecklistTrxValue", limitAndChecklistTrxValue);
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
		} catch (Exception e) {
			e.printStackTrace();
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
/*	private ICheckList linkInsuranceReceipt(ICheckList checkList) throws CheckListException {
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
						long ref = checklistProxyManager.generateCheckListItemSeqNo();
						parentItem.setCheckListItemRef(ref);
						childItem.setParentCheckListItemRef(ref);
					}
				}
			}
		}
		return checkList;
	} */

}
