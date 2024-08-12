/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/stock/list/SaveStockListCommand.java,v 1.17 2005/08/30 09:52:01 hshii Exp $
 */

package com.integrosys.cms.ui.feed.stock.list;

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
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.bus.stock.StockReplicationUtils;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.ui.feed.stock.StockCommand;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/08/30 09:52:01 $ Tag: $Name: $
 */
public class SaveStockListCommand extends StockCommand {

	public String[][] getParameterDescriptor() {
		return new String[][] {

				{ StockListForm.MAPPER, "java.util.List", FORM_SCOPE },
				{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue",
						SERVICE_SCOPE }, { "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {

		{ "stockFeedGroupTrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue", SERVICE_SCOPE },
				{ "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ StockListForm.MAPPER, "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue", FORM_SCOPE },
				{ "request.ITrxValue", "com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue", REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			List inputList = (List) map.get(StockListForm.MAPPER);

			// The below offset is the first record of the current range to be
			// saved.

			// int offset = Integer.parseInt((String)inputList.get(0));
			// int length = Integer.parseInt((String)inputList.get(2));
			int offset = ((Integer) map.get("offset")).intValue();
			int length = ((Integer) map.get("length")).intValue();

			int targetOffset = Integer.parseInt((String) inputList.get(0));
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
			
			for (int i = 0; i < inputEntriesArr.length; i++) {
				for (int j = 0; j < entriesArr.length; j++) {
					if (inputEntriesArr[i].getScriptCode().equals(entriesArr[j].getScriptCode())) {
						
						newScriptName = inputEntriesArr[i].getScriptName();
						oldScriptName = entriesArr[j].getScriptName();
				
						newScriptValue = String.valueOf(inputEntriesArr[i].getScriptValue());
						oldScriptValue = String.valueOf(entriesArr[j].getScriptValue());
						
						newFaceValue = String.valueOf(inputEntriesArr[i].getFaceValue());
						oldFaceValue = String.valueOf(entriesArr[j].getFaceValue());
						
						entriesArr[j].setScriptName(inputEntriesArr[i].getScriptName());
						entriesArr[j].setUnitPrice(inputEntriesArr[i].getUnitPrice());
						entriesArr[j].setBlackListed(inputEntriesArr[i].getBlackListed());
						entriesArr[j].setStockExchangeName(inputEntriesArr[i].getExchange());
						entriesArr[j].setSuspended(inputEntriesArr[i].getSuspended());
						entriesArr[j].setScriptValue(inputEntriesArr[i].getScriptValue());
						entriesArr[j].setFaceValue(inputEntriesArr[i].getFaceValue());
						
						if( ! ( newFaceValue.equals(oldFaceValue) && newScriptName.equals(oldScriptName) && newScriptValue.equals(oldScriptValue) ))
							entriesArr[j].setLastUpdatedDate(new Date());
					}
				}
			}

			replicatedGroup.setFeedEntries(entriesArr);
			value.setStagingStockFeedGroup(replicatedGroup);

			DefaultLogger.debug(this, "before: " + value.getVersionTime());

			IStockFeedProxy proxy = getStockFeedProxy();

			IStockFeedGroupTrxValue resultValue = null;
			if (value.getStatus().equals(ICMSConstant.STATE_REJECTED)) {
				resultValue = proxy.makerUpdateRejectedStockFeedGroup(trxContext, value);
			}
			else {
				resultValue = proxy.makerUpdateStockFeedGroup(trxContext, value, value.getStagingStockFeedGroup());
			}

			DefaultLogger.debug(this, "after: " + resultValue.getVersionTime());

			entriesArr = resultValue.getStagingStockFeedGroup().getFeedEntries();

			// Sort the array.
			if ((entriesArr != null) && (entriesArr.length != 0)) {
				Arrays.sort(entriesArr, new Comparator() {
					public int compare(Object a, Object b) {
						IStockFeedEntry entry1 = (IStockFeedEntry) a;
						IStockFeedEntry entry2 = (IStockFeedEntry) b;
						if (entry1.getScriptName() == null) {
							entry1.setScriptName("");
						}
						if (entry2.getScriptName() == null) {
							entry2.setScriptName("");
						}
						return entry1.getScriptName().compareTo(entry2.getScriptName());
					}
				});
			}
			resultValue.getStagingStockFeedGroup().setFeedEntries(entriesArr);
			int entriesLength = 0;
			if (entriesArr != null) {
				entriesLength = entriesArr.length;
			}
			targetOffset = StockListMapper.adjustOffset(targetOffset, length, entriesLength);

			resultMap.put("request.ITrxValue", resultValue);
			resultMap.put("stockFeedGroupTrxValue", resultValue);
			resultMap.put("offset", new Integer(targetOffset));
			resultMap.put(StockListForm.MAPPER, resultValue);

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
