/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/unittrust/list/ListViewUnitTrustListCommand.java,v 1.1 2003/09/05 09:45:28 btchng Exp $
 */
package com.integrosys.cms.ui.feed.unittrust.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.unittrust.UnitTrustCommand;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/05 09:45:28 $ Tag: $Name: $
 */
public class ListViewUnitTrustListCommand extends UnitTrustCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {// Consume the input target offset.
				{ UnitTrustListForm.MAPPER, "java.lang.Integer", FORM_SCOPE },
				{ "unitTrustFeedGroupTrxValue",
						"com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "offset", "java.lang.Integer", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = ((Integer) map.get(UnitTrustListForm.MAPPER)).intValue();

			// Session-scoped trx value.
			IUnitTrustFeedGroupTrxValue value = (IUnitTrustFeedGroupTrxValue) map.get("unitTrustFeedGroupTrxValue");
			IUnitTrustFeedGroup group = value.getStagingUnitTrustFeedGroup();
			IUnitTrustFeedEntry[] entriesArr = group.getFeedEntries();

			targetOffset = UnitTrustListMapper.adjustOffset(targetOffset, length, entriesArr.length);

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
