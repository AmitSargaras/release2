package com.integrosys.cms.ui.feed.gold.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.gold.GoldCommand;

public class AddGoldListCommand extends GoldCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ GoldListForm.MAPPER, "com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup", FORM_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			IGoldFeedGroup inputFeedGroup = (IGoldFeedGroup) map.get(GoldListForm.MAPPER);
			
			int offset = ((Integer) map.get("offset")).intValue();

			IGoldFeedEntry[] inputEntriesArr = inputFeedGroup.getFeedEntries();

			IGoldFeedGroupTrxValue value = (IGoldFeedGroupTrxValue) map.get("goldFeedGroupTrxValue");
			IGoldFeedGroup group = value.getStagingGoldFeedGroup();
			IGoldFeedEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr != null) {
				for (int i = 0; i < inputEntriesArr.length; i++) {
					for (int j = 0; j < entriesArr.length; j++) {
						if (entriesArr[j].getGoldGradeNum().equals(inputEntriesArr[i].getGoldGradeNum())
								&& entriesArr[j].getUnitMeasurementNum().equals(inputEntriesArr[i].getUnitMeasurementNum())
								&& entriesArr[j].getCurrencyCode().equals(inputEntriesArr[i].getCurrencyCode())) {
							entriesArr[j].setUnitPrice(inputEntriesArr[i].getUnitPrice());
						}
					}
				}
			}

			group.setFeedEntries(entriesArr);
			value.setStagingGoldFeedGroup(group);
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
