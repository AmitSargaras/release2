/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/stock/list/StockListMapper.java,v 1.25 2005/08/05 10:14:23 hshii Exp $
 */
package com.integrosys.cms.ui.feed.stock.list;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.bus.stock.OBStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.OBStockFeedGroup;
import com.integrosys.cms.app.feed.trx.stock.IStockFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.stock.OBStockFeedGroupTrxValue;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.25 $
 * @since $Date: 2005/08/05 10:14:23 $ Tag: $Name: $
 */
public class StockListMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapOBToForm(...).");
		Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		StockListForm form = (StockListForm) aForm;
		String event = form.getEvent();

		int offset = ((Integer) hashMap.get("offset")).intValue();
		int length = ((Integer) hashMap.get("length")).intValue();
		DefaultLogger.debug(this, "event is " + event);

		// Need to readjust the form value for proper display.
		if (StockListAction.EVENT_READ.equals(event) || StockListAction.EVENT_REMOVE.equals(event)
				|| StockListAction.EVENT_PAGINATE.equals(event) || StockListAction.EVENT_LIST_STAGING.equals(event)
				|| StockListAction.EVENT_READ_MAKER_EDIT.equals(event) 
				|| StockListAction.EVENT_PREPARE.equals(event) || StockListAction.EVENT_CHECKER_PREPARE.equals(event)) {

			IStockFeedGroupTrxValue value = (IStockFeedGroupTrxValue) object;
			IStockFeedGroup group = value.getStagingStockFeedGroup();

			extractForDisplay(offset, length, form, group, locale);

			return form;

		}

		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...).");
		Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		StockListForm form = (StockListForm) aForm;
		String event = form.getEvent();

		if (StockListAction.EVENT_REMOVE.equals(event)) {
			// Will return a List of feedGroup OB, String[].

			int offset = ((Integer) hashMap.get("offset")).intValue();

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			
			String[] expiryDateArray = form.getExpiryDate();

			IStockFeedEntry[] feedEntriesArr = new IStockFeedEntry[updatedUnitPricesArr.length];

			String[] chkBlackListedsArr = form.getChkBlackListeds();
			String[] chkSuspendedsArr = form.getChkSuspendeds();
			int[] blackListeds = new int[0];
			int[] suspendeds = new int[0];
			if (chkBlackListedsArr != null) {
				DefaultLogger.debug(this, "chkBlackListedsArr is not null.");
				blackListeds = new int[chkBlackListedsArr.length];
				for (int i = 0; i < blackListeds.length; i++) {
					blackListeds[i] = Integer.parseInt(chkBlackListedsArr[i]);
				}
			}
			if (chkSuspendedsArr != null) {
				DefaultLogger.debug(this, "chkSuspendedsArr is not null.");
				suspendeds = new int[chkSuspendedsArr.length];
				for (int i = 0; i < suspendeds.length; i++) {
					suspendeds[i] = Integer.parseInt(chkSuspendedsArr[i]);
				}
			}
			Date stageDate;
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				feedEntriesArr[i] = new OBStockFeedEntry();
				try {
					feedEntriesArr[i].setUnitPrice(Double.parseDouble(updatedUnitPricesArr[i]));
					
					if ((expiryDateArray != null) && (expiryDateArray.length > 0)) {
						if (!AbstractCommonMapper.isEmptyOrNull(expiryDateArray[i])) {
							Date returnDate = DateUtil.convertDate(locale, expiryDateArray[i]);
							feedEntriesArr[i].setExpiryDate(returnDate);
						}
						else {
							feedEntriesArr[i].setExpiryDate(null);
						}
					}

					if (inArray(offset + i, blackListeds)) {
						feedEntriesArr[i].setBlackListed("Y");
					}
					else {
						feedEntriesArr[i].setBlackListed("N");
					}
					if (inArray(offset + i, suspendeds)) {
						feedEntriesArr[i].setSuspended("Y");
					}
					else {
						feedEntriesArr[i].setSuspended("N");
					}
				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
					feedEntriesArr[i].setUnitPrice(0);
					feedEntriesArr[i].setExpiryDate(null);
				}
				feedEntriesArr[i].setTicker(form.getTickerCodes()[i]);
			}
			IStockFeedGroup feedGroup = new OBStockFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			String[] chkDeletes = form.getChkDeletes();

			List returnList = new ArrayList();
			returnList.add(feedGroup);
			returnList.add(chkDeletes);
			return returnList;

		}
		else if (StockListAction.EVENT_ADD.equals(event)) {
			// Will return a feedGroup OB

			int offset = ((Integer) hashMap.get("offset")).intValue();

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			String[] expiryDateArray = form.getExpiryDate();
			IStockFeedEntry[] feedEntriesArr = null;

			if (updatedUnitPricesArr != null) {
				String[] chkBlackListedsArr = form.getChkBlackListeds();
				String[] chkSuspendedsArr = form.getChkSuspendeds();
				int[] blackListeds = new int[0];
				int[] suspendeds = new int[0];
				if (chkBlackListedsArr != null) {
					DefaultLogger.debug(this, "chkBlackListedsArr is not null.");
					blackListeds = new int[chkBlackListedsArr.length];
					for (int i = 0; i < blackListeds.length; i++) {
						blackListeds[i] = Integer.parseInt(chkBlackListedsArr[i]);
					}
				}
				if (chkSuspendedsArr != null) {
					DefaultLogger.debug(this, "chkSuspendedsArr is not null.");
					suspendeds = new int[chkSuspendedsArr.length];
					for (int i = 0; i < suspendeds.length; i++) {
						suspendeds[i] = Integer.parseInt(chkSuspendedsArr[i]);
					}
				}

				feedEntriesArr = new IStockFeedEntry[updatedUnitPricesArr.length];
				for (int i = 0; i < updatedUnitPricesArr.length; i++) {
					feedEntriesArr[i] = new OBStockFeedEntry();
					try {
						feedEntriesArr[i].setUnitPrice(Double.parseDouble(updatedUnitPricesArr[i]));

						if ((expiryDateArray != null) && (expiryDateArray.length > 0)) {
							if (!AbstractCommonMapper.isEmptyOrNull(expiryDateArray[i])) {
								Date returnDate = DateUtil.convertDate(locale, expiryDateArray[i]);
								feedEntriesArr[i].setExpiryDate(returnDate);
							}
							else {
								feedEntriesArr[i].setExpiryDate(null);
							}
						}
						
						if (inArray(offset + i, blackListeds)) {
							feedEntriesArr[i].setBlackListed("Y");
						}
						else {
							feedEntriesArr[i].setBlackListed("N");
						}
						if (inArray(offset + i, suspendeds)) {
							feedEntriesArr[i].setSuspended("Y");
						}
						else {
							feedEntriesArr[i].setSuspended("N");
						}
					}
					catch (Exception e) {
						DefaultLogger.warn(this, "value is not double-parseable.");
						feedEntriesArr[i].setUnitPrice(0);
						feedEntriesArr[i].setExpiryDate(null);
					}
					feedEntriesArr[i].setTicker(form.getTickerCodes()[i]);
				}

			}
			else {
				feedEntriesArr = new IStockFeedEntry[0];
			}

			IStockFeedGroup feedGroup = new OBStockFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			return feedGroup;

		}
		else if (StockListAction.EVENT_SAVE.equals(event) || StockListAction.EVENT_PAGINATE.equals(event)
				|| StockListAction.EVENT_SUBMIT.equals(event)) {
			// Will return a List of offset as String, feed group OB.

			int offset = ((Integer) hashMap.get("offset")).intValue();

			// Note that the offset is at the first record of the
			// CURRENT range.

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			String[] scriptCodeArr = form.getTickerCodes();
			String[] scriptNameArr = form.getScriptNameArr();
			String[] scriptValueArr = form.getScriptValueArr();
			String[] exchangeNameArr = form.getExchangeNameArr();
			String[] faceValueArr = form.getFaceValueArr();
			
			String[] expiryDateArray = form.getExpiryDate();

			if (updatedUnitPricesArr == null) {
				updatedUnitPricesArr = new String[0];
			}

			String[] chkBlackListedsArr = form.getChkBlackListeds();
			String[] chkSuspendedsArr = form.getChkSuspendeds();
			int[] blackListeds = new int[0];
			int[] suspendeds = new int[0];
			if (chkBlackListedsArr != null) {
				DefaultLogger.debug(this, "chkBlackListedsArr is not null.");
				blackListeds = new int[chkBlackListedsArr.length];
				for (int i = 0; i < blackListeds.length; i++) {
					blackListeds[i] = Integer.parseInt(chkBlackListedsArr[i]);
				}
			}
			if (chkSuspendedsArr != null) {
				DefaultLogger.debug(this, "chkSuspendedsArr is not null.");
				suspendeds = new int[chkSuspendedsArr.length];
				for (int i = 0; i < suspendeds.length; i++) {
					suspendeds[i] = Integer.parseInt(chkSuspendedsArr[i]);
				}
			}

			IStockFeedEntry[] feedEntriesArr = new IStockFeedEntry[updatedUnitPricesArr.length];
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				feedEntriesArr[i] = new OBStockFeedEntry();
				feedEntriesArr[i].setUnitPrice(Double.parseDouble(updatedUnitPricesArr[i]));
				feedEntriesArr[i].setScriptCode(scriptCodeArr[i]);
				feedEntriesArr[i].setScriptName(scriptNameArr[i]);
				feedEntriesArr[i].setScriptValue(Double.parseDouble(scriptValueArr[i]));
				feedEntriesArr[i].setExchange(exchangeNameArr[i]);
				feedEntriesArr[i].setFaceValue(Double.parseDouble(faceValueArr[i]));
				
				if ((expiryDateArray != null) && (expiryDateArray.length > 0)) {
					if (!AbstractCommonMapper.isEmptyOrNull(expiryDateArray[i])) {
						Date returnDate = DateUtil.convertDate(locale, expiryDateArray[i]);
						feedEntriesArr[i].setExpiryDate(returnDate);
					}
					else {
						feedEntriesArr[i].setExpiryDate(null);
					}
				}

				if (inArray(offset + i, blackListeds)) {
					feedEntriesArr[i].setBlackListed("Y");
				}
				else {
					feedEntriesArr[i].setBlackListed("N");
				}
				if (inArray(offset + i, suspendeds)) {
					feedEntriesArr[i].setSuspended("Y");
				}
				else {
					feedEntriesArr[i].setSuspended("N");
				}
				feedEntriesArr[i].setTicker(form.getTickerCodes()[i]);
			}
			IStockFeedGroup feedGroup = new OBStockFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			List returnList = new ArrayList();

			returnList.add(form.getTargetOffset());

			returnList.add(feedGroup);

			return returnList;

		}
		else if (StockListAction.EVENT_READ.equals(event)
				|| StockListAction.EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)
				|| StockListAction.EVENT_READ_MAKER_CLOSE.equals(event)
				|| StockListAction.EVENT_READ_MAKER_EDIT.equals(event) || StockListAction.EVENT_VIEW.equals(event)
				|| StockListAction.EVENT_PREPARE.equals(event)
				|| StockListAction.EVENT_CHECKER_PREPARE.equals(event)) {
			// Will return a trx value.

			IStockFeedGroupTrxValue value = new OBStockFeedGroupTrxValue();
			IStockFeedGroup group = new OBStockFeedGroup();
			group.setSubType(form.getSubType());
			group.setStockType(form.getStockType());
			value.setStockFeedGroup(group);
			value.setTransactionID(form.getTrxId());
			return value;
		}
		else if (StockListAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)
				|| StockListAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {
			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}
		else if (StockListAction.EVENT_LIST_VIEW.equals(event) || StockListAction.EVENT_LIST_READ.equals(event)) {
			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}

		return null;
	}

	public static boolean inArray(int target, int[] arr) {

		if (arr == null) {
			return false;
		}

		for (int i = 0; i < arr.length; i++) {
			if (target == arr[i]) {
				return true;
			}
		}

		return false;
	}

	public static Date compareDate(Locale locale, Date dateOrigin, String dateStage) {

		Date returnDate = DateUtil.convertDate(locale, dateStage);

		if (dateOrigin != null) {
			String originalDate = DateUtil.formatDate(locale, dateOrigin);
//			System.out.println("originalDate = " + originalDate);
//			System.out.println("dateOrigin = " + dateOrigin);
			if (originalDate.equals(dateStage)) {
				returnDate = dateOrigin;
			}
		}
		return returnDate;
	}

	private void extractForDisplay(int offset, int length, StockListForm form, IStockFeedGroup group, Locale locale) {

		if (group == null) {
			// Do nothing when there is no group.
			return;
		}

		IStockFeedEntry[] entries = group.getFeedEntries();

		DefaultLogger.debug(this, "number of feed entries = " + entries.length);

		int limit = offset + length;
		if (limit > entries.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > entries.length "
					+ entries.length);

			limit = entries.length;
		}

		String[] updatedUnitPricesArr = new String[limit - offset];
		String[] expiryDateArray = new String[limit - offset];
		String[] exchangeNameArray = new String[limit - offset];

		List blackListedList = new ArrayList();
		List suspendedList = new ArrayList();

		for (int i = offset; i < limit; i++) {

			updatedUnitPricesArr[i - offset] = String.valueOf(entries[i].getUnitPrice());
			expiryDateArray[i - offset] = DateUtil.formatDate(locale, entries[i].getExpiryDate());
			exchangeNameArray[i - offset] = String.valueOf(entries[i].getStockExchangeName());
			
			if ((entries[i].getBlackListed() != null) && entries[i].getBlackListed().equals("Y")) {
				blackListedList.add(new Integer(i));
			}
			if ((entries[i].getSuspended() != null) && entries[i].getSuspended().equals("Y")) {
				suspendedList.add(new Integer(i));
			}
		}

		String[] blackListedArr = new String[blackListedList.size()];
		String[] suspendedArr = new String[suspendedList.size()];
		String[] exchsngeNameArr = new String[suspendedList.size()];

		for (int i = 0; i < blackListedArr.length; i++) {
			blackListedArr[i] = String.valueOf(((Integer) blackListedList.get(i)).intValue());
		}

		for (int i = 0; i < suspendedArr.length; i++) {
			suspendedArr[i] = String.valueOf(((Integer) suspendedList.get(i)).intValue());
		}
		form.setUpdatedUnitPrices(updatedUnitPricesArr);
		form.setExpiryDate(expiryDateArray);
		form.setChkBlackListeds(blackListedArr);
		form.setChkSuspendeds(suspendedArr);
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(StockListMapper.class.getName(), "offset " + offset + " + length " + length
					+ " >= maxSize " + maxSize);
			if (maxSize == 0) {
				// Do not reduce offset further.
				adjustedOffset = 0;
			}
			else if (offset == maxSize) {
				// Reduce.
				adjustedOffset = offset - length;
			}
			else {
				// Rely on "/" = Integer division.
				// Go to the start of the last strip.
				adjustedOffset = maxSize / length * length;
			}
			DefaultLogger.debug(StockListMapper.class.getName(), "adjusted offset = " + adjustedOffset);
		}

		return adjustedOffset;
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the mapFormToOB method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] { { "offset", "java.lang.Integer", SERVICE_SCOPE },
				{ "length", "java.lang.Integer", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE } };
	}
}
