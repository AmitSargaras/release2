/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/ListExchangeRateListCommand.java,v 1.3 2003/08/11 12:06:50 btchng Exp $
 */
package com.integrosys.cms.ui.feed.exchangerate.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.exchangerate.ExchangeRateCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/11 12:06:50 $ Tag: $Name: $
 */
public class ListExchangeRateListCommand extends ExchangeRateCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {// Produce all the feed entries.
				{ "forexFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						SERVICE_SCOPE }, // To populate the form.
				{ ExchangeRateListForm.MAPPER, "com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue",
						FORM_SCOPE } });
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		DefaultLogger.debug(this, "entering doExecute(...)");

		// Pass through to the mapper to prepare for display.

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IForexFeedGroupTrxValue trxValue = (IForexFeedGroupTrxValue) hashMap.get("forexFeedGroupTrxValue");

		resultMap.put("forexFeedGroupTrxValue", trxValue);
		resultMap.put(ExchangeRateListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
