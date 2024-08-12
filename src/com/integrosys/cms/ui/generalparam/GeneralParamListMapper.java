package com.integrosys.cms.ui.generalparam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.app.generalparam.bus.OBGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.OBGeneralParamGroup;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class GeneralParamListMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapOBToForm(...).");

		GeneralParamListForm form = (GeneralParamListForm) aForm;
		String event = form.getEvent();

		int offset = ((Integer) hashMap.get("offset")).intValue();
		int length = ((Integer) hashMap.get("length")).intValue();

		DefaultLogger.debug(this, "event is " + event);

		// Need to readjust the form value for proper display.
		if (GeneralParamListAction.EVENT_READ.equals(event) || GeneralParamListAction.EVENT_REMOVE.equals(event)
				|| GeneralParamListAction.EVENT_PAGINATE.equals(event) || GeneralParamListAction.EVENT_LIST_STAGING.equals(event)
				|| GeneralParamListAction.EVENT_READ_MAKER_EDIT.equals(event) || GeneralParamListAction.EVENT_PREPARE.equals(event)) {

			IGeneralParamGroupTrxValue value = (IGeneralParamGroupTrxValue) object;
			IGeneralParamGroup group = value.getStagingGeneralParamGroup();

			extractForDisplay(offset, length, form, group);

		}

		if (GeneralParamListAction.EVENT_REMOVE.equals(event)) {

			form.setChkDeletes(new String[0]);
		}

		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...).");

		GeneralParamListForm form = (GeneralParamListForm) aForm;
		String event = form.getEvent();

		if (GeneralParamListAction.EVENT_REMOVE.equals(event)) {
			// Will return a List feedGroup OB, String[].

			String[] updatedParamValueArr = form.getUpdatedParamValue();

			IGeneralParamEntry[] feedEntriesArr = new IGeneralParamEntry[updatedParamValueArr.length];
			for (int i = 0; i < updatedParamValueArr.length; i++) {
				feedEntriesArr[i] = new OBGeneralParamEntry();
				try {
					feedEntriesArr[i].setParamValue(updatedParamValueArr[i]);

					feedEntriesArr[i].setParamName(form.getParamNames()[i]);

				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
					feedEntriesArr[i].setParamValue("");
				}
			}
			IGeneralParamGroup feedGroup = new OBGeneralParamGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			String[] chkDeletes = form.getChkDeletes();

			List returnList = new ArrayList();
			returnList.add(feedGroup);
			returnList.add(chkDeletes);
			return returnList;
		}
		else if (GeneralParamListAction.EVENT_ADD.equals(event)) {
			// Will return a feedGroup OB,

			String[] updatedParamValueArr = form.getUpdatedParamValue();
			String[] schemeNames = form.getParamNames();
			IGeneralParamEntry[] feedEntriesArr = null;

			if (updatedParamValueArr != null) {
				feedEntriesArr = new IGeneralParamEntry[updatedParamValueArr.length];
				for (int i = 0; i < updatedParamValueArr.length; i++) {
					feedEntriesArr[i] = new OBGeneralParamEntry();
					try {
						feedEntriesArr[i].setParamValue(updatedParamValueArr[i]);
						feedEntriesArr[i].setParamName(schemeNames[i]);
					}
					catch (Exception e) {
						DefaultLogger.warn(this, "value is not double-parseable.");
						feedEntriesArr[i].setParamValue("");
					}
				}
			}
			else {
				feedEntriesArr = new IGeneralParamEntry[0];
			}

			IGeneralParamGroup feedGroup = new OBGeneralParamGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			return feedGroup;

		}
		else if (GeneralParamListAction.EVENT_SAVE.equals(event) || GeneralParamListAction.EVENT_PAGINATE.equals(event)
				|| GeneralParamListAction.EVENT_SUBMIT.equals(event)
				|| "checker_paginate".equals(event)) {
			// Will return a List of offset as String, feed group OB.

			// Note that the offset is at the first record of the
			// CURRENT range.

			String[] updatedParamValueArr = form.getUpdatedParamValue();
			String[] schemeNames = form.getParamNames();
			String[] lastUpdatedDate = form.getLastUpdatedDate();
			
			if (updatedParamValueArr == null) {
				updatedParamValueArr = new String[0];
			}

			IGeneralParamEntry[] feedEntriesArr = new IGeneralParamEntry[updatedParamValueArr.length];
			for (int i = 0; i < updatedParamValueArr.length; i++) {
				feedEntriesArr[i] = new OBGeneralParamEntry();
				feedEntriesArr[i].setParamValue(updatedParamValueArr[i]);
				feedEntriesArr[i].setParamName(schemeNames[i]);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					feedEntriesArr[i].setLastUpdatedDate(sdf.parse(lastUpdatedDate[i]));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			IGeneralParamGroup feedGroup = new OBGeneralParamGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			List returnList = new ArrayList();
			returnList.add(form.getTargetOffset());
			returnList.add(feedGroup);

			return returnList;

		}
		else if (GeneralParamListAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)
				|| GeneralParamListAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));

		}
		else if (GeneralParamListAction.EVENT_LIST_VIEW.equals(event) || GeneralParamListAction.EVENT_LIST_READ.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}

		return null;
	}

	private void extractForDisplay(int offset, int length, GeneralParamListForm form, IGeneralParamGroup group) {

		if (group == null) {
			// Do nothing when there is no group.
			return;
		}

		IGeneralParamEntry[] entries = group.getFeedEntries();

		DefaultLogger.debug(this, "number of feed entries = " + entries.length);

		int limit = offset + length;
		if (limit > entries.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > entries.length "
					+ entries.length);

			limit = entries.length;
		}

		String[] updatedParamValueArr = new String[limit - offset];

		String[] rating = new String[limit - offset];

		for (int i = offset; i < limit; i++) {
			updatedParamValueArr[i - offset] = String.valueOf(entries[i].getParamValue());

		}

		form.setUpdatedParamValue(updatedParamValueArr);
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(GeneralParamListMapper.class.getName(), "offset " + offset + " + length " + length
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
			DefaultLogger.debug(GeneralParamListMapper.class.getName(), "adjusted offset = " + adjustedOffset);
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
