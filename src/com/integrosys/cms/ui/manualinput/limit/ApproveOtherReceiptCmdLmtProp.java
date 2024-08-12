package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.checklist.trx.ICheckListTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/11/20 03:04:04 $ Tag: $Name: $
 */
public class ApproveOtherReceiptCmdLmtProp extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ApproveOtherReceiptCmdLmtProp() {
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
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
					GLOBAL_SCOPE },
					{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{"collateralSet","java.util.HashSet",SERVICE_SCOPE},
				{ "collInsurance", "java.util.HashMap", SERVICE_SCOPE },
				{ "insuranceList", "java.util.ArrayList", SERVICE_SCOPE },});
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
		
		 Set collateralSet = (HashSet) map.get("collateralSet");
		 Map collInsurance = (HashMap) map.get("collInsurance");
		 List insuranceList = (ArrayList) map.get("insuranceList");
		 
		 if(collateralSet.size()>0 && insuranceList.size()>0){
		
		try {
			
			
			ICheckListProxyManager proxyManager= CheckListProxyManagerFactory.getCheckListProxyManager();
			ICheckListTrxValue checkListTrxValNew = (ICheckListTrxValue) map.get("checkListTrxVal");
		
			if(null!=checkListTrxValNew){
			ICheckListTrxValue checkListTrxVal = proxyManager.getCheckListByTrxID(checkListTrxValNew.getTransactionID());
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			
			//DefaultLogger.debug(this, "Testing compilation.....................");
			List listDate=new ArrayList();
			DefaultLogger.debug(this, "Testing compilation...........1..........");
			

			ICheckListItem[] iCheckListItems=checkListTrxVal.getStagingCheckList().getCheckListItemList();
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
			
			DefaultLogger.debug(this,"Login id from global scope:"+user.getLoginID());
			
			
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			
			String preStatus = checkListTrxVal.getStatus();
			DefaultLogger.debug(this, "Current status-----" + preStatus);
			
			
				checkListTrxVal = proxy.checkerApproveCheckListReceipt(ctx, checkListTrxVal);
		
			
			proxy.updateSharedChecklistStatus(checkListTrxVal); 
			
			resultMap.put("request.ITrxValue", checkListTrxVal);
			}
			else{
			resultMap.put("request.ITrxValue", checkListTrxValNew);
			}
			
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		 }
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}

