package com.integrosys.cms.ui.feed.gold.list;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedEntry;
import com.integrosys.cms.app.feed.bus.gold.IGoldFeedGroup;
import com.integrosys.cms.app.feed.bus.gold.OBGoldFeedEntry;
import com.integrosys.cms.app.feed.bus.gold.OBGoldFeedGroup;
import com.integrosys.cms.app.feed.trx.gold.IGoldFeedGroupTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

public class GoldListMapper extends AbstractCommonMapper {

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "entering mapFormToOB(...).");

		GoldListForm form = (GoldListForm) aForm;
		String event = form.getEvent();

		if (GoldListAction.EVENT_REMOVE.equals(event)) {
			// Will return a List of feedGroup OB, String[].

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			IGoldFeedEntry[] feedEntriesArr = new IGoldFeedEntry[updatedUnitPricesArr.length];
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				feedEntriesArr[i] = new OBGoldFeedEntry();
				try {
					feedEntriesArr[i].setUnitPrice(new BigDecimal(updatedUnitPricesArr[i]));
				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
					feedEntriesArr[i].setUnitPrice(new BigDecimal(0));
				}
				feedEntriesArr[i].setCurrencyCode(form.getCurrencyCodes()[i]);
			}
			IGoldFeedGroup feedGroup = new OBGoldFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			String[] chkDeletes = form.getChkDeletes();

			List returnList = new ArrayList();
			returnList.add(feedGroup);
			returnList.add(chkDeletes);
			return returnList;

		}
		else if (GoldListAction.EVENT_ADD.equals(event)) {
			// Will return a feedGroup OB,

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			String[] goldGrade = form.getGoldGrade();
			String[] goldUOM = form.getUnitMeasurement();
			String[] currencyCode = form.getCurrencyCodes();
			IGoldFeedEntry[] feedEntriesArr = null;

			if (updatedUnitPricesArr != null) {
				feedEntriesArr = new IGoldFeedEntry[updatedUnitPricesArr.length];
				for (int i = 0; i < updatedUnitPricesArr.length; i++) {
					feedEntriesArr[i] = new OBGoldFeedEntry();
					try {
						feedEntriesArr[i].setUnitPrice(new BigDecimal(updatedUnitPricesArr[i]));
						feedEntriesArr[i].setGoldGradeNum(goldGrade[i]);
						feedEntriesArr[i].setUnitMeasurementNum(goldUOM[i]);
						feedEntriesArr[i].setCurrencyCode(currencyCode[i]);
					}
					catch (Exception e) {
						DefaultLogger.warn(this, "value is not double-parseable.");
						feedEntriesArr[i].setUnitPrice(new BigDecimal(0));
					}

//					feedEntriesArr[i].setCurrencyCode(buyCurrencies[i]);
				}
			}
			else {
				feedEntriesArr = new IGoldFeedEntry[0];
			}

			IGoldFeedGroup feedGroup = new OBGoldFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			return feedGroup;

		}
		else if (GoldListAction.EVENT_SAVE.equals(event) || GoldListAction.EVENT_PAGINATE.equals(event)
				|| GoldListAction.EVENT_SUBMIT.equals(event)) {
			// Will return a List of target offset as String,feed group OB.

			// Note that the offset is at the first record of the
			// CURRENT range.

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			if (updatedUnitPricesArr == null) {
				updatedUnitPricesArr = new String[0];
			}

			String[] buyCurrencies = (form.getCurrencyCodes() == null) ? new String[0] : form.getCurrencyCodes();
			String[] unitMeasurement = (form.getUnitMeasurement() == null) ? new String[0] : form.getUnitMeasurement();
			String[] goldGrade = (form.getGoldGrade() == null) ? new String[0] : form.getGoldGrade();

			IGoldFeedEntry[] feedEntriesArr = new IGoldFeedEntry[updatedUnitPricesArr.length];
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				feedEntriesArr[i] = new OBGoldFeedEntry();
				feedEntriesArr[i].setCurrencyCode(buyCurrencies[i]);
				feedEntriesArr[i].setUnitPrice(new BigDecimal(updatedUnitPricesArr[i]));
				feedEntriesArr[i].setGoldGradeNum(goldGrade[i]);
				feedEntriesArr[i].setUnitMeasurementNum(unitMeasurement[i]);
			}
			IGoldFeedGroup feedGroup = new OBGoldFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			List returnList = new ArrayList();
			returnList.add(form.getTargetOffset());
			returnList.add(feedGroup);

			return returnList;

		}
		else if (GoldListAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)
				|| GoldListAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));

		}
		else if (GoldListAction.EVENT_LIST_VIEW.equals(event) || GoldListAction.EVENT_LIST_READ.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}

		return null;
	}

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {
		DefaultLogger.debug(this, "entering mapOBToForm(...).");

		GoldListForm form = (GoldListForm) aForm;

		String event = form.getEvent();

		int offset = ((Integer) hashMap.get("offset")).intValue();
		int length = ((Integer) hashMap.get("length")).intValue();
		Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		DefaultLogger.debug(this, "event is " + event);

		// Need to readjust the form value for proper display.
		if (GoldListAction.EVENT_READ.equals(event) || GoldListAction.EVENT_REMOVE.equals(event)
				|| GoldListAction.EVENT_PAGINATE.equals(event) || GoldListAction.EVENT_LIST_STAGING.equals(event)
				|| GoldListAction.EVENT_READ_MAKER_EDIT.equals(event) || GoldListAction.EVENT_PREPARE.equals(event)) {

			IGoldFeedGroupTrxValue value = (IGoldFeedGroupTrxValue) object;
			IGoldFeedGroup group = value.getStagingGoldFeedGroup();

			extractForDisplay(offset, length, form, group, locale);
		}

		if (GoldListAction.EVENT_REMOVE.equals(event)) {

			form.setChkDeletes(new String[0]);

		}

		return form;
	}

	private void extractForDisplay(int offset, int length, GoldListForm form, IGoldFeedGroup group, Locale locale) {

		if (group == null) {
			// Do nothing when there is no group.
			return;
		}

		IGoldFeedEntry[] entries = group.getFeedEntries();

		DefaultLogger.debug(this, "number of feed entries = " + entries.length);

		int limit = offset + length;
		if (limit > entries.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > entries.length "
					+ entries.length);

			limit = entries.length;
		}

		String[] updatedUnitPricesArr = new String[limit - offset];
		// NumberFormat nf = NumberFormat.getInstance(locale);
		// nf.setGroupingUsed(false);
		for (int i = offset; i < limit; i++) {
			updatedUnitPricesArr[i - offset] = UIUtil.formatBigDecimalToStr(entries[i].getUnitPrice());
		}

		form.setUpdatedUnitPrices(updatedUnitPricesArr);
	}

	/**
	 * Helper method to return true if integer is one of the array elements in
	 * the integer array.
	 * 
	 * @param target
	 * @param arr
	 * @return
	 */
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

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(GoldListMapper.class.getName(), "offset " + offset + " + length " + length
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
			DefaultLogger.debug(GoldListMapper.class.getName(), "adjusted offset = " + adjustedOffset);
		}

		return adjustedOffset;
	}
}
