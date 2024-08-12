package com.integrosys.cms.ui.feed.gold.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.proxy.gold.IGoldFeedProxy;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.gold.GoldCommand;

public class ReadGoldListCommand extends GoldCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] { 
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE }, 
				// Produce the offset.
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, 
				// Produce the length.
				{ "length", "java.util.Integer", SERVICE_SCOPE }, 
				// To populate the form.
				{ GoldListForm.MAPPER, "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", FORM_SCOPE }};
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IGoldFeedGroupTrxValue trxValue = null;

		try {
			IGoldFeedProxy feedProxy = getGoldFeedProxy();
			trxValue = feedProxy.getGoldFeedGroup();

			DefaultLogger.debug(this, "after getting gold feed group from proxy.");

			String event = (String) map.get("event");

			// If this is the very first online read, then there will be
			// no staging records. So copy the actual records as staging
			// records.
			if (trxValue.getStagingGoldFeedGroup() == null) {
				trxValue.setStagingGoldFeedGroup((IGoldFeedGroup) CommonUtil.deepClone(trxValue.getGoldFeedGroup()));
			}
			if (trxValue.getStatus().equals(ICMSConstant.STATE_ACTIVE)
					|| trxValue.getStatus().equals(ICMSConstant.STATE_ND) || GoldListAction.EVENT_READ.equals(event)) {
				// Set the staging to be the same as actual.
				trxValue.setStagingGoldFeedGroup((IGoldFeedGroup) CommonUtil.deepClone(trxValue.getGoldFeedGroup()));
			}


			// Sort the staging entries.
			IGoldFeedEntry[] entriesArr = trxValue.getStagingGoldFeedGroup().getFeedEntries();

			if (trxValue.getGoldFeedGroup().getFeedEntries() == null) {
				trxValue.getGoldFeedGroup().setFeedEntries(new IGoldFeedEntry[0]);
			}

			// Sort the array.
			if (entriesArr == null) {
				entriesArr = new IGoldFeedEntry[0];
				trxValue.getStagingGoldFeedGroup().setFeedEntries(entriesArr);
			}
			else if (entriesArr.length != 0) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IGoldFeedEntry entry1 = (IGoldFeedEntry) a;
						IGoldFeedEntry entry2 = (IGoldFeedEntry) b;
						if (entry1.getGoldGradeNum() == null) {
							entry1.setGoldGradeNum("");
						}
						if (entry2.getGoldGradeNum() == null) {
							entry2.setGoldGradeNum("");
						}
						return entry1.getGoldGradeNum().compareTo(entry2.getGoldGradeNum());
					}
				});
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		resultMap.put("goldFeedGroupTrxValue", trxValue);
		resultMap.put("offset", new Integer(0));
		resultMap.put("length", new Integer(10));
		resultMap.put(GoldListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
