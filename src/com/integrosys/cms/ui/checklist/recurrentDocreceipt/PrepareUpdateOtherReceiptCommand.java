/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.recurrentDocreceipt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListDAO;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchCriteria;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralChargeDetails;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.LegalFirmList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/10/11 07:26:47 $ Tag: $Name: $
 */
public class PrepareUpdateOtherReceiptCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareUpdateOtherReceiptCommand() {
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
				{ "limitBkgLoc", "java.lang.String", REQUEST_SCOPE },
				{ "legalConstitution", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
					"com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
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
				{ "docCodeWithStocks", "java.util.HashMap", SERVICE_SCOPE },
				 { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "docNos", "java.util.ArrayList", SERVICE_SCOPE },
				 { "stockDocChkList", "java.util.HashMap", SERVICE_SCOPE },
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
		HashMap stockDocChkList = new HashMap();
		try {
			String tCheckListID = (String) map.get("checkListID");
			long checkListID = Long.parseLong(tCheckListID);
			String secType = (String) map.get("secType");
			String secSubType = (String) map.get("secSubType");
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckList checkList = null;
			 List transactionHistoryList= new ArrayList();
			OBCollateralCheckListOwner owner = (OBCollateralCheckListOwner) map.get("ownerObj");
		//	proxy.getCheckList(checkListID).getStagingCheckList();
//			ICheckListTrxValue checkListTrxVal = proxy.getCheckList(checkListID);
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
					  
					checkList = checkListTrxVal.getCheckList();
					ArrayList checklistArray= new ArrayList();
					for(int ab=0;ab<checkList.getCheckListItemList().length;ab++){
						//ICheckListItem[] sortedItems =new ICheckListItem[checkList.getCheckListItemList().length];
						
						checklistArray.add(checkList.getCheckListItemList()[ab]);
						
						
						
					}
					Collection troubleTicketActions=checklistArray;
					List theListToBeSorted = new ArrayList(troubleTicketActions);

					ArrayList sortFields = new ArrayList();
					sortFields.add(new BeanComparator("itemStatus"));
					//sortFields.add(new BeanComparator("docDate"));
					ComparatorChain multiSort = new ComparatorChain(sortFields);
					Collections.sort(theListToBeSorted, multiSort);

					troubleTicketActions = new ArrayList(theListToBeSorted);
					
					checkList.setCheckListItemList((ICheckListItem[]) troubleTicketActions.toArray(new ICheckListItem[troubleTicketActions.size()]));
					
					if(! (checkListTrxVal.getStatus().trim().equalsIgnoreCase("ACTIVE"))){
						resultMap.put("wip", "wip");
					}
					resultMap.put("ownerObj", checkList.getCheckListOwner());
					resultMap.put("checkListTrxVal", checkListTrxVal);
					//ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
					//checkList.setCheckListItemList(sortedItems);
				}
				resultMap.put("transactionHistoryList", transactionHistoryList);
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
						if("STOCK_STATEMENT".equals(item.getStatementType())){
						stockDocChkList.put(item.getItemCode(), item.getItemCode());
						}
					}
				}

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
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
		
		DocumentSearchCriteria criteria = new DocumentSearchCriteria();
		criteria.setDocumentType("REC");
	//	String[] splitList = chkTemplateType.split("-");
		SearchResult sr = null;
		try {
			sr = proxy.getDocumentItemList(criteria);
		}
		catch (CheckListTemplateException ex) {
			throw new CommandProcessingException("failed to retrieve document item list using criteria [" + criteria
					+ "]", ex);
		}

		if (sr != null && sr.getResultList() != null) {
			java.util.Vector vector= (java.util.Vector) sr.getResultList();
			for(int i=0;i<vector.size();i++){
				DocumentSearchResultItem resultItem=(DocumentSearchResultItem)vector.get(i);
				if("STOCK_STATEMENT".equals(resultItem.getStatementType())){
					if(!stockDocChkList.containsKey(resultItem.getItemCode())){
					stockDocChkList.put(resultItem.getItemCode(), resultItem.getItemCode());
					}
				}
			}
			
		}
		
		


		resultMap.put("stockDocChkList", stockDocChkList);
		resultMap.put("deferCreditApproverList", getAllDeferCreditApprover());
		resultMap.put("waiverCreditApproverList", getAllWaiveCreditApprover());
		ICheckListDAO checkListDAO=CheckListDAOFactory.getCheckListDAO();
		ICollateralTrxValue itrxValue = new OBCollateralTrxValue();
		HashMap docCodeWithStocks = new HashMap();		
		ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		long id =0;
		 
		 Iterator iterator=	stockDocChkList.values().iterator();
		 while(iterator.hasNext()){
			 String docCode2=(String)iterator.next();
			 id = checkListDAO.getCollateralIdMap(custOB.getCMSLegalEntity().getLEReference(), docCode2);	
			 if(id!=0){
				 break;
			 }
		 }
		
		try {
			if(id!=0){
			itrxValue = CollateralProxyFactory.getProxy().getCollateralTrxValue(ctx, id);
			}
		} catch (CollateralException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(itrxValue.getCollateral() instanceof  IGeneralCharge)
		{
	    IGeneralCharge newCollateral = (IGeneralCharge) itrxValue.getStagingCollateral();
			IGeneralChargeDetails[] existingGeneralChargeDetails = newCollateral.getGeneralChargeDetails();					
			IGeneralChargeDetails existingChargeDetails;					
			if(existingGeneralChargeDetails!=null){
				for (int i = 0; i < existingGeneralChargeDetails.length; i++) {
					 existingChargeDetails = existingGeneralChargeDetails[i];
					 
					if(existingChargeDetails!=null && null != existingChargeDetails.getDocCode() && !existingChargeDetails.getDocCode().equals("")){								
						docCodeWithStocks.put(existingChargeDetails.getDocCode(),existingChargeDetails.getDocCode());

						
					}
				}
			}
	    
		}
		resultMap.put("docCodeWithStocks",docCodeWithStocks);
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
	
	

}
