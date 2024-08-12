package com.integrosys.cms.ui.feed.mutualfunds.list;

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
public class SubmitMutualFundsListCommand extends MutualFundsCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ MutualFundsListForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "mutualFundsFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue",
				REQUEST_SCOPE } };
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(MutualFundsListForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();

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
			
			// Update using the input unit prices.
			if (inputEntriesArr != null) {
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
							
							entriesArr[j].setCurrentNAV(inputEntriesArr[i].getCurrentNAV());
							entriesArr[j].setSchemeName(inputEntriesArr[i].getSchemeName());
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
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingMutualFundsFeedGroup(replicatedGroup);

			IMutualFundsFeedGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = getMutualFundsFeedProxy().makerSubmitRejectedMutualFundsFeedGroup(trxContext, value);
			}
			else {
				resultValue = getMutualFundsFeedProxy().makerSubmitMutualFundsFeedGroup(trxContext, value, value.getStagingMutualFundsFeedGroup());
			}
			resultMap.put("request.ITrxValue", resultValue);

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
