/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.tradingbook.bus.OBGMRADeal;
import com.integrosys.cms.app.tradingbook.bus.TradingBookException;
import com.integrosys.cms.app.tradingbook.proxy.ITradingBookProxy;
import com.integrosys.cms.app.tradingbook.proxy.TradingBookProxyFactory;
import com.integrosys.cms.app.tradingbook.trx.IGMRADealTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Maker to reedit the GMRA Deal value after
 * rejected by checker Description: command that get the value that being
 * rejected by checker from database to let the user to reedit the value
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

public class GMRADealMakerReadRejectedDetailCmd extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "InitialGMRADeal", "com.integrosys.cms.app.tradingbook.bus.OBGMRADeal", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "GMRADealTrxValue", "com.integrosys.cms.app.tradingbook.trx.OBGMRADealTrxValue", SERVICE_SCOPE },
				{ "productCode", "java.util.Collection", REQUEST_SCOPE },
				{ "productLabel", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
				{ "dealBranchListID", "java.util.Collection", REQUEST_SCOPE },
				{ "dealBranchListValue", "java.util.Collection", REQUEST_SCOPE },
				{ "InitialGMRADeal", "java.util.list", FORM_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here get the value where checker rejected
	 * from database for Interest Rate is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event = (String) map.get("event");
		String trxId = (String) map.get("TrxId");
		trxId = trxId.trim();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");

		try {
			ITradingBookProxy proxy = TradingBookProxyFactory.getProxy();
			// Get Trx By TrxID
			IGMRADealTrxValue gMRADealTrxVal = proxy.getGMRADealTrxValueByTrxID(trxContext, trxId);

			if ("refresh_maker_edit_dealdetails_reject".equals(event)) {
				OBGMRADeal obGMRADeal = (OBGMRADeal) map.get("InitialGMRADeal");
				gMRADealTrxVal.setStagingGMRADeal(obGMRADeal);
			}
			System.out.println("gMRADealTrxVal.getStagingGMRADeal() = " + gMRADealTrxVal.getStagingGMRADeal());

			// if current status is other than ACTIVE & REJECTED, then show
			// workInProgress.
			// i.e. allow edit only if status is either ACTIVE or REJECTED
			if ((!gMRADealTrxVal.getStatus().equals(ICMSConstant.STATE_ACTIVE))
					&& (!gMRADealTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE))
					&& (!gMRADealTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_UPDATE))
					&& (!gMRADealTrxVal.getStatus().equals(ICMSConstant.STATE_REJECTED_DELETE))

			) {
				resultMap.put("wip", "wip");
				resultMap.put("InitialGMRADeal", gMRADealTrxVal.getStagingGMRADeal());
			}
			else {
				resultMap.put("GMRADealTrxValue", gMRADealTrxVal);
			}

			Collection productListID = new ArrayList();
			Collection productListValue = new ArrayList();
			HashMap productListMap = new HashMap();
			HashMap tempProductListMap = new HashMap();

			productListMap = CommonDataSingleton.getCodeCategoryValueLabelMap("FAC_TYPE_TRADE", "GMRA");
			Collection keyvalue1 = productListMap.keySet();
			Iterator itr1 = keyvalue1.iterator();

			while (itr1.hasNext()) {
				String key = (String) itr1.next();
				tempProductListMap.put(productListMap.get(key), key);
				productListValue.add(productListMap.get(key));
			}

			String[] tempProductListValue = (String[]) productListValue.toArray(new String[0]);
			Arrays.sort(tempProductListValue);
			productListValue = Arrays.asList(tempProductListValue);

			for (int i = 0; i < tempProductListValue.length; i++) {
				productListID.add(tempProductListMap.get(tempProductListValue[i]));
			}

			resultMap.put("productCode", productListID);
			resultMap.put("productLabel", productListValue);

			CommonCodeList commonCode = CommonCodeList.getInstance(
					gMRADealTrxVal.getStagingGMRADeal().getDealCountry(), ICMSConstant.CATEGORY_CODE_BKGLOC, true);
			resultMap.put("dealBranchListID", commonCode.getCommonCodeValues());
			resultMap.put("dealBranchListValue", commonCode.getCommonCodeLabels());

			resultMap.put("timefrequency.labels", CommonDataSingleton
					.getCodeCategoryLabels(GMRADealValHelper.TIME_FREQUENCY_CODE));
			resultMap.put("timefrequency.values", CommonDataSingleton
					.getCodeCategoryValues(GMRADealValHelper.TIME_FREQUENCY_CODE));
			resultMap.put("InitialGMRADeal", gMRADealTrxVal.getStagingGMRADeal());

			// set FrequencyUnit List
			resultMap.put("timefrequencies.map", GMRADealValHelper.buildTimeFrequencyMap());

		}
		catch (TradingBookException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
