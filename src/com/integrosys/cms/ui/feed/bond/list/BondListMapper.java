/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/BondListMapper.java,v 1.24 2005/08/05 10:11:42 hshii Exp $
 */
package com.integrosys.cms.ui.feed.bond.list;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
import com.integrosys.cms.app.feed.bus.bond.OBBondFeedEntry;
import com.integrosys.cms.app.feed.bus.bond.OBBondFeedGroup;
import com.integrosys.cms.app.feed.trx.bond.IBondFeedGroupTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2005/08/05 10:11:42 $ Tag: $Name: $
 */
public class BondListMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapOBToForm(...).");

		BondListForm form = (BondListForm) aForm;
		String event = form.getEvent();

		int offset = ((Integer) hashMap.get("offset")).intValue();
		int length = ((Integer) hashMap.get("length")).intValue();

		DefaultLogger.debug(this, "event is " + event);

		// Need to readjust the form value for proper display.
		if (BondListAction.EVENT_READ.equals(event) || BondListAction.EVENT_REMOVE.equals(event)
				|| BondListAction.EVENT_PAGINATE.equals(event) || BondListAction.EVENT_LIST_STAGING.equals(event)
				|| BondListAction.EVENT_READ_MAKER_EDIT.equals(event) || BondListAction.EVENT_PREPARE.equals(event)) {

			IBondFeedGroupTrxValue value = (IBondFeedGroupTrxValue) object;
			IBondFeedGroup group = value.getStagingBondFeedGroup();

			extractForDisplay(offset, length, form, group);

		}

		if (BondListAction.EVENT_REMOVE.equals(event)) {

			form.setChkDeletes(new String[0]);
		}

		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...).");

		BondListForm form = (BondListForm) aForm;
		String event = form.getEvent();

		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
		if (BondListAction.EVENT_REMOVE.equals(event)) {
			// Will return a List feedGroup OB, String[].

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			String[] rating = form.getRatingArr();
			String[] isin = form.getIsinCode();
			String[] couponRate = form.getCouponRate();
			String[] maturityDate = form.getMaturityDateArr();
			String[] lastUpdateDate = form.getLastUpdateDate();

			IBondFeedEntry[] feedEntriesArr = new IBondFeedEntry[updatedUnitPricesArr.length];
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				feedEntriesArr[i] = new OBBondFeedEntry();
				try {
					//Phase 3 CR:comma separated
					updatedUnitPricesArr[i]=UIUtil.removeComma(updatedUnitPricesArr[i]);
					
					feedEntriesArr[i].setUnitPrice(Double.parseDouble(updatedUnitPricesArr[i]));
					feedEntriesArr[i].setIsinCode(isin[i]);
					feedEntriesArr[i].setRating(rating[i]);
					feedEntriesArr[i].setName(form.getBondNames()[i]);
					feedEntriesArr[i].setCouponRate(Double.parseDouble(couponRate[i]));
					feedEntriesArr[i].setMaturityDate(df.parse(maturityDate[i]));
					if( lastUpdateDate[i] != null)
						feedEntriesArr[i].setLastUpdateDate(df.parse(lastUpdateDate[i]));					
				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
//					feedEntriesArr[i].setUnitPrice(0);
				}
			}
			IBondFeedGroup feedGroup = new OBBondFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			String[] chkDeletes = form.getChkDeletes();

			List returnList = new ArrayList();
			returnList.add(feedGroup);
			returnList.add(chkDeletes);
			return returnList;
		}
		else if (BondListAction.EVENT_ADD.equals(event)) {
			// Will return a feedGroup OB,

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			String[] rating = form.getRatingArr();
			String[] bondNames = form.getBondNames();
			IBondFeedEntry[] feedEntriesArr = null;
			String[] isin = form.getIsinCode();
			String[] couponRate = form.getCouponRate();
			String[] maturityDate = form.getMaturityDateArr();
			String[] lastUpdateDate = form.getLastUpdateDate();
			
			if (updatedUnitPricesArr != null) {
				feedEntriesArr = new IBondFeedEntry[updatedUnitPricesArr.length];
				for (int i = 0; i < updatedUnitPricesArr.length; i++) {
					feedEntriesArr[i] = new OBBondFeedEntry();
					try {
						//Phase 3 CR:comma separated
						updatedUnitPricesArr[i]=UIUtil.removeComma(updatedUnitPricesArr[i]);
						
						feedEntriesArr[i].setUnitPrice(Double.parseDouble(updatedUnitPricesArr[i]));
						feedEntriesArr[i].setRating(rating[i]);
						feedEntriesArr[i].setName(bondNames[i]);
						feedEntriesArr[i].setIsinCode(isin[i]);
						feedEntriesArr[i].setCouponRate(Double.parseDouble(couponRate[i]));
						feedEntriesArr[i].setMaturityDate(df.parse(maturityDate[i]));
						if( lastUpdateDate[i] != null)
							feedEntriesArr[i].setLastUpdateDate(df.parse(lastUpdateDate[i]));
					}
					catch (Exception e) {
						DefaultLogger.warn(this, "value is not double-parseable.");
//						feedEntriesArr[i].setUnitPrice(0);
					}
				}
			}
			else {
				feedEntriesArr = new IBondFeedEntry[0];
			}

			IBondFeedGroup feedGroup = new OBBondFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			return feedGroup;

		}
		else if (BondListAction.EVENT_SAVE.equals(event) || BondListAction.EVENT_PAGINATE.equals(event)
				|| BondListAction.EVENT_SUBMIT.equals(event)) {
			// Will return a List of offset as String, feed group OB.

			// Note that the offset is at the first record of the
			// CURRENT range.

			String[] updatedUnitPricesArr = form.getUpdatedUnitPrices();
			String[] rating = form.getRatingArr();
			String[] bondNames = form.getBondNames();
			String[] bondCodes = form.getBondCodes();
			String[] isin = form.getIsinCode();
			String[] couponRate = form.getCouponRate();
			String[] maturityDate = form.getMaturityDateArr();
			String[] lastUpdateDate = form.getLastUpdateDate();
			
			if (updatedUnitPricesArr == null) {
				updatedUnitPricesArr = new String[0];
			}

			IBondFeedEntry[] feedEntriesArr = new IBondFeedEntry[updatedUnitPricesArr.length];
			for (int i = 0; i < updatedUnitPricesArr.length; i++) {
				feedEntriesArr[i] = new OBBondFeedEntry();
				try {
					//Phase 3 CR:comma separated
					updatedUnitPricesArr[i]=UIUtil.removeComma(updatedUnitPricesArr[i]);
					
					feedEntriesArr[i].setUnitPrice(Double.parseDouble(updatedUnitPricesArr[i]));
					feedEntriesArr[i].setRating(rating[i]);
					feedEntriesArr[i].setName(bondNames[i]);
					feedEntriesArr[i].setBondCode(bondCodes[i]);
					feedEntriesArr[i].setIsinCode(isin[i]);
					feedEntriesArr[i].setCouponRate(Double.parseDouble(couponRate[i]));
					feedEntriesArr[i].setMaturityDate(df.parse(maturityDate[i]));
					if( lastUpdateDate[i] != null)
						feedEntriesArr[i].setLastUpdateDate(df.parse(lastUpdateDate[i]));
				} catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
//					feedEntriesArr[i].setUnitPrice(0);
				}
			}
			IBondFeedGroup feedGroup = new OBBondFeedGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			List returnList = new ArrayList();
			returnList.add(form.getTargetOffset());
			returnList.add(feedGroup);

			return returnList;

		}
		else if (BondListAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)
				|| BondListAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));

		}
		else if (BondListAction.EVENT_LIST_VIEW.equals(event) || BondListAction.EVENT_LIST_READ.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}

		return null;
	}

	private void extractForDisplay(int offset, int length, BondListForm form, IBondFeedGroup group) {

		if (group == null) {
			// Do nothing when there is no group.
			return;
		}

		IBondFeedEntry[] entries = group.getFeedEntries();

		DefaultLogger.debug(this, "number of feed entries = " + entries.length);

		int limit = offset + length;
		if (limit > entries.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > entries.length "
					+ entries.length);

			limit = entries.length;
		}

		String[] updatedUnitPricesArr = new String[limit - offset];

		String[] rating = new String[limit - offset];
		String[] isin = new String[limit - offset];
		String[] couponRate = new String[limit - offset];
		String[] maturityDate = new String[limit - offset];
		String[] lastUpdateDate = new String[limit - offset];
		
		for (int i = offset; i < limit; i++) {
			updatedUnitPricesArr[i - offset] = String.valueOf(entries[i].getUnitPrice());
			couponRate[i - offset] = String.valueOf(entries[i].getCouponRate());
			maturityDate[i - offset] = String.valueOf(entries[i].getMaturityDate());
			if( entries[i].getLastUpdateDate() != null )
				lastUpdateDate[i - offset] = String.valueOf(entries[i].getLastUpdateDate());

			if (entries[i].getRating() != null) {
				rating[i - offset] = entries[i].getRating();
			}
			
			if ( entries[i].getIsinCode() != null) {
				isin[i - offset] = entries[i].getIsinCode();
			}			
			
		}

		form.setUpdatedUnitPrices(updatedUnitPricesArr);
		form.setRatingArr(rating);
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(BondListMapper.class.getName(), "offset " + offset + " + length " + length
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
			DefaultLogger.debug(BondListMapper.class.getName(), "adjusted offset = " + adjustedOffset);
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
