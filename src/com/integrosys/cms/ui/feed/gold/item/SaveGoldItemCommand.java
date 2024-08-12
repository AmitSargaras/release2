package com.integrosys.cms.ui.feed.gold.item;

import java.util.Date;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.gold.GoldCommand;

public class SaveGoldItemCommand extends GoldCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ GoldItemForm.MAPPER, "com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry", FORM_SCOPE },
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		boolean isDataExist = false;

		try {

			IGoldFeedEntry entry = (IGoldFeedEntry) map.get(GoldItemForm.MAPPER);

			IGoldFeedGroupTrxValue value = (IGoldFeedGroupTrxValue) map.get("goldFeedGroupTrxValue");
			IGoldFeedGroup group = value.getStagingGoldFeedGroup();
			IGoldFeedEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr == null) {
				entriesArr = new IGoldFeedEntry[0];
			}
			else {
				for (int i = 0; i < entriesArr.length; i++) {
					if (entriesArr[i].getGoldGradeNum().equals(entry.getGoldGradeNum())) {
					/*if (entriesArr[i].getGoldGradeNum().equals(entry.getGoldGradeNum())
								&& entriesArr[i].getUnitMeasurementNum().equals(entry.getUnitMeasurementNum())
								&& entriesArr[i].getCurrencyCode().equals(entry.getCurrencyCode())) {*/
						isDataExist = true;
//						throw new Exception("Inserted data already exists");
//						exceptionMap.put("objectExist", "Inserted data already exists");
						exceptionMap.put("goldGradeNum", new ActionMessage(FeedConstants.ERROR_DUPLICATE,
						"data"));
					}
				}
			}
			if (!isDataExist && exceptionMap.isEmpty()) {
				// Only proceed if there are no duplicate data and no errors.

				entry.setLastUpdatedDate(new Date());
				entry.setGoldFeedEntryID(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);
				entry.setGoldFeedEntryRef(com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE);

				// Add it as the last item of the array.
				IGoldFeedEntry[] newEntriesArr = new IGoldFeedEntry[entriesArr.length + 1];

				System.arraycopy(entriesArr, 0, newEntriesArr, 0, entriesArr.length);

				newEntriesArr[newEntriesArr.length - 1] = entry;

				group.setFeedEntries(newEntriesArr);

				value.setStagingGoldFeedGroup(group);
			}

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
