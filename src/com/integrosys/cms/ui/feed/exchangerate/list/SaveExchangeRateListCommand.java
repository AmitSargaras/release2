/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/SaveExchangeRateListCommand.java,v 1.22 2005/08/30 09:50:38 hshii Exp $
 */

package com.integrosys.cms.ui.feed.exchangerate.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.forex.ForexReplicationUtils;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.proxy.forex.IForexFeedProxy;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2005/08/30 09:50:38 $ Tag: $Name: $
 */
public class SaveExchangeRateListCommand extends ExchangeRateCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of target offset and
				// feed group OB.
				{ ExchangeRateListForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce all the feed entries to aid in display. For save and
				// list.
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ ExchangeRateListForm.MAPPER, "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(ExchangeRateListForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IForexFeedGroup inputGroup = (IForexFeedGroup) inputList.get(1);
			IForexFeedEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IForexFeedGroupTrxValue value = (IForexFeedGroupTrxValue) map.get("forexFeedGroupTrxValue");
			IForexFeedGroup group = value.getStagingForexFeedGroup();

			IForexFeedGroup replicatedGroup = ForexReplicationUtils.replicateForexFeedGroupForCreateStagingCopy(group);

			IForexFeedEntry[] entriesArr = replicatedGroup.getFeedEntries();

			for (int i = 0; i < inputEntriesArr.length; i++) {
				for (int j = 0; j < entriesArr.length; j++) {
					if (entriesArr[j].getBuyCurrency().equals(inputEntriesArr[i].getBuyCurrency())) {
						entriesArr[j].setBuyRate(inputEntriesArr[i].getBuyRate());
						entriesArr[j].setSellRate(inputEntriesArr[i].getSellRate());
					}
				}
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingForexFeedGroup(replicatedGroup);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			IForexFeedProxy proxy = getForexFeedProxy();

			IForexFeedGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = proxy.makerUpdateRejectedForexFeedGroup(trxContext, value);
			}
			else {
				resultValue = proxy.makerUpdateForexFeedGroup(trxContext, value, value.getStagingForexFeedGroup());
			}

			DefaultLogger.debug(this, "after: " + resultValue.getVersionTime());

			entriesArr = resultValue.getStagingForexFeedGroup().getFeedEntries();

			// Sort the array.

			if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IForexFeedEntry entry1 = (IForexFeedEntry) a;
						IForexFeedEntry entry2 = (IForexFeedEntry) b;
						if (entry1.getBuyCurrency() == null) {
							entry1.setBuyCurrency("");
						}
						if (entry2.getBuyCurrency() == null) {
							entry2.setBuyCurrency("");
						}
						return entry1.getBuyCurrency().compareTo(entry2.getBuyCurrency());
					}
				});
			}
			resultValue.getStagingForexFeedGroup().setFeedEntries(entriesArr);

			int entriesLength = 0;
			if (entriesArr != null) {
				entriesLength = entriesArr.length;
			}

			targetOffset = ExchangeRateListMapper.adjustOffset(targetOffset, length, entriesLength);

			resultMap.put("request.ITrxValue", resultValue);
			resultMap.put("forexFeedGroupTrxValue", resultValue);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(ExchangeRateListForm.MAPPER, resultValue);

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
