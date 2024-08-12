/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/SaveBondListCommand.java,v 1.17 2005/08/30 09:49:57 hshii Exp $
 */

package com.integrosys.cms.ui.feed.mutualfunds.list;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.bus.mutualfunds.MutualFundsReplicationUtils;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.mutualfunds.MutualFundsCommand;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class SaveMutualFundsListCommand extends MutualFundsCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {

				{ MutualFundsListForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE }, { "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ MutualFundsListForm.MAPPER, "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(MutualFundsListForm.MAPPER);

			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			IMutualFundsFeedGroup inputGroup = (IMutualFundsFeedGroup) inputList.get(1);
			IMutualFundsFeedEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IMutualFundsFeedGroupTrxValue value = (IMutualFundsFeedGroupTrxValue) map.get("mutualFundsFeedGroupTrxValue");
			IMutualFundsFeedGroup group = value.getStagingMutualFundsFeedGroup();

			IMutualFundsFeedGroup replicatedGroup = MutualFundsReplicationUtils.replicateMutualFundsFeedGroupForCreateStagingCopy(group);

			IMutualFundsFeedEntry[] entriesArr = replicatedGroup.getFeedEntries();

			String newSchemeName = "";
			String oldSchemeName = "";
			
			String newCurrentNav = "";
			String oldCurrentNav = "";
			
			Date newStartDate;
			Date oldStartDate;
			
			Date newExpiryDate;
			Date oldExpiryDate;

			int intStartDateValue;
			int intExpiryDateValue;
			
			boolean booleanStartDateValue = true;
			boolean booleanExpiryDateValue = true;
			
			for (int i = 0; i < inputEntriesArr.length; i++) {
				for (int j = 0; j < entriesArr.length; j++) {
					if (inputEntriesArr[i].getSchemeCode().equals(entriesArr[j].getSchemeCode())) {
						
						newSchemeName = inputEntriesArr[i].getSchemeName();
						oldSchemeName = entriesArr[j].getSchemeName();
				
						newCurrentNav = String.valueOf(inputEntriesArr[i].getCurrentNAV());
						oldCurrentNav = String.valueOf(entriesArr[j].getCurrentNAV());
						
						newStartDate = inputEntriesArr[i].getStartDate();
						oldStartDate = entriesArr[j].getStartDate();
						
						newExpiryDate = inputEntriesArr[i].getExpiryDate();
						oldExpiryDate = entriesArr[j].getExpiryDate();
						
						booleanStartDateValue = true;
						booleanExpiryDateValue = true;
						
						if( oldStartDate != null && !oldStartDate.equals("") ){
							if( newStartDate !=null && !newStartDate.equals("") ){
								intStartDateValue = newStartDate.compareTo(oldStartDate);
								if( intStartDateValue != 0 )
									booleanStartDateValue = false;
							}
							else{
								booleanStartDateValue = false;
							}
						}
						else{
							if( newStartDate == null || newStartDate.equals("") )
								booleanStartDateValue = true;
							else
								booleanStartDateValue = false;
						}
												
						if( oldExpiryDate != null && !oldExpiryDate.equals("") ){
							if( newExpiryDate !=null && !newExpiryDate.equals("") ){
								intExpiryDateValue = newExpiryDate.compareTo(oldExpiryDate);
								if( intExpiryDateValue != 0 )
									booleanExpiryDateValue = false;
							}
							else{
								booleanExpiryDateValue = false;
							}
						}
						else{
							if( newExpiryDate == null || newExpiryDate.equals("") )
								booleanExpiryDateValue = true;
							else
								booleanExpiryDateValue = false;
						}						
						
						entriesArr[j].setSchemeName(inputEntriesArr[i].getSchemeName());
						entriesArr[j].setCurrentNAV(inputEntriesArr[i].getCurrentNAV());
						entriesArr[j].setSchemeType(inputEntriesArr[i].getSchemeType());
						entriesArr[j].setStartDate(inputEntriesArr[i].getStartDate());
						entriesArr[j].setExpiryDate(inputEntriesArr[i].getExpiryDate());
						
						if( ! ( newSchemeName.equals(oldSchemeName) && newCurrentNav.equals(oldCurrentNav) && booleanStartDateValue && booleanExpiryDateValue ))
								entriesArr[j].setLastUpdatedDate(new Date());
						else
							entriesArr[j].setLastUpdatedDate(inputEntriesArr[i].getLastUpdatedDate());
					}
				}
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingMutualFundsFeedGroup(replicatedGroup);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			IMutualFundsFeedGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = getMutualFundsFeedProxy().makerUpdateRejectedMutualFundsFeedGroup(trxContext, value);
			}
			else {
				resultValue = getMutualFundsFeedProxy().makerUpdateMutualFundsFeedGroup(trxContext, value, value.getStagingMutualFundsFeedGroup());
			}

			DefaultLogger.debug(this, "after: " + resultValue.getVersionTime());

			entriesArr = resultValue.getStagingMutualFundsFeedGroup().getFeedEntries();

			// Sort the array.
			if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IMutualFundsFeedEntry entry1 = (IMutualFundsFeedEntry) a;
						IMutualFundsFeedEntry entry2 = (IMutualFundsFeedEntry) b;
						if (entry1.getSchemeName() == null) {
							entry1.setSchemeName("");
						}
						if (entry2.getSchemeName() == null) {
							entry2.setSchemeName("");
						}
						return entry1.getSchemeName().compareTo(entry2.getSchemeName());
					}
				});
			}
			resultValue.getStagingMutualFundsFeedGroup().setFeedEntries(entriesArr);
			int entriesLength = 0;
			if (entriesArr != null) {
				entriesLength = entriesArr.length;
			}
			targetOffset = MutualFundsListMapper.adjustOffset(targetOffset, length, entriesLength);

			resultMap.put("mutualFundsFeedGroupTrxValue", resultValue);
			resultMap.put("request.ITrxValue", resultValue);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(MutualFundsListForm.MAPPER, resultValue);

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
