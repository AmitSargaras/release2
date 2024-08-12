/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/unittrust/list/UnitTrustListMapper.java,v 1.28 2005/08/05 10:15:24 hshii Exp $
 */
package com.integrosys.cms.ui.feed.unittrust.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.bus.unittrust.OBUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.OBUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.trx.unittrust.IUnitTrustFeedGroupTrxValue;
import com.integrosys.cms.app.feed.trx.unittrust.OBUnitTrustFeedGroupTrxValue;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.28 $
 * @since $Date: 2005/08/05 10:15:24 $ Tag: $Name: $
 */
public class UnitTrustListMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapOBToForm(...).");

		UnitTrustListForm form = (UnitTrustListForm) aForm;
		String event = form.getEvent();

		int offset = ((Integer) hashMap.get("offset")).intValue();
		int length = ((Integer) hashMap.get("length")).intValue();
		DefaultLogger.debug(this, "event is " + event);

		// Need to readjust the form value for proper display.
		if (UnitTrustListAction.EVENT_READ.equals(event) || UnitTrustListAction.EVENT_REMOVE.equals(event)
				|| UnitTrustListAction.EVENT_PAGINATE.equals(event)
				|| UnitTrustListAction.EVENT_LIST_STAGING.equals(event)
				|| UnitTrustListAction.EVENT_READ_MAKER_EDIT.equals(event)
				|| UnitTrustListAction.EVENT_PREPARE.equals(event)) {

			IUnitTrustFeedGroupTrxValue value = (IUnitTrustFeedGroupTrxValue) object;
			IUnitTrustFeedGroup group = value.getStagingUnitTrustFeedGroup();

			extractForDisplay(offset, length, form, group);

		}

		if (UnitTrustListAction.EVENT_REMOVE.equals(event)) {
			form.setChkDeletes(new String[0]);
		}

		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...).");

		UnitTrustListForm form = (UnitTrustListForm) aForm;
		String event = form.getEvent();

		if (UnitTrustListAction.EVENT_REMOVE.equals(event)) {
			// Will return a List of feedGroup OB, String[].

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			IUnitTrustFeedEntry[] feedEntriesArr = new IUnitTrustFeedEntry[updatedUnitPricesArr.length];
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				feedEntriesArr[i] = new OBUnitTrustFeedEntry();
				try {
					feedEntriesArr[i].setUnitPrice(Double.parseDouble(updatedUnitPricesArr[i]));
				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
					feedEntriesArr[i].setUnitPrice(0);
				}
				feedEntriesArr[i].setName(form.getUnitTrustNames()[i]);
			}
			IUnitTrustFeedGroup feedGroup = new OBUnitTrustFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			String[] chkDeletes = form.getChkDeletes();

			List returnList = new ArrayList();
			returnList.add(feedGroup);
			returnList.add(chkDeletes);
			return returnList;

		}
		else if (UnitTrustListAction.EVENT_ADD.equals(event)) {
			// Will return a feedGroup OB,

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			IUnitTrustFeedEntry[] feedEntriesArr = null;

			if (updatedUnitPricesArr != null) {
				feedEntriesArr = new IUnitTrustFeedEntry[updatedUnitPricesArr.length];
				for (int i = 0; i < updatedUnitPricesArr.length; i++) {
					feedEntriesArr[i] = new OBUnitTrustFeedEntry();
					try {
						feedEntriesArr[i].setUnitPrice(Double.parseDouble(updatedUnitPricesArr[i]));
						feedEntriesArr[i].setName(form.getUnitTrustNames()[i]);
					}
					catch (Exception e) {
						DefaultLogger.warn(this, "value is not double-parseable.");
						feedEntriesArr[i].setUnitPrice(0);
					}
					feedEntriesArr[i].setName(form.getUnitTrustNames()[i]);
				}
			}
			else {
				feedEntriesArr = new IUnitTrustFeedEntry[0];
			}

			IUnitTrustFeedGroup feedGroup = new OBUnitTrustFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			return feedGroup;

		}
		else if (UnitTrustListAction.EVENT_SAVE.equals(event) || UnitTrustListAction.EVENT_PAGINATE.equals(event)
				|| UnitTrustListAction.EVENT_SUBMIT.equals(event)) {
			// Will return a List of target offset as String,
			// feed group OB.

			// Note that the offset is at the first record of the
			// CURRENT range.

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			if (updatedUnitPricesArr == null) {
				updatedUnitPricesArr = new String[0];
			}

			IUnitTrustFeedEntry[] feedEntriesArr = new IUnitTrustFeedEntry[updatedUnitPricesArr.length];
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				feedEntriesArr[i] = new OBUnitTrustFeedEntry();
				feedEntriesArr[i].setUnitPrice(Double.parseDouble(updatedUnitPricesArr[i]));
				feedEntriesArr[i].setName(form.getUnitTrustNames()[i]);
			}
			IUnitTrustFeedGroup feedGroup = new OBUnitTrustFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			List returnList = new ArrayList();
			returnList.add(form.getTargetOffset());
			returnList.add(feedGroup);

			return returnList;

		}
		else if (UnitTrustListAction.EVENT_READ.equals(event)
				|| UnitTrustListAction.EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)
				|| UnitTrustListAction.EVENT_READ_MAKER_CLOSE.equals(event)
				|| UnitTrustListAction.EVENT_READ_MAKER_EDIT.equals(event)
				|| UnitTrustListAction.EVENT_VIEW.equals(event) || UnitTrustListAction.EVENT_PREPARE.equals(event)) {
			// Will return a trx value.

			IUnitTrustFeedGroupTrxValue value = new OBUnitTrustFeedGroupTrxValue();
			IUnitTrustFeedGroup group = new OBUnitTrustFeedGroup();
			group.setSubType(form.getCountryCode());
			value.setUnitTrustFeedGroup(group);
			value.setTransactionID(form.getTrxId());
			return value;

		}
		else if (UnitTrustListAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)
				|| UnitTrustListAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));

		}
		else if (UnitTrustListAction.EVENT_LIST_VIEW.equals(event) || UnitTrustListAction.EVENT_LIST_READ.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}

		return null;
	}

	private void extractForDisplay(int offset, int length, UnitTrustListForm form, IUnitTrustFeedGroup group) {

		if (group == null) {
			// Do nothing when there is no group.
			return;
		}

		IUnitTrustFeedEntry[] entries = group.getFeedEntries();

		DefaultLogger.debug(this, "number of feed entries = " + entries.length);

		int limit = offset + length;
		if (limit > entries.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > entries.length "
					+ entries.length);

			limit = entries.length;
		}

		String[] updatedUnitPricesArr = new String[limit - offset];
		for (int i = offset; i < limit; i++) {
			updatedUnitPricesArr[i - offset] = String.valueOf(entries[i].getUnitPrice());
		}

		form.setUpdatedUnitPrices(updatedUnitPricesArr);
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(UnitTrustListMapper.class.getName(), "offset " + offset + " + length " + length
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
			DefaultLogger.debug(UnitTrustListMapper.class.getName(), "adjusted offset = " + adjustedOffset);
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
