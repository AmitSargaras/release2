package com.integrosys.cms.ui.feed.gold.list;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.gold.GoldReplicationUtils;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.proxy.gold.IGoldFeedProxy;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.gold.GoldCommand;

public class SaveGoldListCommand extends GoldCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the input fields as a List of target offset and
				// feed group OB.
				{ GoldListForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				// Produce all the feed entries to aid in display. For save and
				// list.
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ GoldListForm.MAPPER, "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(GoldListForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IGoldFeedGroup inputGroup = (IGoldFeedGroup) inputList.get(1);
			IGoldFeedEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IGoldFeedGroupTrxValue value = (IGoldFeedGroupTrxValue) map.get("goldFeedGroupTrxValue");
			IGoldFeedGroup group = value.getStagingGoldFeedGroup();

			IGoldFeedGroup replicatedGroup = GoldReplicationUtils.replicateGoldFeedGroupForCreateStagingCopy(group);

			IGoldFeedEntry[] entriesArr = replicatedGroup.getFeedEntries();
			for (int i = 0; i < inputEntriesArr.length; i++) {
				for (int j = 0; j < entriesArr.length; j++) {
					if (entriesArr[j].getGoldGradeNum().equals(inputEntriesArr[i].getGoldGradeNum())
							&& entriesArr[j].getUnitMeasurementNum().equals(inputEntriesArr[i].getUnitMeasurementNum())
							&& entriesArr[j].getCurrencyCode().equals(inputEntriesArr[i].getCurrencyCode())) {
						entriesArr[j].setUnitPrice(inputEntriesArr[i].getUnitPrice());
					}
				}
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingGoldFeedGroup(replicatedGroup);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			IGoldFeedProxy proxy = getGoldFeedProxy();

			IGoldFeedGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = proxy.makerUpdateRejectedGoldFeedGroup(trxContext, value);
			}
			else {
				resultValue = proxy.makerUpdateGoldFeedGroup(trxContext, value, value.getStagingGoldFeedGroup());
			}

			DefaultLogger.debug(this, "after: " + resultValue.getVersionTime());

			entriesArr = resultValue.getStagingGoldFeedGroup().getFeedEntries();

			resultValue.getStagingGoldFeedGroup().setFeedEntries(entriesArr);

			int entriesLength = 0;
			if (entriesArr != null) {
				entriesLength = entriesArr.length;
			}

			targetOffset = GoldListMapper.adjustOffset(targetOffset, length, entriesLength);

			resultMap.put("request.ITrxValue", resultValue);
			resultMap.put("goldFeedGroupTrxValue", resultValue);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(GoldListForm.MAPPER, resultValue);
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
