/*
Copyright Integro Technologies Pte Ltd
$Header$
 */
package com.integrosys.cms.ui.creditriskparam.unittrust;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.ICreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParam;
import com.integrosys.cms.app.creditriskparam.bus.OBCreditRiskParamGroup;
import com.integrosys.cms.app.creditriskparam.trx.ICreditRiskParamGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.OBCreditRiskParamGroupTrxValue;

/**
 * CreditRiskParamUnitTrustMapper Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class CreditRiskParamUnitTrustMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapOBToForm(...).");

		CreditRiskParamUnitTrustForm form = (CreditRiskParamUnitTrustForm) aForm;
		String event = form.getEvent();

		int offset = ((Integer) hashMap.get("offset")).intValue();
		int length = ((Integer) hashMap.get("length")).intValue();
		DefaultLogger.debug(this, "event is " + event);

		// Need to readjust the form value for proper display.
		if (CreditRiskParamUnitTrustAction.EVENT_READ.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_REMOVE.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_PAGINATE.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_READ_MAKER_EDIT.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_PREPARE.equals(event)) {

			ICreditRiskParamGroupTrxValue value = (ICreditRiskParamGroupTrxValue) object;
			ICreditRiskParamGroup group = value.getStagingCreditRiskParamGroup();

			extractForDisplay(offset, length, form, group);
		}

		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...).");

		CreditRiskParamUnitTrustForm form = (CreditRiskParamUnitTrustForm) aForm;
		String event = form.getEvent();

		DefaultLogger.debug(this, "mapper event : " + event);

		if (CreditRiskParamUnitTrustAction.EVENT_PAGINATE.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_SUBMIT.equals(event)) {
			// Will return a List of target offset as String,
			// feed group OB.

			// Note that the offset is at the first record of the
			// CURRENT range.

			String[] updatedMoaArr = form.getUpdatedMoa();
			String[] updatedMaxCapArr = form.getUpdatedMaxCap();
			String[] updatedChkSuspendedArr = form.getChkSuspended();

			if (updatedMoaArr == null) {
				updatedMoaArr = new String[0];
			}

			if (updatedMaxCapArr == null) {
				updatedMaxCapArr = new String[0];
			}

			if (updatedChkSuspendedArr == null) {
				updatedChkSuspendedArr = new String[0];
			}

			OBCreditRiskParam[] paramEntriesArr = new OBCreditRiskParam[updatedMoaArr.length];
			for (int i = 0; i < updatedMoaArr.length; i++) {
				paramEntriesArr[i] = new OBCreditRiskParam();

				try {
					paramEntriesArr[i].setMarginOfAdvance(Double.parseDouble(updatedMoaArr[i]));
				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
					paramEntriesArr[i].setMarginOfAdvance(0);
				}

				try {
					paramEntriesArr[i].setMaximumCap(Double.parseDouble(updatedMaxCapArr[i]));
				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
					paramEntriesArr[i].setMaximumCap(0);
				}

				DefaultLogger.debug(this, "i : " + i);

				if (updatedChkSuspendedArr.length == 0) {
					paramEntriesArr[i].setIsIntSuspend(ICMSConstant.FALSE_VALUE);
				}
				else {
					for (int j = 0; j < updatedChkSuspendedArr.length; j++) {

						DefaultLogger.debug(this, "j : " + j + " " + updatedChkSuspendedArr[j]);
						if (i == Integer.parseInt(updatedChkSuspendedArr[j])) {

							paramEntriesArr[i].setIsIntSuspend(ICMSConstant.TRUE_VALUE);
							break;
						}
						else {
							paramEntriesArr[i].setIsIntSuspend(ICMSConstant.FALSE_VALUE);
						}
					}
				}

				if ((paramEntriesArr[i].getIsIntSuspend() != null)
						&& (ICMSConstant.TRUE_VALUE).equals(paramEntriesArr[i].getIsIntSuspend())) {
					paramEntriesArr[i].setIsAcceptable(ICMSConstant.FALSE_VALUE);
				}
				else {
					paramEntriesArr[i].setIsAcceptable(ICMSConstant.TRUE_VALUE);
				}

			}
			ICreditRiskParamGroup paramGroup = new OBCreditRiskParamGroup();
			paramGroup.setFeedEntries(paramEntriesArr);

			List returnList = new ArrayList();
			returnList.add(form.getTargetOffset());
			returnList.add(paramGroup);

			return returnList;

		}
		else if (CreditRiskParamUnitTrustAction.EVENT_READ.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_READ_CHECKER_APPROVE_REJECT.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_READ_MAKER_CLOSE.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_READ_MAKER_EDIT.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_TO_TRACK.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_PREPARE.equals(event)) {
			// Will return a trx value.

			ICreditRiskParamGroupTrxValue value = new OBCreditRiskParamGroupTrxValue();
			ICreditRiskParamGroup group = new OBCreditRiskParamGroup();
			DefaultLogger.debug(this, "form.getGroupFeedId()" + form.getGroupFeedId());
			if ((form.getGroupFeedId() != null) && !("").equals(form.getGroupFeedId())) {
				group.setCreditRiskParamGroupID(Long.valueOf(form.getGroupFeedId()).longValue());

			}
			DefaultLogger.debug(this, "group.getCreditRiskParamGroupID()" + group.getCreditRiskParamGroupID());
			value.setCreditRiskParamGroup(group);
			value.setTransactionID(form.getTrxId());
			return value;

		}
		else if (CreditRiskParamUnitTrustAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)
				|| CreditRiskParamUnitTrustAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));

		}
		else if (CreditRiskParamUnitTrustAction.EVENT_LIST_TO_TRACK.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}

		return null;
	}

	private void extractForDisplay(int offset, int length, CreditRiskParamUnitTrustForm form,
			ICreditRiskParamGroup group) {

		if (group == null) {
			// Do nothing when there is no group.
			return;
		}

		OBCreditRiskParam[] entries = group.getFeedEntries();

		DefaultLogger.debug(this, "number of feed entries = " + entries.length);

		int limit = offset + length;
		if (limit > entries.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > entries.length "
					+ entries.length);

			limit = entries.length;
		}

		String[] updatedMoaArr = new String[limit - offset];
		String[] updatedMaxCapArr = new String[limit - offset];
		String[] updatedChkSuspendedArr = new String[limit - offset];

		for (int i = offset; i < limit; i++) {
			updatedMoaArr[i - offset] = String.valueOf(entries[i].getMarginOfAdvance());

			updatedMaxCapArr[i - offset] = String.valueOf(entries[i].getMaximumCap());

			updatedChkSuspendedArr[i - offset] = entries[i].getIsIntSuspend();
		}

		form.setUpdatedMoa(updatedMoaArr);
		form.setUpdatedMaxCap(updatedMaxCapArr);
		form.setChkSuspended(updatedChkSuspendedArr);
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(CreditRiskParamUnitTrustMapper.class.getName(), "offset " + offset + " + length "
					+ length + " >= maxSize " + maxSize);
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
			DefaultLogger.debug(CreditRiskParamUnitTrustMapper.class.getName(), "adjusted offset = " + adjustedOffset);
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
