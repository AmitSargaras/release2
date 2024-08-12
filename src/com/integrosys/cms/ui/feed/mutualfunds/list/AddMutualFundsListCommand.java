/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/AddBondListCommand.java,v 1.4 2004/03/29 10:45:20 sathish Exp $
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
public class AddMutualFundsListCommand extends MutualFundsCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ MutualFundsListForm.MAPPER, "com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup", FORM_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {
		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {
			IMutualFundsFeedGroup inputFeedGroup = (IMutualFundsFeedGroup) map.get(MutualFundsListForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();

			IMutualFundsFeedEntry[] inputEntriesArr = inputFeedGroup.getFeedEntries();

			IMutualFundsFeedGroupTrxValue value = (IMutualFundsFeedGroupTrxValue) map.get("mutualFundsFeedGroupTrxValue");
			IMutualFundsFeedGroup group = value.getStagingMutualFundsFeedGroup();
			IMutualFundsFeedEntry[] entriesArr = group.getFeedEntries();

			if (entriesArr != null) {
				DefaultLogger.debug(this, "number of existing entries = " + entriesArr.length);

				for (int i = 0; i < inputEntriesArr.length; i++) {
					for (int j = 0; j < entriesArr.length; j++) {
						if (inputEntriesArr[i].getSchemeName().equals(entriesArr[j].getSchemeName())) {
							entriesArr[j].setCurrentNAV(inputEntriesArr[i].getCurrentNAV());
						}
					}

				}
			}

			group.setFeedEntries(entriesArr);
			value.setStagingMutualFundsFeedGroup(group);

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
