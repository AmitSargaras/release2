/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genddn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CCCheckListSummary;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.CollateralCheckListSummary;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.creditApproval.bus.ICreditApproval;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.ddn.proxy.DDNProxyManagerFactory;
import com.integrosys.cms.app.ddn.proxy.IDDNProxyManager;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/06/09 02:35:58 $ Tag: $Name: $
 */
public class PrepareDeferredListCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareDeferredListCommand() {
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
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
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
		return (new String[][] { { "deferredList", "java.util.List", SERVICE_SCOPE },
				{ "deferredApprovalList", "java.util.List", SERVICE_SCOPE },
				{ "deferCreditApproverList", "java.util.List", SERVICE_SCOPE },
				{ "waiverCreditApproverList", "java.util.List", SERVICE_SCOPE },
                { "deferredMap", "java.util.HashMap", SERVICE_SCOPE},
                { "facilityMap", "java.util.HashMap", SERVICE_SCOPE},        
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
		HashMap facilityMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			// DefaultLogger.debug(this,"Limit profile "+limit);
			ICMSCustomer cust = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			// DefaultLogger.debug(this,"Customer object"+cust);
			IDDNProxyManager proxy = DDNProxyManagerFactory.getDDNProxyManager();
			OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
			ICheckListProxyManager proxy3 = CheckListProxyManagerFactory.getCheckListProxyManager();
			HashMap checkListMap = proxy3.getAllCollateralCheckListSummaryList(theOBTrxContext, limit.getLimitProfileID());
			List deferredList = new ArrayList();
            HashMap deferredMap = new HashMap();
			List deferredApprovalList = new ArrayList();

			
			 CheckListSearchResult camCheckList= proxy3.getCAMCheckListByCategoryAndProfileID("CAM",limitProfileID);
			 if(camCheckList!=null){
				 
				 ICheckListTrxValue checkListTrxVal = proxy3.getCheckList( camCheckList.getCheckListID());
					ICheckList checkList = checkListTrxVal.getCheckList();
//					OBCMSTrxHistoryLog[] commentList = (OBCMSTrxHistoryLog[]) checkListTrxVal
//							.getTransactionHistoryCollection().toArray(new OBCMSTrxHistoryLog[0]);
					ICheckListItem[] items = checkList.getCheckListItemList();
					if (items != null) {
						for (int xx = 0; xx < items.length; xx++) {
							if (ICMSConstant.STATE_ITEM_DEFERRED.equals(items[xx].getItemStatus())) {
								deferredList.add(items[xx]);

                             //let do some simple mapping here
                             //Key : ChecklistID     Value : ArrayList [deferred checklistitem]
                             ArrayList list = null;
                             if (deferredMap.containsKey( camCheckList.getCheckListID() + "")) {
                                 list = (ArrayList)deferredMap.get(camCheckList.getCheckListID() + "");
                             } else {
                                 list = new ArrayList();
                             }
                             list.add(items[xx]);
                             deferredMap.put(camCheckList.getCheckListID() + "", list);

//								if (commentList != null) {
//									deferredApprovalList.add(commentList[commentList.length - 1]);
//								}
//								else {
//									deferredApprovalList.add((new OBCMSTrxHistoryLog()));
//								}
								DefaultLogger.debug(this, "~~~~~adding item:" + items[xx].getDocRef() + ", "
										+ items[xx].getItemCode());
							}
						}
					}
			 }
			 
			 
			   	ILimit[] OB=limit.getLimits();
	          	for(int i=0;i<OB.length;i++){
	              ILimit obLimit= OB[i];
	              long limitId= obLimit.getLimitID();
	           
	             
	            	  CheckListSearchResult facilitycheckList=proxy3.getCheckListByCollateralID(limitId);
	            	  if(facilitycheckList!=null){
	     				 
	     				 ICheckListTrxValue checkListTrxVal = proxy3.getCheckList( facilitycheckList.getCheckListID());
	     					ICheckList checkList = checkListTrxVal.getCheckList();
//	     					OBCMSTrxHistoryLog[] commentList = (OBCMSTrxHistoryLog[]) checkListTrxVal.getTransactionHistoryCollection().toArray(new OBCMSTrxHistoryLog[0]);
	     					ICheckListItem[] items = checkList.getCheckListItemList();
	     					facilityMap.put(String.valueOf(checkList.getCheckListID()), obLimit);
	     					if (items != null) {
	     						for (int xx = 0; xx < items.length; xx++) {
	     							if (ICMSConstant.STATE_ITEM_DEFERRED.equals(items[xx].getItemStatus())) {
	     								deferredList.add(items[xx]);

	                                  //let do some simple mapping here
	                                  //Key : ChecklistID     Value : ArrayList [deferred checklistitem]
	                                  ArrayList list = null;
	                                  if (deferredMap.containsKey( facilitycheckList.getCheckListID() + "")) {
	                                      list = (ArrayList)deferredMap.get(facilitycheckList.getCheckListID() + "");
	                                  } else {
	                                      list = new ArrayList();
	                                  }
	                                  list.add(items[xx]);
	                                  deferredMap.put(facilitycheckList.getCheckListID() + "", list);

//	     								if (commentList != null) {
//	     									deferredApprovalList.add(commentList[commentList.length - 1]);
//	     								}
//	     								else {
//	     									deferredApprovalList.add((new OBCMSTrxHistoryLog()));
//	     								}
	     								DefaultLogger.debug(this, "~~~~~adding item:" + items[xx].getDocRef() + ", "
	     										+ items[xx].getItemCode());
	     							}
	     						}
	     					}
	     			 }
	     			 
				
	          	}
            /**
             * two validation process involve
             * 1. if none of the checklist (CC | Security) being maintain from CSA, no DDN
             *      Checking Mechanism : if it is not maintain, checklistid = -9999999.. & checkliststatus = NEW
             * 2. if no deferred list in checklist (CC | Security) detect, no DDN
             *      Checking Mechanism : check on deferred list instance
             * */
            //boolean genDDN = true;  /*use this flag to control the condition to generate DDN, once hit any condition above, set to false and throw exception*/
            String errorCode = "";

            int totalCheckList = 0;
            int pendingCheckList = 0;

			if (checkListMap != null) {
				CollateralCheckListSummary[] colChkLst = (CollateralCheckListSummary[]) checkListMap.get(ICMSConstant.NORMAL_LIST);
				if (colChkLst != null) {

                    totalCheckList += colChkLst.length;

					for (int x = 0; x < colChkLst.length; x++) {

                        if (ICMSConstant.STATE_CHECKLIST_NEW.equals(colChkLst[x].getCheckListStatus())) {
                            //genDDN = false;
                            //errorCode = ICMSErrorCodes.DDN_NO_CHECKLIST_MAINTAIN;
                            //break;
                            pendingCheckList++;
                            continue;
                        }

						//if (ICMSConstant.STATE_CHECKLIST_DEFERRED.equals(colChkLst[x].getCheckListStatus())) {
                        if (ICMSConstant.STATE_CHECKLIST_IN_PROGRESS.equals(colChkLst[x].getCheckListStatus())) {
							ICheckListTrxValue checkListTrxVal = proxy3.getCheckList(colChkLst[x].getCheckListID());
							ICheckList checkList = checkListTrxVal.getCheckList();
//							OBCMSTrxHistoryLog[] commentList = (OBCMSTrxHistoryLog[]) checkListTrxVal
//									.getTransactionHistoryCollection().toArray(new OBCMSTrxHistoryLog[0]);
							ICheckListItem[] items = checkList.getCheckListItemList();
							if (items != null) {
								for (int xx = 0; xx < items.length; xx++) {
									if (ICMSConstant.STATE_ITEM_DEFERRED.equals(items[xx].getItemStatus())) {
										deferredList.add(items[xx]);

                                        //let do some simple mapping here
                                        //Key : ChecklistID     Value : ArrayList [deferred checklistitem]
                                        ArrayList list = null;
                                        if (deferredMap.containsKey(colChkLst[x].getCheckListID() + "")) {
                                            list = (ArrayList)deferredMap.get(colChkLst[x].getCheckListID() + "");
                                        } else {
                                            list = new ArrayList();
                                        }
                                        list.add(items[xx]);
                                        deferredMap.put(colChkLst[x].getCheckListID() + "", list);

//										if (commentList != null) {
//											deferredApprovalList.add(commentList[commentList.length - 1]);
//										}
//										else {
//											deferredApprovalList.add((new OBCMSTrxHistoryLog()));
//										}
										DefaultLogger.debug(this, "~~~~~adding item:" + items[xx].getDocRef() + ", "
												+ items[xx].getItemCode());
									}
								}
							}
						}
					}
				}
			}

			CCCheckListSummary[] colChkLst = null;
			if (cust.getNonBorrowerInd()) {
				HashMap rmap = null;
				if (limit != null) {
					rmap = proxy3.getAllCCCheckListSummaryListForNonBorrower(theOBTrxContext,
							limit.getLimitProfileID(), cust.getCustomerID());
				}
				else {
					rmap = proxy3.getAllCCCheckListSummaryListForNonBorrower(theOBTrxContext,
							ICMSConstant.LONG_MIN_VALUE, cust.getCustomerID());
				}
				if (rmap != null) {
					colChkLst = (CCCheckListSummary[]) rmap.get(ICMSConstant.NORMAL_LIST);
				}
			}
			else {
//				long limitProfileID = limit.getLimitProfileID();
				HashMap rmap = proxy3.getAllCCCheckListSummaryList(theOBTrxContext, limitProfileID);
				if (rmap != null) {
					colChkLst = (CCCheckListSummary[]) rmap.get(ICMSConstant.NORMAL_LIST);
				}
			}
			if (colChkLst != null) {

                totalCheckList += colChkLst.length;

				for (int x = 0; x < colChkLst.length; x++) {

                    if (ICMSConstant.STATE_CHECKLIST_NEW.equals(colChkLst[x].getCheckListStatus())) {
                        //genDDN = false;
                        //errorCode = ICMSErrorCodes.DDN_NO_CHECKLIST_MAINTAIN;
                        //break;
                        pendingCheckList++;
                        continue;
                    }

					//if (ICMSConstant.STATE_CHECKLIST_DEFERRED.equals(colChkLst[x].getCheckListStatus())) {
                    if (ICMSConstant.STATE_CHECKLIST_IN_PROGRESS.equals(colChkLst[x].getCheckListStatus())) {
						ICheckListTrxValue checkListTrxVal = proxy3.getCheckList(colChkLst[x].getCheckListID());
						ICheckList checkList = checkListTrxVal.getCheckList();
//						OBCMSTrxHistoryLog[] commentList = (OBCMSTrxHistoryLog[]) checkListTrxVal
//								.getTransactionHistoryCollection().toArray(new OBCMSTrxHistoryLog[0]);
						ICheckListItem[] items = checkList.getCheckListItemList();
						if (items != null) {
							for (int xx = 0; xx < items.length; xx++) {
								if (ICMSConstant.STATE_ITEM_DEFERRED.equals(items[xx].getItemStatus())) {
									deferredList.add(items[xx]);

                                    //let do some simple mapping here
                                    //Key : ChecklistID     Value : ArrayList [deferred checklistitem]
                                    ArrayList list = null;
                                    if (deferredMap.containsKey(colChkLst[x].getCheckListID() + "")) {
                                        list = (ArrayList)deferredMap.get(colChkLst[x].getCheckListID() + "");
                                    } else {
                                        list = new ArrayList();
                                    }
                                    list.add(items[xx]);
                                    deferredMap.put(colChkLst[x].getCheckListID() + "", list);

//									if (commentList != null) {
//										deferredApprovalList.add(commentList[commentList.length - 1]);
//									}
//									else {
//										deferredApprovalList.add((new OBCMSTrxHistoryLog()));
//									}
									DefaultLogger.debug(this, "chklist~~~~~adding item:" + items[xx].getDocRef() + ", "
											+ items[xx].getItemCode());
								}
							}
						}
					}
				}
			}

            //if (genDDN && deferredList.size() == 0) {
            //    errorCode = ICMSErrorCodes.DDN_NO_DEFERRED_DOC;
            //    genDDN = false;
            //}
            if (totalCheckList == pendingCheckList) {
                errorCode = ICMSErrorCodes.DDN_NO_CHECKLIST_MAINTAIN;    
            }

            if (deferredList.size() == 0) {
                errorCode = ICMSErrorCodes.DDN_NO_DEFERRED_DOC;    
            }

            if (!errorCode.equals("")) {
                resultMap.put("error", errorCode);
            }

			resultMap.put("deferredList", deferredList);
			resultMap.put("deferredApprovalList", deferredApprovalList);
            resultMap.put("deferredMap", deferredMap);
            resultMap.put("facilityMap", facilityMap);
            resultMap.put("deferCreditApproverList", getAllDeferCreditApprover());
    		resultMap.put("waiverCreditApproverList", getAllWaiveCreditApprover());
			// DefaultLogger.debug(this,"customer details"+custDetail);

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
