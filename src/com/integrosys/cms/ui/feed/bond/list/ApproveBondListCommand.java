/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/ApproveBondListCommand.java,v 1.9 2005/08/30 09:49:57 hshii Exp $
 */
package com.integrosys.cms.ui.feed.bond.list;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.bond.BondCommand;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.9 $
 * @since $Date: 2005/08/30 09:49:57 $ Tag: $Name: $
 */
public class ApproveBondListCommand extends BondCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "bondFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IBondFeedGroupTrxValue value = (IBondFeedGroupTrxValue) map.get("bondFeedGroupTrxValue");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if (trxContext == null) {
				DefaultLogger.debug(this, "trxContext obtained from map is null.");
			}

			IBondFeedGroup group = value.getBondFeedGroup();
			IBondFeedGroup stageGroup = value.getStagingBondFeedGroup();
			IBondFeedEntry[] actualEntries = null;
			IBondFeedEntry[] stageEntries = null;
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

			
			//stageEntries = updateLastUpdateDate(actualEntries, stageEntries);
			stageGroup.setFeedEntries(stageEntries);
			value.setStagingBondFeedGroup(stageGroup);


			value = getBondFeedProxy().checkerApproveBondFeedGroup(trxContext, value);

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

	/*private IBondFeedEntry[] updateLastUpdateDate(IBondFeedEntry[] actualEntries, IBondFeedEntry[] stageEntries) {
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
				actualMap.put(String.valueOf(actualEntries[i].getBondFeedEntryRef()), actualEntries[i]);
			}
			for (int i = 0; i < stageEntries.length; i++) {
				IBondFeedEntry actual = (IBondFeedEntry) actualMap.get(String.valueOf(stageEntries[i]
						.getBondFeedEntryRef()));
				if ((actual == null) || (actual.getUnitPrice() != stageEntries[i].getUnitPrice())
						|| isRatingChange(actual.getRating(), stageEntries[i].getRating())) {
					stageEntries[i].setLastUpdatedDate(new Date());
				}
			}
		}
		return stageEntries;
	}*/

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
