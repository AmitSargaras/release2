package com.integrosys.cms.ui.feed.stock.list;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.bus.stock.StockReplicationUtils;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.stock.StockCommand;

/**
 * This class implements command
 */
public class SubmitStockListCommand extends StockCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ StockListForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
						SERVICE_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE },
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
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
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

			List inputList = (List) map.get(StockListForm.MAPPER);
			int offset = ((Integer) map.get("offset")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(1));
			// Element at index 2 is the target offset which is not required
			// here.
			IStockFeedGroup inputGroup = (IStockFeedGroup) inputList.get(1);
			IStockFeedEntry[] inputEntriesArr = inputGroup.getFeedEntries();

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			// Session-scoped trx value.
			IStockFeedGroupTrxValue value = (IStockFeedGroupTrxValue) map.get("stockFeedGroupTrxValue");
			IStockFeedGroup group = value.getStagingStockFeedGroup();

			IStockFeedGroup replicatedGroup = (IStockFeedGroup) StockReplicationUtils
					.replicateStockFeedGroupForCreateStagingCopy(group);

			IStockFeedEntry[] entriesArr = replicatedGroup.getFeedEntries();

			String newScriptName = "";
			String oldScriptName = "";
			
			String newScriptValue = "";
			String oldScriptValue = "";
			
			String newFaceValue = "";
			String oldFaceValue = "";
			String stageStockExchangeName="";
			// Update using the input unit prices.
			if (inputEntriesArr != null) {
				for (int i = 0; i < inputEntriesArr.length; i++) {
					for (int j = 0; j < entriesArr.length; j++) {
						if (inputEntriesArr[i].getScriptCode().equals(entriesArr[j].getScriptCode())) {

							newScriptName = inputEntriesArr[i].getScriptName();
							oldScriptName = entriesArr[j].getScriptName();
					
							newScriptValue = String.valueOf(inputEntriesArr[i].getScriptValue());
							oldScriptValue = String.valueOf(entriesArr[j].getScriptValue());
							
							newFaceValue = String.valueOf(inputEntriesArr[i].getFaceValue());
							oldFaceValue = String.valueOf(entriesArr[j].getFaceValue());
							
							entriesArr[j].setUnitPrice(inputEntriesArr[i].getUnitPrice());
							entriesArr[j].setBlackListed(inputEntriesArr[i].getBlackListed());
							entriesArr[j].setSuspended(inputEntriesArr[i].getSuspended());
							
							entriesArr[j].setScriptName(inputEntriesArr[i].getScriptName());
							entriesArr[j].setScriptValue(inputEntriesArr[i].getScriptValue());
							entriesArr[j].setStockExchangeName(inputEntriesArr[i].getExchange());
							entriesArr[j].setFaceValue(inputEntriesArr[i].getFaceValue());
							
							if( ! ( newFaceValue.equals(oldFaceValue) && newScriptName.equals(oldScriptName) && newScriptValue.equals(oldScriptValue) ))
								entriesArr[j].setLastUpdatedDate(new Date());
						}else {
							stageStockExchangeName=inputEntriesArr[0].getExchange();
							entriesArr[j].setStockExchangeName(stageStockExchangeName);
						}
					}
				}
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingStockFeedGroup(replicatedGroup);

			IStockFeedProxy proxy = getStockFeedProxy();

			IStockFeedGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = proxy.makerSubmitRejectedStockFeedGroup(trxContext, value);
			}
			else {
				resultValue = proxy.makerSubmitStockFeedGroup(trxContext, value, value.getStagingStockFeedGroup());
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
