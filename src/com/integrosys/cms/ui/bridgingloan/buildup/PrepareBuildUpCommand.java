package com.integrosys.cms.ui.bridgingloan.buildup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.bridgingloan.bus.IBuildUp;
import com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.common.FrequencyList;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: Apr 25, 2007 Time: 10:44:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class PrepareBuildUpCommand extends AbstractCommand {

	public PrepareBuildUpCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue",
						SERVICE_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "propertyTypeLabels", "java.util.List", REQUEST_SCOPE },
				{ "propertyTypeValues", "java.util.List", REQUEST_SCOPE },
				{ "areaLabels", "java.util.List", REQUEST_SCOPE }, { "areaValues", "java.util.List", REQUEST_SCOPE },
				{ "currencyLabels", "java.util.List", REQUEST_SCOPE },
				{ "currencyValues", "java.util.List", REQUEST_SCOPE },
				{ "tenancyPeriodLabels", "java.util.List", REQUEST_SCOPE },
				{ "tenancyPeriodValues", "java.util.List", REQUEST_SCOPE },
				{ "frequencyLabels", "java.util.List", REQUEST_SCOPE },
				{ "frequencyValues", "java.util.List", REQUEST_SCOPE },
				{ "orig_country", "java.util.List", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug("\n>>>>>>>>>>>>>>>>>>", "Inside PrepareBuildUpCommand doExecute()");
		String event = (String) map.get("event");
		DefaultLogger.debug(this, "event=" + event);

		try {
			String countryCode = (String) map.get("orig_country");
			DefaultLogger.debug(this, "countryCode" + map.get("countryCode"));
			resultMap.put("orig_country", countryCode);

			CommonCodeList propertyTypeList = CommonCodeList.getInstance(null, ICMSUIConstant.PROPERTY_TYPE, true);
			ArrayList propertyTypeLabels = new ArrayList(propertyTypeList.getCommonCodeLabels());
			ArrayList propertyTypeValues = new ArrayList(propertyTypeList.getCommonCodeValues());

			// Filter during maker create
			if (BuildUpAction.EVENT_MAKER_PREPARE_CREATE.equals(event) || BuildUpAction.EVENT_CREATE.equals(event)) {
				IBridgingLoanTrxValue trxValue = (IBridgingLoanTrxValue) map.get("bridgingLoanTrxValue");
				if ((trxValue != null) && (trxValue.getStagingBridgingLoan() != null)
						&& (trxValue.getStagingBridgingLoan().getBuildUpList() != null)) {
					IBuildUp[] objBuildUpList = trxValue.getStagingBridgingLoan().getBuildUpList();
					HashMap propertyTypeMap = new HashMap();
					for (int i = 0; i < objBuildUpList.length; i++) {
						if (!objBuildUpList[i].getIsDeletedInd()) { // not
																	// include
																	// deleted
							propertyTypeMap.put(objBuildUpList[i].getPropertyType(), objBuildUpList[i]
									.getPropertyType());
						}
					}
					HashMap hm = filterList(propertyTypeValues, propertyTypeLabels, propertyTypeMap);
					propertyTypeLabels = (ArrayList) hm.get("labels");
					propertyTypeValues = (ArrayList) hm.get("values");
				}
			}
			resultMap.put("propertyTypeLabels", propertyTypeLabels);
			resultMap.put("propertyTypeValues", propertyTypeValues);

			CommonCodeList areaList = CommonCodeList.getInstance(null, ICMSUIConstant.AREA_UOM, true);
			resultMap.put("areaLabels", areaList.getCommonCodeLabels());
			resultMap.put("areaValues", areaList.getCommonCodeValues());

			CurrencyList currencyList = CurrencyList.getInstance();
			resultMap.put("currencyLabels", currencyList.getCurrencyLabels());
			resultMap.put("currencyValues", currencyList.getCountryValues());

			CommonCodeList tenorPeriodList = CommonCodeList.getInstance(null, ICMSUIConstant.TIME_FREQ, true);
			resultMap.put("tenancyPeriodLabels", tenorPeriodList.getCommonCodeLabels());
			resultMap.put("tenancyPeriodValues", tenorPeriodList.getCommonCodeValues());

			FrequencyList frequencyList = FrequencyList.getInstance();
			resultMap.put("frequencyLabels", frequencyList.getFrequencyLabel());
			resultMap.put("frequencyValues", frequencyList.getFrequencyProperty());

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