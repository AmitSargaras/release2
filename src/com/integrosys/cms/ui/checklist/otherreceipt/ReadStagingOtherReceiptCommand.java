/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.otherreceipt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListJdbc;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.checklist.SharedDocumentsHelper;
import com.integrosys.cms.ui.common.LegalFirmList;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/07/20 06:19:09 $ Tag: $Name: $
 */
public class ReadStagingOtherReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadStagingOtherReceiptCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "custTypeTrxID", "java.lang.String", REQUEST_SCOPE },
				{ ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE },
				{ "session.list", "java.lang.String", SERVICE_SCOPE },
				{ "session.searchType", "java.lang.String", SERVICE_SCOPE },
				{ "session.search", "java.lang.String", SERVICE_SCOPE },
				{ "searchType", "java.lang.String", REQUEST_SCOPE },
				{ "search", "java.lang.String", REQUEST_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "list", "java.lang.String", REQUEST_SCOPE },

				{ "forwardUser", "java.lang.String", REQUEST_SCOPE } // +OFFICE
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
		return (new String[][] {
				{ "checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE},
				{ "flag", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "legalFirmLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "legalFirmValues", "java.util.Collection", REQUEST_SCOPE },
				{ "deferCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "waiverCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "custTrxDtList", "java.util.HashMap", SERVICE_SCOPE },
				{ "session.list", "java.lang.String", SERVICE_SCOPE },
				{ "session.searchType", "java.lang.String", SERVICE_SCOPE },
				{ "session.search", "java.lang.String", SERVICE_SCOPE },
				{ "session.checkListTrxVal", "com.integrosys.cms.app.checklist.trx.ICheckListTrxValue", SERVICE_SCOPE },
				{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				{ "forwardCollection", "java.util.Collection", REQUEST_SCOPE },
				{ "docReceipt", "java.lang.String", SERVICE_SCOPE },// +
																				// OFFICE
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
		try {
			resultMap.put("docReceipt", "O");
			String custTypeTrxID = (String) map.get("custTypeTrxID");
			String event = (String) map.get(ICommonEventConstant.EVENT);
			DefaultLogger.debug(this, "TrxiD before backend call" + custTypeTrxID);
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListTrxValue checkListTrxValSession = (ICheckListTrxValue)map.get("checkListTrxVal");
			if(custTypeTrxID==null){
				if(checkListTrxValSession!=null){
					custTypeTrxID=checkListTrxValSession.getTransactionID();
					
				}
			}
			
			ICheckListTrxValue checkListTrxVal = proxy.getCheckListByTrxID(custTypeTrxID);

			if("close_checklist_item".equals(event)){
				resultMap.put("session.checkListTrxVal", checkListTrxVal);
			}
			else{
				resultMap.put("session.checkListTrxVal", checkListTrxValSession);
			}
			String list = null;
			if(null != map.get("list"))
				list =(String) map.get("list");
			else 
				list =(String) map.get("session.list");
			
			String searchType = null;
			if(null != map.get("searchType"))
				searchType =(String) map.get("searchType");
			else 
				searchType =(String) map.get("session.searchType");
			
			String search = null;
			if(null != map.get("search"))
				search =(String) map.get("search");
			else 
				search =(String) map.get("session.search");
			
			resultMap.put("session.list", list);
			resultMap.put("session.searchType", searchType);
			resultMap.put("session.search", search);
			List transactionHistoryList= new ArrayList();
			 ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
			 transactionHistoryList = customerDAO.getTransactionHistoryList(checkListTrxVal.getTransactionID());
			 resultMap.put("transactionHistoryList", transactionHistoryList);
			// Sorts checklist before putting into resultMap
			ICheckList checkList = checkListTrxVal.getStagingCheckList();
			SharedDocumentsHelper.mergeViewableCheckListItemIntoStaging(checkListTrxVal.getCheckList(), checkList); // R1
																													// .5
																													// CR17
			ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
			checkList.setCheckListItemList(sortedItems);
			ArrayList checklistArray= new ArrayList();
			for(int ab=0;ab<checkList.getCheckListItemList().length;ab++){
				//ICheckListItem[] sortedItems =new ICheckListItem[checkList.getCheckListItemList().length];
				
				checklistArray.add(checkList.getCheckListItemList()[ab]);
				
				
				
			}
			Collection troubleTicketActions=checklistArray;
			List theListToBeSorted = new ArrayList(troubleTicketActions);

			ArrayList sortFields = new ArrayList();
			sortFields.add(new BeanComparator("documentStatus"));
			sortFields.add(new BeanComparator("itemStatus"));
			sortFields.add(new BeanComparator("item.itemDesc"));
			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort(theListToBeSorted, multiSort);

			troubleTicketActions = new ArrayList(theListToBeSorted);
			
			checkList.setCheckListItemList((ICheckListItem[]) troubleTicketActions.toArray(new ICheckListItem[troubleTicketActions.size()]));
			
			
			if (checkList != null) {
				HashMap docNos = new HashMap();
				
				ArrayList resultList = filterList(list, searchType, search, checkList.getCheckListItemList()); 
				checkList.setCheckListItemList((ICheckListItem[]) resultList.toArray(new ICheckListItem[resultList.size()]));
				
				ICheckListJdbc checklistJdbc = (ICheckListJdbc) BeanHouse.get("checkListJdbcImpl");
				Map<String, String> collateralTypeMap = new HashMap<String, String>();
				collateralTypeMap = checklistJdbc.getChecklistItemsCollateralType(checkList.getCheckListID());
				
				ICheckListItem[] itemList = checkList.getCheckListItemList();
				for (int count = 0; count < itemList.length; count++) {
					ICheckListItem item = itemList[count];
					long docNoLong = item.getCheckListItemRef();
					String docNo = String.valueOf(docNoLong);
					docNos.put(docNo,docNo); 
					
					String insuranceReferenceType = (String) collateralTypeMap.get(item.getInsuranceId());
					item.setReferenceType(insuranceReferenceType);
				}
				ArrayList resultListActual = new ArrayList();
				ICheckListItem[] itemListActual = checkListTrxVal.getCheckList().getCheckListItemList();
				for (int count2 = 0; count2 < itemListActual.length; count2++) {
					ICheckListItem item2 = itemListActual[count2];
					long docNoLong2 = item2.getCheckListItemRef();
					String docNo2 = String.valueOf(docNoLong2);
					
					if(docNos.containsKey(docNo2)){
						resultListActual.add(item2);
					}
				}
				checkListTrxVal.getCheckList().setCheckListItemList((ICheckListItem[]) resultListActual.toArray(new ICheckListItem[resultListActual.size()]));
			}
			
			
			
			List res = CompareOBUtil.compOBArrayCheckList(checkList.getCheckListItemList(),checkListTrxVal.getCheckList().getCheckListItemList());
			ArrayList list2 = new ArrayList(res);
			LinkedList linkedList = new LinkedList();
			for(int i =0;i<list2.size();i++){
				CompareResult compareResult =(CompareResult) list2.get(i);
				ICheckListItem OB = (ICheckListItem) compareResult.getObj();
				
				if(compareResult.isAdded()||compareResult.isDeleted()||compareResult.isModified()){
					
					linkedList.addFirst(OB);
				
				}else{
					linkedList.addLast(OB);
				}
				
				
				
			}
			checkListTrxVal.getStagingCheckList().setCheckListItemList((ICheckListItem[]) linkedList.toArray(new ICheckListItem[linkedList.size()]));
			checkList.setCheckListItemList((ICheckListItem[]) linkedList.toArray(new ICheckListItem[linkedList.size()]));
			
			
			
			
			resultMap.put("event",event);
			resultMap.put("checkList", checkList);
			resultMap.put("checkListTrxVal", checkListTrxVal);
			resultMap.put("ownerObj", checkListTrxVal.getStagingCheckList().getCheckListOwner());
			resultMap.put("flag", "Edit");
			if ("close_checklist_item".equals(event)||"search_other_doc_checker_close".equals(event)) {
				resultMap.put("flag", "Close");
			}
			if ("cancel_checklist_item".equals(event)) {
				resultMap.put("flag", "Cancel");
			}
			resultMap.put("frame", "false");// used to hide frames when user
											// comes from to do list
			if ("to_track".equals(event)) {
				resultMap.put("flag", "To Track");
			}
			/*// CR-380 starts
			String countryCode = "none";
			// ICheckList checkList =(ICheckList)
			// checkListTrxVal.getStagingCheckList();
			if ((checkList != null) && (checkList.getCheckListLocation() != null)
					&& (checkList.getCheckListLocation().getCountryCode() != null)) {
				countryCode = checkList.getCheckListLocation().getCountryCode();
				// System.out.println("Country Code Cmd Class????????:"+
				// countryCode);
			}
			LegalFirmList legalFirmList = LegalFirmList.getInstance(countryCode);
			resultMap.put("legalFirmLabels", legalFirmList.getLegalFirmLabel());
			resultMap.put("legalFirmValues", legalFirmList.getLegalFirmProperty());
			// CR-380 ends
*/
			resultMap.put("forwardCollection", checkListTrxVal.getNextRouteCollection()); // +
																							// OFFICE
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		resultMap.put("deferCreditApproverList", getAllDeferCreditApprover());
		resultMap.put("waiverCreditApproverList", getAllWaiveCreditApprover());
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private List getAllDeferCreditApprover() {
		List lbValList = new ArrayList();
		try {
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			
			List defer = (List)proxy.getAllDeferCreditApprover();
			
			//List idList = (List) getCityProxy().getCityList(stateId);				
		
			for (int i = 0; i < defer.size(); i++) {
				ICreditApproval creditApproval = (ICreditApproval)defer.get(i);
				
					String id = creditApproval.getApprovalCode();
					String val = creditApproval.getApprovalName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	
	private List getAllWaiveCreditApprover() {
		List lbValList = new ArrayList();
		try {
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			
			List waive = (List)proxy.getAllWaiveCreditApprover();
			
			//List idList = (List) getCityProxy().getCityList(stateId);				
		
			for (int i = 0; i < waive.size(); i++) {
				ICreditApproval creditApproval = (ICreditApproval)waive.get(i);
				
					String id = creditApproval.getApprovalCode();
					String val = creditApproval.getApprovalName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
	private ArrayList filterList(String list,String searchType,String search,ICheckListItem[] itemList){
		ArrayList sendList=new ArrayList();
		boolean validData = true;
		for (int i = 0; i < itemList.length; i++) {
			/*if(!"INACTIVE".equalsIgnoreCase(list)&& !"PENDING_INACTIVE".equalsIgnoreCase(list)){
				if(!"ACTIVE".equalsIgnoreCase(list)){
			if(null != list && !"".equalsIgnoreCase(list) && !list.equalsIgnoreCase(itemList[i].getItemStatus()))
				validData=false;
				}
//				if(null != list && !"".equalsIgnoreCase(list) && (itemList[i].getDocumentStatus().equalsIgnoreCase("PENDING_INACTIVE") || itemList[i].getDocumentStatus().equalsIgnoreCase("INACTIVE")))
				if(null != list && !"".equalsIgnoreCase(list) && ( itemList[i].getDocumentStatus().equalsIgnoreCase("INACTIVE")))
				validData=false;
			}*/
			if(null != list && !"".equalsIgnoreCase(list)){
			if("ACTIVE".equalsIgnoreCase(list)&& !itemList[i].getDocumentStatus().equalsIgnoreCase("ACTIVE")){
				validData=false;
			}
			if(!"ACTIVE".equalsIgnoreCase(list)&& !"INACTIVE".equalsIgnoreCase(list) && !"PENDING_INACTIVE".equalsIgnoreCase(list)){
				if(!list.equalsIgnoreCase(itemList[i].getItemStatus())||!itemList[i].getDocumentStatus().equalsIgnoreCase("ACTIVE")){
						
							validData=false;
						
				}
			}
			if("INACTIVE".equalsIgnoreCase(list)&& !list.equalsIgnoreCase(itemList[i].getDocumentStatus()))
				validData=false;
			if("PENDING_INACTIVE".equalsIgnoreCase(list)&& !list.equalsIgnoreCase(itemList[i].getDocumentStatus()))
				validData=false;
			}
			if(validData && null != searchType && !"".equalsIgnoreCase(searchType) && null != search && !"".equalsIgnoreCase(search)){
				search=search.trim();
				if(searchType.equalsIgnoreCase("documentcode")){
					validData=itemList[i].getItemCode().startsWith(search);
				}else if(searchType.equalsIgnoreCase("documentdescription")){
					validData=itemList[i].getItemDesc().toUpperCase().startsWith(search.toUpperCase());
				}else if(searchType.equalsIgnoreCase("creditapprover")){
					validData=itemList[i].getCreditApprover().startsWith(search);
				}else if(searchType.equalsIgnoreCase("documentexpirydate")){
					validData=DateUtil.convertToDisplayDate(itemList[i].getExpiryDate()).replace(' ', '/').startsWith(search);
				}
			}
			if(validData){
				sendList.add(itemList[i]);
			}
			validData = true;
		}
		return sendList;
	}
}
