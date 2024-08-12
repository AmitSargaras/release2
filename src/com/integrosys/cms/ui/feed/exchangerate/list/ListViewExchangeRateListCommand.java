/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/ListViewExchangeRateListCommand.java,v 1.1 2003/09/05 09:45:27 btchng Exp $
 */
package com.integrosys.cms.ui.feed.exchangerate.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/05 09:45:27 $ Tag: $Name: $
 */
public class ListViewExchangeRateListCommand extends ExchangeRateCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {// Consume the input target offset.
				{ ExchangeRateListForm.MAPPER, "java.lang.Integer", FORM_SCOPE },
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ ExchangeRateListForm.MAPPER, "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
			FORM_SCOPE }		
		};
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = ((Integer) map.get(ExchangeRateListForm.MAPPER)).intValue();

			// Session-scoped trx value.
			IForexFeedGroupTrxValue value = (IForexFeedGroupTrxValue) map.get("forexFeedGroupTrxValue");
			IForexFeedGroup group = value.getStagingForexFeedGroup();
			IForexFeedEntry[] entriesArr = group.getFeedEntries();

			targetOffset = ExchangeRateListMapper.adjustOffset(targetOffset, length, entriesArr.length);

			resultMap.put("offset", new Integer(targetOffset));

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
