/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.facilityreceipt;

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
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.LegalFirmList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/10/11 07:26:47 $ Tag: $Name: $
 */
public class PrepareUpdateFacilityReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareUpdateFacilityReceiptCommand() {
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
				 { "event", "java.lang.String", REQUEST_SCOPE },
				 { "list", "java.lang.String", REQUEST_SCOPE },
				 { "searchType", "java.lang.String", REQUEST_SCOPE },
				 { "search", "java.lang.String", REQUEST_SCOPE },
				{ "ownerObj", "com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner", FORM_SCOPE },
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
				{ "limitID", "java.lang.String", REQUEST_SCOPE },
				{ "limitIDNew", "java.lang.String", SERVICE_SCOPE },
				{ "limitName", "java.lang.String", REQUEST_SCOPE },
				{ "session.list", "java.lang.String", SERVICE_SCOPE },
				 { "session.searchType", "java.lang.String", SERVICE_SCOPE },
				 { "session.search", "java.lang.String", SERVICE_SCOPE },
					{ "session.checkListID", "java.lang.String", SERVICE_SCOPE },
					{ "session.checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
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
				{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				{ "legalFirmValues", "java.util.Collection", REQUEST_SCOPE },
				{ "deferCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "waiverCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "custTrxDtList", "java.util.HashMap", SERVICE_SCOPE },
				{ "session.checkListID", "java.lang.String", SERVICE_SCOPE },
				{ "limitID", "java.lang.String", SERVICE_SCOPE },
				{ "limitIDNew", "java.lang.String", SERVICE_SCOPE },
				{ "limitName", "java.lang.String", SERVICE_SCOPE },
				{ "checkListIDMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "session.checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "session.list", "java.lang.String", SERVICE_SCOPE },
				 { "session.searchType", "java.lang.String", SERVICE_SCOPE },
				 { "session.search", "java.lang.String", SERVICE_SCOPE },
				 { "flag", "java.lang.String", SERVICE_SCOPE },
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
		HashMap checkListIDMap = new HashMap();
		String event=(String)map.get("event");
		DefaultLogger.debug(this, "Inside doExecute()");
		String tlimitID = (String) map.get("limitID");
		System.out.println("limit id from request map : "+tlimitID);
		if(tlimitID==null) {
			tlimitID = (String) map.get("limitIDNew");
			System.out.println("limit id from session map : "+tlimitID);
		}
		String limitName = (String) map.get("limitName");
		 List transactionHistoryList= new ArrayList();
		try {
			resultMap.put("docReceipt", "F");
			String tCheckListID = (String) map.get("checkListID");
			if(tCheckListID==null || "".equals(tCheckListID)|| "null".equals(tCheckListID)){
				tCheckListID=(String) map.get("session.checkListID");
			}
			long checkListID = Long.parseLong(tCheckListID);
			String secType = (String) map.get("secType");
			String secSubType = (String) map.get("secSubType");
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckList checkList = null;
			ArrayList checkListArray1=new ArrayList();
			ArrayList checklistArray= new ArrayList();
			Collection troubleTicketActions;
			String list="";
			 String searchType="";
			 String search="";
			 resultMap.put("session.checkListID", tCheckListID);
			
			if("search_facility_doc".equals(event)||"checker_search_facility_doc".equals(event)){
				 list=(String)map.get("list");
				 if(list==null)
					 list=(String)map.get("session.list");
				 searchType=(String)map.get("searchType");
				 if(searchType==null)					 
					 searchType=(String)map.get("session.searchType");
				 search=(String)map.get("search");
				 if(search==null)
					 search=(String)map.get("session.search");			
			}
			 resultMap.put("session.list", list.trim());
			 resultMap.put("session.searchType", searchType.trim());
			 resultMap.put("session.search", search.trim());
			
			OBCollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
		//	proxy.getCheckList(checkListID).getStagingCheckList();
			
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			ILimit[] OB=limit.getLimits();
          	for(int i=0;i<OB.length;i++){
              ILimit obLimit= OB[i];
              long limitId= obLimit.getLimitID();
           
              try {
            	  CheckListSearchResult checkList2=proxy.getCheckListByCollateralID(limitId);
            	  if(checkList2!=null)
				checkListIDMap.put(new Long(checkList2.getCheckListID()), obLimit);
				
			} catch (CheckListException e) {
				
				e.printStackTrace();
				throw new CommandProcessingException("failed to retrieve  checklist ", e);
			}
          	}
		
          	MILimitUIHelper helper = new MILimitUIHelper();
          	SBMILmtProxy lmtProxy = helper.getSBMILmtProxy();
          	ILimitTrxValue lmtTrxObj = null;
          	lmtTrxObj = lmtProxy.searchLimitByLmtId(tlimitID);
          	if (CommonUtil.checkWip(event, lmtTrxObj)) {
          		resultMap.put("wip", "wip");
          	}
          	if (CommonUtil.checkDeleteWip(event, lmtTrxObj)) {
          		resultMap.put("wip", "wip");
          	}
          				
          	resultMap.put("checkListIDMap",checkListIDMap);
			int wip = proxy.allowCheckListTrx(owner);
			DefaultLogger.debug(this, "WORK IN Progress >>>>>>>>>" + wip);
			
			
				
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
					if(! (checkListTrxVal.getStatus().trim().equalsIgnoreCase("ACTIVE"))){
						resultMap.put("wip", "wip");
					}
					checkList = checkListTrxVal.getCheckList();
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
					 if("search_facility_doc".equals(event)||"checker_search_facility_doc".equals(event)){/*						 
						 ICheckListItem[] searchCheckListItemList=CheckListDAOFactory.getCheckListDAO().searchDoc(checkListID,list.trim(),searchType.trim(),search.trim());
						 //checkListArray1=(ArrayList)searchMap.get("searchList");
						 checkList.setCheckListItemList(searchCheckListItemList);
					 */}
						
						
						for(int ab=0;ab<checkList.getCheckListItemList().length;ab++){
							//ICheckListItem[] sortedItems =new ICheckListItem[checkList.getCheckListItemList().length];
							
							checklistArray.add(checkList.getCheckListItemList()[ab]);							
						
					 }
				
			
					troubleTicketActions=checklistArray;
					
					List theListToBeSorted = new ArrayList(troubleTicketActions);

					ArrayList sortFields = new ArrayList();
					sortFields.add(new BeanComparator("documentStatus"));
					sortFields.add(new BeanComparator("itemStatus"));
					sortFields.add(new BeanComparator("item.itemDesc"));
					ComparatorChain multiSort = new ComparatorChain(sortFields);
					Collections.sort(theListToBeSorted, multiSort);

					troubleTicketActions = new ArrayList(theListToBeSorted);
					
					checkList.setCheckListItemList((ICheckListItem[]) troubleTicketActions.toArray(new ICheckListItem[troubleTicketActions.size()]));
					
					resultMap.put("ownerObj", checkList.getCheckListOwner());
					resultMap.put("checkListTrxVal", checkListTrxVal);
					//HDFC Changes for sorting according to date:
					
					//ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
					//checkList.setCheckListItemList(sortedItems);
				}
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
				resultMap.put("frame", "true");// used to apply frames
                resultMap.put("checkListForm", checkList);
                resultMap.put("checkList", checkList);
             
				if (checkList != null) {
					ArrayList docNos = new ArrayList();
					ICheckListItem[] itemList = checkList.getCheckListItemList();
					for (int count = 0; count < itemList.length; count++) {
						ICheckListItem item = itemList[count];
						long docNoLong = item.getCheckListItemRef();
						String docNo = String.valueOf(docNoLong);
						docNos.add(docNo);
					}
				}

				// CR-380 starts
			/*	String countryCode = "none";
				if ((checkList != null) && (checkList.getCheckListLocation() != null)
						&& (checkList.getCheckListLocation().getCountryCode() != null)) {
					countryCode = checkList.getCheckListLocation().getCountryCode();
					// System.out.println("Country Code Cmd Class????????:"+
					// countryCode);
				}
				LegalFirmList legalFirmList = LegalFirmList.getInstance(countryCode);
				resultMap.put("legalFirmLabels", legalFirmList.getLegalFirmLabel());
				resultMap.put("legalFirmValues", legalFirmList.getLegalFirmProperty());*/
				// CR-380 ends
			}
			
			
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		String flag="";
		if(event.equals("view")){
			flag="view";
		}
		resultMap.put("flag", flag);
	     resultMap.put("transactionHistoryList", transactionHistoryList);
		resultMap.put("limitID", tlimitID);
		resultMap.put("limitIDNew", tlimitID);
		resultMap.put("limitName", limitName);
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
