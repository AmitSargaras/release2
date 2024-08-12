/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.paripassuupdate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICollateralCheckListOwner;
import com.integrosys.cms.app.checklist.bus.OBCollateralCheckListOwner;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAO;
import com.integrosys.cms.ui.checklist.CheckListHelper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.19 $
 * @since $Date: 2006/10/11 07:26:47 $ Tag: $Name: $
 */
public class PrepareUpdatePPCheckListCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareUpdatePPCheckListCommand() {
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
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE }});
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
				  {"docDueDate","java.lang.Date",REQUEST_SCOPE},
				{ "legalFirmValues", "java.util.Collection", REQUEST_SCOPE },
				{ "deferCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "waiverCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "custTrxDtList", "java.util.HashMap", SERVICE_SCOPE },
				 { "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				{ "docNos", "java.util.ArrayList", SERVICE_SCOPE } });
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
		List transactionHistoryList = null;
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			String tCheckListID = (String) map.get("checkListID");
			long checkListID = Long.parseLong(tCheckListID);
			String secType = (String) map.get("secType");
			String secSubType = (String) map.get("secSubType");
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckList checkList = null;
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			String custCategory = "MAIN_BORROWER";
			String applicationType = "COM";
//			String tCollateralID = "200701010000130";
			long paripassuID = 0L;
			ICollateralCheckListOwner owner = new OBCollateralCheckListOwner(limitProfileID, paripassuID, custCategory,
					applicationType);
			int wip = proxy.allowCheckListTrx(owner);
			DefaultLogger.debug(this, "WORK IN Progress >>>>>>>>>" + wip);
			String event = (String) map.get("event");
			//Date docDueDate=new Date();
			//Date camDate=limit.getCamLoginDate();
			//docDueDate=com.integrosys.cms.app.common.util.CommonUtil.rollUpDateByMonths(camDate, 2);
				
			if ((ICMSConstant.HAS_PENDING_CHECKLIST_TRX == wip) && (event != null) && event.equals("prepare_update")) {
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>in if for WIP " );
				resultMap.put("wip", "wip");
			}
			else {
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is " + checkListID);
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
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is<<<<<<<<<<<<<1>>>>> " + checkListID);
					ICustomerDAO customerDAO = CustomerDAOFactory.getDAO();
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is<<<<<<<<<<<<<2>>>>> " + checkListID);
				    transactionHistoryList = customerDAO.getTransactionHistoryList(checkListTrxVal.getTransactionID());
				    DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is<<<<<<<<<<<<<3>>>>> " + checkListID);
					checkList = checkListTrxVal.getCheckList();
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is<<<<<<<<<<<<<4>>>>> " + checkListID);
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
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is<<<<<<<<<<<<<5>>>>> " + checkListID);
					Collections.sort(theListToBeSorted, multiSort);
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is<<<<<<<<<<<<<6>>>>> " + checkListID);
					troubleTicketActions = new ArrayList(theListToBeSorted);
					
					checkList.setCheckListItemList((ICheckListItem[]) troubleTicketActions.toArray(new ICheckListItem[troubleTicketActions.size()]));
					DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is<<<<<<<<<<<<<7>>>>> " + checkListID);
					
					if( ! (checkListTrxVal.getStatus().trim().equalsIgnoreCase("ACTIVE"))){
						resultMap.put("wip", "wip");
					}
					resultMap.put("ownerObj", checkList.getCheckListOwner());
					resultMap.put("checkListTrxVal", checkListTrxVal);
					//ICheckListItem[] sortedItems = CheckListHelper.sortByParentPrefix(checkList.getCheckListItemList());
					//checkList.setCheckListItemList(sortedItems);
				}
				resultMap.put("transactionHistoryList", transactionHistoryList);
				//resultMap.put("docDueDate", docDueDate);
				resultMap.put("frame", "true");// used to apply frames
                resultMap.put("checkListForm", checkList);
                resultMap.put("checkList", checkList);



                DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is<<<<<<<<<<<<<8>>>>> " + checkListID);
				// CR-380 ends
			}
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
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>Checklist id is<<<<<<<<<<<<<9>>>>> " + checkListID);
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}


		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	


}
