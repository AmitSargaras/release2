/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/gold/list/CloseGoldListCommand.java,v 1.9 2005/01/12 06:39:41 hshii Exp $
 */
package com.integrosys.cms.ui.feed.gold.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.proxy.gold.IGoldFeedProxy;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.gold.GoldCommand;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/01/12 06:39:41 $ Tag: $Name: $
 */
public class CloseGoldListCommand extends GoldCommand {

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

		DefaultLogger.debug(this, "Map is " + map);

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

			// String remarks = (String)map.get(GoldListForm.MAPPER);
			// value.setRemarks(remarks);

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			IGoldFeedProxy proxy = getGoldFeedProxy();
			if (value.getStatus().equals(ICMSConstant.STATE_DRAFT)) {
				value = proxy.makerCloseDraftGoldFeedGroup(trxContext, value);
			}
			else {
				value = proxy.makerCloseRejectedGoldFeedGroup(trxContext, value);
			}

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
}
