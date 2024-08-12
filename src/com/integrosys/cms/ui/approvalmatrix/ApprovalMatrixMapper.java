/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/BondListMapper.java,v 1.24 2005/08/05 10:11:42 hshii Exp $
 */
package com.integrosys.cms.ui.approvalmatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixEntry;
import com.integrosys.cms.app.approvalmatrix.bus.IApprovalMatrixGroup;
import com.integrosys.cms.app.approvalmatrix.bus.OBApprovalMatrixEntry;
import com.integrosys.cms.app.approvalmatrix.bus.OBApprovalMatrixGroup;
import com.integrosys.cms.app.approvalmatrix.trx.IApprovalMatrixTrxValue;
import com.integrosys.cms.ui.common.UIUtil;

/**
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2005/08/05 10:11:42 $ Tag: $Name: $
 */
public class ApprovalMatrixMapper extends AbstractCommonMapper {

	public CommonForm mapOBToForm(CommonForm aForm, Object object, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapOBToForm(...).");

		ApprovalMatrixForm form = (ApprovalMatrixForm) aForm;
		String event = form.getEvent();

		int offset = ((Integer) hashMap.get("offset")).intValue();
		int length = ((Integer) hashMap.get("length")).intValue();

		DefaultLogger.debug(this, "event is " + event);

		// Need to readjust the form value for proper display.
		if (ApprovalMatrixAction.EVENT_READ.equals(event) || ApprovalMatrixAction.EVENT_REMOVE.equals(event)
				|| ApprovalMatrixAction.EVENT_PAGINATE.equals(event) || ApprovalMatrixAction.EVENT_LIST_STAGING.equals(event)
				|| ApprovalMatrixAction.EVENT_READ_MAKER_EDIT.equals(event) || ApprovalMatrixAction.EVENT_PREPARE.equals(event)) {

			IApprovalMatrixTrxValue value = (IApprovalMatrixTrxValue) object;
			IApprovalMatrixGroup group = value.getStagingApprovalMatrixGroup();

			extractForDisplay(offset, length, form, group);

		}

		return form;
	}

	public Object mapFormToOB(CommonForm aForm, HashMap hashMap) throws MapperException {

		DefaultLogger.debug(this, "entering mapFormToOB(...).");

		ApprovalMatrixForm form = (ApprovalMatrixForm) aForm;
		String event = form.getEvent();

		if (ApprovalMatrixAction.EVENT_REMOVE.equals(event)) {
			// Will return a List feedGroup OB, String[].

			String[] riskGrade = form.getRiskGrade();
			String[] level1 = form.getLevel1();
			String[] level2 = form.getLevel2();
			String[] level3 = form.getLevel3();
			String[] level4 = form.getLevel4();

			IApprovalMatrixEntry[] feedEntriesArr = new IApprovalMatrixEntry[riskGrade.length];
			for (int i = 0; i < riskGrade.length; i++) {
				feedEntriesArr[i] = new OBApprovalMatrixEntry();
				try {
					feedEntriesArr[i].setRiskGrade(Integer.parseInt(riskGrade[i])); 
					feedEntriesArr[i].setLevel1(UIUtil.removeComma(level1[i]));   //Phase 3 CR:comma separated
					feedEntriesArr[i].setLevel2(UIUtil.removeComma(level2[i]));
					feedEntriesArr[i].setLevel3(UIUtil.removeComma(level3[i]));
					feedEntriesArr[i].setLevel4(UIUtil.removeComma(level4[i]));
				}
				catch (Exception e) {
					DefaultLogger.warn(this, "value is not double-parseable.");
					feedEntriesArr[i].setRiskGrade(0);
				}
			}
			IApprovalMatrixGroup feedGroup = new OBApprovalMatrixGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			List returnList = new ArrayList();
			returnList.add(feedGroup);
			return returnList;
		}
		else if (ApprovalMatrixAction.EVENT_ADD.equals(event)) {
			// Will return a feedGroup OB,

			String[] riskGrade = form.getRiskGrade();
			String[] level1 = form.getLevel1();
			String[] level2 = form.getLevel2();
			String[] level3 = form.getLevel3();
			String[] level4 = form.getLevel4();
			IApprovalMatrixEntry[] feedEntriesArr = null;

			if (riskGrade != null) {
				feedEntriesArr = new IApprovalMatrixEntry[riskGrade.length];
				for (int i = 0; i < riskGrade.length; i++) {
					feedEntriesArr[i] = new OBApprovalMatrixEntry();
					try {
						feedEntriesArr[i].setRiskGrade(Integer.parseInt(riskGrade[i]));
						feedEntriesArr[i].setLevel1(UIUtil.removeComma(level1[i])); //Phase 3 CR:comma separated
						feedEntriesArr[i].setLevel2(UIUtil.removeComma(level2[i]));
						feedEntriesArr[i].setLevel3(UIUtil.removeComma(level3[i]));
						feedEntriesArr[i].setLevel4(UIUtil.removeComma(level4[i]));
						
					}
					catch (Exception e) {
						DefaultLogger.warn(this, "value is not double-parseable.");
						feedEntriesArr[i].setRiskGrade(0);
					}
				}
			}
			else {
				feedEntriesArr = new IApprovalMatrixEntry[0];
			}

			IApprovalMatrixGroup feedGroup = new OBApprovalMatrixGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			return feedGroup;

		}
		else if (ApprovalMatrixAction.EVENT_SAVE.equals(event) || ApprovalMatrixAction.EVENT_PAGINATE.equals(event)
				|| ApprovalMatrixAction.EVENT_SUBMIT.equals(event)) {
			// Will return a List of offset as String, feed group OB.

			// Note that the offset is at the first record of the
			// CURRENT range.

			String[] riskGrade = form.getRiskGrade();
			String[] level1 = form.getLevel1();
			String[] level2 = form.getLevel2();
			String[] level3 = form.getLevel3();
			String[] level4 = form.getLevel4();
			if (riskGrade == null) {
				riskGrade = new String[0];
			}

			IApprovalMatrixEntry[] feedEntriesArr = new IApprovalMatrixEntry[riskGrade.length];
			for (int i = 0; i < riskGrade.length; i++) {
				feedEntriesArr[i] = new OBApprovalMatrixEntry();
				feedEntriesArr[i].setRiskGrade(Integer.parseInt(riskGrade[i]));
				feedEntriesArr[i].setLevel1(UIUtil.removeComma(level1[i]));  //Phase 3 CR:comma separated
				feedEntriesArr[i].setLevel2(UIUtil.removeComma(level2[i]));
				feedEntriesArr[i].setLevel3(UIUtil.removeComma(level3[i]));
				feedEntriesArr[i].setLevel4(UIUtil.removeComma(level4[i]));
			}
			IApprovalMatrixGroup feedGroup = new OBApprovalMatrixGroup();
			feedGroup.setFeedEntries(feedEntriesArr);

			List returnList = new ArrayList();
			returnList.add(form.getTargetOffset());
			returnList.add(feedGroup);

			return returnList;

		}
		else if (ApprovalMatrixAction.EVENT_LIST_CHECKER_APPROVE_REJECT.equals(event)
				|| ApprovalMatrixAction.EVENT_LIST_MAKER_CLOSE.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));

		}
		else if (ApprovalMatrixAction.EVENT_LIST_VIEW.equals(event) || ApprovalMatrixAction.EVENT_LIST_READ.equals(event)) {

			return new Integer(Integer.parseInt(form.getTargetOffset()));
		}

		return null;
	}

	private void extractForDisplay(int offset, int length, ApprovalMatrixForm form, IApprovalMatrixGroup group) {

		if (group == null) {
			// Do nothing when there is no group.
			return;
		}

		IApprovalMatrixEntry[] entries = group.getFeedEntries();

		DefaultLogger.debug(this, "number of feed entries = " + entries.length);

		int limit = offset + length;
		if (limit > entries.length) {
			DefaultLogger.debug(this, "offset " + offset + " + length " + length + " > entries.length "
					+ entries.length);

			limit = entries.length;
		}

		String[] riskGrade = new String[limit - offset];

		String[] level1 = new String[limit - offset];
		String[] level2 = new String[limit - offset];
		String[] level3 = new String[limit - offset];
		String[] level4 = new String[limit - offset];

		for (int i = offset; i < limit; i++) {
			riskGrade[i - offset] = String.valueOf(entries[i].getRiskGrade());

			if (entries[i].getLevel1()!=null) {
				level1[i - offset] = String.valueOf((entries[i].getLevel1()));
			}
			if (entries[i].getLevel2()!=null) {
				level2[i - offset] = String.valueOf(entries[i].getLevel2());
			}
			if (entries[i].getLevel3()!=null) {
				level3[i - offset] = String.valueOf(entries[i].getLevel3());
			}
			if (entries[i].getLevel4()!=null) {
				level4[i - offset] = String.valueOf(entries[i].getLevel4());
			}
		}

		form.setRiskGrade(riskGrade);
		form.setLevel1(level1);
		form.setLevel2(level2);
		form.setLevel3(level3);
		form.setLevel4(level4);
	}

	public static int adjustOffset(int offset, int length, int maxSize) {

		int adjustedOffset = offset;

		if (offset >= maxSize) {
			DefaultLogger.debug(ApprovalMatrixMapper.class.getName(), "offset " + offset + " + length " + length
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
			DefaultLogger.debug(ApprovalMatrixMapper.class.getName(), "adjusted offset = " + adjustedOffset);
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
