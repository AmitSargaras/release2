/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/propertyindex/list/ListPropertyIndexListCommand.java,v 1.1 2003/08/20 11:00:53 btchng Exp $
 */
package com.integrosys.cms.ui.feed.propertyindex.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 11:00:53 $ Tag: $Name: $
 */
public class ListPropertyIndexListCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ "propertyIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return (new String[][] {// Produce all the feed entries.
				{ "propertyIndexFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", SERVICE_SCOPE }, // To
																															// populate
																															// the
																															// form
																															// .
				{ PropertyIndexListForm.MAPPER,
						"com.integrosys.cms.app.feed.trx.propertyindex.IPropertyIndexFeedGroupTrxValue", FORM_SCOPE } });
	}

	public HashMap doExecute(HashMap hashMap) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		DefaultLogger.debug(this, "entering doExecute(...)");

		// Pass through to the mapper to prepare for display.

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		IPropertyIndexFeedGroupTrxValue trxValue = (IPropertyIndexFeedGroupTrxValue) hashMap
				.get("propertyIndexFeedGroupTrxValue");

		resultMap.put("propertyIndexFeedGroupTrxValue", trxValue);
		resultMap.put(PropertyIndexListForm.MAPPER, trxValue);

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
