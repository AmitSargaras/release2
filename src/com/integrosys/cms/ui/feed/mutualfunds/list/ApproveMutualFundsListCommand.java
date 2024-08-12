/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/ApproveBondListCommand.java,v 1.9 2005/08/30 09:49:57 hshii Exp $
 */
package com.integrosys.cms.ui.feed.mutualfunds.list;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.mutualfunds.MutualFundsCommand;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class ApproveMutualFundsListCommand extends MutualFundsCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IMutualFundsFeedGroupTrxValue value = (IMutualFundsFeedGroupTrxValue) map.get("mutualFundsFeedGroupTrxValue");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			IMutualFundsFeedGroup group = value.getMutualFundsFeedGroup();
			IMutualFundsFeedGroup stageGroup = value.getStagingMutualFundsFeedGroup();
			IMutualFundsFeedEntry[] actualEntries = null;
			IMutualFundsFeedEntry[] stageEntries = null;
			if (group != null) {
				actualEntries = group.getFeedEntries();
			}
			if (stageGroup != null) {
				stageEntries = stageGroup.getFeedEntries();
			}
			
			if(group.getFeedEntries()==null){
				DefaultLogger.debug(this, "///////////////////////////group is null //////////");
			}else{
				DefaultLogger.debug(this, "///////////////////////////group is not null //////////");
			}

			
			stageEntries = updateLastUpdateDate(actualEntries, stageEntries);
			stageGroup.setFeedEntries(stageEntries);
			value.setStagingMutualFundsFeedGroup(stageGroup);


			value = getMutualFundsFeedProxy().checkerApproveMutualFundsFeedGroup(trxContext, value);

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

	private IMutualFundsFeedEntry[] updateLastUpdateDate(IMutualFundsFeedEntry[] actualEntries, IMutualFundsFeedEntry[] stageEntries) {
		if ((actualEntries == null) || (actualEntries.length == 0)) {
			if (stageEntries != null) {
				for (int i = 0; i < stageEntries.length; i++) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		else if ((actualEntries != null) && (stageEntries != null)) {
			HashMap actualMap = new HashMap();
			for (int i = 0; i < actualEntries.length; i++) {
				actualMap.put(String.valueOf(actualEntries[i].getMutualFundsFeedEntryRef()), actualEntries[i]);
			}
			for (int i = 0; i < stageEntries.length; i++) {
				IMutualFundsFeedEntry actual = (IMutualFundsFeedEntry) actualMap.get(String.valueOf(stageEntries[i]
						.getMutualFundsFeedEntryRef()));
				if ((actual == null) || (actual.getCurrentNAV() != stageEntries[i].getCurrentNAV())) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		return stageEntries;
	}

	private boolean isRatingChange(String actualRating, String stageRating) {
		if (((actualRating == null) || (actualRating.trim().length() == 0)) && (stageRating != null)
				&& (stageRating.trim().length() > 0)) {
			return true;
		}
		if ((actualRating != null) && (actualRating.trim().length() > 0)
				&& ((stageRating == null) || (stageRating.trim().length() == 0))) {
			return true;
		}
		if ((actualRating != null) && (stageRating != null) && !actualRating.equals(stageRating)) {
			return true;
		}
		return false;
	}
}
