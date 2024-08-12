package com.integrosys.cms.ui.feed.gold.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CurrencyList;
import com.integrosys.cms.ui.feed.gold.GoldCommand;
import com.integrosys.component.commondata.app.CommonDataSingleton;

public class PrepareGoldItemCommand extends GoldCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "currencyLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "currencyValues", "java.util.Collection", REQUEST_SCOPE },
				{ "goldGradeLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "goldGradeValues", "java.util.Collection", REQUEST_SCOPE },
				{ "goldUOMLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "goldUOMValues", "java.util.Collection", REQUEST_SCOPE },  };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IGoldFeedGroupTrxValue value = (IGoldFeedGroupTrxValue) map.get("goldFeedGroupTrxValue");

			IGoldFeedEntry[] entriesArr = value.getStagingGoldFeedGroup().getFeedEntries();

			CurrencyList currencyList = CurrencyList.getInstance();
			List currencyLabels = (ArrayList) new ArrayList(currencyList.getCurrencyLabels()).clone();
			List currencyValues = (ArrayList) new ArrayList(currencyList.getCountryValues()).clone();

			currencyLabels.add(0, "Please Select");
			currencyValues.add(0, "");

			Map goldGradeCodeMap = new HashMap();
			Map goldUOMCodeMap = new HashMap();
			goldGradeCodeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.CATEGORY_GOLD_GRADE);
			goldUOMCodeMap = CommonDataSingleton.getCodeCategoryValueLabelMap(CategoryCodeConstant.CATEGORY_GOLD_UOM);
			/**
			 * Sort the map based on key
			 */
			goldGradeCodeMap = new TreeMap(goldGradeCodeMap);
			goldUOMCodeMap = new TreeMap(goldUOMCodeMap);

			Set goldGradeCode = goldGradeCodeMap.keySet();
			Collection goldGradeDesc = goldGradeCodeMap.values();

			/**
			 * set the goldGradeValues and goldGradeLabels to the same value, 
			 * because we want to use the key as the label instead the value 
			 */
			List goldGradeValues = new ArrayList(goldGradeCode);
			List goldGradeLabels = new ArrayList(goldGradeDesc);

			goldGradeValues.add(0, "");
			goldGradeLabels.add(0, "Please Select");
			
			Set goldUOMCode = goldUOMCodeMap.keySet();
			Collection goldUOMDesc = goldUOMCodeMap.values();
			
			List goldUOMValues = new ArrayList(goldUOMCode);
			List goldUOMLabels = new ArrayList(goldUOMDesc);

			goldUOMValues.add(0, "");
			goldUOMLabels.add(0, "Please Select");

			resultMap.put("currencyLabels", currencyLabels);
			resultMap.put("currencyValues", currencyValues);
			resultMap.put("goldGradeLabels", goldGradeLabels);
			resultMap.put("goldGradeValues", goldGradeValues);
			resultMap.put("goldUOMLabels", goldUOMLabels);
			resultMap.put("goldUOMValues", goldUOMValues);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
