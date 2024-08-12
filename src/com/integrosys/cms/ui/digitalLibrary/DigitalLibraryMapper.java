/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/BondListMapper.java,v 1.24 2005/08/05 10:11:42 hshii Exp $
 */
package com.integrosys.cms.ui.digitalLibrary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryEntry;
import com.integrosys.cms.app.digitalLibrary.bus.IDigitalLibraryGroup;
import com.integrosys.cms.app.digitalLibrary.bus.OBDigitalLibraryEntry;
import com.integrosys.cms.app.digitalLibrary.bus.OBDigitalLibraryGroup;
import com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2005/08/05 10:11:42 $ Tag: $Name: $
 */
public class DigitalLibraryMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapOBToForm(...).");

		DigitalLibraryForm form = (DigitalLibraryForm) aForm;
		String event = form.getEvent();

		int offset = ((Integer) hashMap.get("offset")).intValue();
		int length = ((Integer) hashMap.get("length")).intValue();

		DefaultLogger.debug(this, "event is " + event);

		// Need to readjust the form value for proper display.
		if (DigitalLibraryAction.EVENT_READ.equals(event) || DigitalLibraryAction.EVENT_REMOVE.equals(event)
				|| DigitalLibraryAction.EVENT_PAGINATE.equals(event) || DigitalLibraryAction.EVENT_LIST_STAGING.equals(event)
				|| DigitalLibraryAction.EVENT_READ_MAKER_EDIT.equals(event) || DigitalLibraryAction.EVENT_PREPARE.equals(event)) {

			IDigitalLibraryTrxValue value = (IDigitalLibraryTrxValue) object;
			IDigitalLibraryGroup group = value.getStagingDigitalLibraryGroup();

			extractForDisplay(offset, length, form, group);

		}

		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...).");

		DigitalLibraryForm form = (DigitalLibraryForm) aForm;
		String event = form.getEvent();

		if (DigitalLibraryAction.EVENT_REMOVE.equals(event)) {
			// Will return a List feedGroup OB, String[].

			String[] climsDocCategory = form.getClimsDocCategory();
			String[] digiLibDocCategory = form.getDigiLibDocCategory();

			IDigitalLibraryEntry[] feedEntriesArr = new IDigitalLibraryEntry[climsDocCategory.length];
			for (int i = 0; i < climsDocCategory.length; i++) {
				feedEntriesArr[i] = new OBDigitalLibraryEntry();
				try {
					feedEntriesArr[i].setClimsDocCategory(climsDocCategory[i]);
					if(digiLibDocCategory[i] != null && digiLibDocCategory[i].trim().isEmpty())
					{
						feedEntriesArr[i].setDigiLibDocCategory(null);	
					}
					else
					{
					feedEntriesArr[i].setDigiLibDocCategory(digiLibDocCategory[i]);
					}
					
				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
					//feedEntriesArr[i].setClimsDocCategory(0);
				}
			}
			IDigitalLibraryGroup feedGroup = new OBDigitalLibraryGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			List returnList = new ArrayList();
			returnList.add(feedGroup);
			return returnList;
		}
		else if (DigitalLibraryAction.EVENT_ADD.equals(event)) {
			// Will return a feedGroup OB,

			String[] climsDocCategory = form.getClimsDocCategory();
			String[] digiLibDocCategory = form.getDigiLibDocCategory();
			/*String[] level2 = form.getLevel2();
			String[] level3 = form.getLevel3();
			String[] level4 = form.getLevel4();*/
			IDigitalLibraryEntry[] feedEntriesArr = null;

			if (climsDocCategory != null) {
				feedEntriesArr = new IDigitalLibraryEntry[climsDocCategory.length];
				for (int i = 0; i < climsDocCategory.length; i++) {
					feedEntriesArr[i] = new OBDigitalLibraryEntry();
					try {
						feedEntriesArr[i].setClimsDocCategory(climsDocCategory[i]);
						
						if(digiLibDocCategory[i] == null)
						{
							feedEntriesArr[i].setDigiLibDocCategory("");	
						}
						else
						{
						feedEntriesArr[i].setDigiLibDocCategory(digiLibDocCategory[i]);
						}
						
						feedEntriesArr[i].setDigiLibDocCategory(digiLibDocCategory[i]); //Phase 3 CR:comma separated
						/*feedEntriesArr[i].setLevel2(UIUtil.removeComma(level2[i]));
						feedEntriesArr[i].setLevel3(UIUtil.removeComma(level3[i]));
						feedEntriesArr[i].setLevel4(UIUtil.removeComma(level4[i]));*/
						
					}
					catch (Exception e) {
						DefaultLogger.warn(this, "value is not double-parseable.");
						//feedEntriesArr[i].setClimsDocCategory(0);
					}
				}
			}
			else {
				feedEntriesArr = new IDigitalLibraryEntry[0];
			}

			IDigitalLibraryGroup feedGroup = new OBDigitalLibraryGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			return feedGroup;

		}
		else if (DigitalLibraryAction.EVENT_SAVE.equals(event) || DigitalLibraryAction.EVENT_PAGINATE.equals(event)
				|| DigitalLibraryAction.EVENT_SUBMIT.equals(event)) {
			// Will return a List of offset as String, feed group OB.

			// Note that the offset is at the first record of the
			// CURRENT range.

			String[] climsDocCategory = form.getClimsDocCategory();
			String[] digiLibDocCategory = form.getDigiLibDocCategory();
			/*String[] level2 = form.getLevel2();
			String[] level3 = form.getLevel3();
			String[] level4 = form.getLevel4();*/
			if (climsDocCategory == null) {
				climsDocCategory = new String[0];
			}

			IDigitalLibraryEntry[] feedEntriesArr = new IDigitalLibraryEntry[climsDocCategory.length];
			for (int i = 0; i < climsDocCategory.length; i++) {
				feedEntriesArr[i] = new OBDigitalLibraryEntry();
				feedEntriesArr[i].setClimsDocCategory(climsDocCategory[i]);
				feedEntriesArr[i].setDigiLibDocCategory(digiLibDocCategory[i]);  //Phase 3 CR:comma separated
				/*feedEntriesArr[i].setLevel2(UIUtil.removeComma(level2[i]));
				feedEntriesArr[i].setLevel3(UIUtil.removeComma(level3[i]));
				feedEntriesArr[i].setLevel4(UIUtil.removeComma(level4[i]));*/
			}
			IDigitalLibraryGroup feedGroup = new OBDigitalLibraryGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			List returnList = new ArrayList();
			returnList.add(form.getTargetOffset());
			returnList.add(feedGroup);

			return returnList;

		}
		else if (DigitalLibraryAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)
				|| DigitalLibraryAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));

		}
		else if (DigitalLibraryAction.EVENT_LIST_VIEW.equals(event) || DigitalLibraryAction.EVENT_LIST_READ.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}

		return null;
	}

	private void extractForDisplay(int offset, int length, DigitalLibraryForm form, IDigitalLibraryGroup group) {

		if (group == null) {
			// Do nothing when there is no group.
			return;
		}

		IDigitalLibraryEntry[] entries = group.getFeedEntries();

		DefaultLogger.debug(this, "number of feed entries = " + entries.length);

		int limit = offset + length;
		if (limit > entries.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > entries.length "
					+ entries.length);

			limit = entries.length;
		}

		String[] climsDocCategory = new String[limit - offset];

		String[] digiLibDocCategory = new String[limit - offset];
		/*String[] level2 = new String[limit - offset];
		String[] level3 = new String[limit - offset];
		String[] level4 = new String[limit - offset];*/

		for (int i = offset; i < limit; i++) {
			climsDocCategory[i - offset] = String.valueOf(entries[i].getClimsDocCategory());

			if (entries[i].getDigiLibDocCategory()!=null) {
				digiLibDocCategory[i - offset] = String.valueOf((entries[i].getDigiLibDocCategory()));
			}
			
		}

		form.setClimsDocCategory(climsDocCategory);
		form.setDigiLibDocCategory(digiLibDocCategory);
		
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(DigitalLibraryMapper.class.getName(), "offset " + offset + " + length " + length
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
			DefaultLogger.debug(DigitalLibraryMapper.class.getName(), "adjusted offset = " + adjustedOffset);
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
