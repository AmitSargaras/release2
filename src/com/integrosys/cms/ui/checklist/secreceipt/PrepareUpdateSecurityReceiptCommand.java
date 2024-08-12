/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.secreceipt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.LegalFirmList;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/10/11 07:26:47 $ Tag: $Name: $
 */
public class PrepareUpdateSecurityReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareUpdateSecurityReceiptCommand() {
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
				 { "list", "java.lang.String", REQUEST_SCOPE },
				 { "searchType", "java.lang.String", REQUEST_SCOPE },
				 { "search", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "checkListID", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE },
				{ "secSubType", "java.lang.String", REQUEST_SCOPE },
				{ "secName", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRef", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "legalID", "java.lang.String", REQUEST_SCOPE },
				{ "custCategory", "java.lang.String", REQUEST_SCOPE },
				{ "session.checkListID", "java.lang.String", SERVICE_SCOPE },
				{ "session.checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "session.list", "java.lang.String", SERVICE_SCOPE },
				 { "session.searchType", "java.lang.String", SERVICE_SCOPE },
				 { "session.search", "java.lang.String", SERVICE_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE},
				{ "wip", "java.lang.String", REQUEST_SCOPE }, { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "no_template", "java.lang.String", REQUEST_SCOPE },
				{ "legalFirmLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "session.checkListID", "java.lang.String", SERVICE_SCOPE },
				{ "session.checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				/*{ "checkListID", "java.lang.String", REQUEST_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ "secType", "java.lang.String", REQUEST_SCOPE },
				{ "secSubType", "java.lang.String", REQUEST_SCOPE },
				{ "secName", "java.lang.String", REQUEST_SCOPE },
				{ "collateralID", "java.lang.String", REQUEST_SCOPE },
				{ "collateralRef", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "legalID", "java.lang.String", REQUEST_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE },*/
				{ "session.list", "java.lang.String", SERVICE_SCOPE },
				 { "session.searchType", "java.lang.String", SERVICE_SCOPE },
				 { "session.search", "java.lang.String", SERVICE_SCOPE },
				{ "custCategory", "java.lang.String", REQUEST_SCOPE },
				{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				{ "deferCreditApproverList", "java.util.List", SERVICE_SCOPE },
				 { "flag", "java.lang.String", SERVICE_SCOPE },
				{ "waiverCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "custTrxDtList", "java.util.HashMap", SERVICE_SCOPE },
				{ "docNos", "java.util.ArrayList", SERVICE_SCOPE }, 
				{ "docReceipt", "java.lang.String", SERVICE_SCOPE },
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
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			String tCheckListID = (String) map.get("checkListID");
			if(null == tCheckListID || "".equalsIgnoreCase(tCheckListID)|| "null".equals(tCheckListID)){
				tCheckListID=(String) map.get("session.checkListID");
			}
			long checkListID = Long.parseLong(tCheckListID);
			String camNo = (String) map.get("session.camNo");
			String secType = (String) map.get("secType");
			String secSubType = (String) map.get("secSubType");
			
			ICheckList checkList = null;
			if(checkListID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE){
				ICheckListTrxValue checkListTrxVal = proxy.getCheckList(checkListID);
				checkList=checkListTrxVal.getCheckList();
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
			OBCollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
		//	proxy.getCheckList(checkListID).getStagingCheckList();
//			ICheckListTrxValue checkListTrxVal = proxy.getCheckList(checkListID);
			resultMap.put("docReceipt", "S");
			int wip = proxy.allowCheckListTrx(owner);
			DefaultLogger.debug(this, "WORK IN Progress >>>>>>>>>" + wip);
			String event = (String) map.get("event");
			
				
			if ((ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) && (event != null) && event.equals("prepare_update")) {
				resultMap.put("wip", "wip");
			}
			else {
				if (checkListID == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					resultMap.put("no_template", "true");
					/*
					 * DefaultLogger.debug(this,
					 * ">>>>>>>>>>>>>>>>>>Checklist id is long.min value");
					 * String legalConstitution = (String)
					 * map.get("legalConstitution"); String limitBkgLoc =
					 * (String) map.get("limitBkgLoc");
					 * DefaultLogger.debug(this, "legalConstitution----------->"
					 * + legalConstitution); DefaultLogger.debug(this,
					 * "limitBkgLoc----------->" + limitBkgLoc); checkList =
					 * proxy
					 * .getDefaultCollateralCheckList(owner,secType,secSubType
					 * ,limitBkgLoc); resultMap.put("checkListTrxVal", null);
					 */
				}
				else {
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is " + checkListID);
					ICheckListTrxValue checkListTrxVal = proxy.getCheckList(checkListID);
					 ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
					 transactionHistoryList = customerDAO.getTransactionHistoryList(checkListTrxVal.getTransactionID());
					 if(null==checkList){
							checkList = checkListTrxVal.getCheckList();
							try {
								resultMap.put("session.checkList", (ICheckList)com.integrosys.cms.app.common.util.CommonUtil.deepClone(checkList));
							} catch (Exception e) {
								e.printStackTrace();
							}
							 
						}
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
					
					
					if(! (checkListTrxVal.getStatus().trim().equalsIgnoreCase("ACTIVE"))){
						resultMap.put("wip", "wip");
					}
					resultMap.put("ownerObj", checkList.getCheckListOwner());
					resultMap.put("checkListTrxVal", checkListTrxVal);
					
					//HDFC Changes for sorting according to date:
					
					//ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
					//checkList.setCheckListItemList(sortedItems);
				}
				  resultMap.put("transactionHistoryList", transactionHistoryList);
				resultMap.put("frame", "true");// used to apply frames
                resultMap.put("checkListForm", checkList);
                resultMap.put("session.checkListID",tCheckListID);
               

                if (checkList != null) {
					ArrayList docNos = new ArrayList();
					
					ArrayList resultList = filterList(list, searchType, search, checkList.getCheckListItemList()); 
					checkList.setCheckListItemList((ICheckListItem[]) resultList.toArray(new ICheckListItem[resultList.size()]));
					
					ICheckListItem[] itemList = checkList.getCheckListItemList();
					for (int count = 0; count < itemList.length; count++) {
						ICheckListItem item = itemList[count];
						long docNoLong = item.getCheckListItemRef();
						String docNo = String.valueOf(docNoLong);
						docNos.add(docNo); 
					}
				}
                resultMap.put("checkList", checkList);
                resultMap.put("checkListForm", checkList);
				/*// CR-380 starts
				String countryCode = "none";
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
*/			}
			
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
			 resultMap.put("session.camNo", camNo);
			 String flag="";
				if(event.equals("view")){
					flag="view";
				}
				resultMap.put("flag", flag);
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
			if(!"INACTIVE".equalsIgnoreCase(list)){
				if(!"ACTIVE".equalsIgnoreCase(list)){
				if(null != list && !"".equalsIgnoreCase(list) && !list.equalsIgnoreCase(itemList[i].getItemStatus()))
					validData=false;
				}
				if(null != list && !"".equalsIgnoreCase(list) && (itemList[i].getDocumentStatus().equalsIgnoreCase("PENDING_INACTIVE") || itemList[i].getDocumentStatus().equalsIgnoreCase("INACTIVE")))
					validData=false;
				}		
				if("INACTIVE".equalsIgnoreCase(list)&& !list.equalsIgnoreCase(itemList[i].getDocumentStatus()))
					validData=false;
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
