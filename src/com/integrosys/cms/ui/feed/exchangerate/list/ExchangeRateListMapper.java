/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/exchangerate/list/ExchangeRateListMapper.java,v 1.39 2005/08/05 10:12:13 hshii Exp $
 */
package com.integrosys.cms.ui.feed.exchangerate.list;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.IForexFeedGroup;
import com.integrosys.cms.app.feed.bus.forex.OBForexFeedEntry;
import com.integrosys.cms.app.feed.bus.forex.OBForexFeedGroup;
import com.integrosys.cms.app.feed.trx.forex.IForexFeedGroupTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.39 $
 * @since $Date: 2005/08/05 10:12:13 $ Tag: $Name: $
 */
public class ExchangeRateListMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapOBToForm(...).");

		ExchangeRateListForm form = (ExchangeRateListForm) aForm;
		String event = form.getEvent();

		int offset = ((Integer) hashMap.get("offset")).intValue();
		int length = ((Integer) hashMap.get("length")).intValue();
		Locale locale = (Locale) hashMap.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

		DefaultLogger.debug(this, "event is " + event);

		// Need to readjust the form value for proper display.
		if (ExchangeRateListAction.EVENT_READ.equals(event) || ExchangeRateListAction.EVENT_REMOVE.equals(event)
				|| ExchangeRateListAction.EVENT_PAGINATE.equals(event)
				|| ExchangeRateListAction.EVENT_PAGINATE_CHECKER.equals(event)
				|| ExchangeRateListAction.EVENT_LIST_STAGING.equals(event)
				|| ExchangeRateListAction.EVENT_READ_MAKER_EDIT.equals(event)
				|| ExchangeRateListAction.EVENT_PREPARE.equals(event)
				|| ExchangeRateListAction.EVENT_VIEW.equals(event)
				|| ExchangeRateListAction.EVENT_READ_MAKER_CLOSE.equals(event)) {

			IForexFeedGroupTrxValue value = (IForexFeedGroupTrxValue) object;
			IForexFeedGroup group = value.getStagingForexFeedGroup();

			extractForDisplay(offset, length, form, group, locale);

		}

		if (ExchangeRateListAction.EVENT_REMOVE.equals(event)) {

			form.setChkDeletes(new String[0]);

		}

		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...).");

		ExchangeRateListForm form = (ExchangeRateListForm) aForm;
		String event = form.getEvent();

		// int offset = ((Integer)hashMap.get("offset")).intValue();
		// int length = ((Integer)hashMap.get("length")).intValue();

		if (ExchangeRateListAction.EVENT_REMOVE.equals(event)) {
			// Will return a List of feedGroup OB, String[].

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			String[] currencyDescriptionArr = form.getUpdatedCurrencyDescription();
			
			
			IForexFeedEntry[] feedEntriesArr = new IForexFeedEntry[updatedUnitPricesArr.length];
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				feedEntriesArr[i] = new OBForexFeedEntry();
				try {
				//	feedEntriesArr[i].setBuyRate(new BigDecimal(updatedUnitPricesArr[i]));
					
					//Phase 3 CR:comma separated
					feedEntriesArr[i].setBuyRate(new BigDecimal(UIUtil.removeComma(updatedUnitPricesArr[i])));
					feedEntriesArr[i].setCurrencyDescription(currencyDescriptionArr[i]);
				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
					feedEntriesArr[i].setBuyRate(new BigDecimal(0));
				}
				feedEntriesArr[i].setBuyCurrency(form.getBuyCurrencies()[i]);
			}
			IForexFeedGroup feedGroup = new OBForexFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			String[] chkDeletes = form.getChkDeletes();

			List returnList = new ArrayList();
			returnList.add(feedGroup);
			returnList.add(chkDeletes);
			return returnList;

		}
		else if (ExchangeRateListAction.EVENT_ADD.equals(event)) {
			// Will return a feedGroup OB,

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			String[] currencyDescriptionArr = form.getUpdatedCurrencyDescription();
			String[] buyCurrencies = form.getBuyCurrencies();
			IForexFeedEntry[] feedEntriesArr = null;

			if (updatedUnitPricesArr != null) {
				feedEntriesArr = new IForexFeedEntry[updatedUnitPricesArr.length];
				for (int i = 0; i < updatedUnitPricesArr.length; i++) {
					feedEntriesArr[i] = new OBForexFeedEntry();
					try {
//						feedEntriesArr[i].setBuyRate(new BigDecimal(updatedUnitPricesArr[i]));
						
						//Phase 3 CR:comma separated
						feedEntriesArr[i].setBuyRate(new BigDecimal(UIUtil.removeComma(updatedUnitPricesArr[i])));
						feedEntriesArr[i].setCurrencyDescription(currencyDescriptionArr[i]);
					}
					catch (Exception e) {
						DefaultLogger.warn(this, "value is not double-parseable.");
						feedEntriesArr[i].setBuyRate(new BigDecimal(0));
					}

					feedEntriesArr[i].setBuyCurrency(buyCurrencies[i]);
				}
			}
			else {
				feedEntriesArr = new IForexFeedEntry[0];
			}

			IForexFeedGroup feedGroup = new OBForexFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			return feedGroup;

		}
		else if (ExchangeRateListAction.EVENT_SAVE.equals(event) || ExchangeRateListAction.EVENT_PAGINATE.equals(event) || ExchangeRateListAction.EVENT_PAGINATE_CHECKER.equals(event)
				|| ExchangeRateListAction.EVENT_SUBMIT.equals(event)) {
			// Will return a List of target offset as String,feed group OB.

			// Note that the offset is at the first record of the
			// CURRENT range.

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			String[] currencyDescriptionArr = form.getUpdatedCurrencyDescription();
			
			if (updatedUnitPricesArr == null) {
				updatedUnitPricesArr = new String[0];
			}

			String[] buyCurrencies = (form.getBuyCurrencies() == null) ? new String[0] : form.getBuyCurrencies();

			IForexFeedEntry[] feedEntriesArr = new IForexFeedEntry[updatedUnitPricesArr.length];
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				feedEntriesArr[i] = new OBForexFeedEntry();
				feedEntriesArr[i].setBuyCurrency(buyCurrencies[i]);
//				feedEntriesArr[i].setBuyRate(new BigDecimal(updatedUnitPricesArr[i]));
//				feedEntriesArr[i].setSellRate(new BigDecimal(updatedUnitPricesArr[i]));
				
				//Phase 3 CR:comma separated
				feedEntriesArr[i].setBuyRate(new BigDecimal(UIUtil.removeComma(updatedUnitPricesArr[i])));
				feedEntriesArr[i].setSellRate(new BigDecimal(UIUtil.removeComma(updatedUnitPricesArr[i])));
				
				feedEntriesArr[i].setCurrencyDescription(currencyDescriptionArr[i]);
			}
			IForexFeedGroup feedGroup = new OBForexFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			List returnList = new ArrayList();
			returnList.add(form.getTargetOffset());
			returnList.add(feedGroup);

			return returnList;

		}
		else if (ExchangeRateListAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)
				|| ExchangeRateListAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));

		}
		else if (ExchangeRateListAction.EVENT_LIST_VIEW.equals(event)
				|| ExchangeRateListAction.EVENT_LIST_READ.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}

		return null;
	}

	private void extractForDisplay(int offset, int length, ExchangeRateListForm form, IForexFeedGroup group,
			Locale locale) {

		if (group == null) {
			// Do nothing when there is no group.
			return;
		}

		IForexFeedEntry[] entries = group.getFeedEntries();

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
			updatedUnitPricesArr[i - offset] = UIUtil.formatBigDecimalToStr(entries[i].getBuyRate());
		}

		form.setUpdatedUnitPrices(updatedUnitPricesArr);
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(ExchangeRateListMapper.class.getName(), "offset " + offset + " + length " + length
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
			DefaultLogger.debug(ExchangeRateListMapper.class.getName(), "adjusted offset = " + adjustedOffset);
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

}
