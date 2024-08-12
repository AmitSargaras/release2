package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IDisbursement;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 3, 2007 Time: 10:41:32 AM To
 * change this template use File | Settings | File Templates.
 */
public class PrepareDisbursementCommand extends AbstractCommand {

	public PrepareDisbursementCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "orig_country", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "purposeLabels", "java.util.List", REQUEST_SCOPE },
				{ "purposeValues", "java.util.List", REQUEST_SCOPE },
				{ "currencyLabels", "java.util.List", REQUEST_SCOPE },
				{ "currencyValues", "java.util.List", REQUEST_SCOPE },
				{ "orig_country", "java.lang.String", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PrepareDisbursementCommand doExecute()");
		String event = (String) map.get("event");
		DefaultLogger.debug(this, "event=" + event);

		try {
			String countryCode = String.valueOf(map.get("orig_country")).trim();
			CommonCodeList purposeList = CommonCodeList.getInstance(countryCode, ICMSUIConstant.DISBURSE_PURPOSE, true);
			ArrayList purposeLabels = new ArrayList(purposeList.getCommonCodeLabels());
			ArrayList purposeValues = new ArrayList(purposeList.getCommonCodeValues());

			// Filter during maker create
			if (DisbursementAction.EVENT_MAKER_PREPARE_CREATE.equals(event)
					|| DisbursementAction.EVENT_CREATE.equals(event)) {
				IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
				if ((trxValue != null) && (trxValue.getStagingBridgingLoan() != null)
						&& (trxValue.getStagingBridgingLoan().getDisbursementList() != null)) {
					IDisbursement[] objDisbursementList = trxValue.getStagingBridgingLoan().getDisbursementList();
					HashMap purposeMap = new HashMap();
					for (int i = 0; i < objDisbursementList.length; i++) {
						if (!objDisbursementList[i].getIsDeletedInd()) { // not
																			// include
																			// deleted
							purposeMap.put(objDisbursementList[i].getPurpose(), objDisbursementList[i].getPurpose());
						}
					}
					HashMap hm = filterList(purposeValues, purposeLabels, purposeMap);
					purposeLabels = (ArrayList) hm.get("labels");
					purposeValues = (ArrayList) hm.get("values");
				}
			}
			resultMap.put("purposeLabels", purposeLabels);
			resultMap.put("purposeValues", purposeValues);

			CurrencyList currencyList = CurrencyList.getInstance();
			resultMap.put("currencyLabels", currencyList.getCurrencyLabels());
			resultMap.put("currencyValues", currencyList.getCountryValues());

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
		}
		catch (Exception e) {
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