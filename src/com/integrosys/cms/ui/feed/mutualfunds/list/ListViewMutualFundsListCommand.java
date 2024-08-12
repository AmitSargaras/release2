/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/ListViewBondListCommand.java,v 1.1 2003/09/05 09:45:27 btchng Exp $
 */
package com.integrosys.cms.ui.feed.mutualfunds.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.ui.feed.mutualfunds.MutualFundsCommand;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class ListViewMutualFundsListCommand extends MutualFundsCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {// Consume the input target offset.
				{ MutualFundsListForm.MAPPER, "java.lang.Integer", FORM_SCOPE },
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
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

			int targetOffset = ((Integer) map.get(MutualFundsListForm.MAPPER)).intValue();

			// Session-scoped trx value.
			IMutualFundsFeedGroupTrxValue value = (IMutualFundsFeedGroupTrxValue) map.get("mutualFundsFeedGroupTrxValue");
			IMutualFundsFeedGroup group = value.getStagingMutualFundsFeedGroup();
			IMutualFundsFeedEntry[] entriesArr = group.getFeedEntries();

			targetOffset = MutualFundsListMapper.adjustOffset(targetOffset, length, entriesArr.length);

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
