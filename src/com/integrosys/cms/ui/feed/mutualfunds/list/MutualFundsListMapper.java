/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/BondListMapper.java,v 1.24 2005/08/05 10:11:42 hshii Exp $
 */
package com.integrosys.cms.ui.feed.mutualfunds.list;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.IMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry;
import com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedGroup;
import com.integrosys.cms.app.feed.trx.mutualfunds.IMutualFundsFeedGroupTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public class MutualFundsListMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapOBToForm(...).");

		MutualFundsListForm form = (MutualFundsListForm) aForm;
		String event = form.getEvent();

		int offset = ((Integer) hashMap.get("offset")).intValue();
		int length = ((Integer) hashMap.get("length")).intValue();

		DefaultLogger.debug(this, "event is " + event);

		// Need to readjust the form value for proper display.
		if (MutualFundsListAction.EVENT_READ.equals(event) || MutualFundsListAction.EVENT_REMOVE.equals(event)
				|| MutualFundsListAction.EVENT_PAGINATE.equals(event) || MutualFundsListAction.EVENT_LIST_STAGING.equals(event)
				|| MutualFundsListAction.EVENT_READ_MAKER_EDIT.equals(event) || MutualFundsListAction.EVENT_PREPARE.equals(event)) {

			IMutualFundsFeedGroupTrxValue value = (IMutualFundsFeedGroupTrxValue) object;
			IMutualFundsFeedGroup group = value.getStagingMutualFundsFeedGroup();

			extractForDisplay(offset, length, form, group);

		}

		if (MutualFundsListAction.EVENT_REMOVE.equals(event)) {

			form.setChkDeletes(new String[0]);
		}

		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...).");

		MutualFundsListForm form = (MutualFundsListForm) aForm;
		String event = form.getEvent();
		
		if (MutualFundsListAction.EVENT_REMOVE.equals(event)) {
			// Will return a List feedGroup OB, String[].

			String[] updatedCurrentNAVArr = form.getUpdatedCurrentNAV();

			IMutualFundsFeedEntry[] feedEntriesArr = new IMutualFundsFeedEntry[updatedCurrentNAVArr.length];
			for (int i = 0; i < updatedCurrentNAVArr.length; i++) {
				feedEntriesArr[i] = new OBMutualFundsFeedEntry();
				try {
					//Phase 3 CR:comma separated
					updatedCurrentNAVArr[i]=UIUtil.removeComma(updatedCurrentNAVArr[i]);
					
					feedEntriesArr[i].setCurrentNAV(Double.parseDouble(updatedCurrentNAVArr[i]));

					feedEntriesArr[i].setSchemeName(form.getSchemeNames()[i]);

				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
					feedEntriesArr[i].setCurrentNAV(0);
				}
			}
			IMutualFundsFeedGroup feedGroup = new OBMutualFundsFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			String[] chkDeletes = form.getChkDeletes();

			List returnList = new ArrayList();
			returnList.add(feedGroup);
			returnList.add(chkDeletes);
			return returnList;
		}
		else if (MutualFundsListAction.EVENT_ADD.equals(event)) {
			// Will return a feedGroup OB,

			String[] updatedCurrentNAVArr = form.getUpdatedCurrentNAV();
			String[] schemeNames = form.getSchemeNames();
			IMutualFundsFeedEntry[] feedEntriesArr = null;
			
			if (updatedCurrentNAVArr != null) {
				feedEntriesArr = new IMutualFundsFeedEntry[updatedCurrentNAVArr.length];
				for (int i = 0; i < updatedCurrentNAVArr.length; i++) {
					feedEntriesArr[i] = new OBMutualFundsFeedEntry();
					try {
						//Phase 3 CR:comma separated
						updatedCurrentNAVArr[i]=UIUtil.removeComma(updatedCurrentNAVArr[i]);
						
						feedEntriesArr[i].setCurrentNAV(Double.parseDouble(updatedCurrentNAVArr[i]));
						feedEntriesArr[i].setSchemeName(schemeNames[i]);
						
					}
					catch (Exception e) {
						DefaultLogger.warn(this, "value is not double-parseable.");
						feedEntriesArr[i].setCurrentNAV(0);
					}
				}
			}
			else {
				feedEntriesArr = new IMutualFundsFeedEntry[0];
			}

			IMutualFundsFeedGroup feedGroup = new OBMutualFundsFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			return feedGroup;

		}
		else if (MutualFundsListAction.EVENT_SAVE.equals(event) || MutualFundsListAction.EVENT_PAGINATE.equals(event)
				|| MutualFundsListAction.EVENT_SUBMIT.equals(event)) {
			// Will return a List of offset as String, feed group OB.

			// Note that the offset is at the first record of the
			// CURRENT range.
			DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
			String[] updatedCurrentNAVArr = form.getUpdatedCurrentNAV();
			String[] schemeTypes = form.getSchemeTypeArr();
			String[] startDateArr = form.getStartDateArr();
			String[] expiryDateArr = form.getExpiryDateArr();
			String[] schemeNames = form.getSchemeNames();
			String[] schemeCodesArr = form.getSchemeCodes();
			String[] schemeTyepeList = form.getSchemeTypeList();
			String[] lastUpdatedDateArr = form.getLastUpdatedDate();
			
			IMutualFundsFeedGroup feedGroup = new OBMutualFundsFeedGroup();
			
			if (updatedCurrentNAVArr == null) {
				updatedCurrentNAVArr = new String[0];
			}
			
			IMutualFundsFeedEntry[] feedEntriesArr = new IMutualFundsFeedEntry[updatedCurrentNAVArr.length];
			for (int i = 0; i < updatedCurrentNAVArr.length; i++) {
				feedEntriesArr[i] = new OBMutualFundsFeedEntry();
				
				//Phase 3 CR:comma separated
				updatedCurrentNAVArr[i]=UIUtil.removeComma(updatedCurrentNAVArr[i]);
				
				feedEntriesArr[i].setCurrentNAV(Double.parseDouble(updatedCurrentNAVArr[i]));
				feedEntriesArr[i].setSchemeName(schemeNames[i]);
				
				if(schemeTypes[i] != null && !schemeTypes[i].equals(""))
					feedEntriesArr[i].setSchemeType(schemeTypes[i]);
				else
					feedEntriesArr[i].setSchemeType(schemeTyepeList[i]);
				
				feedEntriesArr[i].setSchemeCode(schemeCodesArr[i]);
				try{
					if(startDateArr[i]!=null && !startDateArr[i].equals(""))
						feedEntriesArr[i].setStartDate(df.parse(startDateArr[i]));
					
					if(expiryDateArr[i]!=null && !expiryDateArr[i].equals("") 
							&& ((schemeTypes[i]!=null && !schemeTypes[i].equals("") && schemeTypes[i].equalsIgnoreCase("CLOSE")) 
							|| (schemeTyepeList[i]!=null && !schemeTyepeList[i].equals("") && schemeTyepeList[i].equalsIgnoreCase("CLOSE"))))
						feedEntriesArr[i].setExpiryDate(df.parse(expiryDateArr[i]));
					
					if(feedEntriesArr[i].getSchemeType().equalsIgnoreCase("OPEN")){
						feedEntriesArr[i].setExpiryDate(null);
					}
					
					feedEntriesArr[i].setLastUpdatedDate(df.parse(lastUpdatedDateArr[i]));
					
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			feedGroup.setFeedEntries(feedEntriesArr);
			List returnList = new ArrayList();
			returnList.add(form.getTargetOffset());
			returnList.add(feedGroup);
			
			return returnList;

		}
		else if (MutualFundsListAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)
				|| MutualFundsListAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));

		}
		else if (MutualFundsListAction.EVENT_LIST_VIEW.equals(event) || MutualFundsListAction.EVENT_LIST_READ.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}

		return null;
	}

	private void extractForDisplay(int offset, int length, MutualFundsListForm form, IMutualFundsFeedGroup group) {

		if (group == null) {
			// Do nothing when there is no group.
			return;
		}

		IMutualFundsFeedEntry[] entries = group.getFeedEntries();

		DefaultLogger.debug(this, "number of feed entries = " + entries.length);

		int limit = offset + length;
		if (limit > entries.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > entries.length "
					+ entries.length);

			limit = entries.length;
		}

		String[] updatedCurrentNAVArr = new String[limit - offset];

		String[] rating = new String[limit - offset];

		for (int i = offset; i < limit; i++) {
			updatedCurrentNAVArr[i - offset] = String.valueOf(entries[i].getCurrentNAV());

		}

		form.setUpdatedCurrentNAV(updatedCurrentNAVArr);
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(MutualFundsListMapper.class.getName(), "offset " + offset + " + length " + length
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
			DefaultLogger.debug(MutualFundsListMapper.class.getName(), "adjusted offset = " + adjustedOffset);
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
