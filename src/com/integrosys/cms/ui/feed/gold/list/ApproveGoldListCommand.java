package com.integrosys.cms.ui.feed.gold.list;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.proxy.gold.IGoldFeedProxy;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.gold.GoldCommand;

public class ApproveGoldListCommand extends GoldCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IGoldFeedGroupTrxValue value = (IGoldFeedGroupTrxValue) map.get("goldFeedGroupTrxValue");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);
			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			IGoldFeedGroup actualGroup = value.getGoldFeedGroup();
			IGoldFeedGroup stageGroup = value.getStagingGoldFeedGroup();
			IGoldFeedEntry[] actualEntries = null;
			IGoldFeedEntry[] stageEntries = null;
			if (actualGroup != null) {
				actualEntries = actualGroup.getFeedEntries();
			}
			if (stageGroup != null) {
				stageEntries = stageGroup.getFeedEntries();
			}
			stageEntries = updateEffectiveDate(actualEntries, stageEntries);
			stageGroup.setFeedEntries(stageEntries);
			value.setStagingGoldFeedGroup(stageGroup);

			IGoldFeedProxy proxy = getGoldFeedProxy();
			value = proxy.checkerApproveGoldFeedGroup(trxContext, value);

			resultMap.put("request.ITrxValue", value);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	private IGoldFeedEntry[] updateEffectiveDate(IGoldFeedEntry[] actualEntries, IGoldFeedEntry[] stageEntries) {
		if ((actualEntries == null) || (actualEntries.length == 0)) {
			if (stageEntries != null) {
				for (int i = 0; i < stageEntries.length; i++) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		else if ((actualEntries != null) && (stageEntries != null)) {
			HashMap actualMap = new HashMap();
			for (int i = 0; i < actualEntries.length; i++) {
				actualMap.put(String.valueOf(actualEntries[i].getGoldFeedEntryRef()), actualEntries[i]);
			}
			for (int i = 0; i < stageEntries.length; i++) {
				IGoldFeedEntry actual = (IGoldFeedEntry) actualMap.get(String.valueOf(stageEntries[i]
						.getGoldFeedEntryRef()));
				if ((actual == null) || (actual.getUnitPrice() != stageEntries[i].getUnitPrice())) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		return stageEntries;
	}
}
