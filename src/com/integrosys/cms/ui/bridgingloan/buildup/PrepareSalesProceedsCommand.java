package com.integrosys.cms.ui.bridgingloan.buildup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 30, 2007 Time: 12:39:04 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrepareSalesProceedsCommand extends AbstractCommand {
	public PrepareSalesProceedsCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "salesProceedsLabels", "java.util.List", REQUEST_SCOPE },
				{ "salesProceedsValues", "java.util.List", REQUEST_SCOPE },
				{ "currencyLabels", "java.util.List", REQUEST_SCOPE },
				{ "currencyValues", "java.util.List", REQUEST_SCOPE },
				{ "statusLabels", "java.util.List", REQUEST_SCOPE },
				{ "statusValues", "java.util.List", REQUEST_SCOPE },
				{ "buildUpIndex", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PrepareSalesProceedsCommand doExecute()");
		String event = (String) map.get("event");
		DefaultLogger.debug(this, "event=" + event);

		try {
			CommonCodeList salesProceedsList = CommonCodeList.getInstance(null, ICMSUIConstant.SALES_PROCEED_PUR, true);
			List purposeLabels = new ArrayList(salesProceedsList.getCommonCodeLabels());
			List purposeValues = new ArrayList(salesProceedsList.getCommonCodeValues());

			// Filter during maker create
			// if (SalesProceedsAction.EVENT_MAKER_PREPARE_CREATE.equals(event)
			// || SalesProceedsAction.EVENT_CREATE.equals(event)) {
			// IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue)
			// map.get("bridgingLoanTrxValue");
			// if (trxValue != null && trxValue.getStagingBridgingLoan() !=
			// null) {
			// IBuildUp[] objBuildUpList =
			// trxValue.getStagingBridgingLoan().getBuildUpList();
			// if (objBuildUpList != null) {
			// HashMap purposeMap = new HashMap();
			// for (int i=0; i<objBuildUpList.length; i++) {
			//
			// ISalesProceeds[] objSalesProceedsList =
			// objBuildUpList[i].getSalesProceedsList();
			// if (objSalesProceedsList != null) {
			// for (int j = 0; j < objSalesProceedsList.length; j++) {
			// if (!objSalesProceedsList[j].getIsDeletedInd()) { //not include
			// deleted
			// purposeMap.put(objSalesProceedsList[j].getPurpose(),
			// objSalesProceedsList[j].getPurpose());
			// }
			// }
			// }
			// }
			// HashMap hm = filterList(purposeValues, purposeLabels,
			// purposeMap);
			// purposeLabels = (List) hm.get("labels");
			// purposeValues = (List) hm.get("values");
			// }
			// }
			// }
			resultMap.put("salesProceedsLabels", purposeLabels);
			resultMap.put("salesProceedsValues", purposeValues);

			CurrencyList currencyList = CurrencyList.getInstance();
			resultMap.put("currencyLabels", currencyList.getCurrencyLabels());
			resultMap.put("currencyValues", currencyList.getCountryValues());

			CommonCodeList statusList = CommonCodeList.getInstance(null, ICMSUIConstant.SALES_PROCEED_STAT, true);
			resultMap.put("statusLabels", statusList.getCommonCodeLabels());
			resultMap.put("statusValues", statusList.getCommonCodeValues());

			returnMap.put("buildUpIndex", (String) map.get("buildUpIndex"));
			DefaultLogger.debug(this, "buildUpIndex: " + (String) map.get("buildUpIndex"));
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, e.toString());
			throw new CommandProcessingException(e.toString());
		}
	}

	/**
	 * This method is to filter a list, which will remove previous selected
	 * value
	 * 
	 * @param values - list of value
	 * @param labels - list of labels
	 * @param hm - previous selected value
	 * @return HashMap with the Result
	 */
	public HashMap filterList(List values, List labels, HashMap hm) {
		for (int i = values.size() - 1; i > -1; i--) {
			if (hm.get(values.get(i)) != null) {
				values.remove(i);
				labels.remove(i);
			}
		}
		HashMap returnHm = new HashMap();
		returnHm.put("values", values);
		returnHm.put("labels", labels);
		return returnHm;
	}
}