package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.13 $
 * @since $Date: 2006/10/10 08:12:40 $ Tag: $Name: $
 */
public class SubmitOtherReceiptCommandIP extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SubmitOtherReceiptCommandIP() {
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
				{"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},
				{ "checkListForm", "com.integrosys.cms.app.checklist.bus.OBCheckList" ,FORM_SCOPE},
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
		try {
			ICheckListTrxValue checkListTrxVal = (ICheckListTrxValue) map.get("checkListTrxVal");
			
			if(null!=checkListTrxVal){
			ICheckList checkList = checkListTrxVal.getCheckList();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			List insuranceList=(ArrayList)map.get("insuranceList");

			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			// DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>Updating" +
			// checkList);
			
			ICheckListTrxValue checkListTrxValOld = proxy.getCheckListByTrxID(checkListTrxVal.getTransactionID());
			
			ArrayList resultListStage = new ArrayList();
			//ArrayList resultListActual = new ArrayList();
			
			ICheckListItem[] checkListItemList = checkList.getCheckListItemList();
			
			if(null!=checkListItemList && checkListItemList.length >0){
			
				for(int j=0;j<checkListItemList.length;j++){
					
					ICheckListItem iChecklistItem=checkListItemList[j];
						if(null!=insuranceList){
							for(int i=0;i<insuranceList.size();i++){
								IInsurancePolicy actualObj=(IInsurancePolicy)insuranceList.get(i);
								
								
								if(null!=iChecklistItem.getInsuranceId()){
								if(String.valueOf(actualObj.getRefID()).equals(iChecklistItem.getInsuranceId())){
								if(ICMSConstant.STATE_ITEM_RECEIVED.equals(actualObj.getInsuranceStatus())){
									iChecklistItem.setReceivedDate(actualObj.getReceivedDate());
									iChecklistItem.setDocDate(actualObj.getEffectiveDate());
									iChecklistItem.setCurrency(actualObj.getCurrencyCode());
									iChecklistItem.setHdfcAmt(String.valueOf(actualObj.getInsuredAmount().getAmount()));
									iChecklistItem.setDocAmt(String.valueOf(actualObj.getInsurableAmount().getAmount()));
									iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_RECEIVED);
									iChecklistItem.setExpiryDate(actualObj.getExpiryDate());
									
								}else if(ICMSConstant.STATE_ITEM_DEFERRED.equals(actualObj.getInsuranceStatus())){
									iChecklistItem.setOriginalTargetDate(actualObj.getOriginalTargetDate());
									//iChecklistItem.setExpiryDate(actualObj.getOriginalTargetDate());
									iChecklistItem.setDeferExpiryDate(actualObj.getDateDeferred());
									iChecklistItem.setCreditApprover(actualObj.getCreditApprover());
									iChecklistItem.setExpectedReturnDate(actualObj.getNextDueDate());
									iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_DEFERRED);
									
									if(iChecklistItem.getDeferCount() == null){
										iChecklistItem.setDeferCount("1");
									}
									
								}else if(ICMSConstant.STATE_ITEM_WAIVED.equals(actualObj.getInsuranceStatus())){
									iChecklistItem.setWaivedDate(actualObj.getWaivedDate());
									iChecklistItem.setCreditApprover(actualObj.getCreditApprover());
									iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_WAIVED);
								}else if(ICMSConstant.STATE_ITEM_AWAITING.equals(actualObj.getInsuranceStatus())){
									iChecklistItem.setOriginalTargetDate(actualObj.getOriginalTargetDate());
									//iChecklistItem.setExpectedReturnDate(actualObj.getOriginalTargetDate());
									iChecklistItem.setItemStatus(ICMSConstant.STATE_ITEM_AWAITING);
								}
								//Add the updated insurance other cehcklist item
								resultListStage.add(iChecklistItem);
								
								break;
								}else if(i==(insuranceList.size()-1)){
									resultListStage.add(iChecklistItem);
									break;
								}
							}else{
								//Add the non insurance other cehcklist item
								resultListStage.add(iChecklistItem);
								break;
							}
					}
				}else{
					resultListStage.add(iChecklistItem);
				}
			
		//	checkListTrxVal.getCheckList().setCheckListItemList((ICheckListItem[]) resultListActual.toArray(new ICheckListItem[resultListActual.size()]));
			
			//checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkListTrxVal.getStagingCheckList());
			
			
			}
			
			
			checkListTrxVal.getStagingCheckList().setCheckListItemList((ICheckListItem[]) resultListStage.toArray(new ICheckListItem[resultListStage.size()]));
			checkList=checkListTrxVal.getStagingCheckList();
				
			if (checkListTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED)
					|| checkListTrxVal.getStatus().equals(ICMSConstant.STATE_OFFICER_REJECTED)) {
				checkListTrxVal = proxy.makerEditRejectedCheckListReceiptTrx(ctx, checkListTrxVal, checkList);
			}
			else  {
				checkListTrxVal = proxy.makerUpdateCheckListReceipt(ctx, checkListTrxVal, checkList);
			}
			}
			}
			resultMap.put("request.ITrxValue", checkListTrxVal);

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
