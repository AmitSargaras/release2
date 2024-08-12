package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBCMSCustomer;
import com.integrosys.cms.app.otherbank.bus.OBOtherBank;
import com.integrosys.cms.app.otherbank.proxy.IOtherBankProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * Class created by
 * 
 * @author sandiip.shinde
 * @since 17-03-2011
 * 
 */

public class DisplayBankByNameCommand extends AbstractCommand {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	private IOtherBankProxyManager otherBankProxyManager;

	public IOtherBankProxyManager getOtherBankProxyManager() {
		return otherBankProxyManager;
	}

	public void setOtherBankProxyManager(
			IOtherBankProxyManager otherBankProxyManager) {
		this.otherBankProxyManager = otherBankProxyManager;
	}

	public DisplayBankByNameCommand() {

	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "OBCMSCustomer","com.integrosys.cms.app.customer.bus.ICMSCustomer",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "bankName", "java.lang.String", REQUEST_SCOPE },
				{ "branchName", "java.lang.String", REQUEST_SCOPE },
				{ "branchCode", "java.lang.String", REQUEST_SCOPE },
				{ "bankBranchName", "java.lang.String", REQUEST_SCOPE },
				{ "checkboxIsNBFC", "java.lang.String", REQUEST_SCOPE },
				{ "exceptionalCase", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ,"com.integrosys.cms.app.customer.bus.ICMSCustomer",GLOBAL_SCOPE }
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
				{ "OtherBankList","com.integrosys.cms.app.otherbank.bus.OBOtherBank",SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "OBCMSCustomer","com.integrosys.cms.app.customer.bus.ICMSCustomer",REQUEST_SCOPE },
				{ "bankName", "java.lang.String", REQUEST_SCOPE },
				{ "branchName", "java.lang.String", REQUEST_SCOPE },
				{ "checkboxIsNBFC", "java.lang.String", REQUEST_SCOPE },
				{ "exceptionalCase", "java.lang.String", REQUEST_SCOPE },
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get data from database for Interest
	 * Rate is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		String event = (String) map.get("event");

		DefaultLogger.debug(this,
				"Inside doExecute() ManualInputCreateCustomerCommand " + event);

		String bankCode = null;
		String bankName = (String) map.get("bankName");
		String branchName=(String) map.get("bankBranchName");
		String checkboxIsNBFC=(String) map.get("checkboxIsNBFC"); 
		String exceptionalCase=(String) map.get("exceptionalCase"); 
		String branchCode=(String) map.get("branchCode");
		String startInd = (String) map.get("startIndex");
		ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);

		ICMSCustomer obCustomer = (OBCMSCustomer) map.get("OBCMSCustomer");
		List<OBOtherBank> OtherBankList = getOtherBankProxyManager().getOtherBankList(bankCode, bankName,branchName,branchCode);
		resultMap.put("OtherBankList", OtherBankList);
		resultMap.put("bankName", bankName);
		resultMap.put("branchName", branchName);
		resultMap.put("checkboxIsNBFC", checkboxIsNBFC);
		resultMap.put("exceptionalCase", exceptionalCase);
		resultMap.put("event", event);
		//List resultList = new ArrayList();
		//resultMap.put("otherBranchList", new SearchResult(0, resultList.size(), resultList.size(), resultList));
		
		resultMap.put("OBCMSCustomer", obCustomer);

		DefaultLogger.debug(this, " -------- List Retrieved -----------");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
