package com.integrosys.cms.ui.feed.gold.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.gold.GoldCommand;

public class ListGoldListCommand extends GoldCommand {

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE }, 
				// To populate the form.
				{ GoldListForm.MAPPER, "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", FORM_SCOPE } };
	}

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "goldFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		DefaultLogger.debug(this, "entering doExecute(...)");

		// Pass through to the mapper to prepare for display.

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IGoldFeedGroupTrxValue trxValue = (IGoldFeedGroupTrxValue) hashMap.get("goldFeedGroupTrxValue");

		resultMap.put("goldFeedGroupTrxValue", trxValue);
		resultMap.put(GoldListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
